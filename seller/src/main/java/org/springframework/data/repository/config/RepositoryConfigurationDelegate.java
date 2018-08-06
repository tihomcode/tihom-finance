package org.springframework.data.repository.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author TiHom
 * create at 2018/8/6 0006.
 */
public class RepositoryConfigurationDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfigurationDelegate.class);
    private static final String REPOSITORY_REGISTRATION = "Spring Data {} - Registering repository: {} - Interface: {} - Factory: {}";
    private static final String MULTIPLE_MODULES = "Multiple Spring Data modules found, entering strict repository configuration mode!";
    static final String FACTORY_BEAN_OBJECT_TYPE = "factoryBeanObjectType";
    private final RepositoryConfigurationSource configurationSource;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final BeanNameGenerator beanNameGenerator;
    private final boolean isXml;
    private final boolean inMultiStoreMode;

    public RepositoryConfigurationDelegate(RepositoryConfigurationSource configurationSource, ResourceLoader resourceLoader, Environment environment) {
        this.isXml = configurationSource instanceof XmlRepositoryConfigurationSource;
        boolean isAnnotation = configurationSource instanceof AnnotationRepositoryConfigurationSource;
        Assert.isTrue(this.isXml || isAnnotation, "Configuration source must either be an Xml- or an AnnotationBasedConfigurationSource!");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null!");
        RepositoryBeanNameGenerator generator = new RepositoryBeanNameGenerator();
        generator.setBeanClassLoader(resourceLoader.getClassLoader());
        this.beanNameGenerator = generator;
        this.configurationSource = configurationSource;
        this.resourceLoader = resourceLoader;
        this.environment = defaultEnvironment(environment, resourceLoader);
        this.inMultiStoreMode = this.multipleStoresDetected();
    }

    private static Environment defaultEnvironment(Environment environment, ResourceLoader resourceLoader) {
        if (environment != null) {
            return environment;
        } else {
            return (Environment)(resourceLoader instanceof EnvironmentCapable ? ((EnvironmentCapable)resourceLoader).getEnvironment() : new StandardEnvironment());
        }
    }

    public List<BeanComponentDefinition> registerRepositoriesIn(BeanDefinitionRegistry registry, RepositoryConfigurationExtension extension) {
        extension.registerBeansForRoot(registry, this.configurationSource);
        RepositoryBeanDefinitionBuilder builder = new RepositoryBeanDefinitionBuilder(registry, extension, this.resourceLoader, this.environment);
        List<BeanComponentDefinition> definitions = new ArrayList();
        Iterator var5 = extension.getRepositoryConfigurations(this.configurationSource, this.resourceLoader, this.inMultiStoreMode).iterator();

        while(var5.hasNext()) {
            RepositoryConfiguration<? extends RepositoryConfigurationSource> configuration = (RepositoryConfiguration)var5.next();
            BeanDefinitionBuilder definitionBuilder = builder.build(configuration);
            extension.postProcess(definitionBuilder, this.configurationSource);
            if (this.isXml) {
                extension.postProcess(definitionBuilder, (XmlRepositoryConfigurationSource)this.configurationSource);
            } else {
                extension.postProcess(definitionBuilder, (AnnotationRepositoryConfigurationSource)this.configurationSource);
            }

            AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
            String beanName = this.beanNameGenerator.generateBeanName(beanDefinition, registry);

            AnnotationMetadata metadata = (AnnotationMetadata) configurationSource.getSource();
            //判断配置类是否使用primary进行了标注，如果有，就设为primary
            if(metadata.hasAnnotation(Primary.class.getName())){
                beanDefinition.setPrimary(true);
            }else if(metadata.hasAnnotation(RepositoryBeanNamePrefix.class.getName())) {
                //再判断是否使用了RepositoryBeanNamePrefix进行了标注，如果有，添加名称前缀
                Map<String,Object> prefixData = metadata.getAnnotationAttributes(RepositoryBeanNamePrefix.class.getName());
                String prefix = (String) prefixData.get("value");
                beanName = prefix + beanName;
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Spring Data {} - Registering repository: {} - Interface: {} - Factory: {}", new Object[]{extension.getModuleName(), beanName, configuration.getRepositoryInterface(), extension.getRepositoryFactoryClassName()});
            }

            beanDefinition.setAttribute("factoryBeanObjectType", configuration.getRepositoryInterface());
            registry.registerBeanDefinition(beanName, beanDefinition);
            definitions.add(new BeanComponentDefinition(beanDefinition, beanName));
        }

        return definitions;
    }

    private boolean multipleStoresDetected() {
        boolean multipleModulesFound = SpringFactoriesLoader.loadFactoryNames(RepositoryFactorySupport.class, this.resourceLoader.getClassLoader()).size() > 1;
        if (multipleModulesFound) {
            LOGGER.info("Multiple Spring Data modules found, entering strict repository configuration mode!");
        }

        return multipleModulesFound;
    }
}

package com.tihom.seller.configuration;

import com.tihom.entity.Order;
import com.tihom.seller.repositories.OrderRepository;
import com.tihom.seller.repositorybackup.VerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库相关操作配置
 * @author TiHom
 * create at 2018/8/5 0005.
 */

@Configuration
public class DataAccessConfiguration {

    @Autowired
    private JpaProperties properties;

    /**
     * 主数据源
     * @return
     */
    @Bean
    @Primary    //限定注解
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 备份数据源
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSource backupDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 实体管理
     * @param builder
     * @param dataSource
     * @return
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,@Qualifier("primaryDataSource") DataSource dataSource) { //使用限定词注入
        return builder
                .dataSource(dataSource) //这里类型注入的时候因为两个bean的类型都一致，所以会报错
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary")
                .build();
    }

    /**
     * 实体管理
     * @param builder
     * @param dataSource
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean backupEntityManagerFactory(
            EntityManagerFactoryBuilder builder,@Qualifier("backupDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup")
                .build();
    }


    protected Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
        vendorProperties.putAll(properties.getHibernateProperties(dataSource));
        return vendorProperties;
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManagerFactory")
                         LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(primaryEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager backupTransactionManager(@Qualifier("backupEntityManagerFactory")
                                                                        LocalContainerEntityManagerFactoryBean backupEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(backupEntityManagerFactory.getObject());
        return transactionManager;
    }

    // repository 扫描的时候，并不确定哪个先扫描，查看源代码
    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "primaryEntityManagerFactory",transactionManagerRef = "primaryTransactionManager")
    @Primary
    public class PrimaryConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "primaryEntityManagerFactory",transactionManagerRef = "backupEntityManagerFactory")
    @RepositoryBeanNamePrefix("read")
    public class ReadConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = VerifyRepository.class,
            entityManagerFactoryRef = "backupEntityManagerFactory",transactionManagerRef = "backupTransactionManager")
    public class BackupConfiguration {
    }

}

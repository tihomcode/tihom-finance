package com.tihom.manager.controller;

import com.tihom.entity.Product;
import com.tihom.entity.enums.ProductStatus;
import com.tihom.util.RestUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TiHom
 * create at 2018/8/2 0002.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //使用SpringBoot环境随机端口
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //正常产品数据
    private static List<Product> normals = new ArrayList<>();
    //异常数据
    private static List<Product> exceptions = new ArrayList<>();

    @BeforeClass
    public static void init(){
        Product p1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "活期盈-金色人生", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "朝朝盈-聚财", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

        Product e1 = new Product(null, "编号不可为空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.34));
        Product e2 = new Product("E002", "收益率范围错误", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "投资步长需为整数", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.44));

        exceptions.add(e1);
//        exceptions.add(e2);
//        exceptions.add(e3);

        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
        rest.setErrorHandler(errorHandler);
    }


    @Test
    public void create(){
        normals.forEach(product -> {
            Product result = RestUtil.postJSON(rest,baseUrl+"/products",product,Product.class);
            Assert.notNull(result.getCreateAt(),"插入失败");
        });

    }

    @Test
    public void createException(){
        exceptions.forEach(product -> {
            Map<String,String> result = RestUtil.postJSON(rest,baseUrl+"/products",product,HashMap.class);
            //System.out.println(result);
            Assert.isTrue(result.get("message").equals(product.getName()),"插入成功");
        });
    }

    @Test
    public void findOne(){
        normals.forEach(product -> {
            Product result = rest.getForObject(baseUrl+"/products/"+product.getId(),Product.class);
            Assert.isTrue(result.getId().equals(product.getId()),"查询失败");
        });
        exceptions.forEach(product -> {
            Product result = rest.getForObject(baseUrl+"/products/"+product.getId(),Product.class);
            Assert.isNull(result,"查询失败");
        });
    }

    public void query(){
        String[] ids = {"T001","T002","T003"};

        normals.forEach(product -> {
            rest.getForObject(baseUrl+"/products",Page.class);
        });
    }

}

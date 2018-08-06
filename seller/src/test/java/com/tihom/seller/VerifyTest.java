package com.tihom.seller;

import com.tihom.seller.repositories.OrderRepository;
import com.tihom.seller.service.VerifyService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 对账测试
 * @author TiHom
 * create at 2018/8/5 0005.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //使用SpringBoot环境随机端口
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerifyTest {

    @Autowired
    private VerifyService verifyService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepository backupOrderRepoistory;

    //生成对账文件的功能
    @Test
    public void makeVerificationTest(){
        //创建日历
        Date day = new GregorianCalendar(2018,0,1).getTime();
        //获取对账文件存取路径
        File file = verifyService.makeVerificationFile("111",day);
        //打印绝对路径
        System.out.println(file.getAbsolutePath());
    }

    //解析对方的文件并存入数据库
    @Test
    public void saveVerificationOrders(){
        Date day = new GregorianCalendar(2018,0,1).getTime();
        verifyService.saveChanOrders("111",day);
    }

    @Test
    public void verifyTest(){
        Date day = new GregorianCalendar(2018,0,1).getTime();
        System.out.println(String.join(";", verifyService.verifyOrder("111", day)));
    }

    @Test
    public void queryOrder(){
        System.out.println(orderRepository.findAll());
        System.out.println(backupOrderRepoistory.findAll());
    }

}

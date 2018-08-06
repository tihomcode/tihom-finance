package com.tihom.seller.task;

import com.tihom.seller.enums.ChanEnum;
import com.tihom.seller.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时对账任务
 * @author TiHom
 * create at 2018/8/5 0005.
 */

@Component
public class VerifyTask {

    @Autowired
    private VerifyService verifyService;

    @Scheduled(cron = "0 0 1,3,5 * * ? ") //每天凌晨的1、3、5点执行
    public void makeVerificationFile(){
        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        for (ChanEnum chanEnum : ChanEnum.values()) {
            verifyService.makeVerificationFile(chanEnum.getChanId(),yesterday);
        }
    }

    @Scheduled(cron = "0 0 2,4,6 * * ? ")  //每天的2、4、6点执行
    public void verify(){
        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        for (ChanEnum chanEnum : ChanEnum.values()) {
            verifyService.verifyOrder(chanEnum.getChanId(),yesterday);
        }
    }
}

package com.tihom.seller.service;

import com.tihom.entity.VerificationOrder;
import com.tihom.seller.enums.ChanEnum;
import com.tihom.seller.repositorybackup.VerifyRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对账服务
 * @author TiHom
 * create at 2018/8/5 0005.
 */

@Service
public class VerifyService {

    @Autowired
    private VerifyRepository verifyRepository;

    @Value("${verification.rootdir:/opt/verification}")
    private String rootDir;

    //声明换行符
    private static String END_LINE = System.getProperty("line.separator","\n");
    //将日期格式化为年月日格式
    private static DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //将日期格式化为年月日时分秒格式
    private static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成某个渠道某天的对账文件
     * @param chanId
     * @param day
     * @return
     */
    public File makeVerificationFile(String chanId, Date day){
        File path = getPath(rootDir,chanId,day);
        if(path.exists()){
            return path;
        }
        try {
            //新建文件
            path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //构造对账文件数据的起止时间（去掉时分秒）
        //获取到只有年月日的字符串时间
        Date start = getStartOfDay(day);
        //获取start的毫秒时间加上一天的毫秒数就是结束时间
        Date end = add24Hours(start);
        //获取到一天的所有订单数据了
        List<String> orders = verifyRepository.queryVerificationOrders(chanId,start,end);
        //使用换行符将订单数据串联起来
        String content = String.join(END_LINE,orders);
        //把对账数据保存到对应的目录下面去
        FileUtil.writeAsString(path,content);
        return path ;
    }

    /**
     * 添加24小时时间
     * @param start
     * @return
     */
    private Date add24Hours(Date start) {
        return new Date(start.getTime()+1000*60*60*24);
    }

    /**
     * 解析出起始日期
     * @param day
     * @return
     */
    private Date getStartOfDay(Date day) {
        //解析日期为只显示年月日的字符串
        String start_str = DAY_FORMAT.format(day);
        Date start = null;
        try {
            //反解析出一个日期
            start = DAY_FORMAT.parse(start_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return start;
    }

    /**
     * 获取对账文件路径
     * @param chanId
     * @param day
     * @return
     */
    public File getPath(String rootDir,String chanId,Date day){
        String name = DAY_FORMAT.format(day)+"-"+chanId+".txt";
        File path = Paths.get(rootDir,name).toFile();
        return path;
    }

    /**
     * 按照顺序解析字符串创建对账订单
     * order_id,outer_order_id,chan_id,chan_user_id,product_id,order_type,amount,create_at
     * @param line
     * @return
     */
    public static VerificationOrder parseLine(String line){  //使用不到内部的数据
        VerificationOrder order = new VerificationOrder();
        String[] props = line.split("\\|"); //转义
        order.setOrderId(props[0]);
        order.setOuterOrderId(props[1]);
        order.setChanId(props[2]);
        order.setChanUserId(props[3]);
        order.setProductId(props[4]);
        order.setOrderType(props[5]);
        order.setAmount(new BigDecimal(props[6]));
        try {
            order.setCreateAt(DATETIME_FORMAT.parse(props[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * 保存渠道订单数据
     * @param chanId
     * @param day
     */
    public void saveChanOrders(String chanId,Date day){
        //获取渠道的订单数据
        //根据渠道的配置信息，如渠道名称，ftp地址，用户名，密码
        //这里我们直接在这里面建个配置文件，真实开发中是需要建一张表利于维护
        ChanEnum conf = ChanEnum.getByChanId(chanId);
        //根据配置从ftp下载对账的对账数据，这一步省略
        File file = getPath(conf.getRootDir(),chanId,day);
        //表示对方已经生成对账文件，并且我们已经下载到本地了，存在就继续，不存在直接返回
        if(!file.exists()){
            return;
        }
        String content = null;
        try {
            //将文件中的内容解析为字符串
            content = FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据换行符分割每一行的信息
        String[] lines = content.split(END_LINE);
        List<VerificationOrder> orders = new ArrayList<>();
        for(String line : lines){
            //以“|”为分隔符分割出各个字段并且转换为VerificationOrder对象
            orders.add(parseLine(line));
        }
        verifyRepository.save(orders);
    }

    /**
     * 校验订单
     * @param chanId
     * @param day
     * @return
     */
    public List<String> verifyOrder(String chanId,Date day){
        //创建一个用于存储错误信息的列表
        List<String> errors = new ArrayList<>();
        Date start = getStartOfDay(day);
        //获取start的毫秒时间加上一天的毫秒数就是结束时间
        Date end = add24Hours(start);
        List<String> excessOrders = verifyRepository.queryExcessOrders(chanId,start,end);
        List<String> missOrders = verifyRepository.queryMissOrders(chanId,start,end);
        List<String> differentOrders = verifyRepository.queryDifferentOrders(chanId,start,end);

        //join方法是帮助列表拼接字符串
        errors.add("长款订单号："+String.join(",",excessOrders));
        errors.add("漏单订单号："+String.join(",",missOrders));
        errors.add("不一致订单号："+String.join(",",differentOrders));
        //应该把结果存入数据库，这里直接打印了
        return errors;
    }
}

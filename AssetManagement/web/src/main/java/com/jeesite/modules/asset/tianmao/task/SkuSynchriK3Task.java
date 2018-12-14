package com.jeesite.modules.asset.tianmao.task;

import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.tianmao.dao.SkuK3LogDAO;
import com.jeesite.modules.asset.tianmao.dao.TbSkuDao;
import com.jeesite.modules.asset.tianmao.dao.TbSkuK3NameDao;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbLogService;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Sku同步K3物料名称
 * 定时任务，每天早上6点执行
 * @author Jace Xiong
 */
//@Controller
//@RequestMapping("skuSynch")
//@Configuration
//@EnableScheduling
@Component
public class SkuSynchriK3Task {
    @Value("${POST_K3ClOUDRL}")
    private String postUrl;
    @Value("${dbId}")
    private String dbId ;
    @Value("${uid}")
    private String uid;
    @Value("${pwd}")
    private String pwd;
    @Value("${lang}")
    private int lang;

    public static ExecutorService executorService =  Executors.newFixedThreadPool(100);

    @Autowired
    private TbSkuDao tbSkuDao;
    @Autowired
    private SkuK3LogDAO skuK3LogDAO;
    @Autowired
    private TbSkuK3NameDao tbSkuK3NameDao;
    @Autowired
    private TbLogService tbLogService;
    @Autowired
    private TbSkuService tbSkuService;

    private static SkuSynchriK3Task synchriK3Task;
    @PostConstruct
    public void init (){
        synchriK3Task = this;
    }

//    @RequestMapping("k3")
//    @Scheduled(cron = "0 0 6 * * ?")
    public static void skuSych(){
        //1.从数据库tb_sku表获取outer_id,List类型
        List<TbSku> list = synchriK3Task.tbSkuDao.selectSkuList();
        System.out.println(list.size());
        //2.循环调用K3接口获取物料名称并更新到tb_sku中
        String sFormId = "BD_MATERIAL";
        try {
            //登录K3
            if(InvokeHelper.Login(synchriK3Task.dbId,synchriK3Task.uid,synchriK3Task.pwd,synchriK3Task.lang,synchriK3Task.postUrl)){
                System.out.println("登录成功");
                for (TbSku tbSku : list) {
                    K3Thread k3Thread = new K3Thread(synchriK3Task.tbLogService,synchriK3Task.skuK3LogDAO,synchriK3Task.tbSkuK3NameDao);
                    k3Thread.setId(sFormId);
                    k3Thread.setUrl(synchriK3Task.postUrl);
                    k3Thread.setTbSku(tbSku);
                    //启动线程池
                    executorService.execute(k3Thread);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品资料中卖家呢称=saladliang的商品真实价格取值K3该店铺的该SKU的单价；
     */
//    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟同步一次
//    @Scheduled(cron = "0/10 * * * * ?") // 每隔10秒同步一次
//    @Scheduled(cron = "0 0 5,18 * * ?") // 每天早上6点晚上18点更新
    public static void sychPrice() {
        List<TbSku> tbSkuList = synchriK3Task.tbSkuService.selectSku();
        try {
            if (tbSkuList != null && tbSkuList.size() >0) {
                if (InvokeHelper.Login(synchriK3Task.dbId, synchriK3Task.uid, synchriK3Task.pwd, synchriK3Task.lang, synchriK3Task.postUrl)) {
                    for (TbSku tbSku : tbSkuList) {
                        K3Price k3Price = new K3Price(synchriK3Task.tbLogService, synchriK3Task.tbSkuService);
                        k3Price.setTbSku(tbSku);
                        k3Price.setUrl(synchriK3Task.postUrl);
                        executorService.execute(k3Price);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.jeesite.modules.asset.ding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.SyncOrganize;
import com.jeesite.modules.asset.ding.entity.SyncPosition;
import com.jeesite.modules.asset.ding.entity.SyncUser;
import com.jeesite.modules.asset.ding.service.SyncDingService;
import com.jeesite.modules.asset.util.md5ForSyncELearning;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 给E-learning 平台同步数据，调用他们的接口并把数据带过去
 * @author thomas
 * @version 2018-11-20
 */

@Component
public class SyncELearningTask {

    private static SyncELearningTask syncELearningTask;

    @PostConstruct
    public void init() {
        syncELearningTask=this;
    }

    @Autowired
    private SyncDingService syncDingService;

    //
    /**
     * 抽取固定值
     */
    private static final String appKey_ = "76D975F68CF04053BE9177C902C35CE9";
    private static final String appSecret = "B9EDA5B492C849CD925B1E3A6F6BF94E";


    public static void syncElearning(){
        if(syncELearningTask.syncOrganize())if (syncELearningTask.syncPosition())if (syncELearningTask.syncUser())
            System.out.println("全部同步成功");
        else System.out.println("同步失败");
    }



    /**
     * 给E-learning 平台同步部门数据，调用下面方法判断是否数据大于400条，
     * 如果大于400条，则每发400条减掉400条数据再发
     * @author thomas
     * @version 2018-11-20
     */

    //每天凌晨7点执行一次：
    //@Scheduled(cron="0 0 7 * * ?")
    //测试用，每20秒发送一次
    //@Scheduled(cron="0/20 * * * * ?")
    public boolean syncOrganize(){
        List<SyncOrganize> organizes = syncDingService.getSyncOrganizes();
        alterOrganize(organizes);
        return true;
    }

    /**
     * 递归判断数据是否大于400条，如果是，拆分List数据，发送400条后删除400条数据，
     * 从list最后往前删除，避免索引混乱，剩余部分用递归方法发送；
     * @author thomas
     * @version 2018-11-20
     * @param list
     */
    private void alterOrganize(List<SyncOrganize> list){
        if(list.size()>400){
            int size = list.size()-1;
            List<SyncOrganize> organizes = new ArrayList<>();
            for (int i=size;i>size-400;i--){
                SyncOrganize o =list.get(i);
                organizes.add(o);
                list.remove(i);
            }
            syncDingService.saveResult(getTime(),"0",sendOrganizes(organizes));
            alterOrganize(list);
        }
        else{
            syncDingService.saveResult(getTime(),"0",sendOrganizes(list));
        }
    }

    /**
     * 给E-learning 平台同步部门数据，转json发送，返回结果
     * @author thomas
     * @version 2018-11-20
     * @param organizes
     * @return String
     */
    private String sendOrganizes(List<SyncOrganize> organizes){
        //封装数据
        Long timestamp_ = System.currentTimeMillis();
        String requestUrl ="/v1/uc/organize/syncOrganizesV2";
        String url ="http://v4.21tb.com/open/v1/uc/organize/syncOrganizesV2.html";
        String sign_ = md5ForSyncELearning.calculateSign(appSecret,requestUrl);

        //封装成map对象
        Map<String,String> jsonObject = new HashMap<>();
        jsonObject.put("organizes", JSON.toJSONString(organizes));
        jsonObject.put("appKey_",appKey_);
        jsonObject.put("sign_",sign_);
        jsonObject.put("timestamp_",timestamp_.toString());

        //System.out.println(jsonObject);

        //发送并得到返回数据
        return HttpClientUtils.post(url,jsonObject,"UTF-8");
    }


    /**
     * 隔开专用
     * 隔开专用
     * 隔开专用
     * 隔开专用
     * 隔开专用
     */

    /**
     * 给E-learning 平台同步岗位数据，调用下面方法判断是否数据大于400条，
     * 如果大于400条，则每发400条减掉400条数据再发
     * @author thomas
     * @version 2018-11-21
     */
    //每天早上7.15发送
    //@Scheduled(cron="0 15 7 * * ?")
    //测试用，每20秒发送一次
    //@Scheduled(cron="0/20 * * * * ?")
    public boolean syncPosition(){
        List<SyncPosition> positions = syncDingService.getSyncPosition();
        alterPosition(positions);
        return true;
    }

    /**
     * 递归判断数据是否大于400条，如果是，拆分List数据，发送400条后删除400条数据，
     * 从list最后往前删除，避免索引混乱，剩余部分用递归方法发送；
     * @author thomas
     * @version 2018-11-21
     * @param list
     */
    private void alterPosition(List<SyncPosition> list){
        if(list.size()>400){
            int size = list.size()-1;
            List<SyncPosition> positions = new ArrayList<>();
            for (int i=size;i>size-400;i--){
                SyncPosition p =list.get(i);
                positions.add(p);
                list.remove(i);
            }
            syncDingService.saveResult(getTime(),"1",sendPositions(positions));
            alterPosition(list);
        }
        else{
            syncDingService.saveResult(getTime(),"1",sendPositions(list));
        }
    }

    /**
     * 给E-learning 平台同步岗位数据，转json发送，保存结果
     * @author thomas
     * @version 2018-11-21
     * @param positions
     * @return String
     */
    private String sendPositions(List<SyncPosition> positions){
        //封装数据
        Long timestamp_ = System.currentTimeMillis();
        String requestUrl ="/v1/uc/position/syncPositions";
        String url ="http://v4.21tb.com/open/v1/uc/position/syncPositions.html";
        String sign_ = md5ForSyncELearning.calculateSign(appSecret,requestUrl);

        //封装成map对象
        Map<String,String> jsonObject = new HashMap<>();
        jsonObject.put("positions", JSON.toJSONString(positions));
        jsonObject.put("appKey_",appKey_);
        jsonObject.put("sign_",sign_);
        jsonObject.put("timestamp_",timestamp_.toString());

        //System.out.println(jsonObject);

        //发送并得到返回数据
        return HttpClientUtils.post(url,jsonObject,"UTF-8");
    }




    /**
     * 隔开专用
     * 隔开专用
     * 隔开专用
     * 隔开专用
     * 隔开专用
     */



    /**
     * 给E-learning 平台同步员工数据，调用下面方法判断是否数据大于250条，
     * 如果大于250条，则每发250条减掉250条数据再发
     * @author thomas
     * @version 2018-11-21
     */
    //每天早上7.30发送
//    @Scheduled(cron="0 30 7 * * ?")
    //测试用，每20秒发送一次
    //@Scheduled(cron="0/20 * * * * ?")
    public boolean syncUser(){
        List<SyncUser> users = syncDingService.getSyncUsers();
        alterUsers(users);
        return true;
    }

    /**
     * 递归判断数据是否大于250条，如果是，拆分List数据，发送250条后删除250条数据，
     * 从list最后往前删除，避免索引混乱，剩余部分用递归方法发送；
     * @author thomas
     * @version 2018-11-20
     * @param list
     */
    private void alterUsers(List<SyncUser> list){

        if(list.size()>250){
            List<SyncUser> SyncUsers = new ArrayList<>();
            int size=list.size()-1;
            for (int i=size;i>size-250;i--){
                SyncUser u = list.get(i);
                SyncUsers.add(u);
                list.remove(i);
            }
            syncDingService.saveResult(getTime(),"2",sendUsers(SyncUsers));
            alterUsers(list);
        }
        else{
            syncDingService.saveResult(getTime(),"2",sendUsers(list));
        }
    }

    /**
     * 给E-learning 平台同步部门数据，转json发送，保存结果
     * @author thomas
     * @version 2018-11-20
     * @param users
     * @return String
     */
    private String sendUsers(List<SyncUser> users){
        //封装数据
        Long timestamp_ = System.currentTimeMillis();
        String requestUrl ="/v1/uc/user/syncUsers";
        String url ="http://v4.21tb.com/open/v1/uc/user/syncUsers.html";
        String sign_ = md5ForSyncELearning.calculateSign(appSecret,requestUrl);

        //封装成map对象
        Map<String,String> jsonObject = new HashMap<>();
        jsonObject.put("users", JSON.toJSONString(users));
        jsonObject.put("appKey_",appKey_);
        jsonObject.put("sign_",sign_);
        jsonObject.put("timestamp_",timestamp_.toString());
        jsonObject.put("updatePswFirstLogin","false");

        //System.out.println(JSON.toJSONString(users));
        //System.out.println(jsonObject);

        //发送并得到返回数据
        //System.out.println(jsonResult);
        return HttpClientUtils.post(url,jsonObject,"UTF-8");
    }


    //获取格式好看的当前时间
    private String getTime(){
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
    return (format.format(new Date()));
}

}

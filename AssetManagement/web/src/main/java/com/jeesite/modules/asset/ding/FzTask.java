package com.jeesite.modules.asset.ding;

import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationRecordService;
import com.jeesite.modules.fz.appreciation.service.FzLeaderboardsService;
import com.jeesite.modules.fz.neigou.service.FzNeigouRefundService;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.util.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class FzTask {
    private static final String MSG_TYPE="action_card";

    private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";

    private static FzTask fzTask;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private AmqpTemplate template;
    @Resource
    private RedisUtil<String, List> redisList;
    @Autowired
    private DingDepartmentService dingDepartmentService;
    @Autowired
    private DingUserDepartmentService dingUserDepartmentService;
    @Autowired
    private FzLeaderboardsService fzLeaderboardsService;
    @Autowired
    private FzAppreciationRecordService fzAppreciationRecordService;
    @Autowired
    private FzNeigouRefundService fzNeigouRefundService;
    @Value("${sendMsg}")
    private String sendMsg;
    @Value("${loginQueueP}")
    private String loginQueueP;
    @Value("${loginQueueC}")
    private String loginQueueC;
    @Value("${fzMsgP}")
    private String fzMsgP;
    @Value("${fzMsgC}")
    private String fzMsgC;
    @Value("${recordLogP}")
    private String recordLogP;
    @Value("${recordLogC}")
    private String recordLogC;
    @Value("${recordLogP1}")
    private String recordLogP1;
    @Value("${recordLogC1}")
    private String recordLogC1;
    @Value("${fzMsgP1}")
    private String fzMsgP1;
    @Value("${fzMsgC1}")
    private String fzMsgC1;
    @Value("${fzAppreciationP}")
    private String fzAppreciationP;
    @Value("${fzAppreciationC}")
    private String fzAppreciationC;
    @Value("${fzNeigouLoginLogP}")
    private String fzNeigouLoginLogP;
    @Value("${fzNeigouOrderLogP}")
    private String fzNeigouOrderLogP;
    @Value("${fzExpendRecordP}")
    private String fzExpendRecordP;
    // 发送消息标识
    public static String sendMsgFlag;
    // 登录记录生产者
    public static String loginQueuesP;
    // 消息发送生产者
    public static String fzMsgQueueP;
    // 获得赞币消息生产者
    public static String recordLogQueueP;
    // 发出赞币生产者
    public static String recordLogQueueP1;
    //
    public static String fzMsgQueueP1;
    // 赞赏记录生产者
    public static String fzAppreciationQueueP;
    // P代表生产者 C代表消费者
    public static String loginQueuesC;
    public static String fzMsgQueueC;
    public static String recordLogQueueC;
    public static String recordLogQueueC1;
    public static String fzMsgQueueC1;
    public static String fzAppreciationQueueC;
    public static String fzNeigouLoginLogsP;
    public static String fzNeigouOrderLogsP;
    public static String fzExpendRecordsP;
    @PostConstruct
    public void init() {
        fzTask = this;
        // 查询当前链接数据库名
        Variable.dataBase = this.fzAppreciationRecordService.getDataBase();
        sendMsgFlag = this.sendMsg;
        loginQueuesP = this.loginQueueP;
        loginQueuesC = this.loginQueueC;
        fzMsgQueueP = this.fzMsgP;
        fzMsgQueueC = this.fzMsgC;
        recordLogQueueP = this.recordLogP;
        recordLogQueueC = this.recordLogC;
        recordLogQueueP1 = this.recordLogP1;
        recordLogQueueC1 = this.recordLogC1;
        fzMsgQueueP1 = this.fzMsgP1;
        fzMsgQueueC1 = this.fzMsgC1;
        fzAppreciationQueueP = this.fzAppreciationP;
        fzAppreciationQueueC = this.fzAppreciationC;
        fzNeigouLoginLogsP = this.fzNeigouLoginLogP;
        fzNeigouOrderLogsP = this.fzNeigouOrderLogP;
        fzExpendRecordsP = this.fzExpendRecordP;
    }

    /**
     * 每月第一天凌晨 定时清零部门内梵钻，跨部门梵钻
     * @Scheduled(cron = "0 0 0 1 * ?")
     */
    public static void updateGoldNumberEndOfMonth() {
        List<DingUser> dingUserList = fzTask.dingUserService.findList(new DingUser());
        fzTask.dingUserService.updateGoldNumber(dingUserList);
    }



    /**
     * 周排行月排行钉钉消息定时推送
     * @Scheduled(cron="0 0 16 ? * FRI")
     */
    public static void inoutGoldMessageEveryWeek(){
        String titile="梵赞排行榜出炉啦~";
        String singleTile="查看详情";
        String markdown="梵赞排行榜出炉啦，请查看英雄榜单";
        DingUser dingUser=new DingUser();
        dingUser.setleft("0");
//        dingUser.setUserid("18392547561934016313");
        List<DingUser> userList = fzTask.dingUserService.findList(dingUser);
        if(userList != null && userList.size()>0){
            String path = K3Config.BASEDIR +  "/" + "fz/routineMessage.txt";
            // 获取的标识
            String flag = ReadFile.ReadToString(path);
            if ("true".equals(flag)) {
                String FANZAN_RANK_URL = fzTask.amUtilService.getConfigValue("fanzan_rank_url");
                String AGENT_ID = fzTask.amUtilService.getConfigValue("fz_agentid");
                for(DingUser dingUser1:userList){
                    String touser=dingUser1.getUserid();
                    sendFixedMessage(touser,titile,singleTile,markdown, FANZAN_RANK_URL, AGENT_ID);
                }
            }
        }
    }

    public static void sendFixedMessage(String touser,String title,String singleTitle,String markdown, String FANZAN_RANK_URL, String AGENT_ID){

        JSONObject jsonObject=new JSONObject();
        RecordLog recordLog=new RecordLog();
        recordLog.setCreateTime(new Date());
        String accessToken= RedisHelp.redisHelp.getDingDingAcessToken();
        jsonObject.put("touser",touser);
        jsonObject.put("agentid",AGENT_ID);
        jsonObject.put("msgtype",MSG_TYPE);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("title",title);
        jsonObject1.put("markdown",markdown);
        jsonObject1.put("single_title",singleTitle);
        jsonObject1.put("single_url",FANZAN_RANK_URL);
        jsonObject.put("action_card",jsonObject1);
        String url = SEND_ADDRESS + accessToken;
        String responseinfo= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
        recordLog.setWriteTime(new Date());
        JSONObject jsonObject3=JSONObject.fromObject(responseinfo);
        if(0 == jsonObject3.getInt("errcode")){
            recordLog.setLevel("info");
        }else{
            recordLog.setLevel("warn");
        }

        recordLog.setTitle("梵赞消息推送");
        recordLog.setType("fanzan_rank_message");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("钉钉消息请求",jsonObject);
        jsonObject2.put("钉钉消息响应",responseinfo);
        recordLog.setContent(jsonObject2.toString());
//        fzTask.template.convertAndSend("recordLog1", recordLog);
        fzTask.template.convertAndSend(recordLogQueueP1, recordLog);
    }


    /**
     * 部门人员定时任务
     */
    public static void departmentUser() {
        List<DepartmentData> departmentList = fzTask.dingDepartmentService.getDepartment();
        fzTask.redisList.set("dingDepartment" + Variable.dataBase + Variable.RANDOMID, departmentList);
        List<DingUser> dingUserList = fzTask.dingUserService.findList(new DingUser());
        fzTask.redisList.set("dingUser" + Variable.dataBase + Variable.RANDOMID, dingUserList);
        List<DingUserDepartment> dingUserDepartmentList = fzTask.dingUserDepartmentService.selectByLeft();
        fzTask.redisList.set("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID, dingUserDepartmentList);
    }
//    public static void departmentUser() {
//        // 查出所有部门
//        List<DepartmentData> departmentList = fzTask.dingDepartmentService.getDepartment();
//        //保存部门层级关系
//        for(DepartmentData dept:departmentList){
//            String departmentId = dept.getDepartmentId();
//            String parentCodes = dept.getParentCodes();
//            String key = "dingdept";
//            int level = dept.getTreeLevel();
//            DingDepartment bean = new DingDepartment();
//            bean.setDepartmentId(departmentId);
//            List<DingDepartment> deptlist = fzTask.dingDepartmentService.findList(bean);
//            if(level==1){
//                //二级部门
//                key+="_"+departmentId;
//            }else if(level>1){
//                //三级以上部门
//                //0,65019260,1,
//                String deptTemp =parentCodes.substring(parentCodes.indexOf(",")+1,parentCodes.lastIndexOf(",")-2);
//                String[] deptTemps=deptTemp.split(",");
//                for(int i=deptTemps.length-1;i>=0;i--){
//                    key+="_"+deptTemps[i];
//                }
//                key+="_"+departmentId;
//            }
//            if(!"dingdept".equals(key))fzTask.redisList.set(key, deptlist);
//        }
//        fzTask.redisList.set("dingDepartment" + Variable.dataBase + Variable.RANDOMID, departmentList);
//        // 查出所员工(包括在职和离职的，用于后续查询自己的赞赏记录中)
//        List<DingUser> dingUserList = fzTask.dingUserService.findList(new DingUser());
//        //查询员工部门所有层级
//        for(DingUser user:dingUserList){
//            List<DingDepartment> deptlist = fzTask.dingDepartmentService.getDingDepartmentByUser(user.getUserid());
//            List<List<DepartmentData>> list = new ArrayList<List<DepartmentData>>();
//
//            for(DingDepartment dept:deptlist){
//                DepartmentData deptData = new DepartmentData();
//                BeanUtils.copyProperties(dept,deptData);
//                List<DepartmentData> parentDept = new ArrayList<DepartmentData>();
//                if(deptData.getTreeLevel()==1){
//                    //一级部门
//                    parentDept.add(deptData);
//                }else if(deptData.getTreeLevel()>1){
//                    String parentCodes = dept.getParentCodes();
//                    String deptTemp =parentCodes.substring(parentCodes.indexOf(",")+1,parentCodes.lastIndexOf(",")-2);
//                    String[] deptTemps=deptTemp.split(",");
//                    //for(int i=0;i>deptTemps.length;i++){
//                    for(int i=deptTemps.length-1;i>=0;i--){
//                        DingDepartment bean = new DingDepartment();
//                        bean.setDepartmentId(deptTemps[i]);
//                        List<DingDepartment> childlist = fzTask.dingDepartmentService.findList(bean);
//                        if(ParamentUntil.isBackList(childlist)){
//                            DepartmentData childDeptData = new DepartmentData();
//                            BeanUtils.copyProperties(childlist.get(0),childDeptData);
//                            parentDept.add(childDeptData);
//                        }
//                    }
//                    parentDept.add(deptData);
//                }
//                list.add(parentDept);
//            }
//            if(list.size()>0)user.setParentDeptlist(list);
//        }
//        fzTask.redisList.set("dingUser" + Variable.dataBase + Variable.RANDOMID, dingUserList);
//        // 查出所有部门id和员工id
//        List<DingUserDepartment> dingUserDepartmentList = fzTask.dingUserDepartmentService.selectByLeft();
////		List<DingUserDepartment> dingUserDepartmentList = dingUserDepartmentService.findList(new DingUserDepartment());
//        fzTask.redisList.set("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID, dingUserDepartmentList);
//        fzTask.redisUtil.set(1,"dingDepartment" + Variable.dataBase + Variable.RANDOMID,"test");
//    }

    /**
     * 梵赞排行榜定时任务
     */
    public static void leaderboard () throws ParseException {
        // 删除临时表数据，重新插入
        fzTask.fzLeaderboardsService.insertTemp();
        for (int i = 1; i < 5; i++) {
            List<LeaderboardData> leaderYearList = fzTask.fzLeaderboardsService.getLeaderboardList(i);
            fzTask.redisList.set("rankingList" + Variable.dataBase + i, leaderYearList);
        }
    }

    /**
     * 每天凌晨 更新梵赞内购订单数据
     */
    public static void updateOrderIno() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        long start_time = calendar.getTime().getTime();
        long end_time = new Date().getTime();
        //根据现在时间,更新内购订单的一天前的数据
        fzTask.fzNeigouRefundService.updateOrderIno(start_time,end_time);
    }
}

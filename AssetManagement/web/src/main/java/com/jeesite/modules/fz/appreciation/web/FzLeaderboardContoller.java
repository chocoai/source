package com.jeesite.modules.fz.appreciation.web;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.ReadFile;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationFollow;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import com.jeesite.modules.fz.appreciation.returnData.PageData;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationRecordService;
import com.jeesite.modules.fz.appreciation.service.FzLeaderboardsService;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "${adminPath}/fz/appreciation/FzLeaderboard")
public class FzLeaderboardContoller {

    @Autowired
    private FzAppreciationRecordService fzAppreciationRecordService;
    @Resource
    private RedisTemplate<String, List<LeaderboardData>> redisTemplate;
    @Value("${file.baseDir}")
    String baseDir;

    @Autowired
    private FzLeaderboardsService fzLeaderboardsService;
    /**
     * 获取赞赏排行榜
     */
//    @RequestMapping(value = "getZsLeaderboard")
////    @IsFileter(isFile="true")
//    @AccessLimit(limit = 1,sec = 2)
//    @ResponseBody
//    public ReturnInfo getZsLeaderboard(int flag, String type, int pageSize, int pageNo,String userId) throws ParseException {
//        //1表示周，2表示月，3表示季度,4表示年,
////        Integer flag = null;
////        if (ParamentUntil.isBackString(flag_str)) {
////            flag = Integer.parseInt(flag_str);
////        }
//        Date startTime = getStartTime(flag);
//        Date endTime = getEndTime(flag);
//        List<FzAppreciationRecord> fzAppreciationRecords = null;
//        //根据条件查找出时间段类的赞赏记录
//        if (!ParamentUntil.isBackString(type)) {
//            fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTime(startTime, endTime, pageSize, pageNo);
//        } else {
//            fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTimeAndType(startTime, endTime, type, pageSize, pageNo);
//        }
//        PageData<LeaderboardData> page = prosses1(fzAppreciationRecords,userId);
////        PageData<LeaderboardData> page=new PageData<>();
////        Page<LeaderboardData> page=new Page<>();
//        page.setPageSize(pageSize);
//        page.setPageNo(pageNo);
////        page.setTotal(leaderboardDataList.size());
////        page.setList(leaderboardDataList);
//        return ReturnDate.success(page);
//    }

    private Date getEndTime(Integer flag) throws ParseException {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endTime = null;
        if (flag == null) {
            endTime = TimeUtils.getEndDayOfMonth();
        } else if (flag == 1) {
            endTime = TimeUtils.getEndDayOfWeek();
        } else if (flag == 2) {
            endTime = TimeUtils.getEndDayOfMonth();
        } else if (flag == 3) {
            Integer i = Integer.parseInt(TimeUtils.getQuarter()); //得到当前第几季度，1.2.3.4.
            endTime = longSdf.parse(TimeUtils.getCurrQuarter(i)[1]);   //得到借宿时间
        } else if (flag == 4) {
            endTime = TimeUtils.getEndDayOfYear();
        }
        return endTime;
    }

    private Date getStartTime(Integer flag) throws ParseException {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        if (flag == null) {
            // 默认月
            startTime = TimeUtils.getBeginDayOfMonth();
        } else if (flag == 1) {
            // 1代表周
            startTime = TimeUtils.getBeginDayOfWeek();
        } else if (flag == 2) {
            // 2代表月
            startTime = TimeUtils.getBeginDayOfMonth();
        } else if (flag == 3) {
            // 3代表季度
            Integer i = Integer.parseInt(TimeUtils.getQuarter()); //得到当前第几季度，1.2.3.4.
            startTime = longSdf.parse(TimeUtils.getCurrQuarter(i)[0]);
        } else if (flag == 4) {
            // 4代表年
            startTime = TimeUtils.getBeginDayOfYear();
        }
        return startTime;
    }

    private List<LeaderboardData> prosses(List<FzAppreciationRecord> list) {
        if (!ParamentUntil.isBackList(list)) {
            return null;
        }
        List<LeaderboardData> leaderboardDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean ishave=false;
            //根据赞赏记录找到子记录
            List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(list.get(i).getAppreciationCode());
            //找到获赞者信息，头像，图片等
            String praiserId = list.get(i).getPraiserId();   //获赞者Id
            LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
            if (leaderboardData == null) {
                continue;
            }
            leaderboardData.setAppreciationCode(list.get(i).getAppreciationCode());
            long countNumber = sumCountNumber(fzAppreciationFollows, list.get(i));  //记录表的获赠币加赞赏表的获赠币
            if (ParamentUntil.isBackList(leaderboardDataList)){
                for (int j=0;j<leaderboardDataList.size();j++){
                    if (list.get(i).getPraiserId().equals(leaderboardDataList.get(j).getUserid())){
                        ishave=true;
                        leaderboardDataList.get(j).setPraiserNumber(leaderboardDataList.get(j).getPraiserNumber()+list.get(i).getCoinNumber());
                        break;
                    }
                }
            }
            leaderboardData.setPraiserNumber(countNumber);
            if (ishave){
                continue;
            }
            leaderboardDataList.add(leaderboardData);
        }
        leaderboardDataList = getSortList(leaderboardDataList);
        return leaderboardDataList;
    }

    private long sumCountNumber(List<FzAppreciationFollow> fzAppreciationFollows, FzAppreciationRecord appreciationRecord) {
        long rst = appreciationRecord.getCoinNumber();
        if (!ParamentUntil.isBackList(fzAppreciationFollows)) {
            return rst;
        }
        for (int i = 0; i < fzAppreciationFollows.size(); i++) {
            rst = rst + fzAppreciationFollows.get(i).getCoinNumber();
        }
        return rst;
    }

    public static List<LeaderboardData> getSortList(List<LeaderboardData> list) {
        Collections.sort(list, new Comparator<LeaderboardData>() {
            @Override
            public int compare(LeaderboardData o1, LeaderboardData o2) {
                if (o2.getPraiserNumber() > o1.getPraiserNumber()) {
                    return 1;
                }
                if (o2.getPraiserNumber() == o1.getPraiserNumber()) {
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    /**
     * 获取赞赏排行榜后台
     */
    @RequestMapping(value = "getList")
    @ResponseBody
    public Page<LeaderboardData> getList(HttpServletRequest request) throws ParseException {
        int pageSize=100;
		int pageNo=1;
		String pageSize_str=request.getParameter("pageSize");
        String pageSize_no=request.getParameter("pageNo");
        if (ParamentUntil.isBackString(pageSize_str)){
            pageSize=Integer.parseInt(pageSize_str);
        }
        if (ParamentUntil.isBackString(pageSize_no)){
            pageNo=Integer.parseInt(pageSize_no);
        }
        String flag_str= request.getParameter("flag");
        String type= request.getParameter("type");
        Integer flag=null;
       if (ParamentUntil.isBackString(flag_str)){
           flag=Integer.parseInt(flag_str);
       }
//		String type=null;
        //1表示周，2表示月，3表示年,4表示季度
//        Date startTime = getStartTime(flag);
//        Date endTime = getEndTime(flag);
//        List<FzAppreciationRecord> fzAppreciationRecords = null;
        //根据条件查找出时间段类的赞赏记录
//        if (!ParamentUntil.isBackString(type)) {
//            fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTime(startTime, endTime,pageSize,pageNo);
//        } else {
//            fzAppreciationRecords = fzAppreciationRecordService.getRecodeByTimeAndType(startTime, endTime, type,pageSize,pageNo);
//        }
//        List<LeaderboardData> leaderboardDataList = prosses(fzAppreciationRecords);
        if ("".equals(flag) || flag == null) {
            flag = 2;
        }
        List<LeaderboardData> leaderboardDataList = ListUtils.newArrayList();
        if ("".equals(type) || type == null) {
            leaderboardDataList = redisTemplate.opsForValue().get("rankingList" + Variable.dataBase + flag);
        } else {
            leaderboardDataList = redisTemplate.opsForValue().get("rankingList" + Variable.dataBase + "type" + flag);
        }


        List <LeaderboardData> leaderboardList = ListUtils.newArrayList();
//        PageData<LeaderboardData> page = new PageData<>();
        Page<LeaderboardData> page = new Page<>();
        if (leaderboardDataList == null || leaderboardDataList.size() <= 0) {
            page.setList(ListUtils.newArrayList());
            page.setCount(0);
        } else {
            leaderboardList = paer(leaderboardDataList, pageNo, pageSize);
            if (!"".equals(type) && type != null) {
                leaderboardList = leaderboardList.stream().filter(s ->s.getTag().equals(type)).collect(Collectors.toList());
            }
            page.setList(leaderboardList);
            page.setCount(leaderboardDataList.size());
        }
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
//        int total=fzAppreciationRecordService.getTotal(startTime,endTime,type);
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);

        return page;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(LeaderboardData leaderboardData, Model model) {
        model.addAttribute("leaderboardData", leaderboardData);
        return "fz/appreciation/FzLeaderboardList.html";
    }

    private PageData<LeaderboardData> prosses1(List<FzAppreciationRecord> list,String userId) {
        if (!ParamentUntil.isBackList(list)) {
            return new PageData();
        }
        PageData<LeaderboardData> page=new PageData<>();
        List<LeaderboardData> leaderboardDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean ishave=false;
            //根据赞赏记录找到子记录
            List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(list.get(i).getAppreciationCode());
            //找到获赞者信息，头像，图片等
            String praiserId = list.get(i).getPraiserId();   //获赞者Id
            LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
            if (leaderboardData == null) {
                continue;
            }
            leaderboardData.setAppreciationCode(list.get(i).getAppreciationCode());
            long countNumber = sumCountNumber(fzAppreciationFollows, list.get(i));  //记录表的获赠币加赞赏表的获赠币
            if (ParamentUntil.isBackList(leaderboardDataList)){
                for (int j=0;j<leaderboardDataList.size();j++){
                    if (list.get(i).getPraiserId().equals(leaderboardDataList.get(j).getUserid())){
                        ishave=true;
                        leaderboardDataList.get(j).setPraiserNumber(leaderboardDataList.get(j).getPraiserNumber()+list.get(i).getCoinNumber());
                        break;
                    }
                }
            }
            leaderboardData.setPraiserNumber(list.get(i).getCoinCounts());
            if (ishave){
                continue;
            }
            leaderboardDataList.add(leaderboardData);
        }
        leaderboardDataList = getSortList(leaderboardDataList);
        LeaderboardData leaderboardData=new LeaderboardData();
        if (ParamentUntil.isBackList(leaderboardDataList)){
                for (int i=0;i<leaderboardDataList.size();i++){
                    leaderboardDataList.get(i).setSotNo(i+1);
                    String userId1=leaderboardDataList.get(i).getUserid();
                    if (userId.equals(userId1)){
                        leaderboardData.setUserid(userId);
                        leaderboardData.setAvatar(leaderboardDataList.get(i).getAvatar());
                        leaderboardData.setName(leaderboardDataList.get(i).getName());
                        leaderboardData.setPraiserNumber(leaderboardDataList.get(i).getPraiserNumber());
                        leaderboardData.setSotNo(i+1);
                    }
                }
        }
        page.setTotal(leaderboardDataList.size());
        page.setList(leaderboardDataList);
        page.setEntity(leaderboardData);
        return page;
    }

//    /**
//     * 排行榜数据定时放入缓存
//     * @throws ParseException
//     */
////    @Scheduled(fixedRate = 5000 * 6)
//    public void toCache() throws ParseException {
//        // 文件
//        String path = baseDir + "/fz/ranking.txt";
//        // 获取的标识
//        String flag = ReadFile.ReadToString(path);
//        // false 代表不执行 true 代表执行
//        if (!"true".equals(flag)) {
//            return;
//        }
//        if (Variable.flag) {
//            Variable.dataBase = fzAppreciationRecordService.getDataBase();
//            Variable.flag = false;
//        }
//        for (int i = 1; i< 5; i++) {
//            Date startTime = getStartTime(i);
//            Date endTime = getEndTime(i);
//            List<FzAppreciationRecord> fzAppreciationRecords = null;
//            //根据条件查找出时间段类的赞赏记录
//            fzAppreciationRecords = fzAppreciationRecordService.getRecordByTime(startTime, endTime);
//            List<LeaderboardData> leaderboardDataList = getRecord(fzAppreciationRecords, null);
//
//            fzAppreciationRecords = fzAppreciationRecordService.getTypeRecordByTime(startTime, endTime);
//            List<LeaderboardData> leaderboardDataList1 = getRecord(fzAppreciationRecords, "1");
//            if (leaderboardDataList != null && leaderboardDataList.size() >0) {
//                //储存所有的梵赞排行榜数据
//                redisTemplate.opsForValue().set("rankingList" + Variable.dataBase + i, leaderboardDataList);
//                //根据类型例如月季度年这些分别存储
//                redisTemplate.opsForValue().set("rankingList" + Variable.dataBase + "type" + i, leaderboardDataList1);
//            } else {
//                redisTemplate.opsForValue().set("rankingList"+ Variable.dataBase + i, null);
//            }
//        }
//
//    }

    @RequestMapping(value = "getZsLeaderboard")
    @IsFileter(isFile="true")
//    @AccessLimit(limit = 1,sec = 2)
    @ResponseBody
    public ReturnInfo getZsLeaderboard(int flag, String type, int pageNo, int pageSize, String userId) throws ParseException{
        List<LeaderboardData> leaderboardDataList = ListUtils.newArrayList();
        List <LeaderboardData> leaderboardList = ListUtils.newArrayList();
        PageData<LeaderboardData> page = new PageData<>();
        LeaderboardData leaderboardData = new LeaderboardData();
        // 根据榜取到数据
        if ("".equals(type) || type == null) {
            // 标签类型为空时
            leaderboardDataList = redisTemplate.opsForValue().get("rankingList" + Variable.dataBase + flag);
        } else {
            // 标签类型不为空
            Date startTime = getStartTime(flag);
            Date endTime = getEndTime(flag);
            leaderboardDataList = fzLeaderboardsService.getLeaderboardByType(startTime, endTime, type);

        }
        if (leaderboardDataList == null || leaderboardDataList.size() <= 0) {
            page.setList(leaderboardDataList);
        } else {
            // 对获取到的信息分页
            leaderboardList = paer(leaderboardDataList, pageNo, pageSize);
        }
        if (type != null && !"".equals(type)) {
            // 标签类型不为空时添加排行顺序
            int sotNo = 1;
            for (LeaderboardData leaderboard : leaderboardList) {
                leaderboard.setSotNo(sotNo++);
            }
            page.setList(leaderboardList);
            page.setTotal(leaderboardList.size());
        }
        page.setList(leaderboardList);
        // 获取个人排行和赞币
        Optional<LeaderboardData> optionalLeaderboardData = leaderboardDataList.stream().filter(s -> s.getUserid().equals(userId)).findFirst();
        if (optionalLeaderboardData.isPresent()) {
            leaderboardData = optionalLeaderboardData.get();
        }
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
        page.setEntity(leaderboardData);
        return ReturnDate.success(page);
    }


    private List<LeaderboardData> getRecord(List<FzAppreciationRecord> list, String flag) {
        if (!ParamentUntil.isBackList(list)) {
            return ListUtils.newArrayList();
        }
        List<LeaderboardData> leaderboardDataList = ListUtils.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            FzAppreciationRecord fzAppreciationRecord = list.get(i);
            boolean ishave = false;
            //根据赞赏记录找到子记录
            List<FzAppreciationFollow> followList = fzAppreciationRecordService.getFollowsByCode(list.get(i).getAppreciationCode());
            //找到获赞者信息，头像，图片等
            String praiserId = fzAppreciationRecord.getPraiserId();   //获赞者Id
            LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
            if (leaderboardData == null) {
                continue;
            }
            // 类型
            leaderboardData.setTag(fzAppreciationRecord.getTag());
            leaderboardData.setAppreciationCode(fzAppreciationRecord.getAppreciationCode());
//            long countNumber = sumCountNumber(followList, fzAppreciationRecord);  //记录表的获赠币加赞赏表的获赠币
//            if (ParamentUntil.isBackList(leaderboardDataList)){
//                for (int j = 0;j< leaderboardDataList.size();j++){
//                    if (fzAppreciationRecord.getPraiserId().equals(leaderboardDataList.get(j).getUserid())){
//                        ishave = true;
//                        leaderboardDataList.get(j).setPraiserNumber(leaderboardDataList.get(j).getPraiserNumber() + fzAppreciationRecord.getCoinNumber());
//                        break;
//                    }
//                }
//            }
            leaderboardData.setPraiserNumber(fzAppreciationRecord.getCoinCounts());
            if (ishave){
                continue;
            }
            leaderboardDataList.add(leaderboardData);
        }
        if (leaderboardDataList != null && leaderboardDataList.size() >0) {
            leaderboardDataList = getSortList(leaderboardDataList);
        }

        return leaderboardDataList;
    }

    /**
     * 从缓存取出数据分页
     * @param leaderboardDataList
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List paer(List<LeaderboardData> leaderboardDataList, int pageNo, int pageSize) {
        List <LeaderboardData> personIdList=new ArrayList<>();
        int startIndex=(pageNo-1)*pageSize;
        if (leaderboardDataList != null) {
            for (int i = 0; i < pageSize; i++) {
                if (startIndex >= leaderboardDataList.size()) {
                    break;
                }
                LeaderboardData personId = leaderboardDataList.get(startIndex);
                startIndex = startIndex + 1;
                personIdList.add(personId);
                if (leaderboardDataList.size() == startIndex) {
                    break;
                }
            }
            return personIdList;
        }else {
            return null;
        }

    }

//    @Scheduled(fixedRate = 5000 * 6)
//    public void cache() throws ParseException{
//        String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/testTask.txt";
//        String flag = ReadFile.ReadToString(path);
//        if (!"true".equals(flag)) {
//            return;
//        }
//        // 删除临时表数据，重新插入
//        fzLeaderboardsService.insertTemp();
//        for (int i = 1; i < 5; i++) {
//            List<LeaderboardData> leaderYearList = fzLeaderboardsService.getLeaderboardList(i);
//            redisTemplate.opsForValue().set("rankingList" + Variable.dataBase + i, leaderYearList);
//        }
//    }
}

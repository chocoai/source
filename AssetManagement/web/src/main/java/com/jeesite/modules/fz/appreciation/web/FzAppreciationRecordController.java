/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.web;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.ding.entity.*;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.scheduledtask.K3Scheduled;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.appreciation.entity.*;
import com.jeesite.modules.fz.appreciation.returnData.LeaderboardData;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationRecordService;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationTypeService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import com.jeesite.modules.fz.expendrecord.service.FzExpenditureRecordService;
import com.jeesite.modules.fz.utils.common.Variable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import com.jeesite.modules.fz.appreciation.entity.FollowRecordData;

/**
 * 赞赏记录表Controller
 *
 * @author dwh
 * @version 2018-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/appreciation/fzAppreciationRecord")
public class FzAppreciationRecordController extends BaseController {
    private static final String ZSRECORD_PER_FIX = "ZSJL";
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private FzAppreciationRecordService fzAppreciationRecordService;
    @Autowired
    private AmFileUploadService amFileUploadService;
    @Resource
    private RedisUtil<String, List> redisList;
    @Autowired
    private FzAppreciationTypeService appreciationTypeService;
    @Autowired
    private DingDepartmentService dingDepartmentService;
    @Value("${fzSlideUrl}")
    private String fzSlideUrl;

    private final int appreiciationNum = 10; //一天之内赞赏数量的限额


    /**
     * 获取数据
     */
    @ModelAttribute
    public FzAppreciationRecord get(String appreciationCode, boolean isNewRecord) {
        return fzAppreciationRecordService.get(appreciationCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("appreciation:fzAppreciationRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(FzAppreciationRecord fzAppreciationRecord, Model model) {
        List<FzAppreciationType> fzAppreciationTypes=appreciationTypeService.findList(new FzAppreciationType());
        model.addAttribute("fzAppreciationTypes",fzAppreciationTypes);
        model.addAttribute("fzAppreciationRecord", fzAppreciationRecord);
        model.addAttribute("fzSlideUrl",fzSlideUrl);
        return "fz/appreciation/fzAppreciationRecordList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("appreciation:fzAppreciationRecord:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Page<FzAppreciationRecord> listData(FzAppreciationRecord fzAppreciationRecord, HttpServletRequest request, HttpServletResponse response) {
        Page<FzAppreciationRecord> page = fzAppreciationRecordService.findPage(new Page<FzAppreciationRecord>(request, response), fzAppreciationRecord);
        List<String> praiser = ListUtils.newArrayList();
        List<String> presenter = ListUtils.newArrayList();
        if (ParamentUntil.isBackList(page.getList())) {
            for (int i = 0, index=page.getList().size(); i < index; i++) {
                praiser.add(page.getList().get(i).getPraiserId());
                presenter.add(page.getList().get(i).getPresenterId());
            }
            // 赠送者部门获取
            List<DingDepartment> praiserList = dingUserService.getDepartName(praiser);
            Map<String, String> praiserMap = new HashMap<>();
            praiserMap = getMap(praiserList, praiserMap);
            // 获赠者部门
            List<DingDepartment> presenterList = dingUserService.getDepartName(presenter);
            Map<String, String> presenterMap = new HashMap<>();
            presenterMap = getMap(presenterList, presenterMap);
            for (int i = 0,index=page.getList().size(); i<index; i++){
                page.getList().get(i).setPraiserDepartment(praiserMap.get(page.getList().get(i).getPraiserId()));
                page.getList().get(i).setPresenterDepartment(presenterMap.get(page.getList().get(i).getPresenterId()));
            }
        }
        return page;
    }

    /**
     * 循环获取部门名称，如果有多个部门以空格分隔
     * @param list
     * @param map
     * @return
     */
    public Map getMap(List<DingDepartment> list, Map<String, String> map){
        for (int i = 0; i< list.size(); i++) {
            String userId = list.get(i).getUserId();
            if (map.containsKey(userId)) {
                String departmentName = list.get(i).getDepartmentName();
                departmentName = map.get(userId) + " " + departmentName;
                map.put(userId, departmentName);
            } else {
                map.put(userId, list.get(i).getDepartmentName());
            }
        }
        return map;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("appreciation:fzAppreciationRecord:view")
    @RequestMapping(value = "form")
    public String form(FzAppreciationRecord fzAppreciationRecord, Model model) {    //查询照片
        String recordCode =fzAppreciationRecord.getAppreciationCode();
        List<AmFileUpload> fileList = amFileUploadService.getImg(recordCode, "fzAppreciationRecord_image");
        List<String> imgs  = new ArrayList<>();
        if (ParamentUntil.isBackList(fileList)) {
            for (int j = 0; j < fileList.size(); j++) {
                imgs.add(fileList.get(j).getFileRealPath());
            }
        }
        fzAppreciationRecord.setImgList(imgs);
        model.addAttribute("fzAppreciationRecord", fzAppreciationRecord);
        return "fz/appreciation/fzAppreciationRecordForm";
    }

    /**
     * 保存赞赏记录表
     */
    @RequiresPermissions("appreciation:fzAppreciationRecord:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated FzAppreciationRecord fzAppreciationRecord) {
        fzAppreciationRecordService.save(fzAppreciationRecord);
        return renderResult(Global.TRUE, "保存赞赏记录表成功！");
    }

    /**
     * 删除赞赏记录表
     */
    @RequiresPermissions("appreciation:fzAppreciationRecord:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(FzAppreciationRecord fzAppreciationRecord) {
        fzAppreciationRecordService.delete(fzAppreciationRecord);
        return renderResult(Global.TRUE, "删除赞赏记录表成功！");
    }

    /**
     * 保存跟赏记录
     */
    @PostMapping(value = "saveFollow" )
    @IsFileter(isFile="true")
    @AccessLimit(limit = 1,sec = 2)
    @ResponseBody
    public ReturnInfo saveFollow(@RequestBody FzAppreciationFollow fzAppreciationFollow) {
        try {
            FzAppreciationRecord fzAppreciationRecord=fzAppreciationRecordService.get(fzAppreciationFollow.getRecordCode());
            long coinNumber=1;
            String outGoldType="";     //支出梵砖类型
            List<DingDepartment> dingDepartments1=dingUserService.getRootDepartmentByUserId(fzAppreciationRecord.getPraiserId()); //获赞者id
            List<DingDepartment> dingDepartments2=dingUserService.getRootDepartmentByUserId(fzAppreciationFollow.getPresenterId());  //跟赞者
            //查询当天跟赞者给了获赞者多少梵赞了
            int num = fzAppreciationRecordService.countThidDay(fzAppreciationFollow.getPresenterId(),fzAppreciationRecord.getPraiserId());
            if(num == appreiciationNum){
                return  ReturnDate.success(15008,"亲,一天对同一人不能赞赏超过10个哦",null);
            }
            DingUser praiserUser = dingUserService.get(fzAppreciationRecord.getPraiserId());   //获赞者
            DingUser presenterUser = dingUserService.get(fzAppreciationFollow.getPresenterId());   //跟赞者
            boolean isHave=prossesOne(dingDepartments1,dingDepartments2);     //判断是否是同一部门
            if (isHave) {                                 //如果部门一样
                if (presenterUser.getInDepartmentGold() < 1) {
                    return ReturnDate.error(900, "部门内梵钻不足 " + fzAppreciationFollow.getCoinNumber());
                }
                //跟赞者，减部门内钻,获赞者加可兑换币
                presenterUser.setInDepartmentGold(presenterUser.getInDepartmentGold() - coinNumber);
                praiserUser.setConvertibleGold(praiserUser.getConvertibleGold()+coinNumber);
                outGoldType="1";         //0表示可兑换的，1表示部门内的，2表示跨部门的
            } else {
                if (presenterUser.getOutDepartmentGold() < 1) {
                    return  ReturnDate.error(900, "跨部门梵钻不足 " + fzAppreciationFollow.getCoinNumber());
                }
                //赠赞者，减跨部门钻,获赞者加可兑换币
                presenterUser.setOutDepartmentGold(presenterUser.getOutDepartmentGold() - 1);
                praiserUser.setConvertibleGold(praiserUser.getConvertibleGold()+coinNumber);
                outGoldType="2";
            }

            fzAppreciationRecordService.saveFzAppreciationFollow(fzAppreciationFollow,praiserUser,presenterUser,outGoldType,fzAppreciationRecord);
            return ReturnDate.success("保存跟赞成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,e.getMessage());
        }
    }




    /**
     * 获取赞赏排行榜
     */
    @RequestMapping(value = "getZsLeaderboard")
    @IsFileter(isFile="true")
    @ResponseBody
    public List<LeaderboardData> getZsLeaderboard(HttpServletRequest request) {
        //1表示周，2表示月，3表示年
        String flag_str = request.getParameter("flag");
        Integer flag = null;
        if (ParamentUntil.isBackString(flag_str)) {
            flag = Integer.parseInt(flag_str);
        }
        Date startTime = getStartTime(flag);
        Date endTime = getEndTime(flag);
        List<FzAppreciationRecord> fzAppreciationRecords = fzAppreciationRecordService.getListByPraiserNumber(startTime, endTime);
        List<LeaderboardData> leaderboardDataList = prosses(fzAppreciationRecords);
        return leaderboardDataList;
    }

    private Date getEndTime(Integer flag) {
        Date endTime = null;
        if (flag == null) {
            endTime = TimeUtils.getEndDayOfMonth();
        } else if (flag == 1) {
            endTime = TimeUtils.getEndDayOfWeek();
        } else if (flag == 2) {
            endTime = TimeUtils.getEndDayOfMonth();
        } else if (flag == 3) {
            endTime = TimeUtils.getEndDayOfYear();
        }
        return endTime;
    }

    private Date getStartTime(Integer flag) {
        Date startTime = null;
        if (flag == null) {
            startTime = TimeUtils.getBeginDayOfMonth();
        } else if (flag == 1) {
            startTime = TimeUtils.getBeginDayOfWeek();
        } else if (flag == 2) {
            startTime = TimeUtils.getBeginDayOfMonth();
        } else if (flag == 3) {
            startTime = TimeUtils.getBeginDayOfYear();
        }
        return startTime;
    }

    private List<LeaderboardData> prosses(List<FzAppreciationRecord> list) {
        if (!ParamentUntil.isBackList(list)) {
            return null;
        }
        List<LeaderboardData> leaderboardDataList = new ArrayList<>();
        String praiserId="";
        for (int i = 0,index=list.size(); i < index; i++) {
            //按时间排序获赞最多的获赞人
            praiserId = list.get(i).getPraiserId();   //获赞者Id
            LeaderboardData leaderboardData = fzAppreciationRecordService.getUserInfo(praiserId);
            if (leaderboardData == null) {
                continue;
            }
            leaderboardData.setPraiserNumber(list.get(i).getPraiserNumber());
            leaderboardDataList.add(leaderboardData);
        }
        return leaderboardDataList;
    }

    /**
     * 获取id
     */
    @RequestMapping(value = "getId")
    @IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo getId() {
        String id = amSeqService.getCode(ZSRECORD_PER_FIX);
        return ReturnDate.success(id);
    }

//   /**
//     * 查询赞赏记录接口
//     */
//    @RequestMapping(value = "getList")
//    @AccessLimit(limit = 30,sec = 10)
//    @IsFileter(isFile="true")
//    @ResponseBody
//    public ReturnInfo getList(FzAppreciationRecord fzAppreciationRecord, HttpServletRequest request, HttpServletResponse response) {
//        Page<FzAppreciationRecord> page = fzAppreciationRecordService.findPage(new Page<FzAppreciationRecord>(request, response), fzAppreciationRecord);
//        List<String> praiser = ListUtils.newArrayList();
//        List<String> presenter = ListUtils.newArrayList();
//        if (ParamentUntil.isBackList(page.getList())) {
//            for (int i = 0; i < page.getList().size(); i++) {
//                praiser.add(page.getList().get(i).getPraiserId());
//                presenter.add(page.getList().get(i).getPresenterId());
//            }
//            // 赠送者部门获取
//            List<DingDepartment> praiserList = dingUserService.getDepartName(praiser);
//            Map<String, String> praiserMap = new HashMap<>();
//            praiserMap = getMap(praiserList, praiserMap);
//            // 获赠者部门
//            List<DingDepartment> presenterList = dingUserService.getDepartName(presenter);
//            Map<String, String> presenterMap = new HashMap<>();
//            presenterMap = getMap(presenterList, presenterMap);
//            for (int i = 0; i<page.getList().size(); i++){
//                page.getList().get(i).setPraiserDepartment(praiserMap.get(page.getList().get(i).getPraiserId()));
//                page.getList().get(i).setPresenterDepartment(presenterMap.get(page.getList().get(i).getPresenterId()));
//                //查询照片
//                String recordCode = page.getList().get(i).getAppreciationCode();
//                List<AmFileUpload> fileList = amFileUploadService.getImg(recordCode, "fzAppreciationRecord_image");
//                List<String> imgs  = new ArrayList<>();
//                if (ParamentUntil.isBackList(fileList)) {
//                    for (int j = 0; j < fileList.size(); j++) {
//                        imgs.add(fileList.get(j).getFileRealPath());
//                    }
//                }
//                page.getList().get(i).setImgList(imgs);
//                //查询跟赞记录
//                List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(page.getList().get(i).getAppreciationCode());
//                // 跟赞者部门获取
//                List<String> presenter1s=new ArrayList<>();
//                if (ParamentUntil.isBackList(fzAppreciationFollows)) {
//                    for (int j = 0; j < fzAppreciationFollows.size(); j++) {
//                        presenter1s.add(fzAppreciationFollows.get(j).getPresenterId());
//                    }}
//                if(ParamentUntil.isBackList(fzAppreciationFollows)) {
//                    List<DingDepartment> presenter1List = dingUserService.getDepartName(presenter1s);
//                    Map<String, String> presenter1Map = new HashMap<>();
//                    presenter1Map = getMap(presenter1List, presenter1Map);
//                    for (int j=0;j<fzAppreciationFollows.size();j++){
//                        fzAppreciationFollows.get(j).setDepName(presenter1Map.get(fzAppreciationFollows.get(j).getPresenterId()));
//                    }
//                }
//                page.getList().get(i).setCreateTime( page.getList().get(i).getCreateDate().getTime()/1000);
//                page.getList().get(i).setUpdateTime( page.getList().get(i).getUpdateDate().getTime()/1000);
//                page.getList().get(i).setFzAppreciationFollowList(fzAppreciationFollows);
//            }
//            page.setCount(page.getList().size());
//        }
//        return ReturnDate.success(page);
//    }

    @PostMapping(value = "saveRecord")
    @IsFileter(isFile="true")
    @AccessLimit(limit = 1,sec = 2)
    @ResponseBody
    public ReturnInfo saveRecord(FzAppreciationRecord fzAppreciationRecord) {
        try {
            //查询当天跟赞者给了获赞者多少梵赞了
            int num = fzAppreciationRecordService.countThidDay(fzAppreciationRecord.getPresenterId(),fzAppreciationRecord.getPraiserId());
            if(fzAppreciationRecord.getCoinNumber() + num > appreiciationNum){
                return ReturnDate.success(15008,"亲,一天对同一人不能赞赏超过10个哦",null);
            }
            //该接口只适用与新增
            fzAppreciationRecord.setIsNewRecord(true);
            long coin_count = fzAppreciationRecord.getCoinNumber();
            DingUser praiserUser = dingUserService.get(fzAppreciationRecord.getPraiserId());   //获赞者
            DingUser presenterUser = dingUserService.get(fzAppreciationRecord.getPresenterId());   //赠赞者
            if(fzAppreciationRecord.getPraiserId().equals(fzAppreciationRecord.getPresenterId())){
                return ReturnDate.error(900,"获赞者和赠赞者不能为同一用户");
            }
            String outGoldType="";     //支出梵砖类型
            List<DingDepartment> dingDepartments1=dingUserService.getRootDepartmentByUserId(praiserUser.getUserid());
            List<DingDepartment> dingDepartments2=dingUserService.getRootDepartmentByUserId(presenterUser.getUserid());

            boolean isHave=prossesOne(dingDepartments1,dingDepartments2);
            if (isHave) {                                 //如果部门一样
                if (presenterUser.getInDepartmentGold() < fzAppreciationRecord.getCoinNumber()) {
                    return ReturnDate.error(900, "部门内梵钻不足 " + fzAppreciationRecord.getCoinNumber());
                }
                //赠赞者，减部门内钻,获赞者加可兑换币
                presenterUser.setInDepartmentGold(presenterUser.getInDepartmentGold() - coin_count);
                praiserUser.setConvertibleGold(praiserUser.getConvertibleGold()+coin_count);
                outGoldType="1";         //0表示可兑换的，1表示部门内的，2表示跨部门的
            } else {
                if (presenterUser.getOutDepartmentGold() < fzAppreciationRecord.getCoinNumber()) {
                    return  ReturnDate.error(900, "跨部门梵钻不足 " + fzAppreciationRecord.getCoinNumber());
                }
                //赠赞者，减跨部门钻,获赞者加可兑换币
                presenterUser.setOutDepartmentGold(presenterUser.getOutDepartmentGold() - coin_count);
                praiserUser.setConvertibleGold(praiserUser.getConvertibleGold()+coin_count);
                outGoldType="2";
            }
            fzAppreciationRecordService.save(fzAppreciationRecord, praiserUser, presenterUser,outGoldType);
            return ReturnDate.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,"服务器忙",e.getMessage());
        }
    }

    private boolean prossesOne(List<DingDepartment> dingDepartments1, List<DingDepartment> dingDepartments2) {
        boolean isHave=false;
        if (ParamentUntil.isBackList(dingDepartments1)&&ParamentUntil.isBackList(dingDepartments2)){
            for (int i=0;i<dingDepartments1.size();i++){
                String departmentsId=dingDepartments1.get(i).getDepartmentId();
                for (int j=0;j<dingDepartments2.size();j++){
                    String departments2Id=dingDepartments2.get(j).getDepartmentId();
                    if (departmentsId.equals(departments2Id)){
                        isHave=true;
                        break;
                    }
                }
                if (isHave){
                    break;
                }
            }

        }
        return isHave;
    }

    /**
     * 查询跟赏记录接口
     */
    @RequestMapping(value = "getFollowList")
    @IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo getFollowList (String appreciationCode){
        List<FzAppreciationFollow> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByCode(appreciationCode);
        if (ParamentUntil.isBackList(fzAppreciationFollows)) {
            for (int i = 0; i < fzAppreciationFollows.size(); i++) {
                DingUser dingUser = dingUserService.get(fzAppreciationFollows.get(i).getPresenterId());    //获赞者Id
                if (dingUser != null) {
                    fzAppreciationFollows.get(i).setPresenterName(dingUser.getName());
                }
            }
        }
        return ReturnDate.success(fzAppreciationFollows);
    }

    /**
     * 更改赞赏记录接口
     */
    @RequestMapping(value = "updateRecord")
    @IsFileter(isFile="true")
    @AccessLimit(limit = 1,sec = 2)
    @ResponseBody
    public ReturnInfo updateRecord (FzAppreciationRecord appreciationRecord){
        if (!ParamentUntil.isBackString(appreciationRecord.getContent())){
            return ReturnDate.error(900,"理由为空");
        }
        appreciationRecord.setIsNewRecord(false);
        fzAppreciationRecordService.save(appreciationRecord);
        return ReturnDate.success();
    }

    /**
     * 根据用户查询跟赏记录接口
     */
    @RequestMapping(value = "getFollowListByUserId")
    @IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo getFollowListByUserId (String userId){
        List<String> fzAppreciationFollows = fzAppreciationRecordService.getFollowsByUserId(userId);
        return ReturnDate.success(fzAppreciationFollows);
    }

    @RequestMapping("test")
    public void test() throws Exception{
        K3Scheduled.getInfo();
    }
    @RequestMapping(value = "getList")
    @IsFileter(isFile="true")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public ReturnInfo getList(FzAppreciationRecord fzAppreciationRecord, HttpServletRequest request, HttpServletResponse response) {
        Long createTime=fzAppreciationRecord.getCreateTime();
        if (createTime!=null){
            String date=DateUtils.formatDate(createTime*1000,"yyyy-MM-dd HH:mm:ss");
            Date date1=DateUtils.parseDate(date);
            fzAppreciationRecord.setNewDate(date1);
        }

        Page<FzAppreciationRecord> page = fzAppreciationRecordService.findPage(new Page<FzAppreciationRecord>(request, response), fzAppreciationRecord);
        List<String> praiser = ListUtils.newArrayList();
        List<String> presenter = ListUtils.newArrayList();
        List<String> appreciation = ListUtils.newArrayList();
//        DingUser dingUser = new DingUser();

        if (ParamentUntil.isBackList(page.getList())) {
            for (int i = 0; i < page.getList().size(); i++) {
                praiser.add(page.getList().get(i).getPraiserId());
                presenter.add(page.getList().get(i).getPresenterId());
                appreciation.add(page.getList().get(i).getAppreciationCode());
            }

            // 跟赞者记录
            List<FzAppreciationFollow> fzAppreciationFollowList = fzAppreciationRecordService.getFollows(appreciation);

            // 获取图片
            List<AmFileUpload> fileList = amFileUploadService.getImage(appreciation);

            // 赠送者部门获取
            List<DingDepartment> praiserList = dingUserService.getDepartName(praiser);
            Map<String, String> praiserMap = new HashMap<>();
            praiserMap = getMap(praiserList, praiserMap);
            // 获赠者部门
            List<DingDepartment> presenterList = dingUserService.getDepartName(presenter);
            Map<String, String> presenterMap = new HashMap<>();
            presenterMap = getMap(presenterList, presenterMap);
            List<DingUser> dingUserList = redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
            for (int i = 0; i < page.getList().size(); i++) {
                FzAppreciationRecord  fzAppreciationRecord1 = page.getList().get(i);
                fzAppreciationRecord1.setPraiserDepartment(praiserMap.get(page.getList().get(i).getPraiserId()));
                fzAppreciationRecord1.setPresenterDepartment(presenterMap.get(page.getList().get(i).getPresenterId()));
                // 获赞者
                Optional<DingUser> optionalPraiserUser = dingUserList.stream().filter(s ->s.getUserid().equals(fzAppreciationRecord1.getPraiserId())).findFirst();
                if (optionalPraiserUser.isPresent()) {
                    DingUser praiserUser = optionalPraiserUser.get();
                    // 获赞者照片
                    fzAppreciationRecord1.setUser_img(praiserUser.getAvatar());
                    // 获赞者名
                    fzAppreciationRecord1.setPraiserName(praiserUser.getName());
                }
                Optional<DingUser> optionalPresenterUser =  dingUserList.stream().filter(s ->s.getUserid().equals(fzAppreciationRecord1.getPresenterId())).findFirst();
                if (optionalPresenterUser.isPresent()) {
                    // 赠送者
                    DingUser presenterUser = optionalPresenterUser.get();
                    // 赠送者名
                    fzAppreciationRecord1.setPresenterName(presenterUser.getName());
                }

                List<AmFileUpload> fileList1 = ListUtils.newArrayList();
                try {
                    fileList1 = fileList.stream().filter(s ->s.getBizKey().equals(fzAppreciationRecord1.getAppreciationCode())).collect(Collectors.toList());
                }catch (Exception e) {

                }
                if (fileList1 != null && fileList1.size() > 0) {
                    List<String> imgs = ListUtils.newArrayList();
                    for (AmFileUpload amFileUpload : fileList1) {
                        imgs.add(amFileUpload.getFileRealPath());
                        fzAppreciationRecord1.setImgList(imgs);
                    }
                } else {
                    fzAppreciationRecord1.setImgList(new ArrayList<>());
                }
                List<FzAppreciationFollow> appreciationFollowList = ListUtils.newArrayList();
                try{
                    appreciationFollowList = fzAppreciationFollowList.stream().filter(s ->s.getFollowCode().equals(fzAppreciationRecord1.getAppreciationCode())).collect(Collectors.toList());
                }catch (Exception e) {

                }
                if (appreciationFollowList != null && appreciationFollowList.size() > 0) {
                    for (FzAppreciationFollow fzAppreciationFollow : appreciationFollowList) {
                        DingUser dingUser1 = dingUserList.stream().filter(s ->s.getUserid().equals(fzAppreciationFollow.getPresenterId())).findFirst().get();
                        fzAppreciationFollow.setPresenterName(dingUser1.getName());
                    }
                }
                fzAppreciationRecord1.setFzAppreciationFollowList(appreciationFollowList);

                page.getList().get(i).setCreateTime(page.getList().get(i).getCreateDate().getTime() / 1000);

                page.getList().get(i).setUpdateTime(page.getList().get(i).getUpdateDate().getTime() / 1000);
            }
        }
        return ReturnDate.success(page);
    }
    //导出数据
    @RequiresPermissions("appreciation:fzAppreciationRecord:edit")
    @RequestMapping(value = "exportData")
    @ResponseBody
    public void exportData(HttpServletResponse response,String type) {
        List<DingUser> userList = new ArrayList<>();
        List<ExportAppreciationData> appreciationDataList=new ArrayList<>();
        String fileName="";
        if ("1".equals(type)) {
            appreciationDataList = fzAppreciationRecordService.getExportData();
            fileName="获赞数量信息";
        }
        if("2".equals(type)){
            appreciationDataList = fzAppreciationRecordService.getExportData1();
            fileName="赞赏数量和跟赞数量信息";
        }
        List<DingUserDepartment> dingUserDepartmentList = redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
        // 获取缓存中所有部门
        List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
        for (ExportAppreciationData exportAppreciationData : appreciationDataList) {

            DingUser dingUser=new DingUser();
            dingUser.setUserid(exportAppreciationData.getUserid());
            dingUser=dingUserService.get(dingUser);
            String departmentNames = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
           exportAppreciationData.setDepartmentNames(departmentNames);
        }
       fileName =fileName+DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
        try(ExcelExport ee = new ExcelExport("赞赏数据", ExportAppreciationData.class)){
            ee.setDataList(appreciationDataList).write(response, fileName);
        }
    }

    /**
     * 获取根部门数据
     * @return
     */
    @ResponseBody
    @RequestMapping("getDeptDate")
    public ReturnInfo getDeptDate(){
        try {
            List<DingDepartment> list = dingDepartmentService.getChiddeptsByParentCode("1");
            return ReturnDate.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,e.getMessage());
        }
    }


    /**
     *  ranking 梵赞记录滑动效果前几名
     * departmentId 部门id 如果是全公司不分部门的话传0,其余传部门id
     * type 月季度年,自己选 1:星期 2:月 3:季度 4:年
     * monthStartTime 前端传个月份第一天的时间 "2018-11-01 00:00:00"
     * 获取动态效果的赞赏记录
     * @return
     */
    //@RequiresPermissions("appreciation:fzAppreciationRecord:view")
    @RequestMapping(value = "getSlideAppreciation")
    @ResponseBody
    public ReturnInfo getSlideAppreciation (Integer ranking,Integer type,String departmentId,String monthStartTime){
        try {
            if(type == null){
                return ReturnDate.success(15002,"没有时间类型",null);
            }
            if(monthStartTime == null || "".equals(monthStartTime)){
                return ReturnDate.success(15004,"没有开始时间",null);
            }
            if(departmentId == null || "".equals(departmentId)){
                return ReturnDate.success(15005,"没有部门id",null);
            }
            if(ranking == null){
                return ReturnDate.success(15006,"没有排名数据",null);
            }
            if(type > 4 || type < 1){
                return ReturnDate.success(15007,"时间类型不对",null);
            }
            return fzAppreciationRecordService.getSlideAppreciation(type,ranking,departmentId,monthStartTime);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,e.getMessage());
        }
    }

    /**
     * 根据钉钉id获取赞与被赞数据
     * @param type 月季度年,自己选 1:星期 2:月 3:季度 4:年
     * monthStartTime 前端传个月份第一天的时间 "2018-11-01 00:00:00"
     * @param userId 钉钉id
     * @return
     */
    //@RequiresPermissions("appreciation:fzAppreciationRecord:view")
    @RequestMapping(value = "getSlideDate")
    @ResponseBody
    public ReturnInfo getSlideDate(Integer type,String userId,String monthStartTime){
        try {
            if(type == null){
                return ReturnDate.success(15002,"没有时间类型",null);
            }
            if(userId == null || "".equals(userId)){
                return ReturnDate.success(15003,"没有userid",null);
            }
            if(monthStartTime == null || "".equals(monthStartTime)){
                return ReturnDate.success(15004,"没有开始时间",null);
            }
            if(type > 4 || type < 1){
                return ReturnDate.success(15007,"时间类型不对",null);
            }
            return fzAppreciationRecordService.getSlideDate(type,userId,monthStartTime);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,e.getMessage());
        }
    }

    @Autowired
    private FzExpenditureRecordService fzExpenditureRecordService;

    /**
     * 梵赞我的支出记录
     * @param fzExpenditureRecord
     * @param request
     * @param response
     * @return
     */
    @IsFileter(isFile="true")
    @RequestMapping(value = "expenditureRecord")
    @ResponseBody
    public Page<FzExpenditureRecord> expenditureRecord(FzExpenditureRecord fzExpenditureRecord, HttpServletRequest request, HttpServletResponse response) {
        Page<FzExpenditureRecord> page = fzExpenditureRecordService.findPage(new Page<FzExpenditureRecord>(request, response), fzExpenditureRecord);
        return page;
    }


    @IsFileter(isFile="true")
    @RequestMapping(value = "accountRecord")
    @ResponseBody
    public ReturnInfo accountRecord(FzAccountRecord fzAccountRecord, HttpServletRequest request, HttpServletResponse response) {
        FzGoldRecord fzGoldRecord = fzAppreciationRecordService.getAccountRecord(new Page<FzAccountRecord>(request, response), fzAccountRecord);
        return ReturnDate.success(fzGoldRecord);
    }
}

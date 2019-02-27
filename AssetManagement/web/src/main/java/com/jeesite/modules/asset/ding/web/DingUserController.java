/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.web;


import com.google.common.base.Strings;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.*;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingRoleService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.config.Uvantoken;
import com.jeesite.modules.fz.neigou.service.FzNeigouRefundService;
import com.jeesite.modules.fz.order.entity.FzNeigouFzgoldLog;
import com.jeesite.modules.fz.utils.common.Variable;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;


/**
 * 人员管理Controller
 *
 * @author scarlett
 * @version 2018-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/ding/dingUser")
public class DingUserController extends BaseController {

    @Autowired
    private DingUserService dingUserService;

    @Autowired
    private DingDepartmentService departmentService;
    @Autowired
    private DingRoleService dingRoleService;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private FzNeigouRefundService fzNeigouRefundService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public DingUser get(String userid, boolean isNewRecord) {
        return dingUserService.get(userid, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = {"list", ""})
    public String list(DingUser dingUser, Model model) {
        model.addAttribute("dingUser", dingUser);
        return "asset/ding/dingUserList";
    }

    @RequestMapping(value = {"userSelect", ""})
    public String listSelect(DingUser dingUser, Model model, String selectData, String checkbox) {
        dingUser.setleft("0");
        model.addAttribute("selectData", selectData);
        model.addAttribute("dingUser", dingUser);
        model.addAttribute("checkbox", checkbox);
        return "asset/ding/userSelect";
    }

    @RequestMapping(value = {"specialSelect", ""})
    public String specialSelect(DingUser dingUser, Model model, String selectData, String checkbox) {
        dingUser.setSpecialUser("1");
        dingUser.setleft("0");
        model.addAttribute("selectData", selectData);
        model.addAttribute("dingUser", dingUser);
        model.addAttribute("checkbox", checkbox);
        return "asset/ding/specialUserSelect";
    }

    /**
     * 根据条件查询用户列表
     *
     * @param dingUser
     * @param request
     * @param response
     * @return
     */
    public Page<DingUser> getlistData(DingUser dingUser, HttpServletRequest request, HttpServletResponse response) {
        Page<DingUser> page = dingUserService.findPage(new Page<DingUser>(request, response), dingUser);
        List<DingUser> dingUsers = page.getList();
        if (dingUsers != null && dingUsers.size() > 0) {
            for (DingUser user : dingUsers) {
                String id = user.getUserid();
                List<String> list = departmentService.getDepartmentNameByUser(id);
                user.setDepartmentNames(StringUtils.join(list, "  "));
                List<String> roleNames = dingRoleService.getRoleNamesByUser(id);
                user.setRoleNames(StringUtils.join(roleNames, "  "));
                String extraInfo = user.getExtattr();
                JSONObject jsonObject = JSONObject.fromObject(extraInfo);
                if (jsonObject.containsKey("中文名")) {
                    user.setChineseName(jsonObject.getString("中文名"));
                }
                if (jsonObject.containsKey("性别")) {
                    user.setSex(jsonObject.getString("性别"));
                }
            }
        }
        return page;
    }

    /**
     * 查询所有用户列表数据
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<DingUser> listData(DingUser dingUser, HttpServletRequest request, HttpServletResponse response) {
        Page<DingUser> page = getlistData(dingUser, request, response);
        return page;

    }

    /**
     * 获取在职用户列表
     *
     * @param dingUser
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "listData1")
    @ResponseBody
    public Page<DingUser> listData1(DingUser dingUser, HttpServletRequest request, HttpServletResponse response) {
        dingUser.setleft("0");
        Page<DingUser> page = getlistData(dingUser, request, response);
        return page;
    }

    /**
     * 获取特权用户列表
     *
     * @param dingUser
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "listDataS")
    @ResponseBody
    public Page<DingUser> listDataS(DingUser dingUser, HttpServletRequest request, HttpServletResponse response) {
        dingUser.setUserIsVip("1");
        dingUser.setleft("0");
        Page<DingUser> page = getlistData(dingUser, request, response);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "form")
    public String form(DingUser dingUser, Model model) {
        model.addAttribute("dingUser", dingUser);
        return "asset/ding/dingUserForm";
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "formData")
    public String formData(DingUser dingUser, Model model) {
        DingUser dingUser1 = dingUserService.get(dingUser.getDirectSuperior());
        DingUser dingUser2 = dingUserService.get(dingUser.getDepartmentHeader());
        dingUser.setDingUser1(dingUser1);
        dingUser.setDingUser2(dingUser2);
        model.addAttribute("dingUser", dingUser);
        return "asset/ding/dingUserFormData";
    }

    /**
     * 保存人员管理
     */
    @RequiresPermissions("ding:dingUser:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated DingUser dingUser) {
        dingUserService.save(dingUser);
        dingUserService.updateCustomized(dingUser.getUserid());
        return renderResult(Global.TRUE, text("保存人员管理成功！"));
    }

    /**
     * 删除人员管理
     */
    @RequiresPermissions("ding:dingUser:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(DingUser dingUser) {
        dingUserService.delete(dingUser);
        return renderResult(Global.TRUE, text("删除人员管理成功！"));
    }

    /**
     * 新增或更新user（更新钉钉用户）
     */
    @PostMapping(value = "user")
    @ResponseBody
    public String user(@RequestBody String user) {
        String result = dingUserService.sysUser(user);
        if ("".equals(result)) {
            return renderResult(Global.TRUE, "更新用户表成功");
        }
        return renderResult(Global.FALSE, result);
    }

    /**
     * 新增或更新部门表
     */
    @PostMapping(value = "department")
    @ResponseBody
    public String department(@RequestBody String department) {
        dingUserService.sysDepartment(department);
        return renderResult(Global.TRUE, "更新部门表成功");
    }

    /**
     * 新增或更新角色表（钉钉同步）
     */
    @PostMapping(value = "role")
    @ResponseBody
    public String role(@RequestBody String role) {
        dingUserService.sysRole(role);
        return renderResult(Global.TRUE, "更新角色表成功");
    }

    /**
     * 用户列表接口1
     */
    @PostMapping(value = "getUserList")
    @IsFileter(isFile = "true")
    //@AccessLimit(limit = 1,sec = 2)
    @ResponseBody
    public ReturnInfo getUserList(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userid");
        Map<String, Object> map = new HashMap<>();
        String name = request.getParameter("name");
        String pageNo = request.getParameter("pageNo");
        DingUser dingUser = new DingUser();
        dingUser.setleft("0");
        dingUser.setRemoveId(userId);
        if (name != null) {
            dingUser.setName(name);
        }
        if (pageNo == null) {
            List<DingUser> dingUserList = dingUserService.findList(dingUser);
//			if(dingUserList!=null && dingUserList.size()>0) {
            map.put("userList", dingUserList);
            map.put("count", dingUserList.size());
//			}
        } else {
            Page<DingUser> page = dingUserService.findPage(new Page<DingUser>(request, response), dingUser);
            map.put("userList", page.getList());
            map.put("count", page.getCount());
        }
        return ReturnDate.success(0, "请求成功", map);
    }

    /**
     * 根据用户id获取梵钻数量
     */
    @PostMapping(value = "getRecordByUserId")
    @ResponseBody
    //@AccessLimit(limit = 1,sec = 2)
    @IsFileter(isFile = "true")
    public ReturnInfo getAppreciationRecordByUserId(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (userid != null) {
            Integer count1 = dingUserService.getPraiserCount(userid);
            Integer count2 = dingUserService.getPresenterCount(userid);
            Integer count3 = dingUserService.getPraiserFollowCount(userid);
            Integer count4 = dingUserService.getPresenterFollowCount(userid);
            if (null == count1) {
                count1 = 0;
            }
            if (null == count2) {
                count2 = 0;
            }
            if (null == count3) {
                count3 = 0;
            }
            if (null == count4) {
                count4 = 0;
            }
            map.put("praiserCount", count1 + count3);
            map.put("presenterCount", count2 + count4);
        }
        return ReturnDate.success(0, "请求成功", map);
    }

    /**
     * 根据部门id查询部门用户
     */
    @PostMapping(value = "getUserListByDepartmentId")
    //@AccessLimit(limit = 1,sec = 2)
    @IsFileter(isFile = "true")
    @ResponseBody
    public ReturnInfo getUserListByDepartmentId(HttpServletRequest request) {
        List<DingUser> dingUsers = dingUserService.getUserListByDepartmentId(request.getParameter("id"));
        return ReturnDate.success(0, "请求成功", dingUsers);

    }

    /**
     * 匹配员工离职状态（钉钉信息）
     */
    @PostMapping(value = "macthUser")
    @ResponseBody
    public ReturnInfo macthUser(@RequestBody List<String> userIds) {
        if (userIds != null && userIds.size() > 0) {
            dingUserService.updateLeftStatus(userIds);
            return ReturnDate.success(0, "请求成功", "成功匹配员工离职状态");
        } else {
            return ReturnDate.success(0, "请求成功", "用户Id列表为空");

        }
    }

    /**
     * 每月第一天凌晨 定时清零部门内梵钻，跨部门梵钻
     */
//    @Scheduled(cron = "0 0 0 1 * ?")
    //@Scheduled(cron = "0 0/5 15 * * ?")
    public void updateGoldNumberEndOfMonth() {
        List<DingUser> dingUserList = dingUserService.findList(new DingUser());
//        dingUserBackupsService.insert();
        dingUserService.updateGoldNumber(dingUserList);
    }


    /**
     * 测试
     */
    @PostMapping(value = "test")
    //@AccessLimit(limit = 1,sec = 2)
    //@IsFileter(isFile="true")
    @ResponseBody
    public ReturnInfo test() {
        String id = "1559511342-607104135";
        DingUser dingUser = dingUserService.getUserById(id);

        return ReturnDate.success(dingUser);
    }

    public List<String> getParentNodes(List<DingDepartment> departmentList, DingDepartment dingDepartment, List<String> list) {
        //List<String> list=new ArrayList<String>();
        if (null == dingDepartment.getParentid() || "".equals(dingDepartment.getParentid())) {
            return list;
        }
        for (DingDepartment dingDepartment1 : departmentList) {
            String departmentId = dingDepartment1.getDepartmentId();
            if (departmentId.equals(dingDepartment.getParentid())) {
                list.add(departmentId);
                getParentNodes(departmentList, dingDepartment1, list);

            }
        }
        return list;
    }


    @RequestMapping(value = "getUserByUserId")
    @IsFileter(isFile = "true")
    //@AccessLimit(limit = 30,sec = 10)
    @ResponseBody
    public ReturnInfo getUserByUserId(String userId) {
        DingUser dingUser = dingUserService.get(userId);
        if (dingUser.getUsedPoint() == null) {
            dingUser.setUsedPoint(0D);
        }
        List<String> list = departmentService.getDepartmentNameByUser(dingUser.getUserid());
        dingUser.setDepartmentNames(StringUtils.join(list, " "));
        return ReturnDate.success(dingUser);

    }

    @RequiresPermissions("ding:dingUser:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(DingUser dingUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        dingUser.setUserStatus(DingUser.STATUS_DISABLE);
        dingUserService.save(dingUser);
        return renderResult(Global.TRUE, text("停用用户" + dingUser.getName() + "成功"));
    }

    @RequiresPermissions("ding:dingUser:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(DingUser dingUser, HttpServletRequest request, HttpServletResponse response, Model model) {

        dingUser.setUserStatus(DingUser.STATUS_NORMAL);
        dingUserService.save(dingUser);
        return renderResult(Global.TRUE, text("启用用户" + dingUser.getName() + "成功"));
    }

    @Resource
    private RedisUtil<String, List> redisList;

    @RequiresPermissions("ding:dingUser:view")
    @RequestMapping(value = "exportData")
    @SuppressWarnings("unchecked")
    public void exportData(DingUser dingUser, Boolean isAll, HttpServletResponse response) {
        List<DingUser> list = dingUserService.findList(dingUser);
        List<ExportUserData> exportList = ListUtils.newArrayList();

        List<DingUserDepartment> dingUserDepartmentList = redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
        // 获取缓存中所有部门
        List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
        for (DingUser dingUser1 : list) {
            ExportUserData exportUserData = new ExportUserData();
            exportUserData.setName(dingUser1.getName());
            exportUserData.setJobnumber(dingUser1.getJobnumber());
            // 部门获得
            String departmentNames = DepartmentUtil.getDepartment(dingUser1, dingUserDepartmentList, departmentList);
            exportUserData.setDepartmentNames(departmentNames);
            String extattr = dingUser1.getExtattr();
            if (extattr.contains("中文名")) {
                String chineseName = com.alibaba.fastjson.JSONObject.parseObject(extattr).get("中文名").toString();
                // 中文名
                exportUserData.setChineseName(chineseName);
            }
            exportUserData.setAchievement(dingUser1.getAchievement());
            exportUserData.setPrizeType(dingUser1.getPrizeType());
            String winningPrize = null;
            if ("0".equals(dingUser1.getWinningPrize())) {
                winningPrize = "否";
            } else {
                winningPrize = "是";
            }
            exportUserData.setWinningPrize(winningPrize);
            exportList.add(exportUserData);
        }
        String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
        try (ExcelExport ee = new ExcelExport("用户数据", DingUser.class)) {
            ee.setDataList(exportList).write(response, fileName);
        }
    }

    /**
     * 更新用户的特权用户值
     *
     * @param userid
     * @param status
     * @return
     */
    @RequiresPermissions("ding:dingUser:edit")
    @RequestMapping(value = "updateUserIsVip")
    @ResponseBody
    public String updateUserIsVip(String userid, String status) {
        if (Strings.isNullOrEmpty(status)) {
            return renderResult(Global.TRUE, "状态值为空");
        }
        if (Strings.isNullOrEmpty(userid)) {
            return renderResult(Global.TRUE, "状态值为空");
        }
        dingUserService.updateUserIsVip(userid, status);
        return renderResult(Global.TRUE, "修改成功！");
    }

    /**
     * 查询用户梵砖积分
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "queryUserIntegral")
    @ResponseBody
    @Uvantoken(getUvanToken = "true")
    public ReturnInfo queryUserIntegral(String userid) {
        FzNeigouFzgoldLog log = new FzNeigouFzgoldLog();
        log.setAction("1");
        log.setCreateTime(new Date());
        log.setUserId(userid);
        try {
            if (userid == null || "".equals(userid)) {
                return ReturnDate.success(15003, "没有userid", null);
            }
            DingUser user = dingUserService.getUserWithId(userid);
            if (user == null) {
                log.setActionResult("userid错误");
                rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP,log);
                return ReturnDate.success(15009, "userid错误", null);
            }
            //用户总共收到的梵砖减去已经消费的梵砖
            Long convertibleCold = user.getConvertibleGold();
            if (convertibleCold == null ) {
                convertibleCold = 0L;
            }
            double canUse = Double.parseDouble(convertibleCold.toString());
            Double consumeIntegral = user.getUsedPoint();
            if (consumeIntegral == null) {
                consumeIntegral = 0D;
            }
            //用户已经锁定的积分
            Double freezePoint = user.getFreezePoint();
            if(freezePoint == null){
                freezePoint = 0D;
            }
            double integral = canUse - consumeIntegral - freezePoint;
            Map map = new HashMap();
            map.put("member_bn", userid);
            map.put("money", new BigDecimal(integral).setScale(2,ROUND_HALF_UP));
            map.put("point", new BigDecimal(integral).setScale(2,ROUND_HALF_UP));
            map.put("freeze_money", freezePoint);
            map.put("freeze_point", freezePoint);
            map.put("used_point", user.getUsedPoint());
            map.put("used_money", user.getUsedPoint());
            log.setUserName(user.getName());
            log.setActionResult("查询积分成功");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP,log);
            return ReturnDate.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.setActionResult(e.getMessage());
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP,log);
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

    /**
     * 创建内购用户,使用该接口的话会比较慢
     *
     * @param userid
     * @return
     */
    //@RequiresPermissions("ding:dingUser:edit")
    @RequestMapping(value = "createNeigouUser")
    @ResponseBody
    public ReturnInfo createNeigouUser(String userid) {
        try {
            return fzNeigouRefundService.createNeigouUser(userid,true);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,"服务器忙",e.getMessage());
        }
    }

    /**
     * 用户积分锁定
     * @param userLockIntegral
     * @return
     */
    @Uvantoken(getUvanToken = "true")
    @RequestMapping(value = "lockIntegral", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo lockIntegral(@RequestBody UserLockIntegral userLockIntegral) {
        try {
            return fzNeigouRefundService.lockIntegral(userLockIntegral);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

    /**
     * 锁定积分取消
     * @param userLockIntegral
     * @return
     */
    @Uvantoken(getUvanToken = "true")
    @RequestMapping(value = "unLockIntegral", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo unLockIntegral(@RequestBody UserLockIntegral userLockIntegral) {
        try {
            return fzNeigouRefundService.unLockIntegral(userLockIntegral);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

    /**
     * 锁定积分正式使用
     *
     * @param userLockIntegral
     * @return
     */
    @Uvantoken(getUvanToken = "true")
    @ResponseBody
    @RequestMapping(value = "realLockIntegral", method = RequestMethod.POST)
    public ReturnInfo realLockIntegral(@RequestBody UserLockIntegral userLockIntegral) {
        try {
            if (userLockIntegral == null) {
                return ReturnDate.success(15013, "用户积分锁定为空", null);
            }
            return fzNeigouRefundService.realLockIntegral(userLockIntegral);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

    /**
     * 导入用户数据
     */
    @ResponseBody
    @RequiresPermissions("ding:dingUser:import")
    @PostMapping(value = "importData")
    public String importData(MultipartFile file, String updateSupport) {
        try {
            boolean isUpdateSupport = Global.YES.equals(updateSupport);
            String message = dingUserService.importData(file, isUpdateSupport);
            return renderResult(Global.TRUE, "posfull:" + message);
        } catch (Exception ex) {
            return renderResult(Global.FALSE, "posfull:" + ex.getMessage());
        }
    }

   /* *
     * 锁定积分返还
     *
     * @param userLockIntegral
     * @return
    @IsFileter(isFile = "true")
    @ResponseBody
    @AccessLimit(limit = 1,sec = 2)
    @RequestMapping(value = "returnLockIntegral", method = RequestMethod.POST)
    public ReturnInfo returnLockIntegral(@RequestBody UserLockIntegral userLockIntegral) {
        try {
            return dingUserService.returnLockIntegral(userLockIntegral);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }*/

    /**
     * 支付后返还积分,退款用
     *
     * @return
     */
    @Uvantoken(getUvanToken = "true")
    @ResponseBody
    @RequestMapping(value = "returnPoint", method = RequestMethod.POST)
    public ReturnInfo returnPoint(@RequestBody UserLockIntegral userLockIntegral) {
        try {
            return fzNeigouRefundService.returnPoint(userLockIntegral);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100, "服务器忙", e.getMessage());
        }
    }

}

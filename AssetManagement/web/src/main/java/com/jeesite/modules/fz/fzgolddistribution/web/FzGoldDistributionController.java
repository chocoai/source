/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgolddistribution.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationType;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationTypeService;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.fz.fzgolddistribution.entity.FzGoldDistribution;
import com.jeesite.modules.fz.fzgolddistribution.service.FzGoldDistributionService;

import java.util.*;
import java.util.function.Predicate;

/**
 * 分配梵砖Controller
 *
 * @author dwh
 * @version 2018-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/fzgolddistribution/fzGoldDistribution")
public class FzGoldDistributionController extends BaseController {
    private static final String FPZB_PER_FIX = "LX";
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private DingDepartmentService dingDepartmentService;

    @Autowired
    private FzGoldDistributionService fzGoldDistributionService;
    @Autowired
    private FzAppreciationTypeService fzAppreciationTypeService;
    private List<DingUser> userIdList;

    /**
     * 获取数据
     */
    @ModelAttribute
    public FzGoldDistribution get(String documentCode, boolean isNewRecord) {
        return fzGoldDistributionService.get(documentCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("fzgolddistribution:fzGoldDistribution:view")
    @RequestMapping(value = {"list", ""})
    public String list(FzGoldDistribution fzGoldDistribution, Model model) {
        model.addAttribute("fzGoldDistribution", fzGoldDistribution);
        return "fz/fzgolddistribution/fzGoldDistributionList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("fzgolddistribution:fzGoldDistribution:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<FzGoldDistribution> listData(FzGoldDistribution fzGoldDistribution, HttpServletRequest request, HttpServletResponse response) {
        Page<FzGoldDistribution> page = fzGoldDistributionService.findPage(new Page<FzGoldDistribution>(request, response), fzGoldDistribution);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("fzgolddistribution:fzGoldDistribution:view")
    @RequestMapping(value = "form")
    public String form(FzGoldDistribution fzGoldDistribution, Model model) {
        String code = null;
        if (fzGoldDistribution.getIsNewRecord()) {
            code = amSeqService.getSeq(FPZB_PER_FIX);
            fzGoldDistribution.setDocumentCode(code);
            fzGoldDistribution.setDocumentStatus("0");
        }else {
            String userNames="";
            String departmentNames="";
            List<String> userIdList = ParamentUntil.getListByString(fzGoldDistribution.getUserIds());
            if (ParamentUntil.isBackList(userIdList)){
                for (int i=0;i<userIdList.size();i++){
                    DingUser dingUser = dingUserService.get(userIdList.get(i));
                    if (i==userIdList.size()-1){
                        userNames+=dingUser.getName();
                    }else {
                    userNames+=dingUser.getName()+",";
                    }
                }

            }
            List<String> departmentIdList = ParamentUntil.getListByString(fzGoldDistribution.getDepartmentId());
            if (ParamentUntil.isBackList(departmentIdList)){
                for (int i=0;i<departmentIdList.size();i++){
                    DingDepartment dingDepartment = dingDepartmentService.get(departmentIdList.get(i));
                    departmentNames+=dingDepartment.getName();
                }

            }
            fzGoldDistribution.setUserName(userNames);
            fzGoldDistribution.setDepartmentName(departmentNames);
        }

        model.addAttribute("fzGoldDistribution", fzGoldDistribution);
        return "fz/fzgolddistribution/fzGoldDistributionForm";
    }

    /**
     * 保存分配梵砖
     * @param fzGoldDistribution
     * @param documentStatusSH 1的话就是审核
     * @return
     */
    @RequiresPermissions("fzgolddistribution:fzGoldDistribution:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated FzGoldDistribution fzGoldDistribution, String documentStatusSH) {
        //保存校验
        if (fzGoldDistribution.getIsNewRecord()) {
            String code = amSeqService.getCode(FPZB_PER_FIX);
            fzGoldDistribution.setDocumentCode(code);
        }
        List<String> userIdList = ParamentUntil.getListByString(fzGoldDistribution.getUserIds());
        List<String> departmentIdList = ParamentUntil.getListByString(fzGoldDistribution.getDepartmentId());
        if (!ParamentUntil.isBackString(documentStatusSH)) {
            String distributionType = fzGoldDistribution.getDistributionType(); //分配类型
            if (distributionType.equals("3")) {        //特权用户
                if (!ParamentUntil.isBackList(userIdList)) {
                    return renderResult(Global.FALSE, text("用户为空"));
                }
                List<DingUser> dingUsers=dingUserService.getUserByIds(userIdList);
                if (!ParamentUntil.isBackList(dingUsers)) {
                    return renderResult(Global.FALSE, text("用户为空"));
                }
                String isVipUser = isVipUser(dingUsers);
                if (ParamentUntil.isBackString(isVipUser)) {
                    return renderResult(Global.FALSE, text("用户:" + isVipUser + "不是特权用户"));
                }
            } else if (distributionType.equals("2")) {
                if (!ParamentUntil.isBackList(userIdList)) {
                    return renderResult(Global.FALSE, text("用户为空"));
                }
            } else if (distributionType.equals("1")) {
                if (!ParamentUntil.isBackList(departmentIdList)) {
                    return renderResult(Global.FALSE, text("部门为空"));
                }
            }
            fzGoldDistributionService.save(fzGoldDistribution,documentStatusSH,userIdList,departmentIdList);
        }else if ("1".equals(documentStatusSH)){               //审核
            //如果是特权用户
            if ("3".equals(fzGoldDistribution.getDistributionType())){
                //如果没有选择赞赏类型
                if(Strings.isNullOrEmpty(fzGoldDistribution.getFzType())){
                    return renderResult(Global.FALSE, text("不允许赞赏类型为空"));
                }
            }
            fzGoldDistribution.setDocumentStatus("1");
            fzGoldDistribution.setReviewDate(new Date());
            fzGoldDistribution.setReviewUser(UserUtils.getUser().getUserCode());
            fzGoldDistributionService.save(fzGoldDistribution,documentStatusSH,userIdList,departmentIdList);
        }
        return renderResult(Global.TRUE, text("保存分配梵钻成功！"));

    }

    /**
     * 从user集合里面找出不是特权用户的用户
     * @param userIdList
     * @return
     */
    private String isVipUser(List<DingUser> userIdList) {
        String rst = null;
        for (int i = 0; i < userIdList.size(); i++) {
            DingUser dingUser = userIdList.get(i);
            if(dingUser != null){
                String userIsVip = dingUser.getUserIsVip();
                if("0".equals(userIsVip)){
                    rst += dingUser.getName() + "  ";
                }
            }
        }
        return rst;
    }

    /**
     * 删除分配梵砖
     */
    @RequiresPermissions("fzgolddistribution:fzGoldDistribution:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(FzGoldDistribution fzGoldDistribution) {
        fzGoldDistributionService.delete(fzGoldDistribution);
        return renderResult(Global.TRUE, text("删除分配梵砖成功！"));
    }

    /**
     * 用户根据钉钉id扣除梵赞
     * @param nums 需要兑换的梵赞数量
     * @param request
     * @return
     */
    @RequestMapping(value = "fzConsume")
    @IsFileter(isFile = "true")
    @ResponseBody
    public ReturnInfo fzConsume(String nums, HttpServletRequest request){
        try {
            String userid = request.getParameter("userid");
            return fzGoldDistributionService.fzConsume(nums,userid);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-100,e.getMessage());
        }
    }

}
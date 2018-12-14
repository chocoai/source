/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlogistics.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.jeesite.modules.asset.amlogistics.entity.AmLogistics;
import com.jeesite.modules.asset.amlogistics.service.AmLogisticsService;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 物流车查询单Controller
 *
 * @author dwh
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/amlogistics/amLogistics")
public class AmLogisticsController extends BaseController {
    @Value("${POST_K3ClOUDRL}")
    private String POST_K3ClOUDRL;  //测试库
    @Autowired
    private AmLogisticsService amLogisticsService;
    @Autowired
    private K3connection k3connection;
    //审核


    private final String sFormId = "YF_SAL_LogisticsQuery";
    /**
     * 获取数据
     */
    @ModelAttribute
    public AmLogistics get(String documentCode, boolean isNewRecord) {
        return amLogisticsService.get(documentCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("amlogistics:amLogistics:view")
    @RequestMapping(value = {"list", ""})
    public String list(AmLogistics amLogistics, Model model) {
        model.addAttribute("amLogistics", amLogistics);
        return "asset/amlogistics/amLogisticsList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("amlogistics:amLogistics:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AmLogistics> listData(AmLogistics amLogistics, HttpServletRequest request, HttpServletResponse response) {
//        EmpUser user = new EmpUser();
//        EmpUtils.getEmployee().getEmpNameEn();
        String empNameEn = EmpUtils.getEmployee().getEmpNameEn();
        String[] empNameEnList = null;
        if (empNameEn != null && empNameEn.length() > 0) {
            empNameEnList = empNameEn.split(",");
        }
        List<AmLogistics> list = new ArrayList<>();
        long total = 0;
        Page<AmLogistics> page = null;
        if (empNameEnList != null && empNameEnList.length > 0) {
            for (int i = 0; i < empNameEnList.length; i++) {
                amLogistics.setProvince(empNameEnList[i]);
                page = amLogisticsService.findPage(new Page<AmLogistics>(request, response), amLogistics);
                for (int j = 0; j < page.getList().size(); j++) {
                    list.add(page.getList().get(j));
                    total++;
                }
            }
            page.setCount(page.getCount());
            page.setList(list);
        } else {
            page = amLogisticsService.findPage(new Page<AmLogistics>(request, response), amLogistics);
        }
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("amlogistics:amLogistics:view")
    @RequestMapping(value = "form")
    public String form(AmLogistics amLogistics, Model model) {
        model.addAttribute("amLogistics", amLogistics);
        return "asset/amlogistics/amLogisticsForm";
    }

    /**
     * 保存物流车查询单
     */
    @RequiresPermissions("amlogistics:amLogistics:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(AmLogistics amLogistics, String userName, String flag) {
        if (flag == null) {
            if ("".equals(amLogistics.getProcessResult())) {
                return renderResult(Global.FALSE, "单据处理结果为空，不允许保存！");
            }
            if ("创建".equals(amLogistics.getDocumentStatus())) {     // 如果单据状态等于创建，则更新最新修改时间和最新修改人为当前操作时间和当前操作用户
                amLogistics.setModifyDate(new Date());
                amLogistics.setModifyPerson(UserUtils.getUser().getUserCode());   // 当前操作用户
            }else {
                return renderResult(Global.FALSE, "保存失败，单据状态不为创建！");
            }
        } else if ("1".equals(flag)) {
            if ("创建".equals(amLogistics.getDocumentStatus())) {
                {
                    amLogistics.setProcessPerson(userName);
                }
            } else {
                return renderResult(Global.FALSE, "转单失败，单据状态不为创建！");
            }
        } else {
            if (!("创建".equals(amLogistics.getDocumentStatus()))) {
                return renderResult(Global.FALSE, "单据状态不等于创建，不允许完结！");
            }
            if ("".equals(amLogistics.getProcessResult())) {
                return renderResult(Global.FALSE, "单据处理结果为空，不允许完结！");
            }

            boolean isLogin=k3connection.getConnection();
            if (isLogin==false){
                return renderResult(Global.FALSE, "k3登录失败");
            }
            try {
                //修改处理结果
                String result = amLogistics.getProcessResult();
                Long fId = (long)Integer.parseInt(amLogistics.getFdId());
                String billId = amLogistics.getDocumentCode();
                String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[]," +
                        "\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\"," +
                        "\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fId + "\",\"FBillNo\":\"" + billId + "\",\"F_YF_ProcessResult\":\"" + result + "\"}}";
                StringBuffer jsonRst = InvokeHelper.Save(sFormId, saveParam,POST_K3ClOUDRL);
                JSONObject json = (JSONObject) ((JSONObject) ((JSONObject)JSONObject.parse(String.valueOf(jsonRst))).get("Result")).get("ResponseStatus");
                boolean isSuceess= (boolean) json.get("IsSuccess");
                if (isSuceess) {
                    amLogisticsService.save(amLogistics);    //如果k3保存成功先保存进我们的数据库，再审核
                    String queryParam = "{\"FormId\":\"YF_SAL_LogisticsQuery\",\"FieldKeys\":\"" +
                            "FDOCUMENTSTATUS\",\"FilterString\":\"fid=" + fId + "\",\"OrderString\":\"\"," +
                            "\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
                    String a = InvokeHelper.ExecuteBillQuery(sFormId, queryParam,POST_K3ClOUDRL);
                    String[] b = a.split("");
                    String rst = b[3];
                    if (!"C".equals(rst)) {
                        String ID = amLogistics.getDocumentCode();
                        String auditparam = "{\"CreateOrgId\":\"0\",\"Numbers\":[\"" + ID + "\"],\"Ids\":\"\"}";
                        StringBuffer jsonRst1 = InvokeHelper.Audit(sFormId, auditparam,POST_K3ClOUDRL);
                        JSONObject json1 = (JSONObject) ((JSONObject) ((JSONObject) JSONObject.parse(String.valueOf(jsonRst1))).get("Result")).get("ResponseStatus");
                        boolean isSuceess1 = (boolean) json1.get("IsSuccess");
                        if (!isSuceess1) {
                            return renderResult(Global.FALSE, "k3保存成功！审核失败，或已审核");
                        }
                    }
                    } else {
                        return renderResult(Global.FALSE, "k3保存失败");
                    }
                    System.out.println(jsonRst);

            }catch (Exception e){
                return renderResult(Global.FALSE, "k3保存失败！");
            }
//            try {
//                String ID=amLogistics.getDocumentCode();
//                String auditparam= "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+ID+"\"],\"Ids\":\"\"}";
//                StringBuffer jsonRst = InvokeHelper.Audit(sFormId, auditparam);
//                System.out.println(jsonRst);
//            } catch (Exception e) {
//                return renderResult(Global.TRUE, "k3审核失败或者已审核！");
//            }


        }
        if ("2".equals(flag)) {
            amLogistics.setCompletionPerson(UserUtils.getUser().getUserCode());   // 更新完成人等于当前用户
            amLogistics.setCompletionDate(new Date());     // 完成时间等于当前时间
            amLogistics.setDocumentStatus("已完结");
        }

        amLogisticsService.save(amLogistics);
        return renderResult(Global.TRUE, "保存成功！");
    }

    /**
     * 删除物流车查询单
     */
    @RequiresPermissions("amlogistics:amLogistics:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(AmLogistics amLogistics) {
        amLogisticsService.delete(amLogistics);
        return renderResult(Global.TRUE, "删除物流车查询单成功！");
    }

}
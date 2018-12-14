/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.userroleconfig.entity.InterfaceField;
import com.jeesite.modules.userroleconfig.entity.InterfaceInfo;
import com.jeesite.modules.userroleconfig.entity.UserDataPermission;
import com.jeesite.modules.userroleconfig.service.InterfaceInfoService;
import com.jeesite.modules.userroleconfig.service.UserDataPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口信息Controller
 *
 * @author dwh
 * @version 2018-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/userroleconfig/interfaceInfo")
public class InterfaceInfoController extends BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private UserDataPermissionService userDataPermissionService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public InterfaceInfo get(String interfaceCode, boolean isNewRecord) {
        return interfaceInfoService.get(interfaceCode, isNewRecord);
    }
    /**
     * 查询列表
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(InterfaceInfo interfaceInfo, Model model) {
        model.addAttribute("interfaceInfo", interfaceInfo);
        return "modules/userroleconfig/interfaceInfoList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<InterfaceInfo> listData(InterfaceInfo interfaceInfo, HttpServletRequest request, HttpServletResponse response) {
//        StringBuffer url=request.getRequestURL();
//        if (url.toString().contains("/userroleconfig/interfaceInfo/listData1")){
//            System.out.println(url);
//        }
        Page<InterfaceInfo> page = interfaceInfoService.findPage(new Page<InterfaceInfo>(request, response), interfaceInfo);
        return page;
    }

    /**
     * 根据用户code查询接口列表数据
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:view")
    @RequestMapping(value = "listDataByUserCode")
    @ResponseBody
    public Page<InterfaceInfo> listDataByUserCode(InterfaceInfo interfaceInfo, HttpServletRequest request, HttpServletResponse response, String userCode) {
        if (userCode != null && userCode.length() > 0) {
            interfaceInfo.setUserCode(userCode);
        }
        interfaceInfo.setStatus("");
        if(interfaceInfo.getUserCode()==null||interfaceInfo.getUserCode().length()<=0){
            Page<InterfaceInfo> page=new Page<>();
            return page;
        }
        Page<InterfaceInfo> page = interfaceInfoService.findPage(new Page<InterfaceInfo>(request, response), interfaceInfo);
        for (int i=0;i<page.getList().size();i++){
            UserDataPermission userDataPermission=new UserDataPermission();
            userDataPermission.setUserCode(interfaceInfo.getUserCode());
            userDataPermission.setInterfaceCode(page.getList().get(i).getInterfaceCode());
            List <UserDataPermission> userDataPermissions=userDataPermissionService.findList(userDataPermission);
            page.getList().get(i).setStitching(userDataPermissions.get(0).getStitching());
            page.getList().get(i).setInterfaceSql(userDataPermissions.get(0).getInterfaceSql());
        }
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:view")
    @RequestMapping(value = "form")
    public String form(InterfaceInfo interfaceInfo, Model model) {
        model.addAttribute("interfaceInfo", interfaceInfo);
        return "modules/userroleconfig/interfaceInfoForm";
    }

    /**
     * 保存接口信息
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save( InterfaceInfo interfaceInfo) {
//        boolean isHaveChongfu=isHaveChongfu(interfaceInfo);
//        if (isHaveChongfu){
//            return renderResult(Global.FALSE, "保存接口信息失败！接口已存在参数");
//        }
        interfaceInfoService.save(interfaceInfo);
        return renderResult(Global.TRUE, "保存接口信息成功！");
    }

    private boolean isHaveChongfu(InterfaceInfo interfaceInfo) {
        boolean rst=false;
        if (interfaceInfo.getInterfaceFieldList()!=null&&interfaceInfo.getInterfaceFieldList().size()>0){
            List<String> value=new ArrayList<>();
            for (int i=0;i<interfaceInfo.getInterfaceFieldList().size();i++){
                String fieldvalue=interfaceInfo.getInterfaceFieldList().get(i).getFieldValue();
                if (value==null||value.size()<=0){
                    value.add(fieldvalue);
                }else {
                    for (int j=0;j<value.size();j++){
                        if (fieldvalue.equals(value.get(j))){
                            return true;
                        }
                        value.add(fieldvalue);
                    }
                }
            }
        }
        return rst;
    }

    /**
     * 停用接口信息
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(InterfaceInfo interfaceInfo) {
        interfaceInfo.setStatus(InterfaceInfo.STATUS_DISABLE);
        interfaceInfoService.updateStatus(interfaceInfo);
        return renderResult(Global.TRUE, "停用接口信息成功");
    }

    /**
     * 启用接口信息
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(InterfaceInfo interfaceInfo) {
        interfaceInfo.setStatus(InterfaceInfo.STATUS_NORMAL);
        interfaceInfoService.updateStatus(interfaceInfo);
        return renderResult(Global.TRUE, "启用接口信息成功");
    }

    /**
     * 删除接口信息
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(InterfaceInfo interfaceInfo) {
        interfaceInfoService.delete(interfaceInfo);
        return renderResult(Global.TRUE, "删除接口信息成功！");
    }

    /**
     * 删除用户拥有的接口信息
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @RequestMapping(value = "deluserInfterface")
    @ResponseBody
    public String deluserInfterface(String userCode, String ids) {
        if (ids != null && ids.length() > 0) {
            String[] codeList = ids.split(",");
            for (int i = 0; i < codeList.length; i++) {
                interfaceInfoService.deluserInfterface(userCode, codeList[i]);
                //删除redis
                redisTemplate.delete(userCode+"_"+ codeList[i]);
            }
        }
        return renderResult(Global.TRUE, "删除接口信息成功！");
    }

    /**
     * 物理删除入库表
     */
    @RequiresPermissions("userroleconfig:interfaceInfo:edit")
    @RequestMapping(value = "deleteDb")
    @ResponseBody
    public String deleteDb(InterfaceInfo interfaceInfo, String ids) {
        if (ids != null && ids.length() > 0) {
            String[] codeList = ids.split(",");
            for (int i = 0; i < codeList.length; i++) {
                interfaceInfoService.deleteDb(codeList[i]);
            }
        }
        return renderResult(Global.TRUE, "删除接口信息成功！");
    }
    /**
     *查询用户没有的接口信息，如果点击用户新增接口，只传用户id，返回用户没有的接口，如果编辑，传用户和接口ID返回对应接口和sql
     */
    @RequestMapping(value = "getInfoByNotInUser")
    @ResponseBody
    public List<InterfaceInfo> getInfoByNotInUser(String userCode,String interfaceCode) {
        List<InterfaceInfo> rst=null;
        if ((userCode!=null&&userCode.length()>0)&&(interfaceCode==null||interfaceCode.length()<=0)){
        rst =interfaceInfoService.getInfoByNotInUser(userCode);
        }else if (userCode!=null&&userCode.length()>0&&interfaceCode!=null||interfaceCode.length()>0){
            rst =interfaceInfoService.getInfoByUserCodeAndIntCode(userCode,interfaceCode);
        }
        return rst;
    }



    /**
     *根据接口code，查询已有的参数
     */
    @RequestMapping(value = "getFieldByInterCode")
    @ResponseBody
    public List<InterfaceField> getFieldByInterCode(String interfaceCode) {
        List<InterfaceField> rst=null;
        if (interfaceCode!=null&&interfaceCode.length()>0){
            rst=interfaceInfoService.getFieldByInterCode(interfaceCode);
        }
        return rst;
    }
}
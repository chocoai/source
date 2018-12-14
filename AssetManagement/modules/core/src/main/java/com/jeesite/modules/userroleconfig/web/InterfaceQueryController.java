/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.userroleconfig.entity.InterfaceField;
import com.jeesite.modules.userroleconfig.entity.InterfaceQuery;
import com.jeesite.modules.userroleconfig.entity.InterfaceQueryData;
import com.jeesite.modules.userroleconfig.entity.UserDataPermission;
import com.jeesite.modules.userroleconfig.service.InterfaceInfoService;
import com.jeesite.modules.userroleconfig.service.InterfaceQueryService;
import com.jeesite.modules.userroleconfig.service.UserDataPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口查询条件Controller
 *
 * @author dwh
 * @version 2018-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/userroleconfig/interfaceQuery")
public class InterfaceQueryController extends BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private InterfaceQueryService interfaceQueryService;
    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private UserDataPermissionService userDataPermissionService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public InterfaceQuery get(String queryCode, boolean isNewRecord) {
        return interfaceQueryService.get(queryCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:view")
    @RequestMapping(value = {"list", ""})
    public String list(InterfaceQuery interfaceQuery, Model model) {
        model.addAttribute("interfaceQuery", interfaceQuery);
        return "modules/userroleconfig/interfaceQueryList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<InterfaceQuery> listData(InterfaceQuery interfaceQuery, HttpServletRequest request, HttpServletResponse response) {
        Page<InterfaceQuery> page = interfaceQueryService.findPage(new Page<InterfaceQuery>(request, response), interfaceQuery);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:view")
    @RequestMapping(value = "form")
    public String form(InterfaceQuery interfaceQuery, Model model) {
        model.addAttribute("interfaceQuery", interfaceQuery);
        return "modules/userroleconfig/interfaceQueryForm";
    }

    /**
     * 保存接口查询条件
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated InterfaceQuery interfaceQuery) {
        interfaceQueryService.save(interfaceQuery);
        return renderResult(Global.TRUE, "保存接口查询条件成功！");
    }

    /**
     * 停用接口查询条件
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(InterfaceQuery interfaceQuery) {
        interfaceQuery.setStatus(InterfaceQuery.STATUS_DISABLE);
        interfaceQueryService.updateStatus(interfaceQuery);
        return renderResult(Global.TRUE, "停用接口查询条件成功");
    }

    /**
     * 启用接口查询条件
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(InterfaceQuery interfaceQuery) {
        interfaceQuery.setStatus(InterfaceQuery.STATUS_NORMAL);
        interfaceQueryService.updateStatus(interfaceQuery);
        return renderResult(Global.TRUE, "启用接口查询条件成功");
    }

    /**
     * 删除接口查询条件
     */
    @RequiresPermissions("userroleconfig:interfaceQuery:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(InterfaceQuery interfaceQuery) {
        interfaceQueryService.delete(interfaceQuery);
        return renderResult(Global.TRUE, "删除接口查询条件成功！");
    }


    /**
     * 根据接口Code查询条件列表
     */
    @RequestMapping(value = "getQueryList")
    @ResponseBody
    public List<InterfaceQuery> getQueryList(String interfaceCode, String userCode, String fileCode) {
        List<InterfaceQuery> list = interfaceQueryService.getInterfaceQueryList(interfaceCode, userCode, fileCode);

        return list;
    }
    /**
     * 根据接口Code查询条件列表
     */
    @RequestMapping(value = "getQueryListByRole")
    @ResponseBody
    public List<InterfaceQuery> getQueryListByRole(String interfaceCode, String roleCode, String fileCode) {
        List<InterfaceQuery> list = interfaceQueryService.getQueryListByRole(interfaceCode, roleCode, fileCode);

        return list;
    }
    /**
     * 保存接口查询条件
     */
    @PostMapping(value = "saveQuery")
    @ResponseBody
    public String saveQuery(@RequestBody InterfaceQueryData interfaceQueryData) {
        if (interfaceQueryData == null) {
            return renderResult(Global.FALSE, "保存接口查询条件失败！");
        }
        UserDataPermission userDataPermission = interfaceQueryData.getUserDataPermission();
        List<InterfaceQuery> interfaceQuerys = interfaceQueryData.getInterfaceQuerys();
        if (userDataPermission != null) {
            //保存 关联表，替换sql
            System.out.println(userDataPermission.getIsNewRecord());
            UserDataPermission userDataPermission2=new UserDataPermission();
            userDataPermission2.setUserCode(userDataPermission.getUserCode());
            userDataPermission2.setInterfaceCode(userDataPermission.getInterfaceCode());
            List<UserDataPermission> userDataPermissions= userDataPermissionService.findList(userDataPermission2);
            if (userDataPermissions!=null&&userDataPermissions.size()>0){
                userDataPermission.setIsNewRecord(false);
                userDataPermission.setPermissionCode(userDataPermissions.get(0).getPermissionCode());
                userDataPermissionService.save(userDataPermission);
            }else {
                userDataPermissionService.save(userDataPermission);
            }


            String stitching = userDataPermission.getStitching();    //1=1 and 1=3
            StringBuffer interfaceSQL = new StringBuffer();
//            //查询用户接口所有的条件
            UserDataPermission userDataPermission1 = new UserDataPermission();
            userDataPermission1.setUserCode(userDataPermission.getUserCode());
            userDataPermission1.setInterfaceCode(userDataPermission.getInterfaceCode());
            List<UserDataPermission>userDataPermissionlist=userDataPermissionService.findList(userDataPermission1);
            //保存接口对应的条件
            if (interfaceQuerys != null && interfaceQuerys.size() > 0) {
                for (int i = 0; i < interfaceQuerys.size(); i++) {
                    InterfaceQuery interfaceQuery = interfaceQuerys.get(i);
                    String type = interfaceQuery.getType();
                    if ("add".equals(type)) {
                        interfaceQuery.setIsNewRecord(true);
                        interfaceQueryService.save(interfaceQuery);
                    } else if ("edit".equals(type)) {
                        interfaceQuery.setIsNewRecord(false);
                        interfaceQueryService.save(interfaceQuery);
                    } else if ("del".equals(type)) {
                        interfaceQueryService.delete(interfaceQuery);
                    } else {
                        return renderResult(Global.TRUE, "参数type不正确");
                    }


//
//                    String value = interfaceQuerys.get(i).getFieldValue();
//                    InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerys.get(i).getFieldCode());
//                    //得到参数类型
//                    String dataType =interfaceField.getDataType();
//                    //如果字段类型是string和datetime就给条件的值加上单引号
//                    if ("String".equals(dataType) || "DateTime".equals(dataType)) {
//                        value = "'" + value + "'";
//                    }
//                    //得到参数值
//                    String param = interfaceField.getFieldValue();
//                    if (stitching == null || stitching.length() <= 0) {                                   //默认用and拼接
//
//                        interfaceSQL += param + " " + interfaceQuerys.get(i).getSymbol() + " " + value;
//                        if (interfaceQuerys.size() > 1 && i < interfaceQuerys.size() - 1) {  //多条件并且不是最后一条用and拼接
//                            interfaceSQL += " and ";
//                        }
//                    }
//                    if (i==interfaceQuerys.size()-1&&(oldSql!=null&&oldSql.length()>0)){
//                        interfaceSQL=oldSql+" and "+interfaceSQL;
//                    }
                }
                InterfaceQuery interfaceQuery2=new InterfaceQuery();
                interfaceQuery2.setInterfaceCode(userDataPermission.getInterfaceCode());
                interfaceQuery2.setUserCode(userDataPermission.getUserCode());
                List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery2);
                for (int j=0;j<interfaceQuerylist.size();j++) {
                    String value = interfaceQuerylist.get(j).getFieldValue();
                    if ("登陆者ID".equals(value)){
                        value=UserUtils.getUser().getLoginCode();
                    }
                    InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(j).getFieldCode());
                    //得到参数类型
                    String dataType =interfaceField.getDataType();
                    //如果字段类型是string和datetime就给条件的值加上单引号
                    if ("String".equals(dataType) || "DateTime".equals(dataType)) {
                        value = "'" + value + "'";
                    }
                    //得到参数值
                    String param = interfaceField.getFieldValue();
                    if (stitching == null || stitching.length() <= 0) {                                   //默认用and拼接

                        interfaceSQL.append( param + " " + interfaceQuerylist.get(j).getSymbol() + " " + value);
                        if (interfaceQuerylist.size() > 1 && j < interfaceQuerylist.size() - 1) {  //多条件并且不是最后一条用and拼接
                            interfaceSQL .append(" and ");
                        }
                    }
                }

            }

            if (stitching!=null&&stitching.length()>0){
                Map map=new HashMap();
                InterfaceQuery interfaceQuery=new InterfaceQuery();
                interfaceQuery.setInterfaceCode(userDataPermission.getInterfaceCode());
                interfaceQuery.setUserCode(userDataPermission.getUserCode());
                List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery);
                for (int i=0;i<interfaceQuerylist.size();i++) {
                    String value = interfaceQuerylist.get(i).getFieldValue();
                    if ("登陆者ID".equals(value)){
                        value=UserUtils.getUser().getLoginCode();
                    }
                    InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(i).getFieldCode());
                    //得到参数类型
                    String dataType = interfaceField.getDataType();
                    //如果字段类型是string和datetime就给条件的值加上单引号
                    if ("String".equals(dataType) || "DateTime".equals(dataType)) {
                        value = "'" + value + "'";
                    }
                    //得到参数值
                    String param = interfaceField.getFieldValue();
                    //查询这个序号的sql
                    String sortNo = interfaceQuerylist.get(i).getSortNo();    //单个序号
                    String danInterfaceSQL = param + " " + interfaceQuerylist.get(i).getSymbol() + " " + value; //单个序号对应的条件
                    map.put(sortNo,danInterfaceSQL);

//                    String patt = "\\b" + sortNo + "+\\b";
//
////                    // 用于测试的输入字符串
////                    String input = "1 and 2";
//                    System.out.println("Input:" + stitching);
//
//                    // 从正则表达式实例中运行方法并查看其如何运行
//                    Pattern r = Pattern.compile(patt);
//                    Matcher m = r.matcher(stitching);
//                    stitching = m.replaceAll(danInterfaceSQL);     //循环替换正则的数字，把patt替换成sql
////                    System.out.println("ReplaceAll:" + m.replaceAll("user = 'sadsa'"));
//                    interfaceSQL = stitching;
//                }
            }
            if (map!=null&&map.size()>0){
               interfaceSQL=prossesSql(stitching,map);
            }


            }else if ((interfaceQuerys == null || interfaceQuerys.size() <= 0)&&(stitching==null||stitching.length()<=0)){
                InterfaceQuery interfaceQuery2=new InterfaceQuery();
                interfaceQuery2.setInterfaceCode(userDataPermission.getInterfaceCode());
                interfaceQuery2.setUserCode(userDataPermission.getUserCode());
                List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery2);
                for (int j=0;j<interfaceQuerylist.size();j++) {
                    String value = interfaceQuerylist.get(j).getFieldValue();
                    if ("登陆者ID".equals(value)){
                        value=UserUtils.getUser().getLoginCode();
                    }
                    InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(j).getFieldCode());
                    //得到参数类型
                    String dataType =interfaceField.getDataType();
                    //如果字段类型是string和datetime就给条件的值加上单引号
                    if ("String".equals(dataType) || "DateTime".equals(dataType)) {
                        value = "'" + value + "'";
                    }
                    //得到参数值
                    String param = interfaceField.getFieldValue();
                    if (stitching == null || stitching.length() <= 0) {                                   //默认用and拼接

                        interfaceSQL .append( param + " " + interfaceQuerylist.get(j).getSymbol() + " " + value);
                        if (interfaceQuerylist.size() > 1 && j < interfaceQuerylist.size() - 1) {  //多条件并且不是最后一条用and拼接
                            interfaceSQL .append( " and ");
                        }
                    }
                }

            }
            //更新sql
            UserDataPermission userDataPermission3=new UserDataPermission();
            userDataPermission3.setPermissionCode(userDataPermission.getPermissionCode());
            userDataPermission3=userDataPermissionService.get(userDataPermission3);
            userDataPermission3.setInterfaceSql(interfaceSQL.toString());
            if (userDataPermission3!=null){
                userDataPermission3.setIsNewRecord(false);
            }
            userDataPermissionService.save(userDataPermission3);
            //保存进入redis
            if (interfaceSQL!=null&&interfaceSQL.length()>0){
            redisTemplate.opsForValue().set("user_"+userDataPermission.getUserCode()+"_"+userDataPermission.getInterfaceCode(),interfaceSQL.toString() );
            }else {
                //如果保存时没有sql，把redis中的对应的数据删除
               redisTemplate.delete( "user_"+userDataPermission.getUserCode()+"_"+userDataPermission.getInterfaceCode());
            }
        }
        return renderResult(Global.TRUE, "保存接口查询条件成功！");
    }

    private StringBuffer prossesSql(String stitching, Map map) {
        StringBuffer rst=new StringBuffer();
        String [] lists=stitching.split(" ");
        for (int i=0;i<lists.length;i++){
            boolean isInt=isInt(lists[i]);
            if (isInt){
                lists[i]= (String) map.get(lists[i]);
                rst.append(" ");
                rst.append(lists[i]);
            }else {
                rst.append(" ");
                rst.append(lists[i]);
            }
        }
        return rst;
    }

    private boolean isInt(String list) {
        boolean rst=false;
        try {
            Integer.parseInt(list);
            rst=true;
        }catch (Exception e){
            return  rst;
        }
        return rst;
    }


    /**
     * 根据接口Code查询条件列表
     */
    @RequestMapping(value = "getQueryByCode")
    @ResponseBody
    public InterfaceQuery getQueryByCode(String queryCode) {
        InterfaceQuery interfaceQuery = new InterfaceQuery();
        interfaceQuery.setQueryCode(queryCode);
        interfaceQuery = interfaceQueryService.getQueryByCode(interfaceQuery);
        return interfaceQuery;
    }
}
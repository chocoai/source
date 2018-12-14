package com.jeesite.modules.userroleconfig;

import com.jeesite.modules.basicroleinfoconfig.entity.BasicRoleInfo;
import com.jeesite.modules.basicroleinfoconfig.service.BasicRoleInfoService;
import com.jeesite.modules.basicroleinfoconfig.service.RoleDataPermissionService;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.userroleconfig.entity.InterfaceInfo;
import com.jeesite.modules.userroleconfig.service.InterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDataPermissionUnit {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private BasicRoleInfoService roleInfoService;

    public String getParam(HttpServletRequest request) {
        String param = "";
        StringBuffer url = request.getRequestURL();
        List<BasicRoleInfo> roles = roleInfoService.getListByUserCode(UserUtils.getUser().getUserCode());    //得到用户的所属角色
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.listDataByUserCode(UserUtils.getUser().getLoginCode());
        if (!"system".equals(UserUtils.getUser().getLoginCode())) {
            if (interfaceInfos != null && interfaceInfos.size() > 0) {
                for (int i = 0; i < interfaceInfos.size(); i++) {
                    if (url.toString().contains(interfaceInfos.get(i).getUrl()) && "0".equals(interfaceInfos.get(i).getStatus())) {
                        String a = "user_" + UserUtils.getUser().getLoginCode() + "_" + interfaceInfos.get(i).getInterfaceCode();
                        String value = redisTemplate.opsForValue().get(a);
                        if (value != null && value.length() > 0) {
                            param += " and " + redisTemplate.opsForValue().get("user_" + UserUtils.getUser().getLoginCode() + "_" + interfaceInfos.get(i).getInterfaceCode());
                        }
//                        else {
//                            //如果用户配置了接口，但是没配置sql，还是循环用户sql
//                     param=prossesRole(roles,url);
//                        }

                    }
                }
                //循环完后没有sql继续进入角色
                if (param == null || param.length() <= 0) {
                    param = prossesRole(roles, url);
                }

            } else {
                //用户没有所属接口就用角色的
                param = prossesRole(roles, url);
            }
            //当用户he角色都循环完后，sql还是空的，设置1=2
            if (param == null || param.length() <= 0) {
                param = "and 1=2";
            }
        }
        return param;
    }


    //得到角色的sql
    private String prossesRole(List<BasicRoleInfo> roles, StringBuffer url) {
        String param = "";
        //如果用户没配置任何权限，以角色为主
        if (roles != null && roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                if ("2".equals(roles.get(i).getDataScope())) {             //判断角色是否是自定义数据权限
                    //得到角色所拥有的接口
                    List<InterfaceInfo> interfaceInfos1 = interfaceInfoService.listDataByRoleCode(roles.get(i).getRoleCode());
                    if (interfaceInfos1 != null && interfaceInfos1.size() > 0) {
                        for (int h = 0; h < interfaceInfos1.size(); h++) {
                            if (url.toString().contains(interfaceInfos1.get(h).getUrl()) && "0".equals(interfaceInfos1.get(h).getStatus())) {            //如果url正常
                                //循环找到这个接口
                                String key = "role_" + roles.get(i).getRoleCode() + "_" + interfaceInfos1.get(h).getInterfaceCode();   //角色+接口的key
                                String values = redisTemplate.opsForValue().get(key);   //得到该角色的sql
                                if (values != null && values.length() > 0) {
                                    param += " and " + values;
                                }
                            }
                        }

                    }
                } else if (roles.get(i).getDataScope()==null||roles.get(i).getDataScope().length()<=0||"0".equals(roles.get(i).getDataScope())) {
                    param = " and 1=2 ";                      //角色的数据权限范围为0  （未设置,有一个角色的不允许查看就返回1=2）
                    return param;
                } else {
                    if (param==null||param.length()<=0){
                    param += " and 1=1";                          //如只有当参数为空且 角色的数据权限范围为1时（全部数据）
                         }
                }
            }

        } else {
            //如果登录用户没有角色 返回没有权限
            param = " and 1=2 ";
        }
        return param;
    }
    public static void main(String[] args) {
        // 创建一个正则表达式模式，用以匹配一个单词（\b=单词边界）
        StringBuffer a=new StringBuffer();
        String input1 = "( 1 or 2 )";
        String [] lists=input1.split(" ");
        String b="(1";
//        b=b.split("\\(");
//        b=b.split("\\)");
        System.out.println(b);
        for (int i=0;i<lists.length;i++){
//            System.out.println(lists[i]);
//            String patt = "\\b" + lists[i] + "+\\b";
//            Pattern r = Pattern.compile(patt);
//            Matcher m = r.matcher(lists[i]);
//            String value= m.replaceAll(sql);    //替换成对应的sql
            a.append(" and ");
//            a.append(value);
        }



//        for (int i=0;i<2;i++){
//        String patt = "[0-9]";
//
//        // 用于测试的输入字符串
//
////        String input2 = "a.status=1";
////        System.out.println("Input:" + input);
//            String sql="";
//        if (i==0){
//            sql="a.status='1'";
//         }
//            if (i==0){
//                sql="a.status='2'";
//            }
//        // 从正则表达式实例中运行方法并查看其如何运行
//        Pattern r = Pattern.compile(patt);
//        Matcher m = r.matcher(input1);
//            input1= m.replaceAll(sql);
//    }
//        System.out.println(input1);
    }
}

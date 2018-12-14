package com.jeesite.modules.asset.ding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

//若DingUser实体类有了getLeft的构造方法，会导致此同步方法失败

@Component
public class DingSyncTask {

    private static DingSyncTask dingSyncTask;

    @PostConstruct
    public void init() {
        dingSyncTask=this;
    }


    @Autowired
    private DingUserService userService;
    @Autowired
    private DingDepartmentService departmentService;


    /**
     * 调用钉钉接口，查询钉钉所有部门列表,得出id后根据id调用接口得到部门数据
     * @author thomas
     * @version 2018-12-0
     * @param
     * @return void
     */

    //@Scheduled(cron = "15 * * * * ?")
    public static void syncDingData(){
        String access_token = dingSyncTask.getDingToken();
        if(dingSyncTask.syncDingDepartments(access_token))if (dingSyncTask.successSyncRole(access_token)) if (dingSyncTask.syncDingUsers())
            System.out.println("同步钉钉通信录成功");
        else System.out.println("同步钉钉通信录失败");
}

    /**
     * 调用钉钉接口，查询钉钉所有部门列表,得出id后根据id调用接口得到部门数据
     * @author thomas
     * @version 2018-12-05
     * @method GET
     * @param
     * @return boolean
     */
    private boolean syncDingDepartments(String access_token){

        try {
            String url = "https://oapi.dingtalk.com/department/list?access_token=" + access_token + "&fetch_child=true";
            String jsonResponse = HttpClientUtils.get(url);
            Map mapId = JSONObject.parseObject(jsonResponse);

            Object deptObj = mapId.get("department");
            String deptJson = JSONObject.toJSONString(deptObj);
            List deptIdList = JSONArray.parseArray(deptJson);

            System.out.println(deptIdList);
            List<String> ids = new ArrayList<>();
            for (Object s : deptIdList) {
                Map idMap = JSONObject.parseObject(s.toString());
                ids.add(idMap.get("id").toString());
            }
            getDepartmentDetails(access_token, ids);
            System.out.println("同步部门成功，syncDingDepartments");
            return true;
        }catch (Exception e){
            System.out.println("同步部门出错，syncDingDepartments");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 调用钉钉接口，根据id调用接口得到部门数据，并且同步
     * @author thomas
     * @version 2018-12-05
     * @method GET
     * @param
     * @return void
     */
    private void getDepartmentDetails(String token, List<String> ids){

        try {
            String url = "https://oapi.dingtalk.com/department/get?access_token=" + token + "&id=";
            List <Map> dept = new LinkedList<>();
            ids.forEach(id ->  dept.add((Map) JSONObject.parse(HttpClientUtils.get(url + "" + id))));
            departmentService.sysAllDepartment(JSON.toJSONString(dept));
            System.out.println("同步部门详情成功，getDepartmentDetails");
        }catch (Exception e){
            System.out.println("同步部门详情出错，getDepartmentDetails");
            e.printStackTrace();
        }
    }


    private boolean successSyncRole(String access_token){
    try{
        syncDingRoles(access_token,0,50);

        System.out.println("同步角色成功，syncDingRoles");
        return true;
    }catch (Exception e){
        System.out.println("同步角色出错，syncDingRoles");
        e.printStackTrace();
        return false;
    }
    }





    /**
     * 调用钉钉接口，同步角色信息
     * @author thomas
     * @version 2018-12-06
     * @method POST
     * @param
     * @return boolean
     */

    private void syncDingRoles(String access_token, int offset,int size){

            String url = "https://oapi.dingtalk.com/topapi/role/list?access_token=" + access_token;

            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("offset", String.valueOf(offset));
            requestParam.put("size", String.valueOf(size));

            Map json = JSONObject.parseObject(HttpClientUtils.post(url, requestParam));
            Map result = (Map) json.get("result");
            userService.sysDingRole(JSONObject.toJSONString(result));
            if (result.get("hasMore").toString().equals("true")) syncDingRoles(access_token, offset+size+1, size);

    }

    /**
     * 调用钉钉接口,根据部门id去查询所有部门下的员工userid,同步时间太长，另起token
     * @author thomas
     * @version 2018-12-06
     * @method GET
     * @param
     * @return boolean
     */

    public boolean syncDingUsers (){
        try {
            String access_token = getDingToken();
            Set<String> userIds = syncDingUsersID();
            System.out.println(userIds);
            String url = "https://oapi.dingtalk.com/user/get?access_token=" + access_token + "&userid=";
            List<String> list = new ArrayList<>();
            if (userIds!=null) userIds.forEach(s -> list.add(HttpClientUtils.get(url + "" +s)));
            userService.sysDingUser(JSONArray.toJSONString(list));
            System.out.println("根据userid同步员工详情成功，syncDingUsers");
            return true;
        }catch (Exception e){
            System.out.println("根据userid同步员工详情出错，syncDingUsers");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 调用钉钉接口,根据部门id去查询所有部门下的员工userid
     * @author thomas
     * @version 2018-12-06
     * @method GET
     * @param
     * @return Set<String>
     */
    private Set<String> syncDingUsersID(){

        try {
            String access_token =getDingToken();
            String url = "https://oapi.dingtalk.com/user/getDeptMember?access_token="+access_token+"&deptId=";
            List<String> deptId = departmentService.getAllDepartmentId();
            HashSet<String> userIdSet = new HashSet<>();
            for (String s :deptId) {
                Map result = JSONObject.parseObject(HttpClientUtils.get(url+""+s));
                List userIds = JSONArray.parseArray(result.get("userIds").toString());
                for (Object id :userIds) {
                    userIdSet.add(id.toString());
                }
            }
            System.out.println("通过部门id获取部门下员工的userid成功，syncDingUsersID");
            return userIdSet;
        }catch (Exception e){
            System.out.println("通过部门id获取部门下员工的userid出错，syncDingUsersID");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 调用钉钉接口，发送本企业的CorpId和CorpSecret，来换取access_token
     * @author thomas
     * @version 2018-12-05
     * @method GET
     * @param
     * @return String(access_token)
     */
    private String getDingToken(){
        String corpid ="dingde55314a8e20f3f6";
        String corpsecret = "Fz7Yg3r7ITk18Lfx434b2gaRikVN6vtZnX7OxMVIckfF-l0YWZW5lwPobYm48brI";
        String url="https://oapi.dingtalk.com/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
        Map result = JSON.parseObject(HttpClientUtils.get(url));
        return result.get("access_token").toString();
    }


    //@Scheduled(cron = "15 * * * * ?")
    public void test(){
        syncDingUsers();

    }
}

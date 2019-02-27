package com.jeesite.modules.asset.ding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingRoleService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.dingding.entity.DingApiUser;
import com.jeesite.modules.dingding.service.DingDingService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private DingRoleService roleService;
    @Autowired
    private DingDingService dingDingService;


    /**
     * 调用钉钉接口，查询钉钉所有部门列表,得出id后根据id调用接口得到部门数据
     * @author thomas
     * @version 2018-12-0
     * @param
     * @return void
     */
    //@Scheduled(cron = "10 * * * * ?")
    public static void syncDingData() {
        String access_token = dingSyncTask.getDingToken();
        if (dingSyncTask.syncDingDepartments(access_token))
            if (dingSyncTask.successSyncRole(access_token))
                if (dingSyncTask.syncDingUsers(access_token))
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
            System.out.println(deptIdList.size());
            List<String> ids = new ArrayList<>();
            for (Object s : deptIdList) {
                Map idMap = JSONObject.parseObject(s.toString());
                ids.add(idMap.get("id").toString());
            }
            System.out.println(ids);
            System.out.println(ids.size());
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
            ids.forEach(id ->  dept.add((Map) JSONObject.parse(HttpClientUtils.get(url + id))));
            departmentService.syncAllDepartment(JSON.toJSONString(dept));
            System.out.println("同步部门详情成功，getDepartmentDetails");
        }catch (Exception e){
            System.out.println("同步部门详情出错，getDepartmentDetails");
            e.printStackTrace();
        }
    }


    /**
     * 由于有递归逻辑，因此要另起一个方法调用递归逻辑，避免出错
     * @author thomas
     * @version 2018-12-07
     * @param access_token
     * @return boolean
     */
    private boolean successSyncRole(String access_token) {
        try {
            syncDingRoles(access_token, 0, 50);

            System.out.println("同步角色成功，syncDingRoles");
            return true;
        } catch (Exception e) {
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
            roleService.sysDingRole(JSONObject.toJSONString(result));
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

    public boolean syncDingUsers (String accessToken){
        try {

            String deptUrl = "https://oapi.dingtalk.com/user/getDeptMember?access_token=" + accessToken + "&deptId=";
            String userUrl = "https://oapi.dingtalk.com/user/get?access_token=" + accessToken + "&userid=";
            List<String> deptIds = departmentService.getAllDepartmentId();
            List<DingApiUser> userJsonObjects = new ArrayList<>();
            for (String deptId : deptIds) {
                Map result = JSONObject.parseObject(HttpClientUtils.get(deptUrl + deptId));
                List<String> userIds = JSONArray.parseArray(result.get("userIds").toString(), String.class);
                //if(!userIds.contains("1559511342-607104135")) continue;
                for (String userId : userIds) {
                    String userJson = HttpClientUtils.get(userUrl+userId);
                    DingApiUser userJsonObject =JSONObject.parseObject(userJson, DingApiUser.class);
                    userJsonObjects.add(userJsonObject);
                }
                //break;
            }
            userService.saveUsersFromApi(userJsonObjects);
            System.out.println("根据userid同步员工详情成功，syncDingUsers");
            return true;
        } catch (Exception e){
            System.out.println("根据userid同步员工详情出错，syncDingUsers");
            e.printStackTrace();
            return false;
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
        return dingDingService.getAccessToken();
    }


    @Test
    public void test1(){
        String id = "01093037676985";
        String url ="https://oapi.dingtalk.com/user/get?access_token="+getDingToken()+"&userid=";
        System.out.println(HttpClientUtils.get(url));
    }
}

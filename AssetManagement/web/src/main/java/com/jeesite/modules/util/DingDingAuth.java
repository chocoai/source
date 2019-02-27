package com.jeesite.modules.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class DingDingAuth {

    @Resource
    private RedisUtil<String, String> redisString;
    @Resource
    private RedisUtil<String, List> redisList;

    @Autowired
    private DingDepartmentService dingDepartmentService;

    public static DingDingAuth redisHelp;

    @Autowired
    private DingUserService dingUserService;

    @PostConstruct
    public void init() {
        redisHelp = this;
        redisHelp.redisString =this.redisString;
        redisHelp.redisList=this.redisList;
    }

    //根据token得到缓存用户信息
    public CardUsers getUserInfo(HttpServletRequest request){
        CardUsers result = new CardUsers();
        String token = request.getHeader("token");
        String userId = redisString.get("dingding_user_" + token);
        UserData loginUser = null;
        if (userId != null && userId.length() > 0){
            String json = redisString.get("dingding_user_" + userId);
            if (json != null && json.length() > 0){
                JSONObject jObject = JSONObject.parseObject(json);
                loginUser = JSONObject.toJavaObject(jObject, UserData.class);
                //如果我是别人的代理人
                setUserDataByHeader(request, "agent", result, result::setAgentUser);
                //如果我是主管
                setUserDataByHeader(request, "boss", result, result::setBossUser);
                //如果我是管理员
                setUserDataByHeader(request, "manager", result, result::setManagerUser);
            }
        }
        result.setCurrentUser(loginUser);
        return result;
    }

    private void setUserDataByHeader(HttpServletRequest request, String headerKey, CardUsers result, Consumer<UserData> func){
        String headerValue = request.getHeader(headerKey);
        if(!StringUtils.isBlank(headerValue)){
            DingUser dingUser = dingUserService.getUserById(headerValue);
            if(dingUser != null){
                UserData userData = new UserData();
                dingUserService.setUserData(dingUser, "",userData);
                List<DingDepartment> dingDepartments = dingDepartmentService.getDingDepartmentByUser(userData.getUserid());
                userData.setDingDepartmentList(dingDepartments);
                result.setData(func, userData);
            }
        }
    }

    //根据token得到缓存用户信息
    public DingUser getMyBoss(String userId){

        DingUser boss = null;
        List<DingUser> dingUserList = redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
        Optional<DingUser> optionalDingUser = dingUserList.stream().filter(a->a.getUserid().equals(userId)).findFirst();
        if(optionalDingUser.isPresent()){   //获取当前用户信息
            DingUser currentUser = optionalDingUser.get();
            List<DingDepartment> lll = currentUser.getDingDepartmentList();
            Optional<DingUser> optionalDingUserBoss = dingUserList.stream().filter(a-> a.getUserid().equals(currentUser.getDirectSuperior())).findFirst();
            if(optionalDingUserBoss.isPresent()){   //获取我的上级信息
                boss = optionalDingUserBoss.get();
            }
        }
        return boss;
    }

}

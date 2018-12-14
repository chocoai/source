package com.jeesite.modules.achievement.card.entity;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.ding.entity.UserData;

import java.util.function.Consumer;

public class CardUsers {

    private UserData currentUser;
    private UserData agentUser;
    private UserData bossUser;
    private UserData managerUser;

    public boolean hasCurrentUser = false;

    public Boolean isManager = false;
    public Boolean isAgent = false;
    public Boolean isBoss = false;
    public Boolean hasOtherUser = false;

    /**
     * 获取代理用户
     * @return 代理用户
     */
    public UserData getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(UserData agentUser) {
        this.agentUser = agentUser;
        this.isAgent = agentUser != null;
        this.hasOtherUser |= this.isAgent;
    }

    /**
     * 获取下属用户
     * @return 下属用户
     */
    public UserData getBossUser() {
        return bossUser;
    }

    public void setBossUser(UserData bossUser) {
        this.bossUser = bossUser;
        this.isBoss = bossUser != null;
        this.hasOtherUser |= this.isBoss;
    }

    /**
     * 获取当前用户
     * @return 当前用户
     */
    public UserData getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserData currentUser) {
        this.currentUser = currentUser;
        this.hasCurrentUser = currentUser != null && !StringUtils.isBlank(currentUser.getUserid());
    }

    public UserData getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(UserData managerUser) {
        this.managerUser = managerUser;
        this.isManager = managerUser != null;
        this.hasOtherUser |= this.isManager;
    }

    public UserData getOtherOrCurrentUser(){
        if(this.isManager){
            return this.getManagerUser();
        }
        else if(this.isAgent){
            return this.getAgentUser();
        }
        else if(this.isBoss){
            return this.getBossUser();
        }
        return getCurrentUser();
    }

    public Boolean noCurrentUser(){
        return !this.hasCurrentUser;
    }

    /**
     * 将搜索条件设置为特殊角色的userid
     * 如果是特殊角色，则不赋值，否则赋值当前用户
     * @param setTo
     * @param loginUserId
     */
    public void setQueryWithCurrentIfSelf(Consumer<String> setTo, String loginUserId){
        if(!hasOtherUser) setTo.accept(loginUserId);
    }

    /**
     * 将搜索条件设置为特殊角色的userid
     * 如果是特殊角色，则赋值requestUserId，否则赋值当前用户
     * @param setTo
     */
    public void setQueryWithRequestUserIdIfNotSelf(Consumer<String> setTo, String requestUserId){
        if(hasOtherUser) setTo.accept(requestUserId);
        else setTo.accept(getCurrentUser().getUserid());
    }

    public void setData(Consumer<UserData> setTo, UserData userData){
        setTo.accept(userData);
    }

}

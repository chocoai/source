package com.jeesite.modules.userroleconfig.entity;

import java.util.List;

public class InterfaceQueryData {
    List<InterfaceQuery> interfaceQuerys;
    UserDataPermission userDataPermission;

    public List<InterfaceQuery> getInterfaceQuerys() {
        return interfaceQuerys;
    }

    public void setInterfaceQuerys(List<InterfaceQuery> interfaceQuerys) {
        this.interfaceQuerys = interfaceQuerys;
    }

    public UserDataPermission getUserDataPermission() {
        return userDataPermission;
    }

    public void setUserDataPermission(UserDataPermission userDataPermission) {
        this.userDataPermission = userDataPermission;
    }
}

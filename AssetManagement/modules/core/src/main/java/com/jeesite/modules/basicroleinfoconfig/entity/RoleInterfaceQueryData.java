package com.jeesite.modules.basicroleinfoconfig.entity;

import com.jeesite.modules.userroleconfig.entity.InterfaceQuery;

import java.util.List;

public class RoleInterfaceQueryData {
    List<InterfaceQuery> interfaceQuerys;
    RoleDataPermission roleDataPermission;

    public List<InterfaceQuery> getInterfaceQuerys() {
        return interfaceQuerys;
    }

    public void setInterfaceQuerys(List<InterfaceQuery> interfaceQuerys) {
        this.interfaceQuerys = interfaceQuerys;
    }

    public RoleDataPermission getRoleDataPermission() {
        return roleDataPermission;
    }
    public void setRoleDataPermission(RoleDataPermission roleDataPermission) {
        this.roleDataPermission = roleDataPermission;
    }
}

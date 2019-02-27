package com.jeesite.modules.dingding;

import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.util.StringUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 钉钉同步实体
 */
public class DingDepartmentApiObject {

    private Integer errcode;
    private String errmsg;
    private Integer id;
    private String name;
    private Integer order;
    private Integer parentid;
    private Boolean createDeptGroup;
    private Boolean autoAddUser;
    private Boolean deptHiding;
    private String deptPermits;
    private String userPermits;
    private Boolean outerDept;
    private String outerPermitDepts;
    private String outerPermitUsers;
    private String orgDeptOwner;
    private String deptManagerUseridList;
    private String sourceIdentifier;


    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
    public Integer getErrcode() {
        return errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public String getErrmsg() {
        return errmsg;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
    public Integer getOrder() {
        return order;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    public Integer getParentid() {
        return parentid;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }
    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }
    public Boolean getAutoAddUser() {
        return autoAddUser;
    }

    public void setDeptHiding(Boolean deptHiding) {
        this.deptHiding = deptHiding;
    }
    public Boolean getDeptHiding() {
        return deptHiding;
    }

    public void setDeptPermits(String deptPermits) {
        this.deptPermits = deptPermits;
    }
    public String getDeptPermits() {
        return deptPermits;
    }

    public void setUserPermits(String userPermits) {
        this.userPermits = userPermits;
    }
    public String getUserPermits() {
        return userPermits;
    }

    public void setOuterDept(Boolean outerDept) {
        this.outerDept = outerDept;
    }
    public Boolean getOuterDept() {
        return outerDept;
    }

    public void setOuterPermitDepts(String outerPermitDepts) {
        this.outerPermitDepts = outerPermitDepts;
    }
    public String getOuterPermitDepts() {
        return outerPermitDepts;
    }

    public void setOuterPermitUsers(String outerPermitUsers) {
        this.outerPermitUsers = outerPermitUsers;
    }
    public String getOuterPermitUsers() {
        return outerPermitUsers;
    }

    public void setOrgDeptOwner(String orgDeptOwner) {
        this.orgDeptOwner = orgDeptOwner;
    }
    public String getOrgDeptOwner() {
        return orgDeptOwner;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
    }
    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }
    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void CopyTo(DingDepartment dept){
        setByJObject(this.getAutoAddUser(), dept::setAutoAddUser);
        setByJObject(this.getCreateDeptGroup(), dept::setCreateDeptGroup);
        setByJObject(this.getDeptHiding(), dept::setDeptHiding);
        setByJObject(this.getId(), dept::setDepartmentId);
        setByJObject(this.getName(), dept::setName);
        setByJObject(this.getOrder(), dept::setOrder);
        setByJObject(this.getParentid() == null ? 0 : this.getParentid(), dept::setParentid);
        setByJObject(this.getDeptPermits(), dept::setDeptPermits);
        setByJObject(this.getUserPermits(), dept::setUserPermits);
        setByJObject(this.getOuterPermitDepts(), dept::setOuterPermitPepts);
        setByJObject(this.getOuterPermitUsers(), dept::setOuterPermitUsers);
        setByJObject(this.getOrgDeptOwner(), dept::setOrgDeptOwner);
        setByJObject(this.getDeptManagerUseridList(), dept::setDeptManagerUseridList);
        setByJObject(this.getSourceIdentifier(), dept::setSourceIdentifier);
        dept.setTreeSort(dept.getOrder() == null ? null : Integer.valueOf(dept.getOrder()));
        dept.setTreeName_(dept.getName());
        if(StringUtils.isBlank(dept.getParentid())) dept.setParentCode("0");
        else dept.setParentCode(dept.getParentid());
        //如果owner为空，则取DeptManagerUseridList第一个用户
        if(dept.getDeptManagerUseridList() == null && !StringUtils.isBlank(this.getOrgDeptOwner())){
            dept.setDeptManagerUseridList(this.getOrgDeptOwner());
        }
        if(dept.getOrgDeptOwner() == null && !StringUtils.isBlank(this.getDeptManagerUseridList())) {
            if(this.getDeptManagerUseridList().contains("|")) {
                String[] managerUserIds = this.getDeptManagerUseridList().split("|");
                if (managerUserIds.length > 0)
                    dept.setOrgDeptOwner(managerUserIds[0]);
            } else {
                dept.setOrgDeptOwner(this.getDeptManagerUseridList());
            }
        }
    }


    public void setByJObject(String value, Consumer<String> setter){
        if(!StringUtils.isEmpty(value)){
            setter.accept(value);
        }
    }
    public void setByJObject(Boolean value, Consumer<String> setter){
        if(value != null)
            setter.accept(value ? "1" : "0");
    }
    public void setByJObject(Integer value, Consumer<String> setter){
        if(value != null)
            setter.accept(value.toString());
    }
}
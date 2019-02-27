package com.jeesite.modules.asset.ding.entity;

import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 根据用户获取部门
 */
@Component
public class DepartmentUtil {

    @Resource
    private RedisUtil<String, List> redisList;

    public static DepartmentUtil department;

    @PostConstruct
    public void init() {
        department = this;
        department.redisList = this.redisList;
    }
    /**
     * 获取部门
     * @param dingUser
     * @param dingUserDepartmentList
     * @param departmentList
     * @return
     */
   public static String getDepartment(DingUser dingUser, List<DingUserDepartment> dingUserDepartmentList, List<DepartmentData> departmentList) {
       List<DingUserDepartment> dingUserDepartmentList1 = dingUserDepartmentList.stream().filter(s ->s.getUserId().equals(dingUser.getUserid())).collect(Collectors.toList());
       String departmentNames = null;
       for (DingUserDepartment dingUserDepartment : dingUserDepartmentList1) {
           DepartmentData department = departmentList.stream().filter(s ->s.getDepartmentId().equals(dingUserDepartment.getDepartmentId())).findFirst().get();
           if (departmentNames != null) {
               departmentNames = departmentNames + " " + department.getName();
           } else {
               departmentNames = department.getName();
           }
       }
       return departmentNames;
   }

    /**
     * 获取部门Ids
     * @param dingUser
     * @param dingUserDepartmentList
     * @return
     */
    public static String getDepartmentIds(DingUser dingUser, List<DingUserDepartment> dingUserDepartmentList) {
        List<DingUserDepartment> dingUserDepartmentList1 = dingUserDepartmentList.stream().filter(s ->s.getUserId().equals(dingUser.getUserid())).collect(Collectors.toList());
        StringBuilder departmentIds = new StringBuilder();
        for (DingUserDepartment dingUserDepartment : dingUserDepartmentList1) {
            departmentIds.append(dingUserDepartment.getDepartmentId());
            departmentIds.append(',');
        }
        int len = departmentIds.length();
        return len > 0 ? departmentIds.substring(0, departmentIds.length() -1):null;
    }

    /**
     * 获取一级部门
     * @param userId
     * @param dingUserDepartmentList
     * @param departmentList
     * @return
     */
    public static DepartmentData getFirstDepartment(String userId, List<DingUserDepartment> dingUserDepartmentList, List<DepartmentData> departmentList) {
        Optional<DingUserDepartment> optionalDingUserDepartment = dingUserDepartmentList.stream().filter(s ->s.getUserId().equals(userId)).findFirst();
        if(optionalDingUserDepartment.isPresent()){
            DingUserDepartment userDepartment = dingUserDepartmentList.stream().filter(s ->s.getUserId().equals(userId)).findFirst().get();
            DepartmentData departmentData = departmentList.stream().filter(s ->s.getDepartmentId().equals(userDepartment.getDepartmentId())).findFirst().get();
            //Map<String, DepartmentData> departmentList1 = departmentList.stream().filter(s-> s.getTreeLevel().equals(1)).collect(Collectors.toMap(DepartmentData::getDepartmentId, DepartmentData::getDepartmentData));

            return getFirstDepartmentFromTree(departmentList, departmentData);
        }
        return null;

    }

    private static DepartmentData getFirstDepartmentFromTree(List<DepartmentData> departmentList, DepartmentData current){
        Optional<DepartmentData> optionalDepartmentData = departmentList.stream().filter(s->s.getDepartmentId().equals(current.getParentId())).findFirst();
        if(!optionalDepartmentData.isPresent()) return null;
        DepartmentData data = optionalDepartmentData.get();
        if(data.getTreeLevel().equals(1))
            return data;
        else
            return getFirstDepartmentFromTree(departmentList,data);
    }

   @SuppressWarnings("unchecked")
   public static AccessDepartMent access () {
       List<DingUserDepartment> dingUserDepartmentList = department.redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
       // 获取缓存中所有部门
       List<DepartmentData> departmentList = department.redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
       // 获取所有用户
       List<DingUser> dingUserList = department.redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
       AccessDepartMent accessDepartMent = new AccessDepartMent();
       accessDepartMent.setDingUserList(dingUserList);
       accessDepartMent.setDingUserDepartmentList(dingUserDepartmentList);
       accessDepartMent.setDepartmentList(departmentList);
       return accessDepartMent;
   }

   public static List<String> getLeaderFromDepartmentIds(List<String> deptIds){
       List<DepartmentData> departmentList = department.redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
       return departmentList.stream().filter(a->deptIds.contains(a.getDepartmentId())).map(a->a.getManagerUser()).collect(Collectors.toList());
   }

    public static void getChildDepartmentIds(String deptId, List<DepartmentData> departmentList, List<String> deptIds){
        if(departmentList == null)
            departmentList = department.redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
        if(departmentList != null){
            List<String> children =  departmentList.stream().filter(a-> a.getParentId().equals(deptId)).map(a->a.getDepartmentId()).collect(Collectors.toList());
            if(children.size() > 0){
                deptIds.addAll(children);
                for(String dept:children){
                    getChildDepartmentIds(dept, departmentList, deptIds);
                }
            }
        }
    }

}

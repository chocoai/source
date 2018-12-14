package com.jeesite.modules.asset.wechat.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.department.entity.DepartmentTok3;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 检查角色是否为供应商
 * @author Jace Xiong
 */
@MyBatisDao
public interface CheckRoleDao {
    @Select("SELECT * FROM `js_sys_user` a\n" +
            "INNER JOIN js_sys_user_role b ON a.user_code = b.user_code and b.role_code='supplier' " +
            "where a.login_code=#{arg0}")
    User checkRole(String userCode);
    @Select("SELECT a.role_name from js_sys_role a LEFT JOIN js_sys_user_role b ON a.role_code=b.role_code WHERE b.user_code=#{username}")
    String getRoleNameByLoginCode(String username);
    @Select("  SELECT a.* from js_sys_role a LEFT JOIN js_sys_user_role b on a.role_code=b.role_code " +
            "LEFT JOIN js_sys_user c ON b.user_code= c.user_code WHERE c.login_code=#{username}")
    List<Role> getRoleByLoginCode(String username);
    @Select("SELECT dict_label from js_sys_dict_data where dict_code=#{dictCode}")
    String getKeyByCode(String dictCode);
//    根据登录code查找部门
    @Select("  SELECT a.* from js_sys_office a LEFT JOIN js_sys_employee b ON a.office_code=b.office_code LEFT JOIN " +
            " js_sys_user c on b.emp_code=c.ref_code where c.login_code=#{loginCode}" )
    Office getOfficeByLoginCode(String loginCode);

    //根据部门查询对应的K3账号密码
    @Select(" SELECT * from js_department_tok3 where department=#{officeCode}" )
    DepartmentTok3 getDepartmentTok3ByOfficeCode(String officeCode);
}

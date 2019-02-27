/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.member.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.member.entity.CrmMemberInfo;
import org.apache.ibatis.annotations.Select;

/**
 * 淘宝会员信息DAO接口
 * @author Albert Feng
 * @version 2019-02-16
 */
@MyBatisDao
public interface CrmMemberInfoDao extends CrudDao<CrmMemberInfo> {
    @Select("select * from crm_member_info where mobile = #{mobile}")
    CrmMemberInfo getMemberInfoByMobile(String mobile);
}
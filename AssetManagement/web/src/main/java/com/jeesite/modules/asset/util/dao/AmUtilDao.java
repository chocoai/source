/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.util.dao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.DictType;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * js_am_seqDAO接口
 * @author czy
 * @version 2018-04-24
 */
@MyBatisDao
public interface AmUtilDao {
	String findDictLabel (String dictLabel, String dictType);
	AmPeriodState getSection(String dataPeriod);       // 查询数据期间
	@Select("SELECT a.* from js_sys_dict_data a LEFT JOIN js_sys_dict_type b ON a.dict_type=b.dict_type WHERE b.dict_type=#{dictType}")
	List<DictData> findDictLabels (String dictType);
	int checkDictValue(String dictValue,String dictType);
	@Select("SELECT a.config_value from js_sys_config a where a.config_key=#{arg0}")
	String getConfigValue (String congfigKey);

}
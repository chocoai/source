/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.util.dict;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.DingDingAuth;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 字典Controller
 * @author Philip
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/dict")
public class DictController extends BaseController {

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getComboData")
	@ResponseBody
	public ReturnInfo getComboData(String dictType, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<DictData> list = DictUtils.getDictList(dictType);
			if(list!=null && list.size() >0){
				//Map<String, String> data = list.stream().collect(Collectors.toList(DictData::getDictCode, DictData::getDictLabel));
				return ReturnDate.successObject(list);
			}
			return ReturnDate.errorObject(200, "没有数据");
		}
		catch (Exception e){
			return ReturnDate.error(400, e.getMessage());
		}

	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getList")
	@ResponseBody
	public List<DictData> getList(String dictType, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<DictData> list = DictUtils.getDictList(dictType);
			return  list;
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getListByString2")
	@ResponseBody
	public ReturnInfo getListByString2(String dictType, String group, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<DictData> list = DictUtils.getDictList(dictType);
			Optional<DictData> optionalParent = list.stream().filter(a->a.getExtend().getExtendS2().equals(group)).findFirst();
			if(optionalParent.isPresent()){
				List<DictData> result = list.stream().filter(a->
						a.getParentCode().equals(optionalParent.get().getDictCode())
								|| (StringUtils.isBlank(a.getExtend().getExtendS2())
										&& a.getDictLabel().contains("观察")
										&& a.getDictLabel().contains(group))).collect(Collectors.toList());
				return ReturnDate.successObject(result);
			}
			return ReturnDate.success();
		}
		catch (Exception e){
			return ReturnDate.error(400, e.getMessage());
		}
	}

}
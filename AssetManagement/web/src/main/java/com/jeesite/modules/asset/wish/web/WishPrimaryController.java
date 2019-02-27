    /**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wish.entity.PrimaryDetail;
import com.jeesite.modules.asset.wish.entity.WishPrimaryDetail;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.sys.utils.ConfigUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.wish.entity.WishPrimary;
import com.jeesite.modules.asset.wish.service.WishPrimaryService;
import sun.dc.pr.PRError;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

	/**
 * 初选提名记录表Controller
 * @author len
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/wish/wishPrimary")
public class WishPrimaryController extends BaseController {

	@Autowired
	private WishPrimaryService wishPrimaryService;
		// 初选入口关闭截止时间
		private static final String PRIMARY = "wish_primary_time";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WishPrimary get(String userId, boolean isNewRecord) {
		return wishPrimaryService.get(userId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("wish:wishPrimary:view")
	@RequestMapping(value = {"list", ""})
	public String list(WishPrimary wishPrimary, Model model) {
		model.addAttribute("wishPrimary", wishPrimary);
		return "asset/wish/wishPrimaryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("wish:wishPrimary:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WishPrimary> listData(WishPrimary wishPrimary, HttpServletRequest request, HttpServletResponse response) {
		Page<WishPrimary> page = wishPrimaryService.findPage(new Page<WishPrimary>(request, response), wishPrimary); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("wish:wishPrimary:view")
	@RequestMapping(value = "form")
	public String form(WishPrimary wishPrimary, Model model) {
		model.addAttribute("wishPrimary", wishPrimary);
		return "asset/wish/wishPrimaryForm";
	}

	@RequiresPermissions("wish:wishPrimary:edit")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(HttpServletResponse response) {
		List<WishPrimaryDetail> reportList = wishPrimaryService.exportData();
		String fileName = "初选提名投票记录.xlsx";
		try (ExcelExport ee = new ExcelExport("初选提名投票记录", WishPrimaryDetail.class)) {
			ee.setDataList(reportList).write(response, fileName);
		}
	}
	/**
	 * 保存初选提名记录表
	 */
	@RequiresPermissions("wish:wishPrimary:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WishPrimary wishPrimary) {
		wishPrimaryService.save(wishPrimary);
		return renderResult(Global.TRUE, text("保存初选提名记录表成功！"));
	}

	/**
	 * 根据userId获取初选提名记录
	 * @param userId
	 * @return
	 */
	@IsFileter(isFile="true")
	@RequestMapping(value = "nominationRecord", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo getNominationRecord(String userId) {
		DingUser dingUser = wishPrimaryService.getDingUser(userId);
		if (dingUser == null) {
			return ReturnDate.error(12001, "不存在该用户");
		}
		// 根据userId查询初选记录表
		WishPrimary wishPrimary = wishPrimaryService.get(userId);
		if (wishPrimary != null) {
			List<PrimaryDetail> primaryDetailList = ListUtils.newArrayList();
			// 如果初选记录的明细行 即被提名人不为空 返回【被提名人】【提名理由】【是否提交成功】
			if (ListUtils.isNotEmpty(wishPrimary.getWishPrimaryDetailList())) {
				for (WishPrimaryDetail wishPrimaryDetail : wishPrimary.getWishPrimaryDetailList()) {
					PrimaryDetail primaryDetail = new PrimaryDetail();
					// 复制明细表数据返回前端实体
					BeanUtils.copyProperties(wishPrimaryDetail, primaryDetail);
					primaryDetail.setUserId(wishPrimaryDetail.getUserId().getUserId());
					primaryDetailList.add(primaryDetail);
				}
				return ReturnDate.success(primaryDetailList);
			} else {
				return ReturnDate.error(12002, "还没有提名记录");
			}
		} else {
			return ReturnDate.error(12003, "还未参与初选投票");
		}
	}
	@Resource
	private RedisUtil<String, List> redisList;

	/**
	 * 初选添加选择人
	 * @param req
	 * @return
	 */
	@IsFileter(isFile="true")
	@ResponseBody
	@RequestMapping(value = "addNomination", method = RequestMethod.POST)
	public ReturnInfo addNomination(@RequestBody String req) {
		// 初选截止时间
		String primaryTime = ConfigUtils.getConfig(PRIMARY).getConfigValue();
		if (DateUtils.parseDate(primaryTime).compareTo(new Date()) < 0) {
			return ReturnDate.error(12003, "初选投票时间已截止");
		}
		JSON json = JSONObject.parseObject(req);
		WishPrimary wishPrimary = JSONObject.toJavaObject(json, WishPrimary.class);
		WishPrimary wishPrimary1 = wishPrimaryService.get(wishPrimary.getUserId());
		if (wishPrimary1 != null && wishPrimary1.getWishPrimaryDetailList().size() >= 5) {
			return ReturnDate.error(12002, "您已经投过票");
		}
		if (ListUtils.isNotEmpty(wishPrimary.getWishPrimaryDetailList())) {
			// 获取缓存中所有部门
			List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
			// 获取部门用户中间表的数据
			List<DingUserDepartment> dingUserDepartmentList = redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
			for (WishPrimaryDetail wishPrimaryDetail : wishPrimary.getWishPrimaryDetailList()) {
				try {
					// 根据userid获取部门中间表中的部门id
					List<DingUserDepartment> dingUserDepartmentList1 = dingUserDepartmentList.stream().filter(s ->s.getUserId().equals(wishPrimaryDetail.getVotersUserId())).collect(Collectors.toList());
					int i = 0;
					for (DingUserDepartment dingUserDepartment : dingUserDepartmentList1) {
						// 根据部门id去部门中获取对应的部门名
						DepartmentData departmentData = departmentList.stream().filter(s ->s.getDepartmentId().equals(dingUserDepartment.getDepartmentId())).findFirst().get();
						if (i == 0) {
							wishPrimaryDetail.setDepartmentId(departmentData.getDepartmentId());
							wishPrimaryDetail.setDepartment(departmentData.getName());
						} else {
							wishPrimaryDetail.setDepartmentId(wishPrimaryDetail.getDepartmentId() + "," + departmentData.getDepartmentId());
							wishPrimaryDetail.setDepartment(wishPrimaryDetail.getDepartment() + " " + departmentData.getName());
						}
						i++;
					}
				}catch (NoSuchElementException e) {

				}
			    wishPrimaryDetail.setUserId(wishPrimary);
				// 创建时间
				wishPrimaryDetail.setCreateTime(new Date());
			}
		} else {
			return ReturnDate.error(12001, "请输入选择人再提交");
		}
		wishPrimary.setCreateTime(new Date());
		wishPrimaryService.saveData(wishPrimary);
		return ReturnDate.success();
	}
}
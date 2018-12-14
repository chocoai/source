/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.order.entity.OrderApply;
import com.jeesite.modules.asset.order.service.OrderApplyService;

import java.util.List;

/**
 * 订单变更申请表Controller
 * @author czy
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderApply")
public class OrderApplyController extends BaseController {
	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;  //测试库
	@Autowired
	private OrderApplyService orderApplyService;
	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 单据前缀
	 */
	private final String PERFIX = "BGSQ";
	@Autowired
	private K3connection k3connection;
	/**
	 * k3变更申请表单
	 */
	private final String FORMID = "YF_Sal_DealerOrderChange";

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public OrderApply get(String documentCode, boolean isNewRecord) {
		return orderApplyService.get(documentCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:orderApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderApply orderApply, Model model) {
		model.addAttribute("orderApply", orderApply);
		return "asset/order/orderApplyList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:orderApply:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderApply> listData(OrderApply orderApply, HttpServletRequest request, HttpServletResponse response) {
		Page<OrderApply> page = orderApplyService.findPage(new Page<OrderApply>(request, response), orderApply);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:orderApply:view")
	@RequestMapping(value = "form")
	public String form(OrderApply orderApply, Model model) {
		if (orderApply.getIsNewRecord()) {
			orderApply.setDocumentCode(amSeqService.getApplyCode(PERFIX));
			orderApply.setDocumentStatus("创建");
		}
		if ("确认".equals(orderApply.getDocumentStatus())){
			orderApply.setConfirm(true);
		} else {
			orderApply.setConfirm(false);
		}
		model.addAttribute("orderApply", orderApply);
		return "asset/order/orderApplyForm";
	}

	/**
	 * 保存订单变更申请表
	 */
	@RequiresPermissions("order:orderApply:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated OrderApply orderApply, String flag) {
		String salesOrder = orderApply.getSalesOrder();						// 销售订单
//		List<OrderApply> orderApplyList = orderApplyService.getSalesOrder(salesOrder);
//			if (orderApplyList != null && orderApplyList.size() > 0) {
//			for (OrderApply orderApply1 : orderApplyList) {
//				if (!orderApply.getDocumentCode().equals(orderApply1.getDocumentCode())) {
//					return renderResult(Global.FALSE, "存在相同的销售订单");
//				}
//			}
//		}
		if (orderApply.getIsNewRecord()) {
			orderApply.setDocumentCode(amSeqService.getOrderApply(PERFIX));
			orderApply.setApplicant(UserUtils.getUser().getUserName());		// 申请人
//			orderApply.setApplicant(EmpUtils.getOffice().getOfficeName());	// 申请人部门名称
			orderApply.setOfficeCode(EmpUtils.getOffice().getOfficeCode());	// 部门编码
			orderApply.setApplyDate(DateUtils.getDateTime());				// 申请时间
		} else {
			if (orderApply.getOfficeCode() == null || "".equals(orderApply.getOfficeCode())) {
				List<User> userList = orderApplyService.getUser(orderApply.getApplicant());
				if (userList != null && userList.size() > 0) {
					for (User user : userList) {
						String userCode = user.getUserCode();
						if ("employee".equals(user.getUserType())) {
							String officeCode = orderApplyService.getEmp(userCode);
							orderApply.setOfficeCode(officeCode);
						}
					}
				}
			}
		}
		if ("1".equals(flag)) {
			orderApply.setConfirmBy(UserUtils.getUser().getUserName());		// 确认人
			orderApply.setConfirmDate(DateUtils.getDateTime());
			orderApply.setDocumentStatus("确认");
			boolean isLogin= k3connection.getConnection();
			if (!isLogin){
				return renderResult(Global.FALSE, "k3登录失败");
			}
			String applicant = orderApply.getApplicant();					// 申请人
			String applyDate = orderApply.getApplyDate();					// 申请时间
			applyDate = applyDate.replace(" ", "T");
			String cancelProduct = orderApply.getCancelProduct();			// 取消产品
			String addProduct = orderApply.getAddProduct();					// 新增产品
			String modifyCause = orderApply.getModifyCause();				// 变更原因
			String param = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\"," +
					"\"Model\":{\"F_YF_Applicant\":\""+applicant+"\",\"F_YF_ApplyTime\":\""+applyDate+"\",\"F_YF_DealerOrderNo\":\""+salesOrder+"\",\"F_YF_ChangeReason\":\""+modifyCause+"\"," +
					"\"F_YF_AddToMaterial\":\""+addProduct+"\",\"F_YF_ChangeMaterial\":\""+cancelProduct+"\"}}";
			try{
				StringBuffer buffer = InvokeHelper.Save(FORMID, param,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return renderResult(Global.FALSE, "保存失败");
				}
			} catch (Exception e) {
				return renderResult(Global.FALSE, "保存失败");
			}

		}
		orderApplyService.save(orderApply);
		return renderResult(Global.TRUE, "保存订单变更申请表成功！");
	}
	
	/**
	 * 删除订单变更申请表
	 */
	@RequiresPermissions("order:orderApply:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(OrderApply orderApply) {
		if (!"创建".equals(orderApply.getDocumentStatus())) {
			return renderResult(Global.TRUE, "只可删除创建状态的订单");
		}
		orderApplyService.delete(orderApply);
		return renderResult(Global.TRUE, "删除订单变更申请单成功！");
	}

	/**
	 * 获取保存信息
	 * @param buffer
	 * @return
	 */
	public boolean isSuccess (StringBuffer buffer) {
		JSONObject json = JSON.parseObject(buffer.toString());
		String responseStatus = json.getJSONObject("Result").get("ResponseStatus").toString();
		JSONObject json1 = JSON.parseObject(responseStatus);
		boolean isSuccess = Boolean.parseBoolean(json1.get("IsSuccess").toString());
		return isSuccess;
	}
}
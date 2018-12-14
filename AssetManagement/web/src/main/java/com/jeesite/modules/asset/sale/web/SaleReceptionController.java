/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.sale.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;


import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.EmpUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.sale.entity.SaleReception;
import com.jeesite.modules.asset.sale.service.SaleReceptionService;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 客户接待表Controller
 * @author Scarlett
 * @version 2018-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sale/saleReception")
public class SaleReceptionController extends BaseController {

	@Autowired
	private SaleReceptionService saleReceptionService;
	@Autowired
	private AmUtilService amUtilService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SaleReception get(String receptionCode, boolean isNewRecord) {
		return saleReceptionService.get(receptionCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sale:saleReception:view")
	@RequestMapping(value = {"list", ""})
	public String list(SaleReception saleReception, Model model) {
		model.addAttribute("saleReception", saleReception);
		return "asset/sale/saleReceptionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sale:saleReception:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SaleReception> listData(SaleReception saleReception, HttpServletRequest request, HttpServletResponse response) {
		Page<SaleReception> page= saleReceptionService.findPage(new Page<SaleReception>(request, response), saleReception);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sale:saleReception:view")
	@RequestMapping(value = "form")
	public String form(SaleReception saleReception, Model model) {
		model.addAttribute("saleReception", saleReception);
		return "asset/sale/saleReceptionForm";
	}

	/**
	 * 保存客户接待表
	 */
	@RequiresPermissions("sale:saleReception:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SaleReception saleReception) {
		saleReceptionService.save(saleReception);
		return renderResult(Global.TRUE, "保存客户接待表成功！");
	}
	
	/**
	 * 删除客户接待表
	 */
	@RequiresPermissions("sale:saleReception:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SaleReception saleReception) {
		saleReceptionService.delete(saleReception);
		return renderResult(Global.TRUE, "删除客户接待表成功！");
	}
	/**
	 * 查询接待客户接口
	 * 接待ID，客户、性别、移动电话，客户来源，家具需求、接待日期、购买状态、备注，接待状态；
	 */
	@RequiresPermissions("sale:saleReception:view")
	@RequestMapping(value = "outer/queryData")
	@ResponseBody
	public ReturnInfo querySaleReceiption(HttpServletRequest request, @RequestBody String userCode){
		try {
			JSONObject  jsonObject1=JSONObject.fromObject(userCode);
			userCode=jsonObject1.getString("userCode");
		} catch (Exception e) {
			return ReturnDate.error(10002,"参数错误，查询失败！");
		}
		if(userCode.trim().length()<1 ||userCode.length()>=100){
			return ReturnDate.error(10001,"导购员长度应为100字以内的非空字符！");
		}

			SaleReception saleReception=new SaleReception();
			saleReception.setLoginCode(userCode);
			List<SaleReception> list=saleReceptionService.findList(saleReception);
			if(list!=null &&list.size()>0){
				JSONArray jsonArray=new JSONArray();
				JSONObject jsonObject=null;
				SaleReception saleReception1=null;
				for(int i=0;i<list.size();i++){
					saleReception1=list.get(i);
					jsonObject=new JSONObject();
					//客户、性别、移动电话，客户来源，家具需求、接待日期、购买状态、备注；
					jsonObject.put("receptionCode",saleReception1.getReceptionCode());
					jsonObject.put("customer",saleReception1.getCustomer());
					jsonObject.put("gender",saleReception1.getGender());
					jsonObject.put("phonenum",saleReception1.getPhonenum());
					jsonObject.put("customerType",saleReception1.getCustomerType());
					String demands=saleReception1.getDemands();
					jsonObject.put("demands",demands);
					jsonObject.put("createDate",DateUtils.formatDate(saleReception1.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
					jsonObject.put("buyingStatus",saleReception1.getBuyingStatus());
					String remarks=saleReception1.getRemarks();
					jsonObject.put("remarks",remarks);
					jsonObject.put("receptionStatus",saleReception1.getReceptionStatus());
					jsonArray.add(jsonObject);
				}
				return ReturnDate.success(jsonArray);
			}else{
				return ReturnDate.error(10000,"当前导购员暂无客户接待信息！");
			}



	}
	/**
	 * 新增接待客户接口
	 */
	@RequiresPermissions("sale:saleReception:edit")
	@PostMapping(value = "outer/createData")
	@ResponseBody
	public ReturnInfo createSaleReceiption(@RequestBody String info){
		SaleReception saleReception= null;
		//新增接待点击确认时，校验 客户简称，性别，客户来源必填
		try {
			JSONObject jsonObject=JSONObject.fromObject(info);
			//前台传token ,客户，性别，移动电话，客户来源，家具需求，接待id；
			saleReception = new SaleReception();
			if(jsonObject.containsKey("userCode")){
				String userCode=jsonObject.getString("userCode");
				if(userCode.trim().length()<=0 ||userCode.length()>=100){
					return ReturnDate.error(400,"导购员长度应在100字符以内！");
				}
				saleReception.setLoginCode(userCode);
			}else{
				return ReturnDate.error(400,"导购员信息为空，无法保存接待客户信息");
			}
			if(jsonObject.containsKey("receptionCode")){
				String receptionCode=jsonObject.getString("receptionCode");
				SaleReception saleReception1=new SaleReception();
				saleReception1.setReceptionCode(receptionCode);
				saleReception1=saleReceptionService.get(saleReception1);
				if(saleReception1!=null){
					return ReturnDate.error(400,"接待id不可重复，新增接待客户记录失败");
				}
				if(receptionCode.length()>=64){
					return ReturnDate.error(400,"接待id应在64字符以内！");
				}
				saleReception.setReceptionCode(receptionCode);
			}else{
				return ReturnDate.error(400,"接待id为空，无法保存接待客户信息！");
			}
			if(jsonObject.containsKey("customer")){
				String customer=jsonObject.getString("customer");
				if("".equals(customer.trim()) ||customer.length()>=64){
					return ReturnDate.error(400,"客户长度应为64字符以内的非空字符！");
				}
				saleReception.setCustomer(customer);
			}else{
				return ReturnDate.error(400,"客户信息为空，提交失败!");
			}
			String gender="1";
			if(jsonObject.containsKey("gender")){
				gender=jsonObject.getString("gender");
				int num=amUtilService.checkDictValue(gender,"sys_user_sex");
				if(num<=0){
					return ReturnDate.error(400,"性别信息匹配不了后台管理信息，请正确填写!");
				}
			}else {
				return ReturnDate.error(400,"请填写性别信息!");
			}
			saleReception.setGender(gender);
			if(jsonObject.containsKey("phonenum")){
				String phonenum=jsonObject.getString("phonenum");
				if(!"".equals(phonenum)) {
					if (phonenum.length()!=11) {
						return ReturnDate.error(400, "请填写正确的移动电话!");
					}
				}
				saleReception.setPhonenum(phonenum);
			}
			String customerType="0";
			if(jsonObject.containsKey("customerType")){
				customerType=jsonObject.getString("customerType");
				int num=amUtilService.checkDictValue(customerType,"customer_type");
				if(num<=0){
					return ReturnDate.error(400,"客户来源信息匹配不了后台管理信息，请正确填写!");
				}
			}else{
				return ReturnDate.error(400,"必须填写客户来源信息!");
			}
			saleReception.setCustomerType(customerType);
			if(jsonObject.containsKey("demands")) {
				String demands = jsonObject.getString("demands");
				if(demands.length()>=225){
					return ReturnDate.error(400,"家具需求长度应在225字数以内!");
				}
				saleReception.setDemands(demands);
			}
		} catch (Exception e) {
			return ReturnDate.error(400,"信息错误，无法新增接待客户记录！");
		}
		saleReception.setReceptionStatus("0");
        saleReception.setIsNewRecord(true);
        saleReception.setBuyingStatus("0");
		saleReceptionService.save(saleReception);
		return ReturnDate.success();
	}
	/**
	 * 编辑接待客户接口
	 */
	@RequiresPermissions("sale:saleReception:edit")
	@PostMapping(value = "outer/editData")
	@ResponseBody
	public ReturnInfo editSaleReceiption(@RequestBody String info){
		//接待ID，客户，性别，移动电话，客户来源，家具需求，购买状态，备注
		SaleReception saleReception= null;
		String remarks= null;
		try {
			JSONObject jsonObject=JSONObject.fromObject(info);
			saleReception = new SaleReception();
			String receptionCode=jsonObject.getString("receptionCode");
			if(receptionCode.length()<=0 ||receptionCode.length()>=64){
				return ReturnDate.error(400,"接待id应为64位以内的非空字符！");
			}
			saleReception.setReceptionCode(receptionCode);
			String customer=jsonObject.getString("customer");
			if(customer.length()<=0 ||customer.length()>=64){
				return ReturnDate.error(400,"导购员长度应为64位以内的非空字符！");
			}
			saleReception.setCustomer(customer);
			String gender=jsonObject.getString("gender");
			int num=amUtilService.checkDictValue(gender,"sys_user_sex");
			if(num<=0){
				return ReturnDate.error(400,"性别信息无法与后台管理信息匹配，请正确填写！");
			}
			saleReception.setGender(gender);
			String phonenum=jsonObject.getString("phonenum");
			if(!"".equals(phonenum) &&phonenum.length()!=11){
				return ReturnDate.error(400,"请填写正确的移动电话！");
			}
			saleReception.setPhonenum(phonenum);
			String customerType=jsonObject.getString("customerType");
			int num1=amUtilService.checkDictValue(customerType,"customer_type");
			if(num1<=0){
				return ReturnDate.error(400,"客户来源信息匹配不了后台管理信息，请正确填写!");
			}
			saleReception.setCustomerType(customerType);
			String demands=jsonObject.getString("demands");
			if(demands.length()>=225){
				return ReturnDate.error(400,"家具需求长度应在225字符以内！");
			}
			saleReception.setDemands(demands);
			String buyingStatus=jsonObject.getString("buyingStatus");
			int num2=amUtilService.checkDictValue(buyingStatus,"buying_status");
			if(num2<=0){
				return ReturnDate.error(400,"购买状态匹配不了后台管理信息，请正确填写!");
			}
			saleReception.setBuyingStatus(buyingStatus);
			remarks = jsonObject.getString("remarks");
			if(remarks.length()>=1000){
				return ReturnDate.error(400,"备注应在1000字以内！");
			}
		} catch (Exception e) {
			return ReturnDate.error(400,"信息错误，编辑接待客户失败！");
		}
		saleReception.setRemarks(remarks);
        saleReception.setIsNewRecord(false);
        saleReceptionService.save(saleReception);
		return ReturnDate.success();
	}
	/**
	 * 更新接待客户状态接口
	 */
	@RequiresPermissions("sale:saleReception:edit")
	@PostMapping(value = "outer/updateStatus")
	@ResponseBody
	public ReturnInfo updateStatus(@RequestBody String info){
    //前台传token ,接待ID，购买状态，备注；
		SaleReception saleReception= null;
		try {
			JSONObject jsonObject=JSONObject.fromObject(info);
			saleReception = new SaleReception();
			String receptionCode=jsonObject.getString("receptionCode");
			if(receptionCode.length()<=0 ||receptionCode.length()>=64){
				return ReturnDate.error(400,"接待id应为64位以内的非空字符！");

			}
			saleReception.setReceptionCode(receptionCode);
			String buyingStatus=jsonObject.getString("buyingStatus");
			int num2=amUtilService.checkDictValue(buyingStatus,"buying_status");
			if(num2<=0){
				return ReturnDate.error(400,"购买状态匹配不了后台管理信息，请正确填写!");
			}
			saleReception.setBuyingStatus(buyingStatus);
			String remarks=jsonObject.getString("remarks");
			if(remarks.length()>=1000){
				return ReturnDate.error(400,"备注应在1000字以内！");
			}
			saleReception.setReceptionStatus("1");
			saleReception.setRemarks(remarks);
			saleReception.setEndDate(new Date());
			saleReception.setIsNewRecord(false);
		} catch (Exception e) {
			return ReturnDate.error(400,"信息错误，无法更新接待客户状态！");
		}
		saleReceptionService.save(saleReception);
		return ReturnDate.success();
	}


}
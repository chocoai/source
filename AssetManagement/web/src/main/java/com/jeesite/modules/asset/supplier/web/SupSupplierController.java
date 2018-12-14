/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.web;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.supplier.entity.*;
import com.jeesite.modules.asset.supplier.service.SupSupplierQualificationsService;
import com.jeesite.modules.asset.supplier.service.SupSupplierService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 供应商资料Controller
 * @author Scarlett
 * @version 2018-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/supplier/supSupplier")
public class SupSupplierController extends BaseController {
	//private static final String CK_PER_FIX = "Suppllier";

	@Autowired
	private SupSupplierService supSupplierService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private K3connection k3connection;
	@Value("${RM_PREFIX_URL}")
	private  String RM_PREFIX_URL;
	@Value("${POST_K3ClOUDRL}")
	private  String POST_K3ClOUDRL;
	@Autowired
	private SupSupplierQualificationsService supService;
	@Value("${file.baseDir}")
	private String  baseDir;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SupSupplier get(String supplierCode, boolean isNewRecord) {
		return supSupplierService.get(supplierCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("supplier:supSupplier:view")
	@RequestMapping(value = {"list", ""})
	public String list(SupSupplier supSupplier, Model model) {
		model.addAttribute("supSupplier", supSupplier);
		return "asset/supplier/supSupplierList";
	}
	/**
	 * 删除上传的报告文件
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value ="deleteReport")
    public String deleteReport(String deleteRelativePath){
		if(deleteRelativePath!=null) {
			List<String> keys = new ArrayList<String>();
			keys.add(deleteRelativePath);
			amUtilService.deletePicAli(keys);
		}

		return renderResult(Global.TRUE, "成功删除报告文件。");
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("supplier:supSupplier:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SupSupplier> listData(SupSupplier supSupplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SupSupplier> page = supSupplierService.findPage(new Page<SupSupplier>(request, response), supSupplier);
		return page;
	}

	/**
	 * 查看编辑表单，将资质文件信息传递给前端
	 */
	@RequiresPermissions("supplier:supSupplier:view")
	@RequestMapping(value = "form")
	public String form(SupSupplier supSupplier, Model model) {
		String supplierStatusName= amUtilService.findDictLabel(supSupplier.getSupplierStatus(),"supplier_status");
		String businessTypeName= amUtilService.findDictLabel(supSupplier.getBusinessType(),"business_type");
		String companyPropertyName= amUtilService.findDictLabel(supSupplier.getCompanyProperty(),"company_property");
		String businessScopeName= amUtilService.findDictLabel(supSupplier.getBusinessScope(),"business_scope");
		supSupplier.setSupplierStatusName(supplierStatusName);
		supSupplier.setBusinessTypeName(businessTypeName);
		supSupplier.setCompanyPropertyName(companyPropertyName);
		supSupplier.setBusinessScopeName(businessScopeName);
		model.addAttribute("supSupplier", supSupplier);
		if(!supSupplier.getIsNewRecord()) {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			SupSupplierQualifications qual=null;
			List<SupSupplierQualifications> qualificationsList =supService.findBySupSupplierCode(supSupplier.getSupplierCode());
			if (qualificationsList != null && qualificationsList.size() > 0) {
				for (int i = 0; i < qualificationsList.size(); i++) {
					qual=qualificationsList.get(i);
					jsonObject = new JSONObject();
					jsonObject.put("qualificationCode", qualificationsList.get(i).getQualificationCode());
					jsonObject.put("supplierCode",supSupplier.getSupplierCode());
					jsonObject.put("typeName", qualificationsList.get(i).getTypeName());
					jsonObject.put("profileSurfix", qualificationsList.get(i).getProfileSurfix());
					jsonObject.put("savePath", qualificationsList.get(i).getSavePath());
					//阿里云图片相对路径
					//jsonObject.put("qualificationName",qualificationsList.get(i).getQualificationName());
					String effectiveDate = DateUtils.formatDate(qualificationsList.get(i).getEffectiveDate(), "yyyy-MM-dd HH:mm:ss");
					String expireDate = DateUtils.formatDate(qualificationsList.get(i).getExpireDate(), "yyyy-MM-dd HH:mm:ss");
					if(effectiveDate==null ||"".equals(effectiveDate)){
						jsonObject.put("effectiveDate","");
					}else{
						jsonObject.put("effectiveDate", effectiveDate);
					}
					if(expireDate==null ||"".equals(expireDate)){
						jsonObject.put("expireDate","");
					}else{
						jsonObject.put("expireDate", expireDate);
					}
					jsonArray.add(jsonObject.toString());
				}
				model.addAttribute("qualificationsInfo", jsonArray.toString());
			}else {
				model.addAttribute("qualificationsInfo", "");
			}
		}else{
			supSupplier.setSupplierCode(UUID.randomUUID().toString());
			model.addAttribute("qualificationsInfo", "");
		}

		return "asset/supplier/supSupplierForm";
	}

	/**
	 * 保存供应商资料
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SupSupplier supSupplier, String supstatus) {
		if(supstatus.equals("1")){
			if(supSupplier.getWritten().equals("是")){
				return renderResult(Global.FALSE, "供应商信息已写入K3,无法重新审核！");
			}
		}
		//supSupplierService.save(supSupplier);
		Double score=supSupplier.getScore();
		if(score<0 ||score>100){
			return renderResult(Global.FALSE, "综合评分应为0~100的数值");
		}
		String abbreviationName=supSupplier.getAbbreviationName();
		if(null==abbreviationName ||"".equals(abbreviationName)){
			return renderResult(Global.FALSE, "请填写K3名称");
		}
		if(supstatus!=null &&!"0".equals(supstatus)) {
			supSupplier.setSupplierStatus(supstatus);
		}
		supSupplierService.savePatirtialInfo(supSupplier);
		return renderResult(Global.TRUE, "保存供应商资料成功！");
	}

	/**
	 * PC端保存阿里云图片路径和图片信息
	 * @param profileInfo
	 * @return
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@PostMapping(value = "saveProfiles")
	@ResponseBody
	public String saveProfiles(@RequestBody String profileInfo){
		supService.saveProfiles(profileInfo);
		return renderResult(Global.TRUE, "成功保存图片！");
	}

	/**
	 * 后台删除资质文件方法
	 * @param info
	 * @return
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@PostMapping(value = "deleteFile")
	@ResponseBody
	public String deleteFile(@RequestBody String info){
		JSONObject jsonObject=JSONObject.fromObject(info);
		String qualificationCode=jsonObject.getString("qualificationCode");
		String message="";
		if(qualificationCode!= null || !"".equals(qualificationCode)){
			String str[]=qualificationCode.split(",");
			SupSupplierQualifications qual=null;
			List<String> keys=new ArrayList<String>();
			for(int i = 0; i < str.length; i++){
				qual=supService.findByQualificationCode(str[i]);
				if(qual!=null){
					try {
						String path=qual.getQualificationName();
						if(null!=path &&!"".equals(path)) {
							keys.add(path);
						}
						supService.deleteByQualificationCode(str[i]);
					} catch (Exception e) {
						message=message+str[i]+",";
					}
				}
			}
			try {
//				if(null!=keys &&keys.size()>0) {
				amUtilService.deletePicAli(keys);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(!"".equals(message)){
				return renderResult(Global.FALSE, "图片"+message+"删除失败！");
			}
			return renderResult(Global.TRUE, "成功删除图片！");

		}else{
			return renderResult(Global.FALSE, "没有需要删除的图片！");
		}
	}

	/**
	 * 删除供应商资料
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SupSupplier supSupplier, String supplierCode) {
		if (supplierCode != null || !"".equals(supplierCode)) {
			Boolean falg= true;
			try {
				falg = supSupplierService.deleteData(supSupplier,supplierCode);
			} catch (Exception e) {
				return renderResult(Global.FALSE, "删除供应商资料失败！");
			}
			if(falg){
				return renderResult(Global.TRUE, "删除供应商资料成功(注：已审核的供应商资料未删除)");
			}
			return renderResult(Global.FALSE, "删除供应商资料失败！");
		} else {
			return renderResult(Global.FALSE, "没有可删除的供应商资料！");
		}
	}

	/**
	 * 用get请求导出Excel，拼接请求参数实现按查询条件进行导出；
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value = "export")
	public void  exportFile(SupSupplier supSupplier,HttpServletResponse response){
		List<SupSupplier> list=supSupplierService.findList(supSupplier);
		if(list!=null &&list.size()>0){
			for(int i=0;i<list.size();i++){
				SupSupplier supSupplier1=list.get(i);
				supSupplier1.setCompanyProperty(amUtilService.findDictLabel(supSupplier1.getCompanyProperty(),"company_property"));
				supSupplier1.setBusinessScope(amUtilService.findDictLabel(supSupplier1.getBusinessScope(),"business_scope"));
				supSupplier1.setBusinessType(amUtilService.findDictLabel(supSupplier1.getBusinessType(),"business_type"));
				supSupplier1.setSupplierStatus(amUtilService.findDictLabel(supSupplier1.getSupplierStatus(),"supplier_status"));
			}
		}
		String fileName = "供应商信息" + DateUtils.getDate("yyyyMMddHHmmss") +(int)(Math.random()*9000+1000)+ ".xlsx";
		try(ExcelExport ee = new ExcelExport("供应商信息", SupSupplier.class)){
			ee.setDataList(list).write(response, fileName);
		}

	}
	/**
	 *保存供应商信息前进行文件信息修改方法
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value = "updateQualifications", method = RequestMethod.POST)
	public void  updateProfiles(@RequestBody String updateInfo) {
		JSONArray jsonArray=JSONArray.fromObject(updateInfo);
		JSONObject jsonObject=null;
		SupSupplierQualifications qualifications=null;
		if(jsonArray!=null &&jsonArray.size()>0){
			for(int i=0;i<jsonArray.size();i++){
				jsonObject=jsonArray.getJSONObject(i);
				String qualificationCode=jsonObject.getString("qualificationCode");
				Date effectiveDate=null;
				Date expireDate=null;
				String isNeverExpired="";
				effectiveDate=DateUtils.parseDate(jsonObject.getString("effectiveDate"));
				String expireDate1=jsonObject.getString("expireDate");
				if("".equals(expireDate1) ||" 00:00:00".equals(expireDate1)){
					expireDate=DateUtils.parseDate("9999-12-12 00:00:00");
					isNeverExpired="是";
				}else{
					expireDate=DateUtils.parseDate(expireDate1);
					isNeverExpired="否";
				}
				supService.updateDateInfo(effectiveDate,expireDate,qualificationCode,isNeverExpired);

			}
		}
	}

	/**
	 * 后台检查公司名称唯一性方法
	 * @param companyNameInfo
	 * @return
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value = "checkCompanyName", method = RequestMethod.POST)
	@ResponseBody
	public String checkCompanyName(@RequestBody String companyNameInfo) {
		JSONObject jsonObject= null;
		try {
			jsonObject = JSONObject.fromObject(companyNameInfo);
		} catch (Exception e) {
			return renderResult(Global.FALSE, "数据格式错误，无法解析");
		}
		String supplierCode=jsonObject.getString("supplierCode");
		String companyName=jsonObject.getString("companyName");
		SupSupplier supSupplier=new SupSupplier();
		supSupplier.setSupplierCode(supplierCode);
		supSupplier=supSupplierService.get(supSupplier);
		if(supSupplier==null){
			if(supSupplierService.findSupSupplier(companyName)>0){
				return renderResult(Global.FALSE, "你企业的资料已经成功提交过，无需重复提交，感谢你的支持！");
			}
		}else{
			if(!supSupplier.getCompanyName().equals(companyName)){
				if(supSupplierService.findSupSupplier(companyName)>0){
					return renderResult(Global.FALSE, "你企业的资料已经成功提交过，无需重复提交，感谢你的支持！");
				}
			}
		}
		return renderResult(Global.TRUE, "");
	}

	/**
	 * H5页面保存供应商信息接口
	 * @param request
	 * @param response
	 * @param supplierInfo
	 * @return
	 */
	@PostMapping(value = "saveInfo/saveApi")
	@ResponseBody
	public String saveSupplierInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String supplierInfo) {
		JSONObject jsonObject=null;
		try{
			jsonObject=JSONObject.fromObject(supplierInfo);
		}catch (JSONException e){
			return renderResult(Global.FALSE, "数据格式错误，无法解析！");
		}
		String companyName= null;
		try {
			companyName = jsonObject.getString("companyName");
		} catch (JSONException e) {
			return renderResult(Global.FALSE, "数据错误，无法解析公司名称");
		}
		if("".equals(companyName.trim()) ||companyName.length()>64){
			return renderResult(Global.FALSE, "请输入64个字符以内的公司名称");
		}
		if(supSupplierService.findSupSupplier(companyName)>0){
			return renderResult(Global.FALSE, "你企业的资料已经成功提交过，无需重复提交，感谢你的支持！");
		}
		String companyOnlineadd= null;
		String dateStartup= null;
		String companyProperty= null;
		String registeredCapital= null;
		String taxNumber= null;
		String space= null;
		String monthProduction= null;
		String employees= null;
		String monthSurplusProduction= null;
		String yearTurnover= null;
		String businessType= null;
		String businessScope= null;
		String companyIntroduction= null;
		String companyTel= null;
		String companyFax= null;
		String companyPostcode= null;
		String country= null;
		String province= null;
		String city= null;
		String fullAddress= null;
		try {
			companyOnlineadd = jsonObject.getString("companyOnlineadd");
			dateStartup = jsonObject.getString("dateStartup");
			companyProperty = jsonObject.getString("companyProperty");
			registeredCapital = jsonObject.getString("registeredCapital");
			taxNumber = jsonObject.getString("taxNumber");
			space = jsonObject.getString("space");
			monthProduction = jsonObject.getString("monthProduction");
			employees = jsonObject.getString("employees");
			monthSurplusProduction = jsonObject.getString("monthSurplusProduction");
			yearTurnover = jsonObject.getString("yearTurnover");
			businessType = jsonObject.getString("businessType");
			businessScope = jsonObject.getString("businessScope");
			companyIntroduction = jsonObject.getString("companyIntroduction");
			companyTel = jsonObject.getString("companyTel");
			companyFax = jsonObject.getString("companyFax");
			companyPostcode = jsonObject.getString("companyPostcode");
			country = jsonObject.getString("country");
			province = jsonObject.getString("province");
			city = jsonObject.getString("city");
			fullAddress = jsonObject.getString("fullAddress");
		} catch (JSONException e) {
			return renderResult(Global.FALSE, "数据错误，无法解析公司基本信息");
		}
		SupSupplier supSupplier=new SupSupplier();
		boolean flag=StringUtils.isNoneBlank(companyName,dateStartup,companyProperty,registeredCapital,
				taxNumber,space,monthProduction,employees,monthSurplusProduction,yearTurnover,businessType,
				businessScope,companyTel,companyFax,companyPostcode,country,province,city,fullAddress);
		if(!flag){
			return renderResult(Global.FALSE, "请完善供应商基本资料后再提交！");
		}
		if(registeredCapital.length()>18 ||!isNumeric(registeredCapital)){
			return renderResult(Global.FALSE, "注册资本应为18位以内的数字！");
		}
		if(monthSurplusProduction.length()>18 ||!isNumeric(monthSurplusProduction)){
			return renderResult(Global.FALSE, "月富余能力应为18位以内的数字！");
		}
		if(employees.length()>18 ||!isNumeric(employees)){
			return renderResult(Global.FALSE, "员工数量应为18位以内的数字！");
		}
		if(monthProduction.length()>18 ||!isNumeric(monthProduction)){
			return renderResult(Global.FALSE, "月生产能力应为18位以内的数字！");
		}
		if(yearTurnover.length()>18 ||!isNumeric(yearTurnover)){
			return renderResult(Global.FALSE, "年营业额应为18位以内的数字！");
		}
		if(space.length()>18 ||!isNumeric(space)){
			return renderResult(Global.FALSE, "占地面积应为18位以内的数字！");
		}
		if(!"".equals(companyOnlineadd) &&companyOnlineadd.length()>99){
			return renderResult(Global.FALSE, "公司网址长度不能超过 100 个字符！");
		}
		if(companyProperty.length()>20){
			return renderResult(Global.FALSE, "公司性质长度不能超过 20 个字符！");
		}
		if(fullAddress.length()>=200){
			return renderResult(Global.FALSE, "公司详细地址长度不能超过 200 个字符！");
		}
		if(!"".equals(companyIntroduction) &&companyIntroduction.length()>=1000){
			return renderResult(Global.FALSE, "公司简介不能超过 1000 个字符！");
		}
		if(companyTel.length()>=25){
			return renderResult(Global.FALSE, "公司电话长度不能超过 25 个字符！");
		}
		if(taxNumber.length()>=225){
			return renderResult(Global.FALSE, "税号/统一社会信用代码号长度不能超过 225 个字符！");
		}
		if(businessScope.length()>=25){
			return renderResult(Global.FALSE, "企业经营范围长度不能超过 25 个字符！");
		}
		if(companyFax.length()>=25){
			return renderResult(Global.FALSE, "公司传真长度不能超过 25 个字符！");
		}
		if(companyPostcode.length()>=25){
			return renderResult(Global.FALSE, "邮政编码长度不能超过 25 个字符！");
		}
		if(country.length()>=50){
			return renderResult(Global.FALSE, "国家长度不能超过 50 个字符！");
		}
		if(province.length()>=50){
			return renderResult(Global.FALSE, "省份长度不能超过 50 个字符！");
		}
		if(city.length()>=50){
			return renderResult(Global.FALSE, "城市长度不能超过 50 个字符！");
		}
		supSupplier.setDateStartup(DateUtils.parseDate(dateStartup));
		//写入K3,默认为否
		supSupplier.setWritten("否");
		supSupplier.setIsNewRecord(true);
		supSupplier.setCompanyName(companyName);
		supSupplier.setCompanyOnlineadd(companyOnlineadd);
		supSupplier.setCompanyProperty(companyProperty);
		supSupplier.setRegisteredCapital(Long.valueOf(registeredCapital));
		supSupplier.setTaxNumber(taxNumber);
		supSupplier.setSpace(Long.valueOf(space));
		supSupplier.setMonthProduction(Long.valueOf(monthProduction));
		supSupplier.setEmployees(Long.valueOf(employees));
		supSupplier.setMonthSurplusProduction(Long.valueOf(monthSurplusProduction));
		supSupplier.setYearTurnover(Long.valueOf(yearTurnover));
		supSupplier.setBusinessType(businessType);
		supSupplier.setBusinessScope(businessScope);
		supSupplier.setCompanyIntroduction(companyIntroduction);
		supSupplier.setCompanyTel(companyTel);
		supSupplier.setCompanyFax(companyFax);
		supSupplier.setCompanyPostcode(companyPostcode);
		supSupplier.setCountry(country);
		supSupplier.setProvince(province);
		supSupplier.setCity(city);
		supSupplier.setFullAddress(fullAddress);
		JSONArray contacts=null;
		try{
			contacts = jsonObject.getJSONArray("supSupplierContact");
		}catch (JSONException e){
			return renderResult(Global.FALSE, "数据格式错误，无法解析！");
		}
		SupSupplierContact suscontact=null;
		List<SupSupplierContact> suscontacts=new ArrayList<SupSupplierContact>();
		if(contacts!=null && contacts.size()>0) {
			for (int i = 0; i < contacts.size(); i++) {
				String phonenum= null;
				String email= null;
				String contactPosition= null;
				String contactName= null;
				try {
					suscontact= (SupSupplierContact) JSONObject.toBean(contacts.getJSONObject(i),SupSupplierContact.class);
					phonenum = suscontact.getPhonenum();
					email = suscontact.getEmail();
					contactPosition = suscontact.getContactPosition();
					contactName = suscontact.getContactName();
				} catch (JSONException e) {
					return renderResult(Global.FALSE, "数据格式错误，无法解析公司联系人信息！");
				}
				if(!StringUtils.isNoneBlank(phonenum,email,contactPosition,contactName)){
					return renderResult(Global.FALSE, "请完善公司联系人资料后再提交！");
				}
				if(phonenum.length()>22){
					return renderResult(Global.FALSE, "手机长度不能超过 22 个字符!");
				}
				if(email.length()>100){
					return renderResult(Global.FALSE, "邮箱长度不能超过 100 个字符!");
				}
				if(contactPosition.length()>25){
					return renderResult(Global.FALSE, "职位长度不能超过 25 个字符!");
				}
				if(contactPosition.length()>25){
					return renderResult(Global.FALSE, "职位长度不能超过 25 个字符!");
				}
				if(!SupSupplierService.checkEmail(suscontact.getEmail())){
					return renderResult(Global.FALSE, "请正确填写公司联系人邮箱后再提交！");
				}
				String wechat=suscontact.getWechat();
				String qq=suscontact.getQq();
				if(!"".equals(wechat) &&wechat.length()>20){
					return renderResult(Global.FALSE, "微信长度不能超过 20 个字符!");
				}
				if(!"".equals(qq) &&qq.length()> 20){
					return renderResult(Global.FALSE, "QQ长度不能超过 20 个字符!");
				}
				suscontacts.add(suscontact);
			}
			if(suscontacts.size()<5){
				return renderResult(Global.FALSE, "请至少填写5位公司联系人信息后再提交！");
			}
		}else{
			return renderResult(Global.FALSE, "请完善公司联系人资料后再提交！");
		}
		JSONArray customers=null;
		try{
			customers = jsonObject.getJSONArray("supSupplierCustomers");
		}catch (Exception e){
			return renderResult(Global.FALSE, "数据类型错误，无法解析！");
		}
		SupSupplierCustomers supSupplierCustomers=null;
		List<SupSupplierCustomers> suscustomers=new ArrayList<SupSupplierCustomers>();
		if(customers!=null && customers.size()>0) {
			for (int i = 0; i < customers.size(); i++) {
				String customerName = null;
				String	cooperationShare = null;
				String	effectiveDate = null;
				try {
					supSupplierCustomers= (SupSupplierCustomers) JSONObject.toBean(customers.getJSONObject(i),SupSupplierCustomers.class);
					customerName = supSupplierCustomers.getCustomerName();
					cooperationShare = String.valueOf(supSupplierCustomers.getCooperationShare());
					effectiveDate = supSupplierCustomers.getEffectiveDate();
				} catch (Exception e) {
					return renderResult(Global.FALSE, "数据错误，无法解析主要客户信息！");
				}
				if(!StringUtils.isNoneBlank(customerName,cooperationShare
						,effectiveDate)){
					return renderResult(Global.FALSE, "请完善主要客户资料后再提交！");
				}
				if(cooperationShare.length()>18){
					return renderResult(Global.FALSE, "年合作份额应为18位以下的数字!");
				}
				if(customerName.length()>64){
					return renderResult(Global.FALSE, "客户名称长度不能超过 64 个字符!");
				}
				if(effectiveDate.length()>225){
					return renderResult(Global.FALSE, "主要合作产品类型长度不能超过 225 个字符!");
				}
				suscustomers.add(supSupplierCustomers);
			}
			if(suscustomers.size()<2){
				return renderResult(Global.FALSE, "请至少填写2个主要客户信息后再提交！");
			}
		}else{
			return renderResult(Global.FALSE, "请完善主要客户资料后再提交！");
		}
		JSONArray qualifications=null;
		try{
			qualifications = jsonObject.getJSONArray("supSupplierQualifications");
		}catch (Exception e){
			return renderResult(Global.FALSE, "请完善资质文件后再提交！");
		}
		SupSupplierQualifications qualification=null;
		JSONObject jsonObject1=null;
		Map<String,String> map=null;
		List<Map<String,String>> qual=new ArrayList<Map<String,String>>();
		if(qualifications!=null && qualifications.size()>0) {
			for (int i = 0; i < qualifications.size(); i++) {
				jsonObject1 = qualifications.getJSONObject(i);
				String savePath=jsonObject1.getString("savePath");
				if(!"".equals(savePath)) {
					String profileSurfix=jsonObject1.getString("profileSurfix");
					map = new HashMap<String, String>();
					boolean flag1 = StringUtils.isNoneBlank(jsonObject1.getString("type"), jsonObject1.getString("effectiveDate"), jsonObject1.getString("savePath"));
					if (!flag1) {
						return renderResult(Global.FALSE, "请完善资质文件后再提交！");
					}
					String type = jsonObject1.getString("type");
					String types = "123456";
					if (!(type.length() == 1 && (types.contains(type)))) {
						return renderResult(Global.FALSE, "资质文件类型错误！");
					}
					map.put("type", type);
					String isNeverExpired = jsonObject1.getString("isNeverExpired");

					if ("是".equals(isNeverExpired)) {
						map.put("expireDate", "9999-12-12 00:00:00");
					} else {
						map.put("expireDate", jsonObject1.getString("expireDate"));
					}
					String effectiveDate = jsonObject1.getString("effectiveDate");
					if ("".equals(effectiveDate)) {
						String date1 = DateUtils.formatDate(new Date());
						map.put("effectiveDate", date1);
					} else {
						map.put("effectiveDate", effectiveDate);
					}
					map.put("savePath",savePath);
                    map.put("profileSurfix",profileSurfix);
					map.put("qualificationName", jsonObject1.getString("qualificationName"));
					map.put("isNeverExpired", isNeverExpired);
					qual.add(map);
//				}
				}
			}
			if("0".equals(businessScope)){
				if(qual.size()<4){
					return renderResult(Global.FALSE, "请上传必要的资质文件！");
				}
			}else{
				if(qual.size()<3){
					return renderResult(Global.FALSE, "请上传必要的资质文件！");
				}
			}
			Set set=new HashSet();
			for(int i=0;i<qual.size();i++){
				set.add(qual.get(i).get("type"));
			}
			if(set.size()<qual.size()){
				return renderResult(Global.FALSE, "请不要上传相同类型的资质文件！");
			}
			if("0".equals(businessScope)){
				if(!(set.contains("1") &&set.contains("2") &&set.contains("3") &&set.contains("4"))){
					return renderResult(Global.FALSE, "请上传指定类型的资质文件！");
				}
			}else{
				if(!(set.contains("1") &&set.contains("2") &&set.contains("4"))){
					return renderResult(Global.FALSE, "请上传指定类型的资质文件！");
				}
			}
		}else {
			return renderResult(Global.FALSE, "请完善资质文件后再提交！");
		}
		try {
			supSupplierService.saveInfo(supSupplier,suscontacts,suscustomers,qual);
		} catch (Exception e) {
			return renderResult(Global.FALSE,"资料提交失败，服务器开小差了，请重试");
		}
		return renderResult(Global.TRUE, "你的资料已经成功提交，感谢你的加盟与支持!");
	}
	/**
	 h5页面上传文件接口
	 */
	@PostMapping(value = "saveInfo/saveFileApi")
	@ResponseBody
	public String saveFileApi(@RequestBody String file){
		SupSupplierQualifications qualification= null;
		try {
			JSONObject jsonObject=JSONObject.fromObject(file);
			String profileSurfix=jsonObject.getString("profileSurfix");
			if(checkFileSuffix(profileSurfix)){
				String qualificationName=DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+(int)(Math.random()*9000+1000);
				String savePath=qualificationName.substring(0,4)+"/"+qualificationName.substring(4,6)+"/";
				supSupplierService.uploadFile(jsonObject.getString("file"),qualificationName,savePath,profileSurfix);
				qualification = new SupSupplierQualifications();
				qualification.preInsert();
				qualification.setQualificationName(qualificationName);
				qualification.setSavePath(savePath);
				qualification.setProfileSurfix(profileSurfix);
				supService.insert(qualification);
			}else{
				return renderResult(Global.FALSE, "资质文件上传失败，请上传指定格式的文件");
			}

		}catch (MaxUploadSizeExceededException e){
			return renderResult(Global.FALSE, "资质文件过大");
		}
		catch (Exception e) {
			return renderResult(Global.FALSE, "资质文件上传失败");
		}
		return renderResult(Global.TRUE, qualification.getQualificationCode());
	}

	/**
	 * H5页面公司名称唯一性检查
	 * @param companyName1
	 * @return
	 */
	@PostMapping(value = "saveInfo/checkCompany")
	@ResponseBody
	public String checkCompany(@RequestBody String companyName1){
		JSONObject jsonObject= null;
		try {
			jsonObject = JSONObject.fromObject(companyName1);
		} catch (Exception e) {
			return renderResult(Global.FALSE, "数据格式错误，无法解析");
		}
		String companyName=jsonObject.getString("companyName");
		if(supSupplierService.findSupSupplier(companyName)>0){
			return renderResult(Global.FALSE, "你企业的资料已经成功提交过，无需重复提交，感谢你的支持！");
		}
		return renderResult(Global.TRUE, "");
	}

	/**
	 * multipart上传文件
	 * @param request
	 * @param file
	 * @param response
	 * @return
	 */
	@PostMapping(value = "saveInfo/saveFileApiForm")
	@ResponseBody
	public String saveFileApiForm(HttpServletRequest request, @RequestParam("file") MultipartFile file, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		if (!file.isEmpty()) {
			if(file.getSize()<SupSupplierService.MAX_FILESIZE){
				String profileSurfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				if (!checkFileSuffix(profileSurfix)) {
					return renderResult(Global.FALSE, "请上传指定类型的资质文件！");
				}
				SupSupplierQualifications qualification = new SupSupplierQualifications();
				qualification.preInsert();
				String qualificationName = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + (int) (Math.random() * 9000 + 1000);
				String savePath = qualificationName.substring(0, 4) + "/" + qualificationName.substring(4, 6) + "/";
				qualification.setQualificationName(qualificationName);
				qualification.setSavePath(savePath);
				qualification.setProfileSurfix(profileSurfix);
				supService.insert(qualification);
				File filepath = new File(baseDir+SupSupplierService.SAVE_PREFIX + savePath, qualificationName + profileSurfix);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				try {
					file.transferTo(new File(baseDir+SupSupplierService.SAVE_PREFIX + savePath + qualificationName + profileSurfix));
				} catch (IOException e) {
					return renderResult(Global.FALSE, "文件上传失败！");
				}
				return renderResult(Global.TRUE, qualification.getQualificationCode());
			}else{
				return renderResult(Global.FALSE,"资质文件过大");
			}
		}else {
			return renderResult(Global.FALSE, "文件上传失败！");
		}
	}

	/**
	 * 将供应商信息导出到K3
	 * @param supplierCode 供应商编号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("supplier:supSupplier:edit")
	@RequestMapping(value = "exportSupplierInfo")
	@ResponseBody
	public String exportSupplierInfoToK3(String supplierCode,HttpServletRequest request) throws Exception {
		SupSupplier supSupplier=new SupSupplier();
		supSupplier.setSupplierCode(supplierCode);
		supSupplier=supSupplierService.get(supSupplier);
		if(supSupplier!=null){
			if("是".equals(supSupplier.getWritten())){
				return renderResult(Global.FALSE, "供应商资料已写入K3,无需重复写入！");
			}
			if(null==supSupplier.getScore() || supSupplier.getSupplierStatus()==null || supSupplier.getScore()<60 ||!"2".equals(supSupplier.getSupplierStatus())){
				return renderResult(Global.FALSE, "单据为审核状态并且综合评分达到60分才可写入K3！");
			} else {
				RegisteredSupplierRequest registeredSupplierRequest=new RegisteredSupplierRequest();
				//国家
				String country=supSupplier.getCountry();

				registeredSupplierRequest.setCountry(country);
				//省份
				String province=supSupplier.getProvince();
				//城市
				String city=supSupplier.getCity();

				if("无".equals(province)){
					province="";
				}
				if("无".equals(city)){
					city="";
				}

				registeredSupplierRequest.setProvince(province);
				//城市
				registeredSupplierRequest.setCity(city);
				//K3名称对应K3的公司名称
				registeredSupplierRequest.setSupplierName(supSupplier.getAbbreviationName());
				//注册金额
				registeredSupplierRequest.setRegisteredCapital(supSupplier.getRegisteredCapital());
				//工商登记号
				registeredSupplierRequest.setRegisterCode(supSupplier.getTaxNumber());
				//简称
				registeredSupplierRequest.setAbbreviationName(supSupplier.getCompanyName());
				//年营业额
				registeredSupplierRequest.setAnnualTurnover(supSupplier.getYearTurnover());
				//企业经营范围（字典）
				registeredSupplierRequest.setBusinessScope(amUtilService.findDictLabel(supSupplier.getBusinessScope(),"business_scope"));
				//经营类型（字典）
				registeredSupplierRequest.setBusinessType(amUtilService.findDictLabel(supSupplier.getBusinessType(),"business_type"));
				//公司性质 （字典）companyProperty
				registeredSupplierRequest.setCompanyNature(amUtilService.findDictLabel(supSupplier.getCompanyProperty(),"company_property"));
				//公司简介
				registeredSupplierRequest.setCompanyProfile(supSupplier.getCompanyIntroduction());
				//公司网址
				registeredSupplierRequest.setCompanyWebsite(supSupplier.getCompanyOnlineadd());
				//企业传真
				registeredSupplierRequest.setCorporateFax(supSupplier.getCompanyFax());
				//企业电话
				registeredSupplierRequest.setCorporatePhone(supSupplier.getCompanyTel());
				//通讯地址(需确认是否为详细地址)
				registeredSupplierRequest.setCorrespondenceAddress(supSupplier.getFullAddress());
				//占地面积
				registeredSupplierRequest.setCoverArea(supSupplier.getSpace());
				//员工数量
				registeredSupplierRequest.setEmployeeNumber(supSupplier.getEmployees());
				//创立日期
				registeredSupplierRequest.setFoundingDate(DateUtils.formatDate(supSupplier.getDateStartup()));
				//月生产能力
				registeredSupplierRequest.setMonthlyCapacity(supSupplier.getMonthProduction());
				//月富余生产能力
				registeredSupplierRequest.setMonthlySpareCapacity(supSupplier.getMonthSurplusProduction());
				//邮政编码
				registeredSupplierRequest.setPostalCode(supSupplier.getCompanyPostcode());

				//
				SupSupplierQualifications qualifications=new SupSupplierQualifications();
				qualifications.setSupplierCode(supSupplier.getSupplierCode());
				List<SupSupplierQualifications> list=supService.findList(qualifications);
				//插入各文件
				if(null!=list &&list.size()>0) {
					//拼接项目地址和虚拟路径构成图片路径
				/*String url=request.getRequestURL().toString();
				url=url.substring(0,this.find(url,"/",3))+RM_PREFIX_URL+"uploadfiles/";*/
					//String url="http://am.frp.uvanart.com:9200"+RM_PREFIX_URL+"uploadfiles/";
					for (int i = 0; i < list.size(); i++) {
						SupSupplierQualifications qualifications1=list.get(i);

						String type=qualifications1.getTypeName();
						/*String basePath=url+qualifications1.getSavePath()+qualifications1.getQualificationName()+qualifications1.getProfileSurfix();*/
						String basePath=qualifications1.getSavePath();
						//String basePath=PHOTO_TOK3_SUFFIX+RM_PREFIX_URL+"uploadfiles/"+qualifications1.getSavePath()+qualifications1.getQualificationName()+qualifications1.getProfileSurfix();
						String effectiveDate=DateUtils.formatDate(qualifications1.getEffectiveDate());
						String expireDate=DateUtils.formatDate(qualifications1.getExpireDate());
						if("1".equals(type)){
							//身份证
							registeredSupplierRequest.setSFZFile(basePath);
							registeredSupplierRequest.setSFZEffectiveDate(effectiveDate);
							registeredSupplierRequest.setSFZExpiryDate(expireDate);
						}else if("2".equals(type)){
							//营业执照
							registeredSupplierRequest.setYYZZFile(basePath);
							registeredSupplierRequest.setYYZZEffectiveDate(effectiveDate);
							registeredSupplierRequest.setYYZZExpiryDate(expireDate);
						}else if("3".equals(type)){
							//环评报告
							registeredSupplierRequest.setHPBGFile(basePath);
							registeredSupplierRequest.setHPBGEffectiveDate(effectiveDate);
							registeredSupplierRequest.setHPBGExpiryDate(expireDate);
						}else if("4".equals(type)){
							//消费验收报告
							registeredSupplierRequest.setXFYSBGFile(basePath);
							registeredSupplierRequest.setXFYSBGEffectiveDate(effectiveDate);
							registeredSupplierRequest.setXFYSBGExpiryDate(expireDate);
						}else if("5".equals(type)){
							registeredSupplierRequest.setPWXKZFile(basePath);
							registeredSupplierRequest.setPWXKZEffectiveDate(effectiveDate);
							registeredSupplierRequest.setPWXKZExpiryDate(expireDate);
						}else if("6".equals(type)){
							registeredSupplierRequest.setJCBGFile(basePath);
							registeredSupplierRequest.setJCBGEffectiveDate(effectiveDate);
							registeredSupplierRequest.setJCBGFile(expireDate);
						}
					}
				}
				List<SupSupplierContact> contactList=supSupplier.getSupSupplierContactList();
				if(contactList!=null &&contactList.size()>0){
					//“联系人“页签
					List<RegisteredSupplierContacts> contactsInfo=new ArrayList<RegisteredSupplierContacts>();
					RegisteredSupplierContacts contact=null;
					SupSupplierContact supplierContact=null;
					for(int j=0;j<contactList.size();j++){
						contact=new RegisteredSupplierContacts();
						supplierContact=contactList.get(j);
						// 联系人
						contact.setContacts(supplierContact.getContactName());
						//职务
						contact.setPosition(supplierContact.getContactPosition());
						//手机
						contact.setMobilePhone(supplierContact.getPhonenum());
						// 电子邮箱
						contact.setEmail(supplierContact.getEmail());
						// 微信
						contact.setWeChat(supplierContact.getWechat());
						// QQ
						contact.setTencentQQ(supplierContact.getQq());
						contactsInfo.add(contact);
					}
					registeredSupplierRequest.setContactsInfo(contactsInfo);
				}
				List<SupSupplierCustomers> customersList=supSupplier.getSupSupplierCustomersList();
				if(null!=customersList &&customersList.size()>0){
					//主要客户
					List<RegisteredSupplierPartners> partnersList=new ArrayList<RegisteredSupplierPartners>();
					SupSupplierCustomers customers=null;
					RegisteredSupplierPartners partners=null;
					for(int k=0;k<customersList.size();k++){
						customers=customersList.get(k);
						partners=new RegisteredSupplierPartners();
						// 客户名称
						partners.setCustomerName(customers.getCustomerName());
						// 主要合作产品类型
						partners.setMainProductType(customers.getEffectiveDate());
						// 年合作份额
						partners.setAnnualPartnership(customers.getCooperationShare());
						partnersList.add(partners);
					}
					registeredSupplierRequest.setPartnersInfo(partnersList);
				}
				JSONObject jsonObject=JSONObject.fromObject(registeredSupplierRequest);


				boolean isLogin=k3connection.getConnection();
				if (!isLogin){
					return renderResult(Global.FALSE, "k3登录失败");
				}
				String str =jsonObject.toString();
				String result=InvokeHelper.saveSupplierInfo(str,POST_K3ClOUDRL);
				JSONArray jsonArray=JSONArray.fromObject(result);
				JSONArray jsonArray1=jsonArray.getJSONArray(0);
				JSONObject jsonObject1=jsonArray1.getJSONObject(0);
				boolean IsError=jsonObject1.getBoolean("IsError");
				if(IsError){
					return renderResult(Global.FALSE, "供应商信息导入到K3失败，错误信息："+jsonObject1.getString("Message"));
				}
				SupSupplier supSupplier1=new SupSupplier();
				supSupplier1.setSupplierCode(supSupplier.getSupplierCode());
				supSupplier1.setWritten("是");
				supSupplierService.update(supSupplier1);
				return renderResult(Global.TRUE, "供应商资料已成功导入K3");

			}
		}else{
			return renderResult(Global.FALSE, "不存在对应的供应商信息！");
		}
	}
	/**
	 * 检查文件类型
	 * @param profileSurfix
	 * @return
	 */
	private boolean checkFileSuffix(String profileSurfix) {
		String suffixList = ".jpg,.png,.bmp,.jpeg,.pdf";
		if (suffixList.contains(profileSurfix.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 *检验是否为数字构成
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}


	/**
	 *
	 * @param str
	 * @param cha
	 * @param num
	 * @return  返回字符（cha）在字符串（str）中第num次出现的index值
	 *
	 */
	private int find(String str,String cha,int num){
		int x=0;
		for(int i=0;i<num;i++){
			x=str.indexOf(cha,x+1);
		}
		return x;
	}
}
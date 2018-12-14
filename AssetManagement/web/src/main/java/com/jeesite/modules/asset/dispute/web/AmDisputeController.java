/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.dispute.entity.AmDispute;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDispose;
import com.jeesite.modules.asset.dispute.service.AmDisputeService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 物流纠纷表Controller
 * @author czy
 * @version 2018-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/dispute/amDispute")
public class AmDisputeController extends BaseController {
	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;  //测试库
	@Autowired
	private AmDisputeService amDisputeService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private UserService userService;
	@Autowired
	private K3connection k3connection;
	private final String sFormId = "YF_SC_LogisDisputes";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmDispute get(String documentCode, boolean isNewRecord) {
		return amDisputeService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("dispute:amDispute:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmDispute amDispute, Model model) {
		model.addAttribute("amDispute", amDispute);
		return "asset/dispute/amDisputeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("dispute:amDispute:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmDispute> listData(AmDispute amDispute, HttpServletRequest request, HttpServletResponse response) {
		String empNameEn = EmpUtils.getEmployee().getEmpNameEn();
		Page<AmDispute> page = null;
		if (!"".equals(empNameEn) && empNameEn != null) {
			int count = 0;
			List<AmDispute> amDisputeList = new ArrayList<>();
			String [] nameEn = empNameEn.split(",");
			for (int i = 0; i < nameEn.length; i++) {
				amDispute.setProvince(nameEn[i]);
				page = amDisputeService.findPage(new Page<AmDispute>(request, response), amDispute);
				for (int j = 0; j <page.getList().size(); j++) {
					amDisputeList.add(page.getList().get(j));
					count++;
				}
				page.setCount(page.getCount());
				page.setList(amDisputeList);
			}
		} else {
			page = amDisputeService.findPage(new Page<AmDispute>(request, response), amDispute);
		}
		return page;
	}
//	@RequiresPermissions("dispute:amDispute:view")
	@RequestMapping({"userSelect"})
	public String userSelect(User user, String selectData, Model model, String userType) {
		model.addAttribute("selectData", selectData);
		String empNameEn = EmpUtils.getEmployee().getEmpNameEn();
		List<User> users= amDisputeService.userSelect(empNameEn, 0);
		model.addAttribute("user", users);
		return "asset/dispute/userSelect";
	}
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("dispute:amDispute:view")
	@RequestMapping(value = "listSelectData")
	@ResponseBody
	public Page<User> listSelectData(User user, HttpServletRequest request, HttpServletResponse response) {
		String empNameEn = EmpUtils.getEmployee().getEmpNameEn();
		Page<User> page = userService.findPage(new Page<User>(request, response), user);
		List<User> users= amDisputeService.userSelect(empNameEn, page.getPageNo()-1);
		page.setList(users);
		return page;
	}
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("dispute:amDispute:view")
	@RequestMapping(value = "form")
	public String form(AmDispute amDispute, Model model) {
		if ("受理".equals(amDispute.getDocumentStatus())) {
			amDispute.setUpdate(false);
			amDispute.setCreate(true);
		} else if ("创建".equals(amDispute.getDocumentStatus())) {
			amDispute.setCreate(false);
			amDispute.setUpdate(true);
		} else {
			amDispute.setUpdate(true);
			amDispute.setCreate(true);
		}
		if (!"已完结".equals(amDispute.getDocumentStatus())) {
			amDispute.setEnd(false);
		} else {
			amDispute.setEnd(true);
		}
		String userName = UserUtils.getUser().getUserName();
		amDispute.setUserName(userName);
		String dutyValue = amUtilService.findDictLabel(amDispute.getDutyType(), "am_duty_type");
		amDispute.setDutyValue(dutyValue);
		if (amDispute.getAmDisputeImg() != null) {
			String fileId1 = amDispute.getAmDisputeImg().getFileid1();
			if (!"".equals(fileId1) && fileId1 != null) {
				amDispute.getAmDisputeImg().setFileid1(fileId1);
			}
			String fileId2 = amDispute.getAmDisputeImg().getFileid2();
			if (!"".equals(fileId2) && fileId2 != null) {
				amDispute.getAmDisputeImg().setFileid2(fileId2);
			}
			String fileId3 = amDispute.getAmDisputeImg().getFileid3();
			if (!"".equals(fileId3) && fileId3 != null) {
				amDispute.getAmDisputeImg().setFileid3(fileId3);
			}
			String fileId4 = amDispute.getAmDisputeImg().getFileid4();
			if (!"".equals(fileId4) && fileId4 != null) {
				amDispute.getAmDisputeImg().setFileid4(fileId4);
			}
			String fileId5 = amDispute.getAmDisputeImg().getFileid5();
			if (!"".equals(fileId5) && fileId5 != null) {
				amDispute.getAmDisputeImg().setFileid5(fileId5);
			}
			String fileId6 = amDispute.getAmDisputeImg().getFileid6();
			if (!"".equals(fileId6) && fileId6 != null) {
				amDispute.getAmDisputeImg().setFileid6(fileId6);
			}
		}
		model.addAttribute("amDispute", amDispute);
		return "asset/dispute/amDisputeForm";
	}


	/**
	 * 保存物流纠纷表
	 */
	@RequiresPermissions("dispute:amDispute:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmDispute amDispute, String userName, String flag) throws Exception{
		if (flag == null) {
			if ("".equals(amDispute.getProcessResult())) {
				return renderResult(Global.FALSE, "单据处理结果为空，不允许保存！");
			}
			if ("创建".equals(amDispute.getDocumentStatus())) {     // 如果单据状态等于创建，则更新最新修改时间和最新修改人为当前操作时间和当前操作用户
				amDispute.setUpdateDate(new Date());
				amDispute.setUpdateBy(UserUtils.getUser().getUserName());   // 当前操作用户
			}
		}
		String documentStatus = amDispute.getDocumentStatus();
		if ("0".equals(flag) && !"创建".equals(documentStatus)) {
			return renderResult(Global.FALSE, "单据状态不等于创建，不允许驳回！");
		} else if ("0".equals(flag) && "创建".equals(documentStatus)){
			if ("".equals(amDispute.getRejectRemarks()) || amDispute.getRejectRemarks() == null) {
				return renderResult(Global.FALSE, "驳回结果为空，不允许驳回！");
			}
//			amDispute.setDocumentStatus("驳回");
		}
		if ("1".equals(flag) && !"创建".equals(documentStatus)) {
			return renderResult(Global.FALSE, "单据状态不等于创建，不允许转单！");
		} else if ("1".equals(flag) && "创建".equals(documentStatus)) {
			amDispute.setProcessPerson(userName);
		}

		if ("2".equals(flag) && !"创建".equals(documentStatus)) {
			return renderResult(Global.FALSE, "单据状态不等于创建，不允许受理！");
		}else if ("2".equals(flag) && "创建".equals(documentStatus)) {
			amDispute.setAcceptDate(new Date());
			amDispute.setDocumentStatus("受理");
		}
		if ("3".equals(flag)) {
			if (!"受理".equals(amDispute.getDocumentStatus())) {
				return renderResult(Global.FALSE, "单据状态不等于受理，不允许完结！");
			}
			if ("".equals(amDispute.getProcessResult())) {
				return renderResult(Global.FALSE, "单据处理结果为空，不允许完结！");
			}
//			amDispute.setDocumentStatus("已完结");
//			amDispute.setCompletionPerson(UserUtils.getUser().getUserName());   // 更新完成人等于当前用户
//			amDispute.setCompletionDate(new Date());     // 完成时间等于当前时间
		}

		boolean isLogin=k3connection.getConnection();
		if (!isLogin){
			return renderResult(Global.FALSE, "k3登录失败");
		}
		Long fId = Long.valueOf(Integer.parseInt(amDispute.getFid()));
		if ("受理".equals(documentStatus) && flag == null) {
            if (amDispute.getAmDisputeDisposeList() != null && amDispute.getAmDisputeDisposeList().size() >0) {
                for (AmDisputeDispose amDisputeDispose : amDispute.getAmDisputeDisposeList()){
                    if (amDisputeDispose.getIsNewRecord()) {
                        Date disposeDate = amDisputeDispose.getDisposeDate();     // 处理时间
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = null;
                        if (disposeDate != null) {
                            date = format.format(disposeDate);
                            date = date.replace(" ","T");
                        }
                        String processContent = amDisputeDispose.getProcessContent();  // 处理跟进内容
                        String processResult = amDisputeDispose.getProcessResult();   // 处理结果
                        String saveDispose = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\"" +
                                ",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fId + "\"," +
                                "\"F_YF_Processing\":[{\"F_YF_CreateRowDate\":\""+date+"\",\"F_YF_ProcessingText\":\""+processContent+"\",\"F_YF_Result\":\""+processResult+"\"}]}}";
                        StringBuffer buffer1 = InvokeHelper.Save(sFormId, saveDispose,POST_K3ClOUDRL);
                        boolean isSuccessDispose = isSuccess(buffer1);
                        if (!isSuccessDispose) {
                            return renderResult(Global.FALSE, "k3保存处理进度失败！");
                        }
                    }
                }
            }
        }
		if ("受理".equals(documentStatus) && "3".equals(flag)) {
			try {
				//修改处理结果
				String result = amDispute.getProcessResult();
				Double damages = amDispute.getDamages();
				if (damages == null) {
					damages = new Double(0);
				}
				String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[]," +
						"\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\"," +
						"\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fId + "\",\"F_YF_ProcessResult\":\"" + result + "\"," +
						"\"F_YF_CompensationAmount\":\"" + damages + "\"}}";
				StringBuffer buffer = InvokeHelper.Save(sFormId, saveParam,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return renderResult(Global.FALSE, "k3保存失败！");
				}
				if (amDispute.getAmDisputeDisposeList() != null && amDispute.getAmDisputeDisposeList().size() >0) {
					for (AmDisputeDispose amDisputeDispose : amDispute.getAmDisputeDisposeList()){
					    if (amDisputeDispose.getIsNewRecord()) {
                            Date disposeDate = amDisputeDispose.getDisposeDate();     // 处理时间
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = null;
                            if (disposeDate != null) {
                                date = format.format(disposeDate);
                                date = date.replace(" ","T");
                            }
                            String processContent = amDisputeDispose.getProcessContent();  // 处理跟进内容
                            String processResult = amDisputeDispose.getProcessResult();   // 处理结果
                            String saveDispose = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\"" +
                                    ",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fId + "\"," +
                                    "\"F_YF_Processing\":[{\"F_YF_CreateRowDate\":\""+date+"\",\"F_YF_ProcessingText\":\""+processContent+"\",\"F_YF_Result\":\""+processResult+"\"}]}}";
                            StringBuffer buffer1 = InvokeHelper.Save(sFormId, saveDispose,POST_K3ClOUDRL);
                            boolean isSuccessDispose = isSuccess(buffer1);
                            if (!isSuccessDispose) {
                                return renderResult(Global.FALSE, "k3保存处理进度失败！");
                            }
                        }
					}
				}
				amDisputeService.save(amDispute);
			}catch (Exception e){
				return renderResult(Global.FALSE, "k3保存失败！");
			}
			try {
				String ID = amDispute.getDocumentCode();
				String param = "{\"FormId\":\"YF_SC_LogisDisputes\",\"FieldKeys\":\"FDOCUMENTSTATUS\",\"FilterString\":\"FID="+fId+"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
				String result = InvokeHelper.ExecuteBillQuery(sFormId, param,POST_K3ClOUDRL);
				String [] result1 = result.split("");
				String status = result1[3];
				if (!"C".equals(status)) {
					String auditparam = "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+ID+"\"],\"Ids\":\"\"}";
					StringBuffer buffer = InvokeHelper.Audit(sFormId, auditparam,POST_K3ClOUDRL);
					boolean isSuccess = isSuccess(buffer);
					if (!isSuccess) {
						return renderResult(Global.FALSE, "k3审核失败");
					}
				}
			} catch (Exception e) {
				return renderResult(Global.FALSE, "k3保存成功！审核失败，或已审核");
			}
		}
		if ("创建".equals(documentStatus) && "0".equals(flag)) {
			try {
//				Long fId = Long.valueOf(Integer.parseInt(amDispute.getFid()));
				String rejectRemarks = amDispute.getRejectRemarks();  // 驳回备注
				String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[]," +
						"\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\"," +
						"\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fId + "\"," +
						"\"F_YF_TURNREMARKS\":\"" + rejectRemarks + "\"}}";
				StringBuffer buffer = InvokeHelper.Save(sFormId, saveParam,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return renderResult(Global.FALSE, "k3保存失败！");
				}
				amDisputeService.saveData(amDispute);
			} catch (Exception e) {
				return renderResult(Global.FALSE, "k3保存失败！");
			}

			try {
				String param = "{\"FormId\":\"YF_SC_LogisDisputes\",\"FieldKeys\":\"FDOCUMENTSTATUS\",\"FilterString\":\"FID="+fId+"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
				String result = InvokeHelper.ExecuteBillQuery(sFormId, param,POST_K3ClOUDRL);
				String [] result1 = result.split("");
				String status = result1[3];
				if (!"D".equals(status)) {
					String ID = amDispute.getDocumentCode();
					String auditparam = "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+ID+"\"],\"Ids\":\"\"}";
					StringBuffer buffer = InvokeHelper.UnAudit(sFormId, auditparam,POST_K3ClOUDRL);
					boolean isSuccess = isSuccess(buffer);
					if (!isSuccess) {
						return renderResult(Global.FALSE, "k3保存成功！反审核失败，或已反审核");
					}
				}
			} catch (Exception e) {
				return renderResult(Global.FALSE, "k3保存成功！反审核失败，或已反审核");
			}
		}

		if (flag == null) {
            amDisputeService.save(amDispute);
        }
		if ("2".equals(flag)) {
            amDisputeService.saveData(amDispute);
        }
		if ("创建".equals(documentStatus) && "0".equals(flag)) {
			amDispute.setDocumentStatus("驳回");
			amDisputeService.saveData(amDispute);
		} else if ("受理".equals(documentStatus) && "3".equals(flag)){
			amDispute.setDocumentStatus("已完结");
			amDispute.setCompletionPerson(UserUtils.getUser().getUserName());   // 更新完成人等于当前用户
			amDispute.setCompletionDate(new Date());     // 完成时间等于当前时间
			amDisputeService.save(amDispute);
		}
		return renderResult(Global.TRUE, "保存成功！");
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
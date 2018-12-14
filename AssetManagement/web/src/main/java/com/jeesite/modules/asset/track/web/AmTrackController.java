/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.track.entity.AmTrack;
import com.jeesite.modules.asset.track.entity.AmTrackSpeed;
import com.jeesite.modules.asset.track.service.AmTrackService;
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
 * 退货跟踪单Controller
 * @author czy
 * @version 2018-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/track/amTrack")
public class AmTrackController extends BaseController {
	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;  //测试库
	@Autowired
	private AmTrackService amTrackService;
	@Autowired
	private K3connection k3connection;

	private final String sFormId = "YF_SC_ReturnApplyTrack";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmTrack get(String documentCode, boolean isNewRecord) {
		return amTrackService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("track:amTrack:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmTrack amTrack, Model model) {
		model.addAttribute("amTrack", amTrack);
		return "asset/track/amTrackList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("track:amTrack:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmTrack> listData(AmTrack amTrack, HttpServletRequest request, HttpServletResponse response) {
		String empNameEn = EmpUtils.getEmployee().getEmpNameEn();
		Page<AmTrack> page = null;
		if (!"".equals(empNameEn) && empNameEn != null) {
			int count = 0;
			List<AmTrack> amTrackList = new ArrayList<>();
			String [] nameEn = empNameEn.split(",");
			for (int i = 0; i < nameEn.length; i++) {
				amTrack.setProvince(nameEn[i]);
				page = amTrackService.findPage(new Page<AmTrack>(request, response), amTrack);
				for (int j = 0; j <page.getList().size(); j++) {
					amTrackList.add(page.getList().get(j));
					count++;
				}
				page.setCount(page.getCount());
				page.setList(amTrackList);
			}
		} else {
			page = amTrackService.findPage(new Page<AmTrack>(request, response), amTrack);
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("track:amTrack:view")
	@RequestMapping(value = "form")
	public String form(AmTrack amTrack, Model model) {

		if ("已完结".equals(amTrack.getDocumentStatus())) {
			amTrack.setEnd(true);
		} else {
			amTrack.setEnd(false);
		}
		String userName = UserUtils.getUser().getUserName();
		amTrack.setUserName(userName);
		model.addAttribute("amTrack", amTrack);

		return "asset/track/amTrackForm";
	}

	/**
	 * 保存退货跟踪单
	 */
	@RequiresPermissions("track:amTrack:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmTrack amTrack, String flag) {
		String documentStatus = amTrack.getDocumentStatus(); 		// 更新前单据状态
		if ("1".equals(flag)) {
			if (!"创建".equals(amTrack.getDocumentStatus())) {
				return renderResult(Global.FALSE, "单据状态不等于创建，不允许受理！");
			}

		}
		if ("2".equals(flag)) {
			if (!"受理".equals(amTrack.getDocumentStatus())) {
				return renderResult(Global.FALSE, "单据状态不等于受理，不允许完结！");
			}
//			amTrack.setDocumentStatus("已完结");
//			amTrack.setCompletionPerson(UserUtils.getUser().getUserName());   // 更新完成人等于当前用户
//			amTrack.setCompletionDate(new Date());      // 完成时间等于当前时间
		}
		boolean isLogin = k3connection.getConnection();
		if (!isLogin){
			return renderResult(Global.FALSE, "k3登录失败");
		}
		String fid = amTrack.getFid();
		String documentCode = amTrack.getDocumentCode();
		if (flag == null) {
			if ("受理".equals(documentStatus)) {
				String message = saveTrackSpeed(amTrack, fid, documentCode);
				if (!"".equals(message)) {
					return renderResult(Global.FALSE, message);
				}
			}
		}
		if ("创建".equals(documentStatus) && "1".equals(flag)) {
			String status = getBillStatus(fid);
			String message = saveLock(documentCode, status, fid);
			if (!"".equals(message)) {
				return renderResult(Global.FALSE, message);
			}
			amTrackService.save(amTrack);
		}
		if ("受理".equals(documentStatus) && "2".equals(flag)) {
			Date estimateDate = amTrack.getEstimateDate();			// 预计到货时间
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String estimate = null;
			if (estimateDate != null) {
				estimate = format.format(estimateDate);
				estimate = estimate.replace(" ","T");
			}
			String returnLogisticsCode = amTrack.getReturnLogisticsCode();		// 退货物流单号
			String returnMethod = amTrack.getReturnMethod();		// 退货入仓方式F_YF_LogisticsCost
			//if ()
			String returnRemarks = amTrack.getReturnRemarks();		// 退回备注
			Double logisticsCost = amTrack.getLogisticsCost();		// 物流费用
			if (logisticsCost == null) {
				logisticsCost = new Double(0);
			}
			try {
				String param = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"Model\":{\"FID\":\"" + fid + "\"," +
						"\"FBILLNO\":\"" + documentCode + "\",\"F_YF_ESTIMATEDATE\":\"" + estimate + "\",\"F_YF_RETURNNUMBER\":\"" + returnLogisticsCode + "\",\"F_YF_ReturnRemarks\":\"" + returnRemarks + "\",\"F_YF_LogisticsCost\":\"" + logisticsCost + "\"}}";
				StringBuffer buffer = InvokeHelper.Save(sFormId, param,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return renderResult(Global.FALSE, "k3保存失败！");
				}
				amTrackService.save(amTrack);
			} catch (Exception e) {
				return renderResult(Global.FALSE, "k3保存失败！");
			}
			String message = saveTrackSpeed(amTrack, fid, documentCode);
			if (!"".equals(message)) {
				return renderResult(Global.FALSE, message);
			}

			try {
				String status = getBillStatus(fid);
				String msg= saveLock(documentCode, status, fid);
				if (!"".equals(msg)) {
					return renderResult(Global.FALSE, msg);
				}
				if ("B".equals(status)) {
					String param = "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+documentCode+"\"],\"Ids\":\"\"}";
					StringBuffer buffer = InvokeHelper.ExcuteOperation(sFormId, "Fulfill", param,POST_K3ClOUDRL);
					boolean isSuccess = isSuccess(buffer);
					if (!isSuccess) {
						return "k3完成失败！";
					}
				}
			} catch (Exception e) {
				return renderResult(Global.FALSE, "k3完成失败！");
			}
		}
		if ("2".equals(flag)) {
			amTrack.setDocumentStatus("已完结");
			amTrack.setCompletionPerson(UserUtils.getUser().getUserName());   // 更新完成人等于当前用户
			amTrack.setCompletionDate(new Date());      // 完成时间等于当前时间
		}else if ("1".equals(flag)) {
			amTrack.setAcceptDate(new Date());      // 受理时间更新为当前时间
			amTrack.setAcceptPerson(UserUtils.getUser().getUserName());   // 受理人更新为当前用户
			amTrack.setDocumentStatus("受理");
		}
		amTrackService.save(amTrack);
		return renderResult(Global.TRUE, "保存退货跟踪单成功！");
	}

	/**
	 * 进度编码设置
	 * @param progressSpeed
	 * @return
	 */
	private String getProgressSpeed(String progressSpeed) {
		String progress_speed = null;
		if ("已建单".equals(progressSpeed)) {
			progress_speed = "JD_001";
		} else if ("已通知物流拉货".equals(progressSpeed)) {
			progress_speed = "JD_002";
		} else if ("已和客户约好上门".equals(progressSpeed)) {
			progress_speed = "JD_003";
		} else if ("已拉走货物".equals(progressSpeed)) {
			progress_speed = "JD_004";
		} else if ("货物已发出".equals(progressSpeed)) {
			progress_speed = "JD_005";
		} else if ("调货已到达目的地".equals(progressSpeed)) {
			progress_speed = "JD_006";
		} else if ("已完成配送安装".equals(progressSpeed)) {
			progress_speed = "JD_007";
		} else if ("货物已到佛山".equals(progressSpeed)) {
			progress_speed = "JD_008";
		} else if ("货物已入仓".equals(progressSpeed)) {
			progress_speed = "JD_009";
		} else if ("完结".equals(progressSpeed)) {
			progress_speed = "JD_0010";
		} else if ("拉货超时".equals(progressSpeed)) {
			progress_speed = "JD_011";
		} else if ("返货超时".equals(progressSpeed)) {
			progress_speed = "JD_012";
		}
		return progress_speed;
	}

	/**
	 *进度页签更新到k3
	 */
	public String saveTrackSpeed (AmTrack amTrack, String fid, String documentCode) {
		if (amTrack.getAmTrackSpeedList() != null && amTrack.getAmTrackSpeedList().size() > 0) {
			for (AmTrackSpeed amTrackSpeed : amTrack.getAmTrackSpeedList()) {
				amTrackSpeed.setDocumentCode(amTrack);
				if (amTrackSpeed.getIsNewRecord()) {
					String progress_speed = getProgressSpeed(amTrackSpeed.getProgressSpeed()); // 进度编码取得
					Date followDate = amTrackSpeed.getFollowDate();    // 跟进时间
					Date nextDate = amTrackSpeed.getNextDate();     // 下次跟进时间
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String follow = format.format(followDate);
					follow = follow.replace(" ","T");					// 跟进时间
					String next = format.format(nextDate);
					next = next.replace(" ","T");						// 下次跟进日期
					String progressExplain = amTrackSpeed.getProgressExplain();       // 进度说明
					try {
						String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\"," +
								"\"Model\":{\"FID\":\"" + fid + "\",\"FBILLNO\":\"" + documentCode + "\",\"F_YF_ProgressRateTrack\":[{\"F_YF_ProgressRate\":{\"FNumber\":\""+progress_speed+"\"},\"F_YF_FollowDatetime\":\"" + follow + "\"," +
								"\"F_YF_ProgressRateRemark\":\"" + progressExplain + "\",\"F_YF_NextFollowDate\":\"" + next + "\"}]}}";
						StringBuffer buffer = InvokeHelper.Save(sFormId, saveParam,POST_K3ClOUDRL);
						boolean isSuccess = isSuccess(buffer);
						if (!isSuccess) {
							return "k3进度信息保存失败！";
						}
					} catch (Exception e) {
						return "k3进度信息保存失败！";
					}
				}
			}
		}
		return "";
	}

	/**
	 * 锁定并跟踪
	 * @param documentCode
	 * @return
	 */
	public String saveLock (String documentCode, String status, String fid) {

		try {
			if ("A".equals(status)) {
				String param = "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+documentCode+"\"],\"Ids\":\"\"}";
				StringBuffer buffer = InvokeHelper.ExcuteOperation(sFormId, "Lock", param,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return "k3锁定失败！";
				}
			}
		} catch (Exception e) {
			return "k3锁定失败！";
		}
		try {
			String status1 = getBillStatus(fid);
			if ("D".equals(status1)) {
				String param = "{\"CreateOrgId\":\"0\",\"Numbers\":[\""+documentCode+"\"],\"Ids\":\"\"}";
				StringBuffer buffer = InvokeHelper.ExcuteOperation(sFormId, "ProgressTrack", param,POST_K3ClOUDRL);
				boolean isSuccess = isSuccess(buffer);
				if (!isSuccess) {
					return "k3进度跟踪失败！";
				}
			}
		} catch (Exception e) {
			return "k3进度跟踪失败！";
		}
		return "";
	}

	/**
	 * 获取保存信息是否成功
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

	/**
	 *
	 * @param fid
	 * @return
	 */
	public String getBillStatus (String fid) {
		String paramer = "{\"FormId\":\"YF_SC_ReturnApplyTrack\",\"FieldKeys\":\"F_YF_TRACKSTATUS\",\"FilterString\":\"FID="+fid+"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
		String status = null;
		try {
			String result = InvokeHelper.ExecuteBillQuery(sFormId, paramer,POST_K3ClOUDRL);
			String [] result1 = result.split("");
			status = result1[3];
		}catch (Exception e) {

		}
		return status;
	}
}
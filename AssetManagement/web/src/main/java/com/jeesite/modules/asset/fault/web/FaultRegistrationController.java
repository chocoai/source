/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.web;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.fault.entity.FaultRegistration;
import com.jeesite.modules.asset.fault.entity.FaultRegistrationPicture;
import com.jeesite.modules.asset.fault.service.FaultRegistrationPictureService;
import com.jeesite.modules.asset.fault.service.FaultRegistrationService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 故障登记单Controller
 * @author Scarlett
 * @version 2018-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/fault/faultRegistration")
public class FaultRegistrationController extends BaseController {
	@Autowired
	private FaultRegistrationService faultRegistrationService;
	@Autowired
	private FaultRegistrationPictureService pictureService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmUtilService amUtilService;
	private static final String CK_PER_FIX = "GZDJ";


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FaultRegistration get(String registrationCode, boolean isNewRecord) {
		return faultRegistrationService.get(registrationCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("fault:faultRegistration:view")
	@RequestMapping(value = {"list", ""})
	public String list(FaultRegistration faultRegistration, Model model) {
		model.addAttribute("faultRegistration", faultRegistration);
		return "asset/fault/faultRegistrationList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fault:faultRegistration:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FaultRegistration> listData(FaultRegistration faultRegistration, HttpServletRequest request, HttpServletResponse response) {
		Page<FaultRegistration> page = faultRegistrationService.findPage(new Page<FaultRegistration>(request, response), faultRegistration);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fault:faultRegistration:view")
	@RequestMapping(value = "form")
	public String form(FaultRegistration faultRegistration, Model model) {
		if (faultRegistration.getIsNewRecord()) {
			faultRegistration.setRegistrationCode(amSeqService.getSeq(CK_PER_FIX));
			faultRegistration.setFaultDate(new Date());
			model.addAttribute("picInfo","");
		}else{
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			FaultRegistrationPicture pic =null;
			List<FaultRegistrationPicture> picsList =pictureService.findPicsByRegistrationCode(faultRegistration.getRegistrationCode());
			if (picsList != null && picsList.size() > 0) {
				for (int i = 0; i < picsList.size(); i++) {
					pic=picsList.get(i);
					jsonObject = new JSONObject();
					jsonObject.put("faultpicCode",pic.getFaultpicCode());
					jsonObject.put("savePath",pic.getSavePath());
					jsonObject.put("location",pic.getLocation());
					jsonObject.put("registrationCode",pic.getRegistrationCode());
					jsonArray.add(jsonObject);
				}
				model.addAttribute("picInfo", jsonArray.toString());
			}else{
				model.addAttribute("picInfo","");
			}
		}
		model.addAttribute("faultRegistration", faultRegistration);
		return "asset/fault/faultRegistrationForm";
	}

	/**
	 * 保存故障登记单，同时绑定故障单号到相应的图片；
	 */
	@RequiresPermissions("fault:faultRegistration:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FaultRegistration faultRegistration,String faultpicCodes) {
		if (faultRegistration.getIsNewRecord()) {
			faultRegistration.setRegistrationCode(amSeqService.getCode(CK_PER_FIX));
		}
		faultRegistrationService.save(faultRegistration);
		String[] str = faultpicCodes.split(",");
		if (faultpicCodes != null || !"".equals(faultpicCodes)) {
			String registrationCode=faultRegistration.getRegistrationCode();
			for (String faultpicCode : str) {
				pictureService.updateRegistrationCode(registrationCode,faultpicCode);
			}
		}
		return renderResult(Global.TRUE, "保存故障登记单成功！");
	}


	/**
	 * 删除故障登记单
	 */
	@RequiresPermissions("fault:faultRegistration:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String registrationCode) {
		if (registrationCode== null || "".equals(registrationCode)) {
			return renderResult(Global.FALSE, "没有可删除的故障登记单！");
		}else {
			Boolean flag = faultRegistrationService.deleteData(registrationCode);
			if (flag) {
				return renderResult(Global.TRUE, "成功删除故障登记单！");
			}
			return renderResult(Global.TRUE, "故障登记单删除失败！");
		}
	}
	/**
	 * 获取,上传完成后将code传给前端
	 */
	@RequiresPermissions("fault:faultRegistration:edit")
	@PostMapping(value = "uploadPicture")
	@ResponseBody
	public String uploadPicture(@RequestBody String uploadInfo){
		JSONArray jsonArray=JSONArray.fromObject(uploadInfo);
		int size=jsonArray.size();
		if(null!=jsonArray &&jsonArray.size()>0){
			String []codes=new String[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++){
				FaultRegistrationPicture picture=(FaultRegistrationPicture) JSONObject.toBean(jsonArray.getJSONObject(i),FaultRegistrationPicture.class);
				if(null!=picture){
					picture.setIsNewRecord(true);
					picture.preInsert();
					pictureService.save(picture);
					codes[i]=picture.getFaultpicCode();
				}
			}
			String faultpicCodes=StringUtils.join(codes,",");
			return renderResult(Global.TRUE,faultpicCodes);
		}
		return renderResult(Global.FALSE,"图片保存失败");
	}

	/**
	 * 后台删除图片
	 * @param info
	 * @return
	 */
	@RequiresPermissions("fault:faultRegistration:edit")
	@PostMapping(value = "deletePicture")
	@ResponseBody
	public String deletePicture(@RequestBody String info){
		JSONObject jsonObject=JSONObject.fromObject(info);
		String faultpicCode=jsonObject.getString("faultpicCode");
		String message="";
		if (faultpicCode != null || !"".equals(faultpicCode)) {
			String[] str = faultpicCode.split(",");
			FaultRegistrationPicture pic=null;
			List<String> keys=new ArrayList<String>();
			for (int i = 0; i < str.length; i++) {
				try {
					pic = new FaultRegistrationPicture();
					pic.setFaultpicCode(str[i]);
					pic = pictureService.get(pic);
					if(pic!=null){
						String path=faultRegistrationService.getAliPath(pic.getSavePath());
						if(null!=path &&!"".equals(path)) {
							keys.add(path);
						}
						pictureService.delete(str[i]);
					}
				} catch (Exception e) {
					message=message+str[i]+",";
				}
			}
				try {
				//删除阿里云图片
					amUtilService.deletePicAli(keys);
				} catch (OSSException e) {
					e.printStackTrace();
				} catch (ClientException e) {
					e.printStackTrace();
				}
			if(!"".equals(message)){
				return renderResult(Global.FALSE, "图片"+message+"删除失败！");
			}
			return renderResult(Global.TRUE, "成功删除图片！");
		}else{
			return renderResult(Global.FALSE, "没有图片需要删除！");
		}
	}

}
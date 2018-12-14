/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.supplier.dao.SupSupplierContactDao;
import com.jeesite.modules.asset.supplier.dao.SupSupplierCustomersDao;
import com.jeesite.modules.asset.supplier.dao.SupSupplierDao;
import com.jeesite.modules.asset.supplier.dao.SupSupplierQualificationsDao;
import com.jeesite.modules.asset.supplier.entity.SupSupplier;
import com.jeesite.modules.asset.supplier.entity.SupSupplierContact;
import com.jeesite.modules.asset.supplier.entity.SupSupplierCustomers;
import com.jeesite.modules.asset.supplier.entity.SupSupplierQualifications;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 供应商资料Service
 * @author Scarlett
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly=true)
public class SupSupplierService extends CrudService<SupSupplierDao, SupSupplier> {

	@Autowired
	private SupSupplierQualificationsDao supSupplierQualificationsDao;

	@Autowired
	private SupSupplierContactDao supSupplierContactDao;

	@Autowired
	private SupSupplierCustomersDao supSupplierCustomersDao;
	@Autowired
	private SupSupplierDao supSupplierDao;
	@Autowired
	private AmUtilService amUtilService;
	//public static final String SAVE_PREFIX = "D:/uploadfiles/";
	public static final String SAVE_PREFIX = "/uploadfiles/";
	@Value("${file.baseDir}")
	private String  baseDir;
	//设置文件最大上传大小（10M）
	//public static final long MAX_FILESIZE = 10485760;
	//设置文件最大上传大小（20M）
	public static final long MAX_FILESIZE = 20971520;
	//public static final long MAX_FILESIZE = 2097152;
	//设置文件最大上传大小（20M）
	/**
	 * 后台保存接口（修改版,只保存部分更新的数据）
	 */
	@Transactional(readOnly=false)
	public void savePatirtialInfo(SupSupplier supSupplier){
		supSupplier.getScore();
		supSupplierDao.updatePartialInfo(supSupplier.getSupplierStatus(),supSupplier.getScore(),
				supSupplier.getSavePath(),supSupplier.getRelativePath(),supSupplier.getAbbreviationName(),supSupplier.getSupplierCode());
	}


	/**
	 * 前端调用接口保存供应商资料（只有新增）
	 * @param supSupplier
	 * @param suscontacts
	 * @param suscustomers
	 * @param qual
	 */
	@Transactional(readOnly=false)
	public void saveInfo(SupSupplier supSupplier,List<SupSupplierContact> suscontacts,List<SupSupplierCustomers> suscustomers,List<Map<String,String>> qual)
	{
		super.save(supSupplier);
		for (SupSupplierContact supSupplierContact : suscontacts) {
			supSupplierContact.setSupplierCode(supSupplier);
			supSupplierContact.preInsert();
			supSupplierContactDao.insert(supSupplierContact);
		}
		for (SupSupplierCustomers customer : suscustomers) {
			customer.setSupplierCode(supSupplier);
			customer.preInsert();
			supSupplierCustomersDao.insert(customer);
		}
		SupSupplierQualifications qualification=null;
		Map<String,String> map=null;
		for(int i=0;i<qual.size();i++){
			qualification=new SupSupplierQualifications();
			map=qual.get(i);
			qualification.setIsNewRecord(true);
			qualification.setSupplierCode(supSupplier.getSupplierCode());
			String isNeverExpired=map.get("isNeverExpired");
			Date effectiveDate=DateUtils.parseDate(map.get("effectiveDate"));
			Date expireDate=DateUtils.parseDate(map.get("expireDate"));
			qualification.setIsNeverExpired(isNeverExpired);
			qualification.setEffectiveDate(effectiveDate);
			qualification.setExpireDate(expireDate);
			qualification.setTypeName(map.get("type"));
			qualification.setSavePath(map.get("savePath"));
			qualification.setQualificationName(map.get("qualificationName"));
            /*qualification.setQualificationName(map.get("profileSurfix"));*/
			qualification.setProfileSurfix(map.get("profileSurfix"));

			supSupplierQualificationsDao.insert(qualification);
		}

	}

	@Transactional(readOnly=false)
	/**
	 * 后台方法
	 */
	public void saveProfiles(String effectiveDate,String expireDate,String typeName,String profileSurfix,
							 String supplierCode,String file) throws MaxUploadSizeExceededException,IOException{
		Date effectiveDate1 = null;
		Date expireDate1 = null;
		SupSupplierQualifications qualification=new SupSupplierQualifications();
		String qualificationName = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")+(int)(Math.random()*9000+1000);
		String savePath = qualificationName.substring(0, 4) + "/" + qualificationName.substring(4, 6) + "/";
		this.uploadFile(file, qualificationName, savePath, profileSurfix);
		effectiveDate1 = DateUtils.parseDate(effectiveDate);
		if(expireDate==null ||"".equals(expireDate) ||" 00:00:00".equals(expireDate)){
			expireDate1=DateUtils.parseDate("9999-12-12 00:00:00");
			qualification.setIsNeverExpired("是");
		}else{
			expireDate1=DateUtils.parseDate(expireDate);
			qualification.setIsNeverExpired("否");
		}
		qualification.setExpireDate(expireDate1);
		qualification.setEffectiveDate(effectiveDate1);
		qualification.setTypeName(typeName);
		qualification.setProfileSurfix(profileSurfix);
		qualification.setSavePath(savePath);
		qualification.setQualificationName(qualificationName);
		qualification.setSupplierCode(supplierCode);
		qualification.preInsert();
		supSupplierQualificationsDao.insert(qualification);

	}


	/**
	 * 获取单条数据
	 * @param supSupplier
	 * @return
	 */
	@Override
	public SupSupplier get(SupSupplier supSupplier) {
		SupSupplier entity = super.get(supSupplier);
		if (entity != null){
			SupSupplierContact supSupplierContact = new SupSupplierContact(entity);
			supSupplierContact.setStatus(SupSupplierContact.STATUS_NORMAL);
			entity.setSupSupplierContactList(supSupplierContactDao.findList(supSupplierContact));
			SupSupplierCustomers supSupplierCustomers = new SupSupplierCustomers(entity);
			supSupplierCustomers.setStatus(SupSupplierCustomers.STATUS_NORMAL);
			entity.setSupSupplierCustomersList(supSupplierCustomersDao.findList(supSupplierCustomers));
		}
		return entity;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param supSupplier
	 * @return
	 */
	@Override
	public Page<SupSupplier> findPage(Page<SupSupplier> page, SupSupplier supSupplier) {
		return super.findPage(page, supSupplier);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param supSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SupSupplier supSupplier) {
		super.save(supSupplier);
		// 保存 supSupplierContact子表
		for (SupSupplierContact supSupplierContact : supSupplier.getSupSupplierContactList()){
			if (!SupSupplierContact.STATUS_DELETE.equals(supSupplierContact.getStatus())){
				supSupplierContact.setSupplierCode(supSupplier);
				if (supSupplierContact.getIsNewRecord()){
					supSupplierContact.preInsert();
					supSupplierContactDao.insert(supSupplierContact);
				}else{
					supSupplierContact.preUpdate();
					supSupplierContactDao.update(supSupplierContact);
				}
			}else{
				supSupplierContactDao.delete(supSupplierContact);
			}
		}
		// 保存 supSupplierCustomers子表
		for (SupSupplierCustomers supSupplierCustomers : supSupplier.getSupSupplierCustomersList()){
			if (!SupSupplierCustomers.STATUS_DELETE.equals(supSupplierCustomers.getStatus())){
				supSupplierCustomers.setSupplierCode(supSupplier);
				if (supSupplierCustomers.getIsNewRecord()){
					supSupplierCustomers.preInsert();
					supSupplierCustomersDao.insert(supSupplierCustomers);
				}else{
					supSupplierCustomers.preUpdate();
					supSupplierCustomersDao.update(supSupplierCustomers);
				}
			}else{
				supSupplierCustomersDao.delete(supSupplierCustomers);
			}
		}

	}





	/**
	 * 更新状态
	 * @param supSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SupSupplier supSupplier) {
		super.updateStatus(supSupplier);
	}

	/**
	 * 删除数据
	 * @param supSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SupSupplier supSupplier) {
		supSupplierCustomersDao.deleteData(supSupplier.getSupplierCode());
		supSupplierContactDao.deleteData(supSupplier.getSupplierCode());
		//删除图片细信息
		List<String> keys=new ArrayList<String>();
		List<SupSupplierQualifications> qualificationsList=supSupplierQualificationsDao.findBySupSupplierCode(supSupplier.getSupplierCode());
		if(qualificationsList!=null &&qualificationsList.size()>0){
			for(SupSupplierQualifications qualification:qualificationsList){
				String path=qualification.getQualificationName();
				if(null!=path &&!"".equals(path)){
					keys.add(path);
				}
			}
		}
		if(null!=keys &&keys.size()>0){
			amUtilService.deletePicAli(keys);
		}
		supSupplierQualificationsDao.deleteData(supSupplier.getSupplierCode());
		supSupplierDao.deleteData(supSupplier.getSupplierCode());
	}
	public boolean uploadFile(String base64String,String qualificationName,String savePath,String profileSurfix) throws MaxUploadSizeExceededException, IOException {
		if(base64String == null){
			return false;
		}
		BASE64Decoder decoder =new BASE64Decoder();
		//解码过程，即将base64字符串转换成二进制流
		String pathString=baseDir+SAVE_PREFIX+savePath;
		// 将字符串转换成二进制，用于显示图片
		Integer size=imageSize(base64String);
		if(size>(MAX_FILESIZE)){
			throw new MaxUploadSizeExceededException(MAX_FILESIZE);
		}
		byte[] imageByte=decoder.decodeBuffer(base64String);
		InputStream in = new ByteArrayInputStream(imageByte);
		File file = new File(pathString);
		if (!file.exists()) {
			file.mkdirs();
		}
		pathString=pathString+qualificationName+profileSurfix;
		FileOutputStream fos = new FileOutputStream(pathString);
		byte[] b = new byte[20482];
		int nRead = 0;
		while ((nRead = in.read(b)) != -1) {
			fos.write(b, 0, nRead);
		}
		fos.flush();
		fos.close();
		in.close();
		return true;
	}

	public void deleteFile(String path){
		path=baseDir+SAVE_PREFIX+path;
		path = path.replace("/", "\\");
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	@Transactional(readOnly=true)
	public int findSupSupplier(String companyName){
		return supSupplierDao.findSupSupplier(companyName);
	}

	//邮箱检测方法
	public static boolean checkEmail(String email){
		if(email.matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")){
			return true;
		}else{
			return false;
		}
	}
	public Integer imageSize(String image){
		String str=image;
		//找到等号，把等号也去掉
		Integer equalIndex= str.indexOf("=");
		if(str.indexOf("=")>0) {
			str=str.substring(0, equalIndex);
		}
		//原来的字符流大小，单位为字节
		Integer strLength=str.length();
		//计算后得到的文件流大小，单位为字节
		Integer size=strLength-(strLength/8)*2;
		return size;
	}
	@Transactional(readOnly=false)
	public Boolean deleteData(SupSupplier supSupplier,String supplierCode) {
		Boolean flag=true;
		String[] str = supplierCode.split(",");
		for (int i = 0; i < str.length; i++) {
			supSupplier.setSupplierCode(str[i]);
			supSupplier=this.get(supSupplier);
			if ("2".equals(supSupplier.getSupplierStatus())) {
				continue;
			}
			this.delete(supSupplier);
		}
		return flag;
	}


	/**
	 * 截取删除图片的key值
	 * @param path
	 * @return
	 */
	/*public String getAliPath(String path){
		String path1=ENDPOINT;
		path=path.substring(path.indexOf(path1)+path1.length());
		return path;
	}*/


}
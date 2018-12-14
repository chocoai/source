/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.supplier.service;

import java.util.List;

import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.k3.report.supplier.entity.K3ReportSupplier;
import com.jeesite.modules.k3.report.supplier.dao.K3ReportSupplierDao;

/**
 * 供应商Service
 * @author Albert
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly=true)
public class K3ReportSupplierService extends CrudService<K3ReportSupplierDao, K3ReportSupplier> {
	private static Logger log = LoggerFactory.getLogger(K3ReportSupplierService.class);
	@Autowired
	private K3connection k3connection;

	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;

	/**
	 * 获取单条数据
	 * @param k3ReportSupplier
	 * @return
	 */
	@Override
	public K3ReportSupplier get(K3ReportSupplier k3ReportSupplier) {
		return super.get(k3ReportSupplier);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param k3ReportSupplier
	 * @return
	 */
	@Override
	public Page<K3ReportSupplier> findPage(Page<K3ReportSupplier> page, K3ReportSupplier k3ReportSupplier) {
		String querySql;
		try {
			//判断登录K3是否成功
			boolean isLogin = k3connection.getConnection();
			if (!isLogin){
				log.info("K3ReportSupplierService：连接K3Cloud服务器失败!");
				return null;
			}
			//基本sql
			String baseSql = "FROM T_BD_SUPPLIER a\n" +
							"\tLEFT JOIN T_BD_SUPPLIER_L b ON a.FSUPPLIERID = b.FSUPPLIERID\n" +
							"\tLEFT JOIN T_BD_SUPPLIERBASE c ON a.FSUPPLIERID = c.FSUPPLIERID\n" +
							"\tLEFT JOIN T_ECC_LOGISTICSAREADETAIL_L d ON a.F_YF_PROVINCE = d.FDETAILID\n" +
							"\tLEFT JOIN T_ECC_LOGISTICSAREADETAIL_L e ON a.F_YF_CITY = e.FDETAILID\n" +
							"\tLEFT JOIN T_ECC_LOGISTICSAREADETAIL_L f ON a.F_YF_AREA = f.FDETAILID\n" +
							"\tLEFT JOIN V_BD_INSPECTOR_L g ON a.F_YF_CHECKPERSON = g.FID\n" +
							"\tLEFT JOIN YF_T_BD_AssistData_L h ON a.F_YF_PROJECTTREAM = h.FID\n" +
							"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L i ON c.FSUPPLIERCLASSIFY = i.FENTRYID\n" +
							"\tLEFT JOIN T_BD_SUPPLIERGROUP_L j ON a.FPRIMARYGROUP = j.FID\n" +
							"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L k ON c.FCOMPANYCLASSIFY = k.FENTRYID\n" +
							"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L l ON c.FCOMPANYNATURE = l.FENTRYID\n" +
							"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L m ON c.FCOMPANYSCALE = m.FENTRYID\n" +
							"\tLEFT JOIN T_SEC_USER n ON a.FAUDITORID = n.FUSERID\n" +
							"WHERE 1 = 1";
			//拼接过滤参数
			StringBuilder fileterParamSb = new StringBuilder();
			if (!k3ReportSupplier.getSupplierCode().isEmpty()){
				fileterParamSb.append(" AND a.FNUMBER LIKE '%"+k3ReportSupplier.getSupplierCode()+"%' ");
			}
			if (!k3ReportSupplier.getSupplierName().isEmpty()){
				fileterParamSb.append(" AND b.FNAME LIKE '%"+k3ReportSupplier.getSupplierName()+"%' ");
			}
			if (!k3ReportSupplier.getShortName().isEmpty()){
				fileterParamSb.append(" AND b.FSHORTNAME LIKE '%"+k3ReportSupplier.getShortName()+"%' ");
			}
			if (!k3ReportSupplier.getProvince().isEmpty()){
				fileterParamSb.append(" AND d.FDATAVALUE LIKE '%"+k3ReportSupplier.getProvince()+"%' ");
			}
			if (!k3ReportSupplier.getCity().isEmpty()){
				fileterParamSb.append(" AND e.FDATAVALUE LIKE '%"+k3ReportSupplier.getCity()+"%' ");
			}
			if (!k3ReportSupplier.getDistrict().isEmpty()){
				fileterParamSb.append(" AND f.FDATAVALUE LIKE '%"+k3ReportSupplier.getDistrict()+"%' ");
			}
			if (!k3ReportSupplier.getDataStatus().isEmpty()){
				fileterParamSb.append(" AND a.FDOCUMENTSTATUS = '"+k3ReportSupplier.getDataStatus()+"' ");
			}
			if (!k3ReportSupplier.getDisabledStatus().isEmpty()){
				fileterParamSb.append(" AND a.FFORBIDSTATUS = '"+k3ReportSupplier.getDisabledStatus()+"' ");
			}
			if (!k3ReportSupplier.getSupplyCategory().isEmpty()){
				fileterParamSb.append(" AND c.FSUPPLYCLASSIFY = '"+k3ReportSupplier.getSupplyCategory()+"' ");
			}
			if (!k3ReportSupplier.getSupplierStatus().isEmpty()){
				fileterParamSb.append(" AND a.F_YF_SUPPLIERSTATUS = '"+k3ReportSupplier.getSupplierStatus()+"' ");
			}
			if (!k3ReportSupplier.getFilterParam().isEmpty()){
				fileterParamSb.append(k3ReportSupplier.getFilterParam());
			}
			//查询物料表总数
			querySql = "/*dialect*/SELECT COUNT(*) total\n" + baseSql;
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				querySql  += fileterParamSb;
			}
			JSONArray totalJSonArray = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (totalJSonArray == null || totalJSonArray.toArray().length <= 0){
				log.info("K3ReportSupplierService：查询供应商总数失败");
				return null;
			}
			page.setCount(totalJSonArray.getJSONObject(0).getLong("total"));


			StringBuilder sb = new StringBuilder();
			sb.append("/*dialect*/SELECT TOP "+page.getPageSize()+" supplierCode,supplierName,shortName,province,city,district,mailingAddress,phone,isQualityInspection,qualityInspector,projectTeam,supplierClassification,supplyCategory,supplierGroup,companyCategory,companyNature,companyScale,supplierStatus,dataStatus,disabledStatus,auditor,auditDate");
			sb.append(" FROM ( ");
			sb.append("SELECT ROW_NUMBER() OVER (ORDER BY a.FCREATEDATE DESC) AS RowNumber,a.FNUMBER AS supplierCode, b.FNAME AS supplierName, b.FSHORTNAME AS shortName, d.FDATAVALUE AS province, e.FDATAVALUE AS city\n" +
					"\t, f.FDATAVALUE AS district, c.FADDRESS AS mailingAddress, a.F_YF_SBTEL AS phone\n" +
					"\t, CASE \n" +
					"\t\tWHEN a.F_YF_ISQUALITY = '1' THEN '是'\n" +
					"\t\tELSE '否'\n" +
					"\tEND AS isQualityInspection, g.FNAME AS qualityInspector, h.FNAME AS projectTeam, i.FDATAVALUE AS supplierClassification, c.FSUPPLYCLASSIFY AS supplyCategory\n" +
					"\t, j.FNAME AS supplierGroup, k.FDATAVALUE AS companyCategory, l.FDATAVALUE AS companyNature, m.FDATAVALUE AS companyScale, a.F_YF_SUPPLIERSTATUS AS supplierStatus\n" +
					"\t, a.FDOCUMENTSTATUS AS dataStatus, a.FFORBIDSTATUS AS disabledStatus, n.FNAME AS auditor, a.FAUDITDATE AS auditDate\n");
			sb.append(baseSql);
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				sb.append(fileterParamSb);
			}
			sb.append("     )   as A  ");
			sb.append(" WHERE RowNumber > "+page.getPageSize()+" * ("+page.getPageNo()+"-1) ");
			sb.append(" ORDER BY RowNumber ");
			JSONArray jsonArraySupplierList = InvokeHelper.Execute("QueryBySql",sb.toString(),POST_K3ClOUDRLS);
			if (jsonArraySupplierList == null || jsonArraySupplierList.toArray().length <= 0){
				log.info("K3ReportSupplierService：未查询到供应商数据!");
				return null;
			}
			List<K3ReportSupplier> materialsList = (List<K3ReportSupplier>) JSONArray.toCollection(jsonArraySupplierList,K3ReportSupplier.class);
			return page.setList(materialsList);
		}catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param k3ReportSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(K3ReportSupplier k3ReportSupplier) {
		super.save(k3ReportSupplier);
	}
	
	/**
	 * 更新状态
	 * @param k3ReportSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(K3ReportSupplier k3ReportSupplier) {
		super.updateStatus(k3ReportSupplier);
	}
	
	/**
	 * 删除数据
	 * @param k3ReportSupplier
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(K3ReportSupplier k3ReportSupplier) {
		super.delete(k3ReportSupplier);
	}
	
}
/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.material.service;

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
import com.jeesite.modules.k3.report.material.entity.K3ReportMaterial;
import com.jeesite.modules.k3.report.material.dao.K3ReportMaterialDao;

/**
 * 物料Service
 * @author Albert
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly=true)
public class K3ReportMaterialService extends CrudService<K3ReportMaterialDao, K3ReportMaterial> {
	private static Logger log = LoggerFactory.getLogger(K3ReportMaterial.class);
	@Autowired
	private K3connection k3connection;

	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;

	/**
	 * 获取单条数据
	 * @param k3ReportMaterial
	 * @return
	 */
	@Override
	public K3ReportMaterial get(K3ReportMaterial k3ReportMaterial) {
		return super.get(k3ReportMaterial);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param k3ReportMaterial
	 * @return
	 */
	@Override
	public Page<K3ReportMaterial> findPage(Page<K3ReportMaterial> page, K3ReportMaterial k3ReportMaterial) {
		String querySql;
		try {
			//判断登录K3是否成功
			boolean isLogin = k3connection.getConnection();
			if (!isLogin){
				log.info("K3ReportMaterialService：连接K3Cloud服务器失败!");
				return null;
			}
			//基本sql
			String baseSql = "FROM T_BD_MATERIAL a\n" +
					"\tLEFT JOIN T_BD_MATERIAL_L b ON a.FMATERIALID = b.FMATERIALID\n" +
					"\tLEFT JOIN T_BD_MATERIALBASE c ON a.FMATERIALID = c.FMATERIALID\n" +
					"\tLEFT JOIN T_BD_MATERIALSTOCK d ON a.FMATERIALID = d.FMATERIALID\n" +
					"\tLEFT JOIN T_BD_MATERIALCATEGORY_L e ON c.FCATEGORYID = e.FCATEGORYID\n" +
					"\tLEFT JOIN T_BD_MATERIALGROUP_L f ON a.FMATERIALGROUP = f.FID\n" +
					"\tLEFT JOIN T_CP_GoodsType_L g ON a.F_YF_GOODSTYPE = g.FID\n" +
					"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L h ON c.F_YF_PRODUCTSERIES = h.FENTRYID\n" +
					"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L i ON d.F_YF_CATEGORYTYPE = i.FENTRYID\n" +
					"\tLEFT JOIN T_BAS_ASSISTANTDATAENTRY_L j ON a.F_YF_BHSTRATEGY = j.FENTRYID\n" +
					"\tLEFT JOIN YF_T_BD_AssistData_L k ON a.F_YF_SPECIFICATIONS = k.FID\n" +
					"WHERE 1 = 1\n" +
					"\tAND b.FNAME <> '' ";
			//拼接过滤参数
			StringBuilder fileterParamSb = new StringBuilder();
			if (!k3ReportMaterial.getMaterialCode().isEmpty()){
				fileterParamSb.append(" AND a.FNUMBER LIKE '%"+k3ReportMaterial.getMaterialCode()+"%' ");
			}
			if (!k3ReportMaterial.getMaterialName().isEmpty()){
				fileterParamSb.append(" AND b.FNAME LIKE '%"+k3ReportMaterial.getMaterialName()+"%' ");
			}
			if (!k3ReportMaterial.getInventoryCategory().isEmpty()){
				fileterParamSb.append(" AND e.FNAME LIKE '%"+k3ReportMaterial.getInventoryCategory()+"%' ");
			}
			if (!k3ReportMaterial.getMaterialGroup().isEmpty()){
				fileterParamSb.append(" AND f.FNAME LIKE '%"+k3ReportMaterial.getMaterialGroup()+"%' ");
			}
			if (!k3ReportMaterial.getProductCategory().isEmpty()){
				fileterParamSb.append(" AND g.FNAME LIKE '%"+k3ReportMaterial.getProductCategory()+"%' ");
			}
			if (!k3ReportMaterial.getCommoditySeries().isEmpty()){
				fileterParamSb.append(" AND h.FDATAVALUE LIKE '%"+k3ReportMaterial.getCommoditySeries()+"%' ");
			}
			if (!k3ReportMaterial.getDataStatus().isEmpty()){
				fileterParamSb.append(" AND a.FDOCUMENTSTATUS = '"+k3ReportMaterial.getDataStatus()+"' ");
			}
			if (!k3ReportMaterial.getDisabledStatus().isEmpty()){
				String disabledStatus = "A";
				if ("1".equals(k3ReportMaterial.getDisabledStatus())){
					disabledStatus = "B";
				}
				fileterParamSb.append(" AND a.FFORBIDSTATUS = '"+disabledStatus+"' ");
			}
			if (!k3ReportMaterial.getInventoryArea().isEmpty()){
				fileterParamSb.append(" AND i.FDATAVALUE LIKE '%"+k3ReportMaterial.getInventoryArea()+"%' ");
			}
			if (!k3ReportMaterial.getFilterParam().isEmpty()){
				fileterParamSb.append(k3ReportMaterial.getFilterParam());
			}
			//查询物料表总数
			querySql = "/*dialect*/SELECT COUNT(*) total\n" + baseSql;
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				querySql  += fileterParamSb;
			}
			JSONArray totalJSonArray = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (totalJSonArray == null || totalJSonArray.toArray().length <= 0){
				log.info("K3ReportMaterialService：查询物料总数失败");
				return null;
			}
			page.setCount(totalJSonArray.getJSONObject(0).getLong("total"));


			StringBuilder sb = new StringBuilder();
			sb.append("/*dialect*/SELECT TOP "+page.getPageSize()+" materialCode,materialName,inventoryCategory,materialGroup,productCategory,commoditySeries,isWooden,isQualityInspection,outStockMakeWooden,packingLength, "
					+"packingWidth,packingHigh,packingVolume,inventoryArea,replenishmentStrategy,dataStatus,disabledStatus,specification,[description]");
			sb.append(" FROM ( ");
			sb.append("SELECT ROW_NUMBER() OVER (ORDER BY a.FCREATEDATE DESC) AS RowNumber,a.FNUMBER AS materialCode, b.FNAME AS materialName, e.FNAME AS inventoryCategory, f.FNAME AS materialGroup, g.FNAME AS productCategory\n" +
					"\t, h.FDATAVALUE AS commoditySeries\n" +
					"\t, CASE \n" +
					"\t\tWHEN c.F_YF_ISWOOD = '1' THEN '是'\n" +
					"\t\tELSE '否'\n" +
					"\tEND AS isWooden\n" +
					"\t, CASE \n" +
					"\t\tWHEN a.F_YF_ISQUALITY = '1' THEN '是'\n" +
					"\t\tELSE '否'\n" +
					"\tEND AS isQualityInspection\n" +
					"\t, CASE \n" +
					"\t\tWHEN a.F_YF_OUTSTOCKDMJ = '1' THEN '是'\n" +
					"\t\tELSE '否'\n" +
					"\tEND AS outStockMakeWooden, c.F_YF_PACKINGLEN AS packingLength, c.F_YF_PACKINGWID AS packingWidth, c.F_YF_PACKINGHEIG AS packingHigh, c.F_YF_PACKINGVOLUME AS packingVolume\n" +
					"\t, i.FDATAVALUE AS inventoryArea, j.FDATAVALUE AS replenishmentStrategy\n" +
					"\t, CASE a.FDOCUMENTSTATUS\n" +
					"\t\tWHEN 'A' THEN '创建'\n" +
					"\t\tWHEN 'B' THEN '审核中'\n" +
					"\t\tWHEN 'C' THEN '已审核'\n" +
					"\t\tWHEN 'D' THEN '重新审核'\n" +
					"\t\tWHEN 'Z' THEN '暂存'\n" +
					"\t\tELSE '未知'\n" +
					"\tEND AS dataStatus\n" +
					"\t, CASE a.FFORBIDSTATUS\n" +
					"\t\tWHEN 'A' THEN '否'\n" +
					"\t\tWHEN 'B' THEN '是'\n" +
					"\t\tELSE '未知'\n" +
					"\tEND AS disabledStatus, k.FNAME AS specification, b.FDESCRIPTION AS [description]\n");
			sb.append(baseSql);
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				sb.append(fileterParamSb);
			}
			sb.append("     )   as A  ");
			sb.append(" WHERE RowNumber > "+page.getPageSize()+" * ("+page.getPageNo()+"-1) ");
			sb.append(" ORDER BY RowNumber ");
			JSONArray jsonArrayMaterialsList = InvokeHelper.Execute("QueryBySql",sb.toString(),POST_K3ClOUDRLS);
			if (jsonArrayMaterialsList == null || jsonArrayMaterialsList.toArray().length <= 0){
				log.info("K3ReportMaterialService：未查询到物料数据!");
				return null;
			}
			List<K3ReportMaterial> materialsList = (List<K3ReportMaterial>) JSONArray.toCollection(jsonArrayMaterialsList,K3ReportMaterial.class);
			return page.setList(materialsList);
		}catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param k3ReportMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(K3ReportMaterial k3ReportMaterial) {
		super.save(k3ReportMaterial);
	}
	
	/**
	 * 更新状态
	 * @param k3ReportMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(K3ReportMaterial k3ReportMaterial) {
		super.updateStatus(k3ReportMaterial);
	}
	
	/**
	 * 删除数据
	 * @param k3ReportMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(K3ReportMaterial k3ReportMaterial) {
		super.delete(k3ReportMaterial);
	}
	
}
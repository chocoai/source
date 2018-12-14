/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.bom.service;

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
import com.jeesite.modules.k3.report.bom.entity.K3ReportBom;
import com.jeesite.modules.k3.report.bom.dao.K3ReportBomDao;

/**
 * 物料清单Service
 * @author Albert
 * @version 2018-11-21
 */
@Service
@Transactional(readOnly=true)
public class K3ReportBomService extends CrudService<K3ReportBomDao, K3ReportBom> {
	private static Logger log = LoggerFactory.getLogger(K3ReportBomService.class);
	@Autowired
	private K3connection k3connection;

	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;

	/**
	 * 获取单条数据
	 * @param k3ReportBom
	 * @return
	 */
	@Override
	public K3ReportBom get(K3ReportBom k3ReportBom) {
		return super.get(k3ReportBom);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param k3ReportBom
	 * @return
	 */
	@Override
	public Page<K3ReportBom> findPage(Page<K3ReportBom> page, K3ReportBom k3ReportBom) {
		String querySql;
		long total = 0;
		try {
			//判断登录K3是否成功
			boolean isLogin = k3connection.getConnection();
			if (!isLogin){
				log.info("K3ReportBomService：连接K3Cloud服务器失败!");
				return null;
			}
			//基本sql
			String baseSql = "FROM T_ENG_BOM a\n" +
					"LEFT JOIN T_BD_MATERIAL b ON a.FMATERIALID = b.FMATERIALID\n" +
					"LEFT JOIN T_BD_MATERIAL_L c ON b.FMATERIALID = c.FMATERIALID\n" +
					"LEFT JOIN T_ENG_BOMGROUP_L d ON d.FID = a.FGROUP\n" +
					"LEFT JOIN T_ENG_BOMCHILD e ON a.FID = e.FID\n" +
					"LEFT JOIN T_BD_MATERIAL f ON e.FMATERIALID = f.FMATERIALID\n" +
					"LEFT JOIN T_BD_MATERIAL_L g ON f.FMATERIALID = g.FMATERIALID\n" +
					"LEFT JOIN T_BD_UNIT_L h ON h.FUNITID = e.FUNITID\n" +
					"LEFT JOIN T_ENG_BOM i ON i.FID = e.FBOMID\n" +
					"WHERE 1 = 1 AND c.FNAME <> '' AND g.FNAME <> '' ";

			//拼接过滤参数
			StringBuilder fileterParamSb = new StringBuilder();
			if (!k3ReportBom.getBomCode().isEmpty()){
				fileterParamSb.append(" AND a.FNUMBER LIKE '%"+k3ReportBom.getBomCode()+"%' ");
			}
			if (!k3ReportBom.getParentMaterialCode().isEmpty()){
				fileterParamSb.append(" AND b.FNUMBER LIKE '%"+k3ReportBom.getParentMaterialCode()+"%' ");
			}
			if (!k3ReportBom.getParentMaterialName().isEmpty()){
				fileterParamSb.append(" AND c.FNAME LIKE '%"+k3ReportBom.getParentMaterialName()+"%' ");
			}
			if (!k3ReportBom.getBomGroupName().isEmpty()){
				fileterParamSb.append(" AND d.FNAME LIKE '%"+k3ReportBom.getBomGroupName()+"%' ");
			}
			if (!k3ReportBom.getFilterParam().isEmpty()){
				fileterParamSb.append(k3ReportBom.getFilterParam());
			}

			//查询物料清单表表总数
			querySql = "/*dialect*/SELECT COUNT(*) total\n" + baseSql;
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				querySql += fileterParamSb;
			}
			JSONArray totalJSonArray = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (totalJSonArray == null || totalJSonArray.toArray().length <= 0){
				log.info("K3ReportBomService：查询物料清单表总数失败");
				return null;
			}
			page.setCount(totalJSonArray.getJSONObject(0).getLong("total"));

			StringBuilder sb = new StringBuilder();
			sb.append("/*dialect*/SELECT TOP "+page.getPageSize()+" fid,bomCode,parentMaterialCode,parentMaterialName,bomGroupName,subMaterialCode,subMaterialName ");
			sb.append(" ,subMaterialModel,subUnitName,numerator,denominator,subBomCode ");
			sb.append(" FROM ( ");
			sb.append("SELECT ROW_NUMBER() OVER (ORDER BY a.fid) AS RowNumber,a.fid, ISNULL(a.FNUMBER, '') AS bomCode\n" +
					"\t, ISNULL(b.FNUMBER, '') AS parentMaterialCode\n" +
					"\t, ISNULL(c.FNAME, '') AS parentMaterialName\n" +
					"\t, ISNULL(d.FNAME, '') AS bomGroupName\n" +
					"\t, ISNULL(f.FNUMBER, '') AS subMaterialCode\n" +
					"\t, ISNULL(g.FNAME, '') AS subMaterialName\n" +
					"\t, ISNULL(g.FSPECIFICATION, '') AS subMaterialModel\n" +
					"\t, ISNULL(h.FNAME, '') AS subUnitName, e.FNUMERATOR AS numerator\n" +
					"\t, e.FDENOMINATOR AS denominator, ISNULL(i.FNUMBER, '') AS subBomCode\n");
			sb.append(baseSql);
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				sb.append(fileterParamSb);
			}
			sb.append("     )   as A  ");
			sb.append(" WHERE RowNumber > "+page.getPageSize()+" * ("+page.getPageNo()+"-1) ");
			sb.append(" ORDER BY RowNumber ");
			JSONArray jsonArrayBom = InvokeHelper.Execute("QueryBySql",sb.toString(),POST_K3ClOUDRLS);
			if (jsonArrayBom == null || jsonArrayBom.toArray().length <= 0){
				log.info("K3ReportBomService：未查询到物料清单数据!");
				return null;
			}
			List<K3ReportBom> k3ReportBomList = (List<K3ReportBom>) JSONArray.toCollection(jsonArrayBom,K3ReportBom.class);
			return page.setList(k3ReportBomList);
		}catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param k3ReportBom
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(K3ReportBom k3ReportBom) {
		super.save(k3ReportBom);
	}
	
	/**
	 * 更新状态
	 * @param k3ReportBom
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(K3ReportBom k3ReportBom) {
		super.updateStatus(k3ReportBom);
	}
	
	/**
	 * 删除数据
	 * @param k3ReportBom
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(K3ReportBom k3ReportBom) {
		super.delete(k3ReportBom);
	}
	
}
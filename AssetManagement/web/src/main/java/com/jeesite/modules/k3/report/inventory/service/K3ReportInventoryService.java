/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.inventory.service;

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
import com.jeesite.modules.k3.report.inventory.entity.K3ReportInventory;
import com.jeesite.modules.k3.report.inventory.dao.K3ReportInventoryDao;

/**
 * 即时库存Service
 * @author Albert
 * @version 2018-11-21
 */
@Service
@Transactional(readOnly=true)
public class K3ReportInventoryService extends CrudService<K3ReportInventoryDao, K3ReportInventory> {
	private static Logger log = LoggerFactory.getLogger(K3ReportInventoryService.class);
	@Autowired
	private K3connection k3connection;

	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;
	/**
	 * 获取单条数据
	 * @param k3ReportInventory
	 * @return
	 */
	@Override
	public K3ReportInventory get(K3ReportInventory k3ReportInventory) {
		return super.get(k3ReportInventory);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param k3ReportInventory
	 * @return
	 */
	@Override
	public Page<K3ReportInventory> findPage(Page<K3ReportInventory> page, K3ReportInventory k3ReportInventory){
		String querySql;
		try {
			//判断登录K3是否成功
			boolean isLogin = k3connection.getConnection();
			if (!isLogin){
				log.info("K3ReportInventory：连接K3Cloud服务器失败!");
				return null;
			}

			//基本sql
			String baseSql = "FROM T_STK_INVENTORY a\n" +
							"\tLEFT JOIN T_BD_MATERIAL b ON a.FMATERIALID = b.FMATERIALID\n" +
							"\tLEFT JOIN T_BD_MATERIAL_L c ON b.FMATERIALID = c.FMATERIALID\n" +
							"\tLEFT JOIN T_ENG_BOM d ON a.FBOMID = d.FID\n" +
							"\tLEFT JOIN T_BD_STOCK_L e ON e.FSTOCKID = a.FSTOCKID\n" +
							"\tLEFT JOIN YF_T_BAS_FLEXLOCATION f ON f.DimId = a.FSTOCKLOCID\n" +
							"\tLEFT JOIN T_BAS_FLEXVALUESENTRY_L g ON g.FENTRYID = f.LocationId\n" +
							"\tLEFT JOIN T_BD_LOTMASTER h ON h.FLOTID = a.FLOT\n" +
							"\tLEFT JOIN T_BD_UNIT_L i ON i.FUNITID = a.FSTOCKUNITID\n" +
							"\tLEFT JOIN T_BD_STOCKSTATUS_L j ON j.FSTOCKSTATUSID = a.FSTOCKSTATUSID\n" +
							"\tWHERE 1 = 1 AND c.FNAME <> ''  ";

			//拼接过滤参数
			StringBuilder fileterParamSb = new StringBuilder();
			if (!k3ReportInventory.getStockName().isEmpty()){
				fileterParamSb.append(" AND e.FNAME LIKE '%"+k3ReportInventory.getStockName()+"%' ");
			}
			if (!k3ReportInventory.getMaterialCode().isEmpty()){
				fileterParamSb.append(" AND b.FNUMBER LIKE '%"+k3ReportInventory.getMaterialCode()+"%' ");
			}
			if (!k3ReportInventory.getMaterialName().isEmpty()){
				fileterParamSb.append(" AND c.FNAME LIKE '%"+k3ReportInventory.getMaterialName()+"%' ");
			}
			if (!k3ReportInventory.getStockLocName().isEmpty()){
				fileterParamSb.append(" AND g.FNAME LIKE '%"+k3ReportInventory.getStockLocName()+"%' ");
			}
			if (!k3ReportInventory.getBomVersion().isEmpty()){
				fileterParamSb.append(" AND d.FNUMBER LIKE '%"+k3ReportInventory.getBomVersion()+"%' ");
			}
			if (!k3ReportInventory.getLot().isEmpty()){
				fileterParamSb.append(" AND h.FNUMBER LIKE '%"+k3ReportInventory.getLot()+"%' ");
			}
			if (!k3ReportInventory.getFilterParam().isEmpty()){
				fileterParamSb.append(k3ReportInventory.getFilterParam());
			}
			//查询即时库存表总数
			querySql = "/*dialect*/SELECT COUNT(*) total\n" + baseSql;
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				querySql  += fileterParamSb;
			}
			JSONArray totalJSonArray = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (totalJSonArray == null || totalJSonArray.toArray().length <= 0){
				log.info("K3ReportInventory：查询即时库存表总数失败");
				return null;
			}
			page.setCount(totalJSonArray.getJSONObject(0).getLong("total"));


			StringBuilder sb = new StringBuilder();
			sb.append("/*dialect*/SELECT TOP "+page.getPageSize()+" fid,materialCode,materialName,bomVersion,stockName,stockLocName,lot,stockUnit,stockQty,avbQty,stockStatus ");
			sb.append(" FROM ( ");
			sb.append("SELECT ROW_NUMBER() OVER (ORDER BY a.FSTOCKID) AS RowNumber, a.FID as fid, b.FNUMBER AS materialCode, c.FNAME AS materialName\n" +
					"\t, ISNULL(d.FNUMBER, '') AS bomVersion\n" +
					"\t, ISNULL(e.FNAME, '') AS stockName\n" +
					"\t, ISNULL(g.FNAME, '') AS stockLocName\n" +
					"\t, ISNULL(h.FNUMBER, '') AS lot\n" +
					"\t, ISNULL(i.FNAME, '') AS stockUnit, a.FQTY AS stockQty\n" +
					"\t, a.FAVBQTY AS avbQty, ISNULL(j.FNAME, '') AS stockStatus\n");
			sb.append(baseSql);
			if (!fileterParamSb.toString().isEmpty() && fileterParamSb.length() > 0){
				sb.append(fileterParamSb);
			}
			sb.append("     )   as A  ");
			sb.append(" WHERE RowNumber > "+page.getPageSize()+" * ("+page.getPageNo()+"-1) ");
			sb.append(" ORDER BY RowNumber ");
			JSONArray jsonArrayInventoryList = InvokeHelper.Execute("QueryBySql",sb.toString(),POST_K3ClOUDRLS);
			if (jsonArrayInventoryList == null || jsonArrayInventoryList.toArray().length <= 0){
				log.info("K3ReportInventory：未查询到即时库存数据!");
				return null;
			}
			List<K3ReportInventory> inventoryList = (List<K3ReportInventory>) JSONArray.toCollection(jsonArrayInventoryList,K3ReportInventory.class);
			return page.setList(inventoryList);
		}catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param k3ReportInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(K3ReportInventory k3ReportInventory) {
		super.save(k3ReportInventory);
	}
	
	/**
	 * 更新状态
	 * @param k3ReportInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(K3ReportInventory k3ReportInventory) {
		super.updateStatus(k3ReportInventory);
	}
	
	/**
	 * 删除数据
	 * @param k3ReportInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(K3ReportInventory k3ReportInventory) {
		super.delete(k3ReportInventory);
	}
	
}
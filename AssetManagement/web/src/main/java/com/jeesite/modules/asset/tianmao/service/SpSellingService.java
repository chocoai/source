/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.SpSellingDao;
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.YzyGetToken;
import com.jeesite.modules.fgc.util.JsonUnit;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanItemsOnsaleGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsOnsaleGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsOnsaleGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出售中的商品列表Service
 * @author dwh
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly=true)
public class SpSellingService extends CrudService<SpSellingDao, SpSelling> {
	@Autowired
	private SpSellingDao spSellingDao;
	//商品来源为有赞出售中
	private String sourceType="0";

	/**
	 * 获取单条数据
	 *
	 * @param spSelling
	 * @return
	 */
	@Override
	public SpSelling get(SpSelling spSelling) {
		return super.get(spSelling);
	}

	/**
	 * 查询分页数据
	 *
	 * @param page      分页对象
	 * @param spSelling
	 * @return
	 */
	@Override
	public Page<SpSelling> findPage(Page<SpSelling> page, SpSelling spSelling) {
		return super.findPage(page, spSelling);
	}

	/**
	 * 保存数据（插入或更新）
	 *
	 * @param spSelling
	 */
	@Override
	@Transactional(readOnly = false)
	public void save(SpSelling spSelling) {
		super.save(spSelling);
	}

	/**
	 * 更新状态
	 *
	 * @param spSelling
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateStatus(SpSelling spSelling) {
		super.updateStatus(spSelling);
	}

	/**
	 * 删除数据
	 *
	 * @param spSelling
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(SpSelling spSelling) {
		super.delete(spSelling);
	}

//	//有赞云
//	@Transactional(readOnly=false)
//	public boolean synSellingYZY() {
//		int counti=0;
//		     boolean rst=false;
//			OAuthToken authToken = YzyGetToken.getToken();
//			YZClient client = new DefaultYZClient(new Token(authToken.getAccessToken())); //new Sign(appKey, appSecret)
//			YouzanItemsOnsaleGetParams youzanItemsOnsaleGetParams = new YouzanItemsOnsaleGetParams();
//			youzanItemsOnsaleGetParams.setPageNo(1L);
//			youzanItemsOnsaleGetParams.setPageSize(300L);
//			YouzanItemsOnsaleGet youzanItemsOnsaleGet = new YouzanItemsOnsaleGet();
//			youzanItemsOnsaleGet.setAPIParams(youzanItemsOnsaleGetParams);
//			YouzanItemsOnsaleGetResult result = client.invoke(youzanItemsOnsaleGet);
//			Long count=result.getCount();
//			try {
//				List<YouzanItemsOnsaleGetResult.ItemListOpenModel> items = Arrays.asList(result.getItems());
//				if (ParamentUntil.isBackList(items)) {
//					List<String> spSellings = spSellingDao.getListItenId();
//					for (int i = 0; i < items.size(); i++) {
//						SpSelling spSelling = new SpSelling();
//						YouzanItemsOnsaleGetResult.ItemListOpenModel item = items.get(i);
//						if (spSellings.contains(item.getItemId())) {
//							spSelling.setIsNewRecord(false);
//						}
//						spSelling.setItemId(item.getItemId());
//						spSelling.setAlias(item.getAlias());
//						spSelling.setTitle(item.getTitle());
//						spSelling.setPrice(item.getPrice());
//						spSelling.setItemType(item.getItemType());
//						spSelling.setItemNo(item.getItemNo());
//						spSelling.setQuantity(item.getQuantity());
//						spSelling.setPostType(item.getPostType());
//						spSelling.setPostFee(item.getPostFee());
//						spSelling.setDetailUrl(item.getDetailUrl());
//						this.save(spSelling);
//						counti++;
//
//					}
//					rst = true;
//				}
//			} catch (Exception e) {
//				rst = false;
//
//		}
//			return rst;
//
//	}
	@Transactional(readOnly = false)
	public boolean synSellingYZY(){
		boolean rst = true;
		this.deleteAllData();
		int count=this.queryYouzanItemsOnsaleGetResult(1L,300L);
		int totalPages = (count % 300 == 0 ? count / 300 : count / 300 + 1);
		/*List<YouzanItemsOnsaleGetResult.ItemListOpenModel> items = Arrays.asList(result.getItems());*/
		/*this.insertSynSellingYZY(items);*/
		if (totalPages > 1) {
			for (int i = 2; i <= totalPages; i++) {
				this.queryYouzanItemsOnsaleGetResult((long)i,300L);
			}
		}
			return rst;
	}
		public boolean insertSynSellingYZY(List<YouzanItemsOnsaleGetResult.ItemListOpenModel> items){
					boolean flag = true;
					if (ParamentUntil.isBackList(items)) {
						List<String> spSellings = spSellingDao.getListItenId();
						for (int i = 0; i < items.size(); i++) {
							SpSelling spSelling = new SpSelling();
							YouzanItemsOnsaleGetResult.ItemListOpenModel item = items.get(i);
							if (spSellings.contains(item.getItemId())) {
								spSelling.setIsNewRecord(false);
							}
							spSelling.setItemId(item.getItemId());
							spSelling.setAlias(item.getAlias());
							spSelling.setTitle(item.getTitle());
							spSelling.setPrice(item.getPrice());
							spSelling.setItemType(item.getItemType());
							spSelling.setItemNo(item.getItemNo());
							spSelling.setQuantity(item.getQuantity());
							spSelling.setPostType(item.getPostType());
							spSelling.setPostFee(item.getPostFee());
							spSelling.setDetailUrl(item.getDetailUrl());
							spSelling.setSourceType(sourceType);
							this.save(spSelling);
						}
						return flag;
					}
					return flag;
				}
				public int queryYouzanItemsOnsaleGetResult(Long pageNo,Long pageSize){
//					OAuthToken authToken = YzyGetToken.getToken();
//					YZClient client = new DefaultYZClient(new Token(authToken.getAccessToken())); //new Sign(appKey, appSecret)
					String authToken = YzyGetToken.getToken();
					YZClient client = new DefaultYZClient(new Token(authToken)); //new Sign(appKey, appSecret)
					YouzanItemsOnsaleGetParams youzanItemsOnsaleGetParams = new YouzanItemsOnsaleGetParams();
					youzanItemsOnsaleGetParams.setPageNo(pageNo);
					youzanItemsOnsaleGetParams.setPageSize(pageSize);
					YouzanItemsOnsaleGet youzanItemsOnsaleGet = new YouzanItemsOnsaleGet();
					youzanItemsOnsaleGet.setAPIParams(youzanItemsOnsaleGetParams);
					YouzanItemsOnsaleGetResult result1=null;
					String result = client.execute(youzanItemsOnsaleGet);
					com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(result);
					com.alibaba.fastjson.JSONObject respon= (com.alibaba.fastjson.JSONObject) jsonObject.get("response");
					int count= respon.getInteger("count");
					com.alibaba.fastjson.JSONArray items_json= (com.alibaba.fastjson.JSONArray) respon.get("items");
					List items=null;
					if (items_json!=null&&items_json.size()>0){
						items= JsonUnit.jsonToList(items_json.toJSONString(),YouzanItemsOnsaleGetResult.ItemListOpenModel.class);
					}
                    this.insertSynSellingYZY(items);
		               return count;
				}

	public List<SpSelling> getAllYzyInfo() {
		return spSellingDao.getAllYzyInfo();
	}

	public SpSelling getSpSellingByItemId(String itemId) {
		return spSellingDao.getSpSellingByItemId(itemId);
	}

	/**
	 * 删除js_sp_selling表中出售中的所有商品
	 */
	@Transactional(readOnly = false)
	public void deleteAllData(){
		spSellingDao.deleteAllData();
	}

	/**
	 *  删除js_sp_selling表中仓库中的所有商品
	 */
	@Transactional(readOnly = false)
	public void deleteAllDataBySourceType(){
		spSellingDao.deleteAllDataBySourceType();
	}
}
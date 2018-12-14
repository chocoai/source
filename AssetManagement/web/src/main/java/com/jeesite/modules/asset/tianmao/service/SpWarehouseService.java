/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.SpSellingDao;
import com.jeesite.modules.asset.tianmao.dao.SpWarehouseDao;
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import com.jeesite.modules.asset.tianmao.entity.SpWarehouse;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.YzyGetToken;
import com.jeesite.modules.fgc.util.JsonUnit;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanItemsInventoryGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsInventoryGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsInventoryGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库中商品列表Service（获取有赞云youzan.items.inventory.get接口数据）
 * @author dwh
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly=true)
public class SpWarehouseService extends CrudService<SpWarehouseDao, SpWarehouse> {

	@Autowired
	private SpWarehouseDao spWarehouseDao;
	@Autowired
    private SpSellingDao spSellingDao;
	@Autowired
    private SpSellingService sellingService;
	//商品来源为有赞仓库中
	private String sourceType="1";
	/**
	 * 获取单条数据
	 * @param spWarehouse
	 * @return
	 */
	@Override
	public SpWarehouse get(SpWarehouse spWarehouse) {
		return super.get(spWarehouse);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param spWarehouse
	 * @return
	 */
	@Override
	public Page<SpWarehouse> findPage(Page<SpWarehouse> page, SpWarehouse spWarehouse) {
		return super.findPage(page, spWarehouse);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param spWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpWarehouse spWarehouse) {
		super.save(spWarehouse);
	}
	
	/**
	 * 更新状态
	 * @param spWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SpWarehouse spWarehouse) {
		super.updateStatus(spWarehouse);
	}
	
	/**
	 * 删除数据
	 * @param spWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SpWarehouse spWarehouse) {
		super.delete(spWarehouse);
	}
	//获取仓库中的商品列表插入sp_selling中
    @Transactional(readOnly=false)
    public boolean synSpSellingSpYZY() {
        sellingService.deleteAllDataBySourceType();
        boolean rst=true;
        int count=this.querySynSpSellingSpYZY(1L,300L);
        int totalPages = (count % 300 == 0 ? count / 300 : count / 300 + 1);
        if (totalPages > 1) {
            for (int i = 2; i <= totalPages; i++) {
                this.querySynSpSellingSpYZY((long)i,300L);

            }

        }
        return rst;

    }
    public int querySynSpSellingSpYZY(Long pageNo,Long pageSize){
//        OAuthToken authToken = YzyGetToken.getToken();
//        YZClient client = new DefaultYZClient(new Token(authToken.getAccessToken())); //new Sign(appKey, appSecret)
        String authToken = YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(authToken)); //new Sign(appKey, appSecret)
        YouzanItemsInventoryGetParams youzanItemsInventoryGetParams = new YouzanItemsInventoryGetParams();
        youzanItemsInventoryGetParams.setPageNo(pageNo);
        youzanItemsInventoryGetParams.setPageSize(pageSize);
        youzanItemsInventoryGetParams.setOrderBy("asc");
        YouzanItemsInventoryGet youzanItemsInventoryGet = new YouzanItemsInventoryGet();
        youzanItemsInventoryGet.setAPIParams(youzanItemsInventoryGetParams);
        String  result = client.execute(youzanItemsInventoryGet);
        com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(result);
        com.alibaba.fastjson.JSONObject respon= (com.alibaba.fastjson.JSONObject) jsonObject.get("response");
        int count= respon.getInteger("count");
        com.alibaba.fastjson.JSONArray items_json= (com.alibaba.fastjson.JSONArray) respon.get("items");
        List items=null;
        if (items_json!=null&&items_json.size()>0){
            items= JsonUnit.jsonToList(items_json.toJSONString(),YouzanItemsInventoryGetResult.ItemListOpenModel.class);
        }
        this.insertSynSpSellingSpYZY(items);
        return count;
    }

    public boolean insertSynSpSellingSpYZY(List<YouzanItemsInventoryGetResult.ItemListOpenModel> items){
        boolean falg=true;
        if (ParamentUntil.isBackList(items)) {
            List<String> spSellings = spSellingDao.getListItenId();
            for (int i = 0; i < items.size(); i++) {
                SpSelling spSelling=new SpSelling();
                YouzanItemsInventoryGetResult.ItemListOpenModel item = items.get(i);
                if (spSellings.contains(item.getItemId())) {
                    spSelling.setIsNewRecord(false);
                }
                spSelling.setItemNo(item.getItemNo());
                spSelling.setItemId(item.getItemId());
                spSelling.setAlias(item.getAlias());
                spSelling.setTitle(item.getTitle());
                spSelling.setPrice(item.getPrice());
                spSelling.setItemType(item.getItemType());
                spSelling.setQuantity(item.getQuantity());
                spSelling.setPostType(item.getPostType());
                spSelling.setPostFee(item.getPostFee());
                spSelling.setSourceType(sourceType);
               /* spSelling.setCreatedTime(item.getCreatedTime());
                spSelling.setUpdateTime(item.getUpdateTime());
                spSelling.setNum(item.getNum());
                spSelling.setImage(item.getImage());*/
                sellingService.save(spSelling);
            }
            return falg;
        }
        return falg;
    }
	/*@Transactional(readOnly=false)
    public boolean synWarehouseSpYZY(YouzanItemsInventoryGetResult result) {
		Long count = result.getCount();
		Long totalPages = (count % 300 == 0 ? count / 300 : count / 300 + 1);
			boolean rst=true;
				List<YouzanItemsInventoryGetResult.ItemListOpenModel> items = Arrays.asList(result.getItems());
				this.insertSynWarehouseSpYZY(items);
		if (totalPages.intValue() > 1) {
			for (int i = 2; i <= totalPages.intValue(); i++) {
				YouzanItemsInventoryGetResult result1 = this.querySynWarehouseSpYZY((long)i,300L);
				List<YouzanItemsInventoryGetResult.ItemListOpenModel> items1 = Arrays.asList(result1.getItems());
				this.insertSynWarehouseSpYZY(items1);
			}

		}
			return rst;

		}
	public YouzanItemsInventoryGetResult querySynWarehouseSpYZY(Long pageNo,Long pageSize){
		OAuthToken authToken = YzyGetToken.getToken();
		YZClient client = new DefaultYZClient(new Token(authToken.getAccessToken())); //new Sign(appKey, appSecret)
		YouzanItemsInventoryGetParams youzanItemsInventoryGetParams = new YouzanItemsInventoryGetParams();
		youzanItemsInventoryGetParams.setPageNo(pageNo);
		youzanItemsInventoryGetParams.setPageSize(pageSize);
		youzanItemsInventoryGetParams.setOrderBy("asc");
		YouzanItemsInventoryGet youzanItemsInventoryGet = new YouzanItemsInventoryGet();
		youzanItemsInventoryGet.setAPIParams(youzanItemsInventoryGetParams);
		YouzanItemsInventoryGetResult result = client.invoke(youzanItemsInventoryGet);
		return result;
	}
	public boolean insertSynWarehouseSpYZY(List<YouzanItemsInventoryGetResult.ItemListOpenModel> items){
		  boolean falg=true;
		if (ParamentUntil.isBackList(items)) {
			List<String> spWarehouses = spWarehouseDao.getListItemId();
			for (int i = 0; i < items.size(); i++) {
				SpWarehouse spWarehouse = new SpWarehouse();
				YouzanItemsInventoryGetResult.ItemListOpenModel item = items.get(i);
				if (spWarehouses.contains(item.getItemId())) {
					spWarehouse.setIsNewRecord(false);
				}
				spWarehouse.setItemId(item.getItemId());
				spWarehouse.setAlias(item.getAlias());
				spWarehouse.setTitle(item.getTitle());
				spWarehouse.setPrice(item.getPrice());
				spWarehouse.setItemType(item.getItemType());
				spWarehouse.setQuantity(item.getQuantity());
				spWarehouse.setPostType(item.getPostType());
				spWarehouse.setPostFee(item.getPostFee());
				spWarehouse.setCreatedTime(item.getCreatedTime());
				spWarehouse.setUpdateTime(item.getUpdateTime());
				spWarehouse.setNum(item.getNum());
				spWarehouse.setImage(item.getImage());
				this.save(spWarehouse);
			}
			return falg;
		}
		return falg;
	}*/

}
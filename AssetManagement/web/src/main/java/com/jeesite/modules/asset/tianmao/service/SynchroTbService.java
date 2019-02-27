package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.tianmao.dao.*;
import com.jeesite.modules.asset.tianmao.entity.BigItem;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 类描述
 *
 * @author Jace Xiong
 */
@Service
@Transactional(readOnly=true)
public class SynchroTbService {
    private Logger LOGGER = LoggerFactory.getLogger(SynchroTbService.class);

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private TbProductDao tbProductDao;
    @Autowired
    private TbLogService tbLogService;
    @Autowired
    private TbSkuDao tbSkuDao;

    @Autowired
    private TbItemImgsDao tbItemImgsDao;


    @Transactional(readOnly = false)
    public Map insert(BigItem item, TbProduct tbProduct, List<TbSku> tbSkus, List<TbItemImgs> itemImgs, String time){
        TbProduct tbProduct1 = tbProductDao.get(tbProduct);
        int a,b;
        Map<String,Object> map = new HashMap<>(20);
        List<TbSku> skuList = itemDao.selectByNumId(Long.valueOf(item.getId()));
        //如果数据存在就update,否则就insert
        if(tbProduct1!=null){
            a = itemDao.update(item);
            b = (int)tbProductDao.update(tbProduct);
            //日志记录更新
            tbLogService.insertLog(tbSkus,"同步天猫");
            updateSku(tbSkus, skuList);
//            int c = itemDao.deleteSku(Long.valueOf(item.getId()));
//            System.out.println("删除sku记录========"+c);
//            if(tbSkus!=null && tbSkus.size()>0){
////                for (TbSku tbSku : tbSkus) {
////                    itemDao.insertSku(tbSku);
////                }
//                tbSkuDao.insertSkuList(tbSkus);
//            }
        }else {
            BigItem bigItem = itemDao.selectBigItemBynumId(item.getId());
            if(bigItem!=null){
                itemDao.deleteBigItemBynumId(item.getId());
            }
            a = itemDao.insert(item);
            b = itemDao.insertProdect(tbProduct);
            updateSku(tbSkus, skuList);
//            itemDao.deleteSku(Long.valueOf(item.getId()));
//            if(tbSkus!=null && tbSkus.size()>0){
//                tbSkuDao.insertSkuList(tbSkus);
////                for (TbSku tbSku : tbSkus) {
////                    itemDao.insertSku(tbSku);
////                }
//            }
        }
        itemDao.deleteImg(Long.valueOf(tbProduct.getNumIid()));
        tbItemImgsDao.insertBatch(itemImgs);
        LOGGER.info("天猫商品数据同步结果："+a+"条记录");
        if(a>0&&b>0){
            map.put("flag", "success");
            map.put("code",200);
            map.put("msg","数据推送成功");
            map.put("time",time);
            return map;
        }else {
            map.put("flag", "failure");
            map.put("code",500);
            map.put("msg","数据推送失败，请检查参数！！！");
            map.put("time",time);
            return map;
        }
    }

    @Transactional(readOnly = false)
    public void updateSku(List<TbSku> tbSkus, List<TbSku> skuList) {
        if(tbSkus!=null && tbSkus.size()>0){
            List<TbSku> list = ListUtils.newArrayList();
            List<TbSku> list1 = ListUtils.newArrayList();
            for (TbSku tbSku : tbSkus) {
                TbSku tbSku1 = null;
                try {
                    // 根据最新同步的商品skuID，查询数据库中同商品下是否存在这个skuId
                    tbSku1 = skuList.stream().filter(s ->s.getSkuId().equals(tbSku.getSkuId())).findFirst().get();
                } catch (NoSuchElementException e) {

                }
                // 如果不存在 放入List 后边进行插入操作
                if (tbSku1 == null) {
                    list.add(tbSku);
                } else {
                    // 如果存在 后边执行更新操作
                    list1.add(tbSku);
                }
            }
            // 如果list不为空 进行插入操作
            if (list != null && list.size() >0) {
                tbSkuDao.insertSkuList(list);
            }
            // 如果list不为空进行更新操作
            if (list1 != null && list1.size() > 0) {
                tbSkuDao.updateSkuList(list1);
            }
            // 把两个list合并在一起 因为也有可能存在数据库的数据没有更新的 没更新的说明skuid变掉了 后边要把没更新的数据库存数更新为0
            list.addAll(list1);
            List<TbSku> list2 = ListUtils.newArrayList();
            for (TbSku tbSku2 : skuList) {
                TbSku tbSku = null;
                try {
                    // 拿数据库原存在的数据的skuid到前边合并过的list中取值
                    tbSku = list.stream().filter(s ->s.getSkuId().equals(tbSku2.getSkuId())).findFirst().get();
                }catch (NoSuchElementException e) {

                }
                // 如果取不到说明未被更新也没有插入 就要进行把库存更新为0的操作
                if (tbSku == null) {
                    list2.add(tbSku2);
                }
            }
            // 如果不为空 根据skiid执行更新库存为0
            if (list2 != null && list2.size() > 0) {
                tbSkuDao.updateQuantity(list2);
            }
        }
    }
}

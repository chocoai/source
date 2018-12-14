/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.warehioreport.entity.SummaryReport;
import com.jeesite.modules.asset.warehioreport.dao.SummaryReportDao;

/**
 * 耗材仓库进出明细表Service
 *
 * @author czy
 * @version 2018-05-26
 */
@Service
@Transactional(readOnly = true)
public class SummaryReportService extends CrudService<SummaryReportDao, SummaryReport> {

    /**
     * 获取单条数据
     *
     * @param summaryReport
     * @return
     */
    @Autowired
    private SummaryReportDao summaryReportDao;
    @Autowired
    private AmUtilService amUtilService;

    @Autowired
    private AmWarehouseService amWarehouseService;

    @Override
    public SummaryReport get(SummaryReport summaryReport) {
        return super.get(summaryReport);
    }

    /**
     * 查询分页数据
     *
     * @param page          分页对象
     * @param summaryReport
     * @return
     */
    @Override
    public Page<SummaryReport> findPage(Page<SummaryReport> page, SummaryReport summaryReport) {
        Date date = new Date();
        Date beginTime = null;
        Date endTime = null;
        if (summaryReport.getBeginDate() == null && summaryReport.getEndDate() == null) {
            beginTime = TimeUtils.getBeginOfMonthByDate(date);     //月初时间
            endTime = TimeUtils.getEndDayOfMonthByDate(date);     //月末时间
            summaryReport.setBeginDate(beginTime);
            summaryReport.setEndDate(endTime);
        } else {
            beginTime = summaryReport.getBeginDate();
            if (beginTime==null){
                beginTime =summaryReport.getEndDate();
            }
            endTime = summaryReport.getEndDate();
        }
        Page<SummaryReport> pageList = super.findPage(page, summaryReport);
        if (pageList.getList() != null && pageList.getList().size() > 0) {
            pageList.setList(prosses(pageList.getList(), beginTime, endTime)) ;
        }
        long allbgingCount=0;
        Double allbgingAmount=0.0;
        long allinstorageCount=0;
        Double allinstorageAmount=0.0;
        long alloutCount=0;
        Double alloutAmount=0.0;
        long allbalanceCount=0;
        Double allbalanceAmount=0.0;
        //合计
        if (pageList.getList()!=null&&pageList.getList().size()>0){
        for(int i=0;i<pageList.getList().size();i++){
            if (pageList.getList().get(i).getBeginningCount()==null){
                pageList.getList().get(i).setBeginningCount((long) 0);
            }
            allbgingCount=allbgingCount+pageList.getList().get(i).getBeginningCount();
            if (pageList.getList().get(i).getBeginningAmount()==null){
                pageList.getList().get(i).setBeginningAmount( 0.0);
            }
            allbgingAmount=allbgingAmount+pageList.getList().get(i).getBeginningAmount();
            if (pageList.getList().get(i).getInstorageCount()==null){
                pageList.getList().get(i).setInstorageCount((long) 0);
            }
            allinstorageCount=allinstorageCount+pageList.getList().get(i).getInstorageCount();
            if (pageList.getList().get(i).getInstorageAmount()==null){
                pageList.getList().get(i).setInstorageAmount( 0.0);
            }
            allinstorageAmount=allinstorageAmount+pageList.getList().get(i).getInstorageAmount();
            if (pageList.getList().get(i).getOutstorageCount()==null){
                pageList.getList().get(i).setOutstorageCount((long) 0);
            }
            alloutCount=alloutCount+pageList.getList().get(i).getOutstorageCount();
            if (pageList.getList().get(i).getOutstorageAmount()==null){
                pageList.getList().get(i).setOutstorageAmount( 0.0);
            }
            alloutAmount=alloutAmount+pageList.getList().get(i).getOutstorageAmount();
            if (pageList.getList().get(i).getBalanceCount()==null){
                pageList.getList().get(i).setBalanceCount((long) 0);
            }
            allbalanceCount=allbalanceCount+pageList.getList().get(i).getBalanceCount();
            if (pageList.getList().get(i).getBalanceAmount()==null){
                pageList.getList().get(i).setBalanceAmount( 0.0);
            }
            allbalanceAmount=allbalanceAmount+pageList.getList().get(i).getBalanceAmount();
        }
        pageList.getList().get(0).setAllbalanceAmount(allbalanceAmount);
        pageList.getList().get(0).setAllbalanceCount(allbalanceCount);
        pageList.getList().get(0).setAllbgingAmount(allbgingAmount);
        pageList.getList().get(0).setAllbgingCount(allbgingCount);
        pageList.getList().get(0).setAllinstorageAmount(allinstorageAmount);
        pageList.getList().get(0).setAllinstorageCount(allinstorageCount);
        pageList.getList().get(0).setAlloutAmount(alloutAmount);
        pageList.getList().get(0).setAlloutCount(alloutCount);
        }
        return pageList;
    }


    /**
     * 保存数据（插入或更新）
     *
     * @param summaryReport
     */
    @Override
    @Transactional(readOnly = false)
    public void save(SummaryReport summaryReport) {
        super.save(summaryReport);
    }

    /**
     * 更新状态
     *
     * @param summaryReport
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(SummaryReport summaryReport) {
        super.updateStatus(summaryReport);
    }

    /**
     * 删除数据
     *
     * @param summaryReport
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(SummaryReport summaryReport) {
        super.delete(summaryReport);
    }


    private List<SummaryReport> prosses(List<SummaryReport> list, Date beginDate, Date endTime) {
        Date date = new Date();
        List<SummaryReport> rst = null;
        if (list == null || list.size() <= 0) {
            return null;
        }
        rst = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SummaryReport summaryReport = list.get(i);
            if (summaryReport.getInstorageCount()==null){
                summaryReport.setInstorageCount((long) 0);
            }
            if (summaryReport.getInstorageAmount()==null){
                summaryReport.setInstorageAmount(0.0);
            }
            if (summaryReport.getOutstorageCount()==null){
                summaryReport.setOutstorageCount((long) 0);
            }
            if (summaryReport.getOutstorageAmount()==null){
                summaryReport.setOutstorageAmount(0.0);
            }
            if (summaryReport.getBeginningOutCount() == null) {
                summaryReport.setBeginningOutCount((long) 0);
            }
            if (summaryReport.getBeginningInCount() == null) {
                summaryReport.setBeginningCount((long) 0);
            }
            if (summaryReport.getBeginningInAmount() == null) {
                summaryReport.setBeginningInAmount(0.0);
            }
            if (summaryReport.getBeginningOutAmount() == null) {
                summaryReport.setBeginningOutAmount(0.0);
            }
            if (summaryReport.getBeginningAmount() == null) {
                summaryReport.setBeginningAmount(0.0);
            }
            String consumablesCode = summaryReport.getConsumablesCode();
            String warehouseCode = summaryReport.getWarehouseCode();
            String imgPath=amUtilService.getImgPath(list.get(i).getConsumablesCode());//获取照片路径
            //初期数量，金额,得到该单据开始日期前的所有出入库数量、出入库金额的和
            SummaryReport summaryReport1 = summaryReportDao.getCountByDate(beginDate, summaryReport.getConsumablesCode(),summaryReport.getWarehouseCode());
            prossesBeging(summaryReport1, summaryReport);
            //结余数量
            summaryReport.setBalanceCount(summaryReport.getBeginningCount()+summaryReport.getInstorageCount()-summaryReport.getOutstorageCount());
            summaryReport.setBalanceAmount(  summaryReport.getBeginningAmount()+summaryReport.getInstorageAmount()- summaryReport.getOutstorageAmount());
            summaryReport.setBalancePrice(summaryReport.getBalanceAmount()/summaryReport.getBalanceCount());
            list.get(i).setImgPath(imgPath);
            if (rst == null || rst.size() <= 0) {
                summaryReport.setImgPath(imgPath);
                rst.add(summaryReport);
            } else {
                for (int j = 0; j < rst.size(); j++) {
                    boolean isNoN=false;
                    if (rst.get(j).getInstorageCount()==null){
                        rst.get(j).setInstorageCount((long) 0);
                    }
                    if (rst.get(j).getInstorageAmount()==null){
                        rst.get(j).setInstorageAmount(0.0);
                    }
                    if (rst.get(j).getOutstorageCount()==null){
                        rst.get(j).setOutstorageCount((long) 0);
                    }
                    if (rst.get(j).getOutstorageAmount()==null){
                        rst.get(j).setOutstorageAmount(0.0);
                    }
                    if (rst.get(j).getBeginningOutCount() == null) {
                        rst.get(j).setBeginningOutCount((long) 0);
                    }
                    if (rst.get(j).getBeginningInCount() == null) {
                        rst.get(j).setBeginningCount((long) 0);
                    }
                    if (rst.get(j).getBeginningInAmount() == null) {
                        rst.get(j).setBeginningInAmount(0.0);
                    }
                    if (rst.get(j).getBeginningOutAmount() == null) {
                        rst.get(j).setBeginningOutAmount(0.0);
                    }
                    if (rst.get(j).getBeginningAmount() == null) {
                        rst.get(j).setBeginningAmount(0.0);
                    }
//                    if (rst.get(j).getBeginningCount() == null) {
//                        rst.get(j).setBeginningCount((long) 0);
//                    }
//                    if (rst.get(j).getInstorageAmount() == null) {
//                        rst.get(j).setInstorageAmount(0.0);
//                    }
//                    if (rst.get(j).getInstoragePrice() == null) {
//                        rst.get(j).setInstoragePrice(0.0);
//                    }
                    //初期数量，金额,得到该单据开始日期前的所有出入库数量、出入库金额的和
                    SummaryReport summaryReport2 = summaryReportDao.getCountByDate(beginDate, rst.get(j).getConsumablesCode(),rst.get(j).getWarehouseCode());
                    if (consumablesCode.equals(rst.get(j).getConsumablesCode())
                            && warehouseCode.equals(rst.get(j).getWarehouseCode())) {    //重复的就计算;

                        //入库数量，金额
                        rst.get(j).setInstorageCount(rst.get(j).getInstorageCount() + list.get(i).getInstorageCount());
                        rst.get(j).setInstorageAmount(rst.get(j).getInstorageAmount() + list.get(i).getInstorageAmount());
                        if (rst.get(j).getInstorageCount() <= 0) {
                            rst.get(j).setInstoragePrice(0.0);
                        } else {
                            rst.get(j).setInstoragePrice(rst.get(j).getInstorageAmount() / rst.get(j).getInstorageCount());
                        }
                        //出库数量，金额
                        rst.get(j).setOutstorageCount(rst.get(j).getOutstorageCount() + list.get(i).getOutstorageCount());
                        rst.get(j).setOutstorageAmount(rst.get(j).getOutstorageAmount() + list.get(i).getOutstorageAmount());
                        if (rst.get(j).getOutstorageCount() <= 0) {
                            rst.get(j).setOutstoragePrice(0.0);
                        } else {
                            rst.get(j).setOutstoragePrice(rst.get(j).getOutstorageAmount() / rst.get(j).getOutstorageCount());
                        }
                        //结余数量
                        rst.get(j).setBalanceCount(rst.get(j).getBeginningCount()+rst.get(j).getInstorageCount()-rst.get(j).getOutstorageCount());
                        rst.get(j).setBalanceAmount(  rst.get(j).getBeginningAmount()+rst.get(j).getInstorageAmount()- rst.get(j).getOutstorageAmount());
                        rst.get(j).setBalancePrice(rst.get(j).getBalanceAmount()/rst.get(j).getBalanceCount());
                        String imgPath1=amUtilService.getImgPath(rst.get(j).getConsumablesCode());//获取照片路径
//                        AmWarehouse amWarehouse=new AmWarehouse();
//                        amWarehouse.setWarehouseCode(rst.get(j).getWarehouseCode());
//                        amWarehouse=amWarehouseService.get(amWarehouse);
//                        rst.get(j).setWarehouseName(amWarehouse.getWarehouseName());
                        rst.get(j).setImgPath(imgPath1);

                        prossesBeging(summaryReport2, rst.get(j));
                        break;

                    }
                    //如果都为0不添加
                    if (rst.get(j).getBeginningCount()==0&&rst.get(j).getOutstorageCount()==0&&rst.get(j).getInstorageCount()==0&&rst.get(j).getBalanceCount()==0
                            &&rst.get(j).getBeginningAmount()==0&&rst.get(j).getInstorageAmount()==0&&rst.get(j).getOutstorageAmount()==0&&rst.get(j).getBalanceAmount()==0){
                        isNoN=true;
                    }
                    String imgPath1=amUtilService.getImgPath(rst.get(j).getConsumablesCode());//获取照片路径
                    rst.get(j).setImgPath(imgPath1);
                    prossesBeging(summaryReport1, rst.get(j));
                        //结余数量
                        rst.get(j).setBalanceCount(rst.get(j).getBeginningCount()+rst.get(j).getInstorageCount()-rst.get(j).getOutstorageCount());
                        rst.get(j).setBalanceAmount(  rst.get(j).getBeginningAmount()+rst.get(j).getInstorageAmount()- rst.get(j).getOutstorageAmount());
                        rst.get(j).setBalancePrice(rst.get(j).getBalanceAmount()/rst.get(j).getBalanceCount());


                    if (j==rst.size()-1&&!isNoN){
                        summaryReport.setBalanceCount(summaryReport.getBeginningCount()+summaryReport.getInstorageCount()-summaryReport.getOutstorageCount());
                        summaryReport.setBalanceAmount(  summaryReport.getBeginningAmount()+summaryReport.getInstorageAmount()- summaryReport.getOutstorageAmount());
                        summaryReport.setBalancePrice(summaryReport.getBalanceAmount()/summaryReport.getBalanceCount());
                        rst.add(summaryReport);
                        break;
					}

                }

            }

        }
        return rst;
    }

    private void prossesBeging(SummaryReport summaryReport1,SummaryReport summaryReport ) {
        if (summaryReport1 != null) {
            if (summaryReport1.getBeginningOutCount() == null) {
                summaryReport1.setBeginningOutCount((long) 0);
            }
            if (summaryReport1.getBeginningInCount() == null) {
                summaryReport1.setBeginningInCount((long) 0);
            }
            if (summaryReport1.getBeginningInAmount() == null) {
                summaryReport1.setBeginningInAmount(0.0);
            }
            if (summaryReport1.getBeginningOutAmount() == null) {
                summaryReport1.setBeginningOutAmount(0.0);
            }

            summaryReport.setBeginningCount(summaryReport1.getBeginningInCount() - summaryReport1.getBeginningOutCount());
            summaryReport.setBeginningAmount(summaryReport1.getBeginningInAmount() - summaryReport1.getBeginningOutAmount());
            if (summaryReport.getBeginningCount() <= 0) {
                summaryReport.setBeginningPrice(0.0);
            } else {
                summaryReport.setBeginningPrice(summaryReport.getBeginningAmount() / summaryReport.getBeginningCount());
            }
        }else {
            summaryReport.setBeginningCount((long) 0);
            summaryReport.setBeginningAmount(0.0);
            summaryReport.setBeginningPrice(0.0);
        }
    }


}



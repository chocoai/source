/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.fz.report.dao.FzDepartmentRecordDao;
import com.jeesite.modules.fz.report.entity.FzDepartmentRecord;
import com.jeesite.modules.fz.report.entity.FzDeptData;
import com.jeesite.modules.fz.report.entity.FzDeptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * 梵赞部门的赞赏数据Service
 *
 * @author easter
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class FzDepartmentRecordService extends CrudService<FzDepartmentRecordDao, FzDepartmentRecord> {
    @Autowired
    private FzDepartmentRecordDao fzDepartmentRecordDao;
    @Autowired
    private DingDepartmentService dingDepartmentService;

    /**
     * 获取单条数据
     *
     * @param fzDepartmentRecord
     * @return
     */
    @Override
    public FzDepartmentRecord get(FzDepartmentRecord fzDepartmentRecord) {
        return super.get(fzDepartmentRecord);
    }


    /**
     * 保存数据（插入或更新）
     *
     * @param fzDepartmentRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void save(FzDepartmentRecord fzDepartmentRecord) {
        super.save(fzDepartmentRecord);
    }

    /**
     * 更新状态
     *
     * @param fzDepartmentRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(FzDepartmentRecord fzDepartmentRecord) {
        super.updateStatus(fzDepartmentRecord);
    }

    /**
     * 删除数据
     *
     * @param fzDepartmentRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(FzDepartmentRecord fzDepartmentRecord) {
        super.delete(fzDepartmentRecord);
    }

    /**
     * 查出一级部门的赞赏数据
     *
     * @return
     */
    //后面我再用jdk1.8新特性来优化
    public List<FzDepartmentRecord> findByMonth(FzDepartmentRecord fzDepartmentRecord) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String month = fzDepartmentRecord.getMonth();
        if (month == null || "".equals(month)) {
            fzDepartmentRecord.setMonth(format.format(new Date()));
        } else {
            fzDepartmentRecord.setMonth(fzDepartmentRecord.getMonth() + "-01 00:00:00");
        }
        Date startTime = TimeUtils.getBeginDayOfMonth();
        Date endTime = TimeUtils.getEndDayOfMonth();
        month = fzDepartmentRecord.getMonth();
        if (!"".equals(month)) {
            startTime = TimeUtils.getBeginOfMonthByDate(format.parse(month));
            endTime = TimeUtils.getEndDayOfMonthByDate(startTime);
        }
        List<FzDeptData> list = fzDepartmentRecordDao.findByMonth(startTime, endTime);
        Map<String, List<FzDeptData>> result = new HashMap<>();      //放同一赠送部门
        Map<String, List<FzDeptData>> praiserMap = new HashMap<>();  //放同一获赞部门的map
        if (list != null && list.size() > 0) {
            for (FzDeptData fzDeptData : list) {
                String presenterDepartment = fzDeptData.getPresenterDepartment();
                String praiserDepartment = fzDeptData.getPraiserDepartment();
                if (!"加盟体系".equals(praiserDepartment) && !"加盟体系".equals(presenterDepartment)) {
                    //得到map中部门为key的集合
                    List<FzDeptData> fzDeptDataList = result.get(presenterDepartment);
                    List<FzDeptData> praiserList = praiserMap.get("praiser" + praiserDepartment);
                    if (fzDeptDataList == null) {
                        //创建一个集合,并且把集合放进map中
                        fzDeptDataList = new ArrayList<>();
                        result.put(presenterDepartment, fzDeptDataList);
                    }
                    if (praiserList == null) {
                        praiserList = new ArrayList<>();
                        praiserMap.put("praiser" + praiserDepartment, praiserList);
                    }
                    //每次都放进map对应key得到的集合中
                    fzDeptDataList.add(fzDeptData);
                    praiserList.add(fzDeptData);
                }

            }
        }
        //最爱用的赞赏类型
        Map<String, String> fzTypeTime = this.findFzTypeTime(startTime, endTime);
        List<FzDepartmentRecord> recordList = new ArrayList<>();
        Set<String> strings = result.keySet();
        for (String str : strings) {
            if (!"加盟体系".equals(str)) {
                recordList.add(getFzDepartmentRecord(result.get(str), praiserMap.get("praiser" + str), fzTypeTime, month));
            }
        }
        return recordList;
    }

    /**
     * 找出各个一级部门的赞赏类型的数据
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    private Map<String,String> findFzTypeTime(Date startTime, Date endTime) throws Exception {
        //得到了一级部门赞赏的各种使用类型以及使用次数
        List<FzDeptType> fzTypeTime = fzDepartmentRecordDao.findFzTypeTime(startTime, endTime);
        //设置一个map来key:以及部门 value:使用最多的类型以及数字,可能会有并列
        Map<String, List<FzDeptType>> map = new HashMap<>();
        //如果是月初或者下个月这种就是空数据
        if (fzTypeTime == null || fzTypeTime.size() == 0) {
            return null;
        }
        //所有一级部门集合
        for (FzDeptType fzDeptType : fzTypeTime) {
            String dept = fzDeptType.getDept();
            //这个是放改部门最多赞赏类型的对象的
            List<FzDeptType> list = map.get(dept);
            if(list == null || list.size() == 0){
                list = new ArrayList<>();
                list.add(fzDeptType);
                map.put(dept,list);
            }
            //如果map存放的集合里面出现使用次数比这个集合外的要少,那么清空集合并且将这个元素加进去
            if(list.get(0).getUseTime() < fzDeptType.getUseTime()){
                list.clear();
                list.add(fzDeptType);
            }
            if(list.get(0).getUseTime() == fzDeptType.getUseTime()){
                if(!fzDeptType.getUseType().equals(list.get(0).getUseType())){
                    list.add(fzDeptType);
                }
            }
        }
        //这个map key:部门  value:类型拼接字符串
        Map<String,String> typeMap = new HashMap<>();
        Set<Map.Entry<String, List<FzDeptType>>> entries = map.entrySet();
        for (Map.Entry<String, List<FzDeptType>> entry : entries) {
            String key = entry.getKey();
            List<FzDeptType> value = entry.getValue();
            //拼接赞赏类型的字符串
            String type = "";
            for (FzDeptType fzDeptType : value) {
                type += fzDeptType.getUseType()+ " ";
            }
            typeMap.put(key,type);
        }
        return typeMap;
    }

    /**
     * 把单一部门的所有赞赏数据转成FzDepartmentRecord
     *
     * @param list  同一赠送部门
     * @param list2 同一获赞部门
     * @return
     */
    public FzDepartmentRecord getFzDepartmentRecord(List<FzDeptData> list, List<FzDeptData> list2, Map<String, String> fzTypeTime, String month) {
        FzDepartmentRecord fzDepartmentRecord = new FzDepartmentRecord();
        double presenterAll = 0.00;   //赞了全公司包括部门内多少次
        double presenterOut = 0.00;   //赞了全公司不包括本部门多少次
        double number = 0.00;         //赞了部门内多少次
        //格式化小数,不足的补0
        DecimalFormat df = new DecimalFormat("0.00");
        String maxDept = "";             //最多赞赏部门的名字
        String minDept = "";             //最少赞赏部门的名字
        int max = 0;                    //跨部门最多赞赏次数
        int min = 0;                    //跨部门最少赞赏次数
        double avgOutPraiser = 0.00;
        double sumPraiser = 0.00;
        double avgMinDept = 0.00;
        double avgMaxDept = 0.00;
        double avgIn = 0.00;
        double sumPresenter = 0.00;
        double outPresenter = 0.00;
        //所有一级部门集合
        List<DingDepartment> parentCodeList = dingDepartmentService.getChiddeptsByParentCode("1");
        //去除加盟体系之外的所有一级部门的名字
        List<String> deptLevelName = dingDepartmentService.getDeptLevelName();
        //赞过的部门的集合
        List<String> stringList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (FzDeptData fzDeptData : list) {
                //赠送者部门
                String presenterDepartment = fzDeptData.getPresenterDepartment();
                //获赞者部门
                String praiserDepartment = fzDeptData.getPraiserDepartment();
                if (presenterDepartment.equals(praiserDepartment)) {
                    number = fzDeptData.getNumber();
                } else {
                    //赞赏次数
                    int number1 = fzDeptData.getNumber();
                    presenterOut += number1;
                    max = max > number1 ? max : number1;
                    //没有把所有一级部门都赞了
                    if (list.size() < parentCodeList.size()) {
                        min = 0;
                    } else {
                        min = min < number1 ? min : number1;
                    }
                }
            }

            for (FzDeptData fzDeptData : list) {
                String praiserDepartment = fzDeptData.getPraiserDepartment();
                //去除掉本身部门
                if (!fzDeptData.getPresenterDepartment().equals(praiserDepartment)) {
                    if (max == fzDeptData.getNumber()) {
                        if (!"加盟体系".equals(praiserDepartment)) {
                            maxDept += praiserDepartment + " ";
                        }
                    }
                    if (min == fzDeptData.getNumber()) {
                        if (!"加盟体系".equals(praiserDepartment)) {
                            if (!"无".equals(praiserDepartment)) {
                                minDept += praiserDepartment + " ";
                            }
                        }
                    }
                }
                if (!"无".equals(praiserDepartment)) {
                    stringList.add(praiserDepartment);
                }
            }
        }
        if (min == 0) {
            deptLevelName.removeAll(stringList);
            for (String s : deptLevelName) {
                if (!"无".equals(s)) {
                    minDept += s + " ";
                }
            }
        }
        if (max == 0) {
            maxDept = "无";
            minDept = "无";
        }
        presenterAll = number + presenterOut;

        int sumOut = 0;     //总跨部门内获赞次数
        double sumAll = 0;     //全公司获赞次数
        if (list2 != null && list2.size() > 0) {
            for (FzDeptData fzDeptData : list2) {
                String praiserDepartment = fzDeptData.getPraiserDepartment();
                String presenterDepartment = fzDeptData.getPresenterDepartment();
                if (!praiserDepartment.equals(presenterDepartment)) {
                    sumOut += fzDeptData.getNumber();
                }
            }
            sumAll = sumOut + number;
        }
        FzDeptData fzDeptData = list.get(0);
        if (fzDeptData != null) {
            String presenterDepartment = fzDeptData.getPresenterDepartment();
            //得到该赠送的一级部门下有多少人
            float numberByName = dingDepartmentService.getNumberByName(presenterDepartment);
            if (numberByName != 0) {
                avgMinDept = (float) min / numberByName;
                avgMaxDept = (float) max / numberByName;
                avgIn = (float) number / numberByName;
                sumPresenter = (float) presenterAll / numberByName;
                outPresenter = (float) presenterOut / numberByName;
                sumPraiser = (float) sumAll / numberByName;
                avgOutPraiser = (float) sumOut / numberByName;
            }
            fzDepartmentRecord.setDepartment(presenterDepartment);
            fzDepartmentRecord.setMaxDepartment(maxDept);
            fzDepartmentRecord.setMinDepartment(minDept);
            fzDepartmentRecord.setAvgOutPraiser(new Double(df.format(avgOutPraiser)));
            fzDepartmentRecord.setSumPraiser(new Double(df.format(sumPraiser)));
            fzDepartmentRecord.setAvgMin(new Double(df.format(avgMinDept)));
            fzDepartmentRecord.setAvgMax(new Double(df.format(avgMaxDept)));
            fzDepartmentRecord.setAvgIn(new Double(df.format(avgIn)));
            fzDepartmentRecord.setSumPresenter(new Double(df.format(sumPresenter)));
            fzDepartmentRecord.setAvgOutPresenter(new Double(df.format(outPresenter)));
            fzDepartmentRecord.setMonth(month);
            String s = "";   //最爱的类型
            String tag = ""; //用的梵赞类型
            if (fzTypeTime == null || fzTypeTime.size() == 0) {
                tag = "无";
            }else{
                tag = fzTypeTime.get(fzDeptData.getPresenterDepartment());
                if(tag == null || tag == ""){
                    tag = "无";
                }
            }
            /*for (Map<String, String> stringStringMap : fzTypeTime) {
                s = stringStringMap.get("name");
                if (presenterDepartment.equals(s)) {
                    tag = stringStringMap.get("tag");
                    break;
                } else {
                    tag = "无";
                }
            }*/
            fzDepartmentRecord.setMaxType(tag);
        }
        return fzDepartmentRecord;
    }
}
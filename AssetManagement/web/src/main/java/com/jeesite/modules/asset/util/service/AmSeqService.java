package com.jeesite.modules.asset.util.service;

import com.jeesite.modules.asset.util.dao.AmSeqDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AmSeqService {
    @Autowired
    private AmSeqDao dao;

    //自动生成code
    @Transactional
    public  String getCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%04d", i);//字符串格式化
        code=code+strNo;
        return code;

    }
    //获取页面显示的code
    @Transactional
    public  String getSeq(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        Integer i= dao.getSeq(code);
        if (i==null){
            i=0;
        }

        i=i+1;
        String strNo = String.format("%04d", i);//字符串格式化
        code=code+strNo;
        return code;

    }

    /**
     * 订单管理单据编号
     * @param perfix
     * @return
     */
    @Transactional
    public  String getOrderCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%04d", i);//字符串格式化
        code=code+strNo;
        return code;

    }

    /**
     * 导购活动管理用
     * @param perfix
     * @return
     */
    @Transactional
    public  String getActivityCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%05d", i);//字符串格式化
        code=code+strNo;
        return code;

    }

    /**
     * 获取页面显示的code
     * @param perfix
     * @return
     */
    @Transactional
    public  String getSeqCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        Integer i= dao.getSeq(code);
        if (i==null){
            i=0;
        }

        i=i+1;
        String strNo = String.format("%04d", i);//字符串格式化
        code=code+strNo;
        return code;
    }

    /**
     * 订单管理单据编号
     * @param perfix
     * @return
     */
    @Transactional
    public  String getOrderApply(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%03d", i);//字符串格式化
        code=code+strNo;
        return code;

    }

    /**
     * 获取订单变更申请页面显示的code
     * @param perfix
     * @return
     */
    @Transactional
    public  String getApplyCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        Integer i= dao.getSeq(code);
        if (i==null){
            i=0;
        }

        i=i+1;
        String strNo = String.format("%03d", i);//字符串格式化
        code=code+strNo;
        return code;
    }

    /**
     * 绩效加扣分用
     * @param perfix
     * @return
     */
    @Transactional
    public  String getCode(String perfix, String format){
        return getCode(perfix, format, 3);
    }



    /**
     * 绩效加扣分用
     * @param perfix
     * @return
     */
    @Transactional
    public  String getCode(String perfix, String format, int numberLength){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%0"+numberLength+"d", i);//字符串格式化
        code=code+strNo;
        return code;
    }

    /**
     * 绩效加扣分用
     * @param perfix
     * @return
     */
    @Transactional
    public  String getAchCode(String perfix){
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String dateString = formatter.format(now);
        String code=perfix+dateString;
        int i= dao.getInsertSeq(code);
        String strNo = String.format("%03d", i);//字符串格式化
        code=code+strNo;
        return code;
    }

}

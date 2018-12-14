/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.amspecimen.dao.AmSpecimenDao;
import com.jeesite.modules.asset.amspecimen.dao.AmSpecimenProductDao;
import com.jeesite.modules.asset.amspecimen.dao.AmSpecimenRecordDao;
import com.jeesite.modules.asset.amspecimen.dao.AmSpecimenScheduleDao;
import com.jeesite.modules.asset.amspecimen.entity.*;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 构建样品进度Service
 *
 * @author mclaran
 * @version 2018-06-29
 */
@Service
@Transactional(readOnly = true)
public class AmSpecimenService extends CrudService<AmSpecimenDao, AmSpecimen> {
    //    private final String SAVE_PREFIX = "D:/";
    @Value("${file.baseDir}")
    private String baseDir;
    @Value("${RM_PREFIX_URL}")
    private String RM_PREFIX_URL;
    @Autowired
    private AmSpecimenScheduleDao amSpecimenScheduleDao;

    @Autowired
    private AmSpecimenProductDao amSpecimenProductDao;
    @Autowired
    private AmSpecimenRecordDao amSpecimenRecordDao;
    @Autowired
    private AmSpeciQualificationsService amSpeciQualificationsService;
    @Autowired
    private AmSpecimenDao amSpecimenDao;

    /**
     * 获取单条数据
     *
     * @param amSpecimen
     * @return
     */
    @Override
    public AmSpecimen get(AmSpecimen amSpecimen) {
        AmSpecimen entity = super.get(amSpecimen);
        if (entity != null) {
            AmSpecimenRecord amSpecimenRecord = new AmSpecimenRecord(entity);
            amSpecimenRecord.setStatus(AmSpecimenRecord.STATUS_NORMAL);
            entity.setAmSpecimenRecordList(amSpecimenRecordDao.findList(amSpecimenRecord));
            AmSpecimenSchedule amSpecimenSchedule = new AmSpecimenSchedule(entity);
            amSpecimenSchedule.setStatus(AmSpecimenSchedule.STATUS_NORMAL);
            entity.setAmSpecimenScheduleList(amSpecimenScheduleDao.findList(amSpecimenSchedule));
            AmSpecimenProduct amSpecimenProduct = new AmSpecimenProduct(entity);
            amSpecimenProduct.setStatus(AmSpecimenProduct.STATUS_NORMAL);
            entity.setAmSpecimenProductList(amSpecimenProductDao.findList(amSpecimenProduct));
        }
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param page       分页对象
     * @param amSpecimen
     * @return
     */
    @Override
    public Page<AmSpecimen> findPage(Page<AmSpecimen> page, AmSpecimen amSpecimen) {
        return super.findPage(page, amSpecimen);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param amSpecimen
     */
//	@Override
    @Transactional(readOnly = false)
    public void save(AmSpecimen amSpecimen) {
        super.save(amSpecimen);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String savePath = format.format(new Date());
        // 保存 AmSpecimen子表
        for (AmSpecimenRecord amSpecimenRecord : amSpecimen.getAmSpecimenRecordList()) {
            if (amSpecimen.getIsNewRecord()) {
                amSpecimenRecord.setRemark("创建单据");
                amSpecimenRecord.setOperator(UserUtils.getUser().getLoginCode());
                amSpecimenRecord.setOperatorTime(new Date());
            }
            if (!AmSpecimenRecord.STATUS_DELETE.equals(amSpecimenRecord.getStatus())) {
                amSpecimenRecord.setSpecimenCode(amSpecimen);
                if (amSpecimenRecord.getIsNewRecord()) {
                    amSpecimenRecord.preInsert();
                    amSpecimenRecordDao.insert(amSpecimenRecord);
                } else {
                    amSpecimenRecord.preUpdate();
                    amSpecimenRecordDao.update(amSpecimenRecord);
                }
            } else {
                amSpecimenRecordDao.delete(amSpecimenRecord);
            }
        }

        // 保存 AmSpecimen子表
        for (AmSpecimenSchedule amSpecimenSchedule : amSpecimen.getAmSpecimenScheduleList()) {
            if (!AmSpecimenSchedule.STATUS_DELETE.equals(amSpecimenSchedule.getStatus())) {
                amSpecimenSchedule.setSpecimenCode(amSpecimen);
                if (amSpecimenSchedule.getDate() != null && amSpecimenSchedule.getDate().length() > 0) {
//                    amSpecimenSchedule.setDate(date);
                }
                if (amSpecimenSchedule.getStartDate() != null && amSpecimenSchedule.getStartDate().length() > 0) {
                    //Date date=new Date("0000-00-00 00:00:00");
//                    amSpecimenSchedule.setStartDate(date);
                }
                if (amSpecimenSchedule.getIsNewRecord()) {
                    amSpecimenSchedule.preInsert();
                    amSpecimenScheduleDao.insert(amSpecimenSchedule);
                } else {
                    amSpecimenSchedule.preUpdate();
                    amSpecimenScheduleDao.update(amSpecimenSchedule);
                }
            } else {
                amSpecimenScheduleDao.delete(amSpecimenSchedule);
            }
        }
        AmSpeciQualifications amSpeciQualifications = null;
        // 保存 AmSpecimen子表
        for (AmSpecimenProduct amSpecimenProduct : amSpecimen.getAmSpecimenProductList()) {
            if (!AmSpecimenProduct.STATUS_DELETE.equals(amSpecimenProduct.getStatus())) {
                amSpecimenProduct.setSpecimenCode(amSpecimen);
                if (amSpecimenProduct.getIsNewRecord()) {
                    amSpecimenProduct.preInsert();
                    amSpecimenProductDao.insert(amSpecimenProduct);
                } else {
                    amSpecimenProduct.preUpdate();
                    amSpecimenProductDao.update(amSpecimenProduct);
                }
            } else {
                amSpecimenProductDao.delete(amSpecimenProduct);
            }
        }
    }

    /**
     * 更新状态
     *
     * @param amSpecimen
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(AmSpecimen amSpecimen) {
        super.updateStatus(amSpecimen);
    }

    /**
     * 删除数据
     *
     * @param amSpecimen
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(AmSpecimen amSpecimen) {
        super.delete(amSpecimen);
        AmSpecimenRecord amSpecimenRecord = new AmSpecimenRecord();
        amSpecimenRecord.setSpecimenCode(amSpecimen);
        amSpecimenRecordDao.delete(amSpecimenRecord);
        AmSpecimenSchedule amSpecimenSchedule = new AmSpecimenSchedule();
        amSpecimenSchedule.setSpecimenCode(amSpecimen);
        amSpecimenScheduleDao.delete(amSpecimenSchedule);
        AmSpecimenProduct amSpecimenProduct = new AmSpecimenProduct();
        amSpecimenProduct.setSpecimenCode(amSpecimen);
        amSpecimenProductDao.delete(amSpecimenProduct);
    }


    /**
     * 删除数据
     *
     * @param base64String
     * @param qualificationName
     * @param savePath
     * @param profileSurfix
     */
    @Transactional(readOnly = false)
    public boolean uploadFile(String code, String base64String, String qualificationName, String savePath, String profileSurfix, AmSpeciQualifications amSpeciQualifications) {
        boolean rst = false;
        if (base64String == null) {
            return rst;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //解码过程，即将base64字符串转换成二进制流
        String pathString = baseDir + "/" + savePath;
        String name = qualificationName.substring(0, qualificationName.indexOf("."));
        try {
            // 将字符串转换成二进制，用于显示图片
            byte[] imageByte = decoder.decodeBuffer(base64String);
            InputStream in = new ByteArrayInputStream(imageByte);
            File file = new File(pathString);
            if (!file.exists()) {
                file.mkdirs();
            }
            pathString = baseDir + "/" + savePath + qualificationName;
            File descFile = new File(pathString);
            int i = 1;
            //若文件存在重命名在文件名后面加一
            while (descFile.exists()) {
                String newFilename = name + "(" + i + ")" + profileSurfix;
                String parentPath = descFile.getParent();
                descFile = new File(parentPath + File.separator + newFilename);
                i++;
            }

            FileOutputStream fos = new FileOutputStream(descFile);
            byte[] b = new byte[20482];
            int nRead = 0;
            while ((nRead = in.read(b)) != -1) {
                fos.write(b, 0, nRead);
            }
            fos.flush();
            fos.close();
            in.close();
            amSpeciQualifications.setCode(code);
            amSpeciQualifications.setProfileSurfix(profileSurfix);
            amSpeciQualifications.setQualificationName(qualificationName);
            amSpeciQualifications.setSavePath(savePath);
//            String fix=baseDir;
//            String a=((descFile.getPath()).replace(fix, "\\img\\"));
            amSpeciQualifications.setFileUrl(RM_PREFIX_URL + savePath + qualificationName);
            amSpeciQualifications.setIsNewRecord(true);
            amSpeciQualifications.setCreateTime(new Date());
            amSpeciQualificationsService.save(amSpeciQualifications);
            rst = true;
        } catch (Exception e) {
//            e.printStackTrace();
            rst = false;
        }

        return rst;
    }

    /**
     * 保存照片
     *
     * @param
     * @param qualificationName
     * @param
     * @param profileSurfix
     */
    @Transactional(readOnly = false)
    public boolean uploadFile1(String code, String qualificationName, String profileSurfix,String filePath, AmSpeciQualifications amSpeciQualifications) {
        boolean rst = false;
        amSpeciQualifications.setCode(code);
        amSpeciQualifications.setProfileSurfix(profileSurfix);
        amSpeciQualifications.setQualificationName(qualificationName);
        amSpeciQualifications.setSavePath(filePath);
        amSpeciQualifications.setIsNewRecord(true);
        amSpeciQualifications.setCreateTime(new Date());
        amSpeciQualificationsService.save(amSpeciQualifications);
        rst = true;
        return rst;
}

    @Transactional(readOnly = false)
    public boolean deleFile(String fileUrl, String type) {
        boolean rst = false;
//        String pathString = SAVE_PREFIX + savePath + qualificationName;
        String url = null;
        if ("file".equals(type)) {
            url = (baseDir + "/").replaceAll(RM_PREFIX_URL, "") + fileUrl;
        }
        if ("img".equals(type)) {
            url = (baseDir + "/").replaceAll(RM_PREFIX_URL, "") + fileUrl;
        }
        File file = new File(url);
        if (file.exists()) {
            file.delete();
            rst = true;
        }

        return rst;
    }

    @Transactional(readOnly = false)
    public void updateDocumentStatus(AmSpecimen amSpecimen) {
        super.save(amSpecimen);
    }

    @Transactional(readOnly = false)
    public void saveRecord(AmSpecimenRecord amSpecimenRecord, AmSpecimen amSpecimen) {
        amSpecimenRecord.setSpecimenCode(amSpecimen);
        amSpecimenRecordDao.insert(amSpecimenRecord);
    }

    @Transactional(readOnly = false)
    public List findScheduleList(AmSpecimenSchedule amSpecimenSchedule) {
        return amSpecimenScheduleDao.findList(amSpecimenSchedule);
    }

    /**
     * 物理删除数据
     *
     * @param specimenCode
     */
    @Transactional(readOnly = false)
    public void deleteDb(String specimenCode) {
        //删除表体和关联表
        amSpecimenDao.deleteDb(specimenCode);
        amSpecimenScheduleDao.deleteDb(specimenCode);
        amSpecimenProductDao.deleteDb(specimenCode);
        amSpecimenRecordDao.deleteDb(specimenCode);
        //删除照片和附件
        AmSpecimen entity = new AmSpecimen();
        entity.setSpecimenCode(specimenCode);
        entity = super.get(entity);
        if (entity != null && ParamentUntil.isBackList(entity.getAmSpecimenProductList())) {
            for (int i = 0; i < entity.getAmSpecimenProductList().size(); i++) {
                String proDuctCode = entity.getAmSpecimenProductList().get(i).getCode();
                AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
//                 amSpeciQualifications.setTypeName(typeName);
                amSpeciQualifications.setCode(proDuctCode);
                List<AmSpeciQualifications> amSpeciQualificationsList = amSpeciQualificationsService.findList(amSpeciQualifications);
                if (ParamentUntil.isBackList(amSpeciQualificationsList)) {
                    for (int j = 0; j < amSpeciQualificationsList.size(); j++) {
                        this.deleFile(amSpeciQualificationsList.get(j).getFileUrl(), amSpeciQualificationsList.get(j).getTypeName());
                        amSpeciQualificationsService.delete(amSpeciQualificationsList.get(j));

                    }

                }
            }
//        amSpecimenProductDao.deleteDb()   删除照片
        }
    }

    /**
     * 获取部门
     *
     * @param offerCode
     */
    @Transactional(readOnly = false)
    public List<Office> getOfferList(String offerCode) {
        List<Office> list = amSpecimenDao.getOfferList(offerCode);
        return list;
    }

    @Transactional(readOnly = false)
    public void saveRecords(AmSpecimen amSpecimen, String documentStatusSH) {
        //进入审核反审核、审核计划、反审核计划操作
        if (ParamentUntil.isBackString(documentStatusSH)) {
            AmSpecimenRecord amSpecimenRecord = null;


            if ("1".equals(documentStatusSH)) {                                          //审核操作
                amSpecimen.setBillsStatus("1");
                this.updateDocumentStatus(amSpecimen);                     //更新状态

                amSpecimenRecord = new AmSpecimenRecord();                                //保存操作记录
                amSpecimenRecord.setRemark("审核单据");
                amSpecimenRecord.setOperator(UserUtils.getUser().getLoginCode());
                amSpecimenRecord.setOperatorTime(new Date());
                amSpecimenRecord.setIsNewRecord(true);
                this.saveRecord(amSpecimenRecord, amSpecimen);
            }
            if ("2".equals(documentStatusSH)) {                                          //反审核操作
                amSpecimen.setBillsStatus("2");
                this.updateDocumentStatus(amSpecimen);                     //更新状态
                amSpecimenRecord = new AmSpecimenRecord();                                //保存操作记录
                amSpecimenRecord.setRemark("反审核单据");
                amSpecimenRecord.setOperator(UserUtils.getUser().getLoginCode());
                amSpecimenRecord.setOperatorTime(new Date());
                amSpecimenRecord.setIsNewRecord(true);
                this.saveRecord(amSpecimenRecord, amSpecimen);
            }
            if ("3".equals(documentStatusSH)) {                                          //审核计划操作
                amSpecimen.setBillsStatus("4");
                this.updateDocumentStatus(amSpecimen);                     //更新状态
                amSpecimenRecord = new AmSpecimenRecord();                                //保存操作记录
                amSpecimenRecord.setRemark("安排生成计划");
                amSpecimenRecord.setOperator(UserUtils.getUser().getLoginCode());
                amSpecimenRecord.setOperatorTime(new Date());
                amSpecimenRecord.setIsNewRecord(true);
                this.saveRecord(amSpecimenRecord, amSpecimen);
            }
            if ("4".equals(documentStatusSH)) {                                          //反审核计划操作
                amSpecimen.setBillsStatus("1");
                this.updateDocumentStatus(amSpecimen);                     //更新状态
                amSpecimenRecord = new AmSpecimenRecord();                                //保存操作记录
                amSpecimenRecord.setRemark("取消安排生产计划");
                amSpecimenRecord.setOperator(UserUtils.getUser().getLoginCode());
                amSpecimenRecord.setOperatorTime(new Date());
                amSpecimenRecord.setIsNewRecord(true);
                this.saveRecord(amSpecimenRecord, amSpecimen);
            }
        }
    }

    @Transactional
    public boolean deleteDbs(AmSpecimen amSpecimen, String ids) {
        boolean isShStutrs = false;
        if (ids != null && ids.length() > 0) {
            String[] codeList = ids.split(",");
            for (int i = 0; i < codeList.length; i++) {
                AmSpecimen amSpecimen1 = new AmSpecimen();
                amSpecimen1.setSpecimenCode(codeList[i]);
                amSpecimen1 = this.get(amSpecimen1);
                if ((amSpecimen1 != null && amSpecimen1.getBillsStatus().equals("0")) || (amSpecimen1 != null && amSpecimen1.getBillsStatus().equals("2"))) {
                    this.deleteDb(codeList[i]);
                } else {
                    isShStutrs = true;
                }
            }
        } else {
            this.deleteDb(amSpecimen.getSpecimenCode());
        }
        return isShStutrs;
    }
}
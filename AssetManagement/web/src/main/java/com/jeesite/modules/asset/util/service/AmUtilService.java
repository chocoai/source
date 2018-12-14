package com.jeesite.modules.asset.util.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.jeesite.common.config.Global;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.consumables.entity.AmLibIodetails;
import com.jeesite.modules.asset.consumables.entity.AmWarehIodetails;
import com.jeesite.modules.asset.consumables.service.AmLibIodetailsService;
import com.jeesite.modules.asset.consumables.service.AmWarehIodetailsService;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;
import com.jeesite.modules.asset.util.dao.AmUtilDao;
import com.jeesite.modules.asset.warehouse.dao.AmWarehouseDao;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class AmUtilService {
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID;
    @Value("${ACCESS_KEY_SECRET}")
    private String ACCESS_KEY_SECRET;
    @Value("${BUCKET}")
    private String BUCKET;
    @Autowired
    private AmWarehouseDao amWarehouseDao;
    @Autowired
    private AmUtilDao amUtilDao;
    @Value("${file.baseDir}")
     private String  baseDir;
    String SYSNOTIFICATION;  //登录端口
    @Autowired
    private AmLibIodetailsService amLibIodetailsService;
    @Autowired
    private AmWarehIodetailsService amWarehIodetailsService;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private AmFileUploadService uploadService;

    @Transactional(readOnly = true)
    public List<AmWarehouse> getWarehouseListByLeaf(String treeLeaf) {
        return amWarehouseDao.getWarehouseListByLeaf(treeLeaf);
    }

    /**
     * 查询字典
     *
     * @param dictLabel
     * @param dictType
     * @return
     */
    public String findDictLabel(String dictLabel, String dictType) {
        return amUtilDao.findDictLabel(dictLabel, dictType);
    }
    public int checkDictValue(String dictValue,String dictType){
        return amUtilDao.checkDictValue(dictValue, dictType);
    }
    public List<DictData> findDictLabels(String dictType) {
        return amUtilDao.findDictLabels( dictType);
    }
    //获取照片路径判断是否存在
    public String getImgPath(String consumablesCode) {
        String imgPath = null;
        FileUpload fileUpload = new FileUpload();
        fileUpload.setBizKey(consumablesCode);
        List<FileUpload> list = fileUploadService.findList(fileUpload);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                FileUpload imgUntil = list.get(i);
//                FileEntity fileEntity=new FileEntity();
//                fileEntity.setFileId();
//                fileEntity=fileEntityService.get(fileEntity);
                if (imgUntil.getFileEntity().getFilePath() != null && imgUntil.getFileEntity().getFilePath().length() > 0) {
                    String url_fix =baseDir;
                    String url = "/userfiles/fileupload" + "/" + imgUntil.getFileEntity().getFilePath() + imgUntil.getFileEntity().getFileId() + "." + imgUntil.getFileEntity().getFileExtension();
                    File file = new File(url_fix + url);
                    // 989689001677406208.jpg
                    if (file.exists()) {
                        imgPath = url;
                        break;
                    } else {
                        continue;

                    }
                }
            }
        }
        return imgPath;
    }
    /**
     * 获取阿里图片路径
     * @param consumablesCode
     * @return
     */
    public String getImgPathAli(String consumablesCode,String bizType) {
        String imgPath =null;
        AmFileUpload amFileUpload=new AmFileUpload();
        amFileUpload.setBizKey(consumablesCode);
        amFileUpload.setBizType(bizType);
        List<AmFileUpload> list=uploadService.findList(amFileUpload);
        if (list != null &&list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                AmFileUpload amFileUpload1 = list.get(i);
          if(amFileUpload1!=null){
              imgPath=amFileUpload1.getFileRealPath();
              break;
          }
            }
        }
        return imgPath;
    }

    /**
     * 查询数据期间
     *
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    public String getSection() {
        String message = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String dataPeriod = format.format(date);
       Date date1 = DateUtils.parseDate(DateUtils.formatDate(date));
        AmPeriodState amPeriodState = amUtilDao.getSection(dataPeriod);
        if (amPeriodState != null) {
            if (amPeriodState.getBeginData().compareTo(date1) <= 0 && amPeriodState.getEndData().compareTo(date1) >= 0) {
                if ("0".equals(amPeriodState.getPeriodStutrs())) {
                    message = "[日期]的数据期间未打开！";
                } else if ("2".equals(amPeriodState.getPeriodStutrs())) {
                    message = "[日期]的数据期间已结账！";
                }
            } else {
                message = "[日期]未设置数据期间！";
            }
        }
        return message;
    }

    /**
     * @param billCode         编码
     * @param billDate         单据日期
     * @param billType         单据类型
     * @param warehouseCode    仓库
     * @param locationCode     库位
     * @param consumablesCode  耗材
     * @param instorageCount   入库数量
     * @param instorageAmount  入库金额
     * @param outstorageCount  出库数量
     * @param outstorageAmount 出库金额
     * @param price            单价
     * @param sign             出入库标志
     * @return
     */
    @Transactional(readOnly = false)
    public void saveDetail(String billCode, Date billDate, String billType, String warehouseCode, String locationCode,
                             String consumablesCode, Long instorageCount, Double instorageAmount, Long outstorageCount,
                           Double outstorageAmount, Double price, String sign) {
        Date date = new Date();
        AmWarehIodetails amWarehIodetails = new AmWarehIodetails();
        amWarehIodetails.setBillCode(billCode);
        amWarehIodetails.setBillDate(billDate);
        amWarehIodetails.setBillType(billType);
        amWarehIodetails.setWarehouseCode(warehouseCode);
        amWarehIodetails.setSign(sign);
        amWarehIodetails.setConsumablesCode(consumablesCode);
        amWarehIodetails.setInstorageAmount(instorageAmount);
        amWarehIodetails.setInstorageCount(instorageCount);
        amWarehIodetails.setOutstorageAmount(outstorageAmount);
        amWarehIodetails.setOutstorageCount(outstorageCount);
        amWarehIodetails.setPrice(price);
        amWarehIodetails.setOperationBy(UserUtils.getUser().getUserCode());
        amWarehIodetails.setOperationDate(date);
        amWarehIodetailsService.save(amWarehIodetails);

        AmLibIodetails amLibIodetails = new AmLibIodetails();
        amLibIodetails.setBillCode(billCode);
        amLibIodetails.setBillDate(billDate);
        amLibIodetails.setBillType(billType);
        amLibIodetails.setWarehouseCode(warehouseCode);
        amLibIodetails.setSign(sign);
        amLibIodetails.setConsumablesCode(consumablesCode);
        amLibIodetails.setInstorageAmount(instorageAmount);
        amLibIodetails.setInstorageCount(instorageCount);
        amLibIodetails.setOutstorageAmount(outstorageAmount);
        amLibIodetails.setOutstorageCount(outstorageCount);
        amLibIodetails.setPrice(price);
        amLibIodetails.setOperationBy(UserUtils.getUser().getUserCode());
        amLibIodetails.setOperationDate(date);
        amLibIodetails.setLocationCode(locationCode);
        amLibIodetailsService.save(amLibIodetails);

    }
    /**
     * 阿里云删除图片
     * @param keys
     */
    public void deletePicAli(List<String> keys){
        if(null!=keys &&keys.size()>0) {
            OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            // 执行删除阿里云文件
            ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(keys));
        }
    }
    @Transactional(readOnly = true)
    public String getConfigValue(String congfigKey) {
        return amUtilDao.getConfigValue(congfigKey);
    }

}

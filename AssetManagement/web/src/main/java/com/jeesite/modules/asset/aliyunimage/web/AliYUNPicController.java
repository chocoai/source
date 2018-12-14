package com.jeesite.modules.asset.aliyunimage.web;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.cloudphoto.model.v20170711.CreatePhotoRequest;
import com.aliyuncs.cloudphoto.model.v20170711.CreatePhotoResponse;
import com.aliyuncs.cloudphoto.model.v20170711.CreateTransactionRequest;
import com.aliyuncs.cloudphoto.model.v20170711.CreateTransactionResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.config.Global;
import com.jeesite.modules.asset.aliyunimage.service.AmAliyunImageService;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.supplier.entity.SupSupplier;
import com.jeesite.modules.asset.supplier.entity.SupSupplierQualifications;
import com.jeesite.modules.asset.supplier.service.SupSupplierQualificationsService;
import com.jeesite.modules.asset.supplier.service.SupSupplierService;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 修复商品分类和商品系列/供应商文件和图片数据
 * @Author Scarlett
 * @Version 2018-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/AliYUNPic/AliYUNPic")
public class AliYUNPicController extends BaseController {
    @Autowired
    private AmAliyunImageService amAliyunImageService;
    @Autowired
    private SupSupplierQualificationsService qualificationsService;
    @Autowired
    private SupSupplierService supSupplierService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private AmFileUploadService service;
    public static final String SAVE_PREFIX = "/uploadfiles/";
    @Value("${STORE_NAME}")
    String STORE_NAME; //PhotoStore的名称
    @Value("${REGION}")
    String REGION; //区域
    @Value("${ACCESS_KEY_ID}")
    String ACCESS_KEY_ID; //密钥Id
    @Value("${ACCESS_KEY_SECRET}")
    String ACCESS_KEY_SECRET;
    @Value("${LIBRARY_ID}")
    String LIBRARY_ID; //照片库标识
    @Value("${ENDPOINT}")
    String ENDPOINT;
    @Value("${DIR}")
    String DIR;
    @Value("${BUCKET}")
    String BUCKET;
    @Value("${file.baseDir}")
    private String  baseDir;
    private String aliyunUrl="https://after-sales-photo.oss-cn-shanghai.aliyuncs.com/stores/after-ales-photo/data/";
    @Autowired
    private AmFileUploadService amFileUploadService;

    /**
     * 修复数据
     * @param request1
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "fixData")
    public String fixData(HttpServletRequest request1){
        String filePath="";
        FileUpload fileUpload = new FileUpload();
        String bizType=request1.getParameter("bizType");
        fileUpload.setBizType(bizType);
       // fileUpload.setId("1021393462397550592");
        List<FileUpload> list = fileUploadService.findList(fileUpload);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                FileUpload imgUntil = list.get(i);
                String url = "/userfiles/fileupload" + "/" + imgUntil.getFileEntity().getFilePath() + imgUntil.getFileEntity().getFileId() + "." + imgUntil.getFileEntity().getFileExtension();
                filePath=baseDir + url;
                File file1 = new File(filePath);
                if (file1.exists()) {
                    DefaultProfile profile = DefaultProfile.getProfile(REGION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                    DefaultAcsClient acsClient = new DefaultAcsClient(profile);
                    FileInputStream inputStream = null;
                    String md5 = "";
                    try {
                        inputStream = new FileInputStream(file1);
                        md5 = DigestUtils.md5Hex(inputStream);
                        inputStream.close();
                    } catch (Exception ex) {
                        logger.error("图片上传阿里云", ex);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //开启上传事务
                    CreateTransactionRequest request = new CreateTransactionRequest();
                    request.setMd5(md5);
                    request.setExt("jpg");
                    request.setLibraryId(LIBRARY_ID);
                    request.setStoreName(STORE_NAME);
                    request.setSize(file1.length());
                    request.setAcceptFormat(FormatType.JSON);

                    //发起请求
                    CreateTransactionResponse response = null;
                    try {
                        response = acsClient.getAcsResponse(request);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                    CreateTransactionResponse.Transaction tnx = response.getTransaction();
                    CreateTransactionResponse.Transaction.Upload upload = tnx.getUpload();

                    //构建OSS客户端上传图片
                    OSSClient ossClient = new OSSClient(upload.getOssEndpoint(), upload.getAccessKeyId(), upload.getAccessKeySecret(), upload.getStsToken());
                    ossClient.putObject(upload.getBucket(), upload.getObjectKey(), file1);
                    //提交上传事务
                    CreatePhotoRequest createPhotoRequest = new CreatePhotoRequest();
                    createPhotoRequest.setStoreName(STORE_NAME);
                    createPhotoRequest.setLibraryId(LIBRARY_ID);
                    createPhotoRequest.setFileId(upload.getFileId());
                    createPhotoRequest.setSessionId(upload.getSessionId());
                    createPhotoRequest.setPhotoTitle("test");
                    createPhotoRequest.setUploadType("manual");
                    createPhotoRequest.setAcceptFormat(FormatType.JSON);
                    createPhotoRequest.setShareExpireTime(4102416000000L);
                    try {
                        CreatePhotoResponse createPhotoResponse = acsClient.getAcsResponse(createPhotoRequest);
                        if ("Success".equalsIgnoreCase(createPhotoResponse.getCode())) {
                          /*  AmAliyunImage amAliyunImage = getAmAliyunImage(type, aliyunUrl, createPhotoResponse,localFilePath);
                            super.save(amAliyunImage);
                            return amAliyunImage.getImageUrl();*/
                            String path=aliyunUrl+createPhotoResponse.getPhoto().getFileId();
                            AmFileUpload amFileUpload=new AmFileUpload();
                            amFileUpload.setId(UUID.randomUUID().toString());
                            amFileUpload.setBizType(bizType);
                            amFileUpload.setBizKey(imgUntil.getBizKey());
                            amFileUpload.setFileName(imgUntil.getFileName());
                            amFileUpload.setCreateBy(imgUntil.getCreateBy());
                            amFileUpload.setCreateDate(imgUntil.getCreateDate());
                            amFileUpload.setUpdateBy(imgUntil.getUpdateBy());
                            amFileUpload.setUpdateDate(imgUntil.getUpdateDate());
                            amFileUpload.setFileUrl(path);
                            amFileUpload.setFileRealPath(path);
                            amFileUpload.setFileId(createPhotoResponse.getPhoto().getFileId());
                            amFileUpload.setFileExtension(imgUntil.getFileEntity().getFileExtension());
                            amFileUpload.setFileType(imgUntil.getFileType());
                            amFileUpload.setFilePath(this.getAliPath(path));
                            amFileUpload.setIsNewRecord(true);
                            amFileUploadService.insert(amFileUpload);

                        }

                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return  renderResult("True","上传成功");

    }
    public String getAliPath(String path){
        String path1=ENDPOINT;
        path=path.substring(path.indexOf(path1)+path1.length());
        return path;
    }
    @ResponseBody
    @RequestMapping(value = "fixDataSupsupplier")
    public String fixDataSupsupplier(HttpServletRequest request1){
            SupSupplierQualifications qualifications=new SupSupplierQualifications();
           //qualifications.setQualificationCode("1017341700668084224");
            List<SupSupplierQualifications> qualificationsList=qualificationsService.findList(qualifications);
            if(qualificationsList!=null &&qualificationsList.size()>0) {
                for (int j = 0; j < qualificationsList.size(); j++) {
                    SupSupplierQualifications qualification = qualificationsList.get(j);
                    if (qualification.getSavePath().length() <10) {
                        String filepath = baseDir + SAVE_PREFIX + qualification.getSavePath() + qualification.getQualificationName() + qualification.getProfileSurfix();
                        File file1 = new File(filepath);
                        if (file1.exists()) {
                            DefaultProfile profile = DefaultProfile.getProfile(REGION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                            DefaultAcsClient acsClient = new DefaultAcsClient(profile);
                            FileInputStream inputStream = null;
                            String md5 = "";
                            try {
                                inputStream = new FileInputStream(file1);
                                md5 = DigestUtils.md5Hex(inputStream);
                                inputStream.close();
                            } catch (Exception ex) {
                                logger.error("图片上传阿里云", ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            //开启上传事务
                            CreateTransactionRequest request = new CreateTransactionRequest();
                            request.setMd5(md5);
                            request.setExt("jpg");
                            request.setLibraryId(LIBRARY_ID);
                            request.setStoreName(STORE_NAME);
                            request.setSize(file1.length());
                            request.setAcceptFormat(FormatType.JSON);

                            //发起请求
                            CreateTransactionResponse response = null;
                            try {
                                response = acsClient.getAcsResponse(request);
                            } catch (ClientException e) {
                                e.printStackTrace();
                            }
                            CreateTransactionResponse.Transaction tnx = response.getTransaction();
                            CreateTransactionResponse.Transaction.Upload upload = tnx.getUpload();

                            //构建OSS客户端上传图片
                            OSSClient ossClient = new OSSClient(upload.getOssEndpoint(), upload.getAccessKeyId(), upload.getAccessKeySecret(), upload.getStsToken());
                            ossClient.putObject(upload.getBucket(), upload.getObjectKey(), file1);
                            //提交上传事务
                            CreatePhotoRequest createPhotoRequest = new CreatePhotoRequest();
                            createPhotoRequest.setStoreName(STORE_NAME);
                            createPhotoRequest.setLibraryId(LIBRARY_ID);
                            createPhotoRequest.setFileId(upload.getFileId());
                            createPhotoRequest.setSessionId(upload.getSessionId());
                            createPhotoRequest.setPhotoTitle("test");
                            createPhotoRequest.setUploadType("manual");
                            createPhotoRequest.setAcceptFormat(FormatType.JSON);
                            createPhotoRequest.setShareExpireTime(4102416000000L);
                            try {
                                CreatePhotoResponse createPhotoResponse = acsClient.getAcsResponse(createPhotoRequest);
                                if ("Success".equalsIgnoreCase(createPhotoResponse.getCode())) {
                                    String path = aliyunUrl + createPhotoResponse.getPhoto().getFileId();
                                    qualification.setQualificationName(this.getAliPath(path));
                                    qualification.setSavePath(path);
                                    qualificationsService.update(qualification);
                                }
                            } catch (ClientException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        return renderResult(Global.TRUE,"供应商文件更新成功");

    }
    @ResponseBody
    @RequestMapping(value ="delete")
    public String deleteAliPic(HttpServletRequest request) {
        String id=request.getParameter("id");
        AmFileUpload amFileUpload=new AmFileUpload();
        amFileUpload.setId(id);
        amFileUpload=service.get(amFileUpload);
        if(amFileUpload!=null) {
            List<String> list = new ArrayList<String>();
            list.add(amFileUpload.getFilePath());
            if (null != list && list.size() > 0) {
                OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                // 执行删除阿里云文件
                ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(list));
            }
        }
        return renderResult(Global.TRUE,"删除成功");
    }

}

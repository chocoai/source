/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.aliyunimage.service;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.cloudphoto.model.v20170711.CreatePhotoRequest;
import com.aliyuncs.cloudphoto.model.v20170711.CreatePhotoResponse;
import com.aliyuncs.cloudphoto.model.v20170711.CreateTransactionRequest;
import com.aliyuncs.cloudphoto.model.v20170711.CreateTransactionResponse;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.jeesite.common.io.FileUtils;
import com.jeesite.modules.asset.aliyunimage.entity.PostObjectPolicy;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.aliyunimage.entity.AmAliyunImage;
import com.jeesite.modules.asset.aliyunimage.dao.AmAliyunImageDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


/**
 * 阿里云图片Service
 *
 * @author AlbertFeng
 * @version 2018-08-04
 */
@Service
@Transactional(readOnly = true)
public class AmAliyunImageService extends CrudService<AmAliyunImageDao, AmAliyunImage> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AmAliyunImageService.class);
    /**
     * 获取单条数据
     *
     * @param amAliyunImage
     * @return
     */
    @Override
    public AmAliyunImage get(AmAliyunImage amAliyunImage) {
        return super.get(amAliyunImage);
    }

    /**
     * 查询分页数据
     *
     * @param page          分页对象
     * @param amAliyunImage
     * @return
     */
    @Override
    public Page<AmAliyunImage> findPage(Page<AmAliyunImage> page, AmAliyunImage amAliyunImage) {
        return super.findPage(page, amAliyunImage);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param amAliyunImage
     */
    @Override
    @Transactional(readOnly = false)
    public void save(AmAliyunImage amAliyunImage) {
        super.save(amAliyunImage);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(amAliyunImage.getId(), "amAliyunImage_image");
        // 保存上传附件
        FileUploadUtils.saveFileUpload(amAliyunImage.getId(), "amAliyunImage_file");
    }

    /**
     * 更新状态
     *
     * @param amAliyunImage
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(AmAliyunImage amAliyunImage) {
        super.updateStatus(amAliyunImage);
    }

    /**
     * 删除数据
     *
     * @param amAliyunImage
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(AmAliyunImage amAliyunImage) {
        super.delete(amAliyunImage);
    }



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

    /**
     * @Description: 上传图片到阿里云存储
     * @Param: [localFilePath文件本地路径, ext扩展名, titile标题, type图片类型, aliyunUrl阿里云存储路径]
     * @return: Object
     * @Author: Albert Feng
     * @Date: 2018-08-04
     */
    @Transactional
    public Object uploadImageAliyun(String localFilePath, String ext, String titile, String type, String aliyunUrl) throws Exception {
        // 初始化客户端 - 在应用生命周期可以只初始化一次，对象可以重用。
        DefaultProfile profile = DefaultProfile.getProfile(REGION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);

        //计算文件MD5值
        File file = new File(localFilePath);
        FileInputStream inputStream = null;
        String md5 = "";
        try {
            inputStream = new FileInputStream(file);
            md5 = DigestUtils.md5Hex(inputStream);
            inputStream.close();
        } catch (Exception ex) {
            logger.error("图片上传阿里云", ex);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        //开启上传事务
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setMd5(md5);
        request.setExt(ext);
        request.setLibraryId(LIBRARY_ID);
        request.setStoreName(STORE_NAME);
        request.setSize(file.length());
        request.setAcceptFormat(FormatType.JSON);

        //发起请求
        CreateTransactionResponse response = acsClient.getAcsResponse(request);
        CreateTransactionResponse.Transaction tnx = response.getTransaction();
        CreateTransactionResponse.Transaction.Upload upload = tnx.getUpload();

        //构建OSS客户端上传图片
        OSSClient ossClient = new OSSClient(upload.getOssEndpoint(), upload.getAccessKeyId(), upload.getAccessKeySecret(), upload.getStsToken());
        ossClient.putObject(upload.getBucket(), upload.getObjectKey(), file);

        //提交上传事务
        CreatePhotoRequest createPhotoRequest = new CreatePhotoRequest();
        createPhotoRequest.setStoreName(STORE_NAME);
        createPhotoRequest.setLibraryId(LIBRARY_ID);
        createPhotoRequest.setFileId(upload.getFileId());
        createPhotoRequest.setSessionId(upload.getSessionId());
        createPhotoRequest.setPhotoTitle(titile);
        createPhotoRequest.setUploadType("manual");
        createPhotoRequest.setAcceptFormat(FormatType.JSON);
        createPhotoRequest.setShareExpireTime(4102416000000L);

        CreatePhotoResponse createPhotoResponse = acsClient.getAcsResponse(createPhotoRequest);
        //ossClient.deleteObject(BUCKET,"20187135628048tooopen_sy_132700671385.jpg");
        if ("Success".equalsIgnoreCase(createPhotoResponse.getCode())) {
            AmAliyunImage amAliyunImage = getAmAliyunImage(type, aliyunUrl, createPhotoResponse,localFilePath);
            super.save(amAliyunImage);
            return amAliyunImage.getImageUrl();
        }
        return null;
    }


    /**
     * @Description: 上传图片到阿里云存储
     * @Param: [InputStream文件流, ext扩展名, titile标题, type图片类型, aliyunUrl阿里云存储路径]
     * @return: Object
     * @Author: Albert Feng
     * @Date: 2018-08-04
     */
//    @Transactional
//    public Object uploadImageAliyun(InputStream inputStream, String ext, String titile, String type, String aliyunUrl) throws Exception {
//        try {
//            // 初始化客户端 - 在应用生命周期可以只初始化一次，对象可以重用。
//            DefaultProfile profile = DefaultProfile.getProfile(REGION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//            DefaultAcsClient acsClient = new DefaultAcsClient(profile);
//
//            //计算文件流MD5值
//            String md5 = DigestUtils.md5Hex(inputStream);
//
//            //开启上传事务
//            CreateTransactionRequest request = new CreateTransactionRequest();
//            request.setMd5(md5);
//            request.setExt(ext);
//            request.setLibraryId(LIBRARY_ID);
//            request.setStoreName(STORE_NAME);
//            request.setSize(inputStream.available() / 1024L);
//            request.setAcceptFormat(FormatType.JSON);
//
//            //发起请求
//            CreateTransactionResponse response = acsClient.getAcsResponse(request);
//            CreateTransactionResponse.Transaction tnx = response.getTransaction();
//            CreateTransactionResponse.Transaction.Upload upload = tnx.getUpload();
//
//            //构建OSS客户端上传图片
//            OSSClient ossClient = new OSSClient(upload.getOssEndpoint(), upload.getAccessKeyId(), upload.getAccessKeySecret(), upload.getStsToken());
//            ossClient.putObject(upload.getBucket(), upload.getObjectKey(), inputStream);
//            //关闭输入流
//            inputStream.close();
//
//            //提交上传事务
//            CreatePhotoRequest createPhotoRequest = new CreatePhotoRequest();
//            createPhotoRequest.setStoreName(STORE_NAME);
//            createPhotoRequest.setLibraryId(LIBRARY_ID);
//            createPhotoRequest.setFileId(upload.getFileId());
//            createPhotoRequest.setSessionId(upload.getSessionId());
//            createPhotoRequest.setPhotoTitle(titile);
//            createPhotoRequest.setUploadType("manual");
//            createPhotoRequest.setAcceptFormat(FormatType.JSON);
//            createPhotoRequest.setShareExpireTime(4102416000000L);
//
//            CreatePhotoResponse createPhotoResponse = acsClient.getAcsResponse(createPhotoRequest);
//            if ("Success".equalsIgnoreCase(createPhotoResponse.getCode())) {
//                AmAliyunImage amAliyunImage = getAmAliyunImage(type, aliyunUrl, createPhotoResponse);
//                super.save(amAliyunImage);
//                return amAliyunImage.getImageUrl();
//            }
//        }catch (Exception ex){
//            logger.error("文件流上传阿里云失败",ex);
//        }finally {
//            if (inputStream != null)
//                inputStream.close();
//        }
//        return null;
//    }

    private AmAliyunImage getAmAliyunImage(String type, String aliyunUrl, CreatePhotoResponse createPhotoResponse,String imageSavePath) {
        AmAliyunImage amAliyunImage = new AmAliyunImage();
        amAliyunImage.setCreateTime(new Date());
        amAliyunImage.setFileId(createPhotoResponse.getPhoto().getFileId());
        amAliyunImage.setHeight(createPhotoResponse.getPhoto().getHeight().toString());
        amAliyunImage.setWidth(createPhotoResponse.getPhoto().getWidth().toString());
        amAliyunImage.setIsVideo(createPhotoResponse.getPhoto().getIsVideo() ? 1 : 0);
        amAliyunImage.setImageId(createPhotoResponse.getPhoto().getId().toString());
        amAliyunImage.setMd5(createPhotoResponse.getPhoto().getMd5());
        amAliyunImage.setState(createPhotoResponse.getPhoto().getState());
        amAliyunImage.setRemark(createPhotoResponse.getPhoto().getRemark());
        amAliyunImage.setTakenAt(createPhotoResponse.getPhoto().getTakenAt().toString());
        amAliyunImage.setTitle(createPhotoResponse.getPhoto().getTitle());
        amAliyunImage.setType(type);
        amAliyunImage.setImageUrl(aliyunUrl + createPhotoResponse.getPhoto().getFileId());
        amAliyunImage.setId(UUID.randomUUID().toString());
        amAliyunImage.setLocalPath(imageSavePath);
        amAliyunImage.setIsNewRecord(true);
        return amAliyunImage;
    }

//    @PostConstruct
//    void init() {
//        if(this.ossClient == null) {
//            this.ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//        }
//    }


    /**
     * OSS上传组件接口
     * @auther: len
     * @date: 2018/8/7 17:04
     */
    public Map getPostObjectPolicy(String tempContextUrl) {
        Map<String, Object> map = new HashMap<>();
        long expiredSeconds = 10800;
        long expireEndTime = System.currentTimeMillis() + expiredSeconds * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, DIR);

        OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = new byte[0];
        try {
            binaryData = postPolicy.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        PostObjectPolicy policy = new PostObjectPolicy();
        policy.setAccessId(ACCESS_KEY_ID);
        policy.setHost("https://" + BUCKET + "." + ENDPOINT);
        policy.setDir(DIR);
        policy.setExpire(String.valueOf(expireEndTime / 1000));
        policy.setPolicy(encodedPolicy);
        policy.setSignature(postSignature);
        Callback callback = new Callback();
        callback.setCallbackUrl(tempContextUrl + "a/aliyunimage/amAliyunImage/callback");
//        callback.setCallbackHost(ENDPOINT);
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
//        callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},\\\"etag\\\":${etag},\\\"imageInfo.height\\\":${imageInfo.height},\\\"imageInfo.width\\\":${imageInfo.width},\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
//        callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
//                + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}");
        callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},\\\"etag\\\":${etag},"
                + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
        String jsonCb = OSSUtils.jsonizeCallback(callback);
        String base64Cb = BinaryUtil.toBase64String(jsonCb.getBytes());
        policy.setCallBack(base64Cb);
        map.put("code", 200);
        map.put("data", policy);
        return map;
    }
}
package com.jeesite.modules.asset.file.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.entity.FileEntity;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: len
 * @Date: 2018/8/10 19:39
 * @Description:
 */
@RequestMapping("a/file")
@RestController
public class FileController {
    @Autowired
    private AmFileUploadService amFileUploadService;
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID; //密钥Id
    @Value("${ACCESS_KEY_SECRET}")
    private String ACCESS_KEY_SECRET;
    @Value("${BUCKET}")
    private String BUCKET;

    /**
     * 保存图片信息到数据库
     * @param
     */
    @ResponseBody
    @RequestMapping("saveFile")
    public Map saveFile(@RequestBody String req) {
        List<AmFileUpload> fileUploadList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(req);
        // 上传图片
        JSONArray jsonArray = (JSONArray) jsonObject.get("addImg");
        if (jsonArray != null && jsonArray.size() >0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                AmFileUpload amFileUpload = JSONObject.toJavaObject(jsonArray.getJSONObject(i), AmFileUpload.class);
                FileEntity fileEntity = amFileUpload.getFileEntity();
                amFileUpload.setFileId(fileEntity.getFileId());
                amFileUpload.setFileContentType(fileEntity.getFileContentType());
                amFileUpload.setFileExtension(fileEntity.getFileExtension());
                amFileUpload.setFileMd5(fileEntity.getFileMd5());
                amFileUpload.setFilePath(fileEntity.getFilePath());
                amFileUpload.setFileRealPath(fileEntity.getFileRealPath());
                amFileUpload.setFileSize(fileEntity.getFileSize());
                amFileUpload.setFileSizeFormat(fileEntity.getFileSizeFormat());
                amFileUpload.setFileUrl(fileEntity.getFileUrl());
                if("picReview_image".equals(amFileUpload.getBizType())){
                amFileUpload.setPicStatus("o");
                amFileUpload.setPicRemarks("");
                }
                amFileUpload.setIsNewRecord(true);
                fileUploadList.add(amFileUpload);
            }
        }
        // 删除图片
        JSONArray delArray = (JSONArray) jsonObject.get("delImg");
        // 存放数据库id
        List<String> ids = new ArrayList<>();
        // 存放要删除阿里云的object
        List<String> keys = new ArrayList<>();
        if (delArray != null && delArray.size() >0){
            if (delArray != null && delArray.size() >0) {
                for (int i =0; i< delArray.size(); i++) {
                    ids.add(delArray.getJSONObject(i).get("id").toString());
                    keys.add(delArray.getJSONObject(i).get("filePath").toString());
                }
            }
        }


        Map<String, Object> map = new HashMap<>();
        try {
            amFileUploadService.saveData(fileUploadList, ids);
        }catch (Exception e) {
            map.put("code", "400");
            map.put("msg", "数据提交失败");
            return map;
        }
        if(null!=keys &&keys.size()>0) {
            OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            // 执行删除阿里云文件
            ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(keys));
        }
        map.put("code", "200");
        map.put("msg", "数据提交成功");
        return map;
    }

    /**
     * 获取图片信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("query")
    public String query(String bizKey, String bizType) {
        if(null==bizKey ||"".equals(bizKey)){
            return JsonMapper.toJson("");
        }
        List<AmFileUpload> fileList = amFileUploadService.getImg(bizKey, bizType);
        List<AmFileUpload> fileUploadList = new ArrayList<>();
        if (fileList != null && fileList.size() >0) {
            for (AmFileUpload amFileUpload :fileList) {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setId(amFileUpload.getFileId());
                fileEntity.setFileId(amFileUpload.getFileId());
                fileEntity.setFileContentType(amFileUpload.getFileContentType());
                fileEntity.setFileExtension(amFileUpload.getFileExtension());
                fileEntity.setFileMd5(amFileUpload.getFileMd5());
                fileEntity.setFilePath(amFileUpload.getFilePath());
                fileEntity.setFileRealPath(amFileUpload.getFileRealPath());
                fileEntity.setFileSize(amFileUpload.getFileSize());
                fileEntity.setFileSizeFormat(amFileUpload.getFileSizeFormat());
                fileEntity.setFileUrl(amFileUpload.getFileUrl());
                amFileUpload.setFileEntity(fileEntity);
                fileUploadList.add(amFileUpload);
            }
        }
        return JsonMapper.toJson(fileUploadList);
    }
}

/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.aliyunimage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.asset.aliyunimage.entity.PostObjectPolicy;
import com.jeesite.modules.asset.util.result.ReturnDate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.aliyunimage.entity.AmAliyunImage;
import com.jeesite.modules.asset.aliyunimage.service.AmAliyunImageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

/**
 * 阿里云图片Controller
 *
 * @author AlbertFeng
 * @version 2018-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/aliyunimage/amAliyunImage")
public class AmAliyunImageController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(AmAliyunImageController.class);
    @Value("${file.baseDir}")
    String baseDir;

    @Autowired
    private AmAliyunImageService amAliyunImageService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public AmAliyunImage get(String id, boolean isNewRecord) {
        return amAliyunImageService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:view")
    @RequestMapping(value = {"list", ""})
    public String list(AmAliyunImage amAliyunImage, Model model) {
        model.addAttribute("amAliyunImage", amAliyunImage);
        return "asset/aliyunimage/amAliyunImageList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AmAliyunImage> listData(AmAliyunImage amAliyunImage, HttpServletRequest request, HttpServletResponse response) {
        Page<AmAliyunImage> page = amAliyunImageService.findPage(new Page<AmAliyunImage>(request, response), amAliyunImage);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:view")
    @RequestMapping(value = "form")
    public String form(AmAliyunImage amAliyunImage, Model model) {
        model.addAttribute("amAliyunImage", amAliyunImage);
        return "asset/aliyunimage/amAliyunImageForm";
    }

    /**
     * 保存阿里云图片
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated AmAliyunImage amAliyunImage) {
        amAliyunImageService.save(amAliyunImage);
        return renderResult(Global.TRUE, "保存阿里云图片成功！");
    }

    /**
     * 停用阿里云图片
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(AmAliyunImage amAliyunImage) {
        amAliyunImage.setStatus(AmAliyunImage.STATUS_DISABLE);
        amAliyunImageService.updateStatus(amAliyunImage);
        return renderResult(Global.TRUE, "停用阿里云图片成功");
    }

    /**
     * 启用阿里云图片
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(AmAliyunImage amAliyunImage) {
        amAliyunImage.setStatus(AmAliyunImage.STATUS_NORMAL);
        amAliyunImageService.updateStatus(amAliyunImage);
        return renderResult(Global.TRUE, "启用阿里云图片成功");
    }

    /**
     * 删除阿里云图片
     */
    @RequiresPermissions("aliyunimage:amAliyunImage:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(AmAliyunImage amAliyunImage) {
        amAliyunImageService.delete(amAliyunImage);
        return renderResult(Global.TRUE, "删除阿里云图片成功！");
    }

    /**
     * 图片上传阿里云
     */
    @PostMapping(value = "uploadimage")
    @ResponseBody
    public Object imageUploadAliyun(HttpServletRequest request, HttpServletResponse response) {
        //阿里云图片路径
        List<Object> imageUrls = new ArrayList<>();
        //得到图片上传保存目录
        File file = new File(baseDir);
        //判断上传文件保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            //目录不存在，创建目录
            file.mkdir();
        }
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        try {
//            //使用Apache文件上传组件处理文件上传步骤：
//            //1.创建一个DiskFileItemFactory工厂
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //2.创建文件上传解析器
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            //解决上传文件名中文乱码
//            upload.setHeaderEncoding("UTF-8");
//            //3.判断请求数据是否是表单上传
//            if (!ServletFileUpload.isMultipartContent(request)) {
//                return ReturnDate.error(900, "没有文件上传！");
//            }
//            //4.使用ServletFileUpload解析器解析上传数据，解析结果返回的是List<FileItem>集合
//            //每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
//            //图片存储路径
//            String imageSavePath;
//            for (FileItem item : list) {
//                //判断FileItem中封装的是否是上传文件
//                if (!item.isFormField()){
//                    String name = item.getFieldName();
//                    //解决普通输入项数据中文乱码
//                    String value = item.getString("UTF-8");
//                    String fileName = item.getName();
//                    if (fileName.isEmpty())
//                        return ReturnDate.error(900, "上传的文件名称为空！");
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
//                    fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//                    //图片存储路径
//                    imageSavePath = SAVE_IMAGE_PATH + "\\" + fileName;
//                    //获取item中的上传文件输入流
//                    InputStream inputStream = item.getInputStream();
//                    //创建文件输出流
//                    FileOutputStream fileOutputStream = new FileOutputStream(imageSavePath);
//                    //创建缓冲区
//                    byte buffer[] = new byte[1024];
//                    //判断输入流中的数据是否已经读完的标识
//                    int len = 0;
//                    //循环将输入流读入到缓冲区中，(len = in.read(buffer))>0表示in还有数据
//                    while ((len = inputStream.read(buffer)) > 0){
//                        fileOutputStream.write(buffer,0,len);
//                    }
//                    //关闭输入流
//                    inputStream.close();
//                    //关闭输出流
//                    fileOutputStream.close();
//                    //删除处理文件上传时生成的临时文件
//                    item.delete();
//                    //图片上传阿里云
//                    Object imageUrl = amAliyunImageService.uploadImageAliyun(imageSavePath,"jpg",title,
//                            type,"https://after-sales-photo.oss-cn-shanghai.aliyuncs.com/stores/after-ales-photo/data/");
//                    imageUrls.add(imageUrl);
//                }
//            }
            MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("file");
            if (multipartFile == null || multipartFile.isEmpty())
                return ReturnDate.error(900, "文件为空！");
            String fileName = multipartFile.getOriginalFilename();
            //图片存储路径
            String imageSavePath =  file.getPath() + "\\" + fileName;
            //保存文件
            File saveFile = new File(imageSavePath);
            multipartFile.transferTo(saveFile);
            //TODO: 转InputStream上传阿里云失败
//            InputStream inputStream = multipartFile.getInputStream();
            //图片上传阿里云
            Object imageUrl = amAliyunImageService.uploadImageAliyun(imageSavePath, "jpg", title,
                    type, "https://after-sales-photo.oss-cn-shanghai.aliyuncs.com/stores/after-ales-photo/data/");
            imageUrls.add(imageUrl);
        } catch (Exception ex) {
            log.error("图片上传阿里云接口", ex);
            return ReturnDate.error(900, "文件上传失败！");
        }
        return ReturnDate.success(imageUrls);
    }

    /**
     * 前端请求获取阿里云OSS接口的必要参数
     * @auther: len
     * @date: 2018/8/7 17:02
     */
//    @RequiresPermissions("aliyunimage:amAliyunImage:edit")
    @ResponseBody
    @RequestMapping(value = "getPolicy")
    public Map getPolicy(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
        return amAliyunImageService.getPostObjectPolicy(tempContextUrl);
    }

    /**
     * 阿里云请求回调地址
     * @auther: len
     * @date: 2018/8/7 17:02
     */
    @ResponseBody
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public Map callback(@RequestBody String req) {
        Map<String,Object> map = new HashMap<>();
        if (!"".equals(req)) {
            JSONObject jsonObject = JSONObject.parseObject(req);
            map.put("code", 200);
            map.put("msg", "上传阿里云成功");
            map.put("data", jsonObject.get("object"));
            map.put("id", UUID.randomUUID());
            return map;
        }else {
            map.put("code", 400);
            map.put("msg", "上传阿里云失败");
            return map;
        }
    }
}
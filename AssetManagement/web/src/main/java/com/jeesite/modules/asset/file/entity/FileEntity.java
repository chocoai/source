package com.jeesite.modules.asset.file.entity;

/**
 * @Auther: Administrator
 * @Date: 2018/8/10 18:43
 * @Description:
 */
public class FileEntity {
    private String fileContentType;		// 文件内容类型
    private String fileExtension;		// 文件后缀扩展名
    private String fileMd5;		// 文件MD5
    private String filePath;		// 文件相对路径
    private String fileRealPath;		// 文件绝对路径
    private Long fileSize;		// file_size
    private String fileSizeFormat;		// 文件大小
    private String fileUrl;		// 文件路径
    private String id;
    private String fileId;		// 文件编号

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileRealPath() {
        return fileRealPath;
    }

    public void setFileRealPath(String fileRealPath) {
        this.fileRealPath = fileRealPath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSizeFormat() {
        return fileSizeFormat;
    }

    public void setFileSizeFormat(String fileSizeFormat) {
        this.fileSizeFormat = fileSizeFormat;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

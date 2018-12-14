package com.jeesite.modules.asset.amspecimen.dao;

public class UploadFaleParam {
    private String detailCode;
    private String qualificationName;
    private String imgBase64String;
    private String profileSurfix;
    private String file;        //阿里云全路径
    private String typeName;
    private String fileBase64String;
    private String filePath;   //删除的阿里云路径

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getImgBase64String() {
        return imgBase64String;
    }

    public void setImgBase64String(String imgBase64String) {
        this.imgBase64String = imgBase64String;
    }

    public String getProfileSurfix() {
        return profileSurfix;
    }

    public void setProfileSurfix(String profileSurfix) {
        this.profileSurfix = profileSurfix;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
        public String getFileBase64String() {
        return fileBase64String;
    }

    public void setFileBase64String(String fileBase64String) {
        this.fileBase64String = fileBase64String;
    }
}

package com.jeesite.modules.asset.supplier.entity;


import java.util.List;

public class RegisteredSupplierRequest {

        //简称
        private String AbbreviationName;
        // 名称
        private String SupplierName;

        // 公司网址

        private String CompanyWebsite;

        // 创立日期

        private String FoundingDate;

        // 公司性质

        private String CompanyNature;

        // 注册资金

        private Long RegisteredCapital;

        // 工商登记号

        private String RegisterCode;

        // 占地面积

        private Long CoverArea;

        // 员工数量

        private Long EmployeeNumber;

        // 月生产能力

        private Long MonthlyCapacity;

        // 月富余生产能力

        private Long MonthlySpareCapacity;

        // 年营业额

        private Long AnnualTurnover;

        // 经营类型

        private String BusinessType;

        // 企业经营范围

        private String BusinessScope;

        // 公司简介

        private String CompanyProfile;

        // 邮政编码

        private String PostalCode;

        // 国家

        private String Country;

        // 省份

        private String Province;

        // 城市

        private String City;

        // 通讯地址

        private String CorrespondenceAddress;

        // 基本信息.法人身份证	照片文件地址

        private String SFZFile;

        // 基本信息.法人身份证	生效日期

        private String SFZEffectiveDate;

        // 基本信息.法人身份证	失效日期

        private String SFZExpiryDate;

        // 基本信息.营业执照	证件文件照片

        private String YYZZFile;

        // 基本信息.营业执照	生效日期

        private String YYZZEffectiveDate;

        // 基本信息.营业执照	失效日期

        private String YYZZExpiryDate;

        // 基本信息.环评报告	报告文件地址

        private String HPBGFile;

        // 基本信息.环评报告	生效日期

        private String HPBGEffectiveDate;

        // 基本信息.环评报告	失效日期

        private String HPBGExpiryDate;

        // 基本信息.消费验收报告	报告文件地址

        private String XFYSBGFile;

        // 基本信息.消费验收报告	生效日期

        private String XFYSBGEffectiveDate;

        // 基本信息.消费验收报告	失效日期

        private String XFYSBGExpiryDate;

        // 基本信息.排污许可证	证件文件地址

        private String PWXKZFile;

        // 基本信息.排污许可证	生效日期

        private String PWXKZEffectiveDate;

        // 基本信息.排污许可证	失效日期

        private String PWXKZExpiryDate;

        // 基本信息.检测报告	报告文件地址

        private String JCBGFile;

        // 基本信息.检测报告	生效日期

        private String JCBGEffectiveDate;

        // 基本信息.检测报告	失效日期

        private String JCBGExpiryDate;

        // 企业电话

        private String CorporatePhone;

        // 企业传真

        private String CorporateFax;

        // “联系人“页签

        private List<RegisteredSupplierContacts> ContactsInfo;

        // “主要合作客户“页签

        private List<RegisteredSupplierPartners> PartnersInfo;

    public String getAbbreviationName() {
        return AbbreviationName;
    }

    public void setAbbreviationName(String abbreviationName) {
        AbbreviationName = abbreviationName;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getCompanyWebsite() {
        return CompanyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        CompanyWebsite = companyWebsite;
    }

    public String getFoundingDate() {
        return FoundingDate;
    }

    public void setFoundingDate(String foundingDate) {
        FoundingDate = foundingDate;
    }

    public String getCompanyNature() {
        return CompanyNature;
    }

    public void setCompanyNature(String companyNature) {
        CompanyNature = companyNature;
    }

    public Long getRegisteredCapital() {
        return RegisteredCapital;
    }

    public void setRegisteredCapital(Long registeredCapital) {
        RegisteredCapital = registeredCapital;
    }

    public String getRegisterCode() {
        return RegisterCode;
    }

    public void setRegisterCode(String registerCode) {
        RegisterCode = registerCode;
    }

    public Long getCoverArea() {
        return CoverArea;
    }

    public void setCoverArea(Long coverArea) {
        CoverArea = coverArea;
    }

    public Long getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        EmployeeNumber = employeeNumber;
    }

    public Long getMonthlyCapacity() {
        return MonthlyCapacity;
    }

    public void setMonthlyCapacity(Long monthlyCapacity) {
        MonthlyCapacity = monthlyCapacity;
    }

    public Long getMonthlySpareCapacity() {
        return MonthlySpareCapacity;
    }

    public void setMonthlySpareCapacity(Long monthlySpareCapacity) {
        MonthlySpareCapacity = monthlySpareCapacity;
    }

    public Long getAnnualTurnover() {
        return AnnualTurnover;
    }

    public void setAnnualTurnover(Long annualTurnover) {
        AnnualTurnover = annualTurnover;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getBusinessScope() {
        return BusinessScope;
    }

    public void setBusinessScope(String businessScope) {
        BusinessScope = businessScope;
    }

    public String getCompanyProfile() {
        return CompanyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        CompanyProfile = companyProfile;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCorrespondenceAddress() {
        return CorrespondenceAddress;
    }

    public void setCorrespondenceAddress(String correspondenceAddress) {
        CorrespondenceAddress = correspondenceAddress;
    }

    public String getSFZFile() {
        return SFZFile;
    }

    public void setSFZFile(String SFZFile) {
        this.SFZFile = SFZFile;
    }

    public String getSFZEffectiveDate() {
        return SFZEffectiveDate;
    }

    public void setSFZEffectiveDate(String SFZEffectiveDate) {
        this.SFZEffectiveDate = SFZEffectiveDate;
    }

    public String getSFZExpiryDate() {
        return SFZExpiryDate;
    }

    public void setSFZExpiryDate(String SFZExpiryDate) {
        this.SFZExpiryDate = SFZExpiryDate;
    }

    public String getYYZZFile() {
        return YYZZFile;
    }

    public void setYYZZFile(String YYZZFile) {
        this.YYZZFile = YYZZFile;
    }

    public String getYYZZEffectiveDate() {
        return YYZZEffectiveDate;
    }

    public void setYYZZEffectiveDate(String YYZZEffectiveDate) {
        this.YYZZEffectiveDate = YYZZEffectiveDate;
    }

    public String getYYZZExpiryDate() {
        return YYZZExpiryDate;
    }

    public void setYYZZExpiryDate(String YYZZExpiryDate) {
        this.YYZZExpiryDate = YYZZExpiryDate;
    }

    public String getHPBGFile() {
        return HPBGFile;
    }

    public void setHPBGFile(String HPBGFile) {
        this.HPBGFile = HPBGFile;
    }

    public String getHPBGEffectiveDate() {
        return HPBGEffectiveDate;
    }

    public void setHPBGEffectiveDate(String HPBGEffectiveDate) {
        this.HPBGEffectiveDate = HPBGEffectiveDate;
    }

    public String getHPBGExpiryDate() {
        return HPBGExpiryDate;
    }

    public void setHPBGExpiryDate(String HPBGExpiryDate) {
        this.HPBGExpiryDate = HPBGExpiryDate;
    }

    public String getXFYSBGFile() {
        return XFYSBGFile;
    }

    public void setXFYSBGFile(String XFYSBGFile) {
        this.XFYSBGFile = XFYSBGFile;
    }

    public String getXFYSBGEffectiveDate() {
        return XFYSBGEffectiveDate;
    }

    public void setXFYSBGEffectiveDate(String XFYSBGEffectiveDate) {
        this.XFYSBGEffectiveDate = XFYSBGEffectiveDate;
    }

    public String getXFYSBGExpiryDate() {
        return XFYSBGExpiryDate;
    }

    public void setXFYSBGExpiryDate(String XFYSBGExpiryDate) {
        this.XFYSBGExpiryDate = XFYSBGExpiryDate;
    }

    public String getPWXKZFile() {
        return PWXKZFile;
    }

    public void setPWXKZFile(String PWXKZFile) {
        this.PWXKZFile = PWXKZFile;
    }

    public String getPWXKZEffectiveDate() {
        return PWXKZEffectiveDate;
    }

    public void setPWXKZEffectiveDate(String PWXKZEffectiveDate) {
        this.PWXKZEffectiveDate = PWXKZEffectiveDate;
    }

    public String getPWXKZExpiryDate() {
        return PWXKZExpiryDate;
    }

    public void setPWXKZExpiryDate(String PWXKZExpiryDate) {
        this.PWXKZExpiryDate = PWXKZExpiryDate;
    }

    public String getJCBGFile() {
        return JCBGFile;
    }

    public void setJCBGFile(String JCBGFile) {
        this.JCBGFile = JCBGFile;
    }

    public String getJCBGEffectiveDate() {
        return JCBGEffectiveDate;
    }

    public void setJCBGEffectiveDate(String JCBGEffectiveDate) {
        this.JCBGEffectiveDate = JCBGEffectiveDate;
    }

    public String getJCBGExpiryDate() {
        return JCBGExpiryDate;
    }

    public void setJCBGExpiryDate(String JCBGExpiryDate) {
        this.JCBGExpiryDate = JCBGExpiryDate;
    }

    public String getCorporatePhone() {
        return CorporatePhone;
    }

    public void setCorporatePhone(String corporatePhone) {
        CorporatePhone = corporatePhone;
    }

    public String getCorporateFax() {
        return CorporateFax;
    }

    public void setCorporateFax(String corporateFax) {
        CorporateFax = corporateFax;
    }

    public List<RegisteredSupplierContacts> getContactsInfo() {
        return ContactsInfo;
    }

    public void setContactsInfo(List<RegisteredSupplierContacts> contactsInfo) {
        ContactsInfo = contactsInfo;
    }

    public List<RegisteredSupplierPartners> getPartnersInfo() {
        return PartnersInfo;
    }

    public void setPartnersInfo(List<RegisteredSupplierPartners> partnersInfo) {
        PartnersInfo = partnersInfo;
    }
}
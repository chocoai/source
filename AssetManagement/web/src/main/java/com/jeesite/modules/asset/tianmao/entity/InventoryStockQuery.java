package com.jeesite.modules.asset.tianmao.entity;

import java.util.List;

public class InventoryStockQuery {
    //是否成品仓
    private boolean _IsCPC = true;
    //物料ID
    private String F_YF_MaterialId;
    // 物料编码
    private String F_YF_Material;
    //商品类别
    private String _FGoodsType = "";
    //商品系列
    private String _FProductseries = "";
    //销售策略
    private String _FSalePlot = "";
    //空间属性
    private String _FRoomAttribute = "";

    //仓库
    private String _FStockName = "";

    //清仓
    private String _FClearance = "否";

    //规格型号
    private String _FSpecMode = "";

    //是否有权 运营 查询
    private Boolean _isQueryYY = false;

    //补货策略
    private String _FBHStrategy = "";

    // 物料编码集合
    public List<String> _F_YF_Materials;
    // 字段集合
    public List<String> lstField;

    public List<String> get_F_YF_Materials() {
        return _F_YF_Materials;
    }

    public void set_F_YF_Materials(List<String> _F_YF_Materials) {
        this._F_YF_Materials = _F_YF_Materials;
    }

    public List<String> getLstField() {
        return lstField;
    }

    public void setLstField(List<String> lstField) {
        this.lstField = lstField;
    }

    public String getF_YF_MaterialId() {
        return F_YF_MaterialId;
    }

    public void setF_YF_MaterialId(String f_YF_MaterialId) {
        F_YF_MaterialId = f_YF_MaterialId;
    }

    public boolean is_IsCPC() {
        return _IsCPC;
    }

    public void set_IsCPC(boolean _IsCPC) {
        this._IsCPC = _IsCPC;
    }

    public String get_FGoodsType() {
        return _FGoodsType;
    }

    public void set_FGoodsType(String _FGoodsType) {
        this._FGoodsType = _FGoodsType;
    }

    public String get_FProductseries() {
        return _FProductseries;
    }

    public void set_FProductseries(String _FProductseries) {
        this._FProductseries = _FProductseries;
    }

    public String get_FSalePlot() {
        return _FSalePlot;
    }

    public void set_FSalePlot(String _FSalePlot) {
        this._FSalePlot = _FSalePlot;
    }

    public String get_FRoomAttribute() {
        return _FRoomAttribute;
    }

    public void set_FRoomAttribute(String _FRoomAttribute) {
        this._FRoomAttribute = _FRoomAttribute;
    }

    public String get_FStockName() {
        return _FStockName;
    }

    public void set_FStockName(String _FStockName) {
        this._FStockName = _FStockName;
    }

    public String get_FClearance() {
        return _FClearance;
    }

    public void set_FClearance(String _FClearance) {
        this._FClearance = _FClearance;
    }

    public String get_FSpecMode() {
        return _FSpecMode;
    }

    public void set_FSpecMode(String _FSpecMode) {
        this._FSpecMode = _FSpecMode;
    }

    public Boolean get_isQueryYY() {
        return _isQueryYY;
    }

    public void set_isQueryYY(Boolean _isQueryYY) {
        this._isQueryYY = _isQueryYY;
    }

    public String get_FBHStrategy() {
        return _FBHStrategy;
    }

    public void set_FBHStrategy(String _FBHStrategy) {
        this._FBHStrategy = _FBHStrategy;
    }

    public String getF_YF_Material() {
        return F_YF_Material;
    }

    public void setF_YF_Material(String f_YF_Material) {
        F_YF_Material = f_YF_Material;
    }
}

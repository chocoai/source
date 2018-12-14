package com.jeesite.modules.asset.tianmao.entity;

import com.alibaba.fastjson.JSON;

/**
 * 类描述
 *
 * @author Jace Xiong
 */

public class Item {
    private String num_iid;

    private String nick;

    private String approve_status;

    private int cid;

    private boolean has_discount;

    private boolean has_showcase;

    private String created;

    private String modified;

    private int auction_point;

    private String chaoshi_extends_info;

    private Delivery_time delivery_time;

    private String delist_time;

    private String desc_modules;

    private String detail_url;

    private String ems_fee;

    private String express_fee;

    private String freight_payer;

    private String features;

    private JSON food_security;

    private boolean has_invoice;

    private boolean has_warranty;

    private String input_pids;

    private String input_str;

    private String increment;

    private JSON locality_life;

    private String iid;

    private boolean is_3D;

    private int is_fenxiao;

    private boolean is_ex;

    private boolean is_virtual;

    private boolean is_taobao;

    private boolean is_xinpin;

    private boolean is_timing;

    private boolean is_lightning_consignment;

    private Item_imgs item_imgs;

    private String item_size;

    private String list_time;

    private Location location;

    private int num;

    private String outer_id;

    private boolean one_station;

    private String pic_url;

    private String post_fee;

    private int postage_id;

    private String price;

    private int product_id;

    private int period_sold_quantity;

    private String property_alias;

    private String props;

    private String props_name;

    private String sell_point;

    private String seller_cids;

    private boolean sell_promise;

    private Skus skus;

    private String stuff_status;

    private int sub_stock;

    private String template_id;

    private String title;

    private String type;

    private int valid_thru;

    private boolean violation;

    private String wap_detail_url;

    private int with_hold_quantity;

    public String getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(String num_iid) {
        this.num_iid = num_iid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getApprove_status() {
        return approve_status;
    }

    public void setApprove_status(String approve_status) {
        this.approve_status = approve_status;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public boolean isHas_discount() {
        return has_discount;
    }

    public void setHas_discount(boolean has_discount) {
        this.has_discount = has_discount;
    }

    public boolean isHas_showcase() {
        return has_showcase;
    }

    public void setHas_showcase(boolean has_showcase) {
        this.has_showcase = has_showcase;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getAuction_point() {
        return auction_point;
    }

    public void setAuction_point(int auction_point) {
        this.auction_point = auction_point;
    }

    public String getChaoshi_extends_info() {
        return chaoshi_extends_info;
    }

    public void setChaoshi_extends_info(String chaoshi_extends_info) {
        this.chaoshi_extends_info = chaoshi_extends_info;
    }

    public Delivery_time getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Delivery_time delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDelist_time() {
        return delist_time;
    }

    public void setDelist_time(String delist_time) {
        this.delist_time = delist_time;
    }

    public String getDesc_modules() {
        return desc_modules;
    }

    public void setDesc_modules(String desc_modules) {
        this.desc_modules = desc_modules;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getEms_fee() {
        return ems_fee;
    }

    public void setEms_fee(String ems_fee) {
        this.ems_fee = ems_fee;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public String getFreight_payer() {
        return freight_payer;
    }

    public void setFreight_payer(String freight_payer) {
        this.freight_payer = freight_payer;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public JSON getFood_security() {
        return food_security;
    }

    public void setFood_security(JSON food_security) {
        this.food_security = food_security;
    }

    public boolean isHas_invoice() {
        return has_invoice;
    }

    public void setHas_invoice(boolean has_invoice) {
        this.has_invoice = has_invoice;
    }

    public boolean isHas_warranty() {
        return has_warranty;
    }

    public void setHas_warranty(boolean has_warranty) {
        this.has_warranty = has_warranty;
    }

    public String getInput_pids() {
        return input_pids;
    }

    public void setInput_pids(String input_pids) {
        this.input_pids = input_pids;
    }

    public String getInput_str() {
        return input_str;
    }

    public void setInput_str(String input_str) {
        this.input_str = input_str;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public JSON getLocality_life() {
        return locality_life;
    }

    public void setLocality_life(JSON locality_life) {
        this.locality_life = locality_life;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public boolean isIs_3D() {
        return is_3D;
    }

    public void setIs_3D(boolean is_3D) {
        this.is_3D = is_3D;
    }

    public int getIs_fenxiao() {
        return is_fenxiao;
    }

    public void setIs_fenxiao(int is_fenxiao) {
        this.is_fenxiao = is_fenxiao;
    }

    public boolean isIs_ex() {
        return is_ex;
    }

    public void setIs_ex(boolean is_ex) {
        this.is_ex = is_ex;
    }

    public boolean isIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(boolean is_virtual) {
        this.is_virtual = is_virtual;
    }

    public boolean isIs_taobao() {
        return is_taobao;
    }

    public void setIs_taobao(boolean is_taobao) {
        this.is_taobao = is_taobao;
    }

    public boolean isIs_xinpin() {
        return is_xinpin;
    }

    public void setIs_xinpin(boolean is_xinpin) {
        this.is_xinpin = is_xinpin;
    }

    public boolean isIs_timing() {
        return is_timing;
    }

    public void setIs_timing(boolean is_timing) {
        this.is_timing = is_timing;
    }

    public boolean isIs_lightning_consignment() {
        return is_lightning_consignment;
    }

    public void setIs_lightning_consignment(boolean is_lightning_consignment) {
        this.is_lightning_consignment = is_lightning_consignment;
    }

    public Item_imgs getItem_imgs() {
        return item_imgs;
    }

    public void setItem_imgs(Item_imgs item_imgs) {
        this.item_imgs = item_imgs;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getList_time() {
        return list_time;
    }

    public void setList_time(String list_time) {
        this.list_time = list_time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public boolean isOne_station() {
        return one_station;
    }

    public void setOne_station(boolean one_station) {
        this.one_station = one_station;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public int getPostage_id() {
        return postage_id;
    }

    public void setPostage_id(int postage_id) {
        this.postage_id = postage_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPeriod_sold_quantity() {
        return period_sold_quantity;
    }

    public void setPeriod_sold_quantity(int period_sold_quantity) {
        this.period_sold_quantity = period_sold_quantity;
    }

    public String getProperty_alias() {
        return property_alias;
    }

    public void setProperty_alias(String property_alias) {
        this.property_alias = property_alias;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getProps_name() {
        return props_name;
    }

    public void setProps_name(String props_name) {
        this.props_name = props_name;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public String getSeller_cids() {
        return seller_cids;
    }

    public void setSeller_cids(String seller_cids) {
        this.seller_cids = seller_cids;
    }

    public boolean isSell_promise() {
        return sell_promise;
    }

    public void setSell_promise(boolean sell_promise) {
        this.sell_promise = sell_promise;
    }

    public Skus getSkus() {
        return skus;
    }

    public void setSkus(Skus skus) {
        this.skus = skus;
    }

    public String getStuff_status() {
        return stuff_status;
    }

    public void setStuff_status(String stuff_status) {
        this.stuff_status = stuff_status;
    }

    public int getSub_stock() {
        return sub_stock;
    }

    public void setSub_stock(int sub_stock) {
        this.sub_stock = sub_stock;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValid_thru() {
        return valid_thru;
    }

    public void setValid_thru(int valid_thru) {
        this.valid_thru = valid_thru;
    }

    public boolean isViolation() {
        return violation;
    }

    public void setViolation(boolean violation) {
        this.violation = violation;
    }

    public String getWap_detail_url() {
        return wap_detail_url;
    }

    public void setWap_detail_url(String wap_detail_url) {
        this.wap_detail_url = wap_detail_url;
    }

    public int getWith_hold_quantity() {
        return with_hold_quantity;
    }

    public void setWith_hold_quantity(int with_hold_quantity) {
        this.with_hold_quantity = with_hold_quantity;
    }
}

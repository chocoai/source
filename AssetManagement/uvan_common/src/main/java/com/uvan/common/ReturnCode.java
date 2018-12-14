package com.uvan.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author easter
 * @data 2018/12/10 10:24
 */
public class ReturnCode {
    private String returnCode;    //状态码
    private String returnDesc;    //状态码描述

    public static Map<String,String> codeMap = new HashMap<String, String>(){
        private static final long serialVersionUID = 1L;
        {
            put("200","成功");

            put("10001","登陆失败，系统用户密码错误");
            put("10002","参数为空");
            put("10003","账号被禁用，请联系管理员");
            put("10004","账号已被绑定");
            put("10005","用户不存在");

            //梵赞模块
            put("15001","没有梵赞赞赏数据");
            put("15002","没有时间类型");
            put("15003","没有userid");
            put("15004","没有开始时间");
            put("15005","没有部门id");
            put("15006","没有排名数据");
            put("15007","时间类型不对");
            put("15008","亲,一天对同一人不能赞赏超过10个哦");
            put("15009","userid错误");
            put("15010","获取access_token出错");
            put("15011","创建内购用户成功");
            put("15012","创建内购用户失败");
            put("15013","用户积分锁定为空");
            put("15014","积分与钱比例不对");
            put("15015","money或者point为空");
            put("15016","该用户没有该锁定积分的订单");
            put("15017","锁定积分大于用户已有积分");
            put("15018","生成订单失败");
            put("15019","用户没有锁定积分或者锁定积分不需要返还那么多");
            put("15020","没有这个订单");
            put("15021","订单状态错误");
            put("15022","这个订单不是这个用户的");
            put("15023","该用户退款梵砖积分数出错");
            put("15024","取消锁定积分失败");
            put("15025","取消锁定积分成功");
            put("15026","确认锁定积分成功");
            put("15027","退款成功");
            put("15028","用户未在内购创建");
            put("15029","已付款");
            put("15030","这个订单已经退款成功");
            put("15031","获取uvan_token成功");
            put("15032","uvan_token已过期");
            put("15033","订单已取消");
            put("15034","没有退款流水号");
            put("15035","退款流水号已经创建");

        }
    };


}

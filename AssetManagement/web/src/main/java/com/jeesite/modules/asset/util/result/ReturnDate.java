package com.jeesite.modules.asset.util.result;



public class ReturnDate {
    /**
     *
     * 1、系统未知错误 = -1,  //一般是抛了异常，且不清楚是哪类异常
     * 2、系统异常 = -100,  //已知的系统异常，前端可据此展示Message的信息，所以返回的Message字段是必填
     * 3、系统权限异常 = -300,  //适用于未授权，需跳转登录的异常
     * 4、请求成功 = 0,  //数据返回正确的提示码
     * 5、信息码：10000-19999  //特定系统提示码
     * 6、权限码：30000-39999  //特定系统权限码
     * 7、错误码：40000-49999  //特定系统错误码
     * 这些code分三类：<0：属于错误，=0：请求成功，>0：需要指定code来区分信息
     * 请求成功返回
     *
     *-1:系统异常，请重新登录
     *-300：跳转到系统登录页面
     * 10001：登陆失败，系统用户密码错误
     * 10002：参数为空
     * 10003：账号被禁用，请联系管理员
     * 10004：账号已被绑定~~~~
     *
     *
     * 10009   查询结果集为空
     * 11000   token过期
     * 11001   请求频繁
     *
     * @param object
     * @return
     */
    public static ReturnInfo success(Object object){
        ReturnInfo msg=new ReturnInfo();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        return msg;
    }
    public static ReturnInfo success(){
        return success(null);
    }

    public static ReturnInfo error(Integer code, String resultmsg){
        ReturnInfo msg=new ReturnInfo();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        msg.setData("");
        return msg;
    }
    public static ReturnInfo success(Integer code, String resultmsg, Object object){
        ReturnInfo msg=new ReturnInfo();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        msg.setData(object);
        return msg;
    }
    public static ReturnInfo error(Integer code, String resultmsg, Object object){
        ReturnInfo msg=new ReturnInfo();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        msg.setData(object);
        return msg;
    }
    public static <T> ReturnInfo errorObject(Integer code, String resultmsg){
        ReturnInfo<T> msg=new ReturnInfo<T>();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        msg.setData(null);
        return msg;
    }
    public static <T> ReturnInfo successObject(T obj){
        ReturnInfo<T> msg=new ReturnInfo<T>();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(obj);
        return msg;
    }
}

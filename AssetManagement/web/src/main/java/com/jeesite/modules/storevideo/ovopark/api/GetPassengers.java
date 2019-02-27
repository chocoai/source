package com.jeesite.modules.storevideo.ovopark.api;

import org.junit.Test;

import com.ovopark.gw.GwInitRequestHandler;
import com.ovopark.utils.OvoParkHttpClient;

public class GetPassengers {
	static final String apigwUrl="http://openapi.ovopark.com/m.api";
	static final String _akey="";
	static final String _asid="";

	@Test
	public void test(){
		GwInitRequestHandler reqHandler=new  GwInitRequestHandler(); 
		reqHandler.init();
		reqHandler.setApplicationKey(_akey);
		reqHandler.setApplicationSecret(_asid);
		reqHandler.setMethod("open.passengerflow.getPassengers");
		reqHandler.setGateUrl(apigwUrl);
		reqHandler.setParameter("orgid", "");
		reqHandler.setParameter("deviceMac", "");
		reqHandler.setParameter("depId", "");
		reqHandler.setParameter("starttime", "2018-07-27 00:00:00");
		reqHandler.setParameter("endtime", "2018-07-28 23:59:59");
		reqHandler.setParameter("pageNumber", "1");
		reqHandler.setParameter("pageSize", "20");
		OvoParkHttpClient httpClient=new OvoParkHttpClient();
		//获取请求带参数的url
		String requestUrl = reqHandler.getRequestURL();
		System.out.println(requestUrl);
		//获取debug信息
		String debuginfo = reqHandler.getDebugInfo();
		System.out.println("debuginfo:" + debuginfo);
		//设置请求内容
		httpClient.setReqContent(requestUrl);
		if(httpClient.call()){
			String resContent = httpClient.getResContent();
			System.out.println("responseContent:" + resContent);
		}
	}
}

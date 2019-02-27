package com.jeesite.modules.storevideo.ovopark.api;

import org.junit.Test;

import com.ovopark.gw.GwInitRequestHandler;
import com.ovopark.utils.OvoParkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class GetAgeData {
	static final String orgid = "118";
	static final String apigwUrl="http://openapi.ovopark.com/m.api";
	static final String _akey="S107-00000066";
	static final String _asid="3bbe816ba91c6026747818fd462";

	@Test
	public void test(){
		GwInitRequestHandler reqHandler=new  GwInitRequestHandler(); 
		reqHandler.init();
		reqHandler.setApplicationKey(_akey);
		reqHandler.setApplicationSecret(_asid);
		reqHandler.setMethod("open.openplatform.base.getAgeData");//open
		reqHandler.setGateUrl(apigwUrl);
		reqHandler.setParameter("orgid", orgid);
		reqHandler.setParameter("stime", "2019-01-18 00:00:00");
		reqHandler.setParameter("etime", "2019-01-22 23:59:59");
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

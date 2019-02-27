/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.record.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.record.dao.RecordLogDao;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.util.RedisHelp;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 日志管理Service
 * @author scarlett
 * @version 2018-09-17
 */
@Service
@Transactional(readOnly=true)
public class RecordLogService extends CrudService<RecordLogDao, RecordLog> {
	@Autowired
	private AmqpTemplate template;
	private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
	private static final String FANZAN_URL="http://am.frp.uvanart.com:9200";
	private static final String AGENT_ID="5110142";
	/**
	 * 获取单条数据
	 * @param recordLog
	 * @return
	 */
	@Override
	public RecordLog get(RecordLog recordLog) {
		return super.get(recordLog);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param recordLog
	 * @return
	 */
	@Override
	public Page<RecordLog> findPage(Page<RecordLog> page, RecordLog recordLog) {
		return super.findPage(page, recordLog);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param recordLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(RecordLog recordLog) {
		super.save(recordLog);
	}

	/**
	 * 更新状态
	 * @param recordLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(RecordLog recordLog) {
		super.updateStatus(recordLog);
	}

	/**
	 * 删除数据
	 * @param recordLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(RecordLog recordLog) {
		super.delete(recordLog);
	}

	/*public ReturnInfo insertLog(RecordLog recordLog){
	//	String jsonObject=JSONObject.fromObject(recordLog).toString();
		template.convertAndSend("recordLog",1);
		return ReturnDate.success();
	}*/
	public void inoutGoldMessage(String touser,String title,String singleTitle,String markdown){
		JSONObject jsonObject=new JSONObject();
		RecordLog recordLog=new RecordLog();
		recordLog.setCreateTime(new Date());
		String accessToken= RedisHelp.redisHelp.getDingDingAcessToken();
		jsonObject.put("touser",touser);
		jsonObject.put("agentid",AGENT_ID);
		jsonObject.put("msgtype","action_card");
		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("title",title);
		jsonObject1.put("markdown",markdown);
		jsonObject1.put("single_title",singleTitle);
		jsonObject1.put("single_url",FANZAN_URL);
		jsonObject.put("action_card",jsonObject1);
		String url=SEND_ADDRESS+accessToken;
		String responseinfo= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
		recordLog.setWriteTime(new Date());
		recordLog.setLevel("info");
		recordLog.setTitle("梵赞消息推送");
		recordLog.setType("fanzan_ding_message");
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("钉钉消息请求",jsonObject);
		jsonObject2.put("钉钉消息响应",responseinfo);
		recordLog.setContent(jsonObject2.toString());
//		template.convertAndSend("recordLog1",recordLog);
	}

}
/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.draw.entity.ActionCard;
import com.jeesite.modules.asset.draw.entity.LuckDetail;
import com.jeesite.modules.asset.draw.entity.PushMsg;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.service.AmSeqService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.draw.entity.LuckDraw;
import com.jeesite.modules.asset.draw.service.LuckDrawService;

import java.util.Date;
import java.util.List;

/**
 * 抽奖竞猜Controller
 * @author len
 * @version 2018-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/draw/luckDraw")
public class LuckDrawController extends BaseController {

	@Autowired
	private LuckDrawService luckDrawService;
	@Autowired
	private AmSeqService amSeqService;

	private static final String AGENT_ID="5110142";

	private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
	@Autowired
	private DingUserService dingUserService;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(LuckDrawController.class);

	@Autowired
	private AmFileUploadService amFileUploadService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public LuckDraw get(String documentCode, boolean isNewRecord) {
		return luckDrawService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("draw:luckDraw:view")
	@RequestMapping(value = {"list", ""})
	public String list(LuckDraw luckDraw, Model model) {
		model.addAttribute("luckDraw", luckDraw);
		return "asset/draw/luckDrawList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("draw:luckDraw:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<LuckDraw> listData(LuckDraw luckDraw, HttpServletRequest request, HttpServletResponse response) {
		Page<LuckDraw> page = luckDrawService.findPage(new Page<LuckDraw>(request, response), luckDraw); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("draw:luckDraw:view")
	@RequestMapping(value = "form")
	public String form(LuckDraw luckDraw, Model model) {
		if (luckDraw.getIsNewRecord()) {
			luckDraw.setDocumentCode(amSeqService.getSeq("CJ"));
			luckDraw.setPushStatus("0");
		}
		if ("0".equals(luckDraw.getPushStatus())) {
			luckDraw.setPushed(false);
		} else {
			luckDraw.setPushed(true);
		}
		model.addAttribute("luckDraw", luckDraw);
		return "asset/draw/luckDrawForm";
	}

	/**
	 * 保存抽奖竞猜
	 */
	@RequiresPermissions("draw:luckDraw:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated LuckDraw luckDraw) {
		if (luckDraw.getIsNewRecord()) {
			luckDraw.setBillTime(new Date());
			luckDraw.setDocumentCode(amSeqService.getCode("CJ"));
		}
		luckDrawService.save(luckDraw);
		return renderResult(Global.TRUE, text("保存抽奖竞猜成功！"));
	}
	
	/**
	 * 删除抽奖竞猜
	 */
	@RequiresPermissions("draw:luckDraw:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(LuckDraw luckDraw) {
		luckDrawService.delete(luckDraw);
		return renderResult(Global.TRUE, text("删除抽奖竞猜成功！"));
	}

	/**
	 * 获取中奖用户
	 * @return
	 */
	@RequiresPermissions("draw:luckDraw:edit")
	@ResponseBody
	@RequestMapping(value = "getPrizeUsers")
	public String getPrizeUsers(String documentCode, int winnersNum, String winningCode) {
		logger.info("获取中奖用户");
		String message = luckDrawService.getPrizeUsers(documentCode, winnersNum, winningCode);
		if (!"".equals(message)) {
			return renderResult(Global.FALSE, message);
		} else {
			return renderResult(Global.TRUE, "获取中奖员工成功！");
		}
	}

	/**
	 * 推送消息
	 * @param luckDraw
	 * @return
	 */
	@RequiresPermissions("draw:luckDraw:push")
	@ResponseBody
	@RequestMapping(value = "pushMsg")
	public String pushMsg(LuckDraw luckDraw) {
		logger.info("推送消息");
		if (luckDraw.getLuckDetailList() == null || luckDraw.getLuckDetailList().size() <= 0) {
			return renderResult(Global.FALSE, "没有中奖的员工！");
		}
		// 获取上传后的图片地址
		String filePath = amFileUploadService.getImg(luckDraw.getDocumentCode(), "luckDraw_image").get(0).getFileRealPath();
		ActionCard actionCard = new ActionCard();

		// 中奖码
		String winningCode = luckDraw.getWinningCode();
//		// 中奖类型
//		String prizeType = "“你已中奖，中奖码：【" + winningCode + "】!";
		actionCard.setTitle("hey，恭喜成为接头人！任务开启ing，请点击进入");

		actionCard.setText("![alt]("+ filePath +")");
//		actionCard.setContent(prizeType);
		String accessToken = RedisHelp.redisHelp.getDingDingAcessToken();
		String url = SEND_ADDRESS + accessToken;
		List<DingUser> dingUserList = ListUtils.newArrayList();
		for (LuckDetail luckDetail : luckDraw.getLuckDetailList()) {
			PushMsg pushMsg = new PushMsg();
			// 要发送的用户
			pushMsg.setTouser(luckDetail.getUserId());
			// 发送内容
			pushMsg.setMarkdown(actionCard);
			pushMsg.setAgentid(AGENT_ID);
			pushMsg.setMsgtype("markdown");
			// 推送消息
			String msg = HttpClientUtils.ajaxPostJson(url, JsonMapper.toJson(pushMsg));
			logger.info(msg);
			DingUser dingUser = dingUserService.get(luckDetail.getUserId());
			// 用户表【是否中奖】更新为1
			dingUser.setWinningPrize("1");
			// 中奖类型 = 中奖码
			dingUser.setPrizeType(winningCode);
			dingUserList.add(dingUser);
		}
		luckDrawService.saveData(luckDraw, dingUserList);
		return renderResult(Global.TRUE, "消息推送成功！！");
	}
}
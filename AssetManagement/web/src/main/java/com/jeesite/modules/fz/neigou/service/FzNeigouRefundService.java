package com.jeesite.modules.fz.neigou.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.idgen.IdWorker;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.dao.DingUserDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.UserLockIntegral;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.service.FzGoldChangeRecordService;
import com.jeesite.modules.fz.fzlogin.service.FzLoginService;
import com.jeesite.modules.fz.neigou.dao.FzNeigouRefundDao;
import com.jeesite.modules.fz.neigou.entity.FzNeigouRefund;
import com.jeesite.modules.fz.order.dao.FzNeigouOrderDao;
import com.jeesite.modules.fz.order.dao.FzNeigouOrderItemDao;
import com.jeesite.modules.fz.order.entity.FzNeigouFzgoldLog;
import com.jeesite.modules.fz.order.entity.FzNeigouOrder;
import com.jeesite.modules.fz.order.entity.FzNeigouOrderItem;
import com.jeesite.modules.fz.order.service.FzNeigouOrderService;
import com.jeesite.modules.fz.refundrecord.dao.FzRefundRecordDao;
import com.jeesite.modules.fz.refundrecord.entity.FzRefundRecord;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 内购相关业务
 *
 * @author easter
 * @data 2018/12/3 17:17
 */
@Service
@Transactional(readOnly = true)
public class FzNeigouRefundService extends CrudService<FzNeigouRefundDao, FzNeigouRefund> {
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private FzLoginService fzLoginService;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private FzNeigouOrderService fzNeigouOrderService;
    @Autowired
    private FzNeigouRefundDao fzNeigouRefundDao;
    @Autowired
    private DingUserDao dingUserDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FzNeigouOrderDao fzNeigouOrderDao;
    @Resource
    private FzNeigouOrderItemDao fzNeigouOrderItemDao;
    @Value("${neigou.grant_type}")
    private String grant_type;
    @Value("${neigou.client_id}")
    private String client_id;
    @Value("${neigou.client_secret}")
    private String client_secret;
    @Value("${neigou.access_token_url}")
    private String access_token_url;
    @Value("${neigou.create_neigouuser_url}")
    private String create_neigouuser_url;
    @Value("${neigou.order_url}")
    private String order_url;
    @Autowired
    private FzGoldChangeRecordService fzGoldChangeRecordService;
    /**
     * 获取acces_token
     */
    public String getAccessToken() throws Exception {
        String access_token = (String) stringRedisTemplate.opsForValue().get("neigou_access_token");
        if (access_token == null || "".equals(access_token)) {
            Map map = new HashMap();
            map.put("client_id", client_id);
            map.put("client_secret", client_secret);
            map.put("grant_type", grant_type);
            //String s = doPost(access_token_url, map, null, JSON.toJSONString(map), "UTF-8");
            String result = HttpClientUtils.post(access_token_url, map, "UTF-8");
            Object parse = com.alibaba.fastjson.JSONObject.parse(result);
            com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) parse;
            if (object == null) {
                return null;
            } else {
                //如果返回结果是正常的
                if ("true".equals(object.get("Result"))) {
                    com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject) object.get("Data");
                    access_token = (String) data.get("access_token");
                    stringRedisTemplate.opsForValue().set("neigou_access_token", access_token, 40, TimeUnit.MINUTES);
                }
            }
        }
        return access_token;
    }

    /*
     * 创建内购用户
     *
     * @param userid          用户钉钉id
     * @param isGetLoginToken 是否再次创建内购用户,true就去创建,一般写false就行了
     * @return
     */
    public ReturnInfo createNeigouUser(String userid, boolean isGetLoginToken) throws Exception {
        if ("".equals(userid)) {
            return ReturnDate.success(15003, "没有userid", null);
        }
        DingUser user = dingUserService.get(userid);
        if (user == null || "1".equals(user.getleft())) {
            return ReturnDate.success(15009, "userid错误", null);
        }
        String name = user.getName();
        Map userMap = new HashMap();
        userMap.put("external_user_id", userid);
        userMap.put("name", name);
        //获取内购的access_token
        String access_token = this.getAccessToken();
        Map<String, String> headermap = new HashMap();
        headermap.put("Content-Type", "application/x-www-form-urlencoded");
        headermap.put("AUTHORIZATION", "Bearer " + access_token);
        //headermap.put("Cache-Control", "no-cache");
        String aa = JSON.toJSONString(userMap);
        String ss = dingUserService.doPost(create_neigouuser_url, userMap, headermap, aa, "UTF-8");
        com.alibaba.fastjson.JSONObject parse = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(ss);
        if (parse != null) {
            String result = (String) parse.get("Result");
            if ("true".equals(result)) {
                if (isGetLoginToken) {
                    //这里表示创建内购成功之后继续去获取loging_token
                    return fzLoginService.getLoginToken(userid, false);
                } else {
                    //直接创建内购用户成功就行了
                    return ReturnDate.success(15011, "创建内购用户成功", null);
                }
            } else {
                return ReturnDate.success(15012, "创建内购用户失败", null);
            }
        } else {
            return ReturnDate.error(-100, "服务器忙", null);
        }
    }

    /**
     * 用户积分锁定
     *
     * @param userLockIntegral
     * @return
     */
    @Transactional
    public ReturnInfo lockIntegral(UserLockIntegral userLockIntegral) throws Exception {
        if (userLockIntegral == null) {
            return ReturnDate.success(15013, "用户积分锁定为空", null);
        }
        //操作日志记录
        FzNeigouFzgoldLog log = new FzNeigouFzgoldLog();
        //设置日志为用户积分锁定
        log.setAction("2");
        log.setCreateTime(new Date());
        String member_bn = userLockIntegral.getMember_bn();
        //数据库查询得到的实体
        DingUser user = dingUserService.get(member_bn);
        //离职员工不给创建订单
        if (user == null || "1".equals(user.getleft())) {
            log.setActionResult("userid错误");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15009, "userid错误", null);
        }
        log.setUserId(member_bn);
        log.setUserName(user.getName());
        Double money = userLockIntegral.getMoney();
        Double point = userLockIntegral.getPoint();
        if (money == 0 || point == null || money < 0 || point < 0) {
            log.setActionResult("money或者point为空");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15015, "money或者point为空", null);
        }
        //这个是为了防止积分兑换的比例不对,因为我们是1梵砖1块
        if ((double) money != point) {
            log.setActionResult("积分与钱比例不对");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15014, "积分与钱比例不对", null);
        }
        //用户全部梵砖积分
        Long convertibleGold = user.getConvertibleGold();
        if (convertibleGold == null) {
            convertibleGold = 0L;
        }
        //用户已经使用积分
        Double usedPoint = user.getUsedPoint();
        if (usedPoint == null) {
            usedPoint = 0D;
        }
        //已经锁定的用户梵砖积分数
        Double freezePoint = user.getFreezePoint();
        if (freezePoint == null) {
            freezePoint = 0D;
        }
        //用户可使用的积分数量为总共获得的梵砖-已使用的积分-已经冻结的积分
        if (point > convertibleGold - usedPoint - freezePoint) {
            log.setActionResult("锁定积分大于用户已有积分");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15017, "锁定积分大于用户已有积分", null);
        }
        //设置用户的冻结积分在原来的基础上加上订单冻结积分
        Double v = point + freezePoint;
        BigDecimal bigDecimal = new BigDecimal(v.toString()).setScale(2, ROUND_HALF_UP);
        user.setFreezePoint(bigDecimal.doubleValue());

        String trade_no = userLockIntegral.getTrade_no();
        FzNeigouOrder fzNeigouOrder = fzNeigouOrderService.get(trade_no);
        if (fzNeigouOrder != null && StringUtils.isNotEmpty(fzNeigouOrder.getOrderId())) {
            return ReturnDate.success(15018, "生成订单失败", null);
        }


        FzNeigouOrder order = new FzNeigouOrder();
        order.setOrderId(trade_no);
        order.setCreateDate(new Date());
        order.setUserid(member_bn);
        order.setFzNum(point);
        order.setUpdateDate(new Date());
        //设置订单状态为未支付,未完成
        order.setPayStatus("1");
        order.setOrderStatus("1");
        order.setUserName(user.getName());
        log.setOrderId(trade_no);
        log.setActionResult("锁定成功");
        rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
        user.setIsNewRecord(false);
        dingUserService.save(user);
        order.setIsNewRecord(true);
        fzNeigouOrderService.save(order);
        return ReturnDate.success(userLockIntegral);
    }

    /**
     * 锁定积分取消
     *
     * @param userLockIntegral
     * @return
     */
    @Transactional
    public ReturnInfo unLockIntegral(UserLockIntegral userLockIntegral) throws Exception {
        if (userLockIntegral == null) {
            return ReturnDate.success(15013, "用户积分锁定为空", null);
        }
        //操作日志记录
        FzNeigouFzgoldLog log = new FzNeigouFzgoldLog();
        log.setCreateTime(new Date());
        //设置日志为锁定积分取消
        log.setAction("3");
        String member_bn = userLockIntegral.getMember_bn();
        DingUser user = dingUserService.get(member_bn);
        if (user == null) {
            log.setActionResult("userid错误");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15009, "userid错误", null);
        }
        log.setUserId(member_bn);
        log.setUserName(user.getName());
        String trade_no = userLockIntegral.getTrade_no();
        log.setOrderId(trade_no);
        FzNeigouOrder fzNeigouOrder = fzNeigouOrderService.get(trade_no);
        //如果这个订单已经取消锁定了,不给取消,如果这个订单不属于这个用户的话,不给取消
        if (fzNeigouOrder == null || "2".equals(fzNeigouOrder.getOrderStatus()) || !member_bn.equals(fzNeigouOrder.getUserid())) {
            log.setActionResult("取消锁定积分失败");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15024, "取消锁定积分失败", null);
        }
        Double fzNum = fzNeigouOrder.getFzNum();
        if (fzNum == null) {
            fzNum = 0D;
        }
        Double freezePoint = user.getFreezePoint();
        if (freezePoint == null) {
            freezePoint = 0D;
        }

        //设置用户的冻结积分减掉这个订单需要支付的积分
        Double v = freezePoint - fzNum;
        BigDecimal bigDecimal = new BigDecimal(v.toString()).setScale(2, ROUND_HALF_UP);
        user.setFreezePoint(bigDecimal.doubleValue());
        //设置订单状态为已取消
        fzNeigouOrder.setOrderStatus("2");
        fzNeigouOrder.setUpdateDate(new Date());
        fzNeigouOrder.setUserName(user.getName());
        log.setActionResult("取消锁定积分成功");

        rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
        fzNeigouOrderService.update(fzNeigouOrder);
        dingUserService.update(user);
        return ReturnDate.success(15025, "取消锁定积分成功", null);
    }


    /**
     * 锁定积分正式使用
     *
     * @param userLockIntegral
     * @return
     */
    @Transactional(readOnly = false)
    public ReturnInfo realLockIntegral(UserLockIntegral userLockIntegral) {
        if (userLockIntegral == null) {
            return ReturnDate.success(15013, "用户积分锁定为空", null);
        }
        //操作日志记录
        FzNeigouFzgoldLog log = new FzNeigouFzgoldLog();
        //设置日志为锁定积分正式使用
        log.setAction("4");
        log.setCreateTime(new Date());
        String member_bn = userLockIntegral.getMember_bn();
        DingUser dingUser = dingUserService.get(member_bn);
        if (dingUser == null || "1".equals(dingUser.getleft())) {
            log.setActionResult("userid错误");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15009, "userid错误", null);
        }
        log.setUserId(member_bn);
        log.setUserName(dingUser.getName());
        String trade_no = userLockIntegral.getTrade_no();
        FzNeigouOrder fzNeigouOrder = fzNeigouOrderService.get(trade_no);
        if (fzNeigouOrder == null || !member_bn.equals(fzNeigouOrder.getUserid())) {
            log.setActionResult("该用户没有该锁定积分的订单");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15016, "该用户没有该锁定积分的订单", null);
        }
        Double fzNum = fzNeigouOrder.getFzNum();
        //如果订单已经付款了,那么return
        if ("2".equals(fzNeigouOrder.getPayStatus())) {
            log.setActionResult("已付款");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15029, "已付款", null);
        }
        if ("2".equals(fzNeigouOrder.getOrderStatus())) {
            log.setActionResult("订单已取消");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15033, "订单已取消", null);
        }
        //用户总共有的梵砖积分数
        Long convertibleGold = dingUser.getConvertibleGold();
        if (convertibleGold == null) {
            convertibleGold = 0L;
        }
        //已经使用的梵砖积分数
        Double used_point = dingUser.getUsedPoint();
        if (used_point == null) {
            used_point = 0D;
        }
        //剩下的梵砖积分数
        double point = convertibleGold - used_point;
        //这个订单锁定的梵砖积分数

        if (fzNum == null) {
            fzNum = 0D;
        }
        //用户已经锁定的梵砖积分数
        Double freezePoint = dingUser.getFreezePoint();
        if (freezePoint == null) {
            freezePoint = 0D;
        }

        Double v1 = freezePoint - fzNum;
        BigDecimal bigDecimal1 = new BigDecimal(v1.toString()).setScale(2, ROUND_HALF_UP);
        Double v2 = used_point + fzNum;
        BigDecimal bigDecimal2 = new BigDecimal(v2.toString()).setScale(2, ROUND_HALF_UP);
        dingUser.setFreezePoint(bigDecimal1.doubleValue());
        dingUser.setUsedPoint(bigDecimal2.doubleValue());
        //设置订单状态为已支付
        fzNeigouOrder.setPayStatus("2");
        fzNeigouOrder.setOrderStatus("3");
        fzNeigouOrder.setUpdateDate(new Date());
        fzNeigouOrder.setUserName(dingUser.getName());
        log.setOrderId(trade_no);
        log.setActionResult("确认锁定积分成功");

        rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
        dingUser.setIsNewRecord(false);
        dingUserService.save(dingUser);
        fzNeigouOrder.setIsNewRecord(false);
        fzNeigouOrderService.save(fzNeigouOrder);
        FzExpenditureRecord fzExpenditureRecord = new FzExpenditureRecord();
        fzExpenditureRecord.setUserId(dingUser.getUserid());
        fzExpenditureRecord.setExpendNum(fzNum);
        fzExpenditureRecord.setUserName(dingUser.getName());
        rabbitTemplate.convertAndSend(FzTask.fzExpendRecordsP,fzExpenditureRecord);
        addRecord(fzNum, dingUser, "1");
        return ReturnDate.success(15026, "确认锁定积分成功", null);
    }

    @Autowired
    private FzRefundRecordDao fzRefundRecordDao;

    @Transactional(readOnly = false)
    public void addRecord(Double fzNum, DingUser dingUser, String inOrOut) {
        FzGoldChangeRecord fzGoldChangeRecord = new FzGoldChangeRecord();
        // 梵钻数量
        fzGoldChangeRecord.setNumber(fzNum);
        fzGoldChangeRecord.setGoldType("0");
        fzGoldChangeRecord.setInOrOut(inOrOut);
        fzGoldChangeRecord.setUserid(dingUser.getUserid());
        if ("1".equals(inOrOut)) {
            fzGoldChangeRecord.setRecordName("点滴商城消费");
        } else {
            fzGoldChangeRecord.setRecordName("点滴商城退款");
            FzRefundRecord fzRefundRecord = new FzRefundRecord();
            fzRefundRecord.setRefundCode(IdGen.nextId());
            fzRefundRecord.setRefundNum(fzNum);
            fzRefundRecord.setUserId(dingUser.getUserid());
            fzRefundRecord.setUserName(dingUser.getName());
            fzRefundRecord.setRefundMode("点滴商城退款");
            fzRefundRecord.setRefundTime(new Date());
            fzRefundRecordDao.insert(fzRefundRecord);
        }

        fzGoldChangeRecordService.saveMall(fzGoldChangeRecord, dingUser);
    }

    /**
     * 支付后返还积分,退款用
     *
     * @param userLockIntegral
     * @return
     */
    @Transactional
    public ReturnInfo returnPoint(UserLockIntegral userLockIntegral) throws Exception {
        if (userLockIntegral == null) {
            return ReturnDate.success(15013, "用户积分锁定为空", null);
        }
        //操作日志记录
        FzNeigouFzgoldLog log = new FzNeigouFzgoldLog();
        //设置日志为支付后返还积分,退款用
        log.setAction("5");
        log.setCreateTime(new Date());
        String member_bn = userLockIntegral.getMember_bn();
        DingUser user = dingUserService.get(member_bn);
        //离职的话就return
        if (user == null || "1".equals(user.getleft())) {
            log.setActionResult("userid错误");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15009, "userid错误", null);
        }
        log.setUserId(member_bn);
        log.setUserName(user.getName());
        //退款流水号
        String refund_id = userLockIntegral.getRefund_id();
        if (refund_id == null || "".equals(refund_id)) {
            log.setActionResult("没有退款流水号");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.error(15034, "没有退款流水号", null);
        }
        FzNeigouRefund refund = fzNeigouRefundDao.findOne(refund_id);
        if (refund != null) {
            log.setActionResult("退款流水号已经创建");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.error(15035, "退款流水号已经创建", null);
        }
        String trade_no = userLockIntegral.getTrade_no();
        FzNeigouOrder fzNeigouOrder = fzNeigouOrderService.get(trade_no);
        if (fzNeigouOrder == null) {
            log.setActionResult("没有这个订单");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15020, "没有这个订单", null);
        }
        if ("1".equals(fzNeigouOrder.getPayStatus()) || "3".equals(fzNeigouOrder.getPayStatus())) {
            log.setActionResult("订单状态错误");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15021, "订单状态错误", null);
        }
        if (!member_bn.equals(fzNeigouOrder.getUserid())) {
            log.setActionResult("这个订单不是这个用户的");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15022, "这个订单不是这个用户的", null);
        }
        /*if("3".equals(fzNeigouOrder.getPayStatus())){
            log.setActionResult("这个订单已经退款成功");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP,log);
            return ReturnDate.success(15030, "这个订单已经退款成功", null);
        }*/
        //用户已使用的积分
        Double usedPoint = user.getUsedPoint();
        //这个订单消耗的积分
        Double fzNum = fzNeigouOrder.getFzNum();
        if (fzNum == null) {
            fzNum = 0D;
        }
        //退款退多少积分
        Double point = userLockIntegral.getPoint();
        Double money = userLockIntegral.getMoney();
        if (point == null) {
            point = 0D;
        }
        if (money == null) {
            money = 0D;
        }
        //这个订单已经退款的
        Double refundNum = fzNeigouOrder.getRefundNum();
        if (refundNum == null) {
            refundNum = 0D;
        }
        //这个是为了防止积分兑换的比例不对,因为我们是1梵砖1块
        if ((double) money != point) {
            log.setActionResult("积分与钱比例不对");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15014, "积分与钱比例不对", null);
        }
        //退款的积分不能比 (这个订单消耗的积分 - 已经退款的积分) 还要多
        if (usedPoint == null || usedPoint == 0 || point > fzNum - refundNum || money < 0 || point < 0) {
            log.setActionResult("该用户退款梵砖积分数出错");
            rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
            return ReturnDate.success(15023, "该用户退款梵砖积分数出错", null);
        }

        log.setActionResult("退款" + point + "积分成功");
        log.setOrderId(trade_no);
        rabbitTemplate.convertAndSend(FzTask.fzNeigouOrderLogsP, log);
        if (point == fzNum - refundNum) {
            //设置订单状态为已退款
            fzNeigouOrder.setPayStatus("3");
        } else {
            //设置订单状态为退款一部分
            fzNeigouOrder.setPayStatus("4");
        }
        //设置退款信息并插入数据库
        refund = new FzNeigouRefund();
        refund.setOrderId(trade_no);
        refund.setRefundDate(new Date());
        refund.setRefundId(refund_id);
        refund.setUserId(member_bn);
        refund.setRefundPoint(point);
        fzNeigouRefundDao.insert(refund);
        //设置订单退款的积分数量
        fzNeigouOrder.setRefundNum(point + refundNum);
        fzNeigouOrder.setOrderStatus("2");
        fzNeigouOrder.setUpdateDate(new Date());
        fzNeigouOrder.setUserName(user.getName());
        fzNeigouOrderService.update(fzNeigouOrder);

        //设置用户的已使用积分再减掉要退款的积分,如果是多个商品的话,那退款积分可能是小于这个订单支付的积分的
        Double v = usedPoint - point;
        BigDecimal bigDecimal = new BigDecimal(v.toString()).setScale(2, ROUND_HALF_UP);
        user.setUsedPoint(bigDecimal.doubleValue());
        dingUserDao.update(user);
        addRecord(fzNum, user, "0");
        return ReturnDate.success(15027, "退款成功", userLockIntegral);
    }

    /**
     * 根据开始时间和结束时间查询内购订单,并且更新数据库
     *
     * @param start_time
     * @param end_time
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void updateOrderIno(long start_time, long end_time) {
        Map dataMap = new HashMap();
        dataMap.put("start_time", start_time);
        dataMap.put("end_time", end_time);
        Map headerMap = new HashMap();
        String access_token = null;
        try {
            access_token = this.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        headerMap.put("AUTHORIZATION", "Bearer " + access_token);
        String aa = JSON.toJSONString(dataMap);
        String result = dingUserService.doPost(order_url, dataMap, headerMap, aa, "UTF-8");
        List<FzNeigouOrder> orderList = new ArrayList<>();
        List<FzNeigouOrderItem> itemList = new ArrayList<>();
        List<FzNeigouOrder> fzNeigouOrderList = ListUtils.newArrayList();

        if (result != null) {
            JSONObject parse = (JSONObject) JSON.parse(result);
            String errorMsg = (String) parse.get("ErrorMsg");
            if ("请求成功".equals(errorMsg)) {
                JSONArray array = (JSONArray) parse.get("Data");
                if (array != null) {
                    for (Object obj : array) {
                        JSONObject data = (JSONObject) obj;
                        String order_id = (String) data.get("order_id");
                        //得到父订单实体 0代表是父订单
                        composeOrderEntity(data, fzNeigouOrderList, orderList);
                        //获取父订单的商品数据并且添加到集合里面
                        JSONArray items = (JSONArray) data.get("items");
                        for (Object object : items) {
                            FzNeigouOrderItem fzNeigouOrderItem = composeOrderProduct(object, order_id);
                            if (fzNeigouOrderItem != null) {
                                itemList.add(fzNeigouOrderItem);
                            }
                        }
                        JSONArray split_order = (JSONArray) data.get("split_order");

                        if (split_order != null && split_order.size() > 0) {
                            //这里是子订单
                            for (Object o : split_order) {
                                JSONObject orderData = (JSONObject) o;
                                String orderId = (String) orderData.get("order_id");
                                // 1代表是子订单
                                composeOrderEntity(orderData, fzNeigouOrderList, orderList);
                                //获取子订单的商品数据集合
                                JSONArray proItems = (JSONArray) orderData.get("items");
                                for (Object proItem : proItems) {
                                    FzNeigouOrderItem fzNeigouOrderItem = composeOrderProduct(proItem, orderId);
                                    if (fzNeigouOrderItem != null) {
                                        itemList.add(fzNeigouOrderItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 获取最新的订单数据更新
        if (ListUtils.isNotEmpty(orderList)) {
            fzNeigouOrderDao.updateBatch(orderList);
        }
        // 获取最新的子订单数据更新
        if (ListUtils.isNotEmpty(fzNeigouOrderList)) {
            fzNeigouOrderDao.insertBatch(fzNeigouOrderList);
        }
        if (itemList != null && itemList.size() > 0) {
            fzNeigouOrderItemDao.insertBatch(itemList);
        }
    }

    /**
     * 组装内购订单实体类
     *
     * @param data
     * @return
     */
    public void composeOrderEntity(JSONObject data, List<FzNeigouOrder> fzNeigouOrderList, List<FzNeigouOrder> orderList) {
        if (data != null) {
            String order_id = (String) data.get("order_id");
            FzNeigouOrder fzNeigouOrder = fzNeigouOrderService.get(order_id);
            if (fzNeigouOrder == null) {
                fzNeigouOrder = new FzNeigouOrder();
                fzNeigouOrder.setOrderId(order_id);
                fzNeigouOrder.setIsNewRecord(true);
                // 如果是子订单

                fzNeigouOrder.setPayStatus(data.get("pay_status").toString());
                fzNeigouOrder.setShipStatus(data.get("ship_status").toString());

            }
            fzNeigouOrder.setOrderStatus(data.get("status").toString());
            //收货人姓名
            fzNeigouOrder.setShipName((String) data.get("ship_name"));
            //收货人手机号
            fzNeigouOrder.setReceiverMobile((String) data.get("ship_mobile"));
            //收货人所在省
            fzNeigouOrder.setProvince((String) data.get("ship_province"));
            //收货人所在市
            fzNeigouOrder.setCity((String) data.get("ship_city"));
            //收货人所在县
            fzNeigouOrder.setCounty((String) data.get("ship_county"));
            //收货人所在镇
            fzNeigouOrder.setTown((String) data.get("ship_town"));
            //收货人详细地址
            fzNeigouOrder.setDetailAddress((String) data.get("ship_addr"));
            //发货状态 1：未发货 2：已发货
            fzNeigouOrder.setShipStatus(data.get("ship_status") + "");
            //快递号
            fzNeigouOrder.setLogiNo((String) data.get("logi_no"));
            //快递公司
            fzNeigouOrder.setLogiName((String) data.get("logi_name"));
            //设置订单支付金额(钱)
            Double final_amount = Double.parseDouble((String) data.get("final_amount"));
            Double point_amount = Double.parseDouble((String) data.get("point_amount"));
            if (final_amount == point_amount) {
                fzNeigouOrder.setMoney(0D);
            } else {
                double v = final_amount - point_amount;
                BigDecimal bigDecimal = new BigDecimal(v).setScale(3, ROUND_HALF_UP);
                fzNeigouOrder.setMoney(bigDecimal.doubleValue());
            }
            Object p_id = data.get("p_id");
            if (p_id == null) {
                fzNeigouOrder.setpId("0");
            } else {
                fzNeigouOrder.setpId((String) p_id);
            }
            if (fzNeigouOrder.getIsNewRecord()) {
                fzNeigouOrderList.add(fzNeigouOrder);
            } else {
                orderList.add(fzNeigouOrder);
            }



        }
    }

    /**
     * 组装订单实体类
     *
     * @return
     */
    public FzNeigouOrderItem composeOrderProduct(Object object, String order_id) {
        IdWorker idWorker = new IdWorker(-1, -1);
        if (object instanceof JSONObject) {
            JSONObject item = (JSONObject) object;
            if (item != null) {
                List<FzNeigouOrderItem> list = fzNeigouOrderItemDao.getFzNeigouOrderItemByOrderId(order_id);
                if(list == null || list.size() == 0){
                    FzNeigouOrderItem fzNeigouOrderItem = new FzNeigouOrderItem();
                    fzNeigouOrderItem.setOrderId(order_id);
                    fzNeigouOrderItem.setItemId(idWorker.nextId() + "");
                    //货品名称
                    fzNeigouOrderItem.setTitle((String) item.get("title"));
                    //购买数量
                    fzNeigouOrderItem.setNums(Long.parseLong(item.get("nums") + ""));
                    //货品单价
                    fzNeigouOrderItem.setPrice(Double.parseDouble("" + item.get("price")));
                    //货品总价
                    fzNeigouOrderItem.setAmount(Double.parseDouble(item.get("amount") + ""));
                    //内购货品bn
                    fzNeigouOrderItem.setProductBn((String) item.get("product_bn"));
                    return fzNeigouOrderItem;
                }
            }
        }
        return null;
    }
}

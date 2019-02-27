package com.jeesite.modules.storevideo.msgserver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.storevideo.camera.entity.SvTv;
import com.jeesite.modules.storevideo.camera.service.SvTvService;
import com.jeesite.modules.storevideo.tvclient.entity.ClientLogType;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClient;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientLogService;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientService;
import com.jeesite.modules.util.ObjectUtils;
import com.jeesite.modules.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//@ServerEndpoint("/websocket/{user}")
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    public static ConcurrentHashMap<String, WebSocketServer> clients = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String ip;
    private int port;
    private Date lastPushDate = null;

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);



    public static SvTvClientService svTvClientService;
    public static SvTvService svTvService;
    public static SvTvClientLogService svTvClientLogService;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.lastPushDate = new Date();
        this.session = session;
        try {
            InetSocketAddress inetSocketAddress = getRemoteAddress(session);

            if(inetSocketAddress != null){
                ip = inetSocketAddress.getAddress().toString().replace("/", "");
                port = inetSocketAddress.getPort();

                SvTvClient svTvClient;
                clients.put(ip, this);
                svTvClientLogService.addLog(ClientLogType.INFO, ip,"连接打开");

                SvTvClient query = new SvTvClient();
                query.setIp(ip);
                List<SvTvClient> svTvClientList = svTvClientService.findList(query);
                Optional<SvTvClient> optionalSvTvClient = getTvClientByIpFromDb(ip);
                if(optionalSvTvClient.isPresent()){
                    svTvClient = optionalSvTvClient.get();
                    //svTvClientLogService.addLog(ClientLogType.INFO, ip,"已取得svTvClient:"+JSONObject.toJSONString(svTvClient));
                } else {
                    //找不到客户端信息，则查找电视，添加一个客户端
                    SvTv svTvQuery = new SvTv();
                    svTvQuery.setTvNumber(ip);
                    List<SvTv> svTv = svTvService.findList(svTvQuery);
                    if(svTv == null || svTv.size() == 0){
                        String msg = ip + "没有配置电视";
                        log.error(msg);
                        sendMessage(msg);
                        svTvClientLogService.addLog(ClientLogType.ERROR, ip,"没有配置电视");
                        return;
                    }

                    svTvClient = new SvTvClient();
                    svTvClient.setIsNewRecord(true);
                    svTvClient.setClientCode(ip);
                    svTvClient.setTvCode(svTv.get(0).getTvCode());
                    svTvClient.setIp(ip);
                }

                svTvClient.setOnline("1");
                svTvClientService.save(svTvClient);
                String msg = ip + " 连接成功";
                log.info(msg);
                sendMessage(msg);
                svTvClientLogService.addLog(ClientLogType.INFO, ip, "连接成功");
            }
            else{
                String msg = "获取IP失败";
                log.error(msg);
                sendMessage(msg);
                svTvClientLogService.addLog(ClientLogType.ERROR, ip,"获取IP失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            svTvClientLogService.addLog(ClientLogType.ERROR, ip, e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        Optional<SvTvClient> optionalSvTvClient = getTvClientByIpFromDb(ip);
        if(optionalSvTvClient.isPresent()){
            SvTvClient svTvClient = optionalSvTvClient.get();
            svTvClient.setOnline("0");  //设置客户端离线
            svTvClientService.save(svTvClient);
        }
        clients.remove(ip);  //从set中删除
        //log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        svTvClientLogService.addLog(ClientLogType.INFO, ip, "断开连接");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {

        svTvClientLogService.addLog(ClientLogType.INFO, ip, "收到客户端消息:" + message);
        //log.info("来自客户端的消息:" + message);
        //
        ////群发消息
        //for (WebSocketServer item : webSocketSet) {
        //    try {
        //        item.sendMessage(message);
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //}
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        //svTvClientLogService.addLog(ClientLogType.ERROR, ip, error.getMessage());
        error.printStackTrace();
    }

    public static void sendMessageToIp(String ip, String message){
        WebSocketServer client =  getClient(ip);
        if(client == null) return;
        client.sendMessage(message);
    }

    public void sendMessage(String message){
        //5秒内推送过的不能再推
        Date now = new Date();
        long secTimespan = (now.getTime() - this.lastPushDate.getTime())/1000;
        this.lastPushDate = now;
        log.info("时间间隔:" + secTimespan);
        //if(secTimespan < 5){
        //    svTvClientLogService.addLog(ClientLogType.ERROR, ip, "推送间隔少于5秒");
        //    return;
        //}
        try {
            this.session.getBasicRemote().sendText(message);
            svTvClientLogService.addLog(ClientLogType.INFO, ip, "推送消息成功:" + message);
        }
        catch (IOException e){
            svTvClientLogService.addLog(ClientLogType.ERROR, ip, "推送消息失败:" + e.getStackTrace());
            e.printStackTrace();
        }

    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        //log.info(message);
        //for (WebSocketServer item : webSocketSet) {
        //    try {
        //        item.sendMessage(message);
        //    } catch (IOException e) {
        //        continue;
        //    }
        //}
    }

    private static WebSocketServer getClient(String ip){
        WebSocketServer client =  WebSocketServer.clients.get(ip);
        if(client == null){
            svTvClientLogService.addLog(ClientLogType.WARNING, ip, "不存在客户端");
            return null;
        }
        return client;
    }

    public static String getIsOnline(String ip){
        WebSocketServer client = getClient(ip);
        return client == null ? "0": "1";
    }

    private Optional<SvTvClient> getTvClientByIpFromDb(String ip){
        SvTvClient query = new SvTvClient();
        query.setIp(ip);
        List<SvTvClient> svTvClientList = svTvClientService.findList(query);
        return svTvClientList.stream().findFirst();
    }

    public static synchronized int getOnlineCount() {
        return clients.size();
    }


    public static InetSocketAddress getInetSocketAddress(Session session) {
        if (session == null) {
            return null;
        }
        RemoteEndpoint.Async async = session.getAsyncRemote();

        //在Tomcat 8.0.x版本有效
        //InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        //在Tomcat 8.5以上版本有效
        InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async, "base#socketWrapper#socket#sc#remoteAddress");
        return addr;
    }

    private InetSocketAddress getRemoteAddress(Session session) {
        InetSocketAddress isa = getInetSocketAddress(session);
        if(ObjectUtils.isNotEmpty(isa)) {
            return isa;
        }
        return null;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }

        return null;
    }
}
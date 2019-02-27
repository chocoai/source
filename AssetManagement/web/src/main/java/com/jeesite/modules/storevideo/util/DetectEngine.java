package com.jeesite.modules.storevideo.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.storevideo.camera.service.SvCameraService;
import com.jeesite.modules.storevideo.faceplus.FaceplusApi;
import com.jeesite.modules.storevideo.faceplus.SearchResult;
import com.jeesite.modules.storevideo.faceplus.SearchResultItem;
import com.jeesite.modules.storevideo.msgserver.VideoMsg;
import com.jeesite.modules.storevideo.msgserver.WebSocketServer;
import com.jeesite.modules.storevideo.video.entity.SvVideo;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlatResult;
import com.jeesite.modules.storevideo.video.service.SvFaceDetectLogService;
import com.jeesite.modules.storevideo.video.service.SvVideoRlatService;
import com.jeesite.modules.storevideo.video.service.SvVideoService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DetectEngine {

    @Autowired
    private static SvVideoService svVideoService;
    @Autowired
    private SvVideoRlatService svVideoRlatService;
    @Autowired
    private SvFaceDetectLogService svFaceDetectLogService;
    @Autowired
    private SvCameraService svCameraService;
    @Autowired
    private FaceplusApi faceplusApi;

    private static Logger log = LoggerFactory.getLogger(DetectEngine.class);

    @Value("${videoPlatform.push_timespan}")
    private Long PUSH_TIMESPAN;
    @Value("${videoPlatform.customer_left_timespan}")
    private Long CUSTOMER_LEFT_TIMESPAN;
    @Value("${faceplus.match_confidence}")
    private Float MATCH_CONFIDENCE;


    /**
     * key:cameraMacAndUserId like D47C44900186_userid
     * value:timespan
     */
    private final static ConcurrentHashMap<String, Long> lastPushDateMap = new ConcurrentHashMap<>();

    private static VideoMsg defaultVideoMsg;
    public static VideoMsg getDefaultVideoMsg() {
        if(defaultVideoMsg == null){
            SvVideo svVideo = new SvVideo();
            svVideo.setVideoType("default");
            List<SvVideo> svVideoList = svVideoService.findList(svVideo);
            if(ListUtils.isNotEmpty(svVideoList)){
                defaultVideoMsg = new VideoMsg();
                defaultVideoMsg.setFlag("clear");
                defaultVideoMsg.setType("default");
                defaultVideoMsg.setVideoId(svVideoList.stream().map(SvVideo::getVideoId).collect(Collectors.toList()));
            }
        }
        return defaultVideoMsg;
    }

    private static List<DictData> ageList;
    public String getAgeLabel(String age) {
        if(ageList == null){
            List<DictData> list = DictUtils.getDictList("sv_type");
            list.stream().filter(a->a.getDictLabel().equals("年龄")).findFirst().ifPresent(a->{
                ageList = list.stream().filter(b->b.getParentCode().equals(a.getDictCode())).collect(Collectors.toList());
            });
        }
        Long iAge = Long.valueOf(age);
        Optional<DictData> optionalDictData = ageList.stream().filter(a-> iAge > a.getExtend().getExtendI1() && iAge <= a.getExtend().getExtendI2()).findFirst();
        if(optionalDictData.isPresent()){
            return optionalDictData.get().getDictLabel();
        }
        return ageList.get(0).getDictLabel();
    }


    /**
     * 发送图片url到face++获取facetoken
     * @param imageUrl
     * @return
     */
    public String getFaceTokenByImageUrl(String imageUrl) {
        String faceToken = null;
        SearchResult searchResult = faceplusApi.getResultByUrl(imageUrl);
        if(!ObjectUtils.isEmpty(searchResult) && !ListUtils.isEmpty(searchResult.getResults())){
            Optional<SearchResultItem> optionalSearchResult = searchResult.getResults().stream().findFirst();
            if(optionalSearchResult.isPresent()){
                SearchResultItem searchResultItem = optionalSearchResult.get();
                if(searchResultItem.getConfidence() > MATCH_CONFIDENCE){
                    //String facePlusApiUrl = "https://s2bapi.uvanart.com/xc/faceimage/GetPersonInfoBy?faceToken=" + a.getFace_token();
                    //String result = HttpClientUtils.ajaxGet(facePlusApiUrl);
                    //S2bPhotoDatum s2bPhotoDatum = JSONObject.parseObject(result, S2bPhotoDatum.class);
                    //System.out.println("请求梵店");

                    faceToken = searchResultItem.getFace_token();
                }
            }
        }
        return faceToken;
    }

    /**
     * 推送视频
     * @param param
     */
    public void pushVideo(Map<String, String> param){
        String cameraMac = param.get("cameraMac").toString();

        param.remove("cameraMac");

        String userId = param.get("userId");
        //前人未离开，不要推送新内容
        if(isNotLeaved(cameraMac, userId)) {
            log.debug("客人正在观看屏幕，取消推送");
            return;
        }

        //String label = getAgeLabel(param.get("age"));

        VideoMsg videoMsg = getVideoMessage(param);
        String message = JSONObject.toJSONString(videoMsg);
        sendVideoToTv(cameraMac, message, userId);

    }

    /**
     * 获取匹配的视频列表
     * @param param
     * @return
     */
    private VideoMsg getVideoMessage(Map param){
        VideoMsg videoMsg = null;
        List<SvVideoRlatResult> list = svVideoRlatService.getVideoListByMap(param);
        if(ListUtils.isEmpty(list)) {
            videoMsg = getDefaultVideoMsg();
        } else {
            videoMsg = new VideoMsg();
            videoMsg.setType("customer");
            videoMsg.setFlag("clear");
            videoMsg.setVideoId(list.stream().map(SvVideoRlatResult::getVideoCode).skip(0).limit(2).collect(Collectors.toList()));
        }
        return videoMsg;
    }

    /**
     * 发送视频到电视机
     * @param cameraMac
     * @param message
     */
    private void sendVideoToTv(String cameraMac, String message, String userId){
        List<String> ipList = svCameraService.findPushIps(cameraMac);
        if(ListUtils.isNotEmpty(ipList)){
            for(String ip: ipList){
                WebSocketServer.sendMessageToIp(ip, message);
            }
            lastPushDateMap.put(cameraMac+"_"+userId, new Date().getTime());
        }
    }

    /**
     * 上次推送间隔小于10秒视为未离开
     * @param cameraMac
     * @param userId
     * @return
     */
    public boolean isNotLeaved(String cameraMac, String userId){
        long lastPushTimespan = lastPushDateMap.getOrDefault(cameraMac+"_"+userId, -1L);
        return lastPushTimespan != -1 && (new Date().getTime() - lastPushTimespan) < CUSTOMER_LEFT_TIMESPAN;
    }
}

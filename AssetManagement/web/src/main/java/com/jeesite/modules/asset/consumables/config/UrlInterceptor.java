package com.jeesite.modules.asset.consumables.config;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.asset.util.RedisData;
import com.jeesite.modules.asset.util.RedisService;
import com.jeesite.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截所有请求
 *
 * @author Jace Xiong
 */
@EnableWebMvc
public class UrlInterceptor implements HandlerInterceptor  {
    private static Logger LOGGER = LoggerFactory.getLogger(UrlInterceptor.class);




    @Autowired
    private RedisService service;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
//    @Resource
//    private RedisTemplate<String, RedisData> redisTemplate;
    /**
     * 在控制器执行前调用
     * @param request 请求参数
     * @param response 响应参数
     * @param handler 请求头
     * @return boolean
     * @throws Exception 抛异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//        LOGGER.info("拦截控制单据方法-->01");
        boolean isProsses=false;  //是否有人在使用
        //设置字符流编码，而且还会添加content-Type响应头，这个头通知浏览器用utf-8解码。
//        RedisService service=new RedisService();
        String documentCode = request.getParameter("code");
        String isDel=request.getParameter("isDel");
        //删除的校验
        if (documentCode != null && documentCode.length() > 0&&isDel!=null&&isDel.length()>0) {
            String rstCode=null;
            String user=null;
            String[] codeList = documentCode.split(",");
            for (int i = 0; i < codeList.length; i++) {
                RedisData redisData=service.getFlag(codeList[i]);
                if (redisData!=null){
                    rstCode=codeList[i];
                    user=redisData.getUserCode();
                    isProsses=true;
                    break;
                }
             }
             if (isProsses){
                 response.setCharacterEncoding("utf-8");
                 response.setContentType("application/json;charset=utf-8");
                 Map<String, Object> map = new HashMap<>(16);
                 map.put("code", "500");
                 map.put("msg", rstCode+" 该单据已被"+user+"使用！！");
                 map.put("flag", "false");
                 response.getWriter().write(JSON.toJSONString(map));
                 return false;
             }else {
                 response.setCharacterEncoding("utf-8");
                 response.setContentType("application/json;charset=utf-8");
                 Map<String, Object> map = new HashMap<>(16);
                 map.put("code", "200");
                 map.put("msg", "");
                 map.put("flag", "true");
                 response.getWriter().write(JSON.toJSONString(map));
                 return false;
             }
            }


        //截取修改redis时间的方法，放行http://localhost:8080/a/redisUnits/updataTime
        String method=request.getRequestURL().toString();
        String [] a=method.split("/");
        if (a.length>=6){
            method=a[5];
        }
        if (documentCode!=null&&documentCode.length()>0&&!("updataTime".equals(method))){
            RedisData redisData=service.getFlag(documentCode);
            if(redisData!=null&&redisData.getFlag()==1) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                Map<String, Object> map = new HashMap<>(16);
                map.put("code", "500");
                map.put("msg", "该单据已被"+redisData.getUserCode()+"使用！！");
                map.put("flag", "false");
                response.getWriter().write(JSON.toJSONString(map));
                return false;
            }else {
                RedisData data=new RedisData();
                data.setUserCode(UserUtils.getUser().getLoginCode());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                String formatStr =formatter.format(new Date());
                data.setDateTime(formatStr);
                data.setFlag(1);
                JSONObject json = JSONObject.fromObject(data);
                String rst=json.toString();
                redisTemplate.opsForValue().set(documentCode,rst , 4,TimeUnit.SECONDS);
                Map<String, Object> map = new HashMap<>(16);
                map.put("code", "");
                map.put("msg", "");
                map.put("flag", "true");
                response.getWriter().write(JSON.toJSONString(map));
                return false;
            }
        }else {
            return true;
        }
    }

    /**
     * 在后端控制器执行后调
     * @param request 请求参数
     * @param response 响应参数
     * @param handler 请求头
     * @param modelAndView 视图
     * @throws Exception 抛异常
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
    /**
     * 整个请求执行完成后调用
     * @param request 请求参数
     * @param response 响应参数
     * @param handler 请求头
     * @param ex 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
    }


}

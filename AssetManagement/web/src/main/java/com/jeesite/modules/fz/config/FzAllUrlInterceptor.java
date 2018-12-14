package com.jeesite.modules.fz.config;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.fgc.service.FgcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Austin
 * @Date: 2018/8/20 14:22
 * @Description:
 */
@EnableWebMvc
public class FzAllUrlInterceptor implements HandlerInterceptor {

    @Autowired
    private FgcUserService fgcUserService;
    @Autowired
    private DingUserService dingUserService;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 请求之前拦截器先把请求路径先拦截了
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String token = request.getHeader("token");
            Map<String, Object> map = new HashMap<>();
            IsFileter isFileter = method.getAnnotation(IsFileter.class);
            //如果没有@isFileter,那么就放行
            if (isFileter == null) {
                return true;
            }
            if(isFileter != null){
                String isFilet = isFileter.isFile();
                //如果方法有@isFileter,而且就是true的话,那么就需要token
                if ("true".equals(isFilet)) {
                    String userId = null;
                    try {
                        userId = stringRedisTemplate.opsForValue().get("dingding_user_" + token);
                    } catch (NullPointerException e) {

                    }
                    if (userId == null || "".equals(userId)) {
                        map.put("code", 11000);
                        map.put("msg", "token过期");
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write(JSON.toJSONString(map));
                        return false;
                    }
                }
            }




            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            int sec = accessLimit.sec();
//            String key = getIp2(request) + request.getRequestURI();
            if(token != null){
                Integer maxLimit = redisTemplate.opsForValue().get(token);
                if (maxLimit == null) {
                    redisTemplate.opsForValue().set(token, 1, sec, TimeUnit.SECONDS);  //set时一定要加过期时间
                } else if (maxLimit < limit) {
                    redisTemplate.opsForValue().set(token, maxLimit + 1, sec, TimeUnit.SECONDS);
                } else {
                    map.put("code", 11001);
                    map.put("msg", "请求频繁");
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(JSON.toJSONString(map));
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取真实ip
     * @param request
     * @return
     */
    public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}

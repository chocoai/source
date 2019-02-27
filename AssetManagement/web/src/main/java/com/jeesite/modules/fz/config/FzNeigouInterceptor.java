package com.jeesite.modules.fz.config;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.util.redis.RedisUtil;
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
 * @author easter
 * @data 2018/12/11 9:32
 */
@EnableWebMvc
public class FzNeigouInterceptor implements HandlerInterceptor {
    @Resource
    private RedisUtil<String, Integer> redisInteger;
    @Resource
    private RedisUtil<String, String> redisString;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String uvan_token = request.getHeader("uvan_token");
            Map<String, Object> map = new HashMap<>();
            Uvantoken uvantoken = method.getAnnotation(Uvantoken.class);
            //如果没有@uvantoken,那么就放行
            if (uvantoken == null) {
                return true;
            }

            if(uvantoken != null){
                String uvanToken = uvantoken.getUvanToken();
                //如果方法有@Uvantoken,而且就是true的话,那么就需要uvan_token
                if ("true".equals(uvanToken)) {
                    String uvan_neigou_token = null;
                    try {
                        uvan_neigou_token = redisString.get("uvan_neigou_token");
                    } catch (NullPointerException e) {

                    }
                    if (uvan_neigou_token == null || "".equals(uvan_neigou_token) || !uvan_neigou_token.equals(uvan_token)) {
                        map.put("code", 11000);
                        map.put("msg", "uvan_token过期");
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
            long sec = accessLimit.sec();
            if(uvantoken != null){
                Integer maxLimit = redisInteger.get(uvan_token);
                if (maxLimit == null) {
                    redisInteger.set(uvan_token, 1, sec, TimeUnit.SECONDS);  //set时一定要加过期时间
                } else if (maxLimit < limit) {
                    redisInteger.set(uvan_token, maxLimit + 1, sec, TimeUnit.SECONDS);
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
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

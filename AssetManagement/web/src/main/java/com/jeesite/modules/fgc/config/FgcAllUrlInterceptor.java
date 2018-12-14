package com.jeesite.modules.fgc.config;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Austin
 * @Date: 2018/8/20 14:22
 * @Description:
 */
@EnableWebMvc
public class FgcAllUrlInterceptor implements HandlerInterceptor {

    @Autowired
    private FgcUserService fgcUserService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        Map<String, Object> map = new HashMap<>();
        String openId = null;
        try{
            openId = redisTemplate.opsForValue().get("uvanfactory_user_" + token);
        }catch (NullPointerException e) {

        }
        if (openId == null || "".equals(openId)) {
            map.put("code", 11000);
            map.put("msg", "token过期");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(map));
            return false;
        }
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
        if(fgcUser == null) {
            map.put("code", -100);
            map.put("msg", "系统异常，请重新登录");
            response.getWriter().write(JSON.toJSONString(map));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

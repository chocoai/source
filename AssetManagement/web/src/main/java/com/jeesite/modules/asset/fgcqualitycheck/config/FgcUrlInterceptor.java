package com.jeesite.modules.asset.fgcqualitycheck.config;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: len
 * @Date: 2018/8/20 14:22
 * @Description:
 */
@EnableWebMvc
public class FgcUrlInterceptor implements HandlerInterceptor {

    @Autowired
    private FgcUserService fgcUserService;
    @Resource
    private RedisUtil<String, String> redisString;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        Map<String, Object> map = new HashMap<>();
        String openId = null;
        try{
            openId = redisString.get("uvanfactory_user_" + token);
        }catch (NullPointerException e) {

        }
//        openId = "obaI65GJhkkCUKeJudphg2-mndeg";
        if (openId == null || "".equals(openId)) {
            map.put("code", 10000);
            map.put("msg", "请检查登录信息");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(JSON.toJSONString(map));
            return false;
        }
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
        if(fgcUser == null) {
            map.put("code", 10001);
            map.put("msg", "请检查用户信息");
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

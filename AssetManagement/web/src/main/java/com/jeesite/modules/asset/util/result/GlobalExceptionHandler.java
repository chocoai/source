package com.jeesite.modules.asset.util.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = ( LoggerFactory.getLogger(GlobalExceptionHandler.class));

    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnInfo defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        logger.error("", e);
        ReturnInfo r = new ReturnInfo();
        r.setMsg(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            r.setCode(404);
        } else if( e instanceof org.apache.shiro.authz.UnauthenticatedException){
            r.setCode(902);
            r.setMsg("账号权限不足或账号过期");
        } else if( e instanceof org.apache.shiro.authz.UnauthorizedException){
            r.setCode(30001);
            r.setMsg("账号权限不足");
        } else {
            r.setCode(500);
        }
        r.setData("");
//        r.setStatus(false);
        return r;
    }


}

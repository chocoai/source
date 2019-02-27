/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.test;

import com.jeesite.modules.config.Application;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * 初始化核心表数据
 *
 * @author ThinkGem
 * @version 2017-10-22
 */
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Rollback(false)
public class InitCoreData extends com.jeesite.modules.sys.db.InitCoreData {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void initCoreData() throws Exception {
        createTable();
        initLog();
        initArea();
        initConfig();
        initModule();
        initDict();
        initRole();
        initMenu();
        initUser();
        initOffice();
        initCompany();
        initPost();
        initEmpUser();
        initMsgPushJob();
        initGenTestData();
        initGenTreeData();
    }

    public  void main(HttpServletResponse response) {
        try {
            String data = "中国优梵艺术";
            //String data = DateUtil.formatDate(new Date());
            //String data = ReturnCode.codeMap.get("15035");
            OutputStream stream = response.getOutputStream();
            response.setContentType("text/html;charset=utf-8");//设置字符流编码，而且还会添加content-Type响应头，这个头通知浏览器用utf-8解码。
            response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
            stream.write(data.getBytes("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-100);
        long time = calendar.getTime().getTime() /1000;
        System.out.println(time);
        System.out.println(new Date().getTime() / 1000);
    }


    private static boolean A(boolean getToken) {
        //Fun : 创建用户
        boolean isSuccess = true;

        if (isSuccess) {
            if (getToken) {
                return B(false);
            } else {
                return isSuccess;
            }
        } else {
            return isSuccess;
        }
    }

    private static boolean B(boolean isCreateUser) {
        //Fun : 获取令牌
        boolean isSuccess = true;
        if (!isSuccess) {
            //NOUser
            if (isCreateUser) {
                return A(true);
            } else {
                return isSuccess;
            }
        } else {
            return true;
        }
    }

    @Test
    private static void C() {
        B(true);
    }

}

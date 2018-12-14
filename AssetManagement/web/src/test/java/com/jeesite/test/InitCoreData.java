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

    public  void main(String[] args) {

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

package com.jeesite.modules.test.config;

import org.slf4j.LoggerFactory;


public class Task {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Task.class);

    public static void testTask() {
        logger.info("测试111=====================================================================");
        logger.debug("测试222=====================================================================");
    }
}

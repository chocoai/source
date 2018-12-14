package com.jeesite.modules.asset.scheduledtask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class K3Config {
    @Value("${POST_K3ClOUDRL}")
    private String POST_K3ClOUDRL;
    //账套id
    @Value("${dbId}")
    private String dbId ;
    @Value("${uid}")
    private String uid;
    @Value("${pwd}")
    private String pwd;
    @Value("${lang}")
    private int lang;
    @Value("${file.baseDir}")
    private String baseDir;

    public static final String SUNDAY = "星期日";

    public static String K3ClOUDRL;
    public static String DBID;
    public static String UID;
    public static String PWD;
    public static Integer LANG;
    public static String BASEDIR;
    @PostConstruct
    public void init () {
        K3ClOUDRL = this.POST_K3ClOUDRL;
        DBID = this.dbId;
        UID = this.uid;
        PWD = this.pwd;
        LANG = this.lang;
        BASEDIR = this.baseDir;
    }
}

package com.jeesite.modules.asset.k3webapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class K3connection {
    @Value("${POST_K3ClOUDRL}")
    private String POST_K3ClOUDRL;  //测试库
    //账套id
    @Value("${dbId}")
    private String dbId ;
    @Value("${uid}")
    private String uid;
    @Value("${pwd}")
    private String pwd;
    @Value("${lang}")
    private int lang;
    public boolean getConnection() {
        boolean rst = false;
        try {
            if (InvokeHelper.Login(dbId, uid, pwd, lang,POST_K3ClOUDRL)) {
                rst = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
}

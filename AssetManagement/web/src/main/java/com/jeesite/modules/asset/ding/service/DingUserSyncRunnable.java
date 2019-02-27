package com.jeesite.modules.asset.ding.service;

public class DingUserSyncRunnable implements Runnable {
    private String message;

    public DingUserSyncRunnable(String message) {
        this.message = message;
    }

    public void run() {
        while(true) {
            System.out.println(message);
        }
    }
}

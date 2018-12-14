package com.jeesite.modules.asset.ding;

import java.io.*;

public class ReadFile {

    public static String ReadToString(String path) {
        File file = new File(path);
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

package com.jeesite.modules.asset.fgcqualitycheck.common;

import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Auther: len
 * @Date: 2018/8/18 18:05
 * @Description:
 */
public class Convert {
    /**
     *
     * @param json
     * @param key
     * @return
     */
    public static String getString (JSONObject json, String key) {
        String value = "";
        try {
            value = json.get(key).toString();
        }catch (NullPointerException e) {

        }
        return value;
    }

    /**
     *
     * @param json
     * @param key
     * @return
     */
    public static Long getLong (JSONObject json, String key) {
        Long value = null;
        try {
            value = json.getLong(key);
        }catch (NullPointerException e) {

        }
        return value;
    }

    /**
     *
     * @param json
     * @param key
     * @return
     */
    public static Double getDouble (JSONObject json, String key) {
        Double value = null;
        try {
            value = json.getDouble(key);
        }catch (NullPointerException e) {

        }
        return value;
    }

    public static String getStr (JSONArray jsonArray, int i) {
        String str = null;
        if (jsonArray.get(i) == null || "".equals(jsonArray.get(i))) {
            str = null;
        } else {
            str = jsonArray.get(i).toString();
        }
        return str;
    }
}

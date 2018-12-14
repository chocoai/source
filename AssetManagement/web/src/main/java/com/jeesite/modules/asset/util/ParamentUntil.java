package com.jeesite.modules.asset.util;

import com.jeesite.modules.asset.amspecimen.entity.AmSpeciQualifications;

import java.util.ArrayList;
import java.util.List;

public class ParamentUntil {
    public static boolean isBackString(String parament){
        boolean rst=false;
        if (parament!=null&&parament.length()>0){
            rst=true;
        }
        return rst;
    }

    public static boolean isBackList(List<?> list) {
        boolean rst=false;
        if (list!=null&&list.size()>0){
            rst=true;
        }
        return rst;
    }

    public static List<String> getListByString(String param) {
        List<String> list=null;
        if (isBackString(param)){
            list=new ArrayList<>();
            String [] a=param.split(",");
            for (int i=0;i<a.length;i++){
                list.add(a[i]);
            }
        }
        return list;
    }
}

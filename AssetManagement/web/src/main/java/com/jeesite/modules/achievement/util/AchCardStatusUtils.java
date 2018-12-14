package com.jeesite.modules.achievement.util;

import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AchCardStatusUtils {


    public String getStatus(String key, boolean addOrMinus){
        if(addStatusMap == null) initAndGetStatusMap();
        return addOrMinus ? addStatusMap.get(key) : minusStatusMap.get(key);
    }

    private static Map<String, String> addStatusMap;
    private static Map<String, String> minusStatusMap;


    private void initAndGetStatusMap(){
        List<DictData> dictlist = DictUtils.getDictList("ach_card_status");
        addStatusMap = new HashMap<>();
        minusStatusMap = new HashMap<>();
        int len = dictlist.size();
        int lastIndex = len - 1;
        for(int i =0; i < len; i++){
            DictData dict = dictlist.get(i);
            if(i == 0){
                DictData nextDict = dictlist.get(i+1);
                addStatusMap.put(dict.getDictValue(), nextDict.getDictValue()); //0:1

                minusStatusMap.put(dict.getDictValue(), dict.getDictValue());   //0:0
            }
            else if(i == lastIndex){
                DictData pervDict = dictlist.get(i-1);
                minusStatusMap.put(dict.getDictValue(), pervDict.getDictValue());   //last:last-1

                addStatusMap.put(dict.getDictValue(), dict.getDictValue());     //last:last
            }
            else {
                DictData pervDict = dictlist.get(i-1);
                minusStatusMap.put(dict.getDictValue(), pervDict.getDictValue());   //num:num-1

                DictData nextDict = dictlist.get(i+1);
                addStatusMap.put(dict.getDictValue(), nextDict.getDictValue());     //num:num+1
            }
        }
        //List<Integer> statusList = dictlist.stream().map(a->Integer.parseInt(a.getDictValue())).collect(Collectors.toList());
    }
}

package com.jeesite.modules.util.dict;

import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import org.beetl.core.Context;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DictService {

    public static List<DictData> getChildrenByDesc(String dictType, String desc){
        List<DictData> dictExamineGroupList = DictUtils.getDictList(dictType);
        Optional<DictData> optionalDictData = dictExamineGroupList.stream().filter(a-> a.getExtend().getExtendS2().equals(desc)).findFirst();
        List<DictData> dictTargetList = null;
        if(optionalDictData.isPresent()){
            DictData targetItem = optionalDictData.get();
            dictTargetList = dictExamineGroupList.stream().filter(a->a.getParentCode().equals(targetItem.getDictCode())).collect(Collectors.toList());
        }
        return dictTargetList;
    }

    public static String getChildrenByDescJson(String dictType, String desc){
        String result = "\""+JsonMapper.toJson(getChildrenByDesc(dictType, desc)).replace("\"","\\\"")+"\"";
        String result1 = JsonMapper.toJson(DictUtils.getDictListJson(dictType));
        return JsonMapper.toJson(getChildrenByDesc(dictType, desc));
    }
}

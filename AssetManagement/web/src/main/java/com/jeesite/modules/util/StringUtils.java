package com.jeesite.modules.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils extends com.jeesite.common.lang.StringUtils {
    public static String connectParams(String... params){
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(params).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}

package com.jeesite.modules.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils extends com.jeesite.common.lang.ObjectUtils {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Array obj) {
        return obj == null || Array.getLength(obj) == 0;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
    public static boolean isNotEmpty(Collection obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Map obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Array obj) {
        return !isEmpty(obj);
    }
}

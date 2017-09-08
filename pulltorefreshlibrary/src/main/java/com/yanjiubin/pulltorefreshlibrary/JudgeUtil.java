package com.yanjiubin.pulltorefreshlibrary;

import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cnsunrun on 2017-09-07.
 */

public class JudgeUtil {
    public JudgeUtil() {
    }

    public static String dealNull(String dest) {
        if(dest == null) {
            dest = "";
            return "";
        } else {
            return dest;
        }
    }

    public static <T> List<T> dealNull(List<T> list) {
        return list == null? (List<T>) Collections.emptyList() :list;
    }

    public static Map<?, ?> dealNull(Map<?, ?> map) {
        return map == null?Collections.emptyMap():map;
    }

    public static Set<?> dealNull(Set<?> set) {
        return set == null?Collections.emptySet():set;
    }

    public static Object[] dealNull(Object[] array) {
        return array == null?new Object[0]:array;
    }

    public static Object dealNull(Object obj) {
        return obj == null?new Object():obj;
    }

    public static boolean isLessThan(List<?> dest, int size) {
        return dest == null || dest.size() < size;
    }

    public static int size(List<?> dest) {
        return dest == null?0:dest.size();
    }

    public static int size(TextView dest) {
        return dest == null?0:dest.getText().length();
    }

    public static boolean isEmpy(List<?> dest) {
        return dest == null || dest.size() == 0;
    }

    public static boolean isEmpy(String dest) {
        return dest == null || dest.length() == 0 || "".equals(dest);
    }

    public static boolean isEmpy(TextView dest) {
        return dest == null || dest.getText().toString().trim().equals("");
    }

    public static boolean isEmpy(Map<?, ?> dest) {
        return dest == null || 0 == dest.size();
    }

    public static boolean isEmpy(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpy(Object dest) {
        return dest instanceof List?isEmpy((List)dest):(dest instanceof Map?isEmpy((Map)dest):(dest instanceof String?isEmpy((String)dest):(dest instanceof TextView?isEmpy((TextView)dest):dest == null)));
    }

    public static boolean empty(Object dest) {
        return isEmpy(dest);
    }
}

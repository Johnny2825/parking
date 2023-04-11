package ru.example.micro.parking.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Tarkhov Evgeniy
 */
public class TypeCastUtil {
    private TypeCastUtil(){}

    public static Integer castIntegerSafety(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

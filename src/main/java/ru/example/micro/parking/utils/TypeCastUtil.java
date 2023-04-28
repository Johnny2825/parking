package ru.example.micro.parking.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public class TypeCastUtil {
    private TypeCastUtil(){}

    public static Optional<Integer> castIntegerSafety(String str) {
        if (NumberUtils.isDigits(str)) {
            return Optional.empty();
        }
        return Optional.of(Integer.valueOf(str));
    }
}

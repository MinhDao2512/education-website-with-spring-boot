package com.toilamdev.stepbystep.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FormatUtils {
    public static String formatName(String name) {
        return Arrays.stream(name.strip().split("\\s+"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}

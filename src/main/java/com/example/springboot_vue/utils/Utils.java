package com.example.springboot_vue.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public static String getTime() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now().withNano(0);
        return today + " " + now;
    }

    public static String getType(String filename) {
        return Objects.requireNonNull(filename).substring(filename.lastIndexOf("."));
    }

    public static Map<String, String> parseKeyValue(ArrayList<String> list) {
        Map<String, String> returnMap = new HashMap<>();
        String[] arr = {"!=", ">=", "<=", "=", ">", "<"};
        list.forEach(value -> {
            for (int i = 0; i < arr.length; i++) {
                if (value.contains(arr[i])) {
                    String[] part = value.split(arr[i]);
                    System.out.println("part[0] = " + part[0] + "part[1] = " + part[1]);
                    returnMap.put(part[0], part[1]);
                    break;
                }
            }
        });

        return returnMap;
    }
}

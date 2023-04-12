package com.example.springboot_vue.lanqiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StupidFinger {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String first = scanner.next();
        String second = scanner.next();

        StringBuilder str1 = new StringBuilder(first);
        StringBuilder str2 = new StringBuilder(second);

        List<StringBuilder> list1 = getList(str1);
        List<StringBuilder> list2 = new ArrayList<>();

        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == '2') {
                list2.add(new StringBuilder(str2.replace(i, i + 1, "0")));
                list2.add(new StringBuilder(str2.replace(i, i + 1, "1")));
                str2.replace(i, i + 1, "2");
            } else if (str2.charAt(i) == '0') {
                list2.add(new StringBuilder(str2.replace(i, i + 1, "2")));
                list2.add(new StringBuilder(str2.replace(i, i + 1, "1")));
                str2.replace(i, i + 1, "0");
            } else {
                list2.add(new StringBuilder(str2.replace(i, i + 1, "2")));
                list2.add(new StringBuilder(str2.replace(i, i + 1, "0")));
                str2.replace(i, i + 1, "1");
            }
        }

        int res = -1000000;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (Integer.parseInt(list1.get(i).toString(), 2) == Integer.parseInt(list2.get(j).toString(), 3)) {
                    res = Math.max(res, Integer.parseInt(list1.get(i).toString(), 2));
                }
            }
        }
        System.out.println(res);
    }

    public static List<StringBuilder> getList(StringBuilder str1) {
        List<StringBuilder> list1 = new ArrayList<>();

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == '0') {
                list1.add(new StringBuilder(str1.replace(i, i + 1, "1")));
                str1.replace(i, i + 1, "0");
            } else {
                list1.add(new StringBuilder(str1.replace(i, i + 1, "0")));
                str1.replace(i, i + 1, "1");
            }
        }

        return list1;
    }
}

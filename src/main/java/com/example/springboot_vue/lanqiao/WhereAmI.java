package com.example.springboot_vue.lanqiao;

import java.util.*;

public class WhereAmI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        String str = scanner.nextLine();

        int l = 1, r = n;
        while (l < r) {
            int mid = l + r >> 1;
            // 如果中位数满足，那么它左边的也有可能满足，所以区间往左划分
            if (check(mid, str)) {
                r = mid;
            } else {
                // 否则说明中位数不满足，说明现在的划分有点短，需要向右划分
                l = mid + 1;
            }
        }

        System.out.println(r);
    }

    public static boolean check(int mid, String str) {
        Set<String> set = new HashSet<>();

        // 每一次check，都要遍历一边
        // 而每一次遍历，set中记录了一个从i到mid长度的字串
        for (int i = 0; i + mid - 1 < str.length(); i++) {
            // 截取str的值
            String s = str.substring(i, mid);
            // 如果set中已经存在该字符，说明已经重复了，就要返回false
            if (set.contains(s)) {
                return false;
            }
            set.add(s);
        }

        return true;
    }

}

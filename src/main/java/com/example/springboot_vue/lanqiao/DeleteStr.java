package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class DeleteStr {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        String str = scanner.next();

        int count = 0, res = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'x') {
                count++;
            } else {
                if (count >= 3) {
                    res += count - 2;
                }
                count = 0;
            }
        }

        if (count >= 3) {
            res += count - 2;
        }

        System.out.println(res);
    }
}

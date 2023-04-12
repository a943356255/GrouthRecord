package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class Third {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long a = in.nextLong();
        long b = in.nextLong();
        long ans = phi(a);
        ans = (ans * powMod(a, b - 1, 998244353)) % 998244353;
        System.out.println(ans);
    }

    public static long phi(long n) {
        long res = n;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                res = res / i * (i - 1);
                while (n % i == 0) {
                    n /= i;
                }
            }
        }
        if (n > 1) {
            res = res / n * (n - 1);
        }
        return res;
    }

    public static long powMod(long x, long n, long mod) {
        long res = 1;
        x %= mod;
        while (n > 0) {
            if ((n & 1) == 1) {
                res = res * x % mod;
            }
            x = x * x % mod;
            n >>= 1;
        }
        return res;
    }
}

package com.example.springboot_vue.leetcode;

import java.security.NoSuchAlgorithmException;

public class Sha256 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(Sha256.getSha256("a"));
    }

    // 用long模拟32位无符号数据
    // 前32位0，后32位1的数字，控制数字在32位,4294967295
    final static long OVER = 0xFFFFFFFFL;
    // 前8个质数的平方根的小数
//	long[] h = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
    static long h0 = 0x6a09e667L;
    static long h1 = 0xbb67ae85L;
    static long h2 = 0x3c6ef372L;
    static long h3 = 0xa54ff53aL;

    static long h4 = 0x510e527fL;
    static long h5 = 0x9b05688cL;
    static long h6 = 0x1f83d9abL;
    static long h7 = 0x5be0cd19L;

    // 前64个质数的立方根的小数
    static long[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    public static String getSha256(String in) {
        StringBuilder stringBuilder = toBinary(in);
        String string = addZeroTo512(stringBuilder);
        return cycleCalculation(string);
    }

    // 1. 将输入的字符串转换为二进制in
    // string的长度限制在2^32
    private static StringBuilder toBinary(String in) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] inArray = in.toCharArray();
        for (int i = 0; i < inArray.length; i++) {
            // 还有前导的0
            String binary = Integer.toBinaryString(inArray[i]);
            int count0 = 8 - binary.length();
            for (int j = 0; j < count0; j++) {
                stringBuilder.append("0");
            }
            stringBuilder.append(binary);
        }
        return stringBuilder;
    }

    // 2. 将输入的二进制的长度补齐到512的倍数，原长度l + 1（默认1位1） + k（补齐的0） + 64（64为二进制表示的输入字符串的长度）
    private static String addZeroTo512(StringBuilder binaryIn) {

        int l = binaryIn.length();
        int k = 959 - (l % 512);
        if (k > 512) {
            k = k - 512;
        }
        // 默认先添加1
        binaryIn.append("1");
        // 添加k个0
        for (int i = 0; i < k; i++) {
            binaryIn.append("0");
        }
        // 添加64位的源数据长度
        String lengthBinary = Integer.toBinaryString(l);
        int k2 = 64 - lengthBinary.length();
        for (int i = 0; i < k2; i++) {
            binaryIn.append("0");
        }
        binaryIn.append(lengthBinary);

        return binaryIn.toString();
    }

    // 核心：循环计算
    /**
     *
     * @param binaryIn  通过补位后的源数据
     * @return
     */
    private static String cycleCalculation(String binaryIn) {
        // 1. 按照512的长度分成n个消息块
        int n = binaryIn.length() / 512;
        for (int i = 0; i < n; i++) {
            // 对于每一个块
            // 2. 每个消息块分成16个32位的“字”
            String[] wString = new String[16];
            for (int j = 0; j < 16; j++) {
                wString[j] = binaryIn.substring(32 * j, 32 + 32 * j);
            }
            // 3. 将16个字扩充为64个字，转换方法
            long[] w = new long[64];
            for (int j = 0; j < wString.length; j++) {
                // 将二进制的string转为10进制的long
                w[j] = Long.parseLong(wString[j], 2);
            }
            for (int j = 16; j < 64; j++) {
                long s0 = ((rightRotate(w[j - 15], 7)) ^ rightRotate(w[j - 15], 18) ^ (rightShift(w[j - 15], 3))) & OVER;
                long s1 = ((rightRotate(w[j - 2], 17)) ^ (rightRotate(w[j - 2], 19)) ^ (rightShift(w[j - 2], 10))) & OVER;
                w[j] = (w[j - 16] + s0 + w[j - 7] + s1) & OVER;
            }
            // 4. hash初始化
            long a = h0;
            long b = h1;
            long c = h2;
            long d = h3;

            long e = h4;
            long f = h5;
            long g = h6;
            long h = h7;
            // 5. 64次循环
            for (int j = 0; j < 64; j++) {
                long s0 = (rightRotate(a, 2) ^ (rightRotate(a, 13)) ^ (rightRotate(a, 22))) & OVER;
                long maj = ((a & b) ^ (a & c) ^ (b & c)) & OVER;
                long t2 = (s0 + maj) & OVER;
                long s1 = ((rightRotate(e, 6)) ^ (rightRotate(e, 11)) ^ (rightRotate(e, 25))) & OVER;
                long ch = ((e & f) ^ ((~e) & g)) & OVER;
                long t1 = (h + s1 + ch + k[j] + w[j]) & OVER;
                h = g;
                g = f;
                f = e;
                e = (d + t1) & OVER;
                d = c;
                c = b;
                b = a;
                a = (t1 + t2) & OVER;
            }

            h0 = (h0 + a) & OVER;
            h1 = (h1 + b) & OVER;
            h2 = (h2 + c) & OVER;
            h3 = (h3 + d) & OVER;
            h4 = (h4 + e) & OVER;
            h5 = (h5 + f) & OVER;
            h6 = (h6 + g) & OVER;
            h7 = (h7 + h) & OVER;
        }
        // 把h0到h7拼起来就是所需要的值了。
        return getHash(h0) + getHash(h1) + getHash(h2) + getHash(h3) + getHash(h4) + getHash(h5) + getHash(h6) + getHash(h7);
    }

    // 把long型的数字转换为字符串，只取后32位的数字，不足则前补0，前面多余的则舍去
    private static String getHash(long h) {
        String hash = Long.toHexString(h);
        StringBuilder result = new StringBuilder();
        if (hash.length() > 8) {
            result.append(hash.substring(hash.length() - 8, hash.length()));
        } else {
            int count0 = 8 - hash.length();
            for (int i = 0; i < count0; i++) {
                result.append("0");
            }
            result.append(hash);
        }
        return result.toString();
    }

    /**
     * 右旋n位，循环右移n位，对于32位无符号数字而言，末尾的数字被移到开头
     * 用long模拟32位无符号数字，末尾被移出的数字替换到开头，相当于左移了
     * @param x
     * @param n
     * @return
     */
    private static long rightRotate(long x, int n) {
        long wei = (0 << n) - 1; // 这个操作有点疑问
        x = ((wei & (x & OVER)) << (32 - n)) | (x & OVER) >> n;
        return x;
    }
    /**
     * 按位右移n位，末尾的数字舍去，前面补0，相当于>>>，无符号右移
     * @param x
     * @param n
     * @return
     */
    private static long rightShift(long x, int n) {
//        return (x&0xFFFFFFFFL)>>n;
        return (x & OVER) >>> n;
    }
}
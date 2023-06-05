package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain7 {

    public static void main(String[] args) {

    }

    // 2460. 对数组执行操作
    public int[] applyOperations(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }

        int index = 0;
        int[] res = new int[nums.length];
        for (int j : nums) {
            if (j != 0) {
                res[index++] = j;
            }
        }

        for (int i = index; i < res.length; i++) {
            res[i] = 0;
        }

        return res;
    }

    // 2465. 不同的平均值数目
    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;
        Set<Double> map = new HashSet<>();
        while (left < right) {
            double val = (nums[left++] + nums[right--]) / 2.0;
            map.add(val);
        }

        return map.size();
    }

    // 1156. 单字符重复子串的最大长度
    public int maxRepOpt1(String text) {
//        int res = 1;
//
//        Map<Character, List<Integer>> map = new HashMap<>();
//        int[] arr = new int[text.length()];
//        arr[0] = 1;
//        for (int i = 1; i < text.length(); i++) {
//            char a = text.charAt(i);
//            char b = text.charAt(i - 1);
//            if (a == b) {
//                arr[i] = arr[i - 1] + 1;
//            } else {
//                if (map.get(b) == null) {
//                    List<Integer> list = new ArrayList<>();
//                    list.add(i - 1);
//                    map.put(b, list);
//                } else {
//                    map.get(b).add(i - 1);
//                }
//                arr[i] = 1;
//            }
//        }
//
//        System.out.println(Arrays.toString(arr));
//        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue().toString());
//        }
//
//        return res;
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (int i = 0; i < text.length(); ) {
            // step1: 找出当前连续的一段 [i, j)
            int j = i;
            while (j < text.length() && text.charAt(j) == text.charAt(i)) {
                j++;
            }
            int curCnt = j - i;

            // step2: 如果这一段长度小于该字符出现的总数，并且前面或后面有空位，则使用 curCnt + 1 更新答案
            // (j < text.length() || i > 0)判断不是第一个也不是最后一个，说明一定可以换一个位置
            if (curCnt < count.getOrDefault(text.charAt(i), 0) && (j < text.length() || i > 0)) {
                res = Math.max(res, curCnt + 1);
            }

            // step3: 找到这一段后面与之相隔一个不同字符的另一段 [j + 1, k)，如果不存在则 k = j + 1
            // 因为第一次结束循环时，当前的j指向的字符与i指向的字符一定不同，所以k = j + 1，然后往后遍历
            // 这里其实就是找j + 1 ~ k这个区间，是否与当前i相同，如果相同，则可以取区间i ~ k，因为之前的j不同，但是只有1个字符，可以通过替换变为相同
            int k = j + 1;
            while (k < text.length() && text.charAt(k) == text.charAt(i)) {
                k++;
            }
            res = Math.max(res, Math.min(k - i, count.getOrDefault(text.charAt(i), 0)));
            i = j;
        }

        return res;
    }
}

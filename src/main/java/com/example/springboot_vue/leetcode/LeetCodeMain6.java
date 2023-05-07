package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain6 {

    public static void main(String[] args) {

    }

    // 1010. 总持续时间可被 60 整除的歌曲 (超时)
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            int temp = time[i] % 60;
            if (map.get(temp) == null) {
                List<Integer> queue = new ArrayList<>();
                map.put(temp, queue);
            }
            map.get(temp).add(i);
        }

        int res = 0;
        for (int i = 0; i < time.length; i++) {
            int val = 60 - time[i] % 60;
            if (val == 60) {
                val = 0;
            }

            List<Integer> list = map.get(val);
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j) > i) {
                        res += list.size() - j;
                        break;
                    }
                }
            }
        }

        return res;
    }

    // 1419. 数青蛙
    public int minNumberOfFrogs(String croakOfFrogs) {
        if (croakOfFrogs.length() % 5 != 0) {
            return -1;
        }
        int res = 0, frogNum = 0;
        int[] cnt = new int[4];
        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('c', 0);
            put('r', 1);
            put('o', 2);
            put('a', 3);
            put('k', 4);
        }};

        char c;
        for (int i = 0; i < croakOfFrogs.length(); i++) {
            c = croakOfFrogs.charAt(i);
            int temp = map.get(c);
            if (temp == 0) {
                frogNum++;
                cnt[temp]++;
                if (frogNum > res) {
                    res = frogNum;
                }
            } else {
                // 出现了该单词，但是没有前一个，返回-1
                if (cnt[temp - 1] == 0) {
                    return -1;
                }
                cnt[temp - 1]--;
                // 每出现一个k，说明一个青蛙叫完了，下一个c就是该青蛙叫的第二声，所以num--
                if (temp == 4) {
                    frogNum--;
                } else {
                    cnt[temp]++;
                }
            }
        }

        // 这里是还有青蛙没有叫完
        if (frogNum > 0) {
            return -1;
        }

        return res;
    }

    // 2432. 处理用时最长的那个任务的员工
    public int hardestWorker(int n, int[][] logs) {
        int id = logs[0][0], max = logs[0][1];
        int temp;
        for (int i = 1; i < logs.length; i++) {
            temp = logs[i][1] - logs[i - 1][1];
            if (temp > max) {
                max = temp;
                id = logs[i][0];
            } else if (temp == max) {
                if (logs[i][0] < id) {
                    id = logs[i][0];
                }
            }
        }

        return id;
    }

    // 2106. 摘水果
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int[] arr = new int[fruits[fruits.length - 1][0] + 1];
        int index;
        if (fruits[0][0] == 0) {
            arr[0] = fruits[0][1];
            index = 1;
        } else {
            arr[0] = 0;
            index = 0;
        }
        // 求前缀和
        for (int i = 1; i < arr.length; i++) {
            if (i == fruits[index][0]) {
                arr[i] = arr[i - 1] + fruits[index][1];
                index++;
            } else {
                arr[i] = arr[i - 1];
            }
        }
        int maxLeft = Math.max(0, startPos - k);
        int maxRight = Math.min(arr.length - 1, startPos + k);

        int legalStartPos;
        if (startPos > arr.length) {
            legalStartPos = Math.max(startPos - k, arr.length - 1);
            k = k - (startPos - legalStartPos);
            if (legalStartPos > arr.length) {
                return 0;
            }
        } else {
            legalStartPos = startPos;
        }

        int max, val = arr[maxRight] - arr[Math.max(Math.min(startPos - 1, maxRight), 0)];
        if (maxLeft == 0) {
            max = Math.max(arr[legalStartPos], val);
        } else {
            max = Math.max(arr[legalStartPos] - arr[maxLeft - 1], val);
        }
        // 优先往左走，然后右拐
        for (int i = maxLeft + 1; i < legalStartPos; i++) {
            max = Math.max(max, arr[Math.max(legalStartPos, maxRight - 2 * (i - maxLeft))] - arr[i - 1]);
        }

        // 优先往右走，然后左拐
        for (int i = legalStartPos + 1; i <= maxRight; i++) {
            max = Math.max(max, arr[i] - arr[Math.max(Math.min(startPos - k + (i - legalStartPos) * 2, legalStartPos) - 1, 0)]);
        }

        return max;
    }

    // 1003. 检查替换后的词是否有效
    public boolean isValid(String s) {
        Stack<String> stack = new Stack<>();
        stack.add(s);

        while (!stack.isEmpty()) {
            StringBuilder str = new StringBuilder(stack.pop());
            int mark = 0;
            for (int i = 0; i < str.length() - 2; i++) {
                if (str.charAt(i) == 'a' && str.charAt(i + 1) == 'b' && str.charAt(i + 2) == 'c') {
                    stack.push(str.substring(0, i) + str.substring(i + 3, str.length()));
                    mark = 1;
                    break;
                }
            }
            if (mark == 0) {
                return false;
            }

            if (stack.peek().equals("")) {
                return true;
            }
        }

        return true;
    }
}

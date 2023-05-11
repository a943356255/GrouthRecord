package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain6 {

    public static void main(String[] args) {

    }

    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }

        return new ArrayList<>(map.values());
    }

    // 1016. 子串能表示从 1 到 N 数字的二进制串
    public boolean queryString(String s, int n) {
        for (int i = 1; i <= n; i++) {
            if (!s.contains(Integer.toBinaryString(i))) {
                return false;
            }
        }
        return true;
    }

    // 30. 串联所有单词的子串
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int m = words.length, n = words[0].length();
        for (int i = 0; i < n; i++) {
            // i + m * n是截取单词的最右下标，如果超过字符串长度，说明剩下的单词长度不够了
            if (i + m * n > s.length()) {
                break;
            }

            Map<String, Integer> map = new HashMap<>();
            // 从i开始，每n个字符截取成一个单词
            for (int j = 0; j < m; j++) {
                // 这里可以直接截取是由循环第一个if判断保证的
                String word = s.substring(i + j * n, i + (j + 1) * n);
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            // 遍历words，与截取的单词进行比较
            for (String str : words) {
                map.put(str, map.getOrDefault(str, 0) - 1);
                if (map.get(str) == 0) {
                    map.remove(str);
                }
            }

            // 对比较的结果进行判断, 上边那两步是为了初始化窗口的大小，然后才开始往后滑动
            for (int start = i; start < s.length() - m * n + 1; start += n) {
                if (start != i) {
                    // 从start开始的一组单词在上面已经处理过了，放在了窗口中，这里是截取窗口后一个单词的长度
                    String word = s.substring(start + (m - 1) * n, start + m * n);

                    // 将窗口后一个单词放入窗口中，考虑结果
                    map.put(word, map.getOrDefault(word, 0) + 1);
                    if (map.get(word) == 0) {
                        map.remove(word);
                    }

                    // 将窗口中的第一个单词划出去
                    word = s.substring(start - n, start);
                    map.put(word, map.getOrDefault(word, 0) - 1);
                    if (map.get(word) == 0) {
                        map.remove(word);
                    }
                }

                if (map.isEmpty()) {
                    res.add(start);
                }
            }
        }

        return res;
    }

    // 2437. 有效时间的数目
    public int countTime(String time) {
        int countHour = 0;
        int countMinute = 0;
        for (int i = 0; i < 24; i++) {
            int hiHour = i / 10;
            int loHour = i % 10;
            if ((time.charAt(0) == '?' || time.charAt(0) == hiHour + '0') &&
                    (time.charAt(1) == '?' || time.charAt(1) == loHour + '0')) {
                countHour++;
            }
        }
        for (int i = 0; i < 60; i++) {
            int hiMinute = i / 10;
            int loMinute = i % 10;
            if ((time.charAt(3) == '?' || time.charAt(3) == hiMinute + '0') &&
                    (time.charAt(4) == '?' || time.charAt(4) == loMinute + '0')) {
                countMinute++;
            }
        }
        return countHour * countMinute;
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

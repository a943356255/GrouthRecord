package com.example.springboot_vue.leetcode;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class LeetCodeMain15 {

    public static void main(String[] args) {
//        LeetCodeMain15 leetCodeMain15 = new LeetCodeMain15();
//        leetCodeMain15.maximumSwap(4567);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 10; i++) {
            jsonArray.add(new JSONObject().set("de", String.valueOf(9 - i)));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(jsonArray.get(i).toString());
        }

        jsonArray.sort((o, t1) -> {
            JSONObject first = (JSONObject) o;
            JSONObject second = (JSONObject) t1;
            return first.getStr("de").compareTo(second.getStr("de"));
        });

        System.out.println("after sorted");
        for (int i = 0; i < 10; i++) {
            System.out.println(jsonArray.get(i).toString());
        }
    }

    // 365. 水壶问题
    public boolean canMeasureWater(int x, int y, int z) {
        Deque<int[]> stack = new LinkedList<>();
        stack.push(new int[]{0, 0});
        Set<Long> seen = new HashSet<>();
        while (!stack.isEmpty()) {
            if (seen.contains(hash(stack.peek()))) {
                stack.pop();
                continue;
            }
            seen.add(hash(stack.peek()));

            int[] state = stack.pop();
            int remain_x = state[0], remain_y = state[1];
            if (remain_x == z || remain_y == z || remain_x + remain_y == z) {
                return true;
            }
            // 把 X 壶灌满。
            stack.push(new int[]{x, remain_y});
            // 把 Y 壶灌满。
            stack.push(new int[]{remain_x, y});
            // 把 X 壶倒空。
            stack.push(new int[]{0, remain_y});
            // 把 Y 壶倒空。
            stack.push(new int[]{remain_x, 0});
            // 把 X 壶的水灌进 Y 壶，直至灌满或倒空。
            stack.push(new int[]{remain_x - Math.min(remain_x, y - remain_y), remain_y + Math.min(remain_x, y - remain_y)});
            // 把 Y 壶的水灌进 X 壶，直至灌满或倒空。
            stack.push(new int[]{remain_x + Math.min(remain_y, x - remain_x), remain_y - Math.min(remain_y, x - remain_x)});
        }
        return false;
    }

    public long hash(int[] state) {
        return (long) state[0] * 1000001 + state[1];
    }

    // 2861. 最大合金数
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int left = 1, right = 200000000, ans = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            boolean valid = false;
            for (int i = 0; i < k; ++i) {
                long spend = 0;
                for (int j = 0; j < n; ++j) {
                    spend += Math.max((long) composition.get(i).get(j) * mid - stock.get(j), 0) * cost.get(j);
                }
                if (spend <= budget) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    // 2831. 找出最长等值子数组
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size(), ans = 0;
        List<Integer>[] pos = new ArrayList[n + 1];
        Arrays.setAll(pos, e -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            pos[x].add(i - pos[x].size());
        }
        for (var ps : pos) {
            if (ps.size() <= ans) continue;
            int left = 0;
            for (int right = 0; right < ps.size(); right++) {
                while (ps.get(right) - ps.get(left) > k) // 要删除的数太多了
                    left++;
                ans = Math.max(ans, right - left + 1);
            }
        }
        return ans;

//        int[][] sorted = new int[nums.size()][2];
//        for (int i = 0; i < nums.size(); i++) {
//            sorted[i][0] = nums.get(i);
//            sorted[i][1] = i;
//        }
//        // 先按元素由小到大排序，如果元素相等，按照下标由小到大
//        Arrays.sort(sorted, (ints, t1) -> {
//            if (ints[0] != t1[0]) {
//                return ints[0] - t1[0];
//            } else {
//                return ints[1] - t1[1];
//            }
//        });
//
//        int max = 1;
//        int countContinue = 1, countDel = 0;
//        for (int i = 1; i < sorted.length; i++) {
//            if (sorted[i][0] == sorted[i - 1][0]) {
//                if (sorted[i][1] == sorted[i - 1][1] + 1) {
//                    countContinue++;
//                } else {
//                    countDel += (sorted[i][1] - sorted[i - 1][1] - 1);
//                    if (countDel > k) {
//                        if ((sorted[i][1] - sorted[i - 1][1] - 1) > k) {
//                            countDel = 0;
//                            max = Math.max(max, countContinue);
//                            countContinue = 1;
//                        } else {
//                            max = Math.max(max, countContinue);
//                            countDel = sorted[i][1] - sorted[i - 1][1] - 1;
//                            countContinue = 2;
//                        }
//                    } else {
//                        countContinue ++;
//                    }
//                }
//            } else {
//                max = Math.max(max, countContinue);
//                countContinue = 1;
//                countDel = 0;
//            }
//        }
//
//        max = Math.max(countContinue, max);
//        return max;
    }

    // 2859. 计算 K 置位下标对应元素的和
    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int res = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (Integer.bitCount(i) == k) {
                res += nums.get(i);
            }
        }

        return res;
    }

    public int getOne(int num) {
        int count = 0;
        while (num > 0) {
            if ((num & 1) == 1) {
                count++;
            }

            num >>= 1;
        }

        return count;
    }

    // 2865. 美丽塔 I
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int n = maxHeights.size();
        long res = 0;
        for (int i = 0; i < n; i++) {
            int pre = maxHeights.get(i);
            long sum = pre;
            for (int j = i - 1; j >= 0; j--) {
                pre = Math.min(pre, maxHeights.get(j));
                sum += pre;
            }
            int suf = maxHeights.get(i);
            for (int j = i + 1; j < n; j++) {
                suf = Math.min(suf, maxHeights.get(j));
                sum += suf;
            }
            res = Math.max(res, sum);
        }
        return res;
    }

    // 2765. 最长交替子数组
    public int alternatingSubarray(int[] nums) {
        int res = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                System.out.println("nums[i] = " + nums[i] + " nums[i - 1] = " + nums[i - 1]);
                int temp = 2, mark = nums[i], tempIndex = i;
                for (int j = i + 2; j < nums.length; j += 2) {
                    if (nums[j] == mark && nums[j] - nums[j - 1] == 1) {
                        temp += 2;
                    } else {
                        break;
                    }
                    tempIndex = j;
                }
                System.out.println("tempIndex = " + tempIndex + " temp = " + temp);
                if (tempIndex + 1 < nums.length) {
                    if (nums[tempIndex + 1] == mark - 1) {
                        temp++;
                    }
                }
                System.out.println("temp = " + temp);
                res = Math.max(res, temp);
                i = tempIndex;
            }
        }

        return res;
    }

    // 670. 最大交换
    public int maximumSwap(int num) {
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(0, num % 10);
            num /= 10;
        }

        List<Integer> temp = new ArrayList<>(list);
        temp.sort((a, b) -> b - a);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(temp.get(i))) {
                int big = temp.get(i);
                int min = list.get(i);
                for (int j = list.size() - 1; j >= i + 1; j--) {
                    if (list.get(j) == big) {
                        list.set(j, min);
                        list.set(i, big);
                        break;
                    }
                }
                break;
            }
        }

        int res = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            res = res * 10 + list.get(i);
        }
        return res;
    }

    // 2788. 按分隔符拆分字符串
    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> res = new ArrayList<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            int length = word.length();
            for (int i = 0; i < length; i++) {
                char c = word.charAt(i);
                if (c == separator) {
                    if (sb.length() > 0) {
                        res.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                res.add(sb.toString());
            }
        }
        return res;
    }

    // 2171. 拿出最少数目的魔法豆
    public long minimumRemoval(int[] beans) {
//        Arrays.sort(beans);
//        System.out.println(Arrays.toString(beans));
//        long temp = 0;
//        // 按照第一个位置拿
//        for (int i = 1; i < beans.length; i++) {
//            temp += beans[i] - beans[0];
//        }
//        System.out.println("temp = " + temp);
//        long min = temp;
//        int add = beans[0];
//        // 按照第i个位置拿
//        for (int i = 1; i < beans.length; i++) {
//            if (beans[i] == beans[i - 1]) {
//                add += beans[i];
//                continue;
//            }
//            // 以i为基准，后续每一个都会少拿掉(beans[i] - beans[index])个豆子，后续一共有(beans.length - i)
//            // 所以会少拿differ个豆子，但是会多拿掉前i个豆子，不包括i
//            int differ = (beans[i] - beans[i - 1]) * (beans.length - i);
//            temp = temp - differ + beans[i - 1];
//            System.out.println("differ = " + differ + " temp = " + temp + " add = " + add);
//            if (temp < min) {
//                min = temp;
//            }
//            add += beans[i];
//        }
//
//        return min;
        int n = beans.length;
        Arrays.sort(beans);
        long total = 0; // 豆子总数
        for (int bean : beans) {
            total += bean;
        }

        long res = total; // 最少需要移除的豆子数
        for (int i = 0; i < n; i++) {
            res = Math.min(res, total - (long) beans[i] * (n - i));
        }

        return res;
    }

    // 2744. 最大字符串配对数目
    public int maximumNumberOfStringPairs(String[] words) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].charAt(0) != words[i].charAt(words[i].length() - 1))
                map.put(words[i], "1");
        }

        int res = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            StringBuilder temp = new StringBuilder(entry.getKey());
            if (map.get(temp.reverse().toString()) != null) {
                res++;
            }
        }

        return res / 2;
    }

}

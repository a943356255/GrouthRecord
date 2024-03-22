package com.example.springboot_vue.leetcode;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.socket.utils.StringUtils;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

public class LeetCodeMain19 {

    public static void main(String[] args) throws IllegalAccessException {
        int[] arr = {3, 6, 3, 7, 8, 2, 1, 9, 10};
        new LeetCodeMain19().quickSort(0, arr.length - 1, arr);
        System.out.println(Arrays.toString(arr));
        City city = new City();
        city.setMarkId(111);
        JSONObject jsonObject = new JSONObject();
        Class<?> clazz = city.getClass();
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(Arrays.toString(fields));
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            jsonObject.put(fields[i].getName(), fields[i].get(city));
        }
        System.out.println(jsonObject.get("markId"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("123");
        String str = "123";
        String[] strings = str.split("/");
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(strings[i]).append("-");
        }
        String str2 = "467";
        String result = String.join("-", strings);
    }

    // 228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        int first = nums[0];
        for (int i = 1; i < nums.length; i++) {
            while (i < nums.length && nums[i] - nums[i - 1] == 1) {
                i++;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(first);
            if (nums[i - 1] != first) {
                stringBuilder.append("->").append(nums[i - 1]);
            }
            res.add(stringBuilder.toString());
            if (i < nums.length) {
                first = nums[i];
            }
        }

        if (first == nums[nums.length - 1]) {
            res.add(String.valueOf(first));
        }
        return res;
    }

    // 315. 计算右侧小于当前元素的个数
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> second = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (stack.isEmpty() || stack.peek() > nums[i]) {
                res.add(0, 0);
            } else {
                int sum = 0;
                while (!stack.isEmpty() && stack.peek() < nums[i]) {
                    second.push(stack.pop());
                    sum++;
                }
                res.add(0, sum);
            }

            stack.push(nums[i]);
        }

        return res;
    }

    public void quickSort(int left, int right, int[] arr) {
        if (left < right) {
            int i = left, j = right, x = arr[left];
            while (i < j) {
                while (i < j && arr[j] > x) {
                    j--;
                }

                if (i < j) {
                    arr[i++] = arr[j];
                }

                while (i < j && arr[i] < x) {
                    i++;
                }

                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            quickSort(left, i - 1, arr);
            quickSort(i + 1, right, arr);
        }
    }

    // LCR 049. 求根节点到叶节点数字之和
    List<List<Integer>> list = new ArrayList<>();
    public int sumNumbers(TreeNode root) {
        int res = 0;
        List<Integer> temp = new ArrayList<>();
        sumNumbersDfs(root, temp);

        for (int i = 0; i < list.size(); i++) {
            int tempSum = 0;
            for (int j = 0; j < list.get(i).size(); j++) {
                tempSum = tempSum * 10 + list.get(i).get(j);
            }
            res += tempSum;
        }

        return res;
    }

    public void sumNumbersDfs(TreeNode root, List<Integer> temp) {
        if (root == null) {
            return;
        }

        temp.add(root.val);
        if (root.left == null && root.right == null) {
            list.add(new ArrayList<>(temp));
        }
        sumNumbersDfs(root.left, temp);
        sumNumbersDfs(root.right, temp);
        temp.remove(temp.size() - 1);
    }

    // 42. 接雨水
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int sum = 0;
        for (int i = 1; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int top = stack.pop();
                // 栈为空，说明这里左边没有东西，往右递增，不可能积攒雨水
                if (stack.isEmpty()) {
                    break;
                }

                // 当前栈顶元素,即当此计算的左边界
                int left = stack.peek();
                int high = Math.min(height[left], height[i]) - height[top];
                sum += (i - left - 1) * high;
            }

            stack.push(i);
        }

        return sum;
    }

    // 14. 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < strs.length; i++) {
            min = Math.min(min, strs[i].length());
        }

        int right = 0;
        while (right < min) {
            char c = strs[0].charAt(right);
            int same = 1;
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].charAt(right) != c) {
                    same = 0;
                    break;
                }
            }
            if (same == 1) {
                res.append(c);
            } else {
                break;
            }
            right++;
        }

        return res.toString();
    }

    // 433. 最小基因变化
    public int minMutation(String startGene, String endGene, String[] bank) {
        int res = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < startGene.length(); i++) {
            if (startGene.charAt(i) != endGene.charAt(i)) {
                list.add(i);
            }
        }

        return res;
    }

    // 30. 串联所有单词的子串
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }

        int length = words[0].length();
        StringBuilder first = new StringBuilder();
        List<String> list = new ArrayList<>();
        int left = 0, right = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            first.append(c);
            if (first.length() == length) {
                list.add(new String(first));
                first.delete(left, left + 1);
                left++;
            }
            right++;
        }

        List<Integer> res = new ArrayList<>();
        left = 0;
        right = 0;
        while (right < list.size()) {

        }

        return res;
    }

    // 58. 最后一个单词的长度
    public int lengthOfLastWord(String s) {
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ' ') {
            } else {
                while (i >= 0 && s.charAt(i--) != ' ') {
                    res++;
                }
                break;
            }
        }

        return res;
    }
}

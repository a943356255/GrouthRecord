package com.example.springboot_vue.leetcode;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.socket.utils.StringUtils;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

public class LeetCodeMain19 {

    int testNumber = 1;

    static int staticNumber = 1;

    public static void main(String[] args) throws IllegalAccessException {
//        int[] arr = {3, 6, 3, 7, 8, 2, 1, 9, 10};
//        new LeetCodeMain19().quickSort(0, arr.length - 1, arr);
//        System.out.println(Arrays.toString(arr));
//        City city = new City();
//        city.setMarkId(111);
//        JSONObject jsonObject = new JSONObject();
//        Class<?> clazz = city.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        System.out.println(Arrays.toString(fields));
//        for (int i = 0; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            jsonObject.put(fields[i].getName(), fields[i].get(city));
//        }
//        System.out.println(jsonObject.get("markId"));
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("123");
//        String str = "123";
//        String[] strings = str.split("/");
//        for (int i = 0; i < strings.length; i++) {
//            stringBuilder.append(strings[i]).append("-");
//        }
//        String str2 = "467";
//        String result = String.join("-", strings);
//
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(1, 1);
        LeetCodeMain19 leetCodeMain19 = new LeetCodeMain19();
        test();
    }

    // 98. 验证二叉搜索树
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long lower, long higher) {
        if (root == null) {
            return true;
        }

        if (root.val <= lower || root.val >= higher) {
            return false;
        }

        return isValidBST(root.left, lower, root.val) && isValidBST(root.right, root.val, higher);
    }

    // 33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else {
                if (nums[0] <= nums[mid]) {
                    if (nums[0] < target && nums[mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                } else {
                    if (nums[mid] < target && nums[0] > target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }
        System.out.println("left = " + left + " right = " + right);
        return -1;
    }

    //
    public static void test() {
        City city = new City();
        staticNumber = 2;
    }

    // 1673. 找出最具竞争力的子序列
    public int[] mostCompetitive(int[] nums, int k) {

        if (k >= nums.length) {
            return nums;
        }

        int[] res = new int[k];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peekLast()] > nums[i]) {
                stack.pollLast();
            }
            stack.offerLast(i);
        }

        int temp = 0;
        if (!stack.isEmpty() && stack.size() < k) {
            temp = res.length - stack.size();
            k -= stack.size();
            int index = stack.peekFirst();
            for (int i = index - 1; i >= 0 && k > 0; i--, k--) {
                res[temp--] = nums[i];
            }
            System.out.println(Arrays.toString(res));
            temp = res.length - stack.size();
            while (!stack.isEmpty()) {
                res[temp++] = nums[stack.pop()];
            }
        } else {
            while (!stack.isEmpty() && temp < res.length) {
                res[temp++] = nums[stack.pop()];
            }
        }

        return res;
    }

    // 402. 移掉 K 位数字
    public String removeKdigits(String num, int k) {
        if (k == num.length()) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int count = 0, index = -1;
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (!stack.isEmpty() && stack.peek() > c) {
                stack.pop();
                count++;
                if (count == k) {
                    break;
                }
            }
            stack.push(c);
            if (count == k) {
                index = i + 1;
                break;
            }
        }

        if (index == -1) {
            while (!stack.isEmpty() && stack.size() != num.length() - k) {
                stack.pop();
            }
            while (!stack.isEmpty()) {
                res.insert(0, stack.pop());
            }
        } else {
            while (!stack.isEmpty()) {
                res.insert(0, stack.pop());
            }
            for (int i = index; i < num.length(); i++) {
                res.append(num.charAt(i));
            }
        }

        while (res.charAt(0) == '0' && res.length() > 1) {
            res.delete(0, 1);
        }

        return res.toString();
    }

    // 28. 找出字符串中第一个匹配项的下标
    public int strStr(String haystack, String needle) {
        int index = -1;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                index = i;
                int temp = -1;
                int left = 0;
                while (i < haystack.length() && left < needle.length() && haystack.charAt(i) == needle.charAt(left)) {
                    if (haystack.charAt(i) == needle.charAt(0)) {
                        temp = i;
                    }
                    i++;
                    left++;
                }
                if (left == needle.length()) {
                    return index;
                }
                index = -1;
                System.out.println("temp = " + temp);
                i = temp;
            }
        }
        return index;
    }

    List<List<Integer>> subsetsWithDupList = new ArrayList<>();
    Set<String> subsetsWithDupSet = new HashSet<>();
    // 90. 子集 II
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // Arrays.sort(nums);
        LinkedList<Integer> list = new LinkedList<>();
        subsetsWithDupList.add(new ArrayList<>());
        subsetsWithDupDfs(nums, list, 0);
        return subsetsWithDupList;
    }

    public void subsetsWithDupDfs(int[] nums, LinkedList<Integer> list, int index) {
        if (index == nums.length) {
            return;
        }

        for (int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            if (!subsetsWithDupSet.contains(list.toString())) {
                subsetsWithDupList.add(new ArrayList<>(list));
                subsetsWithDupSet.add(list.toString());
            }
            subsetsWithDupDfs(nums, list, i + 1);
            list.removeLast();
        }
    }

    // 2549. 统计桌面上的不同数字
    public int distinctIntegers(int n) {
        if (n == 1) {
            return 0;
        }
        return n - 1;
    }

    // 392. 判断子序列
    public boolean isSubsequence(String s, String t) {
        int left = 0, right = 0;
        while (left < s.length() && right < t.length()) {
            if (s.charAt(left) == t.charAt(right)) {
                left++;
            }
            right++;
        }

        return left == s.length();
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

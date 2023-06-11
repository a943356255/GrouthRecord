package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain7 {

    public static void main(String[] args) {

    }

    // 1171. 从链表中删去总和值为零的连续节点
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        Map<Integer, ListNode> seen = new HashMap<>();
        int prefix = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            prefix += node.val;
            // 前缀和，以及当前节点的值
            seen.put(prefix, node);
        }

        prefix = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            prefix += node.val;
            // 这里修改了节点的后继节点，会影响dummy的结构
            node.next = seen.get(prefix).next;
        }

        return dummy.next;
    }

    // 1170. 比较字符串最小字母出现频次
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] count = new int[12];
        // 记录个数
        for (String s : words) {
            count[getCount(s)]++;
        }
        // 倒置的前缀和，统计个数和
        // 利用数组这样计算可以把中间缺失的也填充为上一个存在的数字，我自己用的map，没法填充
        for (int i = 9; i >= 1; i--) {
            count[i] += count[i + 1];
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String s = queries[i];
            res[i] = count[getCount(s) + 1];
        }
        return res;
//        int[] res = new int[queries.length];
//
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < queries.length; i++) {
//            map.put(i, getCount(queries[i]));
//        }
//
//        List<Integer> list = new ArrayList<>();
//        for (String word : words) {
//            int temp = getCount(word);
//            list.add(temp);
//        }
//        Collections.sort(list);
//
//        Map<Integer, Integer> word = new HashMap<>();
//        int[] arr = new int[list.size()];
//        int index = 1;
//        arr[0] = 1;
//        word.put(list.get(list.size() - 1), 1);
//        for (int i = list.size() - 2; i >= 0; i--) {
//            arr[index] = arr[index - 1] + 1;
//            word.put(list.get(i), arr[index]);
//            index++;
//        }
//
//        return res;
    }

    public int getCount(String str) {
        int temp = 0;
        char c = 'z';
        for (int j = 0; j < str.length(); j++) {
            char q = str.charAt(j);
            if (q == c) {
                temp++;
            } else if (q < c) {
                c = q;
                temp = 1;
            }
        }

        return temp;
    }

    // 129. 求根节点到叶节点数字之和
    public int sumNumbers(TreeNode root) {
        List<String> list = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        dfs(root, str, list);

        int res = 0;
        for (String s : list) {
            res += Integer.parseInt(s);
        }

        return res;
    }

    public void dfs(TreeNode root, StringBuilder last, List<String> res) {
        if (root == null) {
            return;
        }

        StringBuilder temp = new StringBuilder(last);
        temp.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(temp.toString());
        }

        dfs(root.left, temp, res);
        dfs(root.right, temp, res);
    }

    // 222. 完全二叉树的节点个数
    public int countNodes(TreeNode root) {
        int res = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            res++;
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return res;
    }

    // 2611. 老鼠和奶酪
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
//        PriorityQueue<Integer> first = new PriorityQueue<>();
//        PriorityQueue<Integer> second = new PriorityQueue<>();
//        int res = 0;
//        for (int i = 0; i < reward1.length; i++) {
//            if (reward1[i] > reward2[i]) {
//                if (first.size() < k) {
//                    first.add(reward1[i]);
//                    res += reward1[i];
//                } else {
//
//                }
//            }
//        }
        int ans = 0;
        int n = reward1.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            ans += reward2[i];
            pq.offer(reward1[i] - reward2[i]);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            ans += pq.poll();
        }
        return ans;
    }

    // 2352. 相等行列对
    public int equalPairs(int[][] grid) {
        Map<String, Integer> map = new HashMap<>();
        int res = 0;
        for (int[] ints : grid) {
            StringBuilder str = new StringBuilder();
            for (int anInt : ints) {
                str.append(anInt).append("-");
            }
            map.merge(str.toString(), 1, Integer::sum);
        }

        for (int i = 0; i < grid[0].length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < grid.length; j++) {
                str.append(grid[j][i]).append("-");
            }

            if (map.get(str.toString()) != null) {
                res += map.get(str.toString());
            }
        }

        return res;
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

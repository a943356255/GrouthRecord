package com.example.springboot_vue.leetcode;

import java.io.*;
import java.util.*;

class Number {
    int count;
    int val;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}

public class LeetCodeMain6 {

    int res;
    static final int INF = 0x3f3f3f3f;

    class SubTree {
        boolean isBST;
        int minValue;
        int maxValue;
        int sumValue;

        SubTree(boolean isBST, int minValue, int maxValue, int sumValue) {
            this.isBST = isBST;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.sumValue = sumValue;
        }
    }

    public static void main(String[] args) throws IOException {
//        int[] arr = {1,1,1,1,2,2,3,3};
//        new LeetCodeMain6().rearrangeBarcodes(arr);
        System.out.println(Math.pow(-2, 900));
    }

    // 2559. 统计范围内的元音字符串数
    public int[] vowelStrings(String[] words, int[][] queries) {
        Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        int[] arr = new int[words.length + 1];
        arr[0] = 0;
        for (int i = 0; i < words.length; i++) {
            if (set.contains(words[i].charAt(0)) && set.contains(words[i].charAt(words[i].length() - 1))) {
                arr[i + 1] = arr[i] + 1;
            } else {
                arr[i + 1] = arr[i];
            }
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = arr[queries[i][1] + 1 ] - arr[queries[i][0]];
        }

        return res;
    }

    // 1130. 叶值的最小代价生成树
    public int mctFromLeafValues(int[] arr) {
        int res = 0;
        Deque<Integer> stk = new ArrayDeque<>();
        for (int x : arr) {
            // 因为题目要求非叶子节点的值等于左子树和右子树中叶子节点的最大值的乘积，所以这里栈要是单调递减的
            // 才能确保每次相乘都是最大值相乘
            while (!stk.isEmpty() && stk.peek() <= x) {
                int y = stk.pop();
                // 这一步，y小于当前的x，然后出栈，出站后对比栈顶元素和x，哪个小哪个就和y相乘
                if (stk.isEmpty() || stk.peek() > x) {
                    res += y * x;
                } else {
                    res += stk.peek() * y;
                }
            }
            stk.push(x);
        }

        // 这里是处理上面剩余的数据，每次处理一个数据
        while (stk.size() >= 2) {
            int x = stk.pop();
            res += stk.peek() * x;
        }

        return res;
    }

    // 102. 二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(level);
        }

        return res;
    }

    // 2455. 可被三整除的偶数的平均值
    public int averageValue(int[] nums) {
        int count = 0, sum = 0;
        for (int num : nums) {
            if (num % 3 == 0 && num % 2 == 0) {
                count++;
                sum += num;
            }
        }

        if (count == 0) {
            return 0;
        }

        return sum / count;
    }

    // 242. 有效的字母异位词
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }

        Map<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.merge(t.charAt(i), 1, Integer::sum);
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (!entry.getValue().equals(tMap.get(entry.getKey()))) {
                return false;
            }
        }

        return true;
    }

    // 1093. 大样本统计
    public double[] sampleStats(int[] count) {
        Map<String, Integer> map = new HashMap<>();
        int max = -1, min = Integer.MAX_VALUE;
        double sum = 0;
        int countNum = 0, maxCount = -1;
        for (int i = 0; i < count.length; i++) {
            // 统计样本和
            sum += count[i] * i;

            // 统计样本个数
            countNum += count[i];

            // 统计最大值最小值
            if (count[i] != 0) {
                if (i > max) {
                    max = i;
                }

                if (i < min) {
                    min = i;
                }
            }

            if (maxCount < count[i]) {
                maxCount = count[i];
                map.put("maxCount", i);
            }
        }

        // 计算中位数
        int temp = 0, tempAddOne = -1;
        for (int i = 0; i < count.length; i++) {
            if (temp <= countNum / 2 && temp + count[i] >= countNum / 2) {

            }
            temp += count[i];
        }

        double[] res = new double[5];

        res[0] = min;
        res[1] = max;
        res[2] = sum / countNum;
        res[4] = map.get("maxCount");
        return res;
    }

    // 1091. 二进制矩阵中的最短路径
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] == 1) {
            return -1;
        }

        int n = grid.length;
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 1;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int x = arr[0];
            int y = arr[1];

            // 到达右下角
            if (x == n - 1  && y == n - 1) {
                return dist[x][y];
            }

            // 这里其实就是看当前x和y的右边，下边，以及右下角这三个格子是否是0
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    // 越界
                    if (x + dx < 0 || x + dx >= n || y + dy < 0 || y + dy >= n) {
                        continue;
                    }

                    // 单元格值不为 0 或已被访问
                    // 以访问的情况：访问x和y时，会访问该点的右边以及右下，这两个点都会入队
                    // 当遍历到前一个x和y的右边时，该点的下边一个点已经在遍历x和y时赋值了
                    if (grid[x + dx][y + dy] == 1 || dist[x + dx][y + dy] <= dist[x][y] + 1) {
                        continue;
                    }

                    // 这里就是找到一个可以走的点，然后进行赋值，然后加入队列
                    dist[x + dx][y + dy] = dist[x][y] + 1;
                    queue.offer(new int[]{x + dx, y + dy});
                }
            }
        }

        return -1;
    }

    // 2451. 差值数组不同的字符串
    public String oddString(String[] words) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 1; j < words[i].length(); j++) {
                str.append(words[i].charAt(j) - 'a' - (words[i].charAt(j - 1) - 'a'));
                str.append(" ");
            }

            map.merge(str.toString(), 1, Integer::sum);
            result.put(str.toString(), i);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return words[result.get(entry.getKey())];
            }
        }

        return null;
    }

    // 1090. 受标签影响的最大值
    public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
        int res = 0;
        int[][] arr = new int[values.length][2];
        for (int i = 0; i < values.length; i++) {
            arr[i][0] = values[i];
            arr[i][1] = labels[i];
        }
        Arrays.sort(arr, (o1, o2) -> o2[0] - o1[0]);

        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            if (map.get(arr[i][1]) == null) {
                map.put(arr[i][1], 1);
                res += arr[i][0];
                count++;
            } else {
                int val = map.get(arr[i][1]);
                if (val < useLimit) {
                    res += arr[i][0];
                    map.put(arr[i][1], val + 1);
                    count++;
                }
            }

            if (count == numWanted) {
                break;
            }
        }

        return res;
    }

    // 1080. 根到叶路径上的不足节点
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        boolean haveSufficient = sufficientSubsetDfs(root, 0, limit);
        return haveSufficient ? root : null;
    }

    public boolean sufficientSubsetDfs(TreeNode node, int sum, int limit) {
        if (node == null) {
            return false;
        }

        if (node.left == null && node.right == null) {
            return node.val + sum >= limit;
        }

        // 这里，在递归时会把节点的值加到一起，传递给叶子节点，然后进行判断
        boolean haveSufficientLeft = sufficientSubsetDfs(node.left, sum + node.val, limit);
        boolean haveSufficientRight = sufficientSubsetDfs(node.right, sum + node.val, limit);

        if (!haveSufficientLeft) {
            node.left = null;
        }
        if (!haveSufficientRight) {
            node.right = null;
        }

        return haveSufficientLeft || haveSufficientRight;
    }

    // 1373. 二叉搜索子树的最大键值和
    public int maxSumBST(TreeNode root) {
        res = 0;
        dfs(root);
        return res;
    }

    // LCP 33. 蓄水
    public int storeWater(int[] bucket, int[] vat) {
        int n = bucket.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (bucket[i] == 0 && vat[i] != 0) {
                ++cnt;
                ++bucket[i];
            }
            if (vat[i] > 0) {
                pq.offer(new int[]{(vat[i] + bucket[i] - 1) / bucket[i], i});
            }
        }
        if (pq.isEmpty()) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        while (cnt < res) {
            int[] arr = pq.poll();
            int v = arr[0], i = arr[1];
            res = Math.min(res, cnt + v);
            if (v == 1) {
                break;
            }
            int t = (vat[i] + v - 2) / (v - 1);
            cnt += t - bucket[i];
            bucket[i] = t;
            pq.offer(new int[]{(vat[i] + bucket[i] - 1) / bucket[i], i});
        }

        return res;
    }

    public SubTree dfs(TreeNode root) {
        if (root == null) {
            return new SubTree(true, INF, -INF, 0);
        }

        SubTree left = dfs(root.left);
        SubTree right = dfs(root.right);

        if (left.isBST && right.isBST && root.val > left.maxValue && root.val < right.minValue) {
            if (root.val == 3) {
                System.out.println("进入");
            }
            int sum = root.val + left.sumValue + right.sumValue;
            res = Math.max(sum, res);
            // 在这一步赋值sumValue
            // 必须要Math.min(root.val, left.minValue), Math.max(root.val, right.maxValue)
            // 因为叶子节点返回的最小值是INF,最大值是-INF
            // 如果不进行比较，会导致叶子节点的val无法替换掉这两个值，也就是说后续的节点比较时，min = INF， max = -INF，注意左右节点都是这样
            // 这也就会导致判断是否是二叉排序树时，root.val > left.maxValue是root.val跟-INF进行比较，root.val < right.minVal 是和INF进行比较
            // root.val < left.minValue是 root.val跟INF进行比较，导致结果永远都是二叉排序树
            return new SubTree(true, Math.min(root.val, left.minValue), Math.max(root.val, right.maxValue), sum);
        } else {
            return new SubTree(false, 0, 0, 0);
        }
    }

    // 57. 插入区间
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int[][] arr = new int[intervals.length + 1][2];
        for (int i = 0; i < intervals.length; i++) {
            arr[i][0] = intervals[i][0];
            arr[i][1] = intervals[i][1];
        }
        arr[intervals.length][0] = newInterval[0];
        arr[intervals.length][1] = newInterval[1];

        return writeMerge(arr);
    }

    // 1073. 负二进制数相加
    public int[] addNegabinary(int[] arr1, int[] arr2) {
        long arr1Val = 0;
        int n = arr1.length;
        for (int i = 0; i < n; i++) {
            arr1Val += arr1[i] * Math.pow(-2, n - i - 1);
        }

        long arr2Val = 0;
        int m = arr2.length;
        for (int i = 0; i < m; i++) {
            arr2Val += arr2[i] * Math.pow(-2, m - i - 1);
        }

        long val = arr1Val + arr2Val;
        String str = Integer.toBinaryString((int) val);

        int[] res = new int[str.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = str.charAt(i) - '0';
        }

        return res;
    }

    // 56. 合并区间 正确
    public int[][] writeMerge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 按照第一个元素排序，即按左端点
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));

        List<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            int L = interval[0], R = interval[1];
            // 如果是首次进入，或者说当前元素的左端点大于merge中最后一个节点的右端点，说明这俩区间肯定不重合，直接加入
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < L) {
                merged.add(new int[]{L, R});
            } else {
                // 否则，取本次的R和上一次的最后一个端点的右端点进行比较，取较大值
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    // 56. 合并区间 有误
    public int[][] merge(int[][] intervals) {
        int max = -1;
        for (int[] interval : intervals) {
            if (interval[1] > max) {
                max = interval[1];
            }
        }

        int[] arr = new int[max + 1];
        for (int[] interval : intervals) {
            Arrays.fill(arr, interval[0], interval[1] + 1, 1);
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[0] == 1) {
                list.add(0);
            }

            if (i != 0 && arr[i - 1] == 0 && arr[i] == 1) {
                list.add(i);
            }

            if (i != 0 && arr[i - 1] == 1 && arr[i] == 0) {
                list.add(i - 1);
            }
        }
        if (list.size() % 2 != 0) {
            list.add(arr.length - 1);
        }

        int index = 0;
        int[][] res = new int[list.size() / 2][2];
        for (int i = 0; i < list.size(); i += 2) {
            res[index][0] = list.get(i);
            res[index][1] = list.get(i + 1);
            index++;
        }

        return res;
    }

    // 2446. 判断两个事件是否存在冲突
    public boolean haveConflict(String[] event1, String[] event2) {
        String[] first = event1[0].split(":");
        String[] second = event1[1].split(":");
        int time1 = Integer.parseInt(first[0]) * 60 + Integer.parseInt(first[1]);
        int time1End = Integer.parseInt(second[0]) * 60 + Integer.parseInt(second[1]);

        String[] first1 = event2[0].split(":");
        String[] second1 = event2[1].split(":");
        int time2 = Integer.parseInt(first1[0]) * 60 + Integer.parseInt(first1[1]);
        int time2End = Integer.parseInt(second1[0]) * 60 + Integer.parseInt(second1[1]);

        return (time1 <= time2 || time1 <= time2End) && (time2 <= time1 || time2 <= time1End);
    }

    // 205. 同构字符串
    public boolean isIsomorphic(String s, String t) {
        String str1 = get(s);
        String str2 = get(t);

        return str1.equals(str2);
    }

    public String get(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int index = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) == null) {
                map.put(c, index);
                list.add(index);
                index++;
            } else {
                list.add(map.get(c));
            }
        }

        return list.toString();
    }


    // 1335. 工作计划的最低难度
    public int minDifficulty(int[] jobDifficulty, int d) {
        if (jobDifficulty.length < d) {
            return -1;
        }

        int res = 0;

        return res;
    }

    // 1072. 按列翻转得到最大值等行数
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        int res = 0, n = matrix[0].length;
        Map<String, Integer> map = new HashMap<>();
        for (int[] ints : matrix) {
            char[] arr = new char[n];
            Arrays.fill(arr, '0');
            if (ints[0] == 1) {
                for (int j = 0; j < n; j++) {
                    if (ints[j] == 0) {
                        arr[j] = '1';
                    } else {
                        arr[j] = '0';
                    }
                }
            } else {
                for (int j = 0; j < n; j++) {
                    if (ints[j] == 0) {
                        arr[j] = '0';
                    } else {
                        arr[j] = '1';
                    }
                }
            }
            String str = new String(arr);
            map.put(str, map.getOrDefault(str, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            res = Math.max(entry.getValue(), res);
        }

        return res;
    }

    // 1054. 距离相等的条形码
    public int[] rearrangeBarcodes(int[] barcodes) {
        if (barcodes.length == 1) {
            return barcodes;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int barcode : barcodes) {
            map.merge(barcode, 1, Integer::sum);
        }

        List<Number> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Number number = new Number();
            number.count = entry.getValue();
            number.val = entry.getKey();
            list.add(number);
        }

        list.sort(Comparator.comparingInt(a -> a.count));

        int[] res = new int[barcodes.length];
        int left = 0, right = list.size() - 1, index = 0;
        while (left < right) {
            if (list.get(right).count == 0) {
                right--;
            }
            if (right < 0) {
                break;
            }
            res[index++] = list.get(right).val;
            list.get(right).count--;

            if (list.get(left).count == 0) {
                left++;
            }
            if (left >= list.size()) {
                break;
            }
            res[index++] = list.get(left).val;
            list.get(left).count--;

            if (index >= barcodes.length) {
                break;
            }
        }

        return res;
    }

    // 2441. 与对应负数同时存在的最大正整数
    public int findMaxK(int[] nums) {
        int max = -1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int val : nums) {
            if (map.get(val) == null) {
                map.put(-val, 1);
            } else {
                max = Math.max(max, Math.abs(val));
            }
        }

        return max;
    }

    // 76. 最小覆盖子串
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }

        Map<Character, Integer> targetMap = new HashMap<>();
        Map<Character, Integer> haveMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            targetMap.merge(c, 1, Integer::sum);
        }

        int left = 0, right = -1;
        int min = Integer.MAX_VALUE, resL = -1, resR = -1;
        while (right < s.length()) {
            right++;
            // target中有的元素，才有必要加入已有元素的集合
            if (right < s.length() && targetMap.containsKey(s.charAt(right))) {
                haveMap.merge(s.charAt(right), 1, Integer::sum);
            }

            while (check(targetMap, haveMap) && left <= right) {
                // 进入check后，说明满足条件，那么只需要判断长度就可以,如果长度小于之前的长度，那么就可取
                if (right - left + 1 < min) {
                    min = right - left + 1;
                    resL = left;
                    resR = left + min;
                }
                char b = s.charAt(left);
                // 这里是要开始左滑窗口，left是划出去的字符，要判断它是否在target中，如果在，要在have中移除，之后重新判断剩下的单词是否满足
                if (targetMap.containsKey(b)) {
                    haveMap.put(b, haveMap.getOrDefault(b, 0) - 1);
                }

                left++;
            }
        }

        return resL == -1 ? "" : s.substring(resL, resR);
    }

    public boolean check(Map<Character, Integer> targetMap, Map<Character, Integer> haveMap) {
        for (Map.Entry<Character, Integer> entry : targetMap.entrySet()) {
            if (haveMap.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }

        return true;
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

package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain2 {

    public static void main(String[] args) {
        LeetCodeMain2 leetCodeMain2 = new LeetCodeMain2();
        int a = 2;
        int b = 2;
        int c = 9;
        int d = 3;
        int e = 3;
        int f = 1;
        System.out.println(c ^ f);
    }



    // 260. 只出现一次的数字 III
    public int[] singleNumber(int[] nums) {
        int[] res = new int[2];
        int index = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                res[index++] = nums[i];
            } else {
                set.add(nums[i]);
            }
        }

        return res;
    }

    // 1806. 还原排列的最少操作步数
    public int reinitializePermutation(int n) {
        int res = 0;
        int[] perm = new int[n];
        int[] target = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
            target[i] = i;
        }

        do {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    arr[i] = perm[i / 2];
                } else {
                    arr[i] = perm[n / 2 + (i - 1) / 2];
                }
            }
            perm = arr;
            res++;
        } while (!Arrays.equals(target, perm));

        return res;
    }

    // 2185. 统计包含给定前缀的字符串
    public int prefixCount(String[] words, String pref) {
        int res = 0;
        for (String str : words) {
            if (str.startsWith(pref)) {
                res++;
            }
        }

        return res;
    }

    // 1658. 将 x 减到 0 的最小操作数
    public int minOperations(int[] nums, int x) {
        int sum = Arrays.stream(nums).sum();
        if (sum < x) {
            return -1;
        }

        int length = nums.length, lSum = 0, rSum = sum, right = 0;
        int res = length + 1;
        for (int left = -1; left < length - 1; left ++) {
            // 这里也没有意识到lSum要加上nums[left]
            if (left >= 0) {
                lSum += nums[left];
            }

            // 这里没有意识到rSum要减去nums[right]
            while (right < length && lSum + rSum > x) {
                rSum -= nums[right];
                right++;
            }

            if (lSum + rSum == x) {
                res = Math.min(res, length - right + left + 1);
            }
        }

        return res > length ? -1 : res;
    }
//    public int minOperations(int[] nums, int x) {
//        int res = 0, length = nums.length - 1, total = 0;
//        if (nums[0] > x && nums[length] > x) {
//            return -1;
//        }
//
//        // 求前缀和
//        int[] arr = new int[length + 2];
//        for (int i = 1; i <= nums.length; i++) {
//            arr[i] = arr[i - 1] + nums[i - 1];
//        }
//        total = arr[arr.length - 1];
//
//        // left 到 right的距离为窗口大小
//        int left = 1, right = length;
//        while (left < right) {
//            left++;
//
//            // part是中间窗口的总和
//            int part = total - arr[left] - (arr[right] - arr[right - 1]);
//            if (total - part == x) {
//                return res;
//            }
//
//            if ()
//
//            right--;
//        }
//
//        return res;
//    }

    // 42. 接雨水
    public int trap(int[] height) {
        int res = 0, stackBottom = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < height.length; i++) {

            while (!stack.isEmpty() && height[i] > stack.peek()) {

            }
            stack.add(height[i]);
            if (height[i] > stackBottom) {
                stackBottom = height[i];
            }
        }

        return res;
    }

    // 48. 旋转图像
    public void rotate(int[][] matrix) {
        int[][] another = new int[matrix.length][matrix[0].length];
        int length = matrix.length - 1;
        for (int i = length; i >= 0; i--) {
            for (int j = 0; j < matrix[0].length; j++) {
                another[j][length - i] = matrix[i][j];
            }
        }

        for (int i = 0; i <= length; i++) {
            System.arraycopy(another[i], 0, matrix[i], 0, matrix[0].length);
        }
    }

    // 2180. 统计各位数字之和为偶数的整数个数
    public int countEven(int num) {
        int res = 0;
        for (int i = 2; i <= num; i++) {
            int part = 0, a = i;
            while (a > 0) {
                part += a % 10;
                a /= 10;
            }
            if (part % 2 == 0) {
                res ++;
            }
        }

        return res;
    }

    // 1803. 统计异或值在范围内的数对有多少
    public int countPairs(int[] nums, int low, int high) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] ^ nums[j]) >= low && ((nums[i] ^ nums[j])) <= high) {
                    res ++;
                }
            }
        }

        return res;
    }

    // 2042. 检查句子中的数字是否递增
    public boolean areNumbersAscending(String s) {
        int last = -1;
        String[] arr = s.split(" ");

        for (String str : arr) {
            if (str.charAt(0) <= '9' && str.charAt(0) >= '0') {
                int val = Integer.parseInt(str);
                if (last != -1 && val <= last) {
                    return false;
                } else {
                    last = val;
                }
            }
        }

        return true;
    }

    public int[][] add(int[][] arr, int[][] arr2) {
        int[][] val = new int[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                val[i][j] = arr[i][j] + arr2[i][j];
            }
        }

        return val;
    }

    // 1801. 积压订单中的订单总数
    public int getNumberOfBacklogOrders(int[][] orders) {
        // 这里的排序规则，没有答案设计的巧妙。题解是用一个数组，然后数组里边存两个元素，就是我这里map的key和value
        // 而且题解是在没一次遍历，先去另一个队列里看，进行相应的扣除，扣除完再添加到队列中。
        PriorityQueue<HashMap<Integer, Integer>> buyBacklog = new PriorityQueue<>(new MyComparator());
        PriorityQueue<HashMap<Integer, Integer>> sellBacklog = new PriorityQueue<>(new MyComparator());

        long res = 0;
        for (int i = 0; i < orders.length; i++) {
            // 0表示采购订单
            if (orders[i][2] == 0) {
                if (sellBacklog.size() > 0) {
                    int count = 0;
                    for (HashMap<Integer, Integer> map : sellBacklog) {
                        // 每一个map只有一条数据
                        int key = 0, val = 0;
                        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                            key = entry.getKey();
                            val = entry.getValue();
                        }

                        if (key <= orders[i][0]) {
                            if (val <= orders[i][1]) {
                                count++;
                                orders[i][1] -= val;
                            } else {
                                break;
                            }
                        }
                    }

                    while (count > 0) {
                        sellBacklog.poll();
                        count--;
                    }
                } else {
                    HashMap<Integer, Integer> map = new HashMap<>();
                    // key 为价格，value 为数量
                    map.put(orders[i][0], orders[i][1]);
                    buyBacklog.add(map);
                    res += orders[i][1];
                }

            } else {
                // 1表示销售订单

            }
        }

        return (int) (res % (1e9 + 7));
    }

    // 2351. 第一个出现两次的字母
    public char repeatedCharacter(String s) {
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!set.contains(c)) {
                set.add(c);
            } else {
                return c;
            }
        }

        return 'c';
    }

    // 2037. 使每位学生都有座位的最少移动次数
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);

        int res = 0;
        for (int i = 0; i < seats.length; i++) {
            res += Math.abs(seats[i] - students[i]);
        }

        return res;
    }

    // 43. 字符串相乘
    public String multiply(String num1, String num2) {
        StringBuilder res = new StringBuilder();

        int mark = 0;
        for (int i = 0; i < num1.length(); i++) {
            for (int j = 0; j < num2.length(); j++) {

            }
        }

        return res.toString();
    }

    // 2032. 至少在两个数组中出现的值
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Set<Integer> list = new HashSet<>();
        Set<Integer> set = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        for (int j : nums1) {
            set.add(j);
        }

        for (int i : nums2) {
            if (set.contains(i)) {
                list.add(i);
            } else {
                set2.add(i);
            }
        }

        for (int i : nums3) {
            if (set.contains(i) || set2.contains(i)) {
                list.add(i);
            }
        }

        return new ArrayList<>(list);
    }

    // 1750. 删除字符串两端相同字符后的最短长度
    public int minimumLength(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            char c = s.charAt(left);
            if (s.charAt(right) == c) {
                while (right >= 0 && s.charAt(right) == c) {
                    right--;
                }
            } else {
                break;
            }

            while (left < s.length() && s.charAt(left) == c) {
                left++;
            }
        }

        return Math.max(right - left + 1, 0);
    }

    // 2396. 严格回文的数字
    public boolean isStrictlyPalindromic(int n) {
        String binaryStr = Integer.toBinaryString(n);

        int left = 0, right = binaryStr.length() - 1;
        while (left <= right) {
            if (binaryStr.charAt(left) != binaryStr.charAt(right)) {
                return false;
            }
            left ++;
            right --;
        }

        return true;
    }

    // 2390. 从字符串中移除星号
    public String removeStars(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '*') {
                stack.push(s.charAt(i));
            } else {
                stack.pop();
            }
        }

        StringBuilder str = new StringBuilder();
        while (!stack.isEmpty()) {
            str.append(stack.pop());
        }
        str.reverse();
        return str.toString();
    }

    // 2027. 转换字符串的最少操作次数
    // 情况分的太复杂了
    // 只需要从第一个x，每次加三，看其他的x是否在里边
    public int minimumMoves(String s) {
        int res = 0;
        int first = -1, second = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'X') {
                if (first == -1) {
                    first = i;
                } else {
                    second = i;
                    break;
                }
            }
        }

        for (int i = second + 1; i < s.length(); i++) {
            if (s.charAt(i) == 'X') {
                if (second - first >= 3) {
                    res += 2;
                    if (i - second >= 3) {
                        res++;
                    }
                } else {
                    res += 1;
                    if (i - first >= 3) {
                        res++;
                    }
                }
                first = second;
                second = i;
            }
        }

        return res;
    }

    // 2352. 相等行列对
    public int equalPairs(int[][] grid) {
        Map<String, Integer> map = new HashMap<>();

        for (int[] value : grid) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < grid[0].length; ++j) {
                str.append(" ");
                str.append(value[j]);
            }

            map.merge(str.toString(), 1, Integer::sum);
        }

        int res = 0;
        for (int i = 0; i < grid[0].length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int[] ints : grid) {
                stringBuilder.append(" ");
                stringBuilder.append(ints[i]);
            }

            if (map.containsKey(stringBuilder.toString())) {
                res += map.get(stringBuilder.toString());
            }
        }

        return res;
    }

    // 1759. 统计同构子字符串的数目
    public int countHomogenous(String s) {
        long res = 0;

        char last = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            int count = 1, sum = 1;
            while (i < s.length() && s.charAt(i) == last) {
                count++;
                sum += count;
                i++;
            }

            res += sum;
            if (i < s.length()) {
                last = s.charAt(i);
            } else {
                break;
            }
        }

        return (int) (res % (1e9 + 7));
    }

    // 187. 重复的DNA序列
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> list = new ArrayList<>();

        if (s.length() < 10) {
            return list;
        }

        Map<String, String> map = new HashMap<>();
        int begin = 0, end = 10;
        while (end < s.length()) {
            String str = s.substring(begin, end);
            if (map.get(str) != null) {
                if (!list.contains(str)) {
                    list.add(str);
                }
            } else {
                map.put(str, "1");
            }

            begin++;
            end++;
        }

        return list;
    }


    // 1754. 构造字典序最大的合并字符串
    public String largestMerge(String word1, String word2) {
        StringBuilder stringBuilder = new StringBuilder();
        int index1 = 0, index2 = 0;
        while (index1 != word1.length() && index2 != word2.length()) {
            if (word1.charAt(index1) < word2.charAt(index2)) {
                stringBuilder.append(word2.charAt(index2));
                index2++;
            } else if (word1.charAt(index1) > word2.charAt(index2)) {
                stringBuilder.append(word1.charAt(index1));
                index1++;
            } else {
                // 相等的情况
                int count = 0;
                while (index1 < word1.length() && index2 < word2.length() && word1.charAt(index1) == word2.charAt(index2)) {
                    stringBuilder.append(word1.charAt(index1));
                    count++;
                    index1++;
                    index2++;
                }
                if (index1 == word1.length() || index2 == word2.length()) {
                    count--;
                }
                if (index1 == word1.length()) {
                    index1--;
                }
                if (index2 == word2.length()) {
                    index2--;
                }
                if (word1.charAt(index1) > word2.charAt(index2)) {
                    stringBuilder.append(word2.charAt(index2));
                    index1 -= count;
                    index2++;
                } else {
                    stringBuilder.append(word1.charAt(index1));
                    index2 -= count;
                    index1++;
                }
            }
        }

        if (index1 != word1.length()) {
            stringBuilder.append(word1.substring(index1));
        }

        if (index2 != word2.length()) {
            stringBuilder.append(word2.substring(index2));
        }

        return stringBuilder.toString();
    }

    // 2011. 执行操作后的变量值
    public int finalValueAfterOperations(String[] operations) {

        int x = 0;
        for (String operation : operations) {
            if (operation.equals("++X") || operation.equals("X++")) {
                ++x;
            } else {
                --x;
            }
        }

        return x;
    }

    // 2483. 商店的最少代价
    public int bestClosingTime(String customers) {
        int[] arr = new int[customers.length() + 1];
        int countN = 0, countY = 0;
        for (int i = 0; i < customers.length(); i++) {
            if (customers.charAt(i) == 'Y') {
                countY++;
            }
        }

        arr[0] = countY;
        int min = arr[0], index = 0;
        for (int i = 1; i < customers.length(); i++) {
            if (customers.charAt(i - 1) == 'N') {
                countN++;
            } else {
                countY--;
            }
            arr[i] = countY + countN;
            if (arr[i] < min) {
                min = arr[i];
                index = i;
            }
        }

        return index;
    }

    // 2487. 从链表中移除节点
    public ListNode removeNodes(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode ret = new ListNode();

        while (head != null) {
            stack.push(head.val);
            head = head.next;
        }

        int max = stack.peek();

        while (!stack.isEmpty()) {
            if (stack.peek() >= max) {
                ListNode node = new ListNode();
                node.val = stack.pop();
                max = node.val;
                node.next = ret.next;
                ret.next = node;
            } else {
                stack.pop();
            }
        }

        return ret;
    }

    // 1799. N 次操作后的最大分数和
    public int maxScore(int[] nums) {
        return 1;
    }

    // 获取最大公约数
    public int getGCD(int a, int b) {
        if (a < 0 || b < 0) {
            return -1; // 数学上不考虑负数的约数
        }

        if (b == 0) {
            return a;
        }

        while (a % b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return b;
    }

    // 面试题 02.04. 分割链表
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode large = new ListNode(0);
        ListNode largeHead = large;
        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = largeHead.next;
        return smallHead.next;

//        ListNode returnVal = new ListNode();
//        ListNode tail = new ListNode();
//        ListNode before = new ListNode();
//        before.next = tail;
//        returnVal.next = tail;
//
//        while (head != null) {
//            ListNode node = new ListNode();
//            node.val = head.val;
//            if (head.val < x) {
//                node.next = head.next;
//                head.next = node;
//            } else {
//                before = tail;
//                tail.val = node.val;
//                tail.next = node;
//                tail = node;
//            }
//
//            head = head.next;
//        }
//
//        before.next = null;
//        return returnVal;
    }

    // 7. 整数反转
    public int reverse(int x) {
        int count = 0, res = 0, mark = 0;
        if (x < 0) {
            mark = 1;
            x = -x;
        }

        int test = x;
        while (test > 0) {
            test /= 10;
            count++;
        }
        count--;

        while (x > 0) {
            res += (x % 10) * Math.pow(10, count);
            count--;
            x /= 10;
        }

        if (res == 2147483647) {
            return 0;
        }

        return mark == 1 ? -res : res;
    }

    // 392. 判断子序列
    public boolean isSubsequence(String s, String t) {
        if (s.equals("") || t.equals("")) {
            return false;
        }
        int i = 0;
        for (int j = 0; j < t.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
        }
        return i == s.length();
    }

    // 1760. 袋子里最少数目的球
    public int minimumSize(int[] nums, int maxOperations) {
        return 1;
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        int min = 200000;
        List<Integer> list = new ArrayList<>();
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < edges.length; i++) {
            set.add(edges[i][0]);
            set.add(edges[i][1]);

            if (map.get(edges[i][0]) == null) {
                ArrayList<Integer> list1 = new ArrayList<>();
                list1.add(edges[i][1]);
                map.put(edges[i][0], list1);
            } else {
                map.get(edges[i][0]).add(edges[i][1]);
            }

            if (map.get(edges[i][1]) == null) {
                ArrayList<Integer> list1 = new ArrayList<>();
                list1.add(edges[i][0]);
                map.put(edges[i][1], list1);
            } else {
                map.get(edges[i][1]).add(edges[i][0]);
            }
        }

        for (int val : set) {
            int res = 0;
            map.get(val);
        }
        return list;
    }

    // 1971. 寻找图中是否存在路径
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        int[] arr = new int[n];
        // 这里是初始化，把所有节点都当作根节点，即arr[x] = x;
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < edges.length; i++) {
            myMerge(arr, edges[i][0], edges[i][1]);
        }

        return myFind(arr, source) == myFind(arr, destination);
    }

    public void myMerge(int[] arr, int x, int y) {
        x = myFind(arr, x);
        y = myFind(arr, y);
        arr[y] = x;
    }

    public int myFind(int[] arr, int x) {
        // arr的父节点等于本身，则说明到达了根节点, 返回
        if (arr[x] == x) {
            return x;
        }
        // return myFind(arr, arr[x]);
        // 直接return 相当于一步一步的找他的父节点，所以速度会比较慢。
        // 这样写是属于状态压缩，修改了arr[x]的值，并不需要关心他的真证的父节点是谁，只需要考虑他们是否在同一集合中即可。
        // 这里只有一个返回值，就是他们公共的祖先节点。这一赋值操作使得他们每个节点的父节点都变成了这个祖先节点，降低了高度
        return arr[x] = myFind(arr, arr[x]);
    }

    // 1703. 得到连续 K 个 1 的最少相邻交换次数
    public int minMoves(int[] nums, int k) {
        return 1;
    }

    // 1764. 通过连接另一个数组的子数组得到一个数组
    public boolean canChoose(int[][] groups, int[] nums) {

        int last = 0, index = 0, i, j;
        for (i = 0; i < groups.length; ++i) {
            for (j = last; j < nums.length; ++j) {
                for (int k = 0; k < groups[i].length; ++k) {
                    if (nums[j] != groups[i][k]) {
                        index = k;
                        break;
                    }
                }
                if (index == 0) {
                    last += groups[i].length;
                } else {
                    last++;
                }
            }
        }

//        for (int i = 0; i < groups.length; ++i) {
//            if (nums[k] == groups[i][j]) {
//                while (nums[k] == groups[i][j] && k < nums.length && j < groups[i].length) {
//                    k++;
//                    j++;
//                    if (nums[k] == groups[i][0]) {
//                        queue.add(k);
//                    }
//                }
//                if (j != groups[i].length) {
//                    if (!queue.isEmpty()) {
//                        k = queue.poll();
//                    } else {
//                        k = last + 1;
//                    }
//                    j = 0;
//                    i--;
//                }
//            } else {
//                k++;
//                if (k == nums.length - groups[i].length + 1) {
//                    return false;
//                }
//            }
//        }

        return i == groups.length;
    }

    // 剑指 Offer II 044. 二叉树每层的最大值
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int max = queue.peek().val;
            // 注意这里len
            int len = queue.size();
            while (len > 0) {
                len--;
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }

                if (max < node.val) {
                    max = node.val;
                }
            }
            list.add(max);
        }

        return list;
    }
}

class MyComparator implements Comparator<HashMap<Integer, Integer>> {

    @Override
    public int compare(HashMap<Integer, Integer> a, HashMap<Integer, Integer> b) {
        int keyInA = a.keySet().iterator().next(); // key of a
        int keyInB = b.keySet().iterator().next(); // key of b
        return b.get(keyInB) - a.get(keyInA);
    }
}
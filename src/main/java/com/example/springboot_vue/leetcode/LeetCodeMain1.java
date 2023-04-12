package com.example.springboot_vue.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class LeetCodeMain1 {

    public static void main(String[] args) {
        LeetCodeMain1 leetCodeMain1 = new LeetCodeMain1();
        String str = "abcde";
        System.out.println(3 & 3);
    }



    // 2401. 最长优雅子数组
    public int longestNiceSubarray(int[] nums) {
        int ans = 0;
        // 这里用了or存储或的和，然后可以在O(1)的时间复杂度内，求出当前元素与前边所有元素的&
        for (int i = 0; i < nums.length; ++i) {
            int or = 0, j = i;
            // 每次从i开始往前找
            while (j >= 0 && (or & nums[j]) == 0)
                or |= nums[j--];
            ans = Math.max(ans, i - j);
        }
        return ans;

//        int left = 0, right = 1, res = 1;
//
//        while (left < nums.length && right < nums.length) {
//            if ((nums[left] & nums[right]) == 0) {
//                right++;
//            } else {
//                if (res > (right - left)) {
//                    continue;
//                }
//                if (left == right - 1) {
//                    left++;
//                    right++;
//                }
//                // 这里left和right的下标已经不符合，遍历从left + 1到 right - 1
//                for (int i = left + 1; i <= right - 1; ++i) {
//                    for (int j = i + 1; j <= right - 1; ++j) {
//                        if ((nums[i] & nums[j]) != 0) {
//                            left++;
//                            break;
//                        }
//                    }
//                }
//
//                res = Math.max(res, right - left);
//            }
//        }
//
//        return res;
    }

    // 1785. 构成特定和需要添加的最少元素
    // 贪心的思路是正确的，每次都加上最大值limit
    // 但其实并不需要加，直接用差值除以limit，就能得到需要加多少次，整除直接返回，不能整除再加一返回
    public int minElements(int[] nums, int limit, int goal) {
        int res = 0;
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum == goal) {
            return 0;
        }

        // 如果差值小于limit，一次就可以
        if (Math.abs(sum - goal) < limit) {
            return 1;
        }

        while (sum != goal) {

            if (Math.abs(sum - goal) < limit) {
                res++;
                break;
            }

            if (sum > goal) {
                sum -= limit;
            } else {
                sum += limit;
            }
            res++;
        }

        return res;
    }

    // 1945. 字符串转化后的各位数字之和
    public int getLucky(String s, int k) {
//        int sum = 0;
        String sum = "";
        for (int i = 0; i < s.length(); i++) {
            sum += (s.charAt(i) - 'a' + 1);
        }
        int res = 0;

        while (k > 0) {
            k--;
            for (int i = 0; i < sum.length(); i++) {
                res += sum.charAt(i) - '0';
            }

            System.out.println("res = " + res);

            if (k != 0) {
                sum = String.valueOf(res);
            }
        }

        return res;
    }

    // 剑指 Offer II 025. 链表中的两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        int part = 0; int first, second;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            ListNode node = new ListNode();
            first = stack1.pop();
            second = stack2.pop();
            node.val = (part + first + second) % 10;
            if (part + first + second >= 10) {
                part = 1;
            } else {
                part = 0;
            }
            node.next = head.next;
            head.next = node;
        }

        while (!stack1.isEmpty()) {
            first = stack1.pop();
            ListNode node = new ListNode();
            node.val = (first + part) % 10;
            if (part + first >= 10) {
                part = 1;
            } else {
                part = 0;
            }
            node.next = head.next;
            head.next = node;
        }

        while (!stack2.isEmpty()) {
            second = stack2.pop();
            ListNode node = new ListNode();
            node.val = (second + part) % 10;
            if (part + second >= 10) {
                part = 1;
            } else {
                part = 0;
            }
            node.next = head.next;
            head.next = node;
        }

        if (part == 1) {
            ListNode node = new ListNode(1);
            node.next = head.next;
            head.next = node;
        }

//        StringBuilder str1 = new StringBuilder();
//        while (l1 != null) {
//            str1.append(l1.val);
//            l1 = l1.next;
//        }
//
//        StringBuilder str2 = new StringBuilder();
//        while (l2 != null) {
//            str2.append(l2.val);
//            l2 = l2.next;
//        }
//
//        long val = Long.parseLong(str1.toString()) + Long.parseLong(str2.toString());
//
//        if (val == 0) {
//            return new ListNode(0);
//        }
//
//
//        while (val > 0) {
//            ListNode node = new ListNode();
//            node.val = (int) (val % 10);
//            node.next = head.next;
//            head.next = node;
//            val /= 10;
//        }

        return head.next;
    }

    // 400. 第 N 位数字, 这样写会超出内存限制
    public int findNthDigit(int n) {
        StringBuilder str = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            str.append(i);
            if (str.length() >= n) {
                break;
            }
        }

        return Integer.valueOf(str.charAt(n));
    }

    // 1697. 检查边长度限制的路径是否存在
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        boolean[] res = new boolean[queries.length];

        // 根据edgeList的第三个元素进行排序，即根据两点之间的权重进行排序
        Arrays.sort(edgeList, Comparator.comparingInt(a -> a[2]));

        // 查询条件也根据第三个元素排，即限制的权重
        // 直接这样排序是不对的
        // Arrays.sort(queries, Comparator.comparingInt(a -> a[2]));

        // 这里做的映射是干啥的
        Integer[] index = new Integer[queries.length];
        for (int i = 0; i < queries.length; i++) {
            index[i] = i;
        }
        Arrays.sort(index, Comparator.comparingInt(a -> queries[a][2]));

        // arr用于存储并查集
        int[] arr = new int[n];
        // 初始化, 将每个节点的父节点设置为自己
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        int k = 0;
        for (int i : index) {
            while (k < edgeList.length && edgeList[k][2] < queries[i][2]) {
                // 如果满足两个点的边小于限制的边，就加入并查集
                merge(arr, edgeList[k][0], edgeList[k][1]);
                k++;
            }
            // res[i]表示的是queries[i]是否满足条件
            // find(arr, queries[i][1])找到第二个元素的父节点
            // find(arr, queries[i][0])找到第一个元素的父节点
            // 两个元素的父节点如果在同一个集合中，说明他们可以到达
            // 又由于上边的判断，只有满足边小于限制的权重时，才会将他们加入同一个集合
            // 所以当结束合并后，这里直接找就可以了
            res[i] = find(arr, queries[i][1]) == find(arr, queries[i][0]);
        }

        return res;
    }

    public int find(int[] arr, int x) {
        if (arr[x] == x) {
            return x;
        }
        return arr[x] = find(arr, arr[x]);
    }

    public void merge(int[] arr, int x, int y) {
        x = find(arr, x);
        y = find(arr, y);
        arr[y] = x;
    }

    int mark = 0;
    // 306. 累加数 思路正确，但是实现的细节上有点问题。注意处理0开头的数字
    // 一种更为简单的实现方法就是只确定第一位和第二位，然后一直加，加到和num相同取判断他们两个是否相同
    public boolean isAdditiveNumber(String num) {
        for (int i = 1; i <= num.length() / 2; ++i) {
            for (int j = 1; j < num.length(); ++j) {
                sumDfs(num, i, j);
                if (mark == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    // step指本次需要取到的长度, 这里仅针对第一个和第二个数字
    void sumDfs(String num, int stepFirst, int stepSecond) {
        if (num.charAt(stepFirst) == '0' && stepSecond > 1) {
            return;
        }
        int first;
        int second;
        String value, third;
        first = Integer.parseInt(num.substring(0, stepFirst));
        int secondLength = Math.min(stepFirst + stepSecond + 1, num.length());
        second = Integer.parseInt(num.substring(stepFirst, secondLength));
        int index = stepFirst + stepSecond;
        while (true) {
            value = String.valueOf(first + second);
            int thirdLength = Math.min(index + value.length(), num.length());
            third = num.substring(index, thirdLength);
            if (third.equals(value)) {
                first = second;
                second = Integer.parseInt(value);
                index += value.length();
                if (index == num.length()) {
                    mark = 1;
                    break;
                }
            } else {
                break;
            }
        }
    }

    // 416. 分割等和子集
    public boolean canPartition(int[] nums) {
        if (nums.length < 2) {
            return false;
        }

        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) {
            return false;
        }

        int res = sum / 2;
        // dp[i][j]表示从0到i是否存在和为j的元素，存在为true，否则为false, 可以取0个数
        boolean[][] dp = new boolean[nums.length][res + 1];
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i < nums.length; ++i) {
            for (int j = 1; j <= res; ++j) {
                // 如果当前目标值大于num[i]，说明可以考虑将num[i]加进去，所以是两种结果
                // 如果小于，那么num[i]一定无法加进去，直接取上一个结果即可。
                if (j >= nums[i]) {
                    // 若dp[i - 1][j]为true，说明此时不加num[i]即可
                    // 若dp[i - 1][j - nums[i]]为true，此时加上num[i]即可
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[nums.length - 1][res];
    }

    // 1832. 判断句子是否为全字母句
    public boolean checkIfPangram(String sentence) {
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < sentence.length(); i++) {
            if (!list.contains(sentence.charAt(i))) {
                list.add(sentence.charAt(i));
            }

            if (list.size() == 26) {
                return true;
            }
        }

        return false;
    }

    // 395. 至少有 K 个重复字符的最长子串, 这样写超时
    public int longestSubstring(String s, int k) {
        int[] arr = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            arr[s.charAt(i) - 'a']++;
        }

        int min = 0; int max = 0; int sum = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            int[] part = Arrays.copyOf(arr, 26);
            for (int j = 0; j <= i - k; ++j) {
                if (j != 0) {
                    part[s.charAt(j - 1) - 'a']--;
                }
                min = Arrays.stream(part).filter(a -> a != 0).min().getAsInt();
                sum = Arrays.stream(part).sum();
                if (min >= k) {
                    if (sum > max) {
                        max = sum;
                    }
                }
            }
            arr[s.charAt(i) - 'a']--;
        }

        return max;
    }

    // 1781. 所有子字符串美丽值之和
    public int beautySum(String s) {
        int res = 0;
        if (s.length() < 3) {
            return 0;
        }
        int[] arr = new int[26];

        for (int i = 0; i < s.length(); ++i) {
            arr[s.charAt(i) - 'a']++;
        }

        int max = arr[0], min = arr[0];
        for (int i = s.length() - 1; i >= 0; --i) {
            int[] part = Arrays.copyOf(arr, 26);
            for (int j = 0; j <= i - 2; ++j) {
                if (j != 0) {
                    part[s.charAt(j - 1) - 'a']--;
                }
                max = Arrays.stream(part).max().getAsInt();
                min = Arrays.stream(part).filter(a -> a != 0).min().getAsInt();
                res += (max - min);
            }
            arr[s.charAt(i) - 'a']--;
        }

        return res;
    }

    // 面试题 17.24. 最大子矩阵
    // 这里的代码是不对的
    // 这题思路最关键的一点就是化二维变一维，用列元素和来将二维变一维
    public int[] getMaxMatrix(int[][] matrix) {
        int[] arr = new int[matrix.length * matrix[0].length + 1];
        int[][] dp = new int[matrix.length][matrix[0].length];
        int[] res = new int[4];

        int index = 1;
        // 求前缀和
        arr[0] = 0;
        for (int i = 0; i <= matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                arr[index] = arr[index - 1] + matrix[i][j];
                index++;
            }
        }

        int max = matrix[0][0];
        res[0] = 0; res[1] = 0; res[2] = 0; res[3] = 0;
        // 处理第0行，后续从第一行开始
        for (int j = 1; j < matrix[0].length; ++j) {
            for (int k = 0; k <= j; ++k) {
                if (arr[j] - arr[k] > max) {
                    max = arr[j] - arr[k];
                    res[1] = k;
                    res[3] = j;
                }
            }
        }

        int total = 0, part = 0;
        for (int i = 1; i <= matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                // i, k代表以k为起点i，j为终点的矩阵的和
                for (int k = 0; k <= j; ++k) {
                    index = (Math.max(i - 1, 1)) * matrix.length;
                    total = arr[i * matrix[0].length + j] - arr[index] + arr[(i - 1) * matrix[0].length + j + 1];
                    part = arr[i * matrix[0].length + k] - arr[index] + arr[(i - 1) * matrix[0].length + k + 1];
                    if (total - part > max) {
                        res[0] = i; res[1] = 0; res[2] = 0; res[3] = 0;
                        max = total - part;
                    }
                }
            }
        }

        return res;
    }

    // 2414. 最长的字母序连续子字符串的长度
    public int longestContinuousSubstring(String s) {
        int res = 1;
        int max = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) - s.charAt(i - 1) == 1) {
                res ++;
            } else {
                if (max < res) {
                    max = res;
                }
                res = 1;
            }
        }

        if (max < res) {
            max = res;
        }

        return max;
    }

    // 1827. 最少操作使数组递增
    public int minOperations(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        int res = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                res += nums[i - 1] + 1 - nums[i];
                nums[i] = nums[i - 1] + 1;
            }
        }

        return res;
    }

    StringBuilder begin;
    StringBuilder end;

    // 面试题 17.22. 单词转换
    // 这个写法有问题，因为他并不是每一次替换都直接替换成对应的字符
    public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        LinkedList<String> list = new LinkedList<>();
        if (!wordList.contains(endWord) || !wordList.contains(beginWord)) {
            return list;
        }

        begin = new StringBuilder(beginWord);
        end = new StringBuilder(endWord);

        Character[] beginChar = new Character[beginWord.length()];
        Character[] endChar = new Character[beginWord.length()];

        // 建立每一个字符需要替换的映射
        for (int i = 0; i < beginWord.length(); i++) {
            beginChar[i] = beginWord.charAt(i);
            endChar[i] = endWord.charAt(i);
        }

        dfsMap(beginChar, endChar, list, wordList);

        return list;
    }

    void dfsMap(Character[] beginChar, Character[] endChar, LinkedList<String> list, List<String> wordList) {
        for (int i = 0; i < beginChar.length; i++) {
            // 不相等尝试替换
            if (begin.charAt(i) != end.charAt(i)) {
                System.out.println("begin = " + begin + "end = " + end);
                begin.replace(i, i + 1, String.valueOf(endChar[i]));
                // 替换完毕，如果包含，则继续往下找下一个
                if (wordList.contains(begin.toString())) {
                    list.add(begin.toString());
                    dfsMap(beginChar, endChar, list, wordList);
                } else {
                    // 如果替换完不包含，则替换回原来的
                    begin.replace(i, i + 1, String.valueOf(beginChar[i]));
                }
            }
        }
    }

    // 1691. 堆叠长方体的最大高度
    public int maxHeight(int[][] cuboids) {
        int n = cuboids.length;
        // 排序a[0] + a[1] + a[2]，因为必定会选择最长的边作为高
        for (int[] v : cuboids) {
            Arrays.sort(v);
        }

        // 根据a[0] + a[1] + a[2]的和来进行排序
        Arrays.sort(cuboids, Comparator.comparingInt(a -> (a[0] + a[1] + a[2])));

        int ans = 0;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = cuboids[i][2];
            for (int j = 0; j < i; j++) {
                // 整个这一步可以理解为选择cuboids[i][2]， 如果j小于i，那么就可以将j叠加在i上
                // 而dp[j]则记录了j的位置可以叠加的最大高度，直接加上i的高度即可。即 dp[j] + cuboids[i][2]
                // 而dp[i]则记录了当前的高度，目的是为了比较如果不能进行叠加，那么当前高度和之前高度进行比较。
                if (cuboids[i][0] >= cuboids[j][0] &&
                        cuboids[i][1] >= cuboids[j][1] &&
                        cuboids[i][2] >= cuboids[j][2]) {
                    // 这一步的选择过程，就是选择下标为i的方块，作为底部，然后从0遍历到i，判断那些可以放在i的上边
                    // 注意这里的dp[j]，j是永远小于i的，也就意味着dp[j]上的值都是之前的最大值
                    // dp[j]就是之前累加的值
                    dp[i] = Math.max(dp[i], dp[j] + cuboids[i][2]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    // LCP 62. 交通枢纽
    public int transportationHub(int[][] path) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        Map<Integer, Integer> oneToZero = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        int res = -1;
        for (int[] ints : path) {
            if (map.get(ints[1]) == null) {
                List<Integer> list = new ArrayList<>();

                // path[i][0]指向path[i][1], 以被指向的作为key
                list.add(ints[0]);

                map.put(ints[1], list);
            } else {
                map.get(ints[1]).add(ints[0]);
            }

            // 添加所有节点到set中
            set.add(ints[0]);
            set.add(ints[1]);

            // 记录每个节点指向的节点, 不需要考虑重复了替代的问题
            oneToZero.put(ints[0], ints[1]);
        }

        for (Map.Entry<Integer, List<Integer>> partMap : map.entrySet()) {
            if (partMap.getValue().size() == set.size() - 1 && oneToZero.get(partMap.getKey()) == null) {
                return partMap.getKey();
            }
        }

        return res;
    }

    // 1780. 判断一个数字是否可以表示成三的幂的和
    public boolean checkPowersOfThree(int n) {
        while (n >= 3) {
            if (n % 3 == 1) {
                n -= 1;
                n /= 3;
            } else if (n % 3 == 0) {
                n /= 3;
            } else return false;
        }

        return n == 1;
    }

    // 523. 连续的子数组和
    public boolean checkSubarraySum(int[] nums, int k) {
        int[] arr = new int[nums.length + 1];
        // 求前缀和
        arr[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            arr[i] = arr[i - 1] + nums[i - 1];
        }

        // 注意这一步：求和是否是k的倍数(arr[i] - arr[j]) % k
        // 其实如果结果为0，那么意味着arr[i] % k 等于 arr[j] % k
        // 这样我们就只用遍历一次，然后用map来记录每一个arr[i]对应的余数
        // 余数作为key，每次添加时判断一下该key是否存在，如果存在就取出然后计算下标是否满足大于等于2
        // 如果满足返回true，不满足则不做操作。这里要永远记录最远的距离，所以不需要替换掉第一次出现的
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if ((arr[i] - arr[j]) % k == 0 && i - j >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean squareIsWhite(String coordinates) {
        int[][] arr = new int[8][8];
        int mark = 0;
        for (int i = 0; i < 8; i++) {
            int first = 1 - mark;
            for (int j = 0; j < 8; j++) {
                arr[i][j] = first;
                first = 1 - first;
            }
            mark = 1 - mark;
        }

        Arrays.stream(arr).forEach(a -> {
            for (int j : a) {
                System.out.print(j);
            }
            System.out.println();
        });

        char a = coordinates.charAt(0);
        char b = coordinates.charAt(1);

        return arr[a - 'a' - 1][b - '0'] == 1;
    }

    // LCP 40. 心算挑战
    public int maxmiumScore(int[] cards, int cnt) {
        Arrays.sort(cards);

        int res = 0, length = cards.length - 1;
        for (int i = 0; i < cnt; ++i) {
            res += cards[length];
            length --;
        }
        if (res % 2 == 0) {
            return res;
        } else {
            // 依次尝试替换
            for (int i = 0; i < cnt; i++) {
                res -= cards[cards.length - cnt + i];
                for (int j = cards.length - cnt - 1; j >= 0; --j) {
                    res += cards[j];
                    if (res % 2 == 0) {
                        return res;
                    }
                    res -= cards[j];
                }
            }
        }

        return res;
    }

    // 1775. 通过最少操作次数使数组的和相等
    // 这个题问题出在只考虑变换一个数组。
    // 其实最快的方法是两个数组一起变换，多的从6变1，少的从1变6，这样最快。
    // 如果只变一个数组，会出现他可能没有6，只能从5或者4或者更小的变1，而另一个数组有1，导致次数变多。
    public int minOperations(int[] nums1, int[] nums2) {
        int max = Math.max(nums1.length, nums2.length);
        int min = Math.min(nums1.length, nums2.length);

        if (max > min * 6) {
            return -1;
        }

        int sum1 = Arrays.stream(nums1).sum();
        int sum2 = Arrays.stream(nums2).sum();

        int value;
        if  (sum1 > sum2) {
            // 长度为1需要特殊处理
            if (nums2.length == 1) {
                value = special(nums1, sum1 - sum2);
            } else {
                value = getMaxCount(nums2, sum1 - sum2);
            }
        } else {
            if (nums1.length == 1) {
                value = special(nums2, sum2 - sum1);
            } else {
                value = getMaxCount(nums1, sum2 - sum1);
            }
        }
        return value;
    }

    public int special(int[] nums1, int differ) {
        Arrays.sort(nums1);
        int count = 0;
        for (int i = nums1.length - 1; i >= 0; --i) {
            if (nums1[i] - 1 < differ) {
                count++;
                differ -= (nums1[i] - 1);
            } else {
                count++;
                break;
            }
        }
        if (differ != 0) {
            count++;
        }
        return count;
    }

    public int getMaxCount(int[] nums2, int differ) {
        int count = 0;
        Arrays.sort(nums2);
        for (int i = 0; i < nums2.length; i++) {
            if (6 - nums2[i] < differ) {
                differ -= (6 - nums2[i]);
                count++;
            } else {
                count++;
                break;
            }
        }


        return count;
    }

    // 1805. 字符串中不同整数的数目
    public int numDifferentIntegers(String word) {
        int mark = 1;
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) >= 'a' && word.charAt(i) <= 'z') {
                mark = 1;
            } else if (word.charAt(i) <= '9' && word.charAt(i) >= '0' && mark == 1) {
                String str = "";
                while (i < word.length() && word.charAt(i) <= '9' && word.charAt(i) >= '0') {
                    str += word.charAt(i);
                    i++;
                }
                i--;
                list.add(str);
                mark = 0;
            }
        }
        List<String> uniqueList = list.stream().distinct().collect(Collectors.toList());

        for (String s : uniqueList) {
            if (s.charAt(0) == '0') {
                int index = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '0') {
                        index++;
                    } else {
                        break;
                    }
                }
                String str = s.substring(index);
                map.put(str, "");
            } else {
                map.put(s, "");
            }
        }

        return map.size();
    }

    // 面试题 17.11. 单词距离
    public int findClosest(String[] words, String word1, String word2) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                list.add(i);
            }

            if (words[i].equals(word2)) {
                list1.add(i);
            }
        }
        System.out.println(list.toString());
        System.out.println(list1.toString());
        int min = 0;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (min > Math.abs(list.get(j) - list1.get(i))) {
                    min = Math.abs(list.get(j) - list1.get(i));
                }
            }
        }

        return min;
    }

    // 面试题 17.14. 最小K个数
    public int[] smallestK(int[] arr, int k) {
        Arrays.sort(arr);
        int[] returnArr = new int[k];

        int index = 0;
        for (int i = 0; i < k; i++) {
            returnArr[i] = arr[i];
        }

        return returnArr;
    }

    // 349. 两个数组的交集
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, String> map = new HashMap<>();
        int mark = 0;
        for (int i = 0; i < nums1.length; i++) {
            map.putIfAbsent(nums1[i], "1");
        }
        int[] arr = new int[map.size()];
        int index = 0;

        for (int i = 0; i < nums2.length; i++) {
            if (map.get(nums2[i]) != null) {
                arr[index++] = nums2[i];
                map.put(nums2[i], null);
            }
        }

        int[] returnArr = new int[index];
        for (int i = 0; i < index; i++) {
            returnArr[i] = arr[i];
        }
        return returnArr;
    }

    // 1774. 最接近目标价格的甜点成本
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int min = 1000000;
        int returnValue = 0;
        for (int i = 0; i < baseCosts.length; i++) {
            if (baseCosts[i] == target) {
                return target;
            }
            int sum = baseCosts[i];
            min = Math.abs(sum - target);
            returnValue = sum;
            for (int j = 0; j < toppingCosts.length; j++) {
                sum += toppingCosts[j];
                if (sum == target) {
                    return target;
                }
                // 添加第一种配料后的价格
                if (Math.abs(sum - target) < min) {
                    min = Math.abs(sum - target);
                    returnValue = sum;
                } else if (Math.abs(sum - target) == min) {
                    System.out.println("此时相等");
                    returnValue = Math.min(returnValue, sum);
                }

                for (int k = 0; k < toppingCosts.length; k++) {
                    if (k != j) {
                        sum += toppingCosts[k];
                        if (sum == target) {
                            return target;
                        }

                        if (Math.abs(sum - target) < min) {
                            min = Math.abs(sum - target);
                            returnValue = sum;
                        } else if (Math.abs(sum - target) == min) {
                            returnValue = Math.min(returnValue, sum);
                        }
                        sum -= toppingCosts[k];
                    }
                }
                sum -= toppingCosts[j];
            }
        }

        return returnValue;
    }

    // 1796. 字符串中第二大的数字
    public int secondHighest(String s) {
        int[] arr = new int[10];
        Arrays.fill(arr, -1);
        int mark = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                arr[s.charAt(i) - '0'] = s.charAt(i) - '0';
            }
        }

        for (int a : arr) {
            System.out.println(a);
        }

        for (int i = 0; i < 10; i++) {
            if (arr[i] != 0 && mark == 0) {
                mark = 1;
            } else if (mark == 1) {
                return arr[i];
            }
        }

        return -1;
    }

    // 1769. 移动所有球到每个盒子所需的最小操作数
    public int[] minOperations(String boxes) {
        int[] arr = new int[boxes.length()];
        for (int i = 0; i < boxes.length(); i++) {
            int res = 0;
            for (int j = 0; j < boxes.length(); j++) {
                if (i == j) {
                    continue;
                }

                if (boxes.charAt(j) == '1') {
                    res += Math.abs(j - i);
                }
            }

            arr[i] = res;
        }

        return arr;
    }
}
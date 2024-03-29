package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain12 {

    public static void main(String[] args) {
//        PriorityQueue<Integer> queue = new PriorityQueue<>();
//        for (int i = 0; i < 5; i++) {
//            queue.offer(i);
//        }
//        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
//        map.put(1, queue);
//
//        PriorityQueue<Integer> temp = new PriorityQueue<>();
//        temp.addAll(map.get(1));
//        while (!temp.isEmpty()) {
//            temp.poll();
//        }
//
//        PriorityQueue<Integer> temp1 = map.get(1);
//        System.out.println(temp1.size());

        String str = "last    test";
        String[] result = str.split(" ");
    }

    // 765. 情侣牵手
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        int tot = n / 2;

        List<Integer>[] graph = new List[tot];
        for (int i = 0; i < tot; i++) {
            graph[i] = new ArrayList<>();
        }

        // 想坐在一起，那么一对情侣的座位一定是一个奇数，一个偶数，两人座位都 / 2，一定是一样的
        for (int i = 0; i < n; i += 2) {
            // 这里就是先统计了目前各个情侣的座位是怎么坐的
            // 根据调换座位之前来构造一个图
            int l = row[i] / 2;
            int r = row[i + 1] / 2;
            // 如果两个数不相等，说明并不是一对情侣，就需要将他们加入图当中
            // 这其中也意味着这两个人的情侣也坐错了
            if (l != r) {
                graph[l].add(r);
                graph[r].add(l);
            }
        }

        // 这里是根据每个位置来进行遍历，而不是根据人
        boolean[] visited = new boolean[tot];
        int ret = 0;
        for (int i = 0; i < tot; i++) {
            if (!visited[i]) {
                Queue<Integer> queue = new LinkedList<>();
                visited[i] = true;
                queue.offer(i);
                int cnt = 0;

                // 这里是统计不同情侣之间环的度是多少，然后需要调换的次数就是环的度 - 1
                // 这里放入的，就是从第i个位置开始，先看当前坐在i位置的人，以及和他们相关的，所有的坐错位置的人
                // 队列每有一个元素，就意味着多一个位置的人坐错了
                while (!queue.isEmpty()) {
                    int x = queue.poll();
                    cnt += 1;

                    // 这个graph[x]的大小，并不一定是2，有多少个人交叉坐错了，这里就是多少
                    for (int y : graph[x]) {
                        if (!visited[y]) {
                            visited[y] = true;
                            queue.offer(y);
                        }
                    }
                }
                ret += cnt - 1;
            }
        }

        return ret;
    }

    // 2300. 咒语和药水的成功对数
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int[] res = new int[spells.length];
        Arrays.sort(potions);

        for (int i = 0; i < spells.length; i++) {
            res[i] = potions.length - check(potions, success, spells[i]);
        }

        return res;
    }

    public int check(int[] potions, long success, int first) {
        int left = 0, right = potions.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if ((long) first * potions[mid] >= success) {
                if (mid == 0 || (long) potions[mid - 1] * first < success) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            } else {
                if (mid == potions.length - 1 || (long) potions[mid + 1] * first >= success) {
                    return mid + 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        if (left == potions.length - 1) {
            return potions.length;
        } else {
            return 0;
        }
    }

    static final int INF = 1000000000;
    int[][] direction = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    // 2258. 逃离火灾
    public int maximumMinutes(int[][] grid) {
        int[][] fireTime = new int[grid.length][grid[0].length];
        for (int i = 0; i < fireTime.length; i++) {
            Arrays.fill(fireTime[i], INF);
        }
        bfs(grid, fireTime);
        int arriveTime = arriveTime(grid, fireTime, 0);

        // 无法到达
        if (arriveTime < 0) {
            return -1;
        }

        // 无限时间
        if (fireTime[grid.length - 1][grid[0].length - 1] == INF) {
            return INF;
        }

        int res = fireTime[grid.length - 1][grid[0].length - 1] - arriveTime;
        return arriveTime(grid, fireTime, res) >= 0 ? res : (res - 1);
    }

    public void bfs(int[][] grid, int[][] fireTime) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    fireTime[i][j] = 0;
                }
            }
        }

        int time = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                for (int j = 0; j < direction.length; j++) {
                    int x = arr[0] + direction[j][0];
                    int y = arr[1] + direction[j][1];
                    if (legal(x, y, grid)) {
                        // 这里是什么意思
                        if (fireTime[x][y] != INF) {
                            continue;
                        }
                        fireTime[x][y] = time;
                        queue.offer(new int[]{x, y});
                    }
                }
            }

            time++;
        }
    }

    public int arriveTime(int[][] grid, int[][] fireTime, int stayTime) {
        boolean[][] visit = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, stayTime});
        visit[0][0] = true;

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            for (int i = 0; i < direction.length; i++) {
                int x = arr[0] + direction[i][0];
                int y = arr[1] + direction[i][1];
                if (legal(x, y, grid)) {
                    if (!visit[x][y]) {
                        // 到达安全门
                        if (x == grid.length - 1 && y == grid[0].length - 1) {
                            // arr[2]记录的是时间
                            return arr[2] + 1;
                        }

                        // 这里是火到达x,y的时间大于人到达该位置的时间,可以入队
                        if (fireTime[x][y] > arr[2] + 1) {
                            visit[x][y] = true;
                            queue.offer(new int[]{x, y, arr[2] + 1});
                        }
                    }
                }
            }
        }

        return -1;
    }

    public boolean legal(int x, int y, int[][] board) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length && board[x][y] != 2;
    }

    int index = 0;
    // 99. 恢复二叉搜索树
    public void recoverTree(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        midDfs(root, list, 0);
        list.sort(Comparator.comparing(a -> a));
        midDfs(root, list, 1);
    }

    public void midDfs(TreeNode treeNode, List<Integer> list, int mark) {
        if (treeNode == null) {
            return;
        }

        midDfs(treeNode.left, list, mark);
        if (mark == 0) {
            list.add(treeNode.val);
        } else {
            treeNode.val = list.get(index++);
        }
        midDfs(treeNode.right, list, mark);
    }

    // 2609. 最长平衡子字符串
    public int findTheLongestBalancedSubstring(String s) {
        if (s.equals("")) {
            return 0;
        }
        int res = 0;
        int indexZero = -1, countZero = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                if (indexZero == -1) {
                    indexZero = i;
                }
                countZero++;
            } else {
                if (countZero > 0) {
                    countZero--;
                }
            }
            if (countZero == 0) {
                res = Math.max(res, i - indexZero + 1);
                indexZero = -1;
            }
        }

        return res;
    }

    // 2586. 统计范围内的元音字符串数
    public int vowelStrings(String[] words, int left, int right) {
        int res = 0;
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        for (int i = left; i <= right; i++) {
            if (set.contains(words[i].charAt(0)) && set.contains(words[i].charAt(words[i].length() - 1))) {
                res++;
            }
        }

        return res;
    }

    // 40. 组合总和 II,有问题
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            List<Integer> list = new ArrayList<>();
            dfs(list, i, candidates, target, res);
        }

        return res;
    }

    public void dfs(List<Integer> list, int index, int[] candidates, int target, List<List<Integer>> res) {
        if (index > candidates.length) {
            return;
        }

        if (target < candidates[index]) {
            return;
        }
        for (int i = index; i < candidates.length && candidates[i] < target; i++) {
            list.add(candidates[i]);
            System.out.println(target - candidates[i]);
            if (target - candidates[i] == 0) {
                res.add(new ArrayList<>(list));
                return;
            }
            dfs(list, index + 1, candidates, target - candidates[i], res);
            list.remove(list.size() - 1);
            target += candidates[i];
        }
    }

    Map<Integer, String> map = new HashMap<>();
    // 38. 外观数列
    public String countAndSay(int n) {
        map.put(1, "1");
        map.put(2, "11");
        map.put(3, "21");
        map.put(4, "1211");
        map.put(5, "111221");

        return getN(n);
    }

    public String getN(int n) {
        if (n <= 5) {
            return map.get(n);
        }

        String str = getN(n - 1);
        StringBuilder res = new StringBuilder();
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                res.append(count).append(str.charAt(i - 1));
                count = 1;
            }
        }
        res.append(count).append(str.charAt(str.length() - 1));
        return res.toString();
    }

    // 318. 最大单词长度乘积
    public int maxProduct(String[] words) {
        Arrays.sort(words, (a, b) -> b.length() - a.length());
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            Set<Character> set = new HashSet<>();
            for (int j = 0; j < words[i].length(); j++) {
                set.add(words[i].charAt(j));
            }
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].length() * words[j].length() > max) {
                    int mark = 0;
                    for (int k = 0; k < words[j].length(); k++) {
                        if (set.contains(words[j].charAt(k))) {
                            mark = 1;
                            break;
                        }
                    }
                    if (mark == 0) {
                        max = words[i].length() * words[j].length();
                        break;
                    }
                }
            }
        }

        return max;
    }

    // 43. 字符串相乘
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        List<List<Integer>> list = new ArrayList<>();
        for (int i = num1.length() - 1; i >= 0; i--) {
            List<Integer> temp = new LinkedList<>();
            int result = 0;
            int one = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int two = num2.charAt(j) - '0';
                temp.add((one * two + result) % 10);
                result = (one * two + result) / 10;
            }
            if (result > 0) {
                temp.add(result);
            }
            list.add(temp);
        }

        int length = list.get(0).size();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                list.get(i).add(0, 0);
            }
            length = Math.max(length, list.get(i).size());
        }

        StringBuilder res = new StringBuilder();
        int result = 0;
        for (int i = 0; i < length; i++) {
            int tempSum = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).size() > i) {
                    tempSum += list.get(j).get(i);
                }
            }
            tempSum += result;
            res.insert(0, tempSum % 10);
            result = tempSum / 10;
        }
        if (result > 0) {
            res.insert(0, result);
        }

        return res.toString();
    }

    // 187. 重复的DNA序列
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() < 10) {
            return res;
        }

        Set<String> set = new HashSet<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(s.charAt(i));
        }
        set.add(String.valueOf(str));

        for (int i = 10; i < s.length(); i++) {
            str.deleteCharAt(0).append(s.charAt(i));
            if (set.contains(String.valueOf(str)) && !res.contains(String.valueOf(str))) {
                res.add(String.valueOf(str));
            } else {
                set.add(String.valueOf(str));
            }
        }

        return res;
    }

    // 22. 括号生成
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }

        if (open < max) {
            cur.append('(');
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }

        if (close < open) {
            cur.append(')');
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
//    public List<String> generateParenthesis(int n) {
//        List<String> list = new ArrayList<>();
//
//        for (int i = 1; i < 2 * n; i++) {
//            StringBuilder stringBuilder = new StringBuilder("(");
//            dfs(stringBuilder, n, i, n - 1, n, list);
//        }
//        return list;
//    }
//
//    public void dfs(StringBuilder str, int n, int index, int countL, int countR, List<String> list) {
//        for (int i = index; i < 2 * n; i++) {
//            if (countL > 0) {
//                str.append("(");
//                countL--;
//            } else if (countR > 0) {
//                str.append(")");
//                countR--;
//            }
//            if (str.length() == 2 * n) {
//                list.add(str.toString());
//            }
//
//            dfs(str, n, i + 1, countL, countR, list);
//            str.deleteCharAt(str.length() - 1);
//        }
//    }

    // 最高位的二进制编号为30
    static final int HIGH_BIT = 30;

    // 421. 数组中两个数的最大异或值
    public int findMaximumXOR(int[] nums) {
        int x = 0;
        for (int k = HIGH_BIT; k >= 0; --k) {
            Set<Integer> seen = new HashSet<>();
            // 将所有的 pre^k(a_j) 放入哈希表中
            for (int num : nums) {
                // 如果只想保留从最高位开始到第 k 个二进制位为止的部分
                // 只需将其右移 k 位
                seen.add(num >> k);
            }

            // 目前 x 包含从最高位开始到第 k+1 个二进制位为止的部分
            // 我们将 x 的第 k 个二进制位置为 1，即为 x = x*2+1
            int xNext = x * 2 + 1;
            System.out.println("xNext = " + xNext);
            boolean found = false;

            // 枚举 i
            for (int num : nums) {
                if (seen.contains(xNext ^ (num >> k))) {
                    found = true;
                    break;
                }
            }

            if (found) {
                x = xNext;
            } else {
                // 如果没有找到满足等式的 a_i 和 a_j，那么 x 的第 k 个二进制位只能为 0
                // 即为 x = x*2
                x = xNext - 1;
            }
        }

        return x;
    }

    // 224. 基本计算器
//    public int calculate(String s) {
//        // 双端队列
//        Deque<Integer> stack = new LinkedList<>();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (c == ' ') {
//                continue;
//            }
//            if (c >= '0' && c <= '9') {
//                int temp = c - '0', count = 1;
//                for (int j = i + 1; j < s.length() && legal(s.charAt(j)); j++) {
//                    char tempChar = s.charAt(j);
//                    temp = (int) (temp * Math.pow(10, count++) + (tempChar - '0'));
//                }
//                stack.offerFirst(temp);
//            }
//        }
//    }

//    public boolean legal(char c) {
//        return c <= '9' && c >= '0';
//    }

    // 224. 基本计算器
    public int calculateWrong(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c != ')') {
                stack.push(c);
            } else {
                int temp = 0, count = 0, tempSum = 0;
                while (!stack.isEmpty()) {
                    char number = stack.pop();
                    if (number == '(') {
                        String str = String.valueOf(tempSum + temp);
                        for (int j = 0; j < str.length(); j++) {
                            stack.push(str.charAt(j));
                        }
                        break;
                    }

                    if (number >= '0' && number <= '9') {
                        temp += temp + (number - '0') * Math.pow(10, count);
                        count++;
                    } else if (number == '-') {
                        tempSum += Math.abs(tempSum - temp);
                        temp = 0;
                        count = 0;
                    } else {
                        tempSum += temp;
                        temp = 0;
                        count = 0;
                    }
                }
            }
        }

        int res = 0;
        while (!stack.isEmpty()) {
            char c = stack.pop();
            if (c >= '0' && c <= '9') {
                int tempSum = c - '0', count = 1;
                while (!stack.isEmpty()) {
                    char num = stack.pop();
                    if (num == '+') {
                        break;
                    } else if (num == '-') {
                        tempSum = -tempSum;
                        break;
                    } else {
                        tempSum += (num - '0') * Math.pow(10, count);
                        count++;
                    }
                }
                // 这里是处理栈中的第一个元素
                if (stack.isEmpty()) {
                    res += tempSum;
                }
            }
        }

        return res;
    }

    // 834. 树中距离之和
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        int[] res = new int[n];
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.computeIfAbsent(edge[0], k -> new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
        }

        for (int i = 0; i < n; i++) {
            res[i] = dfs(map, i);
        }

        return res;
    }

    public int dfs(Map<Integer, ArrayList<Integer>> map, int index) {
        int val = 0;
        ArrayList<Integer> list = map.get(index);
        if (list == null) {
            return 0;
        }
        val += list.size();
        for (Integer integer : list) {
            val += dfs(map, integer);
        }

        return val;
    }

    // 2103. 环和杆
    public int countPoints(String rings) {
        int res = 0;
        Map<Integer, Set<Character>> map = new HashMap<>();
        for (int i = 0; i < rings.length(); i += 2) {
            char first = rings.charAt(i);
            int second = rings.charAt(i + 1) - '0';
            map.computeIfAbsent(second, k -> new HashSet<>());

            map.get(second).add(first);
        }

        for (Map.Entry<Integer, Set<Character>> entry : map.entrySet()) {
            if (entry.getValue().size() == 3) {
                res ++;
            }
        }

        return res;
    }

    // LCR 077. 排序链表(采用归并排序)
    public ListNode sortListByMerge(ListNode head) {
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }

        // 这里是为什么
        if (head.next == tail) {
            head.next = null;
            return head;
        }

        ListNode fast = head, slow = head;
        // 这里，fast每次比slow多走一步，当fast到最后时，slow正好到中间
        // 这里不能走到最后，要走到tail，即本次分割后的队列末尾
        while (fast != tail) {
            fast = fast.next;
            slow = slow.next;

            if (fast != tail) {
                fast = fast.next;
            }
        }

        ListNode left = sortList(head, slow);
        // 这里为什么是slow，而不是slow.next
        ListNode right = sortList(slow, tail);
        ListNode sorted = merge(left, right);

        return sorted;
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode tempLeft = left, res = new ListNode(), tempRight = right, tempHead = res;
        while (tempLeft != null && tempRight != null) {
            if (tempLeft.val > tempRight.val) {
                res.next = tempRight;
                tempRight = tempRight.next;
            } else {
                res.next = tempLeft;
                tempLeft = tempLeft.next;
            }

            res = res.next;
        }

        while (tempLeft != null) {
            res.next = tempLeft;
            res = res.next;
            tempLeft = tempLeft.next;
        }

        while (tempRight != null) {
            res.next = tempRight;
            res = res.next;
            tempRight = tempRight.next;
        }

        return tempHead.next;
    }

    // 2127. 参加会议的最多员工数
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] deg = new int[n];
        for (int f : favorite) {
            // 统计基环树每个节点的入度
            deg[f]++;
        }

        // 反图,这个反图就是构建了那些入度为0的节点，存储他们指向哪个节点
        // 后续在查看环为2的时候会用到这里，需要考虑最长的链表
        List<Integer>[] rg = new List[n];
        Arrays.setAll(rg, e -> new ArrayList<>());

        // 这里，统计了所有入度为0的节点
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // deg[i]等于0，说明没有人喜欢他
            if (deg[i] == 0) {
                q.add(i);
            }
        }

        // 拓扑排序，剪掉图上所有树枝
        // 这里遍历了队列，因为队列中存储了所有入度为0的节点，所以他们不能被邀请
        while (!q.isEmpty()) {
            int x = q.poll();
            // x 只有一条出边
            int y = favorite[x];
            rg[y].add(x);
            // 如果y节点在去除一个指向它的节点后也入度也为0，加入队列
            // 这里只会去除链表一类的，如果存在环，则没法去除
            if (--deg[y] == 0) {
                q.add(y);
            }
        }

        int maxRingSize = 0, sumChainSize = 0;
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) continue;

            // 遍历基环上的点
            // 将基环上的点的入度标记为 0，避免重复访问
            deg[i] = 0;
            // 基环长度
            int ringSize = 1;
            // 在这里，当有一个i不为0的元素，它一定存在一个环中，所以这里遍历到最后一定会回到i，统计长度就可以
            for (int x = favorite[i]; x != i; x = favorite[x]) {
                // 将基环上的点的入度标记为 0，避免重复访问
                deg[x] = 0;
                ringSize++;
            }

            // 基环长度为 2
            if (ringSize == 2) {
                // 累加两条最长链的长度
                sumChainSize += rdfs(i, rg) + rdfs(favorite[i], rg);
            } else {
                // 取所有基环长度的最大值
                maxRingSize = Math.max(maxRingSize, ringSize);
            }
        }

        return Math.max(maxRingSize, sumChainSize);
    }

    // 通过反图 rg 寻找树枝上最深的链
    private int rdfs(int x, List<Integer>[] rg) {
        int maxDepth = 1;
        for (int son : rg[x]) {
            maxDepth = Math.max(maxDepth, rdfs(son, rg) + 1);
        }

        return maxDepth;
    }
    /**
     * 想到了去构建一个环，然后环大于2的情况，环的最大大小就是能参加最多的员工
     * 但是没有想到怎么去构建这个环，想到用链表，但是没法统计每个环的大小
     * 而且等于2的情况考虑的也有问题，并非简单的+1
     */
    public int maximumInvitationsWrong(int[] favorite) {
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode head = new ListNode(favorite[0]);
        int[] arr = new int[favorite.length];
        Arrays.fill(arr, 0);
        for (int i = 0; i < favorite.length; i++) {
            if (arr[i] == 1) {
                continue;
            }
            while (map.get(i) == null) {
                map.put(i, new ListNode());
            }
        }
        return 0;
    }

    // 68. 文本左右对齐
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int temp = 0, count = 0;
            // 从i开始，统计往后一行可以插入几个单词
            for (int j = i; j < words.length; j++) {
                if (temp + words[j].length() + count <= maxWidth) {
                    temp += words[j].length();
                    count++;
                } else {
                    break;
                }
            }

            int result, spaceLength;
            if (count == 1) {
                result = 0;
                spaceLength = maxWidth - temp;
            } else {
                result = (maxWidth - temp) % (count - 1);
                spaceLength = (maxWidth - temp) / (count - 1);
            }

            for (int j = i; j < i + count && j < words.length; j++) {
                stringBuilder.append(words[j]);
                if (j != i + count - 1 && j != words.length - 1) {
                    stringBuilder.append(" ".repeat(spaceLength + (result > 0 ? 1 : 0)));
                }
                result--;
            }
            stringBuilder.append(" ".repeat(Math.max(0, maxWidth - stringBuilder.length())));
            list.add(stringBuilder.toString());
            i += count - 1;
        }

        String[] last = list.get(list.size() - 1).split(" ");
        StringBuilder lastStr = new StringBuilder();
        for (String s : last) {
            if (!s.equals("")) {
                lastStr.append(s).append(" ");
            }
        }
        lastStr.deleteCharAt(lastStr.length() - 1);
        lastStr.append(" ".repeat(Math.max(0, maxWidth - lastStr.length())));
        list.set(list.size() - 1, lastStr.toString());

        return list;
    }

    // 2003. 每棵子树内缺失的最小基因值
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        List<Integer>[] children = new List[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            children[parents[i]].add(i);
        }

        int[] res = new int[n];
        Arrays.fill(res, 1);
        Set<Integer>[] geneSet = new Set[n];
        for (int i = 0; i < n; i++) {
            geneSet[i] = new HashSet<>();
        }
        dfs(0, res, nums, children, geneSet);
        return res;
    }

    public int dfs(int node, int[] res, int[] nums, List<Integer>[] children, Set<Integer>[] geneSet) {
        geneSet[node].add(nums[node]);
        for (int child : children[node]) {
            res[node] = Math.max(res[node], dfs(child, res, nums, children, geneSet));
            if (geneSet[node].size() < geneSet[child].size()) {
                Set<Integer> temp = geneSet[node];
                geneSet[node] = geneSet[child];
                geneSet[child] = temp;
            }
            geneSet[node].addAll(geneSet[child]);
        }
        while (geneSet[node].contains(res[node])) {
            res[node]++;
        }
        return res[node];
    }

    // 2003. 每棵子树内缺失的最小基因值
    public int[] smallestMissingValueSubtreeWrong(int[] parents, int[] nums) {
        int[] res = new int[parents.length];
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 1; i < parents.length; i++) {
            if (map.get(parents[i]) == null) {
                PriorityQueue<Integer> queue = new PriorityQueue<>();
                // parent[i]是该队列中所有元素共同的父节点
                map.put(parents[i], queue);
            }
            // i是对应的节点
            map.get(parents[i]).offer(i);
        }

        for (int i = 1; i < parents.length; i++) {
            PriorityQueue<Integer> queue = new PriorityQueue<>(map.get(i));

        }
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            PriorityQueue<Integer> queue = new PriorityQueue<>(map.get(entry.getKey()));

        }

        int[] arr = new int[100001];
        for (int num : nums) {
            arr[num]++;
        }

        for (int i = 1; i < parents.length; i++) {
            // 这里就是获取到第i个元素所有的子节点
            map.get(i);
        }
        return res;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {
        int first = nums.length - 2, second = nums.length - 1;
        int mark = 0, minIndex = nums.length - 1;
        // 这一步结束后，从second ~ num.length - 1必定是降序的
        while (first >= 0) {
            if (nums[first] < nums[second]) {
                mark = 1;
                break;
            }
            first--;
            second--;
        }

        if (mark == 0) {
            Arrays.sort(nums);
        } else {
            int tempLeft = second, tempRight = nums.length - 1;
            while (tempLeft < tempRight) {
                swap(nums, tempLeft, tempRight);
                tempLeft++;
                tempRight--;
            }

            for (int i = second; i < nums.length; i++) {
                if (nums[i] > nums[first]) {
                    swap(nums, i, first);
                    break;
                }
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

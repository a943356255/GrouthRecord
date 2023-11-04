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

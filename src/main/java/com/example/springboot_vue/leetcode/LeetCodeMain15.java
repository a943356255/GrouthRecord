package com.example.springboot_vue.leetcode;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class LeetCodeMain15 {

    public static void main(String[] args) {

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

    // 2583. 二叉树中的第 K 大层和
    public long kthLargestLevelSum(TreeNode root, int k) {
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root == null) {
            return -1;
        }
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            long sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            priorityQueue.offer(sum);
        }

        if (k > priorityQueue.size()) {
            return -1;
        }

        while (k > 0) {
            k--;
            System.out.println(priorityQueue.peek());
            priorityQueue.poll();
        }

        return priorityQueue.poll();
    }

    // 105. 从前序与中序遍历序列构造二叉树
    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }

    List<Integer> res = new ArrayList<>();
    // 590. N 叉树的后序遍历
    public List<Integer> postorder(NodeN root) {
        preorderAfter(root);
        return res;
    }

    public void preorderAfter(NodeN root) {
        if (root == null) {
            return;
        }

        List<NodeN> temp = root.children;
        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                preorderAfter(temp.get(i));
            }
        }
        res.add(root.val);
    }

    // 589. N 叉树的前序遍历
    public List<Integer> preorder(NodeN root) {
        preorderN(root);
        return res;
    }

    public void preorderN(NodeN root) {
        if (root == null) {
            return;
        }

        res.add(root.val);
        List<NodeN> temp = root.children;
        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                preorderN(temp.get(i));
            }
        }
    }

    // 429. N 叉树的层序遍历
    public List<List<Integer>> levelOrder(NodeN root) {
        Queue<NodeN> queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                NodeN node = queue.poll();
                temp.add(node.val);
                List<NodeN> children = node.children;
                for (int j = 0; j < children.size(); j++) {
                    if (children.get(j) != null) {
                        queue.offer(children.get(j));
                    }
                }
            }
            res.add(temp);
        }

        return res;
    }


    // 145. 二叉树的后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        afterDfs(root);
        return res;
    }

    public void afterDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        afterDfs(root.left);
        afterDfs(root.right);
        res.add(root.val);
    }

    // 144. 二叉树的前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        firstDfs(root);
        return res;
    }

    public void firstDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        res.add(root.val);
        firstDfs(root.left);
        firstDfs(root.right);
    }

    // 94. 二叉树的中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        midDfs(root);
        return res;
    }

    public void midDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        midDfs(root.left);
        res.add(root.val);
        midDfs(root.right);
    }

    // 993. 二叉树的堂兄弟节点
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root.val == x || root.val == y) {
            return false;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        TreeNode xFather = null, yFather = null;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (node.left != null) {
                        queue.add(node.left);
                        if (node.left.val == x) {
                            xFather = node;
                        }
                        if (node.left.val == y) {
                            yFather = node;
                        }
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                        if (node.right.val == x) {
                            xFather = node;
                        }
                        if (node.right.val == y) {
                            yFather = node;
                        }
                    }
                }
                System.out.println("x = " + xFather + " y = " + yFather);
            }

            // 不在同一层
            if ((xFather != null && yFather == null) || (xFather == null && yFather != null)) {
                return false;
            }

            // 相同父节点
            if (xFather == yFather && xFather != null) {
                return false;
            }

            if (xFather != null && yFather != null && xFather != yFather) {
                return true;
            }
        }

        return true;
    }

    // 2641. 二叉树的堂兄弟节点 II
    public TreeNode replaceValueInTree(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        root.val = 0;
        while (!queue.isEmpty()) {
            Queue<TreeNode> queue2 = new ArrayDeque<>();
            int sum = 0;
            for (TreeNode fa : queue) {
                if (fa.left != null) {
                    queue2.offer(fa.left);
                    sum += fa.left.val;
                }
                if (fa.right != null) {
                    queue2.offer(fa.right);
                    sum += fa.right.val;
                }
            }

            for (TreeNode fa : queue) {
                int childSum = (fa.left != null ? fa.left.val : 0) +
                        (fa.right != null ? fa.right.val : 0);
                if (fa.left != null) {
                    fa.left.val = sum - childSum;
                }
                if (fa.right != null) {
                    fa.right.val = sum - childSum;
                }
            }
            queue = queue2;
        }

        return root;
    }

    // LCP 30. 魔塔游戏
    public int magicTower(int[] nums) {
        // 存放无脑移除的元素
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        Queue<Integer> queue1 = new ArrayDeque<>();
        long blood = 1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                blood += nums[i];
            } else {
                queue.offer(nums[i]);
                // 说明需要移动
                if (blood + nums[i] <= 0) {
                    int add = queue.poll();
                    count++;
                    queue1.offer(add);
                    // 如果当前num[i]就是最大的，需要移动到末尾，那么此次就什么也不做
                    if (add != nums[i]) {
                        blood -= add;
                        blood += nums[i];
                    }
                } else {
                    blood += nums[i];
                }
            }
        }

        while (!queue1.isEmpty()) {
            blood += queue1.poll();
            if (blood <= 0) {
                return -1;
            }
        }

        return count;
    }

    // 1696. 跳跃游戏 VI
    public int maxResult(int[] nums, int k) {
//        // 下标0存放元素，下标1存放元素下标
//        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
//        // 存放元素下标
//        Set<Integer> set = new HashSet<>();
//        int[] res = new int[nums.length];
//        Arrays.fill(res, Integer.MIN_VALUE);
//        res[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            // 元素移出
//            int[] arr;
//            if (i > k) {
//                set.add(i - k);
//                arr = queue.poll();
//                while (!queue.isEmpty() && set.contains(arr[1])) {
//                    arr = queue.poll();
//                }
//            } else {
//                arr = queue.peek();
//            }
//            res[i] = arr[0] + nums[i];
//            queue.add(new int[]{res[i], i});
//        }
//        System.out.println(Arrays.toString(res));
//        return res[nums.length - 1];
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offerLast(0);
        for (int i = 1; i < n; i++) {
            while (queue.peekFirst() < i - k) {
                queue.pollFirst();
            }
            dp[i] = dp[queue.peekFirst()] + nums[i];
            while (!queue.isEmpty() && dp[queue.peekLast()] <= dp[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        return dp[n - 1];
    }

    // 292. Nim 游戏
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    // 1690. 石子游戏 VII
    public int stoneGameVII(int[] stones) {
        int sum = Arrays.stream(stones).sum();
        int alice = 0, bob = 0, left = 0, right = stones.length - 1, people = 0;
        while (left < right) {
            if (people % 2 == 0) {
                if (stones[left] > stones[right]) {
                    sum -= stones[right];
                    right--;
                } else {
                    sum -= stones[left];
                    left++;
                }
                alice += sum;
            } else {
                if (stones[left] < stones[right]) {
                    sum -= stones[right];
                    right--;
                } else {
                    sum -= stones[left];
                    left++;
                }
                bob += sum;
            }
            System.out.println("sum = " + sum);
            people = 1 - people;
        }

        System.out.println("alice = " + alice + " bob = " + bob);

        return Math.abs(alice - bob);
    }

    // 1686. 石子游戏 VI
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        int[][] values = new int[n][3];
        for (int i = 0; i < n; i++) {
            values[i][0] = aliceValues[i] + bobValues[i];
            values[i][1] = aliceValues[i];
            values[i][2] = bobValues[i];
        }
        Arrays.sort(values, (a, b) -> b[0] - a[0]);

        int aliceSum = 0, bobSum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                aliceSum += values[i][1];
            } else {
                bobSum += values[i][2];
            }
        }
        if (aliceSum > bobSum) {
            return 1;
        } else if (aliceSum == bobSum) {
            return 0;
        } else {
            return -1;
        }
    }

    // 2670. 找出不同元素数目差数组
    public int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<Integer>();
        int[] sufCnt = new int[n + 1];
        for (int i = n - 1; i > 0; i--) {
            set.add(nums[i]);
            sufCnt[i] = set.size();
        }

        int[] res = new int[n];
        set.clear();
        for (int i = 0; i < n; i++) {
            set.add(nums[i]);
            res[i] = set.size() - sufCnt[i + 1];
        }
        return res;
    }

    // 2808. 使循环数组所有元素相等的最少秒数
    public int minimumSeconds(List<Integer> nums) {
        HashMap<Integer, List<Integer>> mp = new HashMap<>();
        int n = nums.size(), res = n;
        // 这里，是将num[i]一样的数字放进同一个key，然后val是他们的下标。
        for (int i = 0; i < n; ++i) {
            mp.computeIfAbsent(nums.get(i), k -> new ArrayList<>()).add(i);
        }

        // 这里是遍历集合，找到距离最近的，就是最快的
        for (List<Integer> positions : mp.values()) {
            int mx = positions.get(0) + n - positions.get(positions.size() - 1);
            for (int i = 1; i < positions.size(); ++i) {
                mx = Math.max(mx, positions.get(i) - positions.get(i - 1));
            }
            res = Math.min(res, mx / 2);
        }
        return res;
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

package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain9 {

    public static void main(String[] args) {

    }

    private TreeNode ans;
    // 236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return ans;
    }

    public boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }

        boolean left = dfs(root.left, p, q);
        boolean right = dfs(root.right, p, q);

        // 这里，左子树和右子树都返回真
        // 或者有一个为真，并且当前根节点等于p或者q中的一个，即满足自己是自己的祖先节点
        if ((left && right) || (root.val == p.val || root.val == q.val) && (left || right)) {
            // 只会有一个ans，其他的都不会满足条件
            ans = root;
        }

        return left || right || root.val == p.val || root.val == q.val;
    }

    // 2251. 花期内花的数目
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        int[] starts = new int[flowers.length];
        int[] ends = new int[flowers.length];
        for (int i = 0; i < flowers.length; i++) {
            starts[i] = flowers[i][0];
            ends[i] = flowers[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int[] res = new int[people.length];
        for (int i = 0; i < people.length; i++) {
            res[i] = lowerBound(starts, people[i]) - lowerBound(ends, people[i]);
        }

        return res;
    }

    public int lowerBound(int[] nums, int x) {
        int left = -1, right = nums.length;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return right;
    }

    // 234. 回文链表
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (!list.get(left).equals(list.get(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // 1333. 餐厅过滤器
    public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
        Arrays.sort(restaurants, (ints, t1) -> {
            if (ints[1] != t1[1]) {
                return t1[1] - ints[1];
            } else {
                return t1[0] - ints[0];
            }
        });
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < restaurants.length; i++) {
            if (veganFriendly == 1) {
                if (restaurants[i][2] == 0) {
                    continue;
                }
            }

            if (restaurants[i][2] > maxPrice || restaurants[i][3] > maxDistance) {
                continue;
            }

            res.add(restaurants[i][0]);
        }

        return res;
    }

    // 107. 二叉树的层序遍历 II
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }

                list.add(node.val);
            }
            res.add(0, list);
        }

        return res;
    }

    // 2582. 递枕头
    public int passThePillow(int n, int time) {
        int index = 1, direction = 1;
        for (int i = 0; i < time; i++) {
            if (index == n) {
                direction = -1;
            } else if (index == 1) {
                direction = 1;
            }

            index += direction;
        }

        return index;
    }

    // 114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        List<Integer> list = new ArrayList<>();
        flattenDfs(root, list);

        TreeNode temp = root;
        for (int i = 1; i < list.size(); i++) {
            temp.right = new TreeNode(list.get(i));
            temp.left = null;
            temp = temp.right;
        }
    }

    public void flattenDfs(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        list.add(root.val);
        flattenDfs(root.left, list);
        flattenDfs(root.right, list);
    }

    // 199. 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }

                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }

        return res;
    }

    // 2591. 将钱分给最多的儿童
    public int distMoney(int money, int children) {
        if (money < children) {
            return -1;
        }

        int count = 0;
        money -= children;

        if (money > children * 7) {
            return children - 1;
        }

        count += money / 7;
        int result = money % 7;

        if (result == 3 && count == children - 1) {
            count -= 1;
        }

        return Math.min(count, children);
    }

    // 230. 二叉搜索树中第K小的元素
    public int kthSmallest(TreeNode root, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        dfsKthSmallest(root, queue);

        int res = 0;
        for (int i = 0; i < k; i++) {
            res = queue.poll();
        }
        return res;
    }

    public void dfsKthSmallest(TreeNode treeNode, PriorityQueue<Integer> queue) {
        if (treeNode == null) {
            return;
        }

        queue.add(treeNode.val);
        dfsKthSmallest(treeNode.left, queue);
        dfsKthSmallest(treeNode.right, queue);
    }

    // 36. 有效的数独
    public boolean isValidSudoku(char[][] board) {
        int[][] hang = new int[9][9];
        int[][] lie = new int[9][9];
        int[][][] map = new int[3][3][9];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int index = board[i][j] - '0' - 1;
                if (board[i][j] != '.') {
                    hang[i][index]++;
                    lie[index][j]++;
                    map[i / 3][j / 3][index]++;

                    if (hang[i][index] > 1 || lie[index][j] > 1 || map[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // 763. 划分字母区间
    // 这个题，只需要记录每个字符最后的位置就可以了，不需要记录开始位置，用一个一维数组就可以了
    public List<Integer> partitionLabels(String s) {
        Map<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) == null) {
                int[] arr = new int[2];
                arr[0] = i;
                arr[1] = i;
                map.put(c,arr);
            } else {
                map.get(c)[1] = i;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int last = map.get(c)[1], temp = i;
            while (i < last) {
                if (i == s.length()) {
                    System.out.println("temp = " + temp);
                    res.add(s.length() - temp + 1);
                    break;
                }
                char tempChar = s.charAt(i);
                last = Math.max(last, map.get(tempChar)[1]);
                i++;
            }
            System.out.println("last = " + last + "temp = " + temp);
            res.add(last - temp + 1);
        }

        return res;
    }

    // LCP 06. 拿硬币
    public int minCount(int[] coins) {
        int res = 0;
        for (int coin : coins) {
            if (coin % 2 == 0) {
                res += coin / 2;
            } else {
                res += coin / 2 + 1;
            }
        }

        return res;
    }

    // 240. 搜索二维矩阵 II
    public boolean searchMatrix(int[][] matrix, int target) {
        int indexX = 0, indexY = 0;
        if (matrix[indexX][indexY] == target) {
            return true;
        }

        while (matrix[indexX][indexY] <= target && indexY != matrix[0].length - 1) {
            indexY++;
        }

        // 这里是indexY走到最后了
        if (indexY != matrix[0].length - 1) {
            indexY--;
        }

        while (indexY >= 0) {
            if (matrix[indexX][indexY] == target) {
                return true;
            } else if (matrix[indexX][indexY] < target) {
                indexX++;
                if (indexX == matrix.length) {
                    return false;
                }
            } else if (matrix[indexX][indexY] > target) {
                indexY--;
            }
        }

        return false;
    }

    // 373. 查找和最小的 K 对数字
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int index1 = 0, index2 = 0;
        for (int i = 0; i < k; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(nums1[index1]);
            list.add(nums2[index2]);
            res.add(list);

            if (nums1[index1] > nums2[index2]) {

            }
        }

//        PriorityQueue<Integer> first = new PriorityQueue<>();
//        for (int value : nums1) {
//            first.offer(value);
//        }
//        PriorityQueue<Integer> second = new PriorityQueue<>();
//        for (int j : nums2) {
//            second.offer(j);
//        }
//
//
//        int temp1 = first.poll(), temp2 = second.poll();
//        List<Integer> a = new ArrayList<>();
//        a.add(temp1); a.add(temp2);
//        res.add(a);
//
//        for (int i = 1; i < k; i++) {
//            List<Integer> list = new ArrayList<>();
//            if (temp1 > temp2) {
//                int part = second.poll();
//            } else {
//
//            }
//
//            res.add(list);
//        }
        return res;
    }

    Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
    Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();

    // 正确的打家劫舍 III
    public int rob(TreeNode root) {
        dfs(root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        dfs(node.right);
        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }

    // 337. 打家劫舍 III，该版本有点问题
    public int robThree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        int first = root.val, second = 0, tempVal;
        if (root.left != null) {
            second += root.left.val;
            queue.add(root.left.left);
            queue.add(root.left.right);
        }
        if (root.right != null) {
            second += root.right.val;
            queue.add(root.right.left);
            queue.add(root.right.right);
        }
        second = Math.max(first, second);
        if (queue.size() == 0) {
            return second;
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            int temp = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (node.left != null) {
                        queue.add(node.left);
                    }

                    if (node.right != null) {
                        queue.add(node.right);
                    }
                    temp += node.val;
                }
            }
            tempVal = second;
            second = Math.max(first + temp, second);
            first = tempVal;
        }

        return second;
    }

    // 213. 打家劫舍 II
    public int robTwo(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int length = nums.length;
        return Math.max(robRange(0, length - 2, nums), robRange(1, length - 1, nums));
    }

    public int robRange(int start, int end, int[] nums) {
//        int[] dp = new int[nums.length];
//        Arrays.fill(dp, 0);
//        dp[0] = 0;
//        dp[1] = nums[start];
//        dp[2] = Math.max(nums[start], nums[start + 1]);
//        for (int i = start + 2; i <= end; i++) {
//            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
//        }
//        return dp[nums.length - 1];

        // 这里的first和second相当于计算了nums从start开始的两个数的较大值，相当于dp[0],dp[1]
        int first = nums[start], second = Math.max(nums[start], nums[start + 1]);
        for (int i = start + 2; i <= end; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }

        return second;
    }

    // 198. 打家劫舍
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length + 1];
        Arrays.fill(dp, 0);
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }

        return dp[nums.length];
    }

    // 6. N 字形变换
    public String convert(String s, int numRows) {
        char[][] arr = new char[numRows][s.length()];
        for (char[] chars : arr) {
            Arrays.fill(chars, '1');
        }

        int indexX = 0, indexY = 0, direction = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            arr[indexX][indexY] = c;
            indexX += direction;
            if (indexX == numRows) {
                direction = - 1;
                indexX -= 2;
                indexX = Math.max(0, indexX);
            }
            if (direction == -1) {
                indexY++;
            }
            if (indexX == 0) {
                direction = 1;
            }
        }

        StringBuilder res = new StringBuilder();
        for (char[] chars : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                if (chars[j] != '1') {
                    res.append(chars[j]);
                }
            }
        }
        return res.toString();
    }

    // LCP 50. 宝石补给
    public int giveGem(int[] gem, int[][] operations) {

        for (int[] operation : operations) {
            int temp = gem[operation[0]] / 2;
            gem[operation[0]] -= temp;
            gem[operation[1]] += temp;
        }

        int min = 100001, max = -1;
        for (int j : gem) {
            min = Math.min(min, j);
            max = Math.max(max, j);
        }

        return max - min;
    }

    // 1222. 可以攻击国王的皇后
    public List<List<Integer>> queensAttackTheKing(int[][] queens, int[] king) {
        int[][] arr = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, -1}, {-1, -1}, {1, 1}, {-1, 1}};

        Set<String> set = new HashSet<>();
        for (int i = 0; i < queens.length; i++) {
            set.add(queens[i][0] + " + " + queens[i][1]);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int x = king[0], y = king[1];
            while (isValid(x, y, 8, i, arr)) {
                x += arr[i][0];
                y += arr[i][1];
                String temp = x + " + " + y;
                if (set.contains(temp)) {
                    res.add(new ArrayList<>(Arrays.asList(x, y)));
                    break;
                }
            }
        }

        return res;
    }

    int minDepthMin = 100001;
    // 111. 二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfsMinDepth(root, 0);
        return minDepthMin + 1;
    }

    public void dfsMinDepth(TreeNode treeNode, int high) {
        if (treeNode == null) {
            return;
        }

        if (treeNode.left == null && treeNode.right == null) {
            minDepthMin = Math.min(high, minDepthMin);
            return;
        }

        high++;
        dfsMinDepth(treeNode.left, high);
        dfsMinDepth(treeNode.right, high);
    }
    
    // 2596. 检查骑士巡视方案
    public boolean checkValidGrid(int[][] grid) {
        int[][] arr = {{2, 1}, {1, 2}, {2, -1}, {1, -2}, {-2, -1}, {-1, -2}, {-2, 1}, {-1, 2}};
        if (grid[0][0] != 0) {
            return false;
        }
        int count = 0, x = 0, y = 0;
        while (count != grid.length * grid.length - 1) {
            int mark = 0;
            for (int i = 0; i < 8; i++) {
                if (isValid(x, y, grid.length, i, arr)) {
                    if (grid[x + arr[i][0]][y + arr[i][1]] == count + 1) {
                        x = x + arr[i][0];
                        y = y + arr[i][1];
                        mark = 1;
                        count++;
                        break;
                    }
                }
            }
            if (mark == 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isValid(int x, int y, int n, int i, int[][] arr) {
        if (x + arr[i][0] >= n || x + arr[i][0] < 0 || y + arr[i][1] >= n || y + arr[i][1] < 0) {
            return false;
        }

        return true;
    }

    // 1462. 课程表 IV
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<List<Integer>> edge = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edge.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            edge.get(prerequisite[1]).add(prerequisite[0]);
        }

        List<Boolean> res = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int child = queries[i][1], father = queries[i][0];
            res.add(dfs(edge, child, father));
        }
        return res;
    }

    public boolean dfs(List<List<Integer>> edge, int child, int father) {
        List<Integer> list = edge.get(child);
        if (list == null) {
            return false;
        }
        boolean result = false;
        for (Integer integer : list) {
            if (integer == father) {
                return true;
            }
            result = dfs(edge, integer, father);
        }

        return result;
    }

    // 630. 课程表 III
    public int scheduleCourse(int[][] courses) {
        // 按照课程结束时间进行排序,结束越早的越靠前
        Arrays.sort(courses, Comparator.comparingInt(a -> a[1]));

        // 优先级队列按照从大往小排序
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);
        // 优先队列中所有课程的总时间
        int total = 0;
        for (int[] course : courses) {
            // ti是课程持续时间，di是课程结束时间
            int ti = course[0], di = course[1];
            // 这里是要拿之前总的上课时间与当前课程的结束时间进行比较，如果小于当前课程的结束时间，那么就可以上
            if (total + ti <= di) {
                total += ti;
                q.offer(ti);
            } else if (!q.isEmpty() && q.peek() > ti) {
                // 这里，如果当前课程不能进行学习，那么就去判断优先级队列的最上边元素
                // 如果该课程的持续时间大于当前的课程，那么就将之前队列中的课程出队，然后总时间减去出队课程
                // 之后当前课程进入队列，作为最后一个课程
                total -= q.poll() - ti;
                q.offer(ti);
            }
        }

        return q.size();
    }

    // 849. 到最近的人的最大距离
    public int maxDistToClosest(int[] seats) {
        int res = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                list.add(i);
            }
        }
        if (list.size() == 1) {
            return Math.max(list.get(0), seats.length - list.get(0) - 1);
        }

        int left = list.get(0), right = list.get(1), index = 1;
        for (int i = 0; i < seats.length; i++) {
            if (i == right) {
                left = right;
                index ++;
                if (index < list.size()) {
                    right = list.get(index);
                }
            }
            if (seats[i] == 0) {
                if (i < left) {
                    res = Math.max(left - i, res);
                } else {
                    res = Math.max(res, Math.abs(Math.min(i - left, right - i)));
                }
            }
        }

        return res;
    }

    // 210. 课程表 II
    List<List<Integer>> list = new ArrayList<>();
    int[] visited;
    int index;
    int[] res;
    boolean valid = true;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        for (int i = 0; i < numCourses; i++) {
            list.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            list.get(prerequisite[1]).add(prerequisite[0]);
        }

        res = new int[numCourses];
        visited = new int[numCourses];
        index = numCourses - 1;

        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                dfs(i);
            }
        }

        // 如果没有环，这里是不会触发的
        if (!valid) {
            return new int[0];
        }

        return res;
    }

    public void dfs(int u) {
        // 1表示为在搜索
        visited[u] = 1;
        // 这里是遍历他的子节点
        for (int i = 0; i < list.get(u).size(); i++) {
            // 如果发现节点未遍历，则遍历
            if (visited[list.get(u).get(i)] == 0) {
                dfs(list.get(u).get(i));
                // 如果没有环，这里是不会触发的
                if (!valid) {
                    return;
                }
            } else if (visited[list.get(u).get(i)] == 1) {
                // 标记未1的说明是当前的起始顶点，后续再碰到的话，说明有环了
                valid = false;
                return;
            }
        }

        // 为2说明遍历完了
        visited[u] = 2;
        res[index--] = u;
    }

    // 2240. 买钢笔和铅笔的方案数
    public long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long res = 0;
        for (int i = 0; i < total / cost1; i++) {
            int temp = total - i * cost1;
            res += temp / cost2;
        }

        return res;
    }

    // 2651. 计算列车到站时间
    public int findDelayedArrivalTime(int arrivalTime, int delayedTime) {
        return (arrivalTime + delayedTime) % 24;
    }

    // 2594. 修车的最少时间
    public long repairCars(int[] ranks, int cars) {
        // 这里时直接计算任意一个人修全部车的时间，那么总的修车时间一定从1到该时间之内
        // 然后利用二分即可
        long l = 1, r = (long) ranks[0] * cars * cars;
        while (l < r) {
            // m是二分后的总时间
            long m = l + r >> 1;
            if (check(ranks, cars, m)) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    public boolean check(int[] ranks, int cars, long m) {
        long cnt = 0;
        for (int x : ranks) {
            // 因为计算修车时间是x * n * n，所以这里m / x再开根号就是i员工在改时间内修车的数量
            // cnt计算出了所有员工在改时间内修车的总数量
            cnt += (long) Math.sqrt(m / x);
        }
        return cnt >= cars;
    }

    // 912. 排序数组
    public int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        return nums;
    }

    private TreeNode ans1;
    private int maxDepth = -1; // 全局最大深度
    // 1123.最深叶节点的最近公共祖先
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        dfs(root, 0);
        return ans1;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 维护全局最大深度
            return depth;
        }

        // 获取左子树最深叶节点的深度
        int leftMaxDepth = dfs(node.left, depth + 1);
        // 获取右子树最深叶节点的深度
        int rightMaxDepth = dfs(node.right, depth + 1);
        // 这一步，深度遍历完又回到了根节点，如果他的左子树深度和右子树深度一样，返回改节点即可。
        // 它会一步一步的回退到最开始的根节点，每次回退都会判断一次
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth) {
            ans1 = node;
        }

        // 当前子树最深叶节点的深度
        return Math.max(leftMaxDepth, rightMaxDepth);
    }

    // 2605. 从两个数字数组里生成最小数字
    public int minNumber(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        Set<Integer> set = new HashSet<>();
        for (int j : nums2) {
            set.add(j);
        }
        int temp = Math.min(nums1[0], nums2[0]) * 10 + Math.max(nums1[0], nums2[0]);
        for (int j : nums1) {
            if (j <= temp && set.contains(j)) {
                return j;
            } else if (j > temp) {
                System.out.println(j + " " + temp);
                break;
            }
        }

        return temp;
    }

    // 1921. 消灭怪物的最大数量
    public int eliminateMaximum(int[] dist, int[] speed) {
        double[] arr = new double[dist.length];
        for (int i = 0; i < dist.length; i++) {
            arr[i] = (double) dist[i] / speed[i];
        }
        Arrays.sort(arr);

        int res = 1, count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - count > 0) {
                res++;
                count++;
            } else {
                break;
            }
        }

        return res;
    }

    // 116. 填充每个节点的下一个右侧节点指针
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node temp = queue.poll();
                if (temp != null) {
                    if (i < size - 1) {
                        temp.next = queue.peek();
                    }

                    if (temp.left != null) {
                        queue.add(temp.left);
                    }

                    if (temp.right != null) {
                        queue.add(temp.right);
                    }
                }
            }
        }

        return root;
    }

    // 823. 带因子的二叉树
    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        long[] dp = new long[n];
        long res = 0, mod = 1000000007;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            // 内层循环每次都是从0到i - 1
            for (int left = 0, right = i - 1; left <= right; left++) {
                // 这里是找到第一个满足arr[left] * arr[right]小于当前arr[i]的元素
                while (right >= left && (long) arr[left] * arr[right] > arr[i]) {
                    right--;
                }
                if (right >= left && (long) arr[left] * arr[right] == arr[i]) {
                    // 这里，是以di[i]为根节点的二叉树的个数
                    // 如果左子树不等于右子树，那么两者交换就多了一种情况，如果相等则无法交换，个数就是左子树的种类*右子树种类
                    if (right != left) {
                        dp[i] = (dp[i] + dp[left] * dp[right] * 2) % mod;
                    } else {
                        dp[i] = (dp[i] + dp[left] * dp[right]) % mod;
                    }
                }
            }
            res = (res + dp[i]) % mod;
        }

        return (int) res;
    }

    // 228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }

        StringBuilder str = new StringBuilder(String.valueOf(nums[0]));
        int length = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                length++;
            } else {
                if (length > 1) {
                    str.append("->").append(nums[i - 1]);
                }
                res.add(str.toString());
                length = 1;
                str = new StringBuilder(nums[i]);
            }
        }
        if (length > 1) {
            res.add(str.append("->").append(nums[nums.length - 1]).toString());
        } else {
            res.add(str.toString());
        }

        return res;
    }

}

package com.example.springboot_vue.leetcode;

import com.example.springboot_vue.pojo.city.City;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class LeetCodeMain17 {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

//        var list = new ArrayList<Integer>();
//        char c = 'b';
//        System.out.println(c - 'a' + 'A');
//        Class<?> tClass = City.class;
//        City[] cities = new City[5];
//        for (int i = 0; i < 5; i++) {
//            City city = new City();
//            city.setTest(String.valueOf(i));
//            cities[i] = city;
//        }
//
//        Object o = cities;
//        // 方法1
//        Object[] objects = new Object[Array.getLength(o)];
//        for (int i = 0; i < Array.getLength(o); i++) {
//            objects[i] = Array.get(o, i);
//            Method method = tClass.getDeclaredMethod("getTest");
//            System.out.println(method.invoke(objects[i]));
//        }
//
//        // 方法2
//        City[] cities1 = (City[]) o;
//        for (int i = 0; i < cities1.length; i++) {
//            System.out.println(cities1[i].getTest());
//        }
        LeetCodeMain17 leetCodeMain17 = new LeetCodeMain17();
//        leetCodeMain17.lengthOfLongestSubstring("abcabcbb");
        int[] arr = new int[]{7, 3, 1, 5, 4, 2, 6};
        leetCodeMain17.mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // 438. 找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) {
            return res;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            map.merge(c, 1, Integer::sum);
        }

        Map<Character, Integer> sMap = new HashMap<>();
        int left = 0, right = 0;
        for (int i = 0; i < p.length(); i++) {
            char c = s.charAt(i);
            sMap.merge(c, 1, Integer::sum);
            right++;
        }
        right--;
        // System.out.println(map.toString());
        while (right < s.length()) {
            // System.out.println(sMap.toString() + " left = " + left + " right = " + right);
            if (countTrue(sMap, map)) {
                res.add(left);
            }
            char c = s.charAt(left);
            sMap.put(c, sMap.get(c) - 1);

            left++;
            right++;
            if (right == s.length()) {
                break;
            }
            c = s.charAt(right);
            sMap.merge(c, 1, Integer::sum);
        }

        return res;
    }

    public boolean countTrue(Map<Character, Integer> sMap, Map<Character, Integer> pMap) {
        for (Map.Entry<Character, Integer> entry : pMap.entrySet()) {
            if (!entry.getValue().equals(sMap.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    // 128. 最长连续序列
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 1;
        for (int i : set) {
            if (!set.contains(i - 1)) {
                int temp = 0, x = i;
                while (set.contains(x++)) {
                    temp++;
                }

                res = Math.max(temp, res);
            }
        }

        return res;
    }

    public void mergeSort(int[] arr) {
        mergeSortRewrite(arr, 0, arr.length - 1);
    }

    public void mergeSortRewrite(int[] arr, int start, int end) {
        if (arr == null || start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        mergeSortRewrite(arr, start, mid);
        mergeSortRewrite(arr, mid + 1, end);

        rewriteMerge(start, end, mid, arr);
    }

    public void rewriteMerge(int start, int end, int mid, int[] arr) {
        // 这里，right 少写mid + 1
        int k = 0, left = start, right = mid + 1;
        int[] temp = new int[end - start + 1];

        while (left <= mid && right <= end) {
            if (arr[left] > arr[right]) {
                temp[k++] = arr[right++];
            } else {
                temp[k++] = arr[left++];
            }
        }

        while (left <= mid) {
            temp[k++] = arr[left++];
        }

        while (right <= end) {
            temp[k++] = arr[right++];
        }

        for (int t = 0; t < k; t++) {
            arr[t + start] = temp[t];
        }
    }

    public void trueMergeSort(int[] arr, int start, int end) {
        if (arr == null || start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        // 递归的分割左右两部分
        trueMergeSort(arr, start, mid);
        trueMergeSort(arr, mid + 1, end);

        // 将分割的数据合并
        newMerge(arr, start, end, mid);
    }

    public void newMerge(int[] arr, int start, int end, int mid) {
        int[] temp = new int[end - start + 1];
        // 这里的i是第一个数组的起始，j是第二个数组的起始，k是temp的下标
        int i = start, j = mid + 1, k = 0;

        // 这里少写等于
        while (i <= mid && j <= end) {
            if (arr[i] > arr[j]) {
                temp[k++] = arr[j++];
            } else {
                temp[k++] = arr[i++];
            }
        }

        // 这里也少些等于
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // 这里也少些等于
        while (j <= end) {
            temp[k++] = arr[j++];
        }

        for (int t = 0; t < k; t++) {
            arr[t + start] = temp[t];
        }
    }

    public void rewriteSort(int[] arr, int left, int right) {
        if (left < right) {
            int i = left, j = right, x = arr[left];
            while (i < j) {
                while (i < j && arr[j] > x) {
                    j--;
                }
                if (i < j) {
                    // 找到从右往左第一个比x小的，将其覆盖到原来i的位置，即x的值
                    arr[i++] = arr[j];
                }
                while (i < j && arr[i] < x) {
                    i++;
                }
                // 这里的j是上边找到第一个比x小的位置，因为会将x移动到j的位置，只不过没有实际移动，而是用x代替，原数组直接覆盖写
                if (i < j) {
                    // 找到从左往右第一个比x小的值，直接覆盖
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            rewriteSort(arr, left, i - 1);
            rewriteSort(arr, i + 1, right);
        }
    }

    // quickSort
    public void quickSort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public void sort(int[] arr, int left, int right) {
        if (left < right) {
            int i, j, x;
            i = left;
            j = right;
            // 这里已经用x替换了arr[i]，所以后续可以直接用覆盖写arr[i++] = arr[j]，先将arr[i]覆盖了，然后i++
            x = arr[i];

            while (i < j) {
                // 从右往左找第一个比x小的
                while (i < j && arr[j] > x) {
                    j--;
                }
                if (i < j) {
//                    arr[j--] = arr[i];
                    arr[i++] = arr[j];
                }
                // 从左往右找第一个比x大的
                while (i < j && arr[i] < x) {
                    i++;
                }
                if (i < j) {
//                    arr[i++] = arr[j];
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            sort(arr, left, i - 1);
            sort(arr, i + 1, right);
        }
    }

    // BM20 数组中的逆序对
    int count = 0;
    public int InversePairs(int [] array) {
        // 长度小于2则无逆序对
        if (array.length < 2)
            return 0;
        // 进入归并
        mergeSort(array,0,array.length - 1);
        return count;
    }

    public void mergeSort(int[] array, int left, int right) {
        // 找分割点
        int mid = left + (right - left) / 2;
        if (left < right) {
            // 左子数组
            mergeSort(array, left, mid);
            // 右子数组
            mergeSort(array, mid + 1, right);
            // 并
            merge(array, left, mid, right);
        }
    }

    // 1143. 最长公共子序列
    public int longestCommonSubsequence(String text1, String text2) {
        int res = 0;
        return 0;
    }

    public void myMerge(int[] arr, int left, int mid, int right) {
        // 这里的arr，是原始的数组，left是本次归并的左边数组的下标。mid是结束位置，right是第二个数组结束的位置
        // 临时数组
        int[] temp = new int[right - left + 1];
        int tempIndex = 0;
        int tempLeft = left, tempRight = mid + 1;
        while (tempLeft < mid && tempRight < right) {
            if (arr[tempLeft] < arr[tempRight]) {
                temp[tempIndex++] = arr[tempLeft++];
            } else {
                temp[tempIndex++] = arr[tempRight++];
//                count = (count + (mid - tempLeft + 1)) % 1000000007;
            }
        }

        while (tempLeft < mid) {
            temp[tempIndex++] = arr[tempLeft++];
        }

        while (tempRight < right) {
            temp[tempIndex++] = arr[tempRight++];
        }

        for (int i = left; i < right; i++) {
            arr[left] = temp[i];
        }
    }

    public void merge(int[] array, int left, int mid, int right) {
        // 创建临时数组，长度为此时两个子数组加起来的长度
        int[] arr =  new int[right - left + 1];
        // 临时数组的下标起点
        int c = 0;
        // 保存在原数组的起点下标值
        int s = left;
        // 左子数组的起始指针
        int l = left;
        // 右子数组的起始指针
        int r = mid + 1;
        // 这里是从两个待合并的数组头部开始遍历，然后依次选择元素加入最终数组
        while (l <= mid && r <= right) {
            // 当左子数组的当前元素小的时候，跳过，无逆序对
            if (array[l] <= array[r]) {
                // 放入临时数组
                arr[c] = array[l];
                // 临时数组下标+1
                c++;
                // 左子数组指针右移
                l++;
            } else { // 否则，此时存在逆序对
                // 放入临时数组
                arr[c] = array[r];
                // 逆序对的个数为    左子数组的终点- 当前左子数组的当前指针
                count += mid + 1 - l;
                count %= 1000000007;
                // 临时数组+1
                c++;
                // 右子数组的指针右移
                r++;
            }
        }

        // 左子数组还有元素时，全部放入临时数组
        while (l <= mid)
            arr[c++] = array[l++];
        // 右子数组还有元素时，全部放入临时数组
        while (r <= right)
            arr[c++] = array[r++];
        // 将临时数组中的元素放入到原数组的指定位置
        for (int num : arr) {
            array[s++] = num;
        }
    }

    // BM8 链表中倒数最后k个结点
    public ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        ListNode slow = pHead, fast = pHead;
        while (fast != null && k > 0) {
            k--;
            fast = fast.next;
        }
        if (k != 0) {
            return null;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // BM13 判断一个链表是否为回文结构
    public boolean isPail (ListNode head) {
        // write code here
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new ArrayDeque<>();
        while (head != null) {
            stack.push(head.val);
            queue.add(head.val);
            head = head.next;
        }
        int size = stack.size();
        for (int i = 0; i < size / 2; i++) {
            if (!stack.peek().equals(queue.peek())) {
                return false;
            }
            stack.pop();
            queue.poll();
        }

        return true;
    }

    Map<Character, Integer> minWindowMapT = new HashMap<>();
    Map<Character, Integer> minWindowMapS = new HashMap<>();
    // 76. 最小覆盖子串
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }
//        if (s.length() == t.length()) {
//            return s.equals(t) ? s : "";
//        }
        if (t.length() == 1) {
            return s.contains(t) ? t : "";
        }

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            minWindowMapT.merge(c, 1, Integer::sum);
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (minWindowMapT.containsKey(c)) {
                list.add(i);
            }
        }

        if (list.size() == 0) {
            return "";
        }

        int left = 0, right = 1, res = Integer.MAX_VALUE;
        int resLeft = 0, resRight = 0;
        minWindowMapS.put(s.charAt(list.get(0)), 1);
        int trueLeft = 0;
        int trueRight = 0;
        System.out.println(list.size());
        while (right < list.size()) {
            trueLeft = list.get(left);
            trueRight = list.get(right);
            char c = s.charAt(trueRight);
            minWindowMapS.merge(c, 1, Integer::sum);
            System.out.println(minWindowMapS.toString());
            // 检查相同
            if (check()) {
                if (res > (trueRight - trueLeft)) {
                    res = trueRight - trueLeft;
                    resLeft = trueLeft;
                    resRight = trueRight;
                }
                minWindowMapS.put(s.charAt(trueLeft), minWindowMapS.get(s.charAt(trueLeft)) - 1);
                left++;
                System.out.println("为真" + " left = " + resLeft + " right = " + resRight + " map = " + minWindowMapS.toString());
            }
            right++;
        }

        right--;
        while (left < right) {
            trueLeft = list.get(left);
            trueRight = list.get(right);
            if (check()) {
                if (res > (trueRight - trueLeft)) {
                    res = trueRight - trueLeft;
                    resLeft = trueLeft;
                    resRight = trueRight;
                }
            }
            minWindowMapS.put(s.charAt(trueLeft), minWindowMapS.get(s.charAt(trueLeft)) - 1);
            left++;
        }

        return res == Integer.MAX_VALUE ? "" : s.substring(resLeft, resRight + 1);
    }

    public boolean check() {
        for (Map.Entry<Character, Integer> characterIntegerEntry : minWindowMapT.entrySet()) {
            Character key = (Character) ((Map.Entry) characterIntegerEntry).getKey();
            Integer val = (Integer) ((Map.Entry) characterIntegerEntry).getValue();
            if (minWindowMapS.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }

    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }

        int res = -1, left = 0, right = 1;
        Set<Character> set = new HashSet<>();
        set.add(s.charAt(0));
        while (right < s.length()) {
            char c = s.charAt(right);
            if (!set.contains(c)) {
                set.add(c);
                right++;
                res = Math.max(right - left, res);
            } else {
                while (set.contains(c) && left < right) {
                    set.remove(s.charAt(left));
                    left++;
                }
                set.add(c);
                right++;
            }
        }

        res = Math.max(set.size(), res);

        return res;
    }

    // 322. 零钱兑换
    int coinChangeMark = 0;
    List<List<Integer>> coinChangeRes = new ArrayList<>();
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        Arrays.sort(coins);
        dfs(coins, 0, new LinkedList<>(), amount);
        if (coinChangeRes.size() == 0) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        for (List<Integer> coinChangeRe : coinChangeRes) {
            min = Math.min(min, coinChangeRe.size());
        }

        return min;
    }

    // 题解的记忆优化搜索，它其实也是回溯，只不过用了前一个回溯的最优解来减少当此的计算
    private int coinChange(int[] coins, int rem, int[] count) {
        if (rem < 0) {
            return -1;
        }
        if (rem == 0) {
            return 0;
        }
        if (count[rem - 1] != 0) {
            return count[rem - 1];
        }
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = coinChange(coins, rem - coin, count);
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return count[rem - 1];
    }

    // 自己写的dfs，笨的一逼,提前结束导致找到的不是最优解，不提前结束第二个测试用例超时
    public void dfs(int[] coins, long sum, LinkedList<Integer> list, int amount) {
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coinChangeMark == 1) {
                return;
            }
            if (sum + coins[i] <= amount) {
                list.add(coins[i]);
                if (sum + coins[i] == amount) {
                    coinChangeRes.add(new ArrayList<>(list));
                    coinChangeMark = 1;
                    return;
                } else {
                    dfs(coins, sum + coins[i], list, amount);
                }
                list.removeLast();
            }
        }
    }

    // LCR 076. 数组中的第 K 个最大元素
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        return nums[n - k];
    }

    // 15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    String str = nums[i] + "-" + nums[left] + "-" + nums[right];
                    if (set.contains(str)) {

                    } else {
                        set.add(str);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        res.add(list);
                        list = new ArrayList<>();
                        list.add(nums[i]);
                    }
                    left++;
                    right--;
                } else if (nums[left] + nums[right] > target) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        return res;
    }

    // 11. 盛最多水的容器
    public int maxArea(int[] height) {
        int res = 0;
        int left = 0, right = height.length - 1;
        while (left < right) {
            int min = Math.min(height[left], height[right]);
            res = Math.max(res, min * (right - left));
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }

        return res;
    }

    // 2129. 将标题首字母大写
    public String capitalizeTitle(String title) {
        StringBuilder str = new StringBuilder();
        String[] spilt = title.split(" ");

        for (int i = 0; i < spilt.length; i++) {
            if (spilt[i].length() > 2) {
                str.append(Character.toUpperCase(spilt[i].charAt(0)));
            } else {
                str.append(Character.toLowerCase(spilt[i].charAt(0)));
            }
            for (int j = 1; j < spilt[i].length(); j++) {
                char c = spilt[i].charAt(j);
                str.append(Character.toLowerCase(c));
            }
            str.append(" ");
        }
        str.delete(str.length() - 1, str.length());

        return str.toString();
    }

    // BM53 缺失的第一个正整数
    public int minNumberDisappeared (int[] nums) {
        // write code here
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        for (int i = 1; i <= nums.length; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return set.size() + 1;
    }

    // BM45 滑动窗口的最大值，纯手写板
    public ArrayList<Integer> maxInWindows (int[] num, int size) {
        if (size == 0 || size > num.length) {
            return new ArrayList<>();
        }
        if (size == num.length) {
            int max = Arrays.stream(num).max().getAsInt();
            ArrayList<Integer> list = new ArrayList<>();
            list.add(max);
            return list;
        }
        if (size == 1) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i : num) {
                list.add(i);
            }
            return list;
        }

        ArrayList<Integer> res = new ArrayList<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> (b[0] - a[0]));
        int left = 0, right = 0;
        for (int i = 0; i < size; i++) {
            queue.offer(new int[]{num[i], i});
            right = i;
        }

        res.add(queue.peek()[0]);
        while (right < num.length) {
            right++;
            if (right == num.length) {
                break;
            }
            left++;
            queue.offer(new int[]{num[right], right});
            while (!queue.isEmpty() && queue.size() > size && queue.peek()[1] < left) {
                queue.poll();
            }
            res.add(queue.peek()[0]);

        }

        return res;
    }

    ArrayList<Integer> getPath(TreeNode root, int target) {
        ArrayList<Integer> path = new ArrayList<>();
        while (root != null) {
            if (root.val != target) {
                path.add(root.val);
                if (root.val > target) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            } else {
                path.add(root.val);
                break;
            }
        }

        return path;
    }

    // 二叉树的公共祖先
    public int lowestCommonAncestor (TreeNode root, int p, int q) {
        // write code here
        ArrayList<Integer> pList = getPath(root, p);
        ArrayList<Integer> qList = getPath(root, q);
        int res = 0;
        for (int i = 0; i < pList.size() && i < qList.size(); i++) {
            int x = pList.get(i);
            int y = qList.get(i);
            System.out.println("x = " + x + " y = " + y);
            System.out.println("pList.get(i) = " + pList.get(i) + " qList.get(i) = " + qList.get(i));
            System.out.println(pList.get(i) == qList.get(i));
            if (pList.get(i).equals(qList.get(i))) {
                res = pList.get(i);
            } else {
                break;
            }
        }

        return res;
    }

    // 299. 猜数字游戏
    public String getHint(String secret, String guess) {
        int count1 = 0;
        Map<Character, Integer> secretMap = new HashMap<>();
        Map<Character, Integer> guessMap = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            char c = secret.charAt(i);
            char b = guess.charAt(i);
            if (c == b) {
                count1++;
            } else {
                secretMap.merge(c, 1, Integer::sum);
                guessMap.merge(b, 1, Integer::sum);
            }
        }

        int count = 0;
        for (Map.Entry<Character, Integer> entry : secretMap.entrySet()) {
            int first = entry.getValue();
            int second = guessMap.get(entry.getKey()) == null ? 0 : guessMap.get(entry.getKey());
            count += Math.min(first, second);
        }

        return count1 + "A" + count + "B";
    }

    // BM63 跳台阶
    public int jumpFloor (int number) {
        // write code here
        if (number < 3) {
            return number;
        }

        int[] dp = new int[number + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= number; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[number];
    }
}

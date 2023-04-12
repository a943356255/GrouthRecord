package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain {

    public static void main(String[] args) {
        LeetCodeMain leetCodeMain = new LeetCodeMain();
        String[] arr = {"42","10","O","t","y","p","g","B","96","H","5","v","P","52","25","96","b","L","Y","z","d","52","3","v","71","J","A","0","v","51","E","k","H","96","21","W","59","I","V","s","59","w","X","33","29","H","32","51","f","i","58","56","66","90","F","10","93","53","85","28","78","d","67","81","T","K","S","l","L","Z","j","5","R","b","44","R","h","B","30","63","z","75","60","m","61","a","5","S","Z","D","2","A","W","k","84","44","96","96","y","M"};
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, 2)));
        System.out.println(Arrays.toString(leetCodeMain.findLongestSubarray(arr)));
    }

    // 面试题 17.05.  字母与数字
    public String[] findLongestSubarray(String[] array) {
        String str = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        int[] arr = new int[array.length];
        // 字母为-1
        if (str.contains(array[0])) {
            arr[0] = -1;
        } else {
            arr[0] = 1;
        }
        for (int i = 1; i < array.length; i++) {
            // 是字母
            if (str.contains(array[i])) {
                arr[i] = arr[i - 1] - 1;
            } else {
                arr[i] = arr[i - 1] + 1;
            }
        }
        int max = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            if (arr[i] == 0) {
                // 记录目前的最大值，0~i
                max = i + 1;
                break;
            }
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (!map.containsKey(arr[i])) {
                // 记录arr[i]的值以及对应的下标
                map.put(arr[i], i);
            }
        }

        int start = 0, end = max - 1, temp;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (map.get(arr[i]) != null) {
                temp = i - map.get(arr[i]);
                if (temp >= max) {
                    if (temp == max) {
                        if (start > map.get(arr[i]) + 1) {
                            start = map.get(arr[i]) + 1;
                            end = i;
                            max = temp;
                        }
                    } else {
                        start = map.get(arr[i]) + 1;
                        end = i;
                        max = temp;
                    }
                }
            }
        }

        if (end == 0) {
            return null;
        }
        return Arrays.copyOfRange(array, start, end + 1);
    }

    // 1779. 找到最近的有相同 X 或 Y 坐标的点
    public int nearestValidPoint(int x, int y, int[][] points) {
        // 记录返回值
        int min = -1;
        // 记录曼哈顿距离
        int abs = 10000000;
        // 记录上一个下标
        int index = -1;
        for (int i = 0; i < points.length; i++) {
            if (points[i][0] == x && points[i][1] == y) {
                return 0;
            }
            int part = 0;
            if (points[i][0] == x || points[i][1] == y) {
                part = Math.abs(x - points[i][0]) + Math.abs(y - points[i][1]);
                if (part < abs) {
                    abs = part;
                    index = i;
                } else if (part == abs) {
                    if (index != -1) {
                        if (points[i][0] < points[index][0]) {
                            min = points[i][0];
                            index = i;
                        } else {
                            min = points[index][0];
                        }
                    }
                }
            }
        }

        return min;
    }

    // 290. 单词规律
    public boolean wordPattern(String pattern, String s) {
        Map<Character, String> map = new HashMap<>();
        Map<String, Character> strMap = new HashMap<>();
        String[] arr = s.split(" ");

        if (arr.length != pattern.length()) {
            return false;
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (map.get(pattern.charAt(i)) == null) {
                if (strMap.get(arr[i]) == null) {
                    map.put(pattern.charAt(i), arr[i]);
                    strMap.put(arr[i], pattern.charAt(i));
                } else {
                    return false;
                }
            } else {
                if (!map.get(pattern.charAt(i)).equals(arr[i])) {
                    //  || !strMap.get(arr[i]).equals(pattern.charAt(i))
                    return false;
                }
            }
        }

        return true;
    }

    // 4. 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double res;
        int total = nums1.length + nums2.length;
        int[] nums = new int[total];
        int index = 0;
        int index1 = 0, index2 = 0;
        while (index1 != nums1.length && index2 != nums2.length) {

            if (nums1[index1] < nums2[index2]) {
                nums[index] = nums1[index1];
                index1++;
            } else {
                nums[index] = nums2[index2];
                index2++;
            }
            index++;
        }

        while (index1 != nums1.length) {
            nums[index++] = nums1[index1++];
        }

        while (index2 != nums2.length) {
            nums[index++] = nums1[index2++];
        }

        for (int a : nums) {
            System.out.println(a);
        }

        if (total % 2 == 0) {
            res = (nums[total / 2] + nums[total / 2 - 1]) / 2.0;
        } else {
            res = nums[total / 2 + 1];
        }

        return res;
    }

    // 1758. 生成交替二进制字符串的最少操作数
    public int minOperations(String s) {
        char[] arr = new char[2];
        arr[0] = '0'; arr[1] = '1';
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            str1.append(arr[index]);
            str2.append(arr[1 - index]);
            index = 1 - index;
        }

        int count1 = 0, count2 = 0;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != s.charAt(i)) {
                count1++;
            } else {
                count2++;
            }
        }

        return Math.min(count1, count2);
    }

    // 813. 最大平均值和的分组
    public double largestSumOfAverages(int[] nums, int k) {
        int[] arr = new int[nums.length + 1];
        arr[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            arr[i] = arr[i - 1] + nums[i - 1];
        }
        int[][] dp = new int[k][];

        double sum = 0.0;
        return sum;
    }

    // 1752. 检查数组是否经排序和轮转得到
    public boolean check(int[] nums) {
        int num = 101, index = 0;
        int[] arr = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = nums[i];
            if (nums[i] < num) {
                num = nums[i];
                index = i;
            }
        }

        Arrays.sort(arr);
        for (int i = 0; i < nums.length; i++) {
            if (arr[i] != nums[(index + i) % nums.length]) {
                return false;
            }
        }
        return true;
    }

    // 面试题45. 把数组排成最小的数
    public String minNumber(int[] nums) {
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = nums[i] + "";
        }

        Arrays.sort(strings, (s1, s2) -> (s1 + s2).compareTo(s2 + s1));

        StringBuilder str = new StringBuilder();
        for (String string : strings) {
            str.append(string);
        }

        return str.toString();
    }

    // 面试题61. 扑克牌中的顺子
    public boolean isStraight(int[] nums) {
        int[] arr = new int[14];
        int countZero = 0;
        int min = 15;
        for (int num : nums) {
            if (num != 0 && num < min) {
                min = num;
            }
            if (num != 0) {
                arr[num] = 1;
            }
            if (num == 0) {
                countZero++;
            }
        }

        int target = min;
        if (min > 9) {
            for (int i = target - 1; i >= 9 && countZero > 0; i--) {
                countZero--;
                arr[i] = 1;
                min--;
            }
        }

        for (int i = min; i < min + 5; i++) {
            if (arr[i] == 0) {
                if (countZero <= 0) {
                    return false;
                } else {
                    countZero --;
                }
            }
        }

        return true;
    }

    // 809. 情感丰富的文字
    public int expressiveWords(String s, String[] words) {
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == null) {
                map.put(s.charAt(i), 1);
            } else {
                int value = map.get(s.charAt(i));
                map.put(s.charAt(i), value + 1);
            }
        }

        for (int i = 0; i < words.length; i++) {
            int[] count = new int[26];
            int mark = 0;
            for (int j = 0; j < words[i].length(); j++) {
                // 如果有一个字符不存在，直接结束内层循环
                if (map.get(words[i].charAt(j)) == null || words[i].length() > s.length()) {
                    mark = 1;
                    break;
                }
                count[words[i].charAt(j) - 'a']++;
            }

            int another = 0;
            // 如果有不存在的，这边直接不统计
            if (mark == 0) {
                int value;
                char key;
                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    key = entry.getKey();
                    value = entry.getValue();

                    // 不存在，直接结束
                    if (count[key - 'a'] == 0) {
                        another = 1;
                        break;
                    }
                    if (value < 3) {
                        if (count[entry.getKey() - 'a'] != entry.getValue()) {
                            another = 1;
                            break;
                        }
                    } else {
                        if (value < count[key - 'a']) {
                            another = 1;
                            break;
                        }
                    }
                }
            }

            if (mark == 0 && another == 0) {
                res ++;
            }
        }

        return res;
    }

    // 795. 区间子数组个数 这个题可以重复
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int count = 0;
        List<Integer> continueNum = new ArrayList<>();
        int mark = 0;
        for (int num : nums) {
            if (num <= right) {
                mark++;
            } else {
                continueNum.add(mark);
                mark = 0;
            }
        }

        if (mark != 0) {
            continueNum.add(mark);
        }

        // 这里统计的过程，和题解的思路是一致的。只不过题解在上边求连续子数组长度的时候就统计完了。
        for (int i = 0; i < continueNum.size(); i++) {
//            System.out.println(continueNum.get(i));
            for (int j = 1; j <= continueNum.get(i); j++) {
                count += j;
            }
        }
        return count;
    }

    // 1742. 盒子中小球的最大数量
    public int countBalls(int lowLimit, int highLimit) {
        int max = 0;
        int[] arr = new int[46];
        for (int i = 0; i <= 46; i++) {
            arr[i] = 0;
        }

        for (int i = lowLimit; i <= highLimit; i++) {
            if (i < 10) {
                arr[i]++;
            } else {
                arr[getSum(i)]++;
            }
        }

        for (int i = 0; i <= 46; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    int getSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }

    // 剑指 Offer II 007. 数组中和为 0 的三个数
    // -4 -1 -1 0 1 2
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int length = nums.length;
        List<List<Integer>> list = new ArrayList<>();

        for (int i = 0; i < length; ++i) {
            // nums[i] > 0，则后边不可能存在和为0的
            if (nums[i] > 0) {
                break;
            }

            // i循环枚举的是第一个数字，如果第一个数字与上一个数字重复，那么直接跳过（因为排过序，所以只要与上一个不重复，就不会重复）
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            // 一开始这个third放错位置，导致时间比较多
            int thirdIndex = length - 1;
            for (int j = i + 1; j < length; ++j) {
                // j 作为第二个元素，也不可以重复
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                while (thirdIndex > j && nums[thirdIndex] + nums[j] > target) {
                    --thirdIndex;
                }

                if (thirdIndex == j) {
                    break;
                }

                if (nums[thirdIndex] + nums[j] == target) {
                    List<Integer> value = new ArrayList<>();
                    value.add(nums[i]);
                    value.add(nums[j]);
                    value.add(nums[thirdIndex]);
                    list.add(value);
                }
            }
        }

        return list;
    }

    // 剑指 Offer 04. 二维数组中的查找
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // LCS 01. 下载插件
    public int leastMinutes(int n) {

        int res = 0, download = 1;
        while (n > 0) {
            if (download < n) {
                download *= 2;
            } else {
                n -= download;
            }
            res ++;
        }

        return res;
    }

    // 1732. Find the Highest Altitude
    public int largestAltitude(int[] gain) {
        int max = gain[0], sum = gain[0];
        for (int i = 1; i < gain.length; i++) {
            sum += gain[i];
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }

    // 792. 匹配子序列的单词数
    public int numMatchingSubseq(String s, String[] words) {
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; ++i) {
            pos[i] = new ArrayList<Integer>();
        }
        // 这一步，pos一共26个位置，每一个下标存储了一个字母，因为同一个字母可能有多个，每一个list存储的是他们的下标
        // 比如  abcdefaaa 那么pos[0]这个list 存储的就是 0, 6, 7, 8
        for (int i = 0; i < s.length(); ++i) {
            pos[s.charAt(i) - 'a'].add(i);
        }
        // res代表了一共有几个单词
        int res = words.length;
        for (String w : words) {
            if (w.length() > s.length()) {
                // 上边的不满足，说明有一个不存在
                --res;
                continue;
            }
            int p = -1;
            for (int i = 0; i < w.length(); ++i) {
                char c = w.charAt(i);
                // pos[c - 'a'].get(pos[c - 'a'].size() - 1) 这一个是最大下标，也就是最后一个元素的下标
//                System.out.println("pos = " + pos[c - 'a'].get(pos[c - 'a'].size() - 1) + " " + "P = " + p);
                if (pos[c - 'a'].isEmpty() || pos[c - 'a'].get(pos[c - 'a'].size() - 1) <= p) {
                    --res;
                    break;
                }
                // 上边的pos[c - 'a'].get(pos[c - 'a'].size() - 1) <= p
                // 可能在这一步二分查找时，他查找的是大于当前下标的下一个元素 。
                p = binarySearch(pos[c - 'a'], p);
            }
        }
        return res;
    }

    public int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return list.get(left);
    }

    // 775. 全局倒置与局部倒置
    public boolean isIdealPermutation(int[] nums) {

        if (nums.length == 2) {
            if (nums[0] < nums[1]) {
                return true;
            }
        }

        int part = 0, all = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i != nums.length - 1) {
                if (nums[i] > nums[i + 1]) {
                    part++;
                }
            }

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    all++;
                }
            }
//            if (i < nums.length / 2) {
//                int big = 0;
//                for (int j = 0; j < i; j++) {
//                    if (nums[j] > nums[i]) {
//                        big++;
//                    }
//                }
//
//                all += (nums.length - 1 - big - nums[i]);
//            } else {
//                int small = 0;
//
//
//                all += small;
//            }
        }

        return part == all;
    }

    // 1710. 卡车上的最大单元数
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (i, j) -> j[1] - i[1]);

        int res = 0;
        for (int i = 0; i < boxTypes.length; i++) {
            if (boxTypes[i][0] < truckSize) {
                res += boxTypes[i][0] * boxTypes[i][1];
                truckSize -= boxTypes[i][0];
            } else {
                res += boxTypes[i][1] * truckSize;
                break;
            }
        }

        return res;
    }

    // 416. 分割等和子集
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        if (maxNum > target) {
            return false;
        }
        boolean[][] dp = new boolean[n][target + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;

        // 这里进行了相应的转化，把他转为0-1背包问题。
        for (int i = 1; i < n; i++) {
            int num = nums[i];
            for (int j = 1; j <= target; j++) {
                if (j >= num) {
                    // 这个dp[i - 1][j - num], 表示去上边一行去寻找不包过此时num的值是否为true
                    // 例如此时num = 3， j = 5， 去dp[i-1][2]找是否为true，如果有，那么说明存在和为2的值，加3正好等于5，所以dp[i][5]的值为true
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - num];
                } else {
                    // j < num 说明此时num是不能放入的，直接取上一行的值即可。
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][target];
    }

    // 面试题 01.01. 判定字符是否唯一
    public boolean isUnique(String str) {
        int[] arr = new int[27];

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (arr[c - 'a'] == 1) {
                return false;
            } else {
                arr[c - 'a'] = 1;
            }
        }

        return true;
    }

    // 面试题 01.03. URL化
    public String replaceSpaces(String S, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = S.charAt(i);
            if (c != ' ') {
                stringBuilder.append(c);
            } else {
                stringBuilder.append("%20");
            }
        }

        return stringBuilder.toString();
    }

    // 791. 自定义字符串排序
    public String customSortString(String order, String s) {
        Map<Character, Integer> map = new HashMap<>();
        int res = 0, length = s.length();

        for (int i = 0; i < order.length(); i++) {
            map.put(order.charAt(i), res);
            res ++;
        }

        char[] characters = new char[s.length()];
        for (int i = 0; i < length; i++) {
            characters[i] = s.charAt(i);
        }

        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (map.get(characters[i]) == null || map.get(characters[j]) == null) {
                    continue;
                }
                if (map.get(characters[i]) > map.get(characters[j])) {
                    char c = characters[i];
                    characters[i] = characters[j];
                    characters[j] = c;
                }
            }
        }

        return String.copyValueOf(characters);
    }

    // 790. 多米诺和托米诺平铺
    static final int MOD = 1000000007;

    public int numTilings(int n) {
        int[][] dp = new int[n + 1][4];
        dp[0][3] = 1;
        for (int i = 1; i <= n; i++) {
            // 0状态，意味着新增的一列都不用(带上新增一共i列)，那么可以平铺的方法应该和之前的一种一样。也就是说总列数是(i-1),但是完全铺满
            dp[i][0] = dp[i - 1][3];
            // 这种情况对应的是新增的一列只用上边的那个方块，dp[i-1][0]对应的状态是i-1这一列以及i这一列都为空的状态
            // 而此时要统计第i列上边方块被用，所以要找i-1这一列上边方块没有被使用的状态。只有这样才能确保第i列上方方块用得上。
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            // 这种情况与上边相似
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD;
            // 此时全部用完，把上边三种情况相加即可。
            dp[i][3] = (((dp[i - 1][0] + dp[i - 1][1]) % MOD + dp[i - 1][2]) % MOD + dp[i - 1][3]) % MOD;
        }
        return dp[n][3];
    }

    // 1704. 判断字符串的两半是否相似
    public boolean halvesAreAlike(String s) {
        char[] arr = {'a', 'e', 'i', 'o','u','A','E','I','O','U'};
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }

        int length = s.length();
        int first = 0, second = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (list.contains(c)) {
                if (i < length / 2) {
                    first++;
                } else {
                    second++;
                }
            }
        }

        return first == second;
    }

    // 864. 获取所有钥匙的最短路径
    public int shortestPathAllKeys(String[] grid) {
        int res = 0;
        List<Character> keyList = new ArrayList<>();
        List<Character> lockList = new ArrayList<>();

        char[][] charGrid = new char[grid.length][];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length(); j++) {

            }
        }

        return res;
    }

    boolean hasKey(List<Character> list, char c) {
        return list.contains(c);
    }

    // 354. 俄罗斯套娃信封问题
    public int maxEnvelopes(int[][] envelopes) {
        int res, max = 0, markIndex, change = 0;
        Arrays.sort(envelopes, (arr1, arr2) -> arr1[0] == arr2[0] ? arr1[1] - arr2[1] : arr1[0] - arr2[0]);
        for (int i = 0; i < envelopes.length; i++) {
            System.out.println(Arrays.toString(envelopes[i]));
        }
        for (int i = 0; i < envelopes.length; i++) {
            res = 1;
            markIndex = i;
            for (int j = i + 1; j < envelopes.length; j++) {
                if (envelopes[j][0] > envelopes[markIndex][0] && envelopes[j][1] > envelopes[markIndex][1]) {
                    res ++;
                    markIndex = j;
                    change = 1;
                }
                System.out.println(res);
            }
            if (max < res) {
                max = res;
            }
        }

        if (change == 0) {
            return 0;
        }

        return max;
    }

    // 764. 最大加号标志
    public int orderOfLargestPlusSign(int n, int[][] mines) {

        return 0;
    }

    public String rollback = "";

    // 1684. 统计一致字符串的数目
    public int countConsistentStrings(String allowed, String[] words) {
        int count = 0;
        char[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < allowed.length(); i++) {
            char c = allowed.charAt(i);
            arr[c - 'a'] = '0';
        }

        for (int i = 0; i < words.length; i++) {
            int mark = 0;
            for (int j = 0; j < words[i].length(); j++) {
                if (arr[words[i].charAt(j)] != '0') {
                    mark = 1;
                    break;
                }
            }
            if (mark == 0) {
                count++;
            }
        }

        return count;
    }

    // 816. 模糊坐标
    public List<String> ambiguousCoordinates(String s) {
        rollback = s;

        return new ArrayList<>();
    }

    void dfs(String s, int index) {

    }

    // 1678. 设计 Goal 解析器
    public String interpret(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        int mark = 0;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '(') {
                i++;
                if (command.charAt(i) == ')') {
                    mark = 1;
                }
            }
            if (mark == 1) {
                stringBuilder.append('o');
                mark = 0;
            } else {
                if (command.charAt(i) != ')') {
                    stringBuilder.append(command.charAt(i));
                }
            }
        }

        return stringBuilder.toString();
    }

    // 1106. 解析布尔表达式
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<>();
        int countT = 0, countF = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c != ')') {
                stack.push(c);
            } else {
                // 找到括号中间的所有元素
                while (!stack.empty() && stack.peek() != '(') {
                    char s = stack.pop();
                    if (s == 'f') {
                        countF++;
                    } else if (s == 't') {
                        countT++;
                    }
                }
                // 弹出左括号
                stack.pop();
                // 获取当前括号前的计算符
                char mark = stack.pop();
                if (mark == '&') {
                    if (countF > 0) {
                        stack.push('f');
                    } else {
                        stack.push('t');
                    }
                } else if (mark == '|'){
                    if (countT > 0) {
                        stack.push('t');
                    } else {
                        stack.push('f');
                    }
                } else {
                    if (countF > 0) {
                        stack.push('t');
                    } else {
                        stack.push('f');
                    }
                }
                countF = countT = 0;
            }
        }
        char result = stack.peek();

        return result == 't';
    }

    // 到达终点数字
    public int reachNumber(int target) {
        int index = 0, numMoves = 1;

        while (index != target) {
            if (index < target && index + numMoves < target) {
                index += numMoves;
            } else {
                index -= numMoves;
            }
            numMoves++;
        }

        return numMoves;
    }


    // 1668. 最大重复子字符串
    public int maxRepeating(String sequence, String word) {
        int max = 0, count = 1;
        String str = word;
        while (word.length() < sequence.length()) {
            word += str;
            count++;
            if (sequence.contains(word)) {
                max = count;
            }
        }

        return max;
    }

    // 1620. 网络信号最好的坐标
    public int[] bestCoordinate(int[][] towers, int radius) {
        int[] arr = new int[2];

        for (int i = 0; i < towers.length; i++) {
            
        }

        return arr;
    }

    // 1662. 检查两个字符串数组是否相等
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        String str1 = null, str2 = null;
        for (int i = 0; i < word1.length; i++) {
            str1 += word1[i];
        }
        for (int i = 0; i < word2.length; i++) {
            str2 += word2[2];
        }

        return str1.equals(str2);
    }

    // 481. 神奇字符串
    public int magicalString(int n) {
        int count = 0;
        String str = "1221121221221121122";
        if (n < str.length()) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '1') {
                    count++;
                }
            }
            return count;
        }

        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 1; i <= n; i++) {

        }

        return count;
    }

    List<String> returnList = new ArrayList<>();

    public List<String> letterCasePermutation(String s) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                list.add(i);
            }
        }

        StringBuilder stringBuilder = new StringBuilder(s);

        strDfs(stringBuilder, 0, list);

        return returnList;
    }

    char change(char c) {
        //如果输入的是大写，+32即可得到小写
        if (c >= 'A' && c <= 'Z') {
            c += 32;
        } else if (c >= 'a' && c <= 'z') {    //如果输入的是小写，-32即可得大小写
            c -= 32;
        }
        return c;
    }

    void strDfs(StringBuilder s, int index, List<Integer> list) {
        if (index == list.size() - 1) {
            return;
        }

        for (int i = index; i < list.size(); i++) {
            s.setCharAt(i, change(s.charAt(i)));
            returnList.add(s.toString());
            strDfs(s, index + 1, list);
            s.setCharAt(i, s.charAt(i));
        }
    }
}
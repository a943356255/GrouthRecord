package com.example.springboot_vue.leetcode;

import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        String str = "123";
        System.out.println(str.charAt(3));
//        int[] test = IntStream.range(0, 10).toArray();
//        int[] arr = {1, 1, 0, 0};
//        int[] arr1 = {0, 1, 0, 1};

//        Main main = new Main();
//        main.countStudents(arr, arr1);
//        String[] arr = {"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"};
//        List<String> list = main.subdomainVisits(arr);

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
    }

    // 915. 分割数组
    public int partitionDisjoint(int[] nums) {
        int left = 0, val = nums[0];
        int length = nums.length;
        int[] leftMin = new int[length];
        int[] rightMax = new int[length];

        // 统计从左往右每一个下标的最小值
        for (int i = 0; i < length; i++) {
            if (val > nums[i]) {
                val = nums[i];
            }
            leftMin[i] = val;
        }

        // 统计从右往左每一个下标区间的最大值
        val = nums[length - 1];
        for (int i = length - 1; i >= 0; i--) {
            if (val < nums[i]) {
                val = nums[i];
            }
            rightMax[i] = val;
        }

        for (int i = 0; i < length; i++) {
            if (leftMin[i] < rightMax[i]) {
                left = i + 1;
                break;
            }
        }

        return left;
    }

    // 1768. 交替合并字符串
    public String mergeAlternately(String word1, String word2) {
        StringBuilder stringBuilder = new StringBuilder();
        int index1 = 0, index2 = 0, length1 = word1.length(), length2 = word2.length();
//        while (word1.charAt(index1) != null)
        while (index1 < length1 && index2 < length2) {
            stringBuilder.append(word1.charAt(index1++));
            stringBuilder.append(word2.charAt(index2++));
        }

        while (index1 < length1) {
            stringBuilder.append(word1.charAt(index1++));
        }

        while (index2 < length2) {
            stringBuilder.append(word2.charAt(index2++));
        }

        return stringBuilder.toString();
    }

    // 1700. 无法吃午餐的学生数量
    public int countStudents(int[] students, int[] sandwiches) {

//        Queue<Integer> queue = new LinkedList<>();
//        Stack<Integer> stack = new Stack<>();
//
//        for (int i = 0; i < students.length; i++) {
//            queue.add(students[i]);
//            stack.push(sandwiches[students.length - i - 1]);
//        }
//
//        int res = 0;

        int count = 0;
        int stuNum = students.length, wait = 0, index = 0, stuIndex = 0;
        while (stuNum > 0) {
            if (students[stuIndex] == -1) {
                count++;
                if (count == students.length) {
                    break;
                }
                stuIndex = (stuIndex + 1) % students.length;
                continue;
            }
            if (students[stuIndex] == sandwiches[index]) {
                students[stuIndex] = -1;
                stuNum--;
                index++;
                wait = 0;
            } else {
                wait++;
            }
            System.out.println(students[stuIndex] + " " + sandwiches[index]);
            stuIndex = (stuIndex + 1) % students.length;
            if (wait == stuNum) {
                break;
            }
        }

        return stuNum;
    }

    // 886. 可能的二分法
    public boolean possibleBipartition(int n, int[][] dislikes) {
        int length = dislikes.length;
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            if ((first.contains(dislikes[i][0]) && first.contains(dislikes[i][1])) || (second.contains(dislikes[i][1]) && second.contains(dislikes[i][0]))) {
                return false;
            } else {
                if ((!first.contains(dislikes[i][0]) && !first.contains(dislikes[i][1])) && (!second.contains(dislikes[i][0]) && !second.contains(dislikes[i][1]))) {
                    for (int j = i + 1; j < length; j++) {
                        if (dislikes[j][0] == dislikes[i][0] || dislikes[j][0] == dislikes[i][1] || dislikes[j][1] == dislikes[i][0] || dislikes[j][1] == dislikes[i][1]) {
                            if (first.contains(dislikes[j][0]) && !second.contains(dislikes[j][1])) {

                            }
                        }
                    }

                    first.add(dislikes[i][0]);
                    second.add(dislikes[i][1]);
                } else {
                    // 这里说明dislikes中必有一个数已经包含于first或second
                    if (first.contains(dislikes[i][0])) {
                        second.add(dislikes[i][1]);
                    } else if (first.contains(dislikes[i][1])) {
                        second.add(dislikes[i][0]);
                    } else {
                        // 进入这个else，说明first并不包含此次数据中的任意一个，这时候由second判断。
                        if (second.contains(dislikes[i][0])) {
                            first.add(dislikes[i][1]);
                        } else if (second.contains(dislikes[i][1])) {
                            first.add(dislikes[i][0]);
                        }
                    }
                }
            }
        }

        return true;
    }

    // 1441. Build an Array With Stack Operations Push Pop
    public List<String> buildArray(int[] target, int n) {
        int count = 1, index = 0;
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (target[index] == i) {
                list.add("Push");
                index++;
            } else if (target[index] > i) {
                list.add("Push");
                list.add("Pop");
            } else {
                break;
            }
        }

        return list;
    }

    // 769. 最多能完成排序的块
    public int maxChunksToSorted(int[] arr) {
        int count = 1;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.empty()) {
                if (stack.peek() < arr[i]) {
                    stack.pop();
                    count++;
                } else {
                    break;
                }
            }
            stack.push(arr[i]);
        }

        return count;
    }

    // 817. 链表组件
    public int numComponents(ListNode head, int[] nums) {
        int res = 0, count = 0, index = 0, max = 0;
        int[] arr = new int[10001];
        while (head != null) {
            arr[count++] = head.val;
            head = head.next;
        }

        return res;
    }

    // 1790. 仅执行一次字符串交换能否使两个字符串相等
    public boolean areAlmostEqual(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int index1 = 0, index2 = 0, count = 0;
        char last = ' ';
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (count == 0) {
                    index1 = i;
                } else if (count == 1) {
                    index2 = i;
                }
                count++;

                if (count > 2) {
                    return false;
                }
            }
        }

        if (count == 1 || s2.charAt(index2) != s1.charAt(index1)) {
            return false;
        }
        return true;
    }

    // 这一题，我的想法错了。入栈的并不应该是括号，应该是计算出分数后再入栈，而不是直接将括号入栈然后再计算分数
    public int scoreOfParentheses(String s) {
        int res = 0, length = s.length(), multiplicationTime = 0;
        int[] stack = new int[length + 1];

        int index = -1;
        char lastChar = ' ';
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (lastChar == '(') {
                    multiplicationTime ++;
                } else if (lastChar == ')') {

                }
                stack[++index] = c;
            } else {
                if (lastChar == ')') {

                } else {

                }
            }
            lastChar = c;
        }

        return res;
    }

    // 870. 优势洗牌
//    public int[] advantageCount(int[] nums1, int[] nums2) {
//
//        for (int i = 0; i < nums2.length; i++) {
//            int lastBig = 100000, index = 0;
//            for (int j = 0; j < nums1.length; j++) {
//                if (nums1[j] > nums2[i]) {
//                    int subtractValue = nums1[j] - nums2[i];
//                    if (subtractValue < lastBig) {
//                        lastBig = subtractValue;
//                        index = j;
//                    }
//                }
//            }
//            swap(nums1, index, i);
//            System.out.println(index);
//        }
//
//        return nums1;
//    }

    // 870. 优势洗牌
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] ans = new int[n];
        Arrays.sort(nums1);
        // 通过stream流创建一个0~n的数组，不好含n
        Integer[] ids = IntStream.range(0, n).boxed().toArray(Integer[]::new);

        // 这里是根据nums[2]来对idx数组进行排序
        Arrays.sort(ids, Comparator.comparingInt(i -> nums2[i]));
        int left = 0, right = n - 1;

        // 这里边其实就是比较。两个排完序的数组，如果nums[1]的值大于nums[2]，直接添加就可以
        // 如果小于，那么就把nums[2]的最大值与当前值组成一对。（因为排过序，当前小于，那么后续不可能存在比当前nums[i]更小的元素）
        for (int x : nums1) {
            ans[x > nums2[ids[left]] ? ids[left++] : ids[right--]] = x;
        }
        return ans;
    }

    void swap(int[] arr, int first, int second) {
        int val = arr[first];
        arr[first] = arr[second];
        arr[second] = val;
    }

    // 1800. 最大升序子数组和
    public int maxAscendingSum(int[] nums) {
        int sum = nums[0], max = 0, lastValue = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                sum += nums[i];
            } else {
                max = Math.max(sum, max);
                sum = nums[i];
            }
        }

        max = Math.max(sum, max);

        return max;
    }

    // 927. 三等分
//    public int[] threeEqualParts(int[] arr) {
//        int left = 0, right = left + 1;
//        for (int i = 0; i < arr.length; i++) {
//            int sum = 0;
//            for (int j = 0; j < arr.length; j++) {
//
//            }
//        }
//    }

    // 811. 子域名访问计数
    public List<String> subdomainVisits(String[] cpdomains) {
        ArrayList<String> returnList = new ArrayList<>();

        for (String cpdomain : cpdomains) {
            String[] splitResult = cpdomain.split(" ");
            String[] pointSplitResult = splitResult[1].split("\\.");

            StringBuilder lastStr = new StringBuilder();
            // 其实在这里遍历的时候可以直接使用hash表来存储最终的结果，这样就避免全部存储完后再遍历累加得到结果
            for (int j = pointSplitResult.length - 1; j >= 0; j--) {
                if (j == pointSplitResult.length - 1) {
                    lastStr.append(pointSplitResult[j]);
                } else {
                    lastStr.insert(0, ".");
                    lastStr.insert(0, pointSplitResult[j]);
                }
                returnList.add(splitResult[0] + " " + lastStr);
            }
        }

        Map<String, Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        for (String s : returnList) {
            String[] strings = s.split(" ");
            if (map.get(strings[1]) == null) {
                map.put(strings[1], Integer.valueOf(strings[0]));
            } else {
                int value = map.get(strings[1]);
                value += Integer.parseInt(strings[0]);
                map.put(strings[1], value);
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String str = entry.getValue() + " " + entry.getKey();
            list.add(str);
        }

        return list;
    }

    // 921. 使括号有效的最少添加
    public int minAddToMakeValid(String s) {
        if (s.equals("")) {
            return 2;
        }
        // index = 0不用
        char[] charArr = new char[s.length() + 1];

        int count = 0, index = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i), stackChar = charArr[index - 1];
            if (stackChar == '(' || stackChar == ')') {
                if (c == ')' && stackChar == '(') {
                    index--;
                } else {
                    charArr[index++] = c;
                }
            } else {
                charArr[index++] = c;
            }
        }

        for (int i = 0; i < index; i++) {
            if (charArr[i] == '(' || charArr[i]  == ')') {
                count++;
            }
        }

        return count;
    }

    // 1784. 检查二进制字符串字段
    public boolean checkOnesSegment(String s) {
        int last = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                if (last == 0) {
                    count++;
                    last = 1;
                }
            } else {
                last = 0;
            }
        }

        return count <= 1;
    }

    // 777. 在LR字符串中交换相邻字符, 这里的写法有问题，没办法通过所有的测试用例
    public boolean canTransform(String start, String end) {

        StringBuilder str = new StringBuilder(start);

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            char b = ' ';
            if (i != str.length() - 1) {
                b = str.charAt(i + 1);
            }
            if (c != end.charAt(i)) {
                if (((c == 'X' && b == 'L') || (c == 'R' && b == 'X'))) {
                    str.setCharAt(i, b);
                    str.setCharAt(i + 1, c);
                }
            }
            if (str.charAt(i) == end.charAt(i)) {
                count++;
            }
        }

        return count == start.length();
    }

    // 1694. 重新格式化电话号码
    public String reformatNumber(String number) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder returnStr = new StringBuilder();

        int count = 0, markCount = 0, index;
        for (index = 0; index < number.length(); index++) {
            char c = number.charAt(index);
            if (c >= '0' && c <= '9') {
                stringBuilder.append(c);
                count++;
            }
        }

        if (count == 4) {
            returnStr.append(stringBuilder.charAt(0));
            returnStr.append(stringBuilder.charAt(1));
            returnStr.append('-');
            returnStr.append(stringBuilder.charAt(2));
            returnStr.append(stringBuilder.charAt(3));
            return returnStr.toString();
        }

        for (int i = 0; i < stringBuilder.length(); i++) {
            returnStr.append(stringBuilder.charAt(i));
            markCount++;
            count--;

            if (markCount == 3) {
                if (count != 0) {
                    returnStr.append('-');
                }
                markCount = 0;
                if (count <= 4) {
                    break;
                }
            }
        }

        int part = 0;
        if (count % 2 == 0)  {
            for (int i = index; i < stringBuilder.length(); i++) {
                returnStr.append(stringBuilder.charAt(i));
                part++;
                if (part == 2) {
                    part = 0;
                    returnStr.append('-');
                }
            }
        } else {
            for (int i = index; i < stringBuilder.length(); i++) {
                returnStr.append(stringBuilder.charAt(i));
            }
        }

        return returnStr.toString();
    }

    // 面试题 01.08. 零矩阵
    public void setZeroes(int[][] matrix) {
        int hang = matrix.length, lie = matrix[0].length;
        ArrayList<Integer> hangList = new ArrayList<>();
        ArrayList<Integer> lieList = new ArrayList<>();
        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < lie; j++) {
                if (matrix[i][j] == 0) {
                    hangList.add(i);
                    lieList.add(j);
                }
            }
        }

        for (int i = 0; i < hangList.size(); i++) {
            for (int j = 0; j < lie; j++) {
                matrix[hangList.get(i)][j] = 0;
            }
        }

        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < lieList.size(); j++) {
                matrix[i][lieList.get(j)] = 0;
            }
        }
    }

    // 面试题 01.09. 字符串轮转
    public boolean isFlipedString(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++) {
            int lastIndex = i;
            for (int j = 0; j < s2.length(); j++) {

            }
        }

        return true;
    }

    // 面试题 17.09. 第 k 个数
    public int getKthMagicNumber(int k) {
        return 1;
    }

    // 面试题 01.02. 判定是否互为字符重排
    public boolean CheckPermutation(String s1, String s2) {

        int[] map = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            map[c - 'a']++;
        }

        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            if (map[c - 'a'] == 0) {
                return false;
            } else {
                map[c - 'a']--;
            }
        }

        for (int i = 0; i < map.length; i++) {
            if (map[i] < 0) {
                return false;
            }
        }

        return true;
    }

    // 面试题 17.19. 消失的两个数字
    public int[] missingTwo(int[] nums) {
        Arrays.sort(nums);
        int[] returnArr = new int[2];

        int count = 0, N = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != N) {
                returnArr[count] = N;
                i--;
                count++;
                System.out.println(i);
            }
            N++;
            if (count == 2) {
                break;
            }
        }

        if (count == 1) {
            returnArr[1] = nums[nums.length - 1] + 1;
        }

        if (count == 0) {
            returnArr[0] = nums[nums.length - 1] + 1;
            returnArr[1] = returnArr[0] + 1;
        }

        return returnArr;
    }

    // 788. 旋转数字
    public int rotatedDigits(int n) {
        int count = 0;
        ArrayList<Character> list = new ArrayList<>();
        list.add('1');
        list.add('0');
        list.add('8');

        ArrayList<Character> list1 = new ArrayList<>();
        list1.add('5');
        list1.add('2');
        list1.add('6');
        list1.add('9');

        for (int i = 2; i <= n; i++) {
            int mark = 0;
            String num = String.valueOf(i);
            for (int j = 0; j < num.length(); j++) {
                char character = num.charAt(j);
                if (!list.contains(character) && !list1.contains(character)) {
                    mark = 0;
                    break;
                } else if (list1.contains(character)) {
                    mark = 1;
                }
            }
            if (mark == 1) {
                System.out.println(num);
                count++;
            }
        }

        return count;
    }

    // 1640. 能否连接形成数组
    public boolean canFormArray(int[] arr, int[][] pieces) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int[] piece : pieces) {
                if (piece[0] == arr[i]) {
                    count++;
                    for (int k = 1; k < piece.length; k++) {
                        i++;
                        if (i == arr.length) {
                            return count == arr.length;
                        }
                        if (arr[i] == piece[k]) {
                            count++;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return count == arr.length;
    }

    // 854. 相似度为 K 的字符串 困难，不会做
    public int kSimilarity(String s1, String s2) {
        return 0;
    }

    // 672. 灯泡开关 Ⅱ
    public int flipLights(int n, int presses) {


        return 0;
    }

    // 670. 最大交换
    public int maximumSwap(int num) {

        char[] charArray = String.valueOf(num).toCharArray();
        int n = charArray.length;
        int maxIdx = n - 1;
        int idx1 = -1, idx2 = -1;
        for (int i = n - 1; i >= 0; i--) {
            // 如果当前的index比之前记录的maxIndex大，就进行交换，更替maxIndex
            if (charArray[i] > charArray[maxIdx]) {
                maxIdx = i;
                // 注意这里的不满足，如果当前index的值小于最大，那么就更改替换的index，因为idx1越接近左侧，交换出来的值就越大
                // 而maxIndex则是从右开始，则会记录最右边的最大值。
            } else if (charArray[i] < charArray[maxIdx]) {
                idx1 = i;
                idx2 = maxIdx;
            }
        }
        if (idx1 >= 0) {
            swap(charArray, idx1, idx2);
            return Integer.parseInt(new String(charArray));
        } else {
            return num;
        }


//        int maxIndex = -1, minIndex = -1;
//        String strNum = String.valueOf(num);
//        char max = '0', min = '9';
//
//        for (int i = 0; i < strNum.length(); i++) {
//            char c = strNum.charAt(i);
//            if (max < c) {
//                max = c;
//                maxIndex = i;
//            }
//            if (min > c) {
//                min = c;
//                minIndex = i;
//            }
//        }
//
//        String returnStr = "";
//        returnStr += strNum.charAt(maxIndex);
//        for (int i = 1; i < strNum.length(); i++) {
//            if (i == maxIndex) {
//                returnStr += strNum.charAt(minIndex);
//            } else {
//                returnStr += strNum.charAt(i);
//            }
//        }
//        return Integer.parseInt(returnStr);
    }

    public void swap(char[] charArray, int i, int j) {
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    // 669. 修剪二叉搜索树
    public TreeNode trimBST(TreeNode root, int low, int high) {

        return root;
    }

    // 1598. 文件夹操作日志搜集器
    public int minOperations(String[] logs) {
        int index = 0;
        for(int i = 0; i < logs.length; i++) {
            if (logs[i].equals("../")) {
                index = index > 0 ? --index : 0;
            } else if (logs[i].equals("./")) {

            } else {
                index++;
            }
        }

        return index;
    }

    // 1592 重新排列单词间的空格
    public String reorderSpaces(String text) {
        int countSpace = 0, countWords = 0;
        String str = "";

        int mark = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                mark = i;
                break;
            } else {
                countSpace++;
            }
        }

        int last = 0; // 0代表字符
        for (int i = mark; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (last == 0) {
                    countWords++;
                    last = 1;
                }
                countSpace++;
            } else {
                if (last == 1) {
                    last = 0;
                }
            }
        }

        if (text.charAt(text.length() - 1) != ' ') {
            countWords++;
        }

        int num = countSpace / (countWords - 1);
        int result = countSpace % (countWords - 1);
        String spaceNum = "";
        for (int i = 0; i < num; i++) {
            spaceNum += ' ';
        }

        last = 0;
        int resultWords = countWords;
        for (int i = mark; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != ' ') {
                str += c;
                last = 0;
            } else {
                if (last == 0) {
                    countWords--;
                    if (countWords != 0) {
                        str += spaceNum;
                        last = 1;
                    }
                }
            }
        }

        for (int i = 0; i < result; i++) {
            str += ' ';
        }

        return str;
    }

    // 394字符串解码,有问题
    public String decodeString(String s) {
        String str = "";
        Stack<Character> stack = new Stack<>();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                count++;
            } else if (c == ']'){
                stack.pop();
            } else {
                stack.push(c);

            }
        }

        return str;
    }

    // 652寻找重复子树
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        return null;
    }

    /**
     * 虽然写对了，但是设计方面有问题，用了两个map来记录，还需要用到map.containsKey，提高了用时
     * 答案写法是用数组代替了这个map，因为mat[i][j]的值只能是0或1，直接相加也能达到统计1的个数的效果。
     * 因为忽略了这一点，所以导致用时较高，其他的想法都是一致的
     * @param mat
     * @return
     */
    // 1582
    public int numSpecial(int[][] mat) {
        int count = 0;
        int rows = mat.length, cols = mat[0].length;

        Map<Integer, Integer> mapI = new HashMap<>();
        Map<Integer, Integer> mapJ = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 1) {
                    insert(mapI, i);
                    insert(mapJ, j);
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            int countI;
            if (mapI.containsKey(i)) {
                countI = mapI.get(i);
                if (countI >= 2) {
                    continue;
                }
            }
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 1) {
                    if (mapJ.get(j) == 1) {
                        count++;
                        System.out.println("i = " + i + "j = " + j);
                    }
                }
            }
        }

        return count;
    }

    public void insert(Map<Integer, Integer> map, int index) {
        if (map.containsKey(index)) {
            int value = map.get(index);
            value++;
            map.put(index, value);
        } else {
            map.put(index, 1);
        }
    }

    /**
     * 这个题一开始想到暴力需要双重循环，但是忽略了如果直接暴力，没办法确定后续第一个最接近它的那个值
     * 动态规划则是那每一次的i值去和后续的所有j进行对比
     * 假如数据是【1，2 】 【3，4】  【5，6】  【7，8】
     * 他这里的思路是i在前边，j是用来判断当前j是否可以添加到i的前边，i才是那个c，d
     * 而dp[j]取到的都是前几次更新过后的dp[i]
     * @param pairs
     * @return
     *
     * 答案解析还有一种贪心 + 二分查找的方法，它需要排序
     * 而且他每次只有直接add时才会更新arr的大小，否则都只是更新了arr最后一位的值
     * 然后用于接下来的判断。而且取值是取较小的一个。
     */
    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[i][0] > pairs[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n - 1];
    }

    int max = 0, mark = 0, count = 1;
    public int longestUnivaluePath(TreeNode root) {
        dfs(root, root.val);
        return max;
    }

    public void dfs(TreeNode root, int val) {
        if (root == null) {
            return;
        }

        if (root.val == val) {
            count++;
        } else {
            if (count > max) {
                max = count;
            }
            count = 1;
        }

        dfs(root.left, root.val);
        dfs(root.right, root.val);
    }
    
    /**
     * 这个题还可以用单调栈，倒着遍历数组，一边往栈里添加元素（栈里的元素是从小到大，即栈顶存最大），一边遍历数组
     * 如果栈为空，则说明不能打折，如果栈不为空，则从栈顶开始找第一个小于当前元素的值，就是改商品的打折量。
     * 因为是从右往左遍历，所以栈中元素一定是当前元素右边的元素
     * 又是单调栈，最大元素在上边，所以找到的第一个比当前元素小的元素就是它右边第一个比他小的。
     * @param prices
     * @return
     */
    public int[] finalPrices(int[] prices) {
        int length = prices.length;
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            int mark = 0;
            for (int j = i + 1; j < length; j++) {
                if (prices[j] < prices[i]) {
                    arr[i] = prices[i] - prices[j];
                    mark = 1;
                    break;
                }
            }
            if (mark == 0) {
               arr[i] = prices[i];
            }
        }

        return arr;
    }

    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        dfs(root, val);
        return root;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>();

        int length = arr.length, left = -1, right = 0;
        for (int i = 1; i < length; i++) {
            if (arr[i] >= x) {
                left = i - 1;
                right = i;
                break;
            }
        }

        System.out.println("left = " + left + " " + " right = " + right);

        if (left == -1) {
            left = length - 1;
            right = length;
        }

        int count = 0;
        while (count != k) {
            count++;
            if (left == 0 && right != length) {
                right++;
                continue;
            }

            if (right == length && left != 0) {
                left--;
                continue;
            }

            if (Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                left--;
            } else {
                right++;
            }

        }

        System.out.println(count);

        System.out.println("left = " + left + " " + " right = " + right);

        for (int i = left; i < right - 1; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    public static int maxScore(int[] cardPoints, int k) {

        int sum = Arrays.stream(cardPoints).sum();

        int result = 0, length = cardPoints.length - k;
        System.out.println("length = " + length);
        for (int i = 0; i < length && i < cardPoints.length; i++) {
            result += cardPoints[i];
        }

        System.out.println("第一个result" + result);

        int min = result;
        for (int i = k; i < cardPoints.length - length; i++) {
            result = result - cardPoints[i - k] + cardPoints[i];
            System.out.println(i + "个" + result);
            if (result < min) {
                min = result;
            }
        }

        return sum - min;
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth) {

        depth -= 2;
        if (depth == 0) {
            TreeNode node = new TreeNode(val);
            TreeNode node1 = new TreeNode(val);
            node.left = root.left;
            root.left = node;
            node1.right = root.right;
            root.right = node1;
        }

        if (depth == -1) {
            TreeNode node = new TreeNode(val);
            node.left = root;
            return node;
        }

        dfs(root.left, val, depth);
        dfs(root.right, val, depth);
        return root;
    }

    public void dfs(TreeNode root, int val, int depth) {

        depth -= 1;
        if (root == null || depth < 0) {
            return;
        }

        if (depth == 0) {
            TreeNode node = new TreeNode(val);
            TreeNode node1 = new TreeNode(val);
            node.left = root.left;
            root.left = node;
            node1.right = root.right;
            root.right = node1;
        }

        dfs(root.left, val, depth);
        dfs(root.right, val, depth);
    }

    // AcWing 53 最小的k个整数 优先级队列，取前k个返回
    public static List<Integer> myPriorityQueen(int[] input, int k) {

        List<Integer> list = new ArrayList<>();

        if (input.length == 0) {
            return null;
        }
        if (input.length == 1) {
            list.add(input[0]);
            return list;
        }

        list.add(input[0]);
        int index = 0;
        for (int i = 1; i < input.length; i++) {
            int num = input[i];
            list.add(num);
            while (num < list.get(index)) {
                int part = list.get(index);
                list.set(index, num);
                list.set(index + 1, part);
                index--;
                if (index < 0) {
                    break;
                }
            }

            index = Math.min(i, k-1);
            if (list.size() > k) {
                list.remove(index + 1);
            }
        }

        return list;
    }

    // 力扣 592，没做出来
    public static String fractionAddition(String expression) {
        int mark = 0, index = 0;
        String first = "", second = "";
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) != '+') {
                if (mark == 0) {
                    first += expression.charAt(i);
                } else {
                    second += expression.charAt(i);
                }
            } else if (expression.charAt(i) == '+' && mark == 0) {
                mark = 1;
            } else if ((expression.charAt(i) == '+' || expression.charAt(i) == '-' || i == expression.length() - 1) && mark == 1) {
                index = i;
                first = getResult(first, second);
                break;
            }
        }
        System.out.println(first + " " + second);
        second = null;
        for (int i = index; i < expression.length(); i++) {
            if (expression.charAt(i) != '+' || expression.charAt(i) != '-') {
                second += expression.charAt(i);
            } else {
                first = getResult(first, second);
                if (expression.charAt(i) == '-') {
                    i--;
                }
            }
        }

        return first;
    }

    public static String getResult(String first, String second) {

        System.out.println(first + " " + second + " 这里是进行加法");

        String[] firstValue = first.split("/");
        String[] secondValue = second.split("/");

        int firstChildren = Integer.parseInt(firstValue[0]);
        int firstParent = Integer.parseInt(firstValue[1]);

        int secondChildren = Integer.parseInt(secondValue[0]);
        int secondParent = Integer.parseInt(secondValue[1]);
        int value;
        if (firstParent == secondParent) {
            value = firstChildren + secondChildren;
            if (value == 0) {
                return value + "/" + "1";
            }
            return value + "/" + firstParent;
        } else {
            firstChildren *= secondParent;
            secondChildren *= firstParent;
            value = firstChildren + secondChildren;
            if (value == 0) {
                return value + "/" + "1";
            }
            return value + "/" + firstParent * secondParent;
        }
    }

    // 合并k个升序链表
    public ListNode mergeKLists(ListNode[] lists) {

        if (lists.length == 0) {
            return null;
        }

        ListNode listNode = new ListNode();
        ListNode returnNode = new ListNode();
        returnNode.next = listNode;

        int length = lists.length, mark = 1, count = 0, index = -1, lastValue = 1000000;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            if (lists[i] == null) {
                arr[i] = 1;
                count++;
            } else {
                arr[i] = 0;
                index = i;
            }
        }

        if (index == -1) {
            return null;
        }

        while (mark == 1) {
            lastValue = lists[index].val;
            for (int i = 0; i < lists.length; i++) {
                // 等于1说明已经到达最后
                if (arr[i] == 1 || lists[i] == null) {
                    continue;
                }
                if (lastValue > lists[i].val) {
                    lastValue = lists[i].val;
                    index = i;
                }
            }
            listNode.next = new ListNode(lastValue);
            listNode = listNode.next;

            lists[index] = lists[index].next;
            if (lists[index] == null) {
                arr[index] = 1;
                count++;
                for (int i = 0; i < length; i++) {
                    if (arr[i] != 1) {
                        index = i;
                        break;
                    }
                }
            }

            if (count == length) {
                mark = 0;
            }
        }

        return returnNode.next.next;
    }
}

package com.example.springboot_vue.acwing;

import java.io.BufferedInputStream;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int n = scanner.nextInt();
        String m = scanner.nextLine();

        int[] arr = {2, 5, 3, 5, 3, 2, 23, 23, 4};
        main.difference(arr);
    }

    // 模拟哈希表
    public void myHash() {

    }

    // 堆 下标从1开始，用一个1维数组来代表。例如下标index，那么2 * index 就是他的左儿子，2 * index + 1是他的右儿子
    public void deep() {

    }

    // 将堆顶的元素挪到堆尾 这里是将数组下标为index的元素调整到堆尾  这里是一个小顶堆
    void down(int[] deep, int index) {
        int length = deep.length;

        // 利用t来标记原下标
        int t = index;

        // 计算左边是否小于t，如果小于，则进行交换
        if (index * 2 <= length && deep[index * 2] < deep[t]) {
            t = index * 2;
        }

        // 计算右子树是否小于t
        if (index * 2 + 1 <= length && deep[index * 2 + 1] < deep[t]) {
            t = index * 2 + 1;
        }

        // 上边的那两步，第一步计算如果满足，那么会更改t的值，接下来去走第二个if，这个时候拿值判断是 deep[index * 2 + 1] < deep[t]
        // 也就是说此时deep[t]的值是index节点的左子树，当满足第一个if时，第二个if的作用就会是判断左右子树哪一个更小，然后取更小的那个进行交换。

        // 说明存在子节点小于父节点
        if (index != t) {
            swap(deep, index, t);
            down(deep, t);
        }

    }

    // 将堆尾的元素挪到堆顶部
    void up() {

    }

    // 并查集
    public void DSU(int[] arr) {
        int a = 1, b = 2;
        // 合并a和b所在的集合
        {
            // 这里的思路就是找到a所在的根节点，然后将其父节点设置为b所在集合的父节点
            arr[findDSU(a, arr)] = findDSU(b, arr);
        }
        if (findDSU(a, arr) == findDSU(b, arr)) {
            System.out.println("a 和 b 在同一个集合当中");
        }
    }

    int findDSU(int x, int[] arr) {
        // 并查集当中，x表示元素，arr[x]表示x元素的父节点，所以这里可以递归的寻找，arr[x] = x是规定的根节点
        // 如果不是根节点，则令x等于arr[x]，意味着向上找一层。
        // 例如1是根节点，2，3是他的子节点。x等于2时，arr[2]等于1，所以当令x = arr[2]即x = 1时，就相当于向上走了一层。
        if (x != arr[x]) {
            arr[x] = findDSU(arr[x], arr);
        }

        return arr[x];
    }

    // Trie字符串统计。
    // 这个arr是存储单词的记录。这里每一个单词的每一个字母并不会出现在arr当中的同一行，每个单词的首字母必定出现在第一行。
    public void trieTest(int[][] arr, int n, String str) {
        // 这个cnt是一个标记位，如果有数字，则表示已经存在单词。注意cnt每次存储的数字都是字母，表示以该字母结尾存在。
        int[] cnt = new int[n];
        int idx = 0;
        // 插入
        {
//            int p = 0;
//            for (int i = 0; i < str.length(); i++) {
//                int u = str.charAt(i) - 'a';
//                // 这里的判断是是否有字符，如果没有就新增，如果有其实可以理解为直接跳过。那一步p = arr[p][u] 只有在单词结束的时候才有用，其他时刻都没有用。
//                if (arr[p][u] != 0) {
//                    arr[p][u] = ++idx;
//                }
//                // p存储的并不是字母，而是idx的值。idx的值一直在+1，是模拟树向下一层。
//                p = arr[p][u];
//            }
            // 这个cnt只有在循环结束的时候才会统计添加一次。也就是说没插入一个单词，cnt会更新一次。
//            cnt[p]++;
        }

        // 查询
        {
            int p = 0;
            for (int i = 0; i < str.length(); i++) {
                int u = str.charAt(i) - 'a';
                if (arr[p][u] == 0) {
                    // 如果这里出现0，说明该位置没有字符，一定不存在该单词。（因为在存储的数据中，没有该字母，必定不可能有该单词）
                    return;
                }
                p = arr[p][u];
            }

            // 这里会打印出该单词出现了几次。
            System.out.println(cnt[p]);
        }
    }

    // 滑动窗口(单调队列)
    public void maxValueInTheWindow(int[] arr, int k) {
        // 注意这里的优先级队列，它并不存储arr中的数，而是存储对应元素的下标。
        int[] priorityQueue = new int[arr.length];
        int windowHead = 0, windowTail = -1;
        for (int i = 0; i < arr.length; i++) {
            // 这里是队列中有元素，同时它没有超出k的范围。
            // 要注意queue中存储的是下标，可以根据队头元素和当前i来直接判断出他是否超出了k这个滑动窗口的范围，超出将他移除队列即可。
            // 这里移除队列不需要显示的remove，head的下标加1就相当于取不到该元素。
            if (windowHead <= windowTail && priorityQueue[windowHead] < i - k + 1) {
                windowHead++;
            }
            // 这里是判断队列有元素，同时取出当前队尾元素，来与当前arr[i]的值进行对比，如果当前arr小于队列最大值，那么就把最大值移除队列，
            // 一直到当前元素小于队列中的值，然后加入队列。
            // 一直维护这个队列，可以让取出当前窗口最小值的复杂度为o(1)
            while (windowHead <= windowTail && arr[priorityQueue[windowTail]] >= arr[i]) {
                windowTail--;
            }
            // 找到合适位置，放入当前下标
            priorityQueue[++windowHead] = i;
        }
    }

    // 单调栈
    public void singleStack(int[] arr) {
        int[] stack = new int[arr.length + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            while (index > 0 && stack[index] >= arr[i]) {
                index--;
            }
            stack[index] = arr[i];
            index++;
        }
    }

    // 链表 k是要插入哪个节点的下标
    public void linkArray(int num, int k) {
        // 这里存放数据，即链表中每个节点的数据
        int[] value = new int[30];
        // 这里存放当前节点的下一个节点时那里。
        int[] link = new int[30];
        // 例如 1->2->3->4
        // 那么value中存的值就是[1, 2, 3, 4]，或这[3, 2, 1, 4] [3, 4, 1, 2]无所谓顺序
        // 而link存的值link[0] = 1, link[1] = 2一次类推。注意，这里边value的值可以以任意顺序，但是link的顺序一定是按照链表的顺序存储。

        // idx 是value的下标， head默认是链表的头节点下标
        // 在这种写法当中，link[k]其实就是node写法的node.next
        int idx = 0, head = -1;
        // 在头节点添加一个节点的方法
        {
            // 首先将数据插入value数组
            value[idx] = num;
            link[idx] = head;
            head = idx;
            // 数组下标后移
            idx++;
        }
        // 将一个节点插入固定节点的后边
        {
            value[idx] = num;
            link[idx] = link[k];
            link[k] = idx;
            idx++;
        }
        // 删除操作，下标为k的后边的节点删除
        {
            link[k] = link[link[k]];
        }
    }

    /**
     * 区间和
     */
    public void betweenSum(int[] x, int[] c, int l, int r) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            list.add(x[i]);
        }
        // 对输入的下标进行排序，这里针对的是下标，例如原来的输入顺序是3, -1, 5, 7, 2
        // 排完序的顺序是-1， 2， 3， 5， 7 仅仅是针对下标，这样就做了一个映射。
        // 为什么可以排序映射：
        // 因为原来的输入数据可能不是有序的，但是根据题目的性质，最终要取l，r的区间，他一定是按照从小到大的顺序。所以可以对x进行排序。
        // 他还需要把l，r的所有下标也加入到x当中，然后统一进行排序处理。
        Collections.sort(list);

        int[] a = new int[300010];
        for (int i = 0; i < list.size(); i++) {
            // 这一步是找到原来顺序下x在排完序后对应的下标。
            int index = find(list.get(i), list);
            // 注意这一步，这一步是直接根据他映射完的下标来加上c中的值。
            // 注意这里的c[i]不一定准确，但是他是对应x下标需要加上的c的值。
            // list.get(i)是获取到的c对应的x下标
            a[index] += c[list.get(i)];
        }

        // 这一题，最后求l，r的下标合时，他是通过find来找到原本下标x对应的新下标，并不是找原来的下标。

    }

    public int find(int x, ArrayList<Integer> list) {
        int l = 0, r = list.size() - 1;
        while (l < r)
        {
            int mid = l + r >> 1;
            if (list.get(mid) >= x) r = mid;
            else l = mid + 1;
        }
        return r + 1;
    }

    /**
     * 原码，反码，补码
     * 原码： 10010
     * 反码： 01101
     * 补码  10011（原码取反 + 1）
     */
    public void testBitOperation(int n) {
        // 求n的第k位数字
        int k = 3;
//        n >> k & 1;

        // 统计二进制表示n后n中1的个数
        int res = 0;
        while (n > 0) {
            n -= lowBit(n);
            res ++;
        }
        System.out.println(res);
    }

    int lowBit(int x) {
        return x & -x;
    }

    // 双指针
    // 最长连续子序列
    // 本题i是右边指针，j是左边指针，j代表了i的位置往左最多可以有多少个不重复的元素
    // 在i往右移动的同时，j不可能往左移动。原因：因为前一个j是前一个i对应最左边没有重复的子序列，即j在往左移动一位就会出现重复。所以i往右移动时，j往左移动的话
    // 新的i，j区间会包含前一个不满足提议的i，j。所以j不可能向左移动
    // 例如：1，2，2，3，5  当i指向第二个2时，j只能与i在同一个下标。当i向右移动到3，j不可能向左移动。
    public void longestSubsequence(int[] arr) {

        // 写法一，用map代替开一个很大空间的数组
        // 这里边要注意map存放的是当前i和j里边个个元素的个数，所以说如果map.get(arr[i])的值大于1的话，说明该区间有重复的元素
        Map<Integer, Integer> map = new HashMap<>();

        int count = 0;
//        for (int i = 0, j = 0; i < arr.length; i++) {
//            int value = 1;
//            // 首先存放当前i到map当中，表示当前序列中有了一个arr[i]，用于判断是否与之前的重复
//            if (map.get(i) != null) {
//                value = map.get(arr[i]);
//                map.put(arr[i], value + 1);
//            } else {
//                map.put(i, 1);
//            }
//
//            // 如果value大于1，说明在i，j的区间内有重复的元素，所以j要向前移动，而map要减去对应arr[j]的值
//            while (value > 1) {
//
//            }
//        }


        // 写法二，直接开辟一个很大的空间, 上边的写法会很麻烦，每次修改的时候都要求map的值，还需要修改，但是好处是比较节省空间
        int[] countArr = new int[1000];

        for (int i = 0, j = 0; i < arr.length; i++) {
            countArr[arr[i]]++;

            // 大于1，说明有重复的元素，j需要向右移动，移动的同时把arr[j]的值从计数数组中移除
            while (countArr[arr[i]] > 1) {
                countArr[arr[j]]--;
                j++;
            }

            count = Math.max(count, i - j + 1);
        }

        System.out.println(count);
    }

    // 二维差分的insert
    // 差分在某一点加之后，从那一点开始往后的所有点都会加上一个值，所以在图形中应该是右下角
    public void insert(int x1, int y1, int x2, int y2, int[][] arr) {
        int c = arr[x1][y1];
        // [x1][y1]的位置+c，那么从这个位置后的所有元素都会加c
        arr[x1][y1] += c;
        // 因为在[x2][y2]的位置要停止，所以这里要减去[x2 + 1][y1]的矩形
        arr[x2 + 1][y1] -= c;
        arr[x1][y2 + 1] -= c;
        // 减去上边两个方块后，[x2][y2]与右下角组成的方块被减去了2次，所以要加上一次。
        arr[x2 + 1][y2 + 1] += c;
    }

    // 差分
    // 注意，求差分数组的时间复杂度为O(n), 此题只有在多组数据需要加到原数据上时才会有较高的效率，因为
    public void difference(int[] arr) {
        int[] differenceArr = new int[arr.length + 1];

        // 求arr数组的差分数组
        // 这里可以更换一种理解，可以吧arr的初始值全部当作0，那么他的差分数组differenceArr的初始值也就是0
        // 但是arr并不全是0，可以理解为在arr的[i, i]的位置添加arr[i]
        // 这样方便统一操作，无论初始化差分数组，还是后续对差分数组进行操作，都可以用insert
        for (int i = 0; i < arr.length; i++) {
            // 这里直接使用insert也可以
//            insert(i, i, arr[i], differenceArr);
            differenceArr[i] += arr[i];
            differenceArr[i + 1] -= arr[i];
        }

        // 给arr的left, right位置的值 + c
        int left = 2, right = 4, c = 3;
        insert(left, right, c, differenceArr);

        // 打印arr完成加操作后的值
        int value = 0;
        for (int i = 0; i < differenceArr.length; i++) {
            value += differenceArr[i];
            System.out.print(value + " ");
        }
    }

    public void insert(int left, int right, int value, int[] arr) {
        arr[left] += value;
        arr[right + 1] -= value;
    }

    // 这里是一维前缀和，还有二维前缀和。
    public void preFixSum(int[] arr) {
        // 0的位置不用，所以长度为length + 1
        int[] preFixSumArr = new int[arr.length + 1];

        // 这一步是求前缀和
        for (int i = 1; i <= arr.length; i++) {
            preFixSumArr[i] = preFixSumArr[i - 1] + arr[i - 1];
        }

        int left = 1, right = arr.length - 1;
        // 获取任意区间段的和, 这里的left要从1开始
        System.out.println(arr[right] - arr[left - 1]);
    }

    // 数的范围(二分思想) 该题如果直接遍历，时间复杂度是O(n)，如果利用二分则时间复杂度为O(logN)
    public void theScopeOfNumber(int[] arr, int k) {
        int left = 0, right = arr.length - 1;

        while (left < right) {
            // 这个移位相当于除以2
            // 这里不用+1本质上是因为除法向下取整，其他的二分可能不是这个原因。
            int mid = left + right >> 1;
            if (arr[mid] >= k) {
                // 由于这里的判断是mid >= k，说明mid的位置也是包含在该特性当中，所有right = mid，而不是mid - 1
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        // 因为上一步的二分在结束时确定了最接近k的值，如果arr[left]不等于k，说明数组中并没有k
        if (arr[left] != k) {
            System.out.println("null");
        } else {

            // 这里为什么left就是第一个元素：
            // 因为上一步的arr[mid]的判断是>=，即等于k时，它的区间仍然会向左缩小，所以说当结束循环时，如果有等于k的值，一定是最左边的
            System.out.println(left);

            // 重新初始化l，开始右半部分的二分。因为上一步的取值是>=k,有可能右半部分还有等于k的值
            left = 0;
            right = arr.length - 1;

            while (left < right) {
                // 这里为什么要left + right + 1之后再除以2：
                // 因为这是右半部分，存在一种情况是当left = right - 1时，如果表达式为left + right >> 2, 则mid = right + right - 1 >> 2
                // 在这种情况下，由于除法是向下取整，所以就变成2right - 1 >> 2 = left，所以mid永远等于left，二分区间永远是(left, right)无法结束循环。
                // 如果加1，则会变成2right / 2 = right，即left = mid = right，区间变为(right, right)循环会结束
                int mid = left + right + 1 >> 2;

//                if (arr[mid] >= k) {
//                    right = mid;
//                } else {
//                    left = mid - 1;
//                }

                // 上面那种写法不行的原因：
                // 因为这里是右边，区间要不断的往右边扩大，这样最后的left才是最右边的元素下标，如果区间往左缩小，可能left会记录到
                // 处于有伴区间的最左边的元素，而不是最右边的。
                if (arr[mid] <= k) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            System.out.println(left);
        }
    }

    // 逆序对个数
    public int mergeSort(int left, int right, int[] arr) {
        if (left >= right) {
            return 0;
        }

        int mid = (left + right) / 2;
        // res 就是返回的逆序对的个数
        int res = mergeSort(left, mid, arr) + mergeSort(mid + 1, right, arr);

        int k = 0, i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            // arr[i] < arr[j],说明此时并不构成逆序对
            if (arr[i] < arr[j]) {
                mergeSortTemp[k++] = arr[i++];
            } else {
                // 当arr[i] > arr[j]时，说明找到了一个index i，从i往后的所有值都会大于arr[j]（包括arr[i]）
                // 注意，此时是合并的过程，区分了前半段数组和后半段数组，而且前半段和后半段都是部分有序的
                // 所以找到了第一个大于j的下标i，后续所有都会大于，而且j是处于后半段，所以针对于当前下标的j，mid到i的所有值都会组成逆序对
                mergeSortTemp[k++] = arr[j++];
                res += mid - i + 1;
            }
        }

        while (i <= mid) {
            mergeSortTemp[k++] = arr[i++];
        }
        while (j <= right) {
            mergeSortTemp[k++] = arr[j++];
        }

        // 这里虽然不要求打印排序好的数组，但是由于需要归并，所以还是得把之前已经排好的赋值给原数组，用于最后一次归并排序
        for (i = left, j = 0; i <= right; i++, j++) {
            arr[i] = mergeSortTemp[j];
        }
        return res;
    }

    // the mergeSortTemp is used to store the merge result.
    int[] mergeSortTemp = new int[30];
    // 归并排序
    public void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        int k = 0, i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                mergeSortTemp[k++] = arr[i++];
            } else {
                mergeSortTemp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            mergeSortTemp[k++] = arr[i++];
        }
        while (j <= right) {
            mergeSortTemp[k++] = arr[j++];
        }

        // we cut the array for many times, and the left is start index of the cut arr, and the right is the end index of the cut arr.
        // the mergeSortTemp stored the merge result, we should copy it to the arr.
        for (i = left, j = 0; i <= right; i++, j++) {
            arr[i] = mergeSortTemp[j];
        }
    }

    // 第k个数
    // 每次都会进行一个划分，划分完成之后分两种情况。
    // 这里划分后会根据k的大小以及划分成两段各自的大小来确定下次去哪里递归，随着划分越多，k的值会相应减去一部分，所以最终可以确认第k小。
    public int quickSortK(int left, int right, int k, int[] arr) {
        if (left == right) {
            return arr[left];
        }

        int val = arr[left], i = left - 1, j = right + 1;
        // when i < j, the number in the val's left are smaller than val, the right are bigger
        while (i < j) {
            // find a num less than val from the left
            while (arr[++i] < val);
            // find a num bigger than val from the right
            while (arr[--j] > val);
            // swap the number
            // the val should between i and j, so when i > j, we don't need to swap
            if (i < j) {
                swap(arr, i, j);
            }
            System.out.println(Arrays.toString(arr) + " i =  " + i + " j =  " + j);
        }

        // j会一直往左走，直到找到一个比val小的数字才会停下，所以最后一次i > j 时，j的位置就是小于val的最左边的位置
        // +1是因为j是下标，+1取到小于val的个数，也就是划分了数组。
        int ls = j - left + 1;
        // ls大于k，说明第k个数字在左边，直接递归左边的剩余数组即可
        if (ls > k) {
            return quickSortK(left, j, k, arr);
        }

        // 如果ls小于k，说明第k个数在右边，所以需要寻找右边第k - ls小的数（因为要取消前边ls个比右边所有数都小的）
        return quickSortK(j + 1, right, k - ls, arr);
    }

    public void swap(int[] arr, int i, int j) {
        int val = arr[i];
        arr[i] = arr[j];
        arr[j] = val;
    }
}
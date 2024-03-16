package annotation;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        testMain.forth();
    }

    public void first() {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int[] arr = new int[a];
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
            sum += arr[i];
        }
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        System.out.println(sum - x - y);
    }

    public void second() {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();
//        int lower = 0, upper = 0, firstUpper = 0;
        int lower = 0, upper = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                upper++;
//                if (i == 0) {
//                    firstUpper++;
//                }
            } else {
                lower++;
            }
        }
        int allLower = upper;
        int allUpper = lower;
        int firstUpper = Math.min(lower + (Character.isLowerCase(word.charAt(0)) ? 1 : 0), upper - (Character.isUpperCase(word.charAt(0)) ? 1 : 0));
        int res = Math.min(allLower, Math.min(allUpper, firstUpper));
        System.out.println(res);
//        if ((firstUpper == 1 && upper == 1) || lower == 0 || upper == 0) {
//            System.out.println(0);
//        } else {
//            System.out.println(Math.min(upper, lower));
//        }
    }

    public void third() {
        int mod = 1000000007;
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        long[] arr = new long[m];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }
        int[] arr2 = new int[n];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = scanner.nextInt();
        }

        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (j + 1 != arr2[i]) {
                    arr[j] *= 2;
                }
            }
        }

        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = (sum + arr[i]) % mod;
        }

        System.out.println(sum);
    }

    public void forth() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            int[] count = new int[3];
            int maxCount = 0, mode = 0;
            for (int j = i; j < n; j++) {
                count[arr[j]]++;
                if (count[arr[j]] > maxCount || (count[arr[j]] == maxCount && arr[j] < mode)) {
                    maxCount = count[arr[j]];
                    mode = arr[j];
                }
                res += mode;
            }
        }
        System.out.println(res);
    }

    static int[] temp;
    public void five() {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int[] arr = new int[a];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = -arr[i];
            System.out.println(mergeSortAndCount(arr, 0, arr.length - 1));
            arr[i] = -arr[i];
        }
//        long[] ni = new long[a];
//        for (int i = 0; i < arr.length; i++) {
//            long sum = 0;
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[j] < arr[i]) {
//                    sum++;
//                }
//            }
//            ni[i] = sum;
//        }
//        // System.out.println(Arrays.toString(ni));
//        long sum = 0;
//        for (int i = 0; i < ni.length; i++) {
//            sum += ni[i];
//        }
//        long[] res = new long[a];
//        for (int i = 0; i < res.length; i++) {
//            res[i] = sum - ni[i] + i;
//        }
//
//        for (int i = 0; i < res.length; i++) {
//            System.out.print(res[i] + " ");
//        }
    }

    public static int mergeSortAndCount(int[] arr, int l, int r) {
        int count = 0;
        if (l < r) {
            int m = (l + r) / 2;
            count += mergeSortAndCount(arr, l, m);
            count += mergeSortAndCount(arr, m + 1, r);
            count += mergeAndCount(arr, l, m, r);
        }
        return count;
    }

    public static int mergeAndCount(int[] arr, int l, int m, int r) {
        int count = 0;
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += m - i + 1;
            }
        }

        while (i <= m) {
            temp[k++] = arr[i++];
        }
        while (j <= r) {
            temp[k++] = arr[j++];
        }
        if (l <= r) {
            System.arraycopy(temp, l, arr, l, r - l + 1);
        }

        return count;
    }

    public static int count(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }
}

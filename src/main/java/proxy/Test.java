package proxy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = scanner.nextInt();
            arr[i][1] = scanner.nextInt();
            arr[i][1] = arr[i][1] - arr[i][0];
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> a[1]));


    }

}

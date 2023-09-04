package lock;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);

        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                count += 1;
                arr[i]++;
                Arrays.sort(arr);
            }
        }
        System.out.println(count);
    }

    public static void test() {
        int n = scanner.nextInt();
        int u = scanner.nextInt();
        int v = scanner.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n - i; j++) {
                int sum = 0;
                for (int k = j; k <= j + i; k++) {
                }
            }
        }
    }
}

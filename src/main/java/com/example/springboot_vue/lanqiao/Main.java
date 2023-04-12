package com.example.springboot_vue.lanqiao;

import java.util.*;

public class Main {

    public static int[] value= {};

    public static int returnValue = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        int[] arr = new int[n];
    }

    void add(int[] arr) {
        int[] test = new int[arr.length - 1];
        for (int i = 0; i < arr.length - 1; i++) {
            test[i] = arr[i] + arr[i + 1];
        }
        if (test.length == 1) {
            returnValue = test[0];
        }
        if (test.length != 1) {
            add(test);
        }
    }

    void swap(int[] cs, int index, int target) {
        int tmp = cs[index];
        cs[index] = cs[target];
        cs[target] = tmp;
    }

    void dfs(int[] arr, int index, int sum) {

        if (index > arr.length) {
            return;
        }

        if (index == arr.length) {
            add(arr);
            if (returnValue == sum) {
                if (value.length == 0) {
                    value = new int[arr.length];
                    System.arraycopy(arr, 0, value, 0, arr.length);
                } else {
                    for (int i = 0; i < value.length; i++) {
                        if (value[i] > arr[i]) {
                            System.arraycopy(arr, 0, value, 0, arr.length);
                            break;
                        } else if (value[i] < arr[i]) {
                            break;
                        }
                    }
                }
            }

        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            dfs(arr, index + 1, sum);
            swap(arr, index, i); // 再次交换，保持原状
        }

    }

    void getCoin(int[][] arr, int n) {
        int[][] dp = new int[n][n];
        dp[0][0] = arr[0][0];

        for (int i = 1; i < n; i++) {
            dp[0][i] = arr[0][i] + dp[0][i - 1];
            dp[i][0] = arr[i][0] + dp[i - 1][0];
        }

        // dp[i][j]代表走到i，j这一格可以拿到的最高金币
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + arr[i][j];
            }
        }

        System.out.println(dp[n - 1][n - 1]);
    }

    void huffman(int[] arr) {
        Arrays.sort(arr);
        int count = 1, index = 2, unUse = 0;
        int[] arr2 = new int[arr.length];
        arr2[0] = arr[0] + arr[1];
        while (index < arr.length) {
            System.out.println(index);
            if (index != arr.length - 1) {
                // 两个都小于
                if (arr[index + 1] < arr2[count - 1 - unUse]) {
                    arr2[count++] = arr[index] + arr[index + 1];
                    index += 2;
                    unUse++;
                    if (index == arr.length) {
                        arr2[count] = arr2[count - 1] + arr2[count - 2];
                        count++;
                    }
                } else {
                    // 一个大于一个小于 或者两个都大于的情况
                    // arr[index] < arr2[count - 1] && arr[index + 1] > arr2[count - 1]
                    arr2[count] = arr[index] + arr2[count - 1];
                    count++;
                    index++;
                }
            } else {
                arr2[count] = arr[index] + arr2[count - 1];
                count++;
                index++;
            }

        }

        int sum = 0;
        for (int i = 0; i < count; i++) {
            System.out.println(arr2[i]);
            sum += arr2[i];
        }
        System.out.println(sum);
    }

    void cycle(int[][] arr, int m, int n, int[][] mark) {
        int i = 0, j = 0, direct = 0;
        int count = 0;
        while (count != m * n) {
            System.out.print(arr[i][j] + " ");
            mark[i][j] = 1;
            count++;

            if (direct == 0) {
                if (i + 1 < m && mark[i + 1][j] == 0) {
                    i++;
                } else {
                    j++;
                    direct = (direct + 1) % 4;
                }
            } else if (direct == 1) {
                if (j + 1 < n && mark[i][j + 1] == 0) {
                    j++;
                } else {
                    i--;
                    direct = (direct + 1) % 4;
                }
            } else if (direct == 2) {
                if (i - 1 >= 0 && mark[i - 1][j] == 0) {
                    i--;
                } else {
                    j--;
                    direct = (direct + 1) % 4;
                }
            } else {
                if (j - 1 >= 0 && mark[i][j - 1] == 0) {
                    j--;
                } else {
                    i++;
                    direct = (direct + 1) % 4;
                }
            }
        }
    }

    void test(int v1, int v2, int t, int s, int l) {
        int rabbit = 0, turtle = 0;
        int time = 0;
        while (turtle < l && rabbit < l) {
            if (rabbit - turtle >= t) {
                turtle += s * v2;
                time += s;
            } else {
                rabbit += v1;
                turtle += v2;
                time++;
            }
        }

        if (rabbit == turtle) {
            System.out.println("D");
            System.out.println(l / v2);
        } else if (rabbit > turtle) {
            System.out.println("R");
            System.out.println(time);
        } else {
            System.out.println("T");
            System.out.println(l / v2);
        }
    }
}

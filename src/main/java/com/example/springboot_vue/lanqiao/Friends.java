package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class Friends {

    static int m, n;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] arr = new int[100010];
        for (int i = 1; i <= n; i++) {
            arr[i] = i;
        }
        while (m-- > 0) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            arr[find(x, arr)] = find(y, arr);
        }

        // arr[find(x, arr)]会找到x的父节点，find(y, arr)会找到y的父节点，如果是同一个节点，则返回true
        int q = scanner.nextInt();
        while (q-- > 0) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (find(x, arr) == find(y, arr)) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }

    }

    static void merge(int a, int b, int[] arr) {
        arr[find(b, arr)] = find(a, arr);
    }

    static int find(int x, int[] arr) {
        // 并查集设定arr[x] = x是根节点
        if (x != arr[x]) {
            // 这里对arr[x]的修改不写也行，但那样会导致每一次查找都需要从当前节点一直找到根节点
            // 并查集并不关心一个节点的父节点是谁，他只关心是否在在一个祖先节点中。
            // 所以arr[x]的值之前代表x的父节点，现在把他赋值为它父节点的父节点，并不影响结果的正确性，但是会减少并查集的高度。
            arr[x] = find(arr[x], arr);
        }

        return arr[x];
    }
}

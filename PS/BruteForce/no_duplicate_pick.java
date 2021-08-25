package algorithm.PS.BruteForce;

import java.util.Scanner;

// 백준 15650 N과 M(2) - https://www.acmicpc.net/problem/15650
// N개 중 중복 없이 M개 고르기
public class no_duplicate_pick {
    static StringBuffer sb = new StringBuffer();
    static int N, M;
    static int[] selected;

    static void input() {
        Scanner s = new Scanner(System.in);
        N = Integer.parseInt(s.next());
        M = Integer.parseInt(s.next());
        selected = new int[M + 1];
    }

    static void rec_func(int k) {
        if (k == M + 1) {
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = selected[k - 1] + 1; cand <= N; cand++) {
                selected[k] = cand;
                rec_func(k + 1);
                selected[k] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();
        rec_func(1);
        System.out.println(sb.toString());
    }
}
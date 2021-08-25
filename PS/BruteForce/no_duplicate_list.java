package algorithm.PS.BruteForce;

import java.util.Scanner;

// 백준 15649 N과 M(1) - https://www.acmicpc.net/problem/15649
// N개 중 중복 없이 M개를 순서 있게 나열하기
public class no_duplicate_list {
    static StringBuffer sb = new StringBuffer();
    static int N, M;
    static int[] selected;
    static boolean[] used;

    static void input() {
        Scanner s = new Scanner(System.in);
        N = Integer.parseInt(s.next());
        M = Integer.parseInt(s.next());
        selected = new int[M + 1];
        used = new boolean[10];
    }

    static void rec_func(int k) {
        if (k == M + 1) {
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = 1; cand <= N; cand++) {
                if (used[cand]) continue;

                selected[k] = cand; used[cand] = true;
                rec_func(k + 1);
                selected[k] = 0;    used[cand] = false;
            }
        }
    }

    public static void main(String[] args) {
        input();
        rec_func(1);
        System.out.println(sb.toString());
    }
}

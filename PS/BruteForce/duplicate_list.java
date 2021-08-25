package algorithm.PS.BruteForce;

import java.util.Scanner;

// 백준 15651 N과 M(3) - https://www.acmicpc.net/problem/15651
// N개 중 중복을 허용해서 M개를 순서 있게 나열하기
public class duplicate_list {
    static StringBuffer sb = new StringBuffer();
    static int N, M;
    static int[] selected;

    static void input() {
        Scanner s = new Scanner(System.in);
        N = Integer.parseInt(s.next());
        M = Integer.parseInt(s.next());
        selected = new int[M + 1];
    }

    static void rec_func(int k) {   // 재귀함수
        // M 개를 전부 고름 : 조건에 맞는 탐색 한 가지 성공
        // M 개를 고르지 않음 : k번째 부터 M번째 원소를 조건에 맞게 고르는 모든 방법 시도
        if (k == M + 1) {   // 모두 선택됨
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = 1; cand <= N; cand++) {
                selected[k] = cand;
                // k + 1 부터 M 까지 모두 탐색
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

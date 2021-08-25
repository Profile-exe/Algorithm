package algorithm.theory.graph;

/*
    Backtracking Algorithm: 백트래킹(backtracking) 또는 퇴각 검색(backtrack)으로 부름
        - **제약 조건 만족 문제**(Constraint Satisfaction Problem)에서 해를 찾기 위한 전략
            - 해를 찾기 위해 후보군에 제약 조건을 점진적으로 체크
            - 조건을 불만족하는 즉시 backtrack 후 다른 후보군으로 이동
            - backtrack 시 해당 후보군을 더이상 체크하지 않도록 표시
            
        - 실제 구현 시 고려할 수 있는 모든 경우의 수(후보군)를 상대로 상태 공간 트리(State Space Tree)로 표현
            - 각 후보군을 **DFS**방식으로 확인
            - 상태 공간 트리를 탐색하면서 제약을 불만족하면 backtrack후 다른 곳 탐색
                => Promising : 해당 루트가 조건에 맞는지 검사하는 기법
                => Pruning (가지치기) : 조건을 불만족하면 포기하고 다른 루트로 가서 탐색 시간 절약하는 기법

        > 즉 백트래킹은 트리 구조를 기반으로 **DFS**로 깊이 탐색 진행하며
            - 각 루트에 대해 조건에 부합하는지 체크 : Promising
            - 조건을 불만족시키면 DFS 탐색을 중지하고 가지치기 : Pruning

    상태 공간 트리 (State Space Tree) - 문제 해결 과정의 **중간 상태**를 각각의 노드로 나타낸 트리

 */

import java.util.ArrayList;

// NQueen 문제
public class BackTracking {

    public static void main(String[] args) {
        BackTracking NQueen = new BackTracking();
        NQueen.dfsFunc(4, 0, new ArrayList<>());
    }

    public void dfsFunc(Integer N, Integer currentRow, ArrayList<Integer> currentCandidate) {
        if (currentRow == N) {  // 끝까지 다 탐색한 경우
            System.out.println(currentCandidate); // 최종 선택된 후보군 출력
            return;
        }

        for (int index = 0; index < N; index++) {
            if (this.isAvailable(currentCandidate, index)) {
                currentCandidate.add(index);
                this.dfsFunc(N, currentRow + 1, currentCandidate);
                // 조건에 맞지 않으면 가지치기
                // 출력 후 끝날 수도 있지만 조건에 맞는게 없을 경우도 return 하기 때문
                currentCandidate.remove(currentCandidate.size() - 1);   // Pruning
            }
        }
    }

    public boolean isAvailable(ArrayList<Integer> candidate, Integer currentCol) {
        Integer currentRow = candidate.size();
        for (int index = 0; index < currentRow; index++) {
            // Promising
            if ((candidate.get(index) == currentCol) || (Math.abs(candidate.get(index) - currentCol) == currentRow - index)) {
                return false;
            }
        }
        return true;
    }
}

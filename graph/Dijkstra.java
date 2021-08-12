package algorithm.graph;

/*
    Single-source (단일 출발) 문제
    그래프의 특정 노드에서 출발하여 그래프 내의 모든 다른 노드에 도착하는 최단 경로를 찾는 문제

    Dijkstra Algorithm : 하나의 정점에서 다른 모든 정점에 도착하는 최단 경로를 구하는 문제
        - 첫 정점을 기준으로 연결되어있느 정점들을 추가하며 최단 거리를 갱신(update)
        - BFS(breadth first search)와 유사
            => 거리를 저장하는 배열 만든 후 인접 노드간의 거리부터 먼저 계산
               첫 정점에서 해당하는 노드간의 가장 짧은 거리를 배열에 update

    1. 첫 정점을 기준으로 각 정점까지의 거리를 저장하는 배열 선언
        - 첫 정점 0, 나머지는 INF(무한대)
        - 우선순위 큐(Min Heap)에 첫 정점을 넣음

    2. 우선순위 큐(Min Heap)에서 노드를 꺼냄
        - 처음에는 첫 정점만 연결되어 이것이 꺼내짐
        - 꺼낸 정점의 인접 노드에 대해 배열에 저장된 거리와 비교하여 최솟값으로 배열 갱신
        - 배열이 갱신된 경우 해당 인접 노드를 우선순위 큐에 삽입
            => 결과적으로 BFS 와 유사하게 인접 노드들을 순차적으로 방문하게 된다.
            => 만약 배열에 저장된 최솟값보다 더 큰 가중치를 가지는 경우 해당 노드와 인접 노드의 거리 계산 안함

    3. 2번 과정을 우선순위 큐가 빌 때 까지 반복

    Dijkstra 알고리즘 시간복잡도
        과정 1 : 각 노드마다 인접 간선 모두 검사
        과정 2 : 우선순위 큐에 정점 및 거리 정보 넣고 삭제(poll)

    각 과정별 시간복잡도 - 간선 개수 : E

        과정 1 : 각 정점은 최대 한 번씩 방문 => 모든 간선은 최대 한 번씩 검사
            => 과정 1 시간복잡도 : O(E)

        과정 2 : 우선순위 큐에 가장 많은 노드, 거리 정보가 들어가는 경우
            노드/거리 정보를 넣고 삭제하는 과정이 최악의 시간이 걸림
                - 우선순위 큐에 가장 많은 노드/거리 정보가 들어가는 시나리오
                    그래프의 모든 간선이 검사될 때마다
                    => 배열의 최단거리 갱신
                    => 우선순위 큐에 노드/거리 추가  (갱신되므로 우선순위 큐에 추가해야함)
                - 추가는 각 간선마다 최대 한 번
                    => O(E)
                - E개의 노드/거리 정보에 대해 우선순위 큐 유지
                    => O(logE)
                - 따라서 추가 x 큐 유지 = O(E) x O(logE) = O(ElogE)
            => 과정 2 시간복잡도 : O(ElogE)

    총 시간 복잡도
        - 과정 1 + 과정 2 = O(E) + O(ElogE) = O(E + ElogE) = O(ElogE)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {
    public static HashMap<String, ArrayList<Edge>> graph = new HashMap<>();
    public static HashMap<String, Integer> distance = new HashMap<>();

    public static void main(String[] args) {
        Dijkstra d = new Dijkstra();
        d.initGraph();
        System.out.println(d.dijkstraFunc("A"));
    }

    public HashMap<String, Integer> dijkstraFunc(String start) {
        for (String key : graph.keySet())
            distance.put(key, Integer.MAX_VALUE);
        distance.put(start, 0); // 시작 노드는 0으로 초기화 나머지는 무한대(INF)로 초기화

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(distance.get(start), start)); // 시작 노드 우선순위 큐에 넣기

        // 알고리즘에 필요한 변수 목록
        Edge edgeNode;                  // 우선순위 큐에서 꺼내올 Edge 저장
        ArrayList<Edge> nodeList;       // 인접 정점 리스트 저장
        String currentNode, adjacent;   // 큐에서 꺼낸 노드와 꺼낸 노드의 인접 정점 저장
        int currentDistance, weight, transdistance;

        // 우선순위 큐로 가장 작은(가까운) distance 가지는 정점 꺼내서 알고리즘 진행
        while (!pq.isEmpty()) {                     // 우선순위 큐가 빌 때까지 반복
            edgeNode = pq.poll();                   // 우선순위 큐에서 가장 가까운 인접 정점 하나를 꺼낸다.
            currentDistance = edgeNode.distance;    // 해당 정점 까지의 distance 저장
            currentNode = edgeNode.vertex;          // 목적지 정점의 이름 저장

            // 더 크다면 계산이 불필요하므로 다음 탐색
            if (distance.get(currentNode) < currentDistance) continue;

            nodeList = graph.get(currentNode);      // 목적지 정점의 인접 정점 리스트 가져오기
            for (Edge e : nodeList) {               // 해당 정점의 인접 리스트에 대하여
                adjacent = e.vertex;                // 인접 정점의 이름 저장
                weight = e.distance;                // 인접 정점의 거리 저장

                // 시작 정점에서 edgeNode 거쳐 해당 인접 정점까지의 거리 저장
                transdistance = currentDistance + weight;
                if (distance.get(adjacent) > transdistance) {   // 거리가 더 짧다면
                    distance.put(adjacent, transdistance);      // distance 갱신
                    pq.add(new Edge(transdistance, adjacent));  // 갱신되면 해당 인접 노드를 우선순위 큐에 담는다.
                }
            }
        }

        return distance;
    }

    static class Edge implements Comparable<Edge> {
        public int distance;
        public String vertex;

        public Edge(int distance, String vertex) {
            this.distance = distance;
            this.vertex = vertex;
        }

        // System.out.println() 으로 객체 자체 출력 시
        @Override
        public String toString() {
            return "vertex: " + this.vertex + ", distance: " + this.distance;
        }

        @Override
        public int compareTo(Edge edge) {
            return this.distance - edge.distance;
        }
    }

    public void initGraph() {
        graph.put("A", new ArrayList<>(Arrays.asList(new Edge(8, "B"), new Edge(1, "C"), new Edge(2, "D"))));
        graph.put("B", new ArrayList<>());
        graph.put("C", new ArrayList<>(Arrays.asList(new Edge(5, "B"), new Edge(2, "D"))));
        graph.put("D", new ArrayList<>(Arrays.asList(new Edge(3, "E"), new Edge(5, "F"))));
        graph.put("E", new ArrayList<>(Arrays.asList(new Edge(1, "F"))));
        graph.put("F", new ArrayList<>(Arrays.asList(new Edge(5, "A"))));
    }
}


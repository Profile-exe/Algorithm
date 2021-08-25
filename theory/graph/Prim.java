package algorithm.theory.graph;

/*
    Prim's Algorithm : 최소 신장 트리를 찾는 알고리즘 중 하나
        - 임의의 정점 선택 '연결된 노드 집합'에 삽입
        - 선택된 정점에 연결된 간선들을 간선 리스트에 삽입
        - 간선 리스트에서 최소 가중치를 가지는 간선부터 추출 (Min Heap)
            => 해당 간선에 연결된 인접 정점이 '연결된 노드 집합'에 이미 있다면 Skip (사이클 방지)  
            => 해당 간선에 연결된 인접 정점이 '연결된 노드 집합'에 없다면 해당 간선 선택 후 MST에 간선 정보를 삽입
        - 추출한 간선은 간선 리스트에서 제거 (poll)
        - 간선 리스트가 빌 때까지 간선을 추출하며 반복

    Kruskal's algorithm / Prim's algorithm 비교
        - 둘 다 탐욕(Greedy) 알고리즘을 기초로 하고 있음
        - Kruskal 알고리즘
            => 가장 가중치가 작은 간선부터 선택하면서 MST 구함
        - Prim 알고리즘
            => 특정 정점에서 시작하여 해당 정점에 연결된 가장 가중치가 작은 간선 선택
            => 선택된 정점들의 집합에 연결된 간선중에서 가장 가중치가 작은 간선을 선택하며 MST 구함 

    Prim 알고리즘 시간복잡도
        최악의 경우, while 구문에서 모든 간선에 대해 반복 & 최소 힙 구조 사용

    총 시간 복잡도
        -  O(ElogE)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Prim {
    public static PriorityQueue<Edge> pq = new PriorityQueue<>();
    public static ArrayList<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        Prim p = new Prim();
        System.out.println(p.primFunc("A"));
    }

    public ArrayList<Edge> primFunc(String startNode) {
        Edge popedEdge;
        ArrayList<Edge> mst = new ArrayList<>();
        ArrayList<Edge> currentEdgeList;        // 각 노드(key)에 대한 리스트(value)에 간선을 넣기 위한 변수
        ArrayList<Edge> candidateEdgeList;      // 시작노드에 연결된 간선들의 리스트를 우선순위 큐에 넣기 위한 변수
        ArrayList<Edge> adjacentEdgeNodes;      //
        ArrayList<String> connectedNodes = new ArrayList<>();
        HashMap<String, ArrayList<Edge>> adjacentEdges = new HashMap<>();

        // 초기화 시작 *******************************************************
        initGraph();

        for (Edge e : edges) {      // 정점을 key로 넣는다.
            if (!adjacentEdges.containsKey(e.node1)) {
                adjacentEdges.put(e.node1, new ArrayList<>());
            }
            if (!adjacentEdges.containsKey(e.node2)) {
                adjacentEdges.put(e.node2, new ArrayList<>());
            }
        }

        for (Edge e : edges) {      // 각 노드(key)에 대한 간선들을 리스트(value)에 넣는다.
            currentEdgeList = adjacentEdges.get(e.node1);   // 리스트를 가져와서
            currentEdgeList.add(new Edge(e.weight, e.node1, e.node2));  // 연결된 간선들을 넣기
            currentEdgeList = adjacentEdges.get(e.node2);
            currentEdgeList.add(new Edge(e.weight, e.node2, e.node1));
        }

        // 시작 노드를 정점 집합에 연결
        connectedNodes.add(startNode);
        candidateEdgeList = adjacentEdges.getOrDefault(startNode, new ArrayList<>());

        pq.addAll(candidateEdgeList);
        // 초기화 완료 *******************************************************

        // Prim 알고리즘 시작 *************************************************
        while (!pq.isEmpty()) {
            popedEdge = pq.poll();  // 큐에서 하나를 꺼내 가져온다.
            if (!connectedNodes.contains(popedEdge.node2)) {
                // 해당 edge를 mst에 추가
                connectedNodes.add(popedEdge.node2);
                mst.add(new Edge(popedEdge.weight, popedEdge.node1, popedEdge.node2));
            }
            
            // 꺼낸 노드에 대한 인접 노드 리스트 가져오기
            adjacentEdgeNodes = adjacentEdges.getOrDefault(popedEdge.node2, new ArrayList<>());
            for (Edge e : adjacentEdgeNodes) {
                if (!connectedNodes.contains(e.node2)) {
                    pq.add(e);
                }
            }
        }
        // Prim 알고리즘 끝 ***************************************************

        return mst;
    }

    static class Edge implements Comparable<Edge> {
        public int weight;
        public String node1;
        public String node2;

        public Edge(int weight, String node1, String node2) {
            this.weight = weight;
            this.node1 = node1;
            this.node2 = node2;
        }

        @Override
        public String toString() {
            return "(" + this.weight + ", " + this.node1 + ", " + this.node2 + ")";
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }
    }

    public void initGraph() {
        edges.add(new Edge(7, "A", "B"));
        edges.add(new Edge(5, "A", "D"));
        edges.add(new Edge(8, "B", "C"));
        edges.add(new Edge(9, "B", "D"));
        edges.add(new Edge(7, "D", "E"));
        edges.add(new Edge(5, "C", "E"));
        edges.add(new Edge(7, "B", "E"));
        edges.add(new Edge(6, "D", "F"));
        edges.add(new Edge(8, "E", "F"));
        edges.add(new Edge(9, "E", "G"));
        edges.add(new Edge(11, "F", "G"));
    }
}

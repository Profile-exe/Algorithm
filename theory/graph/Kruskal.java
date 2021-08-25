package algorithm.theory.graph;

/*
    신장 트리 (Spanning Tree)
        - 원래의 그래프의 모든 노드가 포함되어 있음
        - 원래의 그래프의 모든 노드가 연결되어 있음
        - 트리의 속성을 만족함 => 사이클이 존재하지 않음

    최소 신장 트리 (MST - Minimum Spanning Tree)
        - 가능한 Spanning Tree 중에서 간선의 가중치 합이 최소인 Spanning Tree

    Union-Find 알고리즘
        - Disjoint Set (연결되지 않은 집합)을 표현할 때 사용하는 알고리즘으로 트리 구조를 활용함
        - Disjoint Set - 서로소 집합 자료구조
            => 서로 중복되지 않는 부분집합들로 나눠진 원소들에 대한 정보를 저장하고 조작하는 자료구조
            => 공통 원소가 없는(서로소) 상호 배타적인 부분 집합들로 나눠진 원소들에 대한 자료구조
            쉽게 말해서 특정 집합에 대한 부분집합을 만들 때 서로 원소가 중복되지 않도록 하는 자료구조
            이를 이용하면 사이클이 발생하지 않는 그래프를 만들 수 있음

        1. 초기화 - n개의 원소가 개별 집합으로 이루어지도록 초기화
        
        2. Union - 두 개별 집합을 하나의 집합으로 합침 => 두 트리를 하나의 트리로 만듬
        
        3. Find - 여러 노드가 존재할 때, 두 노드를 선택해서 서로 다른 그래프에 속하는지 판별
            => 각 노드의 루트 노드를 확인

    Kruskal's Algorithm : 최소 신장 트리를 찾는 알고리즘 중 하나
        - 모든 정점을 독립적인 집합으로 만듬
        - 모든 간선을 비용을 기준으로 정렬, 비용이 작은 간선부터 양 끝의 두 정점을 비교 (간선은 서로 다른 두 정점을 연결하는 선)
        - 두 정점의 최상위 정점(root)을 확인하하고 서로 다른 경우 두 정점 연결
            => root가 다르면 사이클이 생기지 않는다.
        => 탐욕(Greedy) 알고리즘을 기초로 하고 있음. (당장의 최소 비용을 선택해서 결과적으로 최적의 솔루션 찾기)

    Kruskal 알고리즘 시간복잡도
        과정 1 : 초기화 - makeSet()
        과정 2 : edges 배열 정렬 - Collections.sort()
        과정 3 : 신장트리 구하기 - union-find 연산, mst.add()

    각 과정별 시간복잡도 - 정점 개수 : V, 간선 개수 : E

        과정 1 : 정점의 수 만큼 parent, rank 배열 초기화
            => 과정 1 시간복잡도 : O(V)

        과정 2 : edges 배열을 weight를 기준으로 정렬
            - 정렬을 위해 Edge 클래스는 Comparable<Edge> 인터페이스를 implements 한다.
            - Collections.sort()가 퀵 정렬을 사용한다 하면, n개 정렬시 O(nlogn)
            => 과정 2 시간복잡도 : O(ElogE)

        과정 3 : union-find, mst.add()
            - 각 Edge마다 수행
                => O(E)
            - union-find 연산, mst.add(), return mst
                => path compression, union-by-rank 기법 등을 이용하여 상수에 근사한 시간복잡도를 가짐
                => O(1)
            => 과정 3 시간복잡도 : O(E) * O(1) = O(E)

    총 시간 복잡도
        - 과정 1 + 과정 2 + 과정 3 = O(V) + O(ElogE) + O(E) = O(ElogE)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Kruskal {
    public static ArrayList<String> vertices;
    public static ArrayList<Edge> edges = new ArrayList<>();
    public static HashMap<String, String> parent = new HashMap<>();
    public static HashMap<String, Integer> rank = new HashMap<>();

    public static void main(String[] args) {
        Kruskal k = new Kruskal();
        System.out.println(k.kruskalFunc());
    }

    public ArrayList<Edge> kruskalFunc() {
        ArrayList<Edge> mst = new ArrayList<>();

        // 1. 초기화
        initGraph();    // graph 초기화
        for (String vertex : vertices) { makeSet(vertex); } // parent, rank 초기화

        // 2. 간선 weight 기준 정렬
        Collections.sort(edges);

        for (Edge edge : edges) {
            if (find(edge.nodeV) != find(edge.nodeU)) { // 간선이 연결된 양쪽 정점의 집합이 다른 경우
                union(edge.nodeV, edge.nodeU);
                mst.add(edge);
            }
        }

        return mst;
    }

    // find에 path compression 기법 적용 => 바로 루트 노드를 참조하도록 적용
    public String find(String node) {
        if (parent.get(node) != node) {
            parent.put(node, find(parent.get(node)));   // path compression 기법
        }
        return parent.get(node);
    }

    // 사이클이 생기지 않을 때만 unoin을 호출한다고 하면 병합만 시켜주면 된다.
    public void union(String nodeV, String nodeU) {
        String root1 = find(nodeV);
        String root2 = find(nodeU);

        // union-by-rank 기법
        if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);   // root2의 부모 노드를 root1로 변경
        } else {
            parent.put(root1, root2);   // root1의 부모 노드를 root2로 변경
            if (rank.get(root1) == rank.get(root2)) {   // rank가 같은 경우
                rank.put(root2, rank.get(root2) + 1);   // 부모 노드의 rank를 1 증가
            }
        }
    }

    // n개의 원소를 개별적인 집합으로 분리 => 초기화
    public void makeSet(String node) {
        parent.put(node, node);
        rank.put(node, 0);
    }

    static class Edge implements Comparable<Edge> {
        public int weight;
        public String nodeV;
        public String nodeU;

        public Edge(int weight, String nodeV, String nodeU) {
            this.weight = weight;
            this.nodeV = nodeV;
            this.nodeU = nodeU;
        }

        @Override
        public String toString() {
            return "(" + this.weight + "," + this.nodeV + "," + this.nodeU + ")";
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }
    }

    public void initGraph() {
        vertices = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
        edges.add(new Edge(7, "A", "B"));
        edges.add(new Edge(5, "A", "D"));
        edges.add(new Edge(7, "B", "A"));
        edges.add(new Edge(8, "B", "C"));
        edges.add(new Edge(9, "B", "D"));
        edges.add(new Edge(7, "B", "E"));
        edges.add(new Edge(8, "C", "B"));
        edges.add(new Edge(5, "C", "E"));
        edges.add(new Edge(5, "D", "A"));
        edges.add(new Edge(9, "D", "B"));
        edges.add(new Edge(7, "D", "E"));
        edges.add(new Edge(6, "D", "F"));
        edges.add(new Edge(7, "E", "B"));
        edges.add(new Edge(5, "E", "C"));
        edges.add(new Edge(7, "E", "D"));
        edges.add(new Edge(8, "E", "F"));
        edges.add(new Edge(9, "E", "G"));
        edges.add(new Edge(6, "F", "D"));
        edges.add(new Edge(8, "F", "E"));
        edges.add(new Edge(11, "F", "G"));
        edges.add(new Edge(9, "G", "E"));
        edges.add(new Edge(11, "G", "F"));
    }
}
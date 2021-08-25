package algorithm.theory.graph;

/*
    개선된 Prim's Algorithm : 간선이 아닌 **노드를 중심**으로 우선순위 큐를 적용하는 방식
        - 초기화 : 정점(key)구조를 만들어 놓고 특정 정점의 key값은 0, 이외는 INF로 놓음. 모든 정점을 우선순위 큐에 삽입
        - 가장 작은 key값을 가진 정점 추출 후 추출된 정점의 인접 정점들에 대해
            => key 값과 연결된 가중치 값 비교 : key값이 작으면 해당 정점의 key값 갱신
            => key 값 갱신 시 우선순위 큐는 heap 구조를 유지하도록 함 (decrease key)

    개선된 Prim 알고리즘 시간복잡도
        - 최초 key 생성 (초기화) 시간복잡도 : O(V)
        - while 구문, keys.poll() 시간복잡도 : O(VlogV)
            => while 구문은 V번 실행됨
            => heap에서 최소 key값을 가지는 노드는 정보 추출(pop)시 O(logV)
        - for 구문 : O(ElogV)
            => while 구문 반복 시 for 구문은 총 최대 간선 수 E만큼 실행 가능 : O(E)
            => key 값 변경 시마다 heap 구조를 변경해야 하고 heap에는 최대 V개 정보 : O(logV)
        
        > 일반적인 heap 자료 구조 자체에는 본래 heap 내부의 데이터 우선순위 변경 시,
          최초 우선순위 데이터를 루트노드로 만들어주는 로직은 없음. 이를 decrease key 로직이라 함
          해당 로직은 heapdict 라이브러리를 활용해서 간단히 적용 가능

    총 시간 복잡도
        -  O(V + VlogV + ElogV) => O((V + E)logV) => O(ElogV)
            => E > V (최대 V^2 = E)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class enhancedPrim {
    public static HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();

    public static void main(String[] args) {
        enhancedPrim p = new enhancedPrim();
        System.out.println(p.enhancedPrimFunc("A"));
    }

    public ArrayList<Path> enhancedPrimFunc(String startNode) {
        Integer totalWeight = 0;
        Edge edgeObject, poppedEdge, linkedEdge;
        HashMap<String, Integer> linkedEdges;
        ArrayList<Path> mst = new ArrayList<>();
        PriorityQueue<Edge> keys = new PriorityQueue<>();
        HashMap<String, String> mstPath = new HashMap<>();
        HashMap<String, Edge> keysObjects = new HashMap<>();

        // 초기화 시작 *******************************************************
        initGraph();

        for (String key : graph.keySet()) { // 그래프의 모든 노드를 가지고오기
            // key 값을 업데이트 해야하는 경우 삭제 후 다시 넣는다. (힙 유지)
            if (key == startNode) {
                edgeObject = new Edge(key, 0);
                mstPath.put(key, key);
            } else {
                edgeObject = new Edge(key, Integer.MAX_VALUE);
                mstPath.put(key, null);
            }
            keys.add(edgeObject);
            keysObjects.put(key, edgeObject);
        }
        // 초기화 완료 *******************************************************

        // Prim 알고리즘 시작 *************************************************
        while (!keys.isEmpty()) {
            poppedEdge = keys.poll();
            keysObjects.remove(poppedEdge.node);

            mst.add(new Path(mstPath.get(poppedEdge.node), poppedEdge.node, poppedEdge.weight));
            totalWeight += poppedEdge.weight;

            linkedEdges = graph.get(poppedEdge.node);   // 인접 간선 정보 불러오기
            for (String adjacent : linkedEdges.keySet()) {  // 인접 간선 순회
                if (keysObjects.containsKey(adjacent)) {    // 해당 간선이 선택되지 않은 경우
                    linkedEdge = keysObjects.get(adjacent);
                    // 꺼낸 노드에서 인접 간선까지의 가중치가 인접 간선에 저장된 가중치보다 작은 경우
                    if (linkedEdges.get(adjacent) < linkedEdge.weight) {
                        // 가중치를 update 시키고 mst에 포함
                        linkedEdge.weight = linkedEdges.get(adjacent);
                        mstPath.put(adjacent, poppedEdge.node);

                        keys.remove(linkedEdge);
                        keys.add(linkedEdge);
                    }
                }
            }
        }
        // Prim 알고리즘 끝 ***************************************************

        return mst;
    }

    static class Path {
        public String node1;
        public String node2;
        public int weight;

        public Path(String node1, String node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + this.node1 + ", " + this.node2 + ", " + this.weight + ")";
        }
    }

    static class Edge implements Comparable<Edge> {
        public String node;
        public int weight;

        public Edge(String node, int weight) {
            this.weight = weight;
            this.node = node;
        }

        @Override
        public String toString() {
            return "(" + this.weight + ", " + this.node + ")";
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }
    }

    public void initGraph() {
        HashMap<String, Integer> edges;
        edges = new HashMap<>();
        edges.put("B", 7);
        edges.put("D", 5);
        graph.put("A", edges);

        edges = new HashMap<>();
        edges.put("A", 7);
        edges.put("D", 9);
        edges.put("C", 8);
        edges.put("E", 7);
        graph.put("B", edges);

        edges = new HashMap<>();
        edges.put("B", 8);
        edges.put("E", 5);
        graph.put("C", edges);

        edges = new HashMap<>();
        edges.put("A", 5);
        edges.put("B", 9);
        edges.put("E", 7);
        edges.put("F", 6);
        graph.put("D", edges);

        edges = new HashMap<>();
        edges.put("B", 7);
        edges.put("C", 5);
        edges.put("D", 7);
        edges.put("F", 8);
        edges.put("G", 9);
        graph.put("E", edges);

        edges = new HashMap<>();
        edges.put("D", 6);
        edges.put("E", 8);
        edges.put("G", 11);
        graph.put("F", edges);

        edges = new HashMap<>();
        edges.put("E", 9);
        edges.put("F", 11);
        graph.put("G", edges);
    }
}

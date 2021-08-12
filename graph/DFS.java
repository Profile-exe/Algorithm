package algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DFS {
    public static ArrayList<String> visited = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        DFS d = new DFS();
        d.initGraph();

        d.dfsRecursive("A");
        System.out.println(visited);

        visited.clear();

        d.dfsLoop("A");
        System.out.println(visited);
    }

    public void dfsRecursive(String startNode) {
        visited.add(startNode);

        for (String node : graph.get(startNode)) {
            if (!visited.contains(node)) {
                dfsRecursive(node);
            }
        }
    }

    public void dfsLoop(String startNode) {
        ArrayList<String> needVisit = new ArrayList<>();

        needVisit.add(startNode);
        while (!needVisit.isEmpty()) {  // 스택이 빌 때까지 진행
            // 끝에서부터 꺼내온다 => 스택
            String node = needVisit.remove(needVisit.size() - 1);

            // 방문하지 않은 노드라면 방문 표시 후 스택에 넣는다.
            if (!visited.contains(node)) {
                visited.add(node);
                needVisit.addAll(graph.get(node));
            }
        }
    }

    public void initGraph() {
        graph.put("A", new ArrayList<>(Arrays.asList("B", "C")));
        graph.put("B", new ArrayList<>(Arrays.asList("A", "D")));
        graph.put("C", new ArrayList<>(Arrays.asList("A", "G", "H", "I")));
        graph.put("D", new ArrayList<>(Arrays.asList("B", "E", "F")));
        graph.put("E", new ArrayList<>(Arrays.asList("D")));
        graph.put("F", new ArrayList<>(Arrays.asList("D")));
        graph.put("G", new ArrayList<>(Arrays.asList("C")));
        graph.put("H", new ArrayList<>(Arrays.asList("C")));
        graph.put("I", new ArrayList<>(Arrays.asList("C", "J")));
        graph.put("J", new ArrayList<>(Arrays.asList("I")));
    }
}

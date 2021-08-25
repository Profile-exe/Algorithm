package algorithm.theory.graph;

import java.util.*;

public class BFS {
    public static ArrayDeque<String> queue = new ArrayDeque<>();
    public static ArrayList<String> visited = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        BFS b = new BFS();
        b.initGraph();

        queue.add("A");
        visited.add("A");
        b.bfsRecursive();
        System.out.println(visited);

        visited.clear();

        b.bfsLoop("A");
        System.out.println(visited);
    }

    public void bfsRecursive() {
        if (queue.isEmpty()) return;

        String startNode = queue.poll();
        for (String node : graph.get(startNode)) {
            if (!visited.contains(node)) {
                visited.add(node);
                queue.add(node);
            }
        }
        bfsRecursive();
    }

    public void bfsLoop(String startNode) {
        queue.add(startNode);
        while (!queue.isEmpty()) {
            // 앞에서부터 꺼내온다 => 큐
            String node = queue.poll();

            // 방문하지 않은 노드라면 방문 표시 후 큐에 넣는다.
            if (!visited.contains(node)) {
                visited.add(node);
                queue.addAll(graph.get(node));
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

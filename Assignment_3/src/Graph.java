import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    public void dfs(int start, boolean[] visited, String order) {
        visited[start] = true;
        System.out.print(start + " ");

        List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(start, Collections.emptyList()));
        neighbors.sort(Integer::compareTo);

        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited, order);
            }
        }
    }

    public void bfs(int start, String order) {
        boolean[] visited = new boolean[graph.size() + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(current, Collections.emptyList()));
            neighbors.sort(Integer::compareTo);

            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
    }

    public boolean isBipartite() {
        Map<Integer, Integer> color = new HashMap<>();

        for (int node : graph.keySet()) {
            if (!color.containsKey(node) && !bfsColor(node, color)) {
                return false;
            }
        }

        return true;
    }

    private boolean bfsColor(int start, Map<Integer, Integer> color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color.put(start, 0);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(current, Collections.emptyList()));
            neighbors.sort(Integer::compareTo);

            for (int neighbor : neighbors) {
                if (!color.containsKey(neighbor)) {
                    color.put(neighbor, 1 - color.get(current));
                    queue.add(neighbor);
                } else if (color.get(neighbor).equals(color.get(current))) {
                    return false;
                }
            }
        }

        return true;
    }

    public void findCycles(int start, boolean[] visited, List<Integer> path, List<List<Integer>> cycles) {
        visited[start] = true;
        path.add(start);

        List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(start, Collections.emptyList()));
        neighbors.sort(Integer::compareTo);

        for (int neighbor : neighbors) {
            if (!path.contains(neighbor)) {
                if (!visited[neighbor]) {
                    findCycles(neighbor, Arrays.copyOf(visited, visited.length), new ArrayList<>(path), cycles);
                }
            } else {
                int index = path.indexOf(neighbor);
                cycles.add(path.subList(index, path.size()));
            }
        }
    }

    public void printCycles() {
        boolean[] visited = new boolean[graph.size() + 1];
        List<List<Integer>> cycles = new ArrayList<>();

        for (int node : graph.keySet()) {
            if (!visited[node]) {
                findCycles(node, visited, new ArrayList<>(), cycles);
            }
        }

        for (List<Integer> cycle : cycles) {
            for (int node : cycle) {
                System.out.print(node);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 1);
        g.addEdge(4, 2);

        System.out.println("DFS:");
        g.dfs(1, new boolean[g.graph.size() + 1], "left");
        System.out.println("\nBFS:");
        g.bfs(1, "left");
        System.out.println("\nBipartite: " + g.isBipartite());
        System.out.println("Cycles:");
        g.printCycles();
    }
}

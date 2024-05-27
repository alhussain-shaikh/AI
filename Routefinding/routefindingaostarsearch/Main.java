import java.util.*;

public class Main {
    private static class Node {
        String name;
        int heuristic; // Heuristic value h(n)
        List<Node> children = new ArrayList<>(); // Successor nodes
        boolean isSolved;
        Node parent;
        int cost; // Cost from start to this node g(n)

        Node(String name, int heuristic) {
            this.name = name;
            this.heuristic = heuristic;
            this.isSolved = false;
            this.cost = Integer.MAX_VALUE;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
    private Node start;
    private Node goal;

    public Main(String startName, String goalName) {
        this.start = new Node(startName, 366); // Heuristic for Arad
        this.goal = new Node(goalName, 0); // Heuristic for Bucharest
        nodes.put(startName, start);
        nodes.put(goalName, goal);
    }

    public void addEdge(String sourceName, String destName, int distance) {
        Node source = nodes.get(sourceName);
        Node dest = nodes.computeIfAbsent(destName, k -> new Node(k, 0)); // Heuristic will be set later
        source.children.add(dest);
        // Assuming edge length is considered as 1 for all paths
        dest.cost = distance; // Set the cost for the destination node
    }

    public List<String> findPath() {
        // Implementing AO* requires maintaining a graph structure that supports AND-OR logic.
        // This code snippet provides a simplified version that does not fully implement AO* logic.
        // For a complete implementation, additional logic to handle AND-OR structures is needed.

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic + n.cost));
        start.cost = 0;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (current.equals(goal)) {
                current.isSolved = true;
                return constructPath(current);
            }

            for (Node child : current.children) {
                if (!child.isSolved) {
                    openList.add(child);
                }
            }
            current.isSolved = true;
        }
        return null; // No path found
    }

    private List<String> constructPath(Node node) {
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node.name);
            node = node.parent;
        }
        return path;
    }

    public static void main(String[] args) {
        Main aoStar = new Main("Arad", "Bucharest");
        // Add edges and heuristics here
        aoStar.addEdge("Arad", "Zerind", 75);
        aoStar.addEdge("Arad", "Timisoara", 118);
        aoStar.addEdge("Arad", "Sibiu", 140);
        aoStar.addEdge("Zerind", "Oradea", 71);
        aoStar.addEdge("Oradea", "Sibiu", 151);
        aoStar.addEdge("Timisoara", "Lugoj", 111);
        aoStar.addEdge("Lugoj", "Mehadia", 70);
        aoStar.addEdge("Mehadia", "Drobeta", 75);
        aoStar.addEdge("Drobeta", "Craiova", 120);
        aoStar.addEdge("Craiova", "Rimnicu Vilcea", 146);
        aoStar.addEdge("Craiova", "Pitesti", 138);
        aoStar.addEdge("Sibiu", "Fagaras", 99);
        aoStar.addEdge("Sibiu", "Rimnicu Vilcea", 80);
        aoStar.addEdge("Rimnicu Vilcea", "Pitesti", 97);
        aoStar.addEdge("Fagaras", "Bucharest", 211);
        aoStar.addEdge("Pitesti", "Bucharest", 101);
        aoStar.addEdge("Bucharest", "Urziceni", 85);
        aoStar.addEdge("Bucharest", "Giurgiu", 90);
        aoStar.addEdge("Urziceni", "Vaslui", 142);
        aoStar.addEdge("Vaslui", "Iasi", 92);
        aoStar.addEdge("Iasi", "Neamt", 87);
        aoStar.addEdge("Urziceni", "Hirsova", 98);
        aoStar.addEdge("Hirsova", "Eforie", 86);
        // ... add other edges ...

        List<String> path = aoStar.findPath();
        if (path != null) {
            System.out.println("Path: " + path);
        } else {
            System.out.println("No path found.");
        }
    }
}

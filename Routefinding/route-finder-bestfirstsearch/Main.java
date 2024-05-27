import java.util.*;

public class Main {
    private static class Node implements Comparable<Node> {
        String name;
        Map<Node, Integer> neighbours = new HashMap<>();
        int heuristic; // Straight-line distance to Bucharest
        Node parent;

        Node(String name, int heuristic) {
            this.name = name;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.heuristic, other.heuristic);
        }

        @Override
        public String toString() {
            return name + " (h=" + heuristic + ")";
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

    public void addEdge(String sourceName, String destName, int distance, int destHeuristic) {
        Node source = nodes.get(sourceName);
        Node dest = nodes.computeIfAbsent(destName, k -> new Node(k, destHeuristic));
        source.neighbours.put(dest, distance);
    }

    public List<String> findPath() {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            System.out.println("Processing node: " + current);

            if (current.equals(goal)) {
                return constructPath(current);
            }
            closedList.add(current);

            for (Map.Entry<Node, Integer> entry : current.neighbours.entrySet()) {
                Node neighbour = entry.getKey();
                if (!closedList.contains(neighbour)) {
                    neighbour.parent = current;
                    openList.add(neighbour);
                    System.out.println("Added to open list: " + neighbour);
                }
            }

            System.out.println("Open list: " + openList);
            System.out.println("Closed list: " + closedList);
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
        Main bfs = new Main("Arad", "Bucharest");
        bfs.addEdge("Arad", "Zerind", 75, 374);
        bfs.addEdge("Arad", "Timisoara", 118, 329);
        bfs.addEdge("Arad", "Sibiu", 140, 253);
        bfs.addEdge("Zerind", "Oradea", 71, 380);
        bfs.addEdge("Oradea", "Sibiu", 151, 253);
        bfs.addEdge("Timisoara", "Lugoj", 111, 244);
        bfs.addEdge("Lugoj", "Mehadia", 70, 241);
        bfs.addEdge("Mehadia", "Drobeta", 75, 242);
        bfs.addEdge("Drobeta", "Craiova", 120, 160);
        bfs.addEdge("Craiova", "Rimnicu Vilcea", 146, 193);
        bfs.addEdge("Craiova", "Pitesti", 138, 98);
        bfs.addEdge("Sibiu", "Fagaras", 99, 178);
        bfs.addEdge("Sibiu", "Rimnicu Vilcea", 80, 193);
        bfs.addEdge("Rimnicu Vilcea", "Pitesti", 97, 98);
        bfs.addEdge("Fagaras", "Bucharest", 211, 0);
        bfs.addEdge("Pitesti", "Bucharest", 101, 0);
        bfs.addEdge("Bucharest", "Urziceni", 85, 80);
        bfs.addEdge("Bucharest", "Giurgiu", 90, 77);
        bfs.addEdge("Urziceni", "Vaslui", 142, 199);
        bfs.addEdge("Vaslui", "Iasi", 92, 226);
        bfs.addEdge("Iasi", "Neamt", 87, 234);
        bfs.addEdge("Urziceni", "Hirsova", 98, 151);
        bfs.addEdge("Hirsova", "Eforie", 86, 161);

        List<String> path = bfs.findPath();
        if (path != null) {
            System.out.println("Path: " + path);
        } else {
            System.out.println("No path found.");
        }
    }
}

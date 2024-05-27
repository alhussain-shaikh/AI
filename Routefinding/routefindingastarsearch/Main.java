import java.util.*;

public class Main {
    private static class Node implements Comparable<Node> {
        String name;
        Map<Node, Integer> neighbours = new HashMap<>();
        int g; // Cost from start to this node
        int h; // Heuristic cost from this node to goal
        Node parent;

        Node(String name, int h) {
            this.name = name;
            this.h = h;
            this.g = Integer.MAX_VALUE;
        }

        int f() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f(), other.f());
        }

        @Override
        public String toString() {
            return name + " (g=" + g + ", h=" + h + ", f=" + f() + ")";
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
        start.g = 0;
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
                int tentativeG = current.g + entry.getValue();

                if (tentativeG < neighbour.g) {
                    neighbour.parent = current;
                    neighbour.g = tentativeG;
                    if (!openList.contains(neighbour) && !closedList.contains(neighbour)) {
                        openList.add(neighbour);
                        System.out.println("Added to open list: " + neighbour);
                    } else if (openList.contains(neighbour)) {
                        openList.remove(neighbour);
                        openList.add(neighbour);
                        System.out.println("Updated node in open list: " + neighbour);
                    }
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
        Main astar = new Main("Arad", "Bucharest");
        astar.addEdge("Arad", "Zerind", 75, 374);
        astar.addEdge("Arad", "Timisoara", 118, 329);
        astar.addEdge("Arad", "Sibiu", 140, 253);
        astar.addEdge("Zerind", "Oradea", 71, 380);
        astar.addEdge("Oradea", "Sibiu", 151, 253);
        astar.addEdge("Timisoara", "Lugoj", 111, 244);
        astar.addEdge("Lugoj", "Mehadia", 70, 241);
        astar.addEdge("Mehadia", "Drobeta", 75, 242);
        astar.addEdge("Drobeta", "Craiova", 120, 160);
        astar.addEdge("Craiova", "Rimnicu Vilcea", 146, 193);
        astar.addEdge("Craiova", "Pitesti", 138, 98);
        astar.addEdge("Sibiu", "Fagaras", 99, 178);
        astar.addEdge("Sibiu", "Rimnicu Vilcea", 80, 193);
        astar.addEdge("Rimnicu Vilcea", "Pitesti", 97, 98);
        astar.addEdge("Fagaras", "Bucharest", 211, 0);
        astar.addEdge("Pitesti", "Bucharest", 101, 0);
        astar.addEdge("Bucharest", "Urziceni", 85, 80);
        astar.addEdge("Bucharest", "Giurgiu", 90, 77);
        astar.addEdge("Urziceni", "Vaslui", 142, 199);
        astar.addEdge("Vaslui", "Iasi", 92, 226);
        astar.addEdge("Iasi", "Neamt", 87, 234);
        astar.addEdge("Urziceni", "Hirsova", 98, 151);
        astar.addEdge("Hirsova", "Eforie", 86, 161);

        // ... add other edges with their respective heuristics ...

        List<String> path = astar.findPath();
        if (path != null) {
            System.out.println("Path: " + path);
        } else {
            System.out.println("No path found.");
        }
    }
}

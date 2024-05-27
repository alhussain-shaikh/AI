import java.util.*;

public class Main {
    private static class Node {
        String name;
        Map<Node, Integer> neighbours = new HashMap<>();
        Node parent;
        int pathCost;

        Node(String name) {
            this.name = name;
            this.pathCost = Integer.MAX_VALUE;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
    private Node start;
    private Node goal;

    public Main(String startName, String goalName) {
        this.start = new Node(startName);
        this.goal = new Node(goalName);
        nodes.put(startName, start);
        nodes.put(goalName, goal);
    }

    public void addEdge(String sourceName, String destName, int distance) {
        Node source = nodes.computeIfAbsent(sourceName, Node::new);
        Node dest = nodes.computeIfAbsent(destName, Node::new);
        source.neighbours.put(dest, distance);
        dest.neighbours.put(source, distance);
    }

    public List<String> findShortestPath() {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> explored = new HashSet<>();
        start.pathCost = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            printCurrentState(current);

            if (current.equals(goal)) {
                return constructPath(current);
            }
            explored.add(current);

            for (Map.Entry<Node, Integer> entry : current.neighbours.entrySet()) {
                Node neighbour = entry.getKey();
                int newCost = current.pathCost + entry.getValue();
                if (!explored.contains(neighbour) && !queue.contains(neighbour)) {
                    neighbour.parent = current;
                    neighbour.pathCost = newCost;
                    queue.add(neighbour);
                } else if (queue.contains(neighbour) && newCost < neighbour.pathCost) {
                    neighbour.parent = current;
                    neighbour.pathCost = newCost;
                }
            }
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

    private void printCurrentState(Node current) {
        LinkedList<String> path = new LinkedList<>();
        Node temp = current;
        while (temp != null) {
            path.addFirst(temp.name);
            temp = temp.parent;
        }
        System.out.println("Current path: " + path + " | Cost: " + current.pathCost);
    }

    public static void main(String[] args) {
        Main finder = new Main("Arad", "Bucharest");
        finder.addEdge("Arad", "Zerind", 75);
        finder.addEdge("Arad", "Timisoara", 118);
        finder.addEdge("Arad", "Sibiu", 140);
        finder.addEdge("Zerind", "Oradea", 71);
        finder.addEdge("Oradea", "Sibiu", 151);
        finder.addEdge("Timisoara", "Lugoj", 111);
        finder.addEdge("Lugoj", "Mehadia", 70);
        finder.addEdge("Mehadia", "Drobeta", 75);
        finder.addEdge("Drobeta", "Craiova", 120);
        finder.addEdge("Craiova", "Rimnicu Vilcea", 146);
        finder.addEdge("Craiova", "Pitesti", 138);
        finder.addEdge("Sibiu", "Fagaras", 99);
        finder.addEdge("Sibiu", "Rimnicu Vilcea", 80);
        finder.addEdge("Rimnicu Vilcea", "Pitesti", 97);
        finder.addEdge("Fagaras", "Bucharest", 211);
        finder.addEdge("Pitesti", "Bucharest", 101);
        finder.addEdge("Bucharest", "Urziceni", 85);
        finder.addEdge("Bucharest", "Giurgiu", 90);
        finder.addEdge("Urziceni", "Vaslui", 142);
        finder.addEdge("Vaslui", "Iasi", 92);
        finder.addEdge("Iasi", "Neamt", 87);
        finder.addEdge("Urziceni", "Hirsova", 98);
        finder.addEdge("Hirsova", "Eforie", 86);
        // ... add other edges ...

        List<String> shortestPath = finder.findShortestPath();
        if (shortestPath != null) {
            System.out.println("Shortest path: " + shortestPath);
            System.out.println("Total cost: " + finder.goal.pathCost);
        } else {
            System.out.println("No path found.");
        }
    }
}

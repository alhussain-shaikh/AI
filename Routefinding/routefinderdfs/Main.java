import java.util.*;

public class Main {
    private static class Node {
        String name;
        Map<Node, Integer> neighbours;
        Node parent;

        Node(String name) {
            this.name = name;
            this.neighbours = new HashMap<>();
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
    private Node start;
    private Node goal;
    private List<Node> bestPath = new ArrayList<>();
    private List<Node> currentPath = new ArrayList<>();
    private int bestPathLength = Integer.MAX_VALUE;

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

    public List<String> findPath() {
        dfs(start, new HashSet<>(), 0);
        List<String> pathNames = new ArrayList<>();
        for (Node node : bestPath) {
            pathNames.add(node.name);
        }
        return pathNames;
    }

    private boolean dfs(Node current, Set<Node> visited, int currentPathLength) {
        visited.add(current);
        currentPath.add(current);

        // Print current path and cost
        printCurrentPath(currentPath, currentPathLength);

        if (current.equals(goal)) {
            if (currentPathLength < bestPathLength) {
                bestPathLength = currentPathLength;
                bestPath = new ArrayList<>(currentPath);
            }
            visited.remove(current);
            currentPath.remove(current);
            return true;
        }

        for (Map.Entry<Node, Integer> entry : current.neighbours.entrySet()) {
            Node neighbour = entry.getKey();
            int distance = entry.getValue();
            if (!visited.contains(neighbour)) {
                neighbour.parent = current;
                dfs(neighbour, visited, currentPathLength + distance);
            }
        }

        visited.remove(current);
        currentPath.remove(current);
        return false;
    }

    private void printCurrentPath(List<Node> currentPath, int currentPathLength) {
        StringBuilder pathString = new StringBuilder();
        for (Node node : currentPath) {
            pathString.append(node.name).append(" -> ");
        }
        if (pathString.length() > 0) {
            pathString.setLength(pathString.length() - 4); // Remove the last " -> "
        }
        System.out.println("Current path: " + pathString.toString() + " | Cost: " + currentPathLength);
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

        List<String> route = finder.findPath();
        System.out.println("Path found: " + route);
        System.out.println("Total cost: " + finder.bestPathLength);
    }
}

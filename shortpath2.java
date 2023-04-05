package Shortestpath;
import java.util.*;

public class shortpath2 {
        // Define the graph as an adjacency list
        static Map<Integer, List<Edge>> graph = new HashMap<>();
        static {
            graph.put(1, Arrays.asList(new Edge(2, 2), new Edge(3, 3), new Edge(4, 6), new Edge(5, 9)));
            graph.put(2, Arrays.asList(new Edge(1, 2), new Edge(3, 7), new Edge(4, 5), new Edge(5, 1)));
            graph.put(3, Arrays.asList(new Edge(1, 3), new Edge(2, 7), new Edge(4, 2), new Edge(5, 5)));
            graph.put(4, Arrays.asList(new Edge(1, 6), new Edge(2, 5), new Edge(3, 2), new Edge(5, 4)));
            graph.put(5, Arrays.asList(new Edge(1, 9), new Edge(2, 1), new Edge(3, 5), new Edge(4, 4)));
        }

        public static void main(String[] args) {

            // Prompt user to enter starting and ending points
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter starting point (1-5): ");
            int start = scanner.nextInt();
            System.out.print("Enter ending point (1-5): ");
            int end = scanner.nextInt();
            scanner.close();

            // Call Dijkstra's algorithm to find the shortest path
            PathResult pathResult = dijkstra(graph, start, end);
            int shortestDistance = pathResult.distances[end];
            List<Integer> shortestPath = pathResult.paths.get(end);

            // Print the shortest distance and path to the console
            System.out.println("The shortest distance from " + start + " to " + end + " is " + shortestDistance);
            System.out.print("The shortest path is: ");
            for (int i = 0; i < shortestPath.size(); i++) {
                System.out.print(shortestPath.get(i));
                if (i < shortestPath.size() - 1) {
                    System.out.print(" -> ");
                }
            }
        }

        public static PathResult dijkstra(Map<Integer, List<Edge>> graph, int start, int end) {

            int numVertices = graph.size();
            int[] distances = new int[numVertices+1];
            Map<Integer, List<Integer>> paths = new HashMap<>();
            boolean[] visited = new boolean[numVertices+1];
            PriorityQueue<Edge> pq = new PriorityQueue<>();

            // Initialize distances to infinity and visited to false
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start] = 0;
            visited[start] = true;

            // Add all edges of the starting vertex to the priority queue
            List<Edge> edges = graph.get(start);
            for (Edge edge : edges) {
                pq.add(edge);
                distances[edge.to] = edge.weight;
                paths.put(edge.to, new ArrayList<>(Arrays.asList(start, edge.to)));
            }

            // Apply Dijkstra's algorithm
            while (!pq.isEmpty()) {
                Edge currEdge = pq.poll();
                int currVertex = currEdge.to;
                if (visited[currVertex]) {
                    continue;
                }
                visited[currVertex] = true;
                List<Edge> currEdges = graph.get(currVertex);
                for (Edge         nextEdge : currEdges) {
                    int nextVertex = nextEdge.to;
                    int edgeWeight = nextEdge.weight;
                    int newDistance = distances[currVertex] + edgeWeight;
                    if (!visited[nextVertex] && newDistance < distances[nextVertex]) {
                        distances[nextVertex] = newDistance;
                        pq.add(new Edge(nextVertex, newDistance));
                        List<Integer> newPath = new ArrayList<>(paths.get(currVertex));
                        newPath.add(nextVertex);
                        paths.put(nextVertex, newPath);
                    }
                }
            }

            // Return the shortest distances and paths
            return new PathResult(distances, paths);
        }

        static class Edge implements Comparable<Edge> {
            int to;
            int weight;
            public Edge(int to, int weight) {
                this.to = to;
                this.weight = weight;
            }
            public int compareTo(Edge other) {
                return Integer.compare(weight, other.weight);
            }
        }

        static class PathResult {
            int[] distances;
            Map<Integer, List<Integer>> paths;
            public PathResult(int[] distances, Map<Integer, List<Integer>> paths) {
                this.distances = distances;
                this.paths = paths;
            }
        }
    }

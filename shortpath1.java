package Shortestpath;
import java.util.*;

public class shortpath1 {
    static Map<Integer, List<Edge>> graph = new HashMap<>();
    static{
        graph.put(1, Arrays.asList(new Edge(2, 8), new Edge(3, 5)));
        graph.put(2, Arrays.asList(new Edge(1, 8), new Edge(4, 2)));
        graph.put(3, Arrays.asList(new Edge(1, 5), new Edge(4, 1)));
        graph.put(4, Arrays.asList(new Edge(2, 2), new Edge(3, 1), new Edge(5, 7)));
        graph.put(5, Arrays.asList(new Edge(4, 7)));
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter starting point (1-5): ");
        int start = scanner.nextInt();
        System.out.print("Enter ending point (1-5): ");
        int end = scanner.nextInt();
        scanner.close();

        int[] distance = dijkstra(graph, start);
        int shortestDistance = distance[end];
        System.out.println("The shortest distance from " + start + " to " + end + " is " + shortestDistance);
    }

    public static int[] dijkstra(Map<Integer, List<Edge>> graph, int start){
        int numVertices = graph.size();
        int[] distance = new int[numVertices+1];
        boolean[] visited = new boolean[numVertices+1];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;
        visited[start] = true;

        List<Edge> edges = graph.get(start);
        for(Edge edge : edges){
            pq.add(edge);
            distance[edge.to] = edge.weight;
        }
        while(!pq.isEmpty()){
            Edge currEdge = pq.poll();
            int currVertex = currEdge.to;
            if(visited[currVertex]){
                continue;
            }
            visited[currVertex] = true;
            List<Edge> currEdges = graph.get(currVertex);
            for(Edge edge : currEdges){
                if(!visited[edge.to]){
                    int newDistance = distance[currVertex] + edge.weight;
                    if(newDistance<distance[edge.to]){
                        distance[edge.to] = newDistance;
                        pq.add(new Edge(edge.to, newDistance));
                    }
                }
            }
        }
        return distance;
    }
    static class Edge implements Comparable<Edge>{
        int to;
        int weight;
        public Edge(int to, int weight){
            this.to = to;
            this.weight = weight;
        }
        public int compareTo(Edge other){
            return Integer.compare(weight, other.weight);
        }
    }
}

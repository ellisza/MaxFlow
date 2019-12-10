import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    int vertexCt;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency list for graph.
    Node[] graph = new Node[vertexCt];
    String graphName;  //The file from which the graph was created.

    public Graph() {
        this.vertexCt = 0;
        this.graphName = "";
    }

    public static void main(String[] args) {
        Graph graph1 = new Graph();
        graph1.makeGraph("demands1.txt");
        System.out.println(graph1.toString());
        int maxFlow = graph1.maxFlowAlgorithm(graph1);
        System.out.println("The max flow is " + maxFlow);
    }

    public int maxFlowAlgorithm(Graph g){
        int maxFlow = 0;
        int sink = getVertexCt();

        while (true) {
            // Parent array used for storing path
            // (parent[i] stores edge used to get to node i)
            Edge[] parent = new Edge[vertexCt];

            ArrayList<Node> q = new ArrayList<>();
            q.add(g.graph[0]);

            // BFS finding shortest augmenting path
            while (!q.isEmpty()) {
                Node curr = q.remove(0);

                // Checks that edge has not yet been visited, and it doesn't
                // point to the source, and it is possible to send flow through it.
                for (Edge e : curr.edges)
                    if (parent[e.from] == null && e.from != 0 && e.capacity > e.flow) {
                        parent[e.from] = e;
                        q.add(g.graph[e.from]);
                    }
            }

            // If sink was NOT reached, no augmenting path was found.
            // Algorithm terminates and prints out max flow.
            if (parent[sink] == null)
                break;

            // If sink WAS reached, we will push more flow through the path
            int pushFlow = Integer.MAX_VALUE;

            // Finds maximum flow that can be pushed through given path
            // by finding the minimum residual flow of every edge in the path
            for (Edge e = parent[sink]; e != null; e = parent[e.to - 1])
                pushFlow = Math.min(pushFlow, e.capacity - e.flow);

            // Adds to flow values and subtracts from reverse flow values in path
            for (Edge e = parent[sink]; e != null; e = parent[e.to - 1]) {
                e.flow += pushFlow;
                e.reverse.flow -= pushFlow;
            }

            maxFlow += pushFlow;
        }
        return maxFlow;
    }

    public int getVertexCt() {
        return vertexCt;
    }


    public boolean addEdge(int source, int destination, int cap) {
        //System.out.println("addEdge " + source + "->" + destination + "(" + cap + ")");
        if (source < 0 || source >= vertexCt) return false;
        if (destination < 0 || destination >= vertexCt) return false;
        //add edge


        G[source].addEdge(source, destination, cap);

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The Graph " + graphName + " \n");

        for (int i = 0; i < vertexCt; i++) {
            sb.append(graph[i].toString());
        }
        return sb.toString();
    }

    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            graph = new Node[vertexCt];
            for (int i = 0; i < vertexCt; i++) {
                graph[i] = new Node(i);
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                int cap = reader.nextInt();

                Edge a = new Edge(v1, v2, 0, cap);
                Edge b = new Edge(v2, v1, 0, 0);

                a.setReverse(b);
                b.setReverse(a);

                graph[v1].edges.add(a);
                graph[v2].edges.add(b);
                //if (!addEdge(v1, v2, cap))
                //    throw new Exception();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
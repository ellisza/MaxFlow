import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Arrays;

public class Graph {
    int vertexCt;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency list for graph.
    String graphName;  //The file from which the graph was created.
    long flow[][];

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
        int flowFin = 0;
        int source = 0;
        int destination = getVertexCt() - 1;

        PriorityQueue<Integer> Q = new PriorityQueue<>();
        PriorityQueue<Integer> arbQ = new PriorityQueue<>();
        Q.add(g.G[source].nodeID);

        while(!Q.isEmpty()){
            GraphNode current = g.G[Q.remove()];
            if(!arbQ.contains(current.nodeID)){
                arbQ.add(current.nodeID);
            }
            if(current.nodeID != G.length - 1){
                for(GraphNode.EdgeInfo item : current.succ){
                    if(item.to != g.getVertexCt() - 1){
                        if(!arbQ.contains(item.to)){
                            Q.add(item.to);
                        }
                    }
                }
            }
        }

        System.out.println(arbQ.toString());
        System.out.println(Q.toString());

        return flowFin;
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
            sb.append(G[i].toString());
        }
        return sb.toString();
    }

    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            G = new GraphNode[vertexCt];
            for (int i = 0; i < vertexCt; i++) {
                G[i] = new GraphNode(i);
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                int cap = reader.nextInt();
                if (!addEdge(v1, v2, cap))
                    throw new Exception();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
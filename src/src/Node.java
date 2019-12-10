import java.util.ArrayList;
import java.util.Iterator;

public class Node {
    public int nodeID;
    ArrayList<Edge> edges = new ArrayList<>();

    public Node(int nodeID) {
        this.nodeID = nodeID;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID + ": ");
        Iterator<Edge> itr = edges.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}

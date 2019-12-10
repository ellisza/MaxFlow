class Edge {

    int to , from , flow , capacity;
    Edge reverse;

    public Edge(int to , int from , int flow , int capacity) {
        this.to = to;
        this.to = from;
        this.flow = flow;
        this.capacity = capacity;
    }

    public void setReverse(Edge e) {
        reverse = e;
    }

    public String toString() {
        return "Edge " + from + "->" + to + " (" + capacity + ") ";
    }

}
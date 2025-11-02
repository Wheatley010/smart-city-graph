package graph.common;

import java.util.*;

public class Graph {
    private final int n;
    private final boolean directed;
    private final List<Edge> edges;
    private final List<List<Edge>> adj;

    public Graph(int n, boolean directed, List<Edge> edges) {
        this.n = n;
        this.directed = directed;
        this.edges = List.copyOf(edges);
        this.adj = new ArrayList<>(n);

        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());

        for (Edge e : edges) {
            adj.get(e.u()).add(e);
            if (!directed) {
                adj.get(e.v()).add(new Edge(e.v(), e.u(), e.w()));
            }
        }
    }

    public int n() { return n; }
    public boolean directed() { return directed; }
    public List<Edge> edges() { return edges; }
    public List<List<Edge>> adj() { return adj; }
}

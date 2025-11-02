package graph.topo;

import graph.common.Graph;
import graph.common.Metrics;
import java.util.*;

public class KahnTopologicalSort {
    public static List<Integer> topoOrder(Graph dag, Metrics m) {
        m.timeStart("TOPO_time");
        int n = dag.n();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (var e : dag.adj().get(u)) indeg[e.v()]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>(n);
        while (!q.isEmpty()) {
            int u = q.remove();
            m.inc("TOPO_pops", 1);
            order.add(u);
            for (var e : dag.adj().get(u)) {
                m.inc("TOPO_pushes", 1);
                if (--indeg[e.v()] == 0) q.add(e.v());
            }
        }
        m.timeEnd("TOPO_time");
        if (order.size() != n) throw new IllegalStateException("Not a DAG");
        return order;
    }
}

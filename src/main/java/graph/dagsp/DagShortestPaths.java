package graph.dagsp;

import graph.common.Graph;
import graph.common.Metrics;
import java.util.*;

public class DagShortestPaths {
    private final Graph dag;
    private final Metrics m;

    public DagShortestPaths(Graph dag, Metrics m) {
        this.dag = dag;
        this.m = m;
    }

    public Result shortestFrom(int s, List<Integer> topo) {
        m.timeStart("DAGSP_time");
        int n = dag.n();
        long[] dist = new long[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Long.MAX_VALUE / 4);
        Arrays.fill(parent, -1);
        dist[s] = 0;

        int pos = topo.indexOf(s);
        if (pos == -1) throw new IllegalArgumentException("Source not in topo order");

        for (int i = pos; i < topo.size(); i++) {
            int u = topo.get(i);
            if (dist[u] == Long.MAX_VALUE / 4) continue;
            for (var e : dag.adj().get(u)) {
                long nd = dist[u] + e.w();
                m.inc("DAGSP_relax", 1);
                if (nd < dist[e.v()]) {
                    dist[e.v()] = nd;
                    parent[e.v()] = u;
                }
            }
        }
        m.timeEnd("DAGSP_time");
        return new Result(dist, parent);
    }

    public static class Result {
        public final long[] dist;
        public final int[] parent;
        public Result(long[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }

        public List<Integer> reconstructPath(int t) {
            if (dist[t] >= Long.MAX_VALUE / 5) return List.of();
            List<Integer> path = new ArrayList<>();
            for (int cur = t; cur != -1; cur = parent[cur]) path.add(cur);
            Collections.reverse(path);
            return path;
        }
    }
}

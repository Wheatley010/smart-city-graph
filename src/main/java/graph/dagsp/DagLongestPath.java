package graph.dagsp;

import graph.common.Graph;
import java.util.*;

public class DagLongestPath {
    public static Result longestPath(Graph dag, List<Integer> topo) {
        int n = dag.n();
        long[] best = new long[n];
        int[] parent = new int[n];
        Arrays.fill(best, Long.MIN_VALUE / 4);
        Arrays.fill(parent, -1);

        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (var e : dag.adj().get(u)) indeg[e.v()]++;
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0) best[i] = 0;

        for (int u : topo) {
            if (best[u] == Long.MIN_VALUE / 4) continue;
            for (var e : dag.adj().get(u)) {
                long cand = best[u] + e.w();
                if (cand > best[e.v()]) {
                    best[e.v()] = cand;
                    parent[e.v()] = u;
                }
            }
        }

        long bestVal = Long.MIN_VALUE;
        int end = -1;
        for (int v = 0; v < n; v++)
            if (best[v] > bestVal) {
                bestVal = best[v];
                end = v;
            }

        List<Integer> path = new ArrayList<>();
        if (end != -1) {
            int cur = end;
            while (cur != -1) {
                path.add(cur);
                cur = parent[cur];
            }
            Collections.reverse(path);
        }
        return new Result(bestVal, path);
    }

    public static class Result {
        public final long length;
        public final List<Integer> path;
        public Result(long length, List<Integer> path) {
            this.length = length;
            this.path = path;
        }
    }
}

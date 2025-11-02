package graph.scc;

import graph.common.Graph;
import graph.common.Metrics;
import java.util.*;

public class TarjanSCC {
    private final Graph g;
    private final Metrics m;

    private int time = 0;
    private final int[] disc, low, compId;
    private final boolean[] onStack;
    private final Deque<Integer> st = new ArrayDeque<>();
    private int compCount = 0;

    public TarjanSCC(Graph g, Metrics m) {
        this.g = g;
        this.m = m;
        int n = g.n();
        disc = new int[n];
        low = new int[n];
        compId = new int[n];
        onStack = new boolean[n];
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        Arrays.fill(compId, -1);
    }

    public int[] run() {
        m.timeStart("SCC_time");
        for (int v = 0; v < g.n(); v++) {
            if (disc[v] == -1) dfs(v);
        }
        m.timeEnd("SCC_time");
        m.inc("SCC_components", compCount);
        return compId;
    }

    private void dfs(int u) {
        m.inc("SCC_dfs_visits", 1);
        disc[u] = low[u] = time++;
        st.push(u);
        onStack[u] = true;

        for (var e : g.adj().get(u)) {
            int v = e.v();
            m.inc("SCC_dfs_edges", 1);
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            while (true) {
                int x = st.pop();
                onStack[x] = false;
                compId[x] = compCount;
                if (x == u) break;
            }
            compCount++;
        }
    }

    public int componentsCount() { return compCount; }
}

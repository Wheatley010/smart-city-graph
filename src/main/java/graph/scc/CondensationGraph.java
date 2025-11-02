package graph.scc;

import graph.common.Edge;
import graph.common.Graph;
import java.util.*;

public class CondensationGraph {
    public static Graph build(Graph g, int[] compId, int compCount) {
        Set<Long> uniq = new HashSet<>();
        List<Edge> dagEdges = new ArrayList<>();
        for (Edge e : g.edges()) {
            int a = compId[e.u()];
            int b = compId[e.v()];
            if (a != b) {
                long key = (((long)a) << 32) ^ (b & 0xffffffffL);
                if (uniq.add(key)) dagEdges.add(new Edge(a, b, e.w()));
            }
        }
        return new Graph(compCount, true, dagEdges);
    }

    public static List<List<Integer>> componentsAsLists(int[] compId, int compCount) {
        List<List<Integer>> comps = new ArrayList<>();
        for (int i = 0; i < compCount; i++) comps.add(new ArrayList<>());
        for (int v = 0; v < compId.length; v++) comps.get(compId[v]).add(v);
        return comps;
    }
}

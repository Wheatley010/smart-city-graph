package graph.common;

import graph.scc.TarjanSCC;
import graph.scc.CondensationGraph;
import graph.topo.KahnTopologicalSort;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = (args.length > 0) ? args[0] : "data/tasks.json";

        var metrics = new SimpleMetrics();
        var in = JsonLoader.load(input);
        var g = JsonLoader.toGraph(in);

        var tarjan = new TarjanSCC(g, metrics);
        int[] compId = tarjan.run();
        int compCount = tarjan.componentsCount();

        var comps = CondensationGraph.componentsAsLists(compId, compCount);
        System.out.println("SCC components (" + compCount + "):");
        for (int i = 0; i < comps.size(); i++)
            System.out.println("  C" + i + ": " + comps.get(i));

        var dag = CondensationGraph.build(g, compId, compCount);
        System.out.println("Condensation DAG: n=" + dag.n() + ", edges=" + dag.edges().size());

        var topo = KahnTopologicalSort.topoOrder(dag, metrics);
        System.out.println("Topo order (components): " + topo);

        List<Integer> tasksOrder = new ArrayList<>();
        for (int cid : topo) tasksOrder.addAll(comps.get(cid));
        System.out.println("Derived order (original tasks): " + tasksOrder);

        int sourceVertex = (in.source != null) ? in.source : 0;
        int sourceComp = compId[sourceVertex];

        var sp = new DagShortestPaths(dag, metrics).shortestFrom(sourceComp, topo);
        System.out.println("Shortest distances from comp " + sourceComp + ": " + Arrays.toString(sp.dist));

        int targetComp = topo.get(topo.size() - 1);
        System.out.println("One shortest path to comp " + targetComp + ": " + sp.reconstructPath(targetComp));

        var longest = DagLongestPath.longestPath(dag, topo);
        System.out.println("Critical path (components): " + longest.path + ", length = " + longest.length);

        System.out.println("--- METRICS ---");
        System.out.println("SCC dfs visits: " + metrics.get("SCC_dfs_visits"));
        System.out.println("SCC dfs edges:  " + metrics.get("SCC_dfs_edges"));
        System.out.println("SCC time (ns):  " + metrics.nanos("SCC_time"));
        System.out.println("TOPO pushes:    " + metrics.get("TOPO_pushes"));
        System.out.println("TOPO pops:      " + metrics.get("TOPO_pops"));
        System.out.println("TOPO time (ns): " + metrics.nanos("TOPO_time"));
        System.out.println("DAG relax:      " + metrics.get("DAGSP_relax"));
        System.out.println("DAGSP time (ns):" + metrics.nanos("DAGSP_time"));
    }
}

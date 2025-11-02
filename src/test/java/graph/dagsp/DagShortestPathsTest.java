package graph.dagsp;

import graph.common.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class DagShortestPathsTest {
    @Test
    public void testShortestPath() {
        List<Edge> edges = List.of(
                new Edge(0, 1, 3),
                new Edge(0, 2, 6),
                new Edge(1, 2, 2),
                new Edge(1, 3, 1),
                new Edge(2, 3, 1)
        );
        Graph g = new Graph(4, true, edges);
        var topo = List.of(0, 1, 2, 3);
        var sp = new DagShortestPaths(g, new SimpleMetrics()).shortestFrom(0, topo);
        assertEquals(4, sp.dist[3]); // 0→1→3 = 3+1
        assertEquals(List.of(0, 1, 3), sp.reconstructPath(3));
    }
}

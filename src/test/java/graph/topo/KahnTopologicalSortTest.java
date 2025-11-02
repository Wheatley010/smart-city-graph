package graph.topo;

import graph.common.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class KahnTopologicalSortTest {
    @Test
    public void testTopoOrder() {
        List<Edge> edges = List.of(
                new Edge(0, 1, 1),
                new Edge(0, 2, 1),
                new Edge(1, 3, 1),
                new Edge(2, 3, 1)
        );
        Graph g = new Graph(4, true, edges);
        List<Integer> order = KahnTopologicalSort.topoOrder(g, new SimpleMetrics());
        assertEquals(List.of(0, 1, 2, 3), order);
    }
}

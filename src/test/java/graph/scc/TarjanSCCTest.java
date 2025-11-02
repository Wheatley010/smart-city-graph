package graph.scc;

import graph.common.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TarjanSCCTest {
    @Test
    public void testSimpleSCC() {
        List<Edge> edges = List.of(
                new Edge(0, 1, 1),
                new Edge(1, 2, 1),
                new Edge(2, 0, 1),
                new Edge(2, 3, 1)
        );
        Graph g = new Graph(4, true, edges);
        var m = new SimpleMetrics();
        TarjanSCC scc = new TarjanSCC(g, m);
        int[] comp = scc.run();
        assertEquals(2, scc.componentsCount());
        assertEquals(comp[0], comp[1]);
        assertEquals(comp[1], comp[2]);
        assertNotEquals(comp[2], comp[3]);
    }
}

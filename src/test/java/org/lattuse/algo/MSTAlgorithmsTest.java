package org.lattuse.algo;

import org.junit.jupiter.api.*;
import org.lattuse.algo.algorithms.KruskalAlgorithm;
import org.lattuse.algo.algorithms.PrimAlgorithm;
import org.lattuse.algo.base.Edge;
import org.lattuse.algo.base.Graph;
import org.lattuse.algo.model.MSTResult;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MSTAlgorithmsTest {

    private Graph sampleGraph;

    @BeforeEach
    void setUp() {
        // Graph with 5 vertices and 7 edges (connected)
        sampleGraph = new Graph(
                List.of("A", "B", "C", "D", "E"),
                List.of(
                        new Edge("A", "B", 4),
                        new Edge("A", "C", 3),
                        new Edge("B", "C", 2),
                        new Edge("B", "D", 5),
                        new Edge("C", "D", 7),
                        new Edge("C", "E", 8),
                        new Edge("D", "E", 6)
                )
        );
    }

    @Test
    @Order(1)
    @DisplayName("Prim and Kruskal produce same MST total cost")
    void testTotalCostEquality() {
        MSTResult primResult = PrimAlgorithm.run(sampleGraph);
        MSTResult kruskalResult = KruskalAlgorithm.run(sampleGraph);

        assertEquals(
                primResult.getTotalCost(),
                kruskalResult.getTotalCost(),
                0.0001,
                "Prim and Kruskal MST costs must match"
        );
    }

    @Test
    @Order(2)
    @DisplayName("MST has correct number of edges (V-1)")
    void testMSTEdgeCount() {
        MSTResult primResult = PrimAlgorithm.run(sampleGraph);

        int expectedEdges = sampleGraph.getVertexCount() - 1;
        assertEquals(expectedEdges, primResult.getMstEdges().size(), "MST must contain V–1 edges");
    }

    @Test
    @Order(3)
    @DisplayName("MST contains no cycles (acyclic test)")
    void testAcyclic() {
        MSTResult kruskalResult = KruskalAlgorithm.run(sampleGraph);
        boolean isAcyclic = isAcyclic(kruskalResult.getMstEdges(), sampleGraph.getNodes());
        assertTrue(isAcyclic, "MST should be acyclic");
    }

    @Test
    @Order(4)
    @DisplayName("MST connects all vertices (single connected component)")
    void testConnectivity() {
        MSTResult primResult = PrimAlgorithm.run(sampleGraph);
        Set<String> connectedNodes = new HashSet<>();
        for (Edge e : primResult.getMstEdges()) {
            connectedNodes.add(e.from);
            connectedNodes.add(e.to);
        }
        assertEquals(sampleGraph.getVertexCount(), connectedNodes.size(), "MST must connect all vertices");
    }

    @Test
    @Order(5)
    @DisplayName("Execution time and operations are valid")
    void testPerformanceMetrics() {
        MSTResult primResult = PrimAlgorithm.run(sampleGraph);
        MSTResult kruskalResult = KruskalAlgorithm.run(sampleGraph);

        assertTrue(primResult.getExecutionTimeMs() >= 0, "Execution time cannot be negative");
        assertTrue(primResult.getOperationsCount() >= 0, "Operation count must be non‑negative");
        assertTrue(kruskalResult.getExecutionTimeMs() >= 0, "Execution time cannot be negative");
        assertTrue(kruskalResult.getOperationsCount() >= 0, "Operation count must be non‑negative");
    }


    private boolean isAcyclic(List<Edge> edges, List<String> vertices) {
        Map<String, String> parent = new HashMap<>();
        for (String v : vertices) parent.put(v, v);

        for (Edge e : edges) {
            String rootA = find(parent, e.from);
            String rootB = find(parent, e.to);
            if (rootA.equals(rootB)) return false;  // cycle detected
            parent.put(rootA, rootB);               // union
        }
        return true;
    }

    private String find(Map<String, String> parent, String node) {
        if (!parent.get(node).equals(node))
            parent.put(node, find(parent, parent.get(node)));
        return parent.get(node);
    }
}

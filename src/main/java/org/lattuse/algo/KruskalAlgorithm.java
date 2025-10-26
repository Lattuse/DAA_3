package org.lattuse.algo;

import java.util.*;

public class KruskalAlgorithm {
    static class DSU {
        Map<String, String> parent = new HashMap<>();

        String find(String v) {
            if (!parent.get(v).equals(v))
                parent.put(v, find(parent.get(v)));
            return parent.get(v);
        }

        void union(String a, String b) {
            a = find(a);
            b = find(b);
            if (!a.equals(b)) parent.put(b, a);
        }

        void makeSet(Collection<String> vertices) {
            for (String v : vertices) parent.put(v, v);
        }
    }

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        DSU dsu = new DSU();
        dsu.makeSet(graph.getNodes());

        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        for (Edge e : edges) {
            operations++;
            if (!dsu.find(e.from).equals(dsu.find(e.to))) {
                dsu.union(e.from, e.to);
                mstEdges.add(e);
                totalCost += e.weight;
                if (mstEdges.size() == graph.getVertexCount() - 1) break;
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1e6;

        return new MSTResult(mstEdges, totalCost, operations, timeMs);
    }
}


package org.lattuse.algo;

import org.lattuse.algo.Edge;
import org.lattuse.algo.Graph;
import org.lattuse.algo.MSTResult;

import java.util.*;

public class PrimAlgorithm {
    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        Map<String, List<Edge>> adj = new HashMap<>();
        for (Edge e : graph.getEdges()) {
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(new Edge(e.to, e.from, e.weight));
        }

        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        String startNode = graph.getNodes().get(0);
        visited.add(startNode);
        pq.addAll(adj.getOrDefault(startNode, new ArrayList<>()));

        double totalCost = 0;
        while (!pq.isEmpty() && mstEdges.size() < graph.getVertexCount() - 1) {
            Edge edge = pq.poll();
            operations++;

            if (visited.contains(edge.to)) continue;
            visited.add(edge.to);
            mstEdges.add(edge);
            totalCost += edge.weight;

            for (Edge next : adj.getOrDefault(edge.to, new ArrayList<>())) {
                if (!visited.contains(next.to)) pq.add(next);
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1e6;

        return new MSTResult(mstEdges, totalCost, operations, timeMs);
    }
}



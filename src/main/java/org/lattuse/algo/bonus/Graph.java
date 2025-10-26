package org.lattuse.algo.bonus;

import java.util.*;

public class Graph {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }

    public int getId() { return id; }
    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }

    public List<Edge> getEdgesFrom(String node) {
        List<Edge> connected = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getFrom().equals(node) || e.getTo().equals(node))
                connected.add(e);
        }
        return connected;
    }

    @Override
    public String toString() {
        return String.format("Graph #%d: %d nodes, %d edges", id, nodes.size(), edges.size());
    }
}


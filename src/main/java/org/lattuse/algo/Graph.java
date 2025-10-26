package org.lattuse.algo;

import java.util.*;


public class Graph {
    List<String> nodes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes.addAll(nodes);
        this.edges.addAll(edges);
    }

    public int getVertexCount() { return nodes.size(); }
    public int getEdgeCount() { return edges.size(); }

    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
}



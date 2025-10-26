package org.lattuse.algo.base;

public class Edge implements Comparable<Edge> {
    public String from;
    public String to;
    public double weight;

    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}



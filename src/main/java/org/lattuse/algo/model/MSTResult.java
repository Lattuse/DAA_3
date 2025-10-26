package org.lattuse.algo.model;

import org.lattuse.algo.base.Edge;

import java.util.List;

public class MSTResult {
    private final List<Edge> mstEdges;
    private final double totalCost;
    private final int operationsCount;
    private final double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, double totalCost, int operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }
    public List<Edge> getMstEdges() { return mstEdges; }
    public double getTotalCost() { return totalCost; }
    public int getOperationsCount() { return operationsCount; }
    public double getExecutionTimeMs() { return executionTimeMs; }
}

package org.lattuse.algo;

import java.util.List;

public class MSTResult {
    List<Edge> mstEdges;
    double totalCost;
    int operationsCount;
    double executionTimeMs;

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

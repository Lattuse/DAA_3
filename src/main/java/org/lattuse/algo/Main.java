package org.lattuse.algo;

import org.lattuse.algo.base.Graph;
import org.lattuse.algo.model.MSTResult;
import org.lattuse.algo.algorithms.PrimAlgorithm;
import org.lattuse.algo.algorithms.KruskalAlgorithm;
import org.lattuse.algo.handler.IOHandler;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Graph> graphs = IOHandler.loadGraphs("datasets/graphs_dataset.json");

            for (int i = 0; i < graphs.size(); i++) {
                Graph graph = graphs.get(i);

                MSTResult primResult = PrimAlgorithm.run(graph);
                MSTResult kruskalResult = KruskalAlgorithm.run(graph);

                IOHandler.saveResultsCSV("datasets/benchmark.csv", i, primResult, kruskalResult);
                IOHandler.saveResultsJSON("datasets/output.json", i, primResult, kruskalResult);

                System.out.printf("Processed graph %d: Prim cost=%.2f, Kruskal cost=%.2f%n",
                        i + 1, primResult.getTotalCost(), kruskalResult.getTotalCost());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





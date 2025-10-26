package org.lattuse.algo;
import com.google.gson.*;
import java.util.*;
import java.io.*;

// reminder: do the images both for vertices-edges graphs and also plots for time complexity etc.
// I don't remember if we need plots but anyway

public class Main {
    public static void main(String[] args) throws Exception {
        List<Graph> graphs = IOHandler.loadGraphs("datasets/graphs_dataset.json");
        List<JsonObject> results = new ArrayList<>();

        int id = 1;
        for (Graph g : graphs) {
            MSTResult primResult = PrimAlgorithm.run(g);
            MSTResult kruskalResult = KruskalAlgorithm.run(g);

            JsonObject result = new JsonObject();
            result.addProperty("graph_id", id++);
            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", g.getVertexCount());
            inputStats.addProperty("edges", g.getEdgeCount());
            result.add("input_stats", inputStats);

            result.add("prim", toJsonResult(primResult));
            result.add("kruskal", toJsonResult(kruskalResult));
            results.add(result);
        }

        IOHandler.writeResults("datasets/output.json", results);
        IOHandler.writeCSV("datasets/benchmark.csv", results);

        System.out.println("Execution complete! Files generated: output.json and benchmark.csv");
    }

    private static JsonObject toJsonResult(MSTResult res) {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        for (Edge e : res.getMstEdges()) {
            JsonObject eo = new JsonObject();
            eo.addProperty("from", e.from);
            eo.addProperty("to", e.to);
            eo.addProperty("weight", e.weight);
            arr.add(eo);
        }
        obj.add("mst_edges", arr);
        obj.addProperty("total_cost", res.getTotalCost());
        obj.addProperty("operations_count", res.getOperationsCount());
        obj.addProperty("execution_time_ms", res.getExecutionTimeMs());
        return obj;
    }
}


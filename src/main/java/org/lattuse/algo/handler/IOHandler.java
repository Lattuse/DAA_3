package org.lattuse.algo.handler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.lattuse.algo.base.Edge;
import org.lattuse.algo.base.Graph;
import org.lattuse.algo.model.MSTResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class IOHandler {

    private static class GraphDTO {
        List<String> nodes;
        List<EdgeDTO> edges;
    }

    private static class EdgeDTO {
        String from;
        String to;
        double weight;
    }

    private static class GraphsWrapper {
        List<GraphDTO> graphs;
    }

    public static List<Graph> loadGraphs(String filePath) throws IOException {
        Gson gson = new Gson();
        String json = Files.readString(Paths.get(filePath));

        GraphsWrapper wrapper = gson.fromJson(json, GraphsWrapper.class);
        List<GraphDTO> graphDTOs = wrapper.graphs;

        List<Graph> graphs = new ArrayList<>();
        for (GraphDTO dto : graphDTOs) {
            List<String> nodes = dto.nodes != null ? dto.nodes : new ArrayList<>();
            List<Edge> edges = new ArrayList<>();
            if (dto.edges != null) {
                for (EdgeDTO e : dto.edges) {
                    edges.add(new Edge(e.from, e.to, e.weight));
                }
            }
            graphs.add(new Graph(nodes, edges));
        }
        return graphs;
    }

    public static void saveResultsCSV(String csvPath, int graphId, MSTResult prim, MSTResult kruskal) throws IOException {
        File file = new File(csvPath);
        boolean exists = file.exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            if (!exists) {
                pw.println("GraphID,Algorithm,TotalCost,Operations,ExecutionTimeMs");
            }
            pw.printf(Locale.US, "%d,Prim,%.2f,%d,%.3f%n", graphId + 1,
                    prim.getTotalCost(), prim.getOperationsCount(), prim.getExecutionTimeMs());
            pw.printf(Locale.US, "%d,Kruskal,%.2f,%d,%.3f%n", graphId + 1,
                    kruskal.getTotalCost(), kruskal.getOperationsCount(), kruskal.getExecutionTimeMs());
        }
    }

    public static void saveResultsJSON(String jsonPath, int graphId, MSTResult prim, MSTResult kruskal) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path path = Paths.get(jsonPath);

        List<JsonObject> allResults;

        if (Files.exists(path)) {
            String content = Files.readString(path).trim();
            if (content.isEmpty()) {
                allResults = new ArrayList<>();
            } else if (content.startsWith("{")) {
                JsonObject singleObject = gson.fromJson(content, JsonObject.class);
                allResults = new ArrayList<>();
                allResults.add(singleObject);
            } else {
                allResults = gson.fromJson(content, new TypeToken<List<JsonObject>>() {}.getType());
                if (allResults == null) allResults = new ArrayList<>();
            }
        } else {
            allResults = new ArrayList<>();
        }

        JsonObject graphResult = new JsonObject();
        graphResult.addProperty("graphId", graphId + 1);
        graphResult.add("prim", buildMSTResultJson(prim));
        graphResult.add("kruskal", buildMSTResultJson(kruskal));

        allResults.add(graphResult);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(allResults, writer);
        }
    }

    private static JsonObject buildMSTResultJson(MSTResult res) {
        JsonObject obj = new JsonObject();
        obj.addProperty("totalCost", res.getTotalCost());
        obj.addProperty("operations", res.getOperationsCount());
        obj.addProperty("executionTimeMs", res.getExecutionTimeMs());

        JsonArray edges = new JsonArray();
        for (Edge e : res.getMstEdges()) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", e.from);
            edgeObj.addProperty("to", e.to);
            edgeObj.addProperty("weight", e.weight);
            edges.add(edgeObj);
        }
        obj.add("mstEdges", edges);
        return obj;
    }
}






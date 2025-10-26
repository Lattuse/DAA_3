package org.lattuse.algo;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class IOHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Graph> loadGraphs(String inputFile) throws IOException {
        JsonObject json = JsonParser.parseReader(new FileReader(inputFile)).getAsJsonObject();
        List<Graph> graphs = new ArrayList<>();

        for (JsonElement ge : json.getAsJsonArray("graphs")) {
            JsonObject g = ge.getAsJsonObject();
            List<String> nodes = new ArrayList<>();
            g.getAsJsonArray("nodes").forEach(x -> nodes.add(x.getAsString()));

            List<Edge> edges = new ArrayList<>();
            g.getAsJsonArray("edges").forEach(e -> {
                JsonObject eo = e.getAsJsonObject();
                edges.add(new Edge(
                        eo.get("from").getAsString(),
                        eo.get("to").getAsString(),
                        eo.get("weight").getAsDouble()
                ));
            });

            graphs.add(new Graph(nodes, edges));
        }
        return graphs;
    }

    public static void writeResults(String outputFile, List<JsonObject> results) throws IOException {
        Map<String, Object> out = new HashMap<>();
        out.put("results", results);
        try (FileWriter writer = new FileWriter(outputFile)) {
            gson.toJson(out, writer);
        }
    }

    public static void writeCSV(String csvFile, List<JsonObject> results) throws IOException {
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            Locale.setDefault(Locale.US);
            pw.println("GraphID,Algorithm,TotalCost,Operations,ExecutionTimeMs");
            for (JsonObject r : results) {
                int id = r.get("graph_id").getAsInt();
                JsonObject prim = r.getAsJsonObject("prim");
                JsonObject kruskal = r.getAsJsonObject("kruskal");

                pw.printf(Locale.US, "%d,Prim,%.2f,%d,%.3f%n",
                        id,
                        prim.get("total_cost").getAsDouble(),
                        prim.get("operations_count").getAsInt(),
                        prim.get("execution_time_ms").getAsDouble());

                pw.printf(Locale.US,"%d,Kruskal,%.2f,%d,%.3f%n", id,
                        kruskal.get("total_cost").getAsDouble(),
                        kruskal.get("operations_count").getAsInt(),
                        kruskal.get("execution_time_ms").getAsDouble());
            }
        }
    }
}


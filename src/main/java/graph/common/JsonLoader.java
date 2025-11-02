package graph.common;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonLoader {

    public static class InputData {
        public boolean directed;
        public int n;
        public List<Edge> edges;
        public Integer source;
        public String weight_model;
    }

    public static InputData load(String path) throws IOException {
        String json = Files.readString(Path.of(path));
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        boolean directed = root.get("directed").getAsBoolean();
        int n = root.get("n").getAsInt();

        List<Edge> edges = new ArrayList<>();
        JsonArray arr = root.getAsJsonArray("edges");
        for (JsonElement el : arr) {
            JsonObject e = el.getAsJsonObject();
            int u = e.get("u").getAsInt();
            int v = e.get("v").getAsInt();
            int w = e.has("w") ? e.get("w").getAsInt() : 1;
            edges.add(new Edge(u, v, w));
        }

        Integer source = root.has("source") ? root.get("source").getAsInt() : null;
        String weightModel = root.has("weight_model") ? root.get("weight_model").getAsString() : "edge";

        InputData d = new InputData();
        d.directed = directed;
        d.n = n;
        d.edges = edges;
        d.source = source;
        d.weight_model = weightModel;
        return d;
    }

    public static Graph toGraph(InputData d) {
        return new Graph(d.n, d.directed, d.edges);
    }
}

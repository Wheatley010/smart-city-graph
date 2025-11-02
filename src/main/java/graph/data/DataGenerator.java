package graph.data;

import com.google.gson.*;
import graph.common.Edge;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class DataGenerator {
    private static final Random rnd = new Random();

    public static void main(String[] args) throws IOException {
        generate("small", 6, 10, 3);
        generate("medium", 12, 25, 3);
        generate("large", 30, 80, 3);
    }

    private static void generate(String name, int n, int maxEdges, int count) throws IOException {
        Files.createDirectories(Path.of("data"));
        for (int i = 1; i <= count; i++) {
            boolean directed = rnd.nextBoolean();
            int edgesCount = rnd.nextInt(maxEdges - n + 1) + n;
            List<Edge> edges = new ArrayList<>();
            Set<String> used = new HashSet<>();

            for (int j = 0; j < edgesCount; j++) {
                int u = rnd.nextInt(n);
                int v = rnd.nextInt(n);
                if (u == v) continue;
                String key = u + "-" + v;
                if (used.contains(key)) continue;
                used.add(key);
                int w = rnd.nextInt(9) + 1;
                edges.add(new Edge(u, v, w));
            }

            JsonObject root = new JsonObject();
            root.addProperty("directed", directed);
            root.addProperty("n", n);

            JsonArray arr = new JsonArray();
            for (Edge e : edges) {
                JsonObject o = new JsonObject();
                o.addProperty("u", e.u());
                o.addProperty("v", e.v());
                o.addProperty("w", e.w());
                arr.add(o);
            }
            root.add("edges", arr);
            root.addProperty("weight_model", "edge");
            root.addProperty("source", 0);

            String filename = "data/" + name + "_" + i + ".json";
            Files.writeString(Path.of(filename), new GsonBuilder().setPrettyPrinting().create().toJson(root));
            System.out.println("Generated " + filename + " (" + n + " vertices, " + edges.size() + " edges, directed=" + directed + ")");
        }
    }
}

# Smart City Graph Algorithms

## Project Goal
This project combines three major graph algorithms for the **Smart City / Smart Campus Scheduling** problem:
1. **Strongly Connected Components (Tarjan)**
2. **Topological Ordering (Kahn)**
3. **Shortest and Longest Paths in DAGs**

The goal is to analyze task dependencies for city services (cleaning, repair, maintenance) — detect cycles, compress them into components, build a condensation DAG, and plan an optimal execution order with critical path analysis.

---

## Project Structure
src/  
├── main/java/graph/common # Graph, Edge, Metrics, JsonLoader, Main   
├── main/java/graph/scc # TarjanSCC, CondensationGraph  
├── main/java/graph/topo # KahnTopologicalSort  
├── main/java/graph/dagsp # DAG Shortest and Longest Path  
└── main/java/graph/data # DataGenerator for random graphs  
src/test/java/  
├── graph/scc/TarjanSCCTest.java  
├── graph/topo/KahnTopologicalSortTest.java  
└── graph/dagsp/DagShortestPathsTest.java  
data/  
├── small_1.json … small_3.json  
├── medium_1.json … medium_3.json  
├── large_1.json … large_3.json  
└── tasks.json  

---

## Data Summary
All datasets were generated using `DataGenerator.java` and stored under `/data`.  
Each graph contains weighted directed edges (weights represent task durations or dependency costs).  
Weights are positive integers randomly assigned in the range **1–10**.

| Category | Nodes (n) | Edges (m) | Description | Count |
|-----------|------------|------------|--------------|--------|
| Small | 6–10 | 5–10 | Simple DAGs or 1–2 small cycles | 3 |
| Medium | 10–20 | 10–25 | Mixed graphs with several SCCs | 3 |
| Large | 20–50 | 40–60 | Dense graphs for performance tests | 3 |

Total: **9 datasets** of varying density and cyclicity.

---

## How to Run
1. Open the project in **IntelliJ IDEA**.
2. Run: src/main/java/graph/common/Main.java
3. The program automatically processes all `.json` files in the `/data` folder.  
   It performs:
- SCC detection (Tarjan)
- DAG condensation and topological order (Kahn)
- Critical path computation (Longest path in DAG)
- Metric collection (operation counts and timings)

Example output:
=== Running on data\large_1.json ===  
Components: 16, Edges: 19  
Critical path length = 28  
Metrics: SCC_time=187000, TOPO_time=130700, DAGSP_time=0  

To run a single dataset, specify its name in **Run Configuration → Program arguments**:
data/small_1.json  

---

## Results
| Dataset | Vertices | Edges | SCC Count | Critical Path | SCC Time (ns) | Topo Time (ns) |
|----------|-----------|--------|------------|----------------|----------------|----------------|
| small_1.json | 6 | 7 | 3 | 7 | 71,300 | 51,700 |
| small_2.json | 6 | 5 | 2 | 0 | 76,000 | 24,000 |
| medium_1.json | 12 | 13 | 9 | 17 | 79,500 | 39,500 |
| large_1.json | 30 | 45 | 16 | 28 | 187,000 | 130,700 |
| tasks.json | 8 | 12 | 6 | 8 | 29,000 | 24,800 |

---

## Analysis

### SCC (Tarjan)
- **Time complexity:** O(V + E) — confirmed by linear growth in runtime.
- **Observation:** In dense or cyclic graphs, the number of discovered components increases, but Tarjan remains efficient.
- **Bottleneck:** Recursive DFS calls dominate total time on large graphs (>30 nodes).

### Topological Ordering (Kahn)
- **Performance:** Extremely fast due to queue-based O(V + E) traversal.
- **Observation:** Slightly slower on denser DAGs due to more in-degree updates.
- **Bottleneck:** Minimal — suitable for large-scale scheduling tasks.

### DAG Shortest / Longest Paths
- **Shortest path:** Linear time using DP along topological order.
- **Longest (critical) path:** Computed using sign inversion or max-DP.
- **Observation:** Path length increases with graph density — confirming realistic scheduling growth in complex task networks.
- **Bottleneck:** None observed; computation cost negligible compared to SCC.

### Effect of Structure
- **Sparse DAGs:** fewer relaxations, shorter paths.
- **Dense DAGs:** higher SCC count, more edges → longer paths and slightly higher computation time.
- **Cyclic graphs:** correctly condensed into DAGs before path analysis, ensuring correctness.

---

## Conclusions
- **When to use SCC (Tarjan):** To detect strongly connected task clusters or mutual dependencies.
- **When to use Topological Sort (Kahn):** To determine feasible execution order after removing cycles.
- **When to use DAG-SP:** To find optimal and critical task sequences for scheduling.

### Practical Recommendations
- For **large scheduling systems**, run SCC first to simplify dependency analysis.
- Use **Kahn’s algorithm** for quick, memory-efficient topological sorting.
- Apply **DAG-SP** when time-critical or resource-optimized planning is required.
- Algorithms scale efficiently and are suitable for real-time analytics on graphs up to **50+ nodes**.

---


---

## Author
**Timur Imbergenov** — SE-2401  
Astana IT University, 2025



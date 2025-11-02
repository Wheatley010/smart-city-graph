package graph.common;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {
    private final Map<String, Long> counters = new HashMap<>();
    private final Map<String, Long> timeStart = new HashMap<>();
    private final Map<String, Long> durations = new HashMap<>();

    @Override
    public void reset() {
        counters.clear();
        timeStart.clear();
        durations.clear();
    }

    @Override
    public void inc(String key, long delta) {
        counters.put(key, counters.getOrDefault(key, 0L) + delta);
    }

    @Override
    public long get(String key) {
        return counters.getOrDefault(key, 0L);
    }

    @Override
    public void timeStart(String key) {
        timeStart.put(key, System.nanoTime());
    }

    @Override
    public void timeEnd(String key) {
        long start = timeStart.getOrDefault(key, 0L);
        durations.put(key, durations.getOrDefault(key, 0L) + (System.nanoTime() - start));
    }

    @Override
    public long nanos(String key) {
        return durations.getOrDefault(key, 0L);
    }
}

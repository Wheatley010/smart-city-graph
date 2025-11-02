package graph.common;

public interface Metrics {
    void reset();
    void inc(String key, long delta);
    long get(String key);
    void timeStart(String key);
    void timeEnd(String key);
    long nanos(String key);
}

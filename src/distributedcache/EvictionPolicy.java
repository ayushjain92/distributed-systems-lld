package distributedcache;

public enum EvictionPolicy {
    LRU("LRU");

    private String val;

    EvictionPolicy(String v) {
        val = v;
    }
}

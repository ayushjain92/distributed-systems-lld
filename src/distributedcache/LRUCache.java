package distributedcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache implements Cache {
    private Map<String, Node> hashMap;
    private List<Node> nodes;

    private long capacity;

    protected LRUCache() {
        hashMap = new HashMap<>();
        nodes = new ArrayList<>();
    }

    @Override
    public void put(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void evict() {

    }
}

package distributedcache;

import java.util.Map;

import static distributedcache.EvictionPolicy.LRU;

public interface Cache {
    void put(String key, String value);

    String get(String key);

    void evict();

    class Builder {
        private EvictionPolicy evictionPolicy = LRU;
        private static final Map<EvictionPolicy, Cache> CACHE_FACTORY = Map.of(LRU, new LRUCache());
        private String endpoint;
        private Integer port;

        public Builder withEvictionPolicy(EvictionPolicy e) {
            evictionPolicy = e;
            return this;
        }

        public Builder withEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder withPort(Integer port) {
            this.port = port;
            return this;
        }

        public Cache build() {
            if(endpoint == null || port == null) {
                // throw InvalidInputException
            }

            return CACHE_FACTORY.get(evictionPolicy);
        }
    }
}
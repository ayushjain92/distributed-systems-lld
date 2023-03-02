package distributedcache;

public class Client {

    public static void main(String[] args) {
        Cache newCache = new Cache.Builder().withEvictionPolicy(EvictionPolicy.LRU).
                withEndpoint("myserver-us-west-2.ec2.amazon.com").withPort(3030).build();
    }
}

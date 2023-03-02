# distributed-systems-lld

--------

## Distributed Caching
### Requirements

#### Problem
Create a cache based on user's policy

#### Requirements
##### BR/FR:
1. No requirements for engineering problem

##### TR/NFR:
1. store the data.
2. get key quickly.
3. remove a key -> no need for user to delete but if user wants then set the keys value as null. 
3. eviction policy:
   4. there should be a default policy.
   5. this should be invoked when cache capacity is full.
   5. Some Eviction Policies:
      6. LRU (Least Recently Used)
      7. LFU (Least Frequently Used)
      8. LIFO (Last In First Out)


#### Structure
```java
Map<String, String> cache;
```
1. This should store key by taking a hash.
2. This should store the value by converting value to JSON object.

#### Algorithm

##### LRU
Data Structures = Doubly Link List + HashMap
* add(): O(1)
* get(): O(1)
* evict: O(1)

Workings:
1. Whenever a node is used get that using Map.
1. Remove the node from that place.
2. Move it to head.
3. All the recently used nodes will be collected at head.

For Eviction:
1. Remove the tail node.

```java
class Node {
    String val; // store cache's value.
    Node prev, next;
}

class LRUCache implements Cache {
    List<Node> nodes;
    Map<String, Node> hashmap; // store key to node
    Node head, tail;
    
    public void put(String key, String val) {
    
    }
    
    public String get(String key) {
    }
    
    public void evict() {
    }
}

interface Cache {
    void put(String key, String val);
    String get(String key);
    evict();
}
```

To distribute it.
Have write replicas to write to a single endpoint.
Have read replicas to read from any endpoint.

##### LFU
Data Structures = Map + MinHeap;


* add(): O(1)
* get(): O(1)
* evict: O(1)

Workings:
1. Add the node with frequency = 1;
2. Gets the node and increase frequency;
3. Evict will remove the firstEntry from treeNode.

```java
import java.util.Comparator;
import java.util.TreeMap;

class Node {
   String val;
   Integer frequency;
}

class LFUCache implements Cache {
   Map<String, Node> hashmap;
   TreeMap<Integer, Node> minHeap = new TreeMap<>(Comparator.comparingInt(frequency -> frequency)); // AVL Tree
}
```

For Eviction:
1. Remove the tail node.


------------
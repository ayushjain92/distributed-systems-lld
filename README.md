# distributed-systems-lld

## Capacity Planning

1. TPS
   1. ReadTPS, WriteTPS.
2. Storage: Always consider number of business years & 70% capacity Utilization
   1. Application Storage (based on item size)
   2. Database Storage (based on key size)
   2. Cache Storage (based on 80-20 rule). Full capacity utilization.
3. Bandwidth (based on item size)
   4. Download
   5. Upload

```java
/**
 * Constructor arguments are user assumed.
 */
class CapacityPlanning {
    long writeRequestsPerDay; // user assumed
    long readRequestsPerDay;
   
    CapacityPlanning(long writeRequestsPerDay,
        double readToWriteRatio) { 
        this.writeRequestsPerDay = writeRequestsPerDay;
        this.readRequestsPerDay = (long) writeRequestsPerDay * readToWriteRatio;
    }
    
    class TPS {
       long readTps = findReadTPS();
       long writeTps = findWriteTps();

       long findReadTPS() {
          return readRequestsPerDay / (24 * 60 * 60);
       }

       long findWriteTps() {
          return writeRequestsPerDay / (24 * 60 * 60);
       }
    }
    
    class Storage {
        int NUMBER_OF_APPLICATION_YEARS = 10; // for storing upto 10 years
       
        @Getter
        long sizeOfOneItem; // user assumed, eg. size of 1 image = 4MB
       
       
        long applicationStorage = getApplicationStorageSize();
        long databaseStorage = getDatabaseStorageSize();
        
        Storage(long sizeOfOneItem) {
            this.sizeOfOneItem = sizeOfOneItem;
        }
        
        long getApplicationStorageSize() {
            long idealSize = sizeOfOneItem * NUMBER_OF_APPLICATION_YEARS;
            // assuming we use only 70% of the total size
            long capacityUtilization = 0.7;
            long projectedSize = idealSize/capacityUtilization;
            return projectedSize;
        }
        
        long getDatabaseStorageSize() {
            long numberOfKeys = writeRequestsPerDay * 365 * NUMBER_OF_APPLICATION_YEARS;
            long hashSize;
            if(numberOfKeys < "68.7billion") {
                // we can choose a 6 digit hash function, because If we use base64 encoding ([A-Z, a-z, 0-9, ., -]). 64^6 ~= 68.7 billion unique strings
                hashSize = 6; // 6 bytes, since 1 char takes 1 byte
            } else {
                hashSize = 12; //12 supports 136 billion unique keys.
            }
            long sizeOfAllKeys = hashSize * numberOfKeys;
            return sizeOfAllKeys;
        }
        
        long getCacheSize() {
           return readRequestsPerDay * sizeOfOneItem * 0.2; // 20% data being cached as per 80-20 rule.
        }
    }
    
    class Bandwidth {
        long getUploadBandwidth() {
            return TPS.writeTPS * Storage.sizeOfOneItem;
        }
        
        long getDownloadBandwidth() {
            return TPS.readTPS * Storage.sizeOfOneItem;
        }
    }
}
```


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
* evict(): O(1)

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
* evict(): O(1)

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

## Inventory Management System

### Requirements
#### BR/FR
1. Add Products
   2. Product Location: which shelf it should go to. Assume: 3 shelfs: S/M/L
   3. Increase Product Count
   4. Product Price
   5. Brand
   6. Product weight/price
1. Remove Product Counts
2. Update Product Counts
3. Get Product Counts
4. Status of Unit
   5. Available/Booked/Picked/Packaged/ReadyForDelivery
6. Report EOD
   7. Sale
   8. Finished Products

#### TR/NFR
1. 

### Structure


-----------
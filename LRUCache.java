
/**
 * Author: Oisin Gibson (L00172671)
 * Date: 25/12/2025
 * Last Modified: 25/12/2025
 *
 * Assignment/Project: Advanced Algorithms and Data Structures  Assignment 2
 * Module: Advanced Algorithms and Data Structures
 * Professor/Instructor: Ruth Lennon
 *
 * Description: Implementation of an LRU (Least Recently Used) Cache
 *
 * Java Version: Java 17
 *
 * Modification History:
 * 25/12/2025 - Initial creation
 *
 */
import java.util.HashMap;

class LRUCache {

    // Node class for doubly linked list 
    class Node {

        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> cache;
    private int capacity, size;
    private Node head, tail;

    // Constructor - Question 4.1: Create a hash table with capacity
    // Question 4.1.1: Doubly linked list for order of elements
    // Question 4.1.2: Hash map to store keys and their nodes
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();

        // Create dummy head and tail nodes for easier manipulation
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    // Move a node to the front (most recently used) 
    private void moveToFront(Node node) {
        // Remove the node from its current position
        remove(node);
        // Add it to the front (after head)
        addToFront(node);
    }

    // Add a node to the front of the list 
    private void addToFront(Node node) {
        // Insert node between head and head.next
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // Remove a node from the list - Question 4.3.3: Delete a key from the table
    private void remove(Node node) {
        // Bypass the node by linking its neighbors directly
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Get the value of a key if it exists in the cache
    // Question 4.3.2: Search for a key in the table and mark as most recently used
    public int get(int key) {
        // Check if key exists in the hash map
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            // Mark as most recently used by moving to front
            moveToFront(node);
            return node.value;
        }
        return -1;  // Key not found 
    }

    // Insert a key-value pair into the cache
    // Question 4.3.1: Insert a key-value pair into the cache
    // Question 4.3.4: Evict item when the cache is full
    public void put(int key, int value) {
        // If key already exists, update value and move to front
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToFront(node);
        } else {
            // Create new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToFront(newNode);
            size++;

            // Question 4.2 & 4.3.4: If cache exceeds capacity, evict LRU item
            if (size > capacity) {
                // Remove the least recently used which is the node before tail 
                Node lruNode = tail.prev;
                remove(lruNode);
                cache.remove(lruNode.key);
                size--;
            }
        }
    }

    // Print the cache for debugging 
    public void printCache() {
        System.out.print("Cache (MRU -> LRU): ");
        Node current = head.next;
        while (current != tail) {
            System.out.print("[" + current.key + ":" + current.value + "] ");
            current = current.next;
        }
        System.out.println(" | Size: " + size + "/" + capacity);
    }

    // Main method to test the LRU Cache implementation 
    public static void main(String[] args) {
        // Question 4.1: Create a hash table with 20 values
        LRUCache lruCache = new LRUCache(20);  // Cache capacity of 20 

        System.out.println("=== LRU Cache Test (Capacity: 20) ===\n");

        // Insert items 
        System.out.println("Operation: PUT(1, 102345)");
        lruCache.put(1, 102345);
        System.out.println("Operation: PUT(2, 102342)");
        lruCache.put(2, 102342);
        System.out.println("Operation: PUT(3, 102303)");
        lruCache.put(3, 102303);
        lruCache.printCache();
        System.out.println();

        // Access key 2 (this will make key 2 the most recently used) 
        System.out.println("Operation: GET(2) - Accessing key 2 (moves it to MRU position)");
        int value = lruCache.get(2);
        System.out.println("Returned value: " + value);
        lruCache.printCache();
        System.out.println();

        // Insert a new key
        System.out.println("Operation: PUT(4, 105444) - Adding new key-value pair");
        lruCache.put(4, 105444);
        lruCache.printCache();
        System.out.println();

        // Access key 3 
        System.out.println("Operation: GET(3) - Accessing key 3 (moves it to MRU position)");
        value = lruCache.get(3);
        System.out.println("Returned value: " + value);
        lruCache.printCache();
        System.out.println();

        // Insert another new key
        System.out.println("Operation: PUT(5, 103455) - Adding new key-value pair");
        lruCache.put(5, 103455);
        lruCache.printCache();
        System.out.println();

        System.out.println("=== Test Complete ===");
    }
}

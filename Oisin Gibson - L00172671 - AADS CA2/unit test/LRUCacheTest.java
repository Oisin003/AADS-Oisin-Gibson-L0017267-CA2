
import org.junit.Test;
import static org.junit.Assert.*;

public class LRUCacheTest {

    @Test
    public void testGet() {
        // Test Question 4.3.2: Search for a key and mark as most recently used
        LRUCache cache = new LRUCache(3);

        // Insert three items
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        // Test successful retrieval
        assertEquals(100, cache.get(1));
        assertEquals(200, cache.get(2));
        assertEquals(300, cache.get(3));

        // Test retrieval of non-existent key
        assertEquals(-1, cache.get(999)); // Should return -1

        // Test that get() marks item as most recently used
        // Access key 1 to make it MRU
        cache.get(1);

        // Add a new item (key 4), this should evict key 2 (LRU), not key 1
        cache.put(4, 400);

        // Key 1 should still exist (was marked MRU)
        assertEquals(100, cache.get(1));

        // Key 2 should be evicted (was LRU)
        assertEquals(-1, cache.get(2));
    }

    @Test
    public void testPrintCache() {
        // Test that printCache() runs without errors
        LRUCache cache = new LRUCache(5);

        // Test printing empty cache
        cache.printCache(); // Should print empty cache

        // Add items and test printing
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        // Print cache with items (should display MRU -> LRU order)
        cache.printCache();

        // Access an item to change order
        cache.get(1);

        // Print again to verify order changed
        cache.printCache();

        // This test passes if no exceptions are thrown
        assertTrue(true);
    }

    @Test
    public void testPut() {
        // Test Question 4.3.1: Insert key-value pairs into the cache
        LRUCache cache = new LRUCache(3);

        // Insert three items
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        // Verify all items can be retrieved
        assertEquals(100, cache.get(1));
        assertEquals(200, cache.get(2));
        assertEquals(300, cache.get(3));

        // Test updating existing key
        cache.put(2, 250); // Update key 2
        assertEquals(250, cache.get(2));

        // Test Question 4.3.4: Eviction when cache is full
        // Cache is full (3/3), adding key 4 should evict LRU item (key 1)
        cache.put(4, 400);

        // Key 1 should be evicted (LRU)
        assertEquals(-1, cache.get(1)); // Key 1 not found

        // Keys 2, 3, 4 should still exist
        assertEquals(250, cache.get(2));
        assertEquals(300, cache.get(3));
        assertEquals(400, cache.get(4));
    }
}

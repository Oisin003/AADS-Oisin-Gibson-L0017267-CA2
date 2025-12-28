
/**
 * Author: Oisin Gibson (L00172671)
 * Date: 22/12/2025
 * Last Modified: 22/12/2025
 *
 * Assignment/Project: Assignment Name
 * Module: Advanced Algorithms and Data Structures
 * Instructor: Ruth Lennon
 * Description: Implementation of a Hash Table using Divide and Conquer strategy for resizing and rehashing.
 *
 * Java Version: Java 17
 *
 * References:
 * - GeeksforGeeks. "Divide and Conquer Algorithm."
 *   https://www.geeksforgeeks.org/divide-and-conquer/
 * - GeeksforGeeks. "Open Addressing for Collision Handling."
 *   https://www.geeksforgeeks.org/hashing-set-3-open-addressing/
 * Modification History:
 * 22/12/2025 - Initial creation
 *
 */
public class HashTableDivideAndConquer {

    private String[] table;
    private int capacity;
    private int size;

    // 3.3.1 - Constructor to initialize the hash table with a given capacity
    public HashTableDivideAndConquer(int capacity) {
        this.capacity = capacity;
        this.table = new String[capacity];
        this.size = 0;
    }

    // Simple hash function to map a string key to an index
    // Uses the hashCode() method and modulo operation to ensure index is within table bounds
    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // 3.3.1 - Insert a key into the hash table
    // Uses open addressing with linear probing for collision resolution
    public void insert(String key) {
        // 3.3.4 - Check if resize is needed (load factor > 75%)
        if ((double) size / capacity > 0.75) {
            resize();
        }

        int index = hash(key);

        // Linear probing: find next available slot
        while (table[index] != null && !table[index].equals("DELETED")) {
            // If key already exists, don't insert duplicate
            if (table[index].equals(key)) {
                System.out.println("Key '" + key + "' already exists in the table.");
                return;
            }
            index = (index + 1) % capacity; // Move to next slot
        }

        table[index] = key;
        size++;
        System.out.println("Inserted: " + key);
    }

    // 3.3.2 - Search for a key in the hash table
    // Uses linear probing to find the key
    public boolean search(String key) {
        int index = hash(key);
        int startIndex = index;

        // Linear probing: search through the table
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true; // Key found
            }
            index = (index + 1) % capacity;

            // If we've looped back to the start, key doesn't exist
            if (index == startIndex) {
                break;
            }
        }
        return false; // Key not found
    }

    // 3.3.3 - Delete a key from the hash table
    // Uses lazy deletion by marking slots as "DELETED"
    public void delete(String key) {
        int index = hash(key);
        int startIndex = index;

        // Linear probing: find the key to delete
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = "DELETED"; // Mark as deleted
                size--;
                System.out.println("Deleted: " + key);
                rehash(); // Rehash to fill gaps
                return;
            }
            index = (index + 1) % capacity;

            if (index == startIndex) {
                break;
            }
        }
        System.out.println("Key '" + key + "' not found for deletion.");
    }

    // 3.3.4 - Divide and Conquer Resize: Rehash the table by dividing the task into smaller chunks
    // This method doubles the capacity and rehashes all elements using divide and conquer
    private void resize() {
        System.out.println("\nResizing table from " + capacity + " to " + (capacity * 2));

        String[] oldTable = table;
        int oldCapacity = capacity;

        // Double the capacity
        capacity = capacity * 2;
        table = new String[capacity];
        size = 0;

        // Divide and Conquer: Split old table into two halves
        resizeHelper(oldTable, 0, oldCapacity - 1);

        System.out.println("Resizing complete.\n");
    }

    // Helper method for divide and conquer resize
    // Divides the array into smaller chunks and processes them recursively
    private void resizeHelper(String[] oldTable, int left, int right) {
        // Base case: if range is small enough, process directly
        if (left > right) {
            return;
        }

        if (left == right) {
            // Process single element
            if (oldTable[left] != null && !oldTable[left].equals("DELETED")) {
                insertWithoutResize(oldTable[left]);
            }
            return;
        }

        // Divide: Split the range in half
        int mid = left + (right - left) / 2;

        // Conquer: Process left half
        resizeHelper(oldTable, left, mid);

        // Conquer: Process right half
        resizeHelper(oldTable, mid + 1, right);
    }

    // Insert method without triggering resize (used during resize operation)
    private void insertWithoutResize(String key) {
        int index = hash(key);

        while (table[index] != null && !table[index].equals("DELETED")) {
            if (table[index].equals(key)) {
                return; // Don't insert duplicates
            }
            index = (index + 1) % capacity;
        }

        table[index] = key;
        size++;
    }

    // Rehash remaining elements to fill gaps after a deletion (Divide and Conquer Approach)
    // This helps maintain clustering efficiency after deletions
    private void rehash() {
        String[] tempTable = new String[capacity];

        // Copy non-deleted elements to temp array
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].equals("DELETED")) {
                tempTable[i] = table[i];
            }
        }

        // Clear the table and reinsert
        table = new String[capacity];
        size = 0;

        // Divide and conquer: rehash elements
        rehashHelper(tempTable, 0, capacity - 1);
    }

    // Helper method for divide and conquer rehashing
    private void rehashHelper(String[] tempTable, int left, int right) {
        if (left > right) {
            return;
        }

        if (left == right) {
            if (tempTable[left] != null) {
                insertWithoutResize(tempTable[left]);
            }
            return;
        }

        // Divide: Split into two halves
        int mid = left + (right - left) / 2;

        // Conquer: Process both halves
        rehashHelper(tempTable, left, mid);
        rehashHelper(tempTable, mid + 1, right);
    }

    // Print the hash table for debugging
    // Displays all non-null entries with their index positions
    public void printTable() {
        System.out.println("\n====== Hash Table Contents ======");
        System.out.println("Capacity: " + capacity + ", Size: " + size);
        System.out.println("Load Factor: " + String.format("%.2f%%", (double) size / capacity * 100));
        System.out.println("============================================================================");

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].equals("DELETED")) {
                System.out.println("Index " + i + ": " + table[i]);
            }
        }
        System.out.println("============================================================================\n");
    }

    public static void main(String[] args) {
        HashTableDivideAndConquer superheroTable = new HashTableDivideAndConquer(20);

        System.out.println("====== Hash Table ======");
        System.out.println();

        // Insert keys - 20 Superheroes
        superheroTable.insert("Spider-Man");
        superheroTable.insert("Batman");
        superheroTable.insert("Superman");
        superheroTable.insert("Wonder Woman");
        superheroTable.insert("Batman"); // Duplicate - will be handled :)
        superheroTable.insert("Iron Man");
        superheroTable.insert("Captain America");
        superheroTable.insert("Thor");
        superheroTable.insert("Hulk");
        superheroTable.insert("Black Widow");
        superheroTable.insert("Hawkeye");
        superheroTable.insert("Black Panther");
        superheroTable.insert("Deadpool");
        superheroTable.insert("Flash");
        superheroTable.insert("Aquaman");
        superheroTable.insert("Green Lantern");
        superheroTable.insert("Wolverine");
        superheroTable.insert("Doctor Strange");
        superheroTable.insert("Ant-Man");
        superheroTable.insert("Scarlet Witch");

        // Print the hash table
        superheroTable.printTable();

        // Search for keys
        System.out.println("\n====== Search Functions   ======");
        System.out.println("Is 'Superman' in the table? " + superheroTable.search("Superman")); // true
        System.out.println("Is 'Cyclops' in the table? " + superheroTable.search("Cyclops")); // false
        System.out.println("Is 'Black Widow' in the table? " + superheroTable.search("Black Widow")); // true

        // Delete keys
        System.out.println("\n===== Delete functions =====");
        superheroTable.delete("Wonder Woman");
        superheroTable.delete("Hawkeye");
        superheroTable.printTable();

        // Verify deletion
        System.out.println("\n====== Confirm Deletion ======");
        System.out.println("Is 'Wonder Woman' still in table? " + superheroTable.search("Wonder Woman")); // false
    }
}

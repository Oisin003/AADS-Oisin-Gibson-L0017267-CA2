# Hash Table with Divide and Conquer - Assignment CA2

**Author:** Oisin Gibson (L00172671)  
**Module:** Advanced Algorithms and Data Structures  
**Professor:** Ruth Lennon  
**Date:** 22/12/2025

## Overview

This project implements a **Hash Table using Divide and Conquer strategy** for collision resolution, resizing, and rehashing. The implementation demonstrates key data structure concepts including open addressing, linear probing, and efficient rehashing techniques.

## Assignment Requirements Implemented

### 3.1 Hash Table with Open Addressing
- Implements **linear probing** for collision resolution
- Uses open addressing (no separate chaining)
- Simple hash function using `hashCode()` modulo capacity

### 3.2 Divide and Conquer Approach
- **Resize operation** uses divide and conquer to split the old table into halves
- **Rehash operation** uses divide and conquer to reorganize elements after deletion
- Recursive helper methods (`resizeHelper` and `rehashHelper`) split work into smaller chunks

### 3.3 Core Operations

#### 3.3.1 Insert Operation
- Inserts key-value pairs into the hash table
- Checks for duplicates (prevents duplicate entries)
- Uses linear probing to find next available slot
- Automatically triggers resize when load factor > 75%

#### 3.3.2 Search Operation
- Searches for a key in the table
- Uses linear probing to traverse the table
- Returns `true` if found, `false` otherwise

#### 3.3.3 Delete Operation
- Deletes a key from the table
- Uses lazy deletion (marks slot as "DELETED")
- Automatically triggers rehashing to reorganize remaining elements

#### 3.3.4 Resize Operation
- Triggered when load factor exceeds 75%
- Doubles the table capacity
- Uses **divide and conquer** to rehash all elements efficiently
- Splits old table in half recursively for processing

## How It Works

### Hash Function
```java
private int hash(String key) {
    return Math.abs(key.hashCode()) % capacity;
}
```
- Converts string key to integer using Java's `hashCode()`
- Uses modulo to ensure index fits within table capacity
- `Math.abs()` handles negative hash codes

### Linear Probing (Collision Resolution)
When a collision occurs:
1. Calculate initial index: `index = hash(key)`
2. If slot is occupied, move to next slot: `index = (index + 1) % capacity`
3. Repeat until empty slot is found
4. Uses circular wraparound with modulo operator

### Divide and Conquer Resizing
1. When load factor > 75%, table capacity doubles
2. Old table is split into two halves recursively
3. Each half is processed independently
4. Elements are rehashed into the new larger table
5. Base case: Single element is processed directly

### Load Factor
```
Load Factor = (Number of Elements) / (Table Capacity)
```
- Maintained below 75% to ensure good performance
- When exceeded, automatic resizing is triggered

## How to Run

### Prerequisites
- Java 17 or higher
- Command line or IDE (IntelliJ, Eclipse, VS Code)

### Compilation
```bash
javac HashTableDivideAndConquer.java
```

### Execution
```bash
java HashTableDivideAndConquer
```

### Expected Output
The program will:
1. Insert 20 superhero names
2. Display the hash table contents
3. Perform search operations
4. Delete some entries
5. Display updated table
6. Show load factor and capacity information

## Example Output

```
===== Hash Table Demo - Superheroes =====

Inserted: Spider-Man
Inserted: Batman
...
Key 'Batman' already exists in the table.

Resizing table from 20 to 40
Resizing complete.

===== Hash Table Contents =====
Capacity: 40, Size: 19
Load Factor: 47.50%
-------------------------------
Index 3: Superman
Index 7: Spider-Man
...
==============================
```

## Test Cases Included

The `main()` method demonstrates:
- Inserting 20 superhero entries
- Handling duplicate insertions (Batman is inserted twice)
- Automatic resizing when load factor exceeds 75%
- Searching for existing superheroes (Superman, Black Widow) and non-existing ones (Cyclops)
- Deleting entries (Wonder Woman, Hawkeye) and rehashing
- Displaying table contents and statistics

## Key Concepts Demonstrated

### 1. Open Addressing
- All elements stored in the hash table itself
- No linked lists or external storage
- Memory efficient

### 2. Linear Probing
- Simple collision resolution technique
- Checks next sequential slot when collision occurs
- Can cause clustering but easy to implement

### 3. Lazy Deletion
- Deleted entries marked as "DELETED"
- Prevents breaking probe sequences
- Rehashing removes marked entries

### 4. Divide and Conquer
- **Problem:** Rehash all elements efficiently
- **Divide:** Split array into two halves
- **Conquer:** Process each half recursively
- **Base Case:** Process single element directly
- **Benefit:** Demonstrates recursive problem-solving approach

## Time Complexity

**Insert Operation:**
- Average Case: O(1)
- Worst Case: O(n)

**Search Operation:**
- Average Case: O(1)
- Worst Case: O(n)

**Delete Operation:**
- Average Case: O(1)
- Worst Case: O(n)

**Resize Operation:**
- Average Case: O(n log n)
- Worst Case: O(n log n)

*Note: Divide and conquer resize is O(n log n) due to recursive splitting, though total work is still O(n)*

## References

1. **GeeksforGeeks - Hashing Data Structure**  
   https://www.geeksforgeeks.org/hashing-data-structure/
   - Overview of hash tables and collision resolution techniques

2. **GeeksforGeeks - Open Addressing in Hashing**  
   https://www.geeksforgeeks.org/open-addressing-collision-handling-technique-in-hashing/
   - Detailed explanation of open addressing and linear probing

3. **GeeksforGeeks - Divide and Conquer Algorithm**  
   https://www.geeksforgeeks.org/divide-and-conquer/
   - Introduction to divide and conquer strategy

4. **W3Schools - Java HashMap**  
   https://www.w3schools.com/java/java_hashmap.asp
   - Java hash table basics and methods

5. **Oracle Java Documentation - hashCode()**  
   https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--
   - Official documentation for Java's hashCode() method

## Additional Notes

- The implementation uses strings as keys (superhero names)
- Load factor threshold is set at 75% for optimal performance
- Table capacity doubles on each resize operation
- Duplicate prevention ensures data integrity
- Comprehensive print statements help visualize operations

## Learning Outcomes

This assignment demonstrates understanding of:
- Hash table implementation from scratch
- Collision resolution strategies
- Dynamic resizing and rehashing
- Divide and conquer algorithm design
- Time and space complexity analysis
- Java programming best practices

---
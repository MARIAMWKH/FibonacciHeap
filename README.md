# FibonacciHeap
Implementation of a Fibonacci Heap and regular Heap data structures in Java, featuring efficient operations like insert, delete-min, and decrease-key. The Fibonacci Heap implementation includes advanced operations like cascading cuts and meld. Built for Data Structures course.


# Fibonacci Heap Implementation

A Java implementation of a Fibonacci Heap data structure. This implementation provides amortized time complexity guarantees for various operations, making it efficient for applications requiring frequent decrease-key operations.

## Features

### Core Operations
- Insert: O(1)
- Find Minimum: O(1)
- Delete Minimum: O(log n) amortized
- Decrease Key: O(1) amortized
- Delete: O(log n) amortized
- Meld: O(1)

### Additional Features
- Cascading Cut mechanism for maintaining heap structure
- Efficient node marking system
- Potential function calculation
- Tree consolidation after delete-min operations
- K minimum elements finder

## Implementation Details

### Data Structure
The heap consists of:
- A collection of heap-ordered trees
- Each node contains:
  - Key value
  - Rank (degree)
  - Mark status
  - Parent/Child/Sibling pointers
  - Copy reference for kMin operations

### Key Methods

```java
// Basic Operations
public HeapNode insert(int key)
public void deleteMin()
public HeapNode findMin()
public void delete(HeapNode x)
public void decreaseKey(HeapNode x, int delta)
public void meld(FibonacciHeap heap2)

// Helper Methods
public int potential()       // Returns current heap potential
public int[] countersRep()   // Returns array of tree orders
public static int[] kMin(FibonacciHeap H, int k)  // Finds k minimal elements
```

## Time Complexities

- **Constant Time O(1):**
  - Insert
  - Find Minimum
  - Meld
  - Decrease Key (amortized)

- **Logarithmic Time O(log n):**
  - Delete Minimum (amortized)
  - Delete (amortized)

- **Special Operations:**
  - kMin: O(k*deg(H)) where deg(H) is the degree of the heap's tree

## Usage Example

```java
// Create a new Fibonacci Heap
FibonacciHeap heap = new FibonacciHeap();

// Insert elements
HeapNode node1 = heap.insert(5);
HeapNode node2 = heap.insert(3);
HeapNode node3 = heap.insert(7);

// Find minimum
HeapNode min = heap.findMin(); // returns node with key 3

// Decrease key
heap.decreaseKey(node3, 4); // decreases 7 by 4, making it 3

// Delete minimum
heap.deleteMin(); // removes the minimum element
```

## Implementation Notes
- The heap maintains pointers to both the minimum element and the first root
- Uses a doubly-linked circular list for roots and children
- Implements marking for efficient decrease-key operations
- Maintains counters for total links and cuts performed

## Contributors
- Wael Zidan (211708516)
- Hadeel Abed Alhaleem (211758842)

# Fibonacci Heap Implementation

This project implements a Fibonacci Heap data structure in Java. The implementation uses a circular doubly-linked list representation for both the root list and child lists, with comprehensive pointer management for efficient operations.

## Data Structure Design

The Fibonacci Heap maintains several key pointers:
- `firstRoot`: Points to the first node in the root list
- `lastRoot`: Points to the last node in the root list
- `min`: Points to the node with the minimum key
- Size counters: `size`, `marksNum`, `treesNum`
- Operation counters: `links`, `cuts`

### Node Structure
Each node contains:
```java
- key: Integer value
- rank: Number of children
- mark: Node's mark status
- child: Pointer to one child
- next/prev: Circular list pointers
- parent: Parent node pointer
- copy: Used for kMin operations
```

## Operations and Complexities

### Basic Operations
- Insert: O(1)
- Find Minimum: O(1)
- Delete Minimum: O(log n) amortized
- Decrease Key: O(1) amortized
- Delete: O(log n) amortized
- Meld: O(1)

### Advanced Features
- Cascading Cut Mechanism
  - Maintains heap structure after decrease-key operations
  - Implements marking system for amortized efficiency
  
- Consolidation Process
  - Reorganizes trees after deleteMin
  - Uses an array-based approach for tree combining
  
- K Minimum Elements
  - Finds k smallest elements efficiently
  - Maintains heap integrity during search

## Implementation Details

### Pointer Management
The implementation uses careful pointer management in several key areas:
```java
// Example of circular list insertion
public void AddNextPrev(HeapNode NextNode, HeapNode PrevNode) {
    this.addNext(NextNode);
    this.addPrev(PrevNode);
}
```

### Key Methods

```java
// Basic operations
public HeapNode insert(int key)
public void deleteMin()
public HeapNode findMin()
public void meld(FibonacciHeap heap2)

// Advanced operations
public void decreaseKey(HeapNode x, int delta)
public static int[] kMin(FibonacciHeap H, int k)
public int potential()
```

## Usage Example

```java
FibonacciHeap heap = new FibonacciHeap();

// Insert elements
HeapNode node1 = heap.insert(5);
HeapNode node2 = heap.insert(3);
HeapNode node3 = heap.insert(7);

// Get minimum
HeapNode min = heap.findMin();  // returns node with key 3

// Decrease key
heap.decreaseKey(node3, 4);  // decreases 7 by 4

// Delete minimum
heap.deleteMin();  // removes the minimum element
```

## Special Features

1. Potential Function
   - Tracks heap state using trees and marked nodes
   - Formula: `potential = #trees + 2*#marked`

2. Operation Counting
   - Maintains global counters for links and cuts
   - Useful for performance analysis

3. Array Representation
   - `countersRep()` provides tree distribution by rank
   - Helpful for heap analysis and debugging

## Contributors
- mariamk (212346076)
- mayarsafadi (209826924)

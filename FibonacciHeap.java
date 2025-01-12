/**
 * FibonacciHeap
 * 212346076 id1
 * user1 :mariamk
 * 209826924 id2
 * user2: mayarsafadi
 * An implementation of a Fibonacci Heap over integers.
 */

import java.util.ArrayList;
import java.util.Collections;

public class FibonacciHeap
{
    private HeapNode firstRoot; //points at the first node in the heap
    private HeapNode lastRoot; // points at the last node in the heap
    private HeapNode min; //points at the node with the smallest key in th heap
    private int size; // total nodes number in the heap
    private int marksNum=0; // total number of marked nodes in the heap
    private int treesNum=0; // number of trees in the heap witch is represented by the number of roots
    public static int links=0; // counts the number of links
    public static int cuts=0; // counts the number of cuts

    public FibonacciHeap() {
    }


    /**
     * public boolean isEmpty()
     *
     * Returns true if and only if the heap is empty.
     *
     */
    public boolean isEmpty()
    {
        return this.min==null;//min feild is null means that there was still no insertions

    }

    /**
     * public HeapNode insert(int key)
     *
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     *
     * Returns the newly created node.
     */

    public HeapNode insert(int key) //creates a new root with the value key and adds it to the heap
    {
        HeapNode node = new HeapNode(key);
        if(this.isEmpty()) {
            node.addNext(node);
            insertUpdate(node);
        }
        else{ node.AddNextPrev(firstRoot,lastRoot);
            insertUpdate(node);
        }
        //updating the size and treesNum
        this.size++;
        this.treesNum++;
        return node;
    }
    /**
     *     public void insertUpdate(HeapNode node)
     *
     * updating the pointers of the fibonaci heap after head insertion
     *time complixiety : o(1)
     */
    public void insertUpdate(HeapNode node)// first root points at the new inserted node and if its key is smaller
    //than the min  key  min points at the new node

    {
        if(this.isEmpty())
        {
            this.firstRoot=node;
            this.lastRoot=node;
            this.min=node;
        } else if (this.min.getKey()>node.getKey()) {
            this.min=node;
            this.firstRoot=node;
        }
        else { this.firstRoot=node;
        }
    }

    public void deleteMin()
    {
        if (!isEmpty()) {
            if (this.min.getRank() == 0) {

                if (size() == 1) updaingAfterDeleteMin(1);
                else updaingAfterDeleteMin(2); // size is greater than 1

            } else { // min.rank > 0
                if (!(this.min.getNext().equals(this.min))) { // this.lastRoot!= this.firstRoot
                    updaingAfterDeleteMin(3);

                } else { //firstRoot and lastRoot point at the same node this means there is only ine key
                    updaingAfterDeleteMin(4);

                }
            }
        }

    }

    private void updaingAfterDeleteMin(int i)
    {
        switch(i) {
            case 1: // the  min's rank is 0 && fibonacciHeap's size 1
                //resetting the fibonacciHeap to be empty
                this.firstRoot = null;
                this.lastRoot = null;
                this.min = null;
                this.size = 0;
                this.treesNum = 0;
                break;

            case 2: // min's rank is 0 && fibonacciHeap's size > 1
                if (this.min == this.firstRoot)
                    this.firstRoot = this.min.next;

                if (this.min == this.lastRoot)
                    this.lastRoot = this.min.prev;

                // updating the pointers of min.getPrev() and min.getNext()
                this.min.getPrev().setNext(this.min.getNext());
                this.min.getNext().setPrev(this.min.getPrev());

                //updating the size and treesNum
                this.size--;
                this.treesNum--;
                Consolidating();
                break;

            case 3:
                if (this.min == this.firstRoot) {
                    updateFirst(); // update the firstRoot and delete the min
                    Consolidating();
                    //updating the size
                    this.size--;
                    break;

                } else if (this.min == this.lastRoot) {
                    updateLast(); // update the lastRoot and delete the min
                    Consolidating();
                    //updating the size
                    this.size--;
                    break;

                } else {
                    updateAfterDelete(); // update the pointers of min.getPrev() and min.getNext() and delete min
                    Consolidating();
                    //updating the size
                    this.size--; // descend the heapSize by 1
                    break;

                }
            case 4:
                updateAll(); // update lastRoot and the firstRoot ,and delete the min
                Consolidating();
                //updating the size
                this.size--; // descend the heapSize by 1
                break;
        }


    }

    /*
     * private void updateAll()
     *
     * update the pointers after delete the min
     * O(n) while n is the number of children in min
     *
     */
    private void updateAll()
    {
        HeapNode minChild = this.min.getChild();
        int marksCnt = 0; // count the marked HeapNodes that changed after delete min

        if (minChild.mark == 1) marksCnt++; // child of the min is marked
        minChild.mark = 0;
        minChild.setParent(null);
        // updating firstRoot lastRoot pointer and marksNum and treesNum
        this.firstRoot = minChild;
        HeapNode lastChild = minChild;

        // updating the parents
        for (int i = 2; i <= this.min.rank; i++) {

            minChild = minChild.getNext();

            if (minChild.mark == 1) marksCnt++;
            minChild.mark = 0;
            minChild.setParent(null);

            lastChild = minChild;
        }

        this.lastRoot = lastChild;
        this.marksNum -= marksCnt;
        this.treesNum = this.treesNum -1 +this.min.getRank();
    }

    /*
     * private void updateAfterDelete()
     *
     * O(n) where n is the number if children to the min
     */
    private void updateAfterDelete()
    {

        HeapNode prev = this.min.getPrev();
        HeapNode next = this.min.getNext();
        HeapNode child= this.min.getChild();
        int marksCnt = 0;

        if (child.mark == 1) marksCnt++; // child of the minHeapNode is marked
        child.mark = 0;
        child.setParent(null);

        child.setPrev(prev);
        prev.setNext(child);

        HeapNode lastChild = child;

        // update the parents
        for (int i = 2; i <= this.min.rank; i++) {

            child = child.getNext();

            if (child.mark == 1) marksCnt++;
            child.mark = 0;
            child.setParent(null);

            lastChild = child;
        }

        // Link
        lastChild.setNext(next);
        next.setPrev(lastChild);

        this.marksNum -= marksCnt;
        this.treesNum = this.treesNum -1 +this.min.getRank();

    }

    /*
     * private void updateLast()
     *
     * O(n) where n is the number if children to the minHeapNode
     */
    private void updateLast()
    {

        HeapNode prev = this.min.getPrev();
        HeapNode child = this.min.getChild();
        int marksCnt = 0;

        if (child.mark == 1) marksCnt++;
        child.mark = 0;
        child.setParent(null);

        // Linking pointers
        child.setPrev(prev);
        prev.setNext(child);


        HeapNode lastChild = child;
        // update the parents
        for (int i = 2; i <= this.min.rank; i++) {

            child = child.getNext();

            if (child.mark == 1) marksCnt++; // child of the minHeapNode is marked
            child.mark = 0;
            child.setParent(null);

            lastChild = child;
        }
        this.lastRoot = lastChild;
        lastChild.setNext(this.firstRoot);
        this.firstRoot.setPrev(lastChild);

        this.marksNum -= marksCnt; // descend the numOfMarks by countmarks
        this.treesNum = this.treesNum -1 +this.min.getRank(); // update the num of the trees after delet minHeapNode

    }

    /*
     * private void updateFirst()
     *
     * O(n) where n is the number if children to the minHeapNode
     */
    private void updateFirst()
    {
        HeapNode next = this.min.getNext();
        HeapNode child = this.min.getChild();
        int marksCnt = 0;

        if (child.mark == 1) marksCnt++;
        child.mark = 0;
        child.setParent(null);

        // Update the new pointers
        this.firstRoot = child;
        child.setPrev(lastRoot);
        this.lastRoot.setNext(child);
        HeapNode lastChild = child;
        // update the parents
        for (int i = 2; i <= this.min.rank; i++) {
            child = child.getNext();

            if (child.mark == 1) marksCnt++;

            child.mark = 0;
            child.setParent(null);

            lastChild = child;
        }
        next.setPrev(lastChild);
        lastChild.setNext(next);
        this.marksNum -= marksCnt;
        this.treesNum = this.treesNum -1 +this.min.getRank();

    }

    /*
     * private void Consolidating()
     *
     * inserting trees into array each tree in the index thats equal to the tree's rank of the array if there is a tree already in that index of the array we merge them to become one tree with old rank plus 1 and add it to the array
     * after that we build the new fibonacciHeap from the ranks in our array
     *
     * n = fibonacciHeap.Size()
     * Amort - O(log(n))
     * WC - O(n)
     */
    private void Consolidating()
    {
        HeapNode[] arr = heapToArray();
        arrayToHeap(arr);

    }

    /*
     * private void arrayToHeap(HeapNode[] arr)
     *
     * building the fibonacciHeap from the trees in the arr

     *
     * WC - O(log(n))
     */
    private void arrayToHeap(HeapNode[] arr)
    {
        this.firstRoot = null;
        this.lastRoot = null;
        this.min = null;

        HeapNode temp = null;
        // build the new fibonacciHeap
        for (int i = 0; i < arr.length; i++) {

            if (this.firstRoot != null && arr[i] != null) {
                temp.setNext(arr[i]);
                arr[i].setPrev(temp);
                this.firstRoot.setPrev(arr[i]);
                arr[i].setNext(this.firstRoot);
                this.lastRoot = arr[i];

                if (this.min.getKey() > arr[i].getKey()) this.min = arr[i]; // update the min

                temp = arr[i];
            }

            if (this.firstRoot == null && arr[i] != null) {
                this.firstRoot = arr[i];
                this.lastRoot = arr[i];
                this.min = arr[i];
                this.firstRoot.setNext(firstRoot);
                this.firstRoot.setPrev(firstRoot);

                temp = this.firstRoot;
            }


        }

    }

    /*
     * private HeapNode[] heapToArray()
     *
     * inserting trees into array each tree in the index thats equal to the tree's rank of the array if there is a tree already in that index of the array we merge them to become one tree with old rank plus 1 and add it to the array
     *
     * n = fibonacciHeap.Size()
     * WC - O(n)
     * Amort - O(log(n))
     */
    private HeapNode[] heapToArray()
    {
        HeapNode[] arr = new HeapNode[(int)(Math.log(this.size) / Math.log(2)) +1];
        HeapNode temp1 = this.firstRoot; // pointer to the first of the fibonacci

        // if HeapNode is inserted to arr[i] while arr[i]==null --> add to arr with no link
        // if HeapNode is inserted to arr[i] while arr[i]!=null --> link and add to arr in i+1

        arr[temp1.getRank()] = temp1;

        HeapNode temp2 = temp1.getNext() ;
        temp1.setNext(null);
        temp1.setPrev(null);

        while (temp2 != this.firstRoot) {
            if (arr[temp2.getRank()] == null) {
                arr[temp2.getRank()] = temp2;

                temp1 = temp2;
                temp2 = temp2.getNext();
                temp1.setNext(null);
                temp1.setPrev(null);
            }

            else {
                boolean flag = true;
                temp1 = temp2;
                temp2 = temp2.getNext();
                temp1.setNext(null);
                temp1.setPrev(null);
                HeapNode curr = arr[temp1.getRank()];
                arr[temp1.getRank()] = null;
                while (flag) { // the second case


                    if (arr[temp1.getRank() +1] == null) { //last link
                        arr[temp1.getRank()] = null;
                        arr[temp1.getRank() +1] = Link(temp1, curr); // link
                        flag = false;

                    } else { // last link
                        temp1 = Link(temp1, curr); // Link
                        curr = arr[temp1.getRank()];
                        arr[temp1.getRank()] = null;
                    }
                }
            }
        }

        return arr;
    }



    /*
     * private HeapNode Link(HeapNode heapNode, HeapNode currRoot)
     *
     * RankheapNode = Rankpos = i
     * Linked  HeapNodes  with rank i+1
     *
     * O(1)
     */
    private HeapNode Link(HeapNode heapNode, HeapNode currRoot) {

        if (heapNode.getKey() < currRoot.getKey())
        {
            if (heapNode.getRank() > 0) {
                HeapNode child = heapNode.getChild();

                // update the pointers of heapNode child && posParent
                heapNode.setChild(currRoot);
                currRoot.setParent(heapNode);
                currRoot.setNext(child);
                currRoot.setPrev(child.getPrev());
                child.getPrev().setNext(currRoot);
                child.setPrev(currRoot);

            } else {

                // update the pointers of heapNode child && posParent
                heapNode.setChild(currRoot);
                currRoot.setParent(heapNode);
                currRoot.setNext(currRoot);
                currRoot.setPrev(currRoot);
            }

            heapNode.setRank(heapNode.getRank() +1); // update the heapNodeRank
            this.treesNum--; // update the treesNum
            links++; // update the links
            return heapNode;

        }
        else {
            // heapNode.getKey() > currRoot.getKey()
            if (currRoot.getRank() > 0) {
                HeapNode posChild = currRoot.getChild();

                // update the pointers of currRoot child && heapNodeParent
                currRoot.setChild(heapNode);
                heapNode.setParent(currRoot);
                heapNode.setNext(posChild);
                heapNode.setPrev(posChild.getPrev());
                posChild.getPrev().setNext(heapNode);
                posChild.setPrev(heapNode); //

            } else {

                // update the pointers of currRoot child && heapNodeParent
                currRoot.setChild(heapNode);
                heapNode.setParent(currRoot);
                heapNode.setNext(heapNode);
                heapNode.setPrev(heapNode);
            }

            currRoot.setRank(currRoot.getRank() +1); // update the posRank
            //updating treesNum and links
            this.treesNum--;
            links++;
            return currRoot;
        }
    }

    /**
     * public HeapNode findMin()
     *
     * Returns the node eith the minimum key, or null if the heap is empty.
     *
     */
    public HeapNode findMin()
    {return this.min;}


    /**
     * public void meld (FibonacciHeap heap2)
     *
     * Melds heap2 with the current heap.
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        if (this.isEmpty())
        {
            this.firstRoot = heap2.firstRoot;
            this.lastRoot = heap2.lastRoot;
            this.size = heap2.size;
            this.min = heap2.min;
        }// if this is empty update all the fields for heap2
        else if (heap2.isEmpty()) return; // no object to meld
        else {// if both trees not empty ,meld
            this.lastRoot.addNext(heap2.firstRoot);
            this.firstRoot.addPrev(heap2.lastRoot);
            this.lastRoot=heap2.lastRoot;
            this.size+=heap2.size;
            this.treesNum+=heap2.treesNum;
            if(this.min.getKey()>heap2.min.getKey())
            {this.min=heap2.min;}

        }

    }

    /**
     * public int size()
     *
     * Returns the number of elements in the heap.
     *
     */
    public int size()
    {
        return this.size; // should be replaced by student code
    }

    /**
     * public int[] countersRep()
     *
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     *
     */
    public int[] countersRep()
    {
        if (size() != 0) {
            HeapNode pos = this.firstRoot;
            int[] array = new int[(int)((Math.log(size()) / Math.log(2)) +1)];
            array[pos.getRank()] = 1;
            pos = pos.next;
            while (pos != this.firstRoot) {
                array[pos.getRank()] += 1;
                pos = pos.next;
            }

            return array;
        }
        return new int [0];
    }

    /**
     * public void delete(HeapNode x)
     *
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     *1) - decreasekey(x,-infinity) && change x.ketKey to -infinity
     * 2) - deleteMin()
     */
    public void delete(HeapNode x)
    {
        x.setKey(Integer.MIN_VALUE);
        this.decreaseKey(x, 0);
        this.deleteMin();
    }


    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        if (x.getParent() == null) { // decrease a root HeapNode
            x.setKey(x.getKey() -delta);
            if (x.getKey() < this.min.getKey()) this.min = x;

        } else if (x.getKey() -delta >= x.getParent().getKey()) { // no cut needed
            x.setKey(x.getKey() -delta);

        } else { // (x.getKey() -delta < x.getParent().getKey())
            x.setKey(x.getKey() -delta);//
            cascadingCut(x, x.getParent());
        }
        return;
    }

    /*
     * private void cascadingCut(HeapNode x, HeapNode parent)
     *
     * cut x HeapNode from  parent and insert x to the head of the fibonacci heap
     *
     * O(n) WC
     * O(1) Amort
     */
    private void cascadingCut(HeapNode x, HeapNode parent)
    {
        this.cuts++;
        cut(x,parent);
        if (parent.getParent() != null) { //the parent is not the root
            if (parent.getMark() == 0) { // the parent is unmarked, mark it and update marks num
                parent.setMark(1);
                marksNum++;
            }
            else { //other wise the parent is marked
                cascadingCut(parent, parent.getParent());

            }
        }

    }

    /*
     * private void cut(HeapNode x, HeapNode parent)
     *
     * cut x from his parent and insert to the head of the fibonacci heap
     */
    private void cut(HeapNode x, HeapNode parent)
    {
        x.setParent(null);//cut
        if (x.getMark() == 1) this.marksNum--; //unmark
        x.setMark(0);//unmark
        parent.setRank(parent.getRank()-1);
        if (parent.getRank() == 0) { // parent has one child
            parent.setChild(null);
            x.AddNextPrev(this.firstRoot, this.lastRoot);
            // add x to the head of the fibonacci heap

        }
        else {
            if (parent.getChild()==x)
            {
                parent.setChild(x.getNext());
            }
            x.getNext().setPrev(x.getPrev());
            x.getPrev().setNext(x.getNext());
            x.AddNextPrev(this.firstRoot, this.lastRoot);

        }

        this.firstRoot = x;
        if (x.getKey() < this.min.getKey()) this.min = x;
        this.treesNum += 1;

    }


    /**
     * public int nonMarked()
     *
     * This function returns the current number of non-marked items in the heap
     */
    public int nonMarked()
    {
        return this.size-this.marksNum; // should be replaced by student code
    }

    /**
     * public int potential()
     *
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     *
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential()
    {
        return this.treesNum + 2*this.marksNum;
    }

    /**
     * public static int totalLinks()
     *
     * returns links
     */
    public static int totalLinks()
    {
        return links; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     *
     * returns cuts
     */
    public static int totalCuts()
    {
        return cuts; // should be replaced by student code
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     *
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     *
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        if (H == null || H.isEmpty() || k <= 0) {
            return new int[0];
        }

        int[] Arr = new int[k];
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(H.findMin().getKey());
        heap.firstRoot.copy = H.findMin();

        for (int i = 0; i < k; i++) {
            Arr[i] = heap.min.getKey();
            HeapNode children = heap.min.copy.getChild();
            heap.deleteMin();

            if (children != null) {
                HeapNode current = children;
                do {
                    heap.insert(current.getKey());
                    heap.firstRoot.copy = current;
                    current = current.next;
                } while (current != children);
            }
        }

        return Arr;
    }


    /**
     * public class HeapNode
     *
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     *
     */
    public static class HeapNode{

        public int key;
        public int rank;
        public int mark;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;
        public HeapNode copy;



        public HeapNode getChild() {
            return this.child;
        }
        public void setCopy(HeapNode copy) {
            this.copy = copy;
        }

        public HeapNode getCopy() {
            return this.copy;
        }

        public int getMark() {
            return this.mark;
        }

        public void setChild(HeapNode child) {
            this.child = child;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public HeapNode getNext() {
            return this.next;
        }

        public void setNext(HeapNode next) {
            this.next = next;
        }

        public HeapNode getParent() {
            return this.parent;
        }

        public int getRank() {
            return this.rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public HeapNode getPrev() {
            return prev;
        }

        public void setPrev(HeapNode prev) {
            this.prev = prev;
        }

        public void setParent(HeapNode parent) {
            this.parent = parent;
        }
        public int getKey() {

            return this.key;
        }

        public HeapNode(int key) {

            this.key = key;
        }
        public HeapNode(int key,HeapNode prev,HeapNode next)
        {
            this.key = key;
            this.prev=prev;
            this.next=next;
        }

        public void addPrev(HeapNode PrevNode)
        {
            this.prev=PrevNode;
            prev.next=this;
        }
        public void addNext(HeapNode NextNode)
        {
            this.next=NextNode;
            next.prev=this;
        }
        public void AddNextPrev(HeapNode NextNode,HeapNode PrevNode)
        {
            this.addNext(NextNode);
            this.addPrev(PrevNode);
        }



    }


}

package main.java.com.project.heap;

import com.project.bean.Building;

/**
 * Class which has the implementation of a min heap.
 */
public class MinHeap {
    /**
     * max size of the heap. As the problem statement says that there won't be more than 2000 buildings
     */
    private static final int MAX_SIZE = 2002;
    private static final int ROOT_INDEX = 1;
    /**
     * used to store all the buildings
     */
    private Building[] array;
    /**
     * The current size of the heap.
     */
    private int size;

    public MinHeap() {
        array = new Building[MAX_SIZE];
        size = 0;
    }

    /**
     * Returns true if the heap is empty.
     *
     * @return {@code true} if the heap is empty or else {@code false}.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the heap.
     *
     * @return returns the current size of the heap.
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a building in the min heap. In case, wherein, the execution time is equal to the other building then
     * the building with smaller building number will come to the top of the heap.
     *
     * @param building the building to be added to the min heap.
     */
    public void addBuilding(Building building) {
        size = size + 1;
        array[size] = building;
        int currentIndex = size;
        while (currentIndex > 1) {
            int parentIndex = getParentIndex(currentIndex);
            if (array[parentIndex].isExecutionTimeLessAsComparedTo(array[currentIndex])) {
                //in case the the execution time of parent node is less than the current node then there is not need to further adjust the heap.
                break;
            }
            swapNodes(currentIndex, parentIndex);
            currentIndex = parentIndex;
        }
    }

    /**
     * Returns the building whose executed time is minimum and removes it from the min heap.
     *
     * @return the building whose executed time is minimum.
     */
    public Building getMin() {
        Building min = array[ROOT_INDEX];
        swapNodes(ROOT_INDEX, size);
        size = size - 1;
        adjustHeap(ROOT_INDEX);
        return min;
    }


    /**
     * Return the index of the parent node.
     *
     * @param currentIndex the index of the node whose parent is to be found out.
     * @return the parent index.
     */
    int getParentIndex(int currentIndex) {
        return currentIndex / 2;
    }

    /**
     * Adjust the heap nodes so that the min heap properties are maintained.
     *
     * @param parentIndex the index of the node on which the adjustment is being done.
     */
    private void adjustHeap(int parentIndex) {
        while (getLeftChildIndex(parentIndex) <= size) {
            int leftChildIndex = getLeftChildIndex(parentIndex);
            int rightChildIndex = getRightChildIndex(leftChildIndex);
            if (leftChildIndex < size
                    && !array[leftChildIndex].isExecutionTimeLessAsComparedTo(array[rightChildIndex])) {
                leftChildIndex++;
            }
            if (array[parentIndex].isExecutionTimeLessAsComparedTo(array[leftChildIndex])) {
                //in case the execution time of the parent is less than that of the child then there is no need to adjust the heap.
                break;
            }
            swapNodes(parentIndex, leftChildIndex);
            parentIndex = leftChildIndex;
        }
    }

    /**
     * Returns the index of the left child of the heap.
     *
     * @param currentIndex the index of the current node.
     * @return the left child of the current node.
     */
    private int getLeftChildIndex(int currentIndex) {
        return 2 * currentIndex;
    }

    /**
     * Returns the index of the right child of the node. Need the left child index for that.
     *
     * @param leftChildIndex the index of the left child of the current node.
     * @return the right child of the current node.
     */
    private int getRightChildIndex(int leftChildIndex) {
        return leftChildIndex + 1;
    }

    /**
     * Swaps the nodes at i and j index of the heap.
     *
     * @param i the index of the ith node.
     * @param j the index of the jth node.
     */
    private void swapNodes(int i, int j) {
        Building swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }
}

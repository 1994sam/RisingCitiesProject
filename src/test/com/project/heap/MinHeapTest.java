package test.com.project.heap;

import com.project.bean.Building;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MinHeapTest {

    @Test
    void setUp() {
        MinHeap heap = new MinHeap();
        Assertions.assertTrue(heap.getSize() == 0);
    }

    @Test
    void checkIfAddsBelow() {
        MinHeap heap = new MinHeap();
        Building building = new Building(1, 10);
        heap.addBuilding(building);
        Assertions.assertEquals(1, heap.getSize(), "Size is not valid");
        building = new Building(2, 2, 10);
        heap.addBuilding(building);
        Assertions.assertEquals(2, heap.getSize(), "Size has not increased");
        Building min = heap.getMin();
        Assertions.assertTrue(min.getBuildingNumber() == 1);
        min = heap.getMin();
        Assertions.assertTrue(min.getBuildingNumber() == 2);
    }

    @Test
    void checkIfAddsSameExecutedTime() {
        MinHeap heap = new MinHeap();
        Building building = new Building(10, 2, 10);
        heap.addBuilding(building);
        building = new Building(5, 2, 10);
        heap.addBuilding(building);
        building = new Building(4, 2, 10);
        heap.addBuilding(building);
        Building min = heap.getMin();
        Assertions.assertTrue(min.getBuildingNumber() == 4);
        min = heap.getMin();
        Assertions.assertTrue(min.getBuildingNumber() == 5);
        min = heap.getMin();
        Assertions.assertTrue(min.getBuildingNumber() == 10);
    }

    @Test
    void getMin() {
        MinHeap heap = new MinHeap();
        Building building = new Building(1, 3, 10);
        heap.addBuilding(building);
        building = new Building(2, 2, 10);
        heap.addBuilding(building);
        building = new Building(2, 1, 10);
        heap.addBuilding(building);
        Building min = heap.getMin();
        Assertions.assertTrue(min.getExecutedTime() == 1);
    }
}
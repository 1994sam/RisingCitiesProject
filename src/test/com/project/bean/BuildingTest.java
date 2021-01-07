package test.com.project.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void getBuildingID() {
        Building building = new Building(1, 3, 9);
        assertEquals(1, building.getBuildingNumber());
    }

    @Test
    void getExecutedTime() {
        Building building = new Building(1, 3, 9);
        assertEquals(3, building.getExecutedTime());
        building = new Building(1, 9);
        assertEquals(0, building.getExecutedTime());
    }

    @Test
    void getTotalTime() {
        Building building = new Building(1, 9);
        assertEquals(9, building.getTotalTime());
    }

    @Test
    void isExecutionTimeLessAsComparedTo() {
        Building building1 = new Building(1, 0, 9);
        Building building2 = new Building(1, 2, 9);
        assertTrue(building1.isExecutionTimeLessAsComparedTo(building2));
        assertFalse(building1.isExecutionTimeLessAsComparedTo(building1));
    }
}
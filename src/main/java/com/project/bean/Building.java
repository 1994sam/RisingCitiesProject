package main.java.com.project.bean;

/**
 * The data structure which stores the building data like the building number, the execution time and the total time.
 */
public class Building {
    /**
     * The building number.
     */
    private int buildingNumber;
    /**
     * Total number of days spent so far on this building.
     */
    private int executedTime;
    /**
     * The total number of days needed to complete the construction of the building.
     */
    private int totalTime;

    public Building(int buildingNumber, int totalTime) {
        this.buildingNumber = buildingNumber;
        this.executedTime = 0;
        this.totalTime = totalTime;
    }

    public Building(int buildingNumber, int executedTime, int totalTime) {
        this.buildingNumber = buildingNumber;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    /**
     * Returns the building number of the current building.
     *
     * @return the building number
     */
    public int getBuildingNumber() {
        return buildingNumber;
    }

    /**
     * Sets the number of days spent on construction of the building.
     *
     * @param executedTime the number of days spent.
     */
    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    /**
     * Returns the time spent in the construction of the building.
     *
     * @return the time spent on construction of the building.
     */
    public int getExecutedTime() {
        return executedTime;
    }

    /**
     * The total time to be spent on the construction of the building.
     *
     * @return the total time to be spent on the construction of the building.
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * Compares the execution time of the building with the execution time of the building passed as the parameter
     * and return true or false. But in case, wherein, the execution time is equal to the other building then true will be
     * returned if the {@code buildingNumber} is less than {@code building.buildingNumber}.
     *
     * @param building with which the execution time is to be compared.
     * @return true if the execution time is less than the execution time of the building passed as parameter.
     */
    public boolean isExecutionTimeLessAsComparedTo(Building building) {
        if (this.executedTime == building.executedTime) {
            return buildingNumber < building.buildingNumber;
        }
        return this.executedTime < building.executedTime;
    }

    /**
     * Returns the building details in the format (buildingNumber, executedTime, totalTime)
     *
     * @return returns the building details in format like (50, 10, 20)
     */
    @Override
    public String toString() {
        return "(" + buildingNumber +
                "," + executedTime +
                "," + totalTime +
                ')';
    }
}

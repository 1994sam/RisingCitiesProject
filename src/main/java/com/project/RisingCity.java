package main.java.com.project;

import com.project.bean.Building;
import com.project.heap.MinHeap;
import com.project.tree.RedBackTree;
import com.project.tree.TreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Driver class of the program which will process the input and produce the output in output_file.txt.
 */
public class RisingCity {
    private final MinHeap heap;
    private final RedBackTree tree;
    PrintWriter writer;

    /**
     * Initialises the rising city class
     *
     * @throws FileNotFoundException if the file is not found.
     */
    public RisingCity() throws FileNotFoundException {
        heap = new MinHeap();
        tree = new RedBackTree();
        writer = new PrintWriter("output_file.txt");
    }

    /**
     * This method is responsible to perform the operations specified in the command.
     *
     * @param command              the command to execute.
     * @param buildingNumber       the building number which is to be updated in case of print operation.
     * @param daysOfWorkDone       the number of days worked on the building.
     * @param updateBeforePrinting flags which tells whether to update the building before printing or not.
     * @throws Exception if there is any error while processing the command.
     */
    private void executeCommand(String command, int buildingNumber, int daysOfWorkDone, boolean updateBeforePrinting) throws Exception {
        if (command.contains("Ins")) {
            //insert command is passed and the insert operation will be performed.
            performInsertOperation(command);
        } else {
            //print operation will be performed.
            performPrintOperation(command, buildingNumber, daysOfWorkDone, updateBeforePrinting);
        }
    }

    /**
     * Method responsible to insert the building in the min heap as well as the red black tree.
     *
     * @param command the insert command which contains the detail to insert the building.
     * @throws Exception id there is any problem in getting the data from the command.
     */
    private void performInsertOperation(String command) throws Exception {
        String arguments = command.substring(command.indexOf('(') + 1, command.indexOf(')'));
        String[] parameters = arguments.split(",");
        Building building = new Building(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
        heap.addBuilding(building);
        tree.addBuilding(building);
    }

    /**
     * Performs the printing of currently active buildings on which construction needs to be done.
     *
     * @param command              the print command.
     * @param buildingNumber       the building number which needs to be updated before the print command.
     * @param daysOfWorkDone       the number of days worked on the building.
     * @param updateBeforePrinting the flag which suggest if we need to update the building.
     */
    private void performPrintOperation(String command, int buildingNumber, int daysOfWorkDone, boolean updateBeforePrinting) {
        String arguments = command.substring(command.indexOf('(') + 1, command.indexOf(')'));
        List<Building> allBuildings = new ArrayList<>();
        if (updateBeforePrinting) {
            updateBuildingInRedBlackTree(buildingNumber, daysOfWorkDone);
        }
        if (arguments.contains(",")) {
            String[] parameters = arguments.split(",");
            allBuildings.addAll(tree.getBuildingsInRange(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1])));
        } else {
            TreeNode building = tree.searchBuilding(Integer.parseInt(arguments));
            if (building != null) {
                allBuildings.add(building.getBuilding());
            }
        }
        printBuildingInformation(allBuildings);
    }

    /**
     * Prints the active buildings. If the {@code buildings} is empty, then (0,0,0) is printed.
     *
     * @param buildings the buildings to be printed.
     */
    private void printBuildingInformation(List<Building> buildings) {
        if (buildings.isEmpty()) {
            writer.print("(0,0,0)");
        } else {
            writer.print(buildings.get(0).toString());
            for (int i = 1; i < buildings.size(); i++) {
                writer.print("," + buildings.get(i).toString());
            }
        }
        writer.println();
    }

    /**
     * Function responsible to drive the program.
     *
     * @param args program arguments which will help us get the file name as input.
     * @throws Exception if any problem occurs while running the program.
     */
    public static void main(String[] args) throws Exception {
        int globalTime = 0; //stores the global time
        Scanner scanner = new Scanner(new File(args[0]));
        String command = scanner.nextLine();
        boolean working = false;
        Building workingOn = null;
        int endDate = 0;
        int daysWorked = 0;

        RisingCity city = new RisingCity();

        do {
            if (!working && !city.heap.isEmpty()) {
                //if there is no building to work on and the heap is not empty, then we can start working on a building.
                workingOn = city.heap.getMin(); //get a new building to work on
                //calculate the number of days for which we need to work on the current building.
                endDate = Math.min(workingOn.getExecutedTime() + 5, workingOn.getTotalTime());
                daysWorked = workingOn.getExecutedTime();
                working = true;
            }
            if (command != null && command.startsWith(globalTime + ":")) {
                //in case there is some input present and it is time to execute the input command, then start working on it.
                if (working) {
                    // in case of Print operation we need to update the building first and then print the output
                    workingOn.setExecutedTime(daysWorked);
                    city.executeCommand(command, workingOn.getBuildingNumber(), workingOn.getExecutedTime() + 1, true);
                } else {
                    //if we are not currently working on a building then there is not need to update the building.
                    city.executeCommand(command, 0, daysWorked, false);
                }
                if (scanner.hasNextLine()) {
                    //if there is more input to process then store it.
                    command = scanner.nextLine();
                } else {
                    command = null;
                }
            }
            if (working) {
                //if currently working on a building
                daysWorked++;
                if (daysWorked == endDate) {
                    //if the number of days that we need to work on the building is completed, then stop the work on the building.
                    working = false;
                    int totalTime = workingOn.getTotalTime();
                    if (daysWorked == totalTime) {
                        //if the number of days worked matches the number total_time, then remove the building red black tree.
                        city.tree.removeBuilding(workingOn);
                        //print the data showing when the work of the building finished.
                        city.writer.println("(" + workingOn.getBuildingNumber() + "," + (globalTime) + ")");
                    } else {
                        //if we still need to work on the building, then add the building to min heap so that we can continue to work on the building afterwards.
                        Building building = new Building(workingOn.getBuildingNumber(), daysWorked, workingOn.getTotalTime());
                        city.heap.addBuilding(building);
                        city.updateBuildingInRedBlackTree(workingOn.getBuildingNumber(), daysWorked);
                    }
                }
            }
            globalTime++;
        } while (!city.heap.isEmpty() || working || command != null);
        //flush the data to file and close the writer.
        city.writer.flush();
        city.writer.close();
    }

    /**
     * Updates the building in the red black tree.
     *
     * @param buildingNumber   the building number of the building which is to be updated.
     * @param newExecutionTime the total number of days spent on working on the building.
     */
    private void updateBuildingInRedBlackTree(int buildingNumber, int newExecutionTime) {
        TreeNode treeNode = tree.searchBuilding(buildingNumber);
        treeNode.getBuilding().setExecutedTime(newExecutionTime);
    }

}

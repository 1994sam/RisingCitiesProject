package main.java.com.project.tree;

import com.project.bean.Building;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure which has the implementation of a redd black tree.
 */
public class RedBackTree {
    private TreeNode root;

    public RedBackTree() {
        root = null;
    }

    /**
     * Returns the buildings in the range [buildingNumber1, buildingNumber2]
     *
     * @param buildingNumber1 the starting building number.
     * @param buildingNumber2 the ending building number.
     * @return the building in the range.
     */
    public List<Building> getBuildingsInRange(int buildingNumber1, int buildingNumber2) {
        List<Building> allBuildings = new ArrayList<>();
        getBuildingsInRange(root, buildingNumber1, buildingNumber2, allBuildings);
        return allBuildings;
    }

    /**
     * Recursive function which finds the buildings in the range [buildingNumber1, buildingNumber2].
     *
     * @param node         the node on which we need to traverse to find the buildings in the range.
     * @param start        the starting building number.
     * @param end          the ending building number.
     * @param allBuildings the list in which the buildings are to be added.
     */
    private void getBuildingsInRange(TreeNode node, int start, int end, List<Building> allBuildings) {
        if (node == null) {
            return;
        }
        if (start < node.getBuilding().getBuildingNumber()) {
            //if the building number of the current node is less than the starting building number, then go on going left.
            getBuildingsInRange(node.leftChild, start, end, allBuildings);
        }
        if (start <= node.getBuilding().getBuildingNumber() && node.getBuilding().getBuildingNumber() <= end) {
            //the the building number is in the range, then add it to the list which is to be returned.
            allBuildings.add(node.getBuilding());
        }
        if (end > node.getBuilding().getBuildingNumber()) {
            //if the building number of the current node is greater than the ending building number, then go on going right.
            getBuildingsInRange(node.rightChild, start, end, allBuildings);
        }
    }

    /**
     * Removes the building from the red black tree.
     *
     * @param building the building which needs to be removed.
     */
    public void removeBuilding(Building building) {
        TreeNode buildingNodeToDelete = searchBuilding(building.getBuildingNumber());
        if (buildingNodeToDelete != null) {
            //if the building is present in the tree then delete the node.
            removeBuilding(buildingNodeToDelete);
        }
    }


    /**
     * Removes the node from the red black tree.
     *
     * @param node the node which needs to be removed.
     */
    private void removeBuilding(TreeNode node) {
        TreeNode parent;
        TreeNode child;
        NodeColor nodeColor = null;

        //case wherein degree two node is to be removed
        if (node.leftChild != null && node.rightChild != null) {
            TreeNode temp = node;
            temp = temp.rightChild;
            //find the replacement node
            while (temp.leftChild != null) {
                temp = temp.leftChild;
            }
            if (node.parent != null) {
                //if the node is not root node then find if the node to be removed is the left or the right
                //node of the parent and accordingly remove the node.
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = temp;
                } else {
                    node.parent.rightChild = temp;
                }
            } else {
                root = temp;
            }

            //child is the right child of the node which is to be replaced which needs adjustment
            child = temp.rightChild;
            parent = temp.parent;
            nodeColor = temp.getNodeColor();

            //replace the node with its successor.
            if (parent == node) {
                parent = temp;
            } else {
                if (child != null) {
                    child.parent = parent;
                }
                parent.leftChild = child;

                temp.rightChild = node.rightChild;
                node.rightChild.parent = temp;
            }

            temp.parent = node.parent;
            temp.setNodeColor(node.getNodeColor());
            temp.leftChild = node.leftChild;
            node.leftChild.parent = temp;

            if (NodeColor.BLACK == nodeColor) {
                //if the node color is black then re-balance the node.
                balanceAfterDelete(child, parent);
            }

            node = null;
            return;
        }

        if (node.leftChild != null) {
            child = node.leftChild;
        } else {
            child = node.rightChild;
        }

        parent = node.parent;
        nodeColor = node.getNodeColor();

        if (child != null) {
            child.parent = parent;
        }

        //in case the node to deleted was not the root node.
        if (parent != null) {
            if (parent.leftChild == node) {
                parent.leftChild = child;
            } else {
                parent.rightChild = child;
            }
        } else {
            root = child;
        }

        if (NodeColor.BLACK == nodeColor) {
            //if the node color is black then re-balance the node.
            balanceAfterDelete(child, parent);
        }
        node = null;
    }

    /**
     * After deletion, the red black tree might go out of balance. This function brings the red-black tree back to balance.
     *
     * @param node   the node on which needs to be balanced.
     * @param parent the parent of that node.
     */
    private void balanceAfterDelete(TreeNode node, TreeNode parent) {
        TreeNode temp;
        while ((node == null || node.isBlack()) && (node != root)) {
            if (parent.leftChild == node) {
                //node which is to be balanced is the left child of the parent.
                temp = parent.rightChild;
                if (temp.isRed()) {
                    //case wherein the uncle is a red node.
                    temp.setNodeColor(NodeColor.BLACK);
                    parent.setNodeColor(NodeColor.RED);
                    rotateLeft(parent);
                    temp = parent.rightChild;
                }

                if ((temp.leftChild == null || temp.leftChild.isBlack()) &&
                        (temp.rightChild == null || temp.rightChild.isBlack())) {
                    //case wherein the uncle and the children are of the color black.
                    temp.setNodeColor(NodeColor.RED);
                    node = parent;
                    parent = node.parent;
                } else {
                    //case wherein the right node is null or black and the left child is red node.
                    if (temp.rightChild == null || temp.rightChild.isBlack()) {
                        temp.leftChild.setNodeColor(NodeColor.BLACK);
                        temp.setNodeColor(NodeColor.RED);
                        rotateRight(temp);
                        temp = parent.rightChild;
                    }
                    //in case the uncle is node is black and the right child of uncle is red
                    temp.setNodeColor(parent.getNodeColor());
                    parent.setNodeColor(NodeColor.BLACK);
                    temp.rightChild.setNodeColor(NodeColor.BLACK);
                    rotateLeft(parent);
                    node = root;
                    break;
                }
            } else {
                //case wherein the node to be balanced is the right node of the parent
                temp = parent.leftChild;
                if (temp.isRed()) {
                    //if the uncle is red node
                    temp.setNodeColor(NodeColor.BLACK);
                    parent.setNodeColor(NodeColor.RED);
                    rotateRight(parent);
                    temp = parent.leftChild;
                }

                if ((temp.leftChild == null || temp.leftChild.isBlack()) &&
                        (temp.rightChild == null || temp.rightChild.isBlack())) {
                    //case wherein the children are black.
                    temp.setNodeColor(NodeColor.RED);
                    node = parent;
                    parent = node.parent;
                } else {
                    if (temp.leftChild == null || temp.leftChild.isBlack()) {
                        //case wherein uncle is black and the left child of the uncle is black.
                        temp.rightChild.setNodeColor(NodeColor.BLACK);
                        temp.setNodeColor(NodeColor.RED);
                        rotateLeft(temp);
                        temp = parent.leftChild;
                    }
                    temp.setNodeColor(parent.getNodeColor());
                    parent.setNodeColor(NodeColor.BLACK);
                    temp.leftChild.setNodeColor(NodeColor.BLACK);
                    rotateRight(parent);
                    node = root;
                    break;
                }
            }
        }

        if (node != null) {
            //after processing set the color of the node to black.
            node.setNodeColor(NodeColor.BLACK);
        }
    }

    /**
     * Search the building in the red black tree.
     *
     * @param buildingNumber the number of building which is to be searched.
     * @return return the node if found else null is returned.
     */
    public TreeNode searchBuilding(int buildingNumber) {
        return searchBuilding(root, buildingNumber);
    }

    public TreeNode searchBuilding(TreeNode currentNode, int buildingNumber) {
        if (currentNode == null) {
            return null;
        }
        if (buildingNumber < currentNode.getBuilding().getBuildingNumber()) {
            //the number of the building which to be found is less than the building number of the current building.
            return searchBuilding(currentNode.leftChild, buildingNumber);
        } else if (buildingNumber > currentNode.getBuilding().getBuildingNumber()) {
            //the number of the building which to be found is greater than the building number of the current building.
            return searchBuilding(currentNode.rightChild, buildingNumber);
        } else {
            //the number of the building which to be found is equal to the building number of the current building.
            return currentNode;
        }
    }

    /**
     * Adds the building to the red black tree.
     *
     * @param building the building which is to be added to the read black tree.
     * @throws Exception if there is any problem in added the building to the red black tree.
     */
    public void addBuilding(Building building) throws Exception {
        TreeNode node = new TreeNode(building.getBuildingNumber(), building, null);
        TreeNode x = root;
        TreeNode y = null;
        while (x != null) {
            y = x;
            if (building.getBuildingNumber() < x.getBuilding().getBuildingNumber()) {
                //if the building number of the building is less than the current building then move to left.
                x = x.leftChild;
            } else if (building.getBuildingNumber() > x.getBuilding().getBuildingNumber()) {
                //if the building number of the building is greater than the current building then  move to right.
                x = x.rightChild;
            } else {
                //if the building is already present in the red black tree, then throw exception.
                throw new Exception("com.project.bean.Building " + building.getBuildingNumber() + " is already present!");
            }
        }
        node.parent = y;

        if (y != null) {
            if (building.getBuildingNumber() < y.getBuilding().getBuildingNumber()) {
                //if the building number of the building is less than the current building then add the node as the left node.
                y.leftChild = node;
            } else {
                //if the building number of the building is greater than the current building then add the node as the right node.
                y.rightChild = node;
            }
        } else {
            //if the red black tree is empty.
            root = node;
        }
        //balance after insert.
        balanceAfterInsert(node);
    }

    /**
     * Balance the node after insert operation is performed.
     *
     * @param node the node on which the balancing is to be done.
     */
    private void balanceAfterInsert(TreeNode node) {
        TreeNode p;
        TreeNode gp;

        while (((p = node.parent) != null) && (p.isRed())) {
            gp = p.parent;
            if (p == gp.leftChild) {
                //in case the parent is the left child of the grand parent.
                TreeNode uncle = gp.rightChild;
                if (uncle != null && uncle.isRed()) {
                    //in case the uncle node is red then flip the colors
                    uncle.setNodeColor(NodeColor.BLACK);
                    p.setNodeColor(NodeColor.BLACK);
                    gp.setNodeColor(NodeColor.RED);
                    node = gp;
                    continue;
                }

                if (p.rightChild == node) {
                    //in case if the parent node is right child of the parent, then rotate left
                    TreeNode temp;
                    rotateLeft(p);
                    temp = p;
                    p = node;
                    node = temp;
                }

                p.setNodeColor(NodeColor.BLACK);
                gp.setNodeColor(NodeColor.RED);
                rotateRight(gp);
            } else {
                //in case the parent is the right child of the grand parent.
                TreeNode uncle = gp.leftChild;
                if (uncle != null && uncle.isRed()) {
                    //in case the uncle node is red then flip the colors
                    uncle.setNodeColor(NodeColor.BLACK);
                    p.setNodeColor(NodeColor.BLACK);
                    gp.setNodeColor(NodeColor.RED);
                    node = gp;
                    continue;
                }

                if (p.leftChild == node) {
                    //in case if the node is the left child of the parent then rotate right
                    TreeNode temp;
                    rotateRight(p);
                    temp = p;
                    p = node;
                    node = temp;
                }

                p.setNodeColor(NodeColor.BLACK);
                gp.setNodeColor(NodeColor.RED);
                rotateLeft(gp);
            }
        }
        //the root node needs to be black
        root.setNodeColor(NodeColor.BLACK);
    }

    /**
     * Perform right rotation.
     *
     * @param node the node on which right rotate is to be performed.
     */
    private void rotateRight(TreeNode node) {
        TreeNode temp = node.leftChild;

        node.leftChild = temp.rightChild;
        //if the right child of temp is not null then setting the node as the parent of the right child
        if (temp.rightChild != null) {
            temp.rightChild.parent = node;
        }
        temp.parent = node.parent;
        if (node.parent == null) {
            root = temp; //if node's parent is not is there, then the node is root of the tree.
            // So setting temp as the root of the tree.
        } else {
            if (node == node.parent.rightChild) {
                node.parent.rightChild = temp;
            } else {
                node.parent.leftChild = temp;
            }
        }
        temp.rightChild = node;
        node.parent = temp;
    }

    /**
     * Perform left rotation.
     *
     * @param node the node on which left rotate is to be performed.
     */
    private void rotateLeft(TreeNode node) {
        TreeNode temp = node.rightChild;

        node.rightChild = temp.leftChild;
        //if the left child of temp is not null then setting the node as the parent of the left child
        if (temp.leftChild != null) {
            temp.leftChild.parent = node;
        }

        temp.parent = node.parent;

        if (node.parent == null) {
            root = temp; //if node's parent is not is there, then the node is root of the tree.
            // So setting temp as the root of the tree.
        } else {
            if (node.parent.leftChild == node) {
                node.parent.leftChild = temp;
            } else {
                node.parent.rightChild = temp;
            }
        }

        temp.leftChild = node;
        node.parent = temp;
    }
}

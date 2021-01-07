package main.java.com.project.tree;

import com.project.bean.Building;

/**
 * This class represent the node structure of a node in a red black tree.
 */
public class TreeNode {
    /**
     * The key of the node on which the node insertion will be carried out.
     */
    private int key;
    /**
     * The color of the node. Can take values RED or BLACK.
     */
    private NodeColor nodeColor;
    /**
     * The building that will be stored in the node.
     */
    private Building building;

    /**
     * The left child of the node.
     */
    public TreeNode leftChild;
    /**
     * The right child of the node.
     */
    public TreeNode rightChild;
    /**
     * The parent of the node.
     */
    public TreeNode parent;

    public TreeNode(int key, Building building, TreeNode parent) {
        this.key = key;
        this.building = building;
        this.nodeColor = NodeColor.RED;
        leftChild = null;
        rightChild = null;
        this.parent = parent;
    }

    /**
     * @return Returns the color of the node.
     */
    public NodeColor getNodeColor() {
        return nodeColor;
    }

    /**
     * @return Return the building that the node represents.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * @return Returns true if the color of the node is black.
     */
    public boolean isBlack() {
        return NodeColor.BLACK == nodeColor;
    }

    /**
     * @return Returns true if the color of the node is red.
     */
    public boolean isRed() {
        return NodeColor.RED == nodeColor;
    }

    /**
     * Sets the node color to RED or BLACK
     *
     * @param nodeColor the color that is to be set on the node.
     */
    public void setNodeColor(NodeColor nodeColor) {
        this.nodeColor = nodeColor;
    }
}

/**
 * Enum that represent the color of the node.
 */
enum NodeColor {
    RED, BLACK;
}
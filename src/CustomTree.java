package structures;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

import java.io.IOException;

public class CustomTree<T> {

    private T LABEL;
    private CustomTree<T> PARENT = null;
    private List<CustomTree<T>> CHILDREN;

    private String DISPLAYSEPARATOR = " | ";

    public CustomTree(T label) {
        this.LABEL = label;
        this.CHILDREN = new ArrayList<CustomTree<T>>();
    }

    /**
     * Returns the label of the node, it's type is T (avoid Integer).
     */
    public T getLabel() {
        return this.LABEL;
    }

    /**
     * Returns the parent node.
     */
    public CustomTree<T> getParent() {
        return this.PARENT;
    }

    /**
     * Returns the ArrayList containing every child nodes.
     */
    public List<CustomTree<T>> getChildren() {
        return this.CHILDREN;
    }

    /**
     * Returns the number of child nodes, or CHILDREN size.
     */
    public int getNbChildren() {
        return this.CHILDREN.size();
    }

    /**
     * Returns the child node at the given position.
     * If you're sure that the index is not out of bounds,
     * use getChildren().get(index) to avoid handling IOException.
     * @param index Position of the child node.
     * @throws IOException Thrown when out of bounds.
     */
    public CustomTree<T> getChild(int index)
    throws IOException {
        if (index >= 0 && index < this.getNbChildren()) {
            return this.CHILDREN.get(index);
        } else {
            String strIndex = Integer.toString(this.getNbChildren());
            throw new IOException("Invalid index: should be between 0 and " + strIndex + " (excluded)!");
        }
    }

    /**
     * Retuns whether a label represents a child node or not.
     * @param label Label of type T to find.
     * @param recursive Search among every nodes that originate from this one.
     */
    public boolean contains(T label, boolean recursive) {
        for (int index = 0; index < this.getNbChildren(); index++) {
            if (this.CHILDREN.get(index).getLabel().equals(label)) {
                return true;
            } else {
                if (recursive) {
                    if (this.CHILDREN.get(index).contains(label, recursive)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Returns the child node with the label T.
     * Should be used if you're sure that the label is contained.
     * @param label Label of type T to find.
     * @throws IOException Thrown when label is not contained.
     */
    public CustomTree<T> getChild(T label)
    throws IOException {
        for (int index = 0; index < this.getNbChildren(); index++) {
            if (this.CHILDREN.get(index).getLabel().equals(label)) {
                return this.getChild(index);
            }
        }
        throw new IOException("No such label among children: " + label + "!");
    }

    /**
     * Returns the number of children levels this node has.
     * 0 for the root.
     */
    public int getDepth() {
        int depth = 0;
        if (this.getNbChildren() > 0) {
            for (int index = 0; index < this.getNbChildren(); index++) {
                depth = Math.max(depth, this.CHILDREN.get(index).getDepth());
            }
            depth++;
        }
        return depth;
    }

    /**
     * Returns the number of nodes to the root.
     * 0 for the root.
     */
    public int getNodeDepth() {
        if (this.PARENT == null) {
            return 0;
        } else {
            return this.PARENT.getNodeDepth() + 1;
        }
    }

    /**
     * Returns if the node DOES NOT have children (leaf).
     */
    public boolean isLeaf() {
        return this.getNbChildren() > 0;
    }

    /**
     * Adds a parent to the node which is NOT the root anymore.
     * @param parent
     */
    public void setParent(CustomTree<T> parent) {
        this.PARENT = parent;
    }
    
    /**
     * Create a child node of this one with the given label.
     * 2 direct children can not have the same label.
     * @param label Label used to create the node.
     * @throws IOException Thrown if a child already has the label.
     */
    public CustomTree<T> addChild(T label)
    throws IOException {
        if (!this.contains(label, false)) {
            CustomTree<T> child = new CustomTree<T>(label);
            child.setParent(this);
            this.CHILDREN.add(child);
            return child;
        }
        throw new IOException("Label " + label + " not unique among children!");
    }

    /**
     * Returns the pattern to separate 2 children's labels.
     */
    public String getDisplaySeparator() {
        return this.DISPLAYSEPARATOR;
    }

    /**
     * Set the pattern to separate 2 children's labels.
     * @param displaySeparator Pattern to be printed between labels.
     */
    public void setDisplaySeparator(String displaySeparator) {
        this.DISPLAYSEPARATOR = displaySeparator;
    }

    /**
     * Prints each node and the list of children, then prints each child node
     * in the Depth-First Search order (go to children before neighbors).
     * @param displayLeaf Displays a node if it's a leaf.
     */
    public void display(boolean displayLeaf) {
        if (isLeaf() || displayLeaf) {
            String lineToDisplay = this.getLabel() + ": [";
            for (int index = 0; index < this.getNbChildren(); index++) {
                if (index > 0) {
                    lineToDisplay += this.DISPLAYSEPARATOR;
                }
                lineToDisplay += this.CHILDREN.get(index).getLabel();
            }
            lineToDisplay += "]";
            System.out.println(lineToDisplay);
            for (int index = 0; index < this.getNbChildren(); index++) {
                this.CHILDREN.get(index).display(displayLeaf);
            }
        }
    }

    /**
     * Returns the first neighbor or node that is not a child node,
     * in the Depth-First Search order (go to neighbors before children).
     * If no neighbor is found, returns null.
     */
    private CustomTree<T> nextNotChild() {
        if (this.PARENT != null) {
            for (int index = 0; index < this.PARENT.getNbChildren(); index++) {
                if (this.PARENT.getChildren().get(index).getLabel().equals(this.getLabel())) {
                    if (index + 1 == this.PARENT.getNbChildren()) {
                        return this.PARENT.nextNotChild();
                    } else {
                        return this.PARENT.getChildren().get(index+1);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns if there is a next node, both for Depth-First
     * and Breadth-First Search.
     */
    public boolean hasNext() {
        boolean fail = this.isLeaf() && this.nextNotChild() == null;
        return !fail;
    }
    
    /**
     * Returns the next node according to the Depth-First Search.
     * Returns null if hasNext() is false.
     */
    public CustomTree<T> nextDepthFirst() {
        if (!this.isLeaf()) {
            return this.getChildren().get(0);
        } else {
            return this.nextNotChild();
        }
    }

    /**
     * Returns the next node according to the Breadth-First Search.
     * Returns null if hasNext() is false.
     */
    public CustomTree<T> nextBreadthFirst() {
        CustomTree<T> potential = this.nextNotChild();
        while (potential != null && potential.getNodeDepth() < this.getNodeDepth()) {
            if (potential.isLeaf()) {
                potential = potential.nextNotChild();
            } else {
                potential = potential.getChildren().get(0);
            }
        }
        if (potential == null) {
            if (!this.isLeaf()) {
                return this.CHILDREN.get(0);
            }
        }
        return potential;
    }
}

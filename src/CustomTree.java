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

    public T getLabel() {
        return this.LABEL;
    }

    public CustomTree<T> getParent() {
        return this.PARENT;
    }

    public List<CustomTree<T>> getChildren() {
        return this.CHILDREN;
    }

    public int getNbChildren() {
        return this.CHILDREN.size();
    }

    public CustomTree<T> getChild(int index)
    throws IOException {
        if (index >= 0 && index < this.getNbChildren()) {
            return this.CHILDREN.get(index);
        } else {
            String strIndex = Integer.toString(this.getNbChildren());
            throw new IOException("Invalid index: should be between 0 and " + strIndex + " (excluded)!");
        }
    }

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
    
    public CustomTree<T> getChild(T label)
    throws IOException {
        for (int index = 0; index < this.getNbChildren(); index++) {
            if (this.CHILDREN.get(index).getLabel().equals(label)) {
                return this.getChild(index);
            }
        }
        throw new IOException("No such label among children: " + label + "!");
    }

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

    public int getNodeDepth() {
        if (this.PARENT == null) {
            return 0;
        } else {
            return this.PARENT.getNodeDepth() + 1;
        }
    }

    public boolean isLeaf() {
        return this.getNbChildren() > 0;
    }

    public void setParent(CustomTree<T> parent) {
        this.PARENT = parent;
    }
    
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

    public String getDisplaySeparator() {
        return this.DISPLAYSEPARATOR;
    }

    public void setDisplaySeparator(String displaySeparator) {
        this.DISPLAYSEPARATOR = displaySeparator;
    }

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

    public boolean hasNext() {
        boolean fail = this.isLeaf() && this.nextNotChild() == null;
        return !fail;
    }

    public CustomTree<T> nextDepthFirst() {
        if (!this.isLeaf()) {
            return this.getChildren().get(0);
        } else {
            return this.nextNotChild();
        }
    }

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

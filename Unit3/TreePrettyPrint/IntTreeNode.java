package Unit3.TreePrettyPrint;

public class IntTreeNode {
    public int data;
    public IntTreeNode left;
    public IntTreeNode right;

    public IntTreeNode(int data) {
        this.data = data;
        left = null;
        right = null;
    }

    public int getData() {
        return this.data;
    }

    public IntTreeNode getLeft() {
        return this.left;
    }

    public IntTreeNode getRight() {
        return this.right;
    }

    public void setLeft(IntTreeNode har) {
        this.left = har;
    }

    public void setRight(IntTreeNode har) {
        this.right = har;
    }

    public void add(IntTreeNode current, IntTreeNode added) {
        if (added != null) {
            IntTreeNode currentLocation = current;
            boolean running = true;

            while (running) {
                if (added.getData() < currentLocation.getData()) {
                    if (currentLocation.getLeft() == null) {
                        currentLocation.setLeft(added);
                        running = false;
                    } else {
                        currentLocation = currentLocation.getLeft();
                    }
                } else {
                    if (currentLocation.getRight() == null) {
                        currentLocation.setRight(added);
                        running = false;
                    } else {
                        currentLocation = currentLocation.getRight();
                    }
                }
            }
        }
    }

}
package Unit3.TreePrettyPrint;

public class IntTree {
    
    public IntTreeNode overallRoot;

    public IntTree() {
        overallRoot = null;
    }

    public void addValue(int data) {

        if (overallRoot == null) {
            overallRoot = new IntTreeNode(data);
        } else {
            overallRoot.add(overallRoot, new IntTreeNode(data));
        }

    }

    // preorder printing
    public String toPreOrderString() {
        // preorder is root, left, right
        return preOrder(overallRoot);
    }

    private String preOrder(IntTreeNode node) {
        String accumulator = "";
        if (node != null) {
            accumulator += "[" + node.getData() + "]";
            return accumulator + "(" + preOrder(node.getLeft()) + ")" + "(" + preOrder(node.getRight()) + ")";
        }
        return accumulator;
    }

    // inorder printing
    public String toInOrderString() {
        // inorder is left, root, right
        return inOrder(overallRoot);
    }

    private String inOrder(IntTreeNode node) {
        String accumulator = "";
        if (node != null) {
            accumulator += "[" + node.getData() + "]";
            return "(" + inOrder(node.getLeft()) + ")" + accumulator + "(" + inOrder(node.getRight()) + ")";
        }
        return accumulator;
    }

    // postorder printing

    // expected: (()()[1])((()()[3])(()()[5])[4])[2]
    public String toPostOrderString() {
        // post order is left right root
        return postOrder(overallRoot);
    }

    private String postOrder(IntTreeNode node) {
        String accumulator = "";

        if (node != null) {
            accumulator += "[" + node.getData() + "]";
            return "(" + postOrder(node.getLeft()) + ")" + "(" + postOrder(node.getRight()) + ")" + accumulator;
        }
        return accumulator;
    }

    public String prettyPrint() {

        //find the total length of the nodes.
        int nodes = countNodes(overallRoot);
        //find the depth
        int depth = countDepth(overallRoot);
        //model tree using 2D array
        // IntTreeNode[][] tree = new IntTreeNode[depth][nodes];
        return "" + nodes;
    
    }

    private int countNodes(IntTreeNode node){
        if(node == null) return 0;
        
        int counter = 1;
        int number = node.getData();
        while(number >= 10){
            counter++;
            number/=10;
        }
        counter+=2;

        return counter + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    private int countDepth(IntTreeNode node){
        if(node == null) return 0;
        
        return Math.max(countDepth(node.getLeft()), countDepth(node.getRight()));
    }

}

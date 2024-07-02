import structures.CustomTree;

public class TestCustomTree {
    
    private int testId = 1;
    public static void main(String[] args) {
        CustomTree<String> root = new CustomTree<String>("1");
        CustomTree<String> node1_0 = root.addChild("1.0");
        CustomTree<String> node1_1 = root.addChild("1.1");
        CustomTree<String> node1_0_0 = node1_0.addChild("1.0.0");
        System.out.println(root.getNbChildren());
        System.out.println(root.getDepth());
        for (CustomTree<String> node : root.getChildren()) {
            System.out.println(node.getLabel());
        }
    }
}

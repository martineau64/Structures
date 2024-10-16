import java.io.IOException;

import testTools.TestTemplate;
import structures.CustomTree;

public class TestCustomTree {
    public static void main(String[] args) {
        boolean buffer = true;
        buffer &= testGlobalAccess();
        buffer &= testChildOperation();
        buffer &= testSearch();
        if (buffer) {
            System.out.println("\nEvery tests passed!\n");
        } else {
            System.out.println("\nSome tests might have failed!\n");
        }
    }


    public static boolean testGlobalAccess() {
        TestTemplate tests = new TestTemplate("TEST_GLOBAL_ACCESS", 7);
        CustomTree<String> root = new CustomTree<String>(null);
        tests.displayStartTests();
        // LABEL
        tests.displayTestResult("Get_Null_Label", null, root.getLabel());
        try {
            root.setLabel("");
            tests.displayTestResult("Get_Empty_Label", "", root.getLabel());
        } catch (IOException e) {
            tests.displayTestResult("Get_Empty_Label", e, false);
        }
        try {
            root.setLabel("root");
            tests.displayTestResult("Get_Label", "root", root.getLabel());
        } catch (IOException e) {
            tests.displayTestResult("Get_Label", e, false);
        }
        try {
            root.setLabel(null);
        } catch (IOException e) {
            tests.displayTestResult("Set_Null_Label", e, true);
        }
        try {
            root.setLabel("root");
        } catch (IOException e) {
            tests.displayTestResult("Set_Same_Label", e, true);
        }
        // PARENT & CHILDREN
        tests.displayTestResult("Get_Root_Parent", null, root.getParent());
        root.setParent(root);
        tests.displayTestResult("Get_Parent", root, root.getParent());
        tests.displayEndTests();
        return tests.getFailed() == 0 && tests.getTestId() == tests.getNbTests();
    }


    public static boolean testChildOperation() {
        TestTemplate tests = new TestTemplate("TEST_CHILD_OPERATION", 0);
        tests.displayStartTests();
        tests.displayEndTests();
        return tests.getFailed() == 0 && tests.getTestId() == tests.getNbTests();
    }


    public static boolean testSearch() {
        TestTemplate tests = new TestTemplate("TEST_SEARCH", 0);
        tests.displayStartTests();
        tests.displayEndTests();
        return tests.getFailed() == 0 && tests.getTestId() == tests.getNbTests();
    }
}

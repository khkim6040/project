/**
 * @author: Chanho Song
 * Test code for unused variable in method.
 */

public class DeadCode {

    public static void main(String[] args) {
        DeadCode deadCode = new DeadCode();
        deadCode.exampleMethod();
    }

    public void exampleMethod() {
        int unusedVariable = 5;
        System.out.println("Hello, World!");
    }


}
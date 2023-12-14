/**
 * Test code for unused method and unused variable in the method.
 *
 * @author: Chanho Song
 */

public class DeadCode {

    public static void main(String[] args) {
        DeadCode deadCode = new DeadCode();
        deadCode.usedMethod();
    }

    public void usedMethod() {
        System.out.println("Used");
    }

    private void unusedMethod() {
        System.out.println("Unused");
    }

    private void unusedMethod2() {
        int unusedVariable = 5;
        System.out.println("Hello, World!");
    }

}





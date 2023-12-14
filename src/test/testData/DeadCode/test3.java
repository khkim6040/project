/**
 * Test code for check unused Method.
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

}





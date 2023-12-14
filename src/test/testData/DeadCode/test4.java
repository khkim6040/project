/**
 * @author: Chanho Song
 */

/**
 * Test code for clean code. all 2 methods are used.
 *
 * @author: Chanho Song
 */
public class DeadCode {

    public static void main(String[] args) {
        DeadCode deadCode = new DeadCode();

        deadCode.usedMethod1();
        deadCode.usedMethod2();
    }

    public void usedMethod1() {
        System.out.println("Used");
    }

    private void usedMethod2() {
        System.out.println("Used");
    }

}





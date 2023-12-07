/**
 * Test code for similar 2 method.
 *
 * @author Chanho Song
 */
public class TestFindDuplicatedCode {
    public static void main(String[] args) {
        TestIdentifyLongMethod example = new TestFindDuplicatedCode();
        example.duplicatedMethod1()
        example.duplicatedMethod2()
    }

    public void duplicatedMethod1() {
        for (int i = 1; i <= 5; i++) {
            int a = 0;
            int b = 0;
            int c = 0;
        }
    }
    public void duplicatedMethod2() {
        for (int j = 1; j <= 5; j++) {
            int a = 0;
            int b = 0;
            int c = 0;
        }
    }

}

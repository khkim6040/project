/**
 * An example method with 3 long method code smell.
 * These methods has 33, 40, 30, 10 lines of code.
 *
 * @author Jinyoung Kim
 */
public class TestLongMethod {

    public static void main(String[] args) {
        TestLongMethod example = new TestLongMethod();
        example.tooLongMethod();
    }

    public void tooLongMethod1() {
        int temp = 0;
        temp += 1;
        temp += 2;
        temp += 3;
        temp += 4;
        temp += 5;
        temp += 6;
        temp += 7;
        temp += 8;
        temp += 9;
        temp += 10;
        temp += 11;
        temp += 12;
        temp += 13;
        temp += 14;
        temp += 15;
        temp += 16;
        temp += 17;
        temp += 18;
        temp += 19;
        temp += 20;
        temp += 21;
        temp += 22;
        temp += 23;
        temp += 24;
        temp += 25;
        temp += 26;
        temp += 27;
        temp += 28;
        temp += 29;
        temp += 30;

        // Final result
        System.out.println("Result: " + temp);
    }

    public void tooLongMethod2() {
        int temp = 0;
        temp += 1;
        temp += 2;
        temp += 3;
        temp += 4;
        temp += 5;
        temp += 6;
        temp += 7;
        temp += 8;
        temp += 9;
        temp += 10;
        temp += 11;
        temp += 12;
        temp += 13;
        temp += 14;
        temp += 15;
        temp += 16;
        temp += 17;
        temp += 18;
        temp += 19;
        temp += 20;
        temp += 21;
        temp += 22;
        temp += 23;
        temp += 24;
        temp += 25;
        temp += 26;
        temp += 27;
        temp += 28;
        temp += 29;
        temp += 30;
        temp += 31;
        temp += 32;
        temp += 33;
        temp += 34;
        temp += 35;
        temp += 36;
        temp += 37;

        // Final result
        System.out.println("Result: " + temp);
    }

    public void tooLongMethod3() {
        int temp = 0;
        temp += 1;
        temp += 2;
        temp += 3;
        temp += 4;
        temp += 5;
        temp += 6;
        temp += 7;
        temp += 8;
        temp += 9;
        temp += 10;
        temp += 11;
        temp += 12;
        temp += 13;
        temp += 14;
        temp += 15;
        temp += 16;
        temp += 17;
        temp += 18;
        temp += 19;
        temp += 20;
        temp += 21;
        temp += 22;
        temp += 23;
        temp += 24;
        temp += 25;
        temp += 26;
        temp += 27;

        // Final result
        System.out.println("Result: " + temp);
    }

    public void notLongMethod1() {
        int temp = 0;
        temp += 1;
        temp += 2;
        temp += 3;
        temp += 4;
        temp += 5;
        temp += 6;
        temp += 7;

        // Final result
        System.out.println("Result: " + temp);
    }
}

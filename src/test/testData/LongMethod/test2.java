public class TestLongMethod {

    /**
     * An example method with no long method code smell.
     * This method has 13 lines of code.
     *
     * @author Jinyoung Kim
     */
    public static void main(String[] args) {
        TestLongMethod example = new TestLongMethod();
        example.notLongMethod();
    }

    public void notLongMethod() {
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

        // Final result
        System.out.println("Result: " + temp);
    }
}

public class TestFindDuplicatedCode {
    /**
     * find duplicatedcode test
     *
     * @author Chanho Song
     */
    public static void main(String[] args) {
        TestIdentifyLongMethod example = new TestFindDuplicatedCode();
        example.duplicatedMethod1()
        example.duplicatedMethod2()
    }

    public void duplicatedMethod1() {
        int value = 10;
        if(value > 5 && value < 15)
            value++;
    }
    public void duplicatedMethod2() {
        int value = 10;
        if(value > 5 && value < 15)
            value++;
    }

}

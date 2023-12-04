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
        thisisSimilarmethod1();
        thisisSimilarmethod2();
        thisisSimilarmethod3();
    }
    public void duplicatedMethod2() {
        thisisSimilarmethod4();
        thisisSimilarmethod5();
        thisisSimilarmethod6();
    }

    public void duplicatedMethod2() {
        thisisSimilarmethod7();
        thisisSimilarmethod8();
        thisisSimilarmethod9();
    }


}

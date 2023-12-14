/**
 * Test PsiDeclarationStatement, int a = b.method1().method2()
 * An example code with no message chain code smell.
 * This code has message length of 3. (Default length is 5)
 *
 * @author Jinyoung Kim
 */
public class TestMessageChain {

    private Level1 level1;

    public TestMessageChain() {
        this.level1 = new Level1();
    }

    public void testMethod() {
        // Usage of a method with a message chain of length 6
        String result = level1.getLevel2().getLevel3().getData();

    }

    // Helper classes to create a message chain with "getLevel" methods
    private class Level1 {

        Level2 getLevel2() {
            return new Level2();
        }
    }

    private class Level2 {

        Level3 getLevel3() {
            return new Level3();
        }
    }

    private class Level3 {

        String getData() {
            return "Data";
        }
    }

}

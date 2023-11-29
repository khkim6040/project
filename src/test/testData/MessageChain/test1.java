public class MessageChainExample {

    /**
     * An example code with a message chain code smell.
     * This code has message length of 6.
     *
     * @author Jinyoung Kim
     */
    private Level1 level1;

    public MessageChainExample() {
        this.level1 = new Level1();
    }

    public static void main(String[] args) {
        MessageChainExample example = new MessageChainExample();

        // Usage of a method with a message chain of length 6
        String result = example.level1.level2.level3.level4.level5.level6.getData();

        System.out.println("Result of the message chain: " + result);
    }

    // Helper classes to create a message chain with "level" properties
    private class Level1 {

        Level2 level2 = new Level2();
    }

    private class Level2 {

        Level3 level3 = new Level3();
    }

    private class Level3 {

        Level4 level4 = new Level4();
    }

    private class Level4 {

        Level5 level5 = new Level5();
    }

    private class Level5 {

        Level6 level6 = new Level6();
    }

    private class Level6 {

        String data = "Data";

        String getData() {
            return data;
        }
    }
}

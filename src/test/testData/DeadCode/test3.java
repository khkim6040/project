public class DeadCodeExample {
    public void doSomething(int a) {
        System.out.println("Doing something...");
    }

    public static void main(String[] args) {
        DeadCodeExample example = new DeadCodeExample();
        example.doSomething(10);
    }
}
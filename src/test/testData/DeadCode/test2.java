public class DeadCodeExample {
    private int y; // 사용되지 않는 필드

    public DeadCodeExample(int y) {
        this.y = y;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
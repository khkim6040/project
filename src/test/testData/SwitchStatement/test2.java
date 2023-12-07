/**
 * Test code for type casting in continued if statement
 *
 * @author Chanho Song
 */
class Circle {

    void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle {

    void draw() {
        System.out.println("Drawing Rectangle");
    }
}

class Triangle {

    void draw() {
        System.out.println("Drawing Triangle");
    }
}

public class ShapeDrawer {

    void drawShape(Object shape) {
        if (shape instanceof Circle) {
            ((Circle) shape).draw();
        } else if (shape instanceof Rectangle) {
            ((Rectangle) shape).draw();
        } else if (shape instanceof Triangle) {
            ((Triangle) shape).draw();
        } else {
            throw new IllegalArgumentException("Unknown shape");
        }
    }
}

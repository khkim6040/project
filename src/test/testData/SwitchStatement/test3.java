/**
 * Test code for 2 smelly if statement.
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

public class Test {

    long computeWeight(Element e) {
        if (e instanceof Book) {
            return ((Book) e).getBookWeight();
        } else {
            return ((Collection) e).getTotalWeight();
        }
    }

}
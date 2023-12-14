/**
 * Test code for type casting in switch statement.
 *
 * @author Chanho Song
 */
class Shape {

    String type;

    String getType() {
        return type;
    }

}

class Circle extends Shape {

    Circle() {
        this.type = "circle";
    }

    void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle extends Shape {

    Rectangle() {
        this.type = "rectangle";
    }

    void draw() {
        System.out.println("Drawing Rectangle");
    }
}

class Triangle extends Shape {

    Triangle() {
        this.type = "triangle";
    }

    void draw() {
        System.out.println("Drawing Triangle");
    }
}

class ShapeDrawer {

    void drawShape(Shape shape) {
        switch (shape.getType()) {
            case "circle":
                ((Circle) shape).draw();
                break;
            case "rectangle":
                ((Rectangle) shape).draw();
                break;
            case "triangle":
                ((Triangle) shape).draw();
                break;
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shape.getType());
        }
    }
}
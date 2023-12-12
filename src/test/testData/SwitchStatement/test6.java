/**
 * Test case for 'FindMultiCastedObject' false case
 * If type casting is done to another object, it is not a code smell.
 *
 * @author Chanho Song
 */

class ShapeDrawer {

    void drawShape(Shape shape, Figure figure1, Figure figure2, Figure figure3) {
        switch (shape.getType()) {
            case "circle":
                ((Circle) figure1).draw();
                break;
            case "rectangle":
                ((Rectangle) figure2).draw();
                break;
            case "triangle":
                ((Triangle) figure3).draw();
                break;
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shape.getType());
        }
    }
}


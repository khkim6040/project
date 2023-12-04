/**
 * @author Hyeonbeen Park
 * Test code for type casting in single if statement.
 */

public class Test {

    long computeWeight(Element e) {
        if (e instanceof Book) {
            return ((Book) e).getBookWeight();
        } else {
            return ((Collection) e).getTotalWeight();
        }
    }

}

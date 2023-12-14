/**
 * Test code for type casting in single if statement.
 *
 * @author Hyeonbeen Park
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

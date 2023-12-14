/**
 * An example method with no long parameter list.
 * This method has 3 parameters.
 * Since it is not over 5 parameters, this is not long parameter list.
 *
 * @param name, age, address
 * @author Jinyoung Kim
 */
public class TestLongParameterList {

    public static void main(String[] args) {
        TestLongParameterList example = new TestLongParameterList();

        // Example usage of the method with long parameter list
        example.exampleMethodWithNoLongParameterList(
            "Jinyoung Kim", 24, "postech"
        );
    }

    public void exampleMethodWithNoLongParameterList(String name, int age, String address) {
        // Just printing out the parameters for demonstration
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);

    }
}

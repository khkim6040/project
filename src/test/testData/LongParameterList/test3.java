/**
 * An example method with 3 long parameter lists, demonstrating the code smell.
 * First method has 7 parameters.
 * Second method has 8 parameters.
 * Third method has 9 parameters.
 * Fourth method has 4 parameters. (not long parameter list code smell)
 *
 * @author Jinyoung Kim
 */
public class TestLongParameterList {

    public static void main(String[] args) {
        TestLongParameterList example = new TestLongParameterList();

        // Call methods with long parameter lists, over 5 parameters
        example.methodWithLongParameters1("Chanho", 24, "postech", "1234", "111@example.com", "Engineer",
            "Hiking");
        example.methodWithLongParameters2("Gwhanho", 25, "pohang", "5678", "222@example.com", "Engineer", "Cooking",
            "Soccer");
        example.methodWithLongParameters3("Jinmin", 26, "postech", "9012", "333@example.com", "Engineer",
            "Reading", "Swimming", "Cycling");

        // Call method with shorter parameter list, not over 5 parameters
        example.methodWithShorterParameters("Jinyoung", 27, "postech", "7890");
    }

    public void methodWithLongParameters1(String name, int age, String address, String phoneNumber, String email,
        String occupation, String hobby) {
        // Method implementation
    }

    public void methodWithLongParameters2(String name, int age, String address, String phoneNumber, String email,
        String occupation, String hobby1, String hobby2) {
        // Method implementation
    }

    public void methodWithLongParameters3(String name, int age, String address, String phoneNumber, String email,
        String occupation, String hobby1, String hobby2, String hobby3) {
        // Method implementation
    }

    public void methodWithShorterParameters(String name, int age, String address, String phoneNumber) {
        // Method implementation
    }
}

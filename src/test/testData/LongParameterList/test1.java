public class Main {

    /**
     * An example method with a long parameter list, demonstrating the code smell.
     *
     * @param name Name of the person.
     * @param age Age of the person.
     * @param address Address of the person.
     * @param phoneNumber Phone number of the person.
     * @param email Email address of the person.
     * @param occupation Occupation of the person.
     * @param nationality Nationality of the person.
     * @param height Height of the person.
     * @param weight Weight of the person.
     * @param gender Gender of the person.
     * @param maritalStatus Marital status of the person.
     * @param hobby Hobby of the person.
     */
    public void exampleMethodWithLongParameterList(String name, int age, String address, String phoneNumber, String email,
                                                   String occupation, String nationality, double height, double weight,
                                                   String gender, String maritalStatus, String hobby) {
        // Just printing out the parameters for demonstration
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Occupation: " + occupation);
        System.out.println("Nationality: " + nationality);
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
        System.out.println("Gender: " + gender);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Hobby: " + hobby);
    }

    public static void main(String[] args) {
        Main example = new Main();

        // Example usage of the method with long parameter list
        example.exampleMethodWithLongParameterList(
                "John Doe", 30, "123 Main Street", "555-1234", "john@example.com",
                "Software Engineer", "American", 5.9, 175.0, "Male", "Married", "Photography"
        );
    }
}
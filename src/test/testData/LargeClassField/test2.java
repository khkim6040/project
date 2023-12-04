import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example class with no large class code smell due to many fields.
 * This class has 3 fields and 3 methods. ( Less than 6 fields, default value)
 *
 * @author Jinyoung Kim
 */
public class TestLargeClassField {

    // Fields related to students
    private final Map<Integer, String> studentNames;
    private final Map<Integer, Double> studentGrades;
    private final Map<Integer, List<Integer>> studentCourses;


    // Constructor
    public TestLargeClassField() {
        studentNames = new HashMap<>();
        studentGrades = new HashMap<>();
        studentCourses = new HashMap<>();
    }

    // Methods for managing students
    public void addStudent(int id, String name) {
        studentNames.put(id, name);
    }

    public void recordStudentGrade(int studentId, double grade) {
        studentGrades.put(studentId, grade);
    }

    public void enrollStudentInCourse(int studentId, int courseId) {
        studentCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(courseId);
    }

}

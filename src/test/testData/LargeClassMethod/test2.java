import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example class with no large class code smell due to many methods.
 * This class has 3 methods, which is less than 10 methods (default number)
 *
 * @author Jinyoung Kim
 */
public class TestLargeClassMethod {

    // Fields related to students
    private final Map<Integer, String> studentNames;
    // Fields related to teachers
    private final List<String> teacherNames;
    // Fields related to courses
    private final Map<Integer, String> courseNames;


    // Constructor
    public TestLargeClassMethod() {
        studentNames = new HashMap<>();
        teacherNames = new ArrayList<>();
        courseNames = new HashMap<>();
    }

    // Methods for managing students
    public void addStudent(int id, String name) {
        studentNames.put(id, name);
    }

    public void assignGrade(int studentId, double grade) {
        studentGrades.put(studentId, grade);
    }

    // Methods for managing teachers
    public void addTeacher(String name) {
        teacherNames.add(name);
    }

    // Methods for managing courses
    public void addCourse(int courseId, String courseName, int credits) {
        courseNames.put(courseId, courseName);
        courseCredits.put(courseId, credits);
    }

}

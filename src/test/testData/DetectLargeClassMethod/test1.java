import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example class with a large class code smell due to many methods.
 * This class has 3 fields and 7 methods.
 *
 * @author Jinyoung Kim
 */
public class TestDetectLargeClassMethod {

    // Fields related to students
    private final Map<Integer, String> studentNames;

    // Fields related to teachers
    private final List<String> teacherNames;


    // Fields related to courses
    private final Map<Integer, String> courseNames;


    // Constructor
    public TestDetectLargeClassMethod() {
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

    public void enrollStudentInCourse(int studentId, int courseId) {
        studentCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(courseId);
    }

    // Methods for managing teachers
    public void addTeacher(String name) {
        teacherNames.add(name);
    }

    public void assignSubjectToTeacher(String teacherName, String subject) {
        teacherSubjects.put(teacherName, subject);
    }

    // Methods for managing courses
    public void addCourse(int courseId, String courseName, int credits) {
        courseNames.put(courseId, courseName);
        courseCredits.put(courseId, credits);
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example class with a large class code smell.
 * This class has 7 fields and 7 methods.
 *
 * @author Jinyoung Kim
 */
public class TestDetectLargeClass {
    // Fields related to students
    private final Map<Integer, String> studentNames;
    private final Map<Integer, Double> studentGrades;
    private final Map<Integer, List<Integer>> studentCourses;

    // Fields related to teachers
    private final List<String> teacherNames;
    private final Map<String, String> teacherSubjects;

    // Fields related to courses
    private final Map<Integer, String> courseNames;
    private final Map<Integer, Integer> courseCredits;

    // Constructor
    public TestDetectLargeClass() {
        studentNames = new HashMap<>();
        studentGrades = new HashMap<>();
        studentCourses = new HashMap<>();
        teacherNames = new ArrayList<>();
        teacherSubjects = new HashMap<>();
        courseNames = new HashMap<>();
        courseCredits = new HashMap<>();
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

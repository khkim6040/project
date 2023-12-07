/**
 * Test PsiDeclarationStatement, int a = b.method1().method2()
 * An example code with 3 instances of message chain code smells
 * (with chain lengths of 7, 8, and 9)
 * and one example of a message chain that is not a code smell (with
 * a length of 3, which is not over length 5)
 * Default condition for message chain code smell is 5
 *
 * @author Jinyoung Kim
 */
public class MessageChainExample {


    public static void main(String[] args) {
        // Message chain with length 7 (code smell)
        String result1 = getSchool().getDepartment().getFaculty().getProfessor().getCourse().getModule().getContent();

        // Message chain with length 8 (code smell)
        String result2 = getSchool().getDepartment().getFaculty().getProfessor().getCourse().getModule().getTopic()
            .getDetail();

        // Message chain with length 9 (code smell)
        String result3 = getSchool().getDepartment().getFaculty().getProfessor().getCourse().getModule().getTopic()
            .getSubTopic().getInformation();

        // Message chain with length 3 (not a code smell)
        String result4 = getSchool().getDepartment().getName();
    }

    private static School getSchool() {
        return new School();
    }

    // Hypothetical class definitions
    static class School {

        Department getDepartment() {
            return new Department();
        }
    }

    static class Department {

        Faculty getFaculty() {
            return new Faculty();
        }

        String getName() {
            return "Department Name";
        }
    }

    static class Faculty {

        Professor getProfessor() {
            return new Professor();
        }
    }

    static class Professor {

        Course getCourse() {
            return new Course();
        }
    }

    static class Course {

        Module getModule() {
            return new Module();
        }
    }

    static class Module {

        Topic getTopic() {
            return new Topic();
        }

        String getContent() {
            return "Module Content";
        }
    }

    static class Topic {

        SubTopic getSubTopic() {
            return new SubTopic();
        }

        String getDetail() {
            return "Topic Detail";
        }
    }

    static class SubTopic {

        String getInformation() {
            return "SubTopic Information";
        }
    }
}

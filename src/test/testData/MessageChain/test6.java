/**
 * Example code to test block statement in switch
 * An example code with 1 instances of message chain code smells
 * (with all chain lengths of 7)
 *
 * Default condition for message chain code smell is 5
 *
 * @author Jinyoung Kim
 */
public class TestClass {


    public void testMethod() {
        switch (true) {
            case STATE1:
                int a = 5;
                break;
            case STATE2: { // Case 1: PsiSwitchStatement (length = 7)
                getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().checkCondition();
            }
        }

    }

    private MyClass getObject() {
        return new MyClass();
    }

    class MyClass {

        SubClass getSubObject() {
            return new SubClass();
        }
    }

    class SubClass {

        SubClass getNext() {
            return new SubClass();
        }

        void performAction() { /* ... */ }

        boolean checkCondition() {
            return true;
        }

        boolean isTrue() {
            return true;
        }

        boolean hasNext(int i) {
            return true;
        }

        String getValue() {
            return "value";
        }

        int getState() {
            return 0;
        }
    }
}

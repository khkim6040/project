public class TestClass {

    /**
     * Example method to test other 6 types of statements
     * An example code with 9 instances of message chain code smells
     * (with all chain lengths of 7)
     *
     * Default condition for message chain code smell is 5
     *
     * @author Jinyoung Kim
     */

    public void testMethod() {
        // Case 1: PsiExpressionStatement (length = 7)
        getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().performAction();

        // Case 2: PsiReturnStatement (length = 7)
        return getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().getValue();

        // Case 3: PsiIfStatement (length = 7)
        if (getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().checkCondition()) {
            // Inside if block (length = 7)
            getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().performAction();
        }

        // Case 4: PsiWhileStatement (length = 7)
        while (getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().isTrue()) {
            // Inside while loop (length = 7)
            getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().performAction();
        }

        // Case 5: PsiForStatement (length = 7)
        for (int i = 0; getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().hasNext(i); i++) {
            // Inside for loop (length = 7)
            getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().performAction();
        }

        // Case 6: PsiSwitchStatement (length = 7)
        switch (getObject().getSubObject().getNext().getNext().getNext().getNext().getNext().getState()) {
            case STATE1:
                int a = 5;
                break;
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

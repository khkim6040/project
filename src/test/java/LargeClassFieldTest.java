import detecting.BaseDetectAction;
import detecting.LargeClassField;

/**
 * Test for detecting large class due to fields
 *
 * @author Jinyoung Kim
 */

public class LargeClassFieldTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LargeClassField";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LargeClassField();
    }

    public void testStoryName() {
        LargeClassField largeClassField = new LargeClassField();
        assertEquals("Large Class based on number of fields", largeClassField.storyName());
    }

    public void testDescription() {
        LargeClassField largeClassField = new LargeClassField();
        String expectedDescription = "<html>When there are too many fields in the class<br/>" +
            " ,detect it as code smell large class.</html>";
        assertEquals(expectedDescription, largeClassField.description());
    }

    public void testPrecondition() {
        LargeClassField largeClassField = new LargeClassField();
        String expectedPrecondition = "<html>There are more fields in the class than a set standard</html>";
        assertEquals(expectedPrecondition, largeClassField.precondition());
    }

    public void testLargeClassField1() {
        expectedLocations.add(12);
        doFindSmellTest(1, expectedLocations);

    }
//
//    public void testLargeClassField2() {
//        doDetectSmellTest(2, 0);
//    }
//
//    public void testLargeClassField3() {
//        doDetectSmellTest(3, 3);
//    }
}


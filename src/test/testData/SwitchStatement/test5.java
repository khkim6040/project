/**
 * Test case for clean switch statement. There is no code smell.
 *
 * @author Chanho Song
 */
public class Grade {

    int score = 80;

    void printGrade() {
        switch (score / 10) {
            case 9:
                printf("A");
                break;
            case 8:
                printf("B");
                break;
            case 7:
                printf("C");
                break;
            case 6:
                printf("D");
                break;
            default:
                printf("F");
        }
    }

}
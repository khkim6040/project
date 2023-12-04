// Only LargeClassOne is public

/**
 * An example of 3 large classes code smell due to many methods.
 * First class has 11 methods (large class)
 * Second class has 12 methods (large class)
 * Third class has 13 methods (large class)
 *
 * Fourth class has 4 methods, not large class
 *
 * @author Jinyoung Kim
 */
public class LargeClassOne {

    // 11 different methods performing various operations
    public void method1() { /* implementation */ }

    public void method2() { /* implementation */ }

    public void method3() { /* implementation */ }

    public void method4() { /* implementation */ }

    public void method5() { /* implementation */ }

    public void method6() { /* implementation */ }

    public void method7() { /* implementation */ }

    public void method8() { /* implementation */ }

    public void method9() { /* implementation */ }

    public void method10() { /* implementation */ }

    public void method11() { /* implementation */ }
}


class LargeClassTwo {

    // 12 methods with different functionalities
    public void action1() { /* implementation */ }

    public void action2() { /* implementation */ }

    public void action3() { /* implementation */ }

    public void action4() { /* implementation */ }

    public void action5() { /* implementation */ }

    public void action6() { /* implementation */ }

    public void action7() { /* implementation */ }

    public void action8() { /* implementation */ }

    public void action9() { /* implementation */ }

    public void action10() { /* implementation */ }

    public void action11() { /* implementation */ }

    public void action12() { /* implementation */ }
}


class LargeClassThree {

    // 13 methods, each performing a unique task
    public void process1() { /* implementation */ }

    public void process2() { /* implementation */ }

    public void process3() { /* implementation */ }

    public void process4() { /* implementation */ }

    public void process5() { /* implementation */ }

    public void process6() { /* implementation */ }

    public void process7() { /* implementation */ }

    public void process8() { /* implementation */ }

    public void process9() { /* implementation */ }

    public void process10() { /* implementation */ }

    public void process11() { /* implementation */ }

    public void process12() { /* implementation */ }

    public void process13() { /* implementation */ }
}

class NotALargeClass {

    // Only 4 methods, not considered a large class
    public void operation1() { /* implementation */ }

    public void operation2() { /* implementation */ }

    public void operation3() { /* implementation */ }

    public void operation4() { /* implementation */ }
}

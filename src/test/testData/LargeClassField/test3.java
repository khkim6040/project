/**
 * An example class with 3 large classes code smell due to many fields.
 * First class has 5 fields (Not over 6 fields, default value), not large class
 *
 * Second class has 10 fields, large class
 * Third class has 12 fields, large class
 * Fourth class has 12 fields, large class
 *
 * @author Jinyoung Kim
 */
public class ClassWithFewFields {

    private String name;
    private int id;
    private double score;
    private String address;
    private boolean isActive;

}

class LargeClassOne {

    private String field1, field2, field3, field4, field5, field6, field7;
    private int field8;
    private double field9;
    private boolean field10;

}

class LargeClassTwo {

    private int number1, number2, number3, number4, number5, number6, number7, number8;
    private String text1, text2, text3;
    private boolean flag;

}

class LargeClassThree {

    private double value1, value2, value3, value4, value5, value6, value7, value8, value9;
    private String name1, name2;
    private int count;

}

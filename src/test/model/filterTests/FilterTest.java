package model.filterTests;

public interface FilterTest {

    void setup();

    void constructorTest();

    void satisfiesTestFalse();

    void satisfiesTestTrue();

    void filterTestAllIn();

    void filterTestAllOut();

    void filterTestSomeInSomeOut();

}

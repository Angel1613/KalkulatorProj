package com.example.angel.kalkulatorx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    SUM
    @Test
    public void testCalculateSum() {
        testCase("2 + 2",4.0);
    }

    @Test
    public void testCalculateSumDec() {
        testCase("2 + 2.5", 4.5);
    }

    @Test
    public void testCalculateSumNegDec() {
        testCase("- 2.5 + 2", -0.5);
    }

//    SUB

    @Test
    public void testCalculateSub() {
        testCase("2 - 2", 0.0);
    }

    @Test
    public void testCalculateSubNegative() {
        testCase("2 - 3", -1.0);
    }

    @Test
    public void testCalculateSubNegativeDec() {
        testCase("2 - 3.5", -1.5);
    }

//    MUL

    @Test
    public void testCalculateMul() {
        testCase("2 * 2",4.0);
    }

    @Test
    public void testCalculateMulDec() {
        testCase("2 * 2.2", 4.4);
    }

//    DIV

    @Test
    public void testCalculateDiv() {
        testCase("2 / 2", 1.0);
    }

    @Test
    public void testCalculateDivDec() {
        testCase("1.5 / 0.5", 3.0);
    }

    @Test
    public void testCalculateMulDecSub() {
        testCase("1.5 * -0.5", -0.75);
    }

    @Test
    public void testCalculateMulZero() {
        testCase("1 / 0", 0);
    }

    private void testCase(String s, double i) {
        String equation = s;
        Number number = i;

        Calculator calc = new Calculator();
        Number result = calc.calculate(equation);


        assertEquals(number, result);
    }

}
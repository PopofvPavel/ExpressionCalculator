package com.example;


import org.junit.Test;

import static org.testng.AssertJUnit.assertEquals;

public class CalculatorTest {

    @Test
    public void testCalculate(){
        Calculator calculator = new Calculator();

        double result = calculator.calculate("2 + 3");
        double result2 = calculator.calculate("( 2 + 3 ) * ( 4 - 5 )");
        double result3 = calculator.calculate("2 * ( 3 + 4 )");
        double result4 = calculator.calculate("(-5 + 8) / 4 * ( 2 + ( 3 * 5 ) ) " );
        double result5 = calculator.calculate("7 - 2 ");

        assertEquals((double) 2 + 3, result);
        assertEquals((double) ((2 + 3) * (4 - 5)), result2);
        assertEquals((double) (2 * (3 + 4)), result3);
        assertEquals( (double) (-5 +  8) / 4  * ( 2 + ( 3 * 5)), result4);
        assertEquals((double) 7 - 2, result5);
    }


}
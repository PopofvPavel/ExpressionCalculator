package com.example;


import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.BinaryOperator;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
    @Mock
    private BinaryOperator<Double> plus = mock(BinaryOperator.class);

    @Mock
    private BinaryOperator<Double> minus = mock(BinaryOperator.class);

    @Mock
    private BinaryOperator<Double> multiply = mock(BinaryOperator.class);

    @Mock
    private BinaryOperator<Double> divide = mock(BinaryOperator.class);

    @InjectMocks
    private  Calculator calculator = new Calculator();

    @Test
    public void testDivideByZero() {
        String expression = "2 / 0";
        Assertions.assertThrows(ArithmeticException.class,() -> calculator.calculate(expression));

    }

    @Test
    public void testMockedPlusOperation() {

        when(plus.apply(anyDouble(), anyDouble())).thenReturn(5.0);
        double result = calculator.calculate("2 + 3");
        assertEquals(5.0, result);
        verify(plus).apply(anyDouble(), anyDouble());
    }
    @Test
    public void testMockedMinusOperation() {
        when(minus.apply(anyDouble(), anyDouble())).thenReturn(5.0);
        double result = calculator.calculate("7 - 2");
        assertEquals(5.0, result);
        verify(minus).apply(anyDouble(), anyDouble());
    }

    @Test
    public void testMockedMultiplyOperation() {
        when(multiply.apply(anyDouble(), anyDouble())).thenReturn(10.0);
        double result = calculator.calculate("2 * 5 * 1");
        assertEquals(10.0, result);
        verify(multiply, times(2)).apply(anyDouble(), anyDouble());
    }

    @Test
    public void testMockedDivideOperation() {
        when(divide.apply(anyDouble(), anyDouble())).thenReturn(2.0);
        double result = calculator.calculate("4 / ( 4 / ( 8 / 4 ) )");
        assertEquals(2.0, result);
        verify(divide, times(3)).apply(anyDouble(), anyDouble());
    }



    @Test
    public void testCalculate(){
        //Старый тест без моков
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
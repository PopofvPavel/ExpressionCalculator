import org.w3c.dom.ls.LSOutput;

public class Main {


    public static void main(String[] args) {
        Calculator calculator = Calculator.getCalculator.get();
        String expression = "-8 * (10 + ( 6 + 4 / 2 * 6 )) - 5";
        double r = -8 * 28.0 - 5;
        System.out.println("ACTUAL RESULT = " + r );
        System.out.println(calculator.calculate(expression));

    }
}

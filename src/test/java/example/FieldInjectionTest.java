package example;

import DIContainer.Annotation.DInjection;
import example.service.CalculatorService;
import example.service.TextFormatterService;

public class FieldInjectionTest {

    @DInjection
    private CalculatorService calculatorService;

    @DInjection
    private TextFormatterService textFormatterService;

    public String processNumbers(int firstNumber, int secondNumber) {
        int number = calculatorService.calculate(firstNumber, secondNumber);
        return textFormatterService.format(String.valueOf(number));
    }
}

package example;

import DIContainer.Annotation.DInjection;
import example.service.CalculatorService;
import example.service.TextFormatterService;

public class ConstructorInjectionTest {

    private final CalculatorService calculatorService;
    private final TextFormatterService textFormatterService;

    @DInjection
    public ConstructorInjectionTest(CalculatorService calculatorService, TextFormatterService textFormatterService) {
        this.calculatorService = calculatorService;
        this.textFormatterService = textFormatterService;
    }

    public String processNumbers(int firstNumber, int secondNumber) {
        int number = calculatorService.calculate(firstNumber, secondNumber);
        return textFormatterService.format(String.valueOf(number));
    }
}
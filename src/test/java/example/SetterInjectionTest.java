package example;

import DIContainer.Annotation.DInjection;
import example.service.CalculatorService;
import example.service.TextFormatterService;

public class SetterInjectionTest {

    private CalculatorService calculatorService;

    private TextFormatterService textFormatterService;

    @DInjection
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @DInjection
    public void setTextFormatterService(TextFormatterService textFormatterService) {
        this.textFormatterService = textFormatterService;
    }

    public String processNumbers(int firstNumber, int secondNumber) {
        int number = calculatorService.calculate(firstNumber, secondNumber);
        return textFormatterService.format(String.valueOf(number));
    }



}

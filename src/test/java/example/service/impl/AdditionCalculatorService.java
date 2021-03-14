package example.service.impl;

import example.service.CalculatorService;

public class AdditionCalculatorService implements CalculatorService {

    @Override
    public int calculate(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

}

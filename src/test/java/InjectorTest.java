import DIContainer.Binding.BindingException;
import DIContainer.Container.Container;
import DIContainer.Container.IContainer;
import DIContainer.Injector.DInjector;
import DIContainer.Injector.Injector;
import example.ConstructorInjectionTest;
import example.FieldInjectionTest;
import example.SetterInjectionTest;
import example.service.CalculatorService;
import example.service.TextFormatterService;
import example.service.impl.AdditionCalculatorService;
import example.service.impl.PrettyTextFormatterService;
import example.service.impl.SimpleTextFormatterService;
import example.service.impl.SubtractionCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    DInjector injector;
    IContainer container;

    @BeforeEach
    void createInjector() {
        injector = Injector.getInstance();
        container = Container.getInstance();
    }


    @DisplayName("Field Injection 1")
    @Test
    public void fieldInjection1() throws BindingException {

        container.register(CalculatorService.class, AdditionCalculatorService.class);
        container.register(TextFormatterService.class, PrettyTextFormatterService.class);

        FieldInjectionTest example = injector.bind(FieldInjectionTest.class);
        String processNumbers = example.processNumbers(3, 2);

        assertEquals("Pretty text: <5>", processNumbers);
    }


    @DisplayName("Field Injection 2")
    @Test
    public void fieldInjection2() throws BindingException {

        container.register(CalculatorService.class, AdditionCalculatorService.class);
        container.register(TextFormatterService.class, SimpleTextFormatterService.class);

        FieldInjectionTest example = injector.bind(FieldInjectionTest.class);
        String processNumbers = example.processNumbers(3, 2);

        assertEquals("Simple text: 5", processNumbers);
    }


    @DisplayName("Constructor Injection")
    @Test
    public void constructorInjection() throws BindingException {

        container.register(CalculatorService.class, SubtractionCalculatorService.class);
        container.register(TextFormatterService.class, SimpleTextFormatterService.class);

        ConstructorInjectionTest example = injector.bind(ConstructorInjectionTest.class);
        String processNumbers = example.processNumbers(3, 2);

        assertEquals("Simple text: 1", processNumbers);
    }

    @Test
    @DisplayName("Setter Injection")
    public void SetterInjection() throws BindingException {

        container.register(CalculatorService.class, SubtractionCalculatorService.class);
        container.register(TextFormatterService.class, SimpleTextFormatterService.class);

        SetterInjectionTest example = injector.bind(SetterInjectionTest.class);
        String processNumbers = example.processNumbers(3, 2);

        assertEquals("Simple text: 1", processNumbers);
    }
}
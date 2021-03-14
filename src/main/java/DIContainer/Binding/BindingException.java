package DIContainer.Binding;

/**
 * Own Binding Exception
 */
public class BindingException extends Exception {
    public BindingException(String errorMessage) {
        super("BINDING EXCEPTION : " + errorMessage);
    }
}

package DIContainer.Injector;

import DIContainer.Binding.BindingException;

/**
 * Framework abstraction class
 *      Used to manipulate the DI
 */
public interface DInjector {

    /**
     * Bind a class with Constructor/Fields/Setter
     * @param classToInject class to bind
     * @return the injected object
     * @throws BindingException if any error occurs
     */
    <T> T bind(final Class<T> classToInject) throws BindingException;

}

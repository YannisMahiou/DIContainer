package DIContainer.Container;

import DIContainer.Binding.BindingException;

/**
 * Abstraction interface used to manipulate the Dependency injections
 */
public interface IContainer {

    /**
     * Returns new Instance of dependency
     *
     * @param dependency the dependency
     * @param <T>
     * @return
     * @throws BindingException caused by the binding
     */
    <T> Class<? extends T> newInstance(final Class<T> dependency) throws BindingException;

    /**
     * Register a new dependency
     *
     * @param baseClass from
     * @param subClass to
     * @param <T> type
     */
    <T> void register(final Class<T> baseClass, final Class<? extends T> subClass);

}


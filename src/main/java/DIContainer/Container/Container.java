package DIContainer.Container;

import DIContainer.Binding.BindingException;

import java.util.Hashtable;

/**
 * DI Container class : central point for DI
 *      Manipulates LifeCycle : Creation/Persistence/Destruction
 *      Gives dependencies
 */
public class Container implements IContainer{

    private static Container m_instance;

    protected Hashtable<Class<?>, Class<?>> dependencies;

    /**
     * BindingObject creator
     */
    public Container() {
        this.dependencies = new Hashtable<Class<?>, Class<?>>();
    }

    /**
     * Returns the singleton instance of the DInjector
     *
     * @return the DI Injector instance
     */
    public static IContainer getInstance() {
        if (Container.m_instance == null)
            Container.m_instance = new Container();

        return Container.m_instance;
    }

    /**
     * Register a dependency
     *
     * @param baseClass class/interface
     * @param subClass class
     */
    @Override
    public <T> void register(final Class<T> baseClass, final Class<? extends T> subClass) {
        dependencies.put(baseClass, subClass.asSubclass(baseClass));
    }

    /**
     * Get the dependency for a given type
     *
     * @param dependency given
     * @param <T> dependency
     * @return the found dependency or throw BindingException
     */
    public <T> Class<? extends T> newInstance(final Class<T> dependency) throws BindingException{
        Class<?> implementation = dependencies.get(dependency);

        if (implementation == null) {
            throw new BindingException("DI Container does not have a class registered for use with " + dependency.getName());
        }
        return implementation.asSubclass(dependency);
    }
}
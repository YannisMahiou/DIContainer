package DIContainer.Injector;

import DIContainer.Annotation.Named;
import DIContainer.Binding.BindingException;
import DIContainer.Container.Container;
import DIContainer.Container.IContainer;
import DIContainer.Annotation.DInjection;

import java.lang.reflect.*;

/**
 * Dependency Injection framework, it uses reflection (Constructor, Field) to find the dependency and inject it.
 */
public class Injector implements DInjector {

    private static Injector m_instance;

    private final IContainer container;

    /**
     * DIFramework constructor
     */
    public Injector() {
        this.container = Container.getInstance();
    }
 
    /**
     * Returns the singleton instance of the DInjector
     *
     * @return the DI Injector instance
     */
    public static DInjector getInstance() {
        if (Injector.m_instance == null)
            Injector.m_instance = new Injector();
        return Injector.m_instance;
    }

    /**
     * Main injection access point of the framework
     *
     * @param classToInject class to inject
     * @return injected object
     */
    public <T> T bind(final Class<T> classToInject) throws BindingException{
        try {
            if (classToInject == null) {
                return null;
            }
            return injectFieldsIntoClass(classToInject);
        }catch(Exception e){
            throw new BindingException(e.getMessage());
        }
    }

    /**
     * Injects all fields into a class
     *
     * @param classToInject class to inject
     * @return the object with the field injected
     */
    private <T> T injectFieldsIntoClass(final Class<T> classToInject) throws BindingException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        // For all the constructors needed in the class to inject
        for (final Constructor<?> constructor : classToInject.getConstructors()) {

            // Inject via Constructor
            if (constructor.isAnnotationPresent(DInjection.class)) {
               return injectFieldsViaConstructor(classToInject, constructor);
            }

            // Inject Fields
            if (hasFieldWithAnnotation(classToInject)) {
                return injectFields(classToInject);
            }

            // Inject via Setter
            return injectFieldsViaSetter(classToInject);
        }
        return null;
    }

    /**
     * Inject a class by setter
     *
     * @param classToInject class to inject
     * @return the injected object
     */
    private <T> T injectFieldsViaSetter(Class<T> classToInject) throws BindingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        // Initialization
        T o = classToInject.newInstance();

        // For all the methods in the class to inject
        for(final Method method : classToInject.getDeclaredMethods()){

            // If the method is annotated
            if(method.isAnnotationPresent(DInjection.class)){

                // If the method is a setter
                if(isSetter(method)){

                    // Get the dependency
                    final Class<?> dependency = container.newInstance(method.getParameterTypes()[0]);
                    method.setAccessible(true);
                    method.invoke(classToInject, dependency);
                }
            }
        }

        // Returns the injected object
        return o;
    }

    /**
     * Inject declared/annotated fields of a class
     *
     * @param classToInject class to inject
     * @return the injected object
     */
    private <T> T injectFields(Class<T> classToInject) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BindingException {

        // Initialization
        T o = classToInject.newInstance();

        // For all the declared fields of the class to inject
        for (Field field : classToInject.getDeclaredFields()) {

            // If field is annotated with @DInjection
            if (field.isAnnotationPresent(DInjection.class)) {

                // Get dependency from the DIContainer
                final Class<?> dependency = container.newInstance(field.getType());
                field.setAccessible(true);
                field.set(o, dependency.getConstructor().newInstance());
            }

            // If the field is annotated with @Named
            if (field.isAnnotationPresent(Named.class)) {

                // Register the value
                field.setAccessible(true);
                final Class<?> dependency = container.newInstance(field.getType());
                field.set(classToInject, dependency);
            }
        }

        // Return the injected object
        return o;
    }

    /**
     * Constructor field injector
     *
     * @param classToInject class to inject
     * @param constructor   the constructor function
     * @return object with DI
     */
    private <T> T injectFieldsViaConstructor(Class<T> classToInject, Constructor<?> constructor) throws BindingException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        // Initialisation
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] objArr = new Object[parameterTypes.length];
        int i = 0;

        // For all the parameters in the constructor
        for (final Class<?> param : parameterTypes) {

            // Get the dependency
            final Class<?> dependency = container.newInstance(param);

            // If the type is assignable
            if (param.isAssignableFrom(dependency)) {

                // Assign it
                objArr[i++] = dependency.getConstructor().newInstance();
            }
        }

        // Returns the injected object
        return classToInject.getConstructor(parameterTypes).newInstance(objArr);
    }

    /**
     * Checks if there is an annotation on fields
     *
     * @param classToCheck class to check
     * @return true if annotated with @DInject, false else
     */
    private <T> boolean hasFieldWithAnnotation(Class<T> classToCheck) {

        // For all the declared fields of the class to inject
        for (Field field : classToCheck.getDeclaredFields()) {

            // If field is annotated with @DInjection
            if (field.isAnnotationPresent(DInjection.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a method is a setter
     *
     * @param method to check
     * @return true if it's a setter, false else
     */
    private static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getReturnType().equals(void.class) &&
                method.getParameterTypes().length == 1 &&
                method.getName().matches("^set[A-Z].*");
    }
}

package DIContainer;

import DIContainer.Injector.DInjector;
import DIContainer.Injector.Injector;

public class ContainerEntry implements IContainerEntry{

    private DInjector injector;



    @Override
    public <T> void register(Class<T> dependency) {

    }

    @Override
    public <T> T newInstance(Class<T> dependency) {
        return null;
    }
}

package DIContainer;

public interface IContainerEntry {

    <T> void register(Class<T> dependency);

    <T> T newInstance(Class<T> dependency);
}

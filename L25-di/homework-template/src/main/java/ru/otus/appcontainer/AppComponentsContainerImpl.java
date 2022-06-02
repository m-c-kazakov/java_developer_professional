package ru.otus.appcontainer;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

import static java.util.Optional.ofNullable;

@Getter
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    String annotationParamName = method.getAnnotation(AppComponent.class).name();
                    Object component = createComponent(configClass, method);
                    addComponentInContainers(annotationParamName, component);

                });
    }

    private void addComponentInContainers(String annotationParamName, Object component) {
        Object duplicateComponent = appComponentsByName.putIfAbsent(annotationParamName, component);
        if (Objects.nonNull(duplicateComponent)) {
            throw new RuntimeException(
                    "Found duplicate component with name=" + annotationParamName + "!");
        }
        appComponents.add(component);
    }

    @SneakyThrows
    private Object createComponent(Class<?> configClass, Method method) {
        Object configObject = getConfigObject(configClass);
        Object[] methodArgs = Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toArray();
        return method.invoke(configObject, methodArgs);
    }

    private Object getConfigObject(Class<?> configClass) {
        return ofNullable(appComponentsByName.get(configClass.getName()))
                .orElseGet(() -> createAndPutConfigObject(configClass));
    }

    @SneakyThrows
    private Object createAndPutConfigObject(Class<?> configClass) {
        Object configObject = configClass.getDeclaredConstructor().newInstance();
        appComponentsByName.put(configClass.getName(), configObject);
        return configObject;
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(cmp -> componentClass.isAssignableFrom(cmp.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "The component not found by componentClassName=" + componentClass.getName() + "!"));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) ofNullable(appComponentsByName.get(componentName)).orElseThrow(
                () -> new RuntimeException("The component not found by componentName=" + componentName + "!"));

    }
}

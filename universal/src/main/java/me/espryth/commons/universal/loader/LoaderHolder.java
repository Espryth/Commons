package me.espryth.commons.universal.loader;

import me.espryth.commons.universal.Logger;
import me.espryth.commons.universal.module.container.Container;
import team.unnamed.reflect.identity.TypeReference;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LoaderHolder {

    private final String prefix;

    private final Container container;

    private final Map<Loader, List<Method>> loadMethods;
    private final Map<Loader, List<Method>> enableMethods;
    private final Map<Loader, List<Method>> disableMethods;

    public LoaderHolder(String prefix,
                        Container container) {
        this.prefix = prefix;
        this.container = container;
        this.loadMethods = new HashMap<>();
        this.enableMethods = new HashMap<>();
        this.disableMethods = new HashMap<>();
    }

    public LoaderHolder(Container container) {
        this("", container);
    }

    public void register(Loader loader) {

        Class<?> clazz = loader.getClass();

        for(Method method : clazz.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Register.class)) {

                Register register = method.getAnnotation(Register.class);

                switch (register.type()) {
                    case LOAD:
                        loadMethods.computeIfAbsent(loader, k -> new ArrayList<>());
                        loadMethods.get(loader).add(method);
                        break;
                    case ENABLE:
                        enableMethods.computeIfAbsent(loader, k -> new ArrayList<>());
                        enableMethods.get(loader).add(method);
                        break;
                    case DISABLE:
                        disableMethods.computeIfAbsent(loader, k -> new ArrayList<>());
                        disableMethods.get(loader).add(method);
                        break;
                }
            }
        }
    }

    public void load() {
        initMethods(loadMethods);
    }

    public void enable() {
        initMethods(enableMethods);
    }

    public void disable() {
        initMethods(disableMethods);
    }

    private void initMethods(Map<Loader, List<Method>> map) {

        for(Loader loader : map.keySet()) {

            List<Method> methods = map.get(loader);

            methods.sort((method1, method2) -> {
                Register register1 = method1.getAnnotation(Register.class);
                Register register2 = method2.getAnnotation(Register.class);
                return Integer.compare(register1.priority(), register2.priority());
            });

            for(Method method : methods) {

                Register register = method.getAnnotation(Register.class);

                if(!register.message().isEmpty()) {
                    Logger.log(Logger.Level.INFO, prefix, register.message());
                }

                Object[] args = new Object[method.getParameters().length];

                for(int i = 0; i < method.getParameters().length; i++) {

                    Parameter parameter = method.getParameters()[i];
                    Type type = parameter.getParameterizedType();
                    TypeReference<?> typeReference;

                    if(type instanceof ParameterizedType) {
                        typeReference = TypeReference.of(parameter.getType(),
                                ((ParameterizedType) type).getActualTypeArguments());
                    } else {
                        typeReference = TypeReference.of(parameter.getType());
                    }

                    Object object;

                    if(parameter.isAnnotationPresent(Named.class)) {
                        Named named = parameter.getAnnotation(Named.class);
                        object = container.get(typeReference, named.value());
                    } else {
                        object = container.get(typeReference);
                    }

                    args[i] = object;

                }

                try {
                    method.invoke(loader, args);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }

            }

        }
    }

}

package com.backend.custom_spring_dependency_inj;

import com.backend.custom_spring_dependency_inj.annotations.ComponentScan;
import com.backend.custom_spring_dependency_inj.annotations.Configuration;
import com.backend.custom_spring_dependency_inj.annotations.SimpleComponent;
import com.backend.custom_spring_dependency_inj.annotations.SimplyAutoWired;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContext {
    Map<Class<?>, Object> map = new HashMap<>();

    ApplicationContext(Class<?> c) {
        initializeContext(c);
    }

    private void initializeContext(Class<?> c) {
        if (!c.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException("Please provide a valid configuration file!!");
        } else {

            ComponentScan componentScan = c.getAnnotation(ComponentScan.class);
            String packageValue = componentScan.value();
            Set<Class<?>> classes = findClasses(packageValue);

            for (Class<?> loadingClass : classes) {
                try {
                    if (loadingClass.isAnnotationPresent(SimpleComponent.class)) {
                        Constructor<?> constructor = loadingClass.getDeclaredConstructor();
                        Object newInstance = constructor.newInstance();
                        map.put(loadingClass, newInstance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public <T> T getInstance(Class<T> c) throws Exception {
        T obj = (T) map.get(c);
        Field[] declaredFields = c.getDeclaredFields();
        injectAnnotatedFields(obj, declaredFields);

        return obj;
    }

    private Set<Class<?>> findClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private <T> void injectAnnotatedFields(T object, Field[] declaredFields) throws Exception {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(SimplyAutoWired.class)) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object innerObject = map.get(type);

                field.set(object, innerObject);
                injectAnnotatedFields(innerObject, type.getDeclaredFields());
            }
        }
    }


}

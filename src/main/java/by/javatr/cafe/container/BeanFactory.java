package by.javatr.cafe.container;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.exception.DIException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
/**
 * BeanFactory is a dependency container that provides access to classes
 * that are annotated with Component
 */
public class BeanFactory {

    private static BeanFactory INSTANCE;

    public  static BeanFactory getInstance(){
        if (INSTANCE == null){
            INSTANCE = new BeanFactory();
        }
        return INSTANCE;
    }

    private BeanFactory() {}

    private final Map<String, Object> container = new HashMap<>();

    /**
     * Returns bean by name
     * @param beanName classname with first latter in lower case
     * @return bean
     */
    public Object getBean(String beanName){
        return container.get(beanName);
    }


    /**
     * walks through directories and looks for compiled classes
     * @param basePackage package in which to search classes
     * @throws DIException if something went wrong
     */
    public void instantiate(String basePackage) throws DIException {

        String fileName = null;

        String[] split1 = basePackage.split("/");

        String pack = "";

        int count = 0;
        for (String s : split1) {
            if (count == 1) {
                pack = pack + s + ".";
            }
            if (s.equals("classes")) {
                count++;
            }
        }

        String pat = null;
        if(basePackage.charAt(0) == '/'){
            pat = basePackage.substring(1);
        }else{
            pat=basePackage;
        }

        List<File> collect = null;
        try {
            collect = Files.walk(Paths.get(pat))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DIException("Path not found" + e);
        }


        for (File file:collect) {
            fileName = file.getName();

            String absolutePath = file.getAbsolutePath().replaceAll("\\\\", "/");

            String tempPackage = absolutePath.substring(pat.length());

            String[] split = tempPackage.split("/");

            String filePackage = "";

            for (int i = 0; i < split.length-1; i++) {
                filePackage += split[i] + ".";
            }

            if(filePackage.length()==0)
            {
                filePackage = pack;
            }else {
                filePackage = pack + filePackage.substring(0, filePackage.length() - 1);
            }
            String className = null;
            if(fileName.endsWith(".class")){
                className = fileName.substring(0, fileName.lastIndexOf("."));
            }

            String newClass = filePackage + "." + className;
            newClass = newClass.replaceAll("\\.\\.", ".");

            Class<?> classObject = null;
            try {
                classObject = Class.forName(newClass);
            } catch (ClassNotFoundException e) {
                throw new DIException(e);
            }

            if(classObject.isAnnotationPresent(Component.class)){
                try {
                Constructor<?> declaredConstructor = classObject.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                Object instance = declaredConstructor.newInstance();
                declaredConstructor.setAccessible(false);
                String beanName = className.substring(0,1).toLowerCase() + className.substring(1);
                container.put(beanName, instance);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new DIException(e);
                }
            }

        }

    }

    /**
     * injects dependency to beans
     * @throws DIException something went wrong
     */
    public void populateProperties() throws DIException {

        for (Object object : container.values()) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    for (Object dependency : container.values()) {
                        int counter = 0;
                        Class<?> aClass = dependency.getClass();


                        if(!aClass.isAnnotationPresent(Component.class)){
                            break;
                        }
                        while (!aClass.getTypeName().equals(Object.class.getTypeName())) {
                            if (aClass.equals(field.getType())) {
                                field.setAccessible(true);
                                try {
                                    field.set(object, dependency);
                                } catch (IllegalAccessException e) {
                                    throw new DIException(e);
                                }
                                field.setAccessible(false);
                                counter++;
                            }
                            aClass = aClass.getSuperclass();

                        }

                        if(counter == 0){
                            Class<?> type = field.getType();
                            Class<?>[] interfaces1 = dependency.getClass().getInterfaces();
                            for (Class<?> var:interfaces1) {
                                if(var.getTypeName().equals(Object.class.getTypeName())){
                                    break;
                                }
                                if (var.equals(type)) {
                                    field.setAccessible(true);
                                    try {
                                        field.set(object, dependency);
                                    } catch (IllegalAccessException e) {
                                        throw new DIException(e);
                                    }
                                    field.setAccessible(false);
                                    counter++;
                                }
                            }
                        }

                    }
                }
            }
        }

    }


    /**
     * init container
     * @param packageName base package
     * @throws DIException something went wrong
     */
    public void run(String packageName) throws DIException {
        instantiate(packageName);
        populateProperties();
    }

    public Map<String, Object> getContainer() {
        return container;
    }
}

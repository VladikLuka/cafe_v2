package by.javatr.cafe.container;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private static BeanFactory INSTANCE;

    public  static BeanFactory getInstance(){
        if (INSTANCE == null){
            INSTANCE = new BeanFactory();
        }
        return INSTANCE;
    }

    private BeanFactory() {}

    private final Map<String, Object> singletons = new HashMap();

    public Object getBean(String beanName){
        return singletons.get(beanName);
    }

    public void instantiate(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        String fileName = null;

        String[] split1 = basePackage.split("/");

        String pack = "";

        int count = 0;
        for (int i = 0; i < split1.length; i++) {
            if(count == 1){
                pack = pack + split1[i]+".";
            }
               if(split1[i].equals("classes")){
                count++;
            }
        }


        String pat = basePackage.substring(1);

        List<File> collect = Files.walk(Paths.get(pat))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());


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

            Class<?> classObject = Class.forName(newClass);

            if(classObject.isAnnotationPresent(Component.class)){
                Constructor<?> declaredConstructor = classObject.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                Object instance = declaredConstructor.newInstance();
                declaredConstructor.setAccessible(false);
                String beanName = className.substring(0,1).toLowerCase() + className.substring(1);
                singletons.put(beanName, instance);
            }

        }

    }

    public void populateProperties() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        final Set<String> strings = singletons.keySet();

        for (String str:strings) {
            System.out.println(singletons.get(str));
        }

        for (Object object : singletons.values()) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    for (Object dependency : singletons.values()) {
                        int counter = 0;
                        Class<?> aClass = dependency.getClass();


                        if(!aClass.isAnnotationPresent(Component.class)){
                            break;
                        }
                        while (!aClass.getTypeName().equals(Object.class.getTypeName())) {
                            if (aClass.equals(field.getType())) {
                                field.setAccessible(true);
                                field.set(object, dependency);
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
                                    field.set(object, dependency);
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


    public void run(String name) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, URISyntaxException, ClassNotFoundException, InstantiationException, IOException {
        instantiate(name);
        populateProperties();

    }

    public Map<String, Object> getSingletons() {
        return singletons;
    }
}

package main.java.annotations;
import java.lang.reflect.Method;

import annotations.Route; 
public class Main {
    public static void main(String[] args) {
        TestAnnotations test = new TestAnnotations();

        for (Method method : test.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Route.class)) {
                Route route = method.getAnnotation(Route.class);
                System.out.println("Found route: " + route.value() 
                                   + " -> method: " + method.getName()+"\n");
            }
        }

        System.out.println("Test finished.");
    }
}

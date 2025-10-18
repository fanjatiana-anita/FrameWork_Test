package main.java.annotations;
import annotations.Route; 

public class TestAnnotations {

    @Route(value = "/test")
    public void testMethod() { 
        System.out.println("Annotation test method executed.\n");

    }
}

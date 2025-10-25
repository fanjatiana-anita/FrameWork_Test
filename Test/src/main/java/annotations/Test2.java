package main.java.annotations;

import method_annotations.Route; 

public class Test2 {

    public void testMethod2() { 
        System.out.println("INFO :  testMethod2 executed\n");


    }
    @Route(value = "/add_Test2")
    public void getTest2() {
        System.out.println("INFO : getTest2 executed\n");


    }
}

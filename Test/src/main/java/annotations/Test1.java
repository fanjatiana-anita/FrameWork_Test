package main.java.annotations;
import class_annotations.Controller;
import method_annotations.Route; 

@Controller(value = "PersonController")
public class Test1 {

    public void testMethod1() { 
        System.out.println("INFO :  testMethod1 executed\n");


    }
    @Route(value = "/add_Test1")
    public void getTest1() {
        System.out.println("INFO : getTest1 executed\n");


    }
}

package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 
import view.ModelView;

@Controller(value = "ViewController")
public class ViewController {

    // public ViewController(){}

    @Route(value = "/view-test")
    public void testVueController () {
        System.out.println("A simple controller view controller");
    }

    @Route("/home")
    public ModelView redirectToHome() {
        ModelView view = new ModelView();
        view.setView("home.jsp");
        view.setData("test", "test");
        
        return view;
    }

}

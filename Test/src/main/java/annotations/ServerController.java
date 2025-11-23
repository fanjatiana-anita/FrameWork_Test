package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 
import view.ModelView;

@Controller
public class ServerController {

    // public ViewController(){}

    @Route(value = "/servers/{id}") 
    public String get(String id) {
        Server a = new Server(id, "127.0.0.1");
        return a.getMap();
    }

}


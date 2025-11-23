package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 
import view.ModelView;
import method_annotations.RequestParam;

@Controller
public class ServerController {

    // public ViewController(){}


    @Route(value = "/add") 
    public String addServer(String name, @RequestParam("number") double isa) {
        return "server: "+name+" successfully added whith "+ isa + "numbers";
    }

    @Route(value = "/servers/{id}") 
    public String get(String id) {
        Server a = new Server(id, "127.0.0.1");
        return a.getMap();
    }

}
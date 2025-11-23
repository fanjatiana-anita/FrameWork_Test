package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 
import view.ModelView;
import method_annotations.RequestParam;

@Controller
public class ServerController {

    // public ViewController(){}

    @Route(value = "/servers/get/{id}") 
    public String get(int id) {
        Server a = new Server(id, "127.0.0.1");
        return "ID : "+id;
    }
    @Route(value = "/servers/{ip}") 
    public String getIp(String ip) {
        Server a = new Server(1, ip);
        return "IP : "+ip;
    }

    @Route(value = "/add") 
    public String addServer(String name, @RequestParam("number") double isa) {
        return "server: "+name+" successfully added whith "+ isa + "numbers";
    }

}
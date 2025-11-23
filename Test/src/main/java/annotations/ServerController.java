package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 
import view.ModelView;
<<<<<<< HEAD
<<<<<<< HEAD
import method_annotations.RequestParam;
=======
>>>>>>> test_sprint3_bis
=======
import method_annotations.RequestParam;
>>>>>>> sprint6_bis

@Controller
public class ServerController {

    // public ViewController(){}

<<<<<<< HEAD
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
=======
    @Route(value = "/servers/{id}") 
    public String get(String id) {
        Server a = new Server(id, "127.0.0.1");
        return a.getMap();
    }

}0
>>>>>>> test_sprint3_bis

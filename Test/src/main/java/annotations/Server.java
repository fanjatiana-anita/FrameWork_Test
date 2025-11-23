package main.java.annotations;

import class_annotations.Controller;
import method_annotations.Route; 

@Controller
public class Server {

<<<<<<< HEAD
    private int server_id;
    private String server_ip;

    public Server(int id,String server_ip) {
=======
    private String server_id;
    private String server_ip;

    public Server(String id,String server_ip) {
>>>>>>> test_sprint3_bis
        this.server_id=id;
        this.server_ip=server_ip;
    }

<<<<<<< HEAD
    public int getIdMap() { return this.server_id; }
    public void setIdMap(int id) { this.server_id = id;}
=======
    public String getIdMap() { return this.server_id; }
    public void setIdMap(String id) { this.server_id = id;}
>>>>>>> test_sprint3_bis
    public String getMap() {return this.server_ip;}
    public void setMap() {this.server_ip = server_ip;}
}

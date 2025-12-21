package main.java.class_object;


import class_annotations.Controller;
import method_annotations.Route;
import method_annotations.PostRouteMapping;
import method_annotations.RequestParam;
import view.ModelView;
import java.util.Map;

@Controller
public class EmployeController {

    @PostRouteMapping(value = "/save-employe")
    public ModelView save(Employe employe) {
        System.out.println(employe.getName());
        System.out.println(employe.getDepartement().getName());
        
        ModelView mv = new ModelView("afterAddEmp.jsp");
        mv.setData("emp", employe);
        return mv;
    }

    @Route("/add-emp")
    public ModelView showForm() {
        return new ModelView("addEmp.jsp");
    }
}
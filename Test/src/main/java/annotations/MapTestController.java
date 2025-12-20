package annotations;

import class_annotations.Controller;
import method_annotations.Route;
import method_annotations.PostRouteMapping;
import method_annotations.RequestParam;
import view.ModelView;
import java.util.Map;

@Controller
public class MapTestController {

    @PostRouteMapping(value = "/test-map")
    public ModelView saveUser(
            @RequestParam("name") String nom,
            @RequestParam("qi") int age,
            Map<String, Object> formData                     
    ) {
        System.out.println("=== DONNÉES REÇUES ===");
        System.out.println("Nom : " + nom);
        System.out.println("Âge : " + age);
        System.out.println("TOUT LE FORMULAIRE : " + formData);

        // Tu peux accéder aux checkbox comme ça :
        String[] loisirs = (String[]) formData.get("leasures");
        System.out.println("Loisirs sélectionnés : " + java.util.Arrays.toString(loisirs));

        // Préparer les données pour la vue
        ModelView mv = new ModelView("resultMap.jsp");
        mv.setData("name", nom);
        mv.setData("qi", age);
        mv.setData("formData", formData);
        mv.setData("leasures", loisirs != null ? loisirs : new String[0]);

        return mv;
    }

    // Page d'accueil pour tester
    @Route("/map-test")
    public ModelView showForm() {
        return new ModelView("MapTest.jsp");
    }
}
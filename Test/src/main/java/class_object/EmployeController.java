package main.java.class_object;


import class_annotations.Controller;
import method_annotations.*;
import view.ModelView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeController {
    @Json
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
    
    @Json
    @PostRouteMapping(value = "/save-employe-with-file")
    public ModelView saveWithFile(
            Employe employe,
            Map<String, List<byte[]>> uploadedFiles,  // Une seule Map, gère tout
            Map<String, Object> formData
    ) {
        System.out.println("=== DONNÉES EMPLOYÉ ===");
        System.out.println("Nom Employé : " + employe.getName());
        if (employe.getDepartement() != null) {
            System.out.println("Département : " + employe.getDepartement().getName());
            System.out.println("Niveau : " + employe.getDepartement().getLevel());
        }

        System.out.println("\n=== TOUS LES PARAMÈTRES (Map<String,Object>) ===");
        formData.forEach((key, value) -> {
            if (value.getClass().isArray()) {
                System.out.println(key + " : " + Arrays.toString((Object[]) value));
            } else {
                System.out.println(key + " : " + value);
            }
        });

        // === Sauvegarde des fichiers ===
        String uploadDirPath = System.getProperty("user.home")
                + "/apache-tomcat-10.1.28/webapps/test_app/uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<String> savedFiles = new ArrayList<>();

        for (Map.Entry<String, List<byte[]>> entry : uploadedFiles.entrySet()) {
            String fieldName = entry.getKey();
            List<byte[]> files = entry.getValue();
            for (int i = 0; i < files.size(); i++) {
                byte[] bytes = files.get(i);
                String fileName = fieldName + (files.size() > 1 ? "_" + i : "") + "_" + System.currentTimeMillis() + ".uploaded";
                String filePath = uploadDirPath + File.separator + fileName;
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    fos.write(bytes);
                    savedFiles.add("/test_app/uploads/" + fileName);
                    System.out.println("Fichier sauvegardé (" + fieldName + ") : " + filePath + " (" + bytes.length + " bytes)");
                } catch (IOException e) {
                    System.err.println("Erreur sauvegarde : " + e.getMessage());
                }
            }
        }

        // Retour
        ModelView mv = new ModelView("afterUpload.jsp");
        mv.setData("employe", employe);
        mv.setData("savedFiles", savedFiles);
        mv.setData("formData", formData);
        mv.setData("message", savedFiles.isEmpty() 
                ? "Employé ajouté (sans fichier)." 
                : "Employé + " + savedFiles.size() + " fichier(s) uploadé(s) !");
        return mv;
    }

    @PostRouteMapping(value = "/save-employe-full-test")
    public ModelView saveFullTest(
            Employe employe,                                              // Binding objet complexe
            @RequestParam("poste") String posteAnnoté,                    // Paramètre annoté
            String salaire,                                               // Paramètre simple SANS annotation (par nom de variable)
            Map<String, List<byte[]>> uploadedFiles,                      // Tous les fichiers (single + multiple)
            Map<String, Object> formData                                  // Tous les params texte
    ) {
        System.out.println("=== TEST COMPLET ===");
        System.out.println("Employé : " + employe.getName() + " (Dépt: " + employe.getDepartement().getName() + ")");
        System.out.println("Poste (annoté) : " + posteAnnoté);
        System.out.println("Salaire (simple, sans annotation) : " + salaire);
        System.out.println("Nombre de fichiers uploadés : " + 
            uploadedFiles.values().stream().mapToInt(List::size).sum());

        formData.forEach((k, v) -> {
            if (v.getClass().isArray()) {
                System.out.println("formData[" + k + "] = " + Arrays.toString((Object[]) v));
            } else {
                System.out.println("formData[" + k + "] = " + v);
            }
        });

        // Sauvegarde fichiers (comme avant)
        String uploadDirPath = System.getProperty("user.home")
                + "/apache-tomcat-10.1.28/webapps/test_app/uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        List<String> savedFiles = new ArrayList<>();
        for (Map.Entry<String, List<byte[]>> entry : uploadedFiles.entrySet()) {
            String field = entry.getKey();
            for (int i = 0; i < entry.getValue().size(); i++) {
                byte[] bytes = entry.getValue().get(i);
                String fileName = field + (entry.getValue().size() > 1 ? "_" + i : "") 
                        + "_" + System.currentTimeMillis() + ".uploaded";
                String path = uploadDirPath + File.separator + fileName;
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    fos.write(bytes);
                    savedFiles.add("/test_app/uploads/" + fileName);
                } catch (IOException e) {
                    System.err.println("Erreur sauvegarde : " + e.getMessage());
                }
            }
        }

        ModelView mv = new ModelView("afterUpload.jsp");
        mv.setData("employe", employe);
        mv.setData("savedFiles", savedFiles);
        mv.setData("formData", formData);
        mv.setData("message", "TEST COMPLET RÉUSSI ! Tout fonctionne.");
        return mv;
    }
}
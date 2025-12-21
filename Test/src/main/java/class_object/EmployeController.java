package main.java.class_object;

import class_annotations.Controller;
import method_annotations.*;
import view.ModelView;
import jakarta.servlet.http.HttpServletRequest;
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
        if (employe.getDepartement() != null) {
            System.out.println(employe.getDepartement().getName());
        }
        ModelView mv = new ModelView("afterAddEmp.jsp");
        mv.setData("emp", employe);
        return mv;
    }

    @Route("/add-emp")
    public ModelView showForm() {
        return new ModelView("addEmp.jsp");
    }

    @PostRouteMapping(value = "/save-employe-with-file")
    public ModelView saveWithFile(
            Employe employe,
            Map<String, List<byte[]>> uploadedFiles,
            Map<String, Object> formData,
            HttpServletRequest request
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

        // === CHEMIN D'UPLOAD DYNAMIQUE ET PORTABLE ===
        String uploadDirPath = request.getServletContext().getRealPath("/uploads/");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println("Dossier /uploads créé automatiquement : " + uploadDirPath);
        }

        List<String> savedFiles = new ArrayList<>();

        for (Map.Entry<String, List<byte[]>> entry : uploadedFiles.entrySet()) {
            String fieldName = entry.getKey();
            List<byte[]> files = entry.getValue();
            for (int i = 0; i < files.size(); i++) {
                byte[] bytes = files.get(i);
                String fileName = fieldName + (files.size() > 1 ? "_" + i : "")
                        + "_" + System.currentTimeMillis() + ".uploaded";
                String fullPath = uploadDirPath + fileName;

                try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                    fos.write(bytes);
                    savedFiles.add("/uploads/" + fileName);  // URL accessible
                    System.out.println("Fichier sauvegardé : " + fullPath + " (" + bytes.length + " bytes)");
                } catch (IOException e) {
                    System.err.println("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
                }
            }
        }

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
            Employe employe,
            @RequestParam("poste") String posteAnnoté,
            String salaire,
            Map<String, List<byte[]>> uploadedFiles,
            Map<String, Object> formData,
            HttpServletRequest request
    ) {
        System.out.println("=== TEST COMPLET ===");
        System.out.println("Employé : " + employe.getName() + " (Dépt: " + 
                (employe.getDepartement() != null ? employe.getDepartement().getName() : "aucun") + ")");
        System.out.println("Poste (annoté) : " + posteAnnoté);
        System.out.println("Salaire (simple) : " + salaire);
        System.out.println("Nombre de fichiers uploadés : " +
                uploadedFiles.values().stream().mapToInt(List::size).sum());

        formData.forEach((k, v) -> {
            if (v.getClass().isArray()) {
                System.out.println("formData[" + k + "] = " + Arrays.toString((Object[]) v));
            } else {
                System.out.println("formData[" + k + "] = " + v);
            }
        });

        // Même logique dynamique que dans saveWithFile
        String uploadDirPath = request.getServletContext().getRealPath("/uploads/");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<String> savedFiles = new ArrayList<>();
        for (Map.Entry<String, List<byte[]>> entry : uploadedFiles.entrySet()) {
            String field = entry.getKey();
            List<byte[]> files = entry.getValue();
            for (int i = 0; i < files.size(); i++) {
                byte[] bytes = files.get(i);
                String fileName = field + (files.size() > 1 ? "_" + i : "")
                        + "_" + System.currentTimeMillis() + ".uploaded";
                String fullPath = uploadDirPath + fileName;
                try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                    fos.write(bytes);
                    savedFiles.add("/uploads/" + fileName);
                } catch (IOException e) {
                    System.err.println("Erreur sauvegarde : " + e.getMessage());
                }
            }
        }

        ModelView mv = new ModelView("afterUpload.jsp");
        mv.setData("employe", employe);
        mv.setData("savedFiles", savedFiles);
        mv.setData("formData", formData);
        mv.setData("message", "TEST COMPLET RÉUSSI ! Tout fonctionne parfaitement.");
        return mv;
    }
}
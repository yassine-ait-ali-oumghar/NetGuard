
package ma.netguard.service;

import ma.netguard.dao.TechnicienDAO;
import ma.netguard.model.Technicien;

import java.util.List;

public class TechnicienService {

    private TechnicienDAO technicienDAO = new TechnicienDAO();

    public int ajouterTechnicien(Technicien technicien) {
        if (technicien.getNom() == null || technicien.getNom().isBlank()) {
            System.out.println("Erreur : le nom du technicien est obligatoire !");
            return 0;
        }

        if (technicien.getSpecialite() == null || technicien.getSpecialite().isBlank()) {
            System.out.println("Erreur : la spécialité est obligatoire !");
            return 0;
        }

        return technicienDAO.ajouter(technicien);
    }

    public List<Technicien> afficherTechniciens() {
        return technicienDAO.afficherTous();
    }

    public Technicien rechercherTechnicien(int id) {
        if (id <= 0) {
            System.out.println("ID invalide !");
            return null;
        }

        return technicienDAO.rechercherParId(id);
    }

    public void modifierTechnicien(Technicien technicien) {
        technicienDAO.modifier(technicien);
    }

    public void supprimerTechnicien(int id) {
        if (id <= 0) {
            System.out.println("ID invalide !");
            return;
        }

        technicienDAO.supprimer(id);
    }
}

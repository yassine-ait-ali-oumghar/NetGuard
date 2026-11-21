
package ma.netguard.service;

import ma.netguard.dao.EquipementDAO;
import ma.netguard.model.Equipement;

import java.util.List;

public class EquipementService {

    private EquipementDAO equipementDAO = new EquipementDAO();

    public int ajouterEquipement(Equipement equipement) {
        if (equipement.getNom() == null || equipement.getNom().isBlank()) {
            System.out.println("Erreur : le nom est obligatoire !");
            return 0;
        }

        if (equipement.getAdresseIP() == null || equipement.getAdresseIP().isBlank()) {
            System.out.println("Erreur : l'adresse IP est obligatoire !");
            return 0;
        }

        return equipementDAO.ajouter(equipement);
    }

    public List<Equipement> afficherEquipements() {
        return equipementDAO.afficherTous();
    }

    public Equipement rechercherEquipement(int id) {
        if (id <= 0) {
            System.out.println("ID invalide !");
            return null;
        }

        return equipementDAO.rechercherParId(id);
    }

    public void modifierEquipement(Equipement equipement) {
        equipementDAO.modifier(equipement);
    }

    public void supprimerEquipement(int id) {
        if (id <= 0) {
            System.out.println("ID invalide !");
            return;
        }

        equipementDAO.supprimer(id);
    }
}

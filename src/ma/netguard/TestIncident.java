
package ma.netguard;

import ma.netguard.dao.EquipementDAO;
import ma.netguard.dao.TechnicienDAO;
import ma.netguard.dao.IncidentDAO;

import ma.netguard.model.Equipement;
import ma.netguard.model.Technicien;
import ma.netguard.model.Incident;
import ma.netguard.model.Priorite;
import ma.netguard.model.StatutIncident;

import java.util.List;

public class TestIncident {

    public static void main(String[] args) {

        EquipementDAO equipementDAO = new EquipementDAO();
        TechnicienDAO technicienDAO = new TechnicienDAO();
        IncidentDAO incidentDAO = new IncidentDAO();

        // ==================================
        // 1. CREER UN EQUIPEMENT DE TEST
        // ==================================

        System.out.println("===== CREATION EQUIPEMENT =====");

        Equipement equipement = new Equipement(
                0,
                "Serveur de test",
                "Serveur",
                "192.168.1.10"
        );

        equipementDAO.ajouter(equipement);

        // ==================================
        // 2. CREER UN TECHNICIEN DE TEST
        // ==================================

        System.out.println("\n===== CREATION TECHNICIEN =====");

        Technicien technicien = new Technicien(
                0,
                "Yassine",
                "Cybersecurite"
        );

        technicienDAO.ajouter(technicien);

        // ==================================
        // 3. CREATE : AJOUTER UN INCIDENT
        // ==================================

        System.out.println("\n===== AJOUTER UN INCIDENT =====");

        Incident incident = new Incident(
                "Probleme de connexion au serveur",
                Priorite.CRITIQUE,
                equipement
        );

        incident.setTechnicien(technicien);

        incidentDAO.ajouter(incident);

        int id = incident.getId();

        // ==================================
        // 4. READ : AFFICHER LES INCIDENTS
        // ==================================

        System.out.println("\n===== AFFICHER LES INCIDENTS =====");

        List<Incident> incidents = incidentDAO.afficherTous();

        for (Incident i : incidents) {
            System.out.println(i);
            System.out.println("--------------------");
        }

        // ==================================
        // 5. READ : RECHERCHER PAR ID
        // ==================================

        System.out.println("\n===== RECHERCHER UN INCIDENT =====");

        Incident resultat = incidentDAO.rechercherParId(id);

        if (resultat != null) {
            System.out.println(resultat);
        } else {
            System.out.println("Incident introuvable !");
        }

        // ==================================
        // 6. UPDATE : MODIFIER L'INCIDENT
        // ==================================

        System.out.println("\n===== MODIFIER UN INCIDENT =====");

        if (resultat != null) {

            resultat.setDescription(
                    "Serveur inaccessible apres verification"
            );

            resultat.setPriorite(Priorite.ELEVEE);

            resultat.setStatut(StatutIncident.EN_COURS);

            incidentDAO.modifier(resultat);

            System.out.println(
                    incidentDAO.rechercherParId(id)
            );
        }

        // ==================================
        // 7. DELETE : SUPPRIMER L'INCIDENT
        // ==================================

        System.out.println("\n===== SUPPRIMER UN INCIDENT =====");

        incidentDAO.supprimer(id);

        Incident verification = incidentDAO.rechercherParId(id);

        if (verification == null) {
            System.out.println("Incident supprime et verifie !");
        } else {
            System.out.println("La suppression de l'incident a echoue !");
        }

        // ==================================
        // 8. NETTOYER LES DONNEES DE TEST
        // ==================================

        System.out.println("\n===== NETTOYAGE =====");

        technicienDAO.supprimer(technicien.getId());
        equipementDAO.supprimer(equipement.getId());

        System.out.println("Nettoyage termine !");
    }
}

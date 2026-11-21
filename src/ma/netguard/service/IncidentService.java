
package ma.netguard.service;

import ma.netguard.dao.IncidentDAO;
import ma.netguard.model.Incident;
import ma.netguard.model.Priorite;

import java.util.List;

public class IncidentService {

    private IncidentDAO incidentDAO = new IncidentDAO();

    public int ajouterIncident(Incident incident) {

        if (incident.getDescription() == null ||
                incident.getDescription().isBlank()) {
            System.out.println("Erreur : la description est obligatoire !");
            return 0;
        }

        if (incident.getPriorite() == null) {
            System.out.println("Erreur : la priorité est obligatoire !");
            return 0;
        }

        if (incident.getEquipement() == null) {
            System.out.println("Erreur : un équipement doit être associé !");
            return 0;
        }

        return incidentDAO.ajouter(incident);
    }

    public List<Incident> afficherIncidents() {
        return incidentDAO.afficherTous();
    }

    public Incident rechercherIncident(int id) {

        if (id <= 0) {
            System.out.println("ID invalide !");
            return null;
        }

        return incidentDAO.rechercherParId(id);
    }

    public void modifierIncident(Incident incident) {

        if (incident.getDescription() == null ||
                incident.getDescription().isBlank()) {
            System.out.println("Erreur : la description est obligatoire !");
            return;
        }

        if (incident.getPriorite() == null) {
            System.out.println("Erreur : la priorité est obligatoire !");
            return;
        }

        incidentDAO.modifier(incident);
    }

    public void supprimerIncident(int id) {

        if (id <= 0) {
            System.out.println("ID invalide !");
            return;
        }

        incidentDAO.supprimer(id);
    }
}

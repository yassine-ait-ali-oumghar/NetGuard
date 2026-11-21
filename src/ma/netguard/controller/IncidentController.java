package ma.netguard.controller;

import ma.netguard.model.Incident;
import ma.netguard.service.IncidentService;
import java.util.List;


public class IncidentController {
    private final IncidentService service = new IncidentService();
    public int ajouter(Incident objet) { return service.ajouterIncident(objet); }
    public List<Incident> afficherTous() { return service.afficherIncidents(); }
    public Incident rechercher(int id) { return service.rechercherIncident(id); }
    public void modifier(Incident objet) { service.modifierIncident(objet); }
    public void supprimer(int id) { service.supprimerIncident(id); }
}

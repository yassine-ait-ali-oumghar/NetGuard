package ma.netguard.controller;

import ma.netguard.model.Technicien;
import ma.netguard.service.TechnicienService;
import java.util.List;

public class TechnicienController {
    private final TechnicienService service = new TechnicienService();
    public int ajouter(Technicien objet) { return service.ajouterTechnicien(objet); }
    public List<Technicien> afficherTous() { return service.afficherTechniciens(); }
    public Technicien rechercher(int id) { return service.rechercherTechnicien(id); }
    public void modifier(Technicien objet) { service.modifierTechnicien(objet); }
    public void supprimer(int id) { service.supprimerTechnicien(id); }
}

package ma.netguard.controller;

import ma.netguard.model.Equipement;
import ma.netguard.service.EquipementService;
import java.util.List;


public class EquipementController {
    private final EquipementService service = new EquipementService();
    public int ajouter(Equipement objet) { return service.ajouterEquipement(objet); }
    public List<Equipement> afficherTous() { return service.afficherEquipements(); }
    public Equipement rechercher(int id) { return service.rechercherEquipement(id); }
    public void modifier(Equipement objet) { service.modifierEquipement(objet); }
    public void supprimer(int id) { service.supprimerEquipement(id); }
}

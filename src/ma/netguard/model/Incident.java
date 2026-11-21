
package ma.netguard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class  Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    private StatutIncident statut;

    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "equipement_id", nullable = false)
    private Equipement equipement;

    @ManyToOne
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;

    public Incident() {
        this.statut = StatutIncident.OUVERT;
        this.dateCreation = LocalDateTime.now();
    }

    public Incident(String description, Priorite priorite,
                    Equipement equipement) {
        this.description = description;
        this.priorite = priorite;
        this.equipement = equipement;
        this.statut = StatutIncident.OUVERT;
        this.dateCreation = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public StatutIncident getStatut() {
        return statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public Technicien getTechnicien() {
        return technicien;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public void setStatut(StatutIncident statut) {
        this.statut = statut;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public void setTechnicien(Technicien technicien) {
        this.technicien = technicien;
    }

    public void modifierStatut(StatutIncident statut) {
        this.statut = statut;
    }

    public void modifierPriorite(Priorite priorite) {
        this.priorite = priorite;
    }


    @Override
    public String toString() {
        return "ID : " + id
                + "\nDescription : " + description
                + "\nPriorité : " + priorite
                + "\nStatut : " + statut
                + "\nDate de création : " + dateCreation
                + "\nEquipement : " + (equipement != null ? equipement.getNom() : "Non renseigné")
                + "\nAdresse IP : " + (equipement != null ? equipement.getAdresseIP() : "Non renseignée")
                + "\nTechnicien : " +
                (technicien != null ? technicien.getNom() : "Non affecté");
    }

}


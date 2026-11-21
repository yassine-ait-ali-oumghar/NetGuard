
package ma.netguard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "techniciens")
public class Technicien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String specialite;

    public Technicien() {
    }

    public Technicien(int id, String nom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "ID : " + id
                + "\nNom : " + nom
                + "\nSpécialité : " + specialite;
    }
}

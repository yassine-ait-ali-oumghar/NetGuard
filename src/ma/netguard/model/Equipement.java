
package ma.netguard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipements")
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String type;

    @Column(name = "adresse_ip")
    private String adresseIP;

    public Equipement() {
    }

    public Equipement(int id, String nom, String type, String adresseIP) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.adresseIP = adresseIP;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    @Override
    public String toString() {
        return "ID : " + id
                + "\nNom : " + nom
                + "\nType : " + type
                + "\nAdresse IP : " + adresseIP;
    }
}

package ma.netguard.view;

import ma.netguard.controller.*;
import ma.netguard.model.*;
import ma.netguard.service.CsvService;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/** Vue console : affiche les menus, lit les saisies et présente les résultats. */
public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final EquipementController equipements = new EquipementController();
    private final TechnicienController techniciens = new TechnicienController();
    private final IncidentController incidents = new IncidentController();
    private final CsvService csv = new CsvService();

    public void demarrer() {
        int choix;
        do {
            System.out.println("\n========== NETGUARD ==========");
            System.out.println("1. Gestion des équipements\n2. Gestion des techniciens\n3. Gestion des incidents\n4. Exporter les données en CSV\n5. Importer les données depuis CSV\n0. Quitter");
            choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> menuEquipements();
                case 2 -> menuTechniciens();
                case 3 -> menuIncidents();
                case 4 -> exporterCsv();
                case 5 -> importerCsv();
                case 0 -> System.out.println("Fermeture de NetGuard...");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private void exporterCsv() {
        try {
            csv.exporterTout();
            System.out.println("Export CSV terminé dans le dossier Data.");
        } catch (IOException e) {
            System.out.println("Erreur pendant l'export CSV : " + e.getMessage());
        }
    }

    private void importerCsv() {
        System.out.print("L'import ajoute des enregistrements. Réimporter les mêmes CSV peut créer des doublons. Continuer ? (o/n) : ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
            System.out.println("Import annulé.");
            return;
        }
        try {
            System.out.println(csv.importerTout());
        } catch (IOException e) {
            System.out.println("Erreur pendant l'import CSV : " + e.getMessage());
        }
    }

    private int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine().trim();
            try { return Integer.parseInt(saisie); }
            catch (NumberFormatException e) { System.out.println("Veuillez saisir un nombre entier."); }
        }
    }
    private String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
    private void menuEquipements() {
        int c;
        do {
            System.out.println("\n--- ÉQUIPEMENTS ---\n1 Ajouter\n2 Afficher\n3 Rechercher\n4 Modifier\n5 Supprimer\n0 Retour");
            c=lireEntier("Choix : ");
            switch(c) {
                case 1 -> equipements.ajouter(new Equipement(0,lireTexte("Nom : "),lireTexte("Type : "),lireTexte("Adresse IP : ")));
                case 2 -> equipements.afficherTous().forEach(System.out::println);
                case 3 -> afficher(equipements.rechercher(lireEntier("ID : ")));
                case 4 -> { Equipement e=equipements.rechercher(lireEntier("ID à modifier : ")); if(e!=null){e.setNom(lireTexte("Nouveau nom : "));e.setType(lireTexte("Nouveau type : "));e.setAdresseIP(lireTexte("Nouvelle adresse IP : "));equipements.modifier(e);}else introuvable(); }
                case 5 -> equipements.supprimer(lireEntier("ID à supprimer : "));
                case 0 -> { }
                default -> System.out.println("Choix invalide.");
            }
        } while(c!=0);
    }
    private void menuTechniciens() {
        int c;
        do {
            System.out.println("\n--- TECHNICIENS ---\n1 Ajouter\n2 Afficher\n3 Rechercher\n4 Modifier\n5 Supprimer\n0 Retour");
            c=lireEntier("Choix : ");
            switch(c) {
                case 1 -> techniciens.ajouter(new Technicien(0,lireTexte("Nom : "),lireTexte("Spécialité : ")));
                case 2 -> techniciens.afficherTous().forEach(System.out::println);
                case 3 -> afficher(techniciens.rechercher(lireEntier("ID : ")));
                case 4 -> { Technicien t=techniciens.rechercher(lireEntier("ID à modifier : ")); if(t!=null){t.setNom(lireTexte("Nouveau nom : "));t.setSpecialite(lireTexte("Nouvelle spécialité : "));techniciens.modifier(t);}else introuvable(); }
                case 5 -> techniciens.supprimer(lireEntier("ID à supprimer : "));
                case 0 -> { }
                default -> System.out.println("Choix invalide.");
            }
        } while(c!=0);
    }
    private void menuIncidents() {
        int c;
        do {
            System.out.println("\n--- INCIDENTS ---\n1 Ajouter\n2 Afficher\n3 Rechercher\n4 Modifier\n5 Supprimer\n6 Affecter un technicien\n7 Filtrer les incidents critiques\n0 Retour");
            c=lireEntier("Choix : ");
            switch(c) {
                case 1 -> ajouterIncident();
                case 2 -> incidents.afficherTous().forEach(System.out::println);
                case 3 -> afficher(incidents.rechercher(lireEntier("ID : ")));
                case 4 -> { Incident i=incidents.rechercher(lireEntier("ID à modifier : ")); if(i!=null){i.setDescription(lireTexte("Nouvelle description : "));i.setPriorite(lirePriorite());i.setStatut(lireStatut());incidents.modifier(i);}else introuvable(); }
                case 5 -> incidents.supprimer(lireEntier("ID à supprimer : "));
                case 6 -> affecterTechnicien();
                case 7 -> incidents.afficherTous().stream().filter(i->i.getPriorite()==Priorite.CRITIQUE).forEach(System.out::println);
                case 0 -> { }
                default -> System.out.println("Choix invalide.");
            }
        } while(c!=0);
    }
    private void ajouterIncident() {
        List<Equipement> liste=equipements.afficherTous();
        if(liste.isEmpty()){System.out.println("Ajoutez d'abord un équipement.");return;}
        liste.forEach(System.out::println);
        Equipement e=equipements.rechercher(lireEntier("ID de l'équipement : "));
        if(e==null){introuvable();return;}
        Incident i=new Incident(lireTexte("Description : "),lirePriorite(),e);
        List<Technicien> ts=techniciens.afficherTous();
        if(!ts.isEmpty()){
            ts.forEach(System.out::println);
            int id=lireEntier("ID technicien (0 = aucun) : ");
            if(id!=0){Technicien t=techniciens.rechercher(id);if(t==null){introuvable();return;}i.setTechnicien(t);}
        }
        incidents.ajouter(i);
    }
    private void affecterTechnicien() {
        Incident i=incidents.rechercher(lireEntier("ID de l'incident : "));
        if(i==null){introuvable();return;}
        List<Technicien> ts=techniciens.afficherTous();
        if(ts.isEmpty()){System.out.println("Aucun technicien disponible.");return;}
        ts.forEach(System.out::println);
        Technicien t=techniciens.rechercher(lireEntier("ID du technicien : "));
        if(t==null){introuvable();return;}
        i.setTechnicien(t); incidents.modifier(i);
        System.out.println("Demande d'affectation transmise.");
    }
    private Priorite lirePriorite(){
        Priorite[] valeurs=Priorite.values();
        for(int n=0;n<valeurs.length;n++)System.out.println((n+1)+". "+valeurs[n]);
        int choix=lireEntier("Priorité : ");
        while(choix<1||choix>valeurs.length){System.out.println("Priorité invalide.");choix=lireEntier("Priorité : ");}
        return valeurs[choix-1];
    }
    private StatutIncident lireStatut(){
        StatutIncident[] valeurs=StatutIncident.values();
        for(int n=0;n<valeurs.length;n++)System.out.println((n+1)+". "+valeurs[n]);
        int choix=lireEntier("Statut : ");
        while(choix<1||choix>valeurs.length){System.out.println("Statut invalide.");choix=lireEntier("Statut : ");}
        return valeurs[choix-1];
    }
    private void afficher(Object objet){if(objet==null)introuvable();else System.out.println(objet);}
    private void introuvable(){System.out.println("Élément introuvable.");}
}

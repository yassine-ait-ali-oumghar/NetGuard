package ma.netguard.service;

import ma.netguard.model.Equipement;
import ma.netguard.model.Incident;
import ma.netguard.model.Technicien;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import ma.netguard.model.Priorite;
import ma.netguard.model.StatutIncident;

/** Exporte les données courantes de la base Hibernate en fichiers CSV UTF-8. */
public class CsvService {
    private final Path dossier = Paths.get("Data");
    private final EquipementService equipementService = new EquipementService();
    private final TechnicienService technicienService = new TechnicienService();
    private final IncidentService incidentService = new IncidentService();

    public void exporterTout() throws IOException {
        Files.createDirectories(dossier);
        exporterEquipements(equipementService.afficherEquipements());
        exporterTechniciens(technicienService.afficherTechniciens());
        exporterIncidents(incidentService.afficherIncidents());
    }

    /** Importe les trois CSV comme nouveaux enregistrements et remappe les relations. */
    public String importerTout() throws IOException {
        Map<Integer, Integer> equipementIds = new HashMap<>();
        Map<Integer, Integer> technicienIds = new HashMap<>();
        int[] compteurs = new int[3];
        List<String> erreurs = new java.util.ArrayList<>();

        importer("equipements.csv", "id;nom;type;adresse_ip", 4, (c, n) -> {
            int ancienId = entier(c.get(0));
            int nouvelId = equipementService.ajouterEquipement(
                    new Equipement(0, c.get(1), c.get(2), c.get(3)));
            if (nouvelId == 0) throw new IllegalArgumentException("équipement refusé par la base");
            equipementIds.put(ancienId, nouvelId);
            compteurs[0]++;
        }, erreurs);

        importer("techniciens.csv", "id;nom;specialite", 3, (c, n) -> {
            int ancienId = entier(c.get(0));
            int nouvelId = technicienService.ajouterTechnicien(
                    new Technicien(0, c.get(1), c.get(2)));
            if (nouvelId == 0) throw new IllegalArgumentException("technicien refusé par la base");
            technicienIds.put(ancienId, nouvelId);
            compteurs[1]++;
        }, erreurs);

        importer("incidents.csv", "id;description;priorite;statut;date_creation;equipement_id;technicien_id", 7,
                (c, n) -> {
                    Integer equipementId = equipementIds.get(entier(c.get(5)));
                    if (equipementId == null) throw new IllegalArgumentException("équipement lié introuvable dans l'import");
                    Equipement equipement = equipementService.rechercherEquipement(equipementId);
                    Incident incident = new Incident(c.get(1), Priorite.valueOf(c.get(2)), equipement);
                    incident.setStatut(StatutIncident.valueOf(c.get(3)));
                    incident.setDateCreation(java.time.LocalDateTime.parse(c.get(4)));
                    if (!c.get(6).isBlank()) {
                        Integer technicienId = technicienIds.get(entier(c.get(6)));
                        if (technicienId == null) throw new IllegalArgumentException("technicien lié introuvable dans l'import");
                        incident.setTechnicien(technicienService.rechercherTechnicien(technicienId));
                    }
                    if (incidentService.ajouterIncident(incident) == 0)
                        throw new IllegalArgumentException("incident refusé par la base");
                    compteurs[2]++;
                }, erreurs);

        String resultat = "Import terminé : " + compteurs[0] + " équipements, "
                + compteurs[1] + " techniciens et " + compteurs[2] + " incidents importés.";
        if (!erreurs.isEmpty()) resultat += "\nLignes ignorées :\n - " + String.join("\n - ", erreurs);
        return resultat;
    }

    private interface LigneImport {
        void traiter(List<String> colonnes, int numeroLigne) throws Exception;
    }

    private void importer(String nom, String enteteAttendue, int nombreColonnes,
                          LigneImport action, List<String> erreurs) throws IOException {
        Path fichier = dossier.resolve(nom);
        if (!Files.exists(fichier)) throw new IOException("Fichier manquant : " + fichier);
        try (BufferedReader reader = Files.newBufferedReader(fichier, StandardCharsets.UTF_8)) {
            String entete = reader.readLine();
            if (entete == null) return; // Un fichier vide représente une liste vide.
            int numero = 0;
            if (String.join(";", parser(entete)).equals(enteteAttendue)) {
                numero = 1;
            } else {
                traiterLigne(entete, nom, 1, nombreColonnes, action, erreurs);
                numero = 1;
            }
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                numero++;
                traiterLigne(ligne, nom, numero, nombreColonnes, action, erreurs);
            }
        }
    }

    private void traiterLigne(String ligne, String nom, int numero, int nombreColonnes,
                              LigneImport action, List<String> erreurs) {
        if (ligne.isBlank()) return;
        try {
            List<String> colonnes = parser(ligne);
            if (colonnes.size() != nombreColonnes)
                throw new IllegalArgumentException("nombre de colonnes invalide");
            action.traiter(colonnes, numero);
        } catch (Exception e) {
            erreurs.add(nom + ", ligne " + numero + " : " + e.getMessage());
        }
    }

    private int entier(String valeur) {
        return Integer.parseInt(valeur);
    }

    /** Lit les champs séparés par des points-virgules, avec guillemets CSV et guillemets doublés. */
    private List<String> parser(String ligne) {
        List<String> champs = new java.util.ArrayList<>();
        StringBuilder champ = new StringBuilder();
        boolean entreGuillemets = false;
        for (int i = 0; i < ligne.length(); i++) {
            char caractere = ligne.charAt(i);
            if (caractere == '"') {
                if (entreGuillemets && i + 1 < ligne.length() && ligne.charAt(i + 1) == '"') {
                    champ.append('"');
                    i++;
                } else entreGuillemets = !entreGuillemets;
            } else if (caractere == ';' && !entreGuillemets) {
                champs.add(champ.toString());
                champ.setLength(0);
            } else champ.append(caractere);
        }
        if (entreGuillemets) throw new IllegalArgumentException("guillemets CSV non fermés");
        champs.add(champ.toString());
        return champs;
    }

    private void exporterEquipements(List<Equipement> elements) throws IOException {
        ecrire("equipements.csv", "id;nom;type;adresse_ip", elements.stream()
                .map(e -> ligne(e.getId(), e.getNom(), e.getType(), e.getAdresseIP())).toList());
    }

    private void exporterTechniciens(List<Technicien> elements) throws IOException {
        ecrire("techniciens.csv", "id;nom;specialite", elements.stream()
                .map(t -> ligne(t.getId(), t.getNom(), t.getSpecialite())).toList());
    }

    private void exporterIncidents(List<Incident> elements) throws IOException {
        ecrire("incidents.csv", "id;description;priorite;statut;date_creation;equipement_id;technicien_id",
                elements.stream().map(i -> ligne(i.getId(), i.getDescription(), i.getPriorite(),
                        i.getStatut(), i.getDateCreation(), i.getEquipement().getId(),
                        i.getTechnicien() == null ? "" : i.getTechnicien().getId())).toList());
    }

    private void ecrire(String nom, String entete, List<String> lignes) throws IOException {
        Path fichier = dossier.resolve(nom);
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, StandardCharsets.UTF_8)) {
            writer.write(entete);
            writer.newLine();
            for (String ligne : lignes) {
                writer.write(ligne);
                writer.newLine();
            }
        }
    }

    private String ligne(Object... champs) {
        StringBuilder resultat = new StringBuilder();
        for (Object champ : champs) {
            if (resultat.length() > 0) resultat.append(';');
            String valeur = champ == null ? "" : champ.toString();
            resultat.append('"').append(valeur.replace("\"", "\"\"")).append('"');
        }
        return resultat.toString();
    }
}

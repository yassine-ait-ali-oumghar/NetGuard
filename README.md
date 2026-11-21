# NetGuard

NetGuard est une application console Java de gestion des équipements réseau, des techniciens et des incidents. Le projet utilise MVC, avec des couches service et DAO, Hibernate et PostgreSQL.

## Prérequis

- JDK 21
- Maven 3.9 ou supérieur
- PostgreSQL

## Configuration de PostgreSQL

Créez une base nommée `NetGuard`. Hibernate crée ou met à jour les tables au démarrage.

La connexion utilise `localhost:5432`, l'utilisateur `postgres` et le mot de passe fourni par la variable d'environnement `NETGUARD_DB_PASSWORD`. Aucun mot de passe n'est enregistré dans le dépôt.

Dans PowerShell, définissez le mot de passe pour la session courante :

```powershell
$env:NETGUARD_DB_PASSWORD = "votre_mot_de_passe_postgres"
```

## Compiler et lancer

Depuis la racine du projet :

```powershell
mvn clean compile
mvn exec:java
```

## Fonctionnalités

- Gestion CRUD des équipements, techniciens et incidents
- Affectation d'un technicien à un incident et filtre des incidents critiques
- Export CSV depuis l'option 4 du menu principal vers `Data/equipements.csv`, `Data/techniciens.csv` et `Data/incidents.csv`
- Import CSV depuis l'option 5 ; les identifiants des relations sont remappés vers ceux générés par la base

L'import crée de nouveaux enregistrements et peut donc créer des doublons si les mêmes fichiers sont importés plusieurs fois. Sauvegardez la base avant un import.

Les fichiers de données locaux et les configurations IntelliJ ne sont pas versionnés. L'export recrée le dossier `Data` si nécessaire.

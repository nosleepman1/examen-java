package service;

import model.Athlete;
import model.Competition;
import model.Discipline;
import model.MedailleParPays;
import model.Pays;
import model.Resultat;
import model.Utilisateur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class IMenuImple implements IMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final IAuthService authService = new IAuthServiceImple();
    private final IUtilisateurService utilisateurService = new IUtilisateurServiceImple();
    private final IPaysService paysService = new IPaysServiceImple();
    private final IDisciplineService disciplineService = new IDisciplineServiceImple();
    private final IAthleteService athleteService = new IAthleteServiceImple();
    private final ICompetitionService competitionService = new ICompetitionServiceImple();
    private final IResultatService resultatService = new IResultatServiceImple();
    private final IStatistiqueService statistiqueService = new IStatistiqueServiceImple();

    private Utilisateur utilisateurConnecte;

    @Override
    public void demarrer() {
        System.out.println("=== JEUX OLYMPIQUES DE LA JEUNESSE DAKAR 2026 ===");
        boolean quitter = false;
        while (!quitter) {
            if (seConnecter()) {
                quitter = afficherMenuPrincipal();
            } else {
                System.out.println("Login ou mot de passe incorrect.");
            }
        }
        System.out.println("Fin du programme. A bientot !");
    }

    // ---------------------------------------------------------------------
    // Authentification
    // ---------------------------------------------------------------------

    private boolean seConnecter() {
        System.out.println("\n--- Connexion ---");
        String login = lireTexte("Login : ");
        String motDePasse = lireTexte("Mot de passe : ");
        try {
            utilisateurConnecte = authService.seConnecter(login, motDePasse);
        } catch (SQLException e) {
            System.out.println("Erreur base de donnees : " + e.getMessage());
            return false;
        }
        return utilisateurConnecte != null;
    }

    // ---------------------------------------------------------------------
    // Menu principal
    // ---------------------------------------------------------------------

    /**
     * Retourne true si l'utilisateur choisit de quitter definitivement l'application.
     */
    private boolean afficherMenuPrincipal() {
        boolean deconnecte = false;
        while (!deconnecte) {
            System.out.println("\n===================================");
            System.out.println("JEUX OLYMPIQUES DE LA JEUNESSE 2026");
            System.out.println("===================================");
            System.out.println("1. Gestion des utilisateurs");
            System.out.println("2. Gestion des pays");
            System.out.println("3. Gestion des disciplines");
            System.out.println("4. Gestion des athletes");
            System.out.println("5. Gestion des competitions");
            System.out.println("6. Gestion des resultats");
            System.out.println("7. Statistiques");
            System.out.println("8. Deconnexion");
            System.out.println("9. Quitter");
            int choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1: menuUtilisateurs(); break;
                case 2: menuPays(); break;
                case 3: menuDisciplines(); break;
                case 4: menuAthletes(); break;
                case 5: menuCompetitions(); break;
                case 6: menuResultats(); break;
                case 7: afficherStatistiques(); break;
                case 8: deconnecte = true; break;
                case 9: return true;
                default: System.out.println("Choix invalide.");
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // Module 1 : Gestion des utilisateurs (reserve a l'administrateur)
    // ---------------------------------------------------------------------

    private void menuUtilisateurs() {
        if (!utilisateurConnecte.estAdmin()) {
            System.out.println("Acces reserve a l'administrateur.");
            return;
        }
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des utilisateurs --");
            System.out.println("1. Ajouter utilisateur");
            System.out.println("2. Modifier utilisateur");
            System.out.println("3. Supprimer utilisateur");
            System.out.println("4. Rechercher utilisateur");
            System.out.println("5. Afficher utilisateurs");
            System.out.println("6. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: ajouterUtilisateur(); break;
                    case 2: modifierUtilisateur(); break;
                    case 3: supprimerUtilisateur(); break;
                    case 4: rechercherUtilisateur(); break;
                    case 5: afficherListe(utilisateurService.listerTous()); break;
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            }
        }
    }

    private void ajouterUtilisateur() throws SQLException {
        String nomComplet = lireTexte("Nom complet : ");
        String login = lireTexte("Login : ");
        String motDePasse = lireTexte("Mot de passe : ");
        String role = lireTexte("Role (ADMIN / UTILISATEUR) : ");
        utilisateurService.ajouter(new Utilisateur(nomComplet, login, motDePasse, role));
        System.out.println("Utilisateur ajoute.");
    }

    private void modifierUtilisateur() throws SQLException {
        int id = lireEntier("Id utilisateur a modifier : ");
        String nomComplet = lireTexte("Nouveau nom complet : ");
        String login = lireTexte("Nouveau login : ");
        String motDePasse = lireTexte("Nouveau mot de passe : ");
        String role = lireTexte("Nouveau role : ");
        utilisateurService.modifier(new Utilisateur(id, nomComplet, login, motDePasse, role));
        System.out.println("Utilisateur modifie.");
    }

    private void supprimerUtilisateur() throws SQLException {
        int id = lireEntier("Id utilisateur a supprimer : ");
        utilisateurService.supprimer(id);
        System.out.println("Utilisateur supprime.");
    }

    private void rechercherUtilisateur() throws SQLException {
        String motCle = lireTexte("Mot cle (nom ou login) : ");
        afficherListe(utilisateurService.rechercher(motCle));
    }

    // ---------------------------------------------------------------------
    // Module 2 : Gestion des pays
    // ---------------------------------------------------------------------

    private void menuPays() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des pays --");
            System.out.println("1. Ajouter pays");
            System.out.println("2. Modifier pays");
            System.out.println("3. Supprimer pays");
            System.out.println("4. Rechercher pays");
            System.out.println("5. Liste des pays");
            System.out.println("6. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: ajouterPays(); break;
                    case 2: modifierPays(); break;
                    case 3: supprimerPays(); break;
                    case 4: rechercherPays(); break;
                    case 5: afficherListe(paysService.listerTous()); break;
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            }
        }
    }

    private void ajouterPays() throws SQLException {
        String nomPays = lireTexte("Nom du pays : ");
        String continent = lireTexte("Continent : ");
        paysService.ajouter(new Pays(nomPays, continent));
        System.out.println("Pays ajoute.");
    }

    private void modifierPays() throws SQLException {
        int id = lireEntier("Id pays a modifier : ");
        String nomPays = lireTexte("Nouveau nom du pays : ");
        String continent = lireTexte("Nouveau continent : ");
        paysService.modifier(new Pays(id, nomPays, continent));
        System.out.println("Pays modifie.");
    }

    private void supprimerPays() throws SQLException {
        int id = lireEntier("Id pays a supprimer : ");
        paysService.supprimer(id);
        System.out.println("Pays supprime.");
    }

    private void rechercherPays() throws SQLException {
        String motCle = lireTexte("Mot cle (nom ou continent) : ");
        afficherListe(paysService.rechercher(motCle));
    }

    // ---------------------------------------------------------------------
    // Module 3 : Gestion des disciplines
    // ---------------------------------------------------------------------

    private void menuDisciplines() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des disciplines --");
            System.out.println("1. Ajouter discipline");
            System.out.println("2. Modifier discipline");
            System.out.println("3. Supprimer discipline");
            System.out.println("4. Rechercher discipline");
            System.out.println("5. Afficher disciplines");
            System.out.println("6. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: ajouterDiscipline(); break;
                    case 2: modifierDiscipline(); break;
                    case 3: supprimerDiscipline(); break;
                    case 4: rechercherDiscipline(); break;
                    case 5: afficherListe(disciplineService.listerTous()); break;
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            }
        }
    }

    private void ajouterDiscipline() throws SQLException {
        String nom = lireTexte("Nom de la discipline : ");
        String description = lireTexte("Description : ");
        disciplineService.ajouter(new Discipline(nom, description));
        System.out.println("Discipline ajoutee.");
    }

    private void modifierDiscipline() throws SQLException {
        int id = lireEntier("Id discipline a modifier : ");
        String nom = lireTexte("Nouveau nom : ");
        String description = lireTexte("Nouvelle description : ");
        disciplineService.modifier(new Discipline(id, nom, description));
        System.out.println("Discipline modifiee.");
    }

    private void supprimerDiscipline() throws SQLException {
        int id = lireEntier("Id discipline a supprimer : ");
        disciplineService.supprimer(id);
        System.out.println("Discipline supprimee.");
    }

    private void rechercherDiscipline() throws SQLException {
        String motCle = lireTexte("Mot cle : ");
        afficherListe(disciplineService.rechercher(motCle));
    }

    // ---------------------------------------------------------------------
    // Module 4 : Gestion des athletes
    // ---------------------------------------------------------------------

    private void menuAthletes() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des athletes --");
            System.out.println("1. Ajouter athlete");
            System.out.println("2. Modifier athlete");
            System.out.println("3. Supprimer athlete");
            System.out.println("4. Rechercher athlete");
            System.out.println("5. Afficher athletes");
            System.out.println("6. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: ajouterAthlete(); break;
                    case 2: modifierAthlete(); break;
                    case 3: supprimerAthlete(); break;
                    case 4: rechercherAthlete(); break;
                    case 5: afficherListe(athleteService.listerTous()); break;
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            }
        }
    }

    private void ajouterAthlete() throws SQLException {
        String nom = lireTexte("Nom : ");
        String prenom = lireTexte("Prenom : ");
        String sexe = lireTexte("Sexe (M/F) : ");
        LocalDate dateNaissance = lireDate("Date de naissance");
        int idPays = choisirPays();
        int idDiscipline = choisirDiscipline();
        athleteService.ajouter(new Athlete(nom, prenom, sexe, dateNaissance, idPays, idDiscipline));
        System.out.println("Athlete ajoute.");
    }

    private void modifierAthlete() throws SQLException {
        int id = lireEntier("Id athlete a modifier : ");
        String nom = lireTexte("Nouveau nom : ");
        String prenom = lireTexte("Nouveau prenom : ");
        String sexe = lireTexte("Nouveau sexe (M/F) : ");
        LocalDate dateNaissance = lireDate("Nouvelle date de naissance");
        int idPays = choisirPays();
        int idDiscipline = choisirDiscipline();
        athleteService.modifier(new Athlete(id, nom, prenom, sexe, dateNaissance, idPays, idDiscipline));
        System.out.println("Athlete modifie.");
    }

    private void supprimerAthlete() throws SQLException {
        int id = lireEntier("Id athlete a supprimer : ");
        athleteService.supprimer(id);
        System.out.println("Athlete supprime.");
    }

    private void rechercherAthlete() throws SQLException {
        String motCle = lireTexte("Mot cle (nom ou prenom) : ");
        afficherListe(athleteService.rechercher(motCle));
    }

    /**
     * Affiche la liste des pays disponibles puis demande son id. Evite de saisir
     * un id de pays inexistant lors de la creation/modification d'un athlete.
     */
    private int choisirPays() throws SQLException {
        System.out.println("Pays disponibles :");
        afficherListe(paysService.listerTous());
        return lireEntier("Id du pays : ");
    }

    private int choisirDiscipline() throws SQLException {
        System.out.println("Disciplines disponibles :");
        afficherListe(disciplineService.listerTous());
        return lireEntier("Id de la discipline : ");
    }

    // ---------------------------------------------------------------------
    // Module 5 : Gestion des competitions
    // ---------------------------------------------------------------------

    private void menuCompetitions() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des competitions --");
            System.out.println("1. Ajouter competition");
            System.out.println("2. Modifier competition");
            System.out.println("3. Supprimer competition");
            System.out.println("4. Rechercher competition");
            System.out.println("5. Afficher competitions");
            System.out.println("6. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: ajouterCompetition(); break;
                    case 2: modifierCompetition(); break;
                    case 3: supprimerCompetition(); break;
                    case 4: rechercherCompetition(); break;
                    case 5: afficherListe(competitionService.listerTous()); break;
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void ajouterCompetition() throws SQLException {
        String nom = lireTexte("Nom de la competition : ");
        LocalDate date = lireDate("Date de la competition");
        String lieu = lireTexte("Lieu (Dakar / Diamniadio / Saly) : ");
        int idDiscipline = choisirDiscipline();
        competitionService.ajouter(new Competition(nom, date, lieu, idDiscipline));
        System.out.println("Competition ajoutee.");
    }

    private void modifierCompetition() throws SQLException {
        int id = lireEntier("Id competition a modifier : ");
        String nom = lireTexte("Nouveau nom : ");
        LocalDate date = lireDate("Nouvelle date");
        String lieu = lireTexte("Nouveau lieu (Dakar / Diamniadio / Saly) : ");
        int idDiscipline = choisirDiscipline();
        competitionService.modifier(new Competition(id, nom, date, lieu, idDiscipline));
        System.out.println("Competition modifiee.");
    }

    private void supprimerCompetition() throws SQLException {
        int id = lireEntier("Id competition a supprimer : ");
        competitionService.supprimer(id);
        System.out.println("Competition supprimee.");
    }

    private void rechercherCompetition() throws SQLException {
        String motCle = lireTexte("Mot cle (nom ou lieu) : ");
        afficherListe(competitionService.rechercher(motCle));
    }

    // ---------------------------------------------------------------------
    // Module 6 : Gestion des resultats + tableau des medailles (module 11)
    // ---------------------------------------------------------------------

    private void menuResultats() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n-- Gestion des resultats --");
            System.out.println("1. Enregistrer resultat");
            System.out.println("2. Modifier resultat");
            System.out.println("3. Supprimer resultat");
            System.out.println("4. Classement competition");
            System.out.println("5. Afficher resultats");
            System.out.println("6. Tableau des medailles");
            System.out.println("7. Retour");
            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1: enregistrerResultat(); break;
                    case 2: modifierResultat(); break;
                    case 3: supprimerResultat(); break;
                    case 4: afficherClassement(); break;
                    case 5: afficherListe(resultatService.listerTous()); break;
                    case 6: afficherTableauDesMedailles(); break;
                    case 7: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de donnees : " + e.getMessage());
            }
        }
    }

    private void enregistrerResultat() throws SQLException {
        int idAthlete = lireEntier("Id athlete : ");
        int idCompetition = lireEntier("Id competition : ");
        double score = lireDouble("Score : ");
        int rang = lireEntier("Rang : ");
        resultatService.enregistrer(new Resultat(idAthlete, idCompetition, score, rang));
        System.out.println("Resultat enregistre.");
    }

    private void modifierResultat() throws SQLException {
        int id = lireEntier("Id resultat a modifier : ");
        int idAthlete = lireEntier("Nouvel id athlete : ");
        int idCompetition = lireEntier("Nouvel id competition : ");
        double score = lireDouble("Nouveau score : ");
        int rang = lireEntier("Nouveau rang : ");
        resultatService.modifier(new Resultat(id, idAthlete, idCompetition, score, rang));
        System.out.println("Resultat modifie.");
    }

    private void supprimerResultat() throws SQLException {
        int id = lireEntier("Id resultat a supprimer : ");
        resultatService.supprimer(id);
        System.out.println("Resultat supprime.");
    }

    private void afficherClassement() throws SQLException {
        int idCompetition = lireEntier("Id competition : ");
        List<Resultat> classement = resultatService.classement(idCompetition);
        afficherListe(classement);
    }

    private void afficherTableauDesMedailles() throws SQLException {
        List<MedailleParPays> tableau = resultatService.tableauDesMedailles();
        System.out.println("\nPays                 Or  Argent Bronze Total");
        afficherListe(tableau);
    }

    // ---------------------------------------------------------------------
    // Module 7 : Statistiques
    // ---------------------------------------------------------------------

    private void afficherStatistiques() {
        try {
            System.out.println("\n-- Statistiques --");
            System.out.println("Nombre de pays          : " + statistiqueService.nombrePays());
            System.out.println("Nombre d'athletes       : " + statistiqueService.nombreAthletes());
            System.out.println("Nombre de disciplines   : " + statistiqueService.nombreDisciplines());
            System.out.println("Nombre de competitions  : " + statistiqueService.nombreCompetitions());
            System.out.println("Nombre de resultats     : " + statistiqueService.nombreResultats());
        } catch (SQLException e) {
            System.out.println("Erreur base de donnees : " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    // Methodes utilitaires (saisie clavier + affichage)
    // ---------------------------------------------------------------------

    private String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre entier valide.");
            }
        }
    }

    private double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre valide.");
            }
        }
    }

    private LocalDate lireDate(String message) {
        while (true) {
            System.out.print(message + " (aaaa-mm-jj) : ");
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide, exemple attendu : 2026-05-20");
            }
        }
    }

    /**
     * Affiche chaque element de la liste (un par ligne) via son toString(),
     * ou un message si la liste est vide.
     */
    private <T> void afficherListe(List<T> elements) {
        if (elements.isEmpty()) {
            System.out.println("Aucun resultat.");
            return;
        }
        elements.forEach(System.out::println);
    }
}

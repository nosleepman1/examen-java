package service;

import model.MedailleParPays;
import model.Resultat;

import java.sql.SQLException;
import java.util.List;

public interface IResultatService {

    void enregistrer(Resultat r) throws SQLException;

    void modifier(Resultat r) throws SQLException;

    void supprimer(int idResultat) throws SQLException;

    List<Resultat> listerTous() throws SQLException;

    /**
     * Résultats d'une compétition triés par rang croissant.
     */
    List<Resultat> classement(int idCompetition) throws SQLException;

    /**
     * Tableau des médailles (Or/Argent/Bronze/Total) trié par nombre de médailles décroissant.
     */
    List<MedailleParPays> tableauDesMedailles() throws SQLException;
}

package service;

import model.Athlete;

import java.sql.SQLException;
import java.util.List;

public interface IAthleteService {

    void ajouter(Athlete a) throws SQLException;

    void modifier(Athlete a) throws SQLException;

    void supprimer(int idAthlete) throws SQLException;

    Athlete rechercherParId(int idAthlete) throws SQLException;

    List<Athlete> rechercher(String motCle) throws SQLException;

    List<Athlete> rechercherParPays(String nomPays) throws SQLException;

    List<Athlete> listerTous() throws SQLException;
}

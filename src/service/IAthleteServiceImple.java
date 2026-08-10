package service;

import dao.AthleteDAO;
import model.Athlete;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class IAthleteServiceImple implements IAthleteService {

    private final AthleteDAO athleteDAO = new AthleteDAO();

    @Override
    public void ajouter(Athlete a) throws SQLException {
        athleteDAO.ajouter(a);
    }

    @Override
    public void modifier(Athlete a) throws SQLException {
        athleteDAO.modifier(a);
    }

    @Override
    public void supprimer(int idAthlete) throws SQLException {
        athleteDAO.supprimer(idAthlete);
    }

    @Override
    public Athlete rechercherParId(int idAthlete) throws SQLException {
        return athleteDAO.rechercherParId(idAthlete);
    }

    @Override
    public List<Athlete> rechercher(String motCle) throws SQLException {
        return athleteDAO.rechercher(motCle);
    }

    @Override
    public List<Athlete> rechercherParPays(String nomPays) throws SQLException {
        // Filtrage en mémoire avec Stream plutôt qu'une requête SQL dédiée : la liste des
        // athlètes est déjà chargée avec les noms de pays via la jointure du DAO.
        return athleteDAO.listerTous().stream()
                .filter(a -> a.getNomPays().equalsIgnoreCase(nomPays))
                .collect(Collectors.toList());
    }

    @Override
    public List<Athlete> listerTous() throws SQLException {
        return athleteDAO.listerTous();
    }
}

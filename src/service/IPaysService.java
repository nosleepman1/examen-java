package service;

import model.Pays;

import java.sql.SQLException;
import java.util.List;

public interface IPaysService {

    void ajouter(Pays p) throws SQLException;

    void modifier(Pays p) throws SQLException;

    void supprimer(int idPays) throws SQLException;

    Pays rechercherParId(int idPays) throws SQLException;

    List<Pays> rechercher(String motCle) throws SQLException;

    List<Pays> listerTous() throws SQLException;
}

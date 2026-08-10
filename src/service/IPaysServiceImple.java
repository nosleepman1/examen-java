package service;

import dao.PaysDAO;
import model.Pays;

import java.sql.SQLException;
import java.util.List;

public class IPaysServiceImple implements IPaysService {

    private final PaysDAO paysDAO = new PaysDAO();

    @Override
    public void ajouter(Pays p) throws SQLException {
        paysDAO.ajouter(p);
    }

    @Override
    public void modifier(Pays p) throws SQLException {
        paysDAO.modifier(p);
    }

    @Override
    public void supprimer(int idPays) throws SQLException {
        paysDAO.supprimer(idPays);
    }

    @Override
    public Pays rechercherParId(int idPays) throws SQLException {
        return paysDAO.rechercherParId(idPays);
    }

    @Override
    public List<Pays> rechercher(String motCle) throws SQLException {
        return paysDAO.rechercher(motCle);
    }

    @Override
    public List<Pays> listerTous() throws SQLException {
        return paysDAO.listerTous();
    }
}

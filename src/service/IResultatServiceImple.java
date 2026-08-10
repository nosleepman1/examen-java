package service;

import dao.ResultatDAO;
import model.MedailleParPays;
import model.Resultat;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IResultatServiceImple implements IResultatService {

    private final ResultatDAO resultatDAO = new ResultatDAO();

    @Override
    public void enregistrer(Resultat r) throws SQLException {
        resultatDAO.ajouter(r);
    }

    @Override
    public void modifier(Resultat r) throws SQLException {
        resultatDAO.modifier(r);
    }

    @Override
    public void supprimer(int idResultat) throws SQLException {
        resultatDAO.supprimer(idResultat);
    }

    @Override
    public List<Resultat> listerTous() throws SQLException {
        return resultatDAO.listerTous();
    }

    @Override
    public List<Resultat> classement(int idCompetition) throws SQLException {
        return resultatDAO.listerParCompetition(idCompetition).stream()
                .sorted(Comparator.comparingInt(Resultat::getRang))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedailleParPays> tableauDesMedailles() throws SQLException {
        // On ne garde que les podiums (rang 1 à 3), puis on regroupe par pays et par rang.
        Map<String, Map<Integer, Long>> comptageParPaysEtRang = resultatDAO.listerTous().stream()
                .filter(r -> r.getRang() >= 1 && r.getRang() <= 3)
                .collect(Collectors.groupingBy(
                        Resultat::getNomPaysAthlete,
                        Collectors.groupingBy(Resultat::getRang, Collectors.counting())));

        return comptageParPaysEtRang.entrySet().stream()
                .map(entree -> creerLigneMedaille(entree.getKey(), entree.getValue()))
                .sorted(Comparator.comparingLong(MedailleParPays::getTotal).reversed()
                        .thenComparing(Comparator.comparingLong(MedailleParPays::getOr).reversed()))
                .collect(Collectors.toList());
    }

    private MedailleParPays creerLigneMedaille(String nomPays, Map<Integer, Long> comptageParRang) {
        long or = comptageParRang.getOrDefault(1, 0L);
        long argent = comptageParRang.getOrDefault(2, 0L);
        long bronze = comptageParRang.getOrDefault(3, 0L);
        return new MedailleParPays(nomPays, or, argent, bronze);
    }
}

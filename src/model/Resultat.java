package model;

public class Resultat {

    private int idResultat;
    private int idAthlete;
    private int idCompetition;
    private double score;
    private int rang;

    // Champs pratiques remplis par les jointures du DAO, pour l'affichage
    private String nomAthlete;
    private String nomPaysAthlete;
    private String nomCompetition;

    public Resultat() {
    }

    public Resultat(int idAthlete, int idCompetition, double score, int rang) {
        this.idAthlete = idAthlete;
        this.idCompetition = idCompetition;
        this.score = score;
        this.rang = rang;
    }

    public Resultat(int idResultat, int idAthlete, int idCompetition, double score, int rang) {
        this.idResultat = idResultat;
        this.idAthlete = idAthlete;
        this.idCompetition = idCompetition;
        this.score = score;
        this.rang = rang;
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public int getIdAthlete() {
        return idAthlete;
    }

    public void setIdAthlete(int idAthlete) {
        this.idAthlete = idAthlete;
    }

    public int getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getNomAthlete() {
        return nomAthlete;
    }

    public void setNomAthlete(String nomAthlete) {
        this.nomAthlete = nomAthlete;
    }

    public String getNomPaysAthlete() {
        return nomPaysAthlete;
    }

    public void setNomPaysAthlete(String nomPaysAthlete) {
        this.nomPaysAthlete = nomPaysAthlete;
    }

    public String getNomCompetition() {
        return nomCompetition;
    }

    public void setNomCompetition(String nomCompetition) {
        this.nomCompetition = nomCompetition;
    }

    /**
     * Traduit le rang en type de médaille. Retourne "-" si hors podium.
     */
    public String getMedaille() {
        switch (rang) {
            case 1: return "OR";
            case 2: return "ARGENT";
            case 3: return "BRONZE";
            default: return "-";
        }
    }

    @Override
    public String toString() {
        return idResultat + " | " + nomAthlete + " (" + nomPaysAthlete + ") | " + nomCompetition
                + " | score=" + score + " | rang=" + rang + " | " + getMedaille();
    }
}

package model;

/**
 * Ligne calculée du tableau des médailles (module 11 du cahier des charges).
 * N'est pas stockée en base : reconstruite à chaque appel à partir de la table resultat.
 */
public class MedailleParPays {

    private final String nomPays;
    private final long or;
    private final long argent;
    private final long bronze;

    public MedailleParPays(String nomPays, long or, long argent, long bronze) {
        this.nomPays = nomPays;
        this.or = or;
        this.argent = argent;
        this.bronze = bronze;
    }

    public String getNomPays() {
        return nomPays;
    }

    public long getOr() {
        return or;
    }

    public long getArgent() {
        return argent;
    }

    public long getBronze() {
        return bronze;
    }

    public long getTotal() {
        return or + argent + bronze;
    }

    @Override
    public String toString() {
        return String.format("%-20s Or:%-3d Argent:%-3d Bronze:%-3d Total:%-3d",
                nomPays, or, argent, bronze, getTotal());
    }
}

package g54314.sortthread.model;
/**
 La classe Result représente les résultats d'un tri effectué.
 Elle stocke des informations telles que le type de tri, la taille des données triées,
 le nombre d'échanges effectués et la durée du tri.
 */
public class Result {
    private final SortType nameSort;
    private final long size;
    private final long swap;
    private final long duration;

    /**
     Constructeur de la classe Result.
     @param nameSort Le type de tri effectué (une instance de l'énumération SortType).
     @param size La taille des données triées.
     @param swap Le nombre d'échanges effectués lors du tri.
     @param duration La durée du tri en millisecondes.
     */
    public Result(SortType nameSort, long size, long swap, long duration) {
        this.nameSort = nameSort;
        this.size = size;
        this.swap = swap;
        this.duration = duration;
    }

    public SortType getNameSort() {
        return nameSort;
    }

    public long getSize() {
        return size;
    }

    public long getSwap() {
        return swap;
    }

    public long getDuration() {
        return duration;
    }
}

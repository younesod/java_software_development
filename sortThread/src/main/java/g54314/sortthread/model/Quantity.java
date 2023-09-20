package g54314.sortthread.model;

/**
 L'énumération Quantity représente les différentes quantités de données à utiliser dans l'application de tri.
 Chaque quantité est associée à un nombre de répétitions et à une description.
 */
public enum Quantity {
    VERY_EASY(100, "Très facile : 100 (par 10)"),
    EASY(1_000, "Facile : 1 000 (par 100)"),
    NORMAL(10_000, "Normal : 10 000 (par 1 000)"),
    HARD(100_000, "Difficile : 100 000 (par 10 000)"),
    ULTRA_HARD(1_000_000, "Très difficile : 1 000 000 (par 100 000)");

    private final int nbRepeat;
    private final String type;

    /**
     Constructeur de l'énumération Quantity.
     @param nbRepeat Le nombre de répétitions associé à la quantité.
     @param type La description de la quantité.
     */
    Quantity(int nbRepeat, String type) {
        this.nbRepeat = nbRepeat;
        this.type = type;
    }

    /**
     Méthode statique pour obtenir une instance de Quantity à partir de sa description.
     @param type La description de la quantité.
     @return L'instance de Quantity correspondante, ou null si aucune correspondance n'est trouvée.
     */
    public static Quantity fromString(String type){
        for(var qt: Quantity.values()){
            if(qt.toString().equalsIgnoreCase(type)){
                return qt;
            }
        }
        return null;
    }

    /**
     Getter pour le nombre de répétitions associé à la quantité.
     @return Le nombre de répétitions.
     */
    public int getNbRepeat() {
        return nbRepeat;
    }

    /**
     Redéfinition de la méthode toString pour obtenir la description de la quantité.
     @return La description de la quantité.
     */
    @Override
    public String toString() {
        return type;
    }
}

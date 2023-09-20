package g54314.sortthread.model;

import java.util.Random;

/**
 * Cette classe représente un travail (job) qui produit des tableaux d'entiers de taille variable.
 */
public class Job {
    private static Random random= new Random();
    private int max;
    private int current;

    /**
     * Constructeur de la classe Job.
     *
     * @param max la taille maximale des tableaux à générer
     */
    public Job(int max) {
        this.max = max;
        this.current = 0;
    }

    /**
     * Génère un tableau d'entiers de taille variable.
     *
     * @return le tableau d'entiers généré, ou null si tous les tableaux ont déjà été produits
     */
    public synchronized int[] makeJob(){
        if (current > max) {
            return null;
        }
        int[] tab = new int[current];
        for (int i = 0; i < current; i++) {
            tab[i] = random.nextInt();
        }
        current += max / 10;
        return tab;
    }
}

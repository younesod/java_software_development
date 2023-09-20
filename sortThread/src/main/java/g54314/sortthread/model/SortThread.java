package g54314.sortthread.model;

import g54314.sortthread.model.sort.Bubble;
import g54314.sortthread.model.sort.Merge;
import g54314.sortthread.model.sort.Sort;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
/**
 * La classe SortThread représente un gestionnaire de threads de tri parallèle.
 * Elle permet d'exécuter des tris parallèles en utilisant plusieurs threads.
 * La classe implémente l'interface PropertyChangeListener pour écouter les changements de propriété.
 */
public class SortThread implements PropertyChangeListener {
    private final PropertyChangeSupport pcs= new PropertyChangeSupport(this);

    /**
     * Ajoute un écouteur de changement de propriété à l'objet SortThread.
     *
     * @param listener l'écouteur de changement de propriété à ajouter
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Exécute des tris parallèles en utilisant plusieurs threads.
     *
     * @param threadSpinner le nombre de threads à utiliser
     * @param nbRepeat      le nombre de répétitions du travail de tri
     * @param sortValue     la valeur de tri spécifiée
     */
    public void runSort(int threadSpinner,int nbRepeat,String sortValue){
        var sortType= Objects.requireNonNull(SortType.fromString(sortValue));
        var job = new Job(nbRepeat);

        Sort sort;
        switch(sortType){
            case MERGE -> sort= new Merge();
            case BUBBLE -> sort = new Bubble();
            default -> sort= new Merge();
        };
        for(int i=0;i<threadSpinner;i++){
            MyThread thread= new MyThread(job,sort);
            thread.addPropertyChangeListener(this);
            thread.start();
        }
    }

    /**
     * Méthode de rappel appelée lorsqu'un événement de changement de propriété est reçu.
     * Transfère l'événement de changement de propriété en utilisant PropertyChangeSupport.
     *
     * @param evt l'événement de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        pcs.firePropertyChange("notUsed",evt.getOldValue(),evt.getNewValue());
    }
}

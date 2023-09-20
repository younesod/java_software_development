package g54314.sortthread.model;

import g54314.sortthread.model.Job;
import g54314.sortthread.model.sort.Sort;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MILLIS;

public class MyThread extends Thread{
    private final Job job;
    private final Sort sortType;
    private final PropertyChangeSupport pcs= new PropertyChangeSupport(this);

    /**
     * Construit une nouvelle instance de `MyThread` avec l'objet `Job` spécifié et le type de tri `Sort`.
     *
     * @param job L'objet `Job` qui génère les tableaux à trier.
     * @param sortType L'objet `Sort` représentant le type de tri à effectuer.
     */
    public MyThread(Job job,Sort sortType){
        this.job=job;
        this.sortType=sortType;
    }

    /**
     * Ajoute un écouteur de changement de propriété à l'objet `MyThread`.
     *
     * @param listener L'écouteur de changement de propriété à ajouter.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Exécute le thread en effectuant le tri sur les tableaux générés par l'objet `Job`.
     * Remplace la méthode `run()` de la classe `Thread`.
     */
    @Override
    public void run() {
        long duration;
        int []tabToSort= job.makeJob();
        while(tabToSort!=null){
            LocalTime start= LocalTime.now();
            duration=sortType.sort(tabToSort);
            LocalTime end= LocalTime.now();

            pcs.firePropertyChange("notUsed",null,new long[]{
                tabToSort.length,duration,(int) MILLIS.between(start, end)
            });
            tabToSort= job.makeJob();
        }
    }
}

package atl.g54314.presenter;

import atl.g54314.model.Model;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.exception.RepositoryException;
import atl.g54314.model.utilities.MessageType;
import atl.g54314.model.utilities.observer.Observable;
import atl.g54314.model.utilities.observer.Observer;
import atl.g54314.view.View;

import java.io.IOException;
import java.util.List;

public class Presenter implements Observer {
    private final Model model;
    private final View view;

    /**
     * Creates a new instance of the Presenter class.
     *
     * @param model The model instance.
     * @param view  The view instance.
     */
    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object arg) {
        view.setPathStations(model.getPathStations());
        view.setNbStation(model.getPathStations().size());
        view.setStatusSearch(model.isDoneSearch());
        view.setChangeStation(model.getChangeStation());
        view.setMsgFavorite(model.getMsgFavorite());
        view.setMenuFavorite(model.getAllFavorites());
    }

    /**
     * Initializes the presenter and the view.
     */
    public void initialize() {
        view.initialize(model.getAllStations(), model.getAllFavorites());
    }

    /**
     * Performs a search for an itinerary between two stations.
     *
     * @param source      The source station.
     * @param destination The destination station.
     * @throws IllegalArgumentException If it is impossible to search for the given
     *                                  data.
     */
    public void search(StationDto source, StationDto destination) {
        model.setMsgFavorite(null);
        try {
            model.search(source, destination);
        } catch (RepositoryException e) {
            throw new IllegalArgumentException("Impossible to search theses datas");
        }
    }

    /**
     * Shows a favorite stage with the given source, destination, and name.
     *
     * @param source      The source station.
     * @param destination The destination station.
     * @param name        The name of the favorite stage.
     */
    public void showFavoriteStage(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        view.showFavorite(source, destination, name);
    }

    /**
     * Deletes a favorite stage with the given source, destination, and name.
     *
     * @param source      The source station.
     * @param destination The destination station.
     * @param name        The name of the favorite stage.
     */
    public void deleteFavorite(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        try {
            model.deleteFavorite(source, destination, name);
        } catch (RepositoryException e) {
            model.setMsgFavorite(MessageType.REPOSITORY_ERROR);
            model.notifyObservers();
            return;
        }
        if (model.getMsgFavorite() == null) {
            view.closeFavorite();
        }
    }

    /**
     * Adds a favorite stage with the given source, destination, and name.
     *
     * @param source      The source station.
     * @param destination The destination station.
     * @param name        The name of the favorite stage.
     */
    public void addFavorite(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        try {
            model.addFavorite(source, destination, name);
        } catch (RepositoryException e) {
            model.setMsgFavorite(MessageType.REPOSITORY_ERROR);
            model.notifyObservers();
            return;
        }
        if (model.getMsgFavorite() == null) {
            view.closeFavorite();
        }
    }

    /**
     * Gets the list of change stations.
     *
     * @return The list of change stations.
     */
    public List<String> getChangeStations() {
        return model.getChangeStation();
    }

}

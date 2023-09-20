package atl.g54314.view;

import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.utilities.MessageType;
import atl.g54314.presenter.Presenter;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class View {
    private FxmlController fxmlController;
    private FxmlControllerFav favFxmlController;
    private Stage favStage;

    /**
     * Constructs a new instance of the View class.
     *
     * @param stage The primary stage.
     * @throws IOException If an I/O error occurs during loading of the FXML files.
     */
    public View(Stage stage) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getClassLoader().getResource("atl/g54314/stib.fxml"));
        loader.setController(new FxmlController());
        Pane root= loader.load();

        fxmlController=loader.getController();
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("atl/g54314/logo.png"))));
        stage.setTitle("HE2B ESI - STIB RIDE");
        stage.setScene(scene);
        stage.show();

        makeFavoriteStage(stage);
    }

    /**
     * Creates the favorite stage.
     *
     * @param stage The parent stage.
     * @throws IOException If an I/O error occurs during loading of the FXML file.
     */
    public void makeFavoriteStage(Stage stage) throws IOException {
        favStage= new Stage();
        favStage.initModality(Modality.APPLICATION_MODAL);
        favStage.initOwner(stage);

        FXMLLoader loader= new FXMLLoader(getClass().getClassLoader().getResource("atl/g54314/favorite.fxml"));
        loader.setController(new FxmlControllerFav());
        Pane rootFav =loader.load();
        Scene sceneFav =new Scene(rootFav);

        favFxmlController=loader.getController();
        favStage.setResizable(false);
        favStage.setTitle("HE2B ESI - Gestion favoris");
        favStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("atl/g54314/logo.png"))));
        favStage.setScene(sceneFav);
    }

    /**
     * Adds a presenter to the view.
     *
     * @param presenter The presenter.
     */
    public void addPresenter(Presenter presenter){
        fxmlController.setPresenter(presenter);
        favFxmlController.setPresenter(presenter);
    }

    /**
     * Initializes the view with the provided sets of all stations and all favorites.
     *
     * @param allStations   The set of all stations.
     * @param allFavorites  The set of all favorites.
     */
    public void initialize(ObservableSet<StationDto> allStations, ObservableSet<FavoriteDto> allFavorites) {
        fxmlController.initialize(allStations, allFavorites);
        favFxmlController.initialize(allStations);
    }

    /**
     * Sets the status message for the search.
     *
     * @param isEnd Indicates if the search has ended or encountered an error.
     */
    public void setStatusSearch(boolean isEnd) {
        fxmlController.getStatusSearch().setText(isEnd ? "Recherche terminée" : "Une erreur est survenue, merci de réessayer.");
    }

    /**
     * Sets the number of stations.
     *
     * @param nb The number of stations.
     */
    public void setNbStation(int nb) {
        fxmlController.getNbStation().setText("Nombre de station : " + nb);
    }

    /**
     * Sets the list of stations in the path.
     *
     * @param newList The list of stations in the path.
     */
    public void setPathStations(ObservableList<StationDto> newList) {
        fxmlController.setPathStations(newList);
    }

    /**
     * Sets the list of stations where a change is required.
     *
     * @param listStation The list of stations where a change is required.
     */
    public void setChangeStation(List<String> listStation) {
        fxmlController.setChangeStation(listStation);
    }

    /**
     * Shows the favorite stage with the provided source, destination, and name.
     *
     * @param source      The source station.
     * @param destination The destination station.
     * @param name        The name of the favorite.
     */
    public void showFavorite(StationDto source,StationDto destination,String name){
        favStage.show();
        favFxmlController.setComboBox(source,destination);
        favFxmlController.setName(name);
    }

    /**
     * Closes the favorite stage.
     */
    public void closeFavorite(){
        favStage.close();
    }

    /**
     * Sets the message for the favorite.
     *
     * @param msg The message type.
     */
    public void setMsgFavorite(MessageType msg) {
        favFxmlController.setMsgFavorite(msg);
    }

    /**
     * Sets the menu for the favorites.
     *
     * @param allFavorites The set of all favorites.
     */
    public void setMenuFavorite(ObservableSet<FavoriteDto> allFavorites) {
        fxmlController.setMenuFavorite(allFavorites);
    }

}

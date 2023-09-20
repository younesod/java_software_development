package atl.g54314.view;

import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.utilities.observer.Observable;
import atl.g54314.presenter.Presenter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FxmlController {
    @FXML
    private TableView<StationDto> table;

    @FXML
    private TableColumn<StationDto, String> lineCol;

    @FXML
    private TableColumn<StationDto, String> stationCol;

    @FXML
    private Label nbStation;

    @FXML
    private Label statusSearch;

    @FXML
    private Label chgLine;

    @FXML
    private Button search;

    @FXML
    private MenuItem quit;
    @FXML
    private MenuItem help;

    @FXML
    private SearchableComboBox<StationDto> searchOrigin;

    @FXML
    private SearchableComboBox<StationDto> searchDestination;

    @FXML
    private Button favorite;

    @FXML
    private Menu menuFavorite;

    private ObservableList<StationDto> pathStations;
    private List<String> changeStations;
    private Presenter presenter;

    /**
     * Performs a search operation based on the selected source and destination
     * stations.
     *
     * @param event The action event.
     */
    @FXML
    private void search(ActionEvent event) {
        presenter.search(searchOrigin.getValue(), searchDestination.getValue());
    }

    /**
     * Shows a favorite stage based on the selected source and destination stations.
     *
     * @param event The action event.
     */
    @FXML
    private void favorite(ActionEvent event) {
        presenter.showFavoriteStage(searchOrigin.getValue(), searchDestination.getValue(), "");
    }

    /**
     * Displays the help information in an alert dialog.
     *
     * @param event The action event.
     */
    @FXML
    private void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations complémentaires");
        var sb = new StringBuilder("Création : 54314 OY 2022-2023.\n\n");
        sb.append("Diverses choix s'offrent à vous :\n");
        sb.append("- Vous pouvez sélectionner et rechercher votre chemin.\n");
        sb.append("- Appuyez sur rechercher lorsque tout est prêt!\n\n");
        sb.append("- Vous pouvez ajouter votre chemin en favori.\n");
        sb.append("- Vous pouvez également consulter vos favoris.\n\n");
        sb.append("- Bonne route à vous!\n\n");
        alert.setContentText(sb.toString());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Initializes the controller and sets up the initial state of the view.
     *
     * @param allStations   The set of all stations.
     * @param allFavorites  The set of all favorites.
     */
    public void initialize(ObservableSet<StationDto> allStations, ObservableSet<FavoriteDto> allFavorites) {
        pathStations = FXCollections.observableArrayList();
        stationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lineCol.setCellValueFactory(new PropertyValueFactory<>("linesToString"));
        table.setItems(FXCollections.observableArrayList());
        stationCol.setCellFactory(column -> {
            return new TableCell<StationDto, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item);
                    StationDto station = getTableRow().getItem();
                    for (String stationName : presenter.getChangeStations()) {
                        if (station != null && station.getName().equals(stationName)) {
                            setStyle("-fx-background-color: linear-gradient(to right, #32a4a8, #4632a8);");
                            return;
                        }
                    }

                }
            };
        });
        searchOrigin.setItems(FXCollections.observableArrayList(allStations));
        searchDestination.setItems(FXCollections.observableArrayList(allStations));
        searchOrigin.getSelectionModel().selectFirst();
        searchDestination.getSelectionModel().selectLast();

        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Gets the status search label.
     *
     * @return The status search label.
     */
    public Label getStatusSearch() {
        return statusSearch;
    }

    /**
     * Sets the presenter for the controller.
     *
     * @param presenter The presenter.
     */
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Gets the number of stations label.
     *
     * @return The number of stations label.
     */
    public Label getNbStation() {
        return nbStation;
    }

    /**
     * Sets the path stations in the table view.
     *
     * @param pathStations The path stations.
     */
    public void setPathStations(ObservableList<StationDto> pathStations) {
        this.pathStations = pathStations;
        table.setItems(pathStations);
    }

    /**
     * Sets the change stations for displaying change indicators.
     *
     * @param changeStation The list of change stations.
     */
    public void setChangeStation(List<String> changeStation) {
        StringBuilder changements = new StringBuilder("Changement de ligne : ");
        if (changeStation.isEmpty()) {
            changements.append("aucun");
            chgLine.setText(changements.toString());
            return;
        }
        for (String station : changeStation) {
            changements.append(station).append(", ");
        }
        chgLine.setText(changements.substring(0, changements.length() - 2));
    }

    /**
     * Sets the menu items for displaying favorite routes.
     *
     * @param allFavorites The set of all favorites.
     */
    public void setMenuFavorite(ObservableSet<FavoriteDto> allFavorites) {
        menuFavorite.getItems().clear();
        if (allFavorites.isEmpty()) {
            MenuItem menuItem = new MenuItem("Aucun trajet pour l'instant");
            menuFavorite.getItems().add(menuItem);
            menuItem.setDisable(true);
        } else {
            allFavorites.forEach(v -> {
                var item = new MenuItem(v.getKey());
                item.setOnAction(e -> presenter.showFavoriteStage(v.getSource(), v.getDestination(), v.getKey()));
                menuFavorite.getItems().add(item);
            });
        }
    }
}

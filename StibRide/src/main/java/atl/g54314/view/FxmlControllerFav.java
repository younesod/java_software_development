package atl.g54314.view;

import atl.g54314.model.dto.StationDto;
import atl.g54314.model.utilities.MessageType;
import atl.g54314.presenter.Presenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;



public class FxmlControllerFav {


    @FXML
    private SearchableComboBox<StationDto> searchOrigin;

    @FXML
    private SearchableComboBox<StationDto> searchDestination;
    @FXML
    private TextField name;
    @FXML
    private Text msg;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button search;
    private Presenter presenter;

    /**
     * Adds a favorite route based on the selected source and destination stations.
     *
     * @param event The action event.
     */
    @FXML
    private void add(ActionEvent event){
        presenter.addFavorite(searchOrigin.getValue(),searchDestination.getValue(),name.getText());
    }

    /**
     * Performs a search operation based on the selected source and destination stations.
     *
     * @param event The action event.
     */
    @FXML
    private void search(ActionEvent event){
        presenter.search(searchOrigin.getValue(),searchDestination.getValue());
    }

    /**
     * Deletes a favorite route based on the selected source and destination stations.
     *
     * @param event The action event.
     */
    @FXML
    private void delete(ActionEvent event){
        presenter.deleteFavorite(searchOrigin.getValue(),searchDestination.getValue(),name.getText());
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
     * Sets the values of the source and destination combo boxes.
     *
     * @param source      The source station.
     * @param destination The destination station.
     */
    public void setComboBox(StationDto source, StationDto destination) {
        searchOrigin.getSelectionModel().select(source);
        searchDestination.getSelectionModel().select(destination);
    }

    /**
     * Initializes the controller and sets up the initial state of the view.
     *
     * @param allStations The set of all stations.
     */
    public void initialize(ObservableSet<StationDto> allStations) {
        searchOrigin.setItems(FXCollections.observableArrayList(allStations));
        searchDestination.setItems(FXCollections.observableArrayList(allStations));
        searchOrigin.getSelectionModel().selectFirst();
        searchDestination.getSelectionModel().selectLast();
    }

    /**
     * Sets the name text field.
     *
     * @param name The name value.
     */
    public void setName(String name) {
        this.name.setText(name);
    }

    /**
     * Sets the message text based on the given message type.
     *
     * @param type The message type.
     */
    public void setMsgFavorite(MessageType type){
        String fmsg;
        if(type==null){
            fmsg="";
        }else{
            switch(type){
                case DELETE_FAIL ->fmsg="Impossible de supprimer ce trajet";
                case NO_UPDATE_CHANGE -> fmsg="Pas de changement à mettre à jour";
                case INVALID_DATA -> fmsg="Données invalides, veuillez réessayer";
                case REPOSITORY_ERROR -> fmsg= "Erreur survenu, vérifier votre saisie";
                default -> fmsg="";
            }
        }
        this.msg.setText(fmsg);
    }
}

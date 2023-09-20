package g54314.sortthread.view;

import g54314.sortthread.model.Quantity;
import g54314.sortthread.model.Result;
import g54314.sortthread.model.SortThread;
import g54314.sortthread.model.SortType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * La classe FxmlController est le contrôleur associé à l'interface utilisateur créée à l'aide de FXML.
 * Elle gère les interactions entre les éléments de l'interface utilisateur et la logique métier du programme.
 */
public class FxmlController implements PropertyChangeListener {
    private final ObservableList<Result> tableList = FXCollections.observableArrayList();
    private final XYChart.Series<Number, Number> merge = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> bubble = new XYChart.Series<>();
    private final SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

    @FXML
    private TableView<Result> table;
    @FXML
    private TableColumn<Result, String> nameCol;
    @FXML
    private TableColumn<Result, Integer> sizeCol;
    @FXML
    private TableColumn<Result, Integer> swapCol;
    @FXML
    private TableColumn<Result, Integer> durationCol;
    @FXML
    private LineChart<Number, Number> chart;
    @FXML
    private Spinner<Integer> threadSpinner;
    @FXML
    private ChoiceBox<String> sortChoice;
    @FXML
    private ChoiceBox<String> configurationChoice;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private Button start;

    private LocalTime startTime;
    private double currentProgressBar;

    /**
     * Méthode appelée lorsqu'un événement de changement de propriété est reçu.
     * Elle met à jour les éléments de l'interface utilisateur en fonction des changements de propriété reçus.
     *
     * @param evt L'événement de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (currentProgressBar == 0) {
                start.setDisable(true);
            }
            currentProgressBar += .1;
            progressBar.setProgress(currentProgressBar);

            if (currentProgressBar >= 0.99) {
                currentProgressBar = 1;
                progressBar.setProgress(currentProgressBar);
                start.setDisable(false);
            }

            var sortType = Objects.requireNonNull(SortType.fromString(sortChoice.getValue()));
            long[] givenValues = (long[]) evt.getNewValue();

            XYChart.Series<Number, Number> sort = switch (sortType) {
                case BUBBLE -> bubble;
                case MERGE -> merge;
                default -> bubble;
            };
            sort.getData().add(new XYChart.Data<>(givenValues[0], givenValues[1]));

            tableList.add(new Result(sortType,
                    givenValues[0], // size
                    givenValues[1], // swap
                    givenValues[2])); // duration

            leftStatus.setText(Thread.activeCount() + " Threads actifs");
            var sb = new StringBuilder("Dernière exécution | Début : ").append(startTime);
            sb.append(" - Fin : ").append(LocalTime.now());
            sb.append(" | Durée : ").append(MILLIS.between(startTime, LocalTime.now())).append(" ms");
            rightStatus.setText(sb.toString());
        });
    }

    /**
     * Initialise la table des résultats en définissant les valeurs des colonnes et en associant les propriétés
     * des objets Result aux colonnes correspondantes de la table.
     */
    private void initGraph(){
        // Define the column for lineGraph
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameSort"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        swapCol.setCellValueFactory(new PropertyValueFactory<>("swap"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.setItems(tableList);
    }

    /**
     * Crée et configure les séries de données pour le graphique en appelant la méthode makeGraphHelper pour chaque type de graphique.
     */
    private void makeGraph(){
        makeGraphHelper(bubble,"Tri à bulles");
        makeGraphHelper(merge,"Tri fusion");
    }

    /**
     * Aide à créer et configurer une série de données pour le graphique.
     *
     * @param typeGraph   La série de données pour le graphique
     * @param legendName  Le nom de la légende pour la série
     */
    private void makeGraphHelper(XYChart.Series<Number,Number> typeGraph,String legendName){
        typeGraph.setName(legendName);
        chart.getData().add(typeGraph);
    }

    /**
     * Méthode d'initialisation du contrôleur.
     * Configure l'interface utilisateur en appelant les méthodes initGraph() et makeGraph(),
     * et définit les choix possibles pour les menus déroulants.
     */
    @FXML
    public void initialize(){
        initGraph();
        makeGraph();

        //define the input for choose thread
        threadSpinner.setValueFactory(valueFactory);
        threadSpinner.setEditable(false);

        // Define the list of sort type
        for(var sort: SortType.values()){
            sortChoice.getItems().add(sort.toString());
        }
        sortChoice.getSelectionModel().selectFirst();

        // Define the list of possible quantity
        for (var quantity : Quantity.values()) {
            configurationChoice.getItems().add(quantity.toString());
        }
        configurationChoice.getSelectionModel().selectFirst();
    }

    /**
     * Méthode appelée lorsque le bouton "Start" est cliqué.
     * Récupère les valeurs sélectionnées pour le nombre de threads, le type de tri et la quantité,
     * puis crée une instance de SortThread et démarre l'exécution des tris parallèles.
     *
     * @param evt L'événement du bouton "Start" cliqué
     */
    @FXML
    void start(ActionEvent evt){
        var quantity = Objects.requireNonNull(Quantity.fromString(configurationChoice.getValue()));
        startTime = LocalTime.now();
        currentProgressBar = 0;
        progressBar.setProgress(currentProgressBar);
        var sortThread = new SortThread();
        sortThread.runSort(threadSpinner.getValue(), quantity.getNbRepeat(), sortChoice.getValue());
        sortThread.addPropertyChangeListener(this);
    }

    /**
     * Méthode appelée lorsque le bouton "Quit" est cliqué.
     * Ferme l'application en quittant la plateforme.
     *
     * @param evt L'événement du bouton "Quit" cliqué
     */
    @FXML
    void quit(ActionEvent evt){
        Platform.exit();
        System.exit(0);
    }

    /**
     * Méthode appelée lorsque le bouton "About" est cliqué.
     * Affiche une boîte de dialogue d'information contenant des informations sur le programme.
     *
     * @param evt L'événement du bouton "About" cliqué
     */
    @FXML
    void about(ActionEvent evt){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations complémentaires");
        var sb = new StringBuilder("Creation : 54314 OY 2022-2023.\n\n");
        sb.append("Diverses choix d'algorithmes disponibles:\n");
        sb.append("- Vous pouvez choisir le nombre de threads\n");
        sb.append("- Vous pouvez choisir le tri souhaité.\n");
        sb.append("- Vous pouvez choisir la quantité maximale d'entiers à trier.\n");
        sb.append("- Appuyez start lorsque tout est parametré!\n\n");
        alert.setContentText(sb.toString());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }


}

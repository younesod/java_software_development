package atl.g54314;

import atl.g54314.model.Model;
import atl.g54314.model.config.ConfigManager;
import atl.g54314.presenter.Presenter;
import atl.g54314.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ConfigManager.getInstance().load();
        Model model = new Model();
        View view = new View(stage);
        Presenter presenter  = new Presenter(model,view);
        presenter.initialize();
        view.addPresenter(presenter);
        model.addObserver(presenter);
    }

    /**
     * The main method of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp
        extends Application {

    final static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(LocalDateTime.now().format(FORMATTER_TIME) 
                + " MAINAPP | START | DEBUT");

        Parent root
                = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        System.out.println(LocalDateTime.now().format(FORMATTER_TIME) 
                + " MAINAPP | START | apres load");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        System.out.println(LocalDateTime.now().format(FORMATTER_TIME) 
                + " MAINAPP | START | FIN");

    }

    public static void main(String[] args) {
        launch(args);
    }

}

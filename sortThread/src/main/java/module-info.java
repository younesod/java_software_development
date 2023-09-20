module g54314.sortthread {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.junit.jupiter.api;
    requires java.desktop;

    opens g54314.sortthread to javafx.fxml;
    exports g54314.sortthread.model;
    exports g54314.sortthread.view;
    opens g54314.sortthread.model to javafx.fxml;
    opens g54314.sortthread.view to javafx.fxml;
    exports g54314.sortthread;
}
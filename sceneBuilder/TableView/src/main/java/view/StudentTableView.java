/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author zheln
 */
public class StudentTableView extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        TableView table = new TableView();
        
        TableColumn<Student,Integer> numCol = new TableColumn<>("Numéro");
        TableColumn<Student,String> firstnameCol = new TableColumn<>("Prénom");
        TableColumn<Student,String> lastnameCol= new TableColumn<>("Nom");
        
        table.getColumns().setAll(numCol,firstnameCol,lastnameCol);
        
        numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        
        Student std1 = new Student(1,"Arthur","Paquot");
        Student std2 = new Student(2, "Israe","Serokh");
        
        table.getItems().addAll(std1,std2);
        root.getChildren().add(table);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    
}

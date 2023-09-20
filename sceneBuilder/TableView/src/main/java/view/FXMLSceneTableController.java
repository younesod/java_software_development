/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author zheln
 */
public class FXMLSceneTableController {
    
    @FXML
    private TableColumn<Student,Integer> idCol;
    
    @FXML
    private TableColumn<Student,String> nameCol;
    
    @FXML
    private TableColumn<Student,String> lastnameCol;
    
    @FXML
    private TableView table;
    
    @FXML
    private TextField matricule;
    
    @FXML
    private TextField prenom;
    
    @FXML
    private TextField nom;
    
    
    
    private ObservableList<Student> list;
    
    
    
    @FXML
    void add(ActionEvent event){
        System.out.println("matricule : "+ matricule.getText()+" Prenom :"+prenom.getText() + " Nom :"+ nom.getText());
        try {
            list.add(new Student(Integer.parseInt(matricule.getText()), prenom.getText(), nom.getText()));
        } catch (NumberFormatException e) {
            matricule.setText("Erreur : nombre");
        }
    }
    
    public void initialize() {
        matricule.setText("54314");
        prenom.setText("Younes");
        nom.setText("Oudahya");
        System.out.println("Initialize!");
        list = FXCollections.observableArrayList();
        idCol.setCellValueFactory(element-> new ReadOnlyObjectWrapper<>(element.getValue().getNum()));
        nameCol.setCellValueFactory(element-> new ReadOnlyObjectWrapper<>(element.getValue().getFirstname()));
        lastnameCol.setCellValueFactory(element-> new ReadOnlyObjectWrapper<>(element.getValue().getLastname()));
        table.setItems(list);
    }
    
    
    
    
    
    
    
    
}

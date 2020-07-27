/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.controller;

import bo.model.Part;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author boslott
 */
public class PartsWindowController {
    
    private App app;
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private int selectedPartId = -1;
    
    @FXML TableView<Part> partsTableView;
    @FXML TableColumn<Part, Integer> partsIdColumn;
    @FXML TableColumn<Part, String> partsNameColumn;
    @FXML TableColumn<Part, Double> partsPriceColumn;
    @FXML TableColumn<Part, Integer> partsStockColumn;
    @FXML TextField partSearchField;
    
    public void initialize() {
        partsIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        partsNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        partsPriceColumn.setCellValueFactory( new PropertyValueFactory<>("price") );
        partsStockColumn.setCellValueFactory( new PropertyValueFactory<>("stock") );
    }
    
    public void setMain(App app) throws InterruptedException {
        this.app = app;
        allParts = app.inventory.getAllParts();
        partsTableView.setItems(allParts);
        
        partsTableView.setOnMousePressed((MouseEvent event) -> {
            System.out.println("Just clicked on partId: " + partsTableView.getSelectionModel().getSelectedItem().getId() + "in the PartsTableView");
            selectedPartId = partsTableView.getSelectionModel().getSelectedItem().getId();
        });
        
        partSearchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            this.searchParts();
        });
    }
    
    public void backToMain() {
        app.firstWindow();
    }
    
    public void searchParts() {
        System.out.println("Now searching the master list of all Products ...");
//        System.out.println("productsSearchField.getText: " + productSearchField.getText());
        if(!partSearchField.getText().equals("")) {
            ObservableList<Part> filteredList = FXCollections.observableArrayList();
//            filteredList = allParts;
            allParts.forEach(part -> {
                
                if(part.getName().contains(partSearchField.getText())) {
                    filteredList.add(part);
                }
            });
            partsTableView.setItems(filteredList);
        }
        else {
            partsTableView.setItems(allParts);
        }
    }
    
    public void addPart() {
        System.out.println("adding part now ...");
        app.addPart();
    }
    
    public void modifyPart() {
        System.out.println("modifying part now ...");
        if(selectedPartId != -1) {
            // A Part has been selected by the user, so addPart screen is called but with the Part Id)
            System.out.println("About to modify a part with the id: " + partsTableView.getSelectionModel().getSelectedItem().getId() + " and a name: " + partsTableView.getSelectionModel().getSelectedItem().getName());
            app.addPart(
                partsTableView.getSelectionModel().getSelectedItem().getId(),
                partsTableView.getSelectionModel().getSelectedItem().getName()
            );
        }
        else {
            System.out.println("You have not selected a Part to modify yet ... Please select a Part from the Table first, then click the Modify button ...");
        }
        
    }
    
    public void deletePart() {
        System.out.println("deleting part now ...");
        System.out.println("About to delete the Part with Id: " + selectedPartId);
        if(selectedPartId != -1) {
            boolean deleted = app.inventory.deletePart(app.inventory.lookupPart(selectedPartId));
            if(deleted) { selectedPartId = -1; }
        }
    }
}

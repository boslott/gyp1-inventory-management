/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.controller;

import bo.model.InHouse;
import bo.model.Outsourced;
import bo.model.Part;
import bo.model.Product;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author boslott
 */
public class ModifyProductController {
    
    @FXML private Label modifyProductLabel;
    @FXML private TextField productIdField;
    @FXML private TextField productNameField;
    @FXML private TextField productStockField;
    @FXML private TextField productPriceField;
    @FXML private TextField productMinField;
    @FXML private TextField productMaxField;
    @FXML private TextField searchChoosePartField;
    
    @FXML private TableView<Part> choosePartTableView;
    @FXML private TableColumn<Part, Integer> choosePartIdColumn;
    @FXML private TableColumn<Part, String> choosePartNameColumn;
    @FXML private TableColumn<Part, Integer> choosePartStockColumn;
    @FXML private TableColumn<Part, Double> choosePartPriceColumn;
    
    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> associatedPartsIdColumn;
    @FXML private TableColumn<Part, String> associatedPartsNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartsStockColumn;
    @FXML private TableColumn<Part, Double> associatedPartsPriceColumn;
    
    private App app;
    private Product productToModify;
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private boolean newProduct = true;
    
    public void initialize() {
        PropertyValueFactory id = new PropertyValueFactory<>("id");
        PropertyValueFactory name = new PropertyValueFactory<>("name");
        PropertyValueFactory price = new PropertyValueFactory<>("price");
        PropertyValueFactory stock = new PropertyValueFactory("stock");
        
        choosePartIdColumn.setCellValueFactory( id );
        choosePartNameColumn.setCellValueFactory( name );
        choosePartPriceColumn.setCellValueFactory( price );
        choosePartStockColumn.setCellValueFactory( stock );
        
        associatedPartsIdColumn.setCellValueFactory( id );
        associatedPartsNameColumn.setCellValueFactory( name );
        associatedPartsPriceColumn.setCellValueFactory( price );
        associatedPartsStockColumn.setCellValueFactory( stock );
    }
    
    public void setMain(App app, int productToModifyId) {
        this.app = app;
        if(productToModifyId == -1) { modifyProductLabel.setText("ADD PRODUCT"); }
        else if(productToModifyId != -1) {
            newProduct = false;
            productToModify = app.inventory.lookupProduct(productToModifyId);
            productIdField.setText(String.valueOf(productToModify.getId()));
            productNameField.setText(productToModify.getName());
            productPriceField.setText(String.valueOf(productToModify.getPrice()));
            productStockField.setText(String.valueOf(productToModify.getStock()));
            productMinField.setText(String.valueOf(productToModify.getMin()));
            productMaxField.setText(String.valueOf(productToModify.getMax()));
        }
        allParts = app.inventory.getAllParts();
        if(productToModify != null) {
            associatedParts = productToModify.getAllAssociatedParts();
            associatedPartsTableView.setItems(associatedParts);
        }
        else {
            productToModify = new Product(-1, "Default", 0.00, 0, 0, 0);
        }
        choosePartTableView.setItems(allParts);
        
        // Add event listeners to all the textfields
        addFieldListener(productIdField);
        addFieldListener(productNameField);
        addFieldListener(productPriceField);
        addFieldListener(productStockField);
        addFieldListener(productMinField);
        addFieldListener(productMaxField);
        addFieldListener(searchChoosePartField);
    }
    
    private void addFieldListener(TextField field) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String fieldName = field.getId();
            
                //            if (!newValue.matches("\\d*") && !fieldName.equals("partName")) {
//                field.setText(newValue.replaceAll("[^\\d]", ""));
//            }

            modifyProductProperty(field, fieldName);
            
            
        });
    }
    
    private void modifyProductProperty(TextField field, String fieldName) {
        if(productToModify != null) {
            switch(fieldName) {
                case "productIdField":
                    if(!field.getText().equals("")) { productToModify.setId(Integer.parseInt(field.getText())); }
                    else { productToModify.setId(-1); }
                    break;
                case "productNameField":
                    productToModify.setName(field.getText());
                    break;
                case "productPriceField":
                    if(!field.getText().equals("")) { productToModify.setPrice(Double.parseDouble(field.getText())); }
                    else { productToModify.setPrice(0.00); }
                    break;
                case "productStockField":
                    if(!field.getText().equals("")) {
                        if( Integer.parseInt(field.getText()) < productToModify.getMin() ) {
                            validationAlert(field, "The value entered is less than the minimum");
                        }
                        if( Integer.parseInt(field.getText()) > productToModify.getMax() ) {
                            validationAlert(field, "The value entered is more than the max");
                        }
                        productToModify.setStock(Integer.parseInt(field.getText()));
                    }
                    else { productToModify.setStock(0); }
                    break;
                case "productMinField":
                    if(!field.getText().equals("")) {
                        if(Integer.parseInt(field.getText()) > productToModify.getMax()) {
                            validationAlert(field, "Min cannot be greater than the Max");
                        }
                        else { productToModify.setMin(Integer.parseInt(field.getText())); }
                    }
                    else { productToModify.setMin(0); }
                    break;
                case "productMaxField":
                    if(!field.getText().equals("")) {
                        if(Integer.parseInt(field.getText()) < productToModify.getMin()) {
                            validationAlert(field, "Max cannot be less than the Min");
                        }
                        else { productToModify.setMax(Integer.parseInt(field.getText())); }
                    }
                    else { productToModify.setMax(0); }
                    break;
                default:
                    System.out.println("The field is not recognized: " + fieldName + " ...");
                    break;
            }
        }
    }
    
    public void validationAlert(TextField field, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
//            field.clear();
            alert.close();
        }
    }
    
    public void searchParts() {
        System.out.println("Search All Parts has been called ...");
        if(!searchChoosePartField.getText().equals("")) {
            ObservableList<Part> filteredList = FXCollections.observableArrayList();
//            filteredList = allParts;
            allParts.forEach(part -> {
                if(part.getName().contains(searchChoosePartField.getText())) {
                    System.out.println("Adding part " + part.getName() + " to the associated parts list ...");
                    filteredList.add(part);
                }
            });
            if(!searchChoosePartField.getText().equals("")) {
                System.out.println("Setting the choose Part Table to the filtered list ...");
                choosePartTableView.setItems(filteredList);
            }
        }
        else {
            choosePartTableView.setItems(allParts);
        }
    }
    
    public void addAssociatedPart() {
        if(productToModify != null) {
            productToModify.addAssociatedPart(app.inventory.lookupPart(choosePartTableView.getSelectionModel().getSelectedItem().getId()));
        }
    }
    
    public void deleteAssociatedPart() {
        if(productToModify != null) {
            productToModify.deleteAssociatedPart(app.inventory.lookupPart(associatedPartsTableView.getSelectionModel().getSelectedItem().getId()));
        }
    }
    
    public void saveProduct() throws InterruptedException {
//        System.out.println("partToModify is null ... sad face ): ");
        if(productToModify != null) {
            System.out.println("The partToModify is not null, so we are proceeding with the save ...");
            if(newProduct) {
                app.inventory.addProduct(productToModify);
            }
            else {
                System.out.println("Should be updating the not-new product now ...");
                app.inventory.updateProduct(app.inventory.getAllProducts().indexOf(productToModify), productToModify);
            }
//            productToModify = null;
            System.out.println("productToModify has be set to null ...");
//            this.clearFields();
            System.out.println("fields have been cleared ...");
            app.openProducts();
            System.out.println("app.openParts has been called ...");
        }
    }
    
    private void clearFields() {
        productIdField.clear();
        productNameField.clear();
        productPriceField.clear();
        productStockField.clear();
        productMinField.clear();
        productMaxField.clear();
    }
    
    public void cancelModifyProduct() throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            app.openProducts();
        }
        else {
            alert.close();
        }
    }
    
}

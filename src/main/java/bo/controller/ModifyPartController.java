/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import bo.model.InHouse;
import bo.model.Outsourced;
import bo.model.Part;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;

/**
 *
 * @author boslott
 */
public class ModifyPartController {
    
    @FXML private Label modifyPartLabel;
    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partPrice;
    @FXML private TextField partStock;
    @FXML private TextField partMin;
    @FXML private TextField partMax;
    @FXML private Label partVariableFieldLabel;
    @FXML private TextField partVariableField;
    
    private App app;
    private Part partToModify;
    private String partToModifyName;
    private int partToModifyId;
    private ObservableList<Part> originalPart;
    private ObservableList<Part> partToModifyOL = FXCollections.observableArrayList();
    ;
    private Boolean newPart = true;
    
    public void setMain(App app, int partToModifyId, String partToModifyName) {
        this.app = app;
        if(partToModifyId != -1) {
            System.out.println("Setting main to modify an existing part: " + partToModifyId);
            // Modifying a part, not a creating a new one
            // Set the flag modify-or-new flag
            newPart = false;
            
            partToModify = app.inventory.lookupPart(partToModifyId);
            partToModifyOL = app.inventory.lookupPart(partToModifyName);
            this.partToModifyId = partToModifyId;
            this.partToModifyName = partToModifyName;
            
            
            originalPart = FXCollections.observableArrayList(partToModifyOL);

            
            System.out.println("partToModifyOL: " + partToModifyOL.get(0).getName());
            
            
//            originalPart.get(0).setName("This is a test!");
//            partToModifyOL.get(0).setName("This is the second naming test!");
            partToModify.setName("This is the third test!");
            
            System.out.println("partToModifyOL.getName(): " + partToModifyOL.get(0).getName());
            System.out.println("originalPart: " + originalPart.get(0).getName());
            System.out.println("partToModify: " + partToModify.getName());
            
            
            
            app.inventory.printPart(partToModify);
            
            
            setCurrentData();
        }
        else if(partToModifyId == -1) {
            System.out.println("partToModifyId should equal -1, so new Part: " + partToModifyId);
            modifyPartLabel.setText("Add Part");
        }
        
        // Add radio buttons to a toggle group
        ToggleGroup tg = new ToggleGroup();
        inHouseRadio.setToggleGroup(tg);
        outsourcedRadio.setToggleGroup(tg);
        
        // Add an event listener to the toggle group
        addToggleListener(tg);
        
        if(partToModify == null) {
            // This is the default setting for a new, blank part
            inHouseRadio.setSelected(true);
            outsourcedRadio.setSelected(false);
            partToModify = new InHouse(-1, "Default", 0.00, 0, 0, 0, 0);
        }
        else if(partToModify != null) {
            if(partToModify instanceof InHouse) {
                inHouseRadio.setSelected(true);
                outsourcedRadio.setSelected(false);
            }
            else if(partToModify instanceof Outsourced) {
                inHouseRadio.setSelected(false);
                outsourcedRadio.setSelected(true);
            }
        }
        
        // Add event listeners to all the textfields
        addFieldListener(partId);
        addFieldListener(partName);
        addFieldListener(partPrice);
        addFieldListener(partStock);
        addFieldListener(partMin);
        addFieldListener(partMax);
        addFieldListener(partVariableField);
    }
    
    private void setCurrentData() {
        partId.setText(String.valueOf(partToModify.getId()));
        partName.setText(partToModify.getName());
        partPrice.setText(String.valueOf(partToModify.getPrice()));
        partStock.setText(String.valueOf(partToModify.getStock()));
        partMin.setText(String.valueOf(partToModify.getMin()));
        partMax.setText(String.valueOf(partToModify.getMax()));
//        partVariableField.setPromptText(partToModify instanceof InHouse ? String.valueOf( ((InHouse) partToModify).getMachineId()) : ((Outsourced) partToModify).getCompanyName());
//        partVariableField.setPromptText( partToModify instanceof InHouse ? String.valueOf( ((InHouse) partToModify).getMachineId()) : ((Outsourced) partToModify).getCompanyName() );
        if(partToModify instanceof InHouse) {
            partVariableField.setText(String.valueOf( ((InHouse)partToModify).getMachineId()  ));
        }
        else if(partToModify instanceof Outsourced) {
            partVariableField.setText( ((Outsourced)partToModify).getCompanyName() );
        }
        System.out.println("after field is set to text: " + partVariableField.getText());
    }
    
    private void addToggleListener(ToggleGroup tg) {
        tg.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) -> {
            RadioButton rb = (RadioButton)tg.getSelectedToggle();
            if(rb != null) {
                String s = rb.getText();
                if(s.equals("In-House")) {
                    partVariableFieldLabel.setText("MACHINE ID");
                }
                else if(s.equals("Outsourced")) {
                    partVariableFieldLabel.setText("COMPANY NAME");
                }
            }
        });
    }
    
    private void addFieldListener(TextField field) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String fieldName = field.getId();
            
                //            if (!newValue.matches("\\d*") && !fieldName.equals("partName")) {
//                field.setText(newValue.replaceAll("[^\\d]", ""));
//            }

            modifyPartProperty(field, fieldName);
            app.inventory.printPart(partToModify);
            app.inventory.printPart(app.inventory.lookupPart(partToModifyName).get(0));
        });
    }

    private void modifyPartProperty(TextField field, String fieldName) {
        if(partToModify != null) {
            switch(fieldName) {
                case "partId":
                    if(!field.getText().equals("")) { partToModify.setId(Integer.parseInt(field.getText())); }
                    else { partToModify.setId(-1); }
                    break;
                case "partName":
                    partToModify.setName(field.getText());
                    break;
                case "partPrice":
                    if(!field.getText().equals("")) { partToModify.setPrice(Double.parseDouble(field.getText())); }
                    else { partToModify.setPrice(0.00); }
                    break;
                case "partStock":
                    if(!field.getText().equals("")) {
                        if( Integer.parseInt(field.getText()) < partToModify.getMin()) {
                            validationAlert(field, "The value entered is less than the minimum");
                        }
                        if( Integer.parseInt(field.getText()) > partToModify.getMax()) {
                            validationAlert(field, "The value entered is greater than the max");
                        }
                        partToModify.setStock(Integer.parseInt(field.getText()));
                    }
                    else { partToModify.setStock(0); }
                    break;
                case "partMin":
                    if(!field.getText().equals("")) {
                        if(Integer.parseInt(field.getText()) > partToModify.getMax()) {
                            // Min is greater than the max, open an alert
                            validationAlert(field, "Min cannot be greater than the Max");
                        } else { partToModify.setMin(Integer.parseInt(field.getText())); }
                    }
                    else { partToModify.setMin(0); }
                    break;
                case "partMax":
                    if(!field.getText().equals("")) {
                        if(Integer.parseInt(field.getText()) < partToModify.getMin()) {
                            // Max cannot be less than the min, open an alert
                            validationAlert(field, "Max cannot be less than the Min");
                        }
                        else { partToModify.setMax(Integer.parseInt(field.getText())); }
                    }
                    else { System.out.println("The field is blank, so if-else kicked in"); partToModify.setMax(0); }
                    break;
                case "partVariableField":
                    if(partToModify instanceof InHouse) {
                        if(!field.getText().equals("")) {
                            ((InHouse)partToModify).setMachineId(Integer.parseInt( field.getText() ) );
                            System.out.println("Just got the text from the variable field ... : " + field.getText());
                        }
                        else { ((InHouse)partToModify).setMachineId(-1); }
                    }
                    else if(partToModify instanceof Outsourced) {
                        if(!field.getText().equals("")) {
                            ((Outsourced)partToModify).setCompanyName(field.getText());
                            System.out.println("Just got the text from the variable field ... : " + field.getText());
                            System.out.println("(Outsourced)partToModify).getCompanyName: " + ((Outsourced)partToModify).getCompanyName());
                        }
                        else { ((Outsourced)partToModify).setCompanyName("Default"); }
                    }
                    break;
                default:
                    System.out.println("The field is not recognized ...");
                    break;
            }
        }
//        System.out.println("originalPart.getName(): " + originalPart.get(0).getName());
//        System.out.println("partToModify.getName(0): " + partToModify.getName());
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
    
    
    
    public void cancelModifyPart() throws InterruptedException {
//        if(partToModify != null) { partToModify = null; }
//        if(!partToModify.equals(originalPart)) {
//            partToModify = originalPart.get(0);
//        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            app.openParts();
        }
        else {
            alert.close();
        }
    }
    
    public void savePart() throws InterruptedException {
//        System.out.println("partToModify is null ... sad face ): ");
        if(partToModify != null) {
            System.out.println("The partToModify is not null, so we are proceeding with the save ...");
            if(newPart) {
                System.out.println("newPart is true, so proceeding with saving the new part ...");
                System.out.println("partToModify (really the newPart to add ...): " + partToModify.getName());
                app.inventory.addPart(partToModify);
                app.inventory.printAllParts();
            }
            else {
                System.out.println("newPart is false, so proceeding with saving the modified part ...");
                System.out.println("partToModify is not null, the id is: " + partToModify.getId());
                if(partToModify instanceof Outsourced) { System.out.println("The Company Name about to be saved is: " + ((Outsourced) partToModify).getCompanyName()); }
                app.inventory.updatePart(app.inventory.getAllParts().indexOf(partToModify), partToModify);
            }
//            partToModify = null;
            System.out.println("partToModify has be set to null ...");
//            this.clearFields();
            System.out.println("fields have been cleared ...");
            app.openParts();
            System.out.println("app.openParts has been called ...");
        }
    }
    
    private void clearFields() {
        partId.clear();
        partName.clear();
        partPrice.clear();
        partStock.clear();
        partMin.clear();
        partMax.clear();
        partVariableField.clear();
    }
    
}

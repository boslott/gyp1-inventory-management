package bo.controller;

import bo.model.InHouse;
import bo.model.Inventory;
import bo.model.Outsourced;
import bo.model.Product;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    
    private Stage primaryStage;
    public Inventory inventory = new Inventory();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loadData();
        firstWindow();
    }
    
    private void changeScene(AnchorPane pane) {
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void firstWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstWindowView.fxml"));
            
            AnchorPane pane = loader.load();
            
            FirstWindowController firstWindowController = loader.getController();
            firstWindowController.setMain(this);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void openParts() throws InterruptedException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PartsWindowView.fxml"));
            
            AnchorPane pane = loader.load();
            
            PartsWindowController partsWindowController = loader.getController();
            partsWindowController.setMain(this);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void addPart() {
        try {
            System.out.println("app.addPart ... new Part");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyPartView.fxml"));
            
            AnchorPane pane = loader.load();
            
            ModifyPartController modifyPartController = loader.getController();
            
            modifyPartController.setMain(this, -1, "Default");
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void addPart(int partToModifyId, String partToModifyName) {
        try {
            System.out.println("app.addPart(partToModifyId ... modifyPart " + partToModifyId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyPartView.fxml"));
                
            AnchorPane pane = loader.load();
            
            ModifyPartController modifyPartController = loader.getController();
            
            modifyPartController.setMain(this, partToModifyId, partToModifyName);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void openProducts() throws InterruptedException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductsWindowView.fxml"));
    
            AnchorPane pane = loader.load();
            
            ProductsWindowController productsWindowController = loader.getController();
           
            productsWindowController.setMain(this);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void modifyProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProductView.fxml"));
            
            AnchorPane pane = loader.load();
            
            ModifyProductController modifyProductController = loader.getController();
            
            modifyProductController.setMain(this, -1);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
    public void modifyProduct(int productToModifyId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProductView.fxml"));
                
            AnchorPane pane = loader.load();
            
            ModifyProductController modifyProductController = loader.getController();
            
            modifyProductController.setMain(this, productToModifyId);
            
            this.changeScene(pane);
        }
        catch(IOException e) {
            System.out.println("Exception: " + e);
        }
    }
    
//    public void openAll() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllWindowView.fxml"));
//    
//            AnchorPane pane = loader.load();
//            AllWindowController allWindowController = loader.getController();
//            allWindowController.setMain(this);
//
//            this.changeScene(pane);
//        }
//        catch(IOException e) {
//            System.out.println("Exception: " + e);
//        }
//    }
    
    public void bye() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you would like to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) primaryStage.close();
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }
    
    public void loadData() {
        
        // Create the parts
        InHouse bolt = new InHouse(1, "Bolt", 0.39, 4, 2, 10, 2344);
        InHouse nail = new InHouse(2, "Nail", 0.15, 25, 20, 100, 2344);
        InHouse metalPlate = new InHouse(3, "Metal Plate", 2.46, 4, 2, 10, 2344);
        InHouse spring = new InHouse(4, "Spring", 0.88, 7, 4, 15, 3344);
        InHouse washer = new InHouse(5, "Washer", 0.10, 3, 3, 20, 3344);
        Outsourced hammer = new Outsourced(6, "Hammer", 5.22, 2, 1, 5, "Jack's Hammers, Inc.");
        Outsourced wrench = new Outsourced(7, "Wrench", 3.89, 2, 1, 5, "Bob's Wrenches, LLC");
        Outsourced pliers = new Outsourced(8, "Pliers", 3.12, 2, 2, 10, "Hal's Plier's");
        Outsourced screwdriver = new Outsourced(9, "Screwdriver", 1.19, 8, 5, 10, "Peter's Screwdriver's, Inc.");
        Outsourced toolBelt = new Outsourced(10, "Tool Belt", 10.98, 2, 2, 5, "Sarah's Tool Belt World, LLC");
        
        // Add the parts to the inventory
        inventory.addPart(bolt);
        inventory.addPart(nail);
        inventory.addPart(metalPlate);
        inventory.addPart(spring);
        inventory.addPart(washer);
        inventory.addPart(hammer);
        inventory.addPart(wrench);
        inventory.addPart(pliers);
        inventory.addPart(screwdriver);
        inventory.addPart(toolBelt);
        
        // Create the hardware product, add associated parts, add the product to the inventory
        Product hardware = new Product(1, "Hardware", 40.55, 5, 2, 10);
        hardware.addAssociatedPart(bolt);
        hardware.addAssociatedPart(nail);
        hardware.addAssociatedPart(washer);
        inventory.addProduct(hardware);
        
        // Create the accessoryHardware product, add associated parts, add the product to the inventory
        Product accessoryHardware = new Product(2, "Accessory Hardware", 38.55, 5, 2, 10);
        accessoryHardware.addAssociatedPart(metalPlate);
        accessoryHardware.addAssociatedPart(spring);
        inventory.addProduct(accessoryHardware);
        
        // Create the basicToolset product, add associated parts, add the product to the inventory
        Product basicToolset =  new Product(3, "Basic Toolset", 60.25, 9, 2, 10);
        basicToolset.addAssociatedPart(hammer);
        basicToolset.addAssociatedPart(screwdriver);
        basicToolset.addAssociatedPart(toolBelt);
        
        // Create the accessoryToolset product, add associated parts, add the product to the inventory
        inventory.addProduct(basicToolset);
        Product accessoryToolset = new Product(4, "Accessory Toolset", 50.25, 1, 2, 10);
        accessoryToolset.addAssociatedPart(wrench);
        accessoryToolset.addAssociatedPart(pliers);
        inventory.addProduct(accessoryToolset);
        
    }

    public static void main(String[] args) {
        launch();
    }

}

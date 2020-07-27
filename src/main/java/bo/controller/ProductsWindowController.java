/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.controller;

import bo.model.Part;
import bo.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class ProductsWindowController {
    
    private App app;
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private int selectedProductId = -1;
    
    @FXML TableView<Product> productsTableView;
    @FXML TableColumn<Product, Integer> productsIdColumn;
    @FXML TableColumn<Product, String> productsNameColumn;
    @FXML TableColumn<Product, Double> productsPriceColumn;
    @FXML TableColumn<Product, Integer> productsStockColumn;
    @FXML TextField productSearchField;
    
    public void initialize() {
        productsIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        productsNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        productsPriceColumn.setCellValueFactory( new PropertyValueFactory<>("price") );
        productsStockColumn.setCellValueFactory( new PropertyValueFactory<>("stock") );
    }
    
    public void setMain(App app) throws InterruptedException {
        this.app = app;
        allProducts = app.inventory.getAllProducts();
        productsTableView.setItems(allProducts);
        
        productsTableView.setOnMousePressed((MouseEvent event) -> {
//            System.out.println("Just clicked on partId: " + productsTableView.getSelectionModel().getSelectedItem().getId() + "in the ProductsTableView");
            selectedProductId = productsTableView.getSelectionModel().getSelectedItem().getId();
        });
    }
    
    public void backToMain() {
        app.firstWindow();
    }
    
    public void addProduct() {
        System.out.println("adding product now ...");
        app.modifyProduct();
    }
    
    public void modifyProduct() {
        System.out.println("modifying product now ...");
        if(selectedProductId != -1) {
            // A Product has been selected by the user, so modifyProduct screen is called )
            app.modifyProduct(selectedProductId);
        }
        else {
            System.out.println("You have not selected a Product to modify yet ... Please select a Product from the Table first, then click the Modify button ...");
        }
        
    }
    
    public void deleteProduct() {
        System.out.println("deleting prodcut now ...");
        System.out.println("About to delete the Product with Id: " + selectedProductId);
        if(selectedProductId != -1) {
            boolean deleted = app.inventory.deleteProduct(app.inventory.lookupProduct(selectedProductId));
            if(deleted) { selectedProductId = -1; }
        }
    }
    
    public void searchProducts() {
        System.out.println("Now searching the master list of all Products ...");
//        System.out.println("productsSearchField.getText: " + productSearchField.getText());
        if(!productSearchField.getText().equals("")) {
            ObservableList<Product> filteredList = FXCollections.observableArrayList();
//            filteredList = allParts;
            allProducts.forEach(product -> {
                if(product.getName().contains(productSearchField.getText())) {
                    System.out.println("Adding product " + product.getName() + " to the associated parts list ...");
                    filteredList.add(product);
                }
            });
            productsTableView.setItems(filteredList);
        }
        else {
            productsTableView.setItems(allProducts);
        }
    }
    
}

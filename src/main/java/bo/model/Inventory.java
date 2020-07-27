/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.model;

import java.util.Optional;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author boslott
 */
public class Inventory {
    
    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public void Inventory() {
        allParts.addListener(new ListChangeListener<Part>() {
            @Override
            public void onChanged(Change<? extends Part> change) {
                // What happens when while / when the list changes?
            }
        });
        
        allProducts.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> change) {
                // What happens here while / when the list changes?
            }
        });
    }
    
    public void addPart(Part newPart) {
        System.out.println("The inventory is now adding a newPart to the allParts list ... " + newPart.getName() );
        allParts.add(newPart);
    }
    
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partId) {
        final int[] idx = {0};
        allParts.forEach(part -> {
            if(part.getId() == partId) {
                idx[0] = allParts.indexOf(part);
            }
        });
        
        return allParts.get(idx[0]);
    }
    
    public ObservableList<Part> lookupPart(String name) {
        final int[] idx = {0};
        allParts.forEach(part -> {
            if(part.getName().equals(name)) {
                idx[0] = allParts.indexOf(part);
            }
        });
        return FXCollections.observableArrayList(allParts.get(idx[0]));
    }
    
    public Product lookupProduct(int productId) {
        final int[] idx = {0};
        allProducts.forEach(product -> {
            if(product.getId() == productId) {
                idx[0] = allProducts.indexOf(product);
            }
        });
        return allProducts.get(idx[0]);
    }
    
//    public ObservableList<Product> lookupProduct(String productName) {
//        
//    }
    
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
        
    }
    
    public void updateProduct(int index, Product selectedProduct) {
        System.out.println("Should be updating the inventory.allProducts ...");
//        int tmp = allProducts.indexOf(selectedProduct);
        System.out.println("Index: " + index + ", selectedProduct.name: " + selectedProduct.getName());
        allProducts.set(index, selectedProduct);
    }
    
    public void updateProductAssociatedParts(Product selectedProduct, Part selectedPart, String updateType) {
        if(updateType.equals("add")) {
            selectedProduct.addAssociatedPart(selectedPart);        
        }
        else if(updateType.equals("delete")) {
            selectedProduct.deleteAssociatedPart(selectedPart);
        }
    }
    
    public boolean deletePart(Part selectedPart) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this Part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            int idx = allParts.indexOf(selectedPart);
            if(idx >= 0 && idx < allParts.size()) {
                allParts.remove(idx);
                return true;
            }
            else { return false; }
        }
        else {
            alert.close();
            return false;
        }
    }
    
    public boolean deleteProduct(Product selectedProduct) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this Product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
//            field.clear();
            int idx = allProducts.indexOf(selectedProduct);
            if(idx >= 0 && idx < allProducts.size()) {
                allProducts.remove(idx);
                return true;
            }
            else { return false; }
        }
        else {
            alert.close();
            return false;
        }
    }
    
    public void printPart(Part part) {
        System.out.println("part index: " );
        System.out.println("-----------------------");
        System.out.println("ID: " + part.getId());
        System.out.println("Name: " + part.getName());
        System.out.println("Price: " + part.getPrice());
        System.out.println("Stock: " + part.getStock());
        System.out.println("-----------------------\n");
    }
    
    public void printAllParts() {
        allParts.stream().forEach(part -> {
            System.out.println("part index: " );
            System.out.println("-----------------------");
            System.out.println("ID: " + part.getId());
            System.out.println("Name: " + part.getName());
            System.out.println("Price: " + part.getPrice());
            System.out.println("Stock: " + part.getStock());
            System.out.println("-----------------------\n");
        });
    }
    
    public ObservableList<Part> getAllParts() { return allParts; }
    
    public ObservableList<Product> getAllProducts() { return allProducts; }
    
}

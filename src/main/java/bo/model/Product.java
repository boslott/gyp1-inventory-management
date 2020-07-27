/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author boslott
 */
public class Product {
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    public Product(
        int id,
        String name,
        double price,
        int stock,
        int min,
        int max
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }
    
    public void addAssociatedPart(Part part) {
        System.out.println("About to add the part " + part.getName() + " to the associatedParts list for this product " + this.getName() + " ...");
        associatedParts.add(part);
    }
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
    
}

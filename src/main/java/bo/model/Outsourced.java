/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.model;

/**
 *
 * @author boslott
 */
public class Outsourced extends Part {
    
    private String companyName;
    
    public Outsourced(
        int id,
        String name,
        double price,
        int stock,
        int min,
        int max,
        String companyName
    ) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    public String getCompanyName() { return companyName; }
    
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public void printValues() {
        System.out.println("Outsourced id: " + this.getId());
        System.out.println("Outsourced name: " + this.getName());
        System.out.println("Outsourced price: " + this.getPrice());
        System.out.println("Outsourced stock: " + this.getStock());
        System.out.println("Outsourced min: " + this.getMin());
        System.out.println("Outsourced max: " + this.getMax());
        System.out.println("Outsourced companyName: " + companyName);
    }
    
}

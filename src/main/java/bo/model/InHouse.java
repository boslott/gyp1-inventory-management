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
public class InHouse extends Part {
    
    private int machineId;
    
    public InHouse(
        int id,
        String name,
        double price,
        int stock,
        int min,
        int max,
        int machineId
    ) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
//    public int getId() { return this.id; }
    
//    @Override
    public int getMachineId() { return machineId; }
//    
//    /**
//     *
//     * @param id
//     */
//    @Override
    public void setMachineId(int id) { machineId = id; }
    
    public void printValues() {
        System.out.println("InHouse id: " + this.getId());
        System.out.println("InHouse name: " + this.getName());
        System.out.println("InHouse price: " + this.getPrice());
        System.out.println("InHouse stock: " + this.getStock());
        System.out.println("InHouse min: " + this.getMin());
        System.out.println("InHouse max: " + this.getMax());
        System.out.println("InHouse machineId: " + machineId);
    }
    
}

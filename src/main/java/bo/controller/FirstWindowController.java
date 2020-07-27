/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.controller;

/**
 *
 * @author boslott
 */
public class FirstWindowController {
    
    private App app;
    
    public void setMain(App app) {
        this.app = app;
    }
    
    public void openParts() throws InterruptedException {
        app.openParts();
    }
    
    public void openProducts() throws InterruptedException {
        app.openProducts();
    }
    
//    public void openAll() {
//        app.openAll();
//    }
    
    public void bye() {
        app.bye();
    }
    
}

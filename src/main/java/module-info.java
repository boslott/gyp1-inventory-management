module bo.stevenslott_gyp1_task1_inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    opens bo.controller to javafx.fxml;
    opens bo.model to javafx.base;
    exports bo.controller;
}

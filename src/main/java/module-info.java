module com.mycompany.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;

    opens com.mycompany.pacman to javafx.fxml;
    exports com.mycompany.pacman;
    
    opens controller to javafx.fxml;
    exports controller;
    
    opens model to javafx.fxml;
    exports model;
}

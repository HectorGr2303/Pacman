/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.mycompany.pacman.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author gomez
 */
public class PantallaCargaController implements Initializable {

    @FXML
    private ProgressBar barraCarga;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                Platform.runLater(() -> {
                    barraCarga.setProgress(progress / 100.0);
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                try {
                    App.setRoot("PantallaPrincipal");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }    
    
}

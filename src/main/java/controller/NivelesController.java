package controller;

import com.mycompany.pacman.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class NivelesController implements Initializable {

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void toPrimerNivel(ActionEvent event) throws IOException {
        App.setRoot("PrimerNivel");
    }
}

package controller;

import com.mycompany.pacman.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Pacman;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Fantasma_Clyde;
import model.Fantasma_Pinky;
import model.Fantasma_Red;
import model.Fantasmas;

public class PrimerNivelController implements Initializable {

    private static final int[][] nivel = {
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    {1, 3, 0, 0, 0, 0, 0, 0, 3, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 1, 1, 0, 0, 1, 1, 0, 1},
    {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
    {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
    {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
    {1, 0, 1, 1, 0, 0, 1, 1, 0, 1},
    {1, 3, 0, 0, 0, 0, 0, 0, 3, 1},
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    @FXML
    private GridPane grid;
    @FXML
    GridPane gridVidas = new GridPane();
    private Pacman pacman;
    private TranslateTransition transition;
    private ImageView[][] monedas;
    private ImageView[][] pildora;
    private int puntaje = 0;
    @FXML
    private Label labelPuntaje;
    private MediaPlayer mediaPlayer;
    
    private Fantasma_Clyde fantClyde;
    private Fantasma_Pinky fantPinky;
    private Fantasma_Red fantRed;
    
    private Timeline timelineFantasmasComestibles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pacman = new Pacman(9, 4, nivel);
        for (int i = 0; i < 3; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/corazon.png"));
            ImageView imageView = new ImageView(image);
            gridVidas.add(imageView, i, 0);
        }
        fantClyde = new Fantasma_Clyde(nivel);
        fantPinky = new Fantasma_Pinky(nivel);
        fantRed = new Fantasma_Red(nivel);
        
        transition = new TranslateTransition();
        for (int i = 0; i < nivel.length; i++) {
            for (int j = 0; j < nivel[i].length; j++) {
                if(nivel[i][j] == 1){
                    Image image = new Image(getClass().getResourceAsStream("/images/muro.png"));
                    ImageView imageView = new ImageView(image);
                    grid.add(imageView, j, i);
                }
            }
        }
        monedas = new ImageView[nivel.length][nivel[0].length];
        for (int i = 0; i < nivel.length; i++) {
            for (int j = 0; j < nivel[i].length; j++) {
                if(nivel[i][j] == 0){
                    Image image = new Image(getClass().getResourceAsStream("/images/moneda.png"));
                    ImageView imageView = new ImageView(image);
                    monedas[i][j] = imageView;
                    grid.add(imageView, j, i);
                }
            }
        }
        pildora = new ImageView[nivel.length][nivel[0].length];
        for (int i = 0; i < nivel.length; i++) {
            for (int j = 0; j < nivel[i].length; j++) {
                if(nivel[i][j] == 3){
                    Image image = new Image(getClass().getResourceAsStream("/images/pildora.png"));
                    ImageView imageView = new ImageView(image);
                    pildora[i][j] = imageView; 
                    grid.add(imageView, j, i);
                }
            }
        }
        
        grid.add(pacman.getImageView(), pacman.getPosicionColumna(), pacman.getPosicionFila());
        grid.add(fantClyde.getImageView(), fantClyde.getX(), fantClyde.getY());
        grid.add(fantPinky.getImageView(), fantPinky.getX(), fantPinky.getY());
        grid.add(fantRed.getImageView(), fantRed.getX(), fantRed.getY());
        
        Platform.runLater(() -> {
        grid.requestFocus();
        Media sound = new Media(getClass().getResource("/sonido/comermoneda.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            int direccionClyde = (int) (Math.random() * 4) + 1;
            moverFantasma(fantClyde, direccionClyde);
            int direccionRed = (int) (Math.random() * 4) + 1;
            moverFantasma(fantRed, direccionRed);
            int direccionPinky = (int) (Math.random() * 4) + 1;
            moverFantasma(fantPinky, direccionPinky);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        });
    }    
    @FXML
    public void mover(KeyEvent event) {
        
        switch (event.getCode()) {
            case UP:
            if (pacman.getPosicionFila() > 0 && nivel[pacman.getPosicionFila() - 1][pacman.getPosicionColumna()] != 1) {
                pacman.moverArriba();
                transition.setToY(pacman.getPosicionFila() * 40);
                transition.playFromStart();
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 0) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(monedas[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    
                    puntaje++;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 3) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(pildora[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    pacmanComePildoraEnergia();
                    puntaje+=5;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
            }
            break;
        case DOWN:
            if (pacman.getPosicionFila() < nivel.length - 1 && nivel[pacman.getPosicionFila() + 1][pacman.getPosicionColumna()] != 1) {
                pacman.moverAbajo();
                transition.setToY(pacman.getPosicionFila() * 40);
                transition.playFromStart();
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 0) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(monedas[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane

                    puntaje++;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 3) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(pildora[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    pacmanComePildoraEnergia();
                    puntaje+=5;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
            }
            break;
        case LEFT:
            if (pacman.getPosicionColumna() > 0 && nivel[pacman.getPosicionFila()][pacman.getPosicionColumna() - 1] != 1) {
                pacman.moverIzquierda();
                transition.setToX(pacman.getPosicionColumna() * 40);
                transition.playFromStart();
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 0) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(monedas[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    // Aquí puedes agregar código para incrementar el puntaje, por ejemplo:
                    puntaje++;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 3) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(pildora[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    pacmanComePildoraEnergia();
                    puntaje+=5;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
            }
            break;
        case RIGHT:
            if (pacman.getPosicionColumna() < nivel[0].length - 1 && nivel[pacman.getPosicionFila()][pacman.getPosicionColumna() + 1] != 1) {
                pacman.moverDerecha();
                transition.setToX(pacman.getPosicionColumna() * 40);
                transition.playFromStart();
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 0) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(monedas[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    puntaje++;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
                if (nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] == 3) { // Comprueba si hay una moneda en la nueva posición
                    nivel[pacman.getPosicionFila()][pacman.getPosicionColumna()] = 2; // Cambia el valor de la celda a 2 (o cualquier otro valor) para indicar que la moneda ha sido comida
                    grid.getChildren().remove(pildora[pacman.getPosicionFila()][pacman.getPosicionColumna()]); // Elimina el ImageView de la moneda del GridPane
                    pacmanComePildoraEnergia();
                    puntaje+=5;
                    labelPuntaje.setText("Puntaje: " + puntaje);
                }
            }
            break;
        }
        GridPane.setRowIndex(pacman.getImageView(), pacman.getPosicionFila());
        GridPane.setColumnIndex(pacman.getImageView(), pacman.getPosicionColumna());
        
        GridPane.setRowIndex(fantClyde.getImageView(), fantClyde.getY());
        GridPane.setColumnIndex(fantClyde.getImageView(), fantClyde.getX());
        try {
        // Pausa el hilo durante 200 milisegundos
        Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (pacmanAtrapadoPorFantasma()) {
            gridVidas.getChildren().remove(gridVidas.getChildren().size() - 1);
            if (gridVidas.getChildren().isEmpty()) {
                terminarJuego();
            }
        }
        // Comprueba si el Pacman ha comido un fantasma
        if (fantClyde.isComestible() && fantClyde.pacmanAtrapaFantasma(pacman)) {
            fantClyde.setReiniciar();
            puntaje += 200; // Aumenta el puntaje en 200 puntos
            labelPuntaje.setText("Puntaje: " + puntaje);
        }
        if (fantPinky.isComestible() && fantPinky.pacmanAtrapaFantasma(pacman)) {
            fantPinky.setReiniciar();
            puntaje += 200; // Aumenta el puntaje en 200 puntos
            labelPuntaje.setText("Puntaje: " + puntaje);
        }
        if (fantRed.isComestible() && fantRed.pacmanAtrapaFantasma(pacman)) {
            fantRed.setReiniciar();
            puntaje += 200; // Aumenta el puntaje en 200 puntos
            labelPuntaje.setText("Puntaje: " + puntaje);
        }
        int numeroDeMonedas = 0;
            for (int i = 0; i < nivel.length; i++) {
                for (int j = 0; j < nivel[0].length; j++) {
                    if (nivel[i][j] == 0) {
                        numeroDeMonedas++;
                    }
                }
            }
            if (numeroDeMonedas == 0) {
                cargarSiguienteNivel();
        }
    }
    private void moverFantasma(Fantasmas fantasma, int direccion) {
    switch (direccion) {
        case 1:
            fantasma.moverArriba();
            break;
        case 2:
            fantasma.moverAbajo();
            break;
        case 3:
            fantasma.moverIzquierda();
            break;
        case 4:
            fantasma.moverDerecha();
            break;
    }
    GridPane.setRowIndex(fantasma.getImageView(), fantasma.getY());
    GridPane.setColumnIndex(fantasma.getImageView(), fantasma.getX());
    }
    public void pacmanComePildoraEnergia() {
        // Supongamos que `fantClyde`, `fantPinky` y `fantRed` son instancias de `Fantasma_Clyde`, `Fantasma_Pinky` y `Fantasma_Red` respectivamente
        fantClyde.setComestible(true);
        fantPinky.setComestible(true);
        fantRed.setComestible(true);
        
        timelineFantasmasComestibles = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            fantClyde.setReiniciar();
            fantPinky.setReiniciar();
            fantRed.setReiniciar();
        }));
        timelineFantasmasComestibles.play();
    }
    public boolean pacmanAtrapadoPorFantasma() {
        // Supongamos que `fantClyde`, `fantPinky` y `fantRed` son instancias de `Fantasma_Clyde`, `Fantasma_Pinky` y `Fantasma_Red` respectivamente
        if (!fantClyde.isComestible() && fantClyde.pacmanAtrapaFantasma(pacman)) {
            return true;
        }
        if (!fantPinky.isComestible() && fantPinky.pacmanAtrapaFantasma(pacman)) {
            return true;
        }
        if (!fantRed.isComestible() && fantRed.pacmanAtrapaFantasma(pacman)) {
            return true;
        }
        return false;
    }
    public void terminarJuego() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("¡Has perdido todas tus vidas!");

        alert.showAndWait();

        try {
            App.setRoot("PantallaPrincipal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cargarSiguienteNivel() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You win");
        alert.setHeaderText(null);
        alert.setContentText("¡Has superado el nivel!");

        alert.showAndWait();

        try {
            App.setRoot("Niveles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

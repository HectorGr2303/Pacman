package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Fantasma_Clyde extends Fantasmas {
    
    
    public Fantasma_Clyde(int[][] nivel) {
        super(nivel);
        Image image = new Image(getClass().getResourceAsStream("/images/ghost_orange.png"));
        imageView.setImage(image);
        x = 4;
        y = 4;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        direccion = 2;
        comestible = false;
        activo = true;
        
    }

    public void setReiniciar() {
        Image image = new Image(getClass().getResourceAsStream("/images/ghost_orange.png"));
        x = 4;
        y = 4;
        imageView.setImage(image);
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }
    

    public void cambiarDireccion() {
        int numeroAleatorio;
        do {
            numeroAleatorio = (int) (Math.random() * 4 + 1);
        } while (direccion == numeroAleatorio);

        switch (numeroAleatorio) {
            case 1:
                direccion = 3;
                break;
            case 2:
                direccion = 4;
                break;
            case 3:
                direccion = 1;
                break;
            case 4:
                direccion = 2;
                break;
        }
    }
    public boolean pacmanAtrapaFantasma(Pacman pacman) {
        // Comprueba si el Pacman ha atrapado al fantasma
        if (pacman.getPosicionFila() == this.y && pacman.getPosicionColumna() == this.x) {
            return true;
        } else {
            return false;
        }
    }
}


package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Fantasma_Pinky extends Fantasmas {

    public Fantasma_Pinky(int[][] nivel) {
        super(nivel);
        Image image = new Image(getClass().getResourceAsStream("/images/ghost_pink.png"));
        imageView.setImage(image);
        x = 3;
        y = 4;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        direccion = 2;
        comestible = false;
        activo = true;
    }

    public void setReiniciar() {
        Image image = new Image(getClass().getResourceAsStream("/images/ghost_pink.png"));
        x = 4;
        y = 4;
        imageView.setImage(image);
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }

    public void cambiarDireccion(int posPacX, int posPacY, int direccionAntigua) {
        switch (direccionAntigua) {
            case 1:
                if (posPacY < y) {
                    direccion = 2;
                } else if (posPacY > y) {
                    direccion = 4;
                }
                break;
            case 2:
                if (posPacX < x) {
                    direccion = 1;
                } else if (posPacX > x) {
                    direccion = 3;
                }
                break;
            case 3:
                if (posPacY < y) {
                    direccion = 2;
                } else if (posPacY > y) {
                    direccion = 4;
                }
                break;
            case 4:
                if (posPacX < x) {
                    direccion = 1;
                } else if (posPacX > x) {
                    direccion = 3;
                }
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

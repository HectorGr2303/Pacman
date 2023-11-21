package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pacman {
    private int posicionFila;
    private int posicionColumna;
    private ImageView imageView;
    private int[][] nivel;
    private int vidas;
    
    public Pacman(int posicionFilaInicial, int posicionColumnaInicial, int [][] nivel) {
        Image image = new Image(getClass().getResourceAsStream("/images/sprite.png"));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(15);
        this.imageView.setFitHeight(15);
        this.posicionFila = posicionFilaInicial;
        this.posicionColumna = posicionColumnaInicial;
        this.nivel = nivel;
        this.vidas = 3;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPosicionFila() {
        return posicionFila;
    }

    public void setPosicionFila(int posicionFila) {
        this.posicionFila = posicionFila;
    }

    public int getPosicionColumna() {
        return posicionColumna;
    }

    public void setPosicionColumna(int posicionColumna) {
        this.posicionColumna = posicionColumna;
    }
    
        public int getVidas() {
        return vidas;
    }

    public void perderVida() {
        this.vidas--;
    }

    public boolean estaVivo() {
        return this.vidas > 0;
    }
    
    public void moverArriba() {
    posicionFila--;
    }

    public void moverAbajo() {
        posicionFila++;
    }

    public void moverIzquierda() {
        posicionColumna--;
    }

    public void moverDerecha() {
        posicionColumna++;
    }
}

package model;
   
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Fantasmas {
    protected int x, y; // Variables donde se almacena la posición del fantasma x, y
    protected ImageView imageView; // Variable donde se almacena la imagen del fantasma.
    protected int width; // Ancho.
    protected int height; // Alto.
    protected int direccion; // 1 izquierda, 2 arriba, 3 derecha, 4 abajo.
    protected boolean activo; // Para determinar si se encuentra activo.
    protected boolean comestible; // Para determinar si el fantasma es comestible.
    private Image imagen;
    private Timeline timer;
    private int nivel [][];
    
    public Fantasmas(int[][] nivel) {
        Image image = new Image(getClass().getResourceAsStream("/images/ghost_teal.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(22); 
        imageView.setFitHeight(22); 
        x = 4;
        y = 4;
        width = (int) imageView.getFitWidth();
        height = (int) imageView.getFitHeight();
        direccion = 1;
        comestible = false;
        activo = true;
        this.nivel = nivel;
        
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isComestible() {
        return comestible;
    }

//    public void setComestible(boolean activo) {
//        final Image imagenAnterior = imagen;
//
//        if (activo == true) {
//            comestible = true;
//            imagen = new Image(getClass().getResourceAsStream("/images/ghost_teal.png"));
//            imageView.setImage(imagen);
//            timer.play();
//            activo = false;
//        } else {
//            timer.stop();
//            timer.getKeyFrames().clear();
//        }
//    }
    public void setComestible(boolean comestible) {
        this.comestible = comestible;
        if (comestible) {
            // Cambia la imagen del fantasma a la imagen de fantasma comestible
            Image image = new Image(getClass().getResourceAsStream("/images/ghost_teal.png"));
            imageView.setImage(image);

            // Inicia un Timeline que dura 10 segundos (o el tiempo que quieras que el fantasma sea comestible)
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                this.comestible = false;
                
            }));
            timeline.play();
        }
    }


    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getDireccion() {
        return direccion;
    }

    public void moverDerecha() {
        if(x < nivel[0].length - 1 && nivel[y][x + 1] != 1){
            x = x + 1;
            imageView.setTranslateX(x);
        }
        
    }

    public void moverIzquierda() {
        if(x > 0 && nivel[y][x - 1] != 1){
            x = x - 1;
            imageView.setTranslateX(x);
        }
        
    }

    public void moverAbajo() {
        if(y < nivel.length - 1 && nivel[y + 1][x] != 1){
            y = y + 1;
            imageView.setTranslateY(y);
        }
        
    }

    public void moverArriba() {
        if(y > 0 && nivel[y - 1][x] != 1){
            y = y - 1;
            imageView.setTranslateY(y);
        }
        
    }

    public void cambiarDireccion(int direccionAntigua) {
        switch (direccionAntigua) {
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
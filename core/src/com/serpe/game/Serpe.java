package com.serpe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.Point;
import com.badlogic.gdx.math.Rectangle;
import java.util.LinkedList;

public class Serpe {
    protected Vector2 posicion;
    private LinkedList<Point> body = new LinkedList<Point>();
    private Rectangle rectangulo;


    public Serpe(Vector2 posicion) {
        this.posicion = posicion;
        Point p = new Point((int)posicion.x,(int)posicion.y);
        body.add(p);
        rectangulo = new Rectangle();
        setTamanoRectangulo(28,28);
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setTamanoRectangulo(float width,float height){
        rectangulo.setWidth(width);
        rectangulo.setHeight(height);
    }
    public void actualizarRectangulo(){
        rectangulo.x=posicion.x;
        rectangulo.y=posicion.y;
    }
    public Rectangle getRectangulo(){
        return rectangulo;
    }

    public void setPosicion(Vector2 posicion) {

        this.posicion = posicion;
        actualizarRectangulo();
    }


    public void setPosicion(float x, float y) {
        posicion.x = x;
        posicion.y = y;
        actualizarRectangulo();
    }


    /**
     * Actualiza a posición en función da velocidade
     *
     * @param delta: tempo entre unha chamada e a seguinte
     */

    public void update(float delta) {
        posicion.set(getPosicion().x + 1, getPosicion().y);//aumento la x en 1
    }
}

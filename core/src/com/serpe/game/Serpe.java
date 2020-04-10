package com.serpe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Serpe {




    public Serpe(Vector2 posicion) {


        this.posicion = posicion;
    }


    protected Vector2 posicion;


    public Vector2 getPosicion() {

        return posicion;

    }


    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }


    public void setPosicion(float x, float y) {

        posicion.x = x;

        posicion.y = y;

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

package com.coronavirus.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Xogo extends Game {
    private static final String LOG = "SerpeLogger";

     private PantallaXogo pantallaxogo;

    @Override
    public void create() {
        Gdx.app.log(LOG, "Xogo creado");
        pantallaxogo = new PantallaXogo(this);
        setScreen(pantallaxogo);
    }

    @Override
    public void render(){
        pantallaxogo.render(1f);
    }

    @Override
    public void dispose(){
        super.dispose();
        pantallaxogo.dispose();

    }

}

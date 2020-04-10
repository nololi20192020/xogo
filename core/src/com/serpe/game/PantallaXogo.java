package com.serpe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import javax.swing.text.View;

public class PantallaXogo implements Screen, InputProcessor {
    private static final String LOG = "PantallaXogoLogger";

    SpriteBatch batch;
    private Texture cabezaSerpe;
    private Texture corpoSerpe;
    private Texture coronaVirus;
    private Xogo xogo;
    private static int tamanoCuadro = 28;

    private int i = 0;

    private BitmapFont bitMapFont;

    private Serpe serpe;

    private boolean iniciado;
    private boolean pause;
    private boolean finxogo;
    private boolean sair;

    public PantallaXogo(Xogo xogo) {
        this.xogo = xogo;
        batch = new SpriteBatch();
        serpe = new Serpe(new Vector2(28, 0));
        cabezaSerpe = new Texture("cabezaserpe.png");
        corpoSerpe = new Texture("corposerpe.png");
        coronaVirus = new Texture("coronavirus.png");
        bitMapFont = new BitmapFont();
        iniciado = false;
        pause = false;
        finxogo = false;
        sair = false;
    }

    @Override
    public void show() {
        Gdx.app.log(LOG, "Show PantallaXogoLogger");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (iniciado) {//juego
            Gdx.app.log(LOG, "Xogo  iniciado");
            //TODO a método aparte ?
            i++;
            batch.draw(cabezaSerpe, 28 + i, 0);
            batch.draw(corpoSerpe, 0 + i, 0);
            batch.draw(coronaVirus, 136, 28);

        } else {//no iniciado
            bitMapFont.setColor(Color.BLACK);
            bitMapFont.draw(batch, "Pulse intro para iniciar o xogo", 250, 250);
        }


        batch.end();
    }

    @Override
    public void resize(int i, int i1) {
        Gdx.app.log(LOG, "Resize PantallaXogoLogger");
    }

    @Override
    public void pause() {
        Gdx.app.log(LOG, "Pause PantallaXogoLogger");
    }

    @Override
    public void resume() {
        Gdx.app.log(LOG, "Resume PantallaXogoLogger");
    }

    @Override
    public void hide() {
        Gdx.app.log(LOG, "Hide PantallaXogoLogger");
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG, "Dispose PantallaXogoLogger");
    }


    @Override
    public boolean keyDown(int i) {

        if (Input.Keys.UP == i) {
            Gdx.app.log(LOG, "KEYUP" + i);

            //mover hacia arriba
        } else if (Input.Keys.DOWN == i) {
            Gdx.app.log(LOG, "down" + i);
            //TODO mover hacia abajo
        } else if (Input.Keys.LEFT == i) {
            Gdx.app.log(LOG, "left" + i);
            //mover izquierda
        } else if (Input.Keys.RIGHT == i) {
            Gdx.app.log(LOG, "right" + i);
            //mover derecha
        } else if (i == 66) {//buscar correspondencia
            Gdx.app.log(LOG, "intro" + i);
            iniciado = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}

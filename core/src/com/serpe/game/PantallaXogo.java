package com.serpe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;


public class PantallaXogo implements Screen, InputProcessor {
    private static final String LOG = "PantallaXogoLogger";

    SpriteBatch batch;
    private Texture enfermeiro;
    private Texture coronaVirus;
    private Texture enfermo;
    private Xogo xogo;


    private BitmapFont bitMapFont;

    private Persoa persoaEnfermeiro;
    private Persoa persoaEnfermo;
    List<Coronavirus> coronavirus_Elements;

    private boolean iniciado;
    private boolean pause;
    private boolean finxogo;
    private boolean sair;

    private int contador, multiplicador;
    Sound son;

    private String direccion = "DERECHA";

    public PantallaXogo(Xogo xogo) {
        this.xogo = xogo;
        finxogo=false;
        contador = 0;
        multiplicador = 1;

        batch = new SpriteBatch();
        //creo el enfermero
        persoaEnfermeiro = new Persoa(new Vector2(150, 0));
        persoaEnfermeiro.actualizarRectangulo();
        persoaEnfermo = new Persoa(new Vector2(28, 28));

        String localStorage = Gdx.files.getLocalStoragePath() + "\\desktop\\build\\resources\\main\\";
        son = Gdx.audio.newSound(Gdx.files.internal(localStorage + "berro.mp3"));
        enfermeiro = new Texture(localStorage + "enfermeiro.jpg");
        enfermo = new Texture(localStorage + "enfermo.jpg");
        coronaVirus = new Texture(localStorage + "coronavirus.png");
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
        if (iniciado && finxogo==false) {//juego
            bitMapFont.draw(batch, "Puntuacion : " + contador, 0, 490);
            if (Intersector.overlaps(persoaEnfermeiro.getRectangulo(), persoaEnfermo.getRectangulo())) {
                //engadir 1 obstáculo
                crearVirus(true);

                son.play();//cambiar a aplausos
                contador += 50 * multiplicador;
                multiplicador++;
                crearEnfermo(true);
            }
           crearVirus(false);
            for (int i = 0; i < coronavirus_Elements.size(); i++) {
                batch.draw(coronaVirus, coronavirus_Elements.get(i).getPosicion().x,
                        coronavirus_Elements.get(i).getPosicion().y);
            }
            moverEnfermeiro();
            batch.draw(enfermeiro, persoaEnfermeiro.getPosicion().x, persoaEnfermeiro.getPosicion().y);
            crearEnfermo(false);
            batch.draw(enfermo, persoaEnfermo.getPosicion().x, persoaEnfermo.getPosicion().y);
        } else if(finxogo){//fin xogo
            bitMapFont.setColor(Color.BLACK);
            bitMapFont.draw(batch, "Fin do xogo, puntuación " + contador, 250, 250);
        }else if(iniciado=false){//no iniciado y no fin de partida
            bitMapFont.setColor(Color.BLACK);
            bitMapFont.draw(batch, "Prema intro para iniciar o xogo", 250, 250);
        }
        batch.end();
    }


    private void moverEnfermeiro() {
        for(int i=0;i<coronavirus_Elements.size();i++){
            if (Intersector.overlaps(persoaEnfermeiro.getRectangulo(), coronavirus_Elements.get(i).getRectangulo())) {
                son.play();
                finxogo=true;
                iniciado=false;
            }
        }
        float x = persoaEnfermeiro.getPosicion().x;
        float y = persoaEnfermeiro.getPosicion().y;

        //para que si sale por los lados, aparezca por el opuesto
        if (x == 700) {
            x = 0;
        }
        if (y == 500) {
            y = 0;
        }
        if (x < 0) {
            x = 700;
        }
        if (y < 0) {
            y = 500;
        }


        if (direccion.equals("ARRIBA")) {
            persoaEnfermeiro.setPosicion(x, y + 1);//update en objeto
        } else if (direccion.equals("ABAIXO")) {
            persoaEnfermeiro.setPosicion(x, y - 1);
        } else if (direccion.equals("ESQUERDA")) {
            persoaEnfermeiro.setPosicion(x - 1, y);

        } else {//dereita por defecto
            persoaEnfermeiro.setPosicion(x + 1, y);
        }

        persoaEnfermeiro.actualizarRectangulo();
    }

    private void crearEnfermo(boolean novo) {
        int posX = (int) persoaEnfermo.getPosicion().x;
        int posY = (int) persoaEnfermo.getPosicion().y;
        boolean coincide=true;
        if (novo) {
            while(coincide) {
                posX = (int) (Math.random() * 644 + 28);
                posY = (int) (Math.random() * 444 + 28);
                persoaEnfermo.setPosicion(posX, posY);
                persoaEnfermeiro.actualizarRectangulo();
                for (int i = 0; i < coronavirus_Elements.size(); i++) {
                    if (Intersector.overlaps(persoaEnfermo.getRectangulo(), coronavirus_Elements.get(i).getRectangulo())) {
                        coincide=true;
                        break;
                    }
                    coincide=false;
                }
            }
        }
        persoaEnfermo.setPosicion(posX, posY);
    }

    private void crearVirus(boolean novo) {//meter en array de virus
        //si es nuevo añado un nuevo virus
        float posX = 0;
        float posY = 0;
        if (novo) {//si es nuevo
            posX = (float) (Math.random() * 644 + 28);
            posY = (float) (Math.random() * 444 + 28);
            Coronavirus coronavirusElement = new Coronavirus(new Vector2(posX, posY));
            coronavirusElement.actualizarRectangulo();
            coronavirus_Elements.add(coronavirusElement);//añado elemento a la lista

        }
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
        son.dispose();
    }


    @Override
    public boolean keyDown(int i) {
        batch.begin();
        if (Input.Keys.UP == i) {
            Gdx.app.log(LOG, "KEYUP" + i);
            direccion = "ARRIBA";
        } else if (Input.Keys.DOWN == i) {
            Gdx.app.log(LOG, "down" + i);
            direccion = "ABAIXO";
        } else if (Input.Keys.LEFT == i) {
            Gdx.app.log(LOG, "left" + i);
            direccion = "ESQUERDA";
        } else if (Input.Keys.RIGHT == i) {
            Gdx.app.log(LOG, "right" + i);
            direccion = "DEREITA";
        } else if (i == 66) {//TODO buscar correspondencia
            Gdx.app.log(LOG, "intro" + i);
            iniciado = true;
            finxogo=false;
            coronavirus_Elements=new ArrayList<>();
            contador=0;
        }
        batch.end();
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

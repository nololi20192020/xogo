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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;


public class PantallaXogo implements Screen, InputProcessor {
    private static final String LOG = "PantallaXogoLogger";

    SpriteBatch batch;
    private Texture cabezaSerpe;
    private Texture coronaVirus;
    private Xogo xogo;
    private static int tamanoCuadro = 28;


    private BitmapFont bitMapFont;

    private Serpe serpe;
    private Coronavirus virus ;

    private boolean iniciado;
    private boolean pause;
    private boolean finxogo;
    private boolean sair;


    private  String direccion = "DERECHA";

    public PantallaXogo(Xogo xogo) {
        this.xogo = xogo;
        batch = new SpriteBatch();
        serpe = new Serpe(new Vector2(28, 0));
        virus= new Coronavirus(new Vector2(28,28));

        System.out.println(Gdx.files.getLocalStoragePath());
        String localStorage = Gdx.files.getLocalStoragePath() + "\\desktop\\build\\resources\\main\\";
        cabezaSerpe = new Texture(localStorage + "cabezaserpe.png");
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
        if (iniciado) {//juego
            Gdx.app.log(LOG, "Xogo  iniciado");

            //TODO comprobar colision con virus * 1 elemento
            if (Intersector.overlaps(serpe.getRectangulo(), virus.getRectangulo())){
                System.out.println("Tocaod");
                //añadir 1
                int posicion = serpe.corpoSerpeSize();
                Serpe serpe1 = new Serpe(new Vector2(serpe.getPosicion().x-posicion*28,
                        (float)serpe.getPosicion().y-posicion*28));
                serpe.addCorpoSerpe(serpe1);

               debuxarVirus(batch,true);
            }
            //TODO a método aparte ?
            debuxarSerpe(batch);
            debuxarVirus(batch,false);



        } else {//no iniciado

            bitMapFont.setColor(Color.BLACK);
            bitMapFont.draw(batch, "Pulse intro para iniciar o xogo", 250, 250);
        }


        batch.end();
    }


    private void debuxarVirus(SpriteBatch batch, boolean novo) {
        int posX = (int) virus.getPosicion().x;
        int posY = (int) virus.getPosicion().y;
        if(novo){//TODO comprobar que no colisione con la serpiente
             posX = (int) (Math.random() * 700 + 0);
            posY = (int) (Math.random() * 500 + 0);
        }
        virus.setPosicion(posX,posY);
        System.out.println("Debuxando coronavirus en pos X " + posX + " pos y " + posY);
        batch.draw(coronaVirus, virus.getPosicion().x, virus.getPosicion().y);
    }


    private void debuxarSerpe(SpriteBatch batch) {
        float x = serpe.getPosicion().x;
        float y = serpe.getPosicion().y;

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
            serpe.setPosicion(x, y + 1);
            batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y);
            //debuxar corpo
            if(serpe.corpoSerpeSize()>1){//debuxo corpo serpe
                for(int i=1;i<serpe.corpoSerpeSize();i++){

                    batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y-(28*i));
                }

            }

            System.out.println("Xarriba " + serpe.getPosicion().x + "  " + serpe.getPosicion().y);
            return;
        } else if (direccion.equals("ABAIXO")) {
            serpe.setPosicion(x, y - 1);
            batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y);
            //debuxar corpo
            if(serpe.corpoSerpeSize()>1){//debuxo corpo serpe
                for(int i=1;i<serpe.corpoSerpeSize();i++){
                    batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y+(28*i));
                }

            }
            System.out.println("Xabaixo " + serpe.getPosicion().x + "  " + serpe.getPosicion().y);
        } else if (direccion.equals("ESQUERDA")) {
            serpe.setPosicion(x - 1, y);
            batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y);
            //debuxar corpo
            if(serpe.corpoSerpeSize()>1){//debuxo corpo serpe
                for(int i=1;i<serpe.corpoSerpeSize();i++){
                    batch.draw(cabezaSerpe, serpe.getPosicion().x-(28*i), serpe.getPosicion().y);
                }

            }
            System.out.println("XEsquerda " + serpe.getPosicion().x + "  " + serpe.getPosicion().y);
        } else {//dereita por defecto
            serpe.setPosicion(x + 1, y);
            batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y);
            if(serpe.corpoSerpeSize()>1){//debuxo corpo serpe
                for(int i=1;i<serpe.corpoSerpeSize();i++){
                    batch.draw(cabezaSerpe, serpe.getPosicion().x+(28*i), serpe.getPosicion().y);
                }

            }
            System.out.println("Xnormal " + serpe.getPosicion().x + "  " + serpe.getPosicion().y);
        }
        if(serpe.corpoSerpeSize()>1){
            System.out.println("Tiene cola");
        }else{
            System.out.println("No tiene cola");
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
    }


    @Override
    public boolean keyDown(int i) {
        batch.begin();
        if (Input.Keys.UP == i) {
            Gdx.app.log(LOG, "KEYUP" + i);//TODO si marco esto, mantengo x, sumo y
            direccion = "ARRIBA";
        } else if (Input.Keys.DOWN == i) {//TODO si marco esto, mantengo x, resto y
            Gdx.app.log(LOG, "down" + i);
            direccion = "ABAIXO";
        } else if (Input.Keys.LEFT == i) {//TODO si marco esto, mantengo y, resto x
            Gdx.app.log(LOG, "left" + i);
            direccion = "ESQUERDA";
        } else if (Input.Keys.RIGHT == i) { ///TODO si marco esto, mantengo y, sumo x
            Gdx.app.log(LOG, "right" + i);
            direccion = "DEREITA";
        } else if (i == 66) {//buscar correspondencia
            Gdx.app.log(LOG, "intro" + i);
            iniciado = true;
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

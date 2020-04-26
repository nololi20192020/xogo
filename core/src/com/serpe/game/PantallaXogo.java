package com.serpe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
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
    private Texture corpoSerpe;
    private Xogo xogo;
    private float posX = 0;
    private float posY = 0;

    private BitmapFont bitMapFont;

    private Serpe serpe;
    private Coronavirus virus;

    private boolean iniciado;
    private boolean pause;
    private boolean finxogo;
    private boolean sair;

    private int contador,multiplicador;
    Sound son;

    private String direccion = "DERECHA";

    public PantallaXogo(Xogo xogo) {
        this.xogo = xogo;
        contador =0;
        multiplicador=0;


        batch = new SpriteBatch();
        //creo la serpiente con 2 rectángulos de cuerpo
        serpe = new Serpe(new Vector2(150, 0));
        serpe.addCorpoSerpe(new Serpe(new Vector2(122, 0)));
        serpe.addCorpoSerpe(new Serpe(new Vector2(94, 0)));
        virus = new Coronavirus(new Vector2(28, 28));

        String localStorage = Gdx.files.getLocalStoragePath() + "\\desktop\\build\\resources\\main\\";
        son = Gdx.audio.newSound(Gdx.files.internal(localStorage +"berro.mp3"));
        cabezaSerpe = new Texture(localStorage + "cabSerpe.png");
        corpoSerpe = new Texture(localStorage + "corpoSerpe.png");
        coronaVirus = new Texture(localStorage + "coronavirus.png");
        bitMapFont = new BitmapFont();

        iniciado = false;
        pause = false;
        finxogo = false;
        sair = false;
        System.out.println(contador);
    }

    @Override
    public void show() {
        Gdx.app.log(LOG, "Show PantallaXogoLogger");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        System.out.println(contador);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (iniciado) {//juego
            bitMapFont.draw(batch, "Puntuacion : "+contador, 0,490);
            if (Intersector.overlaps(serpe.getRectangulo(), virus.getRectangulo())) {
                int posicion = serpe.corpoSerpeSize();
                //engadir 1 elemento
                Serpe serpe1 = new Serpe(new Vector2(serpe.getPosicion().x - posicion * 28,
                        (float) serpe.getPosicion().y - posicion * 28));
                serpe.addCorpoSerpe(serpe1);
                son.play();
                contador +=50*multiplicador;
                multiplicador++;
                debuxarVirus(true);
            }
            moverSerpe();
            for (int i = serpe.getCorpoSerpe().size() - 1; i > 0; i--) {
                batch.draw(corpoSerpe, serpe.getCorpoSerpe().get(i).getPosicion().x,
                        serpe.getCorpoSerpe().get(i).getPosicion().y);
            }
            batch.draw(cabezaSerpe, serpe.getPosicion().x, serpe.getPosicion().y);
            debuxarVirus(false);
            batch.draw(coronaVirus, virus.getPosicion().x, virus.getPosicion().y);
        } else {//no iniciado
            bitMapFont.setColor(Color.BLACK);
            bitMapFont.draw(batch, "Pulse intro para iniciar o xogo", 250, 250);
        }
        batch.end();
    }



    private void moverSerpe() {
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
            if (serpe.getCorpoSerpe().size() > 1) {
                for (int i = serpe.getCorpoSerpe().size() - 1; i > 0; i--) {
                    float posXPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().x;
                    float posYPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().y;
                    serpe.getCorpoSerpe().get(i).setPosicion(posXPredecesor, posYPredecesor - (28));
                }
            }
            serpe.getCorpoSerpe().get(0).setPosicion(x, y + 1);//update en array
            serpe.setPosicion(x, y + 1);//update en objeto
        } else if (direccion.equals("ABAIXO")) {
            if (serpe.getCorpoSerpe().size() > 1) {
                for (int i = serpe.getCorpoSerpe().size() - 1; i > 0; i--) {
                    float posXPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().x;
                    float posYPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().y;
                    serpe.getCorpoSerpe().get(i).setPosicion(posXPredecesor, posYPredecesor + (28));
                }
            }
            serpe.getCorpoSerpe().get(0).setPosicion(x, y - 1);
            serpe.setPosicion(x, y - 1);
        } else if (direccion.equals("ESQUERDA")) {
            if (serpe.getCorpoSerpe().size() > 1) {
                System.out.println("Ten corpo");
                for (int i = serpe.getCorpoSerpe().size() - 1; i > 0; i--) {
                    System.out.println("Debuxar cola " + i);
                    float posXPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().x;
                    float posYPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().y;
                    serpe.getCorpoSerpe().get(i).setPosicion(posXPredecesor + 28, posYPredecesor);
                }
            }
            serpe.getCorpoSerpe().get(0).setPosicion(x - 1, y);
            serpe.setPosicion(x - 1, y);

        } else {//dereita por defecto
            if (serpe.getCorpoSerpe().size() > 1) {
                System.out.println("Ten corpo");
                for (int i = serpe.getCorpoSerpe().size() - 1; i > 0; i--) {
                    System.out.println("Debuxar cola " + i);
                    float posXPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().x;
                    float posYPredecesor = serpe.getCorpoSerpe().get(i - 1).getPosicion().y;
                    serpe.getCorpoSerpe().get(i).setPosicion(posXPredecesor - 28, posYPredecesor);
                }
            }
            serpe.getCorpoSerpe().get(0).setPosicion(x + 1, y);
            serpe.setPosicion(x + 1, y);
        }
    }

    private void debuxarVirus(boolean novo) {
        int posX = (int) virus.getPosicion().x;
        int posY = (int) virus.getPosicion().y;
        if (novo) {//TODO comprobar que no colisione con la serpiente al crear
            posX = (int) (Math.random() * 672 + 28);
            posY = (int) (Math.random() * 472 + 28);
        }
        virus.setPosicion(posX, posY);
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

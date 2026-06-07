package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class Lluvia implements Screen {
    private final GameLluvia game;
    private OrthographicCamera camera;
    private Texture texturaSteve;
    private Sound sonidoDrop;
    private Sound sonidoHurt;
    private Music musicaLluvia;
    private Jugador jugador;
    private Array<ObjetoCayendo> objetosCayendo;
    private long tiempoUltimoObjeto;
    private Vector3 touchPos;

    private DirectorNivel directorNivel;
    private Nivel nivelActual;

    private boolean enTransicion;
    private float tiempoTransicion;
    private String mensajeTransicion;

    public Lluvia(final GameLluvia game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.touchPos = new Vector3();

        this.texturaSteve = new Texture(Gdx.files.internal("steve.png"));
        this.sonidoDrop = Gdx.audio.newSound(Gdx.files.internal("pickup.mp3"));
        this.sonidoHurt = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        this.musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("haggstrom.mp3"));
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();

        this.jugador = new Jugador(this.texturaSteve);
        this.objetosCayendo = new Array<>();

        this.directorNivel = new DirectorNivel();
        this.enTransicion = false;
        cambiarNivel(new BuilderOverworld());

        generarObjeto();
    }

    private void cambiarNivel(BuilderNivel builder) {
        if (this.nivelActual != null) {
            this.nivelActual.dispose();
        }
        this.directorNivel.setBuilder(builder);
        this.nivelActual = this.directorNivel.construirNivel();
    }

    private void iniciarTransicion(String mensaje, BuilderNivel builder) {
        this.enTransicion = true;
        this.tiempoTransicion = 2.0f;
        this.mensajeTransicion = mensaje;
        this.objetosCayendo.clear();
        AdministradorJuego.getInstancia().sumarVida();
        cambiarNivel(builder);
    }

    private void generarObjeto() {
        float posicionX = MathUtils.random(0, 800 - 64);
        int tipo = MathUtils.random(1, 100);
        int puntajeActual = AdministradorJuego.getInstancia().getPuntosTotales();

        int puntosNivelActual = 0;
        if (this.nivelActual.getId() == 1) {
            puntosNivelActual = puntajeActual;
        } else if (this.nivelActual.getId() == 2) {
            puntosNivelActual = puntajeActual - 500;
        } else if (this.nivelActual.getId() == 3) {
            puntosNivelActual = puntajeActual - 1000;
        }

        float multiplicadorDificultad = 1 + (puntosNivelActual / 200f);
        float velocidad = (200 * Gdx.graphics.getDeltaTime()) * multiplicadorDificultad;

        Texture[] mins = this.nivelActual.getTexturasMinerales();
        Texture[] enes = this.nivelActual.getTexturasEnemigos();

        if (this.nivelActual.getId() == 1) {
            if (tipo <= 40) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 1, mins[0], 10));
            } else if (tipo <= 60) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 2, mins[1], 20));
            } else if (tipo <= 70) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 4, mins[2], 30));
            } else if (tipo <= 85) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, enes[0], 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, enes[1], 1));
            }
        } else if (this.nivelActual.getId() == 2) {
            if (tipo <= 25) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 2, mins[0], 20));
            } else if (tipo <= 50) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 4, mins[1], 50));
            } else if (tipo <= 75) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, enes[0], 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, enes[1], 1));
            }
        } else {
            if (tipo <= 15) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 5, mins[0], 100));
            } else if (tipo <= 60) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 4, enes[0], 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 4, enes[1], 1));
            }
        }

        this.tiempoUltimoObjeto = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        int puntajeActual = AdministradorJuego.getInstancia().getPuntosTotales();

        if (puntajeActual >= 1000 && this.nivelActual.getId() != 3 && !this.enTransicion) {
            iniciarTransicion("¡VIAJANDO AL END!", new BuilderEnd());
        } else if (puntajeActual >= 500 && puntajeActual < 1000 && this.nivelActual.getId() != 2 && !this.enTransicion) {
            iniciarTransicion("¡VIAJANDO AL NETHER!", new BuilderNether());
        }

        if (this.enTransicion) {
            this.tiempoTransicion -= delta;
            ScreenUtils.clear(0, 0, 0, 1);
            this.camera.update();
            this.game.batch.setProjectionMatrix(this.camera.combined);

            this.game.batch.begin();
            this.game.font.draw(this.game.batch, this.mensajeTransicion, 330, 240);
            this.game.batch.end();

            if (this.tiempoTransicion <= 0) {
                this.enTransicion = false;
                this.tiempoUltimoObjeto = TimeUtils.nanoTime();
            }
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        this.game.batch.draw(this.nivelActual.getTexturaFondo(), 0, 0, 800, 480);
        this.game.font.draw(this.game.batch, "Puntuacion: " + puntajeActual, 10, 470);
        this.game.font.draw(this.game.batch, "Vidas: " + AdministradorJuego.getInstancia().getVidas(), 10, 450);

        this.jugador.dibujar(this.game.batch);
        for (ObjetoCayendo objeto : this.objetosCayendo) {
            objeto.dibujar(this.game.batch);
        }
        this.game.batch.end();

        this.jugador.actualizarMovimiento();

        if (Gdx.input.isTouched()) {
            this.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            this.camera.unproject(this.touchPos);

            this.jugador.getHitbox().x = this.touchPos.x - this.jugador.getHitbox().width / 2;
            this.jugador.getHitbox().y = this.touchPos.y - this.jugador.getHitbox().height / 2;

            if (this.jugador.getHitbox().x < 0) {
                this.jugador.getHitbox().x = 0;
            }
            if (this.jugador.getHitbox().x > 800 - this.jugador.getHitbox().width) {
                this.jugador.getHitbox().x = 800 - this.jugador.getHitbox().width;
            }
            if (this.jugador.getHitbox().y < 0) {
                this.jugador.getHitbox().y = 0;
            }
            if (this.jugador.getHitbox().y > 480 - this.jugador.getHitbox().height) {
                this.jugador.getHitbox().y = 480 - this.jugador.getHitbox().height;
            }
        }

        if (TimeUtils.nanoTime() - this.tiempoUltimoObjeto > this.nivelActual.getSpawnIntervalo()) {
            generarObjeto();
        }

        Iterator<ObjetoCayendo> iterador = this.objetosCayendo.iterator();
        while (iterador.hasNext()) {
            ObjetoCayendo objeto = iterador.next();
            boolean fueraDePantalla = objeto.actualizarFrameFisica();

            if (fueraDePantalla) {
                iterador.remove();
            } else if (objeto.getHitbox().overlaps(this.jugador.getHitbox())) {
                objeto.aplicarEfecto();

                if (objeto instanceof Enemigo) {
                    this.sonidoHurt.play();
                } else {
                    this.sonidoDrop.play();
                }

                iterador.remove();
            }
        }

        if (AdministradorJuego.getInstancia().getVidas() <= 0) {
            this.musicaLluvia.stop();
            this.game.setScreen(new PantallaGameOver(this.game, puntajeActual));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        this.texturaSteve.dispose();
        this.sonidoDrop.dispose();
        this.sonidoHurt.dispose();
        this.musicaLluvia.dispose();
        if (this.nivelActual != null) {
            this.nivelActual.dispose();
        }
    }
}

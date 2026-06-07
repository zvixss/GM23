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
    private Texture texturaFondoOverworld;
    private Texture texturaFondoNether;
    private Texture texturaFondoEnd;
    private Texture texturaSteve;
    private Texture texturaHierro;
    private Texture texturaOro;
    private Texture texturaDiamante;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigo2;
    private Texture texturaNetherite;
    private Texture texturaWither;
    private Texture texturaBlaze;
    private Texture texturaDragonEgg;
    private Texture texturaEnderman;
    private Texture texturaShulker;
    private Sound sonidoDrop;
    private Sound sonidoHurt;
    private Music musicaLluvia;
    private Jugador jugador;
    private Array<ObjetoCayendo> objetosCayendo;
    private long tiempoUltimoObjeto;
    private Vector3 touchPos;

    public Lluvia(final GameLluvia game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.touchPos = new Vector3();

        this.texturaFondoOverworld = new Texture(Gdx.files.internal("overworld.png"));
        this.texturaFondoNether = new Texture(Gdx.files.internal("nether.png"));
        this.texturaFondoEnd = new Texture(Gdx.files.internal("end.png"));

        this.texturaSteve = new Texture(Gdx.files.internal("steve.png"));
        this.texturaHierro = new Texture(Gdx.files.internal("hierro.png"));
        this.texturaOro = new Texture(Gdx.files.internal("oro.png"));
        this.texturaDiamante = new Texture(Gdx.files.internal("diamante.png"));
        this.texturaEnemigo1 = new Texture(Gdx.files.internal("enemigo1.png"));
        this.texturaEnemigo2 = new Texture(Gdx.files.internal("enemigo2.png"));

        this.texturaNetherite = new Texture(Gdx.files.internal("netherite.png"));
        this.texturaWither = new Texture(Gdx.files.internal("witherSkeleton.png"));
        this.texturaBlaze = new Texture(Gdx.files.internal("blaze.png"));

        this.texturaDragonEgg = new Texture(Gdx.files.internal("dragonEgg.png"));
        this.texturaEnderman = new Texture(Gdx.files.internal("enderman.png"));
        this.texturaShulker = new Texture(Gdx.files.internal("shulker.png"));

        this.sonidoDrop = Gdx.audio.newSound(Gdx.files.internal("pickup.mp3"));
        this.sonidoHurt = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        this.musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("haggstrom.mp3"));
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();

        this.jugador = new Jugador(this.texturaSteve);
        this.objetosCayendo = new Array<>();
        generarObjeto();
    }

    private void generarObjeto() {
        float posicionX = MathUtils.random(0, 800 - 64);
        int tipo = MathUtils.random(1, 100);
        float multiplicadorDificultad = 1 + (AdministradorJuego.getInstancia().getPuntosTotales() / 200f);
        float velocidad = (200 * Gdx.graphics.getDeltaTime()) * multiplicadorDificultad;
        int puntajeActual = AdministradorJuego.getInstancia().getPuntosTotales();

        if (puntajeActual < 500) {
            if (tipo <= 40) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 1, this.texturaHierro, 10));
            } else if (tipo <= 60) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 2, this.texturaOro, 20));
            } else if (tipo <= 70) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 4, this.texturaDiamante, 30));
            } else if (tipo <= 85) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, this.texturaEnemigo1, 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, this.texturaEnemigo2, 1));
            }
        } else if (puntajeActual < 1000) {
            if (tipo <= 25) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 2, this.texturaOro, 20));
            } else if (tipo <= 50) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 4, this.texturaNetherite, 50));
            } else if (tipo <= 75) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, this.texturaWither, 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 3, this.texturaBlaze, 1));
            }
        } else {
            if (tipo <= 15) {
                this.objetosCayendo.add(new Mineral(posicionX, 480, velocidad + 5, this.texturaDragonEgg, 100));
            } else if (tipo <= 60) {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 4, this.texturaEnderman, 1));
            } else {
                this.objetosCayendo.add(new Enemigo(posicionX, 480, velocidad + 4, this.texturaShulker, 1));
            }
        }

        this.tiempoUltimoObjeto = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        int puntajeActual = AdministradorJuego.getInstancia().getPuntosTotales();

        this.game.batch.begin();

        if (puntajeActual < 500) {
            this.game.batch.draw(this.texturaFondoOverworld, 0, 0, 800, 480);
        } else if (puntajeActual < 1000) {
            this.game.batch.draw(this.texturaFondoNether, 0, 0, 800, 480);
        } else {
            this.game.batch.draw(this.texturaFondoEnd, 0, 0, 800, 480);
        }

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

        long spawnIntervalo;
        if (puntajeActual < 500) {
            spawnIntervalo = 500000000L;
        } else if (puntajeActual < 1000) {
            spawnIntervalo = 350000000L;
        } else {
            spawnIntervalo = 200000000L;
        }

        if (TimeUtils.nanoTime() - this.tiempoUltimoObjeto > spawnIntervalo) {
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
        this.texturaFondoOverworld.dispose();
        this.texturaFondoNether.dispose();
        this.texturaFondoEnd.dispose();
        this.texturaSteve.dispose();
        this.texturaHierro.dispose();
        this.texturaOro.dispose();
        this.texturaDiamante.dispose();
        this.texturaEnemigo1.dispose();
        this.texturaEnemigo2.dispose();
        this.texturaNetherite.dispose();
        this.texturaWither.dispose();
        this.texturaBlaze.dispose();
        this.texturaDragonEgg.dispose();
        this.texturaEnderman.dispose();
        this.texturaShulker.dispose();
        this.sonidoDrop.dispose();
        this.sonidoHurt.dispose();
        this.musicaLluvia.dispose();
    }
}

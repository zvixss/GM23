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
    private Texture texturaSteve;
    private Texture texturaHierro;
    private Texture texturaOro;
    private Texture texturaDiamante;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigo2;

    private Sound sonidoDrop;
    private Sound sonidoHurt;
    private Music musicaLluvia;

    private Jugador jugador;
    private Array<ObjetoCayendo> objetosCayendo;
    private long tiempoUltimoObjeto;

    public Lluvia(final GameLluvia game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.texturaFondoOverworld = new Texture(Gdx.files.internal("overworld.png"));
        this.texturaSteve = new Texture(Gdx.files.internal("steve.png"));
        this.texturaHierro = new Texture(Gdx.files.internal("hierro.png"));
        this.texturaOro = new Texture(Gdx.files.internal("oro.png"));
        this.texturaDiamante = new Texture(Gdx.files.internal("diamante.png"));
        this.texturaEnemigo1 = new Texture(Gdx.files.internal("enemigo1.png"));
        this.texturaEnemigo2 = new Texture(Gdx.files.internal("enemigo2.png"));

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

        this.tiempoUltimoObjeto = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        this.game.batch.draw(this.texturaFondoOverworld, 0, 0, 800, 480);
        this.game.font.draw(this.game.batch, "Puntuacion: " + AdministradorJuego.getInstancia().getPuntosTotales(), 10, 470);
        this.game.font.draw(this.game.batch, "Vidas: " + AdministradorJuego.getInstancia().getVidas(), 10, 450);

        this.jugador.dibujar(this.game.batch);
        for (ObjetoCayendo objeto : this.objetosCayendo) {
            objeto.dibujar(this.game.batch);
        }
        this.game.batch.end();

        this.jugador.actualizarMovimiento();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            this.camera.unproject(touchPos);

            this.jugador.getHitbox().x = touchPos.x - this.jugador.getHitbox().width / 2;
            this.jugador.getHitbox().y = touchPos.y - this.jugador.getHitbox().height / 2;

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

        if (TimeUtils.nanoTime() - this.tiempoUltimoObjeto > 500000000L) {
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
            int puntosFinales = AdministradorJuego.getInstancia().getPuntosTotales();
            this.musicaLluvia.stop();
            this.game.setScreen(new PantallaGameOver(this.game, puntosFinales));
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
        this.texturaSteve.dispose();
        this.texturaHierro.dispose();
        this.texturaOro.dispose();
        this.texturaDiamante.dispose();
        this.texturaEnemigo1.dispose();
        this.texturaEnemigo2.dispose();
        this.sonidoDrop.dispose();
        this.sonidoHurt.dispose();
        this.musicaLluvia.dispose();
    }
}

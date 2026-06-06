package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaGameOver implements Screen {
    private final GameLluvia game;
    private OrthographicCamera camera;
    private int puntosFinales;

    public PantallaGameOver(final GameLluvia game, int puntosFinales) {
        this.game = game;
        this.puntosFinales = puntosFinales;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0, 0, 1);
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        this.game.font.draw(this.game.batch, "¡PARTIDA TERMINADA!", 330, 320);
        this.game.font.draw(this.game.batch, "Puntaje Obtenido: " + this.puntosFinales, 330, 260);
        this.game.font.draw(this.game.batch, "Puntaje Maximo: " + AdministradorJuego.getInstancia().getPuntajeMaximo(), 330, 230);
        this.game.font.draw(this.game.batch, "Haz clic en cualquier lugar para volver a jugar", 250, 150);
        this.game.batch.end();

        if (Gdx.input.justTouched()) {
            AdministradorJuego.getInstancia().reiniciarJuego();
            this.game.setScreen(new Lluvia(this.game));
            this.dispose();
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}

package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaMenu implements Screen {
    private final GameLluvia game;
    private OrthographicCamera camera;

    public PantallaMenu(final GameLluvia game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        this.game.font.draw(this.game.batch, "FIEBRE VOXEL", 360, 300);
        this.game.font.draw(this.game.batch, "Haz clic en cualquier lugar para comenzar", 260, 220);
        this.game.batch.end();

        if (Gdx.input.justTouched()) {
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

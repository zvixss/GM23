package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Jugador {
    private Rectangle hitbox;
    private Texture textura;

    public Jugador(Texture textura) {
        this.textura = textura;
        this.hitbox = new Rectangle();
        this.hitbox.width = 80;
        this.hitbox.height = 80;
        this.hitbox.x = 800 / 2 - this.hitbox.width / 2;
        this.hitbox.y = 20;
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.hitbox.x -= 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.hitbox.x += 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.hitbox.y += 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.hitbox.y -= 400 * Gdx.graphics.getDeltaTime();
        }

        if (this.hitbox.x < 0) {
            this.hitbox.x = 0;
        }
        if (this.hitbox.x > 800 - this.hitbox.width) {
            this.hitbox.x = 800 - this.hitbox.width;
        }
        if (this.hitbox.y < 0) {
            this.hitbox.y = 0;
        }
        if (this.hitbox.y > 480 - this.hitbox.height) {
            this.hitbox.y = 480 - this.hitbox.height;
        }
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }
}

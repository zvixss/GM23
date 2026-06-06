package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCayendo implements Atrapable {
    protected Rectangle hitbox;
    protected Texture textura;
    protected float velocidadCaida;
    protected EstrategiaMovimiento estrategia;

    public ObjetoCayendo(float posicionX, float posicionY, float velocidadCaida, Texture textura, float ancho, float alto) {
        this.hitbox = new Rectangle(posicionX, posicionY, ancho, alto);
        this.velocidadCaida = velocidadCaida;
        this.textura = textura;
    }

    public void setEstrategia(EstrategiaMovimiento estrategia) {
        this.estrategia = estrategia;
    }

    public final boolean actualizarFrameFisica() {
        mover();
        animacionAdicional();
        return salioDePantalla();
    }

    private void mover() {
        if (this.estrategia != null) {
            this.estrategia.mover(this);
        }
    }

    protected abstract void animacionAdicional();

    private boolean salioDePantalla() {
        return this.hitbox.y + this.hitbox.height < 0;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
    }

    @Override
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public float getVelocidadCaida() {
        return this.velocidadCaida;
    }
}

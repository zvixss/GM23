package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends ObjetoCayendo {
    private int dano;

    public Enemigo(float posicionX, float posicionY, float velocidadCaida, Texture textura, int dano) {
        super(posicionX, posicionY, velocidadCaida, textura, 64, 64);
        this.dano = dano;
    }

    @Override
    public int getPuntos() {
        return 0;
    }

    @Override
    public void aplicarEfecto() {
        AdministradorJuego.getInstancia().restarVidas(this.dano);
    }

    public int getDano() {
        return this.dano;
    }
}

package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Mineral extends ObjetoCayendo {
    private int puntosValor;

    public Mineral(float posicionX, float posicionY, float velocidadCaida, Texture textura, int puntosValor) {
        super(posicionX, posicionY, velocidadCaida, textura, 48, 48);
        this.puntosValor = puntosValor;
    }

    @Override
    public int getPuntos() {
        return this.puntosValor;
    }

    @Override
    public void aplicarEfecto() {
        AdministradorJuego.getInstancia().sumarPuntos(this.puntosValor);
    }
}

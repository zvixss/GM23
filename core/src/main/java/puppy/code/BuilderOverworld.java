package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BuilderOverworld implements BuilderNivel {
    private Nivel nivel;

    @Override
    public void reiniciar() {
        this.nivel = new Nivel();
    }

    @Override
    public void construirFondo() {
        this.nivel.setTexturaFondo(new Texture(Gdx.files.internal("overworld.png")));
    }

    @Override
    public void construirTexturasObjetos() {
        Texture[] minerales = {
            new Texture(Gdx.files.internal("hierro.png")),
            new Texture(Gdx.files.internal("oro.png")),
            new Texture(Gdx.files.internal("diamante.png"))
        };
        Texture[] enemigos = {
            new Texture(Gdx.files.internal("enemigo1.png")),
            new Texture(Gdx.files.internal("enemigo2.png"))
        };
        this.nivel.setTexturasMinerales(minerales);
        this.nivel.setTexturasEnemigos(enemigos);
    }

    @Override
    public void construirDificultad() {
        this.nivel.setSpawnIntervalo(500000000L);
    }

    @Override
    public void construirId() {
        this.nivel.setId(1);
    }

    @Override
    public Nivel getResultado() {
        return this.nivel;
    }
}

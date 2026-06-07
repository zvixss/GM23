package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BuilderEnd implements BuilderNivel {
    private Nivel nivel;

    @Override
    public void reiniciar() {
        this.nivel = new Nivel();
    }

    @Override
    public void construirFondo() {
        this.nivel.setTexturaFondo(new Texture(Gdx.files.internal("end.png")));
    }

    @Override
    public void construirTexturasObjetos() {
        Texture[] minerales = {
            new Texture(Gdx.files.internal("dragonEgg.png"))
        };
        Texture[] enemigos = {
            new Texture(Gdx.files.internal("enderman.png")),
            new Texture(Gdx.files.internal("shulker.png"))
        };
        this.nivel.setTexturasMinerales(minerales);
        this.nivel.setTexturasEnemigos(enemigos);
    }

    @Override
    public void construirDificultad() {
        this.nivel.setSpawnIntervalo(200000000L);
    }

    @Override
    public void construirId() {
        this.nivel.setId(3);
    }

    @Override
    public Nivel getResultado() {
        return this.nivel;
    }
}

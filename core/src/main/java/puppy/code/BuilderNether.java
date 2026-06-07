package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BuilderNether implements BuilderNivel {
    private Nivel nivel;

    @Override
    public void reiniciar() {
        this.nivel = new Nivel();
    }

    @Override
    public void construirFondo() {
        this.nivel.setTexturaFondo(new Texture(Gdx.files.internal("nether.png")));
    }

    @Override
    public void construirTexturasObjetos() {
        Texture[] minerales = {
            new Texture(Gdx.files.internal("oro.png")),
            new Texture(Gdx.files.internal("netherite.png"))
        };
        Texture[] enemigos = {
            new Texture(Gdx.files.internal("witherSkeleton.png")),
            new Texture(Gdx.files.internal("blaze.png"))
        };
        this.nivel.setTexturasMinerales(minerales);
        this.nivel.setTexturasEnemigos(enemigos);
    }

    @Override
    public void construirDificultad() {
        this.nivel.setSpawnIntervalo(350000000L);
    }

    @Override
    public void construirId() {
        this.nivel.setId(2);
    }

    @Override
    public Nivel getResultado() {
        return this.nivel;
    }
}

package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Nivel {
    private Texture texturaFondo;
    private long spawnIntervalo;
    private Texture[] texturasMinerales;
    private Texture[] texturasEnemigos;
    private int id;

    public void setTexturaFondo(Texture texturaFondo) {
        this.texturaFondo = texturaFondo;
    }

    public Texture getTexturaFondo() {
        return this.texturaFondo;
    }

    public void setSpawnIntervalo(long spawnIntervalo) {
        this.spawnIntervalo = spawnIntervalo;
    }

    public long getSpawnIntervalo() {
        return this.spawnIntervalo;
    }

    public void setTexturasMinerales(Texture[] texturasMinerales) {
        this.texturasMinerales = texturasMinerales;
    }

    public Texture[] getTexturasMinerales() {
        return this.texturasMinerales;
    }

    public void setTexturasEnemigos(Texture[] texturasEnemigos) {
        this.texturasEnemigos = texturasEnemigos;
    }

    public Texture[] getTexturasEnemigos() {
        return this.texturasEnemigos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void dispose() {
        if (this.texturaFondo != null) {
            this.texturaFondo.dispose();
        }
        if (this.texturasMinerales != null) {
            for (Texture t : this.texturasMinerales) {
                t.dispose();
            }
        }
        if (this.texturasEnemigos != null) {
            for (Texture t : this.texturasEnemigos) {
                t.dispose();
            }
        }
    }
}

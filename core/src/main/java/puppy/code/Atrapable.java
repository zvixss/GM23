package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public interface Atrapable {
    int getPuntos();
    void aplicarEfecto();
    Rectangle getHitbox();
}

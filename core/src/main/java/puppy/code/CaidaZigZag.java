package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class CaidaZigZag implements EstrategiaMovimiento {
    @Override
    public void mover(ObjetoCayendo objeto) {
        objeto.getHitbox().y -= objeto.getVelocidadCaida();
        objeto.getHitbox().x += MathUtils.sin(objeto.getHitbox().y * 0.02f) * 8;

        if (objeto.getHitbox().x < 0) {
            objeto.getHitbox().x = 0;
        }
        if (objeto.getHitbox().x > 800 - objeto.getHitbox().width) {
            objeto.getHitbox().x = 800 - objeto.getHitbox().width;
        }
    }
}

package puppy.code;

public class CaidaRecta implements EstrategiaMovimiento {
    @Override
    public void mover(ObjetoCayendo objeto) {
        objeto.getHitbox().y -= objeto.getVelocidadCaida();
    }
}

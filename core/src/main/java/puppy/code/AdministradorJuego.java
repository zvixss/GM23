package puppy.code;

public class AdministradorJuego {
    private static AdministradorJuego instancia;
    private int puntosTotales;
    private int vidas;
    private int puntajeMaximo;

    private AdministradorJuego() {
        this.puntosTotales = 0;
        this.vidas = 3;
        this.puntajeMaximo = 0;
    }

    public static AdministradorJuego getInstancia() {
        if (instancia == null) {
            instancia = new AdministradorJuego();
        }
        return instancia;
    }

    public void sumarPuntos(int puntos) {
        this.puntosTotales += puntos;
        if (this.puntosTotales > this.puntajeMaximo) {
            this.puntajeMaximo = this.puntosTotales;
        }
    }

    public void restarVidas(int dano) {
        this.vidas -= dano;
    }

    public void sumarVida() {
        this.vidas++;
    }

    public int getPuntosTotales() {
        return this.puntosTotales;
    }

    public int getVidas() {
        return this.vidas;
    }

    public int getPuntajeMaximo() {
        return this.puntajeMaximo;
    }

    public void reiniciarJuego() {
        this.puntosTotales = 0;
        this.vidas = 3;
    }
}

package puppy.code;

public class AdministradorJuego {
    private static AdministradorJuego instancia;
    private int puntosTotales;
    private int vidas;
    private boolean enNether;
    private final int puntosParaNether = 100;

    private AdministradorJuego() {
        this.puntosTotales = 0;
        this.vidas = 3;
        this.enNether = false;
    }

    public static AdministradorJuego getInstancia() {
        if (instancia == null) {
            instancia = new AdministradorJuego();
        }
        return instancia;
    }

    public void sumarPuntos(int puntos) {
        this.puntosTotales += puntos;
        verificarNivel();
    }

    public void restarVidas(int dano) {
        this.vidas -= dano;
    }

    private void verificarNivel() {
        if (this.puntosTotales >= this.puntosParaNether && !this.enNether) {
            this.enNether = true;
        }
    }

    public int getPuntosTotales() {
        return this.puntosTotales;
    }

    public int getVidas() {
        return this.vidas;
    }

    public boolean isEnNether() {
        return this.enNether;
    }

    public void reiniciarJuego() {
        this.puntosTotales = 0;
        this.vidas = 3;
        this.enNether = false;
    }
}

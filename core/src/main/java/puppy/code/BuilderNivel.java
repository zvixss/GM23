package puppy.code;

public interface BuilderNivel {
    void reiniciar();
    void construirFondo();
    void construirTexturasObjetos();
    void construirDificultad();
    void construirId();
    Nivel getResultado();
}

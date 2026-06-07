package puppy.code;

public class DirectorNivel {
    private BuilderNivel builder;

    public void setBuilder(BuilderNivel builder) {
        this.builder = builder;
    }

    public Nivel construirNivel() {
        this.builder.reiniciar();
        this.builder.construirFondo();
        this.builder.construirTexturasObjetos();
        this.builder.construirDificultad();
        this.builder.construirId();
        return this.builder.getResultado();
    }
}

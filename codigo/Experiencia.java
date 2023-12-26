public enum Experiencia {
    NIVEL_1(1, 0.50),
    NIVEL_2(2, 1.00),
    NIVEL_3(3, 1.50),
    NIVEL_4(4, 2.50);

    private final int nivel;
    private final double salario;

    Experiencia(int nivel, double salario) {
        this.nivel = nivel;
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public int getNivel() {
        return nivel;
    }
}

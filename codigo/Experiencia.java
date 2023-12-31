public enum Experiencia {
    NIVEL_1(1, 1, 0.4),
    NIVEL_2(2, 2, 0.3),
    NIVEL_3(3, 4, 0.2),
    NIVEL_4(4, 6, 0.1);

    private final int nivel;
    private final int salario;
    private final double probabilidade;

    Experiencia(int nivel, int salario, double probabilidade) {
        this.nivel = nivel;
        this.salario = salario;
        this.probabilidade = probabilidade;
    }

    public double getSalario() {
        return salario;
    }

    public int getNivel() {
        return nivel;
    }

    public double getProbabilidade() {
        return probabilidade;
    }
}

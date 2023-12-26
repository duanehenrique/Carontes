public enum Experiencia {
    NIVEL_1(1, 1),
    NIVEL_2(2, 2),
    NIVEL_3(3, 4),
    NIVEL_4(4, 6);

    private final int nivel;
    private final int salario;

    Experiencia(int nivel, int salario) {
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

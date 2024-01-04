public enum Experiencia {
    NIVEL_1(1, 1, 0.4, 0),
    NIVEL_2(2, 2, 0.3, 10),
    NIVEL_3(3, 4, 0.2, 20),
    NIVEL_4(4, 6, 0.1, 35);

    private int nivel;
    private int salario;
    private int custoCurso;
    private double probabilidade;

    Experiencia(int nivel, int salario, double probabilidade, int custoCurso) {
        this.nivel = nivel;
        this.salario = salario;
        this.probabilidade = probabilidade;
        this.custoCurso = custoCurso;
    }

    public int getSalario() {
        return salario;
    }

    public int getNivel() {
        return nivel;
    }

    public double getProbabilidade() {
        return probabilidade;
    }

    public int getCustoCurso(){
        return custoCurso;
    }
}

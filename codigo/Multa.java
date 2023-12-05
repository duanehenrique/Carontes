public class Multa {

    private String tipo;
    private int pontos;
    private double valor;

    public Multa(String tipo, int pontos, double valor) {
        this.tipo = tipo;
        this.pontos = pontos;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
} 

import java.time.LocalDate;

public enum Multa {
    LEVE(3, 88.38),
    MEDIA(4, 130.16),
    GRAVE(5, 195.23),
    GRAVISSIMA(7, 293.47);

    private int pontos;
    private double valor;
    LocalDate dataDeEmissao;

    Multa(int pontos, double valor) {
        this.pontos = pontos;
        this.valor = valor;
        this.dataDeEmissao = LocalDate.now();
    }

    public int getPontos() {
        return pontos;
    }

    public double getValor() {
        return valor;
    }
    public LocalDate getDataDeEmissao(){
        return dataDeEmissao;
    }
}

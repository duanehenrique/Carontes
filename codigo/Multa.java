import java.time.LocalDate;

/**
 * Enum que representa os diferentes tipos de multas.
 */
public enum Multa implements Relatorio{
    // #region Definição dos tipos de multas

    LEVE("Leve", 3, 88.38),
    MEDIA("Média", 4, 130.16),
    GRAVE("Grave", 5, 195.23),
    GRAVISSIMA("Gravíssima", 7, 293.47);

    // #endregion

    // #region Atributos

    private final String tipo;
    private final int pontos;
    private final double valor;
    private final LocalDate dataDeEmissao;
    private boolean multaAtiva;
    private boolean multaPaga;

    // #endregion

    // #region Construtor

    /**
     * Construtor da enumeração Multa.
     *
     * @param tipo   O tipo da multa.
     * @param pontos Os pontos da multa.
     * @param valor  O valor da multa.
     */
    Multa(String tipo, int pontos, double valor) {
        this.tipo = tipo;
        this.pontos = pontos;
        this.valor = valor;
        this.dataDeEmissao = LocalDate.now();
        this.multaAtiva = true;
        this.multaPaga = false;
    }

    // #endregion

    // #region Métodos

    /**
     * Paga a multa.
     *
     * @return O valor da multa.
     * @throws IllegalStateException Se a multa já estiver paga.
     */
    public double pagarMulta() {
        if (multaPaga) {
            throw new IllegalStateException("A multa já está paga.");
        } else {
            multaPaga = true;
            return getValor();
        }
    }

    /**
     * Retorna o tipo da multa.
     *
     * @return O tipo da multa.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna os pontos da multa.
     *
     * @return Os pontos da multa.
     */
    public int getPontos() {
        return pontos;
    }

    /**
     * Retorna o valor da multa.
     *
     * @return O valor da multa.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna a data de emissão da multa.
     *
     * @return A data de emissão da multa.
     */
    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    /**
     * Verifica se a multa foi paga.
     *
     * @return Verdadeiro se a multa foi paga, falso caso contrário.
     */
    public boolean getMultaPaga() {
        return multaPaga;
    }

    /**
     * Verifica se a multa expirou.
     *
     * @return Verdadeiro se a multa expirou, falso caso contrário.
     */
    public boolean multaExpirou() {
        LocalDate dataDeHoje = LocalDate.now();
        if (getDataDeEmissao().plusYears(1).isBefore(dataDeHoje) && getMultaPaga()) {
            multaAtiva = false;
        }
        return multaAtiva;
    }

    /**
     * Retorna uma representação em string da multa.
     *
     * @return Uma representação em string da multa.
     */
    // #region Relatorio
    /**
     * Retorna uma representação em string da multa.
     *
     * @return Uma representação em string da multa.
     */
    @Override
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Tipo: ").append(getTipo()).append("\n");
        relatorio.append("Pontos: ").append(getPontos()).append("\n");
        relatorio.append("Valor: ").append(String.format("%.2f", getValor())).append("\n");
        relatorio.append("Data de Emissão: ").append(getDataDeEmissao()).append("\n");
        relatorio.append("Multa está paga: ").append(multaPaga ? "Sim" : "Não").append("\n");
        relatorio.append("Multa está ativa: ").append(multaAtiva ? "Sim" : "Não").append("\n");

        return relatorio.toString();
    }
    // #endregion
}

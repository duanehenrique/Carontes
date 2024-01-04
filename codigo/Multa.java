import java.time.LocalDate;

/**
 * Enum que representa os diferentes tipos de multas.
 */
public enum Multa implements Relatorio{
    // #region Definição dos tipos de multas

    LEVE("Leve", 3, 1),
    MEDIA("Média", 4, 2),
    GRAVE("Grave", 5, 4),
    GRAVISSIMA("Gravíssima", 7, 5);

    // #endregion

    // #region Atributos

    private final String tipo;
    private final int pontos;
    private final double custo;
    private final LocalDate dataDeEmissao;

    // #endregion

    // #region Construtor

    /**
     * Construtor da enumeração Multa.
     *
     * @param tipo   O tipo da multa.
     * @param pontos Os pontos da multa.
     * @param valor  O valor da multa.
     */
    Multa(String tipo, int pontos, double custo) {
        this.tipo = tipo;
        this.pontos = pontos;
        this.custo = custo;
        this.dataDeEmissao = LocalDate.now();
    }
    
    // #endregion

    // #region Métodos

    /**
     * Paga a multa.
     *
     * @return O custo da multa.
     */
    public double pagarMulta() {
            return getCusto();
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
    public double getCusto() {
        return custo;
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
        relatorio.append("Valor: ").append(String.format("%.2f", getCusto())).append("\n");
        relatorio.append("Data de Emissão: ").append(getDataDeEmissao()).append("\n");
        return relatorio.toString();
    }
    // #endregion
}

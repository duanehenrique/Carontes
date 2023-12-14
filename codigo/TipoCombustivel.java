/**
 * Enum para os tipos de combustível.
 */
public enum TipoCombustivel {

    // #region Tipos de Combustível

    ALCOOL(3.29, 7.0),
    GASOLINA(5.19, 10.0),
    DIESEL(6.09, 4.0);

    // #endregion

    // #region Atributos
    private final double preco;
    private final double consumoMedio;
    // #endregion

    // #region Construtor

    /**
     * Construtor para o tipo de combustível.
     * @param preco O preço médio do combustível.
     * @param consumoMedio O consumo médio do combustível em km/l.
     */
    TipoCombustivel(double preco, double consumoMedio){
        this.preco = preco;
        this.consumoMedio = consumoMedio;
    }

    // #endregion

    // #region Getters

    /**
     * Retorna o preço médio do combustível.
     * @return O preço médio do combustível.
     */
    public double getPreco(){
        return preco;
    }

    /**
     * Retorna o consumo médio do combustível em km/l.
     * @return O consumo médio do combustível.
     */
    public double getConsumoMedio() {
        return consumoMedio;
    }

    // #endregion
}
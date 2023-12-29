/**
 * Enum para os tipos de combustível.
 */
public enum TipoCombustivel {

    // #region Tipos de Combustível

    ALCOOL(1, 6, 0),
    GASOLINA(2, 2, 20),
    DIESEL(3, 1, 40);

    // #endregion

    // #region Atributos
    private final int custo;
    private final int consumoMedio;
    private final int adicionalPrecoVenda;
    // #endregion

    // #region Construtor

    /**
     * Construtor para o tipo de combustível.
     * @param preco O preço médio do combustível.
     * @param consumoMedio O consumo médio do combustível em km/l.
     */
    TipoCombustivel(int custo, int consumoMedio, int adicionalPrecoVenda){
        this.custo = custo;
        this.consumoMedio = consumoMedio;
        this.adicionalPrecoVenda = adicionalPrecoVenda;
    }

    // #endregion

    // #region Getters

    /**
     * Retorna o preço médio do combustível.
     * @return O preço médio do combustível.
     */
    public double getCusto(){
        return custo;
    }

    /**
     * Retorna o consumo médio do combustível em km/l.
     * @return O consumo médio do combustível.
     */
    public double getConsumoMedio() {
        return consumoMedio;
    }

    public int getAdicionalPrecoVenda(){
        return adicionalPrecoVenda;
    }

    // #endregion
}
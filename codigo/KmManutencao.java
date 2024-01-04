/**
 * Enum que representa os diferentes tipos de veículos e suas respectivas
 * quilometragens para manutenção periódica e troca de peças.
 */
public enum KmManutencao {
    // #region Definição dos tipos de veículos
    GONDOLA(250, 5), 
    BALSA(500, 10), 
    NAVIO(500, 15), 
    CRUZEIRO(750, 20); 
    // #endregion

    // #region Atributos
    private final int manutencaoPeriodica; 
    private final int custoManutencao;
    // #endregion

    // #region Construtor

    /**
     * Construtor da enumeração.
     *
     * @param manutencaoPeriodica A quilometragem necessária para manutenção
     *                            periódica.
     * @param manutencaoPecas     A quilometragem necessária para manutenção para
     *                            troca de peças.
     */
    KmManutencao(int manutencaoPeriodica, int custoManutencao) {
        this.manutencaoPeriodica = manutencaoPeriodica;
        this.custoManutencao = custoManutencao;
    }
    // #endregion

    // #region Métodos

    /**
     * Retorna a quilometragem necessária para manutenção periódica.
     *
     * @return A quilometragem necessária para manutenção periódica.
     */
    public int getManutencaoPeriodica() {
        return manutencaoPeriodica;
    }

    public int getCustoManutencao(){
        return custoManutencao;
    }
    // #endregion
}
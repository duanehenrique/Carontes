/**
 * Enum que representa os diferentes tipos de veículos e suas respectivas
 * quilometragens para manutenção periódica e troca de peças.
 */
public enum KmManutencao {
    // #region Definição dos tipos de veículos
    CARRO(10000, 10000), 
    VAN(10000, 12000), 
    FURGAO(10000, 12000), 
    CAMINHAO(20000, 20000); 
    // #endregion

    // #region Atributos
    private final int manutencaoPeriodica; 
    private final int manutencaoPecas; 
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
    KmManutencao(int manutencaoPeriodica, int manutencaoPecas) {
        this.manutencaoPeriodica = manutencaoPeriodica;
        this.manutencaoPecas = manutencaoPecas;
    }
    // #endregion

    // #region Métodos

    /**
     * Retorna a quilometragem necessária para manutenção periódica.
     *
     * @return A quilometragem necessária para manutenção periódica.
     */
    public double getManutencaoPeriodica() {
        return manutencaoPeriodica;
    }

    /**
     * Retorna a quilometragem necessária para manutenção para troca de peças.
     *
     * @return A quilometragem necessária para manutenção para troca de peças.
     */
    public double getManutencaoPecas() {
        return manutencaoPecas;
    }
    // #endregion
}
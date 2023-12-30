/**
 * Enum que representa os diferentes tipos de veículos e suas respectivas
 * quilometragens para manutenção periódica e troca de peças.
 */
public enum KmManutencao {
    // #region Definição dos tipos de veículos
    GONDOLA(5000), 
    BALSA(10000), 
    NAVIO(10000), 
    CRUZEIRO(15000); 
    // #endregion

    // #region Atributos
    private final int manutencaoPeriodica; 
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
    KmManutencao(int manutencaoPeriodica) {
        this.manutencaoPeriodica = manutencaoPeriodica;
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
    // #endregion
}
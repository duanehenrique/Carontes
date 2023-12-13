/**
 * Classe Van que estende a classe Veículo.
 */
public class Van extends Veiculo {

    // #region Atributos
    private static final double TANQUE_MAX = 60;

    // #endregion

    // #region Construtor
    /**
     * Construtor de Van.
     * @param motorista O motorista da Van.
     * @param placa A placa da Van.
     * @param tipoCombustivel O tipo de combustível da Van.
     * @param custoManutencao O custo de manutenção da Van.
     */
    public Van(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao("Van", custoManutencao);
    }

    // #endregion
}
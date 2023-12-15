/**
 * Classe Caminhao que estende a classe Veiculo.
 * Esta classe representa um caminhão no sistema.
 */
public class Caminhao extends Veiculo {

    // #region Atributos
    private static final double TANQUE_MAX = 250;
    // #endregion

    // #region Construtor
    /**
     * Construtor para a classe Caminhao.
     * 
     * @param motorista       O motorista do caminhão.
     * @param placa           A placa do caminhão.
     * @param tipoCombustivel O tipo de combustível que o caminhão usa.
     * @param custoManutencao O custo de manutenção do caminhão.
     */
    public Caminhao(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this, custoManutencao);
    }
    // #endregion
}
/**
 * Classe Carro que estende a classe Veiculo.
 * Esta classe representa um carro no sistema.
 */
public class Carro extends Veiculo {

    // #region Atributos
    private static final double TANQUE_MAX = 50;
    // #endregion

    // #region Construtor
    // Construtor de Carro
    /**
     * Construtor para a classe Carro.
     * 
     * @param motorista       O motorista do carro.
     * @param placa           A placa do carro.
     * @param tipoCombustivel O tipo de combustível que o carro usa.
     * @param custoManutencao O custo de manutenção do carro.
     */
    public Carro(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this, custoManutencao);
    }
    // #endregion
}

/**
 * Classe Carro que estende a classe Veiculo.
 * Esta classe representa um carro no sistema.
 */
public class Gondola extends Barco {

    // #region Atributos
    private static final int TOTAL_PASSAGEIROS = 4;
    protected static final double CUSTO_COMPRA = 10;
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
    public Gondola(Caronte motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.manutencao = new Manutencao(this, custoManutencao);
        inicializarCapacidade(TOTAL_PASSAGEIROS);
    }


    // #endregion

    // #region Métodos

    /**
     * Adiciona uma rota ao veículo.
     *
     * @param rota A rota a ser adicionada.
     * @throws IllegalArgumentException Se o veículo não pode ter mais rotas ou se a
     *                                  autonomia é insuficiente para realizar a
     *                                  rota ou se a distância da rota excede a
     *                                  autonomia máxima do veículo.
     * @throws IllegalStateException    Se a manutenção do veículo está em atraso ou
     *                                  se a carteira de motorista está invalidada
     *                                  por multas.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(getPlaca() + ":\n");
        relatorio.append("Caronte: " + motorista.getNome() + "\n");
        relatorio.append("Nível de Experiência do Caronte: " + motorista.getNivel() + "\n");
        relatorio.append("Capacidade máxima do barco: " + CAPACIDADEPASSAGEIROS + "\n");
        relatorio.append("Km Rodados no mes: " + kmNoMes() + " km\n");
        relatorio.append("Km Total: " + kmTotal() + " km\n");
        relatorio.append("Autonomia do veiculo: Não consume combustível.\n");
        relatorio.append("Tanque abastecido: Veículo sem tanque.\n");
        relatorio.append("Despesas com combustível: R$ " + String.format("%.2f", despesaCombustivel) + "\n");
        relatorio.append("Despesas com multas: R$ " + String.format("%.2f", despesaMulta) + "\n");
        relatorio.append("Despesas com manutenção: R$ " + String.format("%.2f", despesaManutencao) + "\n");
        relatorio.append("Despesa total: R$ " + String.format("%.2f", (despesaCombustivel + despesaManutencao + despesaMulta)) + "\n");
        return relatorio.toString();
    }
    }
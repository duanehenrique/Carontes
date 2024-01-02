/**
 * Classe Carro que estende a classe Veiculo.
 * Esta classe representa um carro no sistema.
 */
public class Gondola extends Barco {

    // #region Atributos
    private static final int TOTAL_PASSAGEIROS = 4;
    private static final int CUSTO_COMPRA = 10;
    private static final int CUSTO_MANUTENCAO = 5;
    private static final int AUTONOMIA = 10;

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
    public Gondola(Caronte motorista, String nome) {
        super(motorista, nome, TOTAL_PASSAGEIROS, CUSTO_COMPRA);
        this.manutencao = new Manutencao(this, CUSTO_MANUTENCAO);
    }


    // #endregion

    // #region Métodos

    public double getAutonomia(){
        return AUTONOMIA;
    }

    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(getNOME() + ":\n");
        relatorio.append("Caronte: " + motorista.getNome() + "\n");
        relatorio.append("Nível de Experiência do Caronte: " + motorista.getNivel() + "\n");
        relatorio.append("Tipo de Barco: ").append(getTipoDeBarco());
        relatorio.append("Capacidade máxima do barco: " + CAPACIDADEPASSAGEIROS + "\n");
        relatorio.append("Km Total: " + kmTotal() + " km\n");
        relatorio.append("Km até próxima manutenção periódica: " + getKmAteProximaManutencao() + " km\n");
        relatorio.append("Autonomia do veiculo: " + getAutonomia() + " km\n");
        relatorio.append("Tanque abastecido: Veículo sem tanque.\n");
        relatorio.append("Despesas com combustível: Veículo não consume combustível.\n");
        relatorio.append("Despesas com multas: " + String.format("%.2f", despesaMulta) + " almas.\n");
        relatorio.append("Despesas com manutenção: " + String.format("%.2f", despesaManutencao) + " almas.\n");
        relatorio.append("Despesa total: " + String.format("%.2f", (getDespesaTotal()) + " almas.\n"));
        relatorio.append("Arrecadação total: " + String.format("%.2f", (getTotalAlmasColetadasDia()) + " almas.\n"));
        return relatorio.toString();
    }
    }
/**
 * Classe Van que estende a classe Veículo.
 */
public class Balsa extends Barco implements BarcoComTanque{

    // #region Atributos
    private static final double TANQUE_MAX = 60;
    private static final int TOTAL_PASSAGEIROS = 10;
    private static final int CUSTO_MANUTENCAO = 10;
    private static final int CUSTO_COMPRA = 30;
    private final Tanque tanque;

    // #endregion

    // #region Construtor
    /**
     * Construtor de Van.
     * @param motorista O motorista da Van.
     * @param placa A placa da Van.
     * @param tipoCombustivel O tipo de combustível da Van.
     * @param custoManutencao O custo de manutenção da Van.
     */
    public Balsa(Caronte motorista, String nome, String tipoCombustivel, int qtdRotas) {
        super(motorista, nome, TOTAL_PASSAGEIROS, CUSTO_COMPRA, qtdRotas);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this, CUSTO_MANUTENCAO);
    }

    // #endregion

    public double autonomiaMaxima() {
        return tanque.autonomiaMaxima();
    }

    /**
     * Retorna a autonomia atual do veículo.
     *
     * @return A autonomia atual do veículo.
     */
    public double autonomiaAtual() {
        return tanque.autonomiaAtual();
    }

    /**
     * Abastece o veículo.
     *
     * @param litros A quantidade de litros a ser abastecida.
     * @return A quantidade de litros abastecida.
     */
    public double abastecer(double litros) {
        addDespesaCombustivel(litros*tanque.getCusto());
        return tanque.abastecer(litros);

    }

    @Override
    public int percorrerRota(Rota rota) {
        int totalAlmas = 0;
        try {
            if (rota.getRotaPercorrida()) {
                throw new IllegalArgumentException("A rota já foi percorrida. Escolha outra rota para percorrer");
            }
    
            if (rota.getQuilometragem() <= autonomiaAtual()) {
                totalAlmas = rota.percorrerRota(CAPACIDADEPASSAGEIROS);
                tanque.consumir(rota.getQuilometragem());
                kmDesdeUltimaManutencao(rota);
                addDespesaSalario(motorista.getSalario());
                System.out.println("Rota percorrida com sucesso!");
                return totalAlmas;
            } else {
                throw new IllegalStateException("Quilometragem da rota excede a autonomia atual da embarcação. Reabasteça antes de percorrer rota");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Erro ao percorrer rota: " + e.getMessage());
            return totalAlmas;
        }
    }
    
    public Tanque getTanque() {
        return tanque;
    }

    public boolean podeRealizarRota(Rota rota) {
        return autonomiaAtual() >= rota.getQuilometragem();
    }

            // #region Relatorio
        /**
     * Retorna uma representação em string do veículo.
     * 
     * @return Uma string representando o veículo.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(getNOME() + ":\n");
        relatorio.append("Caronte: " + motorista.getNome() + "\n");
        relatorio.append("Nível de Experiência do Caronte: " + motorista.getNivel() + "\n");
        relatorio.append("Tipo de Barco: ").append(getTipoDeBarco());
        relatorio.append("Capacidade máxima do barco: " + CAPACIDADEPASSAGEIROS + "\n");
        relatorio.append("Viagens restante por hoje: " + (MAX_ROTAS_DIA - rotas.size()) + " km\n");
        relatorio.append("Km Total: " + kmTotal() + " km\n");
        relatorio.append("Km até próxima manutenção periódica: " + getKmAteProximaManutencao() + " km\n");
        relatorio.append("Autonomia do veiculo: " + autonomiaAtual() + " km\n");
        relatorio.append("Tanque abastecido com: " + getTanque().getCapacidadeAtual() + " almas de "+ getTanque().getTipo() +  "\n");
        relatorio.append("Despesas com combustível: " + String.format("%.2f", despesaCombustivel) + " almas.\n");
        relatorio.append("Despesas com multas: " + String.format("%.2f", despesaMulta) + " almas.\n");
        relatorio.append("Despesas com manutenção: " + String.format("%.2f", despesaManutencao) + " almas.\n");
        relatorio.append("Despesa total: " + String.format("%.2f", (getDespesaTotal()) + " almas.\n"));
        relatorio.append("Arrecadação total: " + String.format("%.2f", (getTotalAlmasColetadasDia()) + " almas.\n"));   return relatorio.toString();
    }

    @Override
    public void addRota(Rota rota) {
        try {
            if (rotas.contains(rota)) {
            throw new IllegalArgumentException("A rota já existe na lista de rotas.");
            }

            if (rota.getQuilometragem() > autonomiaMaxima()) {
                throw new IllegalArgumentException(
                        "Distância da rota excede a autonomia máxima do veículo. Escolha outro veículo para adicionar a rota.");
            }
            
            if (rota.getQuilometragem() > autonomiaAtual()) {
                throw new IllegalArgumentException(
                        "Autonomia insuficiente para realizar a rota. Abasteça o veículo antes de adicionar a rota.");
            }

            if (!manutencao.getManutencaoEmDia()) {
                throw new IllegalStateException(
                        "Manutenção do veículo em atraso. Realize manutenção antes de adicionar rota.");
            }

            if (!motorista.getCarteiraValida()) {
                throw new IllegalStateException(
                        "Carteira de motorista invalidada por multas. Espere o vencimento dos pontos da carteira antes de adicionar a rota.");
            }

            rotas.add(rota);
            System.err.println("Rota adicionada ao veículo de placa " + getNOME() + " com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao adicionar rota: " + e.getMessage());
        }
    }

    public int getAdicionalPrecoVenda(){
        return tanque.getAdicionalPrecoVenda();
    }

    public int getPrecoTotal(){
        return (getPRECOCUSTO() + getAdicionalPrecoVenda());
    }
}
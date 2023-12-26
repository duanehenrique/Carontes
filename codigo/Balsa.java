/**
 * Classe Van que estende a classe Veículo.
 */
public class Balsa extends Barco implements BarcoComTanque{

    // #region Atributos
    private static final double TANQUE_MAX = 60;
    private static final int TOTAL_PASSAGEIROS = 10;
    protected static final double CUSTO_COMPRA = 30;
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
    public Balsa(Caronte motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this, custoManutencao);
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
        addDespesaCombustivel(litros*tanque.getPreco());
        return tanque.abastecer(litros);

    }

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
                /* addDespesaCombustivel(despesaCombustivel); */
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
        relatorio.append(getPlaca() + ":\n");
        relatorio.append("Caronte: " + motorista.getNome() + "\n");
        relatorio.append("Nível de Experiência do Caronte: " + motorista.getNivel() + "\n");
        relatorio.append("Capacidade máxima do barco: " + CAPACIDADEPASSAGEIROS + "\n");
        relatorio.append("Km Rodados no mes: " + kmNoMes() + " km\n");
        relatorio.append("Km Total: " + kmTotal() + " km\n");
        relatorio.append("Autonomia do veiculo: " + autonomiaAtual() + " km\n");
        relatorio.append("Tanque abastecido com: " + getTanque().getCapacidadeAtual() + " litros de "+ getTanque().getTipo() +  "\n");
        relatorio.append("Despesas com combustível: R$ " + String.format("%.2f", despesaCombustivel) + "\n");
        relatorio.append("Despesas com multas: R$ " + String.format("%.2f", despesaMulta) + "\n");
        relatorio.append("Despesas com manutenção: R$ " + String.format("%.2f", despesaManutencao) + "\n");
        relatorio.append("Despesa total: R$ " + String.format("%.2f", (despesaCombustivel + despesaManutencao + despesaMulta)) + "\n");
        return relatorio.toString();
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
            System.err.println("Rota adicionada ao veículo de placa " + getPlaca() + " com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao adicionar rota: " + e.getMessage());
        }
    }
}
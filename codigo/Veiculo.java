import java.time.*;

public abstract class Veiculo implements Relatorio{

    // #region Atributos
    protected static final int MAX_ROTAS;
    protected String placa;
    protected Rota[] rotas;
    protected int quantRotas;
    protected double totalReabastecido;
    protected Motorista motorista;
    protected Tanque tanque;
    protected double despesaTotal;
    protected Manutencao manutencao;

    // #endregion

    static {
        MAX_ROTAS = 30;
    }

    // #region Construtor

    /**
     * Construtor da classe Veiculo.
     *
     * @param motorista O motorista do veículo.
     * @param placa     A placa do veículo.
     */
    public Veiculo(Motorista motorista, String placa) {
        this.motorista = motorista;
        this.placa = placa;
        this.quantRotas = 0;
        this.rotas = new Rota[MAX_ROTAS];
        this.totalReabastecido = 0;
        this.despesaTotal = 0;
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
    public void addRota(Rota rota) {
        try {
            if (quantRotas >= MAX_ROTAS) {
                throw new IllegalArgumentException(
                        "Veículo não pode ter mais rotas. Espere o próximo mês para adicionar a rota.");
            }

            if (rota.getQuilometragem() > autonomiaAtual()) {
                throw new IllegalArgumentException(
                        "Autonomia insuficiente para realizar a rota. Abasteça o veículo antes de adicionar a rota.");
            }

            if (rota.getQuilometragem() > autonomiaMaxima()) {
                throw new IllegalArgumentException(
                        "Distância da rota excede a autonomia máxima do veículo. Escolha outro veículo para adicionar a rota.");
            }

            if (!manutencao.getManutencaoEmDia()) {
                throw new IllegalStateException(
                        "Manutenção do veículo em atraso. Realize manutenção antes de adicionar rota.");
            }

            if (!motorista.getCarteiraValida()) {
                throw new IllegalStateException(
                        "Carteira de motorista invalidada por multas. Espere o vencimento dos pontos da carteira antes de adicionar a rota.");
            }

            rotas[quantRotas] = rota;
            quantRotas++;
            System.err.println("Rota adicionada ao veículo de placa " + getPlaca() + " com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao adicionar rota: " + e.getMessage());
        }
    }

    /**
     * Retorna a autonomia máxima do veículo.
     *
     * @return A autonomia máxima do veículo.
     */
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
        
        addDespesaTotal(litros*this.tanque.getPreco());
        return tanque.abastecer(litros);

    }

    /**
     * Retorna a quilometragem no mês do veículo.
     *
     * @return A quilometragem no mês do veículo.
     */
    public double kmNoMes() {
        LocalDate dataAtual = LocalDate.now();
        double kmNoMes = 0;
        for (int i = 0; i < quantRotas; i++) {
            LocalDate dataRota = rotas[i].getData();
            if (dataAtual.getMonthValue() == dataRota.getMonthValue()) {
                kmNoMes += rotas[i].getQuilometragem();
            }
        }
        return kmNoMes;
    }

    /**
     * Retorna a quilometragem total do veículo.
     *
     * @return A quilometragem total do veículo.
     */
    public double kmTotal() {
        double kmTotal = 0;
        for (int i = 0; i < quantRotas; i++) {
            kmTotal += rotas[i].getQuilometragem();
        }
        return kmTotal;
    }

    /**
     * Retorna se existem multas para serm pagas ou não
     *
     * @return Retorna se existem multas para serm pagas ou não
     */
    public double pagarTodasMultas() {
           
            double valorTotal = this.motorista.pagarTodasMultas();
            addDespesaTotal(valorTotal);
           return valorTotal;
            
        }

/**
 * Percorre uma rota, atualizando o combustível disponível no tanque.
 *
 * @param rota A rota a ser percorrida.
 * @throws IllegalArgumentException Se a rota já foi percorrida.
 * @throws IllegalStateException    Se a quilometragem da rota excede a autonomia atual do veículo.
 */
public void percorrerRota(Rota rota) {
    try {
        if (rota.getRotaPercorrida()) {
            throw new IllegalArgumentException("A rota já foi percorrida. Escolha outra rota para percorrer");
        }

        if (rota.getQuilometragem() > autonomiaAtual()) {
            rota.percorrerRota();
            tanque.consumir(rota.getQuilometragem());
            kmDesdeUltimaManutencao(rota);
            despesaCombustível(rota);
        } else {
            throw new IllegalStateException("Quilometragem da rota excede a autonomia atual do veículo. Reabasteça antes de percorrer rota");
        }
    } catch (IllegalArgumentException | IllegalStateException e) {
        System.err.println("Erro ao percorrer rota: " + e.getMessage());
    }
}

/**
 * Lista as rotas não percorridas numerando cada uma para visualização do usuário.
 * Lança uma exceção se o vetor de rotas estiver vazio ou se não houver rotas não percorridas.
 *
 * @throws IllegalStateException Se o vetor de rotas estiver vazio ou se não houver rotas não percorridas.
 */
public void listarRotasNaoPercorridas() {
    if (quantRotas == 0) {
        throw new IllegalStateException("Não há rotas associadas ao veículo.");
    }

    int numeroRota = 1;
    boolean encontrouRotaNaoPercorrida = false;

    for (Rota rota : rotas) {
        if (!rota.getRotaPercorrida()) {
            encontrouRotaNaoPercorrida = true;
        }
    }
    if (!encontrouRotaNaoPercorrida) {
        throw new IllegalStateException("Não há rotas não percorridas associados ao veículo.");
    }else{
        System.out.println("Rotas não percorridas:");
        for (Rota rota : rotas) {
            if (rota != null && !rota.getRotaPercorrida()) {
                encontrouRotaNaoPercorrida = true;
                System.out.println(numeroRota + ". " + rota);
                numeroRota++;
            }
        }
    }   
}

/**
 * Percorre uma rota não percorrida com base no número fornecido na lista de rotas não percorridas.
 * Lança uma exceção se o vetor de rotas estiver vazio, se não houver rotas não percorridas ou se o número de rota fornecido for inválido.
 *
 * @param numeroRota O número da rota na lista de rotas não percorridas.
 * @throws IllegalArgumentException Se o número de rota fornecido for inválido.
 * @throws IllegalStateException    Se o vetor de rotas estiver vazio ou se não houver rotas não percorridas.
 */
public void percorrerRotaPorLista(int numeroRota) {
    if (quantRotas == 0) {
        throw new IllegalStateException("Não há rotas associadas ao veículo.");
    }

    int numeroRotaAtual = 1;

    for (Rota rota : rotas) {
        if (rota != null && !rota.getRotaPercorrida()) {
            if (numeroRotaAtual == numeroRota) {
                percorrerRota(rota);
                return;
            }
            numeroRotaAtual++;
        }
    }
    throw new IllegalArgumentException("Número de rota inválido.");
}


public String relatorioRotas() {
    StringBuilder relatorio = new StringBuilder();
    relatorio.append("Relatório de Rotas do Veículo " + this.placa + ":\n");
    for (int i = 0; i < this.quantRotas; i++) {
        if (this.rotas[i] != null) {
            relatorio.append("   Data: " + this.rotas[i].getData() + "\n");
            relatorio.append("   Quilometragem: " + this.rotas[i].getQuilometragem() + "\n");
            relatorio.append("\n");
        }
    }
    return relatorio.toString();
}
    

    /**
     * Verifica se o veículo tem autonomia suficiente para realizar a rota.
     *
     * @param rota A rota a ser verificada.
     * @return Verdadeiro se o veículo tem autonomia suficiente para realizar a
     *         rota, falso caso contrário.
     */
    public boolean podeRealizarRota(Rota rota) {
        return autonomiaAtual() >= rota.getQuilometragem();
    }

    /**
     * Adiciona a quilometragem da rota à quilometragem desde a última manutenção,
     * se a manutenção estiver em dia.
     * 
     * @param rota A rota a ser adicionada.
     */
    protected void kmDesdeUltimaManutencao(Rota rota) {
        if (this.manutencao.getManutencaoEmDia() == true) {
            this.manutencao.addKmParaManutencao(rota.getQuilometragem());
        }
    }

    /**
     * Realiza a manutenção do veículo, se necessário, e adiciona o custo da
     * manutenção à despesa total.
     * 
     * @throws Exception Se o veículo não necessitar de manutenção.
     */
    public void fazerManutencao() {
        double custoManutencao;
        if (!this.manutencao.getManutencaoPecasEmDia()) {
            custoManutencao = this.manutencao.realizarManutencaoPecas();
            addDespesaTotal(custoManutencao);
            System.out.println("Manutenção de trocas de peças realizada com sucesso.");
        } else if (!this.manutencao.getManutencaoPeriodicaEmDia()) {
            custoManutencao = this.manutencao.realizarManutencaoPeriodica();
            addDespesaTotal(custoManutencao);
            System.out.println("Manutenção periódica realizada com sucesso.");
        } else {
            System.out.println("Veículo não necessita de manutenção! Apenas "
                    + this.manutencao.getKmDesdeUltimaManutencao() + "km foram rodados.");
        }
    }

    /**
     * Adiciona uma multa ao motorista e retorna a multa mais recente.
     * 
     * @param gravidade A gravidade da multa.
     * @return A multa mais recente do motorista.
     */
    public Multa addMultaAoMotorista(String gravidade) {
        this.motorista.adicionarPontos(gravidade);
        return this.motorista.multaMaisRecente();
    }

    /**
     * Adiciona a despesa de combustível da rota à despesa total.
     * 
     * @param rota A rota a ser adicionada.
     */
    protected void despesaCombustível(Rota rota) {
        addDespesaTotal(tanque.getPreco() * (rota.getQuilometragem() / tanque.getCONSUMO()));
    }

    /**
     * Adiciona a despesa de uma multa à despesa total.
     * 
     * @param valorMulta O valor da multa.
     */
    public void despesaMultaMotorista(double valorMulta) {
        addDespesaTotal(valorMulta);
    }

    /**
     * Adiciona uma nova despesa à despesa total.
     * 
     * @param despesaNova A nova despesa a ser adicionada.
     */
    protected void addDespesaTotal(double despesaNova) {
        this.despesaTotal += despesaNova;
    }

    // #endregion

    // #region Getters

    /**
     * Retorna a placa do veículo.
     * 
     * @return A placa do veículo.
     */
    public String getPlaca() {
        return this.placa;
    }

    /**
     * Retorna as rotas do veículo.
     * 
     * @return Um array de rotas do veículo.
     */
    public Rota[] getRotas() {
        return this.rotas;
    }

    /**
     * Retorna a quantidade de rotas.
     * 
     * @return A quantidade de rotas.
     */
    public double getQuantRotas() {
        return quantRotas;
    }

    /**
     * Retorna o motorista do veículo.
     * 
     * @return O motorista do veículo.
     */
    public Motorista getMotorista() {
        return motorista;
    }

    /**
     * Retorna o total reabastecido.
     * 
     * @return O total reabastecido.
     */
    public double getTotalReabastecido() {
        return totalReabastecido;
    }

    /**
     * Retorna a despesa total.
     * 
     * @return A despesa total.
     */
    public double getDespesaTotal() {
        return despesaTotal;
    }

    /**
     * Retorna a manutenção do veículo.
     * 
     * @return A manutenção do veículo.
     */
    public Manutencao getManutencao() {
        return this.manutencao;
    }

    public Tanque getTanque() {
        return tanque;
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
        relatorio.append("Motorista: " + motorista.getNome() + "\n");
        relatorio.append("CPF do Motorista: " + motorista.getCpf()+ "\n");
        relatorio.append("Km Rodados no mes: " + kmNoMes() + " km\n");
        relatorio.append("Km Total: " + kmTotal() + " km\n");
        relatorio.append("Autonomia do veiculo: " + autonomiaAtual() + " km\n");
        relatorio.append("Tanque abastecido com: " + getTanque().getCapacidadeAtual() + " litros de "+ getTanque().getTipo() +  "\n");
        //relatorio.append(getTanque().getTipo() + "\n");
        return relatorio.toString();
    }

        

    // #endregion
}

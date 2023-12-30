import java.util.ArrayList;
import java.util.List;

public abstract class Barco implements Relatorio{

    // #region Atributos
    protected final String NOME;
    protected List<Rota> rotas;
    protected static final int MAX_ROTAS = 4;
    protected DiarioDeBordo diarioDesteBarco;
    protected final int CAPACIDADEPASSAGEIROS;
    protected static int PRECOCUSTO;
    protected double totalReabastecido;
    protected Caronte motorista;
    protected double despesaCombustivel;
    protected double despesaMulta;
    protected double despesaManutencao;
    protected double despesaSalario;
    protected Manutencao manutencao;
    protected int totalAlmasColetadasDia;
    

    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Barco.
     *
     * @param motorista O motorista do veículo.
     * @param placa     A placa do veículo.
     */
     Barco(Caronte motorista, String nomeBarco, int capacidade, int preco) {
        this.motorista = motorista;
        this.NOME = nomeBarco;
        CAPACIDADEPASSAGEIROS = capacidade;
        PRECOCUSTO = preco;
        this.rotas = new ArrayList<>();
        this.totalReabastecido = 0;
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
            if (rotas.contains(rota)) {
            throw new IllegalArgumentException("A rota já existe na lista de rotas.");
            }

            if(rotas.size() == MAX_ROTAS)
            {
                        throw new IllegalStateException(
                        "Manutenção do veículo em atraso. Realize manutenção antes de adicionar rota.");
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


    public void excluirRota(Rota rota) {
        try {
            if(rotas.isEmpty()){
            throw new IllegalStateException("Este barco não possui rotas atribuídas a ele.");
            }
            if (!rotas.contains(rota)){
            throw new IllegalArgumentException("A rota em questão não foi atribuída a este barco.");
            }

            rotas.remove(rota);
            System.err.println("Rota excluída ao veículo de placa " + getNOME() + " com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao excluir rota: " + e.getMessage());
        }
    }
    /**
     * Retorna a quilometragem no mês do veículo.
     *
     * @return A quilometragem no mês do veículo.
     */

    /**
     * Retorna a quilometragem total do veículo.
     *
     * @return A quilometragem total do veículo.
     */
    public double kmTotal() {
        double kmTotal = 0;
        for (int i = 0; i < rotas.size(); i++) {
            kmTotal += rotas.get(i).getQuilometragem();
        }
        return kmTotal;
    }

    /**
     * Retorna se existem multas para serm pagas ou não
     *
     * @return Retorna se existem multas para serm pagas ou não
     */
    public boolean pagarTodasMultas() {
        double despesaMulta =  this.motorista.pagarTodasMultas();
        addDespesaMulta(despesaMulta);
   
   
    return true;
    
}
 /*
 * @param rota A rota a ser percorrida.
 * @throws IllegalArgumentException Se a rota já foi percorrida.
 * @throws IllegalStateException    Se a quilometragem da rota excede a autonomia atual do veículo.
 */
public int percorrerRota(Rota rota) {
        if (rota.getRotaPercorrida()) {
            throw new IllegalArgumentException("A rota já foi percorrida. Escolha outra rota para percorrer");
        }else{
            int totalAlmas = rota.percorrerRota(CAPACIDADEPASSAGEIROS);
            kmDesdeUltimaManutencao(rota);
            addDespesaSalario(motorista.getSalario());
            System.out.println("Rota percorrida com sucesso! Almas mortais coletadas.");
            return totalAlmas;
        }
}

public void encerrarDia() {
        for (Rota rota : rotas) {
            if (rota != null && !rota.getRotaPercorrida()) {
                this.totalAlmasColetadasDia += rota.percorrerRota(CAPACIDADEPASSAGEIROS);
            }
        }  
}

public void iniciarDia(){
    this.despesaCombustivel = 0;
    this.despesaManutencao = 0;
    this.despesaMulta = 0;
    this.despesaSalario = 0;
    this.totalAlmasColetadasDia = 0;
    rotas.clear();
}

/**
 * Lista as rotas não percorridas numerando cada uma para visualização do usuário.
 * Lança uma exceção se o vetor de rotas estiver vazio ou se não houver rotas não percorridas.
 *
 * @throws IllegalStateException Se o vetor de rotas estiver vazio ou se não houver rotas não percorridas.
 */
public void listarRotasNaoPercorridas() {
    if (rotas.size() == 0) {
        throw new IllegalStateException("Não há rotas associadas ao veículo.");
    }

    int numeroRota = 1;
    boolean encontrouRotaNaoPercorrida = false;

    for (Rota rota : rotas) {
        if (rota != null && !rota.getRotaPercorrida()) {
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
    if (rotas.size() == 0) {
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
    relatorio.append("Relatório de Rotas do Barco " + this.NOME + ":\n");
    for (int i = 0; i < rotas.size(); i++) {
        if (rotas.get(i) != null) {
            relatorio.append(rotas.get(i).relatorio()+ "\n");

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
        if (!this.manutencao.getManutencaoPeriodicaEmDia()) {
            custoManutencao = this.manutencao.realizarManutencaoPeriodica();
            addDespesaManutencao(custoManutencao);
            System.out.println("Manutenção periódica realizada com sucesso no barco " + getNOME() + ".\n");
        } else {
            System.out.println("O barco está com a papelada em dia e não precisa de manutenção! Apenas "
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
    protected void addDespesaCombustivel(double despesaCombustivel) {
        this.despesaCombustivel += despesaCombustivel;
    }

    /**
     * Adiciona a despesa de uma multa à despesa total.
     * 
     * @param valorMulta O valor da multa.
     */
    public void addDespesaMulta(double valorMulta) {
        despesaMulta += valorMulta;
    }

    /**
     * Adiciona uma nova despesa à despesa total.
     * 
     * @param despesaNova A nova despesa a ser adicionada.
     */
    protected void addDespesaManutencao(double despesaManutencao) {
        this.despesaManutencao += despesaManutencao;
    }

    protected void addDespesaSalario (double despesaSalario) {
        this.despesaSalario += despesaSalario;
    }

    public double getDespesaTotal(){
        return (despesaCombustivel + despesaManutencao + despesaMulta + despesaSalario);
    }

    public double getTotalAlmasColetadasDia(){
         return totalAlmasColetadasDia;
    }

        public  String getTipoDeBarco() {
        if (this instanceof Gondola) {
           return "Gôndola";
        } else if (this instanceof Balsa) {
            return "Balsa";
        } else if (this instanceof Navio) {
            return "Navio";
        } else if (this instanceof Cruzeiro) {
           return "Cruzeiro";
        } else {
            throw new IllegalArgumentException("Este barco não existe e seus senhores não estão contentes.");
        }
    }

    public int getQuantRotasPercorridasHj(){
        return (int) getRotas().stream().filter(Rota::getRotaPercorrida).count();
    }
    // #endregion

    // #region Getters

    /**
     * Retorna a placa do veículo.
     * 
     * @return A placa do veículo.
     */
    public String getNOME() {
        return this.NOME;
    }

    /**
     * Retorna as rotas do veículo.
     * 
     * @return Um array de rotas do veículo.
     */
    public List<Rota> getRotas() {
        return this.rotas;
    }

    /**
     * Retorna a quantidade de rotas.
     * 
     * @return A quantidade de rotas.
     */
    public double getQuantRotas() {
        return rotas.size();
    }

    /**
     * Retorna o motorista do veículo.
     * 
     * @return O motorista do veículo.
     */
    public Caronte getMotorista() {
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
     * Retorna a manutenção do veículo.
     * 
     * @return A manutenção do veículo.
     */
    public Manutencao getManutencao() {
        return this.manutencao;
    }

    public int getPRECOCUSTO(){
        return PRECOCUSTO;
    }

    public double getKmAteProximaManutencao(){
       return manutencao.getKmAteProximaManutencao();
    }
    
    // #endregion
}

import java.time.*;

public abstract class Veiculo {
    // Atributos de Classe
    protected static final int MAX_ROTAS; // Constante com valor 30 para qualquer veículo 
    protected String placa;
    protected Rota[] rotas;
    protected int quantRotas;
    protected double tanqueAtual;
    protected double totalReabastecido;
    protected Motorista motorista;
    protected Tanque tanque;
    protected double despesaTotal;
    protected Manutencao manutencao;

    static {
        // Inicialização do atributo estático MAX_ROTAS com o valor 30
        MAX_ROTAS = 30;
    }

    // Construtor
    public Veiculo(Motorista motorista, String placa) {
        this.motorista = motorista;
        this.placa = placa;
        this.quantRotas = 0;
        this.rotas = new Rota[MAX_ROTAS];
        this.totalReabastecido = 0; // Inicialmente, o tanque está vazio
        this.despesaTotal = 0; // Inicialmente, o veículo não teve despesa
    }

    // Métodos da Classe

    // Adiciona a rota recebida como parâmetro ao vetor de rotas do veículo
    // Verifica se o vetor de rotas do veículo tem espaço para adicionar a rota
    // Verifica se o veículo tem autonomia para realizar a rota
    // Verifica se o veículo está com sua manutenção em dia
    // Verifica se o motorista tem menos pontos na carteira que o limite
    //Retorna true se a adição foi bem-sucedida, false se não foi possível adicionar rota
    public void addRota(Rota rota) {
        try {
            if (quantRotas >= MAX_ROTAS) {
                throw new IllegalArgumentException("Veículo não pode ter mais rotas. Espere o próximo mês para adicionar a rota.");
            }
    
            if (rota.getQuilometragem() > autonomiaAtual()) {
                throw new IllegalArgumentException("Autonomia insuficiente para realizar a rota. Abasteça o veículo antes de adicionar a rota.");
            }
    
            if (rota.getQuilometragem() > autonomiaMaxima()) {
                throw new IllegalArgumentException("Distância da rota excede a autonomia máxima do veículo. Escolha outro veículo para adicionar a rota.");
            }
    
            if (!manutencao.getManutencaoEmDia()) {
                throw new IllegalStateException("Manutenção do veículo em atraso. Realize manutenção antes de adicionar rota.");
            }
    
            if (!motorista.getCarteiraValida()) {
                throw new IllegalStateException("Carteira de motorista invalidada por multas. Espere o vencimento dos pontos da carteira antes de adicionar a rota.");
            }
    
            rotas[quantRotas] = rota;
            quantRotas++;
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao adicionar rota: " + e.getMessage());
        }
    }
    

    // Calcula a autonomia máxima, considerando o tanque cheio, com base no consumo
    public double autonomiaMaxima()
    {
        return tanque.autonomiaMaxima();
    }

    // Calcula a autonomia atual, considerando o tanque atual, com base no consumo
    public double autonomiaAtual()
    {
        return tanque.autonomiaAtual();
    }

    // Abastece o tanque do veículo com uma quantidade específica de litros para reabastecimento e retorna o total abastecido
    //Não permite reabastecimento menor ou igual a 0 e, se maior que a capacidade máxima, reabastece apenas o suficiente para encher tanque  
    public double abastecer(double litros)
    {
            return tanque.abastecer(litros);
    }

    // Calcula o total de quilômetros percorridos no mês atual com base nas rotas e suas distâncias e datas
    //Uma data com o mês corrente é criada para comparação com as datas das rotas
    public double kmNoMes()
    {
        LocalDate dataAtual = LocalDate.now();
        double kmNoMes = 0;
        for (int i = 0; i < quantRotas; i++)
        {
            LocalDate dataRota = rotas[i].getData();
            if(dataAtual.getMonthValue() == dataRota.getMonthValue())
            {
                kmNoMes += rotas[i].getQuilometragem();
            }
        }
        return kmNoMes;
    }

    // Calcula o total de quilômetros percorridos desde o início do uso do veículo com base nas rotas
    public double kmTotal()
    {
        double kmTotal = 0;
        for (int i = 0; i < quantRotas; i++)
        {
            kmTotal += rotas[i].getQuilometragem();
        }
        return kmTotal;
    }

    // Registra a rota percorrida, atualizando o combustível disponível no tanque
    public void percorrerRota(Rota rota)
    {
            double consumoRota = (rota.getQuilometragem() / tanque.getCONSUMO());
            tanqueAtual -= consumoRota;
            kmDesdeUltimaManutencao(rota);
            despesaCombustível(rota);
    }

     // Verifica se o veículo tem autonomia suficiente para realizar a rota
    public boolean podeRealizarRota(Rota rota) {
        return autonomiaAtual() >= rota.getQuilometragem();
    }

    // Método para retornar a placa do Veículo
    public String getPlaca()
    {
        return this.placa;
    }

    // Método toString do Veículo, formatado para retornar apenas sua placa, seguido de sua kmTotal
    @Override public String toString()
    {
        return ("Veículo: " + this.placa + "/nTotal de Quilômetros Percorridos: " + kmTotal());
    }

    // Método para retornar as rotas do Veículo
    public Rota[] getRotas()
    {
        return this.rotas;
    }

    public double getQuantRotas() {
        return quantRotas;
    }
    public Motorista getMotorista() {
        return motorista;
    }
    public double getTotalReabastecido(){
        return totalReabastecido;
    }

    public double getDespesaTotal(){
    return despesaTotal;
    }

    protected void kmDesdeUltimaManutencao(Rota rota){
        if(this.manutencao.getManutencaoEmDia() == true)
        {
           this.manutencao.addKmParaManutencao(rota.getQuilometragem());
        }
    }
    public void fazerManutencao(){
        double custoManutencao;
       if(this.manutencao.getManutencaoPecasEmDia())
       {
        custoManutencao = this.manutencao.realizarManutencaoPecas();
        addDespesaTotal(custoManutencao);
        System.out.println("Manutenção de trocas de peças realizada com sucesso.");
       }   
       else if(this.manutencao.getManutencaoPeriodicaEmDia()){
        custoManutencao = this.manutencao.realizarManutencaoPeriodica();
        addDespesaTotal(custoManutencao);
        System.out.println("Manutenção periódica realizada com sucesso.");
       }
       else{
            System.out.println("Veículo não necessita de manutenção! Apenas " + this.manutencao.getKmDesdeUltimaManutencao() + "km foram rodados.");
        }
       }
       public Multa addMultaAoMotorista(String gravidade){
        this.motorista.adicionarPontos(gravidade);
        return this.motorista.multaMaisRecente();
       }

    protected void despesaCombustível(Rota rota){
       addDespesaTotal(tanque.getPreco() * (rota.getQuilometragem() / tanque.getCONSUMO()));
    }
    
    public void despesaMultaMotorista(double valorMulta) {
        addDespesaTotal(valorMulta);
    }

    protected void addDespesaTotal(double despesaNova){
        this.despesaTotal += despesaNova;
    }

    public Manutencao getManutencao() {
        return this.manutencao;
    }    
}

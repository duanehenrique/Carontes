import java.time.*;

public abstract class Veiculo {
    // Atributos de Classe
    private static final int MAX_ROTAS; // Constante com valor 30 para qualquer veículo 
    private static final double CONSUMO; // Constante com valor 8,2 km/litro para qualquer veículo 
    private String placa;
    private Rota[] rotas;
    private int quantRotas;
    private double tanqueAtual;
    private final double tanqueMax; // Após definida a capacidade do tanque do veículo, não é possível alterar
    private double totalReabastecido;
    private Motorista motorista;

    static {
        // Inicialização do atributo estático MAX_ROTAS com o valor 30
        MAX_ROTAS = 30;
        CONSUMO = 8.2; 
    }

    // Construtor
    public Veiculo(Motorista motorista, String placa, double tanqueMax) {
        this.motorista = motorista;
        this.placa = placa;
        this.tanqueAtual = 0; // Inicialmente, o tanque está vazio
        this.tanqueMax = tanqueMax;
        this.quantRotas = 0;
        this.rotas = new Rota[MAX_ROTAS];
        this.totalReabastecido = 0; // Inicialmente, o tanque está vazio
    }

    // Métodos da Classe

    // Adiciona a rota recebida como parâmetro ao vetor de rotas do veículo
    // Verifica se o vetor de rotas do veículo tem espaço para adicionar a rota
    // Verifica se o veículo tem autonomia para realizar a rota
    //Retorna true se a adição foi bem-sucedida, false se não foi possível adicionar rota
    public boolean addRota(Rota rota)
    {
        if ((quantRotas < MAX_ROTAS) && (rota.getQuilometragem() <= autonomiaAtual()) && (rota.getQuilometragem() <= autonomiaMaxima()))
        {
            rotas[quantRotas] = rota;
            quantRotas++;
            percorrerRota(rota);
            return true;
        }
        else
        {
            return false;
        }
    }

    // Calcula a autonomia máxima, considerando o tanque cheio, com base no consumo
    private double autonomiaMaxima()
    {
        return tanqueMax * CONSUMO;
    }

    // Calcula a autonomia atual, considerando o tanque atual, com base no consumo
    private double autonomiaAtual()
    {
        return tanqueAtual * CONSUMO;
    }

    // Abastece o tanque do veículo com uma quantidade específica de litros para reabastecimento e retorna o total abastecido
    //Não permite reabastecimento menor ou igual a 0 e, se maior que a capacidade máxima, reabastece apenas o suficiente para encher tanque  
    public double abastecer(double litros)
    {
        if (litros > 0)
        {
            if(tanqueAtual+litros > tanqueMax)
            {
                double reabastecidoAgora = (litros-((tanqueAtual+litros)-tanqueMax));
                totalReabastecido = reabastecidoAgora;
                tanqueAtual = tanqueMax;
                return totalReabastecido;
            }
            else
            {
                tanqueAtual += litros;
                totalReabastecido = litros;
                return totalReabastecido;
            }
        }
        return litros;
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
    private void percorrerRota(Rota rota)
    {
            double consumoRota = (rota.getQuilometragem() / CONSUMO);
            tanqueAtual -= consumoRota;
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

    public double getQuntRotas() {
        return quantRotas;
    }

    public void receberMulta(Multa multa) {
        motorista.adicionarPontos(multa.getPontos());
    }
    public Motorista getMotorista() {
        return motorista;
    }

}

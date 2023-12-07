/**
 * Representa um tanque de combustível de um veículo.
 */
public class Tanque {

    // Constante que define o consumo médio do veículo em km/l.
    private static final double CONSUMO = 8.2;

    // Capacidade máxima do tanque em litros.
    private double CapacidadeMaxima;

    // Quantidade atual de combustível no tanque em litros.
    private double CapacidadeAtual;

    /**
     * Construtor da classe Tanque.
     *
     * @param CapacidadeAtual Quantidade inicial de combustível no tanque.
     * @param CapacidadeMaxima Capacidade máxima do tanque.
     */
    Tanque(double CapacidadeAtual, double CapacidadeMaxima){
        this.CapacidadeAtual = CapacidadeAtual;
        this.CapacidadeMaxima = CapacidadeMaxima;
    }

    /**
     * Abastece o tanque com uma certa quantidade de litros.
     * Se o abastecimento exceder a capacidade máxima, o tanque é preenchido até sua capacidade máxima.
     *
     * @param litros Quantidade de litros para abastecer.
     * @return Retorna a capacidade atual do tanque após o abastecimento.
     */
    public double abastecer(double litros){
        CapacidadeAtual += litros;
        if(CapacidadeAtual > CapacidadeMaxima){
            CapacidadeAtual = CapacidadeMaxima;
        }
        return CapacidadeAtual;
    }

    /**
     * Calcula a autonomia máxima do veículo com base na capacidade máxima do tanque e no consumo médio.
     *
     * @return Retorna a autonomia máxima em quilômetros.
     */
    public double autonomiaMaxima(){
        return CapacidadeMaxima * CONSUMO;
    }

    /**
     * Calcula a autonomia atual do veículo com base na capacidade atual do tanque e no consumo médio.
     *
     * @return Retorna a autonomia atual em quilômetros.
     */
    public double autonomiaAtual(){
        return CapacidadeAtual * CONSUMO;
    }

    public double getCONSUMO(){
        return CONSUMO;
    }
}

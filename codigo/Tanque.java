import java.text.Normalizer;

public class Tanque {
    // #region Atributos
    private final double CAPACIDADEMAXIMA;
    private double capacidadeAtual;
    private final TipoCombustivel tipoCombustivel;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Tanque.
     *
     * @param capacidadeAtual  Quantidade inicial de combustível no tanque.
     * @param CAPACIDADEMAXIMA Capacidade máxima do tanque.
     */
    Tanque(double capacidadeMaxima, String tipo) {
        this.capacidadeAtual = 0;
        this.CAPACIDADEMAXIMA = capacidadeMaxima;
        switch (Normalizer.normalize(tipo.toUpperCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")) {
            case "ALCOOL":
                tipoCombustivel = TipoCombustivel.ALCOOL;
                break;
            case "GASOLINA":
                tipoCombustivel = TipoCombustivel.GASOLINA;
                break;
            case "DIESEL":
                tipoCombustivel = TipoCombustivel.DIESEL;
                break;
            default:
                throw new IllegalArgumentException("Tipo de combustível desconhecido: " + tipo);
        }
    }
    // #endregion

    // #region metodos
    /**
     * Abastece o tanque com uma certa quantidade de litros.
     * Se o abastecimento exceder a capacidade máxima, o tanque é preenchido até sua
     * capacidade máxima.
     *
     * @param litros Quantidade de litros para abastecer.
     * @return Retorna a capacidade atual do tanque após o abastecimento.
     */
    public double abastecer(double litros) {
        if (capacidadeAtual == CAPACIDADEMAXIMA) {
            return 0;
        } else if((capacidadeAtual + litros) > CAPACIDADEMAXIMA) {
            
                double totalReabastecido = CAPACIDADEMAXIMA - capacidadeAtual;
                capacidadeAtual = CAPACIDADEMAXIMA;
                return totalReabastecido;
            }
         else {
            capacidadeAtual += litros;
            return litros;
         }
            
        }
    

    // #endregion

    // #region Getters

    /**
     * Calcula a autonomia máxima do veículo com base na capacidade máxima do tanque
     * e no consumo médio.
     *
     * @return Retorna a autonomia máxima em quilômetros.
     */
    public double autonomiaMaxima() {
        return CAPACIDADEMAXIMA * tipoCombustivel.getConsumoMedio();
    }

    /**
     * Calcula a autonomia atual do veículo com base na capacidade atual do tanque e
     * no consumo médio.
     *
     * @return Retorna a autonomia atual em quilômetros.
     */
    public double autonomiaAtual() {
        return capacidadeAtual * tipoCombustivel.getConsumoMedio();
    }

    /**
     * 
     * @return Retorna o consumo de acordo com o tipo do combustivel.
     */
    public double getCONSUMO() {
        return tipoCombustivel.getConsumoMedio();
    }

    /***
     * @return Retorna o preço do combustível.
     */
    public double getPreco() {
        return tipoCombustivel.getPreco();
    }

    /***
     * @return Retorna a capacidade maxima.
     */
    public double getCapacidadeMaxima() {
        return CAPACIDADEMAXIMA;
    }

    /***
     * @return Retorna a capacidade atual.
     */
    public double getCapacidadeAtual() {
        return capacidadeAtual;
    }
    // #endregion

    public TipoCombustivel getTipo() {
        return tipoCombustivel;
    }
}

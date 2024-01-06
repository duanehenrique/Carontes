import java.text.Normalizer;

public class Tanque implements Normalizador{
    // #region Atributos
    private final int CAPACIDADEMAXIMA;
    private double capacidadeAtual;
    private TipoCombustivel tipoCombustivel;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Tanque.
     *
     * @param capacidadeAtual  Quantidade inicial de combustível no tanque.
     * @param CAPACIDADEMAXIMA Capacidade máxima do tanque.
     */
    Tanque(int capacidadeMaxima, String tipo) {
        this.CAPACIDADEMAXIMA = capacidadeMaxima;
        instalarTanque(tipo);
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
            System.err.println("Tanque do barco está cheio e não pode ser abastecido.\nTanque com " + capacidadeAtual + " de " + CAPACIDADEMAXIMA + " litros.\n");
            return 0;
        } else if(litros == Double.MAX_VALUE) {
            
                double totalReabastecido = CAPACIDADEMAXIMA - capacidadeAtual;
                capacidadeAtual = CAPACIDADEMAXIMA;
                System.err.println("Tanque do barco foi abastecido até estar cheio.\nTanque com " + capacidadeAtual + " de " + CAPACIDADEMAXIMA + " litros.\n");
                return totalReabastecido * tipoCombustivel.getCusto();
            }else if((capacidadeAtual + litros) > CAPACIDADEMAXIMA) {
            
                double totalReabastecido = CAPACIDADEMAXIMA - capacidadeAtual;
                capacidadeAtual = CAPACIDADEMAXIMA;
                System.err.println("Abastecimento solicitado à garagem excede a capacidade do tanque. Barco foi abastecido até o máximo possível.\nTanque com " + capacidadeAtual + " de " + CAPACIDADEMAXIMA + " litros.\n");
                return totalReabastecido * tipoCombustivel.getCusto();
            }
         else {
            capacidadeAtual += litros;
            System.err.println("Barco foi abastecido com " + litros + " litros.\nTanque com " + capacidadeAtual + " de " + CAPACIDADEMAXIMA + " litros.\n");
            return litros * tipoCombustivel.getCusto();
         }
            
        }

        public void consumir(double km){
            if (((capacidadeAtual) - (km / getCONSUMO())) < 0) {
                throw new IllegalArgumentException("Não há combustível suficiente.");
            } else {
                capacidadeAtual -= (km / getCONSUMO());
            }
        }
    
public void instalarTanque(String tipo){
switch (normalizar(tipo)) {
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
                throw new IllegalArgumentException("Hefesto não pôde fabricar o barco desejado. Tipo de combustível desconhecido: " + tipo);
        }
    capacidadeAtual = 0;
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
    public double getCusto() {
        return tipoCombustivel.getCusto();
    }

    /***
     * @return Retorna a capacidade maxima.
     */
    public int getCapacidadeMaxima() {
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

    public int getAdicionalPrecoVenda(){
        return tipoCombustivel.getAdicionalPrecoVenda();
    }

    private static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.toUpperCase();

        return normalizado;
    }
}

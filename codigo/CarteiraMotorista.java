
/**
 * Classe CarteiraMotorista.
 * Esta classe representa uma carteira de motorista no sistema.
 */
import java.util.ArrayList;
import java.util.List;

public class CarteiraMotorista implements Relatorio{
    // #region Atributos
    private List<Multa> multas;
    // #endregion

    // #region Construtores
    /**
     * Construtor para a classe CarteiraMotorista.
     * Inicializa a lista de multas como uma nova lista vazia.
     */
    public CarteiraMotorista() {
        this.multas = new ArrayList<>();
    }

    public CarteiraMotorista(CarteiraMotorista outraCarteiraMotorista) {
        this.multas = new ArrayList<>(outraCarteiraMotorista.multas);
    }
    // #endregion

    /**
     * Adiciona uma nova multa à lista de multas com base na gravidade fornecida.
     * 
     * @param gravidade A gravidade da multa a ser adicionada.
     * @throws IllegalArgumentException Se a gravidade fornecida não for
     *                                  reconhecida.
     */
    public void adicionarMulta(Multa novaMulta) {
        if(novaMulta != null){
            multas.add(novaMulta);
        }
    }

    /**
     * Verifica se a carteira de motorista é válida com base no total de pontos
     * acumulados.
     * 
     * @return true se o total de pontos for menor que 30, false caso contrário.
     */
    public boolean carteiraValida() {
        int totalPontos = calcularTotalPontos();
        return (totalPontos < 30);
    }

    /**
     * Calcula o total de pontos acumulados com base nas multas ativas.
     * 
     * @return O total de pontos acumulados.
     */
    public int calcularTotalPontos() {
        int totalPontos = 0;
        for (Multa multa : multas) {
            if (multa != null) {
                    totalPontos += multa.getPontos();
            }
        }
        return totalPontos;
    }

    /**
     * Lista todas as multas associadas a esta carteira de motorista.
     * 
     * @return Uma lista de todas as multas.
     */
    public List<Multa> listarMultas() {
        return multas;
    }

    /**
     * Retorna a multa mais recente associada a esta carteira de motorista.
     * 
     * @return A multa mais recente.
     * @throws IllegalStateException Se não houver multas atreladas a esta carteira.
     */
    public Multa multaMaisRecente() {
        if (multas.isEmpty()) {
            throw new IllegalStateException("Não há multas atreladas a esta carteira.");
        }
        return multas.get(multas.size() - 1);
    }

    /**
     * Paga uma multa específica associada a esta carteira de motorista.
     * 
     * @param posicaoMulta A posição da multa na lista de multas.
     * @return O valor pago pela multa.
     * @throws IllegalArgumentException Se a posição da multa for inválida.
     * @throws IllegalStateException    Se a multa já foi paga.
     */
    public double pagarMulta(int posicaoMulta) {
        if (posicaoMulta < 1 || posicaoMulta > multas.size()) {
            throw new IllegalArgumentException("Posição de multa inválida");
        }
        Multa multaParaPagar = multas.get(posicaoMulta - 1);
            double valorPago = multaParaPagar.pagarMulta();
            multas.remove(multaParaPagar);
            return valorPago;
    }

    /**
     * Paga todas as multas associadas a esta carteira de motorista.
     * 
     * @return O valor total pago por todas as multas.
     */
    public double pagarTodasMultas() {
        double valorTotalPago = 0.0;
        for (Multa multa : multas) {
            try {
                valorTotalPago += multa.pagarMulta();
            } catch (IllegalStateException e) {
                System.out.println("Multa já paga: " + e.getMessage());
            }
        }
        return valorTotalPago;
    }
    
    public boolean temMulta(){
        return !multas.isEmpty();
    }
    
            // #region Relatorio
        /**
     * Retorna uma representação em string do veículo.
     * 
     * @return Uma string representando o veículo.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        if (multas.isEmpty()) {
            relatorio.append("Multas do Caronte: Não há multas.\n");
        } else {
            relatorio.append("Multas do Caronte:\n");
            for (int i = 0; i < multas.size(); i++) {
                relatorio.append((i + 1) + ". " + multas.get(i).relatorio() + "\n");
            }
        }
        relatorio.append("Pontos na Carteira: "). append(calcularTotalPontos()).append("\n");
        relatorio.append(carteiraValida() ? "Carteira Válida. Caronte habilitado para dirigir" : "Carteira Suspensa. Caronte impedido de dirigir até que multas sejam pagas").append("\n");
        return relatorio.toString();
    }
    //#endregion
}

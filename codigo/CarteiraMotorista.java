
/**
 * Classe CarteiraMotorista.
 * Esta classe representa uma carteira de motorista no sistema.
 */
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class CarteiraMotorista {
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
    // #endregion

    /**
     * Adiciona uma nova multa à lista de multas com base na gravidade fornecida.
     * 
     * @param gravidade A gravidade da multa a ser adicionada.
     * @throws IllegalArgumentException Se a gravidade fornecida não for
     *                                  reconhecida.
     */
    public void adicionarMulta(String gravidade) {
        Multa novaMulta;
        switch (Normalizer.normalize(gravidade.toUpperCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")) {
            case "LEVE":
                novaMulta = Multa.LEVE;
                break;
            case "MEDIA":
                novaMulta = Multa.MEDIA;
                break;
            case "GRAVE":
                novaMulta = Multa.GRAVE;
                break;
            case "GRAVISSIMA":
                novaMulta = Multa.GRAVISSIMA;
                break;
            default:
                throw new IllegalArgumentException("Gravidade de multa desconhecida: " + gravidade);
        }
        multas.add(novaMulta);
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
                if (multa.multaExpirou()) {
                    totalPontos += multa.getPontos();
                }
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
     * Lista todas as multas ativas associadas a esta carteira de motorista.
     * 
     * @return Uma lista de todas as multas ativas.
     */
    public List<Multa> listarMultasAtivas() {
        List<Multa> multasAtivas = new ArrayList<>();
        for (Multa multa : multas) {
            if (!multa.multaExpirou()) {
                multasAtivas.add(multa);
            }
        }
        return multasAtivas;
    }

    /**
     * Lista todas as multas não pagas associadas a esta carteira de motorista.
     * 
     * @return Uma lista de todas as multas não pagas.
     */
    public List<Multa> listarMultasNaoPagas() {
        List<Multa> multasNaoPagas = new ArrayList<>();
        for (Multa multa : multas) {
            if (!multa.getMultaPaga()) {
                multasNaoPagas.add(multa);
            }
        }
        return multasNaoPagas;
    }

    /**
     * Lista todas as multas pagas associadas a esta carteira de motorista.
     * 
     * @return Uma lista de todas as multas pagas.
     */
    public List<Multa> listarMultasPagas() {
        List<Multa> multasPagas = new ArrayList<>();
        for (Multa multa : multas) {
            if (multa.getMultaPaga()) {
                multasPagas.add(multa);
            }
        }
        return multasPagas;
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
        try {
            double valorPago = multaParaPagar.pagarMulta();
            return valorPago;
        } catch (IllegalStateException e) {
            System.out.println("Multa já paga: " + e.getMessage());
            return 0;
        }
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
}

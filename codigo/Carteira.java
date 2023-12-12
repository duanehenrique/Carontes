import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private List<Multa> multas;

    public Carteira() {
        this.multas = new ArrayList<>();
    }

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

    public boolean carteiraValida() {
        int totalPontos = calcularTotalPontos();
        return (totalPontos > 30);
    }

    public int calcularTotalPontos() {
        int totalPontos = 0;
        for (Multa multa : multas) {
            if(multa.multaExpirou()){
            totalPontos += multa.getPontos();
            }
        }
        return totalPontos;
    }

    public List<Multa> listarMultas() {
        return multas;
    }

    public List<Multa> listarMultasAtivas() {
        List<Multa> multasAtivas = new ArrayList<>();
        for (Multa multa : multas) {
            if (!multa.multaExpirou()) {
                multasAtivas.add(multa);
            }
        }
        return multasAtivas;
    }

    public List<Multa> listarMultasNaoPagas() {
        List<Multa> multasNaoPagas = new ArrayList<>();
        for (Multa multa : multas) {
            if (!multa.getMultaPaga()) {
                multasNaoPagas.add(multa);
            }
        }
        return multasNaoPagas;
    }

    public List<Multa> listarMultasPagas() {
        List<Multa> multasPagas = new ArrayList<>();
        for (Multa multa : multas) {
            if (multa.getMultaPaga()) {
                multasPagas.add(multa);
            }
        }
        return multasPagas;
    }

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

import java.text.Normalizer;
import java.time.LocalDate;
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
        LocalDate dataDeHoje = LocalDate.now();
        for (Multa multa : multas) {
            if(multa.getDataDeEmissao().plusYears(1).isBefore(dataDeHoje)){
            totalPontos += multa.getPontos();
            }
        }
        return totalPontos;
    }
}

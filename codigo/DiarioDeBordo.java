import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiarioDeBordo implements Relatorio {
    private Map<LocalDate, Barco> diarioPorDia;
    private Barco barcoDoDiario;

    DiarioDeBordo(Barco barco) {
        this.diarioPorDia = new HashMap<>();
        this.barcoDoDiario = barco;
    }

        public void addDiario(Barco esteBarco, LocalDate data){
            diarioPorDia.put(data, esteBarco);
        }

        public double balancoTotalPorDia(LocalDate dataDoDiario) {
            Barco barco = diarioPorDia.get(dataDoDiario);  
            double ganhoTotal = 0;
            double despesaTotal = 0;
            
            if (barco != null) {
                if (!barco.getRotas().isEmpty()) {
                    List<Rota> listaRotas = barco.getRotas();        
                    for (Rota rota : listaRotas) {
                        if (rota.getRotaPercorrida()) {
                            ganhoTotal += barco.getTotalAlmasColetadas();
                            despesaTotal += barco.getDespesaTotal();
                        }
                    }        
                    return ganhoTotal - despesaTotal;
                } else {
                    throw new IllegalArgumentException("O barco ainda não possui rotas registradas.");
                }
            } else {
                LocalDate dataMaisAvancada = diarioPorDia.keySet().stream().max(LocalDate::compareTo).orElse(null);
        
                if (dataMaisAvancada != null && dataDoDiario.isAfter(dataMaisAvancada)) {
                    throw new IllegalArgumentException("A data informada é depois da data mais tardia presente no diário.\nTente novamente com outra data.");
                } else {
                    throw new IllegalArgumentException("Uma rota com a data informada não foi encontrada.\nTente novamente com outra data.");
                }
            }
        }
        public double balancoTotalGeral(LocalDate dataInicial, int numeroDias) {
            double balancoTotalGeral = 0;
        
            for (int i = 0; i < numeroDias; i++) {
                LocalDate dataDoDiario = dataInicial.plusDays(i);
                try {
                    double balancoDia = balancoTotalPorDia(dataDoDiario);
                    balancoTotalGeral += balancoDia;
                    System.out.println("Balanço para " + dataDoDiario + ": " + balancoDia);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        
            return balancoTotalGeral;
        }
        
        public String relatorio() {
            StringBuilder relatorio = new StringBuilder();

            relatorio.append("Diário de bordo do barco " + barcoDoDiario.getNome());
    
            for (Map.Entry<LocalDate, Barco> entry : diarioPorDia.entrySet()) {
                LocalDate dataDoDiario = entry.getKey();
                Barco barco = entry.getValue();
                relatorio.append("Data: ").append(dataDoDiario).append("\n");
                relatorio.append(barco.relatorio()).append("\n");
                relatorio.append("\n");
            }
    
            return relatorio.toString();
        }

        }

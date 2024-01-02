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

        public void encerrarDia(LocalDate data){
            barcoDoDiario.encerrarDia();
            diarioPorDia.put(data, barcoDoDiario);
        }

        public void iniciarDiario(){
            barcoDoDiario.iniciarDia();
        }

        public void addRota(Rota rota) {
        barcoDoDiario.addRota(rota);
        }
        
        public void excluirRota(Rota rota) {
        barcoDoDiario.excluirRota(rota);
        }

        public double balancoTotalPorDia(LocalDate dataDoDiario) {
            Barco barco = diarioPorDia.get(dataDoDiario);  
            
            if (barco != null) {
                return calcularBalancoTotal(barco);
            } else {
                LocalDate dataMaisAvancada = diarioPorDia.keySet().stream().max(LocalDate::compareTo).orElse(null);
        
                if (dataMaisAvancada != null && dataDoDiario.isAfter(dataMaisAvancada)) {
                    throw new IllegalArgumentException("A data informada é depois da data mais tardia presente no diário.\nTente novamente com outra data.");
                } else {
                    throw new IllegalArgumentException("Uma rota com a data informada não foi encontrada.\nTente novamente com outra data.");
                }
            }
        }

        public double balancoTotalDeOntem() {
            Barco barco = getUltimoBarcoInserido();  
            if (barco != null) {
                return calcularBalancoTotal(barco);
            } else {
                throw new IllegalArgumentException("Barco não encerrou seu primeiro dia de viagens ainda. Encerre o dia primeiro.");
                }
            }

        public double balancoTotalGeralPorDias(LocalDate dataInicial, int numeroDias) {
            double balancoTotalGeral = 0;
        
            for (int i = 0; i < numeroDias; i++) {
                LocalDate dataDoDiario = dataInicial.plusDays(i);
                try {
                    double balancoDia = balancoTotalPorDia(dataDoDiario);
                    balancoTotalGeral += balancoDia;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        
            return balancoTotalGeral;
        }

        private double calcularBalancoTotal(Barco barcoDoDia){
            double ganhoTotal = 0;
            double despesaTotal = 0;
            if (!barcoDoDia.getRotas().isEmpty()) {
                    List<Rota> listaRotas = barcoDoDia.getRotas();        
                    for (Rota rota : listaRotas) {
                        if (rota.getRotaPercorrida() && rota != null) {
                            ganhoTotal += barcoDoDia.getTotalAlmasColetadasDia();
                            despesaTotal += barcoDoDia.getDespesaTotal();
                        }
                    }        
                    return ganhoTotal - despesaTotal;
                } else {
                    return 0;                
                    }
        }

        public Barco getUltimoBarcoInserido() {
                List<Barco> barcos = new ArrayList<>(diarioPorDia.values());
                return barcos.get(barcos.size() - 1);
        }

        public Barco getBarcoDoDiario() {
            return barcoDoDiario;
        }
        
        public String relatorio() {
            StringBuilder relatorio = new StringBuilder();

            relatorio.append("Diário de bordo do barco " + barcoDoDiario.getNOME());
    
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

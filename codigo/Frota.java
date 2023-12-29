import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Frota implements Normalizador {
    // #region Atributos
    private List<DiarioDeBordo> diariosDeBordo;
    private LocalDate dataUltimaFrota;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Frota.
     *
     * @param tamanhoFrota O tamanho da frota.
     */
    public Frota() {
        this.diariosDeBordo = new ArrayList<>();
        this.dataUltimaFrota = LocalDate.now();
    }

    // #endregion

    // #region Métodos
    /**
     * Adiciona um veículo à frota.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public void adicionarBarco(Barco barco) {
        if (diariosDeBordo.stream().anyMatch(diario -> diario.getBarcoDoDiario().equals(barco))) {
            throw new IllegalArgumentException("O barco já faz parte da frota de Carontes");
        }
        DiarioDeBordo diario = new DiarioDeBordo(barco);
        diariosDeBordo.add(diario);
    }
    
    

    /**
     * Gera um relatório da frota.
     * 
     * @return Uma string contendo o relatório da frota.
     */
    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório da Frota de Carontes:\n");
    
        for (DiarioDeBordo diario : diariosDeBordo) {
            relatorio.append(diario.relatorio()).append("\n");
        }
    
        return relatorio.toString();
    }
    

    public void exibirRelatorioRotas() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite o nome do barco para exibir o relatório de rotas: ");
        String nomeBarco = teclado.nextLine().toUpperCase();
        DiarioDeBordo diario = this.localizarDiario(nomeBarco);
    
        if (diario != null) {
            System.out.println(diario.relatorio());
        } else {
            System.out.println("Barco não encontrado.");
        }
    }
    

    /**
     * Localiza um veículo na frota pela placa.
     * 
     * @param placa A placa do veículo a ser localizado.
     * @return O veículo localizado, ou null se não for encontrado.
     */
        private DiarioDeBordo localizarDiario(String nome) {
        for (DiarioDeBordo diario : diariosDeBordo) {
            if (diario != null && normalizar(nome).equals(normalizar(diario.getBarcoDoDiario().getNOME()))) {
            return diario;
            }
        }
        throw new NoSuchElementException("Não há um barco com este nome na frota de Carontes.");
        }
    
    
    /**
     * Calcula a quilometragem total da frota.
     * 
     * @return A quilometragem total.
     */
    public double quilometragemTotal() {
        double totalKm = 0.0;
        for (DiarioDeBordo diario : diariosDeBordo) {
            totalKm += diario.getBarcoDoDiario().kmTotal();
        }
        return totalKm;
    }
    
    

    /**
     * Encontra o veículo com a maior quilometragem total na frota.
     * 
     * @return O veículo com a maior quilometragem total.
     */
    public Barco maiorKmTotal() {
        Barco barcoComMaiorKm = null;
        double maiorKm = 0.0;
    
        for (DiarioDeBordo diario : diariosDeBordo) {
            if (diario != null) {
                Barco barco = diario.getBarcoDoDiario();
                if (barco != null && barco.kmTotal() > maiorKm) {
                    barcoComMaiorKm = barco;
                    maiorKm = barco.kmTotal();
                }
            }
        }
    
        return barcoComMaiorKm;
    }
    
    
    

    /**
     * Encontra o veículo com a maior quilometragem média na frota.
     * 
     * @return O veículo com a maior quilometragem média.
     */
    public Barco maiorKmMedia() {
        Barco barcoComMaiorKmMedia = null;
        double maiorKmMedia = 0.0;
    
        for (DiarioDeBordo diario : diariosDeBordo) {
            if (diario != null) {
                Barco barco = diario.getBarcoDoDiario();
                if (barco != null && barco.getQuantRotas() > 0) {
                    double kmMedia = barco.kmTotal() / barco.getQuantRotas();
                    if (kmMedia > maiorKmMedia) {
                        barcoComMaiorKmMedia = barco;
                        maiorKmMedia = kmMedia;
                    }
                }
            }
        }
    
        return barcoComMaiorKmMedia;
    }
    
    

        /**
     * Verifica se houve mudança de mês.
     * 
     * @return true se o mês virou, false caso contrário.
     */
    private void passarDia(){
        LocalDate dataDeHoje = LocalDate.now();
        if (dataDeHoje.getMonthValue() != dataUltimaFrota.getMonthValue()) {
            dataUltimaFrota = dataDeHoje;
        }else{
        }        
    }

    // #region Getters
    /**
     * Retorna os veículos da frota.
     * 
     * @return Um array contendo os veículos da frota.
     */
    public List<DiarioDeBordo> getDiarioDeBordos() {
        return diariosDeBordo;
    }
    // #endregion

    private static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.toUpperCase();

        return normalizado;
    }
}

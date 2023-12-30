import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Frota implements Normalizador {
    // #region Atributos
    private List<DiarioDeBordo> diariosDeBordo;
    private LocalDate dataInicial;
    private LocalDate dataAtual;
    private double almas;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Frota.
     *
     * @param tamanhoFrota O tamanho da frota.
     */
    public Frota() {
        this.diariosDeBordo = new ArrayList<>();
        this.dataInicial = LocalDate.now();
        this.dataAtual = LocalDate.now();
    }

    // #endregion

    // #region Métodos
    /**
     * Adiciona um veículo à frota.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public void adicionarBarco(Barco barco) {
        if (diariosDeBordo.stream().anyMatch(diario -> normalizar(diario.getBarcoDoDiario().getNOME()).equals(normalizar(barco.getNOME())))) {
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
    

    public void relatorioBarco(String nomeBarco) {
        DiarioDeBordo diario = this.localizarDiarioPorNome(nomeBarco);
    
        if (diario != null) {
            System.out.println(diario.relatorio());
        } else {
            System.out.println("Barco não encontrado.");
        }
    }

    public List<String> listarBarcosParaRotas() {
    List<String> listaBarcosParaRotas = new ArrayList<>();
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            
            if (diario != null) {
                Barco barco = diario.getBarcoDoDiario();
                StringBuilder textoBarco = new StringBuilder();
                textoBarco.append("Barco #" + (i + 1));
                textoBarco.append("\n");
                textoBarco.append("Nome: " + barco.getNOME());
                textoBarco.append("\n");
                textoBarco.append("Motorista: " + barco.getMotorista().getNome());
                textoBarco.append("\n");
                textoBarco.append("Tipo de barco: " + barco.getTipoDeBarco());
                textoBarco.append("\n");
                textoBarco.append("Capacidade de passageiros: " + barco.getTipoDeBarco());
                textoBarco.append("\n");
                    if(!barco.getRotas().isEmpty()){
                        textoBarco.append("Rotas associadas: " + barco.getRotas().size());
                        textoBarco.append("\n");
                        textoBarco.append("Rotas já percorridas: " + barco.getQuantRotasPercorridasHj());
                        textoBarco.append("\n");
                        textoBarco.append("Rotas ainda não percorridas: " + (4 - barco.getQuantRotasPercorridasHj()));
                    listaBarcosParaRotas.add(textoBarco.toString());
                    }else {
                        textoBarco.append("Nenhuma rota associada barco ainda.");
                        listaBarcosParaRotas.add(textoBarco.toString());
                    }                    
                        } else {
                            throw new IllegalArgumentException("Não há barcos na sua frota de Carontes.");
                        }
                         
                }
                return listaBarcosParaRotas;
            }
    
       public void listarRotasPorBarco() {
        System.out.println("Lista de Barcos na Frota:");
        
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            
            if (diario != null) {
                Barco barco = diario.getBarcoDoDiario();
                    System.out.println("Barco-" + (i + 1));
                    System.out.println("Nome: " + barco.getNOME());
                    System.out.println("Motorista: " + barco.getMotorista().getNome());
                    System.out.println("Tipo de barco: " + barco.getTipoDeBarco());
                    System.out.println("Capacidade de passageiros: " + barco.getTipoDeBarco());
                    if(!barco.getRotas().isEmpty()){
                        System.out.println("Rotas associadas: " + barco.getRotas().size());
                        System.out.println("Rotas já percorridas: " + barco.getQuantRotasPercorridasHj());
                        System.out.println("Rotas ainda não percorridas: " + (4 - barco.getQuantRotasPercorridasHj()));
                    }else {
                        System.out.println("Nenhuma rota associada barco.");
                    }
                    
                        } else {
                            System.out.println("Não há entradas no diário de bordo.");
                        }
                        System.out.println();
                }
            }

    /**
     * Localiza um veículo na frota pela placa.
     * 
     * @param placa A placa do veículo a ser localizado.
     * @return O veículo localizado, ou null se não for encontrado.
     */
        private DiarioDeBordo localizarDiarioPorNome(String nome) {
        for (DiarioDeBordo diario : diariosDeBordo) {
            if (diario != null && normalizar(nome).equals(normalizar(diario.getBarcoDoDiario().getNOME()))) {
            return diario;
            }
        }
        throw new IllegalArgumentException("Não há um barco com este nome na frota de Carontes.");
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
    public void encerrarDia() {
        dataAtual = dataAtual.plusDays(1);
    
        // Percorre a lista de diários e chama o método fecharDiario para cada um
        for (DiarioDeBordo diario : diariosDeBordo) {
            diario.encerrarDia(dataAtual);
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

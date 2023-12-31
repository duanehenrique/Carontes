import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Frota implements Normalizador {
    // #region Atributos
    private List<DiarioDeBordo> diariosDeBordo;
    private LocalDate dataInicial;
    private LocalDate dataAtual;
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
        this.dataAtual = dataInicial;
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
    
        for (DiarioDeBordo diario : diariosDeBordo) {
            relatorio.append(diario.relatorio()).append("\n");
        }
    
        return relatorio.toString();
    }

    public String relatorioFrotaDeOntem() {
        StringBuilder relatorio = new StringBuilder();
        for (DiarioDeBordo diario : diariosDeBordo) {
            Barco barco = diario.getUltimoBarcoInserido();
            relatorio.append(barco.relatorio()).append("\n");
            List <Rota> rotasBarco = barco.getRotas();
            for (Rota rota : rotasBarco) {
                relatorio.append(rota.relatorio()).append("\n");
            }
        }
        return relatorio.toString();
    }
    

    public String relatorioBarcoPorNome(String nomeBarco) {
        DiarioDeBordo diario = this.localizarDiarioPorNome(nomeBarco);
        return diario.getBarcoDoDiario().relatorio();
    }

    public String relatorioBarcoPorIndex(int posicao) {
        DiarioDeBordo diario = this.localizarDiarioPorIndex(posicao);
        return diario.relatorio();
    }

    public String relatorioCompletoBarcoPorIndex(int posicao) {
        DiarioDeBordo diario = this.localizarDiarioPorIndex(posicao);
        return diario.getBarcoDoDiario().relatorio();

    }

    public String relatorioCompletoBarcoPorNome(String nomeBarco) {
        DiarioDeBordo diario = this.localizarDiarioPorNome(nomeBarco);
        return diario.relatorio();
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
                            throw new IllegalArgumentException("Diário de bordo #" + i +" não pertence a um barco.");
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
        public DiarioDeBordo localizarDiarioPorNome(String nome) {
        for (DiarioDeBordo diario : diariosDeBordo) {
            if (diario != null && normalizar(nome).equals(normalizar(diario.getBarcoDoDiario().getNOME()))) {
            return diario;
            }
        }
        throw new IllegalArgumentException("Não há um barco com este nome na frota de Carontes.");
        }
    
        public DiarioDeBordo localizarDiarioPorIndex(int posicao) {
            DiarioDeBordo diarioPorIndex;
            if(posicao < diariosDeBordo.size()){
                if(posicao > 1){
                    diarioPorIndex = diariosDeBordo.get(posicao-1);
                }else{
                    throw new IllegalArgumentException("Posição informada menor que 1.");
                }
            }else{
                throw new IllegalArgumentException("Posição informada excede a quantidade atual de diários de bordo. Quantidade atual: " + diariosDeBordo.size());
            }
           
        if (diarioPorIndex != null) {
            return diarioPorIndex;
        }else{
            throw new IllegalArgumentException("Diário de bordo informado não pertence a um barco.");
        }
        }

        public Caronte localizarMotoristaPorNome(String nome) {
        DiarioDeBordo diario = localizarDiarioPorNome(nome);
            if (diario != null && normalizar(nome).equals(normalizar(diario.getBarcoDoDiario().getMotorista().getNome()))) {
            return diario.getBarcoDoDiario().getMotorista();
            }else{
            throw new IllegalArgumentException("Não há um Caronte com este nome na frota.");
            }
        }
    
        public Caronte localizarMotoristaPorIndex(int posicao) {
            DiarioDeBordo diarioPorIndex = localizarDiarioPorIndex(posicao);
            if(posicao < diariosDeBordo.size()){
                if(posicao > 1){
                    diarioPorIndex = diariosDeBordo.get(posicao-1);
                }else{
                    throw new IllegalArgumentException("Posição informada menor que 1.");
                }
            }else{
                throw new IllegalArgumentException("Posição informada excede a quantidade atual de Carontes. Quantidade atual: " + diariosDeBordo.size());
            }
           
        if (diarioPorIndex != null) {
            return diarioPorIndex.getBarcoDoDiario().getMotorista();
        }else{
            throw new IllegalArgumentException("Caronte informado não está empregado na frota.");
        }
        }

        public List<Rota> localizarRotasPorIndex(int posicao) {
            DiarioDeBordo diarioPorIndex;
            if(posicao < diariosDeBordo.size()){
                if(posicao > 1){
                    diarioPorIndex = diariosDeBordo.get(posicao-1);
                }else{
                    throw new IllegalArgumentException("Posição informada menor que 1.");
                }
            }else{
                throw new IllegalArgumentException("Posição informada excede a quantidade atual de Carontes. Quantidade atual: " + diariosDeBordo.size());
            }
           
        if (diarioPorIndex != null) {
            return diarioPorIndex.getBarcoDoDiario().getRotas();
        }else{
            throw new IllegalArgumentException("Caronte informado não está empregado na frota.");
        }
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
    public double encerrarDia() {
        dataAtual = dataAtual.plusDays(1);
        double totalAlmasColetadasDia = 0;
    
        // Percorre a lista de diários e chama o método fecharDiario para cada um
        for (DiarioDeBordo diario : diariosDeBordo) {
            diario.encerrarDia(dataAtual);
            totalAlmasColetadasDia += diario.balancoTotalDeOntem();
        }
        return totalAlmasColetadasDia;
    }

    public void iniciarDia() {
        for (DiarioDeBordo diario : diariosDeBordo) {
            diario.iniciarDiario();
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

    public LocalDate getDataAtual(){
        return dataAtual;
    }

    public int getDiaDoDesafio(){
        long diferenca = (dataInicial.until(dataAtual).getDays() + 1);
        return (int) diferenca;
        }
    // #endregion

    private static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.toUpperCase();

        return normalizado;
    }
}

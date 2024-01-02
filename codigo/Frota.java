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
    public void addBarco(Barco barco) {
        if (diariosDeBordo.stream().anyMatch(diario -> normalizar(diario.getBarcoDoDiario().getNOME()).equals(normalizar(barco.getNOME())))) {
            throw new IllegalArgumentException("O barco já faz parte da frota de Carontes");
        }
        DiarioDeBordo diario = new DiarioDeBordo(barco);
        diariosDeBordo.add(diario);
    }

    public void addRotaPorNome(String nome, Rota rotaNova) {
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nome);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
        } else {
           throw new IllegalArgumentException("Diário de bordo do barco " + nome +" não pertence a um barco.");
        }
    }

        public void addRotaPorIndex(int posicao, Rota rotaNova) {
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicao);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            diariosDeBordo.set(posicao, diarioRota);
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + posicao +" não pertence a um barco.");
        }
    }
    
    
    public void addExcluirPorIndex(int posicao, Rota rotaNova) {
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicao);
    
        if (diarioRota != null) {
            diarioRota.excluirRota(rotaNova);
            diariosDeBordo.set(posicao, diarioRota);
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + posicao +" não pertence a um barco.");
        }
    }

    public void addExcluirPorNome(String nome, Rota rotaExcluida) {
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nome);
    
        if (diarioRota != null) {
            diarioRota.excluirRota(rotaExcluida);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + nome +" não pertence a um barco.");
        }
    }

    public void trocarCarontes(Barco barco1, Barco barco2) {
        if (barco1 != null && barco2 != null) {
            Caronte caronteBarco1 = barco1.getMotorista();
            Caronte caronteBarco2 = barco2.getMotorista();
    
            if (caronteBarco1 != null && caronteBarco2 != null) {
                // Troca os Carontes entre os barcos
                barco1.atribuirMotorista(caronteBarco2);
                barco2.atribuirMotorista(caronteBarco1);
                relatorioBarcoPorNome(barco1.getNOME());
                relatorioBarcoPorNome(barco2.getNOME());
                } 
        } else {
            System.out.println("Os barcos selecionados não fazem parte da sua frota.");
        }
    }

    public int pagarCursoDeEspecializacaoPorNome(String nome){
        Caronte motorista= localizarMotoristaPorNome(nome);
        int custoCurso = motorista.fazerCursoDeEspecializacao();
        return custoCurso;
    }
    
    public int pagarCursoDeEspecializacaoPorIndex(int posicao){
        Caronte motorista= localizarMotoristaPorIndex(posicao);
        int custoCurso = motorista.fazerCursoDeEspecializacao();
        return custoCurso;
    }
    /**
     * Gera um relatório da frota.
     * 
     * @return Uma string contendo o relatório da frota.
     */
    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Dia " + getDiaDoDesafio()).append("\n");
        for (DiarioDeBordo diario : diariosDeBordo) {
            relatorio.append(diario.relatorio()).append("\n");
        }
    
        return relatorio.toString();
    }

    public String relatorioFrotaDeOntem() {
        StringBuilder relatorio = new StringBuilder();
        int i = 0, j = 0;
        for (DiarioDeBordo diario : diariosDeBordo) {
            Barco barco = diario.getUltimoBarcoInserido();
            relatorio.append("Barco #").append(i + 1).append("\n");
            relatorio.append(barco.relatorio()).append("\n");
            List <Rota> rotasBarco = barco.getRotas();
            for (Rota rota : rotasBarco) {
                relatorio.append("Rota #").append(j + 1).append("\n");
                relatorio.append(rota.relatorio()).append("\n");
                j++;
            }
            i++;
            j = 0;
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

        public String relatorioCarontes() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("---- Carontes na sua frota ----");


        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();

                if (motorista != null) {
                    relatorio.append("Barco #").append(i + 1).append("\n");
                    relatorio.append("Motorista: ").append(motorista.getNome()).append("\n");
                    relatorio.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorio.append("Nível: ").append(motorista.getNivel()).append("\n");
                    relatorio.append("Salário: ").append(motorista.getSalario()).append("\n");
                }else{
                relatorio.append("Barco sem motorista\n");
                }
            }
        }
        return relatorio.toString();
    }

    public String relatorioTodasMultas(){
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("---- Multas na sua frota ----");


        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();

                if (motorista != null) {
                    relatorio.append("Barco #").append(i + 1).append("\n");
                    relatorio.append("Motorista: ").append(motorista.getNome()).append("\n");
                    relatorio.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorio.append("Nível: ").append(motorista.getNivel()).append("\n");

                    List<Multa> multas = motorista.getCarteira().listarMultas();
                    if(!multas.isEmpty()){
                    relatorio.append("Multas: ").append("\n");
                    for (int j = 0; j < diariosDeBordo.size(); j++) {
                        Multa multa = multas.get(j);
                        relatorio.append(multa.relatorio()).append("\n");
                    }
                    }else{
                        relatorio.append("Caronte não tem multas ativas");
                    }
                    
                }else{
                relatorio.append("Barco sem motorista\n");
                }
            }
        }
        return relatorio.toString();
    }

    public String listarTodasMultas(){
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("---- Multas na sua frota ----");


        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();
                List<Multa> multas = motorista.getCarteira().listarMultas();
                if(!multas.isEmpty()){
                if (motorista != null) {
                    relatorio.append("Barco #").append(i + 1).append("\n");
                    relatorio.append("Motorista: ").append(motorista.getNome()).append("\n");
                    relatorio.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorio.append("Nível: ").append(motorista.getNivel()).append("\n");
                    relatorio.append("Multas: ").append("\n");
                    for (int j = 0; j < diariosDeBordo.size(); j++) {
                        Multa multa = multas.get(j);
                        relatorio.append(multa.relatorio()).append("\n");
                    }
                }
            }
        }
    }
        return relatorio.toString();
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
    public List<DiarioDeBordo> getDiariosdeBordo() {
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

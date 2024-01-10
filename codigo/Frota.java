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

    public Frota(Frota outraFrota) {
        this.dataInicial = outraFrota.dataInicial;
        this.dataAtual = outraFrota.dataAtual;

        // Copiando os diários de bordo
        this.diariosDeBordo = new ArrayList<>();
        for (DiarioDeBordo diario : outraFrota.diariosDeBordo) {
            this.diariosDeBordo.add(new DiarioDeBordo(diario));
        }
    }

    // #endregion

    // #region Métodos
    /**
     * Adiciona um veículo à frota.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public List<String> addBarco(Barco barco) {
        List<String> mensagens = new ArrayList<>();
        if (diariosDeBordo.stream().anyMatch(diario -> normalizar(diario.getBarcoDoDiario().getNOME()).equals(normalizar(barco.getNOME())))) {
            throw new IllegalArgumentException("O barco já faz parte da frota de Carontes");
        } else {
            DiarioDeBordo diario = new DiarioDeBordo(barco);
            diariosDeBordo.add(diario);
            mensagens.add("Barco " + barco.getNOME() + " agora faz parte da sua frota.");
        }
        return mensagens;
    }
    
    public List<String> addRotaPorNome(String nomeBarco, Rota rotaNova) {
        List<String> mensagens = new ArrayList<>();
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nomeBarco);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            mensagens.add("Rota associada ao barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
        } else {
            throw new IllegalArgumentException("Diário de bordo do barco " + nomeBarco + " não pertence a um barco.");
        }
        return mensagens;
    }
    
    public List<String> addRotaPorIndex(int posicaoBarco, Rota rotaNova) {
        List<String> mensagens = new ArrayList<>();
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicaoBarco);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            diariosDeBordo.set(posicaoBarco, diarioRota);
            mensagens.add("Rota associada ao barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
        } else {
            throw new IllegalArgumentException("Diário de bordo #" + posicaoBarco + " não pertence a um barco.");
        }
        return mensagens;
    }
    
    public List<String> excluirRotaPorIndex(int posicaoBarco, int posicaoRotaExcluida) {
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicaoBarco);
        
        if (diarioRota != null) {
            Rota rotaExcluida = localizarRotasPorIndex(diarioRota, posicaoRotaExcluida);
            diarioRota.excluirRota(rotaExcluida);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Rota excluída do barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Diário de bordo #" + posicaoBarco + " não pertence a um barco.");
        }
    }
    
    public List<String> excluirRotaPorNome(String nomeBarco, int posicaoRotaExcluida) {
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nomeBarco);
        
        if (diarioRota != null) {
            Rota rotaExcluida = localizarRotasPorIndex(diarioRota, posicaoRotaExcluida);
            diarioRota.excluirRota(rotaExcluida);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Rota excluída do barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Diário de bordo #" + nomeBarco + " não pertence a um barco.");
        }
    }
    
    public List<String> trocarRotasPorNome(String nomeBarco1, int posicaoRota1, String nomeBarco2, int posicaoRota2) {
        DiarioDeBordo diarioRota1 = localizarDiarioPorNome(nomeBarco1);
        DiarioDeBordo diarioRota2 = localizarDiarioPorNome(nomeBarco2);
    
        if (diarioRota1 != null || diarioRota2 != null) {
            Rota rota1 = diarioRota1.localizarRota(posicaoRota1);
            Rota rota2 = diarioRota2.localizarRota(posicaoRota1);
            diarioRota1.excluirRota(rota1);
            diarioRota2.excluirRota(rota2);
            diarioRota1.addRota(rota2);
            diarioRota1.addRota(rota1);
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Barco " + diarioRota1.getBarcoDoDiario().getNOME() + "e barco " + diarioRota2.getBarcoDoDiario().getNOME() + " tiveram suas rotas trocadas.");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }
    
    public List<String> trocarRotasPorIndex(int posicaoBarco1, int posicaoRota1, int posicaoBarco2, int posicaoRota2) {
        DiarioDeBordo diarioRota1 = localizarDiarioPorIndex(posicaoBarco1);
        DiarioDeBordo diarioRota2 = localizarDiarioPorIndex(posicaoBarco2);
    
        if (diarioRota1 != null || diarioRota2 != null) {
            Rota rota1 = diarioRota1.localizarRota(posicaoRota1);
            Rota rota2 = diarioRota2.localizarRota(posicaoRota1);
            diarioRota1.excluirRota(rota1);
            diarioRota2.excluirRota(rota2);
            diarioRota1.addRota(rota2);
            diarioRota1.addRota(rota1);
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Barco " + diarioRota1.getBarcoDoDiario().getNOME() + "e barco " + diarioRota2.getBarcoDoDiario().getNOME() + " tiveram suas rotas trocadas.");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }
    
    public List<String> trocarCarontesPorNome(String nomeCaronte1, String nomeCaronte2) {
        Caronte caronte1 = localizarMotoristaPorNome(nomeCaronte1);
        Caronte caronte2 = localizarMotoristaPorNome(nomeCaronte2);
        Barco barco1 = localizarBarcoPorMotorista(caronte1);
        Barco barco2 = localizarBarcoPorMotorista(caronte2);
    
        if (barco1 != null && barco2 != null) {
            barco1.atribuirMotorista(caronte2);
            barco2.atribuirMotorista(caronte1);
            relatorioBarcoPorNome(barco1.getNOME());
            relatorioBarcoPorNome(barco2.getNOME());
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Caronte " + caronte1.getNome() + " agora conduz o barco " + barco2.getNOME() + ".");
            mensagens.add("Caronte " + caronte2.getNome() + " agora conduz o barco " + barco1.getNOME() + ".");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }
    
    public List<String> trocarCarontesPorIndex(int posicaoCaronte1, int posicaoCaronte2) {
        Caronte caronte1 = localizarMotoristaPorIndex(posicaoCaronte1);
        Caronte caronte2 = localizarMotoristaPorIndex(posicaoCaronte2);
        Barco barco1 = localizarBarcoPorMotorista(caronte1);
        Barco barco2 = localizarBarcoPorMotorista(caronte2);
    
        if (barco1 != null && barco2 != null) {
            // Troca os Carontes entre os barcos
            barco1.atribuirMotorista(caronte2);
            barco2.atribuirMotorista(caronte1);
            relatorioBarcoPorNome(barco1.getNOME());
            relatorioBarcoPorNome(barco2.getNOME());
            List<String> mensagens = new ArrayList<>();
            mensagens.add("Caronte " + caronte1.getNome() + " agora conduz o barco " + barco2.getNOME() + ".");
            mensagens.add("Caronte " + caronte2.getNome() + " agora conduz o barco " + barco1.getNOME() + ".");
            return mensagens;
        } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }
        

    private double abastecerBarcoDoDiario(DiarioDeBordo diario, double qtdCombustivel){
        
        if (diario != null) {
            Barco barco = diario.getBarcoDoDiario();
                if (barco instanceof BarcoComTanque) {
                 return ((BarcoComTanque) barco).abastecer(qtdCombustivel);
            } else {
                throw new IllegalArgumentException("Você tentou abastecer uma gôndola com combstível, mas elas são movidas pelaa força dos Carontes.\n As leis trabalhistas daqui são estranhas.");
            }

            } else {
            throw new IllegalArgumentException("O barco selecionado não faz parte da sua frota.");
        } 
    } 

    public double abastecerBarcoPorIndex(int posicao, double qtdCombustivel) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return abastecerBarcoDoDiario(diario, qtdCombustivel);
    }
    
    public double abastecerBarcoPorNome(String nome, double qtdCombustivel) {
                DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return abastecerBarcoDoDiario(diario, qtdCombustivel);
    }

    public double abastecerBarcoCompletoPorIndex(int posicao) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return abastecerBarcoDoDiario(diario, Double.MAX_VALUE);
    }
    
    public double abastecerBarcoCompletoPorNome(String nome) {
                DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return abastecerBarcoDoDiario(diario, Double.MAX_VALUE);
    }

    public double abastecerTodosBarcos(){
        double totalGasto = 0;
        for (DiarioDeBordo diario : diariosDeBordo) {
        BarcoComTanque barco = ((BarcoComTanque) diario.getBarcoDoDiario());
            if(barco instanceof BarcoComTanque){
        if(barco.getTanque().getCapacidadeAtual() != barco.getTanque().getCapacidadeMaxima()){
          totalGasto = abastecerBarcoDoDiario(diario, Double.MAX_VALUE);
        }
       }
        }
    return totalGasto;
    }


    private double fazerManutencaoBarcoDoDiario(DiarioDeBordo diario){
        
        if (diario != null) {
            Barco barco = diario.getBarcoDoDiario();
                if (barco != null) {
                 return barco.fazerManutencao();
                } else {
                throw new IllegalArgumentException("O barco selecionado não faz parte da sua frota.");
            }
        }else {
            throw new IllegalArgumentException("O barco selecionado não faz parte da sua frota.");
    } 
}

    public double fazerManutencaoBarcoPorIndex(int posicao) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return fazerManutencaoBarcoDoDiario(diario);
    }
    
    public double fazerManutencaoPorNome(String nome) {
        DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return fazerManutencaoBarcoDoDiario(diario);
    }

     public double fazerManutencaoTodosBarcos(){
        double totalGasto = 0;
        for (DiarioDeBordo diario : diariosDeBordo) {
            if(!diario.getBarcoDoDiario().getManutencao().getManutencaoEmDia()){
          totalGasto = fazerManutencaoBarcoDoDiario(diario);
        }
    }  
    return totalGasto;
}

    public double pagarMultaDoCarontePorIndex(int posicao, int posicaoMulta) {
        Caronte caronte = localizarMotoristaPorIndex(posicao);
        return caronte.pagarMulta(posicaoMulta);
    }
    
    public double pagarMultaDoCarontePorNome(String nome, int posicaoMulta) {
        Caronte caronte = localizarMotoristaPorNome(nome);
        return caronte.pagarMulta(posicaoMulta);
    }

        public double pagarTodasMultasDoCarontePorIndex(int posicao, int posicaoMulta) {
        Caronte caronte = localizarMotoristaPorIndex(posicao);
        return caronte.pagarTodasMultas();
    }
    
    public double pagarTodasMultasDoCarontePorNome(String nome, int posicaoMulta) {
        Caronte caronte = localizarMotoristaPorNome(nome);
        return caronte.pagarTodasMultas();
    }
    
    public double pagarTodasMultasDeTodosCarontes() {
        double totalPago = 0;
         for (int i = 0; i < getDiariosdeBordo().size(); i++) {
            Caronte motorista = localizarMotoristaPorIndex(i);
            totalPago += motorista.pagarTodasMultas();
            }
        return totalPago;
    }
    

    public double pagarCursoDeEspecializacaoPorNome(String nome){
        Caronte motorista= localizarMotoristaPorNome(nome);
        int custoCurso = motorista.fazerCursoDeEspecializacao();
        return custoCurso;
    }
    
    public double pagarCursoDeEspecializacaoPorIndex(int posicao){
        Caronte motorista= localizarMotoristaPorIndex(posicao);
        int custoCurso = motorista.fazerCursoDeEspecializacao();
        return custoCurso;
    }

    public double pagarSalarioMotoristaPorIndex(int posicao) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return pagarSalarioMotoristaDoBarco(diario);
    }

    public double pagarSalarioMotoristaPorNome(String nome) {
        DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return pagarSalarioMotoristaDoBarco(diario);
    }

    public double pagarSalarioTodosMotoristas() {
        int totalDespesaSalarios = 0;

        for (DiarioDeBordo diario : diariosDeBordo) {
            totalDespesaSalarios += pagarSalarioMotoristaDoBarco(diario);
        }

        return totalDespesaSalarios;
    }

    private double pagarSalarioMotoristaDoBarco(DiarioDeBordo diario) {
        Barco barco = diario.getBarcoDoDiario();
            if (barco.getMotorista() != null) {
                double despesaSalario = barco.pagarSalarioMotorista();
                return despesaSalario;
            } else{
            throw new IllegalArgumentException("Caronte informado não está empregado na frota.");
            }
    }
    

    /**
     * Gera um relatório da frota.
     * 
     * @return Uma string contendo o relatório da frota.
     */
    public List<String> relatorioFrota() {
        List<String> relatorio = new ArrayList<>();
        relatorio.add("Dia " + getDiaDoDesafio());
        for (DiarioDeBordo diario : diariosDeBordo) {
            relatorio.add(diario.relatorio());
        }
        return relatorio;
    }
    
    public List<String> relatorioFrotaDeOntem() {
        List<String> relatorio = new ArrayList<>();
        int i = 0, j = 0;
        for (DiarioDeBordo diario : diariosDeBordo) {
            Barco barco = diario.getUltimoBarcoInserido();
            relatorio.add("Barco #" + (i + 1));
            relatorio.add(barco.relatorio());
            List<Rota> rotasBarco = barco.getRotas();
            for (Rota rota : rotasBarco) {
                relatorio.add("Rota #" + (j + 1));
                relatorio.add(rota.relatorio());
                j++;
            }
            i++;
            j = 0;
        }
        return relatorio;
    }
    
    public List<String> relatorioBarcoPorNome(String nomeBarco) {
        List<String> relatorio = new ArrayList<>();
        DiarioDeBordo diario = this.localizarDiarioPorNome(nomeBarco);
        relatorio.add(diario.getBarcoDoDiario().relatorio());
        return relatorio;
    }
    
    public List<String> relatorioBarcoPorIndex(int posicao) {
        List<String> relatorio = new ArrayList<>();
        DiarioDeBordo diario = this.localizarDiarioPorIndex(posicao);
        relatorio.add(diario.relatorio());
        return relatorio;
    }
    
    public List<String> relatorioCompletoBarcoPorIndex(int posicao) {
        List<String> relatorio = new ArrayList<>();
        DiarioDeBordo diario = this.localizarDiarioPorIndex(posicao);
        relatorio.add(diario.getBarcoDoDiario().relatorio());
        return relatorio;
    }
    
    public List<String> relatorioCompletoBarcoPorNome(String nomeBarco) {
        List<String> relatorio = new ArrayList<>();
        DiarioDeBordo diario = this.localizarDiarioPorNome(nomeBarco);
        relatorio.add(diario.relatorio());
        return relatorio;
    }
    

    public List<String> listarCarontes() {
        List<String> relatorio = new ArrayList<>();
        relatorio.add("---- Carontes na sua frota ----");
    
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();
    
                if (motorista != null) {
                    StringBuilder relatorioCaronte = new StringBuilder();
                    relatorioCaronte.append("Barco #").append(i + 1).append("\n");
                    relatorioCaronte.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorioCaronte.append("Tipo de Barco: ").append(barco.getTipoDeBarco()).append("\n");
                    relatorioCaronte.append(motorista.relatorio()).append("\n");
    
                    relatorio.add(relatorioCaronte.toString());
                } else {
                    relatorio.add("Barco sem motorista. Isso não parece certo.\n");
                }
            }
        }
        return relatorio;
    }
    
    public List<String> listarBarcosPorTipo(String tipo) {
        List<String> resultado = new ArrayList<>();
        boolean existeBarco = false;
        String tipoNormalizado = normalizar(tipo);
    
        if (tipoNormalizado.equals(normalizar("Gôndola")) || tipoNormalizado.equals(normalizar("Balsa"))
                || tipoNormalizado.equals(normalizar("Navio")) || tipoNormalizado.equals(normalizar("Cruzeiro"))) {    
            for (DiarioDeBordo diario : diariosDeBordo) {
                if (diario != null) {
                    Barco barco = diario.getBarcoDoDiario();
    
                    if (barco != null && normalizar(barco.getTipoDeBarco()).equals(tipoNormalizado)) {
                        existeBarco = true;
                        resultado.add(barco.relatorio());
    
                        if (!barco.getRotas().isEmpty()) {
                            resultado.add(barco.relatorioRotas());
                        } else {
                            resultado.add("Nenhuma rota associada ao barco hoje.");
                        }
    
                        resultado.add("");
                    }
                }
            }
            if(!existeBarco){
               resultado.add("Não há barcos do tipo " + tipo + " selecionado na frota."); 
            }
        } else {
            throw new IllegalArgumentException(tipoNormalizado + " não é um tipo de barco. Você está com criatividade demais.");
        }
    
        return resultado;
    }
    

    public List<String> listarTodosParaAbastecer() {
        List<String> relatorio = new ArrayList<>();
        relatorio.add("---- Barcos na sua frota com tanque para abastecer ----");
    
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null && barco instanceof BarcoComTanque) {
                relatorio.add("Barco #" + (i + 1));
                relatorio.add(barco.relatorio());
            }
        }
        return relatorio;
    }
    
    public List<String> listarTodosSalariosParaPagar() {
        List<String> relatorio = new ArrayList<>();
        relatorio.add("---- Barcos na sua frota com Carontes com salários pendentes ----");
        boolean temLista = false;
    
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();
                if (motorista != null && !motorista.getSalarioEmDia()) {
                    temLista = true;
                    relatorio.add("Barco #" + (i + 1));
                    relatorio.add(barco.relatorio());
                }
            }
        }
    
        if (!temLista) {
            relatorio.add("Não há barcos que atendem a esse critério na sua Frota");
        }
        return relatorio;
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

        public Barco localizarBarcoPorMotorista(Caronte motorista) {
            if ((motorista != null)) {
            String nomeMotoristaSelecionado = motorista.getNome();
            for (DiarioDeBordo diarioDeBordo : diariosDeBordo) {
                String nomeMotoristaDiario = diarioDeBordo.getBarcoDoDiario().getMotorista().getNome();
                if(nomeMotoristaSelecionado.equals(nomeMotoristaDiario)){
                    return diarioDeBordo.getBarcoDoDiario();
                }
            }
            }else{
            throw new IllegalArgumentException("O Caronte selecionado não existe.");
            }
            throw new IllegalArgumentException("O Caronte " + motorista.getNome() + "não está empregado na sua frota");
        }

        public Rota localizarRotasPorIndex(DiarioDeBordo diario, int posicao) {
            if(posicao < diariosDeBordo.size()){
                if(posicao > 1){
                if (diario != null) {
                return diario.localizarRota(posicao);
            }else{
                throw new IllegalArgumentException("Barco não faz parte da frota.");
            }                }else{
                    throw new IllegalArgumentException("Posição informada menor que 1.");
                }
            }else{
                throw new IllegalArgumentException("Posição informada excede a quantidade atual de Carontes. Quantidade atual: " + diariosDeBordo.size());
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
    public void encerrarDia() {
        dataAtual = dataAtual.plusDays(1);
    
        // Percorre a lista de diários e chama o método fecharDiario para cada um
        for (DiarioDeBordo diario : diariosDeBordo) {
            diario.encerrarDia(dataAtual);
        }
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

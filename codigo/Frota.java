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
    public String addBarco(Barco barco) {
        if (diariosDeBordo.stream().anyMatch(diario -> normalizar(diario.getBarcoDoDiario().getNOME()).equals(normalizar(barco.getNOME())))) {
            throw new IllegalArgumentException("O barco já faz parte da frota de Carontes");
        }else{
        DiarioDeBordo diario = new DiarioDeBordo(barco);
        diariosDeBordo.add(diario);
        StringBuilder mensagem = new StringBuilder();           
        mensagem.append("Barco  " + barco.getNOME() + " agora faz parte da sua frota.");
        return mensagem.toString();
        }
    }

    public String addRotaPorNome(String nomeBarco, Rota rotaNova) {
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nomeBarco);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            StringBuilder mensagem = new StringBuilder();           
            mensagem.append("Rota associada ao barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
            return mensagem.toString();
        } else {
           throw new IllegalArgumentException("Diário de bordo do barco " + nomeBarco +" não pertence a um barco.");
        }
    }

        public String addRotaPorIndex(int posicaoBarco, Rota rotaNova) {
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicaoBarco);
    
        if (diarioRota != null) {
            diarioRota.addRota(rotaNova);
            diariosDeBordo.set(posicaoBarco, diarioRota);
            StringBuilder mensagem = new StringBuilder();           
            mensagem.append("Rota associada ao barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
            return mensagem.toString();
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + posicaoBarco +" não pertence a um barco.");
        }
    }
    
    
    public String excluirRotaPorIndex(int posicaoBarco, int posicaoRotaExcluida) {
        DiarioDeBordo diarioRota = localizarDiarioPorIndex(posicaoBarco);
    
        if (diarioRota != null) {
            Rota rotaExcluida = localizarRotasPorIndex(diarioRota, posicaoRotaExcluida);
            diarioRota.excluirRota(rotaExcluida);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            StringBuilder mensagem = new StringBuilder();           
            mensagem.append("Rota excluída do barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
                return mensagem.toString();
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + posicaoBarco +" não pertence a um barco.");
        }
    }

    public String excluirRotaPorNome(String nomeBarco, int posicaoRotaExcluida) {
        DiarioDeBordo diarioRota = localizarDiarioPorNome(nomeBarco);
    
        if (diarioRota != null) {
            Rota rotaExcluida = localizarRotasPorIndex(diarioRota, posicaoRotaExcluida);
            diarioRota.excluirRota(rotaExcluida);
            int posicao = diariosDeBordo.indexOf(diarioRota);
            diariosDeBordo.set(posicao, diarioRota);
            StringBuilder mensagem = new StringBuilder();           
            mensagem.append("Rota excluída do barco " + diarioRota.getBarcoDoDiario().getNOME() + ".");
                return mensagem.toString();
        } else {
           throw new IllegalArgumentException("Diário de bordo #" + nomeBarco +" não pertence a um barco.");
        }
    }

    public String trocarRotasPorNome(String nomeBarco1, int posicaoRota1, String nomeBarco2, int posicaoRota2) {
            DiarioDeBordo diarioRota1 = localizarDiarioPorNome(nomeBarco1);
            DiarioDeBordo diarioRota2 = localizarDiarioPorNome(nomeBarco2);

            if (diarioRota1 != null || diarioRota2 != null) {
                Rota rota1 = diarioRota1.localizarRota(posicaoRota1);
                Rota rota2 = diarioRota2.localizarRota(posicaoRota1);
                diarioRota1.excluirRota(rota1);
                diarioRota2.excluirRota(rota2);
                diarioRota1.addRota(rota2);
                diarioRota1.addRota(rota1);
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Barco " + diarioRota1.getBarcoDoDiario().getNOME() + "e barco " + diarioRota2.getBarcoDoDiario().getNOME() + " tiveram suas rotas trocadas.");
                return mensagem.toString();
            } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }

        public String trocarRotasPorIndex(int posicaoBarco1, int posicaoRota1, int posicaoBarco2, int posicaoRota2) {
            DiarioDeBordo diarioRota1 = localizarDiarioPorIndex(posicaoBarco1);
            DiarioDeBordo diarioRota2 = localizarDiarioPorIndex(posicaoBarco2);

            if (diarioRota1 != null || diarioRota2 != null) {
                Rota rota1 = diarioRota1.localizarRota(posicaoRota1);
                Rota rota2 = diarioRota2.localizarRota(posicaoRota1);
                diarioRota1.excluirRota(rota1);
                diarioRota2.excluirRota(rota2);
                diarioRota1.addRota(rota2);
                diarioRota1.addRota(rota1);
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Barco " + diarioRota1.getBarcoDoDiario().getNOME() + "e barco " + diarioRota2.getBarcoDoDiario().getNOME() + " tiveram suas rotas trocadas.");
                return mensagem.toString();
            } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }

    public String trocarCarontesPorNome(String nomeCaronte1,String nomeCaronte2) {
            Caronte caronte1 = localizarMotoristaPorNome(nomeCaronte1);
            Caronte caronte2 = localizarMotoristaPorNome(nomeCaronte2);
            Barco barco1 = localizarBarcoPorMotorista(caronte1);
            Barco barco2 = localizarBarcoPorMotorista(caronte2);

            if (barco1 != null && barco2 != null) {
                barco1.atribuirMotorista(caronte2);
                barco2.atribuirMotorista(caronte1);
                relatorioBarcoPorNome(barco1.getNOME());
                relatorioBarcoPorNome(barco2.getNOME());
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Caronte " + caronte1.getNome() + " agora conduz o barco " + barco2.getNOME() + ".");
                mensagem.append("Caronte " + caronte2.getNome() + " agora conduz o barco " + barco1.getNOME() + ".");
                return mensagem.toString();
            } else {
            throw new IllegalArgumentException("Ao menos um dos barcos selecionados não fazem parte da sua frota. Não foi possível trocar os Carontes de barco.");
        }
    }

    public String trocarCarontesPorIndex(int posicaoCaronte1, int posicaoCaronte2) {
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
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Caronte " + caronte1.getNome() + " agora conduz o barco " + barco2.getNOME() + ".");
                mensagem.append("Caronte " + caronte2.getNome() + " agora conduz o barco " + barco1.getNOME() + ".");
                return mensagem.toString();
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


    private int fazerManutencaoBarcoDoDiario(DiarioDeBordo diario){
        
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

    public int fazerManutencaoBarcoPorIndex(int posicao, double qtdCombustivel) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return fazerManutencaoBarcoDoDiario(diario);
    }
    
    public int fazerManutencaoPorNome(String nome, double qtdCombustivel) {
        DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return fazerManutencaoBarcoDoDiario(diario);
    }

     public int fazerManutencaoTodosBarcos(){
        int totalGasto = 0;
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
    
    public double pagarTodasMultasDeTodosCarontes(int posicaoMulta) {
        double totalPago = 0;
        int i = 0;
         for (DiarioDeBordo diario : diariosDeBordo) {
            Caronte motorista = localizarMotoristaPorIndex(i);
            totalPago += motorista.pagarTodasMultas();
            i++;
            }
        return totalPago;
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

    public int pagarSalarioMotoristaPorIndex(int posicao) {
        DiarioDeBordo diario = localizarDiarioPorIndex(posicao);
        return pagarSalarioMotoristaDoBarco(diario);
    }

    public int pagarSalarioMotoristaPorNome(String nome) {
        DiarioDeBordo diario = localizarDiarioPorNome(nome);
        return pagarSalarioMotoristaDoBarco(diario);
    }

    public int pagarSalarioTodosMotoristas() {
        int totalDespesaSalarios = 0;

        for (DiarioDeBordo diario : diariosDeBordo) {
            totalDespesaSalarios += pagarSalarioMotoristaDoBarco(diario);
        }

        return totalDespesaSalarios;
    }

    private int pagarSalarioMotoristaDoBarco(DiarioDeBordo diario) {
        Barco barco = diario.getBarcoDoDiario();
            if (barco.getMotorista() != null) {
                int despesaSalario = barco.pagarSalarioMotorista();
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
                    relatorio.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorio.append("Tipo de Barco: ").append(barco.getTipoDeBarco()).append("\n");
                    relatorio.append(motorista.relatorio()).append("\n");
                }else{
                relatorio.append("Barco sem motorista. Isso não parece certo.\n");
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
                    relatorio.append(motorista.relatorio()).append("\n");                    
                }else{
                relatorio.append("Barco sem motorista\n");
                }
            }
        }
        return relatorio.toString();
    }

    public List<String> listarTodasMultas() {
        List<String> relatorio = new ArrayList<>();
        boolean temLista = false;
    
        relatorio.add("---- Barcos na sua frota com multas pendentes ----");
    
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
    
            if (barco != null) {
                Caronte motorista = barco.getMotorista();
                List<Multa> multas = motorista.getCarteira().listarMultas();
    
                if (!multas.isEmpty()) {
                    temLista = true;
                    StringBuilder relatorioBarco = new StringBuilder();
                    relatorioBarco.append("Barco #").append(i + 1).append("\n");
                    relatorioBarco.append("Tipo de Barco: ").append(barco.getTipoDeBarco()).append("\n");
                    relatorioBarco.append("Motorista: ").append(motorista.getNome()).append("\n");
                    relatorioBarco.append("Barco: ").append(barco.getNOME()).append("\n");
                    relatorioBarco.append("Nível: ").append(motorista.getNivel()).append("\n");
                    relatorioBarco.append("Multas: ").append("\n");
    
                    for (int j = 0; j < multas.size(); j++) {
                        Multa multa = multas.get(j);
                        relatorioBarco.append(multa.relatorio()).append("\n");
                    }
    
                    relatorio.add(relatorioBarco.toString());
                }
            }
        }
    
        if (!temLista) {
            relatorio.add("Não há barcos que atendem a esse critério na sua Frota");
        }
    
        return relatorio;
    }
    
    public List<String> listarTodasManutencoes() {
        List<String> relatorio = new ArrayList<>();
        relatorio.add("---- Barcos na sua frota com manutenção pendente ----");
        boolean temLista = false;
    
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
    
            if (barco != null && !barco.getManutencao().getManutencaoEmDia()) {
                temLista = true;
                relatorio.add("Barco #" + (i + 1));
                relatorio.add(barco.relatorio());
            }
        }
    
        if (!temLista) {
            relatorio.add("Não há barcos que atendem a esse critério na sua Frota");
        }
    
        return relatorio;
    }
    
    // Os outros métodos seguiriam uma lógica semelhante
    

    public String listarTodosParaAbastecer(){
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("---- Barcos na sua frota com tanque para abastecer ----");


        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                if(barco instanceof BarcoComTanque)
                {
                    relatorio.append("Barco #").append(i + 1).append("\n");
                    relatorio.append(barco.relatorio()).append("\n");
                }
            }
        }
        return relatorio.toString();
    }

    public String listarTodosSalariosParaPagar(){
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("---- Barcos na sua frota com Carontes com salários pendentes ----");
        boolean temLista = false;

        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            Barco barco = diario.getBarcoDoDiario();
            if (barco != null) {
                Caronte motorista = barco.getMotorista();
                if(!motorista.getSalarioEmDia()){
                if (motorista != null) {
                    temLista = true;
                    relatorio.append("Barco #").append(i + 1).append("\n");
                    relatorio.append(barco.relatorio()).append("\n");
                    }

            }
        }
    }
    if(!temLista)
    {
       relatorio.append("Não há barcos que atendem a esse critério na sua Frota");
    }
        return relatorio.toString();
    }


    public List<String> listarBarcosParaRotas() {
    List<String> listaBarcosParaRotas = new ArrayList<>();
    boolean temLista = false;
    boolean temRotaParaPercorrer = false;
        for (int i = 0; i < diariosDeBordo.size(); i++) {
            DiarioDeBordo diario = diariosDeBordo.get(i);
            
            if (diario != null) {
                Barco barco = diario.getBarcoDoDiario();
                StringBuilder textoBarco = new StringBuilder();
                List <Rota> rotasBarco = barco.getRotas();
                if(!barco.getRotas().isEmpty()){
                        for (Rota rota : rotasBarco) {
                    if(!rota.getRotaPercorrida()){
                        temRotaParaPercorrer = true;
                    }
                }
                if(temRotaParaPercorrer){
                    temLista = true;
                textoBarco.append("Barco #" + (i + 1));
                textoBarco.append("\n");
                textoBarco.append(barco.relatorio());
                        textoBarco.append("Rotas associadas: " + barco.getRotas().size());
                        textoBarco.append("\n");
                        textoBarco.append("Rotas já percorridas: " + barco.getQuantRotasPercorridasHj());
                        textoBarco.append("\n");
                        textoBarco.append("Rotas ainda não percorridas: " + (4 - barco.getQuantRotasPercorridasHj()));
                    listaBarcosParaRotas.add(textoBarco.toString());
                    }}                   
                        } else {
                            throw new IllegalArgumentException("Diário de bordo #" + i +" não pertence a um barco.");
                        }
                         
                }
                if(!temLista)
    {
       listaBarcosParaRotas.add("Não há barcos que atendem a esse critério na sua Frota");
    }
                return listaBarcosParaRotas;
            }
    
            public List<String> listarRotasPorBarco() {
                List<String> resultado = new ArrayList<>();
                resultado.add("Lista de Barcos na Frota:");
            
                for (int i = 0; i < diariosDeBordo.size(); i++) {
                    DiarioDeBordo diario = diariosDeBordo.get(i);
            
                    if (diario != null) {
                        Barco barco = diario.getBarcoDoDiario();
                        resultado.add("Barco #" + (i + 1));
                        resultado.add("Tipo de Barco: " + barco.getTipoDeBarco());
                        resultado.add("Nome: " + barco.getNOME());
                        resultado.add("Motorista: " + barco.getMotorista().getNome());
                        resultado.add("Tipo de barco: " + barco.getTipoDeBarco());
                        resultado.add("Capacidade de passageiros: " + barco.getCAPACIDADEPASSAGEIROS());
            
                        if (!barco.getRotas().isEmpty()) {
                            resultado.add("Rotas associadas: " + barco.getRotas().size());
                            resultado.add("Rotas já percorridas: " + barco.getQuantRotasPercorridasHj());
                            resultado.add("Rotas ainda não percorridas: " + (4 - barco.getQuantRotasPercorridasHj()));
                        } else {
                            resultado.add("Nenhuma rota associada ao barco.");
                        }
                    } else {
                        resultado.add("Não há entradas no diário de bordo.");
                    }
                    resultado.add("");
                }
            
                return resultado;
            }
            
            public List<String> listarCarontesPorNivel(int nivelDesejado) {
                List<String> resultado = new ArrayList<>();
                resultado.add("--- Carontes no Nível " + nivelDesejado + "----");
                int contagem = 0;
            
                for (DiarioDeBordo diario : diariosDeBordo) {
                    Barco barco = diario.getBarcoDoDiario();
            
                    if (barco != null) {
                        Caronte motorista = barco.getMotorista();
            
                        if (motorista != null && motorista.getNivel() == nivelDesejado) {
                            resultado.add("---- Barco #" + (contagem + 1) + "----");
                            resultado.add("Nome: " + barco.getNOME());
                            resultado.add("Tipo de Barco: " + barco.getTipoDeBarco());
                            resultado.add(motorista.relatorio());
                        }
                    }
                    contagem++;
                }
            
                return resultado;
            }
            
            public List<String> listarBarcosPorTipo(String tipo) {
                List<String> resultado = new ArrayList<>();
                
                if (normalizar(tipo).equals("GONDOLA") || normalizar(tipo).equals("BALSA")
                        || normalizar(tipo).equals("NAVIO") || normalizar(tipo).equals("CRUZEIRO")) {
                    resultado.add("---- Lista de " + tipo + " ----");
            
                    for (int i = 0; i < diariosDeBordo.size(); i++) {
                        DiarioDeBordo diario = diariosDeBordo.get(i);
            
                        if (diario != null) {
                            Barco barco = diario.getBarcoDoDiario();
            
                            if (barco != null && normalizar(tipo).equals(normalizar(barco.getTipoDeBarco()))) {
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
                } else {
                    throw new IllegalArgumentException("Isso não é um tipo de barco. Você está com criatividade demais.");
                }
            
                return resultado;
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

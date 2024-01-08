import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Classe App que serve como ponto de entrada do sistema de gerenciamento de
 * frotas.
 * Este sistema permite o gerenciamento de uma frota de veículos, oferecendo
 * funcionalidades
 * como adicionar veículos, registrar rotas, abastecer, registrar multas,
 * verificar manutenção
 * e calcular despesas totais dos veículos, dentre outras funcionalidades.
 */
public class Jogo implements Normalizador{
    static Random random;
    static Frota frota;
    static int MAX_ROTAS_DIA = 4;
    static Jogador jogador;
    static Custos custos;
    static GeradorNomesCarontes nomesCarontes;
    static GeradorNomesBarcos nomesBarcos;
    static Scanner teclado = new Scanner(System.in);   
    /**
     * Limpa a tela do console utilizando sequências de escape específicas do
     * terminal.
     */
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa para leitura de mensagens em console
     */
    private static void pausa() {
        System.out.println("Pressione Enter para continuar...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria uma linha separadora no console.
     */
    private static void separador() {
        String linha = new String(new char[75]).replace('\0', '-');
        System.out.println(linha + "\n");
    }

    // Mantém um registro das placas já geradas para evitar duplicação
    private static Set<String> placasGeradas = new HashSet<>();

    /**
     * Gera uma placa de veículo única, utilizando uma combinação de letras e
     * números.
     * Garante que cada placa gerada seja única para evitar duplicação.
     * 
     * @return Uma string representando a placa única gerada.
     */


private static void cranio(){
            System.out.println("      ___________");
            System.out.println("   /_ _     _  _ \\");
            System.out.println(" /  /  \\    / \\ | \\");
            System.out.println(" |  \\_//  \\\\_/ |   \\");
            System.out.println("|__ | (||)  |_____/   ||");
            System.out.println(" \\ __ || __| _  /_||//");
            System.out.println("  |_|_|_|_|/|  _/___/");
            System.out.println("  |_|_|_|_|_  |");
            System.out.println("  |_________/");
        }

            private static void fogo() {
                System.out.println("               ⢀⣠⡄");
                System.out.println("             ⣤⠃    ⣇");
                System.out.println("          ⣤⡟      ⡾⢷ ⠘⣿⡀");
                System.out.println("⠀⠀⠀⠀⠀⠀⣴⡟⠀    ⣴     ⣦ ⠈⣷⡀");
                System.out.println("⠀⠀⠀⠀⠀⢶⣿  ⣼ ⡟⠀⠀   ⠘⠷⢷⠙⣷⡄");
                System.out.println("⠀⠀⢀⣼⠏⢠⠏      ⠀⢸⢳⡀⢀⠀⠀⢹⡄ ⣿⡀");
                System.out.println("⠀⠀⣼⡏⢀⡏⠀⠀⠀⠀⢸⠀⠉⣦⠀⠀⠁⢻⡀ ⠹⣧⠀");
                System.out.println("⠀⣸⡿⠀⡞⠀⠀⠀⣀⡼⠛⠀⠀⠀⠹⣆⠀⠀⠈⡇⠀⣿");
                System.out.println("⠀⣿⠇⢸⠁⠀⠀⣰⠋ ⠀⠀⠀⠀⠀⢹⡀⠀⠀⣿⠀⣿⡇");
                System.out.println("⠈⣿⠀⢿⠀⠀⢸⠀⠀⠀⠀⠀ ⠀⠀⠀⣸⣇⠀⣸⠃⢰⣇");
                System.out.println("⠀⠀⠈⠻⣦⣀⠙⢮⣄    ⠀⢀⣴⡿⠶⠊⣉⣴⡾⠋⠁");
                System.out.println("⠀⠀⠀⠀⠈⠛⢷⣦⣄⣉⠉⢉⣠⣤⣶⡿⠟⠉");
                System.out.println("⠀⠀⠀⠀⠀⠀⠀⠈⠉⠛⠿⠿⠛⠋⠉");
            }

    private static void exibirDia(){
        separador();;
        System.err.println("---- " + frota.getDataAtual() + " ----");
        System.err.println("------ Dia "+ frota.getDiaDoDesafio() + "------");
        separador();
    }

        private static void exibirAlmas(){
        separador();;
        System.err.println("---- Almas da frota do gerente " + jogador.getNomePersonagem() + " ----");
        System.err.println("---- Almas em Estoque:" + jogador.getAlmas() + " ----");
        System.err.println("---- Almas coletadas hoje: "+ jogador.getAlmasDeHoje() + "----");
        separador();
    }

        private static void exibirRotas(){
        separador();
        System.out.println(frota.relatorioCarontes());
        separador();
        }

    /**
     * Exibe um relatório detalhado da frota, incluindo informações de cada veículo
     * cadastrado.
     */
    private static void exibirRelatorioFrota() {
        StringBuilder resultado = new StringBuilder();
        separador();
        resultado.append("---- Barcos da Frota do Gerente ").append(" ----\n");
        separador();
        System.out.println(frota.relatorioFrota());
        separador();
    }

    private static void exibirRelatorioCarontes(){
        separador();
        System.err.println("---- Carontes da Frota do Gerente ");
        separador();
        System.err.println(frota.relatorioCarontes());
    }

    private static void exibirTodasMultas() {
        separador();
        System.out.println(frota.relatorioTodasMultas());
        separador();
    }

    private static void encerrarDia(){
        jogador.encerrarDia();
        frota.encerrarDia();
        custos.encerrarDia();
    }

        private static void iniciarDia(){
        frota.iniciarDia();
        System.err.println(frota.relatorioFrotaDeOntem());
    }

    private static void tutorial(){
        exibirDia();
        System.err.println("Sua frota não é grande, mas tem potencial.");
        System.err.println("Esses são os seus barcos e seus Carontes disponíveis no momento:");
        exibirRelatorioFrota();
        System.err.println("Carontes podem fazer até " + MAX_ROTAS_DIA + " viagens por dia.");
        System.err.println("Nenhum deles pode percorrer mais de " + MAX_ROTAS_DIA + " rotas em um mesmo dia");
        System.err.println("É algo do sindicato. Melhor não mexer nisso.");
        pausa();
        exibirRotas();
        System.err.println("Não é possível ter um barco na frota sem um Caronte e vice versa. Regras são regras.");
        System.err.println("Carontes têm nível de experiência. Alguns têm mais anos de firma.");
        System.err.println("Aqueles que começaram há pouco tempo não são muito prudentes e tem mais chance de cometer infrações.");
        System.err.println("Quanto mais experiente, menor a chance do guarda costeiro acabar aplicando uma multa.");
        System.err.println("E, mesmo que ele acabe fazendo um desleixo, provavelmente será algo leve.");
        System.err.println("Mas quanto mais experiente, maior será seu salário. Devemos respeitar o piso salarial.");
        System.err.println("Veja os Carontes empregados na sua frota:");
        exibirRelatorioCarontes();
        System.err.println("No momento, todos os Carontes contratados são de nível 1");
        System.err.println("Mas você pode sempre contratar outros quando for adquirir mais barcos. Claro, por um preço.");
        pausa();
        System.err.println("Vale a pena contratar Carontes mais experientes? Bom, nós não queremos nossos Carontes recebendo multas.");
        System.err.println("Se o Caronte tiver uma multa pendente, ele não pode entrar em uma rota.");
        System.err.println("A legislação do Submundo é bem rígida quanto a isso");
        System.err.println("Multas custam. E isso sai do nosso bolso. Do que ganhamos no dia.");
        System.err.println("Veja como estão as carteiras de motorista dos Carontes empregados por você:");
        exibirTodasMultas();
        System.err.println("No momento, nenhum Caronte da sua frota tem uma multa na carteira, mas tome cuidado.");
        System.err.println("Melhor evitar que multas sejam aplicadas. E há formas de fazer isso.");
        pausa();
        System.err.println("Quanto maior a rota que o Caronte for cruzar, maior a chance dele cometer alguma imprudência.");
        System.err.println("Além disso, se o barco estiver com superlotação, o Caronte pode não conseguir guiá-lo direito.");
        System.err.println("");


    



    }

private static void esperarInicio() {
        System.err.println("O Submundo é mais competitivo do que parece. O chefão, o próprio Hades, está ");
        System.err.println("precisando urgentemente de um novo Diretor Regional  e ele quer um funcionário");
        System.err.println("exemplar. Começou, então, a maior competição entre gerentes de Carontes. Temos");
        System.err.println("13 dias para coletar 444 almas e bater a meta. Precisamos de enviar bons ");
        System.err.println("barqueiros em barcos que suportam vários passageiros para conseguir mais pontos ");
        System.err.println("com o chefe. Mas não existe almoço grátis. Você terá que gastar dinheiro para fazer ");
        System.err.println("dinheiro. Ou melhor, dar a alma para o negócio. Só temos que ter o cuidado de não");
        System.err.println("gastar tudo que conseguirmos para em compras, salário e combustível. A meta ");
        System.err.println("ainda é trazer 444 almas penitentes para o fundo do Tártaro.");
        separador();
        System.err.println("Estamos prontos para trazer almas mortais e conquistarmos a vaga de Diretor Regional?");
        pausa();
        }

    /**
     * Inicializa a frota de veículos com base em valores aleatórios gerados pelo
     * sistema.
     * Cria uma nova Frota com um tamanho aleatório entre 1 e 5 veículos e os
     * abastece com uma quantidade inicial de combustível.
     */
    private static void inicializarFrota() {
        frota = new Frota();
        for (int i = 0; i < 4; i++) {
            String nomeMotorista = nomesCarontes.gerarNome();
            Caronte motorista = new Caronte(nomeMotorista, 1, MAX_ROTAS_DIA);
            Barco gondola = gerarGondola(motorista); 
            addBarcoFrota(gondola);
        }
    }
 
    public static void addBarcoFrota(Barco barco){
        nomesBarcos.marcarNomeUtilizado(barco);
        nomesCarontes.marcarNomeUtilizado(barco.getMotorista());
        frota.addBarco(barco);
    }    

    public double executarAcaoNaFrotaComAlmas(Function<Frota, Double> funcao) {
        return custos.executarTransacao(funcao, frota, jogador);
    }

        public void executarAcaoNaFrotaDeListar(Function<Frota, List<String>> funcao) {
        List<String> lista = funcao.apply(frota);
        for (String string : lista) {
            separador();
            System.out.println(string);
        }
        separador();
        fogo();
        pausa();
    }

    
        public void executarAcaoNaFrotaDeImprimir(Function<Frota, Void> funcao) {
            funcao.apply(frota);
        }

        public void executarAcaoNaFrotaDeControle(Function<Frota, String> funcao) {
        String mensagem = funcao.apply(frota);
        System.out.println(mensagem);
        }

    /**
     * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
     */

        private static void mostrarMenuPrincipal() {
        int opcao;
        do {
        exibirAlmas();
        exibirRelatorioFrota();
        System.out.println("--- Menu ---");
        System.out.println("1. Barcos da Frota");
        System.out.println("2. Carontes da Frota");
        System.out.println("3. Rotas de Coleta de Almas");
        System.out.println("4. Setor de Aquisições do Submundo");
        System.out.println("5. Relatório Completo da Frota");
        System.out.println("6. Encerrar Dia");
        separador();
        System.out.print("Selecione uma opção: ");
        
        opcao = teclado.nextInt();
        teclado.nextLine();
        switch (opcao) {
            case 1:
                menuBarcos();
                break;
            case 2:
                menuCarontes();
                break;
            case 3:
                menuRotas();
                break;
            case 4:
                menuAquisicoes();
                break;
            case 5:
                relatorioCompletoFrota();
                break;
            case 6:
                encerrarDia();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }while(opcao != 6);
}

    private void mostrarMenuBarcos() {
        int opcao;
        do {
            System.out.println("\n--- Barcos ---");
            System.out.println("1. Listar Barcos por Tipo");
            System.out.println("2. Abastecer Barcos");
            System.out.println("3. Realizar Manutenção Em Barcos");
            System.out.println("4. Mostrar Barco com Maior Quilometragem Total");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada: ");
            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    listarBarcosPorTipo();
                    break;
                case 2:
                    menuAbastecer();
                    break;
                case 3:
                    menuManutencao();
                    break;
                case 4:
                    mostrarBarcoComMaiorQuilometragemTotal();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }


    private static BarcoComTanque menuCombustivel(BarcoComTanque barco){
        boolean confirmacao = false;
        BarcoComTanque barcoCopia = ((BarcoComTanque) barco);
        while (!confirmacao) {
            System.out.println("O barco " + barco.getNOME() + " é movido a " + barco.getTipoCombustivel());
            System.out.println("Deseja que o barco permaneça com esse tipo de combustível?");
            confirmacao = confirmacao();
            if(!confirmacao){
        List<BarcoComTanque> barcosParaCombustivel = new ArrayList<>();
        for (int i = 1; i <= 2; i++) { 
            barcosParaCombustivel.add(barcoCopia);       
            if (barco instanceof Balsa) {
                barcoCopia = new Balsa((Balsa) barco);
            } else if (barco instanceof Navio) {
                barcoCopia = new Navio((Navio) barco);
            } else if (barco instanceof Cruzeiro) {
                barcoCopia = new Cruzeiro((Cruzeiro) barco);
            } else {
                throw new IllegalArgumentException("Barco selecionado não existe.");
            }
        
            switch (barcoCopia.getTanque().getTipo().getTipo()) {
                case "ALCOOL":
                    barcoCopia.instalarTanque("Gasolina");
                    break;
                case "GASOLINA":
                    barcoCopia.instalarTanque("Diesel");
                    break;
                case "DIESEL":
                    barcoCopia.instalarTanque("Álcool");
                    break;
            }
            while(!confirmacao){
            for(int j = 1; j <= 2; j++){
            BarcoComTanque barcoTanque = barcosParaCombustivel.get(j);
                System.err.println("Tanque #" + j + ":" + barcoTanque.getTipoCombustivel());
                System.err.println("Preço adicional pelo combustível " + barcoTanque.getTipoCombustivel() + ": " + barcoTanque.getAdicionalPrecoVenda() + " almas.");
                System.err.println("Preço do litro de " + barcoTanque.getTipoCombustivel() + ": " + barcoTanque.getAdicionalPrecoVenda() + " almas.");
        }
            System.err.println("Qual tipo de combustível deseja que o barco utilize:");
            int escolha = menuEscolhaNumeros(1, 3);
            barcoCopia = barcosParaCombustivel.get(escolha);
    }
    return (BarcoComTanque) barcoCopia;
            }
        }
    }

    private static Caronte menuContratarCaronte() {
        List<Caronte> carontesDisponiveis = new ArrayList<>();
        boolean confirmacao = false;
        Caronte caronteEscolhido = null;
        while (!confirmacao) {
            separador();
        System.err.println("Lista do Setor de Aquisições de barcos disponíveis:");
            separador();
        for (int i = 1; i <= 4; i++) {
            Caronte caronte = new Caronte(nomesCarontes.gerarNome(), i, MAX_ROTAS_DIA);
            carontesDisponiveis.add(caronte);
    
            System.out.println("Caronte #" + i);
            System.err.println("Nome: " + caronte.getNome());
            System.err.println("Nivel de Experiência: " + caronte.getNivel());
            System.err.println("Salário por Dia: " + caronte.getSalario());
            System.err.println("Probabilidade de Cometer Infrações: " + caronte.getProbabilidade() + "\n");            System.out.println();
        }
            separador();
        System.out.println("Qual Caronte você deseja contratar?");
        int escolhaCaronte = menuEscolhaNumeros(1, 4);
        caronteEscolhido = carontesDisponiveis.get(escolhaCaronte-1);
        System.out.println("Você deseja contratar o Caronte ");  
        System.err.println("Nome: " + caronteEscolhido.getNome());
        System.err.println("Nivel de Experiência: " + caronteEscolhido.getNivel());
        System.err.println("Salário por Dia: " + caronteEscolhido.getSalario());
        System.err.println("Probabilidade de Cometer Infrações: " + caronteEscolhido.getProbabilidade() + "\n");   
       confirmacao = confirmacao();
        }
            return caronteEscolhido;
 
    }
    

    private static boolean confirmacao(){
        boolean confirmacao = false;
        String teste;
        while(!confirmacao){
        System.err.println("Digite Sim ou Não:");
         teste = teclado.nextLine();
        if(normalizar(teste).equals(normalizar("Sim")))
        {
          return true;
        } else if(normalizar(teste).equals(normalizar("Não")))  {
                      return false;
        }else{
        System.err.println("Resposta inválida. Responda com Sim ou Não.");
        confirmacao = false;
        }
        }return false;
    }
   
        private static int menuEscolhaNumeros(int primeiro, int ultimo){
        System.out.print("Selecione um opção (Entre " + primeiro + "-" + ultimo + "): ");
        int escolha = teclado.nextInt();

        // Verificar se a escolha é válida
        while(escolha < primeiro || escolha > ultimo) {
            System.out.println("Não há uma opção referente a esse número. Tente novamente com números entre " + primeiro + "-" + ultimo + ".");
            pausa();
            escolha = teclado.nextInt();
        }
        return escolha;
        }
    /**
     * Interage com o sistema para cadastrar um novo veículo na frota.
     * Solicita ao usuário informações como, placa, tipo de veículo
     * tipo de combustível, nome e CPF do motorista.
     * Valida as entradas e, se bem-sucedido, adiciona o
     * veículo à frota.
     */
    private static void menuComprarBarco() {
        String tipoCombustivel;
        exibirAlmas();

        List<Caronte> motoristas = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
        Caronte motorista;
        motoristas.add(motorista);
        }

        List<String> opcoesCombustivel = new ArrayList<>();
        opcoesCombustivel.add("Álcool");
        opcoesCombustivel.add("Gasolina");
        opcoesCombustivel.add("Diesel");

        Collections.shuffle(opcoesCombustivel);   

        List<Barco> barcosParaVenda = new ArrayList<>();
        // Gerar Gôndola, Balsa, Navio e Cruzeiro
        Gondola gondola = gerarGondola(motoristas.get(1));
        barcosParaVenda.add(gondola);
        Balsa balsa = (Balsa) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(0), "Balsa");
        barcosParaVenda.add(balsa);
        Navio navio = (Navio) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(1), "Navio");
        barcosParaVenda.add(navio); 
        Cruzeiro cruzeiro = (Cruzeiro) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(2), "Cruzeiro");
        barcosParaVenda.add(cruzeiro);
        // Imprimir o relatório
        Barco barcoEscolhido;
        boolean confirmacao = false;
                        separador();
        while(!confirmacao){
          System.out.println("O Setor de Aquisições dá as boas-vindas ao gerente " + jogador.getNomePersonagem() + "!");
          System.err.println("Lista do Setor de Aquisições de barcos disponíveis:");
                      separador();
        for (int i = 1; i <= barcosParaVenda.size(); i++) {
            Barco barco = barcosParaVenda.get(i - 1); // Obtém o barco correspondente ao índice
        
            System.out.println("Barco #" + i);
            System.out.println("Nome: " + barco.getNOME());
            System.out.println("Tipo de Barco: " + barco.getTipoDeBarco());
            System.out.println("Capacidade máxima do barco: " + barco.getCAPACIDADEPASSAGEIROS() + "\n");        
            if (barco instanceof BarcoComTanque) {
              System.out.println("Capacidade máxima do tanque: " + ((BarcoComTanque)barco).getCapacidadeTanque() + "\n");                      
                System.err.println("Preço adicional pelo combustível " + ((BarcoComTanque) barco).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.err.println("Preço do litro de " + ((BarcoComTanque) barco).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.err.println("Preço base do barco: " + barco.getPRECOCUSTO() + " almas.");
                System.err.println("Preço total do barco: " + ((BarcoComTanque) barco).getPrecoTotal() + " almas.");
            } else {
                System.err.println("Preço: " + barco.getPRECOCUSTO() + " almas.");
            }
        }
          System.err.println("Qual barco você deseja incluir no seu pedido ao Setor de Aquisições:");

           int escolha = menuEscolhaNumeros(1, 4);

            barcoEscolhido = barcosParaVenda.get(escolha);
            System.out.println("Você selecionou o Barco #" + escolha);
            System.out.println("Nome: " + barcoEscolhido.getNOME());
            System.out.println("Tipo de Barco: " + barcoEscolhido.getTipoDeBarco()); 
            System.out.println("Tem certeza de que deseja adquirir este barco?");
            confirmacao = confirmacao();
    if (barcoEscolhido instanceof BarcoComTanque) {
                barcoEscolhido = menuCombustivel((BarcoComTanque)  barcoEscolhido);
            }
            Caronte caronte = menuContratarCaronte();
            barcoEscolhido.atribuirMotorista(caronte);
            separador();
            System.out.println("Você está fazendo o seguinte pedido ao Setor de Aquisições:");
            separador();
            System.out.println("Pedido do gerente " + jogador.getNomePersonagem());
            System.out.println("Barco a ser adquirido: " + barcoEscolhido.getNOME());
            System.out.println("Tipo de Barco: " + barcoEscolhido.getTipoDeBarco());
            System.out.println("Capacidade máxima do barco: " + barcoEscolhido.getCAPACIDADEPASSAGEIROS() + "\n");        
            if (barcoEscolhido instanceof BarcoComTanque) {
              System.out.println("Capacidade máxima do tanque: " + ((BarcoComTanque)barcoEscolhido).getCapacidadeTanque() + "\n");                      
                System.err.println("Preço adicional pelo combustível " + ((BarcoComTanque) barcoEscolhido).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.err.println("Preço do litro de " + ((BarcoComTanque) barcoEscolhido).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.err.println("Preço base do barco: " + barcoEscolhido.getPRECOCUSTO() + " almas.");
                System.err.println("Preço total do barco: " + ((BarcoComTanque) barcoEscolhido).getPrecoTotal() + " almas.");            
            }else{
                System.err.println("Preço: " + barcoEscolhido.getPRECOCUSTO() + " almas.");
            }
            separador();
            System.err.println("Caronte a ser contratado: " + caronte.getNome());
            System.err.println("Nivel de Experiência: " + caronte.getNivel());
            System.err.println("Salário por Dia: " + caronte.getSalario());
            System.err.println("Probabilidade de Cometer Infrações: " + caronte.getProbabilidade() + "\n");   
            separador();
            exibirAlmas();
            separador();
            System.out.println("Você tem certeza de que gostaria de fazer este pedido?");
            confirmacao = confirmacao();
        }
        custos.comprarBarco(barcoEscolhido, jogador);
    }


    /**
     * Cadastra um veículo automaticamente com informações aleatórias.
     * 
     * @param placa A placa única do veículo a ser cadastrado.
     * @return O veículo cadastrado ou null se não for possível criar o veículo.
     */
    private static Barco gerarBarcoComTanque(Caronte motorista, String tipoCombustivel, String tipoBarco) {    
        GeradorNomesBarcos cloneNomesBarco = new GeradorNomesBarcos(nomesBarcos);
        String nomeBarco = cloneNomesBarco.gerarNome();
        Barco barco;
        switch(normalizar(tipoBarco)){
            case "BALSA":
                 barco = new Balsa(motorista, nomeBarco, tipoCombustivel, MAX_ROTAS_DIA);
            break;
            case "NAVIO":
                 barco = new Navio(motorista, nomeBarco, tipoCombustivel, MAX_ROTAS_DIA);
            break;
            case "CRUZEIRO":
                 barco = new Cruzeiro(motorista, nomeBarco, tipoCombustivel, MAX_ROTAS_DIA);
            break;
            default:
                    throw new IllegalArgumentException("Este barco não existe e seus senhores não estão contentes.");
            }  
                return barco;
    }
    
     private static Gondola gerarGondola(Caronte motorista) {  
        GeradorNomesBarcos cloneNomesBarco = new GeradorNomesBarcos(nomesBarcos);
        String nomeBarco = cloneNomesBarco.gerarNome();  
        Gondola gondola = new Gondola(motorista, nomeBarco, MAX_ROTAS_DIA);
        return gondola;
    } 


    private static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.toUpperCase();

        return normalizado;
    }

    /**
     * Método principal que atua como ponto de entrada do sistema de gerenciamento
     * de frotas.
     * Inicializa o sistema, saúda o usuário e entra em um loop que apresenta um
     * menu de
     * opções.
     * O sistema seleciona automaticamente as opções para gerenciar veículos, rotas,
     * manutenções
     * e despesas com base em dados aleatórios gerados pelo sistema.
     * O loop continua até que o usuário opte por encerrar o programa.
     * Exceções de entrada são tratadas para evitar falhas devido a entradas
     * inválidas durante a
     * seleção do menu.
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("");
        inicializarFrota();
        separador();
        esperarInicio();
        tutorial();

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            try {
                int opcao = teclado.nextInt();
                separador();
                switch (opcao) {
                    case 1:
                        cadastrarVeiculo(teclado);
                        separador();
                        break;
                    case 2:
                        exibirRelatorioFrota();
                        separador();
                        break;
                    case 3:
                        registrarRota();
                        separador();
                        break;
                    case 4:
                        abastecerVeiculo(teclado);
                        separador();
                        break;
                    case 5:
                        registrarMulta(teclado);
                        separador();
                        break;
                    case 6:
                        pagarMulta(teclado);
                        separador();
                        break;
                    case 7:
                        verificarManutencaoVeiculos();
                        separador();
                        break;
                    case 8:
                        listarRotasNaoPercorridas(teclado);
                        separador();
                        break;
                    case 9:
                        percorrerRotaEspecifica(teclado);
                        separador();
                        break;
                    case 10:
                        realizarManutencaoVeiculo(teclado);
                        separador();
                        break;
                    case 11:
                        exibirRelatorioRotasVeiculo();
                        separador();
                        break;
                    case 12:
                        continuar = false; // Sai do loop e encerra o programa.
                        System.out.println("Até logo ;)");
                        break;
                    default:
                        System.out.println("Opção inválida! Digite opção presente no menu. :)");
                        break;
                }
                // Aguarda um tempo antes de executar a próxima ação
                pausa();
                limparTela();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: digite um número presente no menu." );
                teclado.nextLine();
                pausa();
                limparTela();
            }
        }
        teclado.close();
    }
}
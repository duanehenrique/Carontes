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
import java.util.function.BiFunction;
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
    static int ALMAS_POR_DIA = 10;
    static int TEMPO_MAX = 13;
    static int META = 444;
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


private static void desenharCaronte(){
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

            private static void desenharAlma() {
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

            public static void desenharBarco() {
                System.out.println("          \\./          /||     \\./");
                System.out.println("                     / ||");
                System.out.println("                    /  ||\\        \\./");
                System.out.println("                   /   || \\");
                System.out.println("                  /    ||  \\");
                System.out.println("                 /     /    \\");
                System.out.println("                /_____/      \\");
                System.out.println("               ______/________\\");
                System.out.println("     ~/\\~      \\_|__|__|___|__|_/");
                System.out.println("               ~~~/\\~~~~~~       /\\~~~/\\~~~");
                System.out.println("~~/\\~~~~~~~      /\\~~     ~~~/\\~~~~~");
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
        private static void exibirCustoTotal(double custoDeCompra){
        if(custoDeCompra > 0){
        separador();
        System.err.println("Almas consumidas com a transação: " + custoDeCompra);
        separador();
        pausa();
        }
        }
        private static List<Rota> gerarRotas(){
        separador();
        GeradorRotas rotaDisponivel = new GeradorRotas();
        List<Rota> rotas = rotaDisponivel.gerarRotas(frota);
        for (Rota rota : rotas) {
            rota.relatorio();
        }
        separador();
        return rotas;
        }

    /**
     * Exibe um relatório detalhado da frota, incluindo informações de cada veículo
     * cadastrado.
     */
    private static void exibirRelatorioFrota() {
        StringBuilder resultado = new StringBuilder();
        separador();
        resultado.append("---- Barcos da Frota do Gerente ").append(jogador.getNomePersonagem()).append(" ----\n");
        separador();
        resultado.append(frota.relatorioFrota());
        resultado.toString();
        separador();
    }

    private static void exibirRelatorioCarontes(){
        StringBuilder resultado = new StringBuilder();
        separador();
        resultado.append("---- Carontes da Frota do Gerente ").append(jogador.getNomePersonagem()).append(" ----\n");
        System.err.println(resultado.toString());
        separador();
        resultado.append(frota.relatorioCarontes());
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
        jogador.addAlmasAoDia(ALMAS_POR_DIA);
        System.err.println(frota.relatorioFrotaDeOntem());
    }


    private static void tutorial(){
        boolean confirmacao = false;
        while (!confirmacao) {
            
        }
        exibirDia();
        System.out.println("Sua frota não é grande, mas tem potencial.");
        System.out.println("Esses são os seus barcos e seus Carontes disponíveis no momento:");
        desenharBarco();
        exibirRelatorioFrota();
        System.out.println("Carontes podem fazer até " + MAX_ROTAS_DIA + " viagens por dia.");
        System.out.println("Nenhum deles pode percorrer mais de " + MAX_ROTAS_DIA + " rotas em um mesmo dia");
        System.out.println("É algo do sindicato. Melhor não mexer nisso.");
        pausa();
        desenharCaronte();
        System.out.println("Não é possível ter um barco na frota sem um Caronte e vice versa. Regras são regras.");
        System.out.println("Carontes têm nível de experiência. Alguns têm mais anos de firma.");
        System.out.println("Aqueles que começaram há pouco tempo não são muito prudentes e tem mais chance de cometer infrações.");
        System.out.println("Quanto mais experiente, menor a chance do guarda costeiro acabar aplicando uma multa.");
        System.out.println("E, mesmo que ele acabe fazendo um desleixo, provavelmente será algo leve.");
        System.out.println("Mas quanto mais experiente, maior será seu salário. Devemos respeitar o piso salarial.");
        System.out.println("Veja os Carontes empregados na sua frota:");
        exibirRelatorioCarontes();
        System.out.println("No momento, todos os Carontes contratados são de nível 1.");
        System.out.println("Mas você pode sempre contratar outros quando for adquirir mais barcos. Claro, por um preço.");
        System.out.println("E, como um bom gerente, você pode pagar um bom curso de especialização para seus Carontes. Estamos sempre em uma jornada de crescimento!");    
        pausa();
        System.out.println("Vale a pena contratar Carontes mais experientes? Bom, nós não queremos nossos Carontes recebendo multas.");
        System.out.println("Se o Caronte tiver uma multa pendente, ele não pode entrar em uma rota.");
        System.out.println("A legislação do Submundo é bem rígida quanto a isso");
        System.out.println("Multas custam. E isso sai do nosso bolso. Do que ganhamos no dia.");
        System.out.println("Veja como estão as carteiras de motorista dos Carontes empregados por você:");
        exibirTodasMultas();
        System.out.println("No momento, nenhum Caronte da sua frota tem uma multa na carteira, mas tome cuidado.");
        System.out.println("Melhor evitar que multas sejam aplicadas. E há formas de fazer isso.");
        pausa();
        desenharAlma();
        System.out.println("Quanto maior a rota que o Caronte for cruzar, maior a chance dele cometer alguma imprudência.");
        System.out.println("Além disso, se o barco estiver com superlotação, o Caronte pode não conseguir guiá-lo direito.");
        System.out.println("Imagine o peso do barco super cheio! Difícil demais de conduzir.");
        System.out.println("Sem falar no caos que deve ser ter esse tanto de gente gritando e pedindo clemência para não ser levado ao submundo.");
        System.out.println("Os burocráticos consideram como superlotação uma carga de mais 80% da capacidade do barco. Gente demais de uma vez, apenas.");
        System.out.println("Por outro lado, quanto maior a rota, maior a quantidade de passageiros.");
        System.out.println("E quanto mais passageiros maior a chance de você lucrar. Mais passageiros são mais almas e mais almas significam que o chefe está contente.");
        System.out.println("Mas lembre-se de que trajetos longos implicam em maior gasto com combustível. E instalar o tanque tem um adicional de custo também.");
        System.out.println("Gôndolas são movidas pela força bruta dos Carontes. As leis trabalhistas por aqui sao confusas.");
        System.out.println("Mas barcos maiores consomem combustível e isso requer almas. Sem combustível o barco não consegue viajar, mas sem almas não se pode fazer nada. Equilibrio é tudo.");
        System.out.println("Veja exemplos de rotas:");
        gerarRotas();
        pausa();
        System.out.println("As rotas de coleta têm um número definido de passageiros a serem recolhidos. Esse número pode variar bastante.");
        System.out.println("E pode até ultrapassar a capacidade máxima do barco. Nesse caso, o Caronte vai lotar seu barco e deixar alguns para trás.");        
        System.out.println("É uma pena desperdiçar tantas almas humanas assim, mas é como é.");
        pausa();
        System.out.println("Os passageiros a serem recolhidos nas rotas estão sendo levados por vários motivos.");
        System.out.println("Dependendo do motivo, sua alma pode valer mais ou menos.");
        System.out.println("Veja como podem ser as almas que seus Carontes vão coletar:");
        exibirExemploPassageiro();
        pausa();
        System.out.println("Se você der sorte, seus Carontes levarão apenas pecadores culpados de traição, o mais horrendo dos pecados.");
        System.out.println("Mas, se der azar, você pode ter que gastar horas preenchendo a papelada de heróis com feitos incríveis... Que não nos servem de nada.");
        System.out.println("Ninguém quer levar um herói para o inferno. É um desperdício de recursos, mas infelizmente é nosso trabalho também.");
        pausa();
        System.out.println("A cada dia que se passa, um gerente recebe " + ALMAS_POR_DIA + " almas por dia, para investir no que for preciso.");
        System.out.println("O chefe pode ser carruncudo, mas ele sabe que nem todo dia é fartura. Às vezes um leve empurrãozinho é do que sua frota precisa.");
        pausa();
        desenharBarco();
        exibirRelatorioFrota();       
        System.out.println("Todo barco precisa de manutenção de tempos em tempos. E elas custam, então é bom se prevenir.");
        System.out.println("Cada tipo de barco precisa de ir para a oficina depois de uma certa quilometragem percorrida diferentes, mas todos precisam. É uma lei, pela nossa própria segurança.");
        System.out.println("Barcos que precisam de combustível tem um custo adicional de acordo com o tipo de combustível.");
        System.out.println("Cada tipo de combustível tem um custo para instalar o tanque. Os mecânicos do submundo são terceirizados e querem pagamento imediato.");
        System.out.println("Da mesma forma, cada combustível tem um custo diferente para abastecer. Os postos são traiçoeiros, mas ao menos têm preços tabelados.");
        pausa();
                System.out.println("Sua frota não é grande, mas tem potencial.");

        System.out.println("Para alcançar sua meta de " + META + " almas, você pode fazer o que for preciso.");
        System.out.println("Você pode comprar mais barcos, trocar Carontes de barco e muito mais. Você vai pegar o jeito aos poucos.");
        System.out.println("Só não se esqueça que o desafio vai durar " + TEMPO_MAX + " dias!");
        System.out.println("Se você não bater a meta até lá, a vaga de diretor regional não será sua. Você não quer isso, não é mesmo?");
        System.out.println("Está pronto para gerenciar sua frota de Carontes e trazer o máximo de almas humanas ao submundo que for inumanamente possível?");
        confirmacao = confirmacao();
        if(!confirmacao){
            System.out.println("Deixe-me repetir então.");
        }
    }

private static void definirJogador(){
        boolean confirmacao = false;
        String nome = null;
        separador();
        desenharCaronte();
        separador();
        System.out.println("Olá, gerente! Seja bem-vindo ao submundo!");
        System.out.println("Eu sou o novo chefe de Recursos Inumanos do inferno e estou aqui para te ajudar.");
        System.out.println("Imagino que suas férias tenham sido ótimas. Mas agora é hora de voltarmos ao trabalho.");
        System.out.println("Muita coisa mudou desde que você saiu de férias.");
        System.out.println("Tem pouco tempo que trabalho aqui, então não conheço todos os gerentes. Qual é seu nome?");
        while(!confirmacao){
        System.out.println("Digite seu nome:");
        nome = teclado.nextLine();
        teclado.nextLine();
        System.out.println("Seu nome é " + nome + "?");
        confirmacao = confirmacao();
        if(!confirmacao){
        System.out.println("Acho que eu ouvi errado. Qual é seu nome?");
        }
    }
    jogador = new Jogador(nome);
        System.out.println("Muito bom te conhecer, " + jogador.getNomePersonagem());
        System.out.println("Agora que já está acomodado, deixe-me te explicar o que aconteceu.");
}

private static void esperarInicio() {
        System.out.println("O Submundo é mais competitivo do que parece. O chefão, o próprio Hades, está ");
        System.out.println("precisando urgentemente de um novo Diretor Regional  e ele quer um funcionário");
        System.out.println("exemplar. Começou, então, a maior competição entre gerentes de Carontes. Temos");
        System.out.println(TEMPO_MAX + " dias para coletar " + META + " almas e bater a meta. Precisamos de enviar bons ");
        System.out.println("barqueiros em barcos que suportam vários passageiros para conseguir mais pontos ");
        System.out.println("com o chefe. Mas não existe almoço grátis. Você terá que gastar dinheiro para fazer ");
        System.out.println("dinheiro. Ou melhor, dar a alma para o negócio. Só temos que ter o cuidado de não");
        System.out.println("gastar tudo que conseguirmos  em compras, salário e combustível. A meta ");
        System.out.println("ainda é trazer " + META + " almas penitentes para o fundo do Tártaro.");
        separador();
        System.out.println("Estamos prontos para trazer almas mortais e conquistarmos a vaga de Diretor Regional?");
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
private static void exibirExemploPassageiro() {
    GeradorNomePassageiro gerador = new GeradorNomePassageiro();
    List<Passageiro> listaPassageiros = new ArrayList<>();
            Passageiro passageiro1 = new Passageiro(gerador.gerarNomePassageiro(), "Héroi");
            listaPassageiros.add(passageiro1);
            Passageiro passageiro2 = new Passageiro(gerador.gerarNomePassageiro(), "Limbo");
            listaPassageiros.add(passageiro2);
            Passageiro passageiro3 = new Passageiro(gerador.gerarNomePassageiro(), "Luxúria");
            listaPassageiros.add(passageiro3);
            Passageiro passageiro4 = new Passageiro(gerador.gerarNomePassageiro(), "Gula");
            listaPassageiros.add(passageiro4);
            Passageiro passageiro5 = new Passageiro(gerador.gerarNomePassageiro(), "Avareza");
            listaPassageiros.add(passageiro5);
            Passageiro passageiro6 = new Passageiro(gerador.gerarNomePassageiro(), "Ira");
            listaPassageiros.add(passageiro6);
            Passageiro passageiro7 = new Passageiro(gerador.gerarNomePassageiro(), "Heresia");
            listaPassageiros.add(passageiro7);
            Passageiro passageiro8 = new Passageiro(gerador.gerarNomePassageiro(), "Violência");
            listaPassageiros.add(passageiro8);
            Passageiro passageiro9 = new Passageiro(gerador.gerarNomePassageiro(), "Fraude");
            listaPassageiros.add(passageiro9);
            Passageiro passageiro10 = new Passageiro(gerador.gerarNomePassageiro(), "Traição");
            listaPassageiros.add(passageiro10);
            int contagem = 1; 
            for (Passageiro passageiro : listaPassageiros) {
                System.err.println("Passageiro #" + contagem + ": " + passageiro.relatorio());
                contagem++;
            }
            
}

 
    public static void addBarcoFrota(Barco barco){
        nomesBarcos.marcarNomeUtilizado(barco);
        nomesCarontes.marcarNomeUtilizado(barco.getMotorista());
        frota.addBarco(barco);
    }    

    public static void executarAcaoNaFrotaComAlmasEspecifica(List<Object> objetos, int funcao) {
        try {
        if(funcao >= 1 && funcao <= 17){
        double custoTotal = custos.executarTransacaoEspecifica(objetos, funcao, frota, jogador);
        System.err.println("Gasto da frota do gerente " + jogador.getNomePersonagem() + " com a transação: " + custoTotal + " almas.");
        separador();
        desenharAlma();
        }
        } catch (Exception e) {
         System.out.println(e.getMessage());
        }
        }

        public static void executarAcaoNaFrotaComAlmasGeral(List<Object> objetos, int funcao) {
         try {
        if(funcao >= 18 && funcao <= 23){
        double custoTotal = custos.executarTransacaoGeral(funcao, frota, jogador);
        System.err.println("Gasto da frota do gerente " + jogador.getNomePersonagem() + " com a transação: " + custoTotal + " almas.");
        separador();
        desenharAlma();
        }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
        public static void executarAcaoNaFrotaDeControle(List<Object> objetos, int funcao) {
        Executor executor = new Executor();
        if(funcao >= 24 && funcao <= 31){
        List<String> mensagem = (List<String>) executor.executarAcaoNaFrotaDeControle(frota, funcao, objetos);   
        System.out.println(mensagem);
        }
        }

        public static void executarAcaoNaFrotaDeListarEspecifico(List<Object> objetos, int funcao) {
        Executor executor = new Executor();
        if(funcao >= 32 && funcao <= 37){
        List<String> mensagens = (List<String>) executor.executarAcaoNaFrotaDeListarEspecifico(frota, funcao, objetos);
        imprimirLista(mensagens);
        }
    }

         public static void executarAcaoNaFrotaDeListarGeral(int funcao) {
        Executor executor = new Executor();
        if(funcao >= 32 && funcao <= 37){
        List<String> mensagens = (List<String>) executor.executarAcaoNaFrotaDeListarGeral(frota, funcao, objetos);
        imprimirLista(mensagens);
        }
    }
        private static void imprimirLista(List<String> mensagens){
             for (String string : mensagens) {
            separador();
            System.out.println(string);
        }
        separador();
        pausa();
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

    private static void menuBarcos() {
        int opcao;
        do {
            System.out.println("\n--- Barcos ---");
            System.out.println("1. Listar Barcos por Tipo");
            System.out.println("2. Abastecer Barcos");
            System.out.println("3. Realizar Manutenção Em Barcos");
            System.out.println("4. Mostrar Barco com Maior Quilometragem Total");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada: ");
            opcao = menuEscolhaNumeros(0, 3);
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
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuCarontes() {
        int opcao;
        do {
            System.out.println("\n--- Carontes ---");
            System.out.println("1. Pagar Multa do Caronte por Índice");
            System.out.println("2. Pagar Multa do Caronte por Nome");
            System.out.println("3. Pagar Todas as Multas do Caronte por Índice");
            System.out.println("4. Pagar Todas as Multas do Caronte por Nome");
            System.out.println("5. Pagar Todas as Multas de Todos os Carontes");
            System.out.println("6. Pagar Curso de Especialização por Nome");
            System.out.println("7. Pagar Curso de Especialização por Índice");
            System.out.println("8. Pagar Salário do Motorista por Índice");
            System.out.println("9. Pagar Salário do Motorista por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada: ");
            
            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    pagarMultaDoCarontePorIndex();
                    break;
                case 2:
                    pagarMultaDoCarontePorNome();
                    break;
                case 3:
                    pagarTodasMultasDoCarontePorIndex();
                    break;
                case 4:
                    pagarTodasMultasDoCarontePorNome();
                    break;
                case 5:
                    pagarTodasMultasDeTodosCarontes();
                    break;
                case 6:
                    pagarCursoDeEspecializacaoPorNome();
                    break;
                case 7:
                    pagarCursoDeEspecializacaoPorIndex();
                    break;
                case 8:
                    pagarSalarioMotoristaPorIndex();
                    break;
                case 9:
                    pagarSalarioMotoristaPorNome();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private static void menuRotas() {
        int opcao;
        do {
            System.out.println("\n--- Rotas de Coleta de Almas ---");
            System.out.println("1. Adicionar Barco à Frota");
            System.out.println("2. Adicionar Rota por Nome");
            System.out.println("3. Adicionar Rota por Índice");
            System.out.println("4. Excluir Rota por Índice");
            System.out.println("5. Excluir Rota por Nome");
            System.out.println("6. Trocar Rotas por Nome");
            System.out.println("7. Trocar Rotas por Índice");
            System.out.println("8. Trocar Carontes por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada: ");
            
            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    adicionarBarcoAFrota();
                    break;
                case 2:
                    adicionarRotaPorNome();
                    break;
                case 3:
                    adicionarRotaPorIndex();
                    break;
                case 4:
                    excluirRotaPorIndex();
                    break;
                case 5:
                    excluirRotaPorNome();
                    break;
                case 6:
                    trocarRotasPorNome();
                    break;
                case 7:
                    trocarRotasPorIndex();
                    break;
                case 8:
                    trocarCarontesPorNome();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private static void menuAquisicoes() {
        int opcao;
        do {
            System.out.println("\n--- Setor de Aquisições do Submundo ---");
            System.out.println("1. Listar Carontes");
            System.out.println("2. Relatório da Frota");
            System.out.println("3. Relatório da Frota de Ontem");
            System.out.println("4. Listar Todos para Abastecer");
            System.out.println("5. Listar Todos os Salários para Pagar");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada: ");
            
            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    listarCarontes();
                    break;
                case 2:
                    relatorioFrota();
                    break;
                case 3:
                    relatorioFrotaDeOntem();
                    break;
                case 4:
                    listarTodosParaAbastecer();
                    break;
                case 5:
                    listarTodosSalariosParaPagar();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private static void menuManutencao() {
        int opcao;
        do {
            System.out.println("\n--- Manutenção de Barcos ---");
            System.out.println("1. Fazer Manutenção em Barco por Índice");
            System.out.println("2. Fazer Manutenção em Barco por Nome");
            System.out.println("0. Voltar ao Menu de Barcos");
            System.out.print("Digite a opção desejada: ");
            
            opcao = menuEscolhaNumeros(0, 3);
            teclado.nextLine(); // Limpar o buffer
        
            switch (opcao) {
                case 1:
                    fazerManutencaoBarcoPorIndex();
                    break;
                case 2:
                    fazerManutencaoPorNome();
                    break;
                case 3:
                    fazerManutencaoTodosBarcos();
                break;
                case 0:
                    System.out.println("Voltando ao Menu de Barcos.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    private void menuAbastecer() {
        int opcao;
        do {
            System.out.println("\n--- Abastecimento de Barcos ---");
            System.out.println("1. Abastecer Barco por Índice");
            System.out.println("2. Abastecer Barco por Nome");
            System.out.println("3. Abastecer Barco Completo por Índice");
            System.out.println("4. Abastecer Barco Completo por Nome");
            System.out.println("5. Abastecer Todos os Barcos");
            System.out.println("0. Voltar ao Menu de Barcos");
            System.out.print("Digite a opção desejada: ");
            
            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    abastecerBarcoPorIndex();
                    break;
                case 2:
                    abastecerBarcoPorNome();
                    break;
                case 3:
                    abastecerBarcoCompletoPorIndex();
                    break;
                case 4:
                    abastecerBarcoCompletoPorNome();
                    break;
                case 5:
                    abastecerTodosBarcos();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu de Barcos.");
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
        GeradorNomesCarontes gerador = new GeradorNomesCarontes(nomesCarontes);
        Caronte caronteEscolhido = null;
        while (!confirmacao) {
            separador();
        System.err.println("Lista do Setor de Aquisições de barcos disponíveis:");
            separador();
        for (int i = 1; i <= 4; i++) {
            Caronte caronte = new Caronte(gerador.gerarNome(), i, MAX_ROTAS_DIA);
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
    private static double menuComprarBarco() {
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

        Gondola gondola = gerarGondola(motoristas.get(1));
        barcosParaVenda.add(gondola);
        Balsa balsa = (Balsa) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(0), "Balsa");
        barcosParaVenda.add(balsa);
        Navio navio = (Navio) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(1), "Navio");
        barcosParaVenda.add(navio); 
        Cruzeiro cruzeiro = (Cruzeiro) gerarBarcoComTanque(motoristas.get(1), opcoesCombustivel.get(2), "Cruzeiro");
        barcosParaVenda.add(cruzeiro);
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
        double custoDeCompra = custos.comprarBarco(barcoEscolhido, jogador);
        if(custoDeCompra > 0){
        nomesCarontes.marcarNomeUtilizado(barcoEscolhido.getMotorista());
        nomesBarcos.marcarNomeUtilizado(barcoEscolhido);
        }
        return custoDeCompra;
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

    private void abastecerBarcoPorIndex() {
        System.out.print("Digite o índice do barco que deseja abastecer: ");
        int index = teclado.nextInt();
        teclado.nextLine(); // Limpar o buffer
    
        System.out.print("Digite a quantidade de combustível desejada: ");
        double quantidade = teclado.nextDouble();
        teclado.nextLine(); // Limpar o buffer
    }

    private void abastecerBarcoPorNome() {
        System.out.print("Digite o nome do barco que deseja abastecer: ");
        String nome = teclado.nextLine();
    
        System.out.print("Digite a quantidade de combustível desejada: ");
        double quantidade = teclado.nextDouble();
        teclado.nextLine(); // Limpar o buffer
    
        double gasto = custos.executarTransacaoNomeDouble(
            Frota::abastecerBarcoCompletoPorNome,
            frota,
            jogador,
            nome,
            quantidade
        );
    
        if (gasto > 0) {
            System.out.println("Abastecimento concluído. Gasto: " + gasto);
        } else {
            System.out.println("Não foi possível realizar o abastecimento. Verifique os dados informados.");
        }
    }

    

    private void abastecerBarcoCompletoPorIndex() {
    System.out.print("Digite o índice do barco que deseja abastecer completamente: ");
    int index = teclado.nextInt();
    teclado.nextLine(); // Limpar o buffer

    double gasto = executarTransacao(index);


    if (gasto > 0) {
        System.out.println("Abastecimento completo concluído. Gasto: " + gasto);
    } else {
        System.out.println("Não foi possível realizar o abastecimento. Verifique os dados informados.");
    }
}
      


    private static void listarBarcosPorTipo(){
        System.out.println("\n--- Listagem de Barcos por Tipo ---");

        System.out.println("Listar tipos de Barcos:");
        System.out.println("1. Gôndola");
        System.out.println("2. Balsa");
        System.out.println("3. Navio");
        System.out.println("4. Cruzeiro");
        System.out.println("5. Todos");        

        String tipo = (String) receberString("o tipo de barco desejado");
        List<Object> lista = new ArrayList();
        lista.add((tipo));
        if (tipo.equals("0")) {
            System.out.println("Voltando ao Menu de Barcos.");
            return;
        }
        if(normalizar(tipo).equals(normalizar("Todos"))){
            for(int i = 1; i <= 4; i++){
                switch (i) {
                    case 1:
                        tipo = "GONDOLA";
                        break;
                    case 2:
                        tipo = "BALSA";
                        break;
                    case 3:
                        tipo = "NAVIO";
                        break;
                    case 4:
                        tipo = "CRUZEIRO";
                        break;
                }
            }
        try {
                separador();
                System.out.println("---- LISTA DE " + tipo + "S ----");
                executarAcaoNaFrotaDeListarEspecifico(lista, 32);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }            
        }else{
        try {
                separador();
                System.out.println("---- LISTA DE " + tipo + "S ----");
                executarAcaoNaFrotaDeListarEspecifico(lista, 32);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

private static Object receberString(String enunciado) {
    System.out.println("Digite " + enunciado + ":");
    System.out.println("(Digite 0 para voltar)");

    Object objeto = teclado.nextLine();

    if (normalizar((String) objeto).equals(normalizar("Voltar"))) {
        throw new IllegalArgumentException("Voltando ao menu anterior.");
    }

    return objeto;
}

    private static Object receberNumero(String enunciado) {
        Object numero = null;
        boolean teste = false;

        while (!teste) {
            System.out.println("Digite " + enunciado + ":");
            System.out.println("(Digite 0 para voltar)");

            Object objeto = teclado.nextLine();

            if (normalizar((String) objeto).equals(normalizar("Voltar"))) {
                throw new IllegalArgumentException("Voltando ao menu anterior.");
            } else {
                try {
                    // Tenta converter a entrada para Integer
                    numero = Integer.parseInt((String) objeto);
                    teste = true;
                } catch (NumberFormatException e) {
                    teclado.nextLine();
                    System.out.println("Por favor, insira um número válido.");
                }
            }
        }

        return numero;
    }
    
        private static Object receberDouble(String enunciado) {
            Object numero = null;
            boolean teste = false;
    
            while (!teste) {
                System.out.println("Digite " + enunciado + ":");
                System.out.println("(Digite 0 para voltar)");
    
                Object objeto = teclado.nextLine();
    
                if (normalizar((String) objeto).equals(normalizar("Voltar"))) {
                    throw new IllegalArgumentException("Voltando ao menu anterior.");
                } else {
                    try {
                        // Tenta converter a entrada para Double
                        numero = Double.parseDouble((String) objeto);
                        teste = true;
                    } catch (NumberFormatException e) {
                        teclado.nextLine();
                        System.out.println("Por favor, insira um número válido.");
                    }
                }
            }
    
            return numero;
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
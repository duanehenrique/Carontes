import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    static int ALMAS_EXTRAS = 30;
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
       boolean confirmacao = false;
        String teste;
        while(!confirmacao){
        System.out.println("Pressione Enter para continuar:");
         teste = teclado.nextLine();
        if(teste.trim().isEmpty())
        {
          confirmacao = true;
         }else{
        System.out.println("Resposta inválida");
        confirmacao = false;
        }
    }
}

    /**
     * Cria uma linha separadora no console.
     */
    private static void separador() {
        String linha = new String(new char[75]).replace('\0', '-');
        System.out.println(linha);
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
            System.out.println("   /  _ _     _ _ \\\\");
            System.out.println(" /   / \\\\   / \\\\ | \\\\");
            System.out.println(" |  \\\\_//  \\\\_/ |   \\\\");
            System.out.println("|__ | (||)  |_____/ ||");
            System.out.println(" \\\\ __ || __| _  /_||//");
            System.out.println("  |_|_|_|_|/|  _/___/");
            System.out.println("  |_|_|_|_|_  |");
            System.out.println("  |_________/");
            separador();
        }

            private static void desenharAlma() {
                System.out.println("                            (");
                System.out.println("                .            )        )");
                System.out.println("                         (  (|              .");
                System.out.println("                     )   )\\\\/ ( ( (");
                System.out.println("             *  (   ((  /     ))\\\\))  (  )    )");
                System.out.println("           (     \\\\   )\\\\(          |  ))( )  (|");
                System.out.println("           >)     ))/   |(      )| )/  \\\\((  ) \\\\");
                System.out.println("           (     (   ) .   \\\\    -.)    V )/   )(    (");
                System.out.println("            \\\\   /     .   \\\\       /    .  \\\\   \\\\))   ))");
                System.out.println("             )(    ,'))     \\\\ /    )(      \\\\( `.(   )(");
                System.out.println("          ( \\\\   | / \\\\____( \\\\   / )____/\\\\ | ( (");
                System.out.println("             \\\\.)  |(  |/      \\\\_ | _/       \\\\|  ))\\\\");
                System.out.println("              \\\\. |>  \\\\ _0_ | ___ |_0_/  <|  /");
                System.out.println("                \\\\ (|__  .__  _/::\\\\__  _. __|)/");
                System.out.println("                  )> :   /|_I_I_I_|_|_\\\\  :<(.");
                System.out.println("                 (  \\\\:   T\\\\ _|_|_|_|_|_/T  : ./");
                System.out.println("                    \\\\..`_ ._________. _' ../");
                separador();
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
                separador();
            }

    private static void exibirDia(){
        System.out.println("---- " + frota.getDataAtual() + " ----");
        System.out.println("------ Dia "+ frota.getDiaDoDesafio() + "------");
        separador();
    }

        private static void exibirAlmas(){
        System.out.println("---- Almas da frota do gerente " + jogador.getNomePersonagem() + " ----");
        System.out.println("---- Almas em Estoque:" + jogador.getAlmas() + " ----");
        System.out.println("---- Almas coletadas hoje: "+ jogador.getAlmasDeHoje() + "----");
        separador();
    }
        private static void exibirCustoTotalHoje(){
        System.out.println("Almas consumidas hoje: " + custos.getAlmasDeHoje());
        separador();
        }
        private static void exibirCustoTotalOntem(){
        System.out.println("Almas consumidas ontem: " + custos.getAlmasDeOntem());
        separador();
        }

        private static List<Rota> gerarRotas(){
        GeradorRotas rotaDisponivel = new GeradorRotas();
        List<Rota> rotas = rotaDisponivel.gerarRotas(frota);
        return rotas;
        }

        private static void listarRotasDisponiveis(List<Rota> rotasDisponiveis) {
                List<String> lista = new ArrayList<>();
                lista.add("\n--- Rotas Disponíveis para Escolha---\n");
                separador();
        
                for (Rota rota : rotasDisponiveis) {
                    lista.add(rota.relatorio());
                }
                imprimirLista(lista);
            }
        

    private static void encerrarDia(){
        boolean confirmacao = confirmacao();
        if(confirmacao){
        jogador.encerrarDia();
        frota.encerrarDia();
        custos.encerrarDia();
        }
    }

        private static void iniciarDia(){
        frota.iniciarDia();
        jogador.addAlmasAoDia(ALMAS_POR_DIA);
        System.out.println(frota.relatorioFrotaDeOntem());
    }


    private static void tutorial(){
        boolean confirmacao = false;
        while (!confirmacao) {
        exibirDia();
        System.out.println("Sua frota não é grande, mas tem potencial.");
        System.out.println("Esses são os seus barcos e seus Carontes disponíveis no momento:");
        desenharBarco();
        executarAcaoNaFrotaDeListarGeral(1);
        System.out.println("Os barcos são conduzidos por seus Carontes atrav;es de rotas para coletar almas humanas.");
        System.out.println("Carontes podem fazer até " + MAX_ROTAS_DIA + " viagens por dia.");
        System.out.println("Nenhum deles pode percorrer mais de " + MAX_ROTAS_DIA + " rotas em um mesmo dia");
        System.out.println("É algo do sindicato. Melhor não mexer nisso.");
        pausa();
        separador();
        System.out.println("No submundo, almas humanas são como ouro. Todo mundo quer um pouco.");
        System.out.println("Aqui, elas são a moeda de troca. Tudo tem um custo e vai te custar as almas que for coletando com sua frota.");
        System.out.println("Mas você mora aqui, então obviamente já sabe disso. Nem sei porque estou te falando isso.");
        System.out.println("Talvez alguém aqui ainda não saiba da importância das almas. Mesmo que, teoricamente, não tenha mais ninguém participando dessa conversa...");
        System.out.println("Bom, continuando..");
        separador();
        pausa();
        separador();
        desenharCaronte();
        System.out.println("Não é possível ter um barco na frota sem um Caronte e vice versa. Regras são regras.");
        System.out.println("Carontes têm nível de experiência. Alguns têm mais anos de firma.");
        System.out.println("Aqueles que começaram há pouco tempo não são muito prudentes e tem mais chance de cometer infrações.");
        System.out.println("Quanto mais experiente, menor a chance do guarda costeiro acabar aplicando uma multa.");
        System.out.println("E, mesmo que ele acabe fazendo um desleixo, provavelmente será algo leve.");
        System.out.println("Mas quanto mais experiente, maior será seu salário. Devemos respeitar o piso salarial.");
        System.out.println("Veja os Carontes empregados na sua frota:");
        executarAcaoNaFrotaDeListarGeral(3);
        System.out.println("No momento, todos os Carontes contratados são de nível 1.");
        System.out.println("Mas você pode sempre contratar outros quando for adquirir mais barcos. Claro, por um preço.");
        System.out.println("E, como um bom gerente, você pode pagar um bom curso de especialização para seus Carontes. Estamos sempre em uma jornada de crescimento!");    
        pausa();;
        System.out.println("Vale a pena contratar Carontes mais experientes? Bom, nós não queremos nossos Carontes recebendo multas.");
        System.out.println("Se o Caronte tiver muitas multas pendentes, ele não pode entrar em uma rota.");
        System.out.println("A legislação do Submundo é bem rígida quanto a isso");
        System.out.println("Multas custam. E isso sai do nosso bolso. Do que ganhamos no dia.");
        System.out.println("Veja como estão as carteiras de motorista dos Carontes empregados por você:");
        executarAcaoNaFrotaDeListarGeral(9);
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
        listarRotasDisponiveis(gerarRotas());
        System.out.println("As rotas de coleta têm um número definido de passageiros a serem recolhidos. Esse número pode variar bastante.");
        System.out.println("E pode até ultrapassar a capacidade máxima do barco. Nesse caso, o Caronte vai lotar seu barco e deixar alguns para trás.");        
        System.out.println("É uma pena desperdiçar tantas almas humanas assim, mas é como é.");
        pausa();
        System.out.println("Os passageiros a serem recolhidos nas rotas estão sendo levados por vários motivos.");
        System.out.println("Dependendo do motivo, sua alma pode valer mais ou menos.");
        System.out.println("Veja como podem ser as almas que seus Carontes vão coletar:");
        pausa();
        System.out.println("Se você der sorte, seus Carontes levarão apenas pecadores culpados de traição, o mais horrendo dos pecados.");
        System.out.println("Mas, se der azar, você pode ter que gastar horas preenchendo a papelada de heróis com feitos incríveis... Que não nos servem de nada.");
        System.out.println("Ninguém quer levar um herói para o inferno. É um desperdício de recursos, mas infelizmente é nosso trabalho também.");
        pausa();
        System.out.println("A cada dia que se passa, um gerente recebe " + ALMAS_POR_DIA + " almas por dia, para investir no que for preciso.");
        System.out.println("O chefe pode ser carruncudo, mas ele sabe que nem todo dia é fartura. Às vezes um leve empurrãozinho é do que sua frota precisa.");
        pausa();
        desenharBarco();
        executarAcaoNaFrotaDeListarGeral(6);
        System.out.println("Todo barco precisa de manutenção de tempos em tempos. E elas custam, então é bom se prevenir.");
        System.out.println("Cada tipo de barco precisa de ir para a oficina depois de uma certa quilometragem percorrida diferentes, mas todos precisam. É uma lei, pela nossa própria segurança.");
        System.out.println("No momento, todos os barcos da sua frots são 0 km. Novíssimos de fábrica! Então não precisam de manutenção... por enquanto.");
        System.out.println("Barcos que precisam de combustível tem um custo adicional de acordo com o tipo de combustível.");
        System.out.println("Cada tipo de combustível tem um custo para instalar o tanque. Os mecânicos do submundo são terceirizados e querem pagamento imediato.");
        System.out.println("Da mesma forma, cada combustível tem um custo diferente para abastecer. Os postos são traiçoeiros, mas ao menos têm preços tabelados.");
        pausa();
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
    }

private static void definirJogador(){
        boolean confirmacao = false;
        String nome = null;
        separador();
        desenharCaronte();
        System.out.println("Olá, gerente! Seja bem-vindo ao submundo!");
        System.out.println("Eu sou o novo chefe de Recursos Inumanos do Inferno e estou aqui para te ajudar.");
        System.out.println("Imagino que suas férias tenham sido ótimas. Mas agora é hora de voltarmos ao trabalho.");
        System.out.println("Muita coisa mudou desde que você saiu de férias.");
        System.out.println("Tem pouco tempo que trabalho aqui, então não conheço todos os gerentes. Qual é seu nome?");
        while(!confirmacao){
        System.out.println("Digite seu nome:");
        nome = teclado.nextLine();
        System.out.println("Seu nome é " + nome + "?");
        confirmacao = confirmacao();
        separador();
        if(!confirmacao){
        System.out.println("Acho que eu ouvi errado. Qual é seu nome?");
        }
    }
    jogador = new Jogador(nome);
        System.out.println("Muito bom te conhecer, " + jogador.getNomePersonagem());
        System.out.println("Agora que já está acomodado, deixe-me te explicar o que aconteceu.");
        separador();
        pausa();
        separador();
}

private static void esperarInicio() {
        boolean confirmacao = false;
        while(!confirmacao){
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
        confirmacao = confirmacao();
        if(!confirmacao){
            System.out.println("Então deixe-me repetir.");
        }
        }
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
                System.out.println("Passageiro #" + contagem + ": " + passageiro.relatorio());
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
        desenharAlma();
        double custoTotal = custos.executarTransacaoEspecifica(objetos, funcao, frota, jogador);
        System.out.println("Gasto da frota do gerente " + jogador.getNomePersonagem() + " com a transação: " + custoTotal + " almas.");
        separador();
        } catch (Exception e) {
         System.out.println(e.getMessage());
        }
    }

        public static void executarAcaoNaFrotaComAlmasGeral(int funcao) {
         try {
        desenharAlma();
        double custoTotal = custos.executarTransacaoGeral(funcao, frota, jogador);
        System.out.println("Gasto da frota do gerente " + jogador.getNomePersonagem() + " com a transação: " + custoTotal + " almas.");
        separador();
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Erro: " + e.getMessage() );
    }
    }

    
        public static void executarAcaoNaFrotaDeControle(List<Object> objetos, int funcao) {
        try {
        desenharCaronte();
        Executor executor = new Executor();
        List<String> mensagem = (List<String>) executor.executarAcaoNaFrotaDeControle(frota, funcao, objetos);   
        System.out.println(mensagem);
        separador();
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Erro: " + e.getMessage() );
    }
        }

        public static void executarAcaoNaFrotaDeListarEspecifico(List<Object> objetos, int funcao) {
        Executor executor = new Executor();
        String enunciado = null;
        switch (funcao) {
            case 1:
                enunciado = ("Relatório do Barco " + ((String) objetos.get(0)));
                break;
            case 2:
                enunciado = ("Relatório do Barco " + ((int) objetos.get(0)));
                break;
            case 3:
                enunciado = ("Relatório Completo do Barco " + ((String) objetos.get(0)));
                break;
            case 4:
                enunciado = ("Relatório Completo do Barco " + ((int) objetos.get(0)));
                break;
            case 5:
            switch(normalizar((String) objetos.get(0))){
                
                case "GONDOLA":
                    enunciado = "Gôndolas";
                    break;
                case "BALSA":
                    enunciado = "Balsa";
                    break;
                case "NAVIO":
                    enunciado = "Navio";
                    break;
                case "CRUZEIRO":
                    enunciado = "Cruzeiro";
                    break;
                    default:
                throw new IllegalArgumentException("Não há um registro de barcos " + objetos.get(0) + " nos catálogos do submundo.");            
            }
            case 6:
                enunciado = ("Rotas do Barco " + ((String) objetos.get(0)));
                break;
             case 7:
                enunciado = ("Rotas do Barco #" + ((int) objetos.get(0)));
                break;
        }
        List<String> mensagens = (List<String>) executor.executarAcaoNaFrotaDeListarEspecifico(frota, funcao, objetos);
        desenharBarco();
        System.out.println("\n--- " + enunciado + " da frota do gerente " + jogador.getNomePersonagem() + " ---");
        separador();
        imprimirLista(mensagens);
        }


         public static void executarAcaoNaFrotaDeListarGeral(int funcao) {
        Executor executor = new Executor();
        String enunciado = null;
        switch (funcao) {
            case 1:
                enunciado = "Barcos";
                break;
            case 2:
                enunciado = "Feitos Heroicos de Ontem dos Barcos";
                break;
                case 3:
                enunciado = "Carontes";
                break;
            case 4:
                enunciado = "Barcos potencialmente sem combustível";
                break;
            case 5:
                enunciado = "Carontes com salário pendente";
                break;
            case 6:
                enunciado = "Barcos com manutenção pendente";
                break;
             case 7:
                enunciado = "Barcos com rotas de coletas de alma";
                break;
             case 8:
                enunciado = "Barcos disponíveis para receber rotas";
                break;
             case 9:
                enunciado = "Barcos com Carontes multados";
                break;
            case 10:
                enunciado = "Barcos com Carontes com a carteira detida";
                break;
            default:
                throw new IllegalArgumentException("Operação inválida");            
        }
        desenharBarco();
        List<String> mensagens = (List<String>) executor.executarAcaoNaFrotaDeListarGeral(frota, funcao);
        System.out.println("\n--- " + enunciado + " da frota do gerente " + jogador.getNomePersonagem() + " ---");
        separador();
        imprimirLista(mensagens);
    }
        private static void imprimirLista(List<String> mensagens){
             for (String string : mensagens) {
            System.out.println(string);
            separador();

        }        }

    /**
     * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
     */

     private static void mostrarMenuPrincipal() {
        int opcao;
        do {
            try {
                executarAcaoNaFrotaDeListarGeral(1);
            } catch (Exception e) {
                System.out.println("O computador da Sala do Gerente travou de novo: " + e.getMessage());
            }
            exibirAlmas();
            exibirCustoTotalHoje();
            System.out.println("--- Sala do Gerente " + jogador.getNomePersonagem() + " ---");
            System.out.println("1. Oficina do Tártaro");
            System.out.println("2. Sala de Descanso de Carontes");
            System.out.println("3. Catálogo de Rotas de Coleta de Almas Penitentes");
            System.out.println("4. Setor de Aquisições do Submundo");
            System.out.println("5. Encerrar Dia");
            System.out.println("6. Percorrer Rotas"); // New option
            separador();
            System.out.print("Selecione a opção desejada:");
    
            opcao = menuEscolhaNumeros(1, 6);
            teclado.nextLine();
            try {
                switch (opcao) {
                    case 1:
                        try {
                            menuBarcos();
                        } catch (Exception e) {
                            System.out.println("Ocorreram problemas técnicos na Oficina do Tártaro: " + e.getMessage());
                            System.out.println("Por favor, tente novamente.");
                        }
                        break;
                    case 2:
                        try {
                            menuCarontes();
                        } catch (Exception e) {
                            System.out.println("Parece que ocorreu um problema Sala de Descanso de Carontes: " + e.getMessage());
                            System.out.println("Por favor, tente novamente.");
                        }
                        break;
                    case 3:
                        try {
                            menuRotas();
                        } catch (Exception e) {
                            System.out.println("O pessoal do design errou no Catálogo de Rotas : " + e.getMessage());
                            System.out.println("Por favor, tente novamente.");
                        }
                        break;
                    case 4:
                        try {
                            menuAquisicoes();
                        } catch (Exception e) {
                            System.out.println("A burocracia do Setor de Aquisições complicou o seu trabalho e não foi possível terminar sua solicitação: " + e.getMessage());
                            System.out.println("Por favor, tente novamente.");
                        }
                        break;
                    case 5:
                        try {
                            encerrarDia();
                        } catch (Exception e) {
                            System.out.println("Erro ao Encerrar o Dia: " + e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            menuPercorrerRotas(); // Call the new menu method
                        } catch (Exception e) {
                            System.out.println("Erro ao Percorrer Rotas: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro na opção " + opcao + ": " + e.getMessage());
            }
        } while (opcao != 5);
    }
    
    

    private static void menuBarcos() {
        int opcao;
        do {
            desenharBarco();
            System.out.println("\n--- Oficina do Tártaro ---");
            System.out.println("1. Listar Barcos por Tipo");
            System.out.println("2. Abastecer Barcos");
            System.out.println("3. Realizar Manutenção Em Barcos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada. ");
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
            desenharCaronte();
            System.out.println("\n--- Sala de Descanso de Carontes ---");
            executarAcaoNaFrotaDeListarGeral(3);
            System.out.println("1. Pagar Multa de Carontes");
            System.out.println("2. Pagar Curso de Especialização para Carontes");
            System.out.println("3. Pagar Salário em Atraso de Carontes");
            System.out.println("5. Trocar Carontes pelo Nome do Caronte");
            System.out.println("6. Trocar Carontes pelo Número do Barco do Caronte");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 5);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    menuMultas();
                    break;
                case 2:
                    menuCursos();
                    break;
                case 3:
                    menuSalarios();
                    break;
                case 4:
                    trocarCarontesNome();
                    break;
                case 5:
                    trocarCarontesIndex();
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
        List<Rota> rotasDisponíveis = gerarRotas();
        do {
            desenharAlma();
            System.out.println("\n--- Catálogo de Rotas de Coleta de Almas Penitentes ---");
            executarAcaoNaFrotaDeListarGeral(7);           
            System.out.println("1. Adicionar Rota ao Barco pelo Nome do Barco");
            System.out.println("2. Adicionar Rota ao Barco pelo Número do Barco");
            System.out.println("3. Excluir Rota ao Barco pelo Nome do Barco");
            System.out.println("4. Excluir Rota ao Barco pelo Número do Barco");
            System.out.println("5. Trocar Rotas de Barcos pelo Números dos Barcos");
            System.out.println("6. Trocar Rotas de Barcos pelos Números dos Barcos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 6);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                   rotasDisponíveis = adicionarRotaPorNome(rotasDisponíveis);
                    break;
                case 2:
                    rotasDisponíveis = adicionarRotaPorIndex(rotasDisponíveis);
                    break;
                case 3:
                    excluirRotaPorIndex();
                    break;
                case 4:
                    excluirRotaPorNome();
                    break;
                case 5:
                    trocarRotasPorNome();
                    break;
                case 6:
                    trocarRotasPorIndex();
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
            desenharBarco();
            System.out.println("\n--- Setor de Aquisições do Submundo ---");
            System.out.println("1. Fazer Pedido para Aquisição de Novo Barco");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Digite a opção desejada. ");

            opcao = menuEscolhaNumeros(0, 1);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                try{
                    menuComprarBarco();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuMultas() {
        int opcao;
        do {
            desenharBarco();
            System.out.println("\n--- Multas de Carontes ---");
            executarAcaoNaFrotaDeListarGeral(9);
            System.out.println("1. Pagar multa pendente de um Caronte pelo número do barco do caronte");
            System.out.println("2. Pagar multa pendente de um Caronte pelo nome do Caronte");
            System.out.println("3. Pagar todas as multas pendentes de um Caronte pelo número do barco do Caronte");
            System.out.println("4. Pagar todas as multas pendentes de um Caronte pelo nome do Caronte");
            System.out.println("5. Pagar todas as multas pendentes de todos os Carontes");
            System.out.println("6. Listar todos os Carontes com a carteira detida");
            System.out.println("0. Voltar ao Menu Carontes");
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 5);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    pagarMultaCaronteIndex();
                    break;
                case 2:
                    pagarMultaCaronteNome();
                    break;
                case 3:
                    pagarTodasMultasCaronteIndex();
                    break;
                case 4:
                    pagarTodasMultasCaronteNome();
                    break;
                case 5:
                    pagarTodasMultasTodosCarontes();
                    break;
                case 6:
                    executarAcaoNaFrotaDeListarGeral(10);
                case 0:
                    System.out.println("Voltando ao Menu Carontes.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private static void menuCursos() {
        int opcao;
        do {
            desenharCaronte();
            System.out.println("\n--- Pagar Curso de Especialização para Carontes ---");
            executarAcaoNaFrotaDeListarGeral(3);
            System.out.println("1. Pagar Curso para Caronte pelo número do Barco de Caronte");
            System.out.println("2. Pagar Curso para Caronte pelo Nome de Caronte");
            System.out.println("0. Voltar ao Menu Carontes");
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 2);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    pagarCursoPorIndex();
                    break;
                case 2:
                    pagarCursoPorNome();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Carontes.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private static void menuSalarios() {
        int opcao;
        do {
            desenharAlma();
            System.out.println("\n--- Pagar Salário em Atraso de Carontes ---");
            executarAcaoNaFrotaDeListarGeral(5);
            System.out.println("1. Pagar salário atrasado pelo número do barco de Caronte");
            System.out.println("2. Pagar salário atrasadospelo nome de Caronte");
            System.out.println("3. Pagar todos os salários atrasados");
            System.out.println("0. Voltar ao Menu Carontes");
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 3);
            teclado.nextLine(); // Limpar o buffer
    
            switch (opcao) {
                case 1:
                    pagarSalarioPorIndex();
                    break;
                case 2:
                    pagarSalarioPorNome();
                    break;
                 case 3:
                    pagarTodosSalarios();
                    break;                   
                case 0:
                    System.out.println("Voltando ao Menu Carontes.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
  
    private static List<Rota> adicionarRotaPorNome(List<Rota> rotasdisponíveis) {
        try {
            System.out.println("--- Adicionar Rota pelo Nome do Barco ---");
            listarRotasDisponiveis(rotasdisponíveis);
            executarAcaoNaFrotaDeListarGeral(8);
            // Recebe a entrada do usuário para o nome da Rota
            String nomeRota = (String) receberString("o nome do Barco");
            int indexRota = (int) receberNumero("o número da rota");

            if(indexRota > 0 && indexRota < rotasdisponíveis.size())
            {
            // Obtém a função correspondente
            int funcao = 2;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeRota);
            parametros.add(rotasdisponíveis.get(indexRota-1));
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaDeControle(parametros, funcao);
            rotasdisponíveis.remove(indexRota-1);
            }
            return rotasdisponíveis;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar Rota por nome: " + e.getMessage());
                    return rotasdisponíveis;

        }
    }
    
    private static List<Rota> adicionarRotaPorIndex(List<Rota> rotasdisponíveis) {
        try {
            System.out.println("--- Adicionar Rota pelo Nome do Barco ---");
            listarRotasDisponiveis(rotasdisponíveis);
            executarAcaoNaFrotaDeListarGeral(8);
            // Recebe a entrada do usuário para o nome da Rota
            int indexBarco = (int) receberNumero("o número do Barco");
            int indexRota = (int) receberNumero("o número da rota");

            if(indexRota > 0 && indexRota < rotasdisponíveis.size())
            {
            // Obtém a função correspondente
            int funcao = 3;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indexBarco);
            parametros.add(rotasdisponíveis.get(indexRota-1));
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaDeControle(parametros, funcao);
            rotasdisponíveis.remove(indexRota-1);
            }
            return rotasdisponíveis;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar Rota por nome: " + e.getMessage());
            return rotasdisponíveis;
        }
    }
    
    private static void excluirRotaPorIndex() {
        try {
            System.out.println("--- Excluir Rota por Índice ---");
            int indiceBarco = (int) receberNumero("o número do Barco");
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceBarco);
            executarAcaoNaFrotaDeListarEspecifico(parametros, 7);

            int funcao = 4;
    
            executarAcaoNaFrotaDeControle(parametros, funcao);
            } catch (Exception e) {
            System.out.println("Erro ao excluir Rota por índice: " + e.getMessage());
        }
    }
    
    private static void excluirRotaPorNome() {
        try {
            System.out.println("--- Excluir Rota por Nome ---");
    
            // Recebe a entrada do usuário para o nome da Rota
            String nomeRota = (String) receberString("o nome da Rota");
    
             List<Object> parametros = new ArrayList<>();
            parametros.add(nomeRota);
            executarAcaoNaFrotaDeListarEspecifico(parametros, 6);

            int funcao = 5;
    
            executarAcaoNaFrotaDeControle(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao excluir Rota por nome: " + e.getMessage());
        }
    }
    
    private static void trocarRotasPorNome() {
        try {
            System.out.println("--- Trocar Rotas entre Barcos pelos Nomes dos Barcos ---");
    
            // Recebe a entrada do usuário para o nome da Rota
            String nomeBarco1 = (String) receberString("o nome do primeiro Barco");
            String nomeBarco2 = (String) receberString("o nome do segundo Barco");
            List<Object> nome1 = new ArrayList<>();
            nome1.add(nomeBarco1);
            List<Object> nome2 = new ArrayList<>();
            nome2.add(nomeBarco2);
            int funcao = 6;
            executarAcaoNaFrotaDeListarEspecifico(nome1, funcao);
            executarAcaoNaFrotaDeListarEspecifico(nome2, funcao);
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nome1.get(0));
            parametros.add(nome2.get(0));
             int indiceRota1 = (int) receberNumero("o número da Rota desejada do primeiro Barco");
            parametros.add(indiceRota1);
            int indiceRota2 = (int) receberNumero("o número da Rota desejada do segundo Barco");
            parametros.add(indiceRota2);
            executarAcaoNaFrotaDeControle(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao trocar Rotas por nome: " + e.getMessage());
        }
    }
    
    private static void trocarRotasPorIndex() {
        try {
            System.out.println("--- Trocar Rotas entre Barcos pelos Nomes dos Barcos ---");
    
            // Recebe a entrada do usuário para o nome da Rota
            int indexBarco1 = (int) receberNumero("o número do primeiro Barco");
            int indexBarco2 = (int) receberNumero("o número do segundo Barco");
            List<Object> index1 = new ArrayList<>();
            index1.add(indexBarco1);
            List<Object> index2 = new ArrayList<>();
            index2.add(indexBarco2);
            int funcao = 6;
            executarAcaoNaFrotaDeListarEspecifico(index1, funcao);
            executarAcaoNaFrotaDeListarEspecifico(index2, funcao);
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(index1.get(0));
            parametros.add(index2.get(0));
             int indiceRota1 = (int) receberNumero("o número da Rota desejada do primeiro Barco");
            parametros.add(indiceRota1);
            int indiceRota2 = (int) receberNumero("o número da Rota desejada do segundo Barco");
            parametros.add(indiceRota2);
            executarAcaoNaFrotaDeControle(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao trocar Rotas por índice: " + e.getMessage());
        }
    }
    

    private static void pagarMultaCaronteIndex() {
        try {
            System.out.println("--- Pagar multa pendente de um Caronte pelo número do barco ---");
    
            // Recebe a entrada do usuário para o índice do Caronte
            int indiceCaronte = (int) receberNumero("o número do Caronte");
            int indiceMulta = (int) receberNumero("o número da multa Caronte");

            // Obtém a função correspondente
            int funcao = 8;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceCaronte);
            parametros.add(indiceMulta);

            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao pagar multa pendente de Caronte pelo número do barco: " + e.getMessage());
        }
    }
    
    private static void pagarMultaCaronteNome() {
        try {
            System.out.println("--- Pagar multa pendente de um Caronte pelo nome ---");
    
            // Recebe a entrada do usuário para o nome do Caronte
            String nomeCaronte = (String) receberString("o nome do Caronte");
            int indiceMulta = (int) receberNumero("o número da multa Caronte");
    
            // Obtém a função correspondente
            int funcao = 9;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeCaronte);
            parametros.add(indiceMulta);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar multa pendente de Caronte pelo nome: " + e.getMessage());
        }
    }
    
    private static void pagarTodasMultasCaronteIndex() {
        try {
            System.out.println("--- Pagar todas as multas pendentes de um Caronte pelo número do barco ---");
    
            // Recebe a entrada do usuário para o índice do Caronte
            int indiceCaronte = (int) receberNumero("o índice do Caronte");
    
            // Obtém a função correspondente
            int funcao = 9;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar todas as multas pendentes de Caronte pelo número do barco: " + e.getMessage());
        }
    }
    
    private static void pagarTodasMultasCaronteNome() {
        try {
            System.out.println("--- Pagar todas as multas pendentes de um Caronte pelo nome ---");
    
            // Recebe a entrada do usuário para o nome do Caronte
            String nomeCaronte = (String) receberString("o nome do Caronte");
    
            // Obtém a função correspondente
            int funcao = 10;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar todas as multas pendentes de Caronte pelo nome: " + e.getMessage());
        }
    }
    
    private static void pagarTodasMultasTodosCarontes() {
        try {
            System.out.println("--- Pagar todas as multas pendentes de todos os Carontes ---");
    
            int funcao = 4;
    
            executarAcaoNaFrotaComAlmasGeral(funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar todas as multas pendentes de todos os Carontes: " + e.getMessage());
        }
    }    
    
    private static void pagarCursoPorIndex() {
        try {
            System.out.println("--- Pagar Curso por Índice de Caronte ---");
    
            // Recebe a entrada do usuário para o índice do Caronte
            int indiceCaronte = (int) receberNumero("o índice do Caronte");
    
            // Obtém a função correspondente
            int funcao = 12;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar curso por índice de Caronte: " + e.getMessage());
        }
    }
    
    private static void pagarCursoPorNome() {
        try {
            System.out.println("--- Pagar Curso por Nome de Caronte ---");
    
            // Recebe a entrada do usuário para o nome do Caronte
            String nomeCaronte = (String) receberString("o nome do Caronte");
    
            // Obtém a função correspondente
            int funcao = 11;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar curso por nome de Caronte: " + e.getMessage());
        }
    }
    
    private static void pagarSalarioPorIndex() {
        try {
            System.out.println("--- Pagar salário atrasado pelo número do barco de Caronte ---");
    
            // Recebe a entrada do usuário para o índice do Caronte
            int indiceCaronte = (int) receberNumero("o índice do Caronte");
    
            // Obtém a função correspondente
            int funcao = 13;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar salário atrasado pelo número do barco de Caronte: " + e.getMessage());
        }
    }
    
    private static void pagarSalarioPorNome() {
        try {
            System.out.println("--- Pagar salário atrasado pelo nome de Caronte ---");
    
            // Recebe a entrada do usuário para o nome do Caronte
            String nomeCaronte = (String) receberString("o nome do Caronte");
    
            // Obtém a função correspondente
            int funcao = 14;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeCaronte);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar salário atrasado pelo nome de Caronte: " + e.getMessage());
        }
    }
    
    private static void pagarTodosSalarios() {
        try {
            System.out.println("--- Pagar todos os salários atrasados ---");
            int funcao = 1;
    
            executarAcaoNaFrotaComAlmasGeral(funcao);
            } catch (Exception e) {
            System.out.println("outo ao pagar todos os salários atrasados: " + e.getMessage());
        }
    }
    
    private static void trocarCarontesNome() {
        try {
            System.out.println("--- Trocar Carontes pelo Nome do Caronte ---");
    
            String nomeCaronte1 = (String) receberString("o nome do primeiro Caronte");
             String nomeCaronte2 = (String) receberString("o nome do segundo Caronte");
                int funcao = 6;
    
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeCaronte1);
            parametros.add(nomeCaronte2);
    
            executarAcaoNaFrotaDeControle(parametros, funcao);
            } catch (Exception e) {
            System.out.println("outo ao trocar Carontes pelo nome do Caronte: " + e.getMessage());
        }
    }
    
    private static void trocarCarontesIndex() {
        try {
            System.out.println("--- Trocar Carontes pelo Número do Barco do Caronte ---");
    
            // Recebe a entrada do usuário para o índice do Caronte
            int indiceCaronte1 = (int) receberNumero("o número do Barco do primeiro Caronte");
            int indiceCaronte2 = (int) receberNumero("o número do Barco do segundo Caronte");

            // Obtém a função correspondente
            int funcao = 7;
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceCaronte1);
            parametros.add(indiceCaronte2);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaDeControle(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao trocar Carontes pelo número do barco do Caronte: " + e.getMessage());
        }
    }
    

    private static void menuManutencao() {
        int opcao;
        do {
            desenharBarco();
            System.out.println("\n--- Manutenção de Barcos ---");
            separador();
            executarAcaoNaFrotaDeListarGeral(6);
            System.out.println("1. Fazer Manutenção em Barco por Índice");
            System.out.println("2. Fazer Manutenção em Barco por Nome");
            System.out.println("3. Fazer Manutenção em Todos os Barco");
            System.out.println("0. Voltar ao Menu de Barcos");
            separador();
            System.out.print("Digite a opção desejada. ");
            
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
    private static void menuAbastecer() {
        int opcao;
        do {
            desenharAlma();
            System.out.println("\n--- Abastecimento de Barcos ---");
            separador();
            executarAcaoNaFrotaDeListarGeral(4);
            System.out.println("1. Abastecer Barco por Índice");
            System.out.println("2. Abastecer Barco por Nome");
            System.out.println("3. Abastecer Barco Completo por Índice");
            System.out.println("4. Abastecer Barco Completo por Nome");
            System.out.println("5. Abastecer Todos os Barcos");
            System.out.println("0. Voltar ao Menu de Barcos");
            separador();
            System.out.print("Digite a opção desejada. ");
            
            opcao = menuEscolhaNumeros(0, 5);
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

        while (!confirmacao) {
            System.out.println("O barco " + barco.getNOME() + " é movido a " + barco.getTipoCombustivel());
            System.out.println("Deseja que o barco permaneça com esse tipo de combustível?");
            confirmacao = confirmacao();
            if(!confirmacao){

        List<BarcoComTanque> barcosParaCombustivel = new ArrayList<>();

        for (int i = 0; i <= 2; i++) { 
            BarcoComTanque barcoCopia;
            if (barco instanceof Balsa) {
                barcoCopia = new Balsa((Balsa) barco);
            } else if (barco instanceof Navio) {
                barcoCopia = new Navio((Navio) barco);
            } else if (barco instanceof Cruzeiro) {
                barcoCopia = new Cruzeiro((Cruzeiro) barco);
            } else {
                throw new IllegalArgumentException("Barco selecionado não existe.");
            }
            
        
            switch (i) {
                case 0:
                System.out.println(barcoCopia.getTipoCombustivel());
                barcoCopia.instalarTanque("Álcool");

                System.out.println(barcoCopia.getTipoCombustivel());

                    break;
                case 1:
                System.out.println(barcoCopia.getTipoCombustivel());
                barcoCopia.instalarTanque("Gasolina");

                System.out.println(barcoCopia.getTipoCombustivel());

                    break;
                case 2:
                System.out.println(barcoCopia.getTipoCombustivel());

                barcoCopia.instalarTanque("Diesel");

                System.out.println(barcoCopia.getTipoCombustivel());

                    break;
            }
            barcosParaCombustivel.add(barcoCopia);       

        }
            while(!confirmacao){
            for(int j = 1; j <= 3; j++){
            BarcoComTanque barcoTanque = barcosParaCombustivel.get(j - 1);
                System.out.println("Tanque #" + j + ":" + barcoTanque.getTipoCombustivel());
                System.out.println("Preço adicional pelo combustível " + barcoTanque.getTipoCombustivel() + ": " + barcoTanque.getAdicionalPrecoVenda() + " almas.");
                System.out.println("Preço do litro de " + barcoTanque.getTipoCombustivel() + ": " + barcoTanque.getAdicionalPrecoVenda() + " almas.");
        separador();
            }
            System.out.println("Qual tipo de combustível deseja que o barco utilize:");
            int escolha = menuEscolhaNumeros(1, 3);
            barco = barcosParaCombustivel.get(escolha - 1);

            confirmacao = confirmacao();
    }
            }
        }
       return (BarcoComTanque) barco;
    }

    private static Caronte menuContratarCaronte() {
        List<Caronte> carontesDisponiveis = new ArrayList<>();
        boolean confirmacao = false;
        GeradorNomesCarontes gerador = new GeradorNomesCarontes(nomesCarontes);

        Caronte caronteEscolhido = null;
        while (!confirmacao) {
        System.out.println("Lista do Setor de Aquisições de barcos disponíveis:");
            separador();
        for (int i = 1; i <= 4; i++) {
            Caronte caronte = new Caronte(gerador.gerarNome(), i, MAX_ROTAS_DIA);
            carontesDisponiveis.add(caronte);
    
            System.out.println("Caronte #" + i);
            System.out.println("Nome: " + caronte.getNome());
            System.out.println("Nivel de Experiência: " + caronte.getNivel());
            System.out.println("Salário por Dia: " + caronte.getSalario());
            System.out.println("Probabilidade de Cometer Infrações: " + caronte.getProbabilidade() + "\n");            System.out.println();
        }
            separador();
        System.out.println("Qual Caronte você deseja contratar?");
        int escolhaCaronte = menuEscolhaNumeros(1, 4);
        caronteEscolhido = carontesDisponiveis.get(escolhaCaronte-1);
        System.out.println("Você deseja contratar o Caronte ");  
        System.out.println("Nome: " + caronteEscolhido.getNome());
        System.out.println("Nivel de Experiência: " + caronteEscolhido.getNivel());
        System.out.println("Salário por Dia: " + caronteEscolhido.getSalario());
        System.out.println("Probabilidade de Cometer Infrações: " + caronteEscolhido.getProbabilidade() + "\n");   
       confirmacao = confirmacao();
        }

            return caronteEscolhido;
 
    }
    

    private static boolean confirmacao(){
        boolean confirmacao = false;
        String teste;
        teclado.nextLine();
        while(!confirmacao){
            System.out.println("Tem certeza?");
        System.out.println("Digite Sim ou Não:");
         teste = teclado.nextLine();
        if(normalizar(teste).equals(normalizar("Sim")))
        {
          return true;
        } else if(normalizar(teste).equals(normalizar("Não")))  {
                      return false;
        }else{
        System.out.println("Resposta inválida. Responda com Sim ou Não.");
        confirmacao = false;
        }
        }return false;
    }
   
    private static int menuEscolhaNumeros(int primeiro, int ultimo) {
        try {
            System.out.print("Selecione uma opção (Entre " + primeiro + "-" + ultimo + "): ");
            int escolha = teclado.nextInt();
    
            // Verificar se a escolha é válida
            while (escolha < primeiro || escolha > ultimo) {
                System.out.println("Não há uma opção referente a esse número. Tente novamente com números entre " + primeiro + "-" + ultimo + ".");
                pausa();
                System.out.print("Selecione uma opção (Entre " + primeiro + "-" + ultimo + "): ");
                escolha = teclado.nextInt();
            }
    
            return escolha;
        } catch (InputMismatchException e) {
            teclado.nextLine(); 
            System.out.println("Por favor, insira um número  entre " + primeiro + "-" + ultimo + ".");
            pausa();
            return menuEscolhaNumeros(primeiro, ultimo); 
        }
    }
    
    /**
     * Interage com o sistema para cadastrar um novo veículo na frota.
     * Solicita ao usuário informações como, placa, tipo de veículo
     * tipo de combustível, nome e CPF do motorista.
     * Valida as entradas e, se bem-sucedido, adiciona o
     * veículo à frota.
     */
    private static double menuComprarBarco() throws Exception {
        double custoDeCompra = 0;
        exibirAlmas();
        List<Caronte> motoristas = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
        Caronte motorista = null;
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
        System.err.println(barcosParaVenda.get(0).getPRECOCUSTO());


        boolean confirmacao = false;
                        separador();
        while(!confirmacao){
          System.out.println("O Setor de Aquisições dá as boas-vindas ao gerente " + jogador.getNomePersonagem() + "!");
          System.out.println("Lista do Setor de Aquisições de barcos disponíveis:");
                      separador();
        for (int i = 1; i <= barcosParaVenda.size(); i++) {
            Barco barco = barcosParaVenda.get(i - 1); 

            System.out.println("Barco #" + i);
            System.out.println("Nome: " + barco.getNOME());
            System.out.println("Tipo de Barco: " + barco.getTipoDeBarco());
            System.out.println("Capacidade máxima do barco: " + barco.getCAPACIDADEPASSAGEIROS() + "\n");        
            if (barco instanceof BarcoComTanque) {
              System.out.println("Capacidade máxima do tanque: " + ((BarcoComTanque)barco).getCapacidadeTanque() + "\n");                      
                System.out.println("Preço adicional pelo combustível " + ((BarcoComTanque) barco).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.out.println("Preço do litro de " + ((BarcoComTanque) barco).getTipoCombustivel() + ": " + ((BarcoComTanque) barco).getAdicionalPrecoVenda() + " almas.");
                System.out.println("Preço base do barco: " + barco.getPRECOCUSTO() + " almas.");
                System.out.println("Preço total do barco: " + ((BarcoComTanque) barco).getPrecoTotal() + " almas.");
            } else {
                System.out.println("Preço: " + String.valueOf(barco.getPRECOCUSTO()) + " almas.");
            }
            separador();
        }
          System.out.println("Qual barco você deseja incluir no seu pedido ao Setor de Aquisições:");
          System.out.println("(Digite 0 para desistir do pedido)");

           int escolha = menuEscolhaNumeros(0, 4);
           if(!(escolha == 0)){
            barcoEscolhido = barcosParaVenda.get(escolha - 1);
            System.out.println("Você selecionou o Barco #" + escolha);
            System.out.println("Nome: " + barcoEscolhido.getNOME());
            System.out.println("Tipo de Barco: " + barcoEscolhido.getTipoDeBarco());
            separador(); 
            System.out.println("Tem certeza de que deseja adquirir este barco?");
            confirmacao = confirmacao();

    if (barcoEscolhido instanceof BarcoComTanque) {
        try{
                barcoEscolhido = menuCombustivel((BarcoComTanque)  barcoEscolhido);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
              BarcoComTanque barcoComTanque = (BarcoComTanque) barcoEscolhido;
                System.out.println("Capacidade máxima do tanque: " + barcoComTanque.getCapacidadeTanque() + "\n");                      
                System.out.println("Preço adicional pelo combustível " + barcoComTanque.getTipoCombustivel() + ": " + barcoComTanque.getAdicionalPrecoVenda() + " almas.");
                System.out.println("Preço do litro de " + barcoComTanque.getTipoCombustivel() + ": " + barcoComTanque.getAdicionalPrecoVenda() + " almas.");
                System.out.println("Preço base do barco: " + barcoComTanque.getPRECOCUSTO() + " almas.");
                System.out.println("Preço total do barco: " + barcoComTanque.getPrecoTotal() + " almas.");            
            }else{
                System.out.println("Preço: " + barcoEscolhido.getPRECOCUSTO() + " almas.");
            }
            separador();
            System.out.println("Caronte a ser contratado: " + caronte.getNome());
            System.out.println("Nivel de Experiência: " + caronte.getNivel());
            System.out.println("Salário por Dia: " + caronte.getSalario());
            System.out.println("Probabilidade de Cometer Infrações: " + caronte.getProbabilidade() + "\n");   
            separador();
            exibirAlmas();
            System.out.println("Você tem certeza de que gostaria de fazer este pedido?");
            confirmacao = confirmacao();
            
        }else{
        throw new IllegalArgumentException("Você desistiu da compra. Talvez outro gerente tenha ficado com o barco, mas nunca se sabe. Se mudar de ideia, volte aqui.");
        }
        try{
        custoDeCompra = custos.comprarBarco(barcoEscolhido, jogador);
        if(custoDeCompra > 0){
        nomesCarontes.marcarNomeUtilizado(barcoEscolhido.getMotorista());
        nomesBarcos.marcarNomeUtilizado(barcoEscolhido);
        }
        return custoDeCompra;
        }catch (Exception e){
            throw e;
        }
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

    private static void abastecerBarcoPorIndex() {
        try {
            System.out.println("--- Abastecer Barco por Número ---");
    
            // Recebe a entrada do usuário para o índice do barco
            int indiceBarco = (int) receberNumero("o número do barco");
    
            double qtdCombustivel = (double) receberDouble("quantos litros de combustível deseja abastecer");
    
            int funcao = 1;  

            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceBarco);
            parametros.add(qtdCombustivel);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao abastecer barco por índice: " + e.getMessage());
        }
    }

    private static void abastecerBarcoPorNome() {
        try {
            System.out.println("--- Abastecer Barco por Nome ---");
    
            // Recebe a entrada do usuário para o nome do barco
            String nomeBarco = (String) receberString("o nome do barco");
    
            double qtdCombustivel = (double) receberDouble("quantos litros de combustível deseja abastecer");

            // Obtém a função correspondente
            int funcao = 2;  
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeBarco);
            parametros.add(qtdCombustivel);

            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao abastecer barco por nome: " + e.getMessage());
        }
    }

    

    private static void abastecerBarcoCompletoPorIndex() {
        try {
            System.out.println("--- Abastecer Barco Completo por Número ---");
    
            // Recebe a entrada do usuário para o índice do barco
            int indiceBarco = (int) receberNumero("o número do barco");
    
            int funcao = 3;  
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(indiceBarco);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao abastecer barco completo por índice: " + e.getMessage());
        }
    }

    private static void abastecerBarcoCompletoPorNome() {
        try {
            System.out.println("--- Abastecer Barco Completo por Nome ---");
    
            // Recebe a entrada do usuário para o nome do barco
            String nomeBarco = (String) receberString("o nome do barco");
    
            // Obtém a função correspondente
            int funcao = 4;  
    
            // Parâmetros necessários para a execução da ação
            List<Object> parametros = new ArrayList<>();
            parametros.add(nomeBarco);
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao abastecer barco completo por nome: " + e.getMessage());
        }
    }    
 
    private static void abastecerTodosBarcos() {
        try {
            System.out.println("--- Abastecer Todos os Barcos ---");
    
            // Obtém a função correspondente
            int funcao = 2;  
    
            // Chama o método correspondente na classe Executor
            executarAcaoNaFrotaComAlmasGeral(funcao);
    
        } catch (Exception e) {
            System.out.println("outo ao abastecer todos os barcos: " + e.getMessage());
        }
    }


private static void fazerManutencaoBarcoPorIndex() {
    try {
        System.out.println("--- Fazer Manutenção em Barco por número ---");
        separador();

        // Recebe a entrada do usuário para o índice do barco
        int indiceBarco = (int) receberNumero("o número do barco");

        // Obtém a função correspondente
        int funcao = 3;  

        // Parâmetros necessários para a execução da ação
        List<Object> parametros = new ArrayList<>();
        parametros.add(indiceBarco);
        // Chama o método correspondente na classe Executor
        executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);

    } catch (Exception e) {
        System.out.println("Erro ao fazer manutenção em barco por índice: " + e.getMessage());
    }
}

private static void fazerManutencaoPorNome() {
    try {
        System.out.println("--- Fazer Manutenção em Barco por Nome ---");
        separador();
        // Recebe a entrada do usuário para o nome do barco
        String nomeBarco = (String) receberString("o nome do barco");

        // Obtém a função correspondente
        int funcao = 6;  // Atualize conforme necessário

        // Parâmetros necessários para a execução da ação
        List<Object> parametros = new ArrayList<>();
        parametros.add(nomeBarco);

        // Chama o método correspondente na classe Executor
        executarAcaoNaFrotaComAlmasEspecifica(parametros, funcao);

    } catch (Exception e) {
        System.out.println("Erro ao fazer manutenção em barco por nome: " + e.getMessage());
    }
}

private static void fazerManutencaoTodosBarcos() {
    try {
        System.out.println("--- Fazer Manutenção em Todos os Barcos ---");
        separador();

        int funcao = 3;  

        List<Object> parametros = new ArrayList<>();

        executarAcaoNaFrotaComAlmasGeral(funcao);

    } catch (Exception e) {
        System.out.println("Erro ao fazer manutenção em todos os barcos: " + e.getMessage());
    }
}



    private static void listarBarcosPorTipo(){
        int funcao = 5;
        System.out.println("\n--- Listagem de Barcos por Tipo ---");

        System.out.println("Listar tipos de Barcos:");
        System.out.println("1. Gôndola");
        System.out.println("2. Balsa");
        System.out.println("3. Navio");
        System.out.println("4. Cruzeiro");
        System.out.println("5. Todos");        

        String tipo = (String) receberString("o tipo de barco desejado");
        List<Object> lista = new ArrayList<>();
        lista.add((tipo));
        if (tipo.equals("0")) {
            System.out.println("Voltando ao Menu de Barcos.");
            separador();
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
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }            
        }else{
        try {
                separador();
                executarAcaoNaFrotaDeListarEspecifico(lista, funcao);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}


private static boolean chegouUltimoDia(){
    return (frota.getDiaDoDesafio() < 12);
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
    
        private static void iniciar() {
            System.out.println("");
            separador();
        
            boolean encerrou = false;
        
            while (!encerrou) {
                try {
                    //definirJogador();
                    encerrou = true;
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Não entendi o que disse. Vamos recomeçar a nossa conversa sobre o que você perdeu.");
                }
            }
        
            encerrou = false;
        
            while (!encerrou) {
                try {
                    jogador = new Jogador("teste");
                    inicializarFrota();
                    encerrou = true;
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Não entendi o que disse. Vamos recomeçar a definir sua frota.");
                }
            }
        
            separador();
        
            encerrou = false;
        
            while (!encerrou) {
                try {
                    //esperarInicio();
                    encerrou = true;
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Não entendi o que disse. Vamos recomeçar a explicação sobre  o desejo do chefe.");
                }
            }
        
            encerrou = false;
        
            while (!encerrou) {
                try {
                    //tutorial();
                    encerrou = true;
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                    System.out.println("Não entendi o que disse. Vamos recomeçar nossa conversa sobre seu trabalho aqui.");
                }
            }
        }
        

            private static void dias12(){
                while (chegouUltimoDia()) {
                    iniciarDia();
                    try {
                        mostrarMenuPrincipal();
                        pausa();
                        limparTela();
                        executarAcaoNaFrotaDeListarGeral(2);
                        exibirCustoTotalOntem();
                    } catch (Exception e) {
                        System.out.println("O computador da sua Sala do Gerente travou de novo: " + e.getMessage() );
                        teclado.nextLine();
                        pausa();
                        limparTela();
                    }
                }
            }

            private static void dia13(){
                System.out.println("Olá, seja bem-vindo ao escritório. Eu sou o chefe do Recursos Inumanos. Lembra de mim?" );
                separador();
                boolean confirmacao = confirmacao();
                separador();
                if(confirmacao){
                System.out.println("Ótimo! Positividade comporativa! Beijos de gratiluz para você e sua boa memória." );
                }else{
                System.out.println("Pensei que lembraria dos nossos bons momentos do passado... Quando te perguntei seu nome 12 dias atrás..."); 
                System.out.println("Mas tudo bem. Vamos lá. Isso não é nada que vai me fazer chorar no banheiro em breve nem nada do tipo.");
                }
                System.out.println("Já é o último dia do desafio! O submundo está em pânico... Muito mais que o normal..." );
                System.out.println("O chefe está preocupado, então resolveu dar um empurrãozinho a todos os gerente de frota." );
                System.out.println("Todos os gerente vão ganhar " + ALMAS_EXTRAS + "almas para tentar bater a meta. Você incluso." );
                System.out.println("Nenhum outro gerente está perto de bater a meta. Sua frota tem grandes chances... Só não pode desistir");
                System.out.println("Vamos lá pegar ainda mais humanos para torturá-los por toda a eternidade?" );
                separador();
                confirmacao();
                jogador.addAlmasAoDia(ALMAS_EXTRAS);
                while (frota.getDiaDoDesafio() == 13) {
            try {
                mostrarMenuPrincipal();
                limparTela();
            } catch (Exception e) {
                System.out.println("O computador da sua Sala do Gerente travou de novo: " + e.getMessage() );
                teclado.nextLine();
                pausa();
                limparTela();
            }
            }
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
     * O loop continua até que o usuário opte por encoutar o programa.
     * Exceções de entrada são tratadas para evitar falhas devido a entradas
     * inválidas durante a
     * seleção do menu.
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        random = new Random();
        custos = new Custos();
        nomesCarontes = new GeradorNomesCarontes();
        nomesBarcos = new GeradorNomesBarcos();
        dias12();
        dia13();
        teclado.close();
    }
}
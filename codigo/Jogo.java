import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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
    private static String gerarPlacaUnica() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String placa;
        do {
            StringBuilder placaBuilder = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                placaBuilder.append(letras.charAt(random.nextInt(letras.length())));
            }
            for (int i = 0; i < 4; i++) {
                placaBuilder.append(random.nextInt(10));
            }
            placa = placaBuilder.toString();
        } while (placasGeradas.contains(placa)); // Continua até gerar uma placa única

        placasGeradas.add(normalizar(placa)); // Adiciona a nova placa ao conjunto para rastreamento
        return placa;
    }


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
                
    private static void tutorial(){
        exibirDia();
        System.err.println("Sua frota não é grande, mas tem potencial.");
        System.err.println("Esses são os seus barcos e seus Carontes disponíveis:");
        exibirRelatorioFrota();
        System.err.println("Carontes podem fazer até " + MAX_ROTAS_DIA + " viagens por dia.");
        System.err.println("Nenhum deles pode percorrer mais de " + MAX_ROTAS_DIA + " rotas em um mesmo dia");
        System.err.println("É algo do sindicato. Melhor não mexer nisso.");
        esperarEnter();
        exibirTodasMultas();
        exibirRotas();
        System.err.println("Carontes têm nível de experiência. Alguns têm mais anos de firma.");
        System.err.println("Aqueles que começaram há pouco tempo não são muito prudentes e tem mais chance de cometer infrações.");
        System.err.println("Quanto mais experiente, menor a chance do guarda costeiro acabar aplicando uma multa.");
        System.err.println("Nós não queremos multas. Se o Caronte tiver uma multa pendente, ele não pode entrar em uma rota.");
        System.err.println("A legislação do Submundo é bem rígida quanto a isso");
        System.err.println("Multas custam. E isso sai do nosso bolso. Do que ganhamos no dia.");
    



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
        esperarEnter();
        }

        private static void esperarEnter(){
            String enter;
            System.err.println("Pressione Enter para começar.");
            while(!enter.equals("\n")){
            teclado.next();
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
            Barco Gondola = gerarGondola(motorista); 
        }
    }

    /**
     * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
     */
    private static void mostrarMenu() {
        exibirDia();
        exibirAlmas();
        System.out.println("\t\t  Sistema de Gerenciamento de Frotas");
        separador();
        System.out.println("1. Cadastrar novo veículo na frota");
        System.out.println("2. Exibir relatório completo da frota");
        System.out.println("3. Registrar rota para veículo");
        System.out.println("4. Abastecer veículo");
        System.out.println("5. Registrar multa para motorista");
        System.out.println("6. Pagar multa do motorista");
        System.out.println("7. Verificar necessidade de manutenção dos veículos");
        System.out.println("8. Listar rotas não percorridas de um veículo");
        System.out.println("9. Percorrer rota específica de um veículo");
        System.out.println("10. Realizar manutenção de um veículo");
        System.out.println("11. Exibir relatório de rotas de um veículo");
        System.out.println("12. Encerrar o programa");
        separador();
        System.out.print("Selecione uma opção: ");
    }

    /**
     * Interage com o sistema para cadastrar um novo veículo na frota.
     * Solicita ao usuário informações como, placa, tipo de veículo
     * tipo de combustível, nome e CPF do motorista.
     * Valida as entradas e, se bem-sucedido, adiciona o
     * veículo à frota.
     */
    private static Barco cadastrarVeiculo(Scanner teclado) {
        // Solicita ao usuário que digite o tipo de veículo
        String tipoVeiculo;
        boolean veiculoValido;
        do{
            System.out.print("Digite o tipo de veículo (Carro, Van, Furgao, Caminhao):");
        tipoVeiculo = normalizar(teclado.next());
        teclado.nextLine();
        veiculoValido = ((tipoVeiculo.equals("CARRO")) || (tipoVeiculo.equals("VAN")) || (tipoVeiculo.equals("FURGAO"))|| (tipoVeiculo.equals("CAMINHAO")));
        if(!veiculoValido){
            System.err.println("Tipo de veículo inválido. Tente novamente com alguma das opções disponíveis.");
        }    
        }
        while(!veiculoValido);
        

        // Solicita ao usuário que digite o nome do motorista
        System.out.print("Digite o nome do motorista:");
        String nomeMotorista = teclado.next();
        teclado.nextLine();

        // Solicita ao usuário que digite o CPF do motorista
        System.out.print("Digite o CPF do motorista:");
        String cpfMotorista = teclado.next();
        teclado.nextLine();

        // Cria o motorista
        Caronte motorista = new Caronte(nomeMotorista, cpfMotorista);

        // Solicita ao usuário que digite a placa do veículo
        System.out.print("Digite a placa do veículo:");
        String placa = normalizar(teclado.next());
        teclado.nextLine();

        // Solicita ao usuário que digite o tipo de combustível
        System.out.print("Digite o tipo de combustível (Alcool, Gasolina, Diesel):");
        String tipoCombustivel = normalizar(teclado.next());
        teclado.nextLine();

        // Separa com uma linha
        separador();

        // Calcula o custo de manutenção com base no tipo de veículo
        double custoManutencao = calcularCustoManutencao(tipoVeiculo);

        // Cria o veículo com base no tipo
        Barco veiculo = criarVeiculo(tipoVeiculo, motorista, placa, tipoCombustivel, custoManutencao);

        // Adiciona o veículo à frota se ele foi criado com sucesso
        if (veiculo != null) {
            frota.adicionarVeiculo(veiculo);
            System.out.println("Veículo do tipo " + tipoVeiculo + " cadastrado com sucesso! Placa: " + placa);
        } else {
            System.out.println("Não foi possível cadastrar o veículo.");
        }
        return veiculo;
    }

    /**
     * Cadastra um veículo automaticamente com informações aleatórias.
     * 
     * @param placa A placa única do veículo a ser cadastrado.
     * @return O veículo cadastrado ou null se não for possível criar o veículo.
     */
    private static Barco gerarBarcoComTanque(Caronte motorista, String tipo) {    
        String nomeBarco = nomesBarcos.gerarNome();
                return barco;
        }
    }
    
     private static Barco gerarGondola(Caronte motorista) {    
        Gondola gondola = new Gondola(motorista, nomesBarcos.gerarNome(), MAX_ROTAS_DIA);
        return gondola;
    }
    

    private static double calcularCustoManutencao(String tipoVeiculo) {
        // Usando um HashMap para determinar o custo de manutenção
        HashMap<String, Double> custosManutencao = new HashMap<>();
        custosManutencao.put("CARRO", 500.0);
        custosManutencao.put("VAN", 750.0);
        custosManutencao.put("FURGAO", 1000.0);
        custosManutencao.put("CAMINHAO", 1500.0);

        return custosManutencao.getOrDefault(normalizar(tipoVeiculo), 0.0);
    }

    private static Barco criarVeiculo(String tipoVeiculo, Caronte motorista, String placa, String tipoCombustivel,
            double custoManutencao) {
        switch (normalizar(tipoVeiculo)) {
            case "CARRO":
                return new Carro(motorista, normalizar(placa), normalizar(tipoCombustivel), custoManutencao);
            case "VAN":
                return new Balsa(motorista, normalizar(placa), normalizar(tipoCombustivel), custoManutencao);
            case "FURGAO":
                return new Navio(motorista, normalizar(placa), normalizar(tipoCombustivel), custoManutencao);
            case "CAMINHAO":
                return new Cruzeiro(motorista, normalizar(placa), normalizar(tipoCombustivel), custoManutencao);
            default:
                System.out.println("Tipo de veículo desconhecido.");
                return null;
        }
    }

    private static void exibirDia(){
        separador();;
        System.err.println("---- " + frota.getDataAtual() + " ----");
        System.err.println("------ Dia "+ frota.getDiaDoDesafio() + "------");
        separador();
    }

        private static void exibirAlmas(){
        separador();;
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
        separador();
        System.out.println(frota.relatorioFrota());
        separador();
    }

    private static void exibirTodasMultas() {
        separador();
        System.out.println(frota.relatorioTodasMultas());
        separador();
    }

    /**
     * Permite ao sistema registrar uma rota para um veículo específico.
     * Gera aleatoriamente a placa do veículo e a quilometragem da rota a ser
     * registrada.
     */
     private static void registrarRota() {
        // Solicita a placa e tenta registrar a rota, capturando exceções de entrada
        // inválida
         
        try {
            System.out.print("Digite a placa do veículo para a rota: ");
            String placaRota = normalizar(teclado.next());
            Barco veiculoRota = frota.localizarVeiculo(placaRota);
            // Verifica se o veículo foi encontrado
            teclado.nextLine();
            if (veiculoRota != null) {
                System.out.print("Digite a quilometragem da rota: ");
                double kmRota = teclado.nextDouble();
                // teclado.nextLine(); // Limpa o buffer do teclado
                Rota rota = new Rota(LocalDate.now(), kmRota);
                // Tenta adicionar a rota ao veículo
                veiculoRota.addRota(rota);
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número válido para a quilometragem.");
            teclado.nextLine();
        }
        
    }
    

    /**
     * Permite ao sistema abastecer um veículo específico da frota.
     * Gera aleatoriamente a placa do veículo e a quantidade de combustível para
     * abastecimento.
     */
    private static void abastecerVeiculo(Scanner teclado) {
        System.out.print("Digite a placa do veículo para abastecer: ");
        String placaAbastecer = normalizar(teclado.next());
        teclado.nextLine();

        Barco veiculoAbastecer = frota.localizarVeiculo(placaAbastecer);

        if (veiculoAbastecer != null) {
            System.out.println("Digite a quantidade de combustível para abastecer (em litros): ");
            double litros = teclado.nextDouble();
            teclado.nextLine(); // Limpa o scanner após ler um número

            double litrosAbastecidos = veiculoAbastecer.abastecer(litros);
            System.out.println("Veículo de placa " + placaAbastecer + " abastecido com " + litrosAbastecidos + " litros.");
            System.out.println("Capacidade atual: " + veiculoAbastecer.getTanque().getCapacidadeAtual() + " litros.");
            System.out.println("Capacidade máxima: " + veiculoAbastecer.getTanque().getCapacidadeMaxima() + " litros.");

        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Permite ao sistema registrar uma multa para um motorista específico.
     * Gera aleatoriamente a placa do veículo e o tipo de multa para registro.
     */
    private static void registrarMulta(Scanner teclado) {
        System.out.print("Digite a placa do veículo que recebeu a multa: ");
        String placa = normalizar(teclado.next());
        teclado.nextLine();

        Barco veiculo = frota.localizarVeiculo(placa);

        if (veiculo != null) {
            System.out.println("Selecione o tipo de multa:");
            System.out.println("1. Leve");
            System.out.println("2. Média");
            System.out.println("3. Grave");
            System.out.println("4. Gravíssima");
            int escolhaMulta = teclado.nextInt();
            teclado.nextLine();; // Limpa o buffer do Scanner

            String tipoMulta = normalizar(convertEscolhaParaGravidade(escolhaMulta));
            if (!tipoMulta.isEmpty()) {
                veiculo.addMultaAoMotorista(tipoMulta);
                System.out
                        .println("Multa do tipo " + tipoMulta + " registrada com sucesso no veículo de placa " + normalizar(placa));
            } else {
                System.out.println("Tipo de multa inválido.");
            }
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    private static String convertEscolhaParaGravidade(int escolha) {
        switch (escolha) {
            case 1:
                return "LEVE";
            case 2:
                return "MEDIA";
            case 3:
                return "GRAVE";
            case 4:
                return "GRAVISSIMA";
            default:
                return "";
        }
    }

    /**
     * Permite ao sistema processar o pagamento de uma multa de um motorista
     * específico.
     * Solicita ao usuário o CPF do motorista e realiza o pagamento da multa
     * associada.
     */
    private static void pagarMulta(Scanner teclado) {
        System.out.print("Digite o CPF do motorista para pagar a multa: ");
        String cpf = teclado.next();

        for (Barco veiculo : frota.getVeiculos()) {
            Caronte motorista = veiculo.getMotorista();
            if (motorista.getCpf().equals(cpf)) {
                boolean sucesso = veiculo.pagarTodasMultas();
                if (sucesso) {
                    System.out.println("Multa paga com sucesso para o motorista com CPF: " + cpf);
                } else {
                    System.out.println("Não foi possível pagar a multa. O motorista não tem multas ou já estão pagas.");
                }
                return;
            }
        }

        System.out.println("Motorista com CPF " + cpf + " não encontrado.");
    }

    /**
     * Verifica o estado de manutenção de todos os veículos da frota.
     * Percorre os veículos e relata se algum precisa de manutenção.
     */
    private static void verificarManutencaoVeiculos() {
        try {
            boolean manutencaoNecessaria = false;
            // Itera sobre cada veículo na frota
            for (Barco veiculo : frota.getVeiculos()) {
                if (veiculo != null) {
                    // Obtém o objeto de manutenção do veículo
                    Manutencao manutencao = veiculo.getManutencao();
                    // Verifica se a manutenção está em dia
                    if (!manutencao.getManutencaoEmDia()) {
                        System.out.println("Veículo de placa " + veiculo.getPlaca() + " precisa de manutenção.");
                        manutencaoNecessaria = true;
                    }
                }
            }
            // Se nenhum veículo precisa de manutenção, informa que todos estão em dia
            if (!manutencaoNecessaria) {
                System.out.println("Todos os veículos estão com a manutenção em dia.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao verificar a manutenção dos veículos: " + e.getMessage());
        }
    }
    

    /**
     * Lista as rotas não percorridas de um veículo específico.
     * Solicita ao usuário a placa do veículo e exibe as rotas disponíveis que ainda
     * não foram percorridas.
     */
    private static void listarRotasNaoPercorridas(Scanner teclado) {
        System.out.print("Digite a placa do veículo para listar rotas não percorridas: ");
        String placa = normalizar(teclado.next());
        teclado.nextLine();

        Barco veiculo = frota.localizarVeiculo(normalizar(placa));

        if (veiculo != null) {
            try {
                veiculo.listarRotasNaoPercorridas();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Permite ao usuário selecionar e percorrer uma rota específica de um veículo.
     * Solicita a placa do veículo e o número da rota a ser percorrida.
     */
    private static void percorrerRotaEspecifica(Scanner teclado) {
        System.out.print("Digite a placa do veículo para percorrer uma rota: ");
        String placa = normalizar(teclado.next());
        teclado.nextLine();

        Barco veiculo = frota.localizarVeiculo(placa);

        if (veiculo != null) {
            if(veiculo.getQuantRotas() == 0){
                System.out.println("Não existe uma rota cadastrada nesse veiculo");
            }else{
                veiculo.listarRotasNaoPercorridas(); // Lista as rotas não percorridas
                System.out.print("Escolha o número da rota para percorrer: ");
                int numeroRota = teclado.nextInt();
                teclado.nextLine();
                try {
                    veiculo.percorrerRotaPorLista(numeroRota);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }  
            }
        }
        
        else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Permite ao usuário realizar a manutenção de um veículo específico.
     * Solicita a placa do veículo e executa o procedimento de manutenção, se
     * necessário.
     */
    private static void realizarManutencaoVeiculo(Scanner teclado) {
        System.out.println("Digite a placa do veículo para realizar manutenção:");
        String placa = normalizar(teclado.next());
        teclado.nextLine();

        boolean veiculoEncontrado = false;

        for (Barco veiculo : frota.getVeiculos()) {
            if (veiculo != null && veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoEncontrado = true;
                
                    veiculo.fazerManutencao();   
               
            }
        }

        if (!veiculoEncontrado) {
            System.out.println("Veículo com placa " + normalizar(placa) + " não encontrado.");
        }
    }

    /**
     * Exibe um relatório detalhado das rotas percorridas por um veículo específico.
     * Solicita ao usuário a placa do veículo e exibe o relatório das rotas.
     */
    private static void exibirRelatorioRotasVeiculo() {
        frota.exibirRelatorioRotas();
    }

        /**
        * Normaliza a string fornecida, removendo acentos e cedilha, e transformando
        * todos os caracteres para caixa alta.
        * 
        * @param input A string a ser normalizada.
        * @return A string normalizada.
        */
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
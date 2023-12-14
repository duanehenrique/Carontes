import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
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
 * e calcular despesas totais dos veículos.
 */
public class App {
    static Random random;
    static Frota frota;

    // private static int gerarOpcaoAleatoria() {
    // return random.nextInt(8) + 1; // Retorna um número entre 1 e 8 para as opções
    // do menu.
    // }

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
        String linha = new String(new char[70]).replace('\0', '-');
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

        placa = placa.toUpperCase();
        placasGeradas.add(placa); // Adiciona a nova placa ao conjunto para rastreamento
        return placa;
    }

    /**
     * Inicializa a frota de veículos com base em valores aleatórios gerados pelo
     * sistema.
     * Cria uma nova Frota com um tamanho aleatório entre 1 e 5 veículos e os
     * abastece com uma quantidade inicial de combustível.
     */
    private static void inicializarFrota() {
        int capacidadeTotalFrota = 10; // Define a capacidade total da frota
        frota = new Frota(capacidadeTotalFrota);

        if (frota == null) {
            System.out.println("Erro: 'frota' não foi inicializada corretamente.");
        }
        if (random == null) {
            System.out.println("Erro: 'random' não foi inicializado.");
        }

        // Inicializa metade da capacidade da frota
        int veiculosParaInicializar = capacidadeTotalFrota / 2;
        for (int i = 0; i < veiculosParaInicializar; i++) {
            String placaUnica = gerarPlacaUnica(); // Gera uma placa única para cada veículo
            Veiculo veiculo = cadastrarVeiculoAutomatico(placaUnica); // Passa a placa única para o método de cadastro
            double quantidadeInicial = 10 + random.nextInt(7); // Gera uma quantidade inicial de combustível aleatória
            veiculo.abastecer(quantidadeInicial); // Abastece o veículo com a quantidade inicial
        }
        System.out.println("Frota inicializada com " + veiculosParaInicializar + " de " + capacidadeTotalFrota
                + " veículos possíveis.");
    }

    /**
     * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
     */
    private static void mostrarMenu() {
        separador();
        System.out.println("\t\t  Sistema de Gerenciamento de Frotas");
        separador();
        System.out.println("1. Cadastrar novo veículo na frota");
        System.out.println("2. Exibir relatório completo da frota");
        System.out.println("3. Registrar rota para veículo");
        System.out.println("4. Abastecer veículo");
        System.out.println("5. Registrar multa para motorista");
        System.out.println("6. Pagar multas");
        System.out.println("7. Verificar necessidade de manutenção dos veículos");
        System.out.println("8. Calcular despesas totais de um veículo");
        System.out.println("9. Listar rotas não percorridas de um veículo");
        System.out.println("10. Percorrer rota específica de um veículo");
        System.out.println("11. Realizar manutenção de um veículo");
        System.out.println("12. Exibir relatório de rotas de um veículo");
        System.out.println("13. Listar todos os veículos da frota");
        System.out.println("14. Encerrar o programa");
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
    private static Veiculo cadastrarVeiculo(Scanner teclado) {
        // Solicita ao usuário que digite o tipo de veículo
        System.out.print("Digite o tipo de veículo (Carro, Van, Furgao, Caminhao):");
        String tipoVeiculo = teclado.next().toUpperCase();

        // Solicita ao usuário que digite o nome do motorista
        System.out.print("Digite o nome do motorista:");
        String nomeMotorista = teclado.next();

        // Solicita ao usuário que digite o CPF do motorista
        System.out.print("Digite o CPF do motorista:");
        String cpfMotorista = teclado.next();

        // Cria o motorista
        Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

        // Solicita ao usuário que digite a placa do veículo
        System.out.print("Digite a placa do veículo:");
        String placa = teclado.next();

        // Solicita ao usuário que digite o tipo de combustível
        System.out.print("Digite o tipo de combustível (Alcool, Gasolina, Diesel):");
        String tipoCombustivel = teclado.next();

        // Separa com uma linha
        separador();

        // Calcula o custo de manutenção com base no tipo de veículo
        double custoManutencao = calcularCustoManutencao(tipoVeiculo);

        // Cria o veículo com base no tipo
        Veiculo veiculo = criarVeiculo(tipoVeiculo, motorista, placa, tipoCombustivel, custoManutencao);

        // Adiciona o veículo à frota se ele foi criado com sucesso
        if (veiculo != null) {
            frota.adicionarVeiculo(veiculo);
            System.out.println("Veículo do tipo " + tipoVeiculo + " cadastrado com sucesso! Placa: " + placa);
        } else {
            System.out.println("Não foi possível cadastrar o veículo.");
        }
        return veiculo;
    }

    // Array de nomes que podem ser usados para gerar um nome de motorista
    // aleatório.
    private static final String[] NOMES = {
            "João", "Maria", "José", "Ana", "Pedro", "Sandra", "Carlos", "Laura",
            "Fernando", "Isabela", "Antônio", "Francisco", "Paula", "Patrícia", "Marcos", "Rafael"
    };

    /**
     * Cadastra um veículo automaticamente com informações aleatórias.
     * 
     * @param placa A placa única do veículo a ser cadastrado.
     * @return O veículo cadastrado ou null se não for possível criar o veículo.
     */
    private static Veiculo cadastrarVeiculoAutomatico(String placa) {
        random = new Random();

        // Gera um nome de motorista aleatório a partir do array de nomes.
        String nomeMotorista = NOMES[random.nextInt(NOMES.length)];
        // Gera um CPF aleatório.
        String cpfMotorista = String.format("%011d", random.nextInt(1_000_000_000));

        // Cria o motorista.
        Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

        // Gera um tipo de veículo aleatório.
        String[] tiposVeiculo = { "Carro", "Van", "Furgao", "Caminhao" };
        String tipoVeiculo = tiposVeiculo[random.nextInt(tiposVeiculo.length)].toUpperCase();

        // Gera um tipo de combustível aleatório.
        String[] tiposCombustivel = { "alcool", "gasolina", "diesel" };
        String tipoCombustivel = tiposCombustivel[random.nextInt(tiposCombustivel.length)].toUpperCase();

        // Calcula o custo de manutenção com base no tipo de veículo.
        double custoManutencao = calcularCustoManutencao(tipoVeiculo);

        // Cria o veículo com base no tipo.
        Veiculo veiculo = criarVeiculo(tipoVeiculo, motorista, placa, tipoCombustivel, custoManutencao);

        // Adiciona o veículo à frota se ele foi criado com sucesso.
        if (veiculo != null) {
            frota.adicionarVeiculo(veiculo);
            System.out.println("Veículo do tipo " + tipoVeiculo + " cadastrado com sucesso! Placa: " + placa);
        } else {
            System.out.println("Não foi possível cadastrar o veículo com a placa " + placa + ".");
        }

        return veiculo;
    }

    private static double calcularCustoManutencao(String tipoVeiculo) {
        // Usando um HashMap para determinar o custo de manutenção
        HashMap<String, Double> custosManutencao = new HashMap<>();
        custosManutencao.put("Carro", 500.0);
        custosManutencao.put("Van", 750.0);
        custosManutencao.put("Furgao", 1000.0);
        custosManutencao.put("Caminhao", 1500.0);

        return custosManutencao.getOrDefault(tipoVeiculo, 0.0);
    }

    private static Veiculo criarVeiculo(String tipoVeiculo, Motorista motorista, String placa, String tipoCombustivel,
            double custoManutencao) {
        switch (tipoVeiculo) {
            case "CARRO":
                return new Carro(motorista, placa, tipoCombustivel, custoManutencao);
            case "VAN":
                return new Van(motorista, placa, tipoCombustivel, custoManutencao);
            case "FURGAO":
                return new Furgao(motorista, placa, tipoCombustivel, custoManutencao);
            case "CAMINHAO":
                return new Caminhao(motorista, placa, tipoCombustivel, custoManutencao);
            default:
                System.out.println("Tipo de veículo desconhecido.");
                return null;
        }
    }

    /**
     * Exibe um relatório detalhado da frota, incluindo informações de cada veículo
     * cadastrado.
     */
    private static void exibirRelatorioFrota() {
        System.out.println(frota.relatorioFrota());
    }

    /**
     * Permite ao sistema registrar uma rota para um veículo específico.
     * Gera aleatoriamente a placa do veículo e a quilometragem da rota a ser
     * registrada.
     */
    private static void registrarRota(Scanner teclado) {
        try {
            System.out.print("Digite a placa do veículo para a rota: ");
            String placaRota = teclado.next().toUpperCase();
    
            Veiculo veiculoRota = frota.localizarVeiculo(placaRota);
            if (veiculoRota != null) {
                System.out.print("Digite a quilometragem da rota: ");
                int kmRota = teclado.nextInt(); // Lê a quilometragem como um Int 
    
                Rota rota = new Rota(LocalDate.now(), kmRota);
                veiculoRota.addRota(rota);
                System.out.println("Rota registrada com sucesso!");
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número válido para a quilometragem.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }
    

    /**
     * Permite ao sistema abastecer um veículo específico da frota.
     * Gera aleatoriamente a placa do veículo e a quantidade de combustível para
     * abastecimento.
     */
    private static void abastecerVeiculo(Scanner teclado) {
        System.out.print("Digite a placa do veículo para abastecer: ");
        String placaAbastecer = teclado.next().toUpperCase();
        Veiculo veiculoAbastecer = frota.localizarVeiculo(placaAbastecer);

        if (veiculoAbastecer != null) {
            System.out.print("Digite a quantidade de combustível para abastecer (em litros): ");
            double litros = teclado.nextDouble();
            teclado.nextLine(); // Limpa o buffer após ler um número

            double litrosAbastecidos = veiculoAbastecer.abastecer(litros);
            System.out.println(
                    "Veículo de placa " + placaAbastecer + " abastecido com " + litrosAbastecidos + " litros.");
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
        String placa = teclado.next().toUpperCase();
        Veiculo veiculo = frota.localizarVeiculo(placa);

        if (veiculo != null) {
            System.out.println("Selecione o tipo de multa:");
            System.out.println("1. Leve");
            System.out.println("2. Média");
            System.out.println("3. Grave");
            System.out.println("4. Gravíssima");
            int escolhaMulta = teclado.nextInt();
            teclado.nextLine(); // Limpa o buffer do Scanner

            String tipoMulta = convertEscolhaParaGravidade(escolhaMulta).toUpperCase();
            if (!tipoMulta.isEmpty()) {
                veiculo.addMultaAoMotorista(tipoMulta);
                System.out
                        .println("Multa do tipo " + tipoMulta + " registrada com sucesso no veículo de placa " + placa);
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
    private static void pagarMultas() {
        boolean multaPaga = false;

        for (Veiculo veiculo : frota.getVeiculos()) {
            if (veiculo != null) {
                Motorista motorista = veiculo.getMotorista();
                double valorPago = motorista.pagarTodasMultas();
                if (valorPago > 0) {
                    System.out.println("Todas as multas pagas para o motorista com CPF: " + motorista.getCpf()
                            + ". Total pago: " + valorPago);
                    multaPaga = true;
                }
            }
        }

        if (!multaPaga) {
            System.out.println("Não havia multas para pagar em nenhum veículo da frota.");
        }
    }

    /**
     * Verifica o estado de manutenção de todos os veículos da frota.
     * Percorre os veículos e relata se algum precisa de manutenção.
     */
    private static void verificarManutencaoVeiculos() {
        try {
            boolean manutencaoNecessaria = false;
            // Itera sobre cada veículo na frota
            for (Veiculo veiculo : frota.getVeiculos()) {
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
     * Calcula e exibe as despesas totais de um veículo aleatório na frota.
     * Isso inclui tanto o custo com combustível quanto as despesas com manutenção.
     * O cálculo é feito automaticamente com um veículo selecionado aleatoriamente.
     * Se não houver veículos na frota, uma mensagem de "Não existem veículos
     * cadastrados na frota para calcular despesas" é exibida.
     * Este método trata exceções para garantir que o aplicativo não falhe devido a
     * erros inesperados durante o cálculo.
     */
    private static void calcularDespesasTotaisVeiculo(Scanner teclado) {
        try {
            System.out.print("Digite a placa do veículo para calcular as despesas: ");
            String placa = teclado.next().toUpperCase(); // Converte para maiúscula para evitar problemas de comparação de string
            Veiculo veiculoDespesas = frota.localizarVeiculo(placa);
            
            if (veiculoDespesas != null) {
                double despesaTotal = veiculoDespesas.getDespesaTotal();
                System.out.println("Despesa total do veículo de placa " + placa + ": R$ " + String.format("%.2f", despesaTotal));
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao calcular as despesas totais do veículo: " + e.getMessage());
        }
    }
    

    /**
     * Lista as rotas não percorridas de um veículo específico.
     * Solicita ao usuário a placa do veículo e exibe as rotas disponíveis que ainda
     * não foram percorridas.
     */
    private static void listarRotasNaoPercorridas(Scanner teclado) {
        System.out.print("Digite a placa do veículo para listar rotas não percorridas: ");
        String placa = teclado.next();
        Veiculo veiculo = frota.localizarVeiculo(placa);
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
        String placa = teclado.next();
        Veiculo veiculo = frota.localizarVeiculo(placa);
        if (veiculo != null) {
            veiculo.listarRotasNaoPercorridas(); // Lista as rotas não percorridas
            System.out.print("Escolha o número da rota para percorrer: ");
            int numeroRota = teclado.nextInt();
            try {
                veiculo.percorrerRotaPorLista(numeroRota);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
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
        String placa = teclado.next();
        boolean veiculoEncontrado = false;

        for (Veiculo veiculo : frota.getVeiculos()) {
            if (veiculo != null && veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoEncontrado = true;
                try {
                    veiculo.fazerManutencao();
                    System.out.println("Manutenção realizada com sucesso no veículo com placa: " + placa);
                } catch (Exception e) {
                    System.out.println("Não foi possível realizar a manutenção: " + e.getMessage());
                }
                break;
            }
        }

        if (!veiculoEncontrado) {
            System.out.println("Veículo com placa " + placa + " não encontrado.");
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
     * Lista todos os veículos presentes na frota, incluindo informações como tipo
     * de veículo e placa.
     * Para posições não ocupadas na frota, indica que o espaço está disponível para
     * cadastro.
     * Este método fornece uma visão geral do estado atual da frota, permitindo ao
     * usuário identificar
     * rapidamente veículos existentes e espaços vagos para novos veículos.
     */
    private static void listarTodosVeiculos() {
        Veiculo[] veiculos = frota.getVeiculos();
        int quantidadeVeiculosCadastrados = 0;

        System.out.println("Listando todos os veículos da frota:");
        for (int i = 0; i < veiculos.length; i++) {
            if (veiculos[i] != null) {
                System.out.println("Veículo " + (i + 1) + ": " + veiculos[i].getPlaca() + " - "
                        + veiculos[i].getClass().getSimpleName());
                quantidadeVeiculosCadastrados++;
            } else {
                System.out.println("Espaço para veículo " + (i + 1) + ": Disponível para cadastro");
            }
        }

        System.out.println("Total de veículos cadastrados: " + quantidadeVeiculosCadastrados);
        System.out.println("Espaços disponíveis para cadastro: " + (veiculos.length - quantidadeVeiculosCadastrados));
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
        random = new Random();
        Scanner teclado = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de gerenciamento de frotas!");
        separador();
        inicializarFrota();
        separador();

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
                        registrarRota(teclado);
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
                        pagarMultas();
                        ;
                        separador();
                        break;
                    case 7:
                        verificarManutencaoVeiculos();
                        separador();
                        break;
                    case 8:
                        calcularDespesasTotaisVeiculo(teclado);
                        separador();
                        break;
                    case 9:
                        listarRotasNaoPercorridas(teclado);
                        separador();
                        break;
                    case 10:
                        percorrerRotaEspecifica(teclado);
                        separador();
                        break;
                    case 11:
                        realizarManutencaoVeiculo(teclado);
                        separador();
                        break;
                    case 12:
                        exibirRelatorioRotasVeiculo();
                        separador();
                        break;
                    case 13:
                        listarTodosVeiculos();
                        separador();
                        break;
                    case 14:
                        continuar = false; // Sai do loop e encerra o programa.
                        System.out.println("Até logo ;)");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente! :)");
                        break;
                }
                // Aguarda um tempo antes de executar a próxima ação
                pausa();
                limparTela();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                pausa();
                limparTela();
            }
        }
        teclado.close();
    }
}
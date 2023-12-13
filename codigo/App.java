import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    static Scanner teclado = new Scanner(System.in);
    static Frota frota;

    /**
     * Limpa a tela do console utilizando sequências de escape específicas do
     * terminal.
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa para leitura de mensagens em console
     */
    static void pausa() {
        System.out.println("Pressione Enter para continuar...");
        try {
            System.in.read(); // Aguarda o usuário pressionar Enter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void separador() {
        String linha = new String(new char[60]).replace('\0', '-');
        System.out.println(linha);
    }

    /**
     * Inicializa a frota de veículos com base na entrada do usuário.
     * Solicita ao usuário o número de veículos e cria uma nova Frota com esse
     * tamanho.
     */
    private static void inicializarFrota() {
        System.out.print("Digite o número de veículos da frota: ");
        int tamanhoFrota = teclado.nextInt();
        teclado.nextLine();
        frota = new Frota(tamanhoFrota);
    }

    private static void mostrarMenu() {
        /**
         * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
         */
        String linha = new String(new char[60]).replace('\0', '-');
        System.out.println(linha);
        System.out.println("\t\t  Sistema de Gerenciamento de Frotas");
        System.out.println(linha);
        System.out.println("1. Cadastrar novo veículo na frota");
        System.out.println("2. Exibir relatório completo da frota");
        System.out.println("3. Registrar rota para veículo");
        System.out.println("4. Abastecer veículo");
        System.out.println("5. Registrar multa para motorista");
        System.out.println("6. Verificar necessidade de manutenção dos veículos");
        System.out.println("7. Calcular despesas totais de um veículo");
        System.out.println("8. Encerrar o programa");
        System.out.println(linha);
        System.out.print("Selecione uma opção: ");
    }

    /**
     * Interage com o usuário para cadastrar um novo veículo na frota.
     * Solicita informações como placa, tipo de veículo, custo de manutenção, tipo
     * de combustível, nome e CPF do motorista.
     * Valida as entradas e, se bem-sucedido, adiciona o veículo à frota.
     */
    private static void cadastrarVeiculo() {
        // Solicita e lê as informações do veículo do usuário
        System.out.print("Digite a placa do veículo: ");
        String placa = teclado.next();
        System.out.print("Digite o tipo do veículo (Carro, Van, Furgao, Caminhao): ");
        String tipoVeiculo = teclado.next();

        // Inicializa a variável custoManutencao
        double custoManutencao = 0;

        // Tenta ler o custo da manutenção do veículo, e captura exceções de entrada
        // inválida
        try {
            System.out.print("Digite o custo da manutenção do veículo: ");
            custoManutencao = teclado.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Você digitou um custo inválido. Por favor, digite um número.");
            teclado.next(); // Limpa o buffer do teclado
            return; // Encerra o método para evitar prosseguir com dados inválidos
        }

        // Continua solicitando as demais informações do veículo
        System.out.print("Digite o tipo de combustível (Alcool, Gasolina, Diesel): ");
        String tipoCombustivelStr = teclado.next().toUpperCase();
        System.out.print("Digite o nome do motorista: ");
        String nomeMotorista = teclado.next();
        System.out.print("Digite o CPF do motorista: ");
        String cpfMotorista = teclado.next();
        separador();

        // Cria um objeto motorista com as informações coletadas
        Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

        // Inicializa a variável veiculo como nula
        Veiculo veiculo = null;

        // Cria uma instância do veículo de acordo com o tipo fornecido
        switch (tipoVeiculo.toUpperCase()) {
            case "CARRO":
                veiculo = new Carro(motorista, placa, tipoCombustivelStr, custoManutencao);
                break;
            case "VAN":
                veiculo = new Van(motorista, placa, tipoCombustivelStr, custoManutencao);
                break;
            case "FURGAO":
                veiculo = new Furgao(motorista, placa, tipoCombustivelStr, custoManutencao);
                break;
            case "CAMINHAO":
                veiculo = new Caminhao(motorista, placa, tipoCombustivelStr, custoManutencao);
                break;
            default:
                System.out.println("Tipo de veículo não reconhecido.");
                return; // Encerra o método se o tipo de veículo não for reconhecido
        }

        // Se o veículo foi criado com sucesso, adiciona à frota
        if (veiculo != null) {
            frota.adicionarVeiculo(veiculo);
            System.out.println("Veículo cadastrado com sucesso!");
        }
    }

    /**
     * Exibe um relatório detalhado da frota, incluindo informações de cada veículo
     * cadastrado.
     */
    private static void exibirRelatorioFrota() {
        // Exibir relatorio completo da frota
        System.out.println(frota.relatorioFrota());
    }

    /**
     * Permite ao usuário registrar uma rota para um veículo específico.
     * Solicita a placa do veículo e a quilometragem da rota a ser registrada.
     */
    private static void registrarRota() {
        // Solicita a placa e tenta registrar a rota, capturando exceções de entrada
        // inválida
        try {
            System.out.print("Digite a placa do veículo para a rota: ");
            String placaRota = teclado.nextLine();
            Veiculo veiculoRota = frota.localizarVeiculo(placaRota);
            // Verifica se o veículo foi encontrado
            if (veiculoRota != null) {
                System.out.print("Digite a quilometragem da rota: ");
                double kmRota = teclado.nextDouble();
                teclado.nextLine(); // Limpa o buffer do teclado
                Rota rota = new Rota(LocalDate.now(), kmRota);
                // Tenta adicionar a rota ao veículo
                boolean sucesso = veiculoRota.addRota(rota);
                if (sucesso) {
                    System.out.println("Rota adicionada com sucesso!");
                } else {
                    System.out.println("Não foi possível adicionar a rota.");
                }
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
     * Interage com o usuário para abastecer um veículo específico da frota.
     * Solicita a placa do veículo e a quantidade de combustível para abastecimento.
     */
    private static void abastecerVeiculo() {
        try {
            System.out.print("Digite a placa do veículo para abastecer: ");
            String placaAbastecer = teclado.nextLine();
            Veiculo veiculoAbastecer = frota.localizarVeiculo(placaAbastecer);
            if (veiculoAbastecer != null) {
                System.out.print("Digite a quantidade de combustível para abastecer (em litros): ");
                double litros = teclado.nextDouble();
                teclado.nextLine();
                veiculoAbastecer.abastecer(litros);
                System.out.println("Veículo abastecido com sucesso!");
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número válido para os litros.");
            teclado.nextLine();
        }
    }

    /**
     * Permite ao usuário registrar uma multa para um motorista específico.
     * Solicita a placa do veículo e o tipo de multa para registro.
     */
    private static void registrarMulta() {
        // Registrar multa na carteira do motorista
        System.out.print("Digite a placa do veículo que recebeu a multa: ");
        String placa = teclado.nextLine();
        Veiculo veiculo = frota.localizarVeiculo(placa);

        if (veiculo != null) {
            System.out.println("Selecione o tipo de multa:");
            System.out.println("1. Leve");
            System.out.println("2. Média");
            System.out.println("3. Grave");
            System.out.println("4. Gravíssima");

            try {
                int tipoMulta = teclado.nextInt();
                teclado.nextLine(); // Lê o fim da linha após o número
                Multa multa = null;

                switch (tipoMulta) {
                    case 1:
                        multa = veiculo.addMultaAoMotorista("LEVE");
                        break;
                    case 2:
                        multa = veiculo.addMultaAoMotorista("MEDIA");
                        break;
                    case 3:
                        multa = veiculo.addMultaAoMotorista("GRAVE");
                        break;
                    case 4:
                        multa = veiculo.addMultaAoMotorista("GRAVISSIMA");
                        break;
                    default:
                        System.out.println("Tipo de multa não reconhecido.");
                        return; // Sai do método se o tipo de multa não é reconhecido
                }

                if (multa != null) {
                    veiculo.despesaMultaMotorista(multa.getValor());
                    System.out.println("Multa registrada com sucesso!");
                } else {
                    System.out.println("Erro ao registrar a multa.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número correspondente ao tipo de multa.");
                teclado.nextLine();
            }
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Verifica e relata a necessidade de manutenção dos veículos da frota.
     * Percorre todos os veículos da frota e verifica o estado da manutenção de cada
     * um.
     */
    private static void verificarManutencaoVeiculos() {
        // Verificar necessidade de manutenção de algum veículo
        System.out.println("Verificação de Manutenção dos Veículos:");
        boolean manutencaoNecessaria = false;

        for (int i = 0; i < frota.getTamanhoFrota(); i++) {
            Veiculo veiculo = frota.getVeiculos()[i];
            if (veiculo != null) {
                Manutencao manutencao = veiculo.getManutencao();
                if (!manutencao.getManutencaoPecasEmDia() || !manutencao.getManutencaoPeriodicaEmDia()) {
                    manutencaoNecessaria = true;
                    System.out.println("Veículo " + veiculo.getPlaca() + " precisa de manutenção.");
                }
            }
        }

        if (!manutencaoNecessaria) {
            System.out.println("Todos os veículos estão com a manutenção em dia.");
        }
    }

    /**
     * Calcula e exibe as despesas totais de um veículo específico baseado em sua
     * placa.
     * Isso inclui tanto o custo com combustível quanto as despesas com manutenção.
     * O usuário deve fornecer a placa do veículo para que o cálculo seja realizado.
     * Se a placa não corresponder a um veículo na frota, uma mensagem de veículo
     * não encontrado é exibida.
     * Este método trata exceções para garantir que o aplicativo não falhe devido a
     * erros inesperados durante o cálculo.
     */
    private static void calcularDespesasTotaisVeiculo() {
        try {
            System.out.print("Digite a placa do veículo para calcular as despesas: ");
            String placaDespesas = teclado.nextLine();
            Veiculo veiculoDespesas = frota.localizarVeiculo(placaDespesas);
            if (veiculoDespesas != null) {
                double despesaCombustivel = veiculoDespesas.getDespesaTotal();
                double despesaManutencao = veiculoDespesas.getManutencao().getTotalDespesasManutencao();
                double despesaTotal = despesaCombustivel + despesaManutencao;
                System.out.println("Despesa total do veículo com placa " + placaDespesas + ": R$ "
                        + String.format("%.2f", despesaTotal));
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao calcular as despesas: " + e.getMessage());
        }
    }

    /**
     * Método principal que atua como ponto de entrada para o sistema de
     * gerenciamento de frotas.
     * Inicia o sistema, saúda o usuário e entra em um loop que apresenta um menu de
     * opções.
     * O usuário pode selecionar opções para gerenciar veículos, rotas, manutenções
     * e despesas.
     * O loop continua até que o usuário opte por encerrar o programa.
     * Exceções de entrada são tratadas para evitar entradas inválidas durante a
     * seleção do menu.
     */
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao sistema de gerenciamento de frotas!");
        inicializarFrota();
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            try {
                int opcao = teclado.nextInt();
                separador();
                teclado.nextLine(); // Limpa o buffer do teclado após a leitura de um número.
                switch (opcao) {
                    case 1:
                        cadastrarVeiculo();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 2:
                        exibirRelatorioFrota();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 3:
                        registrarRota();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 4:
                        abastecerVeiculo();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 5:
                        registrarMulta();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 6:
                        verificarManutencaoVeiculos();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 7:
                        calcularDespesasTotaisVeiculo();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 8:
                        continuar = false; // Sai do loop e encerra o programa.
                        System.out.println("Até logo ;)");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente! :)");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                teclado.next(); // Limpa o buffer do teclado para evitar loop infinito.
                pausa();
                limparTela();
            }
        }
        teclado.close(); // Fecha o recurso Scanner antes de encerrar o programa.
    }
}
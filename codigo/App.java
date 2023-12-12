import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    static Scanner teclado = new Scanner(System.in);
    static Frota frota;

    private static void inicializarFrota() {
        System.out.print("Digite o número de veículos da frota: ");
        int tamanhoFrota = teclado.nextInt();
        teclado.nextLine();
        frota = new Frota(tamanhoFrota);
    }

    private static void mostrarMenu() {
        System.out.println("\nSelecione uma opção:");
        System.out.println("1. Cadastrar novo veículo na frota");
        System.out.println("2. Exibir relatório completo da frota");
        System.out.println("3. Registrar rota para veículo");
        System.out.println("4. Abastecer veículo");
        System.out.println("5. Registrar multa para motorista");
        System.out.println("6. Verificar necessidade de manutenção dos veículos");
        System.out.println("7. Calcular despesas totais de um veículo");
        System.out.println("8. Encerrar o programa");
    }

    private static void cadastrarVeiculo() {
        System.out.print("Digite a placa do veículo: ");
        String placa = teclado.next();
        System.out.print("Digite o tipo do veículo (Carro, Van, Furgao, Caminhao): ");
        String tipoVeiculo = teclado.next();
        System.out.print("Digite o custo da manutenção do veículo: "); // Novo input para custo de manutenção
        double custoManutencao = teclado.nextDouble();
        System.out.print("Digite o tipo de combustível (Alcool, Gasolina, Diesel): ");
        String tipoCombustivelStr = teclado.next().toUpperCase();
        System.out.print("Digite o nome do motorista: ");
        String nomeMotorista = teclado.next();
        System.out.print("Digite o CPF do motorista: ");
        String cpfMotorista = teclado.next();
        Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

        // Com base no tipo do veículo e no custo de manutenção, cria-se a instância
        // correta para cada um
        Veiculo veiculo = null;
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
                break;
        }
        if (veiculo != null) {
            frota.adicionarVeiculo(veiculo);
            System.out.println("Veículo cadastrado com sucesso!");
        }
    }

    private static void exibirRelatorioFrota() {
        System.out.println(frota.relatorioFrota());
    }

    private static void registrarRota() {
        // Registrar rota
        try {
            System.out.print("Digite a placa do veículo para a rota: ");
            String placaRota = teclado.nextLine();
            Veiculo veiculoRota = frota.localizarVeiculo(placaRota);
            if (veiculoRota != null) {
                System.out.print("Digite a quilometragem da rota: ");
                double kmRota = teclado.nextDouble();
                teclado.nextLine();
                Rota rota = new Rota(LocalDate.now(), kmRota);
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

    private static void abastecerVeiculo() {
        // Abastecer veículo
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

    private static void registrarMulta() {
        // Registrar multa
        System.out.print("Digite a placa do veículo que recebeu a multa: ");
        String placa = teclado.nextLine();
        Veiculo veiculo = frota.localizarVeiculo(placa);
        if (veiculo != null) {
            System.out.println("Selecione o tipo de multa:");
            System.out.println("1. Leve");
            System.out.println("2. Média");
            System.out.println("3. Grave");
            System.out.println("4. Gravíssima");
            int tipoMulta = teclado.nextInt();
            double valorMulta = 0;
            switch (tipoMulta) {
                case 1:
                    veiculo.getMotorista().adicionarPontos("LEVE");
                    valorMulta = Multa.LEVE.getValor();
                    break;
                case 2:
                    veiculo.getMotorista().adicionarPontos("MEDIA");
                    valorMulta = Multa.MEDIA.getValor();
                    break;
                case 3:
                    veiculo.getMotorista().adicionarPontos("GRAVE");
                    valorMulta = Multa.GRAVE.getValor();
                    break;
                case 4:
                    veiculo.getMotorista().adicionarPontos("GRAVISSIMA");
                    valorMulta = Multa.GRAVISSIMA.getValor();
                    break;
                default:
                    System.out.println("Tipo de multa não reconhecido.");
                    return; // Sai do método se o tipo de multa não é reconhecido
            }
            veiculo.despesaMultaMotorista(valorMulta);
            System.out.println("Multa registrada com sucesso!");
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    private static void verificarManutencaoVeiculos() {
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

    private static void calcularDespesasTotaisVeiculo() {
        try {
            System.out.print("Digite a placa do veículo para calcular as despesas: ");
            String placaDespesas = teclado.nextLine();
            Veiculo veiculoDespesas = frota.localizarVeiculo(placaDespesas);
            if (veiculoDespesas != null) {
                // Obtem a despesa de combustível já existente
                double despesaCombustivel = veiculoDespesas.getDespesaTotal();
    
                // Obtem as despesas totais de manutenção
                double despesaManutencao = veiculoDespesas.getManutencao().getTotalDespesasManutencao();
    
                // Calcula a despesa total somando combustível e manutenção
                double despesaTotal = despesaCombustivel + despesaManutencao;
    
                System.out.println("Despesa total do veículo com placa " + placaDespesas + ": R$ "
                        + String.format("%.2f", despesaTotal));
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao calcular as despesas: " + e.getMessage());
            e.printStackTrace(); // Apenas para depuração, pode ser removido em produção
        }
    }    

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao sistema de gerenciamento de frotas!");
        inicializarFrota();
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            try {
                int opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarVeiculo();
                        break;
                    case 2:
                        exibirRelatorioFrota();
                        break;
                    case 3:
                        registrarRota();
                        break;
                    case 4:
                        abastecerVeiculo();
                        break;
                    case 5:
                        registrarMulta();
                        break;
                    case 6:
                        verificarManutencaoVeiculos();
                        break;
                    case 7:
                        calcularDespesasTotaisVeiculo();
                        break;
                    case 8:
                        continuar = false;
                        System.out.println("Até logo ;)");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente! :)");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                teclado.nextLine(); // Limpa o buffer do teclado
            }
        }
        teclado.close();
    }
}
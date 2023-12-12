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
        // Cadastrar novo veículo
        System.out.print("Digite a placa do veículo: ");
        String placa = teclado.next();
        System.out.print("Digite o tipo do veículo (Carro, Van, Furgao, Caminhao): ");
        String tipoVeiculo = teclado.next();
        System.out.print("Digite o tipo de combustível (Alcool, Gasolina, Diesel): ");
        String tipoCombustivelStr = teclado.next().toUpperCase();
        System.out.print("Digite o nome do motorista: ");
        String nomeMotorista = teclado.next();
        System.out.print("Digite o CPF do motorista: ");
        String cpfMotorista = teclado.next();
        Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

        // Com base no tipo do veículo, cria-se a instância correta para cada um
        Veiculo veiculo = null;
        switch (tipoVeiculo.toUpperCase()) {
            case "CARRO":
                veiculo = new Carro(motorista, placa, tipoCombustivelStr);
                break;
            case "VAN":
                veiculo = new Van(motorista, placa, tipoCombustivelStr);
                break;
            case "FURGAO":
                veiculo = new Furgao(motorista, placa, tipoCombustivelStr);
                break;
            case "CAMINHAO":
                veiculo = new Caminhao(motorista, placa, tipoCombustivelStr);
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
            switch (tipoMulta) {
                case 1:
                    veiculo.getMotorista().adicionarPontos("LEVE");
                    break;
                case 2:
                    veiculo.getMotorista().adicionarPontos("MEDIA");
                    break;
                case 3:
                    veiculo.getMotorista().adicionarPontos("GRAVE");
                    break;
                case 4:
                    veiculo.getMotorista().adicionarPontos("GRAVISSIMA");
                    break;
                default:
                    System.out.println("Tipo de multa não reconhecido.");
                    break;
            }
            System.out.println("Multa registrada com sucesso!");
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    private static void verificarManutencaoVeiculos() {
        // Implementar a lógica de verificação de manutenção de veículos aqui
    }

    private static void calcularDespesasTotaisVeiculo() {
        // Calcular despesas totais de um veículo
        try {
            System.out.print("Digite a placa do veículo para calcular as despesas: ");
            String placaDespesas = teclado.nextLine();
            Veiculo veiculoDespesas = frota.localizarVeiculo(placaDespesas);
            if (veiculoDespesas != null) {
                double despesaTotal = veiculoDespesas.getDespesaTotal();
                System.out.println("Despesa total de combustível para o veículo com placa " + placaDespesas
                        + ": R$ " + despesaTotal);
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao calcular as despesas: " + e.getMessage());
            e.printStackTrace(); // Para ajudar na depuração, imprime a pilha de exceção.
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
                teclado.nextLine(); // Limpa o buffer do scanner
            }
        }
        teclado.close();
    }
}
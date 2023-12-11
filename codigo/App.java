import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Bem-vindo ao sistema de gerenciamento de frotas!");

        // Solicita ao usuário para definir o tamanho da frota e cria a frota
        System.out.print("Digite o número de veículos da frota: ");
        int tamanhoFrota = teclado.nextInt();
        teclado.nextLine();
        Frota frota = new Frota(tamanhoFrota);

        // Menu de opções
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nSelecione uma opção:");
            System.out.println("1. Cadastrar novo veículo na frota");
            System.out.println("2. Exibir relatório completo da frota (inclui reabastecimento e quilometragem)");
            System.out.println("3. Registrar rota para veículo");
            System.out.println("4. Abastecer veículo");
            System.out.println("5. Registrar multa para motorista");
            System.out.println("6. Verificar necessidade de manutenção dos veículos");
            System.out.println("7. Calcular despesas totais de um veículo");
            System.out.println("8. Encerrar o programa");

            int opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
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
                    break;
                case 2:
                    // Exibir o relatório da frota
                    System.out.println(frota.relatorioFrota());
                    break;
                case 3:
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
                    break;
                case 4:
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
                    break;
                case 5:
                    // Registrar multa
                    System.out.print("Digite a placa do veículo que recebeu a multa: ");
                    placa = teclado.nextLine();
                    veiculo = frota.localizarVeiculo(placa);
                    if (veiculo != null) {
                        System.out.print("Digite o tipo de multa: ");
                        String tipoMulta = teclado.nextLine();
                        System.out.print("Digite o valor da multa: ");
                        double valorMulta = teclado.nextDouble();
                        System.out.print("Digite os pontos da multa: ");
                        int pontosMulta = teclado.nextInt();
                        Multa multa = new Multa(tipoMulta, pontosMulta, valorMulta);
                        veiculo.receberMulta(multa);
                        System.out.println("Multa registrada com sucesso!");
                    } else {
                        System.out.println("Veículo não encontrado.");
                    }
                    break;
                case 6:
                    // Verificar manutenção dos veículos (depende da classe Manutenção)
                    break;
                case 7:
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
                    break;
                case 8:
                    continuar = false;
                    System.out.println("Até logo ;)");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente! :)");
                    break;
            }
        }

        teclado.close();
    }
}

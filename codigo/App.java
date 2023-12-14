import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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

    /**
     * Inicializa a frota de veículos com base em valores aleatórios gerados pelo
     * sistema.
     * Cria uma nova Frota com um tamanho aleatório entre 1 e 20 veículos.
     */
    private static void inicializarFrota() {
        int tamanhoFrota = random.nextInt(5) + 1; // Gera um tamanho aleatório para a frota entre 1 e 5
        frota = new Frota(tamanhoFrota);
        for (int i = 0; i < tamanhoFrota; i++) {
            Veiculo veiculo = cadastrarVeiculo();
            // Gera um valor aleatório entre 10 (inclusive) e 17 (exclusive)
            double quantidadeInicial = 10 + random.nextInt(7); // 7 é o intervalo (16-10 + 1)
            veiculo.abastecer(quantidadeInicial); // Abastece o veículo com a quantidade inicial aleatória
        }
        System.out.println("Frota com " + tamanhoFrota + " veículos foi inicializada.");
    }
    

    /**
     * Exibe o menu principal do sistema com as opções disponíveis para o usuário.
     * As opções são escolhidas automaticamente pelo sistema.
     */
    private static void mostrarMenu() {
        String linha = new String(new char[60]).replace('\0', '-');
        System.out.println(linha);
        System.out.println("\t\t  Sistema de Gerenciamento de Frotas");
        System.out.println(linha);
        System.out.println("1. Cadastrar novo veículo na frota");
        System.out.println("2. Exibir relatório completo da frota");
        System.out.println("3. Registrar rota para veículo");
        System.out.println("4. Exibir relatório de rotas");
        System.out.println("5. Abastecer veículo");
        System.out.println("6. Registrar multa para motorista");
        System.out.println("7. Verificar necessidade de manutenção dos veículos");
        System.out.println("8. Calcular despesas totais de um veículo");
        System.out.println("9. Encerrar o programa");
        System.out.println(linha);
        System.out.print("Selecione uma opção: ");
    }

    /**
     * Interage com o sistema para cadastrar um novo veículo na frota.
     * Gera informações aleatórias, como placa, tipo de veículo, custo de
     * manutenção,
     * tipo de combustível, nome e CPF do motorista.
     * Valida as entradas geradas aleatoriamente e, se bem-sucedido, adiciona o
     * veículo à frota.
     */
    private static Veiculo cadastrarVeiculo() {
        Veiculo veiculo = null;
        try {
            // Utiliza os valores do enum TipoCombustivel
            TipoCombustivel[] tiposCombustivel = TipoCombustivel.values();

            // Seleciona um tipo de combustível aleatoriamente
            TipoCombustivel tipoCombustivelEscolhido = tiposCombustivel[random.nextInt(tiposCombustivel.length)];
            String tipoCombustivel = tipoCombustivelEscolhido.name();

            // Escolhe um tipo de veículo aleatoriamente
            String[] tiposVeiculo = { "Carro", "Van", "Furgao", "Caminhao" };
            String tipoVeiculo = tiposVeiculo[random.nextInt(tiposVeiculo.length)];

            // Mapa de custos de manutenção
            Map<String, Double> custosManutencao = new HashMap<>();
            custosManutencao.put("Carro", 500.0);
            custosManutencao.put("Van", 750.0);
            custosManutencao.put("Furgao", 1000.0);
            custosManutencao.put("Caminhao", 1500.0);

            // Lista de nomes de motoristas
            String[] nomesMotoristas = { "João", "Maria", "José", "Ana", "Pedro", "Sandra", "Carlos", "Laura",
                    "Fernando", "Isabela" };
            String nomeMotorista = nomesMotoristas[random.nextInt(nomesMotoristas.length)];
            String cpfMotorista = String.format("%011d", random.nextInt(1000000000));
            Motorista motorista = new Motorista(nomeMotorista, cpfMotorista);

            // Seleciona um tipo de veículo aleatório e seu respectivo custo de manutenção
            double custoManutencao = custosManutencao.get(tipoVeiculo);

            // Gera placa aleatória com 3 letras diferentes pra cada veiculo e os 4 numeros
            // diferentes também
            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String placa = "" +
                    letras.charAt(random.nextInt(letras.length())) +
                    letras.charAt(random.nextInt(letras.length())) +
                    letras.charAt(random.nextInt(letras.length()));

            int numeros = 1000 + random.nextInt(9000); //
            placa += numeros;

            // Cria o veículo baseado no tipo escolhido
            switch (tipoVeiculo) {
                case "Carro":
                    veiculo = new Carro(motorista, placa, tipoCombustivel, custoManutencao);
                    break;
                case "Van":
                    veiculo = new Van(motorista, placa, tipoCombustivel, custoManutencao);
                    break;
                case "Furgao":
                    veiculo = new Furgao(motorista, placa, tipoCombustivel, custoManutencao);
                    break;
                case "Caminhao":
                    veiculo = new Caminhao(motorista, placa, tipoCombustivel, custoManutencao);
                    break;
            }

            // Adiciona o veículo à frota
            if (veiculo != null) {
                frota.adicionarVeiculo(veiculo);
                System.out.println("Veículo do tipo " + tipoVeiculo + " cadastrado com sucesso! Placa: " + placa);
                // Adiciona uma capacidade inicial ao tanque do veículo
                double capacidadeInicial = 10 + random.nextInt(7); // Define a quantidade inicial de combustível
                veiculo.abastecer(capacidadeInicial); // Abastece o veículo com a capacidade inicial
            } else {
                System.out.println("Não foi possível cadastrar o veículo.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao cadastrar o veículo: " + e.getMessage());
        }
        return veiculo;
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

    private static void registrarRota() {
    try {
        if (frota.getTamanhoFrota() > 0) {
            Veiculo veiculoRota = frota.getVeiculos()[random.nextInt(frota.getTamanhoFrota())];
            if (veiculoRota != null && veiculoRota.getQuantRotas() < Veiculo.MAX_ROTAS) {
                double kmRota = 10 + (random.nextDouble() * (Math.min(veiculoRota.autonomiaAtual(), veiculoRota.autonomiaMaxima()) - 10));
                Rota rota = new Rota(LocalDate.now(), kmRota);
                veiculoRota.addRota(rota); // Assume que addRota lida com exceções internamente
                System.out.println("Rota de " + kmRota + " km adicionada ao veículo de placa: " + veiculoRota.getPlaca());
            } else {
                System.out.println("Veículo não encontrado ou já atingiu o limite de rotas para este mês.");
            }
        } else {
            System.out.println("Não existem veículos cadastrados na frota para registrar uma rota.");
        }
    } catch (Exception e) {
        System.out.println("Ocorreu um erro ao registrar a rota: " + e.getMessage());
    }
}
    

    /**
     * Permite ao sistema abastecer um veículo específico da frota.
     * Gera aleatoriamente a placa do veículo e a quantidade de combustível para
     * abastecimento.
     */
    private static void abastecerVeiculo() {
        try {
            if (frota.getTamanhoFrota() > 0) {
                Veiculo veiculoAbastecer = frota.getVeiculos()[random.nextInt(frota.getTamanhoFrota())];
                if (veiculoAbastecer != null) {
                    double capacidadeParaAbastecer = veiculoAbastecer.tanque.getCapacidadeMaxima() - veiculoAbastecer.tanqueAtual;
                    double litros = (int) (10 + (random.nextDouble() * capacidadeParaAbastecer));
                    double custoAbastecimento = litros * veiculoAbastecer.tanque.getPreco();
                    veiculoAbastecer.abastecer(litros);

                    veiculoAbastecer.addDespesaTotal(custoAbastecimento);
                    System.out.println("Veículo de placa " + veiculoAbastecer.getPlaca() + " abastecido com " + litros + " litros de " + veiculoAbastecer.getTanque().getTipo() + ".\nCusto: R$ " + String.format("%.2f", custoAbastecimento));
                   
                }
                else{
                     System.out.println("Veículo não encontrado para abastecer.");
                }
            } else {
                System.out.println("Não existem veículos cadastrados na frota para abastecer.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao abastecer o veículo: " + e.getMessage());
        }
    }
    

    /**
     * Permite ao sistema registrar uma multa para um motorista específico.
     * Gera aleatoriamente a placa do veículo e o tipo de multa para registro.
     */

     private static void registrarMulta() {
        try {
            if (frota.getTamanhoFrota() > 0) {
                Veiculo veiculo = frota.getVeiculos()[random.nextInt(frota.getTamanhoFrota())];
                if (veiculo != null) {
                    Motorista motorista = veiculo.getMotorista();
                    // Verifica se a carteira do motorista ainda é válida
                    if (motorista.getCarteiraValida()) {
                        String[] tiposMulta = { "LEVE", "MEDIA", "GRAVE", "GRAVISSIMA" };
                        String tipoMulta = tiposMulta[random.nextInt(tiposMulta.length)];
                        Multa multa = veiculo.addMultaAoMotorista(tipoMulta); // Adiciona a multa e os pontos associados
                        if (multa != null && multa.multaExpirou()) { // Supõe que a classe Multa tem o método multaExpirou()
                            veiculo.addDespesaTotal(multa.getValor()); // Adiciona o valor da multa ao total de despesas do veículo
                            System.out.println("Multa do tipo " + tipoMulta + " registrada no veículo de placa " + veiculo.getPlaca() + ".");
                        } else {
                            System.out.println("Não foi possível registrar a multa, multa expirada ou inválida.");
                        }
                    } else {
                        System.out.println("Motorista com carteira invalidada, não é possível registrar multa.");
                    }
                } else {
                    System.out.println("Veículo não encontrado para registrar multa.");
                }
            } else {
                System.out.println("Não existem veículos cadastrados na frota para registrar multas.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao registrar a multa: " + e.getMessage());
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
    private static void calcularDespesasTotaisVeiculo() {
        try {
            if (frota.getTamanhoFrota() > 0) {
                Veiculo veiculoDespesas = frota.getVeiculos()[random.nextInt(frota.getTamanhoFrota())];
                if (veiculoDespesas != null) {
                    double despesaTotal = veiculoDespesas.getDespesaTotal();
                    System.out.println("Despesa total do veículo de placa " + veiculoDespesas.getPlaca() + ": R$ "
                            + String.format("%.2f", despesaTotal));
                } else {
                    System.out.println("Veículo não encontrado.");
                }
            } else {
                System.out.println("Não existem veículos cadastrados na frota para calcular despesas.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao calcular as despesas totais do veículo: " + e.getMessage());
        }
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
                        frota.exibirRelatorioRotas();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 5:
                        abastecerVeiculo();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 6:
                        registrarMulta();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 7:
                        verificarManutencaoVeiculos();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 8:
                        calcularDespesasTotaisVeiculo();
                        separador();
                        pausa();
                        limparTela();
                        break;
                    case 9:
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
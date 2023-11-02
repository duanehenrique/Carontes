import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Bem-vindo ao sistema de gerenciamento de frotas!");
        
        // Criação da frota
        int tamanhoFrota = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Digite o número de veículos da frota: ");
                tamanhoFrota = teclado.nextInt();
                entradaValida = true;
              // Tratamento de erros  
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número válido.");
                teclado.next();  // Limpar buffer
            }
        }

        Frota frota = new Frota(tamanhoFrota);

        // Adicionando veículos fictícios para teste
        for (int i = 0; i < tamanhoFrota; i++) {
            Veiculo veiculo = new Veiculo(
                    "Placa-" + (i + 1),
                    new Tanque(30, 60)
            );
            frota.adicionarVeiculo(veiculo);
        }

        // Menu de opções
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nSelecione uma opção:");
            System.out.println("1. Relatório da Frota");
            System.out.println("2. Quilometragem total da frota");
            System.out.println("3. Veículo com maior quilometragem");
            System.out.println("4. Veículo com maior média de quilometragem");
            System.out.println("5. Sair");

            int opcao = 0;
            entradaValida = false;
            while (!entradaValida) {
                try {
                    opcao = teclado.nextInt();
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, escolha uma opção válida.");
                    teclado.next(); 
                }
            }

            switch (opcao) {
                case 1:
                    System.out.println(frota.relatorioFrota());
                    break;
                case 2:
                    System.out.println("Quilometragem total da frota: " + frota.quilometragemTotal() + " km");
                    break;
                case 3:
                    Veiculo maiorKm = frota.maiorKmTotal();
                    System.out.println("Veículo com maior quilometragem: " + maiorKm.getPlaca());
                    break;
                case 4:
                    Veiculo maiorKmMedia = frota.maiorKmMedia();
                    System.out.println("Veículo com maior média de quilometragem: " + maiorKmMedia.getPlaca());
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente! :)");
                    break;
            }
        }

        System.out.println("Até logo ;)");
        teclado.close();
    }
}


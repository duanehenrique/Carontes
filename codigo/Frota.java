import java.util.Scanner;

public class Frota {
    // #region Atributos
    private int tamanhoFrota;
    private Veiculo[] veiculos;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Frota.
     * 
     * @param tamanhoFrota O tamanho da frota.
     */
    public Frota(int tamanhoFrota) {
        this.tamanhoFrota = tamanhoFrota;
        this.veiculos = new Veiculo[tamanhoFrota];
    }

    // #endregion

    // #region Métodos
    /**
     * Adiciona um veículo à frota.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] == null) {
                veiculos[i] = veiculo;
                break;
            }
        }
    }

    /**
     * Gera um relatório da frota.
     * 
     * @return Uma string contendo o relatório da frota.
     */
     public String relatorioFrota() {
    StringBuilder relatorio = new StringBuilder();
         relatorio.append("Relatório da Frota:\n");
         for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                 relatorio.append("Veículo " + veiculos[i].getPlaca() + ":\n");
                 relatorio.append("   Quilometragem no mês: " + veiculos[i].kmNoMes() + " km\n");
                 relatorio.append("   Quilometragem total: " + veiculos[i].kmTotal() + " km\n");
                 relatorio.append("   Autonomia atual: " + veiculos[i].autonomiaAtual() + " km\n");
                 relatorio.append("   Litros reabastecidos: " + veiculos[i].getTanque().getCapacidadeAtual() + " litros\n");
                 relatorio.append("   Tipo de Combustível: " + veiculos[i].getTanque().getTipo() + "\n");
                 relatorio.append("\n");
             }
         }
         return relatorio.toString();
     }

    public void exibirRelatorioRotas() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite a placa do veículo para exibir o relatório de rotas: ");
         String placaRotas = teclado.nextLine();
        Veiculo veiculo = this.localizarVeiculo(placaRotas);
        if (veiculo != null) {
            System.out.println(veiculo.relatorioRotas());
        } else {
            System.out.println("Veículo não encontrado.");
        }
        teclado.close();
    }

    /**
     * Localiza um veículo na frota pela placa.
     * 
     * @param placa A placa do veículo a ser localizado.
     * @return O veículo localizado, ou null se não for encontrado.
     */
    public Veiculo localizarVeiculo(String placa) {
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null && veiculos[i].getPlaca().equals(placa)) {
                return veiculos[i];
            }
        }
        return null;
    }

    /**
     * Calcula a quilometragem total da frota.
     * 
     * @return A quilometragem total.
     */
    public double quilometragemTotal() {
        double totalKm = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                totalKm += veiculos[i].kmTotal();
            }
        }
        return totalKm;
    }

    /**
     * Encontra o veículo com a maior quilometragem total na frota.
     * 
     * @return O veículo com a maior quilometragem total.
     */
    public Veiculo maiorKmTotal() {
        Veiculo veiculoComMaiorKm = null;
        double maiorKm = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null && veiculos[i].kmTotal() > maiorKm) {
                veiculoComMaiorKm = veiculos[i];
                maiorKm = veiculos[i].kmTotal();
            }
        }
        return veiculoComMaiorKm;
    }

    /**
     * Encontra o veículo com a maior quilometragem média na frota.
     * 
     * @return O veículo com a maior quilometragem média.
     */
    public Veiculo maiorKmMedia() {
        Veiculo veiculoComMaiorKmMedia = null;
        double maiorKmMedia = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                double kmMedia = veiculos[i].kmTotal() / veiculos[i].getQuantRotas();
                if (kmMedia > maiorKmMedia) {
                    veiculoComMaiorKmMedia = veiculos[i];
                    maiorKmMedia = kmMedia;
                }
            }
        }
        return veiculoComMaiorKmMedia;
    }

    // #endregion

    // #region Getters
    /**
     * Retorna o tamanho da frota.
     * 
     * @return O tamanho da frota.
     */
    public int getTamanhoFrota() {
        return tamanhoFrota;
    }

    /**
     * Retorna os veículos da frota.
     * 
     * @return Um array contendo os veículos da frota.
     */
    public Veiculo[] getVeiculos() {
        return veiculos;
    }
    // #endregion
}

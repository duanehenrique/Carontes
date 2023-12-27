import java.time.LocalDate;
import java.util.Scanner;

public class Frota {
    // #region Atributos
    private int tamanhoFrota;
    private Barco[] veiculos;
    private LocalDate dataUltimaFrota;
    // #endregion

    // #region Construtores
    /**
     * Construtor da classe Frota.
     * 
     * @param tamanhoFrota O tamanho da frota.
     */
    public Frota(int tamanhoFrota) {
        this.tamanhoFrota = tamanhoFrota;
        this.veiculos = new Barco[tamanhoFrota];
        this.dataUltimaFrota = LocalDate.now();
    }

    // #endregion

    // #region Métodos
    /**
     * Adiciona um veículo à frota.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public void adicionarVeiculo(Barco veiculo) {
        if(mesVirou()){
            reiniciarFrota();
        }
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
                 relatorio.append(veiculos[i].relatorio() + "\n");
             }
         }
         return relatorio.toString();
     }

    public void exibirRelatorioRotas() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite a placa do veículo para exibir o relatório de rotas: ");
         String placaRotas = teclado.nextLine().toUpperCase();
        Barco veiculo = this.localizarVeiculo(placaRotas);
        if (veiculo != null) {
            System.out.println(veiculo.relatorioRotas());
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Localiza um veículo na frota pela placa.
     * 
     * @param placa A placa do veículo a ser localizado.
     * @return O veículo localizado, ou null se não for encontrado.
     */
    public Barco localizarVeiculo(String placa) {
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null && veiculos[i].getNome().equals(placa.toUpperCase())) {
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
    public Barco maiorKmTotal() {
        Barco veiculoComMaiorKm = null;
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
    public Barco maiorKmMedia() {
        Barco veiculoComMaiorKmMedia = null;
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

        /**
     * Verifica se houve mudança de mês.
     * 
     * @return true se o mês virou, false caso contrário.
     */
    private boolean mesVirou(){
        LocalDate dataDeHoje = LocalDate.now();
        if (dataDeHoje.getMonthValue() != dataUltimaFrota.getMonthValue()) {
            dataUltimaFrota = dataDeHoje;
            return true;
        }else{
            return false;
        }        
    }

    /**
     * Reinicia a frota, criando um novo array de veículos com o tamanho especificado.
     */
    private void reiniciarFrota(){
        this.veiculos = new Barco[tamanhoFrota];
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
    public Barco[] getVeiculos() {
        return veiculos;
    }
    // #endregion
}

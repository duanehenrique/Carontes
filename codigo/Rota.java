import java.time.LocalDate;

/**
 * Classe Rota que representa uma rota de um veículo.
 */
public class Rota {

    // #region Atributos
    private LocalDate data;
    private double quilometragem;
    private Veiculo veiculo;

    // #endregion
    // #region Construtor

    /**
     * Construtor para a rota.
     * 
     * @param data          A data da rota.
     * @param quilometragem A quilometragem da rota.
     */
    public Rota(LocalDate data, double quilometragem) {
        this.data = data;
        this.quilometragem = quilometragem;
    }

    // #endregion
    // #region Métodos

    /**
     * Emite um relatório da rota.
     * 
     * @return Uma string representando o relatório da rota.
     */
    public String emitirRelatorioRota() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório da Rota:\n");
        relatorio.append("   Data: " + data + "\n");
        relatorio.append("   Quilometragem: " + quilometragem + "\n");
        relatorio.append("   Veículo: " + veiculo.getPlaca() + "\n");
        relatorio.append("\n");
        return relatorio.toString();
    }

    // #endregion
    // #region Getters

    /**
     * Retorna a quilometragem da rota.
     * 
     * @return A quilometragem da rota.
     */
    public double getQuilometragem() {
        return quilometragem;
    }

    /**
     * Retorna a data da rota.
     * 
     * @return A data da rota.
     */
    public LocalDate getData() {
        return data;
    }
    // #endregion

}


import java.time.LocalDate;

/**
 * Classe Rota que representa uma rota de um veículo.
 */
public class Rota implements Relatorio{

    // #region Atributos
    private LocalDate data;
    private double quilometragem;
    private boolean rotaPercorrida;


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
        this.rotaPercorrida = false;
    }

    // #endregion
    // #region Métodos

    /* Altera rotaPercorrida para verdadeiro.
     */
    public void percorrerRota() {
    this.rotaPercorrida = true;
    }

    // #endregion
    // #region Getters

    /**
     * Retorna se a rota foi percorrida.
     * 
     * @return Valor booleano indicando se a rota foi percorrida ou não.
     */
    public boolean getRotaPercorrida() {
        return rotaPercorrida;
    }

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

        // #region Relatório
    /**
     * Retorna uma representação em formato de string dos atributos da rota.
     * 
     * @return Uma string formatada com informações da rota.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("   Data: " + getData() + "\n");
        relatorio.append("   Quilometragem: " + getQuilometragem());
        relatorio.append("Rota já foi percorrida: ").append(rotaPercorrida ? "Sim" : "Não").append("\n");
        return relatorio.toString();
}

    @Override
    public String toString() {
        return "Rota: data=" + data + ", quilometragem=" + quilometragem + ", rotaPercorrida=" + rotaPercorrida;
    }



    //#endregion

}

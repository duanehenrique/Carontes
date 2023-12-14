
import java.time.LocalDate;

/**
 * Classe Rota que representa uma rota de um veículo.
 */
public class Rota {

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

}

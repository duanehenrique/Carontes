
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Rota que representa uma rota de um veículo.
 */
public class Rota implements Relatorio{

    // #region Atributos
    private List<Passageiro> passageiros;
    private int qtdPassageirosLevados;
    private double quilometragem;
    private boolean rotaPercorrida;


    // #endregion
    // #region Construtor

    /**
     * Construtor para a rota.
     * 
     * @param quilometragem A quilometragem da rota.
     */
    public Rota(double quilometragem, List<Passageiro> passageiros) {
        this.quilometragem = quilometragem;
        this.passageiros = passageiros;
        this.rotaPercorrida = false;
        this.passageiros = passageiros;
    }

    public Rota(Rota outraRota) {
        this.quilometragem = outraRota.quilometragem;
        this.passageiros = new ArrayList<>(outraRota.passageiros);
        this.qtdPassageirosLevados = outraRota.qtdPassageirosLevados;
        this.rotaPercorrida = outraRota.rotaPercorrida;
    }


    // #endregion
    // #region Métodos

    /* Altera rotaPercorrida para verdadeiro.
     */
    public int percorrerRota(int capacidadeMaximaPassageiros) {
    this.rotaPercorrida = true;
    this.qtdPassageirosLevados = capacidadeMaximaPassageiros;
    return calcularTotalAlmas(capacidadeMaximaPassageiros);
    }

    private int calcularTotalAlmas(int capacidadeMaximaPassageiros) {
        int totalAlmas = 0;
        for (Passageiro passageiro : passageiros) {
            if (capacidadeMaximaPassageiros > 0) {
                totalAlmas += passageiro.getAlmas();
                capacidadeMaximaPassageiros--;
            } else {
                break; // Capacidade máxima de passageiros atingida
            }
        }
        return totalAlmas;
    }    // #endregion
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

    public int getQtdTotalPassageiros(){
        return passageiros.size();
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
        relatorio.append("   Quilometragem: ").append(getQuilometragem()).append("\n");
        relatorio.append("   Quantidade Total de Passageiros: ").append(passageiros.size()).append("\n");        
        if(getRotaPercorrida()){
            relatorio.append("   Quantidade Total de Almas: ").append(calcularTotalAlmas(passageiros.size())).append("\n");
            relatorio.append("   Quantidade de Passageiros levados: " + qtdPassageirosLevados).append("\n");
            relatorio.append("   Quantidade Total de Almas Coletadas: ").append(calcularTotalAlmas(qtdPassageirosLevados)).append("\n");
            relatorio.append("   Passageiros na Rota: \n");
            int numeroPassageiro = 1;
            for (Passageiro passageiro : passageiros) {
                relatorio.append(numeroPassageiro).append(passageiro.relatorio()).append("\n");
                numeroPassageiro++;
            }
        }
    
        return relatorio.toString();
    }
    //#endregion

}

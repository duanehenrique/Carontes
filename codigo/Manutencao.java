import java.text.Normalizer;

/**
 * Classe que representa a manutenção de um veículo.
 */
public class Manutencao {
    // #region Atributos

    private boolean manutencaoPeriodicaEmDia;
    private boolean manutencaoPecasEmDia;
    private double kmDesdeUltimaManutencao;
    private double totalDespesasManutencao;
    private final KmManutencao km;
    private final double custo;

    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Manutencao.
     *
     * @param tipoVeiculo O tipo de veículo.
     * @param custo       O custo da manutenção.
     */
    public Manutencao(Veiculo veiculo, double custo) {
        this.manutencaoPeriodicaEmDia = true;
        this.manutencaoPecasEmDia = true;
        this.kmDesdeUltimaManutencao = 0;
        this.custo = custo;
        if(veiculo instanceof Carro){
        km = KmManutencao.CARRO;
        } else if(veiculo instanceof Van){
        km = KmManutencao.VAN;
        }else if(veiculo instanceof Furgao){
        km = KmManutencao.FURGAO;
        }else if(veiculo instanceof Caminhao){
        km = KmManutencao.CAMINHAO;
        }else{
                throw new IllegalArgumentException("Não foi possível criar o veículo desejado.");

        }
    }

    // #endregion

    // #region Métodos

    /**
     * Retorna a quilometragem desde a última manutenção.
     *
     * @return A quilometragem desde a última manutenção.
     */
    public double getKmDesdeUltimaManutencao() {
        return kmDesdeUltimaManutencao;
    }

    /**
     * Verifica se a manutenção está em dia.
     *
     * @return Verdadeiro se a manutenção está em dia, falso caso contrário.
     */
    public boolean getManutencaoEmDia() {
        return (manutencaoPeriodicaEmDia && manutencaoPecasEmDia);
    }

    /**
     * Verifica se a manutenção periódica está em dia.
     *
     * @return Verdadeiro se a manutenção periódica está em dia, falso caso
     *         contrário.
     */
    public boolean getManutencaoPeriodicaEmDia() {
        return manutencaoPeriodicaEmDia;
    }

    /**
     * Verifica se a manutenção de peças está em dia.
     *
     * @return Verdadeiro se a manutenção de peças está em dia, falso caso
     *         contrário.
     */
    public boolean getManutencaoPecasEmDia() {
        return manutencaoPecasEmDia;
    }

    /**
     * Adiciona quilometragem para a manutenção.
     *
     * @param km A quilometragem a ser adicionada.
     */
    public void addKmParaManutencao(double km) {
        this.kmDesdeUltimaManutencao += km;
        manutencaoEstaEmDia();
    }

    /**
     * Verifica se a manutenção está em dia.
     */
    private void manutencaoEstaEmDia() {
        if (kmDesdeUltimaManutencao >= km.getManutencaoPecas()) {
            this.manutencaoPecasEmDia = false;
        }
        if (kmDesdeUltimaManutencao >= km.getManutencaoPeriodica()) {
            this.manutencaoPeriodicaEmDia = false;
        }
    }

    /**
     * Retorna o total de despesas de manutenção.
     *
     * @return O total de despesas de manutenção.
     */
    public double getTotalDespesasManutencao() {
        return totalDespesasManutencao;
    }

    /**
     * Realiza a manutenção de peças.
     *
     * @return O custo da manutenção.
     */
    public double realizarManutencaoPecas() {
        if (!manutencaoPecasEmDia) {
            totalDespesasManutencao += custo;
            manutencaoPecasEmDia = true;
            kmDesdeUltimaManutencao = 0;
        }
        return custo;
    }

    /**
     * Realiza a manutenção periódica.
     *
     * @return O custo da manutenção.
     */
    public double realizarManutencaoPeriodica() {
        if (!manutencaoPeriodicaEmDia) {
            totalDespesasManutencao += custo;
            manutencaoPeriodicaEmDia = true;
            kmDesdeUltimaManutencao = 0;
        }
        return custo;
    }

    // #endregion
}
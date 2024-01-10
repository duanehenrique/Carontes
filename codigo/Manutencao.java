/**
 * Classe que representa a manutenção de um veículo.
 */
public class Manutencao {
    // #region Atributos

    private boolean manutencaoEmDia;
    private double kmDesdeUltimaManutencao;
    private double totalDespesasManutencao;
    private final KmManutencao km;
    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Manutencao.
     *
     * @param tipoVeiculo O tipo de veículo.
     * @param custo       O custo da manutenção.
     */
    public Manutencao(Barco barco) {
        this.manutencaoEmDia = true;
        this.kmDesdeUltimaManutencao = 0;
        if(barco instanceof Gondola){
        km = KmManutencao.GONDOLA;
        } else if(barco instanceof Balsa){
        km = KmManutencao.BALSA;
        }else if(barco instanceof Navio){
        km = KmManutencao.NAVIO;
        }else if(barco instanceof Cruzeiro){
        km = KmManutencao.CRUZEIRO;
        }else{
                throw new IllegalArgumentException("Este barco não existe e seus senhores não estão contentes.");

        }
    }

    public Manutencao(Manutencao outraManutencao) {
        this.manutencaoEmDia = outraManutencao.manutencaoEmDia;
        this.kmDesdeUltimaManutencao = outraManutencao.kmDesdeUltimaManutencao;
        this.totalDespesasManutencao = outraManutencao.totalDespesasManutencao;
        this.km = outraManutencao.km;
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
        return (manutencaoEmDia);
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
        if (kmDesdeUltimaManutencao >= km.getManutencaoPeriodica()) {
            this.manutencaoEmDia = false;
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

    public double getKmParaManutencao(){
        return km.getManutencaoPeriodica();
    }

    public double getKmAteProximaManutencao(){
    return (getKmParaManutencao() - getKmDesdeUltimaManutencao());
    }

    /**
     * Realiza a manutenção periódica.
     *
     * @return O custo da manutenção.
     */
    public int realizarManutencao() {
        if (!manutencaoEmDia) {
            manutencaoEmDia = true;
            kmDesdeUltimaManutencao = 0;
            return km.getCustoManutencao();
        } else{
            throw new IllegalArgumentException("O barco está com a papelada em dia e não precisa realizar manutenção.");
        }
    }

    // #endregion
}
/**
 * Classe Caminhao que estende a classe Veiculo.
 * Esta classe representa um caminhão no sistema.
 */
public class Cruzeiro extends BarcoComTanque{

    // #region Atributos
    private static final int TANQUE_MAX = 250;
    private static final int TOTAL_PASSAGEIROS = 40;
    private static final int CUSTO_COMPRA = 100;
    private final Tanque tanque;
    // #endregion

    // #region Construtor
    /**
     * Construtor para a classe Caminhao.
     * 
     * @param motorista       O motorista do caminhão.
     * @param placa           A placa do caminhão.
     * @param tipoCombustivel O tipo de combustível que o caminhão usa.
     * @param custoManutencao O custo de manutenção do caminhão.
     */
    public Cruzeiro(Caronte motorista, String nome, String tipoCombustivel, int capacidadeTanque, int capacidade, int preco, int qtdRotas) {
        super(motorista, nome, tipoCombustivel, capacidadeTanque, capacidade, preco, qtdRotas);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this);
}

public Cruzeiro(Cruzeiro outroCruzeiro) {
    super(outroCruzeiro.clonarMotorista(), outroCruzeiro.getNOME(), outroCruzeiro.getTanque().getTipo().getTipo(), outroCruzeiro.getTanque().getCapacidadeMaxima(), outroCruzeiro.getCAPACIDADEPASSAGEIROS(), outroCruzeiro.getPRECOCUSTO(), outroCruzeiro.MAX_ROTAS_DIA);

    this.tanque = new Tanque(outroCruzeiro.tanque.getCapacidadeMaxima(), outroCruzeiro.getTanque().getTipo().getTipo());
    this.manutencao = new Manutencao(outroCruzeiro.getManutencao());
    this.despesaMulta = outroCruzeiro.getDespesaMulta();
    this.despesaManutencao = outroCruzeiro.getDespesaManutencao();
    this.despesaSalario = outroCruzeiro.getDespesaSalario();
    this.totalAlmasColetadasDia = outroCruzeiro.getTotalAlmasColetadasDia();
    this.despesaCombustivel = outroCruzeiro.getDespesaCombustivel();

    
        for (Rota rota : outroCruzeiro.getRotas()) {
        outroCruzeiro.addRota(new Rota(rota));
        outroCruzeiro.getMotorista().fazerViagem();     
    }

    Caronte motorista = this.getMotorista();
    for (Multa multa : motorista.listarMultas()) {
        outroCruzeiro.getMotorista().adicionarMulta(multa); 
    }
}


}
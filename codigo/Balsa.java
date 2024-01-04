/**
 * Classe Van que estende a classe Veículo.
 */
public class Balsa extends BarcoComTanque{

    // #region Atributos
    private static final int TANQUE_MAX = 60;
    private static final int TOTAL_PASSAGEIROS = 10;
    private static final int CUSTO_COMPRA = 30;


    // #endregion

    // #region Construtor
    /**
     * Construtor de Van.
     * @param motorista O motorista da Van.
     * @param placa A placa da Van.
     * @param tipoCombustivel O tipo de combustível da Van.
     * @param custoManutencao O custo de manutenção da Van.
     */
    public Balsa(Caronte motorista, String nome, String tipoCombustivel, int capacidadeTanque, int capacidade, int preco, int qtdRotas) {
        super(motorista, nome, tipoCombustivel, capacidadeTanque, capacidade, preco, qtdRotas);
        this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
        this.manutencao = new Manutencao(this);
    }

    public Balsa(Balsa outraBalsa) {
        super(outraBalsa.clonarMotorista(), outraBalsa.getNOME(), outraBalsa.getTanque().getTipo().getTipo(), outraBalsa.getTanque().getCapacidadeMaxima(), outraBalsa.getCAPACIDADEPASSAGEIROS(), outraBalsa.getPRECOCUSTO(), outraBalsa.MAX_ROTAS_DIA);
        
        this.tanque = new Tanque(outraBalsa.tanque.getCapacidadeMaxima(), outraBalsa.getTanque().getTipo().getTipo());        
        this.manutencao = new Manutencao(outraBalsa.getManutencao());
    }
    
}
import java.util.List;

/**
 * Classe Carro que estende a classe Veiculo.
 * Esta classe representa um carro no sistema.
 */
public class Gondola extends Barco {

    // #region Atributos
    private static final int TOTAL_PASSAGEIROS = 4;
    private static final int CUSTO_COMPRA = 10;
    private static final int AUTONOMIA = 10;

    // #endregion

    // #region Construtor
    // Construtor de Carro
    /**
     * Construtor para a classe Carro.
     * 
     * @param motorista       O motorista do carro.
     * @param placa           A placa do carro.
     * @param tipoCombustivel O tipo de combustível que o carro usa.
     * @param custoManutencao O custo de manutenção do carro.
     */
    public Gondola(Caronte motorista, String nome, int qtdRotas) {
        super(motorista, nome, TOTAL_PASSAGEIROS, CUSTO_COMPRA, qtdRotas);
        this.manutencao = new Manutencao(this);
    }

    public Gondola(Gondola outraGondola) {
        super(outraGondola.clonarMotorista(), outraGondola.getNOME(), outraGondola.getCAPACIDADEPASSAGEIROS(), outraGondola.getPRECOCUSTO(), outraGondola.getMAX_ROTAS_DIA());
        
    this.manutencao = new Manutencao(outraGondola.getManutencao());
    this.despesaMulta = outraGondola.getDespesaMulta();
    this.despesaManutencao = outraGondola.getDespesaManutencao();
    this.despesaSalario = outraGondola.getDespesaSalario();
    this.totalAlmasColetadasDia = outraGondola.getTotalAlmasColetadasDia();
    
        for (Rota rota : outraGondola.getRotas()) {
        outraGondola.addRota(new Rota(rota));
        outraGondola.getMotorista().fazerViagem();
    }

    Caronte motorista = this.getMotorista();
    for (Multa multa : motorista.listarMultas()) {
        outraGondola.getMotorista().adicionarMulta(multa); 
    }
    }


    // #endregion

    // #region Métodos

    public double getAutonomia(){
        return AUTONOMIA;
    }

    }
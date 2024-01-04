public class Navio extends BarcoComTanque{
        // #region Atributos
        private static final int TANQUE_MAX = 80;
        private static final int TOTAL_PASSAGEIROS = 20;
        private static final int CUSTO_COMPRA = 65;
        private final Tanque tanque;

        // #endregion
        // #region Construtores
        /**
         * Construtor da classe Furgao.
         * 
         * @param motorista       O motorista do furgão.
         * @param placa           A placa do furgão.
         * @param tipoCombustivel O tipo de combustível do furgão.
         * @param custoManutencao O custo de manutenção do furgão.
         */
        public Navio(Caronte motorista, String nome, String tipoCombustivel, int capacidadeTanque, int capacidade, int preco, int qtdRotas) {
            super(motorista, nome, tipoCombustivel, capacidadeTanque, capacidade, preco, qtdRotas);
            this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
            this.manutencao = new Manutencao(this);
    }

    public Navio(Navio outroNavio) {
        super(outroNavio.clonarMotorista(), outroNavio.getNOME(), outroNavio.getTanque().getTipo().getTipo(), outroNavio.getTanque().getCapacidadeMaxima(), outroNavio.getCAPACIDADEPASSAGEIROS(), outroNavio.getPRECOCUSTO(), outroNavio.MAX_ROTAS_DIA);
    
        this.tanque = new Tanque(outroNavio.tanque.getCapacidadeMaxima(), outroNavio.getTanque().getTipo().getTipo());
        this.manutencao = new Manutencao(outroNavio.getManutencao());
        this.despesaMulta = outroNavio.getDespesaMulta();
        this.despesaManutencao = outroNavio.getDespesaManutencao();
        this.despesaSalario = outroNavio.getDespesaSalario();
        this.totalAlmasColetadasDia = outroNavio.getTotalAlmasColetadasDia();
        this.despesaCombustivel = outroNavio.getDespesaCombustivel();
    
        
            for (Rota rota : outroNavio.getRotas()) {
            outroNavio.addRota(new Rota(rota));
            outroNavio.getMotorista().fazerViagem();     
        }
    
        Caronte motorista = this.getMotorista();
        for (Multa multa : motorista.listarMultas()) {
            outroNavio.getMotorista().adicionarMulta(multa); 
        }
   
    }
    

}
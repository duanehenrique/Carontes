public class Navio extends BarcoComTanque{
        // #region Atributos
        private static final double TANQUE_MAX = 80;
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
}
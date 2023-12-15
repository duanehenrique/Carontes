public class Furgao extends Veiculo {
        // #region Atributos
        private static final double TANQUE_MAX = 80;

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
        public Furgao(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
                super(motorista, placa);
                this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
                this.manutencao = new Manutencao(this, custoManutencao);
                // #endregion
        }
}

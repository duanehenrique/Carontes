public class Furgao extends Veiculo {
        // Atributos específicos de Furgão
        private static final double KM_MANUTENCAO_PERIODICA = 10000;
        private static final double KM_MANUTENCAO_PECAS = 12000;
        private static final double TANQUE_MAX = 80;
    
        // Construtor de Furgão
         public Furgao(Motorista motorista, String placa, String tipoCombustivel) {
            super(motorista, placa);
            this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
            // Inicialmente, o tanque está vazio
    }
}

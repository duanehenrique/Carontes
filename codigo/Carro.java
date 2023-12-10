    public class Carro extends Veiculo {
        // Atributos específicos de Carro
        private static final double KM_MANUTENCAO_PERIODICA = 10000;
        private static final double KM_MANUTENCAO_PECAS = 10000;
        private static final double TANQUE_MAX = 50;
    
        // Construtor de Carro
        public Carro(Motorista motorista, String placa, String tipoCombustivel) {
            super(motorista, placa);
            this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
            // Inicialmente, o tanque está vazio
    }
}

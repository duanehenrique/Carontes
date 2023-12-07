public class Furgao {
    public class Furgao extends Veiculo {
        // Atributos específicos de Furgão
        private static final double KM_MANUTENCAO_PERIODICA = 10000;
        private static final double KM_MANUTENCAO_PECAS = 12000;
        private static final double TANQUE_MAX_FURGAO = 80;
    
        // Construtor de Furgão
        public Furgao(Motorista motorista, String placa) {
            super(motorista, placa);
        }
    }
}

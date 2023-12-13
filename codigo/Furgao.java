public class Furgao extends Veiculo {
        // Atributo específico de Furgão
        private static final double TANQUE_MAX = 80;
    
        // Construtor de Furgão
         public Furgao(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
            super(motorista, placa);
            this.tanque = new Tanque(TANQUE_MAX, tipoCombustivel);
            // Inicialmente, o tanque está vazio
            this.manutencao = new Manutencao("Furgão", custoManutencao);

    }
}

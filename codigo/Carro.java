    public class Carro extends Veiculo {
        // Atributo específico de Carro
        private static final double TANQUE_MAX = 50;
    
        // Construtor de Carro
        public Carro(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
            super(motorista, placa);
            this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
            // Inicialmente, o tanque está vazio
            this.manutencao = new Manutencao("Carro", custoManutencao);
    }
}

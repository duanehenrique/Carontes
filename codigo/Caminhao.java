public class Caminhao extends Veiculo {
    // Atributo específico de Caminhão
    private static final double TANQUE_MAX = 250;

    // Construtor de Caminhão
    public Caminhao(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
            super(motorista, placa);
        this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
        // Inicialmente, o tanque está vazio
        this.manutencao = new Manutencao("Caminhão", custoManutencao);

    }
}
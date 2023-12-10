public class Caminhao extends Veiculo {
    // Atributos específicos de Caminhão
    private static final double KM_MANUTENCAO_PERIODICA = 20000;
    private static final double KM_MANUTENCAO_PECAS = 20000;
    private static final double TANQUE_MAX = 250;

    // Construtor de Caminhão
    public Caminhao(Motorista motorista, String placa, String tipoCombustivel) {
            super(motorista, placa);
        this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
        // Inicialmente, o tanque está vazio
    }
}
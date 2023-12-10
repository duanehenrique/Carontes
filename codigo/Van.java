public class Van extends Veiculo {
    // Atributos específicos de Van
    private static final double KM_MANUTENCAO_PERIODICA = 10000;
    private static final double KM_MANUTENCAO_PECAS = 12000;
    private static final double TANQUE_MAX = 60;

    // Construtor de Van
    public Van(Motorista motorista, String placa, String tipoCombustivel) {
        super(motorista, placa);
        this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
        // Inicialmente, o tanque está vazio
    }
}
public class Van extends Veiculo {
    // Atributos específicos de Van
    private static final double KM_MANUTENCAO_PERIODICA = 10000;
    private static final double KM_MANUTENCAO_PECAS = 12000;
    private static final double TANQUE_MAX_VAN = 60;

    // Construtor de Van
    public Van(Motorista motorista, String placa) {
        super(motorista, placa);
    }
}
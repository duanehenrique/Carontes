public class Van extends Veiculo {
    // Atributo específico de Van
    private static final double TANQUE_MAX = 60;

    // Construtor de Van
    public Van(Motorista motorista, String placa, String tipoCombustivel, double custoManutencao) {
        super(motorista, placa);
        this.tanque = new Tanque(0, TANQUE_MAX, tipoCombustivel);
        // Inicialmente, o tanque está vazio
        this.manutencao = new Manutencao("Van", custoManutencao);
    }
}
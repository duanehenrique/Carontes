public enum KmManutencao {
    CARRO(10000, 10000), // A quilometragem para manutenção periódica e para troca de peças para carro
    VAN(10000, 12000), // A quilometragem para manutenção periódica e para troca de peças para van
    FURGAO(10000, 12000), // A quilometragem para manutenção periódica e para troca de peças para furgão
    CAMINHAO(20000, 20000); // A quilometragem para manutenção periódica e para troca de peças para caminhão

    private final int manutencaoPeriodica; // Atributo para a quilometragem necessária para manutenção periódica
    private final int manutencaoPecas; // Atributo para a quilometragem necessária para manutenção para troca de peças

    KmManutencao(int manutencaoPeriodica, int manutencaoPecas) {
        this.manutencaoPeriodica = manutencaoPeriodica;
        this.manutencaoPecas = manutencaoPecas;
    }

    public double getManutencaoPeriodica() {
        return manutencaoPeriodica;
    }

    public double getManutencaoPecas() {
        return manutencaoPecas;
    }
}
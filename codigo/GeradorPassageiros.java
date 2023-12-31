import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorPassageiros {

    public List<Passageiro> gerarPassageiros(int qtdPassageiros, int km) {
        List<Passageiro> passageiros = new ArrayList<>();

        for (int i = 0; i < qtdPassageiros; i++) {
            Passageiro passageiro = gerarPassageiro(km);
            passageiros.add(passageiro);
        }

        return passageiros;
    }

    private Passageiro gerarPassageiro(int km) {
        double valorPecado = (double) (km / 9) * (new Random().nextDouble() * (new Random().nextInt(5) + 1));
        GeradorNomePassageiro geradorNomePassageiro = new GeradorNomePassageiro();
        String nome = geradorNomePassageiro.gerarNomePassageiro();

        return new Passageiro(nome, obterTipoPecado((int)valorPecado));
    }

    private String obterTipoPecado(int valorPecado) {
        switch (valorPecado) {
            case 1:
                return "HEROI";
            case 2:
                return "LIMBO";
            case 3:
                return "LUXURIA";
            case 4:
                return "GULA";
            case 5:
                return "AVAREZA";
            case 6:
                return "IRA";
            case 7:
                return "HERESIA";
            case 8:
                return "VIOLENCIA";
            case 9:
                return "FRAUDE";
            case 10:
                return "TRAICAO";
            default:
                return "TRAICAO";
        }
    }
}

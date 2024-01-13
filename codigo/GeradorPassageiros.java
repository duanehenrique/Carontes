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
                return "Herói nobre livre de pecados. Enviar aos Campos Elíseos.";
            case 2:
                return "Comportamento Digno do Limbo";
            case 3:
                return "Luxúria";
            case 4:
                return "Gula";
            case 5:
                return "Avareza";
            case 6:
                return "Ira";
            case 7:
                return "Heresia";
            case 8:
                return "Violencia";
            case 9:
                return "Fraude";
            case 10:
                return "Traição";
            default:
                return "Traição";
        }
    }
}

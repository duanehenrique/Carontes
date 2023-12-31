import java.util.Random;

public class GuardaDeTransito {

    public Multa verificarMulta(Barco barco, Rota rota) {
        double chanceBase = barco.getMotorista().getProbabilidade();
        double chanceComCapacidade = calcularChanceComCapacidade(barco.getCAPACIDADEPASSGEIROS());
        double chanceTotal = (chanceBase * chanceComCapacidade);
        return aplicarMulta(chanceTotal);
    }

    private double calcularChanceComCapacidade(int capacidadePassageiros) {
        if (capacidadePassageiros >= 80) {
            return 1.5;
        }
        return 0.0;
    }

    private boolean sorteio(double chance) {
        Random random = new Random();
        return random.nextDouble() > chance;
    }

    private Multa aplicarMulta(double chanceTotal) {
        if (sorteio(chanceTotal)) {
            if (chanceTotal > 0.8) {
                return Multa.GRAVISSIMA;
            } else if (chanceTotal > 0.6) {
                return Multa.GRAVE;
            } else if (chanceTotal > 0.4) {
                return Multa.MEDIA;
            } else{
                return Multa.LEVE;
            }
        }
        return null;
    }
}
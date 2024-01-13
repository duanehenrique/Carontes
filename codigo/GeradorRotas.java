import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorRotas {

    public List<Rota> gerarRotas(Frota frota) {
        List<Rota> rotas = new ArrayList<>();
        List<DiarioDeBordo> diariosDeBordo = frota.getDiariosdeBordo();

        int somaCapacidade = 0;
        int somaNiveis = 0;

        for (DiarioDeBordo diarioDeBordo : diariosDeBordo) {
            Barco barco = diarioDeBordo.getBarcoDoDiario();
            somaCapacidade += barco.getCAPACIDADEPASSAGEIROS();
            somaNiveis += barco.getMotorista().getNivel();
        }

        if (diariosDeBordo != null && !diariosDeBordo.isEmpty()) {
            int quantidadeRotas = diariosDeBordo.size() * 5;
            for (int i = 0; i < quantidadeRotas; i++) {
                Rota rota = gerarRota(somaCapacidade, somaNiveis, diariosDeBordo.size());
                rotas.add(rota);
            }
                    return rotas;
        }else{
            throw new IllegalArgumentException("Você não tem barco na sua Frota. Como eu não sei...");
        }

    }

    private Rota gerarRota(int somaCapacidade, int somaNiveis, int tamanhoFrota) {
        int passageiros = calcularPassageiros(somaCapacidade, somaNiveis, tamanhoFrota);
        int km = calcularKm(somaCapacidade, somaNiveis, tamanhoFrota);

        GeradorPassageiros geradorPassageiros = new GeradorPassageiros();
        List<Passageiro> listaPassageiros = geradorPassageiros.gerarPassageiros(passageiros, km);

        return new Rota(km, listaPassageiros);
    }

    private int calcularPassageiros(int somaCapacidade, int somaNiveis, int tamanhoFrota) {
        double random = (Math.random() + Math.random()+0.1)*2 ;
        return (int) (Math.max(1, ((double) somaCapacidade + somaNiveis/ (double) tamanhoFrota * random)));
    }

    private int calcularKm(int somaCapacidade, int somaNiveis, int tamanhoFrota) {
        int randomInt = new Random().nextInt(10) + 1;
        return (somaCapacidade / tamanhoFrota) * randomInt / somaNiveis;
    }
}

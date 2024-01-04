import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Custos {
    private List<Double> almasPorDia;
    private double almas;

    public Custos (){
        List<Double> almasPorDias = new ArrayList<>();
        almasPorDias.add(0.0); 
        this.almas = 0;
    }


    public double getAlmas() {
        return almas;
    }

    public double getAlmasDeHoje(){
       return almasPorDia.get((almasPorDia.size() -1));
    }

    public void coletarAlmas(double almas){
        addAlmasAoDia(almas);
        almas += almas;
    }

    public void addAlmasAoDia(double almas){
        almasPorDia.add((almasPorDia.size() -1), almas);
    }

    public void encerrarDia(){
        almasPorDia.add(almasPorDia.size(), 0.0);
    }   
    
    public static double comprarBarco(Barco barco, Jogador jogador) {
         try {
        double custoTotal = barco.getPRECOCUSTO();
        double almasJogador = jogador.getAlmas();
        if(custoTotal <= almasJogador){
            if (barco instanceof BarcoComTanque) {
            custoTotal += ((BarcoComTanque) barco).getTanque().getAdicionalPrecoVenda();
            }
            return custoTotal;
        }else {
            throw new IllegalArgumentException("A frota do gerente " + jogador.getNomePersonagem() +
                "não tem fundos suficientes\\n" + "para adquirir esse barco. O setor de aquisições sentiu que \nperdeu tempo" +
                " preenchendo todos os formulários para nada. Vá trabalhar e volte quando tiver " + custoTotal + " almas.");
            }
    }catch(Exception e){
        return 0;
    }
}
        public double executarTransacao(Function<Frota, Double> funcao, Frota frota, Jogador jogador) {
            try {
                Frota frotaClone = clonarFrota(frota);
                double custoTotal = funcao.apply(frotaClone);
                if (custoTotal <= jogador.getAlmas()) {
                    custoTotal = funcao.apply(frota);
                    jogador.consumirAlmas(custoTotal);
                    return custoTotal;
                } else {
                    frotaClone = null;
                    throw new IllegalArgumentException("Você não tem almas suficientes para realizar essa transação.");
                }
            } catch (Exception e) {
                                    return 0;
            }
        } 

    private static Frota clonarFrota(Frota frotaOriginal){
        Frota frotaClone = new Frota(frotaOriginal);
        return frotaClone;
    }
               
}

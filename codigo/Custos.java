import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.BiFunction;


public class Custos {
    private List<Double> almasPorDia;
    private double almas;

    public Custos (){
        List<Double> almasPorDias = new ArrayList<>();
        almasPorDias.add(0.0); 
        this.almas = 0;
    }


    public double getAlmasDeHoje() {
        return almas;
    }

    public double getAlmasDeOntem() {
        return  almasPorDia.get(almasPorDia.size() - 1);
    }

    public void coletarAlmas(double almas){
        almas += almas;
    }

    public void encerrarDia(){
        almasPorDia.add((almasPorDia.size() - 1), almas);
        almasPorDia.add(almasPorDia.size(), 0.0);
        almas = 0;
    }   
    
    public double comprarBarco(Barco barco, Jogador jogador) throws Exception {
        double custoTotal = 0; 
        try {
         custoTotal += barco.getPRECOCUSTO();
        double almasJogador = jogador.getAlmas();
        if (barco instanceof BarcoComTanque) {
            custoTotal += ((BarcoComTanque) barco).getTanque().getAdicionalPrecoVenda();
        }
        if(custoTotal <= almasJogador){
            jogador.consumirAlmas(custoTotal);
            coletarAlmas(custoTotal);
            return custoTotal;
        }else {
            throw new Exception("A frota do gerente " + jogador.getNomePersonagem() +
                    " não tem fundos suficientes.\n" +
                    "Para adquirir esse barco. O setor de aquisições sentiu que \nperdeu tempo" +
                    " preenchendo todos os formulários para nada. Vá trabalhar e volte quando tiver " + custoTotal + " almas.");
        }
    } catch (Exception e) {
        throw e;
    }
}

    public double executarTransacaoEspecifica(List<Object> objetos, int funcao, Frota frota, Jogador jogador) {
                try {
        Frota frotaClone = clonarFrota(frota);
        Executor executor = new Executor();
       double custoTotal = (Double) executor.executarAcaoComAlmasEspecifica(frotaClone, funcao, objetos);

        if (custoTotal <= jogador.getAlmas()) {
            executor.executarAcaoComAlmasEspecifica(frota, funcao, objetos);
            jogador.consumirAlmas(custoTotal);
            coletarAlmas(custoTotal);
            return custoTotal;
        } else {
            frotaClone = null;
            throw new IllegalArgumentException("Você não tem almas suficientes para realizar essa transação. Almas em estoque: " + jogador.getAlmas());
        }
    } catch (Exception e) {
        return 0;
    }
}


    public double executarTransacaoGeral(int funcao, Frota frota, Jogador jogador) {
                try {
        Frota frotaClone = clonarFrota(frota);
        Executor executor = new Executor();
       double custoTotal = (Double) executor.executarAcaoComAlmasGeral(frotaClone, funcao);

        if (custoTotal <= jogador.getAlmas()) {
            executor.executarAcaoComAlmasGeral(frota, funcao);
            jogador.consumirAlmas(custoTotal);
            coletarAlmas(custoTotal);
            return custoTotal;
        } else {
            frotaClone = null;
            throw new IllegalArgumentException("Você não tem almas suficientes para realizar essa transação. Almas em estoque: " + jogador.getAlmas());
        }
    } catch (Exception e) {
        return 0;
    }
}
    private Frota clonarFrota(Frota frotaOriginal){
        Frota frotaClone = new Frota(frotaOriginal);
        return frotaClone;
    }


}




import java.util.ArrayList;
import java.util.List;
public class Jogador {
    private final String nomePersonagem;
    private List<Double> almasPorDia;
    private double almas;

    public Jogador (String nomePersonagem){
        this.nomePersonagem = nomePersonagem;
        this.almasPorDia = new ArrayList<>();
        this.almas = 0;
        almasPorDia.add(almas); 
    }

    public String getNomePersonagem() {
        return nomePersonagem;
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

    public void consumirAlmas(double almas){
     if((this.almas - almas) > 0){
        throw new IllegalArgumentException("Não há almas suficientes para realizar esta transação. Por favor, vá fazer seu trabalho antes de querer receber!");
    }else{
        almas -= almas;
    }}

    public void addAlmasAoDia(double almas){
        almasPorDia.add((almasPorDia.size() -1), almas);
    }

    public void encerrarDia(){
        almasPorDia.add(almasPorDia.size(), 0.0);
    }
}

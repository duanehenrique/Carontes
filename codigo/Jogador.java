public class Jogador {
    final String nomePersonagem;
    double almas;

    public Jogador (String nomePersonagem){
        this.nomePersonagem = nomePersonagem;
        this.almas = 0;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public double getAlmas() {
        return almas;
    }

    public void coletarAlmas(double almas){
        almas += almas;
    }

    public void consumirAlmas(double almas){
     almas += almas;
    }
}

import java.util.List;

public class Motorista {

    private String nome;
    private String cpf;
    private Carteira carteira;


    public Motorista(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.carteira = new Carteira();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Carteira getCarteira(){
        return carteira;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

     public double pagarMulta(int posicaoMulta) {
        return carteira.pagarMulta(posicaoMulta);
    }

    public double pagarTodasMultas(){
        return carteira.pagarTodasMultas();
    }

    public List<Multa> listarMultas() {
        return carteira.listarMultas();
    }

    public List<Multa> listarMultasAtivas() {
        return carteira.listarMultasAtivas();
    }

    public List<Multa> listarMultasNaoPagas() {
        return carteira.listarMultasNaoPagas();
    }

    public List<Multa> listarMultasPagas() {
        return carteira.listarMultasPagas();
    }

    public int getPontos() {
        return carteira.calcularTotalPontos();
    }

    public void adicionarPontos(String gravidade) {
        this.carteira.adicionarMulta(gravidade);
    }
public boolean getCarteiraValida(){
    return this.carteira.carteiraValida();
}
}

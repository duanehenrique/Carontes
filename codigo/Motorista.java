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

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

public class Motorista {

    private String nome;
    private String cpf;
    private int pontos;

    public Motorista(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.pontos = 0;
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
        return pontos;
    }

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }

    public void removerPontos(int pontos) {
        this.pontos -= pontos;
    }
}

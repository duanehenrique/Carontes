import java.util.List;

/**
 * Classe que representa um motorista.
 */
public class Motorista {
    // #region Atributos
    private String nome;
    private String cpf;
    private CarteiraMotorista carteira;
    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Motorista.
     *
     * @param nome O nome do motorista.
     * @param cpf  O CPF do motorista.
     */
    public Motorista(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.carteira = new CarteiraMotorista();
    }

    // #endregion

    // #region Métodos

    /**
     * Retorna o nome do motorista.
     *
     * @return O nome do motorista.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do motorista.
     *
     * @param nome O novo nome do motorista.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF do motorista.
     *
     * @return O CPF do motorista.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna a carteira de motorista do motorista.
     *
     * @return A carteira de motorista do motorista.
     */
    public CarteiraMotorista getCarteira() {
        return carteira;
    }

    /**
     * Define o CPF do motorista.
     *
     * @param cpf O novo CPF do motorista.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Paga uma multa específica.
     *
     * @param posicaoMulta A posição da multa a ser paga.
     * @return O valor da multa paga.
     */
    public double pagarMulta(int posicaoMulta) {
        return carteira.pagarMulta(posicaoMulta);
    }

    /**
     * Paga todas as multas do motorista.
     *
     * @return O valor total das multas pagas.
     */
    public double pagarTodasMultas() {
        return carteira.pagarTodasMultas();
    }

    /**
     * Lista todas as multas do motorista.
     *
     * @return Uma lista de todas as multas do motorista.
     */
    public List<Multa> listarMultas() {
        return carteira.listarMultas();
    }

    /**
     * Lista todas as multas ativas do motorista.
     *
     * @return Uma lista de todas as multas ativas do motorista.
     */
    public List<Multa> listarMultasAtivas() {
        return carteira.listarMultasAtivas();
    }

    /**
     * Lista todas as multas não pagas do motorista.
     *
     * @return Uma lista de todas as multas não pagas do motorista.
     */
    public List<Multa> listarMultasNaoPagas() {
        return carteira.listarMultasNaoPagas();
    }

    /**
     * Lista todas as multas pagas do motorista.
     *
     * @return Uma lista de todas as multas pagas do motorista.
     */
    public List<Multa> listarMultasPagas() {
        return carteira.listarMultasPagas();
    }

    /**
     * Retorna a multa mais recente do motorista.
     *
     * @return A multa mais recente do motorista.
     */
    public Multa multaMaisRecente() {
        return carteira.multaMaisRecente();
    }

    /**
     * Retorna o total de pontos na carteira do motorista.
     *
     * @return O total de pontos na carteira do motorista.
     */
    public int getPontos() {
        return carteira.calcularTotalPontos();
    }

    /**
     * Adiciona pontos à carteira do motorista.
     *
     * @param gravidade A gravidade da infração.
     */
    public void adicionarPontos(String gravidade) {
        this.carteira.adicionarMulta(gravidade);
    }

    /**
     * Verifica se a carteira do motorista é válida.
     *
     * @return Verdadeiro se a carteira é válida, falso caso contrário.
     */
    public boolean getCarteiraValida() {
        return this.carteira.carteiraValida();
    }

    // #endregion
}

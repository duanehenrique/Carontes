import java.util.List;

/**
 * Classe que representa um motorista.
 */
public class Caronte implements Relatorio{
    // #region Atributos
    private String nome;
    private String cpf;
    private CarteiraMotorista carteira;
    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Caronte.
     *
     * @param nome O nome do Caronte.
     * @param cpf  O CPF do Caronte.
     */
    public Caronte(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.carteira = new CarteiraMotorista();
    }

    // #endregion

    // #region Métodos

    /**
     * Retorna o nome do Caronte.
     *
     * @return O nome do Caronte.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do Caronte.
     *
     * @param nome O novo nome do Caronte.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF do Caronte.
     *
     * @return O CPF do Caronte.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna a carteira de motorista do Caronte.
     *
     * @return A carteira de motorista do Caronte.
     */
    public CarteiraMotorista getCarteira() {
        return carteira;
    }

    /**
     * Define o CPF do Caronte.
     *
     * @param cpf O novo CPF do Caronte.
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
     * Paga todas as multas do Caronte.
     *
     * @return O valor total das multas pagas.
     */
    public double pagarTodasMultas() {
        return carteira.pagarTodasMultas();
    }

    /**
     * Lista todas as multas do Caronte.
     *
     * @return Uma lista de todas as multas do Caronte.
     */
    public List<Multa> listarMultas() {
        return carteira.listarMultas();
    }

    /**
     * Lista todas as multas ativas do Caronte.
     *
     * @return Uma lista de todas as multas ativas do Caronte.
     */
    public List<Multa> listarMultasAtivas() {
        return carteira.listarMultasAtivas();
    }

    /**
     * Lista todas as multas não pagas do Caronte.
     *
     * @return Uma lista de todas as multas não pagas do Caronte.
     */
    public List<Multa> listarMultasNaoPagas() {
        return carteira.listarMultasNaoPagas();
    }

    /**
     * Lista todas as multas pagas do Caronte.
     *
     * @return Uma lista de todas as multas pagas do Caronte.
     */
    public List<Multa> listarMultasPagas() {
        return carteira.listarMultasPagas();
    }

    /**
     * Retorna a multa mais recente do Caronte.
     *
     * @return A multa mais recente do Caronte.
     */
    public Multa multaMaisRecente() {
        return carteira.multaMaisRecente();
    }

    /**
     * Retorna o total de pontos na carteira do Caronte.
     *
     * @return O total de pontos na carteira do Caronte.
     */
    public int getPontos() {
        return carteira.calcularTotalPontos();
    }

    /**
     * Adiciona pontos à carteira do Caronte.
     *
     * @param gravidade A gravidade da infração.
     */
    public void adicionarPontos(String gravidade) {
        this.carteira.adicionarMulta(gravidade);
    }

    /**
     * Verifica se a carteira do Caronte é válida.
     *
     * @return Verdadeiro se a carteira é válida, falso caso contrário.
     */
    public boolean getCarteiraValida() {
        return this.carteira.carteiraValida();
    }

    // #endregion

            // #region Relatorio
        /**
     * Retorna uma representação em string do veículo.
     * 
     * @return Uma string representando o veículo.
     */
    public String relatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório do Caronte:\n");
        relatorio.append("Nome: " + getNome() + "\n");
        relatorio.append("CPF: " + getCpf() + "\n");
        relatorio.append("Carteira de Motorista:\n");
        relatorio.append(carteira.relatorio());
        return relatorio.toString();
    }
    //#endregion
}

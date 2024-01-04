import java.util.List;

/**
 * Classe que representa um motorista.
 */
public class Caronte implements Relatorio{
    // #region Atributos
    private String nome;
    private CarteiraMotorista carteira;
    private Experiencia experiencia;
    private int viagensRestantes;
    private boolean salarioEmDia;
    private final int MAX_ROTAS_DIA;
    // #endregion

    // #region Construtor

    /**
     * Construtor da classe Caronte.
     *
     * @param nome O nome do Caronte.
     * @param cpf  O CPF do Caronte.
     */
    public Caronte(String nome, int nivel, int qtdRotas) {
        this.nome = nome;
        this.carteira = new CarteiraMotorista();
        this.experiencia = criarExperiencia(nivel);
        this.MAX_ROTAS_DIA = qtdRotas;
        this.viagensRestantes = qtdRotas;
        this.salarioEmDia = true;
    }
    
        // Construtor de cópia
        public Caronte(Caronte outroCaronte) {
            this.nome = outroCaronte.nome;
            this.carteira = new CarteiraMotorista(outroCaronte.carteira); // Supondo que CarteiraMotorista tenha um construtor de cópia
            this.experiencia = outroCaronte.experiencia; // Supondo que Experiencia tenha um construtor de cópia
            this.MAX_ROTAS_DIA = outroCaronte.MAX_ROTAS_DIA;
            this.viagensRestantes = outroCaronte.viagensRestantes;
        }
    
        // Outros membros da classe...
    

    private Experiencia criarExperiencia(int nivelExperiencia) {
        for (Experiencia experiencia : Experiencia.values()) {
            if (nivelExperiencia == experiencia.getNivel()) {
                return experiencia;
            }
        }
        throw new IllegalArgumentException("Nível de experiência de Caronte inválido: " + nivelExperiencia);
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
     * Retorna a carteira de motorista do Caronte.
     *
     * @return A carteira de motorista do Caronte.
     */
    public CarteiraMotorista getCarteira() {
        return carteira;
    }

    public void fazerViagem(){
        if(getViagensRestantes() > 4){
            throw new IllegalArgumentException("Caronte está fazendo mais viagens que permitido. O sindicato está descontente.");
        }else if(getViagensRestantes() > 0){
            viagensRestantes--;
        }else{
            throw new IllegalArgumentException("Caronte já fez todas as viagens de hoje. O sindicato só permitirá que ele volte a trabalhar amanhã.");
        }
    }

    public void iniciarDia(){
        this.viagensRestantes = 4;
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

    public int fazerCursoDeEspecializacao() {
        switch (experiencia) {
            case NIVEL_1:
                experiencia = Experiencia.NIVEL_2;
                break;
            case NIVEL_2:
                experiencia = Experiencia.NIVEL_3;
                break;
            case NIVEL_3:
                experiencia = Experiencia.NIVEL_4;
                break;
            default:
                throw new IllegalArgumentException("Esse Caronte já é experiente demais.\nPode ser difícil para ele conseguir trabalhos instrumentais.");
            }
        System.out.println("Nível de experiência aumentado para " + experiencia.getNivel());
        return experiencia.getCustoCurso();

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
    public void adicionarMulta(Multa multa) {
        this.carteira.adicionarMulta(multa);
    }

    public int pagarSalario(){
        if(!getSalarioEmDia()){
        salarioEmDia = true;
        return experiencia.getSalario();
        }else {
            throw new IllegalArgumentException("Salário do motorista já está em dia.\n" + relatorio());
        }
    }

    public int cobrarSalario(){
        salarioEmDia = false;
        return experiencia.getSalario();
    }

    /**
     * Verifica se a carteira do Caronte é válida.
     *
     * @return Verdadeiro se a carteira é válida, falso caso contrário.
     */
    public boolean getCarteiraValida() {
        return this.carteira.carteiraValida();
    }
    
    public int getNivel() {
        return this.experiencia.getNivel();
    }

    public double getSalario() {
        return this.experiencia.getSalario();
    }

    public double getProbabilidade(){
        return experiencia.getProbabilidade();
    }

    public int getViagensRestantes(){
        return viagensRestantes;
    }

    public boolean getSalarioEmDia(){
        return salarioEmDia;
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
        relatorio.append("Nome: " + getNome() + "\n");
        relatorio.append("Viagens restante por hoje: " + getViagensRestantes() + "\n");
        relatorio.append("Nivel de Experiência: " + experiencia.getNivel() + "\n");
        relatorio.append("Salário por Dia: " + experiencia.getSalario() + "\n");
        relatorio.append("Probabilidade de Cometer Infrações: " + experiencia.getProbabilidade() + "\n");
        relatorio.append("Carteira de Motorista:\n");
        relatorio.append(carteira.relatorio());
        return relatorio.toString();
    }
    //#endregion
}

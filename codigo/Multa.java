import java.time.LocalDate;

public enum Multa {
    LEVE("Leve", 3, 88.38),
    MEDIA("Média", 4, 130.16),
    GRAVE("Grave", 5, 195.23),
    GRAVISSIMA("Gravíssima", 7, 293.47);

    private final String tipo;
    private final int pontos;
    private final double valor;
    private final LocalDate dataDeEmissao;
    private boolean multaAtiva;
    private boolean multaPaga;

    Multa(String tipo, int pontos, double valor) {
        this.tipo = tipo;
        this.pontos = pontos;
        this.valor = valor;
        this.dataDeEmissao = LocalDate.now();
        this.multaAtiva = true;
        this.multaPaga = false;
    }

    public double pagarMulta() {
        if (multaPaga) {
            throw new IllegalStateException("A multa já está paga.");
        }else{
        multaPaga = true;
        return getValor();
        }
    }

    public String getTipo(){
        return tipo;
    }
    public int getPontos() {
        return pontos;
    }

    public double getValor() {
        return valor;
    }
    public LocalDate getDataDeEmissao(){
        return dataDeEmissao;
    }
    public boolean getMultaPaga(){
    return multaPaga;
    }

    public boolean multaExpirou(){
        LocalDate dataDeHoje = LocalDate.now();
            if(getDataDeEmissao().plusYears(1).isBefore(dataDeHoje) && getMultaPaga()){
                multaAtiva = false;
            }
            return multaAtiva;
    }

    @Override
    public String toString() {
       String multaPaga ;
        if(this.multaPaga){
        multaPaga =  "Sim"; 
        }
        else{
        multaPaga =  "Não"; 
        }
        String validadeDaMulta ;
        if(this.multaAtiva){
        validadeDaMulta =  "Sim"; 
        }
        else{
        validadeDaMulta =  "Não"; 
        }
        return String.format("Gravidade: %s/nPontos: %d/nValor: %.2f/nData de Emissão: %s/nMulta está paga: $s/nMulta está ativa: %s",
        getTipo(), pontos, valor, dataDeEmissao, multaPaga, validadeDaMulta);
    }
}

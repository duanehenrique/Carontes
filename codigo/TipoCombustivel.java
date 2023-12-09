public enum TipoCombustivel {
    ALCOOL(3.29),
    GASOLINA(5.19),
    DIESEL(6.09);

    private final double preco;

    TipoCombustivel(double preco){
        this.preco = preco;
    }

    public double getPreco(){
        return preco;
    }

}

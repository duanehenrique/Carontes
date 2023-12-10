public enum TipoCombustivel {
    ALCOOL(3.29, 9.5),   // O consumo médio em km/l e o preço médio para o álcool
    GASOLINA(5.19, 12.0), // O consumo médio em km/l e o preço médio para a gasolina
    DIESEL(6.09, 15.0);   // O consumo médio em km/l e o preço médio para o diesel

    private final double preco; // Atributo para o preço médio
    private final double consumoMedio; // Atributo para o consumo médio

    TipoCombustivel(double preco, double consumoMedio){
        this.preco = preco;
        this.consumoMedio = consumoMedio;
    }

    public double getPreco(){
        return preco;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }
}
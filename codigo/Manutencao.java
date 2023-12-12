import java.text.Normalizer;

public class Manutencao{
    private boolean manutencaoPeriodicaEmDia;
    private boolean manutencaoPecasEmDia;
    private double kmDesdeUltimaManutencao;
    private final KmManutencao km;
    private final double custo;

public Manutencao(String tipoVeiculo, double custo){
    this.manutencaoPeriodicaEmDia = true;
    this.manutencaoPecasEmDia = true;
    this.kmDesdeUltimaManutencao = 0;
    this.custo = custo;
    switch (Normalizer.normalize(tipoVeiculo.toUpperCase(), Normalizer.Form.NFD)
    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")) {
        case "CARRO":
        km = KmManutencao.CARRO;
            break;
        case "VAN":
        km = KmManutencao.VAN;
            break;
        case "FURGAO":
        km = KmManutencao.FURGAO;
            break;
        case "CAMINHAO":
        km = KmManutencao.CAMINHAO;
        break;
        default:
            throw new IllegalArgumentException("Tipo de veículo desconhecido: " + tipoVeiculo);
    }
}
public double getKmDesdeUltimaManutencao(){
    return kmDesdeUltimaManutencao;
}

public boolean getManutencaoEmDia(){
    return (manutencaoPeriodicaEmDia && manutencaoPecasEmDia);
}
public boolean getManutencaoPeriodicaEmDia(){
    return manutencaoPeriodicaEmDia;
}
public boolean getManutencaoPecasEmDia(){
    return manutencaoPecasEmDia;
}

public void addKmParaManutencao(double km){
    this.kmDesdeUltimaManutencao += km;
    manutencaoEstaEmDia();
}
private void manutencaoEstaEmDia(){
    if(kmDesdeUltimaManutencao >= km.getManutencaoPecas()){
        this.manutencaoPecasEmDia = false;
    }
    if(kmDesdeUltimaManutencao >= km.getManutencaoPeriodica()){
        this.manutencaoPeriodicaEmDia = false;
    }
}

public double realizarManutencaoPecas(){
        this.kmDesdeUltimaManutencao = 0;
        this.manutencaoPecasEmDia = true;
        return custo;
}

public double realizarManutencaoPeriodica(){
        this.kmDesdeUltimaManutencao = 0;
        this.manutencaoPeriodicaEmDia = true;
        return custo;
}
}
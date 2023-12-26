public interface VeiculoComTanque{
    public double autonomiaMaxima();
    public double autonomiaAtual();
    public double abastecer(double litros);
    public int percorrerRota(Rota rota);
    public boolean podeRealizarRota(Rota rota);
    public Tanque getTanque();
}

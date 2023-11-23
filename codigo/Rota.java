package codigo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Rota {
    
    
    public static final LocalDate getData = null;
    private LocalDate data;
    private double quilometragem;
    private Veiculo veiculo;


     public Rota(LocalDate data, double quilometragem) {
        this.data = data;
        this.quilometragem = quilometragem;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

  
    
    public LocalDate getData() {
        return data;
    }


 public String emitirRelatorioRota() {
    StringBuilder relatorio = new StringBuilder();
    relatorio.append("Relatório da Rota:\n");
    relatorio.append("   Data: " + data + "\n");
    relatorio.append("   Quilometragem: " + quilometragem + "\n");
    relatorio.append("   Veículo: " + Veiculo.getPlaca()  + "\n");
    relatorio.append("\n");
    return relatorio.toString();
}

    

 

   

    

    
}

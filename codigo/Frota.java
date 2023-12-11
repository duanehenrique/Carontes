package codigo;

import java.util.ArrayList;

public class Frota {
    private int tamanhoFrota;
    private Veiculo[] veiculos;

    public Frota(int tamanhoFrota) {
        this.tamanhoFrota = tamanhoFrota;
        this.veiculos = new Veiculo[tamanhoFrota];
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] == null) {
                veiculos[i] = veiculo;
                break;
            }
        }
    }

    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório da Frota:\n");
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                relatorio.append("Veículo " + veiculos[i].getPlaca() + ":\n");
                relatorio.append("   Quilometragem no mês: " + veiculos[i].kmNoMes() + " km\n");
                relatorio.append("   Quilometragem total: " + veiculos[i].kmTotal() + " km\n");
                relatorio.append("   Autonomia atual: " + veiculos[i].autonomiaAtual() + " km\n");
                relatorio.append("   Litros reabastecidos: " + veiculos[i].getTotalReabastecido() + " litros\n");
                relatorio.append("\n");
            }
        }
        return relatorio.toString();
    }

    public Veiculo localizarVeiculo(String placa) {
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null && veiculos[i].getPlaca().equals(placa)) {
                return veiculos[i];
            }
        }
        return null;
    }

    public double quilometragemTotal() {
        double totalKm = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                totalKm += veiculos[i].kmTotal();
            }
        }
        return totalKm;
    }

    public Veiculo maiorKmTotal() {
        Veiculo veiculoComMaiorKm = null;
        double maiorKm = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null && veiculos[i].kmTotal() > maiorKm) {
                veiculoComMaiorKm = veiculos[i];
                maiorKm = veiculos[i].kmTotal();
            }
        }
        return veiculoComMaiorKm;
    }

    public Veiculo maiorKmMedia() {
        Veiculo veiculoComMaiorKmMedia = null;
        double maiorKmMedia = 0.0;
        for (int i = 0; i < tamanhoFrota; i++) {
            if (veiculos[i] != null) {
                double kmMedia = veiculos[i].kmTotal() / veiculos[i].getQuantRotas();
                if (kmMedia > maiorKmMedia) {
                    veiculoComMaiorKmMedia = veiculos[i];
                    maiorKmMedia = kmMedia;
                }
            }
        }
        return veiculoComMaiorKmMedia;
    }
}

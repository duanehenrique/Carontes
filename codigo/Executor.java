import java.util.List;

public class Executor {

    public double executarAcaoComAlmasEspecifica(Frota frota, int funcao, List<Object> parametros) {
        switch (funcao) {
            case 1:
                return (Double) frota.abastecerBarcoPorIndex((int) parametros.get(0), (double) parametros.get(1));
            case 2:
                return (Double) frota.abastecerBarcoPorNome((String) parametros.get(0), (double) parametros.get(1));
            case 3:
                return (Double) frota.abastecerBarcoCompletoPorIndex((int) parametros.get(0));
            case 4:
                return (Double) frota.abastecerBarcoCompletoPorNome((String) parametros.get(0));
            case 5:
                return (Double) frota.fazerManutencaoBarcoPorIndex((int) parametros.get(0));
            case 6:
                return (Double) frota.fazerManutencaoPorNome((String) parametros.get(0));
            case 7:
                return (Double) frota.pagarMultaDoCarontePorIndex((int) parametros.get(0), (int) parametros.get(1));
            case 8:
                return (Double) frota.pagarMultaDoCarontePorNome((String) parametros.get(0), (int) parametros.get(1));
            case 9:
                return (Double) frota.pagarTodasMultasDoCarontePorIndex((int) parametros.get(0), (int) parametros.get(1));
            case 10:
                return (Double) frota.pagarTodasMultasDoCarontePorNome((String) parametros.get(0), (int) parametros.get(1));
            case 11:
                return (Double) frota.pagarCursoDeEspecializacaoPorNome((String) parametros.get(0));
            case 12:
                return (Double) frota.pagarCursoDeEspecializacaoPorIndex((int) parametros.get(0));
            case 13:
                return (Double) frota.pagarSalarioMotoristaPorIndex((int) parametros.get(0));
            case 14:
                return (Double) frota.pagarSalarioMotoristaPorNome((String) parametros.get(0));
            default:
                throw new IllegalArgumentException("Operação inválida");
        }
    }
 
    public double executarAcaoComAlmasGeral(Frota frota, int funcao) {
        switch (funcao) {
            case 15:
                return (Double) frota.pagarSalarioTodosMotoristas();
            case 16:
                return (Double) frota.abastecerTodosBarcos();
            case 17:
                return (Double) frota.fazerManutencaoTodosBarcos();
            case 18:
                return (Double) frota.pagarTodasMultasDeTodosCarontes();

            default:
                throw new IllegalArgumentException("Operação inválida");
        }
    }

    public List<String> executarAcaoNaFrotaDeControle(Frota frota, int funcao, List<Object> parametros) {
        switch (funcao) {
            case 19:
                return (List<String>) frota.addBarco((Barco) parametros.get(0));
            case 20:
                return (List<String>) frota.addRotaPorNome((String) parametros.get(0), (Rota) parametros.get(1));
            case 21:
                return (List<String>) frota.addRotaPorIndex((int) parametros.get(0), (Rota) parametros.get(1));
            case 22:
                return (List<String>) frota.excluirRotaPorIndex((int) parametros.get(0), (int) parametros.get(1));
            case 23:
                return (List<String>) frota.excluirRotaPorNome((String) parametros.get(0), (int) parametros.get(1));
            case 24:
                return (List<String>) frota.trocarRotasPorNome((String) parametros.get(0), (int) parametros.get(1), (String) parametros.get(2), (int) parametros.get(3));
            case 25:
                return (List<String>) frota.trocarRotasPorIndex((int) parametros.get(0), (int) parametros.get(1), (int) parametros.get(2), (int) parametros.get(3));
            case 26:
                return (List<String>) frota.trocarCarontesPorNome((String) parametros.get(0), (String) parametros.get(1));
            default:
                throw new IllegalArgumentException("Operação inválida");
        }
    }

    public List<String> executarAcaoNaFrotaDeListarEspecifico(Frota frota, int funcao, List<Object> parametros) {
        switch (funcao) {
            case 27:
                return (List<String>) frota.relatorioBarcoPorNome((String) parametros.get(0));
            case 28:
                return (List<String>) frota.relatorioBarcoPorIndex((int) parametros.get(0));
            case 29:
                return (List<String>) frota.relatorioCompletoBarcoPorIndex((int) parametros.get(0));
            case 30:
                return (List<String>) frota.relatorioCompletoBarcoPorNome((String) parametros.get(0));
            case 31:
                return (List<String>) frota.listarBarcosPorTipo((String) parametros.get(0));
            default:
                throw new IllegalArgumentException("Operação inválida");
        }
    }
    
    public List<String> executarAcaoNaFrotaDeListarGeral(Frota frota, int funcao) {
        switch (funcao) {
            case 32:
                return (List<String>) frota.relatorioFrota();
            case 33:
                return (List<String>) frota.relatorioFrotaDeOntem();
            case 34:
                return (List<String>) frota.listarCarontes();
            case 35:
                return (List<String>) frota.listarTodosParaAbastecer();
            case 36:
                return (List<String>) frota.listarTodosSalariosParaPagar();
            case 37:
                return (List<String>) frota.listarTodosParaManutencao();
            default:
                throw new IllegalArgumentException("Operação inválida");            
        }
    }
}


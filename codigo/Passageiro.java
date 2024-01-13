import java.text.Normalizer;

public class Passageiro implements Normalizador, Relatorio{
    private String nome;
    private Pecado pecado;

    public Passageiro(String nome, String descricaoPecado) {
        this.nome = nome;
        this.pecado = criarPecado(descricaoPecado);
    }

    private Pecado criarPecado(String tipoPecado) {
        for (Pecado pecado : Pecado.values()) {
            if (normalizar(pecado.getPecado()).equals(normalizar(tipoPecado))) {
                return pecado;
            }
        }
        throw new IllegalArgumentException("Descrição de pecado inválida: " + tipoPecado);
    }

    public String getNome() {
        return nome;
    }

    public String getPecado() {
        return pecado.getPecado();
    }

    public int getAlmas() {
        return pecado.getValor();
    }

    private static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.toUpperCase();

        return normalizado;
    }

    public String relatorio(){
        StringBuilder relatorio = new StringBuilder();
            relatorio.append(". Nome: ").append(getNome())
                .append("/nPecado: ").append(getPecado())
                .append("/nValor do Pecado: ").append(getAlmas()).append("\n");

                return relatorio.toString();

    }
}

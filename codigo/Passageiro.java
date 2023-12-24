import java.text.Normalizer;

public class Passageiro implements Normalizador{
    private String nome;
    private Pecado pecado;

    public Passageiro(String nome, String descricaoPecado) {
        this.nome = nome;
        this.pecado = criarPecado(descricaoPecado);
    }

    private Pecado criarPecado(String tipoPecado) {
        for (Pecado pecado : Pecado.values()) {
            if (pecado.getPecado().equals(normalizar(tipoPecado))) {
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
}
import java.text.Normalizer;

public interface Normalizador {
 static String normalizar(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        String caixaAlta = normalizado.toUpperCase();

        return caixaAlta;
    }
}

import java.util.Random;

public class GeradorNomePassageiro {

    private static final String[] NOMES = {
            "Lucas", "Isabela", "Miguel", "Sophia", "Enzo", "Larissa", "Pedro", "Julia", "Matheus", "Felipe",
            "Gabriel", "Lara", "Arthur", "Valentina", "Heitor", "Alice", "Davi", "Isadora", "Bernardo", "Manuela",
            "Sócrates", "Vladimir", "Pôncio", "Amanda", "Diego", "Vitor", "Muhammad", "Elijah", "Daniel", "Lawrence",
            "Dave", "Djimon ", "Alec", "Pierre", "Aristóteles", "Pepino", "Virgílio", "Dante", "Galileu", "Reagan",
            "Lee", "Kaori", "Doritos", "Jin", "Ying ", "Shinji", "Francis", "Bruce", "Pietro", "Maria Eugênia",
            "Minho", "Yeonhee", "Ahmed", "Aisha", "Tadashi ", "Terrance Williams", "Sakura ", "Wanda", "Cosmo", "Duane" 
    };

    public String gerarNomePassageiro() {
        int indiceAleatorio = new Random().nextInt(NOMES.length);
        return NOMES[indiceAleatorio];
    }
}

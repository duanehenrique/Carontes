import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeradorNomesBarcos {
        private static final String[] nomes = {
                "Pescador de Ilusões", "Banana Boat", "Jacaré Panguá", "Olha a Onda", "Pedro Álvares Cabral",
                "Barqueta Furacão", "Titaníquel", "Tubarão (1975)", "Holandês Aviador", "Pérola Branca",
                "Ama Deus", "Nãotilus", "Centennial Hawk", "Aprilflower", "Moby Duck",
                "SS Norman Die", "Mellow Submarine", "Descobridor dos Cinco Rios", "USS Exitprise", "Paraíso Tropical",
                "Príncipe do Mar", "Rei dos Rios", "Domingueiro", "Pilotado Por Mim, Guiado Por Hades", "Apaixonado por Água",
                "Águas de Fortaleza", "Cervejinha e Carne Boa", "Nossa Senhora Aparecida", "O Ciúme da Patroa", "Três Marias",
                "Peixe-Espada", "Pula Onda", "Marca de Noé", "Saúde e Alegria", "Meus Filhos Tão Sem Pensão Por Esse Barco"
        };
    private Map<String, List<Boolean>> nomesDisponíveis;
    Random random;

    public GeradorNomesBarcos() {
        this.nomesDisponíveis = new HashMap<>();
        for (String nome : nomes) {
            List<Boolean> descendencia = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                descendencia.add(false);
            }
            nomesDisponíveis.put(nome, descendencia);
        }
    }

    public String gerarNome(){
        random = new Random();
        int iteracao = Integer.MIN_VALUE; 
        while(iteracao < Integer.MAX_VALUE){
        int posicaoNome = random.nextInt(35);
        StringBuilder nome = new StringBuilder();
        nome.append((String) nomesDisponíveis.keySet().toArray()[posicaoNome]);

        List<Boolean> listaBooleanos = nomesDisponíveis.get(nome.toString());
        for(int i = 0; i < 4; i++){
        if (!listaBooleanos.get(i)) {
            listaBooleanos.set(i, true);
            nomesDisponíveis.replace(nome.toString(), listaBooleanos);
            if(i == 1){
                nome.append(" II");
            }else if(i == 2){
                nome.append(" III");
            }else if(i == 3){
                nome.append(" IV");
            }
            return nome.toString();
        }
        iteracao++;
        } 
    }
    throw new IllegalArgumentException("Todos os barcos do Tártaro já fazem parte da sua frota. Se contente com o que tem.");       
    }}
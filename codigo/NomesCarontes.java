import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NomesCarontes {
        private static final String[] nomes = {
                "Eustáquio Felipe", "Prudêncio José", "Adasmastor", "Carimbo Miguel", "Jacinto",
                "Fagner", "Severino", "Anselmo Antônio", "Waldemar", "Edmundo Joaquim",
                "Remo", "Ricardo", "Ermenegildo", "Nilton", "Juvêncio",
                "Ambrósio Alexandre", "Evaldo", "Evair", "Lourivanderson", "Lourival",
                "Floriano Peixoso", "Iracemo", "Lineu", "Irineu", "Sebastião Doce",
                "Raimundo Do Nato", "Romário", "Ronaldinho Potiguar", "Waldomiro Bolo", "Humberto",
                "Uberaldo", "Uberlando", "João Boticatubas", "Getúlio Vagas", "Juscelino Cobre-Cheque"
        };
    private Map<String, List<Boolean>> nomesDisponíveis;
    Random random;

     NomesCarontes() {
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
                nome.append(" Jr.");
            }else if(i == 2){
                nome.append(" Neto");
            }else if(i == 3){
                nome.append(" IV");
            }
            return nome.toString();
        }
        iteracao++;
        } 
    }
    throw new IllegalArgumentException("Todos os Carontes do Tártaro já estão empregados em sua frota. Se contente com o que tem.");       
    }}
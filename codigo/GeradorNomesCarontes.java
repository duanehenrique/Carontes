import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeradorNomesCarontes {
        private static final String[] nomes = {
                "Eustáquio Felipe", "Prudêncio José", "Adasmastor", "Carimbo Miguel", "Jacinto",
                "Fagner", "Severino", "Anselmo Antônio", "Waldemar", "Edmundo Joaquim",
                "Remo", "Ricardo", "Ermenegildo", "Nilton", "Juvêncio",
                "Ambrósio Alexandre", "Evaldo", "Evair", "Lourivanderson", "Lourival",
                "Floriano Peixoso", "Iracemo", "Lineu", "Irineu", "Sebastião Doce",
                "Raimundo Do Nato", "Romário", "Ronaldinho Potiguar", "Waldomiro Bolo", "Humberto",
                "Uberaldo", "Uberlando", "João Boticatubas", "Getúlio Vagas", "Juscelino Cobre-Cheque"
        };
    private List<List <Boolean>> nomesDisponiveis;


    Random random;

     public GeradorNomesCarontes() {
            for(int i = 0; i < 4; i++){
                List <Boolean> lista = nomesDisponiveis.get(i);
            for(int j = 0; j < nomes.length; i++){
                lista.add(false);
            }
            }
        }

    public String gerarNome(){
        random = new Random();
        int iteracao = Integer.MIN_VALUE; 
        while(iteracao < Integer.MAX_VALUE){
        int posicaoNome = random.nextInt(nomes.length);
        StringBuilder nome = new StringBuilder();
        nome.append(nomes[posicaoNome]);
        for(int i = 0; i < 4; i++){
        List <Boolean> listaDisponiveis = nomesDisponiveis.get(i);
        nome.append(nomes[posicaoNome]);
            if (!listaDisponiveis.get(posicaoNome)) {
            if(i == 0){
            marcarBoolean(posicaoNome, i);
            return nome.toString();
            }
            if(i == 1){
            nome.append(" Jr.");
            marcarBoolean(posicaoNome, i);
            return nome.toString();
            }else if(i == 2){
            nome.append(" Neto");
            marcarBoolean(posicaoNome, i);
            return nome.toString();
            }else if(i == 3){
            nome.append(" IV");
            marcarBoolean(posicaoNome, i);
            return nome.toString();
            }
        }
      }
    } 
    throw new IllegalArgumentException("Todos os Carontes do Tártaro já estão empregados em sua frota. Se contente com o que tem.");       
    }
    
    public void marcarNomeUtilizado(Caronte caronte) {
        String nomeCaronte = caronte.getNome();
        boolean contemNome = false;
        int posicaoNome;
        int ultimoEspaco = nomeCaronte.lastIndexOf(" ");

        if (ultimoEspaco == -1) {
         for (int i = 0; i < nomes.length; i++) {
          if (nomes[i].equals(nomeCaronte)) {
          marcarBoolean(i, 0);
          }
        }
        }else{
            String primeiroNome = nomeCaronte.substring(0, ultimoEspaco);
            String ultimaParte = nomeCaronte.substring(ultimoEspaco + 1);
        for (int i = 0; i < nomes.length; i++) {
          if (nomes[i].equals(primeiroNome)) {
            posicaoNome = i;
          }else{
            throw new IllegalArgumentException("Caronte não registrado no cartório.");
          }

            if (ultimaParte.equals("Jr.")) {
                marcarBoolean(posicaoNome, 1);
            }else if(ultimaParte.equals("Neto")) {
                marcarBoolean(posicaoNome, 2);
            }else if(ultimaParte.equals("IV")){
            marcarBoolean(posicaoNome, 3);
            }else{
                throw new IllegalArgumentException("Caronte não registrado no cartório.");
            }
        }
    }
}

    private void marcarBoolean(int posicaoNome, int qualLista) {
        if(qualLista <= nomesDisponiveis.size())
        {
        List <Boolean> listaDisponiveis = nomesDisponiveis.get(qualLista);
        listaDisponiveis.set(posicaoNome, true); 
        }
        }
    }

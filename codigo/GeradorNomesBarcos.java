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
  
        private List<List <Boolean>> nomesDisponiveis;


        Random random;       
    
    public GeradorNomesBarcos() {
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
            nome.append(" II.");
            marcarBoolean(posicaoNome, i);
            return nome.toString();
            }else if(i == 2){
            nome.append(" III");
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
    throw new IllegalArgumentException("Todos os barcos do Tártaro já fazem parte da sua frota. Se contente com o que tem.");       
    }
    
    public void marcarNomeUtilizado(Barco barco) {
        String nomeCaronte = barco.getNOME();
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
            throw new IllegalArgumentException("Barco não registrado no submundo.");
          }

            if (ultimaParte.equals("II")) {
                marcarBoolean(posicaoNome, 1);
            }else if(ultimaParte.equals("III")) {
                marcarBoolean(posicaoNome, 2);
            }else if(ultimaParte.equals("IV")){
            marcarBoolean(posicaoNome, 3);
            }else{
                throw new IllegalArgumentException("Barco não registrado no submundo.");
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
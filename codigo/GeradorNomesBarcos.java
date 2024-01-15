import java.util.ArrayList;
import java.util.List;
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
            random = new Random();
            nomesDisponiveis = new ArrayList<>();
        
            for (int i = 0; i < 4; i++) {
                List<Boolean> lista = new ArrayList<>();
                for (int j = 0; j < nomes.length; j++) {
                    lista.add(false);
                }
                nomesDisponiveis.add(lista);
            }
        }

        


    public GeradorNomesBarcos(GeradorNomesBarcos gerador) {
        nomesDisponiveis = new ArrayList<>();
        random = new Random();

        for(int i = 0; i < 4; i++){
            List <Boolean> lista = new ArrayList<>();
            List <Boolean> listaOriginal = new ArrayList<>();
            listaOriginal = gerador.nomesDisponiveis.get(i);

        for(int j = 0; j < nomes.length; j++){
            if(listaOriginal.get(j)){

            lista.add(true);
            }else{ 
                lista.add(false);
            }
        }
        nomesDisponiveis.add(lista);
        }
    }

    public String gerarNome() {
        int iteracao = 0; // Contador de iterações
        while (iteracao < Integer.MAX_VALUE) {
            int posicaoNome = random.nextInt(nomes.length);
            StringBuilder nome = new StringBuilder();
            nome.append(nomes[posicaoNome]);
            
            for (int i = 0; i < 4; i++) {
                List<Boolean> listaDisponiveis = nomesDisponiveis.get(i);
                if (!listaDisponiveis.get(posicaoNome)) {
                    if (i == 0) {
                        marcarBoolean(posicaoNome, i);
                        return nome.toString();
                    } else if (i == 1) {
                        nome.append(" II.");
                        marcarBoolean(posicaoNome, i);
                        return nome.toString();
                    } else if (i == 2) {
                        nome.append(" III");
                        marcarBoolean(posicaoNome, i);
                        return nome.toString();
                    } else if (i == 3) {
                        nome.append(" IV");
                        marcarBoolean(posicaoNome, i);
                        return nome.toString();
                    }
                }
            }
    
            iteracao++; // Incrementa o contador de iterações
        }
    throw new IllegalArgumentException("Todos os barcos do Tártaro já fazem parte da sua frota. Se contente com o que tem.");       
    }
    
    public void marcarNomeUtilizado(Barco barco) {
        String nomeBarco = barco.getNOME();
        int posicaoNome;
        int ultimoEspaco = nomeBarco.lastIndexOf(" ");
 
         for (int i = 0; i < nomes.length; i++) {
          if (nomes[i].equals(nomeBarco)) {
          marcarBoolean(i, 0);
          return;
          }
        }
            String primeiroNome = nomeBarco.substring(0, ultimoEspaco);
            String ultimaParte = nomeBarco.substring(ultimoEspaco + 1);
        for (int i = 0; i < nomes.length; i++) {
          if (nomes[i].equals(primeiroNome)) {
            posicaoNome = i;
     

            if (ultimaParte.equals("II")) {
                marcarBoolean(posicaoNome, 1);
                return;
            }else if(ultimaParte.equals("III")) {
                marcarBoolean(posicaoNome, 2);
                                return;
            }else if(ultimaParte.equals("IV")){
            marcarBoolean(posicaoNome, 3);
                            return;
            }
            }
        }
                              throw new IllegalArgumentException("Barco não registrado no submundo. " + nomeBarco);

        }



    private void marcarBoolean(int posicaoNome, int qualLista) {
        if(qualLista <= nomesDisponiveis.size())
        {
        List <Boolean> listaDisponiveis = nomesDisponiveis.get(qualLista);
        listaDisponiveis.set(posicaoNome, true); 
        }
        }
    }
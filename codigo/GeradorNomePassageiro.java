import java.util.Random;

public class GeradorNomePassageiro {

    private static final String[] NOMES = {
            "Lucas", "Isabela", "Miguel", "Sophia", "Enzo", "Larissa", "Pedro", "Julia", "Matheus", "Felipe",
            "Gabriel", "Lara", "Arthur", "Valentina", "Heitor", "Alice", "Davi", "Isadora", "Bernardo", "Manuela",
            "Sócrates", "Vladimir", "Pôncio", "Amanda", "Diego", "Vitor", "Muhammad", "Elijah", "Daniel", "Lawrence",
            "Dave", "Djimon ", "Alec", "Pierre", "Aristóteles", "Pepino", "Virgílio", "Dante", "Beatrice", "Galileu",
            "Li", "Kaori", "Doritos", "Jin", "Ying ", "Priscila", "Francis", "Bruce", "Pietro", "Maria Eugênia",
            "Minho", "Yeonhee", "Ahmed", "Aisha", "Tadashi ", "Terrance Williams", "Sakura ", "Wanda", "Cosmo", "Duane",
            "Faust", "Nikolas", "Hiromu", "Akira", "Jung-kook", "Zelda", "Giuseppe", "Vincent", "Nefertiti", "Orfeu,",
            "Nabucodonosor", "Zoroastro", "Siddartha", "Olavo", "Lars", "Ingrid", "Olaf", "Magnus", "Johan", "Astrid",
            "Nils", "Freja", "Gerard", "Jean-Baptiste", "Cosette", "Quentin", "Zizek", "Ravi", "Priya", "Indira",
            "Tenzin", "Francisco", "Edir", "Asha", "Kiran", "Raj", "Meera", "Vikran", "Safiya ", "Zola",
            "Chukwu", "Adolf", "Kwasi", "Siyabonga", "Chiara", "Francesca", "Domenico", "Benito", "Serena", "Amenhotep",
            "Imotepe", "Phoebe", "Heraclitus", "Sophocles", "Khalid", "Jamal", "Youssef", "Chloé", "Osama", "Aurore",
            "Irapuã", "Jurema", "Iracema", "Yara", "Senaqueribe", "Iltani", "Amat-Ishtar", "Hamurabi", "Allastair", "Hirohito",
            "Dirce", "Regina", "Vera", "Damares", "Michelle", "Tabatha", "Jair", "Louis", "Émile", "Sigmund", "Remi",
            "Jean-Paul", "Michel", "Simone", "Eichiro", "Hayao", "Osamu", "Manuel", "Emnauel", "Giovanni", "Ben-Hur", "Cleópatra",
            "Marco Antônio", "Julius Caeser", "Cezzare","Siegfried", "Robin", "Guinevere", "Basil", "Edward", "Edgar", "Eliana", "Anette",
            "Woody", "Dick", "Pieter", "Taika", "Ferdinand", "Fernanda", "Márcia", "Sérgio", "Helena", "Frederik",
            "Barrabás", "Neil", "Miles", "Friedrich","Herbie", "Kurt", "Mei", "Wei", "Lin", "Cheng",
            "Jonathan", "Elizabeth", "Javier", "Juan", "Ángel", "Adrián", "Karol", "Pilar", "Yolanda", "Ignacio",
            "Ana Carolina", "Ana Vitória", "Rita", "Wendy", "Ronaldo", "Donald", "Haruhi", "Noelle", "Sungmin", "Yuna",
            "Alan", "Alison", "Eunji", "Sooyoung", "Kyungsoo", "Paula", "Shinji", "Jorge", "Harry", "Apolônio",
            "Apolinário", "Jack", "Bartolomeu", "Martha", "Sirichai", "Gertrudes", "Rolph", "Katiuscia", "Sergei", "Yuri",
            "Svetlana", "Nikolai", "Anastasia", "Lourdes", "Myrna", "Wes", "Kanye", "Hans", "Fitzgerald", "Flynn",
            "Fulano", "Cicrano", "Beltrano", "Troy", "Borat", "Elsa", "Dylan", "Sharpay", "Hansel", "Gretel",
            "Tony", "Gaylord", "Angus", "Dougal", "Ruairidh", "Saoirse", "Siobhan", "Scheherazade", "Crystal", "Misty",
            "Joelma", "Graciane", "Mario", "Luigi", "Wario", "Waluigi", "Keslley", "Cindy", "Homero", "Perseu",
            "Héracles", "Cassandra", "Cibelle", "Jasão", "Pablo", "Saddam", "Idi Amin", "Klaus", "Steve", "Cláudia",
            "Ken", "Daisy", "Lucinda", "Rosalina", "Luma", "Anya", "Shigeru", "Masashi", "Minerva", "Morgana",
            "Miika", "Percival", "Gusttavo", "Luan", "Simaria", "Simon", "Mariah", "Celine", "Britney", "Jennifer",
            "Andy", "Betsy", "Edviges", "Clara", "Bárbara", "Reagan", "Clarice", "Clarêncio", "Herbert", "Luiz",
            "Howard", "Barbie", "Açucena", "Apolo", "Marlon", "Lucca", "Kieran", "Rafael", "Liam", "Patrick",
            "Harrison", "Indiana", "Jimothy", "Michael", "Stanley", "Yuki", "Amelia", "Elena", "Mila", "Santiago",
            "Adão", "Eva", "Caim", "Abel", "Hiroshi", "Maya", "Khaled", "Sven", "Zara", "Asuna", 
            "Kai", "Nikita", "Omar", "Danilo", "Viviane", "Shrek", "Fiona", "Lily", "Tasha", "Uniqua",
            "Tyrone", "Leila", "Samir", "Arjun", "Cyrus", "Péricles", "Bilu", "Boudicca", "Xerxes", "Leônidas",
            "Aang", "Korra", "Lindolfo", "Tristão", "Isolda", "Leif Eriksson", "Björn", "Álvaro", "Sara", "Américo",
            "Ebenezer", "Ezequiel", "Clint", "Nikolaj", "Usain", "Verônica", "Carrie", "Sigourney", "Valentina", "Hermione",
            "Otis", "Owen", "Nora", "Odette", "Sófocles", "Luna", "Luma", "Stephanie", "Bonnie", "Clyde",
            "Kimberly", "Kim", "Guy", "Drake", "Marianna", "Dora", "Martha", "Martinho", "Ivan", "Judi",
            "Wren", "Gilgamesh", "Enkidu", "Samara", "Sadako", "Rivaldo", "Leslie", "Winston", "Farah", "Pharrell",
            "Gillian", "Melvin", "Octavia", "Otto", "Jéssica", "Tracy", "Carlo", "Sunita", "Samuel", "Rosa",
            "Spencer", "Timóteo", "Cristóvão", "Mary Jane", "Ren", "Gwen", "Fatu", "Nádia", "Eunice",
            "Mônica", "Ross", "Rachel", "Chandler", "Phoebe", "Jane", "Kramer", "Laís", "Aquiles", "Pandora", 
            "Jocasta", "Geraldine", "Shiro", "Benjamim", "Hamilton", "Ramon", "Valéria", "Sean", "Oren", "Taiga",
            "Édipo", "Heinrich", "Valesca", "Wagner", "Wolfgang", "Ludwig", " Cynthia", "Sabine", "Sabrina", "Sasha",
            "Brandum", "Eduardo", "Drauzio", "Sonya", "Ariel", "Cássia", "Mia", "Simão", "Junpei", "Shinnichi",
            "Glinda", "Glenda", "Quincy", "Stanford", "Jonas", "Nick", "Joe", "Kevin", "James", "Mariya",
            "Harley", "Hurley", "Benjamim", "Alba", "Hector", "Richter", "Ramon", "Arnold", "Helga", "Ramona",
            "Mac", "Maximiliano", "Zack", "Cody", "London", "Glória", "Maria", "Hannah", "Claudiane", "Magali",
            "Karl", "Harrison", "Ford", "Matusalém", "Salomão", "Esaú", "Mabel", "Seung-Hee", "Bong-Soon", "Ciara"


    };

    public String gerarNomePassageiro() {
        int indiceAleatorio = new Random().nextInt(NOMES.length);
        return NOMES[indiceAleatorio];
    }
}

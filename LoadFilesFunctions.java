package pt.ulusofona.aed.deisiRockstar2021;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class LoadFilesFunctions {



    public static void loadFilesSongs() throws IOException {
        Main.songs = new LinkedHashMap<>();
        Main.ignoredlinessongs = 0;
        Main.tagsArtista = new HashMap<>();
        try {
            FileReader ficheiro = new FileReader("songs.txt");
            BufferedReader fis = new BufferedReader(ficheiro);
            String linha = null;

            while ((linha = fis.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 3) {
                    Main.ignoredlinessongs++;
                } else {
                    String id = dados[0].trim();
                    String titulo = dados[1].trim();
                    int anoLancamento = Integer.parseInt(dados[2].trim());

                    if (anoLancamento > 0 && anoLancamento<= 2021) {
                        if (!Main.songs.containsKey(id)) {
                            Song musica = new Song(id, titulo, anoLancamento);
                            Main.songs.put(id, musica);
                        } else {
                            Main.ignoredlinessongs++;
                        }

                    } else {
                        Main.ignoredlinessongs++;
                    }
                }

            }
            fis.close();
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + Main.songsFile + " não foi encontrado";
            System.out.println(mensagem);
        }
    }

    public static void loadFilesArtists() throws IOException {
        String nomeFicheiro2 = Main.songsArtistFile;
        Main.songartists = new LinkedHashMap<>();
        Main.ignoredlinessongsartist = 0;
        Main.nrTemasArtista = new HashMap<>();
        Main.extra = 0;
        try {
            FileReader ficheiro = new FileReader(nomeFicheiro2);
            BufferedReader fis = new BufferedReader(ficheiro);
            String linha = null;
            while ((linha = fis.readLine()) != null) {
                String[] dados = linha.split(" @ ");
                if (dados.length != 2) {
                    Main.ignoredlinessongsartist++;
                } else {
                    boolean formatoValido = true;
                    String IDtemamusical = dados[0].trim();
                    if (IDtemamusical.length() == 0) { //Vazio
                        formatoValido = false;
                    }
                    String[] artistasFormatoIncorreto = artistasVerificarInicioFim2(dados);

                    String[] artista = new String[artistasFormatoIncorreto.length];
                    for (int i = 0; i<artistasFormatoIncorreto.length && formatoValido; i++) { //Percorre array com os nomes enquanto o formato é válido
                        StringBuilder nome = new StringBuilder(artistasFormatoIncorreto[i].trim());
                        nome = LoadFilesFunctions.artistasVerificarInicioFim(nome);
                        if (nome.length() == 0) { //se o tamanho for diferente de 0 coloca o formato como invalido
                            formatoValido = false;
                            break;
                        } else {
                            artista[i] = comporArtistas(nome, artista, i);
                        }
                    }

                    artistas(IDtemamusical, formatoValido, artista);
                }
            }
            fis.close();
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + nomeFicheiro2 + " não foi encontrado";
            System.out.println(mensagem);
        }
    }


    public static String comporArtistas(StringBuilder nome, String[] artista, int i){

        if (nome.charAt(0) == '"' && nome.charAt(1) == '"' &&
                nome.charAt(nome.length() - 1) == '"' && nome.charAt(nome.length() - 2) == '"') { //Se os 2 primeiros e os 2 ultimos char forem " e depois elimina
            nome.delete(0, 2);
            nome.delete(nome.length() - 2, nome.length());
        }
        artista[i] = nome.toString().trim(); //guarda o artista bem num array
        return artista[i];
    }

    public static StringBuilder artistasVerificarInicioFim(StringBuilder nome){
        while (nome.length() > 0 && nome.charAt(0) == '\'') { //Enquanto primeiro char é '
            nome.deleteCharAt(0); //Elimina o '
        }
        while (nome.length() > 0 && nome.charAt(nome.length() - 1) == '\'') { //Enquanto ultimo char é '
            nome.deleteCharAt(nome.length() - 1); //Elimina o '
        }
        return nome;
    }

    public static String[] artistasVerificarInicioFim2(String[] dados){
        StringBuilder artistasStr = new StringBuilder(dados[1]);
        if (artistasStr.charAt(0) == '"') { // Primeiro char é ", remove
            artistasStr.deleteCharAt(0);
        }
        if (artistasStr.charAt(artistasStr.length() - 1) == '"') { // Ultimo char é ", remove
            artistasStr.deleteCharAt(artistasStr.length() - 1);
        }
        String artistasFormatar = artistasStr.toString().replace('[', ' ').replace(']', ' ')
                .trim(); //replace dos [] por espaços
        String[] artistasFormatoIncorreto = artistasFormatar.split(","); //faz split
        return artistasFormatoIncorreto;
    }

    public static void artistas(String IDtemamusical, boolean formatoValido, String[] artista){
        if (formatoValido && Main.songs.containsKey(IDtemamusical)) { //Verifica se o formato é válido e ve se existe o id no songs
            if (!Main.songartists.containsKey(IDtemamusical)) {  //Verifica se ainda não existe o id no songartists
                int testar = verificaNomes(artista);
                if (testar == 0){
                    Main.songartists.put(IDtemamusical, artista); //coloca no songartists
                }
                //songArtistsIDs.add(IDtemamusical);
            } else { // se existir ignora
                Main.extra++;
            }
            numeroTemas(artista);
        } else { // Se não adiciona nas ignoradas
            Main.ignoredlinessongsartist++;
        }
    }

    public static int verificaNomes(String[] artista){
        int testar = 0;
        for (int i = 0; i < artista.length; i++){
            for (int j = 0; i< artista.length - 1 && i != j; j++){
                if (artista[i] != null){
                    if (artista[i].equals(artista[j])){
                        Main.extra++;
                        testar = 1;
                        break;
                    }
                }

            }
            if (testar == 1){
                break;
            }
        }
        return testar;
    }

    public static void numeroTemas(String[] artista){
        for (String art: artista) { //Utilizado para guardar o numero de temas musicais
            if (Main.nrTemasArtista.containsKey(art)) {
                int temas = Main.nrTemasArtista.get(art) + 1;
                Main.nrTemasArtista.put(art, temas);
            } else {
                Main.nrTemasArtista.put(art, 1);
            }
        }
    }

    public static void loadFilesDetails() throws IOException {
        String nomeFicheiro3 = Main.songsDetailsFile;
        Main.songdetails = new LinkedHashMap<>();
        Main.ignoredlinessongsdetails = 0;
        try {
            FileReader ficheiro = new FileReader(nomeFicheiro3);
            BufferedReader fis = new BufferedReader(ficheiro);
            String linha = null;
            while ((linha = fis.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 7) {
                    Main.ignoredlinessongsdetails++;
                } else {
                    String IDtemamusical = dados[0].trim();
                    int duracao = Integer.parseInt(dados[1].trim());
                    boolean letraexplicita = Integer.parseInt(dados[2].trim()) == 1;
                    int popularidade = Integer.parseInt(dados[3].trim());
                    double dancabilidade = Double.parseDouble(dados[4].trim());
                    double vivacidade = Double.parseDouble(dados[5].trim());
                    double volumemedio = Double.parseDouble(dados[6].trim());

                    if (!Main.songdetails.containsKey(IDtemamusical) && Main.songs.containsKey(IDtemamusical)) {
                        Song musica = new Song(IDtemamusical, duracao, letraexplicita, popularidade, dancabilidade, vivacidade, volumemedio);
                        Main.songdetails.put(IDtemamusical, musica);


                    } else {
                        Main.ignoredlinessongsdetails++;
                    }
                }
            }
            fis.close();
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + nomeFicheiro3 + " não foi encontrado";
            System.out.println(mensagem);
        }
    }
}

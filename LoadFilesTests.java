package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoadFilesTests {

    public static void loadFilesTest() throws IOException {
        loadFilesSongs();
        loadFilesArtists();
        loadFilesDetails();

    }

    public static void loadFilesSongs() throws IOException {
        Main.songs = new LinkedHashMap<>();
        Main.ignoredlinessongs = 0;
        Main.tagsArtista = new HashMap<>();
        try {
            FileReader ficheiro = new FileReader("test-files/songs_test.txt");
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
        String nomeFicheiro2 = "test-files/song_artists_test.txt";
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
                    String[] artistasFormatoIncorreto = LoadFilesFunctions.artistasVerificarInicioFim2(dados);

                    String[] artista = new String[artistasFormatoIncorreto.length];
                    for (int i = 0; i<artistasFormatoIncorreto.length && formatoValido; i++) { //Percorre array com os nomes enquanto o formato é válido
                        StringBuilder nome = new StringBuilder(artistasFormatoIncorreto[i].trim());
                        nome = LoadFilesFunctions.artistasVerificarInicioFim(nome);
                        if (nome.length() == 0) { //se o tamanho for diferente de 0 coloca o formato como invalido
                            formatoValido = false;
                            break;
                        } else {
                            artista[i] = LoadFilesFunctions.comporArtistas(nome, artista, i);
                        }
                    }

                    LoadFilesFunctions.artistas(IDtemamusical, formatoValido, artista);
                }
            }
            fis.close();
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + nomeFicheiro2 + " não foi encontrado";
            System.out.println(mensagem);
        }
    }



    public static void loadFilesDetails() throws IOException {
        String nomeFicheiro3 = "test-files/song_details_test.txt";
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

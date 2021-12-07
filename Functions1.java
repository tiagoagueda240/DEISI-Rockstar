package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class Functions1 {

    public static int countSongsYear(int ano) {
        int totalTemas = 0;
        for (Map.Entry<String, Song> song: Main.songs.entrySet()) { //percorrer o Hashmap na classe songs
            if (song.getValue().anoLancamento == ano) { //saber o valor da key onde estamos ever se o valor que contem e igual ao ano
                totalTemas++; // se sim vai somando
            }
        }

        return totalTemas;
    }

    public static int countDuplicateSongsYear(int ano) {
        int temasDuplicados = 0;
        HashSet<String> temas = new HashSet<>();

        for (Map.Entry<String, Song> entry: Main.songs.entrySet()) {
            Song song = entry.getValue();
            if (song.anoLancamento == ano && temas.contains(song.titulo)) {
                temasDuplicados++;
            } else if (song.anoLancamento == ano) {
                temas.add(song.titulo);
            }
        }

        return temasDuplicados;
    }

    public static String cleanup() {
        int musicasRemovidas = 0;
        int artistasRemovidos = 0;

        // limpar musicas
        for (Map.Entry<String, Song> song: Main.songs.entrySet()) {
            if (!Main.songartists.containsKey(song) || !Main.songdetails.containsKey(song)) {
                Main.songs.remove(song);
                musicasRemovidas++;
            }
        }

        //limpar artistas
        for (Map.Entry<String, String[] > id: Main.songartists.entrySet()) {
            if (!Main.songs.containsKey(id)) {
                Main.songartists.remove(id);
                artistasRemovidos++;
            }
        }

        return "Musicas removidas: " + musicasRemovidas + ";" + "\n" + "Artistas removidos: " + artistasRemovidos;
    }


    public static String getRisingStars(int ano1, int ano2, String ordenacao) {
        StringBuilder sb = new StringBuilder();
        HashMap<String, ArrayList<Song>> artistas = new HashMap<>();
        HashSet<String> artistasNaoIncluidos = new HashSet<>();

        for (Map.Entry<String, Song> entry: Main.songs.entrySet()) {
            Song s = entry.getValue();
            if (s.anoLancamento >= ano1 && s.anoLancamento<= ano2) {
                if (Main.songartists.get(s.id) != null) {
                    String[] artistasDoTema = Main.songartists.get(s.id);
                    for (String art: artistasDoTema) {
                        ArrayList<Song> temasDoArtista = artistas.get(art);
                        if (temasDoArtista != null) {
                            temasDoArtista.add(s);
                            artistas.put(art, temasDoArtista);
                        } else {
                            temasDoArtista = new ArrayList<>();
                            temasDoArtista.add(s);
                            artistas.put(art, temasDoArtista);
                        }
                    }
                }
            } else {
                if (Main.songartists.get(s.id) != null) {
                    String[] artistasDoTema = Main.songartists.get(s.id);
                    for (String art: artistasDoTema) {
                        artistasNaoIncluidos.add(art);
                    }
                }
            }
        }

        ArrayList<Map.Entry<String, Integer>> ordenar = ordenarRising(artistasNaoIncluidos, artistas,ordenacao);

        for (Map.Entry<String, Integer> entry: ordenar) {
            sb.append(entry.getKey() + " <=> " + entry.getValue() + "\n");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static ArrayList<Map.Entry<String, Integer>> ordenarRising(HashSet<String> artistasNaoIncluidos,
                                                                      HashMap<String, ArrayList<Song>> artistas, String ordenacao ){
        HashMap<String, Integer> popularidade = new HashMap<>();
        ArrayList<Map.Entry<String, Integer>> ordenar = new ArrayList<>();
        for (String art: artistasNaoIncluidos) {
            artistas.remove(art);
        }

        for (Map.Entry<String, ArrayList<Song>> artista: artistas.entrySet()) {
            ArrayList<Song> songs = artista.getValue();
            Double media = 0.0;
            for (Song song: songs) {
                media += Main.songdetails.get(song.id) == null ? 0 : Main.songdetails.get(song.id).popularidade;
            }
            media /= songs.size();
            popularidade.put(artista.getKey(), (int) Math.ceil(media));

        }

        for (Map.Entry<String, Integer> entry: popularidade.entrySet()) {
            ordenar.add(entry);
        }

        if (ordenacao.equals("ASC")) {

            Collections.sort(ordenar, new MapSortCresc());

        } else {

            Collections.sort(ordenar, new MapSortDec());
        }
        return ordenar;
    }


}

package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class TagsFunctions {
    static String NO_RESULTS = "No results";
    static String NO_ARTISTS = "Inexistent artist";


    private static boolean existsArtist(String artista) {
        boolean exists = false;
        for (Map.Entry<String, String[] > entry: Main.songartists.entrySet()) {
            String[] artistasExistentes = entry.getValue();
            for (String art: artistasExistentes) {
                if (artista.equals(art)) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    public static String addTags(String input) {
        StringBuilder sb = new StringBuilder();
        String[] dados = input.split(";");
        String artista = dados[0];

        if (!existsArtist(artista)) {
            return NO_ARTISTS;
        }

        ArrayList<String> tags = Main.tagsArtista.get(artista);

        if (tags == null) {
            tags = new ArrayList<>();
        }

        for (int i = 1; i<dados.length; i++) {
            String currentTag = dados[i].toUpperCase();
            if (!tags.contains(currentTag)) {
                tags.add(currentTag);
            }
        }

        Main.tagsArtista.put(artista, tags);
        sb.append(artista + " | ");
        for (String s: tags) {
            sb.append(s + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String removeTags(String input) {
        String[] dados = input.split(";");
        String artista = dados[0];
        StringBuilder sb = new StringBuilder(artista + " | ");

        if (!existsArtist(artista)) {
            return NO_ARTISTS;
        }

        ArrayList<String> tags = Main.tagsArtista.get(artista);
        if (tags != null) {
            for (int i = 1; i<dados.length; i++) {
                tags.remove(dados[i].toUpperCase());
            }

            Main.tagsArtista.put(artista, tags);
            for (String s: tags) {
                sb.append(s + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return tags == null || tags.isEmpty() ? sb.toString() + " No tags" : sb.toString();
    }



    public static String getArtistsForTag(String tag) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> artistas = new ArrayList<String> ();

        for (Map.Entry<String, ArrayList<String>> entry: Main.tagsArtista.entrySet()) { //percorrer o Hashmap da tagsartistas
            if (entry.getValue().contains(tag.toUpperCase())) { // Tag em letras maisculas
                artistas.add(entry.getKey()); //Adiciona a key nos aristas, e guardas as tags
            }
        }

        Collections.sort(artistas, new OrdenacaoAlfabetica()); //ordenar alfabeticamente

        for (String art: artistas) {
            sb.append(art + ";"); //tags separadas por ";"
        }

        if (sb.length() > 0) { //se for maior que 0 o tamanho da string
            sb.deleteCharAt(sb.length() - 1);
        }
        if (sb.length() == 0) { //Se introduzir inválido
            return NO_RESULTS;
        }
        return sb.toString();
    }

    public static String getUniqueTagsInBetweenYears(int ano1, int ano2) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> artistasIncluidos = new HashSet<>();

        for (Map.Entry<String, Song> song: Main.songs.entrySet()) {
            if (song.getValue().anoLancamento >= ano1 && song.getValue().anoLancamento<= ano2) {
                String[] artistasDoTema = Main.songartists.get(song.getValue().id);
                if (artistasDoTema != null) {
                    for (String art: artistasDoTema) {
                        artistasIncluidos.add(art);
                    }
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> ordenar = getUniqueTagsOrdenado(artistasIncluidos);

        Collections.sort(ordenar,new MapSortDec());

        for (Map.Entry<String, Integer> entry: ordenar) {
            sb.append(entry.getKey() + " " + entry.getValue() + "\n");
        }

        return sb.length() == 0 ? NO_RESULTS : sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static ArrayList<Map.Entry<String, Integer>> getUniqueTagsOrdenado(HashSet<String>  artistasIncluidos) {
        HashMap<String, Integer> ocorrenciasTags = Functions.ocorrencias();
        HashSet<String> tagsArtistasIncluidos = new HashSet<>();
        for (String art: artistasIncluidos) {
            ArrayList<String> tagsIncluidas = Main.tagsArtista.get(art);
            if (tagsIncluidas != null) {
                for (String tag: tagsIncluidas) {
                    tagsArtistasIncluidos.add(tag);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> ordenar = new ArrayList<>();
        for (String tag: tagsArtistasIncluidos) {
            ordenar.add(Map.entry(tag, ocorrenciasTags.get(tag)));
        }
        return ordenar;
    }


    public static String getUniqueTags() {
        StringBuilder tags = new StringBuilder();
        HashMap<String, Integer> ocorrenciasTags = Functions.ocorrencias();
        ArrayList<Map.Entry<String, Integer>> ordenar = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: ocorrenciasTags.entrySet()) {
            ordenar.add(entry);
        }

        Collections.sort(ordenar, new MapSortCresc());

        for (Map.Entry<String, Integer> entry: ordenar) {
            tags.append(entry.getKey() + " " + entry.getValue() + "\n");
        }
        tags.deleteCharAt(tags.length() - 1);

        return tags.toString();
    }
}

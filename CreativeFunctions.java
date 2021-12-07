package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CreativeFunctions {

    public static String creative(int ano, int duracao){
        /*
         * Musicas do ano introduzido e com duração igual ou menor à introduzida ordenadas por a média de: (popularidade,dancabilidade e vivacidade)
         */

        ArrayList<Map.Entry<String, Double>> media = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Song > detalhes: Main.songdetails.entrySet()) {
            if (Main.songs.get(detalhes.getKey()).anoLancamento == ano && Main.songs.get(detalhes.getKey()).duracaoTema <= duracao){

                media.add(Map.entry(detalhes.getKey(),(detalhes.getValue().dancabilidade + detalhes.getValue().popularidade
                        + detalhes.getValue().vivacidade)/3));

            }
        }

        Collections.sort(media, new MapSortDecOriginal());

        int count = 1;

        for (Map.Entry<String, Double> musicaMedia: media) {
            if (count < 25){
                sb.append(Main.songs.get(musicaMedia.getKey()).titulo + " | " + Main.songs.get(musicaMedia.getKey()).anoLancamento + " | " + Main.songdetails.get(musicaMedia.getKey()).dancabilidade + " | " +
                        Main.songdetails.get(musicaMedia.getKey()).popularidade + " | " + Main.songdetails.get(musicaMedia.getKey()).vivacidade + "\n");
                count++;
            }else{
                break;
            }
        }



        return sb.toString();
    }
}

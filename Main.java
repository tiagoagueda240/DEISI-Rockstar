/*
Realizado por:
Tiago Águeda
*/
package pt.ulusofona.aed.deisiRockstar2021;
import java.io.*;
import java.util.*;

public class Main {
	static LinkedHashMap<String, Song> songs;
	static LinkedHashMap<String, String[] > songartists;
	static LinkedHashMap<String, Song> songdetails;
	static HashMap<String, ArrayList<String>> tagsArtista;
	static HashMap<String, Integer> nrTemasArtista;

	static int ignoredlinessongs;
	static int ignoredlinessongsartist;
	static int ignoredlinessongsdetails;

	static String songsFile = "songs.txt";
	static String songsArtistFile = "song_artists.txt";
	static String songsDetailsFile = "song_details.txt";
	static int extra;

	public static String execute2(String[] dados, String query, String input){
		String resultado = "Illegal command. Try again";
		switch (query) {
			case "COUNT_DUPLICATE_SONGS_YEAR":
				resultado = String.valueOf(Functions1.countDuplicateSongsYear(Integer.parseInt(dados[1])));
				break;
			case "GET_MOST_DANCEABLE":
				resultado = Functions.getMostDanceable(Integer.parseInt(dados[1]),
                        Integer.parseInt(dados[2]), Integer.parseInt(dados[3]));
				break;

			case "GET_ARTISTS_ONE_SONG":
				resultado = Functions.getArtistsOneSong(Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
				break;

			case "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN":
				resultado = Functions.getTopArtistsWithSongsBetween(Integer.parseInt(dados[1]),
						Integer.parseInt(dados[2]), Integer.parseInt(dados[3]));
				break;
			case "MOST_FREQUENT_WORDS_IN_ARTIST_NAME":
				resultado = Functions.mostFrequentWordsInArtistName(Integer.parseInt(dados[1]),
						Integer.parseInt(dados[2]), Integer.parseInt(dados[3]));
				break;
		}
		return resultado;
	}

	public static String execute1(String[] dados, String query, String input) {
		String resultado = "Illegal command. Try again";
		switch (query) {
			case "GET_UNIQUE_TAGS":
				resultado = TagsFunctions.getUniqueTags();
				break;

			case "CLEANUP":
				resultado = Functions1.cleanup();
				break;
			case "GET_ARTISTS_FOR_TAG":
				resultado = TagsFunctions.getArtistsForTag(input);
				break;

			case "ADD_TAGS":

				resultado = TagsFunctions.addTags(input);
				break;

			case "REMOVE_TAGS":
				resultado = TagsFunctions.removeTags(input);
				break;
			case "COUNT_SONGS_YEAR":
				resultado = String.valueOf(Functions1.countSongsYear(Integer.parseInt(dados[1])));
				break;
			default:
				resultado = execute2(dados, query, input);
		}
		return resultado;
	}

	public static String execute(String command) {
		String resultado = "Illegal command. Try again";
		String[] dados = command.split(" ");
		String query = dados[0];
		int fimDaQuerryIndex = command.indexOf(" ");
		String input = command.substring(fimDaQuerryIndex + 1);

			switch (query) {

				case "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS":
					resultado = TagsFunctions.getUniqueTagsInBetweenYears(Integer.parseInt(dados[1]),
							Integer.parseInt(dados[2]));
					break;

				case "GET_RISING_STARS":
					resultado = Functions1.getRisingStars(Integer.parseInt(dados[1]),
							Integer.parseInt(dados[2]), dados[3]);
					break;

				case "TOP25_BEST_MUSIC_FOR_PARTY":
					resultado = CreativeFunctions.creative(Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
					break;
				default:
					resultado = execute1(dados, query, input);
			}
		return resultado;
	}


	public static String getCreativeQuery() {
		return "TOP25_BEST_MUSIC_FOR_PARTY";
	}
	public static int getTypeOfSecondParameter() {
		return 1;
	}
	public static String getVideoUrl() {
		return "https://www.youtube.com/watch?v=gL4vE-XwkxA";
	}

	public static void loadFiles() throws IOException {

		LoadFilesFunctions.loadFilesSongs();
		LoadFilesFunctions.loadFilesArtists();
		LoadFilesFunctions.loadFilesDetails();

	}

	public static ArrayList<Song> getSongs() {
		ArrayList<Song> res = new ArrayList<>();
		for (Map.Entry<String, Song> song: songs.entrySet()) {
			res.add(song.getValue());
		}
		return res;
	}

	public static ParseInfo getParseInfo(String fileName) {
		ParseInfo resultado = new ParseInfo(0, 0);

		if (fileName.equals(songsFile)) {
			resultado = new ParseInfo(songs.size(), ignoredlinessongs);

		} else if (fileName.equals(songsArtistFile)) {
			resultado = new ParseInfo(songartists.size() + extra, ignoredlinessongsartist);
		} else if (fileName.equals(songsDetailsFile)) {
			resultado = new ParseInfo(songdetails.size(), ignoredlinessongsdetails);
		}
		return resultado;

	}

	public static void main(String[] args) throws IOException {
		loadFiles();
		System.out.println(getParseInfo("song_artists.txt"));
		System.out.println("Welcome to DEISI Rockstar!");
		Scanner in = new Scanner(System.in);
		String line = in .nextLine();
		while (line != null && !line.equals("KTHXBYE")) {
			long start = System.currentTimeMillis();
			String result = execute(line);
			long end = System.currentTimeMillis();
			System.out.println(result);
			System.out.println("(took " + (end - start) + " ms)");
			line = in .nextLine();
		}
		System.out.println("Adeus.");

	}
}
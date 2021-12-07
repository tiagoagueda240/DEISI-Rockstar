package pt.ulusofona.aed.deisiRockstar2021;
import java.util.*;

public class Functions {
	static String NO_RESULTS = "No results";
	static String NO_ARTISTS = "Inexistent artist";

	public static String getMostDanceable(int anoInicio, int anoFim, int numeroResultados) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		Double dancabilidade = 0.0;
		HashMap<String, Double> dancabilidadeLista = new HashMap<>();
		ArrayList<Song> musicas = new ArrayList<>();

		for (Map.Entry<String, Song> detalhes: Main.songdetails.entrySet()) {
			dancabilidadeLista.put(detalhes.getKey(), detalhes.getValue().dancabilidade);
		}

		for (Song s: Main.getSongs()) {
			if (s.anoLancamento >= anoInicio && s.anoLancamento<= anoFim) {
				dancabilidade = dancabilidadeLista.get(s.id);
				if (dancabilidade != null) {
					Song musica = new Song(s.titulo, s.anoLancamento, dancabilidade);
					musicas.add(musica);
				}
			}
		}
		Collections.sort(musicas, new ComparadorDeDancabilidade());

		for (int i = 0; i<numeroResultados; i++) {
			if (count<musicas.size()) {
				sb.append(musicas.get(i).titulo + " : " + musicas.get(i).anoLancamento + " : " + musicas.get(i).dancabilidade + "\n");
				count++;
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public static String getArtistsOneSong(int anoInicio, int anoFim) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> ordenar = new ArrayList<>();
		if (anoInicio >= anoFim) {
			return "Invalid period";
		}

		HashMap<String, Song> musicaEntreAnos = musicasEntreAnosOrdenadas(anoInicio, anoFim);

		for (Map.Entry<String, Song> entry: musicaEntreAnos.entrySet()) {
			ordenar.add(entry.getKey());
		}

		Collections.sort(ordenar, new OrdenacaoAlfabetica());

		for (String art: ordenar) {
			Song s = musicaEntreAnos.get(art);
			sb.append(art + " | " + s.titulo + " | " + s.anoLancamento + "\n");
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	private static HashMap<String, Song>  musicasEntreAnosOrdenadas(int anoInicio, int anoFim){
		HashSet<String> duplicados = new HashSet<>();
		HashMap<String, Song> musicaEntreAnos = new HashMap<>();

		for (Map.Entry<String, Song> song: Main.songs.entrySet()) {
			if (song.getValue().anoLancamento >= anoInicio && song.getValue().anoLancamento<= anoFim) {
				String[] artistas = Main.songartists.get(song.getValue().id);
				if (artistas != null) {
					for (String art: artistas) {
						if (!duplicados.contains(art)) {
							String[] artista = {
									art
							};
							musicaEntreAnos.put(art, new Song(song.getValue().id, song.getValue().titulo, song.getValue().anoLancamento, artista));
							duplicados.add(art);
						} else {
							musicaEntreAnos.remove(art);
						}
					}
				}
			} else {
				String[] artistas = song.getValue().artistas;
				if (artistas != null) {
					for (String art: artistas) {
						musicaEntreAnos.remove(art);
					}
				}

			}
		}

		return musicaEntreAnos;
	}

	public static String getTopArtistsWithSongsBetween(int n, int a, int b) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Map.Entry<String, Integer>> ordenar = new ArrayList<>();
		int count = 0;

		for (Song song: Main.getSongs()) {
			String[] artistas = Main.songartists.get(song.id);
			if (artistas != null) {
				for (String artista: artistas) {
					if (Main.nrTemasArtista.get(artista) >= a && Main.nrTemasArtista.get(artista)<= b &&
							!ordenar.contains(Map.entry(artista, Main.nrTemasArtista.get(artista)))) {
						ordenar.add(Map.entry(artista, Main.nrTemasArtista.get(artista)));
					}
				}
			}
		}

		Collections.sort(ordenar, new MapSortDec());

		for (Map.Entry<String, Integer> entry: ordenar) {
			if (count<n) {
				sb.append(entry.getKey() + " " + entry.getValue() + "\n");
				count++;
			} else {
				break;
			}
		}

		return sb.length() == 0 ? NO_RESULTS : sb.deleteCharAt(sb.length() - 1).toString();
	}

	public static String mostFrequentWordsInArtistName(int n, int m, int l) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Map.Entry<String, Integer>> list = nocorrencias(m, l);
		Collections.sort(list, new MapSortCresc());

		int index = list.size() - n > 0 ? list.size() - n : 0;

		for (int i = index; i<list.size(); i++) {
			Map.Entry<String, Integer> entry = list.get(i);
			sb.append(entry.getKey() + " " + entry.getValue() + "\n");
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();

	}

	public static String[] containsE(String artista) {
		String[] nomeArtista = artista.split("&");
		ArrayList<String> nomes = new ArrayList<>();
		for (String nome: nomeArtista) {
			for (String str: nome.split(" ")) {
				nomes.add(str);
			}
		}
		nomeArtista = new String[nomes.size()];
		for (int i = 0; i<nomes.size(); i++) {
			nomeArtista[i] = nomes.get(i);
		}
		return nomeArtista;
	}

	public static HashMap<String, Integer> verificaTamanho(String[] nomeArtista, int l) {
		HashMap<String, Integer> nrocorrencias = new HashMap<>();
		for (String palavra: nomeArtista) {
			palavra = palavra.trim();
			if (palavra.length() >= l) {
				if (nrocorrencias.containsKey(palavra)) {
					int value = nrocorrencias.get(palavra) + 1;

					nrocorrencias.put(palavra, value);
				} else {
					nrocorrencias.put(palavra, 1);
				}
			}

		}
		return nrocorrencias;
	}
	public static ArrayList<Map.Entry<String, Integer>> nocorrencias(int m, int l) {
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>();
		HashMap<String, Integer> nrocorrencias = new HashMap<>();
		for (Song s: Main.getSongs()) {
			if (Main.songartists.get(s.id) != null) {
				for (String artista: Main.songartists.get(s.id)) {
					if (Main.nrTemasArtista.get(artista) >= m) {
						String[] nomeArtista;
						if (artista.contains("&")) {
							nomeArtista = containsE(artista);
						} else {
							nomeArtista = artista.split(" ");
						}
						nrocorrencias = verificaTamanho(nomeArtista, l);
					}
				}
			}
		}
		for (Map.Entry<String, Integer> entry: nrocorrencias.entrySet()) {
			list.add(entry);
		}
		return list;
	}

	public static HashMap<String, Integer> ocorrencias() {
		HashMap<String, Integer> ocorrenciasTags = new HashMap<>();

		for (Map.Entry<String, ArrayList<String>> entry: Main.tagsArtista.entrySet()) {
			for (String s: entry.getValue()) {
				if (ocorrenciasTags.containsKey(s)) {
					int value = ocorrenciasTags.get(s) + 1;

					ocorrenciasTags.put(s, value);
				} else {
					ocorrenciasTags.put(s, 1);
				}
			}
		}

		return ocorrenciasTags;
	}
}
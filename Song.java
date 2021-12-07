package pt.ulusofona.aed.deisiRockstar2021;

public class Song {
	String id;
	String titulo;
	String[] artistas;
	int anoLancamento;
	int duracaoTema;
	boolean letraExplicita;
	double popularidade;
	double dancabilidade;
	double vivacidade;
	double volumeMedio;

	public Song(String titulo, int anoLancamento, double popularidade, double dancabilidade, double vivacidade, double volumeMedio) {
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
		this.popularidade = popularidade;
		this.dancabilidade = dancabilidade;
		this.vivacidade = vivacidade;
		this.volumeMedio = volumeMedio;
	}

	public Song(String id, String titulo, int anoLancamento) {
		this.id = id;
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
	}

	public Song(String titulo, int anoLancamento, double dancabilidade) {
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
		this.dancabilidade = dancabilidade;
	}

	public Song(String id, int duracaoTema, boolean letraExplicita, int popularidade,
				double dancabilidade, double vivacidade, double volumeMedio) {
		this.id = id;
		this.duracaoTema = duracaoTema;
		this.letraExplicita = letraExplicita;
		this.popularidade = popularidade;
		this.dancabilidade = dancabilidade;
		this.vivacidade = vivacidade;
		this.volumeMedio = volumeMedio;
	}

	public Song(String id, String titulo, int anoLancamento, String[] artistas) {
		this.id = id;
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
		this.artistas = artistas;

	}

	@Override
	public String toString() {

		duracaoTema = Main.songdetails.get(id).duracaoTema;
		popularidade = Main.songdetails.get(id).popularidade;
		artistas = Main.songartists.get(id);

		int minutos = (duracaoTema / 1000) / 60;
		int segundos = (duracaoTema / 1000) % 60;
		String duracao = minutos + ":" + segundos;
		StringBuilder sb = new StringBuilder(id + " | " + titulo + " | " + anoLancamento +
				" | " + duracao + " | " + popularidade + " |");
		if (artistas != null) {
			for (String artista: artistas) {
				sb.append(" " + artista + " /");
			}
			sb.deleteCharAt(sb.length() - 1).append("| ");
			sb.append("(");
			for (String artista: artistas) {
				sb.append(Main.nrTemasArtista.get(artista) + " / ");
			}
			sb.append(")");
			sb.delete(sb.length() - 4, sb.length() - 1);

		}
		return sb.toString();
	}
}

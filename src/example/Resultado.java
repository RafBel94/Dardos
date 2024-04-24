package example;

public class Resultado {
	private String nombreEquipo;
	private int puntos;
	
	public Resultado() {
		super();
	}

	public Resultado(String nombreEquipo, int puntos) {
		super();
		this.nombreEquipo = nombreEquipo;
		this.puntos = puntos;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return "Resultado [nombreEquipo=" + nombreEquipo + ", puntos=" + puntos + "]";
	}
}

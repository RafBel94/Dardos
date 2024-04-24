package example;

public class Entrenador extends Persona{
	private String nacionalidad;
	
	public Entrenador() {
		super();
	}

	public Entrenador(int codigo, int anyoNacimiento, String nombre, String nacionalidad) {
		super(codigo, anyoNacimiento, nombre);
		this.nacionalidad = nacionalidad;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	@Override
	public String toString() {
		return super.toString() + ", nacionalidad: " + nacionalidad;
	}
	
	
}

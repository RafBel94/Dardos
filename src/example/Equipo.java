package example;

import java.io.Serializable;

public class Equipo implements Serializable{
	private String nombre;
	private Entrenador entrenador;
	private Jugador [] jugadores = new Jugador [5];
	
	public Equipo() {
		super();
	}

	public Equipo(String nombre, Entrenador entrenador, Jugador[] jugadores) {
		super();
		this.nombre = nombre;
		this.entrenador = entrenador;
		this.jugadores = jugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public Jugador[] getJugadores() {
		return jugadores;
	}

	public void setJugadores(Jugador[] jugadores) {
		this.jugadores = jugadores;
	}

	@Override
	public boolean equals(Object obj) {
		if(this.getNombre().equalsIgnoreCase(((Equipo)obj).getNombre()))
			return true;
		else
			return false;
	}
	
	
}

package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestMenu {
	static Scanner sc = new Scanner(System.in);
	static List<Equipo> listaEquipos = new ArrayList<>();
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Leemos los datos de los equipos del archivo binario
		recopilarDatos();
		
		boolean exit = false;
		do {
			mostrarMenu();
			int resp = Integer.parseInt(sc.nextLine());
			if(resp == 6)
				exit = true;
			else if(resp == 1)
				mostrarEquipos();
			else if(resp == 2)
				mostrarEquipo();
			else if(resp == 3)
				mostrarJugadores();
			else if(resp == 4)
				apartado4();
			else if(resp == 5)
				mostrarClasificacion();
		}while(!exit);
	}
	
	private static void mostrarClasificacion() throws IOException {
		File archivo = new File("data/resultados.txt");
		
		if(archivo.exists()) {
			System.out.println("\n----------[ CLASIFICACION ]----------");
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String linea = br.readLine();
			while(linea != null) {
				String [] matriz = linea.split(";");
				System.out.println("Equipo: " + matriz[0]);
				System.out.println("Puntos: " + matriz[1]);
				System.out.println();
				linea = br.readLine();
			}
			br.close();
		}else
			System.out.println("No se encuentra el archivo");
	}

	private static void apartado4() {
		List<Persona> losMejores = new ArrayList<>();
		Map<String, Integer> nacionalidades = new HashMap<>();
		
		for(Equipo e : listaEquipos) {
			// Agrego el entrenador a la nueva lista y guardo su nacionalidad a un mapa
			String nacionalidad = e.getEntrenador().getNacionalidad();
			losMejores.add(e.getEntrenador());
			if(nacionalidades.keySet().contains(nacionalidad))
				nacionalidades.put(nacionalidad, nacionalidades.get(nacionalidad) + 1);
			else
				nacionalidades.put(nacionalidad, 1);
			
			// Para guardar el mejor jugador aprovecho el metodo CompareTo y Arrays.sort(), pero como no quiero
			// desordenar la lista de jugadores de los equipos, la paso a otro Array temporal
			Jugador [] matriz = Arrays.copyOf(e.getJugadores(), e.getJugadores().length);
			Arrays.sort(matriz);
			losMejores.add(matriz[0]);
		}
		
		System.out.println("\n--- LOS MEJORES JUGADORES DE TODOS LOS EQUIPOS ---");
		for(Persona p : losMejores) {
			if(p instanceof Jugador)
				System.out.println("- Dorsal: " + ((Jugador)p).getDorsal() + ": " + ((Jugador)p).getNombre() + " || Precision: " + ((Jugador)p).getPrecision());
		}
		
		System.out.println("\n--- NACIONALIDADES DE LOS ENTRENADORES ---");
		List<Map.Entry<String,Integer>> entrenadores = new ArrayList<>(nacionalidades.entrySet());
		for(Map.Entry<String, Integer> e : entrenadores)
			System.out.println("Nacionalidad: " + e.getKey() + " || Cantidad: " + e.getValue());
	}

	private static void mostrarJugadores() {
		System.out.println("\nLISTA DE EQUIPOS: ");
		for(Equipo e : listaEquipos)
			System.out.println("- " + e.getNombre());
		
		System.out.println("Introduce el nombre del equipo:");
		String resp = sc.nextLine();
		for(Equipo e : listaEquipos) {
			if(e.getNombre().equalsIgnoreCase(resp)) {
				// Creo una copia del array en otro temporal para no alterar el original
				Jugador [] jugadores = Arrays.copyOf(e.getJugadores(), e.getJugadores().length);
				Arrays.sort(jugadores);
				for(Jugador j : jugadores)
					System.out.println("- Dorsal: " + j.getDorsal() + ": " + j.getNombre() + " || Precision: " + j.getPrecision());
			}
		}
	}

	private static void mostrarEquipo() {
		boolean exit = false;
			do {
			System.out.println("\nLISTA DE EQUIPOS: ");
			for(Equipo e : listaEquipos)
				System.out.println((listaEquipos.indexOf(e)+1) + ") " + e.getNombre());
			
			System.out.println("Introduce el numero del equipo:");
			int resp = Integer.parseInt(sc.nextLine()) - 1;
			if(resp > 4 || resp < 0)
				System.out.println("Eleccion no valida");
			else {
				Equipo equipo = listaEquipos.get(resp);
				System.out.println("\n-- EQUIPO " + equipo.getNombre().toUpperCase() + " ---");
				System.out.println("Entrenador: " + equipo.getEntrenador().getNombre());
				System.out.println("\nJUGADORES");
				for(Jugador j : equipo.getJugadores())
					System.out.println("- Dorsal: " + j.getDorsal() + ": " + j.getNombre() + " || Precision: " + j.getPrecision());
				exit = true;
			}
		}while(!exit);
	}

	private static void mostrarEquipos() {
		for(Equipo e : listaEquipos) {
			System.out.println("\n-- EQUIPO " + e.getNombre().toUpperCase() + " ---");
			System.out.println("Entrenador: " + e.getEntrenador().getNombre());
			System.out.println("JUGADORES");
			for(Jugador j : e.getJugadores()) {
				System.out.println("- Dorsal: " + j.getDorsal() + ": " + j.getNombre() + " || Precision: " + j.getPrecision());
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n---- MENU ----"
				+ "\n1) Ver todos los equipos"
				+ "\n2) Ver un equipo"
				+ "\n3) Mostrar jugadores de un equipo"
				+ "\n4) Mostrar los mejores jugadores y cantidad de entrenadores de cada pais"
				+ "\n5) Mostrar clasificacion"
				+ "\n6) Salir");
	}

	private static void recopilarDatos() throws FileNotFoundException, IOException {
		File archivo = new File("data/data");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
		try {
			listaEquipos = (ArrayList<Equipo>) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
		}
	}

}

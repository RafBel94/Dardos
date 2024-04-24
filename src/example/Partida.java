package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partida {
	static Scanner sc = new Scanner(System.in);
	static List<Equipo> listaEquipos = new ArrayList<>();
	static final int PUNTOS = 100;
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
		
		
		if(prepararPrograma()) {
				// Conseguimos los equipos para la partida
				Equipo equipoJugador = elegirEquipoJugador();
				Equipo equipoInvitado = elegirEquipoInvitado(equipoJugador);
				
				System.out.println("Equipo local: Equipo " + equipoJugador.getNombre());
				System.out.println("Equipo invitado: Equipo " + equipoInvitado.getNombre());
				
				Equipo equipoGanador = jugarPartido(equipoJugador, equipoInvitado);
				
				guardarResultados(equipoGanador);
		}else
			System.out.println("El programa no ha podido iniciarse correctamente o es la primera vez que se ejecuta");
	}

	private static void guardarResultados(Equipo equipoGanador) throws IOException {
		Resultado [] resultados = new Resultado [4];
		File archivo = new File("data/resultados.txt");
		
		// Si el archivo existe, leemos los datos que contenga y luego los sobreescribimos
		// Si no, lo creamos con los datos que tenemos
		if(archivo.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String linea = br.readLine();
			int contPos = 0;
			while(linea != null) {
				String [] matriz = linea.split(";");
				Resultado resultado = new Resultado(matriz[0],Integer.parseInt(matriz[1]));
				resultados[contPos++] = resultado;
				linea = br.readLine();
			}
			br.close();
			
			// Actualizamos los puntos del equipo que ha ganado sumandole 2 (Cada victoria son 2 puntos)
			for(Resultado r : resultados) {
				if(r.getNombreEquipo().equalsIgnoreCase(equipoGanador.getNombre()))
					r.setPuntos(r.getPuntos()+2);
			}
			
			// Ahora guardamos los datos
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			for(Resultado r : resultados) {
				bw.write(r.getNombreEquipo() + ";" + r.getPuntos() + "\n");
			}
			bw.close();
		}else {
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			for(Equipo e : listaEquipos) {
				if(e.getNombre().equalsIgnoreCase(equipoGanador.getNombre()))
					bw.write(e.getNombre() + ";2\n");
				else
					bw.write(e.getNombre() + ";0\n");
			}
			bw.close();
		}
	}

	private static Equipo jugarPartido(Equipo local, Equipo invitado) throws InterruptedException {
		Equipo equipoGanador = null;
		int contPuntosLocal = 0;
		int contPuntosInvitado = 0;
		
		int contRondas = 1;
		do {
			boolean exit = false;
			int puntosRondaLocal = PUNTOS;
			int puntosRondaInvitado = PUNTOS;
			Jugador jugadorLocal = elegirJugadorLocal(local);
			Jugador jugadorInvitado = elegirJugadorInvitado(invitado);
			System.out.println("RONDA --> " + contRondas);
			do {
				// Turno jugador
				System.out.println("\n ---- TURNO DEL EQUIPO " + local.getNombre().toUpperCase() + " ---- ");
				puntosRondaLocal -= jugarTurno(jugadorLocal);
				System.out.println("\n --- Puntos actuales: " + puntosRondaLocal + " --- ");
				if(puntosRondaLocal <= 0) {
					System.out.println("--> GANADOR DE LA RONDA: " + jugadorLocal.getNombre());
					contPuntosLocal++;
					break;
				}
				
				// Turno ordenador
				System.out.println("\n ---- TURNO DEL EQUIPO " + invitado.getNombre().toUpperCase() + " ---- ");
				puntosRondaInvitado -= jugarTurno(jugadorInvitado);
				System.out.println("\n --- Puntos actuales: " + puntosRondaInvitado + " --- ");
				if(puntosRondaInvitado <= 0) {
					System.out.println("--> GANADOR DE LA RONDA: " + jugadorInvitado.getNombre());
					contPuntosInvitado++;
					break;
				}
			}while(!exit);
			
			contRondas++;
		}while(contRondas <= 5 && contPuntosLocal < 3 && contPuntosInvitado < 3);
		
		// Determinamos el ganador y mostramos los resultados
		if(contPuntosLocal > contPuntosInvitado)
			equipoGanador = local;
		else
			equipoGanador = invitado;
		
		System.out.println("\n ---> EL GANADOR ES EL EQUIPO " + equipoGanador.getNombre().toUpperCase() + " <--- ");
		System.out.println("--> Puntos del equipo " + local.getNombre() + ": " + contPuntosLocal);
		System.out.println("--> Puntos del equipo " + invitado.getNombre() + ": " + contPuntosInvitado);
		
		return equipoGanador;
	}
	
	private static Jugador elegirJugadorInvitado(Equipo equipo) {
		Random r = new Random();
		Jugador [] jugadores = equipo.getJugadores();
		Jugador jDevolver = null;
		Jugador jAux = null;
		
		int eleccion = r.nextInt(0,5);
		while(jAux == null) {
			jAux = jugadores[eleccion];
		}
		
		jDevolver = jAux;
		jAux = null;
		
		return jDevolver;
	}
	
	private static Jugador elegirJugadorLocal(Equipo equipo) {
		Jugador [] jugadores = equipo.getJugadores();
		Jugador jDevolver = null;
		
		// Mostramos los jugadores (Disponibles y no disponibles)
		do {
			System.out.println("\nJugadores disponibles:");
			for(int i = 0 ; i < jugadores.length ; i++) {
				Jugador jAux = jugadores[i];
				if(jAux == null)
					System.out.println((i+1) + ") ---");
				else
					System.out.println((i+1) + ") " + jAux.getNombre());
			}
			
			// Elegimos un jugador que no halla jugado
			System.out.println("\nElige un jugador:");
			int eleccion = Integer.parseInt(sc.nextLine()) - 1;
			if(eleccion > 4 || eleccion < 0 || jugadores[eleccion] == null)
				System.out.println("Eleccion no valida");
			else {
				jDevolver = jugadores[eleccion];
				jugadores[eleccion] = null;
			}
		}while(jDevolver == null);
		
		return jDevolver;
	}
	
	private static Equipo elegirEquipoInvitado(Equipo equipoJugador) {
		Random r = new Random();
		Equipo equipo = listaEquipos.get(r.nextInt(0,4));
		
		while(equipo.equals(equipoJugador)) {
			equipo = listaEquipos.get(r.nextInt(0,4));
		}
		
		return equipo;
	}
	
	private static Equipo elegirEquipoJugador() {
		Equipo equipo = null;
		
		// ELECCION DE EQUIPO DEL JUGADOR
		do {
			System.out.println("\n------ ELIGE UN EQUIPO ------");
			for(Equipo e : listaEquipos)
				System.out.println((listaEquipos.indexOf(e)+1) + ") " + e.getNombre());
			Integer elegido = Integer.parseInt(sc.nextLine());
			if(elegido == 1)
				equipo = listaEquipos.get(0);
			else if(elegido == 2)
				equipo = listaEquipos.get(1);
			else if(elegido == 3)
				equipo = listaEquipos.get(2);
			else if(elegido == 4)
				equipo = listaEquipos.get(3);
			else if(elegido == 5)
				equipo = listaEquipos.get(4);
			else
				System.out.println("Opcion no valida");
		}while(equipo == null);
		
		return equipo;
	}
	
	private static int jugarTurno(Jugador j) throws InterruptedException {
		int contPuntos = 0;
		System.out.println("\nTURNO DE " + j.getNombre().toUpperCase());
		while(j.getDardos() > 0) {
			contPuntos += j.lanzar();
		}
		j.setDardos(3);
		
		return contPuntos;
	}
	
	private static boolean prepararPrograma() throws FileNotFoundException, IOException {
		File archivo = new File("data/data");
		if(!archivo.exists()) {
			generarInformacion();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
			oos.writeObject(listaEquipos);
			oos.close();
			System.out.println("Archivo binario generado...\nPor favor, vuelva a ejecutar el programa.");
			return false;
		}else {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
			try {
				listaEquipos = (ArrayList<Equipo>) ois.readObject();
				ois.close();
			} catch (ClassNotFoundException | IOException e) {
			}
			return true;
		}
	}
	
	private static void generarInformacion() {
		// Equipo Azul
		Jugador [] jugadoresAzul = new Jugador [5];
		Entrenador entrenadorAzul = new Entrenador(1,1987,"Paco Molina","Espanya");
		jugadoresAzul[0] = new Jugador(1,2003,"Antonio Perez",1,8);
		jugadoresAzul[1] = new Jugador(2,2007,"Jesus Gomez",2,5);
		jugadoresAzul[2] = new Jugador(3,2004,"Manolo Escobar",3,4);
		jugadoresAzul[3] = new Jugador(4,2001,"Miguel Prieto",4,6);
		jugadoresAzul[4] = new Jugador(5,2000,"Jose Mano",5,7);
		
		listaEquipos.add(new Equipo("Azul",entrenadorAzul,jugadoresAzul));
		
		// Equipo Amarillo
		Jugador [] jugadoresAmarillo = new Jugador [5];
		Entrenador entrenadorAmarillo = new Entrenador(2,1990,"Kikuson Macasaca","Japon");
		jugadoresAmarillo[0] = new Jugador(6,2001,"Takako Nokita",1,7);
		jugadoresAmarillo[1] = new Jugador(7,2009,"Kisune Makito",2,5);
		jugadoresAmarillo[2] = new Jugador(8,2002,"Sakito Sakoto",3,3);
		jugadoresAmarillo[3] = new Jugador(9,1998,"Pokito Mekito",4,8);
		jugadoresAmarillo[4] = new Jugador(10,2004,"Mushou Yokomo",5,8);
		
		listaEquipos.add(new Equipo("Amarillo",entrenadorAmarillo,jugadoresAmarillo));
		
		// Equipo Verde
		Jugador [] jugadoresVerde = new Jugador [5];
		Entrenador entrenadorVerde = new Entrenador(3,1985,"Jorscrobish Pochinski","Rusia");
		jugadoresVerde[0] = new Jugador(11,2001,"Aleksander Reznov",1,7);
		jugadoresVerde[1] = new Jugador(12,2009,"Maxim Petrovski",2,7);
		jugadoresVerde[2] = new Jugador(13,2002,"Mikhail Hielovski",3,4);
		jugadoresVerde[3] = new Jugador(14,1998,"Dmitri Vodskovski",4,6);
		jugadoresVerde[4] = new Jugador(15,2004,"Andrei Grandovski",5,5);
		
		listaEquipos.add(new Equipo("Verde",entrenadorVerde,jugadoresVerde));
		
		// Equipo Rojo
		Jugador [] jugadoresRojo = new Jugador [5];
		Entrenador entrenadorRojo = new Entrenador(4,1983,"Ryan Davis","Estados Unidos");
		jugadoresRojo[0] = new Jugador(16,2001,"James Jhonson",1,3);
		jugadoresRojo[1] = new Jugador(17,2009,"Jhon Williams",2,6);
		jugadoresRojo[2] = new Jugador(18,2002,"Levi Jones",3,8);
		jugadoresRojo[3] = new Jugador(19,1998,"Michael Miller",4,7);
		jugadoresRojo[4] = new Jugador(20,2004,"Noah Smith",5,9);
		
		listaEquipos.add(new Equipo("Rojo",entrenadorRojo,jugadoresRojo));
	}
}
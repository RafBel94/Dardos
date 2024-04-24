package example;

import java.util.Random;

public class Jugador extends Persona implements Comparable{
	private int dardos;
	private int dorsal;
	private int precision;
	
	public Jugador() {
		super();
		this.dardos = 3;
	}
	
	public Jugador(int codigo, int anyoNacimiento, String nombre, int dorsal, int precision) {
		super(codigo, anyoNacimiento, nombre);
		this.dorsal = dorsal;
		this.precision = precision;
		this.dardos = 3;
	}

	public int getDardos() {
		return dardos;
	}

	public void setDardos(int dardos) {
		this.dardos = dardos;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int lanzar() throws InterruptedException {
		Random r = new Random();
		Thread.sleep(300);
		int rlanzamiento = r.nextInt(1,101) - (precision * 2);
		int rSegmento = r.nextInt(1,21);
		
		if(rlanzamiento <= 5) {
			int rZona = r.nextInt(1,101) + precision;
			if(rZona <= 75) {
				System.out.println("Acierto en la zona verde del centro (25 puntos)");
				dardos -= 1;
				return 25;
			}else{
				System.out.println("Acierto en el centro de la diana (50 puntos)");
				dardos -= 1;
				return 50;
			}
		}else if(rlanzamiento <= 30) {
			if(rlanzamiento <= 15) {
				System.out.println("Acierto en el numero " + rSegmento + " en el anillo de triples (" + rSegmento*3 + " puntos)");
				dardos -= 1;
				return rSegmento * 3;
			}else {
				System.out.println("Acierto en el numero " + rSegmento + " en el anillo de dobles (" + rSegmento*2 + " puntos)");
				dardos -= 1;
				return rSegmento * 2;
			}
		}else if(rlanzamiento > 80) {
			System.out.println("El dardo no da en la diana");
			dardos -= 1;
			return 0;
		}else {
			System.out.println("Acierto en el numero: " + rSegmento + " (" + rSegmento + " puntos)");
			dardos -= 1;
			return rSegmento;
		}
	}

	@Override
	public int compareTo(Object o) {
		return ((Jugador)o).getPrecision() - this.getPrecision();
	}

	@Override
	public String toString() {
		return super.toString() + ", dorsal: " + dorsal + ", precision: " + precision;
	}

	
}

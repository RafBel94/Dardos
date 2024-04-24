package example;

import java.io.Serializable;

public class Persona implements Serializable{
	private int codigo, anyoNacimiento;
	private String nombre;
	
	public Persona() {
		super();
	}

	public Persona(int codigo, int anyoNacimiento, String nombre) {
		super();
		this.codigo = codigo;
		this.anyoNacimiento = anyoNacimiento;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getAnyoNacimiento() {
		return anyoNacimiento;
	}

	public void setAnyoNacimiento(int anyoNacimiento) {
		this.anyoNacimiento = anyoNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + ", Codigo: " + codigo + ", anyoNacimiento:" + anyoNacimiento;
	}
	
	
}

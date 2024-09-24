package clases;

import java.util.ArrayList;

public class Paciente extends Persona{
	private int codigoPaciente;
	private ArrayList<String>historialPaciente;
	
	public Paciente(String contrasena, String nombre, String apellido, int edad, String ubicacion, int codigoPaciente,
			ArrayList<String> historialPaciente) {
		super(contrasena, nombre, apellido, edad, ubicacion);
		this.codigoPaciente = codigoPaciente;
		this.historialPaciente = historialPaciente;
	}

	public int getCodigoPaciente() {
		return codigoPaciente;
	}

	public void setCodigoPaciente(int codigoPaciente) {
		this.codigoPaciente = codigoPaciente;
	}

	public ArrayList<String> getHistorialPaciente() {
		return historialPaciente;
	}

	public void setHistorialPaciente(ArrayList<String> historialPaciente) {
		this.historialPaciente = historialPaciente;
	}

	@Override
	public String toString() {
		return "Paciente [codigoPaciente=" + codigoPaciente + ", historialPaciente=" + historialPaciente
				+ ", contrasena=" + contrasena + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
				+ ", ubicacion=" + ubicacion + ", getCodigoPaciente()=" + getCodigoPaciente()
				+ ", getHistorialPaciente()=" + getHistorialPaciente() + ", getContrasena()=" + getContrasena()
				+ ", getNombre()=" + getNombre() + ", getApellido()=" + getApellido() + ", getEdad()=" + getEdad()
				+ ", getUbicacion()=" + getUbicacion() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	
	

}

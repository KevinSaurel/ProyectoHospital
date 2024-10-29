package clases;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona{
	private int codigoPaciente;
	private List<Historial> historialPaciente = new ArrayList() ;
	
	public Paciente(String contrasena, String nombre, String apellido, int edad, String ubicacion, int codigoPaciente,
			List<Historial> historialEntries) {
		super(contrasena, nombre, apellido, edad, ubicacion);
		this.codigoPaciente = codigoPaciente;
		this.historialPaciente = historialEntries;
	}

	

	public int getCodigoPaciente() {
		return codigoPaciente;
	}

	public void setCodigoPaciente(int codigoPaciente) {
		this.codigoPaciente = codigoPaciente;
	}

	public List<Historial> getHistorialPaciente() {
		return historialPaciente;
	}

	public void setHistorialPaciente(ArrayList<Historial> historialPaciente) {
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

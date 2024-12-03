package domain;

import java.util.StringTokenizer;

public class Doctor extends Persona{
	protected String especialidad;
	protected String horario;
	
	public Doctor(String contrasena, String nombre, String apellido, int edad, String ubicacion, String especialidad,
			String horario) {
		super(contrasena, nombre, apellido, edad, ubicacion);
		this.especialidad = especialidad;
		this.horario = horario;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Doctor [especialidad=" + especialidad + ", horario=" + horario + ", contrasena=" + contrasena
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", ubicacion=" + ubicacion + "]";
	}
	
}

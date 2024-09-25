package clases;

public class Enfermero extends Persona{
	private String turno;
	private Paciente pacientesAtendidos;
	
	public Enfermero(String contrasena, String nombre, String apellido, int edad, String ubicacion, String turno) {
		super(contrasena, nombre, apellido, edad, ubicacion);
		
		this.turno = turno;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Paciente getPacientesAtendidos() {
		return pacientesAtendidos;
	}

	public void setPacientesAtendidos(Paciente pacientesAtendidos) {
		this.pacientesAtendidos = pacientesAtendidos;
	}

	@Override
	public String toString() {
		return "Enfermero [turno=" + turno + ", pacientesAtendidos=" + pacientesAtendidos + "]";
	}
	
	
	
	
}

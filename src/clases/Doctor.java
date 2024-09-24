package clases;

public class Doctor extends Persona{
	protected String especiabilidad;
	protected String horario;
	
	public Doctor(String contrasena, String nombre, String apellido, int edad, String ubicacion, String especiabilidad,
			String horario) {
		super(contrasena, nombre, apellido, edad, ubicacion);
		this.especiabilidad = especiabilidad;
		this.horario = horario;
	}
	

}

package clases;

public class Administrador extends Persona{

protected String turno;

public Administrador(String contrasena, String nombre, String apellido, int edad, String ubicacion, String turno) {
	super(contrasena, nombre, apellido, edad, ubicacion);
	this.turno = turno;
}


}

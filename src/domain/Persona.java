package domain;

public class Persona {
	protected String contrasena;
	protected String nombre ;
	protected String apellido ;
	protected int edad; 
	protected String ubicacion;
	public Persona(String contrasena, String nombre, String apellido, int edad, String ubicacion) {
		super();
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.ubicacion = ubicacion;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	@Override
	public String toString() {
		return "Persona [contrasena=" + contrasena + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
				+ ", ubicacion=" + ubicacion + ", getContrasena()=" + getContrasena() + ", getNombre()=" + getNombre()
				+ ", getApellido()=" + getApellido() + ", getEdad()=" + getEdad() + ", getUbicacion()=" + getUbicacion()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}

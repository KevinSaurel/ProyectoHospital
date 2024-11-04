package domain;

public class Departamento {
	private String nombreDpt;
	private Doctor doctor;
	private Enfermero enfermero;
	
	
	public Departamento(String nombreDpt, Doctor doctor, Enfermero enfermero) {
		super();
		this.nombreDpt = nombreDpt;
		this.doctor = doctor;
		this.enfermero = enfermero;
	}


	public String getNombreDpt() {
		return nombreDpt;
	}


	public void setNombreDpt(String nombreDpt) {
		this.nombreDpt = nombreDpt;
	}


	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public Enfermero getEnfermero() {
		return enfermero;
	}


	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}


	@Override
	public String toString() {
		return "Departamento [nombreDpt=" + nombreDpt + ", doctor=" + doctor + ", enfermero=" + enfermero + "]";
	}
	
	
	
	
}

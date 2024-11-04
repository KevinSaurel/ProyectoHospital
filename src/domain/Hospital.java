package domain;

import java.util.TreeSet;

public class Hospital {
	public String nombreHospital;
	private TreeSet<Paciente> pacientes;
	public TreeSet<Doctor> medicos;
	private TreeSet<Cita>citas;
	
	public Hospital(String nombreHospital, TreeSet<Paciente> pacientes, TreeSet<Doctor> medicos, TreeSet<Cita> citas) {
		super();
		this.nombreHospital = nombreHospital;
		this.pacientes = pacientes;
		this.medicos = medicos;
		this.citas = citas;
	}
	
	public void registrarPaciente(Paciente p) {
		pacientes.add(p);
	}
	public void registrarDoctor(Doctor d) {
		medicos.add(d);
	}
	

}

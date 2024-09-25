package clases;

import java.sql.Date;

public class Cita {
	 private Paciente paciente;
	 private Doctor doctor;
	 private Date fechaHora;
	 
	public Cita(Paciente paciente, Doctor doctor, Date fechaHora) {
		super();
		this.paciente = paciente;
		this.doctor = doctor;
		this.fechaHora = fechaHora;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	 
	
	@Override
	public String toString() {
		return "Cita [paciente=" + paciente + ", doctor=" + doctor + ", fechaHora=" + fechaHora + "]";
	}
	 
	 
}

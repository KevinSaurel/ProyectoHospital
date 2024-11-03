package clases;

import java.time.LocalDate;

public class Cama {
	private int numCama;
	private boolean ocupada;
	private String tipoCama;
	protected LocalDate fecha;
	private Paciente paciente;
	
	
	public Cama() {
		super();
	}


	public Cama(int numCama, boolean ocupada, String tipoCama, LocalDate fecha, Paciente paciente) {
		super();
		this.numCama = numCama;
		this.ocupada = ocupada;
		this.tipoCama = tipoCama;
		this.fecha = fecha;
		this.paciente = paciente;
	}


	public int getNumCama() {
		return numCama;
	}



	public void setNumCama(int numCamasDisponibles) {
		this.numCama = numCamasDisponibles;
	}

	

	public boolean isOcupada() {
		return ocupada;
	}



	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}



	public String getTipoCama() {
		return tipoCama;
	}



	public void setTipoCama(String tipoCama) {
		this.tipoCama = tipoCama;
	}



	public Paciente getPaciente() {
		return paciente;
	}


	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	@Override
	public String toString() {
		return "Cama [numCama=" + numCama + ", ocupada=" + ocupada + ", tipoCama=" + tipoCama + "]";
	}
	
	
	
	
}

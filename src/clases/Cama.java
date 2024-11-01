package clases;

import java.time.LocalDate;

public class Cama {
	private int numCama;
	private boolean ocupada;
	private String tipoCama;
	protected LocalDate fecha;
	
	
	public Cama() {
		super();
	}



	public Cama(int numCama, boolean ocupada, String tipoCama) {
		super();
		this.numCama = numCama;
		this.ocupada = ocupada;
		this.tipoCama = tipoCama;
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



	@Override
	public String toString() {
		return "Cama [numCama=" + numCama + ", ocupada=" + ocupada + ", tipoCama=" + tipoCama + "]";
	}
	
	
	
	
}

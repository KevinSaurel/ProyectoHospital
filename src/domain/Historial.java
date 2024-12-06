package domain;

import java.util.ArrayList;
import java.util.Date;

public class Historial  {
	protected String causa;
	protected Date fecha ;
	protected Doctor medico ;
	
	public Historial() {
		// TODO Auto-generated constructor stub
	}
	public Historial(String causa, Date fecha) {
		super();
		this.causa = causa;
		this.fecha = fecha;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Doctor getMedico() {
		return medico;
	}
	public void setMedico(Doctor medico) {
		this.medico = medico;
	}


}

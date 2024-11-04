package domain;

import java.util.ArrayList;
import java.util.Date;

public class Historial  {
	protected String causa;
	protected Date fecha ;
	protected Doctor medico ;
	
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

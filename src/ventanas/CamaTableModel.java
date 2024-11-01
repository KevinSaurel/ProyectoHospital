package ventanas;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import clases.Cama;

public class CamaTableModel extends DefaultTableModel{
	private final List<Cama> camaList;
	private final List<String> lTitulos = Arrays.asList("Numero Cama", "Ocupada", "Tipo");
	
	public CamaTableModel(List<Cama> camaList) {
		this.camaList = camaList;
	}

	@Override
	public String getColumnName(int column) {
		return lTitulos.get(column);
	}
	
	@Override
	public int getRowCount() {
		if(camaList!=null) {
			return camaList.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return lTitulos.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Cama c = camaList.get(row);
		
		switch (column) {
			case 0: 
				return c.getNumCama();
			case 1: 
				return c.isOcupada();
			case 2: 
				return c.getTipoCama();
			default: 
				return null;
		}
	}
	
	
}

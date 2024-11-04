package gui;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import domain.Doctor;
import domain.Historial;
import domain.Persona;

public class HistorialTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;

    private final List<Historial> historialList;
    private final List<String> headers = List.of("Cita", "Nombre Medico", "Fecha");

    public HistorialTableModel(List<Historial> historialList ) {
        this.historialList = historialList;
    }

    @Override
    public String getColumnName(int column) {
        return headers.get(column);
    }

    @Override
    public int getRowCount() {
        return (historialList != null) ? historialList.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Historial historialEntry = historialList.get(rowIndex);
        Doctor doctor = historialEntry.getMedico();
        
        switch (columnIndex) {
            case 0:
                return historialEntry.getCausa(); 
            case 1:
                return (doctor != null) ? doctor.getNombre() : "No asignado"; // Doctor's name
            case 2:
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return (historialEntry.getFecha() != null) ? dateFormat.format(historialEntry.getFecha()) : "Sin fecha";
            default:
                return null;
        }
    }
}
//FUENTE-EXTERNA
//URL: (https://alud.deusto.es/course/view.php?id=27577)
//SIN-CAMBIOS ó ADAPTADO (Me he basado en la practica swing4v2 , del flight table model)
//IAG (herramienta: )
//SIN CAMBIOS ó ADAPTADO ()
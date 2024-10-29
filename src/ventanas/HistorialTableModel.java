package ventanas;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import clases.Doctor;
import clases.Historial;
import clases.Persona;

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
                return historialEntry.getCausa(); // Reason for the appointment
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

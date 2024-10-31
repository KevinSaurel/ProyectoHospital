package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import clases.Cita;
import clases.Context;
import clases.Persona;

public class VentanaCitas extends JFrame{

	 	private List<Cita> citas;
	    private JTable tablaCitas;
	    private JScrollPane scrollPaneCitas;
	    private JTextField txtFiltro;
	    private DefaultTableModel modeloDatosCitas;
	    private JButton AddCita;
	    private Persona usuario;
	
	    
	    public VentanaCitas(List<Cita> citas, Persona usuario) {
	    	
	    	Color MainColor = new Color(6,99,133);
	    
	    //Se crea el panel base
	    	 JPanel panelBase = new JPanel(new BorderLayout());
	         panelBase.setBackground(MainColor);
	         
	         String[] nombreColumnas = {"Código Paciente", "Nombre Paciente", "Apellido Paciente", "Nombre Doctor", "Apellido Doctor", "Fecha"};
	         modeloDatosCitas = new DefaultTableModel(nombreColumnas, 0) {
	             
	         };
	    }
}  


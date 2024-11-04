package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;
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

public class VentanaCitas extends JFrame {

	private List<Cita> citas;
	private JTable tablaCitas;
	private JScrollPane scrollPaneCitas;
	private JTextField txtFiltro;
	private DefaultTableModel modeloDatosCitas;
	private JButton btnAddCita;
	private JButton btnEditCita;
	private Persona usuario;

	public VentanaCitas(List<Cita> citas, Persona usuario) {

		Color MainColor = new Color(6, 99, 133);

		ImageIcon i = new ImageIcon("src/recursos/hospital.png");
		setIconImage(i.getImage());

		// Se crea el panel base
		JPanel panelBase = new JPanel(new BorderLayout());
		panelBase.setBackground(MainColor);

		String[] nombreColumnas = { "Código Paciente", "Nombre Paciente", "Apellido Paciente", "Nombre Doctor",
				"Apellido Doctor", "Fecha" };
		modeloDatosCitas = new DefaultTableModel(nombreColumnas, 0);

		btnAddCita = new JButton("Añadir Cita");
		btnEditCita = new JButton("Editar Cita");

		JPanel panelBoton = new JPanel();
		panelBoton.add(btnAddCita);
		panelBoton.add(btnEditCita);
		panelBase.add(panelBoton, BorderLayout.SOUTH);

		tablaCitas = new JTable(modeloDatosCitas);
		scrollPaneCitas = new JScrollPane(tablaCitas);
		panelBase.add(scrollPaneCitas, BorderLayout.CENTER);

		add(panelBase);

		btnEditCita.addActionListener(e -> editarCita());
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void editarCita() {
		int selectedRow = tablaCitas.getSelectedRow();
		if (selectedRow != -1) {
			String codigoPaciente = (String) modeloDatosCitas.getValueAt(selectedRow, 0);
			String nombrePaciente = (String) modeloDatosCitas.getValueAt(selectedRow, 1);
			String apellidoPaciente = (String) modeloDatosCitas.getValueAt(selectedRow, 2);
			String nombreDoctor = (String) modeloDatosCitas.getValueAt(selectedRow, 3);
			String apellidoDoctor = (String) modeloDatosCitas.getValueAt(selectedRow, 4);
			
			String fecha = (String) modeloDatosCitas.getValueAt(selectedRow, 5);
			System.out.println("Editar Cita: " + codigoPaciente + ", " + nombrePaciente + ", " + apellidoPaciente + ", "
					+ nombreDoctor + ", " + apellidoDoctor + ", " + fecha);
		} else {
			System.out.println("No hay ninguna cita seleccionada para editar.");
		}
	}
}

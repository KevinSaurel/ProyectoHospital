package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import clases.Doctor;


public class VentanaMedicos extends JFrame {

	private List<Doctor> medicos;
	private JTable tablaMedicos;
	private JScrollPane scrollPaneMedicos;
	private JTextField txtFiltro;
	private DefaultTableModel modeloDatosMedicos;
	private JButton btnAnadirM;
	
	
	public VentanaMedicos (List<Doctor> medicos ) {
		this.medicos = medicos;
		//aqui pongo las llamadas para que filtra por nombre 
		 txtFiltro = new JTextField(20);
		 txtFiltro.getDocument().addDocumentListener(new DocumentListener()
	        {

	            public void changedUpdate(DocumentEvent arg0) 
	            	
	            {
	            	filtrarMedicos();
	            }
	            public void insertUpdate(DocumentEvent arg0) 
	            {
	            	filtrarMedicos();
	            }

	            public void removeUpdate(DocumentEvent arg0) 
	            {
	            	filtrarMedicos();
	            }
	        });
		

//https://stackoverflow.com/questions/38150025/adding-jtable-into-jframe
		//me he basado un poco con eso 
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		
		panelPrincipal.setBorder(BorderFactory.createTitledBorder("TablaMedicos"));
		
		String[]nombreColumnas = {"Nombre ","Apellido","Especialidad","Horario"};
		  modeloDatosMedicos = new DefaultTableModel(nombreColumnas, 0);
	        tablaMedicos = new JTable(modeloDatosMedicos);
		for (Doctor medico : medicos) {
            Object[] fila = {medico.getNombre(), medico.getApellido(), medico.getEspecialidad(), medico.getHorario()};
            modeloDatosMedicos.addRow(fila);  
        }
		btnAnadirM = new JButton("Añadir Médico");
		

		// aqui creo el boton para anadir el medico
		btnAnadirM.addActionListener(e -> {
		    JFrame frameNuevoMedico = new JFrame("Añadir Nuevo Médico");
		    frameNuevoMedico.setSize(400, 300);
		    frameNuevoMedico.setLayout(new GridLayout(8, 2));  // 7 labels + text fields, and 1 button

		    JLabel lblContrasena = new JLabel("Contrasena:");
		    JTextField txtContrasena = new JTextField();
		    JLabel lblNombre = new JLabel("Nombre:");
		    JTextField txtNombre = new JTextField();
		   JLabel lblApellido = new JLabel("Apellido:");
		    JTextField txtApellido = new JTextField();
		    JLabel lblEdad = new JLabel("Edad:");
		    JTextField txtEdad = new JTextField();
		    JLabel lblUbicacion = new JLabel("Ubicacion:");
		    JTextField txtUbicacion = new JTextField();
		    
		    JLabel lblEspecialidad = new JLabel("Especialidad:");
		    JTextField txtEspecialidad = new JTextField();
		    JLabel lblHorario = new JLabel("Horario:");
		    JTextField txtHorario = new JTextField();
		    
		   

		    // Add a submit button to confirm and close
		    JButton btnSubmit = new JButton("Añadir Médico");

		    // Add components to the frame
		    frameNuevoMedico.add(lblContrasena);
		    frameNuevoMedico.add(txtContrasena);
		    frameNuevoMedico.add(lblNombre);
		    frameNuevoMedico.add(txtNombre);
		    frameNuevoMedico.add(lblApellido);
		    frameNuevoMedico.add(txtApellido);
		    frameNuevoMedico.add(lblEdad);
		    frameNuevoMedico.add(txtEdad);
		    frameNuevoMedico.add(lblUbicacion);
		    frameNuevoMedico.add(txtUbicacion);
		    frameNuevoMedico.add(lblEspecialidad);
		    frameNuevoMedico.add(txtEspecialidad);
		    frameNuevoMedico.add(lblHorario);
		    frameNuevoMedico.add(txtHorario);
		    
		    
		    frameNuevoMedico.add(new JLabel());  //label vacio para llenar ultimo grid
		    frameNuevoMedico.add(btnSubmit);

		    
		    btnSubmit.addActionListener(e2 -> {
		    	String contrasena = txtContrasena.getText();
		        String nombre = txtNombre.getText();
		        String apellido = txtApellido.getText();
		        int Edad = Integer.parseInt(txtEdad.getText());
		        String ubicacion = txtUbicacion.getText();
		        String especialidad = txtEspecialidad.getText();
		        String horario = txtHorario.getText();

		     //por si hay campos vacios 
		        if (nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty() || horario.isEmpty()|| Edad<0|| ubicacion.isEmpty()) {
		            JOptionPane.showMessageDialog(frameNuevoMedico, "Por favor, rellena todos los campos.");
		            return;
		        }
		        Doctor nuevoMedico = new Doctor(contrasena ,nombre, apellido,Edad , ubicacion, especialidad, horario);
		        medicos.add(nuevoMedico);

		       
		        modeloDatosMedicos.addRow(new Object[]{nombre, apellido, especialidad, horario});

		      
		        frameNuevoMedico.dispose();
		    });

		
		    frameNuevoMedico.setLocationRelativeTo(null);  // Center the window on the screen
		    frameNuevoMedico.setVisible(true);
		});

		
		JPanel panelArriba = new JPanel(new BorderLayout());
		panelArriba.add(txtFiltro, BorderLayout.CENTER);
		panelArriba.add(btnAnadirM,BorderLayout.EAST);
		
		tablaMedicos = new JTable(modeloDatosMedicos);	
		//scroll pane crear y meter al pael 
		scrollPaneMedicos = new JScrollPane(tablaMedicos);
        //this.add(scrollPaneMedicos);
        
        panelPrincipal.add(scrollPaneMedicos, BorderLayout.CENTER);
        panelPrincipal.add(panelArriba , BorderLayout.NORTH);

        
        setTitle("Lista de Médicos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400); 
        setLocationRelativeTo(null); 
        add(panelPrincipal);
        setVisible(true);
	}
	private void initTables() {
		
		
		this.tablaMedicos = new JTable(this.modeloDatosMedicos);
		
		
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			// por si es null
		    if (value != null && value instanceof String) {
		        String valueStr = (String) value;	
		        
			if(valueStr.contains(txtFiltro.getText())) {
				
			}
			
			//Si el valor es un str numero se convierte en un int y lo centraliza
			} else if (value instanceof Number) {
				result.setHorizontalAlignment(JLabel.CENTER);
			} else {
				//Si el valor es texto pero representa un nÃºmero se renderiza centrado
				try {
					Integer.parseInt(value.toString());
					result.setHorizontalAlignment(JLabel.CENTER);				
				} catch(Exception ex) {
					result.setText(value.toString());
				}		
			}
			
			return result;
		};
		
		//el costomizador de cells
		TableCellRenderer headerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());			
			result.setHorizontalAlignment(JLabel.CENTER);
			
			switch (value.toString()) {
				case "nombre":
				case "apellido":
				case "especialidad":
					result.setHorizontalAlignment(JLabel.LEFT);
			}
			
			result.setBackground(table.getBackground());
			result.setForeground(table.getForeground());
			
			result.setOpaque(true);
			
			return result;
		};
		
		
		
		
	
		this.tablaMedicos.setRowHeight(26);
		this.tablaMedicos.isCellEditable(tablaMedicos.getSelectedRow(), tablaMedicos.getSelectedColumn());
		//para que no se muevan columnas
		this.tablaMedicos.getTableHeader().setReorderingAllowed(false);
		this.tablaMedicos.getTableHeader().setResizingAllowed(false);
		//orden delault 
		this.tablaMedicos.setAutoCreateRowSorter(true);
		
		//Se establecen los renderers al la cabecera y el contenido
		this.tablaMedicos.getTableHeader().setDefaultRenderer(headerRenderer);		
		this.tablaMedicos.setDefaultRenderer(Object.class, cellRenderer);
		
	
		
		//ancura cells
		this.tablaMedicos.getColumnModel().getColumn(2).setPreferredWidth(400);
		
	
		
		
	
}
	private void filtrarMedicos() {
		//Se borran los datos 
		this.modeloDatosMedicos.setRowCount(0);
		
		String filtro = txtFiltro.getText().toLowerCase();
		for(Doctor medico : medicos) {
			if(medico.getNombre().toLowerCase().contains(filtro)) {
				this.modeloDatosMedicos.addRow(
						new Object[] {medico.getNombre(), medico.getApellido(), medico.getEspecialidad(), medico.getHorario()} );
	
			}
			
		}
	}
	
}

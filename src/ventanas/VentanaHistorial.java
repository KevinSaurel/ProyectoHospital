package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import clases.Administrador;
import clases.Doctor;
import clases.Historial;
import clases.Paciente;
import clases.Persona;

public class VentanaHistorial extends JFrame{
	JButton btnAnadir ;
	private JTable tableHistorial;
	private HistorialTableModel tableModel;	
	private Color color;
	private JButton btnVolver;
	
	//JLabel jlabel;
	public VentanaHistorial(Paciente paciente , Persona usuario) {
		 btnAnadir = new JButton("Anadir");
		
		 btnVolver = new JButton("Volver");
		 
		 color =  new Color(6,99,133);
		
		ImageIcon im = new ImageIcon("src/recursos/hospital.png");
		setIconImage(im.getImage());
		 
		 JPanel panelN = new JPanel(new BorderLayout());
		GridLayout grid1 = new GridLayout(1,6);
		
		JPanel panelPrincipal = new JPanel(new GridLayout(2,1));
		// panelPrincipal.add(grid1 , BorderLayout.NORTH);
		//Mitad 1 del panel osea  la suerior ,panelN North
		 JPanel panelUpper = new JPanel(grid1);
		for (int i = 0; i < 5; i++) {
			JLabel l = new JLabel("");
			panelUpper.add(l);
			}
		if(!(paciente instanceof Paciente)) {
		panelUpper.add(btnAnadir);
		}
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel pU = crearPanelN();
		p2.add(pU,BorderLayout.NORTH);
		p2.add(panelUpper,BorderLayout.CENTER);
		panelN.add(p2 , BorderLayout.NORTH);
		///panelN , center
		GridLayout grid2 = new GridLayout(2,3);
		JPanel panelCenter = new JPanel(grid2);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		JLabel nombre = new JLabel("Nombre: " + paciente.getNombre());
		JLabel apellido = new JLabel("Apellido: " + paciente.getApellido());
		JLabel edad = new JLabel("Edad: " + paciente.getEdad());
		JLabel ubicacion = new JLabel("Ubicaci�n: " + paciente.getUbicacion());
		JLabel codigoPaciente = new JLabel("C�digo Paciente: " + paciente.getCodigoPaciente());
		
		//nombre.setFont(font);
		
		panelCenter.add(nombre);
		panelCenter.add(apellido);
		panelCenter.add(edad);
		panelCenter.add(ubicacion);
		panelCenter.add(codigoPaciente);
		panelCenter.add(new JLabel(""));
		anadirColores(panelCenter.getComponents(), new Color(6,99,133));
		panelCenter.setBackground(color);
		/////
		//anadir panelupper and panelCenert
		
		panelN.add(panelCenter , BorderLayout.CENTER);
		/////
		JPanel panelS = new JPanel(new BorderLayout());
		JPanel panel2Upper = new JPanel(new GridLayout(1,6));
		setEmptyLabel(panel2Upper,5);
		panel2Upper.add(btnAnadir);
		////
		// panelHistorial = new JPanel(new GridLayout(0, 1));
		 tableModel = new HistorialTableModel(paciente.getHistorialPaciente());
	        tableHistorial = new JTable(tableModel);
	        tableHistorial.setFillsViewportHeight(true);
	        
		JScrollPane pane = new JScrollPane(tableHistorial);
		
		
		
		
		panelS.add(panel2Upper,BorderLayout.NORTH);
		panelS.add(pane,BorderLayout.CENTER);
		///
		panelPrincipal.add(panelN);
		panelPrincipal.add(panelS);
		setContentPane(panelPrincipal); 
        panelPrincipal.setVisible(true);
        
		
		  btnAnadir.addActionListener(e ->{
			  if(usuario instanceof Doctor) {
				  
			  anadirHistorial(paciente , usuario);
			  }
	        });

	        setTitle("Historial del Paciente");
	        setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setVisible(true);
	       
	}


 
public Object setEmptyLabel(JPanel panel, int numeroLabels) {
    JLabel emptyLabel = new JLabel("");
    for (int i = 0; i < numeroLabels; i++) {
        panel.add(emptyLabel);
    }
    return panel;
}

public void anadirHistorial(Paciente paciente , Persona medico) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setSize(300, 150);

    JTextField textField = new JTextField();
    panel.add(textField, BorderLayout.CENTER);

    JButton btnSubmit = new JButton("Guardar");
    panel.add(btnSubmit, BorderLayout.SOUTH);

    btnSubmit.addActionListener(e -> {
        String newEntry = textField.getText();
        if (!newEntry.isEmpty()) {
            // Assuming that getHistorialPaciente() returns a List<Historial> and Historial has a setCausa method
            Historial nuevoHistorial = new Historial();
            nuevoHistorial.setCausa(newEntry);
            nuevoHistorial.setFecha(new java.util.Date());
            
            if(medico instanceof Doctor) {
            	 nuevoHistorial.setMedico((Doctor) medico); 
            //nuevoHistorial.setMedico(medico);
            }
            paciente.getHistorialPaciente().add(0, nuevoHistorial);

            // Update table model after adding new entry
            tableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "Historial a�adido exitosamente.");
        } else {
            JOptionPane.showMessageDialog(panel, "Por favor, ingrese un texto.");
        }
    });

    JOptionPane.showMessageDialog(this, panel, "A�adir Historial", JOptionPane.PLAIN_MESSAGE);
}


private void anadirColores(Component[] components ,Color color) {
	Font font = new Font("Arial", Font.PLAIN, 20);
	for(Component component :components) {
		if(component instanceof JLabel) {
			JLabel label = (JLabel)component;
		label.setFont(font);
		 label.setHorizontalAlignment(SwingConstants.CENTER);
		//component.setBackground(color);
		label.setForeground(color.white);
		
		}else if (component instanceof JButton) {

			
			component.setBackground(color);
			component.setForeground(Color.white);
			component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
			component.addMouseListener(new MouseAdapter() {
				 
		            @Override
		            public void mouseEntered(MouseEvent e) {
		            	component.setBackground(Color.white); 
		            	component.setForeground(color);
		            }

		            @Override
		            public void mouseExited(MouseEvent e) {
		            	component.setBackground(color); 
		            	component.setForeground(Color.white);
		            }
		        });
		}
	}
}
private JPanel crearPanelN() {
	JPanel grid = new JPanel();
	grid.setLayout(new GridLayout(1,5,10,50));
//aqui quiero meter el logo donde el primer hueco de grid 
	JButton btnParametros = new JButton("Parametros");
	JButton btnUsuario = new JButton("Usuario");
	
	ImageIcon iconoHospital = new ImageIcon(getClass().getResource("/recursos/hospital.png"));

	
	Image imagenEscalada = iconoHospital.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	iconoHospital = new ImageIcon(imagenEscalada);
	
	JLabel labelConIcono = new JLabel(iconoHospital);

	grid.add(labelConIcono);
	
	grid.add(btnVolver);
	grid.add(btnParametros);
	grid.add(btnUsuario);
	anadirColores(grid.getComponents(),color);
	
	
	grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	grid.setBackground(Color.white);
	JPanel p = new JPanel();
	
	p.setBackground(color.white);
	anadirColores(p.getComponents(), color);
	p.setLayout(new GridLayout(1,2));
	JPanel t = new JPanel();
	t.setBackground(color.white);
	anadirColores(t.getComponents(), color);
	t.setLayout(new GridLayout(1,2));
	t.add(btnVolver);
	t.add(labelConIcono);
	p.add(t);
	p.add(new JLabel(""));
	
	grid.add(p);
	grid.add(new JLabel(""));
	
	//grid.add(btnVolver);
	grid.add(btnParametros);
	grid.add(btnUsuario);
	anadirColores(grid.getComponents(),color);
	
	
	return grid;
}
}


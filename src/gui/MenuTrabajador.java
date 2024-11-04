package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import domain.Administrador;
import domain.Cama;
import domain.Cita;
import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;

import java.awt.BorderLayout;
public class MenuTrabajador extends JFrame{
	
//	private JFrame vActual, vAnterior;
//	
	private List<Cama> camas;
	private List<Cita> citas;
	private List<Paciente> pacientes;
	private JButton btnVolver;
	private JButton btnCitas;
	private JButton btnPacientes;
	private JButton btnCamas;
	private JButton btnSesion;
	private JButton btnUsuario;
	private JButton btnMedicos;
	
	
	public  MenuTrabajador(Persona usuario) {
		ArrayList<Doctor> medicos = new ArrayList<>();
		  
		Context context = Context.getInstance();  
    	this.pacientes = context.getPacientes();
		  
    	ImageIcon i = new ImageIcon("src/db/hospital.png");
		setIconImage(i.getImage());
    	
		
		btnVolver = new JButton("Salir");
		
		btnCitas = new JButton("CITAS");
		btnCamas = new JButton("Camas");
		btnPacientes = new JButton("Pacientes");
		btnSesion = new JButton("cerrar sesion");
		btnUsuario = new JButton("Usuario");
		btnMedicos = new JButton("Medicos");
		Color color = new Color(6,99,133);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(1,5,10,50));
// aqui quiero meter el logo donde el primer hueco de grid 
		
		

		ImageIcon iconoHospital = new ImageIcon(getClass().getResource("/db/hospital.png"));
		setIconImage(iconoHospital.getImage());
		
		Image imagenEscalada = iconoHospital.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		iconoHospital = new ImageIcon(imagenEscalada);
		JLabel labelConIcono = new JLabel(iconoHospital);

		grid.add(labelConIcono);
		
		
		grid.add(btnSesion);
		grid.add(btnUsuario);
		if(usuario instanceof Administrador ) {
			grid.add(btnMedicos);
		}
		
		anadirColores(grid.getComponents(),color);
		
		
		
		
		grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		grid.setBackground(Color.white);
		panel.add(grid,BorderLayout.NORTH);
		
		
		JPanel pngHistorial = new JPanel();
		pngHistorial.setLayout(new GridLayout(1 , 2 , 10 , 10 ));
		
		ImageIcon imagenMedicos = new ImageIcon(getClass().getResource("/db/MedicosFelices.png"));

		
		Image imagenmodi = imagenMedicos.getImage().getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH);
		imagenMedicos = new ImageIcon(imagenmodi);
		JLabel labelMedicos = new JLabel(imagenMedicos);
		pngHistorial.add(labelMedicos);
		pngHistorial.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel cita = new JPanel();
		cita.setLayout(new GridLayout(2,1,10,10));
		
		cita.add(btnPacientes);
		anadirColores(cita.getComponents(),color);
		cita.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel ubicacionHospital = new JPanel();
		ubicacionHospital.setLayout(new GridLayout(1,2,10,10));
		ubicacionHospital.add(btnCitas);
		ubicacionHospital.add(btnCamas);
		ubicacionHospital.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		anadirColores(ubicacionHospital.getComponents(),color);
		cita.add(ubicacionHospital);
		pngHistorial.add(cita);
		panel.add(pngHistorial , BorderLayout.CENTER);
		//panel.add(btnVolver,BorderLayout.SOUTH);
		
		/**
		 * Cambiamos el tipo de letra en los botones
		 * 
		 **/
		
		btnCitas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnPacientes.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnCamas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        
        btnPacientes .addActionListener(e -> {
            VentanaPacientes ventana = new VentanaPacientes(pacientes , usuario);
            ventana.setVisible(true);
            this.dispose(); 
        });
        btnUsuario.addActionListener(e -> {
            VentanaVisualizarUsuario ventana = new VentanaVisualizarUsuario(usuario);
            ventana.setVisible(true);
            this.dispose(); 
        });
        btnSesion.addActionListener(e -> {
            VentanaSeleccion ventana = new VentanaSeleccion();
            ventana.setVisible(true);
            this.dispose(); 
        });
        
        btnCamas.addActionListener((e) -> {
        	VentanaCamas ventana = new VentanaCamas(camas, usuario);
            ventana.setVisible(true);
            this.dispose();
        });
        
        btnCitas.addActionListener((e) -> {
        	VentanaCitas ventana = new VentanaCitas(citas, usuario);
            ventana.setVisible(true);
            this.dispose();
        });
        
        
  	
	}
	

	private void anadirColores(Component[] components ,Color color) {
		for(Component component :components) {
			if(component instanceof JButton) {
		
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
}


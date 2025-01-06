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
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Administrador;
import domain.Cama;
import domain.Cita;
import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;
import persistente.GestorBD;

import java.awt.BorderLayout;
public class MenuTrabajador extends JFrame{
	
//	private JFrame vActual, vAnterior;
//	
	private List<Cama> camas;
	private List<Cita> citas;
	private List<Paciente> pacientes;
	private List<Doctor>medicos;
	private JButton btnVolver;
	private JButton btnCitas;
	private JButton btnPacientes;
	private JButton btnCamas;
	private JButton btnSesion;
	private JButton btnUsuario;
	private JButton btnMedicos;
	
	private JPanel panelHilo; 
	private JLabel labelMedicos;
	protected GestorBD gestorBD;
	
	
	public  MenuTrabajador(Persona usuario, GestorBD gestorBD) {
		
		  
		Context context = Context.getInstance();  
    	this.pacientes = context.getPacientes();
    	this.medicos = gestorBD.getMedicos();
    	ImageIcon i = new ImageIcon("resources/images/hospital.png");

    	//ImageIcon i = new ImageIcon("/resources/images/hospital.png");
    	setIconImage(i.getImage());
    	
		
		
		btnVolver = new JButton("SALIR");
		
		btnCitas = new JButton("CITAS");
		btnCamas = new JButton("CAMAS");
		btnPacientes = new JButton("PACIENTES");
		btnSesion = new JButton("CERRAR SESION");
		btnUsuario = new JButton("USUARIO");
		btnMedicos = new JButton("MEDICOS");
		Color color = new Color(6,99,133);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(1,5,10,50));
// aqui quiero meter el logo donde el primer hueco de grid 
		
		
		ImageIcon iconoHospital = new ImageIcon("resources/images/hospital.png");

		//ImageIcon iconoHospital = new ImageIcon("resources/images/hospital.png");
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
		
		
		
		
		
		grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		grid.setBackground(Color.white);
		panel.add(grid,BorderLayout.NORTH);
		
		
		 JPanel pngHistorial = new JPanel();
		  pngHistorial.setLayout(new GridLayout(1 , 2 , 10 , 10 ));
		  ImageIcon imagenMedicos = new ImageIcon("resources/images/MedicosFelices.png");

		//ImageIcon imagenMedicos = new ImageIcon("resources/images/MedicosFelices.png");

		
		Image imagenmodi = imagenMedicos.getImage().getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH);
		panelHilo = new JPanel(new BorderLayout());
		imagenMedicos = new ImageIcon(imagenmodi);
		
		HiloImagen hilo = new HiloImagen();
		hilo.start();
		 //this.imagenMedicos = new JLabel(imagenMedicos);
		pngHistorial.add(panelHilo);
		pngHistorial.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel cita = new JPanel();
		cita.setLayout(new GridLayout(2,1,10,10));
		
		cita.add(btnPacientes);
		anadirColores(cita.getComponents(),color);
		cita.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel ubicacionHospital = new JPanel();
		ubicacionHospital.setLayout(new GridLayout(1,2,10,0));
		ubicacionHospital.add(btnCitas);
		ubicacionHospital.add(btnCamas);
		//ubicacionHospital.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		cita.add(ubicacionHospital);
		pngHistorial.add(cita);
		panel.add(pngHistorial , BorderLayout.CENTER);
		//panel.add(btnVolver,BorderLayout.SOUTH);
		
		/**
		 * Cambiamos el tipo de letra en los botones
		 * 
		 **/
		
		anadirColores(panel.getComponents(),color);
		

		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        
        btnPacientes .addActionListener(e -> {
            VentanaPacientes ventana = new VentanaPacientes(pacientes , usuario, gestorBD);
            ventana.setVisible(true);
            this.dispose(); 
        });
        btnMedicos .addActionListener(e -> {
            VentanaMedicos ventana = new VentanaMedicos(medicos , usuario, gestorBD);
            ventana.setVisible(true);
            this.dispose(); 
        });
        btnUsuario.addActionListener(e -> {
            VentanaVisualizarUsuario ventana = new VentanaVisualizarUsuario(usuario, gestorBD);
            ventana.setVisible(true);
            this.dispose(); 
        });
        btnSesion.addActionListener(e -> {
            VentanaSeleccion ventana = new VentanaSeleccion(gestorBD);
            ventana.setVisible(true);
            this.dispose(); 
        });
        
        btnCamas.addActionListener((e) -> {
            VentanaCamas ventana = new VentanaCamas(gestorBD);
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
			}else if(component instanceof JPanel) {
				JPanel componentN = (JPanel)component;
				anadirColores(componentN.getComponents(), color);
				
			}
		}
	}
	private class HiloImagen extends Thread {
		private int currentIndex = 0;
	    public void run() {
	    	ImageIcon imagenMenu = new ImageIcon("resources/images/Menuimage.png");
	    	
	        Image imagenMenuEscalada = imagenMenu.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenMenuIcon = new ImageIcon(imagenMenuEscalada);
	        // Load and scale images once at the start
	        ImageIcon imagenMedicos = new ImageIcon("resources/images/MedicosFelices.png");
	        Image imagenMedicosEscalada = imagenMedicos.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenMedicosIcon = new ImageIcon(imagenMedicosEscalada);

	        ImageIcon imagenSala = new ImageIcon("resources/images/imagenSala.png");
	        Image imagenSalaEscalada = imagenSala.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenSalaIcon = new ImageIcon(imagenSalaEscalada);
	        this.currentIndex = 0;
	        try {
	            while (true) {
	            	cambiarImagen();
	                Thread.sleep(5000);
	            }
	        } catch (InterruptedException e) {
	            System.out.println("Countdown interrupted!");
	        }
	       
	    }
	    private void cambiarImagen() {
	    	ImageIcon imagenMenu = new ImageIcon("resources/images/Menuimage.png");
	    	
	        Image imagenMenuEscalada = imagenMenu.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenMenuIcon = new ImageIcon(imagenMenuEscalada);
	        // Load and scale images once at the start
	        ImageIcon imagenMedicos = new ImageIcon("resources/images/MedicosFelices.png");
	        Image imagenMedicosEscalada = imagenMedicos.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenMedicosIcon = new ImageIcon(imagenMedicosEscalada);

	        ImageIcon imagenSala = new ImageIcon("resources/images/imagenSala.png");
	        Image imagenSalaEscalada = imagenSala.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
	        ImageIcon imagenSalaIcon = new ImageIcon(imagenSalaEscalada);
	        List<ImageIcon>list = new ArrayList();
	        list.add(imagenMenuIcon);
	        list.add(imagenMedicosIcon);
	        list.add(imagenSalaIcon);
	        
	        SwingUtilities.invokeLater(() -> {
	        	
	        		 panelHilo.removeAll(); // Clear the panel before adding a new image
	                 labelMedicos = new JLabel(list.get(currentIndex)); // Display "imagenSala" image
	                 panelHilo.add(labelMedicos, BorderLayout.CENTER);
	                 panelHilo.revalidate();
	                 panelHilo.repaint();
	                 this.currentIndex++;
	                 if(currentIndex==list.size()) {
	                	 this.currentIndex=0;
	                 }
               
            });
        }
	}
}


package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clases.Paciente;

public class MenuPaciente extends JFrame{
	
//	private JFrame vActual, vAnterior;
//	
	private JButton btnVolver;
	private JButton btnCitas;
	private JButton btnHospitales;
	private JButton btnMedicos;
	private JButton btnHistorial;
	private JButton btnContacto;
	private JButton btnParametros;
	private JButton btnUsuario;
	
	
	public MenuPaciente() {
		
//		vActual = this;
//		this.vAnterior = vAnterior;
		
		btnVolver = new JButton("VOLVER");
		
		btnCitas = new JButton("CITAS");
		btnHospitales = new JButton("HOSPITALES");
		btnMedicos = new JButton("MEDICOS");
		btnHistorial = new JButton("HISTORIAL");
		btnContacto = new JButton("Contacto");
		btnParametros = new JButton("Parametros");
		btnUsuario = new JButton("Usuario");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(1,5,10,50));
// aqui quiero meter el logo donde el primer hueco de grid 

		ImageIcon iconoHospital = new ImageIcon(getClass().getResource("/recursos/hospital.png"));

		
		Image imagenEscalada = iconoHospital.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		iconoHospital = new ImageIcon(imagenEscalada);
		JLabel labelConIcono = new JLabel(iconoHospital);

		grid.add(labelConIcono);
		
		grid.add(btnContacto);
		grid.add(btnParametros);
		grid.add(btnUsuario);
		grid.add(btnHistorial);
		
		grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		grid.setBackground(Color.RED);
		panel.add(grid,BorderLayout.NORTH);
		
		
		JPanel pngHistorial = new JPanel();
		pngHistorial.setLayout(new GridLayout(1 , 2 , 10 , 10 ));
		
		ImageIcon imagenMedicos = new ImageIcon(getClass().getResource("/recursos/MedicosFelices.png"));

		
		Image imagenmodi = imagenMedicos.getImage().getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH);
		imagenMedicos = new ImageIcon(imagenmodi);
		JLabel labelMedicos = new JLabel(imagenMedicos);
		pngHistorial.add(labelMedicos);
		pngHistorial.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel cita = new JPanel();
		cita.setLayout(new GridLayout(2,1,10,10));
		cita.add(btnHistorial);
		cita.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel ubicacionHospital = new JPanel();
		ubicacionHospital.setLayout(new GridLayout(1,2,10,10));
		ubicacionHospital.add(btnCitas);
		ubicacionHospital.add(btnHospitales);
		ubicacionHospital.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		cita.add(ubicacionHospital);
		pngHistorial.add(cita);
		panel.add(pngHistorial , BorderLayout.CENTER);
		
		
//		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
//		box.add(new JTextField("Aqui nombre del paciente"),BorderLayout.CENTER);
//		box.add(new JTextField("Otros datos de paciente"),BorderLayout.CENTER);
//		panel.add(box,BorderLayout.WEST);
		
		panel.add(btnVolver,BorderLayout.SOUTH);
		
		
		
		
		
		
		
		
		
		/**
		 * Cambiamos el tipo de letra en los botones
		 * 
		 **/
		btnVolver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnCitas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnHistorial.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnHospitales.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnMedicos.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnContacto.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnParametros.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnUsuario.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		
		/**
		 * Establecemos el color de el fondo de los botones en blanco
		 **/
		btnVolver.setBackground(Color.WHITE);
		btnCitas.setBackground(Color.WHITE);
		btnHistorial.setBackground(Color.WHITE);
		btnHospitales.setBackground(Color.WHITE);
		btnMedicos.setBackground(Color.WHITE);
		btnUsuario.setBackground(Color.WHITE);
		btnParametros.setBackground(Color.WHITE);
		btnContacto.setBackground(Color.WHITE);
		
//		
//		
//		btnVolver.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				/*Aqui escribimos el codigo a ejecutar cuando se clicke el boton
//				 * */
//				vActual.dispose();
//				
//				//Volvemos a visualizar la ventana anterior
//				vAnterior.setVisible(true);
//				
//			}
//		});
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        
		
		
		
	}

}

package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	
	
	public MenuPaciente() {
		
//		vActual = this;
//		this.vAnterior = vAnterior;
		
		btnVolver = new JButton("VOLVER");
		
		btnCitas = new JButton("CITAS");
		btnHospitales = new JButton("HOSPITALES");
		btnMedicos = new JButton("MEDICOS");
		btnHistorial = new JButton("HISTORIAL");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(1,4,10,10));
//		grid.add(new JButton("Citas"));
//		grid.add(new JButton("Hospitales"));
//		grid.add(new JButton("Medicos"));
//		grid.add(new JButton("Historial"));
		grid.add(btnCitas);
		grid.add(btnHospitales);
		grid.add(btnMedicos);
		grid.add(btnHistorial);
		
		panel.add(grid,BorderLayout.NORTH);
		
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		
		
		box.add(new JTextField("Aqui nombre del paciente"),BorderLayout.CENTER);
		box.add(new JTextField("Otros datos de paciente"),BorderLayout.CENTER);
		panel.add(btnVolver,BorderLayout.SOUTH);
		panel.add(box,BorderLayout.WEST);
		
		/**
		 * Cambiamos el tipo de letra en los botones
		 * 
		 **/
		btnVolver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnCitas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnHistorial.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnHospitales.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		btnMedicos.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		
		/**
		 * Establecemos el color de el fondo de los botones en blanco
		 **/
		btnVolver.setBackground(Color.WHITE);
		btnCitas.setBackground(Color.WHITE);
		btnHistorial.setBackground(Color.WHITE);
		btnHospitales.setBackground(Color.WHITE);
		btnMedicos.setBackground(Color.WHITE);
		
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
		
		setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        
		
		
		
	}

}

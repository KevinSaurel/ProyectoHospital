package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VentanaSeleccion extends JFrame{
	
	private static final long serialVersionUID = 1L;
	protected JButton botonCerrar, botonPaciente, botonTrabajador;
	protected JPanel pAbajo, pCentro;
	protected JTextField textoIdentificacion;
	protected JLabel lblIdentificacion, lblImagenTrabajador, lblImagenCliente;
	
	public VentanaSeleccion() {
		
		ImageIcon i = new ImageIcon("src/recursos/hospital.png");
		setIconImage(i.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 600, 400);
		Color color = new Color(6,99,133);
		
		
		
		botonCerrar = new JButton("CERRAR");
		botonPaciente = new JButton("SOY PACIENTE");
		botonTrabajador = new JButton("SOY TRABAJADOR");


		lblIdentificacion = new JLabel("Introduce que eres: ");
//		lblImagenTrabajador = new JLabel(new ImageIcon("src\\Imagenes\\imagenTrabajador.jpeg"));
//		lblImagenCliente = new JLabel(new ImageIcon("src\\Imagenes\\ImagenCliente.jpeg"));

		pAbajo = new JPanel();
		pCentro = new JPanel();
		pCentro.setBackground(color.white);
		pAbajo.setBackground(color.white);

		textoIdentificacion = new JTextField(20);

		pCentro.add(lblIdentificacion);
		pCentro.add(botonTrabajador);
//		pCentro.add(lblImagenTrabajador);
		pCentro.add(botonPaciente);
//		pCentro.add(lblImagenCliente);
		pAbajo.add(botonCerrar);
		
		anadirColores(pCentro.getComponents(), color);
		anadirColores(pAbajo.getComponents(), color);
		getContentPane().add(pAbajo, BorderLayout.SOUTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);

		botonCerrar.addActionListener((e) -> {
			System.exit(0);
			
		});

		botonTrabajador.addActionListener((e) -> {
			 VentanaInicioTrabajador ventana = new VentanaInicioTrabajador();
	            ventana.setVisible(true);
	            this.dispose(); 
		});


		 botonPaciente.addActionListener(e -> {
	       
	            VentanaInicioPaciente ventana = new VentanaInicioPaciente();
	            ventana.setVisible(true);
	            this.dispose(); 
	        });
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void anadirColores(Component[] components ,Color color) {
		for(Component component :components) {
			if(component instanceof JButton) {
		
			component.setBackground(color);
			component.setForeground(Color.white);
			component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
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
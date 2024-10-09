package ventanas;

import java.awt.BorderLayout;

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
		
		
		botonCerrar = new JButton("CERRAR");
		botonPaciente = new JButton("SOY PACIENTE");
		botonTrabajador = new JButton("SOY TRABAJADOR");


		lblIdentificacion = new JLabel("Introduce que eres: ");
		lblImagenTrabajador = new JLabel(new ImageIcon("src\\Imagenes\\imagenTrabajador.jpeg"));
		lblImagenCliente = new JLabel(new ImageIcon("src\\Imagenes\\ImagenCliente.jpeg"));

		pAbajo = new JPanel();
		pCentro = new JPanel();

		textoIdentificacion = new JTextField(20);

		pCentro.add(lblIdentificacion);
		pCentro.add(botonTrabajador);
		pCentro.add(lblImagenTrabajador);
		pCentro.add(botonPaciente);
		pCentro.add(lblImagenCliente);
		pAbajo.add(botonCerrar);

		getContentPane().add(pAbajo, BorderLayout.SOUTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);

		botonCerrar.addActionListener((e) -> {
			System.exit(0);
			
		});

		botonTrabajador.addActionListener((e) -> {
			SwingUtilities.invokeLater(new Runnable() {
			    @Override
				public void run() {
					new VentanaInicioTrabajador();	
					
				}
			});	
            dispose();
		});

		botonPaciente.addActionListener((e) -> {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new VentanaInicioPaciente();
					
				}
			});
			
            dispose();
		});
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
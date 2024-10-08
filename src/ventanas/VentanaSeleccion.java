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
	protected JButton botonCerrar, botonCliente, botonTrabajador;
	protected JPanel pAbajo, pCentro;
	protected JTextField textoIdentificacion;
	protected JLabel lblIdentificacion, lblImagenTrabajador, lblImagenCliente;
	
	public VentanaSeleccion() {
		
		ImageIcon h = new ImageIcon("src/Imagenes/h.png");
		setIconImage(h.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		
		botonCerrar = new JButton("CERRAR");
		botonCliente = new JButton("SOY CLIENTE");
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
		pCentro.add(botonCliente);
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

		botonCliente.addActionListener((e) -> {
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
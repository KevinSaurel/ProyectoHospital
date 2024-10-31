package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import clases.Persona;

public class VentanaVisualizarUsuario extends JFrame{
	
	private JButton btnVolver;
	private Persona usuario;
	private Color color;
	private Color color2;
	
	public  VentanaVisualizarUsuario(Persona usuario) {
		this.usuario = usuario;
		 this.color = new Color(6,99,133);
		Color color2 = new Color(7,120,163);

		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(color2);
		add(panel);
		
		JPanel panelN = new JPanel();
		panelN.setLayout(new BorderLayout());
		
		//
		this.btnVolver = new JButton();
		crearBtnVolver(btnVolver);
		panelN.add(btnVolver,BorderLayout.WEST);
		///
		JPanel grid = crearPanelN();
		//grid.setBackground(color2);
		panel.add(grid,BorderLayout.NORTH);
		
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new GridLayout(5,2,10,100));
		
		crearCentro(panelCentro);
		anadirColores(panelCentro.getComponents(), color);
		panel.add(panelCentro,BorderLayout.CENTER);
		
		 setTitle("Visualizar tu usuario");
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setLocationRelativeTo(null);
	        
	        setVisible(true);
	}

private Component crearCentro(JPanel panel) {
	JLabel lNombre = new JLabel("Nombre : "+usuario.getNombre());
	
	JLabel lApellido = new JLabel("Apellido : "+usuario.getApellido());
	JLabel lEdad = new JLabel("Edad : "+usuario.getEdad());
	JLabel lContrasena = new JLabel("Contrasena : "+usuario.getContrasena());
	JLabel lUbicacion= new JLabel("Ubicacion : "+usuario.getUbicacion());
	
	JButton btnNombre = new JButton();
	btnNombre.setText("Cambiar Nombre");
	JButton btnApellido = new JButton();
	btnApellido.setText("Cambiar Apellido");
	JButton btnEdad = new JButton();
	btnEdad.setText("Cambiar Edad");
	JButton btnContrasena = new JButton();
	btnContrasena.setText("Cambiar Contrasena");
	JButton btnUbicacion = new JButton();
	btnUbicacion.setText("Cambiar Ubicacion");
	
	btnNombre.addActionListener(e -> {
//        MenuTrabajador ventana = new MenuTrabajador(usuario);
//        ventana.setVisible(true);
//        
    });
	panel.add(lNombre);
	panel.add(btnNombre);
	panel.add(lApellido);
	panel.add(btnApellido);
	panel.add(lEdad);
	panel.add(btnEdad);
	panel.add(lContrasena);
	panel.add(btnContrasena);
	panel.add(lUbicacion);
	panel.add(btnUbicacion);
	//panel.setBackground(color2);
	return panel;
	
}

private Component crearBtnVolver(JButton btnBack) {
	ImageIcon iconBack = new ImageIcon("src/recursos/icons8-back-25.png");
	 if (iconBack.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
	        System.out.println("Error loading icon.");
	    }
	 btnBack.setIcon(iconBack);
	Color color = new Color(6,99,133);
    btnBack.setBackground(color);
    btnBack.setPreferredSize(new Dimension(80, 25));
    btnBack.addActionListener(e -> {
        MenuTrabajador ventana = new MenuTrabajador(usuario);
        ventana.setVisible(true);
        ((JFrame) btnBack.getTopLevelAncestor()).dispose(); 
    });
    return btnBack;
}
private void anadirColores(Component[] components ,Color color) {
	for(Component component :components) {
		if(component instanceof JButton) {
	
		component.setBackground(color);
		component.setForeground(Color.white);
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
		}else if(component instanceof JLabel) {
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
			((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
			component.setBackground(color);
			component.setForeground(color);
			component.setFont(font);
			
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
package ventanas;


	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	public class ventanaInicio extends JFrame {

	    // Constructor
	    public ventanaInicio() {
	        // Configuraci�n b�sica de la ventana
	        setTitle("Sistema de Gesti�n de Hospital - Inicio de Sesi�n");
	        setSize(400, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);  
	        setResizable(false);  

	       
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(3, 2, 10, 10));  // 3 filas, 2 columnas, espacio entre componentes
	        add(panel);

	        // Crear etiquetas y campos de texto
	        JLabel usuarioLabel = new JLabel("Usuario:");
	        JTextField usuarioCampo = new JTextField();

	        JLabel contrasenaLabel = new JLabel("Contrase�a:");
	        JPasswordField contrasenaCampo = new JPasswordField();

	        // Crear botones
	        JButton botonLogin = new JButton("Iniciar Sesi�n");
	        JButton botonSalir = new JButton("Salir");

	        // Agregar los elementos al panel
	        panel.add(usuarioLabel);
	        panel.add(usuarioCampo);
	        panel.add(contrasenaLabel);
	        panel.add(contrasenaCampo);
	        panel.add(botonLogin);
	        panel.add(botonSalir);
	        
	        String usuarioI = "";
	        // Configurar acciones de los botones
	        botonLogin.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String usuario = usuarioCampo.getText();
	                
	                String contrasena = new String(contrasenaCampo.getPassword());

	                // Verificaci�n simple (por ejemplo, usuario: admin, contrase�a: admin)
	                if (usuario.equals("admin") && contrasena.equals("admin")) {
	                    JOptionPane.showMessageDialog(null, "�Inicio de sesi�n exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
	                    // Aqu� podr�as abrir la ventana principal del sistema de gesti�n de hospital
	                } else {
	                    JOptionPane.showMessageDialog(null, "Usuario o contrase�a incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        botonSalir.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);  
	            }
	        });
	    }

	  
	}



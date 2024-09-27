package ventanas;


	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	public class ventanaInicio extends JFrame {
		
		private JTextField usuarioCampo;
		
		private JPasswordField contrasenaCampo;
		
		private JFrame vActual;
	   
	    public ventanaInicio() {
	       
	    	vActual = this;
	    	
	        setTitle("Sistema de Gestion de Hospital - Inicio de Sesion");
	        setSize(400, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);  
	        setResizable(false);  

	       
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(3, 2, 10, 10));  // 3 filas, 2 columnas, espacio entre componentes
	        add(panel);

	        
	        JLabel usuarioLabel = new JLabel("Usuario:");
	        JTextField usuarioCampo = new JTextField();

	        JLabel contrasenaLabel = new JLabel("Contraseïña:");
	        JPasswordField contrasenaCampo = new JPasswordField();

	        
	        JButton botonLogin = new JButton("Iniciar Sesion");
	        JButton botonSalir = new JButton("Salir");

	       
	        panel.add(usuarioLabel);
	        panel.add(usuarioCampo);
	        panel.add(contrasenaLabel);
	        panel.add(contrasenaCampo);
	        panel.add(botonLogin);
	        panel.add(botonSalir);
	        
	        String usuarioI = "";
	     
	        botonLogin.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String usuario = usuarioCampo.getText();
	                
	                String contrasena = new String(contrasenaCampo.getPassword());

	                // Verificaciï¿½n  usuario: admin, contraseï¿½a: admin)
	                if (usuario.equals("admin") && contrasena.equals("admin")) {
	                    JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
	                    // Aquï¿½ podrï¿½as abrir la ventana principal del sistema de gestiï¿½n de hospital
	                    panel.setVisible(false);
//	                    vaciarCampos();
//						vActual.dispose();
	                    MenuPaciente ventana = new MenuPaciente();
	                    ventana.setVisible(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
//	                    vaciarCampos();
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
//	    public void vaciarCampos() {
//    		//Vaciamos los cuadros de texto
//        	usuarioCampo.setText("");
//        	contrasenaCampo.setText("");
//    	}

	  
	}



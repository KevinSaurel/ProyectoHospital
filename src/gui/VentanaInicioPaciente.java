package gui;


	import javax.swing.*;

import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.util.ArrayList;


	public class VentanaInicioPaciente extends JFrame {
		
		private JTextField usuarioCampo;
		
		private JPasswordField contrasenaCampo;
		
		private JFrame vActual;
		private ArrayList<Paciente>listaPacientes;
	    private Paciente usuario;
	    
	    public VentanaInicioPaciente() {
	       
	    	vActual = this;
	    	Context context = Context.getInstance(); 
	    	listaPacientes = context.getPacientes(); 
	    	System.out.println(listaPacientes);
	    	ImageIcon i = new ImageIcon("src/db/hospital.png");
			setIconImage(i.getImage());
	    	
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

	        JLabel contrasenaLabel = new JLabel("Contrase��a:");
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
	                String usuarioN = usuarioCampo.getText();
	                
	                String contrasenaN = new String(contrasenaCampo.getPassword());

	                // Verificaci�n  usuario: admin, contrase�a: admin)
	                if (usuarioN.equals("admin") && contrasenaN.equals("admin")) {
	                    JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
	                    // Aqu� podr�as abrir la ventana principal del sistema de gesti�n de hospital
	                    panel.setVisible(false);
	                    MenuPaciente ventana = new MenuPaciente(usuario);
	                    ventana.setVisible(true);
	                } else if(listaGetNombre(usuarioN, contrasenaN)==true){
	                	
	                	JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!", "Bienvenido: "+ usuarioN, JOptionPane.INFORMATION_MESSAGE);
	                	MenuPaciente ventana = new MenuPaciente(usuario);
	               
		                   ventana.setVisible(true);
		                   vActual.dispose();
	                
	                }else{
	                
	                    JOptionPane.showMessageDialog(null, "Usuario o contrase�a incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
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
	    
	    public boolean listaGetNombre(String nombre , String contrasena) {
	    	for(Paciente paciente :listaPacientes) {
	    		if (paciente.getNombre().equals(nombre) && paciente.getContrasena().equals(contrasena)) {
	    			usuario = paciente;
	    			System.out.println(usuario);
	    			return true ;
	    			
	    		
	    		}
	    		}
	    return false;
	    	
	    	
			
	  
	    }
	}
	//FUENTE-EXTERNA
	//URL: ()
	//SIN-CAMBIOS ó ADAPTADO ()
	//IAG (herramienta: chatgpt)
	//SIN CAMBIOS ó ADAPTADO (he usado chagpt para resolver duda de la comprobacion de datos )


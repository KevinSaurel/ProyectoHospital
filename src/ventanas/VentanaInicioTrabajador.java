package ventanas;


	import javax.swing.*;

import clases.Context;
import clases.Doctor;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

	public class VentanaInicioTrabajador extends JFrame {
		
		private JTextField usuarioCampo;
		
		private JPasswordField contrasenaCampo;
		
		private JFrame vActual;
	   
		private ArrayList<Doctor> listaMedicos;
		private Doctor usuario;
	   
	    public VentanaInicioTrabajador() {
	      // listaMedicos = new ArrayList<>()
	    	vActual = this;
	    	//Doctor usuario ;
	    	Context context = Context.getInstance(); 
	    	listaMedicos = context.getMedicos(); 
	    	
	    	System.out.println(listaMedicos);
	    	
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
	                
	                String contrasena = new String(contrasenaCampo.getPassword());
	                
	                // Verificaci�n  usuario: admin, contrase�a: admin)
	                if ( usuarioN.equals("admin") && contrasena.equals("admin")) {
	                    JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
	                    // Aqu� podr�as abrir la ventana principal del sistema de gesti�n de hospital
	                    panel.setVisible(false);
//	                    vaciarCampos();
	                    //vActual.dispose();
	                    MenuTrabajador ventana = new MenuTrabajador(usuario);
	                    
	                   ventana.setVisible(true);
	                   panel.setVisible(false);
	                }else if(listaGetNombre(usuarioN, contrasena)==true){
	                	
	                	JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!", "Bienvenido: "+ usuarioN, JOptionPane.INFORMATION_MESSAGE);
	                	MenuTrabajador ventana = new MenuTrabajador(usuario);
	                //	System.out.println(usuario);
		                   ventana.setVisible(true);
		                   panel.setVisible(false);
	                }else {
	                
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
	    	for(Doctor medico :listaMedicos) {
	    		if (medico.getNombre().equals(nombre) && medico.getContrasena().equals(contrasena)) {
	    			usuario = medico;
	    			System.out.println(usuario);
	    			return true ;
	    			
	    		
	    		}
	    		
	    	}
	    	return false;
	    }
	   
	  
	}



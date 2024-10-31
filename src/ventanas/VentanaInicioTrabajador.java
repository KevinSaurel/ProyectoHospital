package ventanas;


	import javax.swing.*;

import clases.Administrador;
import clases.Context;
import clases.Doctor;
import clases.Persona;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		private ArrayList<Administrador>listaAdmin;
		private Persona usuario;
	   
	    public VentanaInicioTrabajador() {
	    	ImageIcon i = new ImageIcon("src/recursos/hospital.png");
			setIconImage(i.getImage());
	    	Color color = new Color(6,99,133);
	      // listaMedicos = new ArrayList<>()
	    	vActual = this;
	    	//Doctor usuario ;
	    	Context context = Context.getInstance(); 
	    	listaMedicos = context.getMedicos(); 
	    	listaAdmin = context.getAdministrador();
	    	
	    	//System.out.println(listaMedicos); //pa comprobar si se carga bien descomenta
	    	
	        setTitle("Sistema de Gestion de Hospital - Inicio de Sesion");
	        setSize(400, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);  
	        setResizable(false);  

	       
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(3, 2, 10, 10));  // 3 filas, 2 columnas, espacio entre componentes
	        panel.setBackground(color.white);
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
	        
	        anadirColores(panel.getComponents(), color);
	        
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
		                   vActual.dispose();
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
	    	for(Administrador admin :listaAdmin) {
	    		if (admin.getNombre().equals(nombre) && admin.getContrasena().equals(contrasena)) {
	    			usuario = admin;
	    			System.out.println(usuario);
	    			return true ;
	    			
	    		
	    		}
	    
	    		
	    	}
	    	return false;
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



package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Cama;
import domain.Context;
import domain.Historial;
import domain.Paciente;
import domain.Persona;

public class VentanaCamas extends JFrame{
	private JButton btnAsignarCama, btnVaciarCama, btnBack, btnAñadirCama;
	private List<Cama> camas;
	private JPanel pEste, pOeste, pCentro, pNorte, pSur;
	private JScrollPane scrollTabla; 
	private JTable tableCama;
	private CamaTableModel tableModel;
	private Persona usuario;
	
	public VentanaCamas(List<Cama> camas, Persona usuarioP) {
	
		
		usuario = usuarioP;
		Color color =  new Color(6,99,133);
		
		ImageIcon i = new ImageIcon("src/db/hospital.png");
		setIconImage(i.getImage());
		
		pEste = new JPanel();
		pOeste = new JPanel();
		pCentro = new JPanel(new GridLayout(2, 1));
		pNorte = new JPanel();
		pSur = new JPanel();
		
		getContentPane().add(pEste, BorderLayout.EAST);
		getContentPane().add(pOeste, BorderLayout.WEST);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		
        tableModel = new CamaTableModel(null);
        tableCama = new JTable(tableModel);
        scrollTabla = new JScrollPane(tableCama);
        
        
        btnAsignarCama = new JButton("Asignar");
		btnVaciarCama = new JButton("Vaciar");
        
        pCentro.add(scrollTabla);
        pSur.add(btnAsignarCama);
        pSur.add(btnVaciarCama);
        
        tableCama.getColumnModel().getColumn(0).setPreferredWidth(100); 
        tableCama.getColumnModel().getColumn(1).setPreferredWidth(100); 
        tableCama.getColumnModel().getColumn(2).setPreferredWidth(100); 
        tableCama.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        
        for (Cama cama : camas) {
            Object[] fila = {cama.getNumCama(), cama.isOcupada(), cama.getTipoCama()};
            tableModel.addRow(fila);
        }
        
        
		ImageIcon iconBack = new ImageIcon(getClass().getResource("/recursos/icons8-back-25.png"));
        btnBack = new JButton(iconBack);
        btnBack.setBackground(color);
        btnBack.setPreferredSize(new Dimension(80, 25));
        
        pOeste.add(btnBack);
        
        btnBack.addActionListener(e -> {
            MenuTrabajador ventana = new MenuTrabajador(usuario);
            ventana.setVisible(true);
            ((JFrame) btnBack.getTopLevelAncestor()).dispose(); 
        });
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnBack.setBackground(Color.white);
                btnBack.setForeground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setBackground(color);
                btnBack.setForeground(Color.white);
            }
        });
		
        
        btnAsignarCama.addActionListener((e) -> {
        	
        	 int filaSeleccionada = tableCama.getSelectedRow();
        	    if (filaSeleccionada == -1) {
        	        JOptionPane.showMessageDialog(this, "Por favor, seleccione una cama primero");
        	        return;
        	    }
        	
        	 Cama camaSeleccionada = camas.get(filaSeleccionada);
        	    
        	 // 3. Verificar disponibilidad
        	 if (camaSeleccionada.isOcupada()) {
        		 JOptionPane.showMessageDialog(this, "Esta cama ya está ocupada");
        	     return;
        	 }
        	
        	 
        	 
        	 
        	 
        	 
        	 JLabel lblContrasena = new JLabel("Contrase�a:");
             JTextField txtContrasena = new JTextField();
             JLabel lblNombre = new JLabel("Nombre:");
             JTextField txtNombre = new JTextField();
             JLabel lblApellido = new JLabel("Apellido:");
             JTextField txtApellido = new JTextField();
             JLabel lblEdad = new JLabel("Edad:");
             JTextField txtEdad = new JTextField();
             JLabel lblUbicacion = new JLabel("Ubicaci�n:");
             JTextField txtUbicacion = new JTextField();
             JLabel lblCodigoPaciente = new JLabel("C�digo Paciente:");
             JTextField txtCodigoPaciente = new JTextField();
             
        	
        	
//        	JFrame frameAsignarCama  = new JFrame("Asignar cama a paciente");
//        	frameAsignarCama.setSize(400, 300);
//            frameAsignarCama.setLayout(new GridLayout(8, 2)); 
//            frameAsignarCama.getContentPane().setBackground(color);
//            
//            
            
            
            
            
            JButton btnAsignar = new JButton("Asignar Cama");
            btnAsignar.setBackground(color);
            btnAsignar.setForeground(Color.white);
            btnAsignar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                	btnAsignar.setBackground(Color.white);
                	btnAsignar.setForeground(color);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	btnAsignar.setBackground(color);
                	btnAsignar.setForeground(Color.white);
                }
            });
            
            
            
            
            
            btnAsignar.addActionListener(e2 -> {
//                int numCama = 
//                String nombre = txtNombre.getText();
//                String apellido = txtApellido.getText();
//                String ubicacion = txtUbicacion.getText();
//                int codigoP = Integer.parseInt(txtCodigoPaciente.getText());
//                List<Historial> historial = new ArrayList<>();
//                int edad;
//                if (nombre.isEmpty() || apellido.isEmpty() || ubicacion.isEmpty() || edad < 0) {
//                    JOptionPane.showMessageDialog(frameAsignarCama, "Por favor, rellena todos los campos.");
//                    return;
//                }
//                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoP, historial);
//                pacientes.add(nuevoPaciente);
//                context.guardarPaciente(nuevoPaciente);
//
//                tableCama.addRow(new Object[]{nombre, apellido, edad, codigoP});

//                frameAsignarCama.dispose(); 
            });
        });
        
        
        btnAñadirCama.addActionListener((e) -> {
        	JFrame frameAñadirCama  = new JFrame("Añadir una nueva cama");
        	frameAñadirCama.setSize(400, 300);
        	frameAñadirCama.setLayout(new GridLayout(8, 2)); 
        	frameAñadirCama.getContentPane().setBackground(color);
            
            
            JLabel lblNumCama = new JLabel("Numero de Cama:");
            JTextField txtNumCama = new JTextField();
            JLabel lblOcupada = new JLabel("Esta Ocupada?:");
            JTextField txtOcupada = new JTextField();
            JLabel lblTipo = new JLabel("Tipo:");
            JTextField txtTipo = new JTextField();
            
            
            JButton btnAñadir = new JButton("Asignar Cama");
            btnAñadir.setBackground(color);
            btnAñadir.setForeground(Color.white);
            btnAñadir.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                	btnAñadir.setBackground(Color.white);
                	btnAñadir.setForeground(color);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	btnAñadir.setBackground(color);
                	btnAñadir.setForeground(Color.white);
                }
            });
            
            
            frameAñadirCama.add(lblNumCama);
            lblNumCama.setForeground(Color.WHITE);
            frameAñadirCama.add(txtNumCama);
            frameAñadirCama.add(lblOcupada);
            lblOcupada.setForeground(Color.WHITE);
            frameAñadirCama.add(txtOcupada);
            frameAñadirCama.add(lblTipo);
            lblTipo.setForeground(Color.WHITE);
            frameAñadirCama.add(txtTipo);
            
            frameAñadirCama.add(new JLabel()); // Label vac�o para llenar �ltimo grid
            frameAñadirCama.add(btnAñadir);
            
            
            btnAñadir.addActionListener(e2 -> {
                int numCama = Integer.parseInt(txtNumCama.getText());
                String respuesta = txtOcupada.getText().toLowerCase();
                
                //IAG (Claude) Inicio
                boolean ocupada;

                if (respuesta.equals("si")) {
                    ocupada = true;
                } else if (respuesta.equals("no")) {
                    ocupada = false;
                } else {
                    ocupada = false;  // Valor por defecto
                }
                
                // IAG (Claude) Fin
                
                String tipo = txtTipo.getText();
                
                if (numCama < 0 || respuesta.isEmpty() || tipo.isEmpty()) {
                    JOptionPane.showMessageDialog(frameAñadirCama, "Por favor, rellena todos los campos.");
                    return;
                }
//                Cama nuevaCama = new Cama(numCama, ocupada, tipo);
//               
//                camas.add(nuevaCama);
//                context.guardarPaciente(nuevoPaciente);
//                tableCama.addRow(new Object[]{numCama, ocupada, tipo});

            	frameAñadirCama.dispose(); 
            });
            
            frameAñadirCama.setLocationRelativeTo(null);
            frameAñadirCama.setVisible(true);
        });
        
        
        
        btnVaciarCama.addActionListener((e) -> {
        	
        });
        
		setTitle("Camas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}
}

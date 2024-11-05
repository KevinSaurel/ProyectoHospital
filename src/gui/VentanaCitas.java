package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import domain.Cita;
import domain.Context;
import domain.Persona;

public class VentanaCitas extends JFrame{

	 	private List<Cita> citas;
	    private JTable tablaCitas;
	    private JScrollPane scrollPaneCitas;
	    private JTextField txtFiltro;
	    private DefaultTableModel modeloDatosCitas;
	    private JButton AddCita;
	    private JButton ModCita;
	    private JButton BorrCita;
	    private JButton btnBack;
	    private Persona usuario;
	
	    
	    public VentanaCitas(List<Cita> citas, Persona usuario) {
	    	
	    	Color MainColor = new Color(6,99,133);
	    	
	    	ImageIcon i = new ImageIcon("src/db/hospital.png");
			setIconImage(i.getImage());
	    
	    // Se crean los paneles que le dan forma a la ventana
			
	    	 JPanel panelBase = new JPanel(new BorderLayout());
	         panelBase.setBackground(MainColor);
	         
	         JPanel panelNorte = new JPanel(new GridLayout(1, 2));
	         panelNorte.setBackground(MainColor);
	         panelBase.add(panelNorte, BorderLayout.NORTH);
	         
	         JPanel panelSur = new JPanel(new GridLayout (1, 3));
	         panelBase.add(panelSur, BorderLayout.SOUTH);
	    // Se añade el ScrollPane
	         
	         scrollPaneCitas = new JScrollPane();
	         panelBase.add(scrollPaneCitas, BorderLayout.EAST);
	         
	    // Se añade el cuadro de texto para el filtro al panel base
	         
	         txtFiltro = new JTextField(20);
	         panelNorte.add(txtFiltro);
	         
	    // Se añaden los botones
	         
	         // Añadir
	         AddCita = new JButton("Añadir Cita");
	         AddCita.setBackground(MainColor);
	         AddCita.setForeground(Color.white);
	         AddCita.addMouseListener(new MouseAdapter() {
	             @Override
	             public void mouseEntered(MouseEvent e) {
	            	 AddCita.setBackground(Color.white);
	            	 AddCita.setForeground(MainColor);
	             }
	             public void mouseExited(MouseEvent e) {
	            	 AddCita.setBackground(MainColor);
	            	 AddCita.setForeground(Color.white);
	             }
	         });
	         panelSur.add(AddCita);
	         
	         // Modificar
	         ModCita = new JButton("Modificar Cita");
	         ModCita.setBackground(MainColor);
	         ModCita.setForeground(Color.white);
	         ModCita.addMouseListener(new MouseAdapter() {
	             @Override
	             public void mouseEntered(MouseEvent e) {
	            	 ModCita.setBackground(Color.white);
	            	 ModCita.setForeground(MainColor);
	             }
	             public void mouseExited(MouseEvent e) {
	            	 ModCita.setBackground(MainColor);
	            	 ModCita.setForeground(Color.white);
	             }
	         });
	         panelSur.add(ModCita);
	         
	         // Borrar
	         BorrCita = new JButton("Eliminar Cita");
	         BorrCita.setBackground(MainColor);
	         BorrCita.setForeground(Color.white);
	         BorrCita.addMouseListener(new MouseAdapter() {
	             @Override
	             public void mouseEntered(MouseEvent e) {
	            	 BorrCita.setBackground(Color.white);
	            	 BorrCita.setForeground(MainColor);
	             }
	             public void mouseExited(MouseEvent e) {
	            	 BorrCita.setBackground(MainColor);
	            	 BorrCita.setForeground(Color.white);
	             }
	         });
	         panelSur.add(BorrCita);
	         
	         // Se añade el botón que te lleva atrás
	         
	         ImageIcon iconBack = new ImageIcon(getClass().getResource("/db/icons8-back-25.png"));
	         btnBack = new JButton(iconBack);
	         btnBack.setBackground(MainColor);
	         btnBack.setPreferredSize(new Dimension(80, 25));
	         panelNorte.add(btnBack);
	         
	         btnBack.addActionListener((e) -> {
	         	MenuTrabajador ventana = new MenuTrabajador(usuario);
	             ventana.setVisible(true);
	             this.dispose();
	         });
	         
	    // Se añade la tabla de la info de citas     
	         
	         String[] nombreColumnas = {"Código Paciente", "Nombre Paciente", "Apellido Paciente", "Nombre Doctor", "Apellido Doctor", "Fecha"};
	         modeloDatosCitas = new DefaultTableModel(nombreColumnas, 0) {
	             };
	         
	         tablaCitas = new JTable(modeloDatosCitas);
	         tablaCitas.setGridColor(Color.black);
	         
	    // Ultimos toques para que salga todo en pantalla
	         
	         this.setVisible(true);
	         this.add(panelBase);
	         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		     setExtendedState(JFrame.MAXIMIZED_BOTH);
		     setLocationRelativeTo(null);
	         
	    }
	    
	    // Funcionalidad 
	    
	    	// Renderer de la tabla de citas
	    
	    private void initTables() {
	    	
	    }
	    	// Funcion para añadir al filtro
	    private void filtrarCitas() {
	    	
	    }
	    	// Listeners para los botones
	    
	    
	    
	    
	    
}  
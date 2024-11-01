package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clases.Persona;

public class VentanaCamas extends JFrame{
	private JButton btnAsignarCama, btnLiberarCama, btnBack;
	
	private JPanel panelArriba;
	
	private CamaTableModel tableModel;
	private Persona usuario;
	
	public VentanaCamas(Persona usuarioP) {
		
		btnAsignarCama = new JButton("Asignar");
		btnLiberarCama = new JButton("Liberar");
		
		usuario = usuarioP;
		Color color =  new Color(6,99,133);
		
		ImageIcon i = new ImageIcon("src/recursos/hospital.png");
		setIconImage(i.getImage());
		
		// Panel principal y configuraci�n general
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(color);
		
		
		ImageIcon iconBack = new ImageIcon(getClass().getResource("/recursos/icons8-back-25.png"));
        btnBack = new JButton(iconBack);
        btnBack.setBackground(color);
        btnBack.setPreferredSize(new Dimension(80, 25));
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
		
        
        panelArriba = new JPanel(new BorderLayout());
        panelArriba.add(btnBack, BorderLayout.WEST);
        
        
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);
        
		setTitle("Camas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}
}

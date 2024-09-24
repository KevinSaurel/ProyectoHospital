package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clases.Paciente;

public class MenuPaciente extends JFrame{
	
	public MenuPaciente() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(1,4,10,10));
		grid.add(new JButton("Citas"));
		grid.add(new JButton("Hospitales"));
		grid.add(new JButton("Medicos"));
		grid.add(new JButton("Historial"));
		panel.add(grid,BorderLayout.NORTH);
		
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		
		
		box.add(new JTextField("Aqui nombre del paciente"),BorderLayout.CENTER);
		box.add(new JTextField("Otros datos de paciente"),BorderLayout.CENTER);
		panel.add(box,BorderLayout.WEST);
		
		setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        
		
		
		
	}

}

package ventanas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class VentanaCamas extends JFrame{

	public VentanaCamas() {
		
		ImageIcon i = new ImageIcon("src/recursos/hospital.png");
		setIconImage(i.getImage());
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800,220);
		setTitle("Camas");
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}

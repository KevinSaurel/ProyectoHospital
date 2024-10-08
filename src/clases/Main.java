package clases;

import javax.swing.SwingUtilities;

import ventanas.MenuPaciente;
import ventanas.VentanaInicioPaciente;

public class Main {
	 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                
                //MenuPaciente ventana = new MenuPaciente();
            	VentanaInicioPaciente ventana = new VentanaInicioPaciente();
                ventana.setVisible(true);
            }
        });
    }
}

package clases;

import javax.swing.SwingUtilities;

import ventanas.MenuPaciente;
import ventanas.VentanaInicioPaciente;
import ventanas.VentanaSeleccion;

public class Main {
	 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                
                //MenuPaciente ventana = new MenuPaciente();
            	VentanaSeleccion ventana = new VentanaSeleccion();
                ventana.setVisible(true);
            }
        });
    }
}

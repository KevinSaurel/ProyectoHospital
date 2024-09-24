package clases;

import javax.swing.SwingUtilities;

import ventanas.MenuPaciente;
import ventanas.ventanaInicio;

public class Main {
	 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                
                MenuPaciente ventana = new MenuPaciente();
                ventana.setVisible(true);
            }
        });
    }
}

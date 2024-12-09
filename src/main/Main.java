package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;

import domain.Doctor;
import domain.Historial;
import domain.Paciente;
import gui.VentanaHistorial;
import gui.VentanaSeleccion;
import persistente.GestorBD;

public class Main {
    public static void main(String[] args) {
       
    	GestorBD gestorbd = new GestorBD();
    	gestorbd.crearBBDD();
    	//gestorbd.borrarBBDD();
    	
    	
    	
        SwingUtilities.invokeLater(() -> {
            VentanaSeleccion ventanaHistorial = new VentanaSeleccion();
            ventanaHistorial.setVisible(true);
          
        });
    }
}
    
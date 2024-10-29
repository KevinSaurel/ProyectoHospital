package clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;
import clases.Doctor;
import clases.Historial;
import clases.Paciente;
import ventanas.VentanaHistorial;
import ventanas.VentanaSeleccion;

public class Main {
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> {
            VentanaSeleccion ventanaHistorial = new VentanaSeleccion();
            ventanaHistorial.setVisible(true);
          
        });
    }
}
    